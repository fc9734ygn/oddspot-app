package data.repository

import API_BASE_URL
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrThrow
import com.github.michaelbull.result.runCatching
import data.ENDPOINT_SUBMIT_SPOT
import data.MULTIPART_DATA_KEY
import data.MULTIPART_IMAGE_KEY
import data.MimeTypeMapper
import data.UPLOAD_IMAGE_FILE_NAME
import data.request.SubmitSpotRequest
import domain.use_case.spot.model.Spot
import domain.use_case.spot.model.SubmittedSpot
import domain.use_case.spot.model.VisitedSpot
import getSpotsTestData
import getSubmittedSpotsTestData
import getVisitedSpotsTestData
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton
import ui.util.Location

@Suppress("MagicNumber")
@Singleton
class SpotRepository(
    private val client: HttpClient,
) {
    suspend fun getVisitedSpots(): List<VisitedSpot>? {
        // temporary mock data
        return getVisitedSpotsTestData().asReversed().take(9)
    }

    suspend fun getSubmittedSpots(): List<SubmittedSpot>? {
        // temporary mock data
        return getSubmittedSpotsTestData().asReversed().take(7)
    }

    suspend fun getUnknownSpots(currentUserCoordinates: Pair<Double, Double>): List<Spot> {
        return getSpotsTestData(currentUserCoordinates).take(5)
    }

    suspend fun submitSpot(
        title: String,
        description: String,
        location: Location,
        difficulty: Int,
        image: ByteArray
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
                            difficulty
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
}