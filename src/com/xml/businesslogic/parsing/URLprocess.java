package com.xml.businesslogic.parsing;

import java.io.IOException;
//import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.List;

import org.jsoup.Connection;
//import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xml.businesslogic.session.URLsession;

public class URLprocess {
	private URLsession urlSession = URLsession.getInctance();

	public URLprocess() {
	}

	public LinkedList<String> getURL(String url) throws IOException {
		Connection.Response response = Jsoup.connect(url)
				.ignoreContentType(true).timeout(10000).execute();
		LinkedList<String> listOfLinks = null;
		if (response.statusCode() == 200) {
			listOfLinks = new LinkedList<String>();
			Document doc = Jsoup.connect(url).timeout(10000).get();
			Elements link = doc.select("a[href]");
			
			for (Element links : link) {
				if (!links.attr("abs:href").contains("#")) {
					if (!links.attr("abs:href").toLowerCase()
							.matches(".*\\.(jpg|gif|png)$"))
						if (links.attr("abs:href").contains(url)) {

							if (urlSession.addItemToSession(links
									.attr("abs:href")))
								if (!links
										.attr("abs:href")
										.toLowerCase()
										.matches(
												".*\\.(doc|docx|pdf|rar|xml|jpg)$"))
									listOfLinks.add(links.attr("abs:href"));
							// System.out.println(listOfLinks.add(links.attr("abs:href")));
						} else if (links.attr("href").trim().startsWith("/")
								& !links.attr("abs:href").contains(url)) {
							if (urlSession.addItemToSession(links.attr(
									"abs:href").trim()))
								if (!links
										.attr("abs:href")
										.toLowerCase()
										.matches(
												".*\\.(doc|docx|pdf|rar|xml|jpg)$"))
									listOfLinks.add(links.attr("abs:href")
											.trim());
							// System.out.println(listOfLinks.add(links.attr("abs:href").trim()));
						}

				}

			}
		}

		return listOfLinks;
	}

	// public static class GetURLinformation {
	// private String url;
	// private Connection.Response response;
	//
	// public static GetURLinformation getFromURL(String url)
	// throws IOException {
	// return new GetURLinformation(url);
	// }
	//
	// public GetURLinformation(String url) throws IOException {
	// this.url = url;
	// response = Jsoup.connect(this.url).ignoreContentType(true)
	// .execute();
	// }
	//
	// public String getLastModified() {
	// return response.header("Last-Modified");
	// }
	//
	// public String getExpires() {
	// return response.header("Expires");
	// }
	//
	// @Deprecated
	// @SuppressWarnings("unused")
	// public Changefreq getChangeFreq() {
	// String expires = getExpires();
	// return Changefreq.always;
	// }
	//
	// @Deprecated
	// public String getPriority() {
	// return "0.5";
	// }
	//
	// }

	public static class TestClass {

		public static void main(String[] args) throws IOException {
			for (String str : new URLprocess()
					.getURL("http://uawebchallenge.com/front-end")) {
				System.out.println(str);
			}
			// System.out
			// .println(URLprocess.GetURLinformation
			// .getFromURL(
			// "http://uawebchallenge.com/public/files/js/ckfinder/userfiles/files/%D0%9F%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0_Web_Challenge_VI%D0%86.pdf")
			// .getLastModified());

		}

	}
}
