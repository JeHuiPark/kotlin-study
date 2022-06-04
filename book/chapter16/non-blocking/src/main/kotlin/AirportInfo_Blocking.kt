import kotlin.system.measureTimeMillis

fun main() {
    val format = "%-10s%-20s%-10s"
    println(String.format(format, "Code", "Temperature", "Delay"))
    val time = measureTimeMillis {
        listOf("LAX", "SFO", "PDX", "SEA")
            .mapNotNull(Airport::getAirportData)
            .forEach {
                println(String.format(format, it.code, it.weather.temperature[0], it.delay))
            }
    }
    println("Time taken $time ms")
}
