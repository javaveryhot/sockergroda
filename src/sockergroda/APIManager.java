package sockergroda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIManager {
	public static JSONObject createSecret(String freeText, String password, String title, long expiration, int expirationType) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/create_secret");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);

		JSONObject jsonInput = new JSONObject();
		jsonInput.append("freetext", freeText);
		jsonInput.append("password", password);
		jsonInput.append("title", title);
		jsonInput.append("expire", expiration);
		jsonInput.append("expire_type", expirationType);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString());
		}
	}

	public static JSONObject inspectSecret(int secretId, String password) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/inspect_secret");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("id", secretId);
		jsonInput.append("password", password);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString());
		}
	}

	public static JSONObject checkSecret(int secretId) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/check_secret");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("id", secretId);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString());
		}
	}

	public static JSONObject grabAdvertisement() throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/grab_advertisement");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString());
		}
	}
	
	public static void registerAdvertisementClicked(String id) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/click_advertisement");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("id", id);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); // Waits for the server to receive the request
	}
	
	public static JSONObject grabVersionData() throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/grab_version_data");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString());
		}
	}
	
	public static boolean testRAKey(int key) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/check_rakey");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("key", key);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString()).getBoolean("valid");
		}
	}
	
	public static void deleteSecret(String id, String ownerKey) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/delete_secret");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("id", Integer.parseInt(id));
		jsonInput.append("owner_key", ownerKey);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); // Waits for the server to receive the request
	}
	
	public static JSONObject grabStackedMetadata(Map<String, Object> stackedMap) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/grab_metadata");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("stacked_secrets", stackedMap);
		String jsonInputString = jsonInput.toString();
		
		try(OutputStream outputStream = connection.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    outputStream.write(input, 0, input.length);
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONObject(fullResponse.toString());
		}
	}
	
	public static JSONArray grabJSONArrayGet(String url) throws IOException {
		URL requestUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		try(BufferedReader bufferedReader = new BufferedReader(
				  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
		    StringBuilder fullResponse = new StringBuilder();
		    String tempResponse = null;
		    while((tempResponse = bufferedReader.readLine()) != null) {
		    	fullResponse.append(tempResponse.trim());
		    }
		    return new JSONArray(fullResponse.toString());
		}
	}
	
	public static boolean testSockergrodaAPI() {
		try {
			URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/connection_test");
			HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			
			try(BufferedReader bufferedReader = new BufferedReader(
					  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
			    StringBuilder fullResponse = new StringBuilder();
			    String tempResponse = null;
			    while((tempResponse = bufferedReader.readLine()) != null) {
			    	fullResponse.append(tempResponse.trim());
			    }
			    return new JSONObject(fullResponse.toString()).getInt("code") == 1;
			}
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
