package ex;

public class Util {
  public double f2c(double fahrenheit) {
    return (fahrenheit - new Constants().getFreezingPointInF()) * 5 / 9.0;
  }
}