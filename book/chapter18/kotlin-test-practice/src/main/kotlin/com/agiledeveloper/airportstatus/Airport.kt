package com.agiledeveloper.airportstatus

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.net.URL

data class Airport(
    @Json(name = "IATA") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Delay") val delay: Boolean) {

    companion object {
        fun sort(airports: List<Airport>): List<Airport> {
            return airports.sortedBy { it.name }
        }

        fun fetchData(code: String): String {
            return URL("https://soa.smext.faa.gov/asws/api/airport/status/$code").readText()
        }

        fun getAirportData(code: String) = try {
            Klaxon().parse<Airport>(fetchData(code))
        } catch (e: Exception) {
            Airport(code, "Invalid Airport", false)
        }
    }
}
