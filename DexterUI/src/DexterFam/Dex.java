package DexterFam;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import DexterFam.TextToSpeech;
import java.util.concurrent.TimeUnit;
public class Dex {
	static String [] greeting = new String[9];{
	greeting[0] = "Hello "+who;
	greeting[1] = "Greetings "+who+", at your service";
	greeting[2] = "Hello" +who+", how are you?";
	greeting[3] = "Greetings "+who;
	greeting[4] = "How's your day gone "+who+"?";
	greeting[5] = "Good day "+who;
	greeting[6] = "How are you doing "+who+"?";
	greeting[7] = "How are you "+who+"?";
	greeting[8] = "";}
	static String who;
	boolean whom = false;{
	if(whom==true) {
		who = "";
	}else {
		who = "sir";
	}}
	boolean greet = false;
	static TextToSpeech tts = new TextToSpeech();
	static Calendar calendar = Calendar.getInstance();
	static int hour = calendar.get(Calendar.HOUR);
	static int a_p = calendar.get(java.util.Calendar.AM_PM);
	static String am_pm;
	float loudness = 2.0f;{
	if (a_p == 0) {
		am_pm = "AM";
	} else {
		am_pm = "PM";
	}}{
	if(hour>6&&am_pm.equals("AM")) {
		greeting[8] = "Good morning"+who;
	}else if(hour==12||hour<6&&am_pm.equals("PM")) {
		greeting[8] = "Good afternoon"+who;
	}else {
		greeting[8] = "Good evening"+who;
	}
	}
	int rand = (int)(9*Math.random());
	private Logger logger = Logger.getLogger(getClass().getName());
	private String result;
	Thread speechThread;
	Thread resourcesThread;
	private LiveSpeechRecognizer recognizer;
	public Dex() {
	logger.log(Level.INFO, "Loading..\n");
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
	if (speechThread != null && speechThread.isAlive())
		return;
	speechThread = new Thread(() -> {
		
		try {
			tts.speak("At your service", loudness, false, false);
			TimeUnit.SECONDS.sleep(2);
			tts.speak("Is this Noah?", loudness, false, false);
			SpeechResult verification = recognizer.getResult();
			String verify = verification.getHypothesis();
			if(verify.equalsIgnoreCase("yes")) {
				whom=true;
			}else{
				whom=false;
			}
			while (greet==false) {
				TimeUnit.SECONDS.sleep(2);
				tts.speak(greeting[rand], loudness, false, false);
				greet = true;
//				logger.log(Level.INFO, "I can't understand what you said.\n");
			}
//			while (true) {
//				SpeechResult speechResult = recognizer.getResult();
//				if (speechResult != null) {
//					result = speechResult.getHypothesis();
//					System.out.println("You said: "+result+"\n");
//				} else
//					logger.log(Level.INFO, "I can't understand what you said.\n");
//			}
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
public static void main(String[] args) {
	new Dex();
}
}
