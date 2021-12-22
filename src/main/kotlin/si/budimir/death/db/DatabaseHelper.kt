package si.budimir.death.db

import org.ktorm.dsl.*
import si.budimir.death.DeathMain

class DatabaseHelper {
    companion object {
        private val plugin = DeathMain.instance

        // Database Interaction Functions
        fun logDeath(data: DeathData, callback: (Boolean) -> Unit) {
            plugin.server.scheduler.runTaskAsynchronously(plugin, Runnable {
                try {
                    plugin.dbManager.getDatabase().useTransaction {
                        plugin.dbManager.getDatabase().insert(DeathEntity) {
                            set(it.uuid, data.uuid)
                            set(it.deathReason, data.deathReason)
                            set(it.deathLocation, data.deathLocation)
                            set(it.deathTime, data.deathTime)
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
                    val database = plugin.dbManager.getDatabase()
                    val select = database.useTransaction {
                        database
                            .from(DeathEntity)
                            .select()
                            .where(DeathEntity.uuid eq playerUUID)
                            .orderBy(DeathEntity.deathTime.desc())
                            .limit(numberOfDeaths)
                            .mapNotNull {
                                if (it[DeathEntity.uuid] == null) return@mapNotNull null

                                mapToDeathData(it)
                            }
                    }

                    callback(select)
                } catch (e: Error) {
                    plugin.logger.severe("There was an error getting last $numberOfDeaths deaths of $playerUUID")
                    e.printStackTrace()
                    callback(listOf())
                }
            })
        }

        private fun mapToDeathData(it: QueryRowSet) = DeathData(
            it[DeathEntity.uuid]!!,
            it[DeathEntity.deathReason]!!,
            it[DeathEntity.deathLocation]!!,
            it[DeathEntity.deathTime]!!,
            it[DeathEntity.id]
        )
    }
}