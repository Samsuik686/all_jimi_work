package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 报价配件料组合管理实体类
 * 数据库表名称：t_sale_grouppj_price
 * </pre>
 */
public class PriceGroupPj implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer groupPjId;

	/**
     * 字段名称：配件料名称
     * 
     * 数据库字段信息:groupName VARCHAR(255)
     */
    private String groupName;

    /**
     * 字段名称：总价格
     * 
     * 数据库字段信息:groupPrice DECIMAL(8,2)
     */
    private BigDecimal groupPrice;
    
    private BigDecimal pjlPrice;
    
    private BigDecimal hourFee;
    
    private String model;//主板型号
    
    private String remark;//备注
    
    private String searchType; // 区分查询列表
    
    private String pjlDesc;//配件料id
    
    private String pjlNames;//配件料名称
    
    private Integer importFalg;//导入标识
    
    public PriceGroupPj() {
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public BigDecimal getPjlPrice() {
		return pjlPrice;
	}

	public void setPjlPrice(BigDecimal pjlPrice) {
		this.pjlPrice = pjlPrice;
	}

	public BigDecimal getHourFee() {
		return hourFee;
	}

	public void setHourFee(BigDecimal hourFee) {
		this.hourFee = hourFee;
	}

	public Integer getImportFalg() {
		return importFalg;
	}

	public void setImportFalg(Integer importFalg) {
		this.importFalg = importFalg;
	}

	public String getPjlDesc() {
		return pjlDesc;
	}

	public void setPjlDesc(String pjlDesc) {
		this.pjlDesc = pjlDesc;
	}

	public String getPjlNames() {
		return pjlNames;
	}

	public void setPjlNames(String pjlNames) {
		this.pjlNames = pjlNames;
	}

}