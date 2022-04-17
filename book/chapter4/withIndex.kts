// 구조분해 활용
val nameArray = arrayOf("A", "B", "C")
for ((index, name) in nameArray.withIndex()) {
    println("Position of $name is $index")
}

val nameList = listOf("A", "B", "C")
// 코틀린이 JDK 클래스에 편의를 위해 추가해 두었기 때문에 JDK 클래스도 아래와 같이 활용할 수 있다.
// kotlin.collections 패키지 참고
for ((index, name) in nameList.withIndex()) {
    println("Position of $name is $index")
}
