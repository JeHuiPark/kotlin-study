package com.example.testFactory

import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.freeSpec
import io.kotest.matchers.shouldBe

object SwitchTestFactory {

    fun testSuit(switchInstanceProvider: () -> Switch): TestFactory = freeSpec {
        "initial state" {
            val switch = switchInstanceProvider.invoke()
            switch.isOn shouldBe false
        }

        "isOn" {
            val switch = switchInstanceProvider.invoke()

            switch.turnOn()
            switch.isOn shouldBe true

            switch.turnOff()
            switch.isOn shouldBe false

            switch.turnOn()
            switch.isOn shouldBe true
        }
    }
}
