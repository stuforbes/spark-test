package uk.co.stuforbes.sparktest.route;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import spark.RequestResponseFactory;
import spark.Response;
import uk.co.stuforbes.template.TemplateApplication;
import uk.co.stuforbes.template.TemplateController;

import com.google.common.base.Supplier;

@RunWith(JMock.class)
public class RenderedRouteTest {

	private static final String PATH = "/path";
	private static final String TEMPLATE_NAME = "a-template";

	private Mockery context;

	private TemplateApplication application;
	private RenderedRoute route;

	@Before
	public void initialise() {
		this.context = new Mockery();

		this.application = context.mock(TemplateApplication.class);
		this.route = new RenderedRoute(PATH, TEMPLATE_NAME, application);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void defersToCorrectControllerForRendering() {

		final TemplateController controller = context
				.mock(TemplateController.class);

		final String successMessage = "Successful result";

		context.checking(new Expectations() {
			{
				oneOf(application).controllerForTemplate(TEMPLATE_NAME);
				will(returnValue(controller));

				oneOf(controller).process(with(any(Supplier.class)));
				will(returnValue(successMessage));
			}
		});

		final Object result = route.handle(null, null);
		assertThat(result, is(notNullValue()));
		assertThat(result, is((Object) successMessage));
	}

	@Test
	public void returns404IfNoControllerFound() {
		final HttpServletResponse httpResponse = context
				.mock(HttpServletResponse.class);
		final Response response = RequestResponseFactory.create(httpResponse);

		context.checking(new Expectations() {
			{
				oneOf(application).controllerForTemplate(TEMPLATE_NAME);
				will(returnValue(null));

				oneOf(httpResponse).setStatus(404);
			}
		});

		final Object result = route.handle(null, response);
		assertThat(result, is(notNullValue()));
		assertThat(result, is((Object) "An error occurred"));
	}
}
