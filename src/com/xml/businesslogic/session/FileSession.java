package com.xml.businesslogic.session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileSession {
	private List<String> fileList;
	private volatile static FileSession instance;
	public volatile static Boolean isDone = false;
	private final Lock lock = new ReentrantLock();

	private FileSession() {
		fileList = new ArrayList<String>();
	}

	public static FileSession getInctance() {
		if (instance == null) {
			synchronized (URLsession.class) {
				if (instance == null)
					instance = new FileSession();
			}
		}
		return instance;
	}// getInctance

	public void addFile(String file) {
		lock.lock();
		try {
			fileList.add(file);
		} finally {
			lock.unlock();
		}
	}

	public void clrFileList() {
		fileList = new ArrayList<String>();
	}

	public List<String> getFileList() {
		return fileList;
	}

}
