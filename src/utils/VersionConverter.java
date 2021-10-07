package utils;

public class VersionConverter {
	/**
	 * Example:
	 * 100 turns into v1.0.0 or 1.0.0
	 */
	public static String intToString(int version, boolean full) {
		return (full ? "v" : "") + (String.valueOf(version).replaceAll(".(?=.)", "$0."));
	}
	
	public static String intToString(int version) {
		return intToString(version, true);
	}
}
