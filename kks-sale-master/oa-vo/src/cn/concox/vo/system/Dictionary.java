package cn.concox.vo.system;

import java.io.Serializable;
import java.util.List;

public class Dictionary implements Serializable {
	
	private static final long serialVersionUID = 482341347091234879L;
	
	private Integer dicId;
	
	private Integer proTypeId;

	private String itemName;
	
	private String itemValue;

	private String descrp;

	private String sn;

	private Integer orderindex;
	
	private List<Dictionary> children;

	public Integer getDicId() {
		return dicId;
	}

	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}

	public Integer getProTypeId() {
		return proTypeId;
	}

	public void setProTypeId(Integer proTypeId) {
		this.proTypeId = proTypeId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
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

	public List<Dictionary> getChildren() {
		return children;
	}

	public void setChildren(List<Dictionary> children) {
		this.children = children;
	}
	
	
}
