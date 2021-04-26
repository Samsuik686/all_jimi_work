package cn.concox.vo.system;

import java.io.Serializable;
import java.util.List;

public class MenuDetails implements Serializable {

	private static final long serialVersionUID = 5355923107637933217L;
	
   
	
	private Integer menuId;
	
	private Integer functionId;
	
	private String functionURL;
	
	private String functionDesc;
	
	private String creater;
	
	private String creatTime;
	
	 private Integer orderindex;
	
	private List<MenuDetails> children;

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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex = orderindex;
	}

	public List<MenuDetails> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDetails> children) {
		this.children = children;
	}

	
}
