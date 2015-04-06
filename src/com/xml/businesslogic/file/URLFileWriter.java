package com.xml.businesslogic.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.Writer;
import java.util.Calendar;
//import java.util.HashSet;
//import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

//import javax.management.Query;





import com.xml.businesslogic.object.UrlBeans;
import com.xml.businesslogic.session.FileSession;
import com.xml.businesslogic.thread.FileSplitWriter;

public class URLFileWriter {
	private String path;
	private int number = 1;
	private String fileName;
	private ConcurrentLinkedQueue<String> urlShema ;
	private static int iterator = 9;
	
//	private Writer writer = null;
	private File file = null;
	
	private BufferedWriter bufWriter = null;
	private StringBuilder bufString = null;
	private FileSession fileSession = FileSession.getInctance();
	
	public URLFileWriter(String path,Set<String> urlShema){
		this.path = path;
		this.urlShema= new ConcurrentLinkedQueue<String>(urlShema);
	}
	public URLFileWriter(String path,ConcurrentLinkedQueue<String> urlShema,int number){
		this.path = path;
		this.number = number;
		this.urlShema= urlShema;
	}

	public void boss() {
		
		FileSession.isDone = false;
		try {init(); writeShema();} catch (IOException e){
			if(bufWriter != null){try {bufWriter.close();}catch (IOException e1) {e1.printStackTrace();}
			}e.printStackTrace();} 
	}

	private void init(){
		try{
			fileName = ("Shema"+this.number+(Calendar.getInstance().getTime().toString().replace(':', '_').replace(' ', '_').trim())+".xsd");
			file = new File((this.path + fileName).trim());
			if(!file.exists()){file.createNewFile();}
//			file = File.createTempFile(fileName, ".xsd");
//			writer = new FileWriter(this.path + fileName+".txt");
			bufWriter = new BufferedWriter(new FileWriter(file)); 
			bufString = new StringBuilder();
		}catch(IOException e){ e.printStackTrace();}
	}

	private void writeShema() throws IOException{
		try{
		String charCounter;
		bufWriter.write(charCounter ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bufString.append(charCounter);
		bufWriter.newLine();
		bufWriter.write(charCounter ="<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
		bufString.append(charCounter);
		bufString.append(charCounter);
		bufWriter.newLine();
		bufWriter.flush();
		
		UrlBeans beanUrl;
		while(!urlShema.isEmpty()){
					
						
			String s = urlShema.poll();
	        beanUrl = new UrlBeans(s);
			bufWriter.newLine();
			bufWriter.newLine();
			bufWriter.write(charCounter ="<url>");
			bufString.append(charCounter);
			bufWriter.newLine();
			bufWriter.newLine();
			bufWriter.write(charCounter = "	<loc>"
						 + s.replace("&", "&amp;").replace("'", "&apos;")
							.replace("\"", "&quot;").replace(">", "&gt;")
							.replace("<", "&lt;") + "</loc>");
			bufString.append(charCounter);
			bufWriter.newLine();
			bufWriter.newLine();
			if(beanUrl.getLastmod() != null){
			bufWriter.write(charCounter ="	<lastmod>"+beanUrl.getLastmod()+"</lastmod>");
			bufString.append(charCounter);
			bufWriter.newLine();
			bufWriter.newLine();
			}
			if(beanUrl.getChangefreq() != null){
			bufWriter.write(charCounter ="	<changefreq>"+beanUrl.getChangefreq()+"</changefreq>");
			bufString.append(charCounter);
			bufWriter.newLine();
			bufWriter.newLine();
			}
			if(beanUrl.getPriority() != null){
			bufWriter.write(charCounter ="	<priority>"+beanUrl.getPriority()+"</priority>");
			bufString.append(charCounter);
			bufWriter.newLine();
			bufWriter.newLine();
			}
			bufWriter.write(charCounter ="</url>");
			bufString.append(charCounter);
			
			bufWriter.flush();
			
			iterator += bufString.length();
			bufString = new StringBuilder();
			if(iterator == 5242869){break;} 
			
		}
		
		bufWriter.newLine();
		bufWriter.write("</urlset>");
		bufWriter.flush();
		}finally{
			try{
		bufWriter.close();
					}catch(IOException e){e.printStackTrace();}
		}
		fileSession.addFile(file.getAbsolutePath());
		FileSession.isDone = true;
		if(iterator == 5242871){
			 FileSession.isDone = false;
			 Thread myThready = new Thread( new FileSplitWriter(this.path,this.urlShema,this.number));
			 myThready.isDaemon();
			
		}
//		System.out.println(iterator+9);
	}

}
/*
\\	Вывести обратную черту \ 
\"	Вывести двойную кавычку “ 
\'	Вывести одинарную кавычку '
\{	Вывести фигурную скобку {
\[	Вывести квадратную скобку [
 * */
 