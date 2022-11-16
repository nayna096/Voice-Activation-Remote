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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class DexterMK1 {
	static TextToSpeech tts = new TextToSpeech();
	private Logger logger = Logger.getLogger(getClass().getName());
	private String result;
	Thread speechThread;
	Thread resourcesThread;
	private LiveSpeechRecognizer recognizer;
	
	private LiveSpeechRecognizer recognizer_2;
	
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	static String textLine = "";
//	static String resourcesPath = getResourcesPath();
//	static Bot bot = new Bot("super", resourcesPath);
//	Chat chatSession = new Chat(bot);
	
	public DexterMK1() {
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		configuration.setGrammarPath("resource:/grammar");
		configuration.setGrammarName("dexterlanguage");
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
			
			
//			recognizer_2 = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		recognizer.startRecognition(true);
//		recognizer_2.startRecognition(true);
		startSpeechThread();
		startResourcesThread();
	}

	protected void startSpeechThread() {
		tts.setVoice("dfki-obadiah-hsmm");
		try {
			MagicBooleans.trace_mode = TRACE_MODE;
		//	bot.brain.nodeStats();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (speechThread != null && speechThread.isAlive())
			return;
		speechThread = new Thread(() -> {
			tts.speak("At your service", 2.0f, false, true);
			try {
				while (true) {
				//	try {
						SpeechResult speechResult = recognizer.getResult();
						if (speechResult != null) {
							textLine = speechResult.getHypothesis();
							System.out.println("human: " + textLine);
							if(textLine.equals("go to VAR")||textLine.equals("hey")) {
								
								recognizer = null;
								
								
								Configuration configuration = new Configuration();
								configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
								configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
								configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
								configuration.setGrammarPath("resource:/grammar");
								configuration.setGrammarName("counting");
								configuration.setUseGrammar(true);
								try {
									recognizer = new LiveSpeechRecognizer(configuration);
								} catch (IOException ex) {
									logger.log(Level.SEVERE, null, ex);
								}
								recognizer.startRecognition(true);
								
								
							
//								recognizer.stopRecognition();
//								VoiceActivationRemote.main(new String[0]);
								System.out.println("W");
							}
//							if ((textLine == null) || (textLine.length() < 1))
//								textLine = MagicStrings.null_input;
//							if (textLine.equals("q")) {
//								System.exit(0);
//							} else if (textLine.equals("wq")) {
////								bot.writeQuit();
//								System.exit(0);
//							}
//							String request = textLine;
//							if (MagicBooleans.trace_mode)
//								System.out.println("STATE=" + request + ":THAT="
////										+ ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC="
////										+ chatSession.predicates.get("topic"));
////
////							String response = chatSession.multisentenceRespond(request);
//
//							while (response.contains("&lt;"))
//								response = response.replace("&lt;", "<");
//							while (response.contains("&gt;"))
//								response = response.replace("&gt;", ">");
//							tts.speak(response, 2.0f, false, true);
//							System.out.println("machine: " + response);
						} else {
							logger.log(Level.INFO, "I can't understand what you said.\n");
						}
			//		} catch (NoSuchFieldError e) {
				//		tts.speak("I didn't catch that, could you repeat it please", 2.0f, false, true);
					//	System.out.println("erred");
					//}

				}
			} catch (Exception ex) {
				logger.log(Level.WARNING, null, ex);
			}
			logger.log(Level.INFO, "SpeechThread has exited...");
		});
		speechThread.start();
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

//	private static String getResourcesPath() {
//		File currDir = new File(".");
//		String path = currDir.getAbsolutePath();
//		path = path.substring(0, path.length() - 2);
//		System.out.println(path);
//		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
//		return resourcesPath;
//	}

	public static void main(String[] args) {
		new DexterMK1();
	}
}
