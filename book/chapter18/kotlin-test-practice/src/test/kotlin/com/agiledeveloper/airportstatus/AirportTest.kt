package com.agiledeveloper.airportstatus

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify

/**
 * @see Airport
 */
@Suppress("MemberVisibilityCanBePrivate")
class AirportTest : StringSpec() {
    init {
        "canary test should pass" {
            true shouldBe true
        }
    }

    val iah = Airport("IAH", "Houston", true)
    val iad = Airport("IAD", "Dulles", false)
    val ord = Airport("ORD", "Chicago", true)

    init {
        "create Airport" {
            iah.code shouldBe "IAH"
            iad.name shouldBe "Dulles"
            ord.delay shouldBe true
        }
    }

    init {
        "sort empty list should return an empty list" {
            Airport.sort(listOf()) shouldBe listOf()
        }
    }

    init {
        "sort list with one Airport should return the given Airport" {
            Airport.sort(listOf(iad)) shouldBe listOf(iad)
        }
    }

    init {
        "sort pre-sorted list should return the given list" {
            Airport.sort(listOf(iad, iah)) shouldBe listOf(iad, iah)
        }
    }

    init {
        "sort airports should return airports in sorted order of name" {
            Airport.sort(listOf(iah, iad, ord)) shouldBe listOf(ord, iad, iah)
        }
    }

    init {
        "sort airports by name" {
            forall(
                row(listOf(), listOf()),
                row(listOf(iad), listOf(iad)),
                row(listOf(iad, iah), listOf(iad, iah)),
                row(listOf(iad, iah, ord), listOf(ord, iad, iah))) { input, result ->
                Airport.sort(input) shouldBe result
            }
        }
    }


    // mock library practice

    override fun beforeTest(testCase: TestCase) {
        mockkObject(Airport) // Airport 싱글톤의 목을 생성
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        "getAirportData invokes fetchData" {
            every { Airport.fetchData("IAD") } returns
                    """{"IATA":"IAD", "Name":"Dulles", "Delay":false}"""

            Airport.getAirportData("IAD")

            verify { Airport.fetchData("IAD") }
        }
    }

    init {
        "getAirportData extracts Airport from JSON returned by fetchData" {
            every { Airport.fetchData("IAD") } returns
                    """{"IATA":"IAD", "Name":"Dulles", "Delay":false}"""

            Airport.getAirportData("IAD") shouldBe iad

            verify { Airport.fetchData("IAD") }
        }
    }

    init {
        "getAirportData handles error fetching data" {
            every { Airport.fetchData("ERR") } returns "{}"

            Airport.getAirportData("ERR") shouldBe
                    Airport("ERR", "Invalid Airport", false)

            verify { Airport.fetchData("ERR") }
        }
    }
}