val nameArray = arrayOf("A", "B", "C")
for (index in nameArray.indices) {
    println("Position of ${nameArray.get(index)} is $index")
}

val nameList = listOf("A", "B", "C")
for (index in nameList.indices) {
    println("Position of ${nameList.get(index)} is $index")
}
