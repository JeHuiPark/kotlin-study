package com.example

import com.example.TestLifeCyclePractice.Fixture.printTestScope
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestScope

/**
 * TestLifeCyclePractice: prepareSpec
 * TestLifeCyclePractice: beforeSpec
 * 1 level: beforeContainer
 * 1 level: beforeAny
 * 1 level: beforeTest
 * 1 level: beforeInvocation, iteration count: 0
 * 1 level block
 * 2 level: beforeEach
 * 2 level: beforeAny
 * 2 level: beforeTest
 * 2 level: beforeInvocation, iteration count: 0
 * 2 level block
 * 2 level: afterInvocation, iteration count: 0
 * 2 level: afterTest
 * 2 level: afterAny
 * 2 level: afterEach
 * 1 level: afterInvocation, iteration count: 0
 * 1 level: afterTest
 * 1 level: afterAny
 * 1 level: afterContainer
 * multiple invocation: beforeEach
 * multiple invocation: beforeAny
 * multiple invocation: beforeTest
 * multiple invocation: beforeInvocation, iteration count: 0
 * multiple invocation block
 * multiple invocation: afterInvocation, iteration count: 0
 * multiple invocation: beforeInvocation, iteration count: 1
 * multiple invocation block
 * multiple invocation: afterInvocation, iteration count: 1
 * multiple invocation: afterTest
 * multiple invocation: afterAny
 * multiple invocation: afterEach
 * TestLifeCyclePractice: afterSpec
 * TestLifeCyclePractice: finalizeSpec
 */
class TestLifeCyclePractice : FreeSpec({

    "1 level" - {
        printTestScope()
        "2 level" {
            printTestScope()
        }
    }

    "multiple invocation".config(invocations = 2) {
        printTestScope()
    }
}) {

    private object Fixture {

        fun TestScope.printTestScope() {
            println("${testCase.name.testName} block")
        }
    }
}
