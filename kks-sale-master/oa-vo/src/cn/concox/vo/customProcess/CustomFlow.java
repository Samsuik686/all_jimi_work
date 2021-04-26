package cn.concox.vo.customProcess;

import java.util.Date;

public class CustomFlow {

	private String name; // 名称 唯一
	
	private String remark; // 备注
	
	private String xml; // xml文档
	
	private String createUser; // 创建人
	
	private Date createDate; // 创建时间
	
	// 0.生效 1.失效 保留字段
	private Integer status; // 状态
	
	// 页面权限id
	private Integer menuId;
	
	private String fields;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

}
