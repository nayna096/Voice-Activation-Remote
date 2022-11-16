package Dexter;
import java.io.File;
import Dexter.getNext;
import Dexter.getReply;
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
public class VoiceActivationRemote {
		static TextToSpeech tts = new Dexter.TextToSpeech();
		private static Logger logger = Logger.getLogger("VoiceActivationRemote");
		static Thread speechThread;
		static Thread resourcesThread;
		private static LiveSpeechRecognizer recognizer;
		private static final boolean TRACE_MODE = false;
		static String botName = "super";
		static String textLine = "";
		static String resourcesPath = getResourcesPath();
		static String currentState = "sleep";
		static Enumeration portList;
		static CommPortIdentifier portId;
		static SerialPort serialPort;
		static OutputStream outputStream;
		static String[] commands = new String[29];
		static protected void startSpeechThread() {
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
							System.out.println("current State: " + currentState);
							String response = getReply.act(currentState, textLine);
							currentState = getNext.Getnext(currentState, textLine);
							if (currentState.equals("on")) {
								arduinoSender(textLine);
							}
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

		public static void arduinoSender(String input) {
			try {
				outputStream = serialPort.getOutputStream();
				outputStream.write(input.getBytes());
				outputStream.close();
			} catch (IOException e) {
			}
		}

		static protected void startResourcesThread() {
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
			Configuration configuration = new Configuration();
			configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
			configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
			configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
			configuration.setGrammarPath("resource:/grammar");
			configuration.setGrammarName("VAR");
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

		@SuppressWarnings("deprecation")
		@Override
		public void finalize() throws Throwable {
			if (serialPort != null) {
				serialPort.close();
			}
			super.finalize();
		}
	

}
