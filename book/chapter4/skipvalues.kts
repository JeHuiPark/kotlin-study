for (i in 1 until 5) { print("$i, ") } // 1, 2, 3, 4,
println()

// step() 메소드는 IntRange, IntProgression 객체를 IntProgression 객체로 변환한다
for (i in 1 .. 5 step 2) { print("$i, ") } // 1, 3, 5
println()

for (i in (1..9).filter { it != 4 }) { print("$i, ") } // 1, 2, 3, 5, 6, 7, 8, 9,
println()
