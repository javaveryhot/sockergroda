package sockergroda;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sockergroda.enums.Images;
import utils.APIResponse;
import utils.VersionConverter;

public class Main {
	public static int versionInt = 110;
	public static String versionName = VersionConverter.intToString(versionInt); // Only for visuals! Do not use for detection
	public static String version = VersionConverter.intToString(versionInt, false);
	public static String websiteUrl = "https://sockergroda.repl.co";
	public static String downloadUrl = "https://sockergroda.repl.co/download";
	public static String helpUrl = "https://sockergroda.repl.co/help";
	public static String reportIssueUrl = "https://github.com/javaveryhot/sockergroda/issues";
	public static String gitHubUrl = "https://github.com/javaveryhot/sockergroda";
	
	public static String hostNameAPI;

	public static String ipAddress;
	
	public static boolean launchedAlready = false;
	public static long lastCopyAttempt = 0;
	
	public static void main(String[] args) {
		fixUI();

		InitWindow initializationWnd = InitWindow.display();
		
		initializationWnd.setStatus("Setting up storage file", 15);
		
		StorageManager.createStorageFile();
		
		initializationWnd.setStatus("Checking internet connection", 30);

		if(!hasInternetConnection()) {
			JOptionPane.showMessageDialog(null, "Could not connect to the internet.\nMake sure that you have an internet connection.\nIt is required in order to use Sockergroda.", "No Internet Access", JOptionPane.ERROR_MESSAGE);
		}
		
		initializationWnd.setStatus("Finding internet protocol", 45);
		
		if((ipAddress = getIPAddress()) == null) {
			JOptionPane.showMessageDialog(null, "Cannot get a valid internet protocol.", "Invalid Internet Protocol", JOptionPane.ERROR_MESSAGE);
		}
		
		initializationWnd.setStatus("Checking API host server", 60);

		updateAPIHost();
		
		initializationWnd.setStatus("Connecting to the API", 70);
		
		if(APIManager.testSockergrodaAPI().getCode() != 1) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the Sockergroda API!\nThe server may be under maintenance.", "No API Access", JOptionPane.ERROR_MESSAGE);
		}
		
		initializationWnd.setStatus("Confirming client address", 80);
		
		APIResponse addressTest = APIManager.isAddressOk();
		if(addressTest.isRequestSuccessful() && addressTest.getCode() == 0) {
			JOptionPane.showMessageDialog(null, "You are banned from the Sockergroda API for abuse.\nYou cannot access this service anymore.\nReason: " + addressTest.getStringValue("reason"), "Bad Client Access", JOptionPane.ERROR_MESSAGE);
			initializationWnd.dispose();
			return;
		}
		
		initializationWnd.setStatus("Setting up copy listener", 90);

		if(!launchedAlready) {
			FlavorListener clipboardListener = new FlavorListener() {
				@Override
				public void flavorsChanged(FlavorEvent e) {
					try {
						String newClipboard = ((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).trim();
						if(newClipboard.startsWith("<sg?") && newClipboard.endsWith(">") && System.currentTimeMillis() > Main.lastCopyAttempt + 5000) {
							Main.updateLastCopyAttempt();
							InspectSecretWindow.display(null, true);
						}
					} catch (HeadlessException | UnsupportedFlavorException | IOException | IllegalStateException e1) {}
				}
			};
			
			Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(clipboardListener);
			
			launchedAlready = true;
		}
		
		initializationWnd.setStatus("Launching", 100);

		if(!StorageManager.getBoolean("pre_usage_warning_confirmed")) {
			PreUsageWarningWindow.display();
		} else {
			MainWindow.display();
			
			if(versionInt > StorageManager.getInt("latest_version_used")) {
				StorageManager.setAttribute("latest_version_used", versionInt);
				int dialogResult = JOptionPane.showConfirmDialog(null, "Welcome to Sockergroda " + versionName + ".\nDo you want to see the changelog for this version?", "Welcome!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Images.ICON_16x16.getImage()));
				if(dialogResult == 0) {
					ChangeLogWindow.display();
				}
			} else if(Math.floor(Math.random() * 10) < 2 && !StorageManager.getBoolean("offered_feedback")) {
				FeedbackWindow.display();
				StorageManager.setAttribute("offered_feedback", true);
			}
		}
		
		initializationWnd.dispose();
	}
	
	public static boolean hasInternetConnection() {
		try {
			String host = "www.google.com", command = "";
			command = "ping -" + (System.getProperty("os.name").startsWith("Windows") ? "n" : "c") + " 1 " + host;
			Process pingProcess = Runtime.getRuntime().exec(command);
			pingProcess.waitFor();
			return pingProcess.exitValue() == 0;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean hasRemovedAds() {
		return StorageManager.getBoolean("remove_ads");
	}
	
	public static void removeAds() {
		StorageManager.setAttribute("remove_ads", true);
	}
	
	public static void openURL(String url) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(new URI(url));
			} catch(IOException | URISyntaxException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Could not open the web page.", "Browser Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void displayUpdate(boolean onlyIfAvailable) {
		APIResponse globalVersionData = APIManager.grabVersionData();
		int latestVersion = globalVersionData.getIntValue("latest_version");
		if(latestVersion > Main.versionInt || !onlyIfAvailable) {
			UpdateWindow.display(versionInt, latestVersion);
		}
	}
	
	public static void updateLastCopyAttempt() {
		lastCopyAttempt = System.currentTimeMillis();
	}
	
	public static void copyToClipboard(String text) {
		updateLastCopyAttempt();
		StringSelection copyString = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyString, copyString);
	}
	
	public static void updateAPIHost() {
		if(StorageManager.hasAttribute("override_api_host")) {
			hostNameAPI = StorageManager.getString("override_api_host");
		} else {
			hostNameAPI = "https://api.sockergrodaapi.repl.co";
		}
	}
	
	public static String getIPAddress() {
		try {
			URL ipGetter = new URL("http://checkip.amazonaws.com");
			return new BufferedReader(new InputStreamReader(ipGetter.openStream())).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void fixUI() {
		try {
			String preferredLookAndFeel = StorageManager.getString("preferred_look_and_feel");
			UIManager.setLookAndFeel(preferredLookAndFeel.equals("") ? UIManager.getSystemLookAndFeelClassName() : preferredLookAndFeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
