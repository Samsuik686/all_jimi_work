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

package cn.concox.app.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.exam.Exam;
import cn.concox.vo.exam.ExamAnswer;
import cn.concox.vo.exam.Examinee;

/**
 * @FileName MarkScoreMapper.java
 * @Description: 
 *
 * @Date 2019年7月11日 上午11:06:09
 * @author Wang Xirui
 * @version 1.0
 */
public interface MarkScoreMapper extends BaseSqlMapper<T>{
    
    Page<Exam> listExam(@Param(PageInterceptor.PAGE_KEY) Page<Exam> page, @Param("searchText")String searchText);
    
    Page<Examinee> listExaminee(@Param(PageInterceptor.PAGE_KEY) Page<Examinee> page, @Param("examId")String examId, @Param("searchText")String searchText);
    
    List<ExamAnswer> listExamAnswer(@Param("examId")String examId, @Param("examineeId")String examineeId);
    
    void updateExamAnswer(ExamAnswer examAnswer);
    
}
