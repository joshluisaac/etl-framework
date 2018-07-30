package com.powerapps.services;

/**
 * Interface that defines common SFTP operations.
 *
 * @author Josh Nwankwo
 */

import java.util.List;

import com.kollect.etl.service.exception.EtlSftpConnectionException;
import com.kollect.etl.service.exception.EtlSftpException;

public interface ISftpService {

  public void validateConnection() throws EtlSftpConnectionException;

  public void connect( String username, String password, String server, int port );

  public void disconnect();

  public void uploadFile( String srcFile, String dstFile, final String dstDir ) throws EtlSftpConnectionException;

  public void downloadFile( String src, String dst, String dstDir ) throws EtlSftpConnectionException;

  public void createDir( String dirPath, String dstDir ) throws EtlSftpException, EtlSftpConnectionException;

  public List<?> getDirContents( String dirPath ) throws EtlSftpConnectionException;

  public void changeDir( String dirPath ) throws EtlSftpConnectionException;
  
  public void echoTest(String userName, int port, String password);

}
