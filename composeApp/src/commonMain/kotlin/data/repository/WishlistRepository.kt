package data.repository

import API_BASE_URL
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.homato.oddspot.Database
import data.ENDPOINT_WISHLIST
import data.ENDPOINT_WISHLIST_ADD
import data.ENDPOINT_WISHLIST_REMOVE
import data.request.WishlistRequest
import data.response.WishlistResponse
import domain.util.DomainError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Singleton

@Singleton
class WishlistRepository(
    private val client: HttpClient,
    private val database: Database
) {

    suspend fun getWishlistFlow(): Flow<List<Int>> =
        database.wishlistQueries
            .selectAllWishlistItems()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toInt() } }

    suspend fun getWishlist(): Result<List<Int>, Throwable> = withContext(Dispatchers.IO) {
        runCatching {
            database.wishlistQueries
                .selectAllWishlistItems()
                .executeAsList()
                .map { it.toInt() }
        }
    }

    suspend fun updateWishlist(): Result<Unit, DomainError.Network> =
        withContext(Dispatchers.IO) {
            runCatching {
                val response = client.get(API_BASE_URL + ENDPOINT_WISHLIST)
                    .body<WishlistResponse>()
                response.spotIds.forEach {
                    database.wishlistQueries.insertWishlistItem(it.toLong())
                }
            }.mapError {
                DomainError.Network(it)
            }
        }

    suspend fun addSpotToWishlist(spotId: Int): Result<Unit, DomainError.Network> =
        withContext(Dispatchers.IO) {
            runCatching {
                database.wishlistQueries.insertWishlistItem(spotId.toLong())
                client.post(API_BASE_URL + ENDPOINT_WISHLIST_ADD) {
                    setBody(WishlistRequest(spotId))
                }
                Unit
            }.mapError {
                // If request fails deleting the item from the local cache
                database.wishlistQueries.removeWishlistItem(spotId.toLong())
                DomainError.Network(it)
            }
        }

    suspend fun removeSpotFromWishlist(spotId: Int): Result<Unit, DomainError.Network> =
        withContext(Dispatchers.IO) {
            runCatching {
                database.wishlistQueries.removeWishlistItem(spotId.toLong())
                client.delete(API_BASE_URL + ENDPOINT_WISHLIST_REMOVE) {
                    setBody(WishlistRequest(spotId))
                }
                Unit
            }.mapError {
                // If request fails adding the item back to the local cache
                database.wishlistQueries.insertWishlistItem(spotId.toLong())
                DomainError.Network(it)
            }
        }
}