package domain.use_case.spot.model

data class SubmittedSpot(
    val id : String, // id is the same as the object of Spot data class
    val title: String,
    val description: String,

    val imageUrl: String,

    val submittedOn: Long, // unix timestamp in milliseconds
    val status: SubmissionStatus = SubmissionStatus.Submitted
)
