package cn.concox.vo.materialManage;

import java.sql.Timestamp;

/**
 * 
 * 数据库表名称：t_sale_product_material_log 下载日志记录表
 */
public class ProductMaterialFileLog {

	private Integer id;

	private String ip; // IP

	private String userName;// 下载人

	private String fileName; // 文件名

	private String fileType; // 类型

	private Timestamp downloadTime;// 下载时间
	
	private String startTime;//开始时间
    
	private String endTime;//结束时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Timestamp getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Timestamp downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}