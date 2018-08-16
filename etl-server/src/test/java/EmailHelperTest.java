/*
public class EmailHelperTest {
  
  
  private EmailHelper emailHelper;
  private ComponentProvider comProvider;
  String dir = "/home/joshua/Desktop/out";
  private String cacheFilePath = "/media/joshua/martian/kvworkspace/PowerETL/poweretl-resources/resources/manifestCache.log";
  
  
  public EmailHelperTest() {
    comProvider = new ComponentProvider();
    emailHelper = new EmailHelper(comProvider,cacheFilePath);
  }

  @Test
  public void aggregateLogFiles_Test() throws IOException {
    List<String> prefixes = new ArrayList<>(Arrays.asList("stats_manifest_pelita_"));
    List<String> diff = emailHelper.fetchNewManifestLog(new File(dir), prefixes);
    System.out.println(diff.toString()); 
  }
  
  @Test
  public void emailAndPresistToCache_Test() throws IOException {
    */
/*Create prefixes*//*

    List<String> prefixes = new ArrayList<>(Arrays.asList("stats_manifest_pelita_"));
    
    */
/*Resolve differences*//*

    List<String> diff = emailHelper.fetchNewManifestLog(new File(dir), prefixes);
    
    //email the contents of the differences
    
    */
/*write difference to cache*//*

    emailHelper.persistToCache(diff);
  }
  
}
*/
