package portal.util;

public class StringUtil {
	public static boolean isNullEmptyOrAllWhiteSpace(String s) {
		return (s == null || s.replace(" ", "").length() == 0);
	}

	public static boolean isNullOrEmpty(String s) {
		return (s == null || s.length() == 0);
	}
}
