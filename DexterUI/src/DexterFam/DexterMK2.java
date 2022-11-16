package DexterFam;

import java.io.File;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import DexterFam.TextToSpeech;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class DexterMK2 {
	static TextToSpeech tts = new TextToSpeech();
	private Logger logger = Logger.getLogger(getClass().getName());
	private String result;
	Thread speechThread;
	Thread resourcesThread;
	private LiveSpeechRecognizer recognizer;
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	static String textLine = "";
	static String resourcesPath = getResourcesPath();
	static Bot bot = new Bot("super", resourcesPath);
	static Chat chatSession = new Chat(bot);
	static String currentState = "sleep";
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	static OutputStream outputStream;
	static String[] commands = new String[29];
	static boolean mode = false;
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
	static String sir = "sir";
	int who = 0;
//	static String response;
//	static Logger friends = Logger.getLogger(response);
	public DexterMK2() {
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		configuration.setGrammarPath("resource:/grammar");
		configuration.setGrammarName("dexterlanguage");
		configuration.setUseGrammar(true);
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		recognizer.startRecognition(true);
		startSpeechThread();
		startResourcesThread();
	}

	protected void startSpeechThread() {
		tts.setVoice("dfki-obadiah-hsmm");
		try {
			MagicBooleans.trace_mode = TRACE_MODE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (speechThread != null && speechThread.isAlive())
			return;
		speechThread = new Thread(() -> {
			tts.speak("At your service", 2.0f, false, true);

			try {
				while (true) {
					if (who == 0) {
						tts.speak("Is this Noah?", 2.0f, false, true);
					}
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {
						textLine = speechResult.getHypothesis();
						if (who == 0) {
							if (textLine.equals("yes")) {
								who += 1;
								sir = "sir";
							} else if (textLine.equals("no")) {
								who += 1;
								sir = "";
							} else {
								who += 0;
							}
							if (textLine.equals("yes") || textLine.equals("no")) {
								tts.speak("Noted", 2.0f, false, true);
							}
						}
						System.out.println("human: " + textLine);
						System.out.println("current State: " + currentState);
						String response = act(currentState, textLine);
						currentState = getNext(currentState, textLine);
						System.out.println("Response: " + response);
						System.out.println("next State: " + currentState);
						if (response != null) {
							tts.speak(response, 2.0f, false, true);
						}

					} else {
						logger.log(Level.INFO, "I can't understand what you said.\n");
					}
				}
			} catch (Exception ex) {
				logger.log(Level.WARNING, null, ex);
			}
			logger.log(Level.INFO, "SpeechThread has exited...");
		});
		speechThread.start();
	}

	private static String act(String currentState, String input) {
		String[] commands = new String[29];
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
		replies[1] = "I'm Dex, hello there";
		replies[2] = "Dexter's the name, AI is the game";
		replies[3] = "Dexter, newest member of the Tedla Aynalem household";
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
		replies[14] = "No ah would be disappointed in your lack of skill";
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
		replies[26] = "Mister I nahlem, but you may call him No ah";
		replies[27] = "No ah Tedla I nahlem";
		replies[28] = "No ah";
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
		replies[39] = "I would love to be your friend! If you wish tell No ah to add some phrases about you to my database";
		replies[40] = "In No ah's house";
		replies[41] = "At No ah's";
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
		confirmation[0] = "Of course " + sir;
		confirmation[1] = "Very well " + sir;
		confirmation[2] = "Not a problem " + sir;
		confirmation[3] = "All right " + sir;
		confirmation[4] = "On it " + sir;
		confirmation[5] = "Will do " + sir;
		confirmation[6] = "Yes " + sir;
		confirmation[7] = "Okay " + sir;
		confirmation[8] = "Affirmative " + sir;
		confirmation[9] = "Absolutely " + sir;
		confirmation[10] = "Sure thing " + sir;
		confirmation[11] = "Duly noted " + sir;
		confirmation[12] = "Got it " + sir;
		confirmation[13] = "Certainly " + sir;
		confirmation[14] = "For sure " + sir;
		int a = ((int) ((15) * Math.random() + 1));
		String response = "";
		if (currentState.equals("sleep")) {
			if (input.equalsIgnoreCase("dex") || input.equalsIgnoreCase("dexter")) {
				response = "shall I wake "+sir+"?";
			} else {
				response = null;
			}
		} else if (currentState.equals("limbo")) {
			if ((input.equals("sleep") || input.equals("no"))) {
				response = confirmation[a - 1] + ", returning to sleep";
			} else if (input.equals("get up") || input.equals("wake up") || input.equals("wake")
					|| input.equals("yes")) {
				response = confirmation[a - 1] + ". What mode do you want me to wake in to?";
				mode = true;
			} else if (input.equals("v") && mode == true) {
				response = confirmation[a - 1] + ". VAR mode is turned on. At your service.";
			} else if (input.equals("general") && mode == true) {
				response = confirmation[a - 1] + ". General mode activated, and I am at your service";
			} else if ((input.equals("sleep") || input.equals("no")) && mode == true) {
				response = confirmation[a - 1] + ". Returning to sleep";
			} else {
				response = "Do you wish for me to wake, or to sleep?";
			}
		} else if (currentState.equals("general")) {
			if (input.equals("v")) {
				response = confirmation[a - 1] + ". Voice Activation Remote initialized sir";
				return response;
			} else if (input.equals("sleep")) {
				response = confirmation[a - 1] + ". Entering sleep, say my name to wake me up sir";
				return response;
			} else {
				int i = 0;
				if (input.contains("who are")) {
					i = 0;
				} else if (input.contains("how old")) {
					i = 1;
				} else if (input.contains("beautiful")) {
					i = 2;
				} else if (input.contains("birthday")) {
					i = 3;
				} else if (input.contains("born")) {
					i = 4;
				} else if (input.contains("boss")) {
					i = 5;
				} else if (input.contains("funny")) {
					i = 6;
				} else if (input.contains("hobby")) {
					i = 7;
				} else if (input.contains("friends")) {
					i = 8;
				} else if (input.contains("where") && input.contains("live")) {
					i = 9;
				} else if (input.contains("where") && input.contains("from")) {
					i = 10;
				} else if (input.contains("where") && input.contains("born")) {
					i = 11;
				} else if (input.contains("job")) {
					i = 12;
				} else if (input.contains("how are you")) {
					i = 13;
				} else if (input.contains("What's up")) {
					i = 14;
				} else if (input.contains("What's good")) {
					i = 15;
				} else if (input.contains("Are you doing")) {
					i = 16;
				} else if (input.contains("nice") && input.contains("meet")) {
					i = 17;
				} else if (input.contains("nice") && input.contains("see")) {
					i = 18;
				}
				if (i == 0 || i == 12) {
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
				} else if (i == 9) {
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
				} else if (i > 9 && i < 12) {
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
				} else if (i > 12 && i < 17) {
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
				} else if (i > 16) {
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
				} else if (i == 7) {
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
				} else if (i > 2 && i < 5) {
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
				} else if (i == 5) {
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
				} else if (i == 6) {
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
				} else if (i == 1) {
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
				} else if (i == 2) {
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
				} else {
					response = replies[39];
//					String name = textLine;
//					friends.log(textLine);
					return response;
				}
			}
			response = "What did you say";
			return response;
		} else if (currentState.equals("VAR")) {

			if (input.equalsIgnoreCase("dex") || input.equalsIgnoreCase("dexter")) {
				response = "Yes "+sir+" ?";
			} else if (input.equals("general")) {
				response = confirmation[a - 1] + ". Returning to general mode";
			} else if (input.equals("sleep")) {
				response = confirmation[a - 1] + ". if you wish to wake me say Dexter";
			} else {
				response = "I beg your pardon?";
			}

		} else if (currentState.equals("VAR limbo")) {
			if (input.equals("<unk>")) {
				response = "I beg your pardon sir";
				return response;
			} else if (input.equals("general")) {
				response = confirmation[a - 1] + ". Heading back to general sir";
				return response;
			} else if (input.equals("sleep")) {
				response = confirmation[a - 1] + ". I shall take a nap, if you wish to wake me say Dex";
				return response;
			} else {
				for (int i = 0; i < 29; i++) {
					if (input.equals(commands[i])) {
						response = confirmation[a - 1] + ". Implementing command " + input+". ";
						arduinoSender(input);
						response = response+" "+ keshum(input);
						return response;
					}
				}
				response = "Sir that is not a command for the VAR";
			}
		}
		return response;

	}

	public static void arduinoSender(String input) {
		try {
			outputStream = serialPort.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.close();
		} catch (IOException e) {
		}
	}

	private static String getNext(String currentState, String input) {
		String[] commands = new String[29];
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
				nextState = "limbo";
			} else if (input.equals("v") && mode == true) {
				nextState = "VAR";
				mode = false;
			} else if (input.equals("general") && mode == true) {
				nextState = "general";
				mode = false;
			} else if ((input.equals("sleep") || input.equals("no")) && mode == true) {
				nextState = "sleep";
				mode = false;
			} else {
				nextState = "limbo";
			}
		} else if (currentState.equals("general")) {
			if (input.equals("v")) {
				nextState = "VAR";
			} else if (input.equals("sleep")) {
				nextState = "sleep";
			} else {
				nextState = "general";
			}
		} else if (currentState.equals("VAR")) {
			if (input.equalsIgnoreCase("dex") || input.equalsIgnoreCase("dexter")) {
				nextState = "VAR limbo";
			} else if (input.equals("general")) {
				nextState = "general";
			} else if (input.equals("sleep")) {
				nextState = "sleep";
			} else {
				nextState = "VAR";
			}
		} else if (currentState.equals("VAR limbo")) {
			if (input.equals("<unk>")) {
				nextState = "VAR limbo";
				return nextState;
			} else if (input.equals("general")) {
				nextState = "general";
				return nextState;
			} else if (input.equals("sleep")) {
				nextState = "sleep";
				return nextState;
			} else {
				for (int i = 0; i < 29; i++) {
					if (input.equals(commands[i])) {
						nextState = "VAR";
						return nextState;
					}
				}
				nextState = "VAR limbo";
			}
		}
		return nextState;
	}

	protected void startResourcesThread() {
		if (resourcesThread != null && resourcesThread.isAlive())
			return;
		resourcesThread = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(350);
				}
			} catch (InterruptedException ex) {
				logger.log(Level.WARNING, null, ex);
				resourcesThread.interrupt();
			}
		});
		resourcesThread.start();
	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

	public static void main(String[] args) {
		portList = CommPortIdentifier.getPortIdentifiers();
		portId = (CommPortIdentifier) portList.nextElement();
		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			if (portId.getName().equals("COM3")) {
				try {
					serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
					Thread.sleep(2500);
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
				} catch (PortInUseException e) {
					System.out.println("err");
				} catch (UnsupportedCommOperationException e) {
					System.out.println("err2");
				} catch (InterruptedException e) {
				}
			}
		}
		new DexterMK2();
	}

	@Override
	public void finalize() throws Throwable {
		if (serialPort != null) {
			serialPort.close();
		}
		super.finalize();
	}

	public static String keshum(String result) {
		tts.setVoice("dfki-obadiah-hsmm");
		String quip = "";
		Date now = new Date();
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
			if ((calendar.get(Calendar.DAY_OF_WEEK)) < 7 && (calendar.get(Calendar.DAY_OF_WEEK)) > 1) {
				if (hour < 4 && hour > 8 && am_pm.equals("PM")) {		
					quip = "Time for Tim and Sid!";
				}
			}
		} else if (result.equals("zero") || result.equals("one") || result.equals("two") || result.equals("three")
				|| result.equals("four")) {
			if (hour > 6 && hour < 11 && am_pm.equals("AM")) {
				quip = "Some early morning sports centre "+sir+" ?";
			}
		} else if (result.equals("in down")) {
			quip = "Time for some X box "+sir+" ?";
		} else if (result.equals("off")) {
			quip = "Goodbye "+sir;
		}
		return quip;
	}
}
