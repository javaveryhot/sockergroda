package utils;

public class VersionConverter {
	/**
	 * Example:
	 * 100 turns into v1.0.0
	 */
	public static String intToString(int version) {
		return "v" + (String.valueOf(version).replaceAll(".(?=.)", "$0."));
	}
}
