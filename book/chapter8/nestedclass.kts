interface Remote {
    fun up()
    fun down()
}

class TV {

    private var volume = 0
    val remote: Remote
        get() = TVRemote()

    override fun toString() = "Volume: $volume"

    /**
     * Java 와 다르게 코틀린의 중첩 클래스는 외부 클래스의 private 멤버에 접근할 수 없다
     * 하지만 중첩 클래스에 inner 키워드를 사용한다면 내부 클래스로 변하면서 제약이 사라진다 (Java 와 반대되는 컨셉)
     *
     * 내부 클래스는 외부 클래스의 private 멤버를 포함한 모든 멤버에 직접 접근 가능
     */
    inner class TVRemote : Remote {

        override fun up() {
            volume++
        }

        override fun down() {
            volume--
        }

        /**
         * 내부 클래스의 속성이나 메소드명이 외부 클래스와 중복될 경우에는 특별한 this 표현식을 이용하여 적절히 처리한다
         *
         * 객체 그래프 상향 탐색이 필요하면 super 키워드 사용
         */
        override fun toString() = "Remote: ${this@TV.toString()}"
    }
}

val tv = TV()
val remote = tv.remote
println("$tv") // Volume: 0
remote.up()
println("After increasing: $tv") // After increasing: Volume: 1
remote.down()
println("After decreasing: $tv") // After down: Volume: 0

println(remote) // Remote: Volume: 0


class TV2 {

    private var volume = 0
    val remote: Remote
        get() = object: Remote {

            // Anonymous inner class

            override fun up() {
                volume++
            }
            override fun down() {
                volume--
            }

            override fun toString() = "Remote: ${this@TV2.toString()}"
        }
    override fun toString() = "Volume: $volume"
}

/**
 * 코틀린은 개발자의 의도가 클래스에 명확하게 전달되도록 만든다
 * 코틀린은 클래스가 의도치 않게 베이스 클래스가 되는 것을 원하지 않는다
 * 개발자는 작성하는 클래스가 베이스 클래스로 사용되는 것을 허용하려면 명시적으로 권한을 제공해야 한다
 *    -> Java 와 다르게 기본이 final
 *    -> open 키워드를 이용하여 상속 허용여부를 명시적으로 선언한다
 *    -> 클래스의 속성, 생성자에 정의된 속성 모두 오버라이드 가능
 *    -> val -> var|var 오버라이드 가능
 *    -> var -> var 오버라이드 가능
 *        -> 상위 클래스에서 이미 setter 가 정의 되었기 때문에 var -> val 재정의는 불가능
 *    -> private, protected 멤버를 서브 클래스에서 public 으로 변경하는 것을 허용한다
 *        -> val,var 와 마찬가지로 public 멤버의 제약사항을 좁히는 것은 불가능하다
 */

open class Vehicle(val year: Int, open var color: String) {
    open val km = 0
    final override fun toString() = "Year: $year, Color: $color, KM: $km"
    fun repaint(newColor: String) {
        color = newColor
    }
}

open class Car(year: Int, color: String) : Vehicle(year, color) {
    override var km: Int = 0 // km 속성을 재정의 함
        set(value) {
            if (value < 1) {
                throw IllegalArgumentException("can't set negative value")
            }
            field = value
        }

    fun drive(distance: Int) {
        // Vehicle 의 km 속성이 아닌 Car 의 km 속성을 변경
        km += distance
    }

    fun printInstanceDetailState() {
        // 이것이 가능한 원리는 바이트 코드를 보면 확인이 가능하겠지만 귀찮으니 이렇게 확인
        // 접근제어 오버라이드도 비슷한 원리일 것이다 한계점도 유사
        println("Vehicle 의 km = ${super.km} Car 의 km = $km")
    }
}

val car = Car(2019, "Orange")
println(car) // Year: 2019, Color: Orange, KM: 0
car.drive(80)
println(car) // Year: 2019, Color: Orange, KM: 80
car.printInstanceDetailState() // Vehicle 의 km = 0 Car 의 km = 80

class FamilyCar(year: Int, color: String) : Car(year, color) {
    override var color: String
        get() = super.color // 베이스 클래스의 color getter 메서드를 이용하여 처리
        set(value) {
            if (value.isEmpty()) {
                throw IllegalArgumentException("Color required")
            }
            // FamilyCar 는 color 의 값을 저장하지 않는다
            // 베이스 클래스의 color setter 메서드를 이용하여 처리
            super.color = color
        }
}

val familyCar = FamilyCar(2019, "Green")
println(familyCar.color) // Green
try {
    familyCar.repaint("")
} catch (expected: IllegalArgumentException) {
}
