package cn.concox.vo.materialManage;

import java.sql.Timestamp;

/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午9:40:34
 * 数据库表名称：t_sale_product_material_file 产品材料附件表
 */
public class InteriorMaterial {	
	private Integer id;
	/**
	 * 字段名称：附件保存在服务器的路径
	 * 数据库字段信息：file_url 
	 */
	private String fileUrl;
	/**
	 * 字段名称：附件名称
	 * 数据库字段信息：file_name 
	 */
	private String fileName;
	/**
	 * 字段名称：文件上传人
	 * 数据库字段信息：create_user 
	 */
	private String createUser;
	/**
	 * 字段名称：文件上传时间
	 * 数据库字段信息：create_time 
	 */
	private Timestamp createTime;	
	/**
	 * 字段名称：更新时间；
	 * 数据库字段信息：update_time 
	 */
	private Timestamp updateTime; 
	/**
	 * 字段名称：更新人
	 * 数据库字段信息：update_user 
	 */
	private String updateUser;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	
	
}
