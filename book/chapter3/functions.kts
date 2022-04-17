fun max(numbers: IntArray):Int {
    var max = Int.MIN_VALUE
    for (number in numbers) {
        max = if (max < number) number else max
    }
    return max
}

fun printMax(numbers: IntArray) {
    var max = Int.MIN_VALUE
    for (number in numbers) {
        max = if (max < number) number else max
    }
    println(max)
//    return max // error: type mismatch: inferred type is Int but Unit was expected
}

println(max(intArrayOf(1, 2, 3)))
printMax(intArrayOf(1, 2, 3, 999))

fun lambda(factor: Int) = { n: Int -> n * factor } // 익명함수 (anonymous function)
println(lambda(3)(2)) // 6
