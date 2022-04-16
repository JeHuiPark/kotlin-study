var mutablVariable = 10
val immutableVariable = 10

mutablVariable = 20
// immutableVariable = 30  error: val cannot be reassigned

println(mutablVariable)
println(immutableVariable)
