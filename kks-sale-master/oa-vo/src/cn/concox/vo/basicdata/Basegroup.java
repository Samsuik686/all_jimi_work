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
 */
package cn.concox.vo.basicdata;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <pre>
 * 基础数据分组表实体类
 * 数据库表名称：t_sale_basegroup
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
public class Basegroup implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public enum Type {
		/**
		 * 随机附件
		 */
		BASE_SJFJ("BASE_SJFJ", "随机附件"),
		/**
		 * 最终故障
		 */
		BASE_ZZGZ("BASE_ZZGZ", "最终故障"),
		/**
		 * 初次故障
		 */
		BASE_CCGZ("BASE_CCGZ", "初次故障"),
		/**
		 * 主板型号
		 */
		BASE_ZBXH("BASE_ZBXH", "主板型号"),
		/**
		 * 创建类型
		 */
		BASE_CJLX("BASE_CJLX", "创建类型"),
		/**
		 * 故障报价
		 */
		BASE_GZBJ("BASE_GZBJ", "故障报价"),
		/**
		 * 维修报价
		 */
		BASE_WXBJ("BASE_WXBJ","维修报价"),
		/**
		 * 电子料
		 */
		BASE_DZL("BASE_DZL","电子料");
		

		private String code;
		private String name;

		private Type(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public static String getName(String code) {
			String result = null;
			for (Type dateType : Type.values()) {
				if (dateType.getCode().equals(code)) {
					result = dateType.getName();
					break;
				}
			}
			return result;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}
	
	/**
     * 字段名称：分组ID
     * 
     * 数据库字段信息:gId Integer(11)
     */
    private Integer gId;
    

	/**
     * 字段名称：枚举类型[组名分类]
     * 
     * 数据库字段信息:enumSn VARCHAR(255)
     */
    private String enumSn;

    /**
     * 字段名称：组名ID
     * 
     * 数据库字段信息:gName VARCHAR(255)
     */
    private String gName;

    /**
     * 字段名称：创建时间
     * 
     * 数据库字段信息:createTime DATETIME(19)
     */
    private Timestamp createTime;

    public Basegroup() {
    }	

    public String getEnumSn() {
        return this.enumSn;
    }

    public void setEnumSn(String enumSn) {
        this.enumSn = enumSn;
    }
	
    public String getGName() {
        return this.gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

	
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

	public Integer getGId() {
		return gId;
	}

	public void setGId(Integer gId) {
		this.gId = gId;
	}

	
}