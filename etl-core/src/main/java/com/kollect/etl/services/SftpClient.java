package com.kollect.etl.services;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.kollect.etl.exception.EtlSftpConnectionException;
import com.kollect.etl.exception.EtlSftpException;

public class SftpClient {
  
  private static final Logger LOG = LoggerFactory.getLogger(SftpClient.class);
  
  private String server;
  private int port;
  private String username;
  private String password;
  private String dstDir;
  
  private JSch jsch = null;
  private Session session = null;
  private Channel channel = null;
  private ChannelSftp channelSftp = null;
  
  public SftpClient(String server, int port, String username, String password, String dstDir){
    this.server = server;
    this.port = port;
    this.username = username;
    this.password = password;
    this.dstDir = dstDir;
  }
  
  private void validateConnection() throws EtlSftpConnectionException {
    if (session==null || channelSftp == null || !session.isConnected() || !channelSftp.isConnected()) {
      throw new EtlSftpConnectionException("Connection to SFTP server is closed. Please open connection and retry");
    }
  }


  public void connect(){
    try{
      jsch = new JSch();
      session = jsch.getSession(username, server, port);
      session.setPassword(password.getBytes(Charset.forName("ISO-8859-1")));
      LOG.debug("Jsch has been initialized");
      
      Properties config = new Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      
      LOG.debug("Connecting to SFTP server {} on port {}. That is, {}:{} ", new Object[]{server,port,server,port});
      session.connect();
      LOG.debug("Connected to SFTP server");
      
      channel = session.openChannel("sftp");
      channel.connect();
      channelSftp = (ChannelSftp) channel;
      LOG.debug("SFTP channel opened and connected");
      
    }
    catch(JSchException e){
      LOG.error(e.getMessage());
    }
  }
  
  
  public void disconnect(){
    if(channelSftp != null){
      channelSftp.disconnect();
      LOG.debug("Disconnected channel SFTP");
    }
    if(channel != null){
      channel.disconnect();
      LOG.debug("Disconnected channel");
    }
    if(session != null){
      session.disconnect();
      LOG.debug("Disconnected session");
    }
  }
  
  
  public void uploadFile(String srcFile, String dstFile, final String dstDir) throws EtlSftpConnectionException{
    validateConnection();
    try {
      LOG.debug("Copying {} to server", new Object[]{srcFile});
      channelSftp.cd(dstDir);
      channelSftp.put(srcFile,dstFile);
      LOG.debug("Copied {} to server @ {}",new Object[]{srcFile,dstDir});
    }
    catch(SftpException e) {
      throw new EtlSftpConnectionException(e);
    }
  }
  
  public void downloadFile(String src, String dst) throws EtlSftpConnectionException{
    validateConnection();
    try {
      LOG.debug("Downloading {} from server", new Object[]{src});
      channelSftp.cd(dstDir);
      channelSftp.get(src,dst);
      LOG.debug("Downloaded {} from server",new Object[]{src});
    }
    catch(SftpException e) {
      throw new EtlSftpConnectionException(e);
    }
  }
  
  public void createDir(String dirPath) throws EtlSftpException, EtlSftpConnectionException {
    validateConnection();
    try {
      channelSftp.cd(dstDir);
      channelSftp.mkdir(dirPath);
      LOG.debug("Created directory {}",new Object[]{dirPath});
    }
    catch(SftpException e) {
      LOG.error("Error occured while creating directory {}. Status code {}", new Object[]{dirPath,e.toString()});
      throw new EtlSftpException(e);
    }
  }
  
  public List<?> getDirContents(String dirPath) throws EtlSftpConnectionException{
    validateConnection();
    List<?> vector = null;
    try {
      vector = channelSftp.ls(dirPath);
      for(int i=0; i < vector.size(); i++) {
        System.out.println(vector.get(i));
      }
      return vector;
    }
    catch(SftpException e) {
      throw new EtlSftpConnectionException(e);
    }
  }
  
  
  public void changeDir(String dirPath) throws EtlSftpConnectionException{
    validateConnection();
    try {
      channelSftp.cd(dirPath);
      LOG.debug("Changed path to {}", new Object[]{dirPath});
    }
    catch(SftpException e) {
      throw new EtlSftpConnectionException(e);
    }
  }
  
  

}
