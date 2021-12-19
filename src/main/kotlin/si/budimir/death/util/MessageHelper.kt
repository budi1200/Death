package si.budimir.death.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import si.budimir.death.DeathMain

abstract class MessageHelper {
    companion object {
        private var plugin = DeathMain.instance
        private lateinit var pluginPrefix: Component

        fun load(plugin: DeathMain) {
            this.plugin = plugin
            pluginPrefix = parseString(plugin.mainConfig.pluginPrefix)
        }

        private val miniMessage = MiniMessage.builder().markdown().build()

        fun reloadPrefix() {
            pluginPrefix = parseString(plugin.mainConfig.pluginPrefix)
        }

        // Send message with string from config
        fun sendMessage(player: CommandSender, message: String, placeholders: MutableMap<String, String> = hashMapOf(), prefix: Boolean = true) {
            var tmp = Component.text("")

            if (prefix) {
                tmp = tmp.append(pluginPrefix)
            }

            tmp = tmp.append(getParsedString(message, placeholders))

            player.sendMessage(tmp)
        }

        fun parseString(key: String, placeholders: Map<String, String> = hashMapOf()): Component {
            return Component
                .text("")
                .decoration(TextDecoration.ITALIC, false)
                .append(
                    miniMessage.parse(key, placeholders)
                )
        }

        fun getParsedString(key: String, placeholders: Map<String, String> = hashMapOf()): Component {
            return Component
                .text("")
                .decoration(TextDecoration.ITALIC, false)
                .append(
                    miniMessage.parse(key, placeholders)
                )
        }
    }
}