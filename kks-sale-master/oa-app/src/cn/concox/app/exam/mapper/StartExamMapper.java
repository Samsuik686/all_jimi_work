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

package cn.concox.app.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.ExamGrade;
import cn.concox.vo.exam.ExamTopic;

/**
 * @FileName StartExamMapper.java
 * @Description:
 *
 * @Date 2019年7月1日 下午3:46:01
 * @author Wang Xirui
 * @version 1.0
 * @param <T>
 */
public interface StartExamMapper extends BaseSqlMapper<Exam> {

    void insertExamAnswer(List<ExamAnswer> examAnswerList);

    Page<Exam> listExam(@Param(PageInterceptor.PAGE_KEY) Page<Exam> page, @Param("examineeId") String examineeId, @Param("searchText")String searchText);

    List<ExamTopic> listExamTopic(@Param("examId") String examId);

    Long getExamStartTime(@Param("examId") String examId, @Param("examineeId") String examineeId);

    void insertExamGrade(ExamGrade examGrade);
    
    Integer countSubjective(@Param("examId") String examId);
    
    Integer countExamAnswer(@Param("examId") String examId, @Param("examineeId") String examineeId);
    
    Exam getExamByExamId(@Param("examId") String examId, @Param("examineeId") String examineeId);
}
