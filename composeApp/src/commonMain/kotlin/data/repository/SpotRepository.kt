package data.repository

import API_BASE_URL
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrThrow
import com.github.michaelbull.result.runCatching
import com.homato.oddspot.Database
import com.homato.oddspot.ExploreSpot
import data.ENDPOINT_REPORT_SPOT
import data.ENDPOINT_SPOTS
import data.ENDPOINT_SUBMITTED_SPOTS
import data.ENDPOINT_SUBMIT_SPOT
import data.MULTIPART_DATA_KEY
import data.MULTIPART_IMAGE_KEY
import data.MimeTypeMapper
import data.UPLOAD_IMAGE_FILE_NAME
import data.model.SpotWithVisits
import data.model.VisitedSpot
import data.request.ReportSpotRequest
import data.request.SubmitSpotRequest
import data.response.SpotsFeedResponse
import data.response.SubmittedSpot
import data.response.SubmittedSpotsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton
import ui.util.Location
import util.toLong

@Suppress("MagicNumber")
@Singleton
class SpotRepository(
    private val client: HttpClient,
    private val database: Database
) {

    suspend fun getSpotWithVisitsBySpotId(id: Int): Result<SpotWithVisits, Throwable> =
        withContext(Dispatchers.IO) {
            runCatching {
                val spots = database.exploreSpotQueries.selectById(id.toLong()).executeAsOne()
                val visits =
                    database.exploreVisitQueries.selectBySpotId(id.toLong()).executeAsList()
                SpotWithVisits(spots, visits)
            }
        }

    suspend fun getSpotById(id: Int): Result<ExploreSpot, Throwable> =
        withContext(Dispatchers.IO) {
            runCatching {
                val spot = database.exploreSpotQueries.selectById(id.toLong()).executeAsOne()
                spot
            }
        }

    suspend fun getVisitedSpots(userId: String): Result<List<VisitedSpot>, Throwable> =
        withContext(Dispatchers.IO) {
            runCatching {
                val visits =
                    database.exploreVisitQueries.selectAllByUserId(userId).executeAsList()
                visits.map { visit ->
                    val spot = database.exploreSpotQueries.selectById(visit.spot_id).executeAsOne()
                    VisitedSpot(
                        id = visit.id.toInt(),
                        title = spot.title,
                        description = spot.description,
                        mainImage = spot.image_url,
                        visitTimestamp = visit.visit_time,
                        accessibility = spot.accessibility.toInt()
                    )
                }
            }
        }

    suspend fun getSubmittedSpots() : Result<List<SubmittedSpot>, Throwable> = withContext(Dispatchers.IO) {
        runCatching {
            val response = client.get(API_BASE_URL + ENDPOINT_SUBMITTED_SPOTS)
                .body<SubmittedSpotsResponse>()
            response.spots
        }
    }

    fun getExploreSpotsFlow(): Flow<List<SpotWithVisits>> {

        val spotsFlow = database.exploreSpotQueries
            .selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

        val visitsFlow = database.exploreVisitQueries
            .selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

        return combine(spotsFlow, visitsFlow) { spots, visits ->
            spots.map { spot ->
                SpotWithVisits(
                    spot = spot,
                    visits = visits.filter { it.spot_id == spot.id }
                )
            }
        }
    }


    suspend fun updateExploreSpots() = runCatching {
        val response = client.get(API_BASE_URL + ENDPOINT_SPOTS)
            .body<SpotsFeedResponse>()

        database.transaction {

            database.exploreSpotQueries.deleteAll()
            database.exploreVisitQueries.deleteAll()

            response.spotsWithVisitsResponse.forEach { spotWithVisits ->
                val spot = spotWithVisits.spot

                database.exploreSpotQueries.insertOrReplaceSpot(
                    spot.id.toLong(),
                    spot.title,
                    spot.description,
                    spot.latitude,
                    spot.longitude,
                    spot.creatorId,
                    spot.pictureUrl,
                    spot.createTime,
                    spot.category,
                    spot.difficulty.toLong(),
                    spot.isArea.toLong()
                )

                // Replace visits related to the spot
                spotWithVisits.visits.forEach { visit ->
                    database.exploreVisitQueries.insertOrReplaceVisit(
                        visit.id.toLong(),
                        visit.spotId.toLong(),
                        visit.userId,
                        visit.visitTime,
                        visit.imageUrl
                    )
                }
            }
        }
    }

    suspend fun submitSpot(
        title: String,
        description: String,
        location: Location,
        difficulty: Int,
        image: ByteArray,
        isArea : Boolean
    ): Result<Unit, Throwable> = runCatching {
        val mimeType = MimeTypeMapper.detectImageFormat(image).getOrThrow()
        val multipartData = MultiPartFormDataContent(
            formData {
                append(
                    MULTIPART_DATA_KEY, Json.encodeToString(
                        SubmitSpotRequest.serializer(),
                        SubmitSpotRequest(
                            title,
                            description,
                            location.latitude,
                            location.longitude,
                            difficulty,
                            isArea
                        )
                    )
                )
                append(MULTIPART_IMAGE_KEY, image, Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=$UPLOAD_IMAGE_FILE_NAME")
                    append(HttpHeaders.ContentType, mimeType)
                })
            }
        )

        client.post(API_BASE_URL + ENDPOINT_SUBMIT_SPOT) {
            contentType(ContentType.MultiPart.FormData)
            setBody(multipartData)
        }
    }

    suspend fun reportSpot(spotId: Int, reason: String) : Result<Unit, Throwable> = runCatching {
        client.post(API_BASE_URL + ENDPOINT_REPORT_SPOT) {
            setBody(ReportSpotRequest(spotId, reason))
        }
    }
}