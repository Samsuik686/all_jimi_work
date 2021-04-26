package cn.concox.vo.rolePrivilege;

import java.io.Serializable;

public class FunctionDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer menuId;
	
	private Integer functionId;
	
	private String functionURL;
	
	private String functionDesc;

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

	public String getFunctionURL() {
		return functionURL;
	}

	public void setFunctionURL(String functionURL) {
		this.functionURL = functionURL;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
