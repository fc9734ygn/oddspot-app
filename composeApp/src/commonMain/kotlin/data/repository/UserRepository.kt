package data.repository

import API_BASE_URL
import app.cash.sqldelight.coroutines.asFlow
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrThrow
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.homato.oddspot.Database
import com.homato.oddspot.User
import data.ENDPOINT_AUTHENTICATE
import data.ENDPOINT_CHANGE_AVATAR
import data.ENDPOINT_CHANGE_USERNAME
import data.ENDPOINT_LOGIN
import data.ENDPOINT_REGISTER
import data.MULTIPART_IMAGE_KEY
import data.MimeTypeMapper
import data.UPLOAD_IMAGE_FILE_NAME
import data.request.LoginRequest
import data.request.RegisterRequest
import data.request.UsernameChangeRequest
import data.response.LoginResponse
import domain.util.DomainError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
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

    suspend fun register(email: String, password: String): Result<Unit, DomainError.Network> {
        return runCatching {
            client.post(API_BASE_URL + ENDPOINT_REGISTER) {
                setBody(RegisterRequest(email, password))
            }
            Unit
        }.mapError {
            DomainError.Network(it)
        }
    }

    suspend fun login(email: String, password: String): Result<Unit, Throwable> {
        return runCatching {
            val response = client.post(API_BASE_URL + ENDPOINT_LOGIN) {
                setBody(LoginRequest(email, password))
            }.body<LoginResponse>()

            database.userQueries.insertUser(
                id = DEFAULT_PROFILE_ID,
                userId = response.id,
                name = response.username,
                email = response.email,
                imageUrl = response.imageUrl,
                jwt = response.jwt,
                avatar_url = response.avatar
            )
        }
    }

    suspend fun changeUsername(username: String): Result<Unit, Throwable> {
        return runCatching {
            client.patch(API_BASE_URL + ENDPOINT_CHANGE_USERNAME) {
                setBody(UsernameChangeRequest(username))
            }
        }.map {
            database.userQueries.updateUsername(username, DEFAULT_PROFILE_ID)
        }
    }

    suspend fun changeAvatar(avatar: ByteArray): Result<String, Throwable> {
        return runCatching {
            val mimeType = MimeTypeMapper.detectImageFormat(avatar).getOrThrow()
            val multipartData = MultiPartFormDataContent(
                formData {
                    append(MULTIPART_IMAGE_KEY, avatar, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=$UPLOAD_IMAGE_FILE_NAME")
                        append(HttpHeaders.ContentType, mimeType)
                    })
                }
            )

            val response = client.patch(API_BASE_URL + ENDPOINT_CHANGE_AVATAR) {
                contentType(ContentType.MultiPart.FormData)
                setBody(multipartData)
            }.body<String>()

            database.userQueries.updateAvatar(response, DEFAULT_PROFILE_ID)
            response
        }
    }

    suspend fun authenticate(): Result<Unit, Throwable> {
        return runCatching {
            client.post(API_BASE_URL + ENDPOINT_AUTHENTICATE)
        }
    }

    suspend fun deleteLocalData(): Result<Unit, Throwable> = withContext(Dispatchers.IO) {
        runCatching {
            database.wishlistQueries.deleteAll()
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

    suspend fun getUser(): Result<User?, Throwable> = withContext(Dispatchers.IO) {
        runCatching {
            database
                .userQueries
                .selectUser()
                .executeAsOneOrNull()
        }
    }

}