package domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val error: DomainError = DomainError.Generic) : Resource<T>()
    class Loading<out T> : Resource<T>()
}

fun <T> Flow<Resource<T>>.collectResource(
    scope: CoroutineScope,
    onError: (DomainError) -> Unit = {},
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
) {
    onEach { resource ->
        when (resource) {
            is Resource.Success -> onSuccess(resource.data)
            is Resource.Error -> onError(resource.error)
            is Resource.Loading -> onLoading()
        }
    }.launchIn(scope)
}