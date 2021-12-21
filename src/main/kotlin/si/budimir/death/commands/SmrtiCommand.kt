package si.budimir.death.commands

import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import si.budimir.death.DeathMain
import si.budimir.death.db.DatabaseHelper
import si.budimir.death.util.MessageHelper

class SmrtiCommand(private val plugin: DeathMain): CommandExecutor, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            MessageHelper.sendMessage(sender, "This command can only be used ingame!")
            return true
        }

        DatabaseHelper.getLastXDeaths(sender.uniqueId.toString(), 5) { latestDeaths ->
            if (latestDeaths.isEmpty()) {
                MessageHelper.sendMessage(sender, plugin.mainConfig.lang.noDeathsFound)
                return@getLastXDeaths
            }

            var output: Component = MessageHelper.pluginPrefix.append(Component.text(plugin.mainConfig.lang.lastFiveDeathsHeader + "\n"))

            for (death in latestDeaths) {
                // Death info placeholders
                val placeholders = death.getPlaceholders()

                output = output
                    .append(MessageHelper.getParsedString(" <bold>• <reset>"))
                    .append(MessageHelper.getParsedString(plugin.mainConfig.lang.deathEntry, placeholders))
                    .append(Component.text("\n"))
            }

            sender.sendMessage(output)
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        return null
    }
}