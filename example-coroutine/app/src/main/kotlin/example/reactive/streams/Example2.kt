package example.reactive.streams

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import kotlin.system.exitProcess

fun main() {
    var value = 0L
    Flux.generate<String> { sink ->
        val localValue = value++
        sink.next("<${Thread.currentThread().name} - generated value = $localValue>")
    }
        .publishOn(Schedulers.newSingle("publish-1"))
        .doOnNext { println("${Thread.currentThread().name} doOnNext-1 --------- $it") }
        .publishOn(Schedulers.newSingle("publish-2"))
        .doOnNext { println("${Thread.currentThread().name} doOnNext-2 --------- $it") }
        .take(10)
        .subscribeOn(Schedulers.newSingle("subscribe-1"))
        .subscribe { println("${Thread.currentThread().name} subscribe --------- $it") }

    println("=".repeat(200))

    Thread.sleep(500)
    exitProcess(0)
}
