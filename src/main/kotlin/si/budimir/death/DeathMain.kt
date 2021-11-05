package si.budimir.death

import org.bukkit.plugin.java.JavaPlugin
import si.budimir.death.commands.DeathCommand
import si.budimir.death.config.MainConfig
import si.budimir.death.listeners.PlayerDeathListener

class DeathMain: JavaPlugin() {
    companion object {
        lateinit var instance: DeathMain
        lateinit var mainConfig: MainConfig
    }

    override fun onEnable() {
        // Init instance
        instance = this

        // Init config
        mainConfig = MainConfig(this)

        // Init commands
        getCommand("death")?.setExecutor(DeathCommand())

        // Register Event Handlers
        server.pluginManager.registerEvents(PlayerDeathListener(), this)
    }
}