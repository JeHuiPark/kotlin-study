fun nickName1(name: String): String {
    if (name =="William") {
        return "Bill"
    }
    return name
//    return null // error: null can not be a value of a non-null type String
}

println(nickName1("William"))
//println(nickName1(null)) // error: null can not be a value of a non-null type String

// 코틀린은 null 을 null 불가 참조에 할당하거나 참조타입이 null 불가인 곳에 null 을 리턴하려고 하면 컴파일 오류가 난다

// 일반적으로 Java 와 상호운용할 목적이 아니라면 null 과 nullable 타입은 절대 사용하지 않는 편을 권장한다

/**
 * nullable
 * null 불가 타입들은 각자 대응되는 null 가능 타입이 존재한다
 * nullable 타입은 nonnull 타입 뒤에 ? 가 붙는다
 * 예를 들어 String 은 nonnull, String? 은 nullable 이다
 */

/**
 * null 가능 타입의 바이트코드 맵핑
 * JVM 에 직접 표시할 수 없으므로 null 가능 타입은 대응되는 null 불가 타입으로 대체된다.
 * 예를 들어 String? 은 바이트코드에서 다른 메타 인스트럭션과 함께 String이 된다.
 * 코틀린 컴파일러는 이 메타 인스트럭션을 컴파일 시간에 체크하기 때문에 실행 시간에는 성능적 오버헤드가 없다.
 */

fun nickName2(name: String): String? {
    if (name == "William") {
        return "Bill"
    }
    return null
}

println(nickName2("William")) // Bill
println(nickName2("Ben")) // null
//println(nickName2(null)) // error: null can not be a value of a non-null type String

fun nickName3(name: String?): String? {
    if (name == "William") {
        return "Bill"
    }
//    return name.reversed() // error only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?
    // 세이프 콜 연산자 (safe call operator) 혹은 nulll 아 아님을 확인해 주는 연산자 (non-null assertion operator) 프리픽스가 필요하다

    // non-null assertion operator
//    if (name != null) {
//        return name.reversed()
//    }
//    return null

    // safe call operator
//    return name?.reversed()

    // 메서드 체이닝 형태에서 safe call operator
//    return name?.reversed()?.uppercase()

    // 엘비스 연산자 (elvis operator)
    return name?.reversed()?.uppercase() ?: "empty"

    // notnull 확정 연산자 (당연히 권장하지 않는다)
//    return name!!.reversed() // throwable NullPointerException
}
println(nickName3(null))
println(nickName3("abc"))

/**
 * nullable 참조를 처리할 때 참조의 값에 따라 다르게 동작하거나 다른 행동을 취해야 한다면
 * safe call, elvis operator 대신 when 사용을 고려
 *
 * 요약
 * -> safe call, elvis operator 는 값을 추출할 때
 * -> when 은 nullable 참조에 대한 처리를 결정할 때
 */
fun nickName4(name: String?) = when (name) {
    "William" -> "Bill"
    null -> "empty"
    else -> name.reversed().uppercase()
}
println(nickName4("William"))
println(nickName4(null))
println(nickName4("abc"))
