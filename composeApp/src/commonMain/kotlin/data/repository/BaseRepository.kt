package data.repository


import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getError
import org.koin.core.annotation.Singleton

@Singleton
open class BaseRepository {

    protected fun <T, E> logNetworkError(result: Result<T, E>) {
        result.getError()?.let {
            // TODO: Log error
        }
    }
}