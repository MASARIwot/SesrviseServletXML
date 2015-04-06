package com.xml.businesslogic.object;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class UrlBeans {
	private String loc;
	private GetURLinformation Gurl;

	public UrlBeans() {
	}

	public UrlBeans(String loc) throws IOException {
		this.loc = loc;
		Gurl = UrlBeans.GetURLinformation.getFromURL(loc);
	}

	public String getLoc() {
		return loc;
	}

	// public void setLoc(String loc) {
	// this.loc = loc;
	// }
	public String getLastmod() throws IOException {
		return Gurl.getLastModified();
	}

	// public void setLastmod(String lastmod) {
	// this.lastmod = lastmod;
	// }
	public String getChangefreq() {
		return Gurl.getChangeFreq();
	}

	// public void setChangefreq(int changefreq) {
	// this.changefreq = changefreq;
	// }
	public String getPriority() {
		return Gurl.getPriority();
	}

	// public void setPriority(int priority) {
	// this.priority = priority;
	// }

	protected static class GetURLinformation {
		private String url;
		private Connection.Response response ;

		protected static GetURLinformation getFromURL(String url)
				throws IOException {
			return new GetURLinformation(url);
		}

		protected GetURLinformation(String url) throws IOException {
			this.url = url;
			response = Jsoup.connect(this.url).ignoreContentType(true)
					.execute();
		}

		protected String getLastModified() {
			return response.header("Last-Modified");
		}

		protected String getExpires() {
			return response.header("Expires");
		}

		@Deprecated
		@SuppressWarnings("unused")
		public String getChangeFreq() {
			String expires = getExpires();
			return null;
		}

		@Deprecated
		public String getPriority() {
			int countSlesh = 0;
			for (char element : this.url.toCharArray()) {
				if (element == '/')
					countSlesh++;
			}
			if (countSlesh <= 3) {
				return "1";
			} else if (countSlesh == 4) {
				return "0.9";
			} else if (countSlesh == 5) {
				return "0.8";
			} else if (countSlesh == 6) {
				return "0.7";
			} else if (countSlesh == 7) {
				return "0.6";
			} else if (countSlesh >= 8) {
				return "0.5";
			}
			return "0.5";

		}

	}// */

	@Override
	public String toString() {
		return "UrlBeans [loc=" + this.loc + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof UrlBeans)) {
			return false;
		}
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		UrlBeans other = (UrlBeans) obj;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		return true;
	}

}
