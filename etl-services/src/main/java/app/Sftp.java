package app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.service.exception.EtlSftpConnectionException;
import com.kollect.etl.services.ISftpServiceProvider;

/**
 * @author Josh Nwankwo
 */

public class Sftp {
  
  private String server;
  private int port;
  private String username;
  private String password;
  private String dstDir;

  public Sftp(String server, int port, String username, String password, String dstDir) {
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
  public void execute(ISftpServiceProvider sftp, List<String> fileList, String srcDir, String finalFtpDst) throws EtlSftpConnectionException{
    try {
      sftp.connect(getUsername(), getPassword(), getServer(), getPort());
      for (int i = 0; i < fileList.size(); i++) {
        final String fileName = fileList.get(i);
        LOG.info("SFTP transferring {}", new Object[] { fileName });
        sftp.uploadFile(srcDir + "/" + fileName, fileName, finalFtpDst);
      }
    }
    
    finally {
      sftp.disconnect();
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
