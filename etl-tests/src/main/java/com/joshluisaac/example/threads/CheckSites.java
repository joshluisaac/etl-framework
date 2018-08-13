package com.joshluisaac.example.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckSites {

	private static final List<String> URLs = new ArrayList<String>(
			Arrays.asList("http://www.youtube.com/", "http://www.google.ca/"));

	public CheckSites() {
	}

	void pingAndReportEachSiteWhenKnown() {

		// max thread = 4
		int numberOfThreads = URLs.size() > 4 ? 4 : URLs.size();

		// create a thread pool
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

		// create an executor completion com.kollect.etl.service using the
		CompletionService<PingResult> compService = new ExecutorCompletionService<PingResult>(executor);
		
		for (String url : URLs){
			Task task = new Task(url);
		}

	}

}
