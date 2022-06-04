import kotlinx.coroutines.*

suspend fun doWork(id: Int, sleep: Long) = coroutineScope {
    try {
        println("$id: entered $sleep")
        delay(sleep)
        println("$id: finished nap $sleep")

        withContext(NonCancellable) {
            println("$id: do not disturb, please")
            delay(5000)
            println("$id: Ok, you can talke to me now, parent isActive: $${this@coroutineScope.isActive}")
        }
        println("$id: outside the restricted context")
        println("$id: isActive: $isActive")
    } catch (e: CancellationException) {
        println("$id: doWork($id, $sleep) was cancelled")
    }
}

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        launch { doWork(1, 3000) }
        launch { doWork(2, 1000) }
    }
    Thread.sleep(2000)
    job.cancel()
    println("cancelling")
    job.join()
    println("done")
}