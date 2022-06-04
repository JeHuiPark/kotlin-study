import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val airportDatas = listOf("LAX", "SF-", "PD-", "SEA") // 예외 케이스를 발생시키기 위해 이상한 값을 2개 추가
        .map { anAirportCode ->
            // SupervisorJob 을 이용하여 자식 코루틴이 부모 코루틴을 취소하는 것을 방지할 수 있다
            // 오직 단방향(top - down)으로 전파
            // https://kotlinlang.org/docs/exception-handling.html#supervision
            async(Dispatchers.IO + SupervisorJob()) {
                Airport.getAirportData(anAirportCode)
            }
        }
    for (airportData in airportDatas) {
        try {
            val airport = airportData.await()
            println("${airport?.code} delay: ${airport?.delay}")
        } catch (e: Exception) {
            println("Error: ${e.message?.substring(0..28)}")
        }
    }
}
