package uk.co.stuforbes.template.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import uk.co.stuforbes.template.TemplateController;
import uk.co.stuforbes.template.thymeleaf.context.ContextFactory;

import com.google.common.base.Supplier;

public class PathDefinedTemplateController implements TemplateController {

	private final String path;
	private final TemplateEngine templateEngine;

	public PathDefinedTemplateController(final String path,
			final TemplateEngine templateEngine,
			final ContextFactory contextFactory) {
		this.path = path;
		this.templateEngine = templateEngine;
	}

	public String process(final Supplier<IContext> contextProvider) {
		return templateEngine.process(path, contextProvider.get());
	}

}
