package ex

import java.io.FileNotFoundException

fun main() {
    val counter = Counter(1)
    println(counter + counter)
    println(Counter.create1())
    println(Counter.create2())
    println(counter.map { it + it })

    try {
        counter.readFile("not_found")
    } catch (expected: FileNotFoundException) {
        println("file not found")
    }

    createCounter() // ok
}