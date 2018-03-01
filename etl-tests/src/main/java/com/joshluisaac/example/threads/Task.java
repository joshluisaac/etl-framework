package com.joshluisaac.example.threads;

import java.util.concurrent.Callable;

public class Task implements Callable<PingResult> {

	
	private String URL;
	
	Task(String URL){
		this.URL = URL;
	}

	public PingResult call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
