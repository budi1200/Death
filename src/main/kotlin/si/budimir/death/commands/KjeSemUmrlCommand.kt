package si.budimir.death.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.joda.time.DateTime
import org.joda.time.Instant
import org.joda.time.Period
import si.budimir.death.DeathMain
import si.budimir.death.db.DatabaseHelper
import si.budimir.death.util.DateTimeHelper
import si.budimir.death.util.LocationHelper
import si.budimir.death.util.MessageHelper
import kotlin.math.roundToInt

class KjeSemUmrlCommand(private val plugin: DeathMain): CommandExecutor, TabExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            MessageHelper.sendMessage(sender, "This command can only be used ingame!")
            return true
        }

        val playerLoc = sender.location

        DatabaseHelper.getLastXDeaths(sender.uniqueId.toString(), 1) {
            if (it.isEmpty()) {
                MessageHelper.sendMessage(sender, plugin.mainConfig.lang.noDeathsFound)
                return@getLastXDeaths
            }

            val latestDeath = it.first()
            val deathLocation = LocationHelper.string2loc(latestDeath.deathLocation)
            val dateInstant = Instant.ofEpochMilli(latestDeath.deathTime)
            val relativeTime = Period(dateInstant, DateTime())

            // Death info placeholders
            val placeholders = latestDeath.getPlaceholders()
            placeholders["death_relative_time"] = DateTimeHelper.prettyPrintPeriod(relativeTime)

            if (playerLoc.world == deathLocation?.world) {
                placeholders["distance"] = playerLoc.distance(deathLocation ?: playerLoc).roundToInt().toString() + 'm'
            } else {
                placeholders["distance"] = "-1m"
            }

            MessageHelper.sendMessage(sender, plugin.mainConfig.lang.lastDeathLocation, placeholders)
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): MutableList<String>? {
        return null
    }
}