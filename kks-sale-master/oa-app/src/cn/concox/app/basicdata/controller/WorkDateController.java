/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2020.
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
 * 2020-09-22             pengshoulong   Create the class
 * http://www.jimilab.com/
 */


package cn.concox.app.basicdata.controller;

import cn.concox.app.basicdata.service.WorkDateService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.WorkDate;
import cn.concox.vo.basicdata.WorkDateQuery;
import org.drools.audit.event.RuleFlowGroupLogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 * 工作日
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
@Controller
@RequestMapping("workDate")
public class WorkDateController extends BaseController {
    Logger logger = LoggerFactory.getLogger(WorkDateController.class);

    @Resource
    private WorkDateService workDateService;

    //前端传来日期的转换问题。
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    /**
     * 分页获取
     * @param query
     * @return
     */
    @RequestMapping(value = "/getWorkDate")
    @ResponseBody
    public APIContent getWorkDate(@ModelAttribute WorkDateQuery query){
        try {
            if (query == null) {
                return super.operaStatus(Globals.ILLEGAL_PARAMETER, Globals.ILLEGAL_PARAMETER_DESC);
            }
            Page<WorkDate> page = new Page<>();
            page.setSize(query.getPageSize());
            page.setCurrentPage(query.getPageNum());
            workDateService.getWorkDate(page, query);
            return new APIContent(page.getTotal(), page.getResult(), Globals.OPERA_SUCCESS_CODE);
        }catch (Exception e){
            logger.error(e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 更新工作日
     * @param workDate
     * @return
     */
    @RequestMapping(value = "/modifyWorkDate")
    @ResponseBody
    public APIContent setWorkDate(@ModelAttribute WorkDateQuery workDate){
        try {
           if(workDateService.setWorkDate(workDate)) {
               return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
           } else{
                return super.operaStatus(Globals.ILLEGAL_PARAMETER,"数据已存在");
           }
        }catch (Exception e){
            logger.error(e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping(value = "/batchAdd")
    @ResponseBody
    public APIContent batchAdd(){
        try {
            workDateService.batchAdd();
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
        }catch (Exception e){
            logger.error(e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
