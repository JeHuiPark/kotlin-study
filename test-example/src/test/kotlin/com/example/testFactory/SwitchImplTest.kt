package com.example.testFactory

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

/**
 * [SwitchImpl] test
 */
class SwitchImplTest : FreeSpec({
    include(SwitchTestFactory.testSuit { SwitchImpl() })

    "callCount" {
        val switch = SwitchImpl()

        // required pre-condition for test
        switch.controlCount shouldBe 0

        val expectedControlCount = (0..30).random()
        repeat(expectedControlCount) {
            if (Random.nextBoolean()) {
                switch.turnOff()
            } else {
                switch.turnOn()
            }
        }

        switch.controlCount shouldBe expectedControlCount
    }
})
