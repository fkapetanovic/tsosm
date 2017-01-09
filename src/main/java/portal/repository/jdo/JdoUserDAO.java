package portal.repository.jdo;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import portal.domain.impl.Authority;
import portal.domain.impl.User;
import portal.repository.UserDAO;

@Repository
public class JdoUserDAO extends JdoDAO implements UserDAO {
	@Override
	public User getUser(String userName) {
		String filter = "username == p1 && deleted == false";
		String parameters = "java.lang.String p1";
		String ordering = "";

		Object[] values = new Object[1];

		values[0] = userName;

		Collection<User> users = getEntities(User.class, filter, parameters,
				ordering, values);

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}

	@Override
	public User getUserByEMail(String eMail) {
		String filter = "eMailAddress == p1 && deleted == false";
		String parameters = "java.lang.String p1";
		String ordering = "";

		Object[] values = new Object[1];

		values[0] = eMail;

		Collection<User> users = getEntities(User.class, filter, parameters,
				ordering, values);

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}

	@Override
	public Authority getAuthority(String authorityName) {
		String filter = "authority == p1";
		String parameters = "java.lang.String p1";
		String ordering = "";

		Object[] values = new Object[1];

		values[0] = authorityName;

		Collection<Authority> authorities = getEntities(Authority.class,
				filter, parameters, ordering, values);

		if (authorities.isEmpty()) {
			return null;
		}

		return authorities.iterator().next();
	}

	@Override
	public User getUserByUserNameAndActivationCode(String userName, String activationCode) {
		String filter = "username == p1 && activationCode == p2 && deleted == false";
		String parameters = "java.lang.String p1, java.lang.String p2";
		String ordering = "";

		Object[] values = new Object[2];

		values[0] = userName;
		values[1] = activationCode;

		Collection<User> users = getEntities(User.class, filter, parameters,
				ordering, values);

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}

	@Override
	public User getUserByUserNameAndPasswordChangeCode(String userName, String passwordChangeCode) {
		String filter = "username == p1 && passwordChangeCode == p2 && deleted == false";
		String parameters = "java.lang.String p1, java.lang.String p2";
		String ordering = "";

		Object[] values = new Object[2];

		values[0] = userName;
		values[1] = passwordChangeCode;

		Collection<User> users = getEntities(User.class, filter, parameters,
				ordering, values);

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}
}
