package portal.validation;

import java.util.Properties;

import portal.config.AppPropKeys;
import portal.util.Helper;
import portal.util.StringUtil;

public class InputCheck {
	private static final int MIN_PASSWORD_LENGTH;
	private static final int MIN_USERNAME_LENGTH;
	private static final int MAX_USERNAME_LENGTH;
	private static final int MAX_EMAIL_ADDRESS_LENGTH;
	private static final int MAX_NO_OF_TAGS_ALLOWED;
	private static final int MAX_NO_OF_WITYPES_ALLOWED;
	private static final String LOGICAL_OR_DELIMITER;
	private static final String LOGICAL_AND_DELIMITER;

	private final static Properties appProperties = Helper.getAppProperties();

	static {
		MIN_PASSWORD_LENGTH = Integer.parseInt
				(appProperties.getProperty(AppPropKeys.MIN_PASSWORD_LENGTH));
		MIN_USERNAME_LENGTH = Integer.parseInt
				(appProperties.getProperty(AppPropKeys.MIN_USERNAME_LENGTH));
		MAX_USERNAME_LENGTH = Integer.parseInt
				(appProperties.getProperty(AppPropKeys.MAX_USERNAME_LENGTH));
		MAX_EMAIL_ADDRESS_LENGTH = Integer.parseInt
				(appProperties.getProperty(AppPropKeys.MAX_EMAIL_ADDRESS_LENGTH));
		MAX_NO_OF_TAGS_ALLOWED = Integer.parseInt
				(appProperties.getProperty(AppPropKeys.MAX_NO_OF_TAGS_ALLOWED));
		MAX_NO_OF_WITYPES_ALLOWED = Integer.parseInt
				(appProperties.getProperty(AppPropKeys.MAX_NO_OF_WI_TYPES_ALLOWED));
		LOGICAL_OR_DELIMITER = appProperties.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);
		LOGICAL_AND_DELIMITER = appProperties.getProperty(AppPropKeys.LOGICAL_AND_DELIMITER);
	}

	public static boolean isValidUserName(String userName) {
		final String PATTERN = String.format("^(?=.{%d,%d}$)[a-zA-Z][A-Za-z0-9]*" +
				"([_-][A-Za-z0-9]+)*$", MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
		return userName.matches(PATTERN);
	}

	public static boolean isReservedUserName(String userName) {
		final String PATTERN = "\\b(admin|administrator|system|systemadministrator|" +
				"systemadmin|sysadministrator|sysadmin|root)\\b";
		return userName.toLowerCase().matches(PATTERN);
	}

	public static boolean isValidPassword(String password) {
		final String PATTERN = ".{" + MIN_PASSWORD_LENGTH + ",}";
		return password.matches(PATTERN);
	}

	public static boolean isSafeIpAddress(String ipAddress){

		final String PATTERN = "^[a-fA-F0-9.:]{0,40}";
		return ipAddress.matches(PATTERN);
	}

	public static boolean isValidEmailAddress(String eMailAddress) {
		final String PATTERN = "^([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]"
				+ "*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";
		return eMailAddress.matches(PATTERN) &&
			   eMailAddress.length() < MAX_EMAIL_ADDRESS_LENGTH;
	}

	public static boolean isValidGeneratedCode(String activationCode) {

		final String PATTERN = "^[a-fA-F0-9]{0,50}";
		return activationCode.matches(PATTERN);
	}

	public static boolean isValidStringOfTagIdGroups(String tagIdGroups){
		if(StringUtil.isNullOrEmpty(tagIdGroups)) {
			return true;
		}

		String [] tagIdGroupsArray = tagIdGroups.split(LOGICAL_OR_DELIMITER);
		String [] tagIds;

		int numberOfTags = 0;

		try {
			for (String tagIdGroup : tagIdGroupsArray){
				tagIds = tagIdGroup.split(LOGICAL_AND_DELIMITER);

				for (String tagId : tagIds){
					Long.parseLong(tagId);
					numberOfTags++;
				}
			}
		} catch (Exception e) {

			return false;
		}

		if (numberOfTags > MAX_NO_OF_TAGS_ALLOWED){
			return false;
		}
		return true;
	}

	public static boolean isValidStringOfWebItemTypeIds(String webItemTypeIds){
		if(StringUtil.isNullOrEmpty(webItemTypeIds)) {
			return true;
		}

		String [] webItemTypesIdsArray = webItemTypeIds.split(LOGICAL_OR_DELIMITER);
		int numberOfWebItemTypes = 0;

		try {
			for (String webItemTypeId : webItemTypesIdsArray){
				Long.parseLong(webItemTypeId);
				numberOfWebItemTypes++;
			}
		} catch (Exception e) {
			return false;
		}

		if (numberOfWebItemTypes > MAX_NO_OF_WITYPES_ALLOWED){
			return false;
		}
		return true;
	}
}
