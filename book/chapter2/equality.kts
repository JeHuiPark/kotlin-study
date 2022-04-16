// Java 의 equals() 는 코틀린에서 == 연산자로 대체된다
// Java 의 == 연산자는 코틀린에서 === 연산자로 대체된다

fun intToString(n: Int) = "" + n;

println(intToString(1) == intToString(1)) // true
println(intToString(1) === intToString(1)) // false
println(null == "hi") // false (null safety)
