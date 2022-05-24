/**
 * 함수에 operator 키워드를 정의하여 연산자 오버로딩을 정의할 수 있다
 */

// Pair<Int, Int> 에 +연산자를 오버로딩하는 예제 (서드파티 클래스 연산자 오버로딩)
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(first + other.first, second + other.second)
// plus()는 '+' 연산자를 위한 특별한 메소드 이름이다.
// plus()는 묵시적 객체에서 동작한다. 왼쪽 피연산자는 this 를 이용해서 참조한다, 오른쪽 피연산자는 other(메소드 인자)를 이용해서 참조한다

val pair = (1 to 1) + (2 to 3)
println(pair) // (3, 4)

// Custom Class Overloading
data class MyClass(val prop: Int) {
    operator fun plus(other: MyClass) = MyClass(prop + other.prop)
    operator fun minus(other: MyClass) = MyClass(prop - other.prop)
    operator fun times(other: MyClass) = MyClass(prop * other.prop)
    operator fun div(other: MyClass) = MyClass(prop / other.prop)
    operator fun not() = prop < 0

    // https://kotlinlang.org/docs/operator-overloading.html

    // 혼합연산(+=, -=, ...)을 오버로딩할 경우에는 특화 메소드명 뒤에 Assign 을 붙인다 예를들어 plusAssign
    // plus()를 구현해놨다면 +=는 plus()를 적절하게 사용할 것이기 때문에 이 경우에는 혼합연산을 구현할 필요가 없다
    // check point -> plus()는 순수함수이지만, 혼합연산은 피연산자의 상태를 변경하기 때문에 피연산자는 뮤터블이어야 한다
    // https://kotlinlang.org/docs/operator-overloading.html#augmented-assignments

    override fun toString(): String {
        return prop.toString()
    }
}
println(MyClass(1) + MyClass(1)) // 2
println(MyClass(1) - MyClass(1)) // 0
println(MyClass(2) * MyClass(3)) // 6
println(MyClass(64) / MyClass(2)) // 32
println(!MyClass(-1)) // true

//val a = MyClass(1) // a += 2 ---> a = a + 2
var a = MyClass(1) // 혼합 연산을 처리하기 위해 피연산자로 사용될 변수를 var로 선언하였다
val b = MyClass(3)
a += b
println(a) // 4
