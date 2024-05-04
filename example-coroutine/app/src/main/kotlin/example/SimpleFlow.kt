package example

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

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

fun <T, R> Flow<T>.map(
    transformation: suspend (T) -> R
): Flow<R> = flow {
    collect {
        emit(transformation(it))
    }
}

suspend fun <T> Flow<T>.counterA(): Flow<Int> {
    return flow {
        // counter 변수는 flow collector에 종속 되므로 동기화 이슈에서 안전
        var counter = 0
        collect {
            counter++
            List(100) { Random.nextLong() }.shuffled().sorted()
            emit(counter)
        }
    }
}

fun <T> Flow<T>.counterB(): Flow<Int> {
    // flow 에 종속 되므로 counter 변수는 동기화 이슈가 발생할 수 있다
    var counter = 0
    return map {
        counter++
        List(100) { Random.nextLong() }.shuffled().sorted()
        counter
    }
}

suspend fun <T> Flow<T>.last(): T? {
    var ele: T? = null
    collect {
        ele = it
    }
    return ele
}

suspend fun main() {
    println("---------------------------------------- 기본 구조 ----------------------------------------")
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

    f
        .map { "$it." }
        .collect(flowCollector)

    println("----------------------------------------------------------------------------------------")

    println("---------------------------------------- 변수 공유와 관련된 실수 ----------------------------------------")
    val abcFlow = flow {
        emit("A")
        emit("B")
        emit("C")
    }
    coroutineScope {
        val f1= abcFlow
        val f2 = abcFlow.counterA()

        launch { println("f1.counterA: ${f1.counterA().last()}") } // 3
        launch { println("f1.counterA: ${f1.counterA().last()}") } // 3
        launch { println("f2.counterA: ${f2.last()}") } // 3
        launch { println("f2.counterA: ${f2.last()}") } // 3
    }

    coroutineScope {
        val f1= abcFlow
        val f2 = abcFlow.counterB()

        launch { println("f1.counterB: ${f1.counterB().last()}") } // 3
        launch { println("f1.counterB: ${f1.counterB().last()}") } // 3
        launch { println("f2.counterB: ${f2.last()}") } // 6 이하 (동기화 이슈로 일관된 값을 얻을 수 없음)
        launch { println("f2.counterB: ${f2.last()}") } // 6 이하 (동기화 이슈로 일관된 값을 얻을 수 없음)
    }
    println("----------------------------------------------------------------------------------------")
}
