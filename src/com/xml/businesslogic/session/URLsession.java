package com.xml.businesslogic.session;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class URLsession {

	public volatile static Boolean isDone = false;
	private volatile static URLsession instance = null;
//	private String mainURL;
	private final Lock lock = new ReentrantLock();
	private HashSet<String> sessionSet = null;// = new HashSet<String>();
	
	private URLsession() {
		sessionSet = new HashSet<String>();
	}
	public static URLsession getInctance() {
		if (instance == null) {
			synchronized (URLsession.class) {
				if (instance == null)
					instance = new URLsession();
			}
		}
		return instance;
	}

	public boolean addItemToSession(String url) {
		lock.lock();
		try {
//			System.out.println("d"+url);
			return sessionSet.add(url);
		} finally {
			lock.unlock();
		}
	}
//	public String getMainURL() {
//		return mainURL;
//	}
	public void clrSession() {
		sessionSet = new HashSet<String>();
	}
	public HashSet<String> getSession() {
		return this.sessionSet;
	}
	public Set<String> getSortedSession(){
		return  new TreeSet<String>(this.sessionSet);
	}
	public void print() {
	//	sessionSet.forEach(System.out::println);
		System.out.println(sessionSet);
	}

}
