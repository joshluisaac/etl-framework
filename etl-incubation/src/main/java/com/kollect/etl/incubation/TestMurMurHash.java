package com.kollect.etl.incubation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.util.FileUtils;

import ie.ucd.murmur.MurmurHash;

public class TestMurMurHash {

  static final Logger LOG = LoggerFactory.getLogger(TestMurMurHash.class);

  String file1 = "/home/joshua/martian/_info_tech/datasets/Crimes_-_2001_to_present.csv";
  String file2 = "/home/joshua/martian/_info_tech/datasets/Crimes_-_2001_to_present.csv";
  String dest = "/home/joshua/martian/_info_tech/datasets/";
  String sampleFile = "/home/joshua/desktop/joshua_sample.txt";

  public static void main(String[] args) throws Exception {
    
    TestMurMurHash t = new TestMurMurHash();
    long startEpoch = System.currentTimeMillis();
    //t.convertToHash();
    LOG.debug("{} lines", t.readBytes1());
    float diff = (System.currentTimeMillis() - startEpoch)/1000.0F;
    float diff2 = (float) (System.currentTimeMillis() - startEpoch)/1000;
    LOG.debug("{} sec", diff);
    LOG.debug("{} sec", diff2);
  }
  
  
  public void convertToHash() {
    long hashVal1 = MurmurHash.hash64("joshua" + "\n");
    long hashval2 = MurmurHash.hash64(new byte[] { 106, 111, 115, 104, 117, 97, 10 }, 7);
    long hashVal3 = MurmurHash.hash64("2926945,HJ610417,09/05/2003 10:30:00 PM,049XX N MILWAUKEE AVE,0320,ROBBERY,STRONGARM - NO WEAPON,STREET,false,false,1623,016,45,11,03,1139425,1932215,2003,04/15/2016 08:55:02 AM,41.970110743,-87.762711958,\"(41.970110743, -87.762711958)\"" + "\n");
    LOG.debug("{}", hashVal1);
    LOG.debug("{}", hashval2);
    LOG.debug("{}", hashVal3);
    
  }

  public int readBytes() throws IOException, InterruptedException {
    byte[] byteArray = new byte[400];
    int maxByteAllocationSize = 100;
    byte[] bytesRead = new byte[maxByteAllocationSize];
    int actualBytesRead = 0;
    int counter = 0;
    try (BufferedInputStream buff = new BufferedInputStream(
        new FileInputStream(new File(dest, "sample.txt")));) {
      while ((actualBytesRead = buff.read(bytesRead)) != -1) {
        System.out.println("Read chunk");
        Thread.sleep(5000);
        int srcPos = 0;
        for (int i = 0; i < actualBytesRead; i++) {
          //System.out.println(i + " | " + bytesRead[i] +  " | " + srcPos);
          
          if (bytesRead[i] == 10) {
            int lineByteLength = (i - srcPos) + 1;
            System.out.println(i + " Copying.." + lineByteLength + " bytes");
            //System.arraycopy(bytesRead, srcPos, byteArray, 0, lineByteLength);
            
            srcPos = i + 1; //move the current src position by index + 1
            
            long hashVal = MurmurHash.hash64(byteArray, lineByteLength);
            //LOG.debug("{}", hashVal);
            //System.out.println(Arrays.toString(byteArray));
            //System.out.println("--new line--");
         // increment line counter
            counter = counter + 1;
          }
        }
      }
    }
    return counter;
  }


    public int readBytes1 () throws IOException, InterruptedException {
    byte[] byteArray = new byte[400];
    int maxByteAllocationSize = 10;
    byte[] bytesRead = new byte[maxByteAllocationSize];
    int actualBytesRead = 0;
    int counter = 0;
    
    File f = new File(dest, "joshua.txt");
    int fileLen = (int)f.length();
    int bytesLeft = 0;
    int indexOfLastLf = 0;
    

    try (BufferedInputStream buff = new BufferedInputStream(
        new FileInputStream(f));) {
      while ((actualBytesRead = buff.read(bytesRead)) != -1) {
        
        bytesLeft = (bytesLeft == 0) ? (fileLen - actualBytesRead) : (bytesLeft - actualBytesRead);
        
        LOG.debug("Read a chunk of {} from a total of {} and {} remaining bytes",actualBytesRead, fileLen, bytesLeft);
        Thread.sleep(2000);
        int srcPos = 0;
        
        int indexOfLastByte = actualBytesRead - 1;
        int valOfLastByte = bytesRead[indexOfLastByte];
        System.out.println(valOfLastByte);
        
        
        if(valOfLastByte != 10) {
          for (int i = 0; i < actualBytesRead; i++) {
            
            if (bytesRead[i] == 10) {
              indexOfLastLf = i;
            }
          }
          System.out.println(indexOfLastLf);
        }
        
        
        
      }
    }
    return 0;
  }
  
  
  
  

}