import java.io.File;
import java.io.IOException;
import java.util.List;

import com.kollect.etl.util.FileUtils;

public class MetaData2DBTableGenerator {
  
  
  public static void main(String[] args) throws IOException {
    
    File file = new File("/media/joshua/martian/kvworkspace/poweretl/etl-utils/salaesOrder.meta");
    
    List<String> list = new FileUtils().readFile(file);
    
    for(int x = 0; x < list.size(); x++) {
      String s = list.get(x);
      //System.out.println(s);
      
      String[] columns = list.get(x).split("\\|");
      
      String row = columns[1] +"  "+ columns[2] +",";
      
      System.out.println(row);
    }
    
  }

}
