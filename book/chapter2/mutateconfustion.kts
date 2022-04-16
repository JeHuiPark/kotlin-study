var factor = 2
fun doubleIt(n: Int) = n * factor
val message = "The factor is $factor"
factor = 0
println(message)
println(doubleIt(2))
