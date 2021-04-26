package cn.concox.vo.workflow;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <pre>
 * 报价关联表实体类
 * 数据库表名称：t_sale_workflow_price
 * </pre>
 * 
 * @author Li.ShangZhi
 * @version v1.0
 */
public class WorkflowPrice {
    /**
     * 字段名称：主键 公共表的主键 数据库字段信息:id INT(10)
     */
    private Integer id;
    /**
     * 字段名称：外键 公共表的主键
     * 
     * 数据库字段信息:wfId INT(10)
     */
    private Integer wfId;

    /**
     * 字段名称：付款账号
     * 
     * 数据库字段信息:remAccount VARCHAR(255)
     */
    private String remAccount;

    /**
     * 字段名称：付款日期
     * 
     * 数据库字段信息:payTime DATETIME(19)
     */
    private Timestamp payTime;

    /**
     * 字段名称：是否付款 0已付款 1未付款
     * 
     * 数据库字段信息:isPay INT(10)
     */
    private Integer isPay;

    /**
     * 字段名称：报价人
     * 
     * 数据库字段信息:offer VARCHAR(255)
     */
    private String offer;

    /**
     * 字段名称：报价状态：(-1:已发送，0:待报价 1：待维修 2：待装箱)
     * 
     * 数据库字段信息:priceState INT(10)
     */
    private Long priceState;
    /**
     * 字段名称：维修金额
     * 
     * 数据库字段信息:repairMoney Decimal
     */
    private BigDecimal repairMoney;

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
     * 字段名称：单台设备报价备注
     * 
     * 数据库字段信息 onePriceRemark VARCHAR(255)
     */
    private String onePriceRemark;

    /* ====================业务字段 start=========================== */
    private String[] imeis;

    private String price_imei;// IMEI号

    private String price_lockId;// 智能锁ID

    private String price_bluetoothId;// 蓝牙ID

    private String price_repairnNmber;// 送修批号

    private String price_cusName;// 客户名称

    private BigDecimal repairnNmber_totalMoney;// 该批次所有维修费用

    private String rfild;

    private String customerReceipt; // 客户收货方式
    private String priceRemark; // 报价备注

    private String price_engineer; // 维修人员
    private String price_acceptRemark; // 受理备注
    private String price_sxRemark; // 送修备注
    private String price_expressNO; // 快递单号
    private String price_zzgzDesc; // 最终故障
    private String price_isPrice; // 放弃报价
    private String receipt; // 是否开发票 0：开，1：不开
    private BigDecimal rate; // 税率
    private String state; // 进度查询
    private String allPrice; // 查询可报价批次

    private String t_remAccount; // 付款方式

    private String startTime;// 开始时间

    private String endTime;// 结束时间

    private String treeDate;

    private String price_repairRemark; // 维修备注
    private String price_priceRemark; // 维修报价描述
    private String price_repairNumberRemark; // 批次备注

    private String testResult; // 测试结果
    private String testPerson; // 测试人
    private Timestamp testTime; // 测试时间

    /* =================================业务字段 end=============================== */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWfId() {
        return wfId;
    }

    public void setWfId(Integer wfId) {
        this.wfId = wfId;
    }

    public Integer getIsPay() {
        return this.isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getRemAccount() {
        return remAccount;
    }

    public void setRemAccount(String remAccount) {
        this.remAccount = remAccount;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public String getOffer() {
        return this.offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public Long getPriceState() {
        return priceState;
    }

    public void setPriceState(Long priceState) {
        this.priceState = priceState;
    }

    public String[] getImeis() {
        return imeis;
    }

    public void setImeis(String[] imeis) {
        this.imeis = imeis;
    }

    public String getPrice_imei() {
        return price_imei;
    }

    public void setPrice_imei(String price_imei) {
        this.price_imei = price_imei;
    }

    public String getPrice_repairnNmber() {
        return price_repairnNmber;
    }

    public void setPrice_repairnNmber(String price_repairnNmber) {
        this.price_repairnNmber = price_repairnNmber;
    }

    public BigDecimal getRepairMoney() {
        return repairMoney;
    }

    public void setRepairMoney(BigDecimal repairMoney) {
        this.repairMoney = repairMoney;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getOnePriceRemark() {
        return onePriceRemark;
    }

    public void setOnePriceRemark(String onePriceRemark) {
        this.onePriceRemark = onePriceRemark;
    }

    public BigDecimal getRepairnNmber_totalMoney() {
        return repairnNmber_totalMoney;
    }

    public void setRepairnNmber_totalMoney(BigDecimal repairnNmber_totalMoney) {
        this.repairnNmber_totalMoney = repairnNmber_totalMoney;
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

    public String getRfild() {
        return rfild;
    }

    public void setRfild(String rfild) {
        this.rfild = rfild;
    }

    public String getTreeDate() {
        return treeDate;
    }

    public void setTreeDate(String treeDate) {
        this.treeDate = treeDate;
    }

    public String getPrice_cusName() {
        return price_cusName;
    }

    public void setPrice_cusName(String price_cusName) {
        this.price_cusName = price_cusName;
    }

    public String getCustomerReceipt() {
        return customerReceipt;
    }

    public void setCustomerReceipt(String customerReceipt) {
        this.customerReceipt = customerReceipt;
    }

    public String getPriceRemark() {
        return priceRemark;
    }

    public void setPriceRemark(String priceRemark) {
        this.priceRemark = priceRemark;
    }

    public String getPrice_engineer() {
        return price_engineer;
    }

    public void setPrice_engineer(String price_engineer) {
        this.price_engineer = price_engineer;
    }

    public String getPrice_acceptRemark() {
        return price_acceptRemark;
    }

    public void setPrice_acceptRemark(String price_acceptRemark) {
        this.price_acceptRemark = price_acceptRemark;
    }

    public String getPrice_expressNO() {
        return price_expressNO;
    }

    public void setPrice_expressNO(String price_expressNO) {
        this.price_expressNO = price_expressNO;
    }

    public String getPrice_zzgzDesc() {
        return price_zzgzDesc;
    }

    public void setPrice_zzgzDesc(String price_zzgzDesc) {
        this.price_zzgzDesc = price_zzgzDesc;
    }

    public String getPrice_isPrice() {
        return price_isPrice;
    }

    public void setPrice_isPrice(String price_isPrice) {
        this.price_isPrice = price_isPrice;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public String getT_remAccount() {
        return t_remAccount;
    }

    public void setT_remAccount(String t_remAccount) {
        this.t_remAccount = t_remAccount;
    }

    public String getPrice_sxRemark() {
        return price_sxRemark;
    }

    public void setPrice_sxRemark(String price_sxRemark) {
        this.price_sxRemark = price_sxRemark;
    }

    public String getPrice_repairRemark() {
        return price_repairRemark;
    }

    public void setPrice_repairRemark(String price_repairRemark) {
        this.price_repairRemark = price_repairRemark;
    }

    public String getPrice_priceRemark() {
        return price_priceRemark;
    }

    public void setPrice_priceRemark(String price_priceRemark) {
        this.price_priceRemark = price_priceRemark;
    }

    public String getPrice_repairNumberRemark() {
        return price_repairNumberRemark;
    }

    public void setPrice_repairNumberRemark(String price_repairNumberRemark) {
        this.price_repairNumberRemark = price_repairNumberRemark;
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

    public String getPrice_lockId() {
        return price_lockId;
    }

    public void setPrice_lockId(String price_lockId) {
        this.price_lockId = price_lockId;
    }

    public String getPrice_bluetoothId() {
        return price_bluetoothId;
    }

    public void setPrice_bluetoothId(String price_bluetoothId) {
        this.price_bluetoothId = price_bluetoothId;
    }

}
