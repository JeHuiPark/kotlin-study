package ex

import kotlin.jvm.JvmStatic

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Running App...")
        println(Util().f2c(50.0))
    }
}
