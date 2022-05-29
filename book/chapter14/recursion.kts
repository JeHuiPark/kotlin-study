import java.math.BigInteger

fun factorialRec(n: Int): BigInteger =
    if (n <= 0) 1.toBigInteger() else n.toBigInteger() * factorialRec(n - 1)
factorialRec(5)

//factorialRec(50000) // java.lang.StackOverflowError

// 꼬리호출 최적화 (tail call optimization)
tailrec fun factorial(n: Int, result: BigInteger = 1.toBigInteger()): BigInteger =
    if (n <= 0) result else factorial(n - 1, result * n.toBigInteger())
// 런타임에 반복문으로 변경된다 (꼬리호출 최적화 조건을 만족하지 못하면 컴파일 경고가 발생한다)
factorial(50000)
