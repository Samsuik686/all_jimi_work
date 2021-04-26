package cn.concox.vo.basicdata;

import java.io.Serializable;
/**
 * <pre>
 * 实体类
 * 数据库表名称：t_sale_sjfjmanage
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
public class Sjfjmanage  implements Serializable  {
	 private static final long serialVersionUID = 1L;
	 /**
	  * 随机附件id
	  */
	 private Integer eid;
	 
	   /**
	     * 字段名称：分组gId
	     * 
	     * 数据库字段信息:gId INT(11)
	     */
      private Integer gId;
	    /**
	     * 字段名称：类别
	     * 
	     * 数据库字段信息:category VARCHAR(32)
	     */
	    private String category;

	    /**
	     * 字段名称：品牌
	     * 
	     * 数据库字段信息:brand VARCHAR(32)
	     */
	    private String brand;

	    /**
	     * 字段名称：名称
	     * 
	     * 数据库字段信息:name VARCHAR(32)
	     */
	    private String name;

	    /**
	     * 字段名称：颜色
	     * 
	     * 数据库字段信息:color VARCHAR(32)
	     */
	    private String color;

	    /**
	     * 字段名称：数量个数
	     * 
	     * 数据库字段信息:number INT(10)
	     */
	    private Integer number;

	    public Sjfjmanage() {
	    }	

		
	    public Integer getEid() {
			return eid;
		}


		public void setEid(Integer eid) {
			this.eid = eid;
		}


		public Integer getgId() {
			return gId;
		}


		public void setgId(Integer gId) {
			this.gId = gId;
		}


		public static long getSerialversionuid() {
			return serialVersionUID;
		}


		public String getCategory() {
	        return this.category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

		
	    public String getBrand() {
	        return this.brand;
	    }

	    public void setBrand(String brand) {
	        this.brand = brand;
	    }

		
	    public String getName() {
	        return this.name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

		
	    public String getColor() {
	        return this.color;
	    }

	    public void setColor(String color) {
	        this.color = color;
	    }

		
	    public Integer getNumber() {
	        return this.number;
	    }

	    public void setNumber(Integer number) {
	        this.number = number;
	    }

}
