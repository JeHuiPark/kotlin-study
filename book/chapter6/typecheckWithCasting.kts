val anyVariable1: Any = Switch()
//anyVariable1.on() // error: unresolved reference: on

// smart cast
if (anyVariable1 is Switch) {
    anyVariable1.on()
}

// smart cast in when
fun ex1(v: Any) = when (v) {
    "A" -> "a"
    "B" -> "b"
    "C" -> "c"
    is String -> "[smart cast example] str length = ${v.length}, uppercase result = ${v.uppercase()}, 'v' is Any Type Parameter"
    else -> "N/A"
}

println(ex1("A"))
println(ex1("abab"))

// 명시적 캐스트 (unsafe cast) as 연산자
val anyVariable2: Any = Switch()
(anyVariable2 as Switch).on()

//val anyVariable3: Any = "String"
//(anyVariable3 as Switch).on() // throw ClassCastException

// 안전한 명시적 캐스트 as? 연산자, 캐스팅 실패 -> null 참조
val anyVariable4: Any = "String"
(anyVariable4 as? Switch)?.on() ?: println("anyVariable4 is string")

/**
 * 가능하면 스마트 캐스트 사용
 * 스마트 캐스트가 불가능한 경우에 safe cast (as?) 사용
 * unsafe cast (as) 는 지양하자
 */

class Switch {
    fun on() {
        println("on")
    }
}
