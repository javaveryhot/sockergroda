package utils;

import org.json.JSONObject;

public class FastJSON {
	private Object[] keysValues;
	
	public FastJSON(Object[] parallelKeysValues) {
		this.keysValues = parallelKeysValues;
	}
	
	public JSONObject produce() {
		JSONObject finishedObject = new JSONObject();

		for(int i = 0; i < keysValues.length; i++) {
			if(i % 2 != 0) { // The current object index is a value
				String theKey = keysValues[i - 1].toString();
				Object theValue = keysValues[i];
				finishedObject.append(theKey, theValue);
			}
		}

		return finishedObject;
	}
}
