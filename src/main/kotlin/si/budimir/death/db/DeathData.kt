package si.budimir.death.db

import si.budimir.death.util.DateTimeHelper
import si.budimir.death.util.LocationHelper
import si.budimir.death.util.MessageHelper.Companion.capitalizeWords

data class DeathData(
    val uuid: String,
    val deathReason: String,
    val deathLocation: String,
    val deathTime: Long,
    val id: Int? = null
) {
    fun getPlaceholders(): HashMap<String, String> {
        val worldLocation = LocationHelper.string2loc(deathLocation)

        return hashMapOf(
            "loc_world" to (worldLocation?.world?.name?.capitalizeWords("_") ?: "null"),
            "locx" to worldLocation?.blockX.toString(),
            "locy" to worldLocation?.blockY.toString(),
            "locz" to worldLocation?.blockZ.toString(),
            "death_reason" to deathReason,
            "death_time" to DateTimeHelper.prettyPrintTimestamp(deathTime)
        )
    }
}