package uk.co.stuforbes.sparktest.domain;

public interface UserManager {

	User userWithCredentials(String email, String password)
			throws InvalidCredentialsException;

}
