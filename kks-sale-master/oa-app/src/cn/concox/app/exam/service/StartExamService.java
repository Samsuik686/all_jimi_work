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

package cn.concox.app.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.common.page.Page;
import cn.concox.app.exam.mapper.StartExamMapper;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.ExamGrade;
import cn.concox.vo.exam.ExamTopic;

/**
 * @FileName StartExamService.java
 * @Description:
 *
 * @Date 2019年7月1日 下午3:36:06
 * @author Wang Xirui
 * @version 1.0
 */
@Service
public class StartExamService {

    @Autowired
    private StartExamMapper startExamMapper;

    /**
     * @Title: saveExamAnswer
     * @Description:保存答案
     * @param examAnswer
     * @author Wang Xirui
     * @date 2019年7月1日 下午5:15:25
     */
    @Transactional
    public void saveExamAnswer(List<ExamAnswer> examAnswerList) {
        startExamMapper.insertExamAnswer(examAnswerList);
    }

    /**
     * @Title: listExam
     * @Description:获取考试场次信息
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:53:02
     */
    public Page<Exam> listExam(Integer currentpage, Integer pageSize, String examineeId, String searchText) {
        Page<Exam> page = new Page<Exam>();
        page.setCurrentPage(currentpage);
        page.setSize(pageSize);
        page = startExamMapper.listExam(page, examineeId, searchText);
        return page;
    }

    /**
     * @Title: listExamTopic
     * @Description:获取考试所有题目
     * @param examId
     * @return
     * @author Wang Xirui
     * @date 2019年7月9日 下午4:09:44
     */
    public List<ExamTopic> listExamTopic(String examId) {
        List<ExamTopic> topicList = startExamMapper.listExamTopic(examId);
        for (ExamTopic topic : topicList) {
            if (topic.getTopicType().equals("1") && topic.getTopicAnswer().length() == 1) {
                topic.setIsSingleSelect(1);
            }
            if (topic.getTopicType().equals("1") && topic.getTopicAnswer().length() > 1) {
                topic.setIsSingleSelect(0);
            }
            topic.setTopicAnswer(null);
        }

        return topicList;
    }

    /**
     * @Title: getExamStartTime
     * @Description:获取考试开始时间
     * @param examGrade
     * @return
     * @author Wang Xirui
     * @date 2019年7月15日 下午3:12:19
     */
    @Transactional
    public Long getExamStartTime(ExamGrade examGrade) {
        Long startTime = startExamMapper.getExamStartTime(examGrade.getExamId(), examGrade.getExamineeId());
        if (startTime != null) {
            return startTime;
        }
        examGrade.setGradeState(-1);
        examGrade.setExamStartTime(System.currentTimeMillis());
        startExamMapper.insertExamGrade(examGrade);
        return null;
    }

    /**
     * @Title: countSubjective
     * @Description:考试场次是否存在主观题
     * @param examId
     * @return
     * @author Wang Xirui
     * @date 2019年7月15日 下午5:57:26
     */
    public boolean countSubjective(String examId) {
        Integer count = startExamMapper.countSubjective(examId);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Title: countExamAnswer
     * @Description:考生是否已提交答案
     * @param examId
     * @param examineeId
     * @return
     * @author Wang Xirui
     * @date 2019年7月16日 下午2:29:17
     */
    public boolean countExamAnswer(String examId, String examineeId) {
        Integer count = startExamMapper.countExamAnswer(examId, examineeId);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Title: getExamByExamId
     * @Description:通过examId获取考试信息
     * @param examId
     * @return
     * @author Wang Xirui
     * @date 2019年7月17日 上午11:10:15
     */
    public Exam getExamByExamId(String examId, String examinee) {
        return startExamMapper.getExamByExamId(examId, examinee);
    }
}
