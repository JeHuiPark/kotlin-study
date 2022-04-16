val price = 12.25
val taxRate = 0.08
val output = "The amount $price after tax comes to $${price * (1 + taxRate)}"
val disclaimer = "The amount is in US$, that's right in \$only"

println(output) // The amount 12.25 after tax comes to $13.23
println(disclaimer) // The amount is in US$, that's right in $only
