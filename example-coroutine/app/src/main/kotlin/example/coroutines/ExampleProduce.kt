package example.coroutines

import example.printDone
import example.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.joinAll

suspend fun main() {
    coroutineScope {
        val receiveChannel = produce(
            capacity = Channel.Factory.CONFLATED
        ) {
            (0 until 5).forEach {
                println("before send value $it")
                send(it)
                println("sent value $it")
            }
        }
        delay(100)

//        before send value 0
//        sent value 0
//        before send value 1
//        sent value 1
//        before send value 2
//        sent value 2
//        before send value 3
//        sent value 3
//        before send value 4
//        sent value 4
        receiveChannel.receive() shouldBe 4
        printDone()
    }

    val channel = createChannelUsingProduce(channelNameSuffix = "1")
    for (i in channel) {
        println(i)
    }
    printDone()

    with(createChannelUsingProduce(channelNameSuffix = "2")) {
        // fan-out model
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val flow = receiveAsFlow()
            .take(2)

        val job1 = flow
            .onEach { println("$it at job-1") }
            .launchIn(coroutineScope)
        println("createdJob1")
        val job2 = flow
            .onEach { println("$it at job-2") }
            .launchIn(coroutineScope)
        println("createdJob2")
        listOf(job1, job2).joinAll()
        printDone()
    }

    with(createChannelUsingProduce(channelNameSuffix = "3")) {
        val flow = consumeAsFlow()
            .take(1)
            .onEach { println("$it at consumeAsFlow-test-collector") }
        flow.collect()
        try {
            flow.collect()
        } catch (expected: IllegalStateException) {
            println("ReceiveChannel.consumeAsFlow can be collected just once")
        }
        printDone()
    }
}

private suspend fun createChannelUsingProduce(channelNameSuffix: String): ReceiveChannel<Int> {
    // no buffered
    val channel = CoroutineScope(Dispatchers.Default).produce {
        for (i in 0 until 5) {
            send(i)
        }
    }
    println("created channel-$channelNameSuffix")
    return channel
}
