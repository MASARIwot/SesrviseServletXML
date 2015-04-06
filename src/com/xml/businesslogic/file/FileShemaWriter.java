package com.xml.businesslogic.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import com.xml.businesslogic.session.FileSession;

public class FileShemaWriter {
	private static FileSession fileSession = FileSession.getInctance();

	public FileShemaWriter() {
	}

	/**
	 * 
	 * @param path
	 * @param ServerLocation
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String write(String path, String ServerLocation)throws UnsupportedEncodingException {

		StringBuilder bufString = new StringBuilder();
		String fileName = "FileShema"+ (Calendar.getInstance().getTime().toString()
						.replace(':', '_').replace(' ', '_').trim()) + ".xsd";
		File file = null;
		BufferedWriter bufWriter = null;
		try {
			file = new File((path + fileName).trim());
			bufWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// cp1251 - windows
		bufString
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "\n<sitemapindex xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
						+ "\nxsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd\"\n"
						+ "\nxmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

		for (String s : fileSession.getFileList()) {
			bufString.append("\n<sitemap>\n");
			bufString.append("\n\t\t<loc>"
					+ (ServerLocation + s.replace(path, "")).trim()
					+ "</loc>\n");
			bufString.append("\n\t\t<lastmod>"
					+ new Date((new File(s).lastModified())) + "</lastmod>\n");
			bufString.append("\n </sitemap>\n");

		}
		bufString.append("\n</sitemapindex>");
		try {
			bufWriter.write(bufString.toString());
			fileName = file.getAbsolutePath();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufWriter != null)
					bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return fileName;

	}

}
