package cn.concox.vo.commvo;

import java.util.List;

public class TimeTree {
	
	private String id;
	private String text;
	private List<TimeTree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TimeTree> getChildren() {
		return children;
	}
	public void setChildren(List<TimeTree> children) {
		this.children = children;
	}
	
	
	
}
