package cn.concox.vo.material;

import java.sql.Timestamp;

/**
 * <pre>
 * 物料申请表实体类
 *  数据库表名称：t_sale_wlsqmanage
 * </pre>
 */
public class Wlsqmanage{
	/**
	 * 字段名称：id
	 * 
	 * 数据库字段信息:id int(11),主键，自增长
	 */
	private Integer id;

	/**
	 * 字段名称：物料名称
	 * 
	 * 数据库字段信息:proName VARCHAR(50)
	 */
	private String proName;

	/**
	 * 字段名称：平台
	 * 
	 * 数据库字段信息:platform VARCHAR(50)
	 */
	private String platform;

	/**
	 * 字段名称：物料编码
	 * 
	 * 数据库字段信息:proNO VARCHAR(50)
	 */
	private String proNO;

	/**
	 * 字段名称：物料规格
	 * 
	 * 数据库字段信息:proSpeci VARCHAR(50)
	 */
	private String proSpeci;

	/**
	 * 字段名称：单位
	 * 
	 * 数据库字段信息:unit VARCHAR(50)
	 */
	private String unit;

	/**
	 * 字段名称：申请数量
	 * 
	 * 数据库字段信息:number INT(10)
	 */
	private Integer number;

	/**
	 * 字段名称：申请日期
	 * 
	 * 数据库字段信息:appTime DATETIME(19)
	 */
	private Timestamp appTime;

	/**
	 * 字段名称：申请用途
	 * 
	 * 数据库字段信息:purpose VARCHAR(255)
	 */
	private String purpose;

	/**
	 * 字段名称：申请人
	 * 
	 * 数据库字段信息:applicater VARCHAR(255)
	 */
	private String applicater;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;

	/**
	 * 字段名称：批复
	 * 
	 * 数据库字段信息:state INT(10)
	 */
	private Integer state;
	
	/**
	 * 字段名称：物料类型
	 * 
	 * 数据库字段信息:matType INT(4)
	 */
	private Integer matType;
	
	/* =================================业务字段 start===============================*/
    private String startTime;//开始时间
    
    private String endTime;//结束时间
    /* =================================业务字段 end===============================*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getProNO() {
		return this.proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public String getProSpeci() {
		return this.proSpeci;
	}

	public void setProSpeci(String proSpeci) {
		this.proSpeci = proSpeci;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Timestamp getAppTime() {
		return this.appTime;
	}

	public void setAppTime(Timestamp appTime) {
		this.appTime = appTime;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getApplicater() {
		return this.applicater;
	}

	public void setApplicater(String applicater) {
		this.applicater = applicater;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getMatType() {
		return matType;
	}

	public void setMatType(Integer matType) {
		this.matType = matType;
	}
	
}