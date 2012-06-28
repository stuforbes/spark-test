package uk.co.stuforbes.sparktest.authentication;

import spark.Request;
import spark.Response;

public class SparkRequestLifecycleContext {

	private final Request request;
	private final Response response;

	public SparkRequestLifecycleContext(final Request request,
			final Response response) {
		this.request = request;
		this.response = response;
	}

	public Request request() {
		return request;
	}

	public Response response() {
		return response;
	}
}
