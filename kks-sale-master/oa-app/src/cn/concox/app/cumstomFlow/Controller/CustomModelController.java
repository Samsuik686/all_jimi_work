package cn.concox.app.cumstomFlow.Controller;

import cn.concox.app.common.page.Page;
import cn.concox.app.cumstomFlow.CustomFlowCache;
import cn.concox.app.cumstomFlow.service.CustomFlowService;
import cn.concox.app.cumstomFlow.service.CustomTaskService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.customProcess.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sso.comm.model.UserInfo;
import sso.comm.util.SSOUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author 183
 * @version 2020年5月28日
 */

@Controller
@RequestMapping("/customModel")
@Scope("prototype")
public class CustomModelController extends BaseController {
    Logger log = Logger.getLogger(CustomModelController.class);

    @Autowired
    private CustomFlowService customFlowService;

    @Resource(name = "customTaskService")
    private CustomTaskService customTaskService;

    @Autowired
    private CustomFlowCache customFlowCache;

    /**
     * 获取模块信息
     *
     * @author 183
     * @version 2020年5月28日 下午2:13:03
     */
    @RequestMapping("/customModelByIdAndBelong")
    @ResponseBody
    public APIContent customModelByIdAndBelong(@ModelAttribute CustomModel cm) {
        try {
            return super.putAPIData(getCustomModel(cm));
        } catch (RuntimeException re) {
            log.error("获取模块数据失败:", re);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, re.getMessage());
        } catch (Exception e) {
            log.error("获取模块数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取子模块信息
     *
     * @author 183
     * @version 2020年5月28日 下午2:13:03
     */
    @RequestMapping("/customModelBackByBelong")
    @ResponseBody
    public APIContent customModelBackByBelong(@ModelAttribute CustomModel cm) {
        try {
            CustomModel customModel = getCustomModel(cm);
            if (StringUtil.isEmpty(customModel.getBackNodes())) {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "找不到后继节点，请联系管理员");
            }
            List<String> ids = new ArrayList<>(Arrays.asList(customModel.getBackNodes().split(",")));
            CustomModel query2 = new CustomModel();
            query2.setIdList(ids);
            List<CustomModel> list2 = customFlowService.queryCustomModelByIds(query2);
            return super.putAPIData(list2);
        } catch (RuntimeException re) {
            log.error("获取子模块数据失败:", re);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, re.getMessage());
        } catch (Exception e) {
            log.error("获取子模块数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 根据查询条件，获取唯一查询结果
     */
    private CustomModel getCustomModel(CustomModel cm) {
        if (cm == null || StringUtil.isEmpty(cm.getId()) || StringUtil.isEmpty(cm.getBelong())) {
            throw new RuntimeException("参数错误");
        }
        CustomModel query = new CustomModel();
        // 0代表总览节点
        if ("0".equals(cm.getId())) {
            query.setType(0);
        } else {
            query.setId(cm.getId());
        }
        query.setBelong(cm.getBelong());
        return getCustomModelByQuery(query);
    }

    /**
     * 根据查询条件，获取唯一查询结果，重载方法
     */
    private CustomModel getCustomModel(String id) {
        if (StringUtil.isEmpty(id)) {
            throw new RuntimeException("参数为空");
        }
        CustomModel query = new CustomModel();
        query.setId(id);
        return getCustomModelByQuery(query);
    }

    private CustomModel getCustomModelByQuery(CustomModel query) {
        List<CustomModel> list = customFlowService.queryCustomModel(query);
        if (list == null || list.size() > 1) {
            throw new RuntimeException("查到多个模块信息，请联系管理员");
        }
        if (list.size() == 0) {
            throw new RuntimeException("查不到模块信息，请联系管理员");
        }
        return list.get(0);
    }

    /*
     * 获取模块对应的跟进人
     */
    @RequestMapping("/getFollowers")
    @ResponseBody
    public APIContent getFollowers(String id) {
        try {
            CustomModel customModel = getCustomModel(id);
            Map<String, String> resultMap = new HashMap<>();
            if (1 == customModel.getType()) {
                resultMap.put("1", "结束");
            } else {
                List<String> followers = customFlowService.queryFollowers(customModel.getMenuId());
                SSOUtils utils = new SSOUtils();
                for (String follower : followers) {
                    UserInfo userInfo = utils.getUserInfo(follower);
                    resultMap.put(follower, userInfo.getuName());
                }
            }
            return super.putAPIData(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取异常反馈列表失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 查询条件(日期)
     */
    public void searchBy(CustomTask customTask, HttpServletRequest req) {
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        if (!StringUtils.isBlank(startTime)) {
            customTask.setStartTime(startTime);
        }
        if (!StringUtils.isBlank(endTime)) {
            customTask.setEndTime(endTime);
        }
    }

    /*
     * 自定义任务分页查询
     */
    @RequestMapping("/customTaskList")
    @ResponseBody
    public APIContent getCustomTaskListPage(@ModelAttribute CustomTask customTask, @ModelAttribute CommonParam comp,
                                            HttpServletRequest req) {
        try {
            searchBy(customTask, req);
            String userId = super.getSessionUser(req).getuId();
            // 总览界面查询所有
            if ("0".equals(customTask.getModelId())) {
                customTask.setModelId(null);
            }
            Page<CustomTask> customTaskListPage = customTaskService.getCustomTaskListPage(customTask,
                    comp.getCurrentpage(), comp.getPageSize(), userId);
            req.getSession().setAttribute("totalValue", customTaskListPage.getTotal());
            return super.putAPIData(customTaskListPage.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取异常反馈列表失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /*
     * 查询任务详情
     */
    @RequestMapping("/selectCustomTask")
    @ResponseBody
    public APIContent selectCustomTask(@ModelAttribute CustomTask customTask) {
        log.info("异常反馈查询开始");
        try {
            if (customTask.getId() != null) {
                CustomTask result = customTaskService.select(customTask);
                return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", result));
            } else {
                log.error("异常反馈查询失败！");
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            log.error("异常反馈查询失败！", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /*
     * 更新和新增任务
     */
    @RequestMapping("/addOrUpdateCustomTask")
    @ResponseBody
    public APIContent addOrUpdateCustomTask(HttpServletRequest req, @ModelAttribute CustomTask customTask,
                                            @RequestParam(value = "files", required = false) MultipartFile[] multipartFile) {
        log.info("更新异常反馈信息开始");
        try {
            // 带引号的无法用ajax上传，将引号改成单引号上传，接受之后替换为双引号
            customTask.setParams(customTask.getParams().replaceAll("'", "\""));
            UserInfo user = (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);

            if (customTask.getId() == null) {
                customTask.setSerial(UUID.randomUUID().toString().replaceAll("-", ""));
                customTask.setCreater(user.getuId());
                customTask.setUpdater(user.getuId());
                customTask.setState(0);
                customTask.setCreateDate(new Date());
                customTask.setUpdateDate(new Date());
                customTaskService.insert(customTask, multipartFile, user.getuId());
                return new APIContent(Globals.OPERA_SUCCESS_CODE, "新增任务成功" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                customTask.setUpdater(user.getuId());
                customTask.setUpdateDate(new Date());
                customTaskService.update(customTask, multipartFile, user.getuId());
                return new APIContent(Globals.OPERA_SUCCESS_CODE, "修改任务成功" + Globals.OPERA_SUCCESS_CODE_DESC);
            }
        } catch (Exception e) {
            log.error("更新异常反馈信息失败！", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /*
     * 删除异常反馈
     */
    @RequestMapping("/deleteCustomTask")
    @ResponseBody
    public APIContent deleteCustomTask(HttpServletRequest req,@ModelAttribute CustomTask customTask) {
        log.info("删除异常反馈开始");
        try {
            String idSplit = req.getParameter("ids");
            String[] serials = idSplit.split(",");
            if (serials.length > 0) {
                customTaskService.detele(serials);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "删除成功" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                log.error("删除异常反馈失败！");
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            log.error("删除异常反馈！", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 导出自定义流程任务
     */
    @RequestMapping("/exportDatas")
    @ResponseBody
    public void exportDatas(@ModelAttribute CustomTask customTask, HttpServletRequest req,
                            HttpServletResponse response) {
        try {
            searchBy(customTask, req);
            String userId = super.getSessionUser(req).getuId();
            customTaskService.exportDatas(customTask, req, response, userId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出异常反馈信息数据失败:", e);
        }
    }

    /**
     * 列表查询不分页
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public APIContent queryList(@ModelAttribute CustomTask customTask) {
        log.info("列表查询开始");
        try {
            List<CustomTask> customTaskList = customTaskService.queryList(customTask);
            return super.putAPIData(customTaskList);
        } catch (Exception e) {
            log.error("获取异常反馈列表失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 异常反馈发送数据到下一工站
     *
     * @author TangYuping
     * @version 2017年3月9日 下午1:50:01
     */
    @RequestMapping("/sendDataToNextSite")
    @ResponseBody
    public APIContent sendDataToNextSite(@ModelAttribute CustomTask customTask, HttpServletRequest req) {
        try {
            String idSplit = req.getParameter("ids");
            CustomModel query = new CustomModel();
            query.setId(customTask.getModelId());
            CustomModel customModel = getCustomModelByQuery(query);

            // 如果是结束模块，则完成任务
            if ("1".equals(customTask.getFollower())) {
                customTaskService.updateStateFinish(idSplit.split(","));
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "数据流程已完成！");
            }
            // 非结束模块，获取id相关信息
            String mess = customTaskService.sendData(customTask, idSplit, req, customModel);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, mess);
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }

    }

    /**
     * 异常反馈下载附件
     *
     * @author TangYuping
     * @version 2017年4月14日 下午2:19:18
     */
    @RequestMapping("/downLoadFile")
    @ResponseBody
    public void downProductFile(@ModelAttribute CustomTaskFile file, HttpServletRequest req,
                                HttpServletResponse response) {
        try {
            customTaskService.downLoadFile(file, req, response);
        } catch (Exception e) {
            log.error("下载文件失败:", e);
        }
    }

    /**
     * 删除附件信息
     *
     * @author TangYuping
     * @version 2017年4月14日 下午2:22:57!
     */
    @RequestMapping("/deleteFileByFid")
    @ResponseBody
    public APIContent deleteFileByFid(@ModelAttribute CustomTaskFile file) {
        try {
            customTaskService.deleteFileByFid(file);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            log.error("deleteFileByFid异常", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "删除附件信息失败！");
        }
    }

    /**
     * 根据异常反馈ID获取对应的附件信息
     *
     * @author TangYuping
     * @version 2017年4月14日 上午11:48:51
     */
    @RequestMapping("/getFileByYid")
    @ResponseBody
    public APIContent getFileByYid(@Param("yid") String yid) {
        try {
            List<CustomTaskFile> fileList = customTaskService.getFileByYid(Integer.valueOf(yid));
            return super.putAPIData(fileList);
        } catch (Exception e) {
            log.error("根据异常反馈ID获取附件失败！", e);
            return super.putAPIData(new ArrayList<CustomTaskFile>());
        }
    }

//	----------------------------------------------自定义字段-----------------------------------------

    /**
     * 增加自定义字段
     *
     * @author TangYuping
     * @date 2017年4月14日 上午11:48:51
     */
    @RequestMapping("/addCustomField")
    @ResponseBody
    public APIContent addCustomField(@ModelAttribute CustomFlow customFlow) {
        try {
            ObjectMapper om = new ObjectMapper();
            CollectionType arrayType = om.getTypeFactory().constructCollectionType(ArrayList.class, CustomField.class);
            List<CustomField> cfList = om.readValue(customFlow.getFields().replaceAll("'", "\""), arrayType);
            // 自定义字段校验
            Set<String> sets = new HashSet<>();
            for (CustomField cf : cfList) {
                if (StringUtil.isEmpty(cf.getName())) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "字段设置失败，字段名称不能为空");
                }
                if (sets.contains(cf.getName())) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "字段设置失败，重复字段=" + cf.getName());
                }
                sets.add(cf.getName());
                cf.setBelong(customFlow.getName());
            }
            customFlowService.updateFields(customFlow.getName(), cfList);
            customFlowCache.updateCustomFieldMap();
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            log.error("addCustomField失败！", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "字段设置失败！");
        }
    }

    /**
     * 查询自定义字段
     *
     * @author TangYuping
     * @version 2017年4月14日 上午11:48:51
     */
    @RequestMapping("/getCustomField")
    @ResponseBody
    public APIContent getCustomField(String flowName) {
        try {
            return super.putAPIData(customFlowCache.getCustomFieldMap().get(flowName));
        } catch (Exception e) {
            log.error("根据异常反馈ID获取附件失败！", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "字段设置失败！");
        }
    }

}
