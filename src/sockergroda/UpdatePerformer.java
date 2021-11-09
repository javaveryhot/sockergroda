package sockergroda;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.APIResponse;

public class UpdatePerformer {
	private String preferredFileType;
	
	private boolean hasFinished = false;
	private boolean isSuccessful = false;
	
	private boolean launchOnFinish;

	private String abortReason;
	
	private String currentOperation;
	private int currentOperationPercentage;
	
	private OperationListener onDoneListener;
	private OperationListener onOperationChangeListener;
	
	public UpdatePerformer(String preferredFileType) {
		this.preferredFileType = preferredFileType;
		this.currentOperation = "No operation is currently running";
		this.currentOperationPercentage = 0;
	}
	
	public void beginUpdate() {
		this.setOperation("Grabbing the latest version data", 20);
		
		APIResponse response = APIManager.grabVersionData();

		if(!response.isRequestSuccessful()) {
    		this.abortUpdate("Cannot connect to the Sockergroda API.");
    		return;
		}

		int latestVersion = response.getIntValue("latest_version");
		if(latestVersion <= Main.versionInt) {
			this.abortUpdate("No update is available.");
			return;
		}    	
    	
    	try {
    		this.setOperation("Checking the releases", 40);

    		String assetsUrl = APIManager.grabJSONArrayGet("https://api.github.com/repos/javaveryhot/sockergroda/releases").getJSONObject(0).getString("assets_url");

			this.setOperation("Grabbing the assets from the latest version", 50);

			JSONArray assets = APIManager.grabJSONArrayGet(assetsUrl);
			String selectedDownloadName = null;
			String selectedDownloadUrl = null;
			
			for(int i = 0; i < assets.length(); i++) {
				JSONObject thisAsset = assets.getJSONObject(i);
				if(thisAsset.getString("content_type").equals(preferredFileType)) {
					selectedDownloadName = thisAsset.getString("name");
					selectedDownloadUrl = thisAsset.getString("browser_download_url");
					break;
				}
			}
			
			if(selectedDownloadUrl != null) {
				this.setOperation("Downloading " + selectedDownloadName, 70);

				try(InputStream in = new URL(selectedDownloadUrl).openStream()) {
					Path downloadPath = Paths.get(System.getProperty("user.dir") + File.separator + selectedDownloadName);
					Files.copy(in, downloadPath);
					
					if(this.launchOnFinish) {
						this.setOperation("Initiating the new version");
						
						if(preferredFileType == "application/java-archive") {
							ProcessBuilder executeNewFileBuilder = new ProcessBuilder("java", "-jar", downloadPath.toString());
							executeNewFileBuilder.start();
						} else if(preferredFileType == "application/x-msdownload") {
							Runtime.getRuntime().exec(downloadPath.getFileName().toString(), null, new File(System.getProperty("user.dir")));
						}
					}
					
					this.isSuccessful = true;
					this.hasFinished = true;
					this.onDoneListener.onPerformed();
				}
			} else {
				this.abortUpdate("No matching asset file was found.");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.abortUpdate("Cannot download the update. The version may already exist on your computer!");
		}
	}
	
	public void abortUpdate(String reason) {
		this.abortReason = reason;
		this.hasFinished = true;
		this.isSuccessful = false;
		this.onDoneListener.onPerformed();
	}
	
	public String getAbortReason() {
		return this.abortReason;
	}
	
	public boolean hasFinished() {
		return this.hasFinished;
	}
	
	public boolean isSuccessful() {
		return this.isSuccessful;
	}
	
	public String getCurrentOperation() {
		return this.currentOperation;
	}
	
	public int getCurrentOperationPercentage() {
		return this.currentOperationPercentage;
	}
	
	private void setOperation(String context, int percentage) {
		this.currentOperation = context;
		this.currentOperationPercentage = percentage;
		this.onOperationChangeListener.onPerformed();
	}
	
	private void setOperation(String context) {
		this.setOperation(context, getCurrentOperationPercentage());
	}
	
	public void setLaunchOnFinish(boolean shouldLaunchOnFinish) {
		this.launchOnFinish = shouldLaunchOnFinish;
	}
	
	public void setDoneListener(OperationListener listener) {
		this.onDoneListener = listener;
	}
	
	public void setOperationChangeListener(OperationListener listener) {
		this.onOperationChangeListener = listener;
	}
}
