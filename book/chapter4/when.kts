import java.util.*

fun whatTodo(dayOfWeek: Any) = when (dayOfWeek) {
    "SAT", "SUN" -> "RELAX"
    in listOf("TUE", "WED", "THU", "FRI") -> "WORK HARD"
    "MON" -> "SAD"
    0 -> "SAD"
    1..4 -> "WORK HARD"
            5..6 -> "RELAX"
    is String -> "MON|TUE|WED|THU|FRI|SAT|SUN"
    is Int -> "0..6"
    else -> "N/A" // when 이 표현식으로 사용될 경우에는 else 가 강제된다 (모든 경우에 대한 처리를 강제)
}

println(whatTodo("SAT")) // RELAX
println(whatTodo("FRI")) // WORK HARD
println(whatTodo(0)) // SAD
println(whatTodo("MON")) // SAD
println(whatTodo("AAA")) // MON|TUE|WED|THU|FRI|SAT|SUN
println(whatTodo(7)) // 0..6

// when scope
val core = Runtime.getRuntime().availableProcessors()
var systeInfo1 = when {
    core >= 16 -> "oh!!!"
    core >= 8 -> "oh!!"
    core >= 4 -> "oh!"
    else -> "oops your machine core is $core"
}
println(systeInfo1)

// when scope
val systeInfo2 = when (val core = 3) {
    in Int.MIN_VALUE..3 -> "oops your machine core is $core"
    in 4..7 -> "oh!"
    in 8..15 -> "oh!!"
    else -> "oh!!!"
}
println(systeInfo2) // oops your machine core is 3

// 명령형 when 에서는 모든 케이스를 처리하지 않더라도 컴파일 오류가 발생하지 않는다 (경고로 처리)
when (Random().nextBoolean()) {
    true -> println("it is random print")
}
