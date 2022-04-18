/**
 * to() 는 코틀린의 모든 객체에서 사용가능
 * to() 는 Pair 이 인스턴스를 만든다
 */
println("k" to "v") // (k, v)
println("k".to("v")) // (k, v)

var pair = "key" to "value"
val map = mapOf("k" to "v", pair)
println(map) // {k=v, key=value}
