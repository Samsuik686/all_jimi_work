package cn.concox.vo.system;

import java.io.Serializable;
import java.util.List;


public class Menus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5355923107637946217L;

	private Integer menuId;
	
	private Integer parentId;
	
	private String displayName;
	
	private String url;
	
	private String descrp;
	
	private String sn;
 
	private String orderindex;
	
	private List<Menus> children;
	
	public String getOrderindex(){
		return orderindex;
	}
	
	public void setOrderindex(String orderindex){
		this.orderindex=orderindex;
	}
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public List<Menus> getChildren() {
		return children;
	}

	public void setChildren(List<Menus> children) {
		this.children = children;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}
 
}
