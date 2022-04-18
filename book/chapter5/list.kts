val list = listOf("A", "B", "C") // kotlin.collection.List<T> 인터페이스 참조 리턴 (immutable view), 객체는 JDK 의 컬렉션 객체임
// list.add("D") // compile error

/**
 * 인덱스 연산자 [] 사용 가능 (내부적으로 get() 메서드 실행)
 * 책에서는 get() 대신 [] 연사자를 사용하는 것을 권장하고 있다 -> 노이즈가 적고 편리하다
 */
println("${list[0]}") // A

/**
 * 컬렉션에 요소가 있는지 확인할 때 in 연산자를 사용하는 것도 가능 (연산자 오버로딩)
 */
println("${"A" in list}") // true

var newList = list + "D" // 연산자 오버로딩, list 를 변경하지 않고 기존 list 를 복사한 새로운 list 에서 요소 추가
println("list size = ${list.size}, newList size = ${newList.size}") // list size = 3, newList size = 4

newList = list - "D" // 연산자 오버로딩, list 를 변경하지 않고 기존 list 를 복사한 새로운 list 에서 요소 제거
println("list size = ${list.size}, newList size = ${newList.size}") // list size = 3, newList size = 3

val mutableList1 = arrayListOf(0) // JDK ArrayList 직접 참조
val mutableList2 = mutableListOf(0) // 코틀린 뷰를 이용한 간접 참조

mutableList1.add(1)
mutableList2.add(1)
println("mutableList1 size = ${mutableList1.size}, mutableList2 size = ${mutableList2.size}") // mutableList1 size = 2, mutableList2 size = 2
