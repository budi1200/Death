package si.budimir.death.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class MainConfigData(
    val pluginPrefix: String = "<bold>Death »<reset> ",
    val lang: Lang = Lang()
)

@ConfigSerializable
data class Lang(
    val missingPermission: String = "You do not have the required premission",
    val locationLogFail: String = "Failed to save death location",
    @Comment("Usable placeholders: locWorld, locX, locY, locZ, deathReason")
    val deathMessage: String = "<dark_red>You died! At <locX>:<locY>:<locZ> in <locWorld> from <deathReason>",
    val noDeathsFound: String = "There are no deaths on record",
    @Comment("Usable placeholders: locWorld, locX, locY, locZ, deathReason, deathTime")
    val lastDeathLocation: String = "Last death location is at <locX>:<locY>:<locZ> in <locWorld> (<distance>) from <deathReason> pred <deathTime>",
    @Comment("Usable placeholders: locWorld, locX, locY, locZ, deathReason")
    val deathEntry: String = "Death at at <locX>:<locY>:<locZ> in <locWorld> from <deathReason> at <deathTime>",
    val deathCompassTitle: String = "Death Compass",
    val deathCompassLore: List<String> = listOf("<yellow>Death location: <locX>:<locY>:<locZ> in <locWorld> at <deathTime>"),
    val lastFiveDeathsHeader: String = "Your last 5 deaths:"
    )