package Dexter;

import java.io.File;
import Dexter.TextToSpeech;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class Lights {
	static TextToSpeech tts = new Dexter.TextToSpeech();
	private Logger logger = Logger.getLogger(getClass().getName());
	Thread speechThread;
	Thread resourcesThread;
	private LiveSpeechRecognizer recognizer;
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	static String textLine = "";
	static String resourcesPath = getResourcesPath();
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	static OutputStream outputStream;
	static String sir = "sir";
	static String[] confirmation = new String[12];
	{
		confirmation[0] = "Of course ";
		confirmation[1] = "Very well ";
		confirmation[2] = "Alright ";
		confirmation[3] = "On it ";
		confirmation[4] = "Will do ";
		confirmation[5] = "Yes ";
		confirmation[6] = "Okay ";
		confirmation[7] = "Absolutely ";
		confirmation[8] = "Sure thing ";
		confirmation[9] = "Got it ";
		confirmation[10] = "Certainly ";
		confirmation[11] = "For sure ";
	}
	static int a = ((int) ((12) * Math.random() + 1));

	public Lights() {

		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		configuration.setGrammarPath("resource:/grammar");
		configuration.setGrammarName("lights");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (speechThread != null && speechThread.isAlive())
			return;
		speechThread = new Thread(() -> {
			tts.speak("At your service", 2.0f, false, true);
			try {
				while (true) {
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {
						textLine = speechResult.getHypothesis();
						System.out.println("human: " + textLine);
						System.out.println(sir);
						if(textLine.equals("not noah")&&sir.equals("sir")) {
							sir = " ";
							tts.speak("Hello stranger", 2.0f, false, true);
						}
						if(textLine.equals("noah")&sir.equals(" ")) {
							sir = "sir";
							tts.speak("Welcome back sir", 2.0f, false, true);
						}
						System.out.println(sir);
						if (textLine.contains("dex") || textLine.contains("dexter")) {
							tts.speak(confirmation[a - 1]+sir, 2.0f, false, true);
							arduinoSender(textLine);
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

	public static void arduinoSender(String input) {
		try {
			outputStream = serialPort.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.close();
		} catch (IOException e) {
		}
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

		new Lights();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void finalize() throws Throwable {
		if (serialPort != null) {
			serialPort.close();
		}
		super.finalize();
	}

}
