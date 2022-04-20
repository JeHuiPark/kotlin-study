import java.io.Closeable
import java.lang.Runnable

open class Fruit {
    fun printClass() {
        println(this::class)
    }
}
class Banana : Fruit()
class Orange : Fruit()

fun receiveFruits1(fruits: Array<Fruit>) {
    println("${fruits.size}")
}

val bananas1: Array<Banana> = arrayOf()
// 타입 불변성 제약으로 인해 컴파일 오류 발생
//receiveFruits1(bananas) // error: type mismatch: inferred type is Array<Generic.Banana> but Array<Generic.Fruit> was expected

fun receiveFruits2(fruits: List<Fruit>) {
    println("${fruits.size}")
}

val bananas2: List<Banana> = listOf()
// 코틀린에서는 아래와 같은 표현이 가능하다 -> List 인터페이스 선언부에 공변성을 허용하도록 정의 됨 (Declaration-site variance)
// (Java 에서는 사용처에서도 공변성 허용을 위한 처리를 명시적으로 처리해야 했으나, 제네릭 클래스 선언부에 공변성이 허용되도록 정의 되었다면 사용처에도 적용 됨)
receiveFruits2(bananas2) // OK

// Array 는 mutable 하기 때문에 타입 안정성을 지키고자 선언부에서 공변성 허용하지 않음
// 제네릭 클래스 선언부에 공변성 허용이 정의되지 않았으며, 공변성이 필요할 경우에는 공변성 허용을 컴파일러에게 요청 (Use-site variance)
fun receiveFruits3(fruits: Array<out Fruit>) {
    // Array<out T> 는 코틀린에게 공변성 파라미터에 변경이나 추가가 없다는 것을 보장한다 -> 쓰기 작업 불가
//    fruits[0] = Fruit() // error: type mismatch: inferred type is Generic.Fruit but Nothing was expected

    // read only
    fruits[0].printClass()
}
receiveFruits3(arrayOf(Banana())) // OK

// 반공병성 요청을 위한 in 키워드
fun receiveFruits4(fruits: Array<in Fruit>) {
//    fruits[0].printClass() //error: unresolved reference: printClass

    // 값을 설정할 수 있게 만들고, 읽을 수는 없게 만든다
    fruits[0] = Banana()
}

receiveFruits4(arrayOf<Any>(Banana())) // OK

fun <T: Runnable> run(runable: T) {
    runable.run()
}

fun <T> runAndClose(runable: T) where T: Runnable, T: Closeable {
    runable.run()
    runable.close()
}

// 스타 프로젝션 https://kotlinlang.org/docs/generics.html#star-projections
fun ex(values: Array<*>) {
    for (value in values) {
        println(value)
    }
}
ex(arrayOf("1", "2"))
ex(arrayOf(1, 2))

abstract class Book(val name: String) {
    override fun toString(): String {
        return this.name
    }
}
class Fiction(name: String) : Book(name)
class NonFiction(name: String) : Book(name)

/**
 * 구체화된 타입 파라미터 reified type parameter
 *
 * 코틀린도 타입 이레이저의 한계를 다뤄야 한다
 * 실행 시간에 파라미터 타입은 사용할 수 없지만, 코틀린은 파라미터 타입이 reified 라고 마크되어 있고 함수가 inline 으로 선언 되었다면
 * 파라미터 타입을 사용할 수 있도록 권한을 준다
 */
inline fun <reified T: Book> conditionalPrintBook(book: Book) {
    if (book is T) {
        println(book)
    }
}

// 컴파일 타임에 인라인 함수를 확장하면서 타입 T는 확인되는 타입으로 대체된다
conditionalPrintBook<Fiction>(Fiction("fiction")) // print
conditionalPrintBook<Fiction>(NonFiction("non fiction")) // ignore
