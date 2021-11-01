package utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIResponse {
	private JSONObject theResponse;
	
	public APIResponse(JSONObject response) {
		this.theResponse = response;
	}
	
	public boolean isRequestSuccessful() {
		return this.theResponse != null;
	}
	
	public JSONObject getRealResponse() {
		return this.theResponse;
	}
	
	public int getCode() {
		return this.isRequestSuccessful() ? this.theResponse.getInt("code") : 0;
	}
	
	public String getStringValue(String key) {
		return this.theResponse.getString(key);
	}
	
	public int getIntValue(String key) {
		return this.theResponse.getInt(key);
	}

	public long getLongValue(String key) {
		return this.theResponse.getLong(key);
	}

	public boolean getBooleanValue(String key) {
		return this.theResponse.getBoolean(key);
	}

	public JSONArray getJSONArrayValue(String key) {
		return this.theResponse.getJSONArray(key);
	}

	public JSONObject getJSONObjectValue(String key) {
		return this.theResponse.getJSONObject(key);
	}
}
