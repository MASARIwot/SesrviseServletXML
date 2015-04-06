package com.xml.businesslogic.thread;

//import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.xml.businesslogic.parsing.URLprocess;


public class URLsurfer implements Callable<List<String>> {
	private String url;
	private URLprocess urlProcess;

	public URLsurfer(String url) {

		this.url = url;
		this.urlProcess = new URLprocess();
	}

	@Override
	public List<String> call() throws Exception {
		return urlProcess.getURL(url);
	}

}
