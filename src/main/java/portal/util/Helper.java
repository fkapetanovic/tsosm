package portal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;

public class Helper {
	private final static Properties APP_PROPERTIES = new Properties();

	@Autowired
	private static MessageSource messageSource;

	static {
		final String PROJECT_SETUP = System.getProperty("project_setup");
		final InputStream STREAM = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("settings/" + PROJECT_SETUP + "/app.properties");

		try {
			APP_PROPERTIES.load(STREAM);
		} catch (IOException e) {
			System.out.print(e.getStackTrace());
		} finally {
			try {
				STREAM.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static HashSet<Long> convertDelimitedStringToLongHashSet(String s,
			String delimiter) throws NumberFormatException, NullPointerException {
		if (s == null) {
			String msg = messageSource.getMessage
					(MessageKeys.STRING_NULL, null, null);
			throw new NullPointerException(msg);
		}

		if (delimiter == null) {
			String msg = messageSource.getMessage
					(MessageKeys.DELIMITER_NULL, null, null);
			throw new NullPointerException(msg);
		}

		HashSet<Long> itemsSet = new HashSet<Long>();

		if (s.length() > 0) {
			String[] itemsArray = s.split(delimiter);

			for (int i = 0; i < itemsArray.length; i++) {
				itemsSet.add(Long.parseLong(itemsArray[i]));
			}
		}
		return itemsSet;
	}

	public static String generateUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String encodePassword(String password) {
		final int MAX_PASSWORD_LENGTH = Integer.parseInt
				(APP_PROPERTIES.getProperty(AppPropKeys.MAX_PASSWORD_LENGTH));

		if (password.length() > MAX_PASSWORD_LENGTH) {
			password = password.substring(0, MAX_PASSWORD_LENGTH);
		}

		Assert.isTrue(password.length() <=  MAX_PASSWORD_LENGTH);
		return new BCryptPasswordEncoder().encode(password);
	}

	public static Properties getAppProperties(){
		return APP_PROPERTIES;
	}
}
