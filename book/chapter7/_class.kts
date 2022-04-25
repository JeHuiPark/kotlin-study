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

/**
 * 컴패니언 객체
 *
 * 캠피니언 객체는 클래스 안에 정의한 싱글톤이다
 * 인터페이스 구현이 가능
 * 다른 클래스 확징이 가능
 */

// 클래스 레벨의 속성과 메소드가 필요할 경우 컴패니언 객체를 이용하여 구현할 수 있다
class MachineOperator(val name: String) {

    fun checkin() = checkedIn++
    fun checkout() = checkedIn--

    companion object {
        // 클래스 레벨 속성
        var checkedIn = 0

        // 클래스 레벨 메소드
        fun minimumBreak() = "15 minutes every 2 hours"
    }
}

MachineOperator("Master").checkin()
println(MachineOperator.minimumBreak())
println(MachineOperator.checkedIn)

// 인스턴스 참조에선 companion 객체에 접근할 수 없다
//MachinOperator("Master").minimumBreak() // error: unresolved reference: minimumBreak

// 컴패니언 객체 접근
println(MachineOperator.Companion::class)

class CompanionNamingExample {

    companion object CompanionObjectName
}

println(CompanionNamingExample.CompanionObjectName::class)

class MachineOperator2 private constructor(val name: String) {

    companion object Factory {

        fun create(name: String): MachineOperator2 {
            val instance = MachineOperator2(name)
            // ...
            return instance
        }
    }
}

// 클래스 이름을 이용해서 컴패니언 객체에 접근하는 것을 보면 create 메서드가 static 멤버로 보일 수도 있지만, static 메소드는 아니다
// 컴패니언 객체의 멤버에 접근하면 코틀린 컴파일러는 싱글톤 객체로 라우팅을 한다
// 이러한 점은 Java 와의 상호 운용성 측면에서 문제가 될 수 있기 때문에 static 메서드를 기대하는 경우에는 약속된 annotation 을 이용하면 된다 (17 챕터)
var machineOperator2 = MachineOperator2.create("machine")

/**
 * 데이터 클래스
 *
 * 행동, 동작보다는 데이터를 옮기는 데 특화된 클래스
 * 주 생성자를 이용하여 최소 한 개 이상의 속성을 정의해야 함
 * val, var 가 아닌 파라미터는 사용할 수 없다
 * 클래스 바디에 속성이나 메소드 추가 가능
 * equals(), hashcode(), toString() 메소드를 코틀린이 만들어줌
 * 셀렉트 속성의 업데이트된 값을 제공하면서 인스턴스를 복사해 주는 copy() 메소드 제공 (쉘로우 카피)
 * 주 생성자에 의해 정의된 속성에 접근할 수 있게 해주는 componentN() 메소드 제공 (componentN 메소드의 주된 목적은 구조분해)
 *     -> 속성정의 순서대로 1부터 순차적으로 증가하는 정수를 메소드 접미사로 추가 함
 *         -> component1(), component2(), component3() ...
 */

data class Task(val id: Int, val name: String, val completed: Boolean, val assigned: Boolean)
val task1 = Task(1, "name", false, true)
println(task1) // Task(id=1, name=name, completed=false, assigned=true)

// copy
var task1Completed = task1.copy(completed = true, assigned = false)
println(task1Completed) // Task(id=1, name=name, completed=true, assigned=false)

// 구조분해 (destructuring)
// task1 의 id 값을 획득하기 위해 component1() 메소드를 실행한다
// task1 의 assigned 값을 획득하기 위해 component4() 메소드를 실행한다
// -> 속성의 순서를 기반으로 데이터를 추출하기 때문에 주의할 필요가 있다
val (id, _, _, isAssigned) = task1
println("Id: $id Assigned: $isAssigned") // Id: 1 Assigned: true

data class DataClassOverrideEx(val id: String, val name: String) {

    override fun equals(other: Any?): Boolean {
        return when {
            !(other is DataClassOverrideEx) -> false
            id == other.id -> true
            else -> false
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
val aDataClassOverrideEx = DataClassOverrideEx("id", "before-name")
println(aDataClassOverrideEx == aDataClassOverrideEx.copy(name = "after-name")) // true
