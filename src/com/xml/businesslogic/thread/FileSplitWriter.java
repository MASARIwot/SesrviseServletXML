package com.xml.businesslogic.thread;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.xml.businesslogic.file.URLFileWriter;

public class FileSplitWriter implements Runnable {
	private String path;
	private int number = 1;
	private ConcurrentLinkedQueue<String> urlShema ;
	public  FileSplitWriter(String path,ConcurrentLinkedQueue<String> urlShema,int number) {
		this.path = path;
		this.number += number;
		this.urlShema= urlShema;
		
	}

	@Override
	public void run() {
		
		new URLFileWriter(this.path,this.urlShema,this.number).boss();
		
	}

}
