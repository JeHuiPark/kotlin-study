package com.jh.demo

context (LoggerContext)
private fun logStorageEvent(s: String) {
    logger.log("Stored $s on disk (via ${logger.name})")
}

context(LoggerContext, NotificationContext)
class Repository {
    fun store(s: String) {
        logStorageEvent(s)
        notificationSender.send("Successful storage event.")
    }
}

fun main() {
    val logger = Logger("Main")
    val notificationSender = NotificationSender()

    val loggerContext = object : LoggerContext {
        override val logger = logger
    }
    val notificationContext = object : NotificationContext {
        override val notificationSender = notificationSender
    }

    val repository = with(loggerContext) {
        with(notificationContext) {
            Repository()
        }
    }
    repository.store("An image")
}