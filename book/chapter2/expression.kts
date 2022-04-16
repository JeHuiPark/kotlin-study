fun boolToString(bool: Boolean): String {
    val conditionalVariable = if (bool == true) "yes" else "no"
    return conditionalVariable
}
println(boolToString(true))
println(boolToString(false))

fun stringToBoolean(str: String): Boolean {
    return try {
        str.toBooleanStrict()
    } catch (ex: Exception) {
        println("stringToBoolean fallback")
        false
    }
}

println(stringToBoolean("true"))
println(stringToBoolean("false"))
println(stringToBoolean("afaf"))
