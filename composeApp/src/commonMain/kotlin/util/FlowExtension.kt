package util

import domain.util.DomainError
import domain.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

fun <T> Flow<T>.collectInScope(scope: CoroutineScope, action: suspend (T) -> Unit): Job {
    return scope.launch { collect { action(it) } }
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

fun <T> Flow<T>.collectInScopeAtLimitedRate(
    scope: CoroutineScope,
    minIntervalBetweenCalls: Long,
    action: suspend () -> Unit
) {
    var lastExecutionTime = 0L
    var nextExecutionScheduled = false

    val runAction = suspend {
        lastExecutionTime = Clock.System.now().toEpochMilliseconds()
        nextExecutionScheduled = false
        action()
    }

    collectInScope(scope) {
        val timeSinceLastExecution = Clock.System.now().toEpochMilliseconds() - lastExecutionTime
        if (timeSinceLastExecution > minIntervalBetweenCalls) {
            scope.launch { runAction() }
            return@collectInScope
        }
        if (!nextExecutionScheduled) {
            nextExecutionScheduled = true
            scope.launch {
                delay(minIntervalBetweenCalls - timeSinceLastExecution)
                runAction()
            }
        }
    }
}