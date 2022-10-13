package com.jh.demo

context(Logger)
private fun store(s: String) {
    log("Stored $s on disk")
}

fun main() {
    val logger = Logger("Main")

    // logger.store("Not allowed")

    with(logger) {
        store("An image")
        store("A text file")
        store("A cheese burger")
    }
}