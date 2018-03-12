package com.kollect.etl.util;


//does do anything other than call FileAppender. This is because this is used in production otherwise it should be deleted.

public class FileConcatenator extends AbstractTextFileProcessor {

  public FileConcatenator(Configuration config) {
    super(config);
  }

  
  public static void main(String[] args) throws Exception {
    FileAppender.main(args);

  }

}
