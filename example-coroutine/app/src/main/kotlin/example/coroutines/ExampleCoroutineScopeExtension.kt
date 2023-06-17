package example.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun main() {
    // 이 범위의 자식 코루틴이 실패하면 이 범위가 실패하고 나머지 모든 자식이 취소됩니다
    coroutineScope {
        val data = async(Dispatchers.IO) { // <- extension on current scope
            fetchData() // will be canceled
        }

        withContext(Dispatchers.Default) {
            willBeThrow()
            val result = data.await()
            display(result)
        }
    }
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
    println("display $data")
}
