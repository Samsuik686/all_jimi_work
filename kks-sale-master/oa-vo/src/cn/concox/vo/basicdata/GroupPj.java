package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 销售配件料组合管理实体类
 * 数据库表名称：t_sale_grouppj
 * </pre>
 */
public class GroupPj implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer groupPjId;

	/**
     * 字段名称：配件料名称
     * 
     * 数据库字段信息:groupName VARCHAR(255)
     */
    private String groupName;

    /**
     * 字段名称：销售配件料组合总价格
     * 
     * 数据库字段信息:groupPrice DECIMAL(8,2)
     */
    private BigDecimal groupPrice;
    
    private String marketModel;//市场型号
    
    private String remark;//备注
    
    private Integer importFalg;//导入标识

    public GroupPj() {
    }	

    public Integer getGroupPjId() {
    	return groupPjId;
    }
    
    public void setGroupPjId(Integer groupPjId) {
    	this.groupPjId = groupPjId;
    }
	
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
	
    public BigDecimal getGroupPrice() {
        return this.groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public Integer getImportFalg() {
		return importFalg;
	}

	public void setImportFalg(Integer importFalg) {
		this.importFalg = importFalg;
	}

}