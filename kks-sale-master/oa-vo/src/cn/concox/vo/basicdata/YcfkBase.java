package cn.concox.vo.basicdata;

/**
 * 400电话记录定义数据
 * @author 20160308
 *
 */
public class YcfkBase {
	private Integer yid;

	private Integer gId;
	
	private String problems;// 类别
	
	private String proDetails;// 问题描述
	
	private String methods;// 处理方法

	public Integer getYid() {
		return yid;
	}

	public void setYid(Integer yid) {
		this.yid = yid;
	}

	public Integer getGId() {
		return gId;
	}

	public void setGId(Integer gId) {
		this.gId = gId;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getProDetails() {
		return proDetails;
	}

	public void setProDetails(String proDetails) {
		this.proDetails = proDetails;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

}
