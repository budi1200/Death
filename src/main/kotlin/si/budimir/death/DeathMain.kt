package si.budimir.death

import org.bukkit.plugin.java.JavaPlugin
import si.budimir.death.commands.DeathCommand
import si.budimir.death.commands.KjeSemUmrlCommand
import si.budimir.death.commands.SmrtiCommand
import si.budimir.death.config.MainConfig
import si.budimir.death.config.MainConfigData
import si.budimir.death.db.DatabaseManager
import si.budimir.death.db.DeathData
import si.budimir.death.listeners.PlayerDamageListener
import si.budimir.death.listeners.PlayerDeathListener
import si.budimir.death.listeners.PlayerRespawnListener
import si.budimir.death.util.MessageHelper
import java.time.Duration
import java.time.Instant

class DeathMain: JavaPlugin() {
    lateinit var mainConfigObj: MainConfig
    lateinit var mainConfig: MainConfigData
    lateinit var dbManager: DatabaseManager
    private val startTime = Instant.now()

    companion object {
        lateinit var instance: DeathMain
        val deathStore = hashMapOf<String, DeathData>()
    }

    override fun onEnable() {
        // Init instance
        instance = this

        // Database setup
        dbManager = DatabaseManager(this)
        dbManager.connect()

        // Init config
        mainConfigObj = MainConfig(this)
        mainConfig = mainConfigObj.getConfig()

        MessageHelper.load(this)

        // Init commands
        getCommand("death")?.setExecutor(DeathCommand(this))
        getCommand("kjesemumrl")?.setExecutor(KjeSemUmrlCommand(this))
        getCommand("smrti")?.setExecutor(SmrtiCommand(this))

        // Register Event Handlers
        server.pluginManager.registerEvents(PlayerDamageListener(this), this)
        server.pluginManager.registerEvents(PlayerDeathListener(this), this)
        server.pluginManager.registerEvents(PlayerRespawnListener(this), this)

        val loadTime = Duration.between(startTime, Instant.now())
        logger.info("Death loaded (took ${loadTime.toMillis()}ms)")
    }
}