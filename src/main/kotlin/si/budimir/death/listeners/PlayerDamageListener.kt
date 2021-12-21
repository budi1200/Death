package si.budimir.death.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import si.budimir.death.DeathMain
import si.budimir.death.db.DatabaseHelper
import si.budimir.death.db.DeathData
import si.budimir.death.util.LocationHelper
import si.budimir.death.util.MessageHelper

class PlayerDamageListener(private val plugin: DeathMain): Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun playerDeathHandler(e: EntityDamageEvent) {
        // Exit if not player
        if (e.entity !is Player) return

        val player = e.entity as Player

        // Exit if player didn't die
        if (player.health - e.finalDamage > 0) return

        val deathLocation = e.entity.location
        val deathTime = System.currentTimeMillis()
        val deathCause: String

        @Suppress("LiftReturnOrAssignment")
        if (e.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            deathCause = (e as EntityDamageByEntityEvent).damager.type.toString()
        } else {
            deathCause = e.cause.toString()
        }

        val death = DeathData(player.uniqueId.toString(), deathCause, LocationHelper.loc2string(deathLocation), deathTime)

        DeathMain.deathStore[death.uuid] = death

        DatabaseHelper.logDeath(death) {
            if (!it) {
                MessageHelper.sendMessage(player, plugin.mainConfig.lang.locationLogFail)
            }
        }

        // Save death info
        val placeholders = death.getPlaceholders()

        MessageHelper.sendMessage(player, plugin.mainConfig.lang.deathMessage, placeholders)
    }
}