package com.example

class ExampleClass(
    val a: String,
    val b: ExInterface,
    val c: Collection<ExInterface>,
    private val d: String,
    e: String,
    val num: Int,
    val nullable: String?
) {

    private val e: String = e

    fun checkPrivatePropertyD(expected: String) {
        if (d != expected) {
            throw AssertionError("expected: $expected, but was $d")
        }
    }

    fun checkPrivatePropertyE(expected: String) {
        if (e != expected) {
            throw AssertionError("expected: $expected, but was $e")
        }
    }
}
