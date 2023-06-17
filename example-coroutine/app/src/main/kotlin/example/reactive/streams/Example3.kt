package example.reactive.streams

import reactor.core.publisher.Flux
import reactor.util.retry.Retry
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

fun main() {
    val errorCount = AtomicInteger()
    Flux.generate<String> {
        if ((0..5).random() == 3) {
            it.error(RuntimeException("error"))
        } else {
            it.next("normal")
        }
    }
        .doOnError { e: Throwable ->
            errorCount.incrementAndGet()
            println(e.toString() + " at " + LocalTime.now())
        }
        .retryWhen(
            Retry.backoff(3, Duration.ofMillis(100)).jitter(0.0)
                .doAfterRetry { retrySignal ->
                    println("retried at ${LocalTime.now()}, attempt ${retrySignal.totalRetries()}, totalRetriesInARow ${retrySignal.totalRetriesInARow()}, totalRetries ${retrySignal.totalRetries()}")
                }
                .onRetryExhaustedThrow { spec, retrySignal ->
                    println("retry context ${spec.retryContext}")
                    retrySignal.failure()
                }
        )
        .subscribe()

    Thread.sleep(2000)
    exitProcess(0)
}

