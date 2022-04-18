val set = setOf("A", "B", "C") // kotlin.collection.Set<T> 인터페이스 참조 리턴 (immutable view), 객체는 JDK 의 컬렉션 객체 -> java.util.LinkedHashSet

hashSetOf("A") // JDK HashSet 참조 리턴
mutableSetOf("A") // 코틀린 뷰 참조 리턴
