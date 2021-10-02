package sockergroda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import utils.VersionConverter;

public class Main {
	public static int versionInt = 103;
	public static String version = VersionConverter.intToString(versionInt); // Only for visuals! Do not use for detection
	public static String downloadUrl = "https://github.com/javaveryhot/sockergroda/releases";
	
	public static void main(String[] args) {
		createSockergrodaDataFile();
		
		MainWindow.display();
		if(!hasInternetConnection()) {
			JOptionPane.showMessageDialog(null, "Could not connect to the internet.\nMake sure that you have an internet connection and try again.\nIt is required in order to use Sockergroda.", "No Internet Access", JOptionPane.ERROR_MESSAGE);
		}
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
	
	private static URL getSockergrodaDataURL() {
		try {
			return new URL("file:///" + System.getProperty("user.home") + "\\sockergrodadata.txt");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void createSockergrodaDataFile() {
		File dataFile = new File(getSockergrodaDataURL().getPath());
		try {
			dataFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean hasRemovedAds() {
	    String content = null;
	    
    	try {
    		InputStream inputStream = getSockergrodaDataURL().openStream();

    		if(inputStream != null) {
    			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    			content = reader.readLine();
    		}

    	    inputStream.close();
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
    	
    	return content != null ? content.equals("1") : false;
	}
	
	public static void removeAds() {
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(getSockergrodaDataURL().getPath()));
	    	writer.write("1");
	    	writer.close();
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	}
}
