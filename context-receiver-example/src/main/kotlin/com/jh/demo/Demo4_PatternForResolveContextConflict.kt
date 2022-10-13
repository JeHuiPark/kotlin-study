package com.jh.demo

context(LoggerContext, NotificationContext)
private fun store(s: String) {
    logStorageEvent(s)
    notificationSender.send("Successful storage event.")
}

context (LoggerContext)
private fun logStorageEvent(s: String) {
    logger.log("Stored $s on disk (via ${logger.name})")
}

class Demo4 {

    fun run() {
        val logger = Logger("Main")
        val notificationSender = NotificationSender()

        val loggerContext = object : LoggerContext {
            override val logger = logger
        }
        val notificationContext = object : NotificationContext {
            override val notificationSender = notificationSender
        }

        with(loggerContext) {
            with(notificationContext) {
                store("An image")
                store("A text file")
                store("A cheese burger")
            }
        }
    }
}

fun main() {
    val demo = Demo4()
    demo.run()
}