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

import org.json.JSONException;
import org.json.JSONObject;

public class StorageManager {
	private static URL getStorageURL() {
		try {
			return new URL("file:///" + System.getProperty("user.home") + "\\sockergroda_storage.json");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static File getStorageFile() {
		return new File(getStorageURL().getPath());
	}
	
	public static void createStorageFile() {
		if(!getStorageFile().exists()) {
			try {
				getStorageFile().createNewFile();
				resetFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setFileContents(String content) {
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(getStorageURL().getPath()));
	    	writer.write(content);
	    	writer.close();
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void resetFile() {
		JSONObject defaultSettings = new JSONObject();
		defaultSettings.put("automatic_update_check", true);
		setStorage(defaultSettings);
	}
	
	private static JSONObject getStorage() {
	    StringBuilder fullContents = new StringBuilder();
	    
    	try {
    		InputStream inputStream = getStorageURL().openStream();

    		String tempLine = null;
    		
    		if(inputStream != null) {
    			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    			while((tempLine = reader.readLine()) != null) {
    				fullContents.append(tempLine.trim());
    			}
    		}

    	    inputStream.close();
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
    	
    	try {
    		return new JSONObject(fullContents.toString());
    	} catch(JSONException e) {
    		return new JSONObject();
    	}
	}
	
	private static void setStorage(JSONObject jsonObject) {
		setFileContents(jsonObject.toString());
	}
	
	public static void setAttribute(String key, Object value) {
		JSONObject theStorage = getStorage();
		theStorage.put(key, value);
		setStorage(theStorage);
	}
	
	public static boolean hasAttribute(String key) {
		return getStorage().has(key);
	}
	
	public static boolean getBoolean(String key) {
		return hasAttribute(key) ? getStorage().getBoolean(key) : false;
	}

	public static int getInt(String key) {
		return hasAttribute(key) ? getStorage().getInt(key) : 0;
	}

	public static String getString(String key) {
		return hasAttribute(key) ? getStorage().getString(key) : "";
	}
}
