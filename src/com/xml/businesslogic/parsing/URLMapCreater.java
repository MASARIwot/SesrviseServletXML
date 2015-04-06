package com.xml.businesslogic.parsing;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xml.businesslogic.file.FileShemaWriter;
import com.xml.businesslogic.file.URLFileWriter;
import com.xml.businesslogic.session.FileSession;
import com.xml.businesslogic.session.URLsession;
import com.xml.businesslogic.thread.URLsurfer;

public class URLMapCreater {

	private URLprocess urlProcess;
	private String mainURL;
	private ExecutorService executorPool = null;// = Executors.newFixedThreadPool(5);
	private ConcurrentLinkedQueue<String> urlQueue = null;// new ConcurrentLinkedQueue<String>();
	/**
	 * @param mainURL
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public URLMapCreater(String mainURL) throws IllegalArgumentException,
			NullPointerException {
		if (mainURL.toLowerCase().matches(".*\\.(doc|docx|pdf|rar)$")) {
			throw new IllegalArgumentException("Bed URL");
		} else if (mainURL == " " || mainURL == null) {
			throw new NullPointerException("Can not be NULL");
		}
		URLsession.isDone = false;
		this.urlQueue = new ConcurrentLinkedQueue<String>();
		this.executorPool = Executors.newFixedThreadPool(5);
		this.mainURL = mainURL;
		this.urlProcess = new URLprocess();
	}

	public void createMap() {

		try {
			//urlQueue.add(mainURL);
			urlQueue.addAll(urlProcess.getURL(mainURL));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Future<List<String>> taskURL_1 = null;
			Future<List<String>> taskURL_2 = null;
			Future<List<String>> taskURL_3 = null;
			Future<List<String>> taskURL_4 = null;
			Future<List<String>> taskURL_5 = null;
			if (!urlQueue.isEmpty()) {
				taskURL_1 = executorPool.submit(new URLsurfer(urlQueue.poll()));
			}
			if (!urlQueue.isEmpty()) {
				taskURL_2 = executorPool.submit(new URLsurfer(urlQueue.poll()));
			}
			if (!urlQueue.isEmpty()) {
				taskURL_3 = executorPool.submit(new URLsurfer(urlQueue.poll()));
			}
			if (!urlQueue.isEmpty()) {
				taskURL_4 = executorPool.submit(new URLsurfer(urlQueue.poll()));
			}
			if (!urlQueue.isEmpty()) {
				taskURL_5 = executorPool.submit(new URLsurfer(urlQueue.poll()));
			}

			while (!urlQueue.isEmpty()) {

				if (taskURL_1.isDone() && taskURL_1 != null) {
					try {
						urlQueue.addAll(taskURL_1.get());
						if (!urlQueue.isEmpty())
							taskURL_1 = executorPool.submit(new URLsurfer(
									urlQueue.poll()));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}// if
				if (taskURL_2.isDone() && taskURL_2 != null) {
					try {
						urlQueue.addAll(taskURL_2.get() );
						if (!urlQueue.isEmpty())
							taskURL_2 = executorPool.submit(new URLsurfer(
									urlQueue.poll()));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}// if
				if (taskURL_3.isDone() && taskURL_3 != null) {
					try {
						urlQueue.addAll(taskURL_3.get());
						if (!urlQueue.isEmpty())
							taskURL_3 = executorPool.submit(new URLsurfer(
									urlQueue.poll()));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}// if
				if (taskURL_4.isDone() && taskURL_4 != null) {
					try {
						urlQueue.addAll(taskURL_4.get());
						if (!urlQueue.isEmpty())
							taskURL_4 = executorPool.submit(new URLsurfer(
									urlQueue.poll()));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}// if
				if (taskURL_5.isDone() && taskURL_5 != null) {
					try {
						urlQueue.addAll(taskURL_5.get());
						if (!urlQueue.isEmpty())
							taskURL_5 = executorPool.submit(new URLsurfer(
									urlQueue.poll()));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}// if

			}// while
			
		} finally {
			try{
			executorPool.shutdown();
			}catch(SecurityException e){ e.printStackTrace();}
		}
		while(executorPool.isTerminated() == false){};
		URLsession.isDone = true;
		//

	}
	@SuppressWarnings("unused")
	@Deprecated
	private boolean checkURL(String url) {
		return url.toLowerCase().matches(".*\\.(doc|docx|pdf|rar)$");
	}

//	@SuppressWarnings("unused")
	public static class TestClass {

		public static void main(String[] args) throws IOException {
			long before = System.currentTimeMillis();
			URLsession urlSession = URLsession.getInctance();
			// http://uawebchallenge.com/
			URLMapCreater aa = new URLMapCreater("http://uawebchallenge.com/");
//			urlSession.setMainURL("http://hello.com/");
			aa.createMap();
			while(URLsession.isDone == false){}
			urlSession.print();
			String path = System.getProperty("java.io.tmpdir");
			URLFileWriter wrT = new URLFileWriter(path, urlSession.getSortedSession());
			wrT.boss();
			while(FileSession.isDone == false){}
			System.out.println(FileShemaWriter.write(path,"LocalHost/Download"));
			long after = System.currentTimeMillis();
			System.out.println("Time" + (after - before) / 1000);

		}

	}// TestClass

}
