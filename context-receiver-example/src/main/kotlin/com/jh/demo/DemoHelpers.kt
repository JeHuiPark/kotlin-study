package com.jh.demo

class Logger(val name: String) {
    fun log(s: String) = println("$name: $s")
}

class NotificationSender {
    fun send(s: String) = println("NOTIFY: $s")
}

interface LoggerContext {
    val logger: Logger
}

interface NotificationContext {
    val notificationSender: NotificationSender
}
