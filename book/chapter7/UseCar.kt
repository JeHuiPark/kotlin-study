fun useCarObject(): Pair<Int, String> {
    val car = Car(2019, "red")
    var year = car.yearOfMake
    car.color = "green"
    val color = car.color
    return year to color
}

// $ kotlinc-jvm Car.kt UseCar.kt
// $ javap -c UseCarKt.class

//Compiled from "UseCar.kt"
//public final class UseCarKt {
//    public static final kotlin.Pair<java.lang.Integer, java.lang.String> useCarObject();
//    Code:
//    0: new           #10                 // class Car
//    3: dup
//    4: sipush        2019
//    7: ldc           #12                 // String red
//    9: invokespecial #16                 // Method Car."<init>":(ILjava/lang/String;)V
//    12: astore_0
//    13: aload_0
//    14: invokevirtual #20                 // Method Car.getYearOfMake:()I
//    17: istore_1
//    18: aload_0
//    19: ldc           #22                 // String green
//    21: invokevirtual #26                 // Method Car.setColor:(Ljava/lang/String;)V
//    24: aload_0
//    25: invokevirtual #30                 // Method Car.getColor:()Ljava/lang/String;
//    28: astore_2
//    29: iload_1
//    30: invokestatic  #36                 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
//    33: aload_2
//    34: invokestatic  #42                 // Method kotlin/TuplesKt.to:(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
//    37: areturn
//}

// 위 바이트 코드를 통해 코틀린에서는 getter, setter 대신 속성의 이름을 이용해서 속성에 접근할 수 있음을 확인할 수 있다
