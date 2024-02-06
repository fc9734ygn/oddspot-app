package domain.use_case.spot.model

sealed class SubmissionStatus {
    object Submitted : SubmissionStatus()
    object Accepted : SubmissionStatus()
    class Rejected(val reason : RejectionReason?) : SubmissionStatus()
}

enum class RejectionReason {
    INAPPROPRIATE,
    DUPLICATE,
    SPAM,
    LOW_QUALITY,
    LACK_OF_INFO,
    BAD_LOCATION,
    BAD_IMAGE,
    BAD_TITLE,
    BAD_DESCRIPTION,
    NOT_A_SPOT,
    DANGEROUS,
    TRESPASSING,
    OTHER,
}