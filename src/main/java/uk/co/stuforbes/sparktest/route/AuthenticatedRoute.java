package uk.co.stuforbes.sparktest.route;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.co.stuforbes.sparktest.session.SessionPersistence;

public abstract class AuthenticatedRoute extends Route {

	private final SessionPersistence sessionPersistence;

	public AuthenticatedRoute(final String path,
			final SessionPersistence sessionPersistence) {
		super(path);
		this.sessionPersistence = sessionPersistence;
	}

	@Override
	public final Object handle(final Request request, final Response response) {

		final String userId = sessionPersistence.retrieve(request, "userId");
		if (userId != null) {
			return handleAsUser(Integer.parseInt(userId), request, response);
		} else {
			response.status(401);
			response.redirect("/spark/login");
		}
		return "Login failed";
	}

	protected abstract Object handleAsUser(final int userId,
			final Request request, final Response response);
}
