package com.kollect.etl.notification.entity;

public class EmailConfigEntity {
  
  String host; 
  Integer port;
  String username;
  String password; 
  String smtpAuth; 
  String startTls;
  String debug;
  
  
  
  
  public String getHost() {
    return host;
  }
  public void setHost(String host) {
    this.host = host;
  }
  public Integer getPort() {
    return port;
  }
  public void setPort(Integer port) {
    this.port = port;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getSmtpAuth() {
    return smtpAuth;
  }
  public void setSmtpAuth(String smtpAuth) {
    this.smtpAuth = smtpAuth;
  }
  public String getStartTls() {
    return startTls;
  }
  public void setStartTls(String startTls) {
    this.startTls = startTls;
  }
  public String getDebug() {
    return debug;
  }
  public void setDebug(String debug) {
    this.debug = debug;
  }
  @Override
  public String toString() {
    return "EmailConfigEntity [host=" + host + ", port=" + port + ", username=" + username + ", password=" + password
        + ", smtpAuth=" + smtpAuth + ", startTls=" + startTls + ", debug=" + debug + "]";
  }
  
  

}
