package uk.co.stuforbes.sparktest.fake;

import spark.Response;

public class FakeResponse extends Response {

	private int status = 200;
	private String redirectLocation;

	public FakeResponse() {
		super();
	}

	@Override
	public void status(final int statusCode) {
		this.status = statusCode;
	}

	public int statusCode() {
		return status;
	}

	@Override
	public void redirect(final String location) {
		this.redirectLocation = location;
	}

	public String redirectLocation() {
		return redirectLocation;
	}
}
