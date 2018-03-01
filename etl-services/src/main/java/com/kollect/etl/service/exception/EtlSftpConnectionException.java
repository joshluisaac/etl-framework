package com.kollect.etl.service.exception;

public class EtlSftpConnectionException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public EtlSftpConnectionException() {
    super();
  }

  public EtlSftpConnectionException(String message) {
    super(message);
  }

  public EtlSftpConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public EtlSftpConnectionException(Throwable cause) {
    super(cause);
  }

}
