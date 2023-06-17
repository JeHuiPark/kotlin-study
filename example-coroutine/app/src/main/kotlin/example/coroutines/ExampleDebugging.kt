package example.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

suspend fun main() {
    System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
    CoroutineScope(CoroutineName("example")).launch {
        println(currentCoroutineContext())
        // [CoroutineName(example), CoroutineId(1), "example#1":StandaloneCoroutine{Active}@c1eeaa5, Dispatchers.Default]
        println(Thread.currentThread().name)
        // DefaultDispatcher-worker-1 @example#1
    }.join()
}
