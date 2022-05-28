import kotlin.random.Random

/**
 * https://kotlinlang.org/docs/scope-functions.html
 */

fun format(vararg args: String) = String.format("%-10s%-10s%-10s%-10s%-10s",
    args[0], args[1], args[2], args[3], args[4])
val str = "context"
val result = "RESULT"
fun toString() = "lexical"

println(format("Method", "Argument", "Receiver", "Return", "Result"))
println("=".repeat(50))

val formatArgs = Array<String>(5, { "" })

formatArgs[4] = str.let { arg ->
    formatArgs[0] = "let"
    formatArgs[1] = arg
    formatArgs[2] = this.toString()
    formatArgs[3] = result
    result
}
println(format(*formatArgs))

formatArgs[4] = str.also { arg ->
    formatArgs[0] = "also"
    formatArgs[1] = arg
    formatArgs[2] = this.toString()
    formatArgs[3] = result
    result
}
println(format(*formatArgs))

formatArgs[4] = str.run {
    formatArgs[0] = "run"
    formatArgs[1] = "N/A"
    formatArgs[2] = this.toString()
    formatArgs[3] = result
    result
}
println(format(*formatArgs))

formatArgs[4] = str.apply {
    formatArgs[0] = "apply"
    formatArgs[1] = "N/A"
    formatArgs[2] = this.toString()
    formatArgs[3] = result
    result
}
println(format(*formatArgs))
/*
Method    Argument  Receiver  Return    Result
==================================================
let       context   lexical   RESULT    RESULT
also      context   lexical   RESULT    context
run       N/A       context   RESULT    RESULT
apply     N/A       context   RESULT    context
 */

/**
 * apply 를 활용한 반복 참조 제거
 *
 * 클래스에서 메서드 체이닝을 지원하지 않는 경우에 체이닝과 유사한 형태로 코드를 디자인 하고 싶을 경우에 적합
 */
println(
    mutableListOf<Int>()
        .apply {
            add(1)
            add(2)
        }
        .apply { add(3) }
        .apply { add(4) }
)

/**
 * run 을 이용한 결과 얻기
 */
println(
    "list size is ${
        mutableListOf<Int>()
            .run { 
                add(1)
                add(2)
                size
            }
    }"
)

/**
 * let & also
 */
fun doSomethingAndReturn(list: MutableCollection<Int>): Int =
    list.apply {
        val number = Random.nextInt()
        println("gen number is $number")
        add(number)
    }.last()

println(
    "${
        mutableListOf(1)
            .let(::doSomethingAndReturn)
    }"
) // {last number}

println(
    "${
        mutableListOf(1)
            .also { doSomethingAndReturn(it) }
            .also { doSomethingAndReturn(it) }
            .also { doSomethingAndReturn(it) }
    }"
) // [1, {gen number}, {gen number}, {gen number}]


/**
 * 람다에 리시버 전달
 */
val printIt: String.(Int) -> Unit = {
    println("it is $it, legnth is $length")
}
// 람다 시그니쳐를 보면 확장 함수 선언부와 유사하다는 것을 알 수 있음
printIt("ABC", 1) // it is 1, legnth is 3
"ABC".printIt(1) // it is 1, legnth is 3

fun top(func: String.() -> Unit) = "hello".func()
fun nested(func: Int.() -> Unit) = (-2).func()
fun toDouble() = -2

val length = 200
top {
    println("In Outer lambda $this and $length") // In Outer lambda hello and 5
    nested {
        println("in inner lambda $this and ${toDouble()}") // in inner lambda -2 and -2.0
        println("from inner throuh receiver of outer: ${length}") // from inner throuh receiver of outer: 5
        println("from inner to outer receiver ${this@top}") // from inner to outer receiver hello
    }
}
