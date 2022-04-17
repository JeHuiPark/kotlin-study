fun greet(name: String, msg: String = "$name, Current Time is ${java.time.LocalDateTime.now()}") = msg

greet("Jehui")
