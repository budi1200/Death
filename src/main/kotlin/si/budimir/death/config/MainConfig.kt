package si.budimir.death.config

import si.budimir.death.DeathMain

class MainConfig(plugin: DeathMain) : ConfigBase<MainConfigData>(plugin, "config.conf", MainConfigData::class.java) {
}