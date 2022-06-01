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
        launch { task1() }
        launch { task2() }
        println("called task1 and task2 from ${Thread.currentThread()}")

        // 코루틴은 non-blocking 디자인을 가능하게 한다 (코드를 동시에 실행할 수 있게 함)
        // 코루틴을 이용하여 콜백이나 프로미스같은 것들 없이도 하나의 스레드에서 동시에 실행되는 코드를 작성할 수 있다
    }
    println("done")
}
