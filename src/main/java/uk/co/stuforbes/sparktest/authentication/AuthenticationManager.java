package uk.co.stuforbes.sparktest.authentication;

import uk.co.stuforbes.sparktest.domain.User;

public interface AuthenticationManager<AuthenticationContext> {

	User doAuthentication(AuthenticationContext context);
}
