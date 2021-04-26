/*
 * Created: 2016-7-20
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.app.workflow.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.basicdata.service.TipService;
import cn.concox.app.basicdata.service.TipService.TipState;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowRepair;

/**
 * 分拣 接口管理
 * 
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
@Service("workflowSortService")
@Scope("prototype")
public class WorkflowSortService {
    Logger logger = Logger.getLogger("workflowInfo");

    @Resource(name = "workflowMapper")
    private WorkflowMapper<Workflow> workflowMapper;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    @Resource(name = "workflowRepairService")
    private WorkflowRepairService workflowRepairService;

    @Resource(name = "tipService")
    private TipService tipService;

    /**
     * @Title: getCountSortByIds
     * @Description:分拣工站查询选中的数据是否有不是已受理，待分拣？有：不允许分拣、返回受理
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 下午4:01:22
     */
    public int getCountSortByIds(String... ids) {
        return workflowMapper.getCountSortByIds(ids);
    }

    @Transactional
    public void sendRepair(String engineer, String... ids) {
        /**
         * API 调用维修表新增数据接口 WorkflowRepairService.doInset(WorkflowRepair);
         * 
         * @date 2016-08-15 17:45:55
         */
        for (String id : ids) {
            // 如果为18-已测试，待分拣，则发送到维修的状态为16-已测试，待维修
            Workflow workflow = workflowMapper.getById(Integer.valueOf(id));
            if (workflow.getState() == 18) {
                workflowService.updateState(16L, id);
            } else {
                workflowService.updateState(2L, id);
            }
            if (!StringUtil.isEmpty(id)) {
                WorkflowRepair repair = new WorkflowRepair();
                repair.setWfId(Integer.valueOf(id).intValue());
                repair.setEngineer(engineer);
                repair.setRepairAgainCount(0);
                if (workflow.getState() == 18) {
                    repair.setRepairState(9L);
                }
                // TODO 新增维修单
                workflowRepairService.doInset(repair);
            }
        }
        tipService.addTip(TipState.TIP_WX, ids.length);
    }

    /**
     * @Title: updateBackToAccept
     * @Description:返回受理
     * @param state
     * @param ids
     * @author HuangGangQiang
     * @date 2017年7月26日 下午6:54:57
     */
    @Transactional
    public void updateBackToAccept(Integer state, String... ids) {
        workflowMapper.updateBackToAccept(state, ids);
    }

    /**
     * @Title: testBackToAccept
     * @Description:包含已测试的设备返回受理
     * @param ids
     * @author Wang Xirui
     * @date 2019年6月19日 下午2:28:55
     */
    @Transactional
    public void backToAccept(Integer state, String... ids) {
        for (String id : ids) {
            Workflow workflow = workflowMapper.getById(Integer.valueOf(id));
            if (workflow.getState() == 18) {
                workflowMapper.updateBackToAccept(17, id);
                continue;
            }
            workflowMapper.updateBackToAccept(state, id);
        }
    }
}
