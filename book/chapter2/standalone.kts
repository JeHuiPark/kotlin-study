fun notluff() {
    println("called")
    throw RuntimeException("oops")
}

println("not in a function")

try {
    notluff()
} catch (ex: Exception) {
    val stackTrace = ex.getStackTrace()
    println(stackTrace[0]) // function
    println(stackTrace[1]) // class constructor
}