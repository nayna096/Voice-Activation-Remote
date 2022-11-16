package DexterFam;

import java.util.ArrayList;

public class Friends {
	static int a = 0;
	static int b = 0;
	static int c = 0;
	static int d = 0;
	static int e = 0;
	static int f = 0;
	static int g = 0;
	static String LastName = "";
	static String gender = "";
	static String birthday = "";
	static String phonenumber = "";
	static String phonenumbertype = "";
	static String nickname = "";

	public static String getInfo(String input) {
		String FirstName = getInfo.setFirstName(input);
		return FirstName;
//		if (a == 0 && FirstName == "") {
//			a++;
//			System.out.println(a);
//			instruct = "Firstly, spell out your first name. When you are finished, say stop";
//			return instruct;
//		}
//		while (!(input.equals("stop")) && a == 1) {
//			if (input.length() == 1) {
//				FirstName = FirstName + input;
//				System.out.println(FirstName);
//			} else {
//				return "Use letters to spell your name, not words you doofus";
//			}
//			return FirstName;
//		}
//		if (input.equals("stop")) {
//			a++;
//			return "So your first name is " + FirstName;
//		}
//		if (a == 2) {
//			if (input.equals("no")) {
//				a--;
//				return "Let's try that again then";
//			}else if (input.equals("yes")) {
//				a++;
//				return "Great! Let's move on to your last name.";
//			}else {
//				return "I beg your pardon?";
//			}
//		}
//		return "Fix something Noah";

	}

}
