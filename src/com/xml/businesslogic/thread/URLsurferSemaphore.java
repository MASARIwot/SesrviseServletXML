package com.xml.businesslogic.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import com.xml.businesslogic.parsing.URLprocess;


public class URLsurferSemaphore implements Callable<List<String>> {
	private String url;
	private URLprocess urlProcess;
	private Semaphore available = null;

	public URLsurferSemaphore(String url,Semaphore available) {

		this.url = url;
		this.available = available;
		this.urlProcess = new URLprocess();
	}

	@Override
	public List<String> call() throws Exception {
		try {
			available.acquire();
			return urlProcess.getURL(url);
		} finally {
			available.release();
		}
	}
	

}
