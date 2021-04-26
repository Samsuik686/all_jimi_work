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

package cn.concox.app.exam.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.concox.app.common.page.Page;
import cn.concox.app.exam.service.ExamGradeService;
import cn.concox.app.exam.service.StartExamService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.ExamGrade;
import cn.concox.vo.exam.ExamTopic;
import sso.comm.model.UserInfo;

/**
 * @FileName StartExamController.java
 * @Description:
 *
 * @Date 2019年7月1日 下午3:30:09
 * @author Wang Xirui
 * @version 1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/startExam")
public class StartExamController extends BaseController {

    Logger logger = Logger.getLogger("StartExamController");

    @Resource(name = "startExamService")
    private StartExamService startExamService;

    @Resource(name = "examGradeService")
    private ExamGradeService examGradeService;

    /**
     * @Title: saveExamAnswer
     * @Description:保存答案
     * @param examAnswer
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:40:00
     */
    @RequestMapping("/saveExamAnswer")
    @ResponseBody
    public APIContent saveExamAnswer(String answerJson, String examId, HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            String examineeId = user.getuId();
            String examineeName = user.getuName();
            // 过滤重复提交
            boolean was = startExamService.countExamAnswer(examId, examineeId);
            if (was) {
                return operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
            List<ExamAnswer> examAnswerList = JSON.parseArray(answerJson, ExamAnswer.class);
            for (ExamAnswer answer : examAnswerList) {
                answer.setAnswerId(UUID.randomUUID().toString().replaceAll("-", ""));
                answer.setExamineeId(examineeId);
                answer.setExamineeName(examineeName);
            }
            startExamService.saveExamAnswer(examAnswerList);

            ExamGrade examGrade = new ExamGrade(examId, examineeId);
            Long examStartTime = startExamService.getExamStartTime(examGrade);
            Long endTime = System.currentTimeMillis();
            Long costTime = (endTime - examStartTime) / (1000 * 60);
            examGrade.setCostTime(costTime.intValue());
            examGrade.setGradeState(0);
            // 未评阅状态
            examGradeService.updateExamGrade(examGrade);

            // 计算客观题分数
            examGradeService.markChoiceScore(examId, examineeId);

            // 是否存在主观题
            boolean exist = startExamService.countSubjective(examId);
            if (!exist) {
                examGradeService.updateExamGradeByExamId(examId, examineeId);

                examGrade.setGradeState(1);
                examGrade.setCostTime(null);
                // 已评阅状态
                examGradeService.updateExamGrade(examGrade);
            }
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: queryExam
     * @Description:获取考试场次列表
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:54:54
     */
    @RequestMapping("/queryExam")
    @ResponseBody
    public APIContent queryExam(@ModelAttribute CommonParam comp, String searchText, HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            String examineeId = user.getuId();

            Page<Exam> examPage = startExamService.listExam(comp.getCurrentpage(), comp.getPageSize(), examineeId, searchText);
            List<Exam> examList = examPage.getResult();
            return super.putAPIData(examList);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: queryExamTopicList
     * @Description:查询所有题目
     * @param examId
     * @return
     * @author Wang Xirui
     * @date 2019年7月9日 下午4:13:04
     */
    @RequestMapping("/queryExamTopicList")
    @ResponseBody
    public APIContent queryExamTopicList(String examId) {
        try {
            List<ExamTopic> topicList = startExamService.listExamTopic(examId);
            return super.putAPIData(topicList);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: queryExamStartTime
     * @Description:获取考试开始时间
     * @param examId
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年7月15日 下午3:13:58
     */
    @RequestMapping("/queryExamStartTime")
    @ResponseBody
    public APIContent queryExamStartTime(String examId, HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            String examineeId = user.getuId();
            String examineeName = user.getuName();
            ExamGrade examGrade = new ExamGrade(examId, examineeId, examineeName);
            
            Long examStartTime = startExamService.getExamStartTime(examGrade);
            return super.putAPIData(examStartTime);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: queryExamByExamId 
     * @Description:获取单场考试信息
     * @param examId
     * @param req
     * @return 
     * @author Wang Xirui
     * @date 2019年7月17日 上午11:12:00
     */
    @RequestMapping("/queryExamByExamId")
    @ResponseBody
    public APIContent queryExamByExamId(String examId, HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            String examineeId = user.getuId();
            
            Exam exam = startExamService.getExamByExamId(examId, examineeId);
            return super.putAPIData(exam);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
