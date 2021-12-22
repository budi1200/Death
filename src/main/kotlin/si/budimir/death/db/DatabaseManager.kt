package si.budimir.death.db

import org.ktorm.database.Database
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import org.ktorm.support.sqlite.SQLiteDialect
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

            db = Database.connect(
                url = url,
                driver = "si.budimir.death.libs.sqlite.JDBC",
                dialect = SQLiteDialect()
            )

            if (isNew) {
                db.useConnection {
                    val sql = """
                        CREATE TABLE death_entity (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          uuid BINARY(16) NOT NULL,
                          death_reason VARCHAR(254) NOT NULL,
                          death_location VARCHAR(254) NOT NULL,
                          death_time BIGINT NOT NULL
                        )
                    """.trimIndent()

                    it.prepareStatement(sql).execute()
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

object DeathEntity: Table<Nothing>("death_entity") {
    val id = int("id").primaryKey()
    val uuid = varchar("uuid")
    val deathReason = varchar("death_reason")
    val deathLocation = varchar("death_location")
    val deathTime = long("death_time")
}