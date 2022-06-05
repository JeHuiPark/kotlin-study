# 조인트 컴파일
Java와 Kotlin을 혼용해서 코드를 사용하는 방법은 두 가지가 있다
- Java 또는 Kotlin으로 작성된 프로젝트에서 Jar 파일 디펜던시를 통해서 코드를 가져오는 법
- Java와 Kotlin으로 작성된 소스 파일을 프로젝트 내에 나란히 가지고 있는 방법

[참조 문서](https://kotlinlang.org/docs/gradle.html)

Kotlin 코드와 Java 코드가 상호 참조를 하고 있는 경우에 Java 코드를 먼저 컴파일한다면 컴파일에 실패할 것이다.
이런 상황에는 Kotlin Compiler를 먼저 실행시킨 후 Java 컴파일러를 실행하면 해결된다 (Java 소스 파일과 Kotlin 소스 파일을 Kotlin Compiler에게 제공해야 한다) 


## Manual Compile
컴파일 매커니즘을 확인하기 위해 직접 컴파일을 진행해본다

```shell
kotlinc-jvm -d classes \
   src/main/kotlin/ex/*.kt \
   src/main/java/ex/*.java
```
`classes/` 디렉토리에 생성된 클래스를 확인해보면 App.class와 Constants.class를 확인할 수 있다  
이때 Kotlin Compiler는 Util 클래스의 스터브를 생성한다. 하지만 디렉토리에 Util 클래스는 보이지 않는다  
컴파일러는 스터브를 이용해서 App에 걸려있는 Util클래스의 디펜던시를 검증한다

Java 에서 참조중인 Kotlin 클래스가 컴파일이 된 상태이니 Java 컴파일을 진행해보자
```shell
javac -d classes -classpath classes \
  src/main/java/ex/*.java
```

코드를 실행해보자
```shell
kotlin -classpath classes ex.App
```
```shell
java -classpath classes:$KOTLIN_PATH/lib/kotlin-stdlib.jar ex.App
```

**실행결과**
```
Running App...
10.0
```