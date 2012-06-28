package uk.co.stuforbes.sparktest.session;

import spark.Request;

public class HttpSessionPersistence implements SessionPersistence {

	public void persist(final Request request, final String key,
			final String value) {
		request.raw().getSession().setAttribute(key, value);
	}

	public String retrieve(final Request request, final String key) {
		return (String) request.raw().getSession().getAttribute(key);
	}
}
