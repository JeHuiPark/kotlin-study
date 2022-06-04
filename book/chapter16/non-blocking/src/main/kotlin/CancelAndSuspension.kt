import kotlinx.coroutines.*
import java.net.URL

suspend fun compute(checkActive: Boolean) = coroutineScope {
    var count = 0
    val max = Int.MAX_VALUE
    while (if (checkActive) { isActive && count < max } else count < max) {
        count++
    }
    if (count == max) {
        println("compute, checkActive $checkActive ignored cancellation")
    } else {
        println("compute, checkActive $checkActive bailed out early")
    }
}

fun getResponse() = URL("https://httpstat.us/200?sleep=2000").readText()
suspend fun fetchResponse(callAsync: Boolean) = coroutineScope {
    try {
        val response = if (callAsync) {
            async { getResponse() }.await()
        } else {
            getResponse()
        }
        println(response)
    } catch (e: CancellationException) {
        println("fetchResponse called with callAsync $callAsync: ${e.message}")
    }
}

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        launch { compute(checkActive = false) }
        launch { compute(checkActive = true) }
        launch { fetchResponse(callAsync = false) }
        launch { fetchResponse(callAsync = true) }
    }
    println("Let them run...")
    Thread.sleep(1000)
    println("OK, That's enough, cancel")
    job.cancelAndJoin()
}
