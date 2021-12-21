package si.budimir.death.db

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import si.budimir.death.DeathMain

class DatabaseHelper {
    companion object {
        private val plugin = DeathMain.instance

        // Database Interaction Functions
        fun logDeath(data: DeathData, callback: (Boolean) -> Unit) {
            plugin.server.scheduler.runTaskAsynchronously(plugin, Runnable {
                try {
                    transaction(plugin.dbManager.getDatabase()) {
                        DeathEntity.insert {
                            it[uuid] = data.uuid
                            it[deathReason] = data.deathReason
                            it[deathLocation] = data.deathLocation
                            it[deathTime] = data.deathTime
                        }
                    }

                    callback(true)
                } catch (e: Error) {
                    plugin.logger.severe("There was an error saving a death - ${data.uuid}")
                    e.printStackTrace()

                    callback(false)
                }
            })
        }

        fun getLastXDeaths(playerUUID: String, numberOfDeaths: Int, callback: (List<DeathData>) -> Unit) {
            plugin.server.scheduler.runTaskAsynchronously(plugin, Runnable {
                try {
                    val select = transaction(plugin.dbManager.getDatabase()) {
                        DeathEntity
                            .select { DeathEntity.uuid eq playerUUID }
                            .orderBy(DeathEntity.deathTime to SortOrder.DESC)
                            .limit(numberOfDeaths)
                            .map { mapToDeathData(it) }
                    }

                    callback(select)
                } catch (e: Error) {
                    plugin.logger.severe("There was an error getting last $numberOfDeaths deaths of $playerUUID")
                    e.printStackTrace()
                    callback(listOf())
                }
            })
        }

        private fun mapToDeathData(it: ResultRow) = DeathData(
            it[DeathEntity.uuid],
            it[DeathEntity.deathReason],
            it[DeathEntity.deathLocation],
            it[DeathEntity.deathTime],
            it[DeathEntity.id]
        )
    }
}