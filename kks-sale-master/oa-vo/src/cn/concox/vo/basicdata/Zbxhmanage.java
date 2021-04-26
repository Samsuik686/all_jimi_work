package cn.concox.vo.basicdata;

import java.io.Serializable;

/**
 * <pre>
 * 主板型号管理表实体类
 * 数据库表名称：t_sale_zbxhmanage
 * </pre>
 */
public class Zbxhmanage implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 字段名称：主板型号ID
	 * 
	 * 数据库字段信息:mId int(4)，主键，自增长
	 */
	private Integer mId;

	private Integer gId;

	/**
	 * 字段名称：主板型号（售后型号）
	 * 
	 * 数据库字段信息:model VARCHAR(100)
	 */
	private String model;
	
	/**
	 * 字段名称：市场型号（销售型号）
	 * 
	 * 数据库字段信息:marketModel VARCHAR(100)
	 */
	private String marketModel;

	/**
	 * 字段名称：主板型号类别（车载 、个人、 手机 等）
	 * 
	 * 数据库字段信息:modelType VARCHAR(100)
	 */
	private String modelType;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;
	
	private String marketModelAccept;//受理时ams系统有市场型号对应多个主板型号时使用
	
	private String showType;//受理不显示禁用数据
	
	private Integer createType;//创建类型id

	private String createTypeName;//创建类型名字
	
	private Integer enabledFlag;//是否禁用

	public Zbxhmanage() {
	}
	public String getCreateTypeName(){
		return createTypeName;
	}
	public void setCreateTypeName(String createTypeName){
		this.createTypeName = createTypeName;
	}

	public Integer getMId() {
		return mId;
	}

	public void setMId(Integer mId) {
		this.mId = mId;
	}

	public Integer getGId() {
		return gId;
	}

	public void setGId(Integer gId) {
		this.gId = gId;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMarketModelAccept() {
		return marketModelAccept;
	}

	public void setMarketModelAccept(String marketModelAccept) {
		this.marketModelAccept = marketModelAccept;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Override
	public String toString() {
		return "Zbxhmanage{" +
				"mId=" + mId +
				", gId=" + gId +
				", model='" + model + '\'' +
				", marketModel='" + marketModel + '\'' +
				", modelType='" + modelType + '\'' +
				", remark='" + remark + '\'' +
				", marketModelAccept='" + marketModelAccept + '\'' +
				", showType='" + showType + '\'' +
				", createType=" + createType +
				", createTypeName='" + createTypeName + '\'' +
				", enabledFlag=" + enabledFlag +
				'}';
	}
}