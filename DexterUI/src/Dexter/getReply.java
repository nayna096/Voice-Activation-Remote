package Dexter;
		
import java.util.Calendar;

public class getReply {
	String response = "";
	static String sir = "sir";
	static String currentState = "";
	static String input = "";
	static int who = 2;
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
	static int key = 0;

	public static String act(String currentState, String input) {
		String[] commands = new String[30];
		commands[0] = "volume up";
		commands[1] = "volume down";
		commands[2] = "on";
		commands[3] = "off";
		commands[4] = "select";
		commands[5] = "zero";
		commands[6] = "one";
		commands[7] = "two";
		commands[8] = "three";
		commands[9] = "four";
		commands[10] = "five";
		commands[11] = "six";
		commands[12] = "seven";
		commands[13] = "eight";
		commands[14] = "nine";
		commands[15] = "ten";
		commands[16] = "eleven";
		commands[17] = "twelve";
		commands[18] = "dexter";
		commands[19] = "dex";
		commands[20] = "cancel";
		commands[21] = "volume";
		commands[22] = "go back";
		commands[23] = "go back twice";
		commands[24] = "go back three";
		commands[25] = "go back four";
		commands[26] = "go back five";
		commands[27] = "in up";
		commands[28] = "in down";
		commands[29] = "n";
		String[] confirmation = new String[15];
		confirmation[0] = "Of course ";
		confirmation[1] = "Very well ";
		confirmation[2] = "Not a problem ";
		confirmation[3] = "Alright ";
		confirmation[4] = "On it ";
		confirmation[5] = "Will do ";
		confirmation[6] = "Yes ";
		confirmation[7] = "Okay ";
		confirmation[8] = "Affirmative ";
		confirmation[9] = "Absolutely ";
		confirmation[10] = "Sure thing ";
		confirmation[11] = "Duly noted ";
		confirmation[12] = "Got it ";
		confirmation[13] = "Certainly ";
		confirmation[14] = "For sure ";
		int a = ((int) ((15) * Math.random() + 1));
		String response = "";
		if (currentState.equals("sleep")) {
			if (input.equalsIgnoreCase("dex") || input.equalsIgnoreCase("dexter")) {
				response = "shall I wake " + sir + "?";
			} else {
				response = null;
			}
		} else if (currentState.equals("limbo")) {
			if ((input.equals("sleep") || input.equals("no"))) {
				response = confirmation[a - 1] + ", returning to sleep";
			} else if (input.equals("get up") || input.equals("wake up") || input.equals("wake")
					|| input.equals("yes")) {
				response = confirmation[a - 1] + sir + ". Waking up";
			} else if ((input.equals("sleep") || input.equals("no"))) {
				response = confirmation[a - 1] + sir + ". Returning to sleep";
			} else {
				response = "Do you wish for me to wake, or to sleep?";
			}
		} else if (currentState.equals("on")) {
			if (input.equals("sleep")) {
				response = confirmation[a - 1] + sir + ". Entering sleep, say my name to wake me up " + sir;
				return response;
			} else if (input.equalsIgnoreCase("dex") || input.equalsIgnoreCase("dexter")) {
				response = "Yes " + sir + " ?";
				return response;
			} else if (input.equals("<unk>")) {
				response = "I beg your pardon " + sir;
				return response;
			} else if (input.contains("not your boss") || (input.contains("am") && input.contains("your boss"))) {
				response = "is this Noah";
				who = 0;
				return response;
			} else if (input.equals("yes") && who == 0) {
				sir = "sir";
				who += 1;
				String greet = "";
				int hello = (int)(5*Math.random()+1);
				switch(hello) {
				case 1:
					greet = "Welcome back ";
					break;
				case 2:
					greet = "Of course. Hello ";
					break;
				case 3:
					greet = "Welcome ";
					break;
				case 4:
					greet = "Good to see you ";
					break;
				case 5:
					greet = "Welcome home ";
					break;
				}
				response = greet + sir;
				return response;
			} else if (input.equals("no") && who == 0) {
				sir = " ";
				who += 1;
				response = confirmation[a - 1] + sir + ". Hello stranger";
				return response;
			} else if (who == 0) {
				response = "is this Noah?";
				return response;
			} else {
				for (int i = 0; i < 30; i++) {
					if (input.equals(commands[i])) {
						response = confirmation[a - 1] + sir + ". Implementing command " + input + ". "
								+ Quip.keshum(input, sir);
						return response;
					}
				}
				
			}
			response = "What did you say " + sir;
			return response;
		}
		return response;

	}
}
