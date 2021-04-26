package cn.concox.vo.workflow;

import java.sql.Timestamp;

/**
 * 维修 数据库表名称：t_sale_workflow_pack
 *
 */
public class WorkflowPack {
	/**
	 * 字段名称：主键ID
	 * 
	 * 数据库字段信息:Id INT(11)
	 */
	private Integer Id;
	/**
	 * 字段名称：快递公司
	 * 
	 * 数据库字段信息:expressCompany VARCHAR(255)
	 */
	
	 /**
     * 字段名称：送修批号
     * 
     * 数据库字段信息:repairnNmber VARCHAR(50)
     */
    private String repairnNmber;
    
	
	private String expressCompany;

	/**
	 * 字段名称：快递单号
	 * 
	 * 数据库字段信息:expressNO VARCHAR(50)
	 */
	private String expressNO;

	/**
	 * 字段名称：装箱单号
	 * 
	 * 数据库字段信息:packingNO VARCHAR(50)
	 */
	private String packingNO;

	/**
	 * 字段名称：装箱人
	 * 
	 * 数据库字段信息:packer VARCHAR(255)
	 */
	private String packer;

	/**
	 * 字段名称：发货方
	 * 
	 * 数据库字段信息:shipper VARCHAR(255)
	 */
	private String shipper;

	private Long packState;
	/**
	 * 字段名称：装箱时间
	 * 
	 * 数据库字段信息:packTime DATETIME(19)
	 */
	private Timestamp packTime;

	/**
	 * 字段名称：创建时间
	 * 
	 * 数据库字段信息:createTime DATETIME(19)
	 */
	private Timestamp createTime;

	/**
	 * 字段名称：修改时间
	 * 
	 * 数据库字段信息:updateTime DATETIME(19)
	 */
	private Timestamp updateTime;
	
	/**
	 * 字段名称：装箱备注
	 * 
	 * 数据库字段信息:packRemark VARCHAR(200)
	 */
	private String packRemark;

	/*=================================业务字段 start==========================================*/
	private String pack_model;//主板型号
	private String pack_repairnNmber;//送修批号
	
	private String type;//装箱树日期类型（月，日）
	private String treeTime;//日期树（年-月）
	/*=================================业务字段 end==========================================*/
	
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	
	public String getRepairnNmber() {
		return repairnNmber;
	}

	public void setRepairnNmber(String repairnNmber) {
		this.repairnNmber = repairnNmber;
	}

	public String getExpressCompany() {
		return this.expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNO() {
		return this.expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public String getPackingNO() {
		return this.packingNO;
	}

	public void setPackingNO(String packingNO) {
		this.packingNO = packingNO;
	}

	public String getPacker() {
		return this.packer;
	}

	public void setPacker(String packer) {
		this.packer = packer;
	}

	public String getShipper() {
		return this.shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public Timestamp getPackTime() {
		return this.packTime;
	}

	public void setPackTime(Timestamp packTime) {
		this.packTime = packTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getPackRemark() {
		return packRemark;
	}

	public void setPackRemark(String packRemark) {
		this.packRemark = packRemark;
	}

	public Long getPackState() {
		return packState;
	}

	public void setPackState(Long packState) {
		this.packState = packState;
	}

	public String getPack_model() {
		return pack_model;
	}

	public void setPack_model(String pack_model) {
		this.pack_model = pack_model;
	}

	public String getPack_repairnNmber() {
		return pack_repairnNmber;
	}

	public void setPack_repairnNmber(String pack_repairnNmber) {
		this.pack_repairnNmber = pack_repairnNmber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTreeTime() {
		return treeTime;
	}

	public void setTreeTime(String treeTime) {
		this.treeTime = treeTime;
	}

}