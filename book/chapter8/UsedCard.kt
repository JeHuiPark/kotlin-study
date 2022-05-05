/**
 * sealed class 의 인스턴스를 when 표현식으로 처리할 때 else 처리(anti pattern)는 지양 하도록 하자
 * (sealed class 의 자식 클래스가 새로 추가될 경우에 컴파일 타임에 알아채지 못하는 불상사를 방지하기 위한 구현패턴 정도로 이해)
 */
fun process(card: Card) = when (card) {
    is Ace -> "${card.javaClass.name} of ${card.suit}"
    is King, is Queen, is Jack -> "$card"
    is Pip -> "${card.number} of ${card.suit}"
}

// $ kotlinc-jvm Card.kt UsedCard.kt -d UsedCard.jar
// $ kotlin -classpath UsedCard.jar UsedCardKt
fun main() {
    println(process(Ace("Diamond")))
    println(process(Queen("Clubs")))
    println(process(Pip("Spades", 2)))
    println(process(Pip("Hearts", 6)))
}
