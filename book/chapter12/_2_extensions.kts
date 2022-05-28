/**
 * 확장 함수를 이용한 메소드 인젝팅
 */

data class Point(val x: Int, val y: Int)
data class Circle(val cx: Int, val cy: Int, val radius: Int)

// 클래스 제공자가 정의하지 않은 기능이 필요해졌다 -> 이런 경우를 위해 코틀린은 클래스 클라이언트가 이미 정의된 클래스를 상속없이 확장할 수 있는 장치를 제공한다

// Circle 클래스에 contains(point: Point)메소드를 추가한다
operator fun Circle.contains(point: Point) = (point.x - cx) * (point.x - cx) + (point.y - cy) * (point.y - cy) < radius * radius
// 암시적으로 Circle 클래스 인스턴스의 멤버에 접근한다
// 실제로 확장 함수는 컴파일 타임에 static 으로 구현된다
// 확장 함수에서 객체 컨텍스트에 접근할 수 있도록 인스턴스가 함수로 전달될 것이다
/*
static class CircleExtensions {
  public boolean contains(Circle it, Point point) {
    // point.getX() - it.getCx() ....
    return result;
  }
}
 */

// 확장 함수로 전달된 객체 컨텍스트를 이용하여 구현된 매커니즘이기 때문에 private 멤버에 접근이 불가능하다는 한계점을 가지고 있다 (확장함수가 정의된 위치에서 접근 가능한 수준까지 접근할 수 있음)
// 확장함수와 멤버 메소드와 메소드 시그니쳐 충돌이 발생하면 확장함수는 무시된다 (다른곳에 이미 정의된 확장 함수를 사용자가 정의한 확장함수로 변경하는 것은 가능하다, <- 양날의 검)

val circle = Circle(100, 100, 25)
val point1 = Point(110, 110)
val point2 = Point(10, 10)
println(circle.contains(point1)) // true
println(circle.contains(point2)) // false
println(point1 in circle) // true

/**
 * 확장 속성을 이용한 속성 인젝팅
 */
val Circle.area: Double
    get() = kotlin.math.PI * radius * radius
// 확장 속성 또한 클래스 내부에 존재하는 것이 아니기 때문에, 백킹 필드를 가질 수 없다 -> 클래스에 접근 가능한 속성을 이용하여 확장하는 수준까지 가능
// val Circle.number = 1 // error: initializers are not allowed for extension properties
println("Area is ${circle.area}") // Area is 1963.4954084936207

fun String.isPalindrome(): Boolean = reversed() == this
println("aba".isPalindrome()) // true

/**
 * static 메소드 인젝팅
 */

// 클래스의 컴패니언 객체를 확장해서 static 메소드를 인젝팅할 수 있다.
fun String.Companion.toURL(link: String) = java.net.URL(link)
// 코틀린은 String 에 확장되고 추가된 컴패니언이 있다.

String.toURL("https://github.com")

class Ex
//fun Ex.Companion.ex(): Unit = println("error") // error: unresolved reference: Companion

class Ex2 {
    companion object Named
}
fun Ex2.Named.ex() = println("extension")
Ex2.ex()

/**
 * 클래스 내부에서 인젝팅
 */

// 클래스 내부에서 확장한 코드는 scope 이 클래스 안으로 제한된다 (visiblity 제한)

class Point2(x: Int, y: Int) {
    private val pair = Pair(x, y)
    private val firstsign = if (pair.first < 0) "" else "+"
    private val secondsign = if (pair.second < 0) "" else "+"
    override fun toString() = pair.point2String()
    fun Pair<Int, Int>.point2String() = "(${firstsign}${first}, ${this@Point2.secondsign}${this.second})"
    // 클래스 내부에서 정의한 확장 함수의 바디에서 접근할 수 있는 context 는 2개이다 => 함수 내부에 2개의 리시버가 추가됨
    // 함수의 caller 가 가장 우선순위가 높은 context 라고 표현해도 좋을 것 같다 (this 참조의 기본이기 때문에) -> extension receiver 라고 부름
    // 또 다른 context 는 Point2 의 객체 context 이다 -> dispatch receiver 라고 부름
    // point2 의 객체 context 에 접근하기 위해선 this@Point2 <- 이런식의 명시적 표현이 필요하다 (Chapter8 에서 학습한 문법과 동일)
    // 확장함수에서 메소드를 찾는 순서 = extension receiver 탐색 -> dispatch receiver 탐색
    //     프로퍼티 또한 동일
}

val point22 = Point2(1, -1)
println(point22) // (+1, -1)
//2 to 2.point2String() // error: unresolved reference: point2String

fun <T, R, U> ((T) -> R).andThen(next: (R) -> U): ((T) -> U) = { next(this(it)) }
fun inc(number: Int) = number + 1
fun dec(number: Int) = number - 1

val incAndDecAndDecAndDec = ::inc
    .andThen(::dec)
    .andThen(::dec)
    .andThen(::dec)
incAndDecAndDecAndDec(10) // 8
