class Car(val yearOfMake: Int, var color: String = "black")

// $ kotlinc-jvm Car.kt
// $ javap -p Car.class

//Compiled from "Car.kt"
//public final class Car {
//    private final int yearOfMake;
//    private java.lang.String color;
//    public Car(int, java.lang.String);
//    public Car(int, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker);
//    public final int getYearOfMake();
//    public final java.lang.String getColor();
//    public final void setColor(java.lang.String);
//}

// 코틀린 컴파일러가 바이트 코드를 만들면서 속성을 위한 두 개의 백킹 필드, 생성자, 두 개의 getter, 한 개의 setter 를 만들었다
