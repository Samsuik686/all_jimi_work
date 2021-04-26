package cn.concox.app.workflow.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowRepairMapper;
import cn.concox.app.workflow.mapper.WorkflowTestMapper;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowRepair;
import cn.concox.vo.workflow.WorkflowTest;

/**
 * 售后管理 测试工站
 * 
 * @author TangYuping
 * @version 2017年2月20日 下午4:48:45
 */
@Service("workflowTestService")
@Scope("prototype")
public class WorkflowTestService {

    @Autowired
    private WorkflowTestMapper<WorkflowTest> workflowTestMapper;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    @Autowired
    private WorkflowRepairService workflowRepairService;

    @Autowired
    private WorkflowFicheckService workflowFicheckService;

    @Resource(name = "workflowMapper")
    private WorkflowMapper<Workflow> workflowMapper;

    @Resource(name = "workflowRepairMapper")
    private WorkflowRepairMapper<WorkflowRepair> workflowRepairMapper;

    /**
     * 查询测试工站数据列表
     * 
     * @author TangYuping
     * @version 2017年2月21日 下午2:32:13
     * @param wf
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Workflow> workflowTestList(Workflow wf, Integer currentPage, Integer pageSize) {
        Page<Workflow> pages = new Page<Workflow>();
        pages.setCurrentPage(currentPage);
        pages.setSize(pageSize);
        pages = workflowTestMapper.workflowTestList(pages, wf);
        List<Workflow> list = pages.getResult();
        workflowService.setList(list);
        pages.setResult(list);
        return pages;
    }

    /**
     * 各工站发送数据到测试工站 添加
     * 
     * @author TangYuping
     * @version 2017年2月21日 下午2:33:09
     * @param wfIds
     * @param dataSrouce
     * @param testStatus
     */
    public void insertTest(String[] wfIds, WorkflowTest wt) {
        if (wfIds != null && wfIds.length > 0) {
            for (String id : wfIds) {
                WorkflowTest workflowTest = workflowTestMapper.getWorkflowTestByWfId(id);
                if (workflowTest != null) {
                    workflowTest.setDataSource(wt.getDataSource());
                    workflowTest.setTestStatus(Integer.valueOf(wt.getTestStatus()));
                    workflowTest.setTestPerson(wt.getTestPerson());
                    workflowTest.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    workflowTestMapper.update(workflowTest);
                } else {
                    workflowTest = new WorkflowTest();
                    workflowTest.setDataSource(wt.getDataSource());
                    workflowTest.setTestStatus(Integer.valueOf(wt.getTestStatus()));
                    workflowTest.setWfId(Integer.valueOf(id));
                    workflowTest.setTestPerson(wt.getTestPerson());
                    workflowTest.setSendTime(new Timestamp(System.currentTimeMillis()));
                    workflowTestMapper.insert(workflowTest);
                }
            }
        }
    }

    /**
     * 返回数据到数据来源站
     * 
     * @author TangYuping
     * @version 2017年2月21日 下午3:41:59
     * @param ids
     */
    @Transactional
    public void returnDataSrouce(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String wfId : ids) {
                WorkflowTest test = workflowTestMapper.getWorkflowTestByWfId(wfId);
                if (test != null && test.getTid() > 0) {
                    String dataSource = test.getDataSource();
                    Long state = 0L;
                    if (dataSource.indexOf("受理站") > -1) {
                        Workflow workflow = workflowMapper.getById(Integer.valueOf(wfId));
                        if (workflow.getState() == 19) {
                            state = 17L;
                        } else {
                            state = 0L;
                        }
                    } else if (dataSource.indexOf("维修站") > -1) {
                        state = 16L; // 已测试，待维修
                        workflowRepairService.updateStateByWfids(9L, wfId); // TODO 维修表 处于 已测试，待维修
                    } else if (dataSource.indexOf("终检站") > -1) {
                        state = 13L;
                        workflowFicheckService.updateStateByWfid(0, wfId); // TODO 终检表 处于待终检状态
                    }
                    workflowService.updateState(state, wfId);
                }
            }
            workflowTestMapper.updateStatus(-1L, ids);
        }
    }

    /**
     * 添加测试结果
     * 
     * @author TangYuping
     * @version 2017年2月22日 上午9:37:50
     * @param wt
     */
    @Transactional
    public void saveOrUpdateTestResult(WorkflowTest wt, String currentUserName) {
        if (null == wt.getTestTime() || "".equals(wt.getTestTime())) {
            wt.setTestTime(new Timestamp(System.currentTimeMillis()));
        }
        if (null == wt.getTestPerson() || "".equals(wt.getTestPerson())) {
            wt.setTestPerson(currentUserName);
        }
        wt.setTestStatus(3); // 修改测试工站状态，测试完成
        workflowTestMapper.update(wt);
    }

    /**
     * 测试人员获取数据，设置当前测试人员
     * 
     * @author TangYuping
     * @version 2017年3月2日 上午9:54:19
     * @param tids
     */
    @Transactional
    public void updateTestPerson(String currentUserName, String[] ids) {
        workflowTestMapper.updateTestPerson(currentUserName, ids);
    }

    /**
     * @Title: getCountToComeBackByWfids
     * @Description:测试工站查询选中的数据是否有不是已完成？有：不允许点击返回数据来源工站
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月10日 上午10:03:07
     */
    public int getCountToComeBackByWfids(String... ids) {
        return workflowTestMapper.getCountToComeBackByWfids(ids);
    }

    /**
     * @Title: getCountUpdateByWfids
     * @Description:测试工站查询选中的数据是否有不是未完成或未发送？有：不允许点击修改
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月10日 上午10:56:50
     */
    public int getCountUpdateByWfids(String... ids) {
        return workflowTestMapper.getCountUpdateByWfids(ids);
    }

    /**
     * @Title: setNotPrice
     * @Description:设置为不报价状态
     * @param Workflow
     * @param currentUserName
     * @author Wang Xirui
     * @date 2019年6月18日 上午10:55:10
     */
    @Transactional
    public void setNotPrice(String currentUserName, String... ids) {
        for (String wfId : ids) {
            WorkflowTest workflowTest = workflowTestMapper.getWorkflowTestByWfId(wfId);
            if (null == workflowTest.getTestTime()) {
                workflowTest.setTestTime(new Timestamp(System.currentTimeMillis()));
            }
            if (StringUtil.isEmpty(workflowTest.getTestPerson())) {
                workflowTest.setTestPerson(currentUserName);
            }

            // 修改测试工站状态，4-不报价，测试中
            workflowTest.setTestStatus(4);
            workflowTestMapper.update(workflowTest);

            if (workflowTest.getDataSource().equals("受理站")) {
                // 17-已受理，已测试
                workflowService.updateState(19L, wfId);
            }
            if (workflowTest.getDataSource().equals("维修站")) {
                // 维修10-不报价，测试中；19-不报价，测试中
                WorkflowRepair workflowRepair = new WorkflowRepair();
                workflowRepair.setWfId(Integer.valueOf(wfId));
                workflowRepair.setRepairState(10L);
                workflowRepair.setIsPrice(1);
                workflowRepairMapper.update(workflowRepair);
                workflowService.updateState(19L, wfId);
            }
        }
    }

}
