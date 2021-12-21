package si.budimir.death.commands.subcommands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import si.budimir.death.DeathMain
import si.budimir.death.commands.SubCommandBase
import si.budimir.death.enums.Permission
import si.budimir.death.util.MessageHelper

class ReloadSubCommand: SubCommandBase {
    private val plugin = DeathMain.instance

    override fun execute(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        val result = plugin.mainConfigObj.reloadConfig()
        MessageHelper.reloadPrefix()

        if (!result) {
            MessageHelper.sendMessage(sender, "Error reloading plugin!")
            return true
        }

        plugin.mainConfig = plugin.mainConfigObj.getConfig()
        MessageHelper.sendMessage(sender, "<green>Reload Complete!")
        return true
    }

    override fun getPermission(): String {
        return Permission.ADMIN.getPerm()
    }

    override fun getDesc(): String {
        return "Reloads the plugin"
    }
}