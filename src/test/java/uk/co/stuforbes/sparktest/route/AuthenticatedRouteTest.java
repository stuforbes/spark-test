package uk.co.stuforbes.sparktest.route;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import spark.Request;
import spark.Response;
import uk.co.stuforbes.sparktest.fake.FakeRequestBuilder;
import uk.co.stuforbes.sparktest.fake.FakeResponse;
import uk.co.stuforbes.sparktest.session.SessionPersistence;

@RunWith(JMock.class)
public class AuthenticatedRouteTest {

	private Mockery context;

	private SessionPersistence sessionPersistence;

	private FakeAuthenticatedRoute authenticatedRoute;

	@Before
	public void initialise() {
		this.context = new Mockery();

		this.sessionPersistence = context.mock(SessionPersistence.class);

		this.authenticatedRoute = new FakeAuthenticatedRoute("/path",
				sessionPersistence);
	}

	@Test
	public void authenticatedUserCanPerformAction() {

		final Request request = FakeRequestBuilder.newInstance().build();

		context.checking(new Expectations() {
			{
				oneOf(sessionPersistence).retrieve(request, "userId");
				will(returnValue("1234"));
			}
		});

		authenticatedRoute.handle(request, new FakeResponse());
		assertThat(authenticatedRoute.actionPerformed, is(true));
	}

	@Test
	public void unauthorizedUsersAreRedirectedToLoginPage() {

		final Request request = FakeRequestBuilder.newInstance().build();

		context.checking(new Expectations() {
			{
				oneOf(sessionPersistence).retrieve(request, "userId");
				will(returnValue(null));
			}
		});

		final FakeResponse response = new FakeResponse();
		authenticatedRoute.handle(request, response);
		assertThat(authenticatedRoute.actionPerformed, is(false));
		assertThat(response.statusCode(), is(401));
		assertThat(response.redirectLocation(), is("/spark/login"));
	}

	private static final class FakeAuthenticatedRoute extends
			AuthenticatedRoute {

		private boolean actionPerformed;

		public FakeAuthenticatedRoute(final String path,
				final SessionPersistence sessionPersistence) {
			super(path, sessionPersistence);
			this.actionPerformed = false;
		}

		@Override
		protected Object handleAsUser(final int userId, final Request request,
				final Response response) {
			actionPerformed = true;
			return "handled";
		}

	}
}
