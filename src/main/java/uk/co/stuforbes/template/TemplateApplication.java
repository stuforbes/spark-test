package uk.co.stuforbes.template;

public interface TemplateApplication {

	TemplateController controllerForTemplate(final String path);
}
