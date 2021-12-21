package si.budimir.death.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import si.budimir.death.DeathMain
import si.budimir.death.commands.subcommands.ReloadSubCommand
import si.budimir.death.util.MessageHelper

class DeathCommand(private val plugin: DeathMain) : CommandExecutor, TabExecutor {
    private val subCommands: MutableMap<String, SubCommandBase> = HashMap()
    private var subCommandsList: List<String> = emptyList()

    init {
        subCommands["reload"] = ReloadSubCommand()

        subCommandsList = subCommands.keys.toList()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (args.isNotEmpty()) run {
            val sc: SubCommandBase = subCommands[args[0]] ?: return false
            val reqPerm: String = sc.getPermission()

            if (reqPerm == "" || sender.hasPermission(reqPerm)) {
                sc.execute(sender, command, label, args)
            } else {
                MessageHelper.sendMessage(sender, plugin.mainConfig.lang.missingPermission)
                plugin.logger.info("${sender.name} is missing permission $reqPerm")
            }
        } else {
            // TODO: Base command
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        return when {
            args[0] == "" -> {
                subCommandsList
            }
            args.size == 1 -> {
                subCommandsList.filter { it.contains(args[0], ignoreCase = true) }
            }
            else -> {
                val sc: SubCommandBase = subCommands[args[0]] ?: return emptyList()
                return sc.onTabComplete(sender, command, alias, args)
            }
        }
    }

    fun getSubCommands(): MutableMap<String, SubCommandBase> {
        return subCommands
    }
}