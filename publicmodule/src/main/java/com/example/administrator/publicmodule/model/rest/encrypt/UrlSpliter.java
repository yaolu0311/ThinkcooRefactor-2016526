package com.example.administrator.publicmodule.model.rest.encrypt;


import com.example.administrator.publicmodule.util.log.ThinkcooLog;

public class UrlSpliter {
	
	
	public static final String TAG = "UrlSpliter";
	
	public static final String ACTION_PART = "action/";
	private String rawUrl;
	private boolean splitSuccessed;
	private String baseUrl;
	private String encryptUrl;
	private String timeString;
	
	public UrlSpliter(String rawUrl) {
		this.rawUrl = rawUrl;
	}

	public void doSplit(){
		
		if(rawUrlIsEmpty()){
			return;
		}
		try{
			
			useRawUrlCalculateBaseUrl();
			useRawUrlAndBaseUrlCalculateEncryptUrl();
			addTimeStringToEncrypUrl();
			
			splitSuccessed = true;
		}catch(Exception e){
			ThinkcooLog.e(TAG, e.getMessage());
		}
	}
	
	private void useRawUrlAndBaseUrlCalculateEncryptUrl() {
		encryptUrl = rawUrl.replaceAll(baseUrl, "");
	}

	private void useRawUrlCalculateBaseUrl() {
		
		String tempUrl = rawUrl.replaceFirst("//", "/");
		String [] httpUrlParts = tempUrl.split("/");
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(httpUrlParts[0]);
		stringBuilder.append("//");
		stringBuilder.append(httpUrlParts[1]);
		stringBuilder.append("/");
		stringBuilder.append(httpUrlParts[2]);
		stringBuilder.append("/");
		
		baseUrl = stringBuilder.toString().trim();
	}

	private void addTimeStringToEncrypUrl() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getCurrentTimeString());
		stringBuilder.append("/");
		stringBuilder.append(encryptUrl);
		
		encryptUrl = stringBuilder.toString();
	}
	
	public String getCurrentTimeString(){
		
		if (null == timeString || timeString.length() == 0) {
			return String.valueOf(System.currentTimeMillis());
		}
		return timeString;
	}
	
	//for test
	public void setTimeString(String timeString){
		this.timeString = timeString;
	}

	public String getBaseUrl(){
		return baseUrl;
	}
	
	public String getToBeEncryptionUrl(){
		return encryptUrl;
	}

	private boolean rawUrlIsEmpty(){
		return null == rawUrl || rawUrl.trim().length() == 0;
	}
	
	public boolean isSplitSuccessed() {
		return splitSuccessed;
	}
	
}
