package cn.concox.vo.report;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author huangwm
 * @date 创建时间：2016年11月24日 上午11:21:41
 */
public class RepairAgainDetail {

    private String cusName;
    private String model;
    private String imei;
    private Integer isWarranty;
    private String cjgzDesc;
    private Timestamp acceptanceTime;
    private String remark;
    private Integer returnTimes; // 二次返修次数
    private String engineer;
    private Timestamp packTime;
    private String solution;
    private String zzgzDesc;
    private Timestamp lastAccTime;
    private String lastEngineer;

    private String StratTime; // 开始时间

    private String EndTime; // 结束时间

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getIsWarranty() {
        return isWarranty;
    }

    public void setIsWarranty(Integer isWarranty) {
        this.isWarranty = isWarranty;
    }

    public String getCjgzDesc() {
        return cjgzDesc;
    }

    public void setCjgzDesc(String cjgzDesc) {
        this.cjgzDesc = cjgzDesc;
    }

    public Date getAcceptanceTime() {
        return acceptanceTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getReturnTimes() {
        return returnTimes;
    }

    public void setReturnTimes(Integer returnTimes) {
        this.returnTimes = returnTimes;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Date getPackTime() {
        return packTime;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getZzgzDesc() {
        return zzgzDesc;
    }

    public void setZzgzDesc(String zzgzDesc) {
        this.zzgzDesc = zzgzDesc;
    }

    public void setAcceptanceTime(Timestamp acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public void setPackTime(Timestamp packTime) {
        this.packTime = packTime;
    }

    public Timestamp getLastAccTime() {
        return lastAccTime;
    }

    public void setLastAccTime(Timestamp lastAccTime) {
        this.lastAccTime = lastAccTime;
    }

    public String getLastEngineer() {
        return lastEngineer;
    }

    public void setLastEngineer(String lastEngineer) {
        this.lastEngineer = lastEngineer;
    }

    public String getStratTime() {
        return StratTime;
    }

    public void setStratTime(String stratTime) {
        StratTime = stratTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
