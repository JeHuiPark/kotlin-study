package com.agiledeveloper.airportstatus

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

@Suppress("MemberVisibilityCanBePrivate")
class AirportStatusTest : StringSpec() {

    val iad = Airport("IAD", "Dulles", false)
    val iah = Airport("IAH", "Houston", true)
    val inv = Airport("inv", "Invalid Airport", true)

    override fun beforeTest(testCase: TestCase) {
        mockkObject(Airport)
        every { Airport.getAirportData("IAD") } returns iad
        every { Airport.getAirportData("IAH") } returns iah
        every { Airport.getAirportData("inv") } returns inv
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        "getAirportStatus return status for airports in sorted order" {
            forall(
                row(listOf(), listOf()),
                row(listOf("IAD"), listOf(iad)),
                row(listOf("IAD", "IAH"), listOf(iad, iah)),
                row(listOf("IAH", "IAD"), listOf(iad, iah)),
                row(listOf("inv", "IAD", "IAH"), listOf(iad, iah, inv)),
            ) { input, result ->
                runBlocking { getAirportStatus(input) shouldBe result }
            }
        }
    }

    init {
        "getAirportStatus runs in the Dispatcher.IO context" {
            mockkStatic("kotlinx.coroutines.BuildersKt__Builders_commonKt")
            coEvery {
                withContext<List<Airport>>(context = IO, block = captureCoroutine())
            } answers {
                listOf(iah)
            }

            val result = getAirportStatus(listOf("IAH"))

            result shouldBe listOf(iah)
            coVerify { withContext<List<Airport>>(IO, block = any()) }
        }
    }

    init {
        "getAirportStatus class getAirportData asynchronously" {
            mockkStatic("kotlinx.coroutines.BuildersKt__Builders_commonKt")
            coEvery {
                any<CoroutineScope>().async<Airport>(context = any(), block = captureCoroutine())
            } answers {
                CompletableDeferred(iad)
            }

            val result = getAirportStatus(listOf("IAD"))

            result shouldBe listOf(iad)
            coVerify {
                any<CoroutineScope>().async<Airport>(context = any(), block = any())
            }
        }
    }
}