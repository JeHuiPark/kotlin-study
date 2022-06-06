package com.agiledeveloper.ui

import com.agiledeveloper.airportstatus.getAirportStatus
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    getAirportStatus(listOf("SFO", "IAD", "IAH", "ORD", "LAX"))
        .forEach(::println)
}