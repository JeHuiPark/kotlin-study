package com.jh.demo

context(Logger, NotificationSender)
private fun store(s: String) {
    log("Stored $s on disk (via $name)")
    send("Successful storage event.")
}

fun main() {
    val logger = Logger("Main")
    val notificationSender = NotificationSender()

    // logger.store("Not allowed")

    with(logger) {
//            store("Not allowed")
        with(notificationSender) {
            store("An image")
            store("A text file")
            store("A cheese burger")
        }
    }
}