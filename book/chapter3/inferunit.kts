/**
 * 코틀린의 Unit 은 자바의 void 와 대응
 * Unit 이라는 이름은 타입 이론에서 아무런 정보를 갖지 않는 싱글톤인 Unit 에서 유래
 *
 * 코틀린은 함수에 리턴이 없으면 Unit 타입을 리턴 타입으로 추론
 */
fun printHello() = println("hello")
val unit = Unit

if (unit === printHello()) {
    println("Unit 은 싱글톤")
}
