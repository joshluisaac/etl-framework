package com.kollect.etl.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPService{
  
  
  public Properties loadFtpProp(){
    
    Properties p = new Properties();
    try(FileInputStream in = new FileInputStream(new File("../config/ftp.properties"))) {
      p.load(in);
      return p;
    } catch(IOException e){
      e.printStackTrace();
    }
    return p;
  }
  
  
  public  void send (String fileName) {
    
    Properties p = loadFtpProp();
    String SFTPHOST = p.getProperty("upload.server");
    int SFTPPORT = Integer.parseInt(p.getProperty("upload.port"));
    String SFTPUSER = p.getProperty("upload.user");
    String SFTPPASS = p.getProperty("upload.pass");
    String SFTPWORKINGDIR = p.getProperty("upload.remoteDir");
    

    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;
    System.out.println("preparing the host information for sftp.");
    try {
        JSch jsch = new JSch();
        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
        session.setPassword(SFTPPASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        System.out.println("Host connected.");
        channel = session.openChannel("sftp");
        channel.connect();
        System.out.println("sftp channel opened and connected.");
        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(SFTPWORKINGDIR);
        File f = new File(fileName);
        channelSftp.put(new FileInputStream(f), f.getName());
        System.out.println("File transfered successfully to host.");
    } catch (Exception ex) {
         System.out.println("Exception found while tranfer the response.");
         ex.printStackTrace();
    }
    finally{

        channelSftp.exit();
        System.out.println("sftp Channel exited.");
        channel.disconnect();
        System.out.println("Channel disconnected.");
        session.disconnect();
        System.out.println("Host Session disconnected.");
    }
}
  
}