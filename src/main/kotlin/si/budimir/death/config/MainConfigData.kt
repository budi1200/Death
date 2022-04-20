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
    @Comment("Usable placeholders: loc_world, locx, locy, locz, death_reason, death_time")
    val deathMessage: String = "<red>You died! At <locx>:<locy>:<locz> in <loc_world> from <death_reason>",
    val noDeathsFound: String = "There are no deaths on record",
    @Comment("Usable placeholders: loc_world, locx, locy, locz, death_reason, death_time, deathRelativeTime")
    val lastDeathLocation: String = "Last death location is at <locx>:<locy>:<locz> in <loc_world> (<distance>) from <death_reason> pred <death_time>",
    @Comment("Usable placeholders: loc_world, locx, locy, locz, death_reason")
    val deathEntry: String = "Death at at <locx>:<locy>:<locz> in <loc_world> from <death_reason> at <death_time>",
    val deathCompassTitle: String = "Death Compass",
    @Comment("Usable placeholders: loc_world, locx, locy, locz, death_reason")
    val deathCompassLore: List<String> = listOf("<yellow>Death location: <locx>:<locy>:<locz> in <loc_world> at <death_time>"),
    val lastFiveDeathsHeader: String = "Your last 5 deaths:"
    )