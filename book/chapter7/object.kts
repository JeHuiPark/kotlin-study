fun printUser() {

    // 익명 객체, 객체 표현식
    val user = object {
        val name = "박제희"
        val age = 31
        val gender = "male"

        override fun toString(): String {
            return "name = $name, age = $age, gender = $gender"
        }
    }
    println(user)
}

printUser()

fun createRunnable(): Runnable {

    // 익명 구현체
    return object :Runnable {
        override fun run() {
            println("called")
        }
    }
}
createRunnable().run()
Runnable { println("구현 대상이 싱글 추상 메소드 인터페이스라면 이런 표현도 가능하다") }.run()

// 객체 선언을 이용한 싱글톤
// oject 키워드와 {} 블록 사이에 이름을 넣는다면, 코틀린은 이를 표현식이 아니라 명령문 또는 선언(Declaration)으로 인식한다
object Util {
    // @JvmStatic 어노테이션을 사용하지 않았기 때문에 해당 메소드는 바이트코드에서 static이 되지 않는다
    fun numberOfProcessors() = Runtime.getRuntime().availableProcessors()

    fun printMe() = println("${this.javaClass}, ${this::class}")
}
println(Util.numberOfProcessors())
Util.printMe()
// 위 클래스는 객체를 생성할 수 없다
// 코틀린 컴파일러는 위 클래스를 클래스로 취급하지 않는다. Util 은 이미 객체인 상태다

// 싱글톤이 부모 클래스나 인터페이스를 가지고 있다면 싱글톤은 참조로 할당되거나 부모 타입의 파라미터로 전달될 수 있다
object Sun: Runnable {
    val radiusInKM = 696000
    var coreTemperatureInC = 15000000
    override fun run() {
        println("spin...")
    }
}

fun moveIt(runnable: Runnable) = runnable.run()

println(Sun.radiusInKM)
println(Sun.radiusInKM)

