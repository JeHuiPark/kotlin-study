/**
 * JAVA 와 다르게 Stream 에서 제공하는 filter(), map() 과 같은 반복 연산을 Collection 에서도 직접 사용할 수 있도록 지원한다
 */

val list = listOf(1, 2, 3, 4, 5)
list.filter({
    // collection 에서 제공하는 고차함수는 즉시 실행된다
    println("by Collection filter1")
    it % 2 == 0
})

list.filter({
    println("by Collection filter2")
    it == 2
    // collection 에서 지원하는 연산은 실행 최적화를 지원하지 않는다
})
    // 실행 최적화를 지원하지 않기 때문에 filter 는 collection 의 크기만큼 실행된다
    .first()

// 실행 최적화를 위한 시퀀스 (지연 연산) Java 의 Stream 과 유사
list.asSequence().filter({
    println("This code is not executed")
    it % 2 == 0 //
})
// Java 의 Stream 처럼 연산 파이프라인만 정의된 상태이기 때문에 filter 에 전달된 람다는 실행되지 않는다

val listSequence = list.asSequence()
listSequence.filter({
    println("value = $it, by Sequence filter1")
    it % 2 == 0 //
}).first()
// forEach 메서드 실행으로 Sequence 에 정의된 연산 파이프라인이 순서대로 실행된다 => filter 에 전달된 람다 실행

listSequence.filter({
    println("value = $it, by Sequence filter2")
    it == 1 //
}).first()
// Sequence 는 실행 최적화를 지원하기 때문에 원하는 결과를 반환할 수 있을 경우에는 연산을 종료한다 (awesome)
//     -> 예제 코드에서는 필터에 전달된 람다는 1회만 실행될 것이다

var seq = 0
val infiniteSequence = generateSequence {
    println("gen sequence")
    seq++
}
println(infiniteSequence.first())
// println(infiniteSequence.first()) generateSequence 이 리턴하는 시퀀스는 오직 한 번만 실행할 수 있다

val primes = sequence {
    var i: Long = 0
    fun isPrime(n: Long) = n > 1 && (2 until n).none { i-> n % i == 0L }
    while (true) {
        i++
        println("requested value = $i")
        if (isPrime(i)) {
            yield(i) // Yields a value to the Iterator being built and suspends until the next value is requested.
        }
    }
}
primes.take(2).forEach { println("received value = $it") }
