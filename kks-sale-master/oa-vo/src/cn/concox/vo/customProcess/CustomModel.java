package cn.concox.vo.customProcess;

import java.util.List;

/**
 * 
 * @author 86183 自定义流程模块
 *
 */
public class CustomModel {

	// 模块ID
	private String id;
	
	// 模块类型 0 start 1 end 2 task
	private Integer type;
	
	// 模块名称
	private String name;
	
	// 所属流程
	private String belong;
	
	// 后一节点
	private String backNodes;
	
	// 查询id列表
	private List<String> idList;
	
	private Integer menuId;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBackNodes() {
		return backNodes;
	}

	public void setBackNodes(String backNodes) {
		this.backNodes = backNodes;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}
	
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	@Override
	public String toString() {
		return "CustomModel [id=" + id + ", type=" + type + ", name=" + name + ", belong=" + belong  + ", backNodes=" + backNodes + "]";
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	
	
}
