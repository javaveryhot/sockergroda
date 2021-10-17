package utils;

public class TimeStrings {
	/**
	 * Turns an amount of time in milliseconds into a string, such as "1 minute" or "2 days"
	 */
	public static String getTimeString(long time) {
		String result = null;
		int usingTimeUnit = 0; // 0 = seconds, 1 = minutes, 2 = hours, 3 = days, 4 = months, 5 = years
		boolean isMultiple = false;
		
		if(time < 60) { // Seconds
			usingTimeUnit = 0;
			isMultiple = Math.floor(time) != 1;
		} else if(time < 60 * 60) { // Minutes
			usingTimeUnit = 1;
			isMultiple = Math.floor(time / 60) != 1;
		} else if(time < 60 * 60 * 24) { // Hours
			usingTimeUnit = 2;
			isMultiple = Math.floor(time / 60 / 60) != 1;
		} else if(time < 60 * 60 * 24 * 30) { // Days
			usingTimeUnit = 3;
			isMultiple = Math.floor(time / 60 / 60 / 24) != 1;
		} else if(time < 60 * 60 * 24 * 365) { // Months
			usingTimeUnit = 4;
			isMultiple = Math.floor(time / 60 / 60 / 24 / 30) != 1;
		} else { // Years
			usingTimeUnit = 5;
			isMultiple = Math.floor(time / 60 / 60 / 24 / 365) != 1;
		}
		
		switch(usingTimeUnit) {
		case 0:
			result = (int)Math.floor(time) + " second";
			break;
		case 1:
			result = (int)Math.floor(time / 60) + " minute";
			break;
		case 2:
			result = (int)Math.floor(time / 60 / 60) + " hour";
			break;
		case 3:
			result = (int)Math.floor(time / 60 / 60 / 24) + " day";
			break;
		case 4:
			result = (int)Math.floor(time / 60 / 60 / 24 / 30) + " month";
			break;
		case 5:
			result = (int)Math.floor(time / 60 / 60 / 24 / 365) + " year";
			break;
		}
		
		result += (isMultiple ? "s" : "");
		return result;
	}
}
