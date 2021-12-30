package si.budimir.death.listeners

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CompassMeta
import si.budimir.death.DeathMain
import si.budimir.death.util.LocationHelper
import si.budimir.death.util.MessageHelper

class PlayerRespawnListener(private val plugin: DeathMain): Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onPlayerRespawn(event: PlayerPostRespawnEvent) {
        // Check if we have data for players last death
        val player = event.player
        val deathData = DeathMain.deathStore[player.uniqueId.toString()] ?: return

        val compass = ItemStack(Material.COMPASS)

        val deathLocation = LocationHelper.string2loc(deathData.deathLocation)
        // Death info placeholders
        val placeholders = deathData.getPlaceholders()
        placeholders["playerName"] = player.name

        compass.editMeta { meta ->
            meta as CompassMeta

            meta.setCustomModelData(22)
            meta.displayName(MessageHelper.getParsedString(plugin.mainConfig.lang.deathCompassTitle))
            meta.lore(plugin.mainConfig.lang.deathCompassLore.map { MessageHelper.getParsedString(it, placeholders) })
            meta.isLodestoneTracked = false
            meta.lodestone = deathLocation
        }

        player.inventory.addItem(compass)
        player.updateInventory()
        DeathMain.deathStore.remove(player.uniqueId.toString())
    }
}