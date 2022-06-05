package ex;

import java.util.List;
import java.util.stream.Collectors;

public class JavaClass {

  public int getZero() {
    return 0;
  }

  public List<String> convertToUpper(List<String> names) {
    return names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
  }

  // 코틀린 예약어 충돌
  public void suspend() {
    System.out.println("suspending...");
  }

  // 코틀린 예약어 충돌
  public String when() {
    return "Now!";
  }
}
