val map = mapOf("key" to "value") // immutable view, list, set 과 동일한 패턴

println(map["key"]) // [] 연산자도 사용할 수 있다

//val nonNullVariable: String = map["key"] // compile error -> map.get 은 nullable 로 디자인되었기 때문에 당연한 결과다
val nullableVariable: String? = map["key"]

hashMapOf("key" to "value") // jdk 참조 리턴
linkedMapOf("key" to "value") // jdk 참조 리턴
sortedMapOf("key" to "value") // jdk 참조 리턴

mutableMapOf("key" to "value") // 코틀린 뷰 참조 리턴

/**
 * 앞서 학습한 컬렉션들 처럼 +, - 연산자를 지원한다
 * 구현 디자인은 동일하다
 * k/v 자료구조 특징에 맞추어 구현되었으니 예제코드 생략
 */

for ((k, v) in mapOf ("k1" to "v1", "k2" to "v2")) {
    println("map 의 entry 는 구조분해 (destructuring)를 지원하도록 디자인 되어 이렇게도 사용할 수 있다 {k=$k, v=$v}")
}
