package si.budimir.death.db

import si.budimir.death.util.DateTimeHelper
import si.budimir.death.util.LocationHelper
import si.budimir.death.util.MessageHelper.Companion.capitalize
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
            "locWorld" to (worldLocation?.world?.name?.capitalizeWords("_") ?: "null"),
            "locX" to worldLocation?.blockX.toString(),
            "locY" to worldLocation?.blockY.toString(),
            "locZ" to worldLocation?.blockZ.toString(),
            "deathReason" to deathReason.lowercase().capitalize(),
            "deathTime" to DateTimeHelper.prettyPrintTimestamp(deathTime)
        )
    }
}