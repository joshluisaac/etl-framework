package com.kollect.etl.util.load.config.xml;

public class DcXml {
  
  public final String fileName;
  public final byte[] rawByte;
  
  public DcXml(String fileName, byte[] rawByte) {
    this.fileName = fileName;
    this.rawByte = rawByte;
  }

  public String getFileName() {
    return fileName;
  }

  public byte[] getRawByte() {
    return rawByte;
  }
  
  

}
