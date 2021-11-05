package si.budimir.death.commands.subcommands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import si.budimir.death.DeathMain
import si.budimir.death.commands.SubCommandBase
import si.budimir.death.util.MessageHelper

class ReloadSubCommand: SubCommandBase {
    override fun execute(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val config = DeathMain.mainConfig

        config.reloadConfig()

        MessageHelper.sendMessage(sender as Player, "<green>Reload Complete!")
        return true
    }

    override fun getPermission(): String {
        TODO("Not yet implemented")
    }

    override fun getDesc(): String {
        TODO("Not yet implemented")
    }
}