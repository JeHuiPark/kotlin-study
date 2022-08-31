import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.reflect.KProperty

interface Worker {
    fun work()
    fun takeVacation()
}

class JavaProgrammer : Worker {
    override fun work() {
        println("write java")
    }

    override fun takeVacation() {
        println("rest")
    }
}

class CSharpProgrammer : Worker {
    override fun work() {
        println("write c#")
    }

    override fun takeVacation() {
        println("rest")
    }
}

/**
 * 다중 델리게이션을 허용한다
 *
 * 만약 메소드 명이 충돌할 경우에는 개발자가 충돌할 수 있도록 코틀린이 가이드한다
 */
class Manager(val staff: Worker) : Worker by staff, Runnable by object : Runnable {
    override fun run() {
        // 델리게이션으로 전달된 anonymous object 내부에서는 델리게이션 속성에 접근이 가능하다
        staff.work()
        staff.takeVacation()
    }
} {

    override fun takeVacation() {
        println("나도 쉬고 너도 쉬고")
        staff.takeVacation()
    }
}

val manager = Manager(JavaProgrammer())
manager.run();

/**
 * 주의 : 델리게이션 속성과 객체 속성은 따로 관리
 */
class Manager2(var staff: Worker) : Worker by staff, Runnable by object : Runnable {
    override fun run() {
        staff.work()
        staff.takeVacation()
    }
}

var manager2 = Manager2(JavaProgrammer())
manager2.run() // write java ..
manager2.staff = CSharpProgrammer() // manager2 의 백킹 필드(staff)만 변경한다, 델리게이션 참조는 변경하지 않는다
manager2.run() // write java ..
println(manager2.staff.javaClass) // class Delegation$CSharpProgrammer

/**
 * 변수 델리게이션
 *
 * 아래 인터페이스를 참고
 * @see kotlin.properties.ReadOnlyProperty
 * @see kotlin.properties.ReadWriteProperty
 *
 * getValue(), setValue() 메소드가 위의 상징적인 인터페이스와 동일한 역할을 함
 */
class PoliteString(var content: String) {

    // operator 표기로 작성되어 특정 연산자에서 실행을 재정의한다 (대입 연산에서 right value)
    operator fun getValue(thisRef: Any?, property: KProperty<*>) =
        content.replace("stupid", "s*****")

    // operator 표기로 작성되어 특정 연산자에서 실행을 재정의한다 (대입 연산에서 left value)
    operator fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        content = value
    }
}

// by 뒤에 델리게이션 참조를 정의한다
var comment: String by PoliteString("Some nice mssage")
comment = "This is stupid"
println(comment) // This is s*****
println("comment is of length : ${comment.length}")

/**
 * 델리게이션은 getValue()를 구현하는, 그리고 getValue()와 setValue()를 모두 사용하는 속성이라면 어떤 객체에서든 사용 가능하다
 */

class StringByMap(val map: MutableMap<String, Any>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) =
        (map[property.name] as? String)?.replace("stupid", "s*****") ?: ""

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        map[property.name] = value
    }
}

var map = mutableMapOf<String, Any>("key1" to "value", "key2" to "stupid")
val key1: String by StringByMap(map)
var key2: String by StringByMap(map)
println(key1) // value
println(key2) // s*****
key2 = "nice message"
println(map["key2"]) // nice message

/**
 * 코틀린 스탠다드 라이브러리 디자인을 보면 Map과 MutableMap 은 델리게이션을 사용할 수 있다
 * 위에서 이야기한 델리게이션을 지원하기 위한 인터페이스가 구현되어 있기 때문에 가능
 */
class PostComment(dataSource: MutableMap<String, Any>) {
    val title: String by dataSource // 코틀린 빌트인 스탠다드
    var likes: Int by dataSource // 코틀린 빌트인 스탠다드
    val comment: String by StringByMap(dataSource) // 커스텀

    override fun toString() = "Title: $title Likes: $likes Comment: $comment"
}
val data = listOf(
    mutableMapOf<String, Any>(
        "title" to "Using Delegation",
        "likes" to 2,
        "comment" to "Keep it simple, stupid"
    ),
    mutableMapOf<String, Any>(
        "title" to "Using Inheritance",
        "likes" to 1,
        "comment" to "Prefer Delegation where possible"
    )
)
val forPost1 = PostComment(data[0])
val forPost2 = PostComment(data[1])
forPost1.likes++
println(forPost1) // Using Delegation Likes: 3 Comment: Keep it simple, s*****
println(forPost2) // Using Inheritance Likes: 1 Comment: Prefer Delegation where possible

fun getTemperature(): Int {
    println("fetch temperature")
    return 1
}

// 지연 델리게이션 lazy deleagtaion
fun lazyEvalEx(bool: Boolean) {
    val temperature by lazy { getTemperature() }
    println("lazy init")
    if (bool && temperature == 1) {
        println("something")
    }
    println("finally")
}
lazyEvalEx(false) // lazy init ... finally
lazyEvalEx(true) // lazy init ... fetch temperature ... something ... finally

// 옵저버블 델리게이션 observable delegation
var count by observable(0) { prop, old, new ->
    println("property: $prop, old: $old, new:$new")
}
count++ // property: var Delegation.count: kotlin.Int, old: 0, new:1
count-- // property: var Delegation.count: kotlin.Int, old: 1, new:0

// 데이터 변경 불/허 제어
var counter by vetoable(0) { _, old, new -> new > old }
counter++
println("The value of count is : $counter") // The value of count is : 1
counter--
println("The value of count is : $counter") // The value of count is : 1
