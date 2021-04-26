/*
 * Created: 2016-7-20
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.vo.commvo;
import java.util.List;
public class ZtreeShow {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String id;
	private String text;
	private Integer wfid; 
	public Integer getWfid() {
		return wfid;
	}
	public void setWfid(Integer wfid) {
		this.wfid = wfid;
	}
	private List<ZtreeShow> children;

	public List<ZtreeShow> getChildren() {
		return children;
	}
	public void setChildren(List<ZtreeShow> children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
