import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

// 피보나치 수열
// https://ko.wikipedia.org/wiki/%ED%94%BC%EB%B3%B4%EB%82%98%EC%B9%98_%EC%88%98
fun fib(n: Int): Long = when (n) {
    0, 1 -> 1L
    else -> fib(n - 1) + fib(n - 2)
}

println("${measureTimeMillis { fib(40) }} ms") // 350 ms
println("${measureTimeMillis { fib(45) }} ms") // 3881 ms

// memoization solution 1
fun <T, R> ((T) -> R).memoize(): ((T) -> R) {
    val original = this
    val cache = mutableMapOf<T, R>()
    return { n: T -> cache.getOrPut(n) { original(n) } }
}

lateinit var fib2: (Int) -> Long
fib2 = { n: Int ->
    when (n) {
        0, 1 -> 1L
        else -> fib2(n - 1) + fib2(n - 2)
    }
}.memoize()

println("${measureTimeMillis { fib2(40) }} ms") // 0 ms
println("${measureTimeMillis { fib2(45) }} ms") // 0 ms

// memoization solution 2
class Memoize<T, R>(val func: (T) -> R) {
    val cache = mutableMapOf<T, R>()
    operator fun getValue(thisRef: Any?, property: KProperty<*>) = { n: T -> cache.getOrPut(n) { func(n) } }
}

val fib3: (Int) -> Long by Memoize<Int, Long> {
    when (it) {
        0, 1 -> 1L
        else -> fib3(it - 1) + fib3(it - 2)
    }
}

println("${measureTimeMillis { fib3(40) }} ms") // 0 ms
println("${measureTimeMillis { fib3(45) }} ms") // 0 ms
