import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

private suspend fun task1() {
    println("start - task1 in ${Thread.currentThread()}")
    yield() // 실행 흐름을 양보 (만약 양보해야할 대상이 없으면 계속 실행)
    println("end - task1 in ${Thread.currentThread()}")
}

private suspend fun task2() {
    println("start - task2 in ${Thread.currentThread()}")
    yield()
    println("end - task2 in ${Thread.currentThread()}")
}


fun main() {
    runBlocking {
        launch { task1() }
        launch { task2() }
        println("called task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}
