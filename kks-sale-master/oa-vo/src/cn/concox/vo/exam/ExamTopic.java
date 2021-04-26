package cn.concox.vo.exam;

public class ExamTopic {

    private long id;

    // 试题编号
    private String topicId;

    // 考试编号
    private String examId;

    // 试题序号
    private Integer topicSequence;

    // 试题类型（1-选择题，2-判断题，3-填空题，4-问答题）
    private String topicType;

    // 试题题目描述
    private String topicDescription;

    // 试题分值
    private String topicScore;

    // 试题答案
    private String topicAnswer;

    // 选项A描述
    private String topicOptionA;

    // 选项B描述
    private String topicOptionB;

    // 选项C描述
    private String topicOptionC;

    // 选项D描述
    private String topicOptionD;

    /**
     * 1-单选；0-多选
     */
    private Integer isSingleSelect;

    public Integer getIsSingleSelect() {
        return isSingleSelect;
    }

    public void setIsSingleSelect(Integer isSingleSelect) {
        this.isSingleSelect = isSingleSelect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Integer getTopicSequence() {
        return topicSequence;
    }

    public void setTopicSequence(Integer topicSequence) {
        this.topicSequence = topicSequence;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getTopicScore() {
        return topicScore;
    }

    public void setTopicScore(String topicScore) {
        this.topicScore = topicScore;
    }

    public String getTopicAnswer() {
        return topicAnswer;
    }

    public void setTopicAnswer(String topicAnswer) {
        this.topicAnswer = topicAnswer;
    }

    public String getTopicOptionA() {
        return topicOptionA;
    }

    public void setTopicOptionA(String topicOptionA) {
        this.topicOptionA = topicOptionA;
    }

    public String getTopicOptionB() {
        return topicOptionB;
    }

    public void setTopicOptionB(String topicOptionB) {
        this.topicOptionB = topicOptionB;
    }

    public String getTopicOptionC() {
        return topicOptionC;
    }

    public void setTopicOptionC(String topicOptionC) {
        this.topicOptionC = topicOptionC;
    }

    public String getTopicOptionD() {
        return topicOptionD;
    }

    public void setTopicOptionD(String topicOptionD) {
        this.topicOptionD = topicOptionD;
    }

}
