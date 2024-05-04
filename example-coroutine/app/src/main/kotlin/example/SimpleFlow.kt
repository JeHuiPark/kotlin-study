package example

fun interface FlowCollector<T> {
    suspend fun emit(value: T)
}

interface Flow<T> {
    suspend fun collect(collector: FlowCollector<T>)
}

// builder -> FlowCollector.emit 함수를 실행할 수 있는 suspend lambda
fun <T> flow(builder: suspend FlowCollector<T>.() -> Unit) = object : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        // FlowCollector 객체('collector')를 builder 람다의 리시버로 전달.
        // same as `builder.invoke(collector)`
        collector.builder()
    }
}

suspend fun main() {
    val flowCollector: FlowCollector<String> = FlowCollector {
        println(it)
    }
    val f = flow {
        // receiver == collector
        if (flowCollector == this) {
            println("should be same")
        }
        emit("1")
        emit("2")
    }

    f.collect(flowCollector)
}
