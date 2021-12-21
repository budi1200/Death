package si.budimir.death.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import si.budimir.death.DeathMain

class PlayerDeathListener(private val plugin: DeathMain): Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        // Return if player didnt have keep inventory
        if (!event.keepInventory) return

        DeathMain.deathStore.remove(event.player.uniqueId.toString())
        plugin.logger.info("Removing death from store because keep inventory was on")
    }
}