package cn.concox.vo.rolePrivilege;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleMenus implements Serializable {

	/**
	 * t_oa_roler_menus
	 */
	private static final long serialVersionUID = 1L;

	private Integer rolerId;
	
	private Integer parentId;
	
	private Integer menuId;
	
	private Integer functionId;
	
	private String creater;
	
	private Date creatTime;
	
	private Integer orderindex;
	
	public RoleMenus(){
		
	}
	
	public RoleMenus(Integer rolerId,Integer parentId,Integer menuId,Integer functionId,String creater){
		this.rolerId=rolerId;
		this.parentId=parentId;
		this.menuId=menuId;
		this.functionId=functionId;
		this.creater=creater;
	}

	public Integer getRolerId() {
		return rolerId;
	}

	public void setRolerId(Integer rolerId) {
		this.rolerId = rolerId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex = orderindex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
