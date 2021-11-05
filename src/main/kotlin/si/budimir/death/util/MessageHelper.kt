package si.budimir.death.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import si.budimir.death.DeathMain
import si.budimir.death.enums.Lang

abstract class MessageHelper {
    companion object {
        private val plugin = DeathMain.instance
        private val config = DeathMain.mainConfig
        private val pluginPrefix = config.getParsedString("pluginPrefix")

        // Send message with string from config
        fun sendMessage(player: Player, key: Lang, placeholders: MutableMap<String, String> = hashMapOf(), prefix: Boolean = true) {
            val path = key.getPath()
            var tmp = Component.text("")

            if (prefix) {
                tmp = tmp.append(pluginPrefix)
            }

            tmp = tmp.append(config.getParsedString(path, placeholders))

            player.sendMessage(tmp)
        }

        // Send message with provided string
        fun sendMessage(player: Player, message: String, prefix: Boolean = true) {
            var tmp = Component.text("")

            if (prefix) {
                tmp = tmp.append(pluginPrefix)
            }

            tmp = tmp.append(MiniMessage.markdown().parse(message))

            player.sendMessage(tmp)
        }
    }
}