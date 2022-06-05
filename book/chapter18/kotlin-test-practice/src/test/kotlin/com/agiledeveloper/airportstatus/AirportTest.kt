package com.agiledeveloper.airportstatus

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

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
}