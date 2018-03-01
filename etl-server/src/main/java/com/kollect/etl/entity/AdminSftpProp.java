package com.kollect.etl.entity;

public class AdminSftpProp {
		private String ServerName,UserName,Password,LocalDirectory,RemoteDirectory;
		private int PortNo;
		
		
		public AdminSftpProp(String serverName, int portNo,String userName, String password, String localDirectory,
				String remoteDirectory) {
		
			this.ServerName = serverName;
			this.PortNo = portNo;
			this.UserName = userName;
			this.Password = password;
			this.LocalDirectory = localDirectory;
			this.RemoteDirectory = remoteDirectory;
			
		}
		
		
}
