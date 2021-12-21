package si.budimir.death.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import si.budimir.death.DeathMain
import java.io.File

class DatabaseManager(private val plugin: DeathMain) {
    private lateinit var db: Database

    fun connect() {
        val dbFile = File(plugin.dataFolder, "database.db")
        var isNew = false

        try {
            if (!dbFile.exists()) {
                dbFile.createNewFile()
                isNew = true
            }

            val url: String = "jdbc:sqlite:" + dbFile.path

            db = Database.connect(url, "org.sqlite.JDBC")

            if (isNew) {
                transaction {
                    SchemaUtils.create(DeathEntity)
                }
            }
        } catch (e: Exception) {
            plugin.logger.severe("Failed to connect to database!")
            e.printStackTrace()
        }
    }

    fun getDatabase(): Database {
        return db
    }
}

object DeathEntity: Table("death_entity") {
    val id: Column<Int> = integer("id").autoIncrement()
    val uuid: Column<String> = varchar("uuid", 254)
    val deathReason: Column<String> = varchar("death_reason", 254)
    val deathLocation: Column<String> = varchar("death_location", 254)
    val deathTime: Column<Long> = long("death_time")

    override val primaryKey = PrimaryKey(id)
}