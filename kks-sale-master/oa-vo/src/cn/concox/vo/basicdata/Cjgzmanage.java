package cn.concox.vo.basicdata;


/**
 * <pre>
 * 初检故障实体类
 * 数据库表名称：t_sale_cjgzmanage
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
public class Cjgzmanage{
	  /**
	     * 字段名称：故障id
	     * 
	     * 数据库字段信息:iid INT(11)
	     */
	 
       private Integer iid;
      
       /**
	     * 字段名称：分组gId	     * 
	     * 数据库字段信息:gId INT(11)
	     */
       private Integer gId;
	   
      


		/**
	     * 字段名称：故障类别
	     * 
	     * 数据库字段信息:faultClass VARCHAR(32)
	     */
	    private String faultClass;

	    /**
	     * 字段名称：初检故障
	     * 
	     * 数据库字段信息:initheckFault VARCHAR(20)
	     */
	    private String initheckFault;

	    /**
	     * 字段名称：故障现象描述
	     * 
	     * 数据库字段信息:desc VARCHAR(100)
	     */
	    private String description;

	    /**
	     * 字段名称：故障编码
	     * 
	     * 数据库字段信息:number VARCHAR(20)
	     */
	    private String number;

	    /**
	     * 字段名称：是否禁用
	     * 
	     * 数据库字段信息:available INT(10)
	     */
	    private Integer  available;

		public Integer getIid() {
			return iid;
		}

		public void setIid(Integer iid) {
			this.iid = iid;
		}

		public Integer getGId() {
			return gId;
		}

		public void setGId(Integer gId) {
			this.gId = gId;
		}

		public String getFaultClass() {
			return faultClass;
		}

		public void setFaultClass(String faultClass) {
			this.faultClass = faultClass;
		}

		public String getInitheckFault() {
			return initheckFault;
		}

		public void setInitheckFault(String initheckFault) {
			this.initheckFault = initheckFault;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public Integer getAvailable() {
			return available;
		}

		public void setAvailable(Integer available) {
			this.available = available;
		}

}
