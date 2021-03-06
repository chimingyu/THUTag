package org.thunlp.url;

import java.util.regex.Pattern;

public class HttpUrlNormalizer {
	private static Pattern defaultPagePat = Pattern
			.compile(".*(default|index)\\." + "(asp|aspx|php|html|htm|shtml|dhtml|jsp)$");
	private static Pattern wrongPrefixPat = Pattern.compile("^http:/[0-9a-z]");

	public static String normalize(String url) {
		url = url.toLowerCase();
		if (url.startsWith("http//")) {
			url = url.replaceFirst("http//", "http://");
		}
		if (url.startsWith("http:///")) {
			url = url.replaceFirst("http:///", "http://");
		}
		if (wrongPrefixPat.matcher(url).find()) {
			url = url.replaceFirst("http:/", "http://");
		}
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}

		if (url.endsWith("#")) {
			url = url.substring(0, url.length() - 1);
		}

		url = url.replaceAll("/\\.(?=[^.]|$)", "");

		if (url.contains("..")) {
			boolean hasEndSlash = url.endsWith("/");
			String[] splitted = url.split("/");
			boolean[] keep = new boolean[splitted.length];
			for (int i = 0; i < splitted.length; i++) {
				keep[i] = true;
				if (splitted[i].equals("..")) {
					keep[i] = false;
					int k = i - 1;
					while (k > 0 && !keep[k])
						k--;
					if (k > 2)
						keep[k] = false;
				}
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < splitted.length; i++) {
				if (keep[i]) {
					sb.append(splitted[i]);
					if (i < splitted.length - 1 || hasEndSlash)
						sb.append("/");
				}
			}
			url = sb.toString();
		}

		if (defaultPagePat.matcher(url).matches()) {
			url = url.replaceAll("[^/]*$", "");
		}
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		return url;
	}
}
