package domain.use_case.spot.model

sealed class Accessibility {
    data object Easy : Accessibility()
    data object Average : Accessibility()
    data object Hard : Accessibility()
}

fun Int.toAccessibility(): Accessibility {
    return when (this) {
        0 -> Accessibility.Easy
        1 -> Accessibility.Average
        2 -> Accessibility.Hard
        else -> throw IllegalArgumentException("Invalid accessibility value")
    }
}