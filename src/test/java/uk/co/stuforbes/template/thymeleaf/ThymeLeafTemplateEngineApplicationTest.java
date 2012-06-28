package uk.co.stuforbes.template.thymeleaf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.stuforbes.template.TemplateApplication;
import uk.co.stuforbes.template.TemplateController;
import uk.co.stuforbes.template.thymeleaf.context.ContextFactory;

@RunWith(JMock.class)
public class ThymeLeafTemplateEngineApplicationTest {

	private Mockery context;

	private TemplateEngineFactory templateEngineFactory;
	private TemplateApplication application;
	private ContextFactory contextFactory;

	@Before
	public void initialise() {
		this.context = new Mockery();

		this.templateEngineFactory = context.mock(TemplateEngineFactory.class);
		this.contextFactory = context.mock(ContextFactory.class);

		this.application = new ThymeLeafTemplateEngineApplication(
				templateEngineFactory, contextFactory);
	}

	@Test
	public void canCreateControllerForPath() {

		context.checking(new Expectations() {
			{
				oneOf(templateEngineFactory).create();
			}
		});

		final TemplateController controller = application
				.controllerForTemplate("/path");
		assertThat(controller, is(notNullValue()));
		assertThat(controller, is(PathDefinedTemplateController.class));
	}
}
