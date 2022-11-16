package Dexter;

import java.util.Calendar;

public class Quip {
	static Calendar calendar = Calendar.getInstance();
	static int hour = calendar.get(Calendar.HOUR);
	static int a_p = calendar.get(java.util.Calendar.AM_PM);
	static String am_pm;
	{
		if (a_p == 0) {
			am_pm = "AM";
		} else {
			am_pm = "PM";
		}
	}
	public static String keshum(String result, String sir) {
		String quip = "";
		if (result.equals("nine")) {
			int joke = (int) ((3) * Math.random() + 1);
			if (joke == 1) {
				int style = ((int) ((5) * Math.random() + 1));
				switch (style) {
				case 1:
					quip = "lol bet";
					break;
				case 2:
					quip = "Desperate for some comedy?";
					break;
				case 3:
					quip = "What has the orange man said today?";
					break;
				case 4:
					quip = "What goes down in crazy town?";
					break;
				case 5:
					quip = "Well then";
					break;
				}
			}
		} else if (result.equals("five") || result.equals("six") || result.equals("seven") || result.equals("eight")) {
			if (hour < 4 && hour > 8 && am_pm.equals("PM")) {
				quip = "Time for Tim and Sid!";
			}
		} else if (result.equals("zero") || result.equals("one") || result.equals("two") || result.equals("three")
				|| result.equals("four")) {
//			if (hour > 6 && hour < 11 && am_pm.equals("AM")) {
				quip = "Some early morning sports centre " + sir + " ?";
//			}
		} else if (result.equals("in down")) {
			quip = "Time for some X box " + sir + " ?";
		} else if (result.equals("off")) {
			quip = "Goodbye " + sir;
		}
		return quip;
	}

}
