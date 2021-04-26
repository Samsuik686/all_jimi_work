package cn.concox.vo.workflow;

import java.sql.Timestamp;

/**
 * 受理管理 测试工站 实体类
 * 数据库 表名称：  t_sale_workflow_test
 * @author TangYuping
 * @version 2017年2月20日 下午4:40:42
 */
public class WorkflowTest {

	/**
	 * 字段名称：
	 * 数据库字段信息
	 */
	private Integer tid;
	/**
	 * 字段名称：
	 * 数据库字段信息
	 */
	private Integer wfId;
	/**
	 * 字段名称： 测试结果
	 * 数据库字段信息： test_result VARCHAR(500) 
	 */
	private String testResult;
	
	/**
	 * 字段名称：测试时间
	 * 数据库字段信息：test_time datetime
	 */
	private Timestamp testTime;
	
	/**
	 * 字段名称：测试人
	 * 数据库字段信息：test_person VARCHAR(32)
	 */
	private String testPerson;
	
	/**
	 * 字段名称：测试工站状态
	 * 数据库字段信息：test_status INT(11)
	 * 
	 */
	private Integer testStatus;
	
	/**
	 * 字段名称：数据来源站
	 * 数据库字段信息：data_source varchar(32)
	 * 
	 */
	private String dataSource; 
	
	/**
	 * 字段名称：数据发送到测试工站时间
	 * 数据库字段信息：update_datetime Timestamp
	 * 
	 */
	private Timestamp sendTime; 
	
	/**
	 * 字段名称：修改时间，同一台设备二次发送至测试
	 * 数据库字段信息：send_time Timestamp
	 * 
	 */
	private Timestamp updateTime;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getWfId() {
		return wfId;
	}

	public void setWfId(Integer wfId) {
		this.wfId = wfId;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
