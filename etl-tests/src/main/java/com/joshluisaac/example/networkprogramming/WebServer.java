package com.joshluisaac.example.networkprogramming;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	
	public WebServer(){
		
	}

	public static void main(String[] args) {
		
		try {
			ServerSocket serverSockect = new ServerSocket(6000);
			System.out.println("Waiting for connection..");
			Socket clientSockect = serverSockect.accept();
			System.out.println("Connected to client..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
