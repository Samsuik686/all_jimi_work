package cn.concox.vo.exam;

import java.util.Date;

public class ExamGrade {

    // 主键
    private Integer id;

    // 考试编号
    private String examId;

    // 考试名称
    private String examName;

    // 考生编号
    private String examineeId;

    // 考生姓名
    private String examineeName;

    // 考生成绩
    private Integer grade;

    // 评阅状态
    private Integer gradeState;

    // 考试用时（分钟）
    private Integer costTime;

    // 开始时间
    private Date createTime;

    /**
     * 考试开始时间
     */
    private Long examStartTime;

    // 排序列
    private String sortNo;

    public ExamGrade() {

    }
    
    public ExamGrade(String examId, String examineeId) {
        this.examId = examId;
        this.examineeId = examineeId;
    }

    public ExamGrade(String examId, String examineeId, String examineeName) {
        this.examId = examId;
        this.examineeId = examineeId;
        this.examineeName = examineeName;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    public String getExamineeName() {
        return examineeName;
    }

    public void setExamineeName(String examineeName) {
        this.examineeName = examineeName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGradeState() {
        return gradeState;
    }

    public void setGradeState(Integer gradeState) {
        this.gradeState = gradeState;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Long getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(Long examStartTime) {
        this.examStartTime = examStartTime;
    }

}
