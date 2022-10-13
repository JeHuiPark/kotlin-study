package com.jh.demo

private fun Logger.store(s: String) {
    log("Stored $s on disk")
}

fun main() {
    val logger = Logger("Main")

    logger.store("First object")

    with(logger) {
        store("An image")
        store("A text file")
        store("A cheese burger")
    }
}