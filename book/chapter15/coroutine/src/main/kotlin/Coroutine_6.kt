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

// 코루틴 컨텍스트 변경 예제
fun main() {
    System.setProperty("kotlinx.coroutines.debug", "")
    runBlocking {
        println("starting in Thread ${Thread.currentThread()}") // 1번 코루틴, 메인 스레드

        withContext(Dispatchers.Default) { task1() } // 1번 코루틴, 스레드풀 스레드
        // 코루틴 생성없이 컨텍스트만 스위칭하여 실행

        launch { println("launch in ${Thread.currentThread()}") } // 2번 코루틴
        launch { task2() } // 3번 코루틴, 메인 스레드
        println("ending in Thread ${Thread.currentThread()}") // 1번 코루틴, 메인 스레드
    }
}
