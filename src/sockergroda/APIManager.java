package sockergroda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class APIManager {
	public static int createSecret(String freeText, String password, long expiration) throws IOException {
		URL requestUrl = new URL("https://api.sockergrodaapi.repl.co/create_secret");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);

		JSONObject jsonInput = new JSONObject();
		jsonInput.append("freetext", freeText);
		jsonInput.append("password", password);
		jsonInput.append("expire", expiration);
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
		    JSONObject jsonResponse = new JSONObject(fullResponse.toString());
		    if((int)jsonResponse.get("code") != 1) {
		    	return 0;
		    }
		    return (int)jsonResponse.get("secret_id");
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
}
