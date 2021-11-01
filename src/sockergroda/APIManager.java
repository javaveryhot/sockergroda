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

import utils.APIResponse;
import utils.FastJSON;

public class APIManager {
	private static APIResponse doAPIRequest(String name, FastJSON parameters, boolean abandon) {
		try {
			URL requestUrl = new URL(Main.hostNameAPI + "/" + name);
			HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			
			if(parameters != null) {
				try(OutputStream outputStream = connection.getOutputStream()) {
				    byte[] input = parameters.produce().toString().getBytes("utf-8");
				    outputStream.write(input, 0, input.length);
				}
			}
			
			try(BufferedReader bufferedReader = new BufferedReader(
					  new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				if(!abandon) {
				    StringBuilder fullResponse = new StringBuilder();
				    String tempResponse = null;
				    while((tempResponse = bufferedReader.readLine()) != null) {
				    	fullResponse.append(tempResponse.trim());
				    }
				    return new APIResponse(new JSONObject(fullResponse.toString()));
				} else {
					return null;
				}
			} catch(IOException e) {
				e.printStackTrace();
				return new APIResponse(null);
			}
		} catch(IOException e) {
			e.printStackTrace();
			return new APIResponse(null);
		}
	}
	
	public static APIResponse createSecret(String freeText, String password, String title, long expiration, int expirationType) {
		return doAPIRequest("create_secret", new FastJSON(new Object[] {
			"freetext", freeText,
			"password", password,
			"title", title,
			"expire", expiration,
			"expire_type", expirationType,
			"author", Main.ipAddress
		}), false);
	}

	public static APIResponse inspectSecret(long secretId, String password) {
		return doAPIRequest("inspect_secret", new FastJSON(new Object[] {
			"id", secretId,
			"password", password
		}), false);
	}

	public static APIResponse checkSecret(long secretId) {
		return doAPIRequest("check_secret", new FastJSON(new Object[] {
			"id", secretId
		}), false);
	}

	public static APIResponse grabAdvertisement() {
		return doAPIRequest("grab_advertisement", null, false);
	}
	
	public static void registerAdvertisementClicked(String id) {
		doAPIRequest("click_advertisement", new FastJSON(new Object[] {
			"id", id
		}), true);
	}
	
	public static APIResponse grabVersionData() {
		return doAPIRequest("grab_version_data", null, false);
	}
	
	public static boolean testRAKey(int key) {
		return doAPIRequest("check_rakey", new FastJSON(new Object[] {
			"key", key
		}), false).getBooleanValue("valid");
	}
	
	public static APIResponse deleteSecret(int id, String ownerKey) {
		return doAPIRequest("delete_secret", new FastJSON(new Object[] {
			"id", id,
			"owner_key", ownerKey
		}), false);
	}
	
	public static APIResponse grabStackedMetadata(Map<String, Object> stackedMap) {
		return doAPIRequest("grab_metadata", new FastJSON(new Object[] {
			"stacked_secrets", stackedMap
		}), false);
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
	
	public static APIResponse testSockergrodaAPI() {
		return doAPIRequest("connection_test", null, false);
	}
	
	public static APIResponse isAddressOk() {
		return doAPIRequest("address_tester", new FastJSON(new Object[] {
			"address", Main.ipAddress
		}), false);
	}
	
	public static APIResponse reportSecret(int secretId, String password) {
		return doAPIRequest("report_content", new FastJSON(new Object[] {
			"id", secretId,
			"password", password
		}), false);
	}
	
	public static APIResponse moderatorLogin(String moderatorKey) {
		return doAPIRequest("moderator_login", new FastJSON(new Object[] {
			"mod_key", moderatorKey
		}), false);
	}
	
	public static APIResponse moderatorGrabReports(String moderatorKey) {
		return doAPIRequest("moderator_grab_reports", new FastJSON(new Object[] {
			"mod_key", moderatorKey
		}), false);
	}
	
	public static APIResponse moderatorDenyReport(String moderatorKey, int reportId) {
		return doAPIRequest("moderator_deny_report", new FastJSON(new Object[] {
			"mod_key", moderatorKey,
			"report_id", reportId
		}), false);
	}
	
	public static APIResponse moderatorConfirmReport(String moderatorKey, int reportId, String reason) {
		return doAPIRequest("moderator_confirm_report", new FastJSON(new Object[] {
			"mod_key", moderatorKey,
			"report_id", reportId,
			"reason", reason
		}), false);
	}
	
	public static APIResponse grabGlobalStats() {
		return doAPIRequest("grab_global_stats", null, false);
	}
}
