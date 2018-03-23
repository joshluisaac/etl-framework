import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
  
  public boolean hasMatch(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    System.out.println(m.find());
    return m.matches();
  }

  public static void main(String[] args) {
    String x = "CONV_AP009|1000|0028000300|0001|CDPR|||";
    
    int len = x.split("\\|", -1).length;
    
    System.out.println(len);
    
    //\\^3[0-9]
    
    boolean res = new Test().hasMatch("30000000", "\\d{3}");
    System.out.println(res);
    
    

  }

}
