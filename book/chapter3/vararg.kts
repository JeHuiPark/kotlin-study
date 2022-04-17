fun printMax(vararg numbers: Int) {
    var max = Int.MIN_VALUE
    if (numbers.size == 0) {
        println("n/a")
        return
    }
    for (number in numbers) {
        max = if (max < number) number else max
    }
    println(max)
}

printMax() // n/a
printMax(1, 2, 3) // 3

// spread operator
printMax(*intArrayOf(1, 2, 3))
printMax(*listOf(1, 2, 3).toIntArray())
