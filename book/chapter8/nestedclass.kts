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
