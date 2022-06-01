import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun task1() {
    println("start - task1 in ${Thread.currentThread()}")
    Thread.sleep(100)
    println("end - task1 in ${Thread.currentThread()}")
}

private fun task2() {
    println("start - task2 in ${Thread.currentThread()}")
    println("end - task2 in ${Thread.currentThread()}")
}


fun main() {
    runBlocking {
        launch(Dispatchers.Default) { task1() } // DefaultDispatcher 의 thread pool 에서 실행
        launch { task2() }
        println("called task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}
