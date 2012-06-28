package uk.co.stuforbes.sparktest.domain;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

public class FakeUserManager implements UserManager {

	private final Set<User> users;

	public FakeUserManager() {
		final User user1 = new User(1, "user-1@example.com", "pwd1",
				"firstname-1", "lastname-1", dateOf(1, 1, 2001));
		final User user2 = new User(2, "user-2@example.com", "pwd2",
				"firstname-2", "lastname-2", dateOf(2, 2, 2002));
		final User user3 = new User(3, "user-3@example.com", "pwd3",
				"firstname-3", "lastname-3", dateOf(3, 3, 2003));
		final User user4 = new User(4, "user-4@example.com", "pwd4",
				"firstname-4", "lastname-4", dateOf(4, 4, 2004));
		this.users = Sets.newHashSet(user1, user2, user3, user4);
	}

	public User userWithCredentials(final String email, final String password)
			throws InvalidCredentialsException {
		final Collection<User> validUser = Collections2.filter(users,
				new UserCredentialPredicate(email, password));
		if (validUser.isEmpty()) {
			throw new InvalidCredentialsException(
					"Could not authenticate user " + email);
		}
		return validUser.iterator().next();
	}

	private Date dateOf(final int day, final int month, final int year) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}

	private static class UserCredentialPredicate implements Predicate<User> {

		private final String email;
		private final String password;

		public UserCredentialPredicate(final String email, final String password) {
			this.email = email;
			this.password = password;
		}

		public boolean apply(final User input) {
			return email.equals(input.getEmail())
					&& password.equals(input.getPassword());
		}

	}
}
