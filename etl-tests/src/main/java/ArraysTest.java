

public class ArraysTest {

  private class Person {

    String name;
    Integer age;
    Long income = 290_897L;

    private void printIncome(){
      System.out.println(Long.toHexString(income));
      System.out.println(Long.toBinaryString(income));
      System.out.println(Long.toOctalString(income));
    }

  }

  Person p = new Person();

  public static void main( String[] args ) {
    int[] x = new int[4];

    x[0] = 1;
    x[1] = 2;
    x[3] = 3;

    System.out.println(java.util.Arrays.toString(x));


    String s1 = "Hello";
    String s2 = "Hello";

    System.out.println(s1==s2);
    //Person p = new Person();
    



  }

}


