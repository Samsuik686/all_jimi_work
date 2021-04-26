package cn.concox.app.aipay.controller;

import cn.concox.app.aipay.service.CustomerClientService;
import cn.concox.app.basicdata.service.SxdwmanageService;
import cn.concox.app.basicdata.service.ValidateCodeService;
import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowPriceService;
import cn.concox.app.workflow.service.WorkflowRepairService;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.alipay.config.AlipayConfig;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.sms.other.SMSConstants;
import cn.concox.comm.sms.other.UCPaasUtils;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.aipay.CustomerClient;
import cn.concox.vo.aipay.PayRecord;
import cn.concox.vo.aipay.ReturnJson;
import cn.concox.vo.aipay.SaleFeedback;
import cn.concox.vo.basicdata.Sxdwmanage;
import cn.concox.vo.basicdata.ValidateCode;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.commvo.TimeTree;
import cn.concox.vo.workflow.Workflow;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户工作空间管理
 * 
 * @author Li.Shangzhi
 * @date 2016-08-22 18:18:25
 */
@Controller
@RequestMapping("/custClient")
@Scope("prototype")
public class CustomerClientController extends BaseController {

    @Resource(name = "customerClientService")
    private CustomerClientService customerClientService;

    @Resource(name = "sxdwmanageService")
    public SxdwmanageService sxdwmanageService;

    @Resource(name = "workflowPriceService")
    private WorkflowPriceService workflowPriceService;

    @Resource(name = "workflowRepairService")
    private WorkflowRepairService workflowRepairService;

    @Resource(name = "validateCodeService")
    private ValidateCodeService validateCodeService;

    /**
     * 登录进入客户反馈操作中心
     * 
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getAiPaytList")
    @ResponseBody
    public APIContent getAiPaytList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String batchPhone = request.getParameter("batchPhone");
            String loginPhone = request.getParameter("loginPhone");
            String batchNumber = request.getParameter("batchNumber");
            String loginPassword = request.getParameter("loginPassword");
            CustomerClient client = new CustomerClient();
            if (!StringUtil.isRealEmpty(batchPhone) && !StringUtil.isRealEmpty(batchNumber)) {
                client.setPhone(batchPhone.trim());
                client.setRepairnNmber(batchNumber.trim());
            } else if (!StringUtil.isRealEmpty(loginPhone) && !StringUtil.isRealEmpty(loginPassword)) {
                client.setPhone(loginPhone.trim());
                client.setLoginPwd(loginPassword.trim());
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "输入信息不完整");
            }

            // TODO 数据校验
            if (null != client && customerClientService.checkClientLogin(client)) {
                if (!StringUtil.isRealEmpty(batchPhone) && !StringUtil.isRealEmpty(batchNumber)) {
                    request.getSession().setAttribute(GlobalCons.PHONENUMBER, batchPhone.trim());
                    request.getSession().setAttribute(GlobalCons.BATCHNUMBER, batchNumber.trim());
                } else if (!StringUtil.isRealEmpty(loginPhone) && !StringUtil.isRealEmpty(loginPassword)) {
                    request.getSession().setAttribute(GlobalCons.LOGINPWD, loginPassword.trim());
                    // 查询最新的批次号,显示到首页
                    String repairNumber = customerClientService.getLatestRepairNumber(loginPhone.trim());
                    if (!StringUtil.isRealEmpty(repairNumber)) {
                        request.getSession().setAttribute(GlobalCons.PHONENUMBER, loginPhone.trim());
                        request.getSession().setAttribute(GlobalCons.BATCHNUMBER, repairNumber.trim());
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "输入信息不完整");
                }
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "该号码无返修记录或输入错误");
            }
        } catch (Exception e) {
            logger.error("客户登录错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 退出客户端
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping(value = "/doOutLogin")
    @ResponseBody
    public APIContent doOutLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().removeAttribute(GlobalCons.BATCHNUMBER);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("退出客户端错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取数据
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public APIContent queryList(@ModelAttribute CustomerClient client, HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute CommonParam comp) {
        try {
            super.CheckSSO(request, response);
            Page<CustomerClient> page = customerClientService.queryListPage(client, comp.getCurrentpage(),
                    comp.getPageSize());
            request.getSession().setAttribute("totalValue", page.getTotal());

            // 查询整批的维修费用
            BigDecimal sumPrice = BigDecimal.ZERO;

            BigDecimal sumRatePrice = BigDecimal.ZERO;
            ReturnJson json = new ReturnJson();

            // 查询单个批次
            if (null != client.getRepairnNmber() && !"".equals(client.getRepairnNmber())) {
                request.getSession().setAttribute(GlobalCons.BATCHNUMBER, client.getRepairnNmber().trim());
                String clientSum = customerClientService.getSumCount(client);
                CustomerClient sum = new CustomerClient();
                sum = customerClientService.getSum(client);
                sumPrice = customerClientService.sumRepairPrice(client);
                if (!page.getResult().isEmpty() && page.getResult().size() > 0) {
                    CustomerClient customerClient = page.getResult().get(0);
                    sumRatePrice = customerClient.getRatePrice() == null ? BigDecimal.ZERO : customerClient
                            .getRatePrice();
                }
                if (null != sum) {
                    sum.setTotalsum(new BigDecimal(clientSum));
                    sum.setSumPrice(sumPrice);
                    sum.setRatePrice(sumRatePrice);
                }
                json.setClient(sum);
                json.setRepairCount(Integer.valueOf(clientSum));
            } else {
                json.setRepairCount(page.getTotal());
            }
            json.setClients(page.getResult());
            return super.putAPIData(json);
        } catch (Exception e) {
            logger.error("获取数据错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 导出客户查询数据
     * 
     * @author TangYuping
     * @version 2017年1月4日 上午10:20:04
     * @param customerClient
     * @param request
     * @param response
     */
    @RequestMapping(value = "/ExportDatas")
    @ResponseBody
    public void ExportDatas(@ModelAttribute CustomerClient customerClient, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        customerClientService.exportDates(customerClient, request, response);
    }

    /**
     * 客户端修改设备维修状态
     * 
     * @author TangYuping
     * @version 2017年2月7日 下午1:53:12
     * @param client
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateRepairStatus")
    @ResponseBody
    public APIContent updateRepairStatus(@ModelAttribute CustomerClient client, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String acceptTime = request.getParameter("acceptanceTime");
            client.setAcceptTime(Timestamp.valueOf(acceptTime));
            int ret = workflowRepairService.updateRepairStatus(client);
            if (ret == 0) {
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "操作成功");
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "您的设备信息已更新，请刷新后再操作");
            }
        } catch (Exception e) {
            logger.error("客户端修改设备维修状态：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "操作失败");
        }
    }

    @RequestMapping(value = "/updateAllRepairStatus")
    @ResponseBody
    public APIContent updateAllRepairStatus(@ModelAttribute CustomerClient customerClient, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            super.CheckSSO(request, response);
            List<CustomerClient> customerClients = customerClientService.queryList(customerClient);
            
            for (CustomerClient item : customerClients){
                customerClient.setImei(item.getImei());
                customerClient.setAcceptTime(item.getAcceptTime());
                workflowRepairService.updateRepairStatus(customerClient);
            }
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "操作成功");
        } catch (Exception e) {
            logger.error("客户端修改设备维修状态：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "操作失败");
        }
    }

    /**
     * 获取当前客户历史送修设备批次号
     * 
     * @author TangYuping
     * @version 2017年2月28日 上午11:13:34
     * @param client
     * @param comp
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/queryRepairNumberList")
    @ResponseBody
    public APIContent queryRepairNumberList(@ModelAttribute CustomerClient client, @ModelAttribute CommonParam comp,
            HttpServletRequest req, HttpServletResponse res) {
        try {
            if (client.getPhone() != null && StringUtils.isNotEmpty(client.getPhone())) {
                Page<Workflow> list = customerClientService.getRepairNumberList(client, comp.getCurrentpage(),
                        comp.getPageSize());
                req.getSession().setAttribute("totalValueTwo", list.getTotal());
                List<Workflow> wl = list.getResult();
                return super.putAPIData(wl);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询错误！");
            }
        } catch (Exception e) {
            logger.error("获取当前客户历史送修设备批次号：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取数据失败！");
        }
    }

    /**
     * 支付数据校验
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "/checkAipay")
    @ResponseBody
    public APIContent CheckAipay(@ModelAttribute CustomerClient client, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            super.CheckSSO(request, response);
            if (!workflowPriceService.isAllPay(client.getRepairnNmber())) {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "该批次还有设备未报价，不允许付款");
            }

            // 判断是否已支付
            boolean payState = customerClientService.getPayState(client.getRepairnNmber());
            if (payState) {
                CustomerClient sum = new CustomerClient();
                sum.setTotalPrice(sum.getTotalPrice());
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "费用已支付");

            } else {
                CustomerClient sum = customerClientService.getSum(client);// 只是查了总表
                // 查询报价的费用是否和维修表、报价表的最新数据一致
                BigDecimal pricePay = workflowPriceService.bathSumRepairPriceByPrice(client.getRepairnNmber());

                BigDecimal repairPay = workflowPriceService.bathSumRepairPriceByRepair(client.getRepairnNmber());

                // 总费用 = 配件配 + 税费 + 维修费 + 运费
                BigDecimal sumPay = sum.getBatchPjCosts().add(sum.getRatePrice()).add(sum.getSumRepair())
                        .add(sum.getLogCost());

                if (null == pricePay) {
                    pricePay = BigDecimal.ZERO;
                }
                if (null == repairPay) {
                    repairPay = BigDecimal.ZERO;
                }

                if (pricePay.compareTo(repairPay) != 0 || pricePay.compareTo(sum.getSumRepair()) != 0
                        || repairPay.compareTo(sum.getSumRepair()) != 0 || sumPay.compareTo(sum.getTotalPrice()) != 0) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "报价费用有误，请联系售后服务确认");
                } else {
                    PayRecord record = customerClientService.createPayRecord(client.getRepairnNmber(),
                            sum.getTotalPrice(), AlipayConfig.subject, "支付宝支付");
                    sum.setOutTradeNo(record.getOutTradeNo());
                    return super.putAPIData(sum);
                }
            }
        } catch (Exception e) {
            logger.error("支付数据校验错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 保存评价
     * 
     * @param saleFeedback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveEvaluate")
    @ResponseBody
    public APIContent saveEvaluate(@ModelAttribute SaleFeedback saleFeedback, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            super.CheckSSO(request, response);
            customerClientService.saveEvaluate(saleFeedback);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "评价成功");
        } catch (Exception e) {
            logger.error("保存评价错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 验证用户是否可进行评价操作
     * 
     * @param saleFeedback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/checkEvalueat")
    @ResponseBody
    public APIContent checkEvalueat(@ModelAttribute SaleFeedback saleFeedback, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            super.CheckSSO(request, response);
            Integer count = customerClientService.getSendNumByRepairnNum(saleFeedback.getRepairnNmber());
            if (count > 0) {
                SaleFeedback result = customerClientService.selectByRepairnNmber(saleFeedback.getRepairnNmber());
                if (null != result) {
                    String createTime = result.getFcreateTime().toString();
                    String endTime = new Timestamp(System.currentTimeMillis()).toString();
                    Integer number = DateUtil.daysBetween(createTime, endTime);
                    if (number > 30) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, "评价已超过三个月不可再次评价");
                    } else {
                        return super.returnJson(Globals.OPERA_SUCCESS_CODE, "可进行评价", result);
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "可进行评价");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "批次正在处理中，不能进行评价操作");
            }
        } catch (Exception e) {
            logger.error("验证用户是否可进行评价操作错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 支付宝回调
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "/alipayback")
    @ResponseBody
    public APIContent alipayback(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 送修批号
            String outTradeNo = request.getParameter("out_trade_no");
            PayRecord payRecord = customerClientService.dealResult(outTradeNo, null);
            if (null != payRecord && null != payRecord.getStatus() && Integer.valueOf(payRecord.getStatus()) == 0) {
                // TODO 支付回回执
                workflowPriceService.UpdateAlipayBackCode(payRecord.getRepairnNmber(), Globals.ALIPAY);
            }
            // TODO 调用本地回执接口
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("支付宝回调操作错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: checkRegister
     * @Description:验证手机号是否存在
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月11日 下午3:24:43
     */
    @RequestMapping(value = "/checkRegister")
    @ResponseBody
    public APIContent checkRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            List<Sxdwmanage> sxdwList = sxdwmanageService.checkRegister(phone);
            if (null != sxdwList && sxdwList.size() > 0) {
                if (sxdwList.get(0).getRegState() != null && sxdwList.get(0).getRegState() == 1) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "该手机号已注册");
                } else {
                    return super.putAPIData(sxdwList.get(0));
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "手机号不正确或者未录入到系统");
            }
        } catch (Exception e) {
            logger.error("注册手机号查询错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: getRandomNumber
     * @Description:获得随机验证码
     */
    public Integer getRandomNumber() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println("---" + (int) Math.floor(Math.random() * 1000000));
            System.out.println("===" + (int) ((Math.random() * 9 + 1) * 100000));
        }
    }

    // 发送验证码
    public boolean sendMsg(String phone, String validateCode) {
        // String templateId = "kks-sale.verification";
        String ret = UCPaasUtils.sendSms(SMSConstants.SMS_APPID, SMSConstants.VALIDATE_TEMPLATE_ID, new StringBuilder(
                validateCode).toString(), phone);
        // String ret = "{\"respCode\":\"000000\"}";
        logger.info("发送验证码：" + ret + ",手机号：" + phone);
        String code = JSONObject.parseObject(ret).getString("resultCode");
        if ("0".equals(code)) {
            return true;
        } else {
            return false;
        }
    }

    // 发送密码
    public boolean sendMsg(String phone, Sxdwmanage sxdw) {
        // String templateId = "133744";
        String ret = UCPaasUtils.sendSms(SMSConstants.SMS_APPID, SMSConstants.PASSWORD_TEMPLATE_ID, new StringBuilder(
                phone).append(",").append(sxdw.getLoginPwd()).toString(), phone);
        // String ret = "{\"respCode\":\"000000\"}";
        logger.info("发送密码：" + ret + ",手机号：" + phone + ",密码：" + sxdw.getLoginPwd());
        String code = JSONObject.parseObject(ret).getString("resultCode");
        if ("0".equals(code)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Title: getValidateCode
     * @Description:获取验证码
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月30日 下午1:52:33
     */
    @RequestMapping(value = "/getValidateCode")
    @ResponseBody
    public APIContent getValidateCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            if (!StringUtil.isRealEmpty(phone)) {
                List<Sxdwmanage> sxdwList = sxdwmanageService.checkRegister(phone);
                ValidateCode valid = validateCodeService.getValidateCodeByPhone(phone, 1);
                if (null != sxdwList && sxdwList.size() > 0) {
                    String validateCode = getRandomNumber().toString();
                    ValidateCode v = new ValidateCode();
                    v.setSendType(1);
                    v.setPhone(phone);
                    v.setValidateCode(validateCode);
                    if (null != valid && null != valid.getSendCount()) {
                        if (valid.getSendCount() < 5) {
                            // 一个小时内验证码不重新发送
                            if (valid.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取验证码"
                                        + Globals.OPERA_SUCCESS_CODE_DESC);
                            }
                            if (sendMsg(phone, validateCode)) {
                                v.setSendCount(valid.getSendCount().intValue() + 1);
                                v.setCreateTime(valid.getCreateTime());
                                validateCodeService.updateByPhone(v);
                                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取验证码"
                                        + Globals.OPERA_SUCCESS_CODE_DESC);
                            } else {
                                return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取验证码失败");
                            }
                        } else {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE, "一个号码每天最多获取5次验证码");
                        }
                    } else {
                        if (sendMsg(phone, validateCode)) {
                            v.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            v.setSendCount(1);
                            validateCodeService.insert(v);
                            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取验证码"
                                    + Globals.OPERA_SUCCESS_CODE_DESC);
                        } else {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取验证码失败");
                        }
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "输入手机号不正确或未录入系统");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "输入手机号不正确");
            }
        } catch (Exception e) {
            logger.error("获取验证码错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: register
     * @Description:注册
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月11日 下午3:47:15
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public APIContent register(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            String loginPwd = request.getParameter("loginPwd");
            String validateCode = request.getParameter("validateCode");
            if (!StringUtil.isRealEmpty(phone) && !StringUtil.isRealEmpty(loginPwd)
                    && !StringUtil.isRealEmpty(validateCode)) {
                List<Sxdwmanage> sxdwList = sxdwmanageService.checkRegister(phone);
                if (null != sxdwList && sxdwList.size() > 0) {
                    ValidateCode v = validateCodeService.getValidateCodeByPhone(sxdwList.get(0).getPhone(), 1);
                    if (null != v && null != v.getValidateCode() && validateCode.equals(v.getValidateCode())) {
                        if (v.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                            sxdwList.get(0).setLoginPwd(loginPwd);
                            sxdwList.get(0).setRegState(1);
                            sxdwmanageService.updateByRegister(sxdwList.get(0));// 增加手机号一致的客户登录密码和注册状态
                            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
                        } else {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE, "验证码已失效");
                        }
                    } else {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, "验证码错误");
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "手机号出错");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "参数错误");
            }
        } catch (Exception e) {
            logger.error("注册错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: getPwd
     * @Description:忘记密码，获取密码
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月25日 下午4:31:23
     */
    @RequestMapping(value = "/getPwd")
    @ResponseBody
    public APIContent getPwd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            if (!StringUtil.isRealEmpty(phone)) {
                List<Sxdwmanage> sxdwList = sxdwmanageService.checkRegister(phone);
                ValidateCode valid = validateCodeService.getValidateCodeByPhone(phone, 2);// 发送密码
                if (null != sxdwList && sxdwList.size() > 0) {
                    if (null != sxdwList.get(0).getRegState() && sxdwList.get(0).getRegState() == 1
                            && !StringUtil.isRealEmpty(sxdwList.get(0).getLoginPwd())) {

                        ValidateCode v = new ValidateCode();
                        v.setSendType(2);
                        v.setPhone(phone);
                        if (null != valid && null != valid.getSendCount()) {
                            if (valid.getSendCount() < 2) {
                                if (valid.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取密码"
                                            + Globals.OPERA_SUCCESS_CODE_DESC);
                                }
                                if (sendMsg(phone, sxdwList.get(0))) {
                                    v.setSendCount(valid.getSendCount().intValue() + 1);
                                    v.setCreateTime(valid.getCreateTime());
                                    validateCodeService.updateByPhone(v);
                                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取密码"
                                            + Globals.OPERA_SUCCESS_CODE_DESC);
                                } else {
                                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取验证码失败");
                                }
                            } else {
                                return super.operaStatus(Globals.OPERA_FAIL_CODE, "一个号码每天最多获取2次密码");
                            }
                        } else {
                            if (sendMsg(phone, sxdwList.get(0))) {
                                v.setCreateTime(new Timestamp(System.currentTimeMillis()));
                                v.setSendCount(1);
                                validateCodeService.insert(v);
                                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取密码"
                                        + Globals.OPERA_SUCCESS_CODE_DESC);
                            } else {
                                return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取密码失败");
                            }
                        }
                    } else {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, "该手机号未注册");
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "该手机号未注册");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "手机号不正确");
            }
        } catch (Exception e) {
            logger.error("密码查询错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: queryList
     * @Description:加载该手机号客户批次树信息
     * @param req
     * @return
     * @author HuangGangQiang
     * @date 2017年8月25日 下午4:30:39
     */
    @RequestMapping("getTreeTime")
    @ResponseBody
    public APIContent queryList(HttpServletRequest req) {
        logger.info("开始获取列表信息");
        try {
            String phone = req.getParameter("phone");
            List<TimeTree> tList = new ArrayList<TimeTree>();
            if (!StringUtil.isRealEmpty(phone)) {
                tList = customerClientService.getAcceptanceTimeList(phone);
                return super.putAPIData(tList);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取受理日期树失败");
            }
        } catch (Exception e) {
            logger.info("获取列表信息失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: getStateByPhone
     * @Description:修改客户信息，查询是否有未完成的批次，无，则可以修改
     * @param req
     * @return
     * @author HuangGangQiang
     * @date 2017年8月24日 下午4:29:54
     */
    @RequestMapping("getStateByCId")
    @ResponseBody
    public APIContent getStateByPhone(HttpServletRequest req) {
        try {
            String cId = req.getParameter("cId");
            if (!StringUtil.isRealEmpty(cId)) {
                int ret = customerClientService.getStateByCId(cId);
                if (ret < 1) {
                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "该客户还有批次未全部处理完毕，不能修改");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取客户出错");
            }
        } catch (Exception e) {
            logger.info("查询该客户所有批次是否处理完毕错误:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: getSxdwList
     * @Description:获取该手机号已有批次的客户列表
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月24日 下午4:29:23
     */
    @RequestMapping(value = "getSxdwList")
    @ResponseBody
    public APIContent getSxdwList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            List<Sxdwmanage> sxdwList = sxdwmanageService.checkRegister(phone);
            if (null != sxdwList && sxdwList.size() > 0) {
                return super.putAPIData(sxdwList);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取该手机号用户列表失败");
            }
        } catch (Exception e) {
            logger.error("获取该手机号用户列表：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: getSxdwInfo
     * @Description:显示选中的客户信息
     * @param sxdwmanage
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月24日 下午4:29:03
     */
    @RequestMapping(value = "getSxdwInfo")
    @ResponseBody
    public APIContent getSxdwInfo(@Param("cId") Integer cId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Sxdwmanage sxdw = sxdwmanageService.getSxdwInfo(cId);
            if (null != sxdw && sxdw.getCId() > 0) {
                if (null != sxdw.getRepairnumCount() && sxdw.getRepairnumCount() > 0) {
                    return super.putAPIData(sxdw);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "该客户未返修过设备，不能修改");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("获取客户信息：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: getUpdateSxdwValidateCode
     * @Description:修改手机号获取验证码
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月25日 下午4:28:42
     */
    @RequestMapping(value = "/getUpdateSxdwValidateCode")
    @ResponseBody
    public APIContent getUpdateSxdwValidateCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String oldPhone = request.getParameter("oldPhone");
            String phone = request.getParameter("phone");
            String validateCode1 = getRandomNumber().toString();
            String validateCode2 = getRandomNumber().toString();
            ValidateCode valid1 = validateCodeService.getValidateCodeByPhone(oldPhone, 1);
            ValidateCode valid2 = validateCodeService.getValidateCodeByPhone(phone, 1);
            StringBuilder sb = new StringBuilder();

            boolean falg = false;// 原手机号发送短信是否成功
            ValidateCode v1 = new ValidateCode();
            v1.setSendType(1);
            v1.setPhone(oldPhone);
            v1.setValidateCode(validateCode1);
            if (null != valid1 && null != valid1.getSendCount()) {
                if (valid1.getSendCount() < 5) {
                    // 一个小时内验证码不重新发送
                    if (valid1.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                        return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取验证码" + Globals.OPERA_SUCCESS_CODE_DESC);
                    }
                    if (sendMsg(oldPhone, validateCode1)) {
                        v1.setSendCount(valid1.getSendCount().intValue() + 1);
                        v1.setCreateTime(valid1.getCreateTime());
                        validateCodeService.updateByPhone(v1);
                        falg = true;
                    } else {
                        sb.append("原手机号" + oldPhone + "获取验证码失败");
                    }
                } else {
                    sb.append("一个号码每天最多获取5次验证码");
                }
            } else {
                if (sendMsg(oldPhone, validateCode1)) {
                    v1.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    v1.setSendCount(1);
                    validateCodeService.insert(v1);
                    falg = true;
                } else {
                    sb.append("原手机号" + oldPhone + "获取验证码失败");
                }
            }

            if (falg) {
                ValidateCode v2 = new ValidateCode();
                v2.setSendType(1);
                v2.setPhone(phone);
                v2.setValidateCode(validateCode2);
                if (null != valid2 && null != valid2.getSendCount()) {
                    if (valid2.getSendCount() < 5) {
                        // 一个小时内验证码不重新发送
                        if (valid2.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取验证码"
                                    + Globals.OPERA_SUCCESS_CODE_DESC);
                        }
                        if (sendMsg(phone, validateCode2)) {
                            v2.setSendCount(valid2.getSendCount().intValue() + 1);
                            v2.setCreateTime(valid2.getCreateTime());
                            validateCodeService.updateByPhone(v2);
                        } else {
                            sb.append("新手机号" + phone + "获取验证码失败");
                        }
                    } else {
                        sb.append("一个号码每天最多获取5次验证码");
                    }
                } else {
                    if (sendMsg(phone, validateCode2)) {
                        v2.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        v2.setSendCount(1);
                        validateCodeService.insert(v2);
                    } else {
                        sb.append("新手机号" + phone + "获取验证码失败");
                    }
                }
            }

            if (null == sb || sb.length() == 0) {
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取验证码" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, sb.toString());
            }
        } catch (Exception e) {
            logger.error("注册手机号查询错误：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: updateSxdwmanage
     * @Description:修改客户信息
     * @param sxdwmanage
     * @param request
     * @return
     * @author HuangGangQiang
     * @date 2017年8月25日 下午4:28:19
     */
    @RequestMapping("updateSxdw")
    @ResponseBody
    public APIContent updateSxdwmanage(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
        try {
            if (sxdwmanageService.isExists(sxdwmanage) == 0) {
                StringBuilder sb = new StringBuilder();
                if ((null != sxdwmanage.getPhone() && !"".equals(sxdwmanage.getPhone().trim())
                        && null != sxdwmanage.getOldPhone() && !"".equals(sxdwmanage.getOldPhone().trim()) && sxdwmanage
                        .getPhone().trim().equals(sxdwmanage.getOldPhone().trim()))
                        || (null == sxdwmanage.getPhone() || "".equals(sxdwmanage.getPhone().trim()))) {} else {
                    ValidateCode v1 = validateCodeService.getValidateCodeByPhone(sxdwmanage.getPhone(), 1);
                    ValidateCode v2 = validateCodeService.getValidateCodeByPhone(sxdwmanage.getOldPhone(), 1);
                    String validateCode = request.getParameter("validateCode");
                    String oldValidateCode = request.getParameter("oldValidateCode");
                    if (null != v1 && null != v1.getValidateCode()) {
                        if (v1.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                            if (!validateCode.equals(v1.getValidateCode())) {
                                sb.append("手机号：" + sxdwmanage.getPhone() + "的验证码不正确 ");
                            }
                        } else {
                            sb.append("手机号：" + sxdwmanage.getPhone() + "的验证码已失效 ");
                        }
                    } else {
                        sb.append("手机号：" + sxdwmanage.getPhone() + "的验证码获取失败 ");
                    }
                    if (null != v2 && null != v2.getValidateCode()) {
                        if (v2.getOutTime().getTime() > new Timestamp(System.currentTimeMillis()).getTime()) {
                            if (!oldValidateCode.equals(v2.getValidateCode())) {
                                sb.append("手机号：" + sxdwmanage.getOldPhone() + "的验证码不正确 ");
                            }
                        } else {
                            sb.append("手机号：" + sxdwmanage.getOldPhone() + "的验证码已失效 ");
                        }
                    } else {
                        sb.append("手机号：" + sxdwmanage.getOldPhone() + "的验证码获取失败 ");
                    }
                }
                if (null == sb || sb.length() == 0) {
                    sxdwmanageService.updateByCustomer(sxdwmanage);
                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, sb.toString());
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "信息已存在");
            }
        } catch (Exception e) {
            logger.error("修改客户信息：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: updatePwd
     * @Description:修改密码
     * @param request
     * @param response
     * @return
     * @author HuangGangQiang
     * @date 2017年8月25日 下午4:28:07
     */
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    public APIContent updatePwd(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            String loginPwd = request.getParameter("loginPwd");
            String newLoginPwd = request.getParameter("newLoginPwd");
            if (!StringUtil.isRealEmpty(phone) && !StringUtil.isRealEmpty(loginPwd)
                    && !StringUtil.isRealEmpty(newLoginPwd)) {
                List<Sxdwmanage> sxdwList = sxdwmanageService.checkRegister(phone);
                if (null != sxdwList && sxdwList.size() > 0) {
                    Sxdwmanage s = sxdwList.get(0);
                    if (!StringUtil.isRealEmpty(s.getLoginPwd()) && loginPwd.equals(s.getLoginPwd())) {
                        s.setLoginPwd(newLoginPwd);
                        s.setRegState(1);
                        sxdwmanageService.updateByRegister(s);
                        return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
                    } else {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, "原密码输入错误");
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "该手机号还未注册");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "参数错误");
            }
        } catch (Exception e) {
            logger.error("修改密码：", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
