package ui.base

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.util.DomainError
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import util.collectResource

open class BaseScreenModel<T> constructor(initialState: T) : StateScreenModel<T>(initialState) {

    fun <T> Flow<T>.toStateFlow(default: T) =
        stateIn(screenModelScope, SharingStarted.WhileSubscribed(FLOW_SUBSCRIPTION_TIMEOUT), default)

    fun <T> Flow<Resource<T>>.collectResource(
        onError: (DomainError) -> Unit = {},
        onLoading: () -> Unit = {},
        onSuccess: (T) -> Unit = {},
    ) = collectResource(screenModelScope, onError, onLoading, onSuccess)

    companion object {
        private const val FLOW_SUBSCRIPTION_TIMEOUT = 5000L
    }
}