// Keep It Simple, Studpid

/**
 * 단일표현함수 (single expression function)
 * 함수 정의부분과 함수 바디를 = 로 구분한다
 * 리턴타입 추론이 가능
 * return 키워드 사용불가
 */
fun greet() = "hello world"

//val v:Int = greet() // error: type mismatch: inferred type is String but Int was expected
