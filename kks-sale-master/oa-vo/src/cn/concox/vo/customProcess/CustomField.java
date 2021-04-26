package cn.concox.vo.customProcess;

public class CustomField {

	// 字段名称
	private String name;
	
	// 所属流程名
	private String belong;
	
	// 类型：0 文本 1 单选
	private Integer type;
	
	// 是否必填 0 必填 1 非必填
	private Integer isNeed;
	
	// 单选框情况下才有值，将单选的值用逗号分隔
	private String checkBox;
	
	// 排序
	private Integer order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(Integer isNeed) {
		this.isNeed = isNeed;
	}

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
