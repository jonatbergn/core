import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import react.useEffectOnce
import react.useState

fun <T> StateFlow<T>.collectAsViewState(scope: CoroutineScope = GlobalScope): T {
    var state by useState(value)
    useEffectOnce {
        val job = onEach { state = it }.launchIn(scope)
        cleanup { job.cancel() }
    }
    return state
}
