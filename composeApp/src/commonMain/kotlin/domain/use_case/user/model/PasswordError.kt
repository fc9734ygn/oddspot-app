package domain.use_case.user.model

enum class PasswordError {
    TOO_SHORT,
    TOO_LONG,
    COMMON,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_NUMBER,
    PASSWORDS_NOT_MATCHING
}