/**
 * Array<T> 클래스는 코틀린의 배열을 상징한다
 * 최상위 함수 (top-level function) arrayOf()를 이용하여 만들 수 있다
 * 배열은 인덱스 연산자 []를 이용하여 요소에 접근할 수 있다 -> Array<T> 의 get() 메소드 호출
 *
 * 코틀린에서 배열의 타입은 Kotlin.Array 지만 JVM 런타임에서는 Java 의 배열이다
 */
var arr = arrayOf("A", "B", "C")
println("${arr[0] + arr[1] + arr[2]}") // ABC


var numbers = arrayOf(1, 2, 3) // boxing overhead
println(numbers::class) // class kotlin.Array
println(numbers.javaClass) // class [Ljava.lang.Integer;

var rawNumbers = intArrayOf(1, 2, 3) // 특수 어레이 (Java 처럼 primitive 타입 처리를 위한 특수 클래스를 제공, 더 편한듯)
println(rawNumbers::class) // class kotlin.IntArray
println(rawNumbers.javaClass) // class [I

// Array 에 편리한 기능들이 내장되어 있음
println(rawNumbers.size) // 3
println(rawNumbers.average()) // 2.0

// Array 를 생성하면서 배열 초기화시 실행되는 람다함수를 전달할 수 있음 (kotlin document 확인)
println(Array(init = { i -> (i + 1) * (i + 1) }, size = 5).sum()) // 55
println(Array(5) { i -> (i + 1) * (i + 1) }.sum()) // 위 코드와 동일한 코드, Kotlin Syntax 에 익숙치 않아서 여기에선 이해하지 못했으며 추론만하고 넘어감 (책의 예제코드 발췌)

/**
 * 배열 -> 컬렉션 뷰 지원 X
 * 리스트 -> 컬렉션 뷰 지원 O
 */
