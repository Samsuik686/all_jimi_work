package cn.concox.vo.rolePrivilege;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleMgt implements Serializable {

	/**
	 * 角色实体Bean
	 */
	private static final long serialVersionUID = 1L;

	private Integer rolerId;
	
	private String rolerName;
	
	private String rolerDesc;
	
	private String creater;

	private Date creatTime;
	
	private Integer orderIndex;
	
	private List<RoleMenus> roleMenus;
	
	public List<RoleMenus> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<RoleMenus> roleMenus) {
		this.roleMenus = roleMenus;
	}

	public Integer getRolerId() {
		return rolerId;
	}

	public void setRolerId(Integer rolerId) {
		this.rolerId = rolerId;
	}

	public String getRolerName() {
		return rolerName;
	}

	public void setRolerName(String rolerName) {
		this.rolerName = rolerName;
	}
	
	public String getRolerDesc() {
		return rolerDesc;
	}

	public void setRolerDesc(String rolerDesc) {
		this.rolerDesc = rolerDesc;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
