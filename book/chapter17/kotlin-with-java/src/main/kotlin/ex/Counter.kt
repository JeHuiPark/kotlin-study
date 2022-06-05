package ex

import java.io.File
import java.io.FileNotFoundException

data class Counter(val value: Int) {
    operator fun plus(other: Counter) = Counter(value + other.value)

    companion object {

        /**
         * kotlin에는 static 메소드가 없다. static 메소드와 가장 가까운 코틀린의 메소드는 싱글톤과 컴패니언 객체에서 정의한 메소드이다.
         * Java 에서 이런 메소드를 static 메소드로 이용하는 것과 동일한 경험을 주도록 설계하려면
         * kotlin complier에게 이 메소드를 바이트코드에 static으로 만들라는 지시를 해야 한다.
         * 이때 @JvmStatic 어노테이션을 이용한다
         */
        @JvmStatic
        fun create1() = Counter(0)

        fun create2() = Counter(0)
    }

    fun map(mapper: (Counter) -> Counter) = mapper(this)

    // Kotlin compiler는 Java compiler와 다르게 exception을 하나로 취급한다
    // checked exception, unchecked exception을 구분하지 않고 하나로 취급한다
    // try - catch 는 선택사항이다 (예외처리를 개발자에게 강제하지 않는다, 메소드에 throws 절을 마크할 수 없음)
    fun readFile(path: String) = File(path).readLines()

    // @Throws 어노테이션을 이용하여 Kotlin compiler에게 throws FileNotFoundException 절을 메소드 시그니쳐에 추가하라고 지시할 수 있다
    // Kotlin compiler는 throws 절을 무시하기 때문에 이 어노테이션은 코틀린으로 작성된 코드의 디자인에 영향을 주지 않는다
    @Suppress("FunctionName")
    @kotlin.jvm.Throws(FileNotFoundException::class)
    fun readFile_markedThrows(path: String) = File(path).readLines()

    fun add(n: Int = 1) = Counter(value + n)

    @Suppress("FunctionName")
    @JvmOverloads
    fun add_javaCompatible(n: Int = 1) = Counter(value + n)
}

/**
 * top level function
 * 바이트코드에서 탑레벨 함수는 허용되지 않는다
 * 그래서 Kotlin은 top level 함수를 가지고 있는 파일의 이름에서 유리된 클래스에서 탑레벨 함수를 가지고 있도록 한다
 *
 * package 선언 전에 @file:JvmName 어노테이션을 사용하여 top level 의 클래스 명을 지정할 수 있음
 */
fun createCounter() = Counter(0)
