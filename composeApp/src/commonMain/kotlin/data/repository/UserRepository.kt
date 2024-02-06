package data.repository

import app.cash.sqldelight.coroutines.asFlow
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import com.homato.oddspot.Database
import com.homato.oddspot.User
import API_BASE_URL
import data.ENDPOINT_AUTHENTICATE
import data.ENDPOINT_LOGIN
import data.ENDPOINT_REGISTER
import data.request.AuthRequest
import data.response.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Singleton

const val DEFAULT_PROFILE_ID = "default_profile_id"

@Singleton
class UserRepository(
    private val client: HttpClient,
    private val database: Database
) : BaseRepository() {

    suspend fun register(email: String, password: String): Result<Unit, Throwable> {
        return runCatching {
            client.post(API_BASE_URL + ENDPOINT_REGISTER) {
                setBody(AuthRequest(email, password))
            }
        }
    }

    suspend fun login(email: String, password: String): Result<Unit, Throwable> {
        return runCatching {
            val response = client.post(API_BASE_URL + ENDPOINT_LOGIN) {
                setBody(AuthRequest(email, password))
            }.body<TokenResponse>()

            database.userQueries.insertUser(
                id = DEFAULT_PROFILE_ID,
                name = email.substringBefore("@"),
                email = email,
                imageUrl = null,
                jwt = response.token
            )
        }
    }

    suspend fun authenticate(): Result<Unit, Throwable> {
        return runCatching {
            client.post(API_BASE_URL + ENDPOINT_AUTHENTICATE)
        }
    }

    suspend fun logout(): Result<Unit, Throwable> = withContext(Dispatchers.IO) {
        runCatching {
            database.userQueries.deleteUser()
        }
    }

    fun getUserFlow(): Flow<User?> {
        return database
            .userQueries
            .selectUser()
            .asFlow()
            .map { it.executeAsOneOrNull() }
    }

}