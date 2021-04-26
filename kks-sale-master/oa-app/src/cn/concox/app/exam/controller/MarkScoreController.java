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

package cn.concox.app.exam.controller;

import java.util.List;

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
import cn.concox.app.exam.service.MarkScoreService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.ExamGrade;
import cn.concox.vo.exam.Examinee;
import sso.comm.model.UserInfo;

/**
 * @FileName MarkScore.java
 * @Description:
 *
 * @Date 2019年7月11日 上午10:36:38
 * @author Wang Xirui
 * @version 1.0
 */

@Controller
@Scope("prototype")
@RequestMapping("/markScore")
public class MarkScoreController extends BaseController {

    Logger logger = Logger.getLogger("MarkScoreController");
    
    @Resource(name = "markScoreService")
    private MarkScoreService markScoreService;
    
    @Resource(name = "examGradeService")
    private ExamGradeService examGradeService;
    
    /**
     * @Title: queryExam
     * @Description:获取考试场次列表
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:54:54
     */
    @RequestMapping("/queryExam")
    @ResponseBody
    public APIContent queryExam(@ModelAttribute CommonParam comp, String searchText) {
        try {
            Page<Exam> examPage = markScoreService.listExam(comp.getCurrentpage(), comp.getPageSize(), searchText);
            List<Exam> examList = examPage.getResult();
            
            return super.putAPIData(examList);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: listExaminee
     * @Description:查询考生列表
     * @param examId
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:39:24
     */
    @RequestMapping("/queryExaminee")
    @ResponseBody
    public APIContent queryExaminee(@ModelAttribute CommonParam comp, String examId, String searchText) {
        try {
            Page<Examinee> examineePage = markScoreService.listExaminee(comp.getCurrentpage(), comp.getPageSize(), examId, searchText);
            List<Examinee> examineeList = examineePage.getResult();
            
            return super.putAPIData(examineeList);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: listExamAnswer 
     * @Description:获取考生答题信息
     * @param examId
     * @param examineeId
     * @return 
     * @author Wang Xirui
     * @date 2019年7月11日 上午11:24:38
     */
    @RequestMapping("/listExamAnswer")
    @ResponseBody
    public APIContent listExamAnswer(String examId, String examineeId) {
        try {
            List<ExamAnswer> examAnswerList = markScoreService.listExamAnswer(examId, examineeId);
            
            return super.putAPIData(examAnswerList);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: updateExamAnswer
     * @Description:打分
     * @param examAnswer
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年7月5日 下午3:40:32
     */
    @RequestMapping("/updateExamAnswer")
    @ResponseBody
    public APIContent updateExamAnswer(String answerJson, String examId, HttpServletRequest req) {
        try {
            List<ExamAnswer> examAnswerList = JSON.parseArray(answerJson, ExamAnswer.class); 
            // 保存分数
            markScoreService.updateExamAnswer(examAnswerList);
            
            UserInfo user = getSessionUser(req);
            String examineeId = user.getuId();
            ExamGrade examGrade = new ExamGrade(examId, examineeId);
            examGrade.setGradeState(1);
            // 更变已评阅状态
            examGradeService.updateExamGrade(examGrade);

            // 计算总分
            examGradeService.updateExamGradeByExamId(examId, examineeId);
            
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
