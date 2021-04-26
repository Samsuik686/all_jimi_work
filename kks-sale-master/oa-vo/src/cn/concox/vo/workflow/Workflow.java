/*
 * Created: 2016-7-20
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.vo.workflow;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 售后管理工作流公用表实体类
 * 数据库表名称：t_sale_workflow
 * </pre>
 * 
 * @author Li.ShangZhi
 * @version v1.0
 */
public class Workflow {

	/**
	 * 字段名称：主键ID
	 * 
	 * 数据库字段信息:Id INT(10)
	 */
	private Integer Id;

	/**
	 * 字段名称：售后服务关联表ID
	 * 
	 * 数据库字段信息:rfild INT(10)
	 */
	private Integer rfild;

	/**
	 * 字段名称：送修单位ID
	 * 
	 * 数据库字段信息:sxdwId INT(10)
	 */
	private Long sxdwId;

	/**
	 * 字段名称：送修批号
	 * 
	 * 数据库字段信息:repairnNmber VARCHAR(50)
	 */
	private String repairnNmber;

	/**
	 * 字段名称：型号ID
	 * 
	 * 数据库字段信息:xhId INT(10)
	 */
	private Long xhId;

	/**
	 * 字段名称：IMEI
	 * 
	 * 数据库字段信息:imei VARCHAR(30)
	 */
	private String imei;

	private Integer isWarranty;

	/**
	 * 字段名称：受理时间
	 * 
	 * 数据库字段信息:acceptanceTime DATETIME(19)
	 */
	private Timestamp acceptanceTime;

	/**
	 * 字段名称：返修次数
	 * 
	 * 数据库字段信息:returnTimes INT(10)
	 */
	private Long returnTimes;

	/**
	 * 字段名称：出货日期
	 * 
	 * 数据库字段信息:salesTime DATETIME(19)
	 */
	private Timestamp salesTime;

	/**
	 * 字段名称：受理人员
	 * 
	 * 数据库字段信息:accepter VARCHAR(255)
	 */
	private String accepter;

	/**
	 * 字段名称：取机时间
	 * 
	 * 数据库字段信息:qujiTime DATETIME(19)
	 */
	private Timestamp qujiTime;

	/**
	 * 字段名称：终检时间
	 * 
	 * 数据库字段信息:finalTime DATETIME(19)
	 */
	private Timestamp finalTime;

	/**
	 * 字段名称：确认日期
	 * 
	 * 数据库字段信息:checkTime DATETIME(19)
	 */
	private Timestamp checkTime;

	/**
	 * 字段名称：状态/进度 {0.1.2.3.4.5}
	 * 
	 * 数据库字段信息:state INT(10)
	 */
	private Integer state;

	/**
	 * 字段名称：送修备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;

	/**
	 * 字段名称：受理备注
	 * 
	 * 数据库字段信息:acceptRemark VARCHAR(255)
	 */
	private String acceptRemark;

	/**
	 * 字段名称：货到付款 Y 是货到付款，N 否
	 * 
	 * 数据库字段信息： payDelivery
	 */
	private String payDelivery;

	/**
	 * 
	 * 字段名称：无名件 normal 正常，un_normal 不明件 数据库字段信息：customerStatus 用于区分
	 * 受理时只看到IMEI号，其他信息不明确，此客户只停留在受理阶段不可发送到下一个工站
	 */
	private String customerStatus;

	/**
	 * 字段名称：payedLogCost 是否已支付物流费 0:是; 1:否 数据库字段信息：payedLogCost VARCHAR(10)
	 */
	private String payedLogCost;

	/**
	 * 字段名称：寄来无名件的快递公司
	 * 
	 * 数据库字段信息：unNormal_expressCompany VARCHAR(255)
	 */
	private String unNormalExpressCompany;
	/**
	 * 字段名称：寄来无名件的快递单号
	 * 
	 * 数据库字段信息：unNormal_expressNO VARCHAR(50)
	 */
	private String unNormalExpressNo;

	/**
	 * 字段名称：受理发送分拣时间
	 * 
	 * 数据库字段信息：sortTime datetime
	 */
	private Timestamp sortTime;

	/**
	 * 受理站发送状态 字段名称：0 未发送，1：已发送
	 * 
	 * 数据库字段信息：sendStatus VARCHAR(10)
	 */
	private String sendStatus;

	/**
	 * 受理站发送状态 字段名称：设备在装箱状态 Y：在装箱工站
	 * 
	 * 数据库字段信息：machina_in_pack VARCHAR(10)
	 */
	private String machinaInPack;

	private String repairNumberRemark;// 批次备注
	private String lastEngineer;// 上次维修人员
	private Timestamp lastAccTime;// 上次受理时间

	private String lockId;// 智能锁ID/密码
	private String lockInfo;// 智能锁ID二维码信息
	private String bluetoothId;// 蓝牙ID

	
	private Integer pricedCount;// 该批次是否报价 
	private String oldRepairNumber;//受理更改前的批次号
	/**
	 * 字段名称：付款原因
	 * 
	 * 数据库字段信息:payTreason VARCHAR(255)
	 */

	/**
	 * ================================ 业务字段 START
	 * =======================================
	 */

	/*
	 * =================================业务字段
	 * start===============================
	 */
	private List<String> imeis;// 批量查询
	
	
	public List<String> getImeis() {
        return imeis;
    }

    public void setImeis(List<String> imeis) {
        this.imeis = imeis;
    }

    private String startTime;// 开始时间

	private String endTime;// 结束时间

	private Integer sortState;// 分拣状态

	private String searchMorOrAft; // 判断查询上午或是下午
	
	private Date backTime; //应返还时间

	private Integer timeoutDays; // 超三天未寄出超期天数

	private String dutyOfficer;// 超三天责任人

	private String timeoutRemark;// 超三天备注

	private String timeoutReason;// 超三天原因

	private String w_station;// 设备所在工站
	/* =================================业务字段 end=============================== */

	/*** == 关联 START== ***/

	private String w_sjfj;
	private String w_cjgzId;
	private String w_zzgzId;
	private String w_matId;
	private String w_dzlId;
	private String w_wxbjId; // 维修报价Id

	private Integer sendFlag;
	private Integer w_finId;

	/*** == 关联 END == ***/

	private String w_cusName;
	private String w_model;
	private String w_ams_model;// ams获取的主板号，系统未录入
	private String w_modelType;
	private String w_marketModel;
	private String w_sjfjDesc;
	private String w_cjgzDesc;

	private String w_linkman;
	private String w_phone;
	private String w_telephone;// 座机号
	private String w_email;
	private String w_fax;
	private String w_address;
	private String w_cusRemark;// 送修单位备注

	/** -- 报价 Start-- **/
	private Integer w_priceId;
	private BigDecimal w_priceRepairMoney;// 报价表维修报价
	private String w_payReason; // 付款原因
	private String w_remAccount; // 付款账号
	private Timestamp w_payTime; // 付款日期
	private String w_offer; // 报价人
	private BigDecimal w_logCost; // 物流费
	private BigDecimal w_totalMoney; // 总金额
	private Long w_priceState; // 报价状态
	private Long w_priceStateFalg; // 报价状态
	private BigDecimal repairnNmber_totalMoney;// 该批次所有维修费用
	private String w_receipt; // 是否要开发票
	private BigDecimal w_rate;// 税率
	private String w_onePriceRemark; // 单台设备报价备注
	private Timestamp price_createTime; // 维修发送报价的时间
	private String w_t_remAccount; // 付款方式

	/** -- 报价 End-- **/

	/** -- 维修 Start-- **/
	private Integer w_repairId;
	private String w_zzgzDesc; // 最终故障标识
	private String w_wxbjDesc; // 维修报价标识
	private String w_methodDesc; // 故障处理方法标识
	private String w_matDesc; // 维修组件标识
	private String w_dzlDesc; // 电子料组件标识
	private Integer w_isRW; // 是否人为(0:是 1：否)
	private Integer w_isPay; // 是否付款(0:是 1：否)
	private String w_engineer; // 维修工程师
	private Long w_repairState; // 维修状态 JSP 描述
	private Long w_repairStateFalg; // 维修状态
	private String w_repairRemark; // 维修备注
	private String w_solution; // 维修 处理方法
	private String w_priceRemark; // 报价说明
	private String w_solutionTwo; // 处理方法2，在客户登录页面显示
	private BigDecimal w_repairPrice; // 维修报价
	private BigDecimal w_hourFee; // 工时费
	private BigDecimal w_sumPrice; // 维修总报价
	private Integer w_repairAgainCount; // 内部返修次数

	private String w_isPrice; // 放弃报价
	private String w_giveUpRepairStatus; // 客户放弃维修

	/** -- 维修 End-- **/

	/** -- 终检 Start-- **/
	private Timestamp w_sendFinTime;// 发送终检时间
	private String w_fId; // 终检匹配ID
	private String w_FinispassDesc; // 终检查状态描述
	private Long w_FinispassFalg; // 终检查状态
	private String w_FinDesc; // 终检查描述
	private String w_FinChecker; // 终检人
	private String w_ispass; // 是否合格{1.合格，0不合格}
	private Long giveupPriceCount; // 放弃报价数量
	/** -- 维修 End-- **/

	/** -- 装箱 Start -- **/
	private Integer w_packId;// 装箱Id
	private String w_expressCompany;// 快递公司
	private String w_expressNO;// 快递单号
	private String w_packingNO;// 装箱单号
	private String w_packer;// 装箱人
	private String w_shipper;// 发货方
	private Long w_packState;// 装箱状态
	private Timestamp w_packTime;// 装箱时间
	private Timestamp w_sendPackTime;// 发送装箱时间
	private String w_customerReceipt;// 客户收货方式
	private String w_price_Remark;// 批次报价备注
	private String newImei; // 修改IMEI
	private String w_packRemark;
	private Integer machinaInPackCount; // 装箱工站扫描过的设备数目

	private Integer countOfRepairnNmber;// 设备总数量

	private String packMonth;// 装箱树选中月
	private String packTreeDate;// 装箱树节点
	/** -- 装箱 End -- **/

	/** 测试工站 start **/
	private Integer testStatus;
	private String dataSource;
	private Timestamp sendTime;
	private String tid;
	private String testResult; // 测试结果
	private String testPerson; // 测试人
	private Timestamp testTime; // 测试时间

	/** 测试工站 end **/

	private String strSalesTime;// 出货日期
	private String strLastAccTime;// 上次受理日期

	private String accTime;// 受理日期（无名件改成正常件）

	private String cusName; // 添加时获取到添加的无名氏客户

	private String treeDate;

	private String backDate; // 预计出货时间

	private BigDecimal w_batchPjCosts; // 批次配件费

	private BigDecimal w_ratePrice; // 税费

	private BigDecimal w_repairMoney; // total维修费

	private String stateStr; // 状态 维修中，已完成

	private Integer w_isAllPay;// 批次付款状态

	private Integer isWork;// 周六是否上班，

	private Timestamp sendPackTime;// 发送装箱时间

	private String sfVersion;// 软件版本号

	private String bill;// 订单号

	private Integer outCount;// 该订单出货总数量

	private String testMatStatus;// 是否使用试流料

	private String w_sllMatNO;// 试流料编码

	private String w_sllDesc;// 试流料名字

	private String insideSoftModel; // 内部机型

	/**
	 * ================================ 业务字段 START
	 * =======================================
	 **/
	//未寄出查询时新加的参数
	private Integer isPacked;//已至装箱？0否、1是、2全部
	private Integer workStation;//要查询的工站 0全部、1受理、2分拣、3维修、4报价、5终检、6测试

	public Integer getIsPacked() {
		return isPacked;
	}

	public void setIsPacked(Integer isPacked) {
		this.isPacked = isPacked;
	}

	public Integer getWorkStation() {
		return workStation;
	}

	public void setWorkStation(Integer workStation) {
		this.workStation = workStation;
	}

	public Workflow() {

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getW_wxbjId() {
		return w_wxbjId;
	}

	public void setW_wxbjId(String w_wxbjId) {
		this.w_wxbjId = w_wxbjId;
	}

	public Long getSxdwId() {
		return this.sxdwId;
	}

	public void setSxdwId(Long sxdwId) {
		this.sxdwId = sxdwId;
	}

	public String getW_priceRemark() {
		return w_priceRemark;
	}

	public void setW_priceRemark(String w_priceRemark) {
		this.w_priceRemark = w_priceRemark;
	}

	public String getW_solutionTwo() {
		return w_solutionTwo;
	}

	public void setW_solutionTwo(String w_solutionTwo) {
		this.w_solutionTwo = w_solutionTwo;
	}

	public String getRepairnNmber() {
		return this.repairnNmber;
	}

	public void setRepairnNmber(String repairnNmber) {
		this.repairnNmber = repairnNmber;
	}

	public Long getXhId() {
		return this.xhId;
	}

	public void setXhId(Long xhId) {
		this.xhId = xhId;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Timestamp getAcceptanceTime() {
		return this.acceptanceTime;
	}

	public void setAcceptanceTime(Timestamp acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public Long getReturnTimes() {
		return this.returnTimes;
	}

	public void setReturnTimes(Long returnTimes) {
		this.returnTimes = returnTimes;
	}

	public Timestamp getSalesTime() {
		return this.salesTime;
	}

	public void setSalesTime(Timestamp salesTime) {
		this.salesTime = salesTime;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public Timestamp getQujiTime() {
		return this.qujiTime;
	}

	public void setQujiTime(Timestamp qujiTime) {
		this.qujiTime = qujiTime;
	}

	public Integer getW_finId() {
		return w_finId;
	}

	public void setW_finId(Integer w_finId) {
		this.w_finId = w_finId;
	}

	public Timestamp getFinalTime() {
		return this.finalTime;
	}

	public void setFinalTime(Timestamp finalTime) {
		this.finalTime = finalTime;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAcceptRemark() {
		return acceptRemark;
	}

	public void setAcceptRemark(String acceptRemark) {
		this.acceptRemark = acceptRemark;
	}

	public String getW_cusName() {
		return w_cusName;
	}

	public void setW_cusName(String w_cusName) {
		this.w_cusName = w_cusName;
	}

	public String getW_model() {
		return w_model;
	}

	public void setW_model(String w_model) {
		this.w_model = w_model;
	}

	public String getW_marketModel() {
		return w_marketModel;
	}

	public void setW_marketModel(String w_marketModel) {
		this.w_marketModel = w_marketModel;
	}

	public String getW_sjfjDesc() {
		return w_sjfjDesc;
	}

	public void setW_sjfjDesc(String w_sjfjDesc) {
		this.w_sjfjDesc = w_sjfjDesc;
	}

	public String getW_cjgzDesc() {
		return w_cjgzDesc;
	}

	public void setW_cjgzDesc(String w_cjgzDesc) {
		this.w_cjgzDesc = w_cjgzDesc;
	}

	public String getW_zzgzDesc() {
		return w_zzgzDesc;
	}

	public void setW_zzgzDesc(String w_zzgzDesc) {
		this.w_zzgzDesc = w_zzgzDesc;
	}

	public String getW_wxbjDesc() {
		return w_wxbjDesc;
	}

	public void setW_wxbjDesc(String w_wxbjDesc) {
		this.w_wxbjDesc = w_wxbjDesc;
	}

	public String getW_linkman() {
		return w_linkman;
	}

	public void setW_linkman(String w_linkman) {
		this.w_linkman = w_linkman;
	}

	public String getW_phone() {
		return w_phone;
	}

	public void setW_phone(String w_phone) {
		this.w_phone = w_phone;
	}

	public String getW_telephone() {
		return w_telephone;
	}

	public void setW_telephone(String w_telephone) {
		this.w_telephone = w_telephone;
	}

	public String getW_email() {
		return w_email;
	}

	public void setW_email(String w_email) {
		this.w_email = w_email;
	}

	public String getW_fax() {
		return w_fax;
	}

	public void setW_fax(String w_fax) {
		this.w_fax = w_fax;
	}

	public String getW_sjfj() {
		return w_sjfj;
	}

	public void setW_sjfj(String w_sjfj) {
		this.w_sjfj = w_sjfj;
	}

	public String getW_cjgzId() {
		return w_cjgzId;
	}

	public void setW_cjgzId(String w_cjgzId) {
		this.w_cjgzId = w_cjgzId;
	}

	public String getW_address() {
		return w_address;
	}

	public void setW_address(String w_address) {
		this.w_address = w_address;
	}

	public Integer getRfild() {
		return rfild;
	}

	public void setRfild(Integer rfild) {
		this.rfild = rfild;
	}

	public Integer getIsWarranty() {
		return isWarranty;
	}

	public void setIsWarranty(Integer isWarranty) {
		this.isWarranty = isWarranty;
	}

	public Integer getW_priceId() {
		return w_priceId;
	}

	public void setW_priceId(Integer w_priceId) {
		this.w_priceId = w_priceId;
	}

	public String getW_payReason() {
		return w_payReason;
	}

	public void setW_payReason(String w_payReason) {
		this.w_payReason = w_payReason;
	}

	public String getW_remAccount() {
		return w_remAccount;
	}

	public void setW_remAccount(String w_remAccount) {
		this.w_remAccount = w_remAccount;
	}

	public Timestamp getW_payTime() {
		return w_payTime;
	}

	public void setW_payTime(Timestamp w_payTime) {
		this.w_payTime = w_payTime;
	}

	public String getW_offer() {
		return w_offer;
	}

	public void setW_offer(String w_offer) {
		this.w_offer = w_offer;
	}

	public BigDecimal getW_logCost() {
		return w_logCost;
	}

	public void setW_logCost(BigDecimal w_logCost) {
		this.w_logCost = w_logCost;
	}

	public BigDecimal getW_totalMoney() {
		return w_totalMoney;
	}

	public void setW_totalMoney(BigDecimal w_totalMoney) {
		this.w_totalMoney = w_totalMoney;
	}

	public Long getW_priceState() {
		return w_priceState;
	}

	public void setW_priceState(Long w_priceState) {
		this.w_priceState = w_priceState;
	}

	public Long getW_priceStateFalg() {
		return w_priceStateFalg;
	}

	public void setW_priceStateFalg(Long w_priceStateFalg) {
		this.w_priceStateFalg = w_priceStateFalg;
	}

	public String getW_receipt() {
		return w_receipt;
	}

	public void setW_receipt(String w_receipt) {
		this.w_receipt = w_receipt;
	}

	public BigDecimal getW_rate() {
		return w_rate;
	}

	public void setW_rate(BigDecimal w_rate) {
		this.w_rate = w_rate;
	}

	public String getW_onePriceRemark() {
		return w_onePriceRemark;
	}

	public void setW_onePriceRemark(String w_onePriceRemark) {
		this.w_onePriceRemark = w_onePriceRemark;
	}

	public Timestamp getPrice_createTime() {
		return price_createTime;
	}

	public void setPrice_createTime(Timestamp price_createTime) {
		this.price_createTime = price_createTime;
	}

	public String getW_t_remAccount() {
		return w_t_remAccount;
	}

	public void setW_t_remAccount(String w_t_remAccount) {
		this.w_t_remAccount = w_t_remAccount;
	}

	public Integer getW_repairId() {
		return w_repairId;
	}

	public void setW_repairId(Integer w_repairId) {
		this.w_repairId = w_repairId;
	}

	public String getW_methodDesc() {
		return w_methodDesc;
	}

	public void setW_methodDesc(String w_methodDesc) {
		this.w_methodDesc = w_methodDesc;
	}

	public String getW_matDesc() {
		return w_matDesc;
	}

	public void setW_matDesc(String w_matDesc) {
		this.w_matDesc = w_matDesc;
	}

	public BigDecimal getW_sumPrice() {
		return w_sumPrice;
	}

	public void setW_sumPrice(BigDecimal w_sumPrice) {
		this.w_sumPrice = w_sumPrice;
	}

	public Integer getW_repairAgainCount() {
		return w_repairAgainCount;
	}

	public void setW_repairAgainCount(Integer w_repairAgainCount) {
		this.w_repairAgainCount = w_repairAgainCount;
	}

	public Integer getW_isRW() {
		return w_isRW;
	}

	public void setW_isRW(Integer w_isRW) {
		this.w_isRW = w_isRW;
	}

	public Integer getW_isPay() {
		return w_isPay;
	}

	public void setW_isPay(Integer w_isPay) {
		this.w_isPay = w_isPay;
	}

	public BigDecimal getW_repairPrice() {
		return w_repairPrice;
	}

	public void setW_repairPrice(BigDecimal w_repairPrice) {
		this.w_repairPrice = w_repairPrice;
	}

	public String getW_engineer() {
		return w_engineer;
	}

	public void setW_engineer(String w_engineer) {
		this.w_engineer = w_engineer;
	}

	public Long getW_repairState() {
		return w_repairState;
	}

	public void setW_repairState(Long w_repairState) {
		this.w_repairState = w_repairState;
	}

	public String getW_repairRemark() {
		return w_repairRemark;
	}

	public void setW_repairRemark(String w_repairRemark) {
		this.w_repairRemark = w_repairRemark;
	}

	public String getW_FinChecker() {
		return w_FinChecker;
	}

	public void setW_FinChecker(String w_FinChecker) {
		this.w_FinChecker = w_FinChecker;
	}

	public String getW_ispass() {
		return w_ispass;
	}

	public void setW_ispass(String w_ispass) {
		this.w_ispass = w_ispass;
	}

	public Integer getW_packId() {
		return w_packId;
	}

	public void setW_packId(Integer w_packId) {
		this.w_packId = w_packId;
	}

	public String getW_expressCompany() {
		return w_expressCompany;
	}

	public void setW_expressCompany(String w_expressCompany) {
		this.w_expressCompany = w_expressCompany;
	}

	public String getW_expressNO() {
		return w_expressNO;
	}

	public void setW_expressNO(String w_expressNO) {
		this.w_expressNO = w_expressNO;
	}

	public String getW_packingNO() {
		return w_packingNO;
	}

	public void setW_packingNO(String w_packingNO) {
		this.w_packingNO = w_packingNO;
	}

	public String getW_packer() {
		return w_packer;
	}

	public void setW_packer(String w_packer) {
		this.w_packer = w_packer;
	}

	public String getW_shipper() {
		return w_shipper;
	}

	public void setW_shipper(String w_shipper) {
		this.w_shipper = w_shipper;
	}

	public Long getW_packState() {
		return w_packState;
	}

	public void setW_packState(Long w_packState) {
		this.w_packState = w_packState;
	}

	public Timestamp getW_packTime() {
		return w_packTime;
	}

	public void setW_packTime(Timestamp w_packTime) {
		this.w_packTime = w_packTime;
	}

	public String getW_customerReceipt() {
		return w_customerReceipt;
	}

	public String getW_price_Remark() {
		return w_price_Remark;
	}

	public void setW_price_Remark(String w_price_Remark) {
		this.w_price_Remark = w_price_Remark;
	}

	public void setW_customerReceipt(String w_customerReceipt) {
		this.w_customerReceipt = w_customerReceipt;
	}

	public String getW_zzgzId() {
		return w_zzgzId;
	}

	public void setW_zzgzId(String w_zzgzId) {
		this.w_zzgzId = w_zzgzId;
	}

	public String getW_matId() {
		return w_matId;
	}

	public void setW_matId(String w_matId) {
		this.w_matId = w_matId;
	}

	public Long getW_repairStateFalg() {
		return w_repairStateFalg;
	}

	public void setW_repairStateFalg(Long w_repairStateFalg) {
		this.w_repairStateFalg = w_repairStateFalg;
	}

	public String getW_FinispassDesc() {
		return w_FinispassDesc;
	}

	public void setW_FinispassDesc(String w_FinispassDesc) {
		this.w_FinispassDesc = w_FinispassDesc;
	}

	public Long getW_FinispassFalg() {
		return w_FinispassFalg;
	}

	public void setW_FinispassFalg(Long w_FinispassFalg) {
		this.w_FinispassFalg = w_FinispassFalg;
	}

	public String getW_FinDesc() {
		return w_FinDesc;
	}

	public void setW_FinDesc(String w_FinDesc) {
		this.w_FinDesc = w_FinDesc;
	}

	public String getW_fId() {
		return w_fId;
	}

	public void setW_fId(String w_fId) {
		this.w_fId = w_fId;
	}

	public String getW_dzlDesc() {
		return w_dzlDesc;
	}

	public void setW_dzlDesc(String w_dzlDesc) {
		this.w_dzlDesc = w_dzlDesc;
	}

	public String getW_dzlId() {
		return w_dzlId;
	}

	public void setW_dzlId(String w_dzlId) {
		this.w_dzlId = w_dzlId;
	}

	public BigDecimal getRepairnNmber_totalMoney() {
		return repairnNmber_totalMoney;
	}

	public void setRepairnNmber_totalMoney(BigDecimal repairnNmber_totalMoney) {
		this.repairnNmber_totalMoney = repairnNmber_totalMoney;
	}

	public String getStrSalesTime() {
		return strSalesTime;
	}

	public void setStrSalesTime(String strSalesTime) {
		this.strSalesTime = strSalesTime;
	}

	public Integer getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public String getW_solution() {
		return w_solution;
	}

	public void setW_solution(String w_solution) {
		this.w_solution = w_solution;
	}

	public String getPayDelivery() {
		return payDelivery;
	}

	public void setPayDelivery(String payDelivery) {
		this.payDelivery = payDelivery;
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

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getPayedLogCost() {
		return payedLogCost;
	}

	public void setPayedLogCost(String payedLogCost) {
		this.payedLogCost = payedLogCost;
	}

	public String getUnNormalExpressCompany() {
		return unNormalExpressCompany;
	}

	public void setUnNormalExpressCompany(String unNormalExpressCompany) {
		this.unNormalExpressCompany = unNormalExpressCompany;
	}

	public String getUnNormalExpressNo() {
		return unNormalExpressNo;
	}

	public void setUnNormalExpressNo(String unNormalExpressNo) {
		this.unNormalExpressNo = unNormalExpressNo;
	}

	public Timestamp getSortTime() {
		return sortTime;
	}

	public void setSortTime(Timestamp sortTime) {
		this.sortTime = sortTime;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getMachinaInPack() {
		return machinaInPack;
	}

	public void setMachinaInPack(String machinaInPack) {
		this.machinaInPack = machinaInPack;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getTreeDate() {
		return treeDate;
	}

	public void setTreeDate(String treeDate) {
		this.treeDate = treeDate;
	}

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getRepairNumberRemark() {
		return repairNumberRemark;
	}

	public void setRepairNumberRemark(String repairNumberRemark) {
		this.repairNumberRemark = repairNumberRemark;
	}

	public String getLastEngineer() {
		return lastEngineer;
	}

	public void setLastEngineer(String lastEngineer) {
		this.lastEngineer = lastEngineer;
	}

	public String getW_modelType() {
		return w_modelType;
	}

	public void setW_modelType(String w_modelType) {
		this.w_modelType = w_modelType;
	}

	public String getW_isPrice() {
		return w_isPrice;
	}

	public void setW_isPrice(String w_isPrice) {
		this.w_isPrice = w_isPrice;
	}

	public String getW_giveUpRepairStatus() {
		return w_giveUpRepairStatus;
	}

	public void setW_giveUpRepairStatus(String w_giveUpRepairStatus) {
		this.w_giveUpRepairStatus = w_giveUpRepairStatus;
	}

	public Integer getSortState() {
		return sortState;
	}

	public void setSortState(Integer sortState) {
		this.sortState = sortState;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getSearchMorOrAft() {
		return searchMorOrAft;
	}

	public void setSearchMorOrAft(String searchMorOrAft) {
		this.searchMorOrAft = searchMorOrAft;
	}

	public String getNewImei() {
		return newImei;
	}

	public void setNewImei(String newImei) {
		this.newImei = newImei;
	}

	public String getW_packRemark() {
		return w_packRemark;
	}

	public void setW_packRemark(String w_packRemark) {
		this.w_packRemark = w_packRemark;
	}

	public Integer getMachinaInPackCount() {
		return machinaInPackCount;
	}

	public void setMachinaInPackCount(Integer machinaInPackCount) {
		this.machinaInPackCount = machinaInPackCount;
	}

	public String getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public String getTimeoutRemark() {
		return timeoutRemark;
	}

	public void setTimeoutRemark(String timeoutRemark) {
		this.timeoutRemark = timeoutRemark;
	}

	public Long getGiveupPriceCount() {
		return giveupPriceCount;
	}

	public void setGiveupPriceCount(Long giveupPriceCount) {
		this.giveupPriceCount = giveupPriceCount;
	}

	public Timestamp getW_sendPackTime() {
		return w_sendPackTime;
	}

	public void setW_sendPackTime(Timestamp w_sendPackTime) {
		this.w_sendPackTime = w_sendPackTime;
	}

	public String getW_station() {
		return w_station;
	}

	public void setW_station(String w_station) {
		this.w_station = w_station;
	}

	public BigDecimal getW_batchPjCosts() {
		return w_batchPjCosts;
	}

	public void setW_batchPjCosts(BigDecimal w_batchPjCosts) {
		this.w_batchPjCosts = w_batchPjCosts;
	}

	public BigDecimal getW_ratePrice() {
		return w_ratePrice;
	}

	public void setW_ratePrice(BigDecimal w_ratePrice) {
		this.w_ratePrice = w_ratePrice;
	}

	public BigDecimal getW_repairMoney() {
		return w_repairMoney;
	}

	public void setW_repairMoney(BigDecimal w_repairMoney) {
		this.w_repairMoney = w_repairMoney;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public Integer getW_isAllPay() {
		return w_isAllPay;
	}

	public void setW_isAllPay(Integer w_isAllPay) {
		this.w_isAllPay = w_isAllPay;
	}

	public String getW_cusRemark() {
		return w_cusRemark;
	}

	public void setW_cusRemark(String w_cusRemark) {
		this.w_cusRemark = w_cusRemark;
	}

	public Timestamp getSendPackTime() {
		return sendPackTime;
	}

	public void setSendPackTime(Timestamp sendPackTime) {
		this.sendPackTime = sendPackTime;
	}

	public String getW_ams_model() {
		return w_ams_model;
	}

	public void setW_ams_model(String w_ams_model) {
		this.w_ams_model = w_ams_model;
	}

	public Integer getIsWork() {
		return isWork;
	}

	public void setIsWork(Integer isWork) {
		this.isWork = isWork;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public Integer getOutCount() {
		return outCount;
	}

	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}

	public String getTestMatStatus() {
		return testMatStatus;
	}

	public void setTestMatStatus(String testMatStatus) {
		this.testMatStatus = testMatStatus;
	}

	public String getW_sllMatNO() {
		return w_sllMatNO;
	}

	public void setW_sllMatNO(String w_sllMatNO) {
		this.w_sllMatNO = w_sllMatNO;
	}

	public String getW_sllDesc() {
		return w_sllDesc;
	}

	public void setW_sllDesc(String w_sllDesc) {
		this.w_sllDesc = w_sllDesc;
	}

	public String getSfVersion() {
		return sfVersion;
	}

	public void setSfVersion(String sfVersion) {
		this.sfVersion = sfVersion;
	}

	public String getInsideSoftModel() {
		return insideSoftModel;
	}

	public void setInsideSoftModel(String insideSoftModel) {
		this.insideSoftModel = insideSoftModel;
	}

	public BigDecimal getW_hourFee() {
		return w_hourFee;
	}

	public void setW_hourFee(BigDecimal w_hourFee) {
		this.w_hourFee = w_hourFee;
	}

	public String getAccTime() {
		return accTime;
	}

	public void setAccTime(String accTime) {
		this.accTime = accTime;
	}

	public Integer getCountOfRepairnNmber() {
		return countOfRepairnNmber;
	}

	public void setCountOfRepairnNmber(Integer countOfRepairnNmber) {
		this.countOfRepairnNmber = countOfRepairnNmber;
	}

	public Timestamp getW_sendFinTime() {
		return w_sendFinTime;
	}

	public void setW_sendFinTime(Timestamp w_sendFinTime) {
		this.w_sendFinTime = w_sendFinTime;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getBluetoothId() {
		return bluetoothId;
	}

	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}

	public String getLockInfo() {
		return lockInfo;
	}

	public void setLockInfo(String lockInfo) {
		this.lockInfo = lockInfo;
	}

	public String getTimeoutReason() {
		return timeoutReason;
	}

	public void setTimeoutReason(String timeoutReason) {
		this.timeoutReason = timeoutReason;
	}

	@Override
	public String toString() {
		return "Workflow [Id=" + Id + ", repairnNmber=" + repairnNmber + ", imei=" + imei + ", acceptanceTime=" + acceptanceTime
				+ ", salesTime=" + salesTime + ", state=" + state + ", sortTime=" + sortTime + ", sendStatus=" + sendStatus
				+ ", machinaInPack=" + machinaInPack + ", repairNumberRemark=" + repairNumberRemark + ", w_priceId=" + w_priceId
				+ ", w_payTime=" + w_payTime + ", w_logCost=" + w_logCost + ", w_totalMoney=" + w_totalMoney + ", w_priceState="
				+ w_priceState + ", repairnNmber_totalMoney=" + repairnNmber_totalMoney + ", price_createTime=" + price_createTime
				+ ", w_repairId=" + w_repairId + ", w_zzgzDesc=" + w_zzgzDesc + ", w_wxbjDesc=" + w_wxbjDesc + ", w_methodDesc="
				+ w_methodDesc + ", w_matDesc=" + w_matDesc + ", w_dzlDesc=" + w_dzlDesc + ", w_isRW=" + w_isRW + ", w_engineer="
				+ w_engineer + ", w_repairState=" + w_repairState + ", w_repairRemark=" + w_repairRemark + ", w_solution=" + w_solution
				+ ", w_priceRemark=" + w_priceRemark + ", w_repairPrice=" + w_repairPrice + ", w_sumPrice=" + w_sumPrice
				+ ", w_repairAgainCount=" + w_repairAgainCount + ", w_isPrice=" + w_isPrice + ", w_giveUpRepairStatus="
				+ w_giveUpRepairStatus + ", w_sendFinTime=" + w_sendFinTime + ", w_fId=" + w_fId + ", w_FinispassFalg=" + w_FinispassFalg
				+ ", w_packId=" + w_packId + ", w_price_Remark=" + w_price_Remark + ", sendTime=" + sendTime + ", w_ratePrice="
				+ w_ratePrice + ", w_repairMoney=" + w_repairMoney + ", sendPackTime=" + sendPackTime + "]";
	}

	public String getPackMonth() {
		return packMonth;
	}

	public void setPackMonth(String packMonth) {
		this.packMonth = packMonth;
	}

	public String getPackTreeDate() {
		return packTreeDate;
	}

	public void setPackTreeDate(String packTreeDate) {
		this.packTreeDate = packTreeDate;
	}

	public Timestamp getLastAccTime() {
		return lastAccTime;
	}

	public void setLastAccTime(Timestamp lastAccTime) {
		this.lastAccTime = lastAccTime;
	}

	public String getStrLastAccTime() {
		return strLastAccTime;
	}

	public void setStrLastAccTime(String strLastAccTime) {
		this.strLastAccTime = strLastAccTime;
	}

	public BigDecimal getW_priceRepairMoney() {
		return w_priceRepairMoney;
	}

	public void setW_priceRepairMoney(BigDecimal w_priceRepairMoney) {
		this.w_priceRepairMoney = w_priceRepairMoney;
	}

	public Integer getPricedCount() {
		return pricedCount;
	}

	public void setPricedCount(Integer pricedCount) {
		this.pricedCount = pricedCount;
	}

	public String getOldRepairNumber() {
		return oldRepairNumber;
	}

	public void setOldRepairNumber(String oldRepairNumber) {
		this.oldRepairNumber = oldRepairNumber;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Integer getTimeoutDays() {
		return timeoutDays;
	}

	public void setTimeoutDays(Integer timeoutDays) {
		this.timeoutDays = timeoutDays;
	}
}