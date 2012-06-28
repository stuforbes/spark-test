package uk.co.stuforbes.sparktest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.servlet.SparkApplication;
import uk.co.stuforbes.sparktest.route.Module;
import uk.co.stuforbes.template.TemplateApplication;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

public class Launcher implements SparkApplication {

	private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

	public static void main(final String[] args) {

		new Launcher().init();
	}

	public void init() {
		// final TemplateEngineFactory templateEngineFactory = new
		// XHTMLTemplateEngineFactory();
		// final TemplateApplication application = new TemplateApplication(
		// templateEngineFactory);

		final Injector injector = Guice.createInjector(new SparkTestModule());

		final TemplateApplication application = injector
				.getInstance(TemplateApplication.class);

		LOG.debug("About to initialise modules");
		final List<Binding<Module>> bindings = injector
				.findBindingsByType(TypeLiteral.get(Module.class));
		for (final Binding<Module> binding : bindings) {
			addModule(binding.getProvider().get(), application);
		}
	}

	private static void addModule(final Module module,
			final TemplateApplication application) {

		LOG.debug("About to add module " + module.getClass().getName());
		module.addRoutes(application);
	}
}
