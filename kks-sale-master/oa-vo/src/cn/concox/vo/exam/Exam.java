package cn.concox.vo.exam;

import java.util.Date;

public class Exam {

    private Long id;

    private String[] examIds;

    // 考试编号
    private String examId;

    // 考试名称
    private String examName;

    // 考试描述
    private String examDescription;

    // 考试总时间
    private Integer examTotalTime;

    // 考试创建人
    private String creater;

    // 考试创建时间
    private Date createTime;

    // 考试状态
    private String state;

    // 考生答题状态
    private Integer answerStatus;

    // 参与考生数量
    private Integer totalNum;

    // 已批阅考生数量
    private Integer doneNum;

    public String[] getExamIds() {
        return examIds;
    }

    public void setExamIds(String[] examIds) {
        this.examIds = examIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDescription() {
        return examDescription;
    }

    public void setExamDescription(String examDescription) {
        this.examDescription = examDescription;
    }

    public Integer getExamTotalTime() {
        return examTotalTime;
    }

    public void setExamTotalTime(Integer examTotalTime) {
        this.examTotalTime = examTotalTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getDoneNum() {
        return doneNum;
    }

    public void setDoneNum(Integer doneNum) {
        this.doneNum = doneNum;
    }

}
