package cn.concox.vo.customProcess;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <pre>
 * 实体类
 * 数据库表名称：t_sale_custom_task 自定义流程任务表
 * </pre>
 */
public class CustomTask {
	/**
	 * 字段名称：id
	 *
	 * 
	 * 数据库字段信息:backId INT(11)
	 */
	private Integer id;
	
	/**
	 * 流水号
	 */
	private String serial;
	
	/**
	 * 来源名称
	 */
	private String fromModel;
	
	/**
	 * 所属流程
	 */
	private String flowName;
	
	/**
	 * 所处模块
	 */
	private String modelId;	

	/**
	 * 字段名称：反馈人
	 * 
	 * 数据库字段信息:feedBackPerson VARCHAR(40)
	 */
	private String creater;
	
	/**
	 * 字段名称：跟进人
	 * 
	 * 数据库字段信息:feedBackPerson VARCHAR(40)
	 */
	private String follower;
	
	/**
	 * 更新人
	 */
	private String updater;

	/**
	 * 字段名称：反馈日期
	 * 
	 * 数据库字段信息:feedBackDate DATE(10)
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDate;

	/**
	 * 字段名称：更新时间
	 * 
	 * 数据库字段信息:completionDate DATE(10)
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateDate;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remak VARCHAR(32)
	 */
	private String remark;

	/**
	 * 字段名称：状态0:待解决，1:已完成，
	 * 
	 * 数据库字段信息:completionState INT(10)
	 */
	private Integer state;
	
	/*
	 * =================================业务字段
	 * start===============================
	 */
	private String startTime;// 开始时间

	private String endTime;// 结束时间
	
	private String treeDate;
	
	private String modelName;

	private List<CustomTaskFile> attachFileList; // 对应的附件信息
	
	// 自定义字段数据json
	private String params;
	
	/**
	 * 字段名称：过期时间
	 */
	private String expireDateStr;
	
	/**
	 * 字段名称：过期时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date expireDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public List<CustomTaskFile> getAttachFileList() {
		return attachFileList;
	}

	public void setAttachFileList(List<CustomTaskFile> attachFileList) {
		this.attachFileList = attachFileList;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getFromModel() {
		return fromModel;
	}

	public void setFromModel(String fromModel) {
		this.fromModel = fromModel;
	}

	public String getTreeDate() {
		return treeDate;
	}

	public void setTreeDate(String treeDate) {
		this.treeDate = treeDate;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getExpireDateStr() {
		return expireDateStr;
	}

	public void setExpireDateStr(String expireDateStr) {
		this.expireDateStr = expireDateStr;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}
