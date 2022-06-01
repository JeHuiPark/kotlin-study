import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * async
 *
 * async()는 launch()와 동일한 파라미터를 받는다.
 * async()는 Deferred<T>를 리턴하며, 이 객체를 이용하여 코루틴 상태를 기반으로 흐름 제어를 할 수 있다 (java의 Future<T>와 유사)
 */
fun main() {
    runBlocking {
        // 코루틴 1
        println("코루틴1")

        val count = async {
            // 코루틴 2
            println("코루틴2")
            Runtime.getRuntime().availableProcessors()
        }

        launch {
            // 코루틴 3
            println("코루틴3")
        }

        println("Number of cores is ${count.await()}") // await 함수 실행으로 실행 제어권이 다음 코루틴2에게 넘어감
        println("코루틴1 done")
    }
}
