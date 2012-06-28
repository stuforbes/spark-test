package uk.co.stuforbes.sparktest.session;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import spark.Request;
import uk.co.stuforbes.sparktest.fake.FakeRequestBuilder;

@RunWith(JMock.class)
public class HttpSessionPersistenceTest {

	private Mockery context;

	private HttpServletRequest httpRequest;
	private HttpSession httpSession;

	private SessionPersistence sessionPersistence;

	@Before
	public void initialise() {
		this.context = new Mockery();

		httpRequest = context.mock(HttpServletRequest.class);
		httpSession = context.mock(HttpSession.class);

		this.sessionPersistence = new HttpSessionPersistence();
	}

	@Test
	public void canUpdateSessionCorrectly() {
		final Request request = FakeRequestBuilder.newInstance()
				.withRaw(httpRequest).build();

		context.checking(new Expectations() {
			{
				oneOf(httpRequest).getSession();
				will(returnValue(httpSession));

				oneOf(httpSession).setAttribute("a-key", "a-value");
			}
		});

		sessionPersistence.persist(request, "a-key", "a-value");
	}

	@Test
	public void canRetrieveFromSession() {
		final Request request = FakeRequestBuilder.newInstance()
				.withRaw(httpRequest).build();

		context.checking(new Expectations() {
			{
				oneOf(httpRequest).getSession();
				will(returnValue(httpSession));

				oneOf(httpSession).getAttribute("a-key");
				will(returnValue("a-value"));
			}
		});

		final String value = sessionPersistence.retrieve(request, "a-key");
		assertThat(value, is(notNullValue()));
		assertThat(value, is("a-value"));
	}
}
