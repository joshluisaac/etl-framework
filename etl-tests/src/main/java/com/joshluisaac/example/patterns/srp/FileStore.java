package com.joshluisaac.example.patterns.srp;

import java.io.File;
import java.text.MessageFormat;

public class FileStore {

  public String workingDirectory;

  public FileStore(String workingDirectory) {
    if (workingDirectory == null)
    // fail-fast if working directory is null
    {
      throw new NullPointerException("working directory is null");
    }
    if (!new File(workingDirectory).isDirectory()) {
      // fail-fast if invalid directory is provided
      throw new NullPointerException(MessageFormat.format(
          "{0} does not represent a working directory. Please supply a valid path to an existing directory",
          workingDirectory));
    }
    this.workingDirectory = workingDirectory;

  }

  public void save( int id, String message ) {
  }

  public String read( int id ) {
    return "X";
  }

  public String getFileName( int id ) {
    return "X";
  }

  public static void main( String[] args ) {

    new FileStore("yyyy");
  }
}
