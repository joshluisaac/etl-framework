package com.kollect.etl.util.exceptions;

public class DirectoryNotFoundException extends Exception {
  
  private static final long serialVersionUID = 1L;
  
  public DirectoryNotFoundException() {
    super();
  }
  
  public DirectoryNotFoundException(String message) {
    super(message);
  }
  
  public DirectoryNotFoundException(Throwable cause) {
    super(cause);
  }
  
  public DirectoryNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
