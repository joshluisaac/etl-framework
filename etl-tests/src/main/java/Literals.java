
public class Literals {
  
  private double pi = 3.14159D;

  private long getDistance() {
    return -1;
  }

  private class IntegerLiterals {
    byte fileSize = -120;
    short fossilAge = 350;
    int mileageAsDecimal = 1789;
    int mileageAsHex = 0x900F; // mileage expressed as hex integer literal
    long distance = -160000000090L;
    
    double x = pi * mileageAsDecimal;

  }

  private class FloatingPointLiterals {

  }

  private class BooleanLiterals {

  }

  private class StringLiterals{

  }
  
  private static class MainApp {
    public static void main(String[] args) {
      Literals l = new Literals();
      Literals.MainApp x = new Literals.MainApp();


      System.out.println(Byte.MIN_VALUE);
      System.out.println(Byte.MAX_VALUE);
      System.out.println(Short.MAX_VALUE);
    }
  }


}



