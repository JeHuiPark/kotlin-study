// 람다가 하나의 파라미터만 받는다면 파라미터 정의를 생략하고 it 이라는 이름의 특별한 암시적 파라미터를 사용할 수 았다.
// 암시적 파라미터를 사용할 때는 파라미터 정의와 화살표를 생략한다
// 이 표현식의 단점은 람다가 파라미터가 없는 람다인지 아니면 it 을 사용하여 파라미터 하나를 취하는 람다인지 빠르게 파악하기 힘들다는 점이다.
//     -> 람다가 짧다면 수용할 수 있는 단점이다 (장점이 더 크기 때문)
fun isPrime(n: Int) = n > 1 && (2 until n).none { n % it == 0 }

/**
 * 코틀린은 함수 인자중 람다가 마지막에 있는 경우엔 아래와 같은 특별한 문법을 지원한다
 *
 * 함수의 파라미터로 람다가 하나 이상 들어간다면 람다 파라미터를 맨 마지막에 사용하도록 하자
 */
fun walk1To(n: Int, action: (Int) -> Unit) = (1..n).forEach(action)
walk1To(5) { print(it) }

/**
 * 함수에 전달된 람다가 단순하게 전달된 파라미터를 다른 함수로 전달하는 패스스루 역할을 한다면 (Java 의 메소드 레퍼런스처럼)함수참조를 사용하여 표현할 수 있다
 */
walk1To(5, System.out::println)

// return lambda example
// 람다에서는 일반적으로 return 키워드를 허용하지 않는다 (예외는 있다)
//fun predicateOfLength(length: Int): (String) -> Boolean {
//    return { input: String -> input.length == length }
//}
fun predicateOfLength(length: Int) = { input: String -> input.length == length }
val names = listOf("pam", "pat", "paul", "paula")
println(names.find(predicateOfLength(5))) // paula
println(names.find(predicateOfLength(4))) // paul

// anonymous function
// 익명 함수는 람다보다 익명함수가 더 적합한 경우에 선택적으로 사용 (16챕터 비동기 프로그래밍 참고)
val check5Length = fun(name: String): Boolean { return name.length == 5 }
println(check5Length("ABCDE"))

/**
 * 클로저와 렉시컬 스코핑
 */
val doubleIt = { e: Int -> e * 2 }
// 람다는 클로저라고 불린다 -> 람다는 스코프를 로컬이 아닌 속성과 메소드로 확장할 수 있다
// doubleIt 람다를 클로저로 만들어보자
val doubleIt2 = { e: Int -> e * factor }
var factor = 2
// doubleIt2 람다 바디에는 factor 가 없다 -> 로컬 변수가 아니다
//     -> 컴파일러는 factor 변수에 대한 클로저의 범위(스코프) 즉, 클로저의 바디가 정의된 곳을 찾는다
//     -> 만약 클로저가 정의된 곳에서 factor 변수를 찾지 못하면 클로저가 정의된 곳이 정의된 곳으로 스코프를 확장하고, 또 못 찾는다면 계속 범위를 확장한다
//     -> 이것을 렉시컬 스코핑이라고 한다
println(doubleIt2(2)) // 4
// 코틀린은 클로저 안의 뮤터블 변수의 값을 변경할 수 있다 -> 람다 바디 바깥의 변수에 쓰기 가능
var incrementFactor = { factor++ }
incrementFactor()
println(doubleIt2(2)) // 6
println(factor) // 3

/**
 * 비지역성(non-local)과 라벨(labeled)리턴
 */
fun invokeWith(n: Int, action: (Int) -> Unit) {
    println("enter invokeWith n: $n")
    action(n)
    println("exit invokeWith n: $n")
}

fun caller() {
    (1..3).forEach { i ->
        invokeWith(i) {
            println("caller:enter for $it")
            if (it == 2) {
                // 코틀린은 아래 return 이 무슨 의미인지 모른다
                // invokeWith() 리턴?
                // forEach() 리턴?
                // caller() 리턴?
                // 그래서 코틀린은 일반적인 상황에는 람다에서 return 키워드를 허용하지 않는다
//                return // error: 'return' is not allowed here

                // 코틀린은 이 규칙에 2가지 예외(labeled return, non-local return)를 제공한다
                return@invokeWith
            }
            println("caller:exit for $it")
        }
    }
}

caller()
println("after return from caller")

fun caller2() {
    (1..3).forEach { i ->
        invokeWith(i) here@ { // 라벨링
            println("caller2:enter for $it")
            if (it == 2) {
                return@here // 라벨 리턴 (명시적 라벨 리턴이라고 부르며 권장사항)
//                return@invokeWith // 암시적 라벨 리턴
            }
            println("caller2:exit for $it")
        }
    }
}
caller2()
println("after return from caller2")

fun caller3() {
    (1..3).forEach { i ->
        println("caller3:in forEach for $i")
        if (i == 2) {
            // 함수(caller3)를 빠져나간다 -> forEach 메서드가 inline 메서드이기 때문
            return // non-local return
        }
        invokeWith(i) {
            println("caller3:enter for $it")
            if (it == 2) {
                return@invokeWith
            }
            println("caller3:exit for $it")
        }
    }
    println("end of caller3")
}
caller3()
println("after return from caller3")
// 람다를 받는 함수가 inline으로 선언된 경우 람다에서 non-local return 을 사용하면 현재 동작중인 람다를 선언한 곳 바깥으로 나갈 수 있다.

// 람다를 빠져나가기 위해 라벨 리턴을 사용할 수 있다
// 람다에서 return 을 사용할 수 있다면 람다에서 빠져나가거나 람다를 호출한 곳을 빠져나가는 것이 아니라 람다가 정의된 곳에서 빠져나간다는 사실을 기억해야 한다

println("")

/**
 * 인라인 함수
 *
 * 코틀린은 람다를 사용할 때 호출 오버헤드를 제거하고 성능을 향상시키기 위해서 inline 키워드를 제공함
 */
inline fun invokeThree(
    action1: () -> Unit, // 기본적으로 인라인 키워드는 함수에서 실행하는 람다도 인라인으로 변경한다
    noinline action2: () -> Unit, // 어떤 이유로 선택적으로 인라인 최적화에서 제외하고자 할 때는 noinline 키워드를 적용하면 해당 람다는 inline 최적화에서 제외된다
    crossinline action3: () -> Unit // action3 는 invokeThree 함수가 아닌 실제 호출되는 부분에서 인라인 최적화가 진행된다, crossinline 람다 파라미터에는 논로컬 리턴을 사용할 수 없다
): () -> Unit {
    println("enter invokeThree")
    action1()
    action2()
    println("exit invokeThree")
//    return { input: Int -> action1() } // invokeThree 애서 직접 호출하지 않는 람다를 인라인으로 만들 수 없음 -> 컴파일 에러
    return {
        reportStackTrace()
        action3() // 여기에서 인라인 최적화가 진행된다
    }
}
fun callInvokeThree(): () -> Unit {
    val action3 = invokeThree({ reportStackTrace() }, ::reportStackTrace, ::reportStackTrace)
    println("received action3")
    return action3
}
fun reportStackTrace() {
    println("")
    val stackTrace = java.lang.RuntimeException().stackTrace
    println("Stack depth: ${stackTrace.size}")
    println("Partial listing of the stack: ")
    stackTrace.take(3).forEach(::println)
}
val returnedFunction = callInvokeThree()
returnedFunction()

/**
 * inline 함수 다시 요약
 *
 * inline 은 함수를 인라인으로 함들어서 함수 호출 오버헤드를 제거하여 함수 성능을 최적화한다
 * 파라미터로 전달된 람다가 noinline이나 crossinline이 아닌 경우에만 논로컬 리턴이 가능하다
 *
 * 람다에서 라벨이 없는 리턴은 항상 함수에서 발생하며 람다에서는 발생하지 않는다
 * 람다에서 라벨이 없는 리턴은 인라인이 아닌 람다에서 허용되지 않는다
 * 함수명은 라벨의 기본 값이 되지만 이를 맹신해서는 안된다 -> 명시적 라벨링을 권장
 * 일반적으로 코드 최적화를 하기전에 성능 측정을 먼저하라. 특히 람다를 사용하는 코드라면 성능 측정을 먼저 해야한다
 * inline은 눈에 띄는 성능 향상이 있을 때만 사용하라
 */
