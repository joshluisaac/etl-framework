package com.kollect.etl.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import com.kollect.etl.exception.EtlSftpConnectionException;
import com.kollect.etl.services.SftpClient;

import java.io.File;



public class SftpClientTest {
  
  private SftpClient testClient;
  private String localDstDir = "../out/";
  private String serverDstDir = "/home/joshua/martian/ptrworkspace/etl_YYC/sftp_out/";
  
  @Before
  public void setUp(){
    testClient = new SftpClient("localhost", 22, "joshua", "234vivalee", serverDstDir);
  }
  
  //@Test
  public void testing_download_of_file_from_server_to_client() throws EtlSftpConnectionException{
    testClient.connect();
    testClient.downloadFile("x.txt", "y.txt");
    testClient.disconnect();
    Assert.assertTrue(new File("../out/y.txt").exists());
  }
  
  //@Test
  public void testing_upload_of_file_from_client_to_server() throws EtlSftpConnectionException{
    testClient.connect();
    testClient.uploadFile(localDstDir + "CREDIT_NOTE_AED_ELEGANT.csv", "CREDIT_NOTE_AED_ELEGANT.csv","");
    testClient.disconnect();
    Assert.assertTrue(new File(serverDstDir + "CREDIT_NOTE_AED_ELEGANT.csv").exists());
  }
  //@Test
  public void list_directory_contents() throws EtlSftpConnectionException{
    testClient.connect();
    testClient.changeDir(serverDstDir);
    testClient.getDirContents("2017-08-21");
    testClient.disconnect();
  }
  

}
