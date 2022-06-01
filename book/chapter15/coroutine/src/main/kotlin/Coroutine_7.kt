import kotlinx.coroutines.*

private suspend fun task1() {
    println("start - task1 in ${Thread.currentThread()}")
    yield()
    println("end - task1 in ${Thread.currentThread()}")
}

private suspend fun task2() {
    println("start - task2 in ${Thread.currentThread()}")
    yield()
    println("end - task2 in ${Thread.currentThread()}")
}

// 코루틴 네이밍
fun main() {
    System.setProperty("kotlinx.coroutines.debug", "")
    runBlocking(CoroutineName("top")) {
        println("starting in Thread ${Thread.currentThread()}")
        withContext(Dispatchers.Default) { task1() }
        launch(Dispatchers.Default + CoroutineName("task runner")) { task2() }
        println("ending in Thread ${Thread.currentThread()}")
    }
}
