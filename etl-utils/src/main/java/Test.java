
public class Test {

  public static void main(String[] args) {
    String x = "CONV_AP009|1000|0028000300|0001|CDPR|||";
    
    int len = x.split("\\|", -1).length;
    
    System.out.println(len);
    

  }

}
