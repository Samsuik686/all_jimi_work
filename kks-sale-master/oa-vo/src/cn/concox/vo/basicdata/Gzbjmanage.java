package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 故障报价表实体类
 * 数据库表名称：t_sale_gzbjmanage
 * </pre>
 */
public class Gzbjmanage implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 字段名称：故障报价表Id，主键，自增长
	 * 
	 * 数据库字段信息:faId int(4)
	 */
	private Integer id;

	/**
	 * 字段名称：故障类别
	 * 
	 * 数据库字段信息:faultType VARCHAR(255)
	 */
	private String faultType;

	/**
	 * 字段名称：费用
	 * 
	 * 数据库字段信息:costs DECIMAL(8,2)
	 */
	private BigDecimal costs;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(250)
	 */
	private String remark;

	private String faultDesc;//故障现象
	
	public Gzbjmanage() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public BigDecimal getCosts() {
		return costs;
	}

	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFaultDesc() {
		return faultDesc;
	}

	public void setFaultDesc(String faultDesc) {
		this.faultDesc = faultDesc;
	}
}