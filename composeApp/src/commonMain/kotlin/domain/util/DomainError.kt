package domain.util

sealed class DomainError(
    val throwable: Throwable? = null,
    val code: Int? = null,
) {
    data object Generic : DomainError()
    class Database(throwable: Throwable) : DomainError(throwable = throwable)
    class Network(
        throwable: Throwable? = null,
        code: Int? = null,
    ) : DomainError(throwable = throwable, code = code)
}