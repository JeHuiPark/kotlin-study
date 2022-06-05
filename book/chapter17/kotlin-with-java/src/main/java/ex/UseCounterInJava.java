package ex;

import java.io.FileNotFoundException;

public class UseCounterInJava {
  public static void main(String[] args) {
    Counter counter = new Counter(1);
//    System.out.println(counter + counter); // Java 는 연산자 오버로딩을 허용하지 않는다
    System.out.println(counter.plus(counter));
    System.out.println(Counter.create1());
//    System.out.println(Counter.create2()); // Java 에서는 불가능
    System.out.println(Counter.Companion.create2());
    System.out.println(counter.map(e -> e.plus(e)));

    try {
      counter.readFile("not_found"); // 바이트 코드로 생성된 readFile() 메소드는 throws절로 마크되지 않았다
//    } catch (FileNotFoundException e) { //java: exception java.io.FileNotFoundException is never thrown in body of corresponding try statement
    } catch (Exception e) {
      System.out.println("file not found, exception type is " + e.getClass());
    }

    try {
      counter.readFile_markedThrows("not_found");
    } catch (FileNotFoundException e) {
      System.out.println("file not found");
    }

    counter.add(3); // ok
//    counter.add(); // error
    counter.add_javaCompatible(3); // ok
    counter.add_javaCompatible(); // ok

    CounterKt.createCounter();
  }
}
