package com.example.testExtension

import com.example.testExtension.TestLifeCyclePractice.Fixture.printTestScope
import io.kotest.core.annotation.AutoScan
import io.kotest.core.listeners.AfterContainerListener
import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.AfterInvocationListener
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeContainerListener
import io.kotest.core.listeners.BeforeEachListener
import io.kotest.core.listeners.BeforeInvocationListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.listeners.FinalizeSpecListener
import io.kotest.core.listeners.PrepareSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestScope
import kotlin.reflect.KClass

/**
 * Print Result
 *
 * ```
 * beforeProject
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
 * afterProject
 * ```
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

    @AutoScan
    object ExtensionPractice :
        BeforeContainerListener,
        AfterContainerListener,
        BeforeEachListener,
        AfterEachListener,
        BeforeTestListener,
        AfterTestListener,
        BeforeInvocationListener,
        AfterInvocationListener,
        BeforeSpecListener,
        AfterSpecListener,
        PrepareSpecListener,
        FinalizeSpecListener,
        BeforeProjectListener,
        AfterProjectListener {

        override suspend fun beforeSpec(spec: Spec) {
            println("${spec.javaClass.simpleName}: beforeSpec")
        }

        override suspend fun afterSpec(spec: Spec) {
            println("${spec.javaClass.simpleName}: afterSpec")
        }

        override suspend fun beforeTest(testCase: TestCase) {
            println("${testCase.name.testName}: beforeTest")
        }

        override suspend fun afterTest(testCase: TestCase, result: TestResult) {
            println("${testCase.name.testName}: afterTest")
        }

        override suspend fun beforeContainer(testCase: TestCase) {
            println("${testCase.name.testName}: beforeContainer")
        }

        override suspend fun afterContainer(testCase: TestCase, result: TestResult) {
            println("${testCase.name.testName}: afterContainer")
        }

        override suspend fun beforeEach(testCase: TestCase) {
            println("${testCase.name.testName}: beforeEach")
        }

        override suspend fun afterEach(testCase: TestCase, result: TestResult) {
            println("${testCase.name.testName}: afterEach")
        }

        override suspend fun beforeInvocation(testCase: TestCase, iteration: Int) {
            println("${testCase.name.testName}: beforeInvocation, iteration count: $iteration")
        }

        override suspend fun afterInvocation(testCase: TestCase, iteration: Int) {
            println("${testCase.name.testName}: afterInvocation, iteration count: $iteration")
        }

        override suspend fun beforeAny(testCase: TestCase) {
            println("${testCase.name.testName}: beforeAny")
        }

        override suspend fun afterAny(testCase: TestCase, result: TestResult) {
            println("${testCase.name.testName}: afterAny")
        }

        override suspend fun finalizeSpec(kclass: KClass<out Spec>, results: Map<TestCase, TestResult>) {
            println("${kclass.simpleName}: finalizeSpec")
        }

        override suspend fun prepareSpec(kclass: KClass<out Spec>) {
            println("${kclass.simpleName}: prepareSpec")
        }

        override suspend fun beforeProject() {
            println("beforeProject")
        }

        override suspend fun afterProject() {
            println("afterProject")
        }
    }
}
