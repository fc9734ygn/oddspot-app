package domain.use_case.spot.model

sealed class ReportReason {
    data object Unsafe : ReportReason()
    data object SpotChangedOrGone : ReportReason()
    data object Other : ReportReason()
}