/**
 * 중위 표기법 infix notation
 *
 * 연산자가 중간에 있거나 피연산자 사이에 있음
 * -> Java 에선 -> obj instanceOf Type
 */

println("A" in setOf("A")) // true
println(setOf("A").contains("A")) // true

// 메소드는 기본으로 중위표기를 사용하지 않는다
// 메소드에 infix 어노테이션을 사용하면 코틀린은 점과 괄호를 제거하는 것을 허용해준다.
// infix는 operator와 함께 사용할 수 있다 -> infix에 operator가 꼭 필요한 것은 아님 (서로 독립적)
// infix 한계 -> 정확히 하나의 파라미터만 받아야 함, vararg 미지원, 기본 파라미터 미지원

infix fun Set<String>.has(value: String) = contains(value)
println(setOf("A") has "A") // true
println(setOf("A") has "a") // false
