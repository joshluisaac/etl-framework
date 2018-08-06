package com.powerapps.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.service.exception.EtlSftpConnectionException;

/**
 * @author Josh Nwankwo
 */

public class Sftp {
  
  ISftpService service;
  private String server;
  private int port;
  private String username;
  private String password;
  private String dstDir;

  public Sftp(ISftpService service, String server, int port, String username, String password, String dstDir) {
    this.service = service;
    this.server = server;
    this.port = port;
    this.username = username;
    this.password = password;
    this.dstDir = dstDir;
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(Sftp.class);

  /**
   * Uploads a list of files in a directory to some remote directory
   * 
   * <p>
   * @param sftp
   *         An instance of {@link SftpServiceProviderImpl}
   *
   * @param fileList
   *        The list of files in outDir, which is the source directory
   * 
   * @param srcDir
   *        Source directory, which is the relative or absolute path to source files
   * 
   * @param finalFtpDst
   *        Destination directory, which is the absolute path to destination folder on the server
   *        
   * @throws EtlSftpConnectionException
   *        
   */
  public void execute(List<String> fileList, String srcDir, String finalFtpDst) throws EtlSftpConnectionException{
    try {
      service.connect(getUsername(), getPassword(), getServer(), getPort());
      for (int i = 0; i < fileList.size(); i++) {
        final String fileName = fileList.get(i);
        LOG.info("SFTP transferring {}", new Object[] { fileName });
        service.uploadFile(srcDir + "/" + fileName, fileName, finalFtpDst);
      }
    }
    
    finally {
      service.disconnect();
    }
  }
  
  
  /**
   * @return the server
   */
  public String getServer() {
    return server;
  }

  /**
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @return the dstDir
   */
  public String getDstDir() {
    return dstDir;
  }

}
