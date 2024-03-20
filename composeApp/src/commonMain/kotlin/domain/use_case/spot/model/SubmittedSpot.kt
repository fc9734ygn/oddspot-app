package domain.use_case.spot.model

data class SubmittedSpot(
    val id : Int, // id is the same as the object of Spot data class
    val title: String,
    val description: String,

    val imageUrl: String,

    val submittedOn: Long, // unix timestamp in milliseconds
    val status: SubmissionStatus = SubmissionStatus.Submitted
)
