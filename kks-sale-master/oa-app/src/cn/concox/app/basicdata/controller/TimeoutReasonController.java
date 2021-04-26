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

import cn.concox.app.basicdata.service.TimeoutReasonService;
import cn.concox.comm.Globals;
import cn.concox.comm.alipay.util.httpClient.HttpRequest;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.TimeoutReason;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * 超三天未寄出，超期原因增删改
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
@Controller
@RequestMapping("timeoutReason")
public class TimeoutReasonController extends BaseController {
    @Resource
    private TimeoutReasonService timeoutReasonService;

    @RequestMapping("/getTimeoutReason")
    @ResponseBody
    public APIContent getTimeoutReason(@ModelAttribute TimeoutReason timeoutReason){
        try {
            List<TimeoutReason> list = timeoutReasonService.getTimeoutReason(timeoutReason);
            return  new APIContent(list.size(),list,Globals.OPERA_SUCCESS_CODE);
        }catch (Exception e){
            logger.error(e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("/getTimeoutReasonMap")
    @ResponseBody
    public APIContent getTimeoutReasonMap(HttpServletRequest request){
        try {
            List<TimeoutReason> list = timeoutReasonService.getTimeoutReason(new TimeoutReason());
            List<Map<String,String>> mapList = new ArrayList<>();
            for(TimeoutReason reason:list){
                Map<String,String> tmp = new HashMap<>();
                tmp.put("reason",reason.getReason());
                mapList.add(tmp);
            }
            return  new APIContent(mapList.size(),mapList,Globals.OPERA_SUCCESS_CODE);
        }catch (Exception e){
            logger.error(e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 根据ID删除超期原因
     * @param timeoutReason
     * @return
     */
    @RequestMapping("/removeTimeoutReason")
    @ResponseBody
    public APIContent removeTimeoutReason(@ModelAttribute TimeoutReason timeoutReason){
        try {
            if(timeoutReason.getId() == null){
                return super.operaStatus(Globals.ILLEGAL_PARAMETER,Globals.ILLEGAL_PARAMETER_DESC);
            }
            timeoutReasonService.removeTimeoutReason(timeoutReason);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 添加工作日
     * @param timeoutReason
     * @return
     */
    @RequestMapping("/addTimeoutReason")
    @ResponseBody
    public APIContent addTimoutReason(@ModelAttribute TimeoutReason timeoutReason){
        try {
            timeoutReasonService.addTimeoutReason(timeoutReason);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    /**
     * 添加工作日
     * @param timeoutReason
     * @return
     */
    @RequestMapping("/modifyTimeoutReason")
    @ResponseBody
    public APIContent modifyTimoutReason(@ModelAttribute TimeoutReason timeoutReason){
        try {
            if(timeoutReason.getId() == null ){
                return super.operaStatus(Globals.ILLEGAL_PARAMETER,Globals.ILLEGAL_PARAMETER_DESC);
            }
            timeoutReasonService.modifyTimeoutReason(timeoutReason);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        }catch (Exception e){
            logger.error(e.toString());
            e.printStackTrace();
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }


}
