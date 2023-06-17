package example.coroutines

import example.printDone
import example.shouldBe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.ensurePresent
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun main() {
    repeat(500000) {
        val threadLocal = ThreadLocal<String>()
        threadLocal.set("value-1")
        val job = GlobalScope.launch(threadLocal.asContextElement("example")) {
            threadLocal.getSafely() shouldBe "example"

            threadLocal.set("local-value")
            threadLocal.getSafely() shouldBe "local-value"

            (0 until 10).map {
                launch {
                    threadLocal.ensurePresent()
                    threadLocal.getSafely() shouldBe "example"

                    val localVar = (0..100).random()
                    threadLocal.set("value-$localVar")
                    threadLocal.getSafely() shouldBe "value-$localVar"
                }
            }.joinAll()

            // 로컬에서 값을 변경할 경우 부작용이 발생할 수 있다
            // returns 'local-value' or 'example'
            // threadLocal.getSafely() shouldBe "example"
        }
        threadLocal.get() shouldBe "value-1"
        job.join()
    }

    printDone()
}

private suspend inline fun <T> ThreadLocal<T>.getSafely(): T {
    ensurePresent()
    return get()
}
