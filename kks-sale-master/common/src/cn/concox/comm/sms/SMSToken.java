package cn.concox.comm.sms;

import cn.concox.comm.util.MD5Util;

public class SMSToken {
	
	public String appId ;            		
	
	public String sign ;
	
	public String time;
	
	public String valid ;
	
	public String url;
	
	public SMSToken(){
		
	}
	
	public SMSToken(String appId,String token,String url,String valid){
		String Ttime = System.currentTimeMillis()+"";
		this.appId=appId;
		this.sign= MD5Util.md5Digest(Ttime+token);
		this.time =Ttime;
		this.valid=valid;
		this.url = url;
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	
	
}
