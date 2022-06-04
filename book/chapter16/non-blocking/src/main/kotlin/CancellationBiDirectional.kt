import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.net.URL

suspend fun fetchResponse(code: Int, delay: Int) = coroutineScope {
    try {
        val response = async {
            URL("https://httpstat.us/$code?sleep=$delay").readText()
        }.await()
        println(response)
    } catch (e: CancellationException) {
        println("${e.message} for fetchResponse $code")
    }
}

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, e ->
        println("Exception handled: ${e.message}")
    }
    launch(IO + SupervisorJob() + handler) {
        launch { fetchResponse(200, 5000) } // 2. 부모 코루틴 취소로 중단점에서 CancellationException 발생
        launch { fetchResponse(202, 1000) }
        launch { fetchResponse(404, 2000) } // 1. err -> 부모 코루틴 취소
    }.join()

    println("=".repeat(50))

    val job = launch(IO + handler) {
        supervisorScope {
            // supervisorScope 이 적용되어 형제 코루틴이 처리하지 못한 예외에 영향을 받지 않는다
            launch { fetchResponse(200, 5000) }
            launch { fetchResponse(202, 1000) }
            launch { fetchResponse(404, 2000) }
        }
    }
    Thread.sleep(4000)
    println("200 should still be running at this time")
    println("let the parent cancel now")
    job.cancel()
    job.join()
}