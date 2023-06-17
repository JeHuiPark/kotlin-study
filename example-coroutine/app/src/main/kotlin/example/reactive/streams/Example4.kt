package example.reactive.streams

import reactor.core.publisher.Flux
import kotlin.system.exitProcess

fun main() {
    // broadcasting
    val flux = Flux.range(0, 2)
        .publish()
        .autoConnect(2)

    flux.subscribe { println("${Thread.currentThread()} - $it") }
    flux.subscribe { println("${Thread.currentThread()} - $it") }

    Thread.sleep(100)
    exitProcess(0)
}
