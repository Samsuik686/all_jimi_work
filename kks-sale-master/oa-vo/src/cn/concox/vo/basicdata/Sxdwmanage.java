package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <pre>
 * 送修单位信息表实体类
 * 数据库表名称：t_sale_sxdwmanage
 * </pre>
 */
public class Sxdwmanage implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 字段名称： 客户id
	 * 
	 * 数据库字段信息:cId int(4)，主键，自增长
	 */
	private Integer cId;

	/**
	 * 字段名称：客户名称
	 * 
	 * 数据库字段信息:cusName VARCHAR(255)
	 */
	private String cusName;

	/**
	 * 字段名称：服务周期（例如1年、3年......终身）
	 * 
	 * 数据库字段信息:serviceTime VARCHAR(20)
	 */
	private String serviceTime;

	/**
	 * 字段名称：联系人
	 * 
	 * 数据库字段信息:linkman VARCHAR(50)
	 */
	private String linkman;

	/**
	 * 字段名称：电话
	 * 
	 * 数据库字段信息:phone VARCHAR(20)
	 */
	private String phone;
	
	private String telephone;//座机号

	/**
	 * 字段名称：邮箱
	 * 
	 * 数据库字段信息:email VARCHAR(50)
	 */
	private String email;

	/**
	 * 字段名称：传真
	 * 
	 * 数据库字段信息:fax VARCHAR(20)
	 */
	private String fax;

	/**
	 * 字段名称：通信地址
	 * 
	 * 数据库字段信息:address VARCHAR(255)
	 */
	private String address;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;
	
	private String createBy;//创建人 
	
	private String updateBy; //修改人
	
	private Timestamp createTime;//创建时间
	
	private Timestamp updateTime;//修改时间
	
	private String loginPwd;//登录密码
	private String oldPhone;//修改前手机号列表
	private String oldAddress;//修改前地址列表
	private Integer regState; //注册状态
	
	private String showType;//受理不显示禁用数据
	private Integer enabledFlag;//是否禁用
	
	/**=========================  业务字段	Start==============================**/
	private String repairnum;
	private Integer isExistsAndPay;  // 同一个客户同一天多次受理时是否生成新的送修批号
	private String isDelivery;  //是否货到付款
	
	private Integer repairnumCount;//手机号的批次数量
	
	private Integer pricedCount;//该批次是否已付款
	private String oldRepairNumber;//受理更改前的批次号
	/**=========================  业务字段	End==============================**/
	

	public Sxdwmanage() {
	}

	public Sxdwmanage(String cusName, String serviceTime, String linkman,
			String phone, String email, String fax, String address,
			String remark) {
		this.cusName = cusName;
		this.serviceTime = serviceTime;
		this.linkman = linkman;
		this.phone = phone;
		this.email = email;
		this.fax = fax;
		this.address = address;
		this.remark = remark;
	}
	
	public String getRepairnum() {
		return repairnum;
	}

	public void setRepairnum(String repairnum) {
		this.repairnum = repairnum;
	}

	public Integer getCId() {
		return cId;
	}

	public void setCId(Integer cId) {
		this.cId = cId;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getServiceTime() {
		return this.serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsExistsAndPay() {
		return isExistsAndPay;
	}

	public void setIsExistsAndPay(Integer isExistsAndPay) {
		this.isExistsAndPay = isExistsAndPay;
	}

	public String getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getOldPhone() {
		return oldPhone;
	}

	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}

	public String getOldAddress() {
		return oldAddress;
	}

	public void setOldAddress(String oldAddress) {
		this.oldAddress = oldAddress;
	}

	public Integer getRegState() {
		return regState;
	}

	public void setRegState(Integer regState) {
		this.regState = regState;
	}

	public Integer getRepairnumCount() {
		return repairnumCount;
	}

	public void setRepairnumCount(Integer repairnumCount) {
		this.repairnumCount = repairnumCount;
	}

	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
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
}