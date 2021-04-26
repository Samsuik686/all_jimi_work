package cn.concox.vo.rolePrivilege;

import java.io.Serializable;
import java.util.List;

public class MenusFunction implements Serializable {

	/**
	 * 对应t_oa_menus
	 */
	private static final long serialVersionUID = 1L;

	private Integer menuId;
	
	private Integer parentId;
	
	private String displayName;
	
	private String url;
	
	private String descrp;
	
	private String sn;
	
	private Integer orderindex;
	
	private List<FunctionDetails> funcDetails;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex = orderindex;
	}

	public List<FunctionDetails> getFuncDetails() {
		return funcDetails;
	}

	public void setFuncDetails(List<FunctionDetails> funcDetails) {
		this.funcDetails = funcDetails;
	}	
}
