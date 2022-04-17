// kotlin.langes 패키지를 활용하여 반복문을 우아하게 작성할 수 있다

val intRange = 1..5 // Inferred Type is IntRange
var charRange = 'a' .. 'c' // Inferred Type is CharRange

for (i in intRange) { print("$i, ") } // 1, 2, 3, 4, 5,
println()
for (c in charRange) { print("$c, ") } // a, b, c,
println()

var closedRange = "aa" .. "ac" // Inferred Type is ClosedRange
println(closedRange.contains("aa")) // true
println(closedRange.contains("ab")) // true
println(closedRange.contains("ac")) // true
println(closedRange.contains("a")) // false
// for (str in closedRange) { print("$str") } // ClosedRange 에는 iterator() 메소드가 구현되지 않아 외부 반복자를 사용할 수 없음 => 서드파트 클래스 인젝팅으로 확장가능
