package di

import co.touchlab.kermit.Logger
import data.getUserAgent
import domain.holder.UserHolder
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val NETWORK_REQUEST_CONNECT_TIMEOUT = 10000L
const val NETWORK_REQUEST_TOTAL_TIMEOUT  = 60000L
const val NETWORK_REQUEST_SOCKET_TIMEOUT = 15000L
const val NETWORK_REQUEST_RETRY_ATTEMPTS = 3

val networkModule = module {
    single {
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        Logger.d { message }
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                connectTimeoutMillis = NETWORK_REQUEST_CONNECT_TIMEOUT
                requestTimeoutMillis = NETWORK_REQUEST_TOTAL_TIMEOUT
                socketTimeoutMillis = NETWORK_REQUEST_SOCKET_TIMEOUT
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = NETWORK_REQUEST_RETRY_ATTEMPTS)
                exponentialDelay()
            }
            install(DefaultRequest) {
                val userHolder: UserHolder = get()
                userHolder.user?.jwt?.let { jwt ->
                    if (!headers.contains("No-Authentication")) {
                        header(HttpHeaders.Authorization, "Bearer $jwt")
                    }
                }
                header(HttpHeaders.UserAgent, getUserAgent())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}