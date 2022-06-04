import kotlinx.coroutines.*

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { context, ex ->
        println("Caught: ${context[CoroutineName]} ${ex.message?.substring(0..28)}")
    }
    val jobs = listOf("LAX", "SF-", "PD-", "SEA") // 예외 케이스를 발생시키기 위해 이상한 값을 2개 추가
        .map { anAirportCode ->
            launch(Dispatchers.IO + CoroutineName(anAirportCode) + handler + SupervisorJob()) {
                val airport = Airport.getAirportData(anAirportCode)
                println("${airport?.code} delay: ${airport?.delay}")
            }
        }
    jobs.forEach { it.join() } // 역시 호출자에게 예외가 전파되지 않지만 launch 에 등록한 exceptionHandler 로 예외 컨텍스트가 전달된다
    jobs.forEach { println("Canelled: ${it.isCancelled}") }
}