package si.budimir.death.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import si.budimir.death.enums.Lang
import si.budimir.death.util.MessageHelper

class PlayerDeathListener: Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun playerDeathHandler(e: EntityDamageEvent) {
        // Exit if not player
        if (e.entity !is Player) return

        val player = e.entity as Player

        // Exit if player didn't die
        if (player.health - e.damage > 0) return

        // Save death info
        val placeholders = hashMapOf(
            "location" to ""
        )
        MessageHelper.sendMessage(player, Lang.DEATH_MESSAGE)

    }
}