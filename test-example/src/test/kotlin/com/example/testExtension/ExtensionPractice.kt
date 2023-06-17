package com.example.testExtension

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
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlin.reflect.KClass

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
