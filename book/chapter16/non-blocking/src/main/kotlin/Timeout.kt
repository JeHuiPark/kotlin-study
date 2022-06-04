import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, e ->
        println("Exception handled: ${e.message}")
    }
    launch(IO + handler) {
        withTimeout(3000) {
            launch { fetchResponse(200, 5000) }
            launch { fetchResponse(201, 1000) }
            launch { fetchResponse(202, 2000) }
        }
    }.join()
}
