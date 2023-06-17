package example.reactive.streams

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.util.retry.RetrySpec
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun main() {
    val exampleSubscriber = ExampleSubscriber<Int>()
    val scheduler = Schedulers.newSingle("example-worker-thread")
    ExamplePublisher.createFlux()
        .log()
        .retryWhen(RetrySpec.withThrowable { throwableFlux ->
            throwableFlux.delaySequence(1.seconds.toJavaDuration())
                .map { it is java.lang.RuntimeException }
        })
        .delaySubscription(1000.milliseconds.toJavaDuration())
        .subscribeOn(scheduler)
        .subscribe(exampleSubscriber)

    Thread.sleep(10000)
    exampleSubscriber.cancel()
    scheduler.dispose()
    println("done")
}

class ExampleSubscriber<T> : Subscriber<T> {

    private lateinit var subscription: Subscription
    private val requestSize = 10L
    private var counter = 0L
    override fun onSubscribe(s: Subscription) {
        subscription = s
        subscription.request(requestSize)
    }

    override fun onError(throwable: Throwable) {
    }

    override fun onComplete() {
    }

    override fun onNext(item: T) {
        if (++counter == requestSize) {
            counter = 0
            subscription.request(requestSize)
        }
    }

    fun cancel() {
        subscription.cancel()
    }
}

private object ExamplePublisher {

    private var sequence = 0

    fun createFlux(): Flux<Int> {
        return Flux.create { sink ->
            sink.onRequest {
                repeat(it.toInt()) {
                    if (sequence > 0 && sequence % 1000 == 0) {
                        sink.error(RuntimeException("example-exception"))
                    }
                    sink.next(sequence++)
                }
            }

            sink.onCancel {
                println("received cancel signal")
            }
        }
    }
}
