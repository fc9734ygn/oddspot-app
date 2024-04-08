package domain.use_case.spot.model

sealed class SubmissionStatus {
    data object Submitted : SubmissionStatus()
    data object Verified : SubmissionStatus()
    data object Rejected : SubmissionStatus()
}

fun String.toSubmissionStatus(): SubmissionStatus {
    return when (this) {
        "submitted" -> SubmissionStatus.Submitted
        "verified" -> SubmissionStatus.Verified
        "rejected" -> SubmissionStatus.Rejected
        else -> SubmissionStatus.Submitted
    }
}