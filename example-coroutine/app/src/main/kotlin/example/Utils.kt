package example

fun printDone() {
    println("""
        ${"=".repeat(45)} done ${"=".repeat(45)}
    """.trimIndent())
}

infix fun <T> T.shouldBe(expected: T) {
    if (this != expected) throw AssertionError(
        """
            
            expected: <$expected>,
            actual: <$this>
        """.trimIndent()
    )
}
