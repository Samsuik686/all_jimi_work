package cn.concox.vo.customProcess;

import java.sql.Timestamp;

/**
 * 异常反馈 附件
 *  table t_sale_ycfkmanage_file
 * @author TangYuping
 * @version 2017年4月14日 上午11:02:36
 */
public class CustomTaskFile {
	
	private Integer fid;
	private Integer yid;  //对应异常反馈ID
	private String fileName; //附件名称
	private String fileUrl; //附件路径 
	private String createUser;
	private Timestamp createTime;
	private String fileSite; //文件上传工站
	
	public CustomTaskFile(){}
	
	public CustomTaskFile(Integer yid, String fileName, String fileUrl, String createUser, 
			Timestamp createTime, String fileSite){
		this.yid = yid;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.createUser = createUser;
		this.createTime = createTime;
		this.fileSite = fileSite;
	}
	
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getYid() {
		return yid;
	}
	public void setYid(Integer yid) {
		this.yid = yid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getFileSite() {
		return fileSite;
	}
	public void setFileSite(String fileSite) {
		this.fileSite = fileSite;
	}
	
	

}
