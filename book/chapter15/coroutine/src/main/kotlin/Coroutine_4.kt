import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

private fun task1() {
    println("start - task1 in ${Thread.currentThread()}")
    println("end - task1 in ${Thread.currentThread()}")
}

private fun task2() {
    println("start - task2 in ${Thread.currentThread()}")
    println("end - task2 in ${Thread.currentThread()}")
}


fun main() {
    println("start in ${Thread.currentThread()}")
    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use {
        println("start in ${Thread.currentThread()}")
        runBlocking {
            launch(it) { task1() }
            launch { task2() }
            println("called task1 and task2 from ${Thread.currentThread()}")
        }
        println("end in ${Thread.currentThread()}")
    }
    println("end in ${Thread.currentThread()}")
}
