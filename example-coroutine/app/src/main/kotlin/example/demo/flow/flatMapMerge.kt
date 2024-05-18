package example.demo.flow

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
suspend fun main() {
    flowOf("A", "B", "C")
        .flatMapMerge(2) { alphabet ->
            println("merge $alphabet with numeric flow")
            flowOf("1", "2", "3")
                .onEach { delay(100.milliseconds) }
                .onCompletion { println("merge completed for $alphabet") }
        }
        .collect()
}
