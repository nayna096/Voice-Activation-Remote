package DexterFam;

public class getNext {
	String currentState = "";
	String input = "";
	public static String Getnext(String currentState, String input) {
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
		String nextState = "";
		if (currentState.equals("sleep")) {
			if (input.equalsIgnoreCase("dex") || input.equalsIgnoreCase("dexter")) {
				nextState = "limbo";
			} else {
				nextState = "sleep";
			}
		} else if (currentState.equals("limbo")) {
			if ((input.equals("sleep") || input.equals("no"))) {
				nextState = "sleep";
			} else if (input.equals("get up") || input.equals("wake up") || input.equals("wake")
					|| input.equals("yes")) {
				nextState = "on";
			} else {
				nextState = "limbo";
			}
		} else if (currentState.equals("on")) {
			if (input.equals("sleep")) {
				nextState = "sleep";
			} else {
				nextState = "on";
			}
		}
		return nextState;
	}
}
