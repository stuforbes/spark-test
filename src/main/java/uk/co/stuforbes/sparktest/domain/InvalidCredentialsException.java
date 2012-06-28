package uk.co.stuforbes.sparktest.domain;

public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = 1510241899460556820L;

	public InvalidCredentialsException() {
		super();
	}

	public InvalidCredentialsException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialsException(final String message) {
		super(message);
	}

	public InvalidCredentialsException(final Throwable cause) {
		super(cause);
	}

}
