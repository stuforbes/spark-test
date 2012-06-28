package uk.co.stuforbes.sparktest.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import uk.co.stuforbes.sparktest.domain.InvalidCredentialsException;
import uk.co.stuforbes.sparktest.domain.User;
import uk.co.stuforbes.sparktest.domain.UserManager;
import uk.co.stuforbes.sparktest.session.SessionPersistence;

import com.google.inject.Inject;

public class SimpleSparkAuthenticationManager implements
		AuthenticationManager<SparkRequestLifecycleContext> {

	private static final Logger LOG = LoggerFactory
			.getLogger(SimpleSparkAuthenticationManager.class);
	private final UserManager userManager;
	private final SessionPersistence sessionPersistence;

	@Inject
	public SimpleSparkAuthenticationManager(final UserManager userManager,
			final SessionPersistence sessionPersistence) {
		this.userManager = userManager;
		this.sessionPersistence = sessionPersistence;
	}

	public User doAuthentication(final SparkRequestLifecycleContext context) {
		LOG.debug("Handling authentication request for login email "
				+ context.request().queryParams("st_email"));
		final Request request = context.request();
		try {
			final User user = userManager.userWithCredentials(
					request.queryParams("st_email"),
					request.queryParams("st_password"));

			LOG.debug("Authentication successful, updating session");
			sessionPersistence
					.persist(request, "userId", "" + user.getUserId());
			return user;
		} catch (final InvalidCredentialsException ex) {
			LOG.debug("authentication failed, updating response with code 401");
			context.response().status(401);
			return null;
		}
	}
}
