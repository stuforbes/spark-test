package uk.co.stuforbes.sparktest.route.module;

import static spark.Spark.*;

import spark.Request;
import spark.Response;
import uk.co.stuforbes.sparktest.domain.User;
import uk.co.stuforbes.sparktest.keystore.Keystore;
import uk.co.stuforbes.sparktest.route.AuthenticatedRoute;
import uk.co.stuforbes.sparktest.route.Module;
import uk.co.stuforbes.sparktest.session.SessionPersistence;
import uk.co.stuforbes.template.TemplateApplication;
import uk.co.stuforbes.template.thymeleaf.contextprovider.SparkFrameworkContextProvider;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class UserModule implements Module {

	private final SessionPersistence sessionPersistence;
	private final Keystore keystore;
	private final Gson gson;

	@Inject
	public UserModule(final SessionPersistence sessionPersistence,
			final Keystore keystore, final Gson gson) {
		super();
		this.sessionPersistence = sessionPersistence;
		this.keystore = keystore;
		this.gson = gson;
	}

	public void addRoutes(final TemplateApplication application) {
		get(new AuthenticatedRoute("/user/home", sessionPersistence) {

			@Override
			protected Object handleAsUser(final int userId,
					final Request request, final Response response) {

				final SparkFrameworkContextProvider contextProvider = new SparkFrameworkContextProvider(
						request);
				if (keystore.retrieve("user:" + userId) != null) {
					contextProvider.addModel("user", gson.fromJson(
							keystore.retrieve("user:" + userId), User.class));
				}

				return application.controllerForTemplate("/spark/user/home")
						.process(contextProvider);
			}
		});
	}
}
