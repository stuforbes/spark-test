package uk.co.stuforbes.template.thymeleaf.contextprovider;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thymeleaf.context.IContext;

import spark.Request;
import uk.co.stuforbes.sparktest.fake.FakeRequestBuilder;

import com.google.common.base.Supplier;

@RunWith(JMock.class)
public class SparkFrameworkContextProviderTest {

	private Mockery context;

	private HttpServletRequest httpRequest;

	@Before
	public void initialise() {
		this.context = new Mockery();

		this.httpRequest = context.mock(HttpServletRequest.class);
	}

	@Test
	public void canCreateProviderWithRequestParameters() {

		final Request request = FakeRequestBuilder.newInstance()//
				.withParam("param-1", "value1")//
				.withParam("param-2", "value2")//
				.withParam("param-3", "value3")//
				.withRaw(httpRequest)//
				.build();

		context.checking(new Expectations() {
			{
				oneOf(httpRequest).getLocale();
				will(returnValue(Locale.getDefault()));
			}
		});

		final Supplier<IContext> provider = new SparkFrameworkContextProvider(
				request);

		final Map<String, Object> results = provider.get().getVariables();
		assertThat(3, is(results.size()));
		assertThat(results.get("param-1"), is((Object) "value1"));
		assertThat(results.get("param-2"), is((Object) "value2"));
		assertThat(results.get("param-3"), is((Object) "value3"));
	}

	@Test
	public void canCreateProviderWithRequestAttributes() {

		final Request request = FakeRequestBuilder.newInstance()//
				.withAttribute("attr-1", "value1")//
				.withAttribute("attr-2", "value2")//
				.withAttribute("attr-3", "value3")//
				.withRaw(httpRequest) //
				.build();

		context.checking(new Expectations() {
			{
				oneOf(httpRequest).getLocale();
				will(returnValue(Locale.getDefault()));
			}
		});

		final Supplier<IContext> provider = new SparkFrameworkContextProvider(
				request);

		final Map<String, Object> results = provider.get().getVariables();
		assertThat(3, is(results.size()));
		assertThat(results.get("attr-1"), is((Object) "value1"));
		assertThat(results.get("attr-2"), is((Object) "value2"));
		assertThat(results.get("attr-3"), is((Object) "value3"));
	}

	@Test
	public void canCreateProviderWithRequestParametersAndAttributes() {

		final Request request = FakeRequestBuilder.newInstance()//
				.withParam("param-1", "valueP1")//
				.withParam("param-2", "valueP2")//
				.withParam("param-3", "valueP3")//
				.withAttribute("attr-1", "valueA1")//
				.withAttribute("attr-2", "valueA2")//
				.withAttribute("attr-3", "valueA3")//
				.withRaw(httpRequest)//
				.build();

		context.checking(new Expectations() {
			{
				oneOf(httpRequest).getLocale();
				will(returnValue(Locale.getDefault()));
			}
		});

		final Supplier<IContext> provider = new SparkFrameworkContextProvider(
				request);

		final Map<String, Object> results = provider.get().getVariables();
		assertThat(6, is(results.size()));
		assertThat(results.get("param-1"), is((Object) "valueP1"));
		assertThat(results.get("param-2"), is((Object) "valueP2"));
		assertThat(results.get("param-3"), is((Object) "valueP3"));
		assertThat(results.get("attr-1"), is((Object) "valueA1"));
		assertThat(results.get("attr-2"), is((Object) "valueA2"));
		assertThat(results.get("attr-3"), is((Object) "valueA3"));
	}
}
