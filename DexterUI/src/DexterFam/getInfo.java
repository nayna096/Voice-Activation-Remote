package DexterFam;

public class getInfo {
	static String FirstName = "";
	static String LastName = "";
	static String gender = "";
	static String birthday = "";
	static String phonenumber = "";
	static String phonenumbertype = "";
	static String nickname = "";
	static String instruct = "";
	static int a = 0;

	public static String setFirstName(String input) {
		if (a == 0 && FirstName == "") {
			a++;
			System.out.println(a);
			instruct = "Firstly, spell out your first name. When you are finished, say stop";
			return instruct;
		}
		while (!(input.equals("done")) && a == 1) {
			if (input.length() == 1) {
				FirstName = FirstName + input;
				System.out.println(FirstName);
			} else {
				return "Use letters to spell your name, not words you doofus";
			}
			return FirstName;
		}
		if (input.equals("done")) {
			a++;
			return "So is your first name " + FirstName;
		}
		if (a == 2) {
			if (input.equals("no")) {
				a--;
				return "Let's try that again then";
			} else if (input.equals("yes")) {
				a++;
				return "Great! Let's move on to your last name.";
			} else {
				return "I beg your pardon?";
			}
		}
		return "Fix something Noah";
	}

	public String setLastName(String input) {
		return LastName;
	}

	public String setGender(String input) {
		return gender;
	}

	public String setBirth(String input) {
		return birthday;
	}

	public String setPhone(String input) {
		return phonenumber;
	}

	public String setType(String input) {
		return phonenumbertype;
	}
}
