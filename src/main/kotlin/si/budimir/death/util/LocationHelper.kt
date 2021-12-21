package si.budimir.death.util

import org.bukkit.Location
import si.budimir.death.DeathMain

class LocationHelper {
    companion object {
        private val plugin = DeathMain.instance

        fun loc2string(location: Location): String {
            return "${location.blockX}:${location.blockY}:${location.blockZ}:${location.world.name}"
        }

        fun string2loc(locString: String): Location? {
            val locArr = locString.split(":")
            val world = plugin.server.getWorld(locArr[3])

            if (world == null) {
                plugin.logger.severe("Error converting string to location! World not found - ${locArr[3]}")
                return null
            }

            return Location(world, locArr[0].toDouble(), locArr[1].toDouble(), locArr[2].toDouble())
        }
    }
}