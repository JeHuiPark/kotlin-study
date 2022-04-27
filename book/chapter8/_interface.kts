interface Remote {
    fun up()
    fun down()

    // 바이트 코드에서 default 메소드로 표기하길 원한다면 @jvmDefault 어노테이션 활용
    fun doubleUp() {
        up()
        up()
    }

    /**
     * 클래스에 static 메소드를 직접 정의할 수 없는 것 처럼 인터페이스도 static 메소드를 인터페이스 안에 직접 만들 수 없다
     */
    companion object {

        // single expression function
        fun combine(first: Remote, second: Remote): Remote = object : Remote {

            // anonymous object ('Remote' Implementation)

            override fun up() {
                first.up()
                second.up()
            }

            override fun down() {
                first.down()
                second.down()
            }
        }
    }
}

class TVRemote() : Remote {

    // Java 와 다르게 override 키워드 강제
    override fun up() {
        println("up")
    }

    override fun down() {
        println("down")
    }
}

val tvRemote: Remote = TVRemote()
tvRemote.doubleUp() // up\nup

val combinedRemote = Remote.combine(TVRemote(), TVRemote())
combinedRemote.down() // down\ndown


