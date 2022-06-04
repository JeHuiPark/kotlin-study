import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    try {
        val jobs = listOf("LAX", "SF-", "PD-", "SEA") // 예외 케이스를 발생시키기 위해 이상한 값을 2개 추가
            .map { anAirportCode ->
                // SupervisorJob 을 이용하여 자식 코루틴이 부모 코루틴을 취소하는 것을 방지할 수 있다
                // 단방향(부모 -> 자식)으로 전파가능
                // https://kotlinlang.org/docs/exception-handling.html#supervision
                launch(Dispatchers.IO + SupervisorJob()) {
                    val airport = Airport.getAirportData(anAirportCode)
                    println("${airport?.code} delay: ${airport?.delay}")
                }
            }
        jobs.forEach { it.join() } // SupervisorJob 에서 실행되는 코루틴이므로 호출자에게 예외를 전파하지 않는다
        jobs.forEach { println("Canelled: ${it.isCancelled}") } // 정상호출
    } catch (e: Exception) {
        println("ERROR: ${e.message}") // 예외가 전파되지 않아서 catch 블록은 실행되지 않는다
    }
}