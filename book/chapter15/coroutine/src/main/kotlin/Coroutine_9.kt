@file:Suppress("ClassName")

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine_9 {

    /**
     * 스레드간 컨텍스트 전달이 자연스럽게 진행됨을 확인할 수 있는 함수
     */
    suspend fun compute(n: Long): Long {
        val factor = 2
        println("$n received, Thread: ${Thread.currentThread()}")
        delay(n * 1000)
        val result = n * factor
        println("$n received, returning $result, Thread: ${Thread.currentThread()}")
        return result
    }
    /**
     * suspend 어노테이션이 붙은 함수를 Java 코드로 변환하면 Continuation 인자가 추가된 것을 확인할 수 있음
     * 추가로 리턴타입도 Object 를 리턴하는 것으로 변경됨
     *
     * ===================================================================================
     * public final Object compute(long n, @NotNull Continuation var3)
     * ===================================================================================
     *
     * 결과적으로 개발자는 코틀린이 지원하는 기능들을 활용하여 Continuation 을 사용하는 것에 집중하게 되는 것
     */
}

fun main() = runBlocking<Unit> {
    val compute = Coroutine_9()
    launch(Dispatchers.Default) {
        compute.compute(2)
    }
    launch(Dispatchers.Default) {
        compute.compute(1)
    }
}
