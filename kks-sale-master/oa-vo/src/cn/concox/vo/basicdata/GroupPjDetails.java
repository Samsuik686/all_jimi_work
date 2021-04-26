package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 销售配件料组合详情表实体类
 * 数据库表名称：t_sale_grouppj_details
 * </pre>
 */
public class GroupPjDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	/**
     * 
     * 
     * 数据库字段信息:groupId INT(10)
     */
    private Integer groupId;

    /**
     * 
     * 
     * 数据库字段信息:pjlId INT(10)
     */
    private Integer pjlId;

    /**
     * 字段名称：编码
     * 
     * 数据库字段信息:proNO VARCHAR(50)
     */
    private String proNO;

    /**
     * 字段名称：名称
     * 
     * 数据库字段信息:proName VARCHAR(50)
     */
    private String proName;

    /**
     * 字段名称：规格
     * 
     * 数据库字段信息:proSpeci VARCHAR(255)
     */
    private String proSpeci;

    /**
     * 字段名称：零售价
     * 
     * 数据库字段信息:retailPrice DECIMAL(8,2)
     */
    private BigDecimal retailPrice;

    /**
     * 字段名称：数量
     * 
     * 数据库字段信息:proNumber INT(10)
     */
    private Integer proNumber;
    
    private String tempIds;//选择的配件料id
    
    private String masterOrSlave;

    public GroupPjDetails() {
    }	
    
    public Integer getId() {
    	return id;
    }
    
    
    public void setId(Integer id) {
    	this.id = id;
    }

	
    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

	
    public Integer getPjlId() {
        return this.pjlId;
    }

    public void setPjlId(Integer pjlId) {
        this.pjlId = pjlId;
    }

	
    public String getProNO() {
        return this.proNO;
    }

    public void setProNO(String proNO) {
        this.proNO = proNO;
    }

	
    public String getProName() {
        return this.proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

	
    public String getProSpeci() {
        return this.proSpeci;
    }

    public void setProSpeci(String proSpeci) {
        this.proSpeci = proSpeci;
    }

	
    public BigDecimal getRetailPrice() {
        return this.retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

	
    public Integer getProNumber() {
        return this.proNumber;
    }

    public void setProNumber(Integer proNumber) {
        this.proNumber = proNumber;
    }

	public String getTempIds() {
		return tempIds;
	}

	public void setTempIds(String tempIds) {
		this.tempIds = tempIds;
	}

	public String getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(String masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}

}