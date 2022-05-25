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
 * TODO static 메소드 인젝팅
 */
