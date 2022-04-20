import java.lang.IllegalArgumentException

// 아래와 같이 클래스의 이름 앞에 class 의 키워드만 적어도 클래스 정의가 가능하다
//class Car // OK

// 클래스에 읽기전용 속성 정의
// 코틀린 컴파일러는 생성자를 작성하고, 필드를 정의하고, 해당 필드에 접근하게 해주는 getter 를 추가한다
class Car(val yearOfMake: Int) // OK
//public class Car public constructor(public val yearOfMake: Int) // 위에 작성한 코드와 같은 의미
var car = Car(2019)
println(car.yearOfMake) // 2019


class MutableCar1(val yearOfMake: Int, var color: String = "black")
var mutableCar1 = MutableCar1(2019)
mutableCar1.color = "white"
println("${mutableCar1.yearOfMake}, ${mutableCar1.color}") // 2019, white

/**
 * 필드와 속성
 * 책을 집필한 필자는 코틀린에서 클래스에는 필드가 없다고 이야기하며 속성이라고 표현하고 있다
 */
// car.yearOfMake 를 호출하면 실제로는 car.getYearOfMake() 를 호출한 것이다.
// 코틀린 컴파일러는 JavaBean 컨벤션을 지원한다 -> 바이트 코드에서 확인이 가능하다

class MutableCar2(val yearOfMake: Int, theColor: String) { // 'theColor' is not property
    var fuelLevel = 100
    var color = theColor
        // https://kotlinlang.org/docs/properties.html#getters-and-setters
        // 커스텀 getter 혹은 setter 를 가진 필드를 정의하고 field 키워드를 사용하여 속성에 참조하는 경우에 백킹 필드(backing field)가 생성된다
        set (value) {
            println("called setter")
            if (value.isBlank()) {
                throw IllegalArgumentException("no empty, please")
            }
            field = value // 'field' is special keyword
            // color = value // ERROR StackOverflow: Using actual name 'color' would make setter recursive
        }
}

var mutableCar2 = MutableCar2(2019, "")
mutableCar2.fuelLevel--
println(mutableCar2.fuelLevel) // 99
//mutableCar2.theColor // error: unresolved reference: theColor
//mutableCar2.color = "" // throw IllegalArgumentException

/**
 * 접근 제어자
 * 코틀린에서 클래스의 속성과 메소드는 public 이 기본이다
 * 코틀린에는 public, private, protected, internal 네 개의 접근 제어자가 있다.
 * public, private 은 Java 와 동일하다
 * protected 는 파생 클래스들의 메소드가 속성에 접근할 수 있는 권한을 준다
 * internal 은 같은 모듈에 있는 모든 코드에서 속성이나 메소드에 접근이 가능하다 (컴파일 단위)
 * intenral 코틀린 컴파일러에 의해서만 처리되며 바이트 코드 표현이 없으며 실행 시간에 오버헤드가 없다
 *
 * getter 의 접근 권한은 속성의 접근 권한과 동일
 * setter 의 접근 권한은 개발자가 설정 가능
 */

class Ex {
    var fuelLevel = 100
        private set
}

//Ex().fuelLevel = 99 // error: cannot assign to 'fuelLevel': the setter is private in 'Ex'

/**
 * 생성자
 *
 * 주 생성자는 첫 번째 줄에 나타난다
 * 생성자의 파라미터로 전달되지 않는 속성들은 클래스 내부에서 정의된다
 *
 * 초기화 코드가 복잡할 경우 생성자용 바디를 만들 필요가 있다 -> 코틀린은 생성자용 바디를 만들기 위한 특별한 공간을 제공한다
 *     -> 클래스는 0개 이상의 init 블록을 가질 수 있다.
 *     -> 이 블록들은 주 생성자의 실행의 한 부분으로써 실행된다.
 *     -> init 블록의 코드는 top - down 으로 순차적으로 실행된다
 *     -> 주 생성자에 선언된 속성과 파라미터는 클래스 전체에서 사용 가능하기 때문에 init 블록 어디에서든 사용 가능
 *     -> 클래스 내부에서 선언된 속성은 init 블록을 속성 선언 아래에 위치시켜야 함
 */

class Ex2 {
    var fuelLevel = 100
        private set

    init {
        fuelLevel = 50
    }
}

println(Ex2().fuelLevel) // 50

/**
 * 보조 생성자(secondary constructor)
 *
 * 보조 생성자는 주 생성자를 호출하거나, 다른 보조 생성자를 호출해야만 한다.
 * 보조 생성자의 파라미터에는 val 이나 var 를 사용할 수 없다
 * 보조 생성자에서는 속성을 선언할 수 없다. -> 속성 정의는 주 생성자와 클래스 내부에서만 가능
 */
class Ex3() {

    constructor(arg: String): this() {
        println("secondary constructor arg is '$arg'")
    }

    init {
        // 앞서 배운 것 처럼 init 블럭은 주 생성자에서 실행되기 때문에 보조 생성자보다 먼저 실행된다
        println("init block ")
    }
}

Ex3("a")

/**
 * 인라인 클래스(inline class)
 *
 * 책을 집필할 시점에는 실험적 기능으로 설명 -> kotlin 1.5.0 에서 stable 버전 release (https://youtrack.jetbrains.com/issue/KT-42434)
 * 책에서는 inline 키워드를 소개 하였음 (kotlin 공식에서 deprecated 됨을 확인)
 *
 * 속성과 메소드를 가질 수 있다
 * 인터페이스를 구현할 수 있다
 * final 이 되어야 하므로, 다른 클래스로 확장할 수 없다
 * mutable 디자인이 허용되지 않는다
 *
 * 인라인 함수처럼 오버헤드를 줄일 수 있다
 *
 * https://kotlinlang.org/docs/inline-classes.html
 */

//value class SSN(var id: String) // error: value class primary constructor must only have final read-only (val) property parameter
//inline class SSN(val id: String) // warning: 'inline' modifier is deprecated. Use 'value' instead

@JvmInline
value class SSN(val id: String)
fun receiveSSN(ssn: SSN) {
    println("Received $ssn")
}
receiveSSN(SSN("111-11-1111"))
