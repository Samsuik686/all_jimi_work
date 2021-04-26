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
 * 2019年7月11日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.app.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.concox.app.common.page.Page;
import cn.concox.app.exam.mapper.MarkScoreMapper;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.Examinee;

/**
 * @FileName MarkScoreService.java
 * @Description:
 *
 * @Date 2019年7月11日 上午11:05:14
 * @author Wang Xirui
 * @version 1.0
 */
@Service
public class MarkScoreService {

    @Autowired
    private MarkScoreMapper markScoreMapper;

    /**
     * @Title: listExam
     * @Description:获取考试场次信息
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:53:02
     */
    public Page<Exam> listExam(Integer currentpage, Integer pageSize, String searchText) {
        Page<Exam> page = new Page<Exam>();
        page.setCurrentPage(currentpage);
        page.setSize(pageSize);
        page = markScoreMapper.listExam(page, searchText);
        return page;
    }

    /**
     * @Title: listExaminee
     * @Description:获取考生列表
     * @param examId
     * @return
     * @author Wang Xirui
     * @date 2019年7月1日 下午5:15:22
     */
    public Page<Examinee> listExaminee(Integer currentpage, Integer pageSize, String examId, String searchText) {
        Page<Examinee> page = new Page<Examinee>();
        page.setCurrentPage(currentpage);
        page.setSize(pageSize);
        page = markScoreMapper.listExaminee(page, examId, searchText);
        return page;
    }

    /**
     * @Title: listExamAnswer
     * @Description:获取考生答题列表
     * @param examId
     * @param examineeId
     * @return
     * @author Wang Xirui
     * @date 2019年7月11日 上午11:16:53
     */
    public List<ExamAnswer> listExamAnswer(String examId, String examineeId) {
        return markScoreMapper.listExamAnswer(examId, examineeId);
    }

    /**
     * @Title: updateExamAnswer
     * @Description:打分
     * @param examAnswer
     * @author Wang Xirui
     * @date 2019年7月1日 下午5:15:28+
     */
    public void updateExamAnswer(List<ExamAnswer> examAnswerList) {
        for (ExamAnswer examAnswer : examAnswerList) {
            markScoreMapper.updateExamAnswer(examAnswer);
        }
    }

}
