package di

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
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val NETWORK_REQUEST_TIMEOUT = 15000L
const val NETWORK_REQUEST_RETRY_ATTEMPTS = 5

val networkModule = module {
    single {
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
//                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
//                )
            }
            install(Logging) {
                // TODO : Add logger
//                logger = HttpLogger
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = NETWORK_REQUEST_TIMEOUT
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