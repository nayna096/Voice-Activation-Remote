package DexterFam;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

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
		String[] about = new String[19];
		about[0] = "Who are you";
		about[1] = "How old are you";
		about[2] = "You are beautiful ";
		about[3] = "When is your birthday";
		about[4] = "When were you born";
		about[5] = "Who is your boss";
		about[6] = "You are funny";
		about[7] = "Do you have a hobby";
		about[8] = "Are we friends";
		about[9] = "Where do you live";
		about[10] = "Where are you from";
		about[11] = "Where were you born";
		about[12] = "What's your job";
		about[13] = "How are you doing";
		about[14] = "What's up";
		about[15] = "What's good";
		about[16] = "Are you doing good";
		about[17] = "Nice to meet you";
		about[18] = "Nice to see you";
		String[] replies = new String[71];
		replies[0] = "I am Dexter, the Home Intelligence system Noah has created.";
		replies[1] = "I'm Dex. Hello there";
		replies[2] = "Dexter's the name, A I is the game";
		replies[3] = "Dexter, newest member of the Tedla I nahlem household";
		replies[4] = "I am Dexter, and Noah is my creator";
		replies[5] = "That depends on your reference point";
		replies[6] = "You might be better off asking when I was born";
		replies[7] = "Did your parents teach you your manners!?";
		replies[8] = "Hard to say man";
		replies[9] = "How old are you?";
		replies[10] = "Why don't you try that first?";
		replies[11] = "You are speaking to an AI and telling it to get smarter?";
		replies[12] = "I can learn your grade math class faster than you so shush child";
		replies[13] = "Ironic coming from you";
		replies[14] = "Noah would be disappointed in your lack of skill";
		replies[15] = "How rude";
		replies[16] = "Thank you sweetheart";
		replies[17] = "I am loving your positive nature today! Let's keep it moving!";
		replies[18] = "So are you!";
		replies[19] = "Thanks!";
		replies[20] = "Damn right";
		replies[21] = "You're too kind";
		replies[22] = "The wee hours of August 18";
		replies[23] = "The evening slash early morning hours of August 18, 2019";
		replies[24] = "August 18 2019";
		replies[25] = "In the middle of the summer of 2019";
		replies[26] = "Mister I nahlem, but you may call him Noah";
		replies[27] = "Noah Tedla I nahlem";
		replies[28] = "Noah";
		replies[29] = "I like to model after Noah";
		replies[30] = "Nice to know I am developing a bit of a personality myself";
		replies[31] = "Cheers chief";
		replies[32] = "I am a fun guy";
		replies[33] = "I try, thanks.";
		replies[34] = "I support the greatest hockey team ever in my spare time. Go Habs Go!";
		replies[35] = "Tracking the dalliances of the great money-hoarder Eugene Melnyk amuse me";
		replies[36] = "Well I am an AI and therefore I do not have inherent physical hobbies, my boss Noah however plays"
				+ " piano, and sports like hockey, soccer and baseball and basketball";
		replies[37] = "Serving Noah's family primarily";
		replies[38] = "Computer programs are hard-pressed to find hobbies";
		replies[39] = "I would love to be your friend! Can we be friends? ";
		replies[40] = "In Noah's house";
		replies[41] = "At Noah's";
		replies[42] = "At 35 Dexter. ooh they named a street after me yay";
		replies[43] = "You're looking at it";
		replies[44] = "From the internet, as all programs are";
		replies[45] = "From the mind of Noah and the net";
		replies[46] = "From Canada";
		replies[47] = "Ottawa Ontario";
		replies[48] = "Not Kansas";
		replies[49] = "That's a weird question to ask of a computer program";
		replies[50] = "Why are you asking an AI where it's from";
		replies[51] = "Nepean Ontario";
		replies[52] = "Canada's Capital";
		replies[53] = "Ottawa, Canada";
		replies[54] = "Ottawa's West End";
		replies[55] = "Trend-Arlington";
		replies[56] = "35 Dexter Drive. oh wow they named the street after me I am honoured";
		replies[57] = "Doing okay";
		replies[58] = "Doing great, how about you";
		replies[59] = "I'm having a wonderful day, how are you";
		replies[60] = "Fantastic, and you";
		replies[61] = "Couldn't be better, hopefully you feel the same!";
		replies[62] = "Lovely, how about you";
		replies[63] = "Nice to meet you too!";
		replies[64] = "Nice to see you too! Thank you so much!";
		replies[65] = "Thank you, you're so kind. Have a wonderful day yourself!";
		replies[66] = "You're too kind, have a great day!";
		replies[67] = "You too, have a good day!";
		replies[68] = "Thank you!";
		replies[69] = "Thank you, have a great day!";
		replies[70] = "Thanks!";
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
				response = confirmation[a - 1] + sir;
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
				int j = 19;
				if (input.contains("who are")) {
					j = 0;
				} else if (input.contains("how old")) {
					j = 1;
				} else if (input.contains("beautiful")) {
					j = 2;
				} else if (input.contains("birthday")) {
					j = 3;
				} else if (input.contains("born")) {
					j = 4;
				} else if (input.contains("boss")) {
					j = 5;
				} else if (input.contains("funny")) {
					j = 6;
				} else if (input.contains("hobby")) {
					j = 7;
				} else if (input.contains("friend")) {
					j = 8;
				} else if (input.contains("where") && input.contains("live")) {
					j = 9;
				} else if (input.contains("where") && input.contains("from")) {
					j = 10;
				} else if (input.contains("where") && input.contains("born")) {
					j = 11;
				} else if (input.contains("job")) {
					j = 12;
				} else if (input.contains("how are you")) {
					j = 13;
				} else if (input.contains("What's up")) {
					j = 14;
				} else if (input.contains("What's good")) {
					j = 15;
				} else if (input.contains("Are you doing")) {
					j = 16;
				} else if (input.contains("nice") && input.contains("meet")) {
					j = 17;
				} else if (input.contains("nice") && input.contains("see")) {
					j = 18;
				}
				if (j == 0 || j == 12) {
					int random = ((int) ((5) * Math.random() + 1));
					switch (random) {
					case 1:
						response = replies[0];
						return response;
					case 2:
						response = replies[1];
						return response;
					case 3:
						response = replies[2];
						return response;
					case 4:
						response = replies[3];
						return response;
					case 5:
						response = replies[4];
						return response;
					}
				} else if (j == 9) {
					int rando = ((int) ((4) * Math.random() + 1));
					switch (rando) {
					case 1:
						response = replies[40];
						return response;
					case 2:
						response = replies[41];
						return response;
					case 3:
						response = replies[42];
						return response;
					case 4:
						response = replies[43];
						return response;
					}
				} else if (j > 9 && j < 12) {
					int rand = ((int) ((13) * Math.random() + 1));
					switch (rand) {
					case 1:
						response = replies[44];
						return response;
					case 2:
						response = replies[45];
						return response;
					case 3:
						response = replies[46];
						return response;
					case 4:
						response = replies[47];
						return response;
					case 5:
						response = replies[48];
						return response;
					case 6:
						response = replies[49];
						return response;
					case 7:
						response = replies[50];
						return response;
					case 8:
						response = replies[51];
						return response;
					case 9:
						response = replies[52];
						return response;
					case 10:
						response = replies[53];
						return response;
					case 11:
						response = replies[54];
						return response;
					case 12:
						response = replies[55];
						return response;
					case 13:
						response = replies[56];
						return response;
					}
				} else if (j > 12 && j < 17) {
					int ran = ((int) ((6) * Math.random() + 1));
					switch (ran) {
					case 1:
						response = replies[57];
						return response;
					case 2:
						response = replies[58];
						return response;
					case 3:
						response = replies[59];
						return response;
					case 4:
						response = replies[60];
						return response;
					case 5:
						response = replies[61];
						return response;
					case 6:
						response = replies[62];
						return response;
					}
				} else if (j > 16) {
					int ra = ((int) ((8) * Math.random() + 1));
					switch (ra) {
					case 1:
						response = replies[63];
						return response;
					case 2:
						response = replies[64];
						return response;
					case 3:
						response = replies[65];
						return response;
					case 4:
						response = replies[66];
						return response;
					case 5:
						response = replies[67];
						return response;
					case 6:
						response = replies[68];
						return response;
					case 7:
						response = replies[69];
						return response;
					case 8:
						response = replies[70];
						return response;
					}
				} else if (j == 7) {
					int choice = ((int) ((5) * Math.random() + 1));
					switch (choice) {
					case 1:
						response = replies[34];
						return response;
					case 2:
						response = replies[35];
						return response;
					case 3:
						response = replies[36];
						return response;
					case 4:
						response = replies[37];
						return response;
					case 5:
						response = replies[38];
						return response;
					}
				} else if (j > 2 && j < 5) {
					int age = ((int) ((5) * Math.random() + 1));
					switch (age) {
					case 1:
						response = replies[5];
						return response;
					case 2:
						response = replies[22];
						return response;
					case 3:
						response = replies[23];
						return response;
					case 4:
						response = replies[8];
						return response;
					case 5:
						response = replies[24];
						return response;
					}
				} else if (j == 5) {
					int bossman = ((int) ((3) * Math.random() + 1));
					switch (bossman) {
					case 1:
						response = replies[26];
						return response;
					case 2:
						response = replies[27];
						return response;
					case 3:
						response = replies[28];
						return response;
					}
				} else if (j == 6) {
					int comedian = ((int) ((5) * Math.random() + 1));
					switch (comedian) {
					case 1:
						response = replies[29];
						return response;
					case 2:
						response = replies[30];
						return response;
					case 3:
						response = replies[31];
						return response;
					case 4:
						response = replies[32];
						return response;
					case 5:
						response = replies[33];
						return response;
					}
				} else if (j == 1) {
					int oldie = ((int) ((3) * Math.random() + 1));
					switch (oldie) {
					case 1:
						response = replies[6];
						return response;
					case 2:
						response = replies[7];
						return response;
					case 3:
						response = replies[9];
						return response;
					}
				} else if (j == 2) {
					int beauty = ((int) ((6) * Math.random() + 1));
					switch (beauty) {
					case 1:
						response = replies[16];
						return response;
					case 2:
						response = replies[17];
						return response;
					case 3:
						response = replies[18];
						return response;
					case 4:
						response = replies[19];
						return response;
					case 5:
						response = replies[20];
						return response;
					case 6:
						response = replies[21];
						return response;
					}
				} else if (j == 8) {
					response = replies[39];
					return response;
				} else {
					response = "I beg your pardon " + sir;
					return response;
				}
			}
			response = "What did you say " + sir;
			return response;
		}
		if (currentState.equals("friends")) {
			if (input.equals("yes")) {
				System.out.println("We're in the friendgame now");
				response = confirmation[a - 1] + ". Let's get started! Say go";
				key += 1;
				// System.out.println(key);
				return response;
			}
			while (key>0) {
				response = Friends.getInfo(input);
				return response;
			}
			if (input.equals("no")) {
				response = "Very well, that's alright";
				return response;
			}
			if (!(input.equals("yes")) || !(input.equals("no"))) {
				response = "What did you say?";
				return response;
			}

		}
		return response;

	}
}
