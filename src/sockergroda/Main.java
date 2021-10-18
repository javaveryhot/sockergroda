package sockergroda;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.JSONObject;

import utils.VersionConverter;

public class Main {
	public static int versionInt = 107;
	public static String versionName = VersionConverter.intToString(versionInt); // Only for visuals! Do not use for detection
	public static String version = VersionConverter.intToString(versionInt, false);
	public static String downloadUrl = "https://sockergroda.repl.co/download";
	public static String helpUrl = "https://sockergroda.repl.co/help";
	public static String reportIssueUrl = "https://github.com/javaveryhot/sockergroda/issues";
	public static String gitHubUrl = "https://github.com/javaveryhot/sockergroda";
	
	public static long lastCopyAttempt = 0;
	
	public static void main(String[] args) {
		StorageManager.createStorageFile();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		if(!StorageManager.getBoolean("pre_usage_warning_confirmed")) {
			PreUsageWarningWindow.display();
		} else {
			MainWindow.display();
		}
		
		if(!hasInternetConnection()) {
			JOptionPane.showMessageDialog(null, "Could not connect to the internet.\nMake sure that you have an internet connection and try again.\nIt is required in order to use Sockergroda.", "No Internet Access", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		FlavorListener clipboardListener = new FlavorListener() {
			@Override
			public void flavorsChanged(FlavorEvent e) {
				try {
					String newClipboard = ((String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).trim();
					if(newClipboard.startsWith("<sg?") && newClipboard.endsWith(">") && System.currentTimeMillis() > Main.lastCopyAttempt + 5000) {
						Main.updateLastCopyAttempt();
						InspectSecretWindow.display(null, true);
					}
				} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(clipboardListener);
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
    	try {
    		JSONObject globalVersionData = APIManager.grabVersionData();
    		int latestVersion = globalVersionData.getInt("latest_version");
    		if(latestVersion > Main.versionInt || !onlyIfAvailable) {
    			UpdateWindow.display(versionInt, latestVersion);
    		}
    	} catch(IOException e) {
    		e.printStackTrace();
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
}
