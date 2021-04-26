package cn.concox.vo.materialManage;

import java.sql.Timestamp;

/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午9:40:34
 * 数据库表名称：t_sale_product_material_file 产品材料附件表
 */
public class ProductMaterialFile {	
	private Integer fid;
	/**
	 * 字段名称：产品材料表关联id
	 * 数据库字段信息：pid 
	 */
	private Integer pid;
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
	 * 字段名称：附件表类型
	 * 数据库字段信息：file_type
	 */
	private String fileType;
	
	private String deleteType; //删除标示，区分根据pid删除还是，附件ID
	
	private Integer fileTypeCout;//附件类型数量
	
	private String model;
	
	private String projectName;//项目
	
	public ProductMaterialFile(){}

	
	public ProductMaterialFile(ProductMaterialVo vo){
		this.pid = vo.getId();
		this.fileUrl = vo.getFileUrl();
		this.fileName = vo.getFileName();
		this.createTime =  vo.getCreateTime();
		this.createUser = vo.getCreateUser();
		this.fileType = vo.getFileType();
	}
	
	
	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public Integer getFileTypeCout() {
		return fileTypeCout;
	}


	public void setFileTypeCout(Integer fileTypeCout) {
		this.fileTypeCout = fileTypeCout;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
}
