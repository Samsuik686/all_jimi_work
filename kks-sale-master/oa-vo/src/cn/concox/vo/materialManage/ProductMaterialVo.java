package cn.concox.vo.materialManage;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class ProductMaterialVo {
	private Integer id;
	private String projectName;//项目名称
	private String model;//主板型号
	private String marketModel;//市场型号
	private String productName;//产品名称
	private String proNum;//编码
	private String proType;//类别
	private String proChildType;//子类别
	private String trademark;//品牌
	private String series;//系列
	private String proColor;//颜色
	private String customerName;//客户名称
	private Timestamp createTime;
	private String createUser;//创建人
	private Timestamp updateTime;
	private String updateUser;

	// 产品资料附件
	private List<ProductMaterialFile> fileList = new ArrayList<ProductMaterialFile>();
		
	private String fileType; //区分附件来源
	private String fileUrl;   //公用的url和name
	private String fileName;
	
	public ProductMaterialVo(){}
	public ProductMaterialVo(ProductMaterial product){
		this.id = product.getId();
		this.projectName = product.getProjectName();
		this.model = product.getModel();
		this.marketModel = product.getMarketModel();
		this.productName = product.getProductName();
		this.proNum = product.getProNum();
		this.proType = product.getProType();
		this.proChildType = product.getProChildType();
		this.trademark = product.getTrademark();
		this.series = product.getSeries();
		this.proColor = product.getProColor();
		this.customerName = product.getCustomerName();
		this.createTime = product.getCreateTime();
		this.createUser = product.getCreateUser();
		this.updateTime = product.getUpdateTime();
		this.updateUser = product.getUpdateUser();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMarketModel() {
		return marketModel;
	}
	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProNum() {
		return proNum;
	}
	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getProChildType() {
		return proChildType;
	}
	public void setProChildType(String proChildType) {
		this.proChildType = proChildType;
	}
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getProColor() {
		return proColor;
	}
	public void setProColor(String proColor) {
		this.proColor = proColor;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	public List<ProductMaterialFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<ProductMaterialFile> fileList) {
		this.fileList = fileList;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	
	
	
}
