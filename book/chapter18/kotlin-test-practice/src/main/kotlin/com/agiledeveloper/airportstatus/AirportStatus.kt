package com.agiledeveloper.airportstatus

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

suspend fun getAirportStatus(airportCodes: List<String>): List<Airport> =
    withContext(IO) {
        Airport.sort(
            airportCodes
                .map { anAirportCode -> async { Airport.getAirportData(anAirportCode) } }
                .mapNotNull { it.await() }
        )
    }