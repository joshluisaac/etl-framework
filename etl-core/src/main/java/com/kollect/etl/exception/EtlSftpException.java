package com.kollect.etl.exception;

public class EtlSftpException extends Exception {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public EtlSftpException() {
    super();
  }

  public EtlSftpException(String message) {
    super(message);
  }

  public EtlSftpException(String message, Throwable cause) {
    super(message, cause);
  }

  public EtlSftpException(Throwable cause) {
    super(cause);
  }
  
  

}
