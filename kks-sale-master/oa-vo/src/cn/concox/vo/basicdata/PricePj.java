package cn.concox.vo.basicdata;

import java.math.BigDecimal;

public class PricePj {
	private Integer id;
	private Integer mid;// 物料表id
	private String proNO;// 配件编码
	private String proName;// 配件名
	private String marketModel;// 市场型号
	private BigDecimal retailPrice;// 单价
	private Integer pjNumber;// 数量
	private String repairNumber;// 批次号

	private String cusName;// 客户名字
	private String offer;// 报价人

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getProNO() {
		return proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Integer getPjNumber() {
		return pjNumber;
	}

	public void setPjNumber(Integer pjNumber) {
		this.pjNumber = pjNumber;
	}

	public String getRepairNumber() {
		return repairNumber;
	}

	public void setRepairNumber(String repairNumber) {
		this.repairNumber = repairNumber;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}
}