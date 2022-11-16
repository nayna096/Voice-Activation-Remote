package DexterFam;

import java.io.*;
import java.util.*;
import gnu.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import marytts.modules.synthesis.Voice;
import java.util.concurrent.TimeUnit;

public class VoiceActivationRemote {
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	static OutputStream outputStream;
	private Logger logger = Logger.getLogger(getClass().getName());
	private String result;
	boolean drawbridge = false;
	static TextToSpeech tts = new TextToSpeech();
	static Calendar calendar = Calendar.getInstance();
	static int hour = calendar.get(Calendar.HOUR);
	static int a_p = calendar.get(java.util.Calendar.AM_PM);
	static String am_pm;
//	static String hello = "hello";
	float loudness = 2.0f;{
	if (a_p == 0) {
		am_pm = "AM";
	} else {
		am_pm = "PM";
	}
	if(hour>10&&am_pm.equals("PM")||hour<12&&am_pm.equals("AM")){
		loudness = 1.0f;
	}
	}
	Thread speechThread;
	Thread resourcesThread;
	private LiveSpeechRecognizer recognizer;

	public VoiceActivationRemote() {
		logger.log(Level.INFO, "Loading..\n");
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
		startSpeechThread();
		startResourcesThread();
	}

	protected void startSpeechThread() {
		tts.setVoice("dfki-obadiah-hsmm");
		if (speechThread != null && speechThread.isAlive())
			return;
		speechThread = new Thread(() -> {
			logger.log(Level.INFO, "You can start to speak...\n");
			tts.speak("At your service", loudness, false, true);
			try {
				while (true) {
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {
						result = speechResult.getHypothesis();

						if (result.equals("<unk>")) {
							tts.speak("I beg your pardon sir", loudness, false, false);
							TimeUnit.SECONDS.sleep(1);
						}
						if (result.equalsIgnoreCase("dexter") || result.equalsIgnoreCase("dex")) {
							drawbridge = true;
							tts.speak("Yes sir?", loudness, false, false);
							System.out.println(loudness);
							TimeUnit.SECONDS.sleep(2);
						} else if (drawbridge == true) {
							tts.speak("Of course sir", loudness, false, false);
							System.out.println(loudness);
							TimeUnit.SECONDS.sleep(2);
							keshum(result, loudness);
							if (result.equals("off")) {
								tts.speak("Goodbye sir", loudness, false, false);
							}
							arduinoSender(result);
							drawbridge = false;
						}
					} else
						logger.log(Level.INFO, "I can't understand what you said.\n");
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

	public static void arduinoSender(String result) {
		try {
			outputStream = serialPort.getOutputStream();
			outputStream.write(result.getBytes());
			outputStream.close();

		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		portList = CommPortIdentifier.getPortIdentifiers();
		portId = (CommPortIdentifier) portList.nextElement();
		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			if (portId.getName().equals("COM7")) {
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
		new VoiceActivationRemote();
	}

	@Override
	public void finalize() throws Throwable {
		if (serialPort != null) {
			serialPort.close();
		}
		super.finalize();
	}

	public static void keshum(String result, float loudness) {
		tts.setVoice("dfki-obadiah-hsmm");
		Date now = new Date();
		if (result.equals("nine")) {
			int joke = (int) ((3) * Math.random() + 1);
			if (joke == 1) {
				int style = (int) ((5) * Math.random() + 1);
				switch (style) {
				case 1:
					tts.speak("lol bet", loudness, false, false);
					break;
				case 2:
					tts.speak("Desperate for some comedy sir?", loudness, false, false);
					break;
				case 3:
					tts.speak("What has the orange man said today?", loudness, false, false);
					break;
				case 4:
					tts.speak("What goes down in crazy town?", loudness, false, false);
					break;
				case 5:
					tts.speak("Well then", loudness, false, false);
				}
			}
		} else if (result.equals("five") || result.equals("six") || result.equals("seven") || result.equals("eight")) {
			if ((calendar.get(Calendar.DAY_OF_WEEK)) < 7 && (calendar.get(Calendar.DAY_OF_WEEK)) > 1) {
				if (hour < 4 && hour > 8 && am_pm.equals("PM")) {
					tts.speak("Time for Tim and Sid!", loudness, false, false);
				}
			}

		} else if (result.equals("zero") || result.equals("one") || result.equals("two") || result.equals("three")
				|| result.equals("four")) {
			if (hour > 6 && hour < 11 && am_pm.equals("AM")) {
				tts.speak("Some early morning sports centre sir?", loudness, false, false);

			}
		} else if (result.equals("down")) {
			tts.speak("Time for some X box sir?", loudness, false, false);
		}
	}
}
