package example.coroutines

import example.printDone
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

suspend fun main() {
    try {
        coroutineScope {
            val data = async(Dispatchers.IO + SupervisorJob()) { // <- extension on current scope
                fetchData().apply {
                    println("done")
                }
            }

            withContext(Dispatchers.Default) {
                willBeThrow()
                val result = data.await()
                display(result)
            }
        }
    } catch (ignored: Exception) {
    }
    delay(500)
    printDone()

    try {
        supervisorScope {
            launch { throw RuntimeException() }
            launch {
                delay(100)
                println("execute")
            }
        }
    } catch (ignored: Exception) {
    }
    delay(500)
    printDone()
}

private suspend fun willBeThrow() {
    delay(100)
    throw RuntimeException("error while some work")
}

private suspend fun fetchData(): String {
    try {
        delay(200)
    } catch (e: CancellationException) {
        println("data fetching cancelled")
        throw e
    }
    return "data"
}

private fun display(data: String) {
    println("example.coroutines.display $data")
}
