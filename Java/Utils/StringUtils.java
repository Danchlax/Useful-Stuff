package monitora.qa.utils;

public class StringUtils {

	public static String formatName(String name) {
		String lastName = name.split(", ")[0];
		String firstName = name.split(", ")[1];
		return firstName + " " + lastName;
	}

	public static double timeToDouble(String time){
		double hourValue = Double.parseDouble(time.split(":")[0]);
		double minuteValue = Double.parseDouble(time.split(":")[1])/60;
		return hourValue + minuteValue;
	}
}
