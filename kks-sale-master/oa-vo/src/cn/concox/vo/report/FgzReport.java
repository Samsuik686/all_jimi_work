package cn.concox.vo.report;

public class FgzReport {

    private String boardModel; // 机型

    private String faultType; // 故障类型

    private String number; // 故障次数

    private String percent; // 百分比

    private String startTime; // 开始日期

    private String endTime; // 结束日期

    private String isWarranty;// 是否保修

    private String topfive; // 区分查询前五项还是所有

    /**
     * 是否发货
     */
    private Boolean isSend;

    public String getBoardModel() {
        return boardModel;
    }

    public void setBoardModel(String boardModel) {
        this.boardModel = boardModel;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
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

    public String getIsWarranty() {
        return isWarranty;
    }

    public void setIsWarranty(String isWarranty) {
        this.isWarranty = isWarranty;
    }

    public String getTopfive() {
        return topfive;
    }

    public void setTopfive(String topfive) {
        this.topfive = topfive;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

}
