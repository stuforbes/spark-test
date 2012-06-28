package uk.co.stuforbes.template.thymeleaf;

import uk.co.stuforbes.template.TemplateApplication;
import uk.co.stuforbes.template.TemplateController;
import uk.co.stuforbes.template.thymeleaf.context.ContextFactory;

import com.google.inject.Inject;

public class ThymeLeafTemplateEngineApplication implements TemplateApplication {

	private final TemplateEngineFactory templateEngineFactory;
	private final ContextFactory contextFactory;

	@Inject
	public ThymeLeafTemplateEngineApplication(
			final TemplateEngineFactory templateEngineFactory,
			final ContextFactory contextFactory) {
		this.templateEngineFactory = templateEngineFactory;
		this.contextFactory = contextFactory;
	}

	public TemplateController controllerForTemplate(final String path) {
		return new PathDefinedTemplateController(path,
				templateEngineFactory.create(), contextFactory);
	}
}