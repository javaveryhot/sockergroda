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
		URL requestUrl = new URL(Main.hostNameAPI + "/create_secret");
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
		jsonInput.append("author", Main.ipAddress);
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

	public static JSONObject inspectSecret(long secretId, String password) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/inspect_secret");
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

	public static JSONObject checkSecret(long secretId) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/check_secret");
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
		URL requestUrl = new URL(Main.hostNameAPI + "/grab_advertisement");
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
		URL requestUrl = new URL(Main.hostNameAPI + "/click_advertisement");
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
		URL requestUrl = new URL(Main.hostNameAPI + "/grab_version_data");
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
		URL requestUrl = new URL(Main.hostNameAPI + "/check_rakey");
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
		URL requestUrl = new URL(Main.hostNameAPI + "/delete_secret");
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
		URL requestUrl = new URL(Main.hostNameAPI + "/grab_metadata");
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
			URL requestUrl = new URL(Main.hostNameAPI + "/connection_test");
			HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			connection.setConnectTimeout(15000);
			
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
	
	public static JSONObject isAddressOk() throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/address_tester");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("address", Main.ipAddress);
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
	
	/**
	 * Reports a secret.
	 * @param secretId
	 * @param password
	 * @return The integer code result of the request.<br>Invalid secret (0), bad password (1), duplicate request (2) and success (3).
	 * @throws IOException
	 */
	public static int reportSecret(int secretId, String password) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/report_content");
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
		    return new JSONObject(fullResponse.toString()).getInt("code");
		}
	}
	
	public static boolean moderatorLogin(String moderatorKey) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/moderator_login");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("mod_key", moderatorKey);
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
		    return new JSONObject(fullResponse.toString()).getInt("code") == 1;
		}
	}
	
	public static JSONObject moderatorGrabReports(String moderatorKey) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/moderator_grab_reports");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("mod_key", moderatorKey);
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
	
	public static boolean moderatorDenyReport(String moderatorKey, int reportId) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/moderator_deny_report");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("mod_key", moderatorKey);
		jsonInput.append("report_id", reportId);
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
		    return new JSONObject(fullResponse.toString()).getInt("code") == 1;
		}
	}
	
	public static boolean moderatorConfirmReport(String moderatorKey, int reportId, String reason) throws IOException {
		URL requestUrl = new URL(Main.hostNameAPI + "/moderator_confirm_report");
		HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		
		JSONObject jsonInput = new JSONObject();
		jsonInput.append("mod_key", moderatorKey);
		jsonInput.append("report_id", reportId);
		jsonInput.append("reason", reason);
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
		    return new JSONObject(fullResponse.toString()).getInt("code") == 1;
		}
	}
}
