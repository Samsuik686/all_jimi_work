package cn.concox.vo.basicdata;

import java.io.Serializable;

/**
 * <pre>
 * 电子料管理
 * 
 * 数据库表名称：t_sale_dzlmanage
 * </pre>
 */
public class Dzlmanage implements Serializable{
	private static final long serialVersionUID = 1L;
	

	/**
     * 字段名称：ID
     * 
     * 数据库字段信息:dzlId AUTO_INCREMENT COMMENT 'ID',
     */
    private Integer dzlId;
    
    private Integer gId;
    
	private String dzlType;
	
    /**
     * 字段名称：编码
     * 
     * 数据库字段信息:proNO VARCHAR(50)
     */
    private String proNO;

    /**
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
     * 字段名称：序号
     * 
     * 数据库字段信息:placesNO VARCHAR(32)
     */
    private String placesNO;

    /**
     * 字段名称：用量
     * 
     * 数据库字段信息:consumption INT(10)
     */
    private Integer consumption;

    /**
     * 字段名称：备注
     * 
     * 数据库字段信息:remark VARCHAR(255)
     */
    
    private String remark;
    
    /**
	 * 字段名称：M|T
	 * 
	 * 数据库字段信息:masterOrSlave VARCHAR(255)
	 */
	private String masterOrSlave;
	
	  /**
     * 字段名称：是否启用
     * 
     * 数据库字段信息:enabledFlag INT(11)
     */
    private int enabledFlag;
    
    private String showType;//显示位置

    public Dzlmanage() {
    }	
	
    public Integer getDzlId() {
		return dzlId;
	}

	public void setDzlId(Integer dzlId) {
		this.dzlId = dzlId;
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
		return proSpeci;
	}

	public void setProSpeci(String proSpeci) {
		this.proSpeci = proSpeci;
	}

	public String getPlacesNO() {
        return this.placesNO;
    }

    public void setPlacesNO(String placesNO) {
        this.placesNO = placesNO;
    }

    public Integer getConsumption() {
        return this.consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(String masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}

	public String getDzlType() {
		return dzlType;
	}

	public void setDzlType(String dzlType) {
		this.dzlType = dzlType;
	}

	public int getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(int enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
   
}