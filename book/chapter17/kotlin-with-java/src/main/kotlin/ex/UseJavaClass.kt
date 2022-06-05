package ex

fun main() {
    val javaObject = JavaClass()
    println(javaObject.zero)
    println(javaObject.convertToUpper(listOf("Jehui", "Park")))
    javaObject.suspend()
//    javaObject.when() // error: Expecting an expression
    javaObject.`when`() // 키워드 충돌이 발생할 때는 백틱을 이용하여 escape 할 수 있음
}
