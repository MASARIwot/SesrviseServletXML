package com.xml.businesslogic.parsing;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import com.xml.businesslogic.file.FileShemaWriter;
import com.xml.businesslogic.file.URLFileWriter;
import com.xml.businesslogic.session.FileSession;
import com.xml.businesslogic.session.URLsession;
import com.xml.businesslogic.thread.URLsurfer;
import com.xml.businesslogic.thread.URLsurferSemaphore;

public class URLMapCreater {

	private URLprocess urlProcess;
	private String mainURL;
	private ExecutorService executorPool = null;// = Executors.newFixedThreadPool(5);
	private ConcurrentLinkedQueue<String> urlQueue = null;// new ConcurrentLinkedQueue<String>();
	private Semaphore available = null;
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
		this.urlQueue = new ConcurrentLinkedQueue<String>();
		this.mainURL = mainURL;
		this.urlProcess = new URLprocess();
		
	}

		
	public void createMap(int numberOfThread) throws IOException{
		if(numberOfThread == 0) numberOfThread = 5;
		this.executorPool = Executors.newFixedThreadPool(numberOfThread);
		this.available = new Semaphore(numberOfThread);
		Set<Future<List<String>>> set = new HashSet<>();
		try {
			urlQueue.addAll(urlProcess.getURL(mainURL));
		} catch (IOException e) {
			throw new IOException(e.getStackTrace().toString());
		}
			
		URLsession.isDone = false;
		try {
			
			while (true) {
				if (!urlQueue.isEmpty()) {
					if(urlQueue.size() >= numberOfThread){
						for(int i = 0; i < numberOfThread; i++){
							set.add(executorPool.submit(new URLsurferSemaphore(urlQueue.poll(),available)));
						}
					}else set.add(executorPool.submit(new URLsurferSemaphore(urlQueue.poll(),available)));
				}
				if (available.availablePermits() >= numberOfThread/2) {
					for (Future<List<String>> future : set) {
						try {
							if(future.isDone()){
							urlQueue.addAll(future.get());
							set.remove(future);
							}
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
				}//
				if (available.availablePermits() == numberOfThread
						&& urlQueue.isEmpty() == true)
					break;

			}
		} finally {
			try {

				executorPool.shutdown();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		while (executorPool.isTerminated() == false) {
		}
		
		URLsession.isDone = true;
	
	}
	@Deprecated
	public void createMap() throws IOException {
//		this.urlQueue = new ConcurrentLinkedQueue<String>();
		this.executorPool = Executors.newFixedThreadPool(5);
		try {
			//urlQueue.add(mainURL);
			urlQueue.addAll(urlProcess.getURL(mainURL));
		} catch (IOException e) {
			e.printStackTrace();
		}
		URLsession.isDone = false;
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
		while(executorPool.isTerminated() == false){}
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
//			URLMapCreater aa = new URLMapCreater("http://hello.com/");
//			urlSession.setMainURL("http://hello.com/");
			aa.createMap(0);
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
