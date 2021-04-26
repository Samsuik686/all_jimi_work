package cn.concox.comm.util.http;

import java.util.Map;

/**
 * 
 * @author 20160614
 *
 */
public class RequestParam {
	
	private String bodys;

	//请求url
	private  String url;
	
	//请求方法 
	private String method;
	
	//请求头内容
	private Map<String, String> headers;
	
	//附带参数
	private Map<String, String> params;
	
	//编码格式
	private String encoding; 
	
	//文件目录
	private String filepath ; 
	
	private boolean duan;

	public String getBodys() {
		return bodys;
	}

	public void setBodys(String bodys) {
		this.bodys = bodys;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isDuan() {
		return duan;
	}

	public void setDuan(boolean duan) {
		this.duan = duan;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
	
}
