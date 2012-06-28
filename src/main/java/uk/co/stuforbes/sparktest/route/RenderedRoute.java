package uk.co.stuforbes.sparktest.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.co.stuforbes.template.TemplateApplication;
import uk.co.stuforbes.template.TemplateController;
import uk.co.stuforbes.template.thymeleaf.contextprovider.SparkFrameworkContextProvider;

public class RenderedRoute extends Route {

	private static final Logger LOG = LoggerFactory
			.getLogger(RenderedRoute.class);

	private final String templateName;
	private final TemplateApplication application;

	public RenderedRoute(final String path, final String templateName,
			final TemplateApplication application) {
		super(path);
		this.templateName = templateName;
		this.application = application;
	}

	@Override
	public Object handle(final Request request, final Response response) {
		LOG.debug("About to attempt to render template " + templateName);
		final TemplateController controller = application
				.controllerForTemplate(templateName);
		if (controller == null) {
			LOG.debug("No controller found for template " + templateName
					+ ", setting response status code to 404");
			response.status(404);
		} else {
			LOG.debug("Found controller, about to render template "
					+ templateName);
			return controller
					.process(new SparkFrameworkContextProvider(request));
		}
		return "An error occurred";
	}
}
