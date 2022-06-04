import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.net.URL

class Weather(@Json(name = "Temp") val temperature: Array<String>)
class Airport(
    @Json(name = "IATA") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Delay") val delay: Boolean,
    @Json(name = "Weather") val weather: Weather,
) {
    companion object {
        fun getAirportData(code: String): Airport? =
            Klaxon().parse<Airport>(URL("https://soa.smext.faa.gov/asws/api/airport/status/$code").readText())
    }
}
