package com.xml.businesslogic.interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.xml.businesslogic.file.FileShemaWriter;
import com.xml.businesslogic.file.URLFileWriter;
import com.xml.businesslogic.parsing.URLMapCreater;
import com.xml.businesslogic.session.FileSession;
import com.xml.businesslogic.session.URLsession;

public interface ICreateXML {

	public String create(String url, String ServerLocation)
			throws UnsupportedEncodingException, IOException;

	public String create(String url, String ServerLocation, String pathToSave)
			throws UnsupportedEncodingException, IOException;

	public static class CreateXML implements ICreateXML {

		private URLsession urlSession = null;
		private FileSession fileSession = null;
		private URLFileWriter urlFileWriter = null;
		private URLMapCreater urlMapCreator = null;

		public CreateXML() {
			this.fileSession = FileSession.getInctance();
			this.urlSession = URLsession.getInctance();

		}

		@Override
		public String create(String url, String ServerLocation)	throws UnsupportedEncodingException, IOException {
			String path = System.getProperty("java.io.tmpdir");
			this.urlMapCreator = new URLMapCreater(url);
			urlMapCreator.createMap(0);
			while (URLsession.isDone == false) {
			}
			// urlSession.print();
			this.urlFileWriter = new URLFileWriter(path,urlSession.getSortedSession());
			this.urlFileWriter.boss();
			while (FileSession.isDone == false) {
			}
			String resultPatn = FileShemaWriter.write(path, ServerLocation);
			return resultPatn;
		}

		@Override
		public String create(String url, String ServerLocation,	String pathToSave) throws UnsupportedEncodingException,	IOException {
			String path = pathToSave;
			this.urlMapCreator = new URLMapCreater(url);
			urlMapCreator.createMap(0);
			while (URLsession.isDone == false) {
			}
			// urlSession.print();
			this.urlFileWriter = new URLFileWriter(path,urlSession.getSortedSession());
			this.urlFileWriter.boss();
			while (FileSession.isDone == false) {
			}
			String resultPatn = FileShemaWriter.write(path, ServerLocation);
			fileSession.clrFileList();
			urlSession.clrSession();
			return resultPatn;
		}
	}

}
