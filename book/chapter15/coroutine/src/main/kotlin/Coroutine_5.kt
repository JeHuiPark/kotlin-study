import kotlinx.coroutines.*
import java.util.concurrent.Executors

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


fun main() {
    Executors.newFixedThreadPool(2).asCoroutineDispatcher().use {
        println("start")
        runBlocking {
            launch(context = it, start = CoroutineStart.UNDISPATCHED) { task1() }
            // 이 코루틴은 호출자의 context 에서 실행 되다가 중단점을 만나게 되면 launch 의 인자로 넘긴 context 로 스위칭 되어 실행된다

            launch { task2() }
            println("called task1 and task2 from ${Thread.currentThread()}")
        }
        println("end")
    }
}
