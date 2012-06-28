package uk.co.stuforbes.sparktest.authentication;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import spark.Request;
import uk.co.stuforbes.sparktest.domain.InvalidCredentialsException;
import uk.co.stuforbes.sparktest.domain.User;
import uk.co.stuforbes.sparktest.domain.UserManager;
import uk.co.stuforbes.sparktest.fake.FakeRequestBuilder;
import uk.co.stuforbes.sparktest.fake.FakeResponse;
import uk.co.stuforbes.sparktest.session.SessionPersistence;

@RunWith(JMock.class)
public class SimpleSparkAuthenticationManagerTest {

	private Mockery context;

	private UserManager userManager;
	private SessionPersistence sessionPersistence;

	private AuthenticationManager<SparkRequestLifecycleContext> authenticationManager;

	@Before
	public void initialise() {
		this.context = new Mockery();

		this.userManager = context.mock(UserManager.class);
		this.sessionPersistence = context.mock(SessionPersistence.class);

		this.authenticationManager = new SimpleSparkAuthenticationManager(
				userManager, sessionPersistence);
	}

	@Test
	public void validCredentialsUpdatesSessionCorrectly()
			throws InvalidCredentialsException {
		final Request request = FakeRequestBuilder.newInstance() //
				.withParam("st_email", "an-email") //
				.withParam("st_password", "pwd") //
				.build();

		final User user = new User(1234, "an-email", "pwd", "first", "last",
				new Date());

		context.checking(new Expectations() {
			{
				oneOf(userManager).userWithCredentials("an-email", "pwd");
				will(returnValue(user));

				oneOf(sessionPersistence).persist(request, "userId", "1234");
			}
		});

		assertThat(
				authenticationManager.doAuthentication(new SparkRequestLifecycleContext(
						request, new FakeResponse())), is(user));
	}

	@Test
	public void invalidCredentialsUpdateResponseCode()
			throws InvalidCredentialsException {
		final Request request = FakeRequestBuilder.newInstance() //
				.withParam("st_email", "an-email") //
				.withParam("st_password", "pwd") //
				.build();

		final FakeResponse response = new FakeResponse();

		context.checking(new Expectations() {
			{
				oneOf(userManager).userWithCredentials("an-email", "pwd");
				will(throwException(new InvalidCredentialsException()));
			}
		});

		assertThat(
				authenticationManager.doAuthentication(new SparkRequestLifecycleContext(
						request, response)), is(nullValue()));
		assertThat(response.statusCode(), is(401));
	}
}
