package cn.concox.vo.aipay;

import java.sql.Timestamp;

public class SaleFeedback {

	/**
	 * 字段：id
	 */
	private Integer id;

	/**
	 * 字段：送修批号
	 */
	private String repairnNmber;

	/**
	 * 字段：技能评价
	 */
	private Integer skillDesc;

	/**
	 * 字段：服务评价
	 */
	private Integer serviceDesc;

	/**
	 * 字段：初次评价说明
	 */
	private String Fremark;

	/**
	 * 字段：再次评价说明
	 */
	private String Sremark;

	/**
	 * 字段：第一次评价时间
	 */
	private Timestamp FcreateTime;

	/**
	 * 字段：第二次评价时间
	 */
	private Timestamp ScreateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRepairnNmber() {
		return repairnNmber;
	}

	public void setRepairnNmber(String repairnNmber) {
		this.repairnNmber = repairnNmber;
	}

	public Integer getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(Integer skillDesc) {
		this.skillDesc = skillDesc;
	}

	public Integer getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(Integer serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getFremark() {
		return Fremark;
	}

	public void setFremark(String fremark) {
		Fremark = fremark;
	}

	public String getSremark() {
		return Sremark;
	}

	public void setSremark(String sremark) {
		Sremark = sremark;
	}

	public Timestamp getFcreateTime() {
		return FcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		FcreateTime = fcreateTime;
	}

	public Timestamp getScreateTime() {
		return ScreateTime;
	}

	public void setScreateTime(Timestamp screateTime) {
		ScreateTime = screateTime;
	}


}
