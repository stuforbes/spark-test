package uk.co.stuforbes.template.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.inject.Inject;

public class XHTMLTemplateEngineFactory implements TemplateEngineFactory {

	@Inject
	public XHTMLTemplateEngineFactory() {
	}

	public TemplateEngine create() {
		final TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		// XHTML is the default mode, but we will set it anyway for better
		// understanding of code
		templateResolver.setTemplateMode("XHTML");
		// This will convert "home" to "/WEB-INF/templates/home.html"
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		// Set template cache TTL to 1 hour. If not set, entries would live in
		// cache until expelled by LRU
		templateResolver.setCacheTTLMs(3600000L);
		final TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

}
