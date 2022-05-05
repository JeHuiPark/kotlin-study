import java.lang.RuntimeException

/**
 * sealed class 는 동일한 파일에 작성된 다른 클래스들에 확장이 허용되지만 그 외의 클래스들은 확장할 수 없다
 *
 * sealed class 의 생성자는 private 이 표기되지 않았지만 private 으로 취급 됨
 */
sealed class Card(val suit: String)
class Ace(suit: String) : Card(suit)
class King(suit: String) : Card(suit) {
    override fun toString() = "King of $suit"
}
class Queen(suit: String) : Card(suit) {
    override fun toString() = "Queen of $suit"
}
class Jack(suit: String) : Card(suit) {
    override fun toString() = "Jack of $suit"
}
class Pip(suit: String, val number: Int) : Card(suit) {
    init {
        if (number < 2 || number > 10) {
            throw RuntimeException("Pip has to be between 2 and 10")
        }
    }
}


