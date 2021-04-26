/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording, 
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 * 
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2019年7月1日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.vo.exam;

import java.io.Serializable;
import java.util.Date;

/**
 * @FileName ExamAnswer.java
 * @Description:
 *
 * @Date 2019年7月1日 下午3:58:49
 * @author Wang Xirui
 * @version 1.0
 */
public class ExamAnswer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 传递参数使用
    private String examAnswer;

    public String getExamAnswer() {
        return examAnswer;
    }

    public void setExamAnswer(String examAnswer) {
        this.examAnswer = examAnswer;
    }

    /**
     * 答题编号
     */
    private String answerId;

    /**
     * 考生ID
     */
    private String examineeId;

    /**
     * 考生姓名
     */
    private String examineeName;

    /**
     * 题目ID
     */
    private String topicId;

    /**
     * 考生答案
     */
    private String examineeAnswer;

    /**
     * 考生得分
     */
    private Integer examineeScore;

    /**
     * 答题时间
     */
    private Date creationDate;

    /**
     * 题目描述
     */
    private String topicDescription;

    /**
     * 题目分值
     */
    private Integer topicScore;

    /**
     * 题目答案
     */
    private String topicAnswer;

    /**
     * 题目类型
     */
    private String topicType;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getExamineeAnswer() {
        return examineeAnswer;
    }

    public void setExamineeAnswer(String examineeAnswer) {
        this.examineeAnswer = examineeAnswer;
    }

    public Integer getExamineeScore() {
        return examineeScore;
    }

    public void setExamineeScore(Integer examineeScore) {
        this.examineeScore = examineeScore;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public Integer getTopicScore() {
        return topicScore;
    }

    public void setTopicScore(Integer topicScore) {
        this.topicScore = topicScore;
    }

    public String getTopicAnswer() {
        return topicAnswer;
    }

    public void setTopicAnswer(String topicAnswer) {
        this.topicAnswer = topicAnswer;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

}
