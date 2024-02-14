package data.repository

import com.github.michaelbull.result.runCatching
import com.homato.oddspot.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Singleton
import util.toBoolean
import util.toLong

@Singleton
class LocalFlagsRepository(private val database: Database) {

    init {
        database.localFlagQueries.insertDefault()
    }

    suspend fun setTutorialSeen() = withContext(Dispatchers.IO) {
        runCatching {
            database.localFlagQueries.updateTutorialSeen(true.toLong())
        }
    }

    suspend fun isTutorialSeen() = withContext(Dispatchers.IO) {
        runCatching {
            database
                .localFlagQueries
                .select()
                .executeAsOne()
                .tutorialSeen
                .toBoolean()
        }
    }
}