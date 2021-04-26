package cn.concox.vo.report;

public class BadRateReport {
	
	private String model;       //主板型号
	
	private String marketModel;       //市场型号
	
	private Integer repairs;      //保内返修机数量
		
 	private Long goods;      //出货总数
	
	private String percent;         //总返修比率
	    
	private String startTime;    //开始日期
	    
	private String endTime;    //结束日期
	
	private String isWarranty;// 是否保修

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public Integer getRepairs() {
		return repairs;
	}

	public void setRepairs(Integer repairs) {
		this.repairs = repairs;
	}

	public Long getGoods() {
		return goods;
	}

	public void setGoods(Long goods) {
		this.goods = goods;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
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

	public String getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(String isWarranty) {
		this.isWarranty = isWarranty;
	}
	
	

}
