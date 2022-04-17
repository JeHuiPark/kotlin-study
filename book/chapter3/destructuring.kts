// Triple 은 튜플을 구현하는 코틀린 스탠다드 라이브러리
fun getFullName() = Triple("A", "B", "C")
val (first, middle, last) = getFullName()
println("$first $middle $last") // A B C

// 불필요한 속성은 언더스코어를 활용하여 스킵
val (a) = getFullName()
val (_,b) = getFullName()
println("$a $b") // A B
