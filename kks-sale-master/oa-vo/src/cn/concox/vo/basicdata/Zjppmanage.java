package cn.concox.vo.basicdata;

import java.io.Serializable;

/**
 * <pre>
 * 终检型号匹配表实体类
 * 数据库表名称：t_sale_zjppmanage
 * </pre>
 */
public class Zjppmanage implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 字段名称：终检型号匹配表Id，主键，自增长
	 * 
	 * 数据库字段信息:matchId int(4)
	 */
	private Integer matchId;

	/**
	 * 字段名称：测试步骤
	 * 
	 * 数据库字段信息:step INT(10)
	 */
	private Integer step;

	/**
	 * 字段名称：型号（售后型号）
	 * 
	 * 数据库字段信息:model VARCHAR(30)
	 */
	private String model;

	/**
	 * 字段名称：测试方法
	 * 
	 * 数据库字段信息:testMethod VARCHAR(2000)
	 */
	private String testMethod;

	/**
	 * 字段名称：注意事项
	 * 
	 * 数据库字段信息:becareful VARCHAR(2000)
	 */
	private String becareful;

	public Zjppmanage() {
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getStep() {
		return this.step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTestMethod() {
		return this.testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public String getBecareful() {
		return this.becareful;
	}

	public void setBecareful(String becareful) {
		this.becareful = becareful;
	}

}