package sockergroda;

import javax.swing.JOptionPane;

public class Main {
	public static String version = "v1.0.0";
	
	public static void main(String[] args) {
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
}
