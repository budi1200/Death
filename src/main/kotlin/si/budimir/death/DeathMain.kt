package si.budimir.death

import org.bukkit.plugin.java.JavaPlugin
import si.budimir.death.commands.DeathCommand
import si.budimir.death.config.MainConfig
import si.budimir.death.config.MainConfigData
import si.budimir.death.listeners.PlayerDeathListener
import si.budimir.death.util.MessageHelper

class DeathMain: JavaPlugin() {
    lateinit var mainConfigObj: MainConfig
    lateinit var mainConfig: MainConfigData

    companion object {
        lateinit var instance: DeathMain
    }

    override fun onEnable() {
        // Init instance
        instance = this

        // Init config
        mainConfigObj = MainConfig(this)
        mainConfig = mainConfigObj.getConfig()

        MessageHelper.load(this)

        // Init commands
        getCommand("death")?.setExecutor(DeathCommand())

        // Register Event Handlers
        server.pluginManager.registerEvents(PlayerDeathListener(this), this)
    }
}