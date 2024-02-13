package data.repository

import com.homato.oddspot.Database
import org.koin.core.annotation.Singleton
import util.toBoolean
import util.toLong

@Singleton
class LocalFlagsRepository(private val database: Database) {

    suspend fun setTutorialSeen() {
        database.localFlagQueries.updateTutorialSeen(true.toLong())
    }

    suspend fun isTutorialSeen() = database
        .localFlagQueries
        .select()
        .executeAsOne()
        .tutorialSeen
        .toBoolean()

}