import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val format = "%-10s%-20s%-10s"
    println(String.format(format, "Code", "Temperature", "Delay"))
    val time = measureTimeMillis {
        listOf("LAX", "SFO", "PDX", "SEA")
            .map { anAirportCode ->
                async(Dispatchers.IO) {
                    Airport.getAirportData(anAirportCode)
                }
            }
            .mapNotNull { it.await() }
            .forEach {
                println(String.format(format, it.code, it.weather.temperature[0], it.delay))
            }
    }
    println("Time taken $time ms")
}
