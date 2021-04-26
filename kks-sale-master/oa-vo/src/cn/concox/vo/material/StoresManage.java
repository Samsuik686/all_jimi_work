package cn.concox.vo.material;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <pre>
 * 物料仓库管理实体类
 * 数据库表名称：t_sale_materiel_depot
 * </pre>
 * @author Li.Bifeng 
 * @version v1.0
 */
public class StoresManage implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 * 数据库字段信息:did VARCHAR(11)
	 */
    private String id; 	
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * 
     *仓库id
     * 数据库字段信息:did VARCHAR(11)
     */
    private String did;

    /**
     * 出库入库
     * 字段名称：子节点ID
     * 
     * 数据库字段信息:oid VARCHAR(11)
     */
    private String oid;

    /**
     * 出库入库类型id
     * 
     * 数据库字段信息:tid VARCHAR(32)
     */
    private String tid;

    /**
     * 字段名称：节点名称（仓库、类型.....）
     * 
     * 数据库字段信息:depotName VARCHAR(255)
     */
    private String depotName;

    /**
     * 
     * 
     * 数据库字段信息:outPutType VARCHAR(255)
     */
    private String outPutType;

    /**
     * 
     * 
     * 数据库字段信息:type VARCHAR(255)
     */
    private String type;

    /**
     * 
     * 
     * 数据库字段信息:createTime DATETIME(19)
     */
    private Timestamp createTime;

    /**
     * 字段名称：0.1
     * 
     * 数据库字段信息:enableFalg INT(10)
     */
    private Long enableFalg;

    public StoresManage() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getOutPutType() {
		return outPutType;
	}

	public void setOutPutType(String outPutType) {
		this.outPutType = outPutType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getEnableFalg() {
		return enableFalg;
	}

	public void setEnableFalg(Long enableFalg) {
		this.enableFalg = enableFalg;
	}	
}
