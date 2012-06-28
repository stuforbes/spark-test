package uk.co.stuforbes.sparktest.route.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.co.stuforbes.sparktest.authentication.AuthenticationManager;
import uk.co.stuforbes.sparktest.authentication.SparkRequestLifecycleContext;
import uk.co.stuforbes.sparktest.domain.User;
import uk.co.stuforbes.sparktest.keystore.Keystore;
import uk.co.stuforbes.sparktest.route.Module;
import uk.co.stuforbes.sparktest.route.RenderedRoute;
import uk.co.stuforbes.template.TemplateApplication;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class LoginModule implements Module {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoginModule.class);

	private final AuthenticationManager<SparkRequestLifecycleContext> authenticationManager;
	private final Keystore keystore;

	private final Gson gson;

	@Inject
	public LoginModule(
			final AuthenticationManager<SparkRequestLifecycleContext> authenticationManager,
			final Keystore keystore, final Gson gson) {
		this.authenticationManager = authenticationManager;
		this.keystore = keystore;
		this.gson = gson;
	}

	public void addRoutes(final TemplateApplication application) {
		get(new RenderedRoute("/login", "login", application));

		post(new Route("/login") {
			@Override
			public Object handle(final Request request, final Response response) {
				final User user = authenticationManager
						.doAuthentication(new SparkRequestLifecycleContext(
								request, response));

				if (user != null) {
					LOG.debug("Found user " + user.getUserId()
							+ ", about to cache");
					keystore.save("user:" + user.getUserId(), gson.toJson(user));
					response.redirect("/spark/user/home");
				} else {
					response.redirect("/spark/login");
				}
				return "done";
			}
		});
	}
}
