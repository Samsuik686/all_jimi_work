package cn.concox.vo.materialManage;

import java.sql.Timestamp;

/**
 * @author TangYuping
 * @version 2017年3月28日 上午9:38:26
 * 数据库表名称：product_material 产品材料表
 */
public class ProductMaterial {
	
	private Integer id;
	/**
	 * 字段名称：项目名称；
	 * 数据库字段信息：project_name 
	 */
	private String projectName;
	/**
	 * 字段名称：主板型号；
	 * 数据库字段信息：model 
	 */ 
	private String model;
	/**
	 * 字段名称：市场型号；
	 * 数据库字段信息：market_model 
	 */
	private String marketModel;
	/**
	 * 字段名称：产品名称；
	 * 数据库字段信息：product_name 
	 */
	private String productName;
	/**
	 * 字段名称：编码；
	 * 数据库字段信息：pro_num 
	 */
	private String proNum;
	/**
	 * 字段名称：类别；
	 * 数据库字段信息：pro_type
	 */
	private String proType;
	/**
	 * 字段名称：子类别；
	 * 数据库字段信息：pro_child_type 
	 */
	private String proChildType;
	/**
	 * 字段名称：品牌；
	 * 数据库字段信息：trademark 
	 */
	private String trademark;
	/**
	 * 字段名称：系列；
	 * 数据库字段信息：series 
	 */
	private String series;
	/**
	 * 字段名称：颜色；
	 * 数据库字段信息：pro_color 
	 */
	private String proColor;
	/**
	 * 字段名称：客户名称；
	 * 数据库字段信息：customer_name 
	 */
	private String customerName;
	/**
	 * 字段名称：创建时间；
	 * 数据库字段信息：create_time 
	 */
	private Timestamp createTime;
	/**
	 * 字段名称：创建人；
	 * 数据库字段信息：create_user 
	 */
	private String createUser;
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
	
	
	private String project;//项目
	private Integer bomCount;//BOM表
	private Integer dianzibomCount;//电子料BOM表
	private Integer repairCount;//维修手册
	private Integer differenceCount;//差异说明
	private Integer projectRemarkCount;//备注附件
	
	private String product;//产品
	private Integer softwareCount;//软件
	private Integer explainCount;//说明书
	private Integer kfCount;//客服手册
	private Integer softDifCount;//软件功能差异表
	private Integer datumCount;//产品资料
	private Integer huaceCount;//产品画册
	private Integer functionCount;//功能列表
	private Integer zhilingCount;//指令表
	private Integer productRemarkCount;//备注附件
	
	private String quality;//品质
	private Integer zuzhuangCount;//组装作业指导书
	private Integer testCount;//测试作业指导书
	private Integer testToolCount;//测试工具
	private Integer xiehaoTooCount;//写号工具
	private Integer qualityRemarkCount;//备注附件
	
	private String startTime;//开始时间
	    
	private String endTime;//结束时间
	
	
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
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getBomCount() {
		return bomCount;
	}
	public void setBomCount(Integer bomCount) {
		this.bomCount = bomCount;
	}
	public Integer getDianzibomCount() {
		return dianzibomCount;
	}
	public void setDianzibomCount(Integer dianzibomCount) {
		this.dianzibomCount = dianzibomCount;
	}
	public Integer getRepairCount() {
		return repairCount;
	}
	public void setRepairCount(Integer repairCount) {
		this.repairCount = repairCount;
	}
	public Integer getDifferenceCount() {
		return differenceCount;
	}
	public void setDifferenceCount(Integer differenceCount) {
		this.differenceCount = differenceCount;
	}
	public Integer getProjectRemarkCount() {
		return projectRemarkCount;
	}
	public void setProjectRemarkCount(Integer projectRemarkCount) {
		this.projectRemarkCount = projectRemarkCount;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getSoftwareCount() {
		return softwareCount;
	}
	public void setSoftwareCount(Integer softwareCount) {
		this.softwareCount = softwareCount;
	}
	public Integer getExplainCount() {
		return explainCount;
	}
	public void setExplainCount(Integer explainCount) {
		this.explainCount = explainCount;
	}
	public Integer getKfCount() {
		return kfCount;
	}
	public void setKfCount(Integer kfCount) {
		this.kfCount = kfCount;
	}
	public Integer getSoftDifCount() {
		return softDifCount;
	}
	public void setSoftDifCount(Integer softDifCount) {
		this.softDifCount = softDifCount;
	}
	public Integer getDatumCount() {
		return datumCount;
	}
	public void setDatumCount(Integer datumCount) {
		this.datumCount = datumCount;
	}
	public Integer getHuaceCount() {
		return huaceCount;
	}
	public void setHuaceCount(Integer huaceCount) {
		this.huaceCount = huaceCount;
	}
	public Integer getFunctionCount() {
		return functionCount;
	}
	public void setFunctionCount(Integer functionCount) {
		this.functionCount = functionCount;
	}
	public Integer getZhilingCount() {
		return zhilingCount;
	}
	public void setZhilingCount(Integer zhilingCount) {
		this.zhilingCount = zhilingCount;
	}
	public Integer getProductRemarkCount() {
		return productRemarkCount;
	}
	public void setProductRemarkCount(Integer productRemarkCount) {
		this.productRemarkCount = productRemarkCount;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public Integer getZuzhuangCount() {
		return zuzhuangCount;
	}
	public void setZuzhuangCount(Integer zuzhuangCount) {
		this.zuzhuangCount = zuzhuangCount;
	}
	public Integer getTestCount() {
		return testCount;
	}
	public void setTestCount(Integer testCount) {
		this.testCount = testCount;
	}
	public Integer getTestToolCount() {
		return testToolCount;
	}
	public void setTestToolCount(Integer testToolCount) {
		this.testToolCount = testToolCount;
	}
	public Integer getXiehaoTooCount() {
		return xiehaoTooCount;
	}
	public void setXiehaoTooCount(Integer xiehaoTooCount) {
		this.xiehaoTooCount = xiehaoTooCount;
	}
	public Integer getQualityRemarkCount() {
		return qualityRemarkCount;
	}
	public void setQualityRemarkCount(Integer qualityRemarkCount) {
		this.qualityRemarkCount = qualityRemarkCount;
	}
}
