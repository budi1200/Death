package si.budimir.death.commands.subcommands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import si.budimir.death.DeathMain
import si.budimir.death.commands.SubCommandBase
import si.budimir.death.enums.Permission
import si.budimir.death.util.MessageHelper

class ReloadSubCommand: SubCommandBase {
    override fun execute(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        val result = DeathMain.instance.mainConfigObj.reloadConfig()
        MessageHelper.reloadPrefix()

        if (!result) {
            MessageHelper.sendMessage(sender, "Error reloading plugin!")
            return true
        }

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