package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <pre>
 * 验证码实体类
 * 数据库表名称：t_sale_validate_code
 * </pre>
 */
public class ValidateCode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String phone;
	
	private String validateCode; //验证码
	
	private Timestamp createTime;//生成验证码时间
	
	private Timestamp outTime;//失效时间
	
	private Integer sendCount; //当天发送短信次数
	
	private Integer sendType;//发送类型（1：验证吗 2：密码）

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getOutTime() {
		return outTime;
	}

	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	
	
}