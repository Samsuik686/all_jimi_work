/*
 * Created: 2016-7-27
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
 **/
package cn.concox.comm.vo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.concox.comm.util.StringUtil;
/**
 * <pre>
 * 树型节点类
 * @author Li.Shangzhi
 * @date 2016-07-27 21:15:25
 * @since v1.0
 * </pre>
 */
public class TreeNode {
    //--------------------easyUI 属性--------------------------------start
	//节点的id
    private String id;
    //节点显示名 
    private String text;
    //节点状态: open 或 closed
    private String open;
    //当前选中节点
    private boolean checked;
    
    private boolean chkDisabled=false;    //默认可选
    //自定义属性
    private Map<String, Object> attributes;
    //子节点列表
    private List<TreeNode> children;
    //节点图标样式
    private String iconCls;
    
    //--------------------easyUI 属性--------------------------------end
    
    public TreeNode(String id, String text) {
        this(id, text, null);
    }
    
    public TreeNode(String id, String text, String iconCls) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
    }

    
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
    
    public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
		
		if (this.children != null && this.children.size() > 0) {
			this.open = "true";
		}
	}
	
	public void addChild(TreeNode child) {
		if (child != null) {
			if (this.children == null) {
				this.children = new ArrayList<TreeNode>();
			}
			
			this.children.add(child);
			this.open = "true";
		}
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(String key, Object value) {
		key = StringUtil.strip(key);
		if (key.length() > 0) {
			if (this.attributes == null) {
				this.attributes = new HashMap<String, Object>();
			}
			
			this.attributes.put(key, value);
		}
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
}
