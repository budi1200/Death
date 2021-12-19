package si.budimir.death.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MainConfigData(
    val pluginPrefix: String = "<bold>Death »<reset> ",
    val lang: Lang = Lang()
)

@ConfigSerializable
data class Lang(
    val missingPermission: String = "You do not have the required premission",
    val deathMessage: String = "Deathed"
)