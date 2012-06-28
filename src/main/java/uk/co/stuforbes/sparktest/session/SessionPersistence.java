package uk.co.stuforbes.sparktest.session;

import spark.Request;

public interface SessionPersistence {

	void persist(Request request, String key, String value);

	String retrieve(Request request, String key);
}
