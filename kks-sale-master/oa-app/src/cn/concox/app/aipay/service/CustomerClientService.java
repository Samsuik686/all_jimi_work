package cn.concox.app.aipay.service;

import cn.concox.app.aipay.mapper.CustomerClientMapper;
import cn.concox.app.aipay.mapper.PayRecordMapper;
import cn.concox.app.aipay.mapper.SaleFeedbackMapper;
import cn.concox.app.basicdata.mapper.RepairPriceMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.aipay.CustomerClient;
import cn.concox.vo.aipay.PayRecord;
import cn.concox.vo.aipay.SaleFeedback;
import cn.concox.vo.basicdata.Cjgzmanage;
import cn.concox.vo.basicdata.RepairPriceManage;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.commvo.TimeTree;
import cn.concox.vo.workflow.Workflow;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service("customerClientService")
@Scope("prototype")
public class CustomerClientService {

    Logger logger = Logger.getLogger("privilege");

    @Resource(name = "customerClientMapper")
    private CustomerClientMapper<CustomerClient> customerClientMapper;

    @Resource(name = "saleFeedbackMapper")
    private SaleFeedbackMapper<SaleFeedback> saleFeedbackMapper;

    @Resource(name = "payRecordMapper")
    private PayRecordMapper<PayRecord> payRecordMapper;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource(name = "repairPriceMapper")
    private RepairPriceMapper<RepairPriceManage> repairPriceMapper;

    public Page<CustomerClient> queryListPage(CustomerClient customerClient, int currentPage, int pageSize) {
        Page<CustomerClient> page = new Page<CustomerClient>();
        page.setCurrentPage(currentPage);
        page.setSize(pageSize);
        page = customerClientMapper.queryListPage(page, customerClient);

        if (null != page && null != page.getResult() && page.getResult().size() > 0) {
            this.getPropertyName(page.getResult());
        }
        return page;
    }

    public List<CustomerClient> queryList(CustomerClient client) {
        return customerClientMapper.queryListPage(client);
    }

    /**
     *
     * @author TangYuping
     * @version 2017年1月4日 上午10:38:02
     * @param client
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    public void exportDates(CustomerClient client, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<CustomerClient> list = customerClientMapper.queryListPage(client);
        if (list != null && list.size() > 0) {
            this.getPropertyName(list);

            String[] fieldNames = new String[] { "送修单位", "批次号", "IMEI", "机型", "受理时间", "是否保修", "是否人为", "维修报价描述", "送修备注",
                    "终检故障", "随机附件", "处理措施", "维修费（￥）", "进度", "支付时间" };
            Map map = new HashMap();
            map.put("size", list.size() + 2);
            map.put("peList", list);
            map.put("fieldNames", fieldNames);
            map.put("cosLenth", fieldNames.length);
            String fileName = new StringBuilder("送修设备维修状态").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
            String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/")
                    .append(fileName).toString();
            String templatePath = new StringBuilder(GlobalCons.CLIENT).append("customerClient.ftl").toString();
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
            FreemarkerManager.down(request, response, exportFile, fileName, template, map);
        }
    }

    public List<CustomerClient> getPropertyName(List<CustomerClient> customerList) {
        for (CustomerClient cc : customerList) {
            String[] cjArrys = StringUtil.split(cc.getCjgzDesc());
            if (cjArrys.length != 0) {
                StringBuilder sb = new StringBuilder();
                List<Cjgzmanage> cjList = customerClientMapper.getCJGZList(cjArrys);
                for (Cjgzmanage cjgz : cjList) {
                    sb.append(cjgz.getInitheckFault() + ";");
                }
                if (sb.length() - 1 > 0) {
                    cc.setCjgzDesc(sb.substring(0, sb.length() - 1));
                }
            }
            String[] zzArrys = StringUtil.split(cc.getZzgzDesc());
            if (zzArrys.length != 0) {
                StringBuilder gzsb = new StringBuilder();
                List<Zgzmanage> zzList = customerClientMapper.getZZGZList(zzArrys);
                boolean isSynSolution = false;
                for (Zgzmanage zzgz : zzList) {
                    gzsb.append(zzgz.getProceMethod() + ";");
                    if(!isSynSolution){
                        if(zzgz.getIsSyncSolution() == 1){
                            isSynSolution = true;
                        }else{
                            isSynSolution = false;
                        }
                    }
                }
                if (gzsb.length() - 1 > 0) {
                    cc.setZzgzDesc(gzsb.substring(0, gzsb.length() - 1));
                }
                // 获取同步信息进行判断，从而获取解决措施。如果是保外或人为，则同步
                String solution2 = "换板";
                if(isSynSolution || cc.getIsWarranty()==1 ||"0".equals(cc.getIsRW())||"是".equals(cc.getIsRW())){
                    solution2 = cc.getSolution();
                }
                cc.setSolutionTwo(solution2);
            }

            String[] sjArrys = StringUtil.split(cc.getSjfjDesc());
            if (sjArrys.length != 0) {
                StringBuilder sjsb = new StringBuilder();
                List<Sjfjmanage> sjList = customerClientMapper.getSJFJList(sjArrys);
                for (Sjfjmanage sjfj : sjList) {
                    sjsb.append(sjfj.getName() + ";");
                }
                if (sjsb.length() - 1 > 0) {
                    cc.setSjfjDesc(sjsb.substring(0, sjsb.length() - 1));
                }
            }

            String[] wxbjArrys = StringUtil.split(cc.getWxbjDesc());
            if (wxbjArrys.length != 0) {
                // TODO 维修报价
                StringBuilder wxbj = new StringBuilder();
                List<RepairPriceManage> wxbjList = customerClientMapper.getWxbjList(wxbjArrys);
                Set<String> setType = new HashSet<String>();// 维修报价类型出现多次时，只显示一次
                for (RepairPriceManage repairPrice : wxbjList) {
                    if (repairPrice.getRepairType().equals("通用")) {
                        wxbj.append(repairPrice.getAmount() + ";");
                    } else {
                        if (setType.contains(repairPrice.getRepairType())) {
                            wxbj.append(repairPrice.getAmount() + ";");
                        } else {
                            wxbj.append("【" + repairPrice.getRepairType() + "】" + repairPrice.getAmount() + ";");
                            setType.add(repairPrice.getRepairType());
                        }
                    }

                }
                if (wxbj.length() - 1 > 0) {
                    cc.setWxbjDesc(wxbj.substring(0, wxbj.length() - 1));
                }

            }
        }
        return customerList;
    }

    public List<CustomerClient> getWeChatAllDatas(CustomerClient customerClient) {
        List<CustomerClient> clients = new ArrayList<CustomerClient>();
        clients = customerClientMapper.queryListClients(customerClient);
        if (null != clients && clients.size() > 0) {
            for (CustomerClient cc : clients) {
                String[] zzArrys = StringUtil.split(cc.getZzgzDesc());
                if (zzArrys.length != 0) {
                    StringBuilder gzsb = new StringBuilder();
                    List<Zgzmanage> zzList = customerClientMapper.getZZGZList(zzArrys);
                    for (Zgzmanage zzgz : zzList) {
                        gzsb.append(zzgz.getFaultType() + ";");
                    }
                    cc.setZzgzDesc(gzsb.substring(0, gzsb.length() - 1));
                }
                // 获取某个设备的维修报价描述
                String[] wxbjArr = StringUtil.split(cc.getWxbjDesc());
                String wxbjName = "";
                if (null != wxbjArr && wxbjArr.length > 0) {
                    wxbjName = repairPriceMapper.getGRoupConcat(wxbjArr);
                }
                cc.setWxbjDesc(wxbjName);
            }
        }
        return clients;
    }

    public boolean checkClientLogin(CustomerClient client) {
        if (null != customerClientMapper.checkClientLogin(client)) {
            return true;
        } else {
            return false;
        }
    }

    public String getLatestRepairNumber(String phone) {
        return customerClientMapper.getLatestRepairNumber(phone);
    }

    /**
     * 获取总数量
     *
     * @param client
     * @return
     */
    public String getSumCount(CustomerClient client) {
        return customerClientMapper.getSumCount(client);
    }

    /**
     * 获取费用
     *
     * @param client
     * @return
     */
    public CustomerClient getSum(CustomerClient client) {
        return customerClientMapper.getSum(client);
    }

    public BigDecimal sumRepairPrice(CustomerClient client) {
        if (client.getRepairnNmber() != null && StringUtils.isNotBlank(client.getRepairnNmber())) {
            return customerClientMapper.sumRepairPrice(client);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 保存评价
     *
     * @param saleFeedback
     */
    public void saveEvaluate(SaleFeedback saleFeedback) {
        if (saleFeedback.getId() != null && saleFeedback.getId() > 0) {
            saleFeedbackMapper.update(saleFeedback);
        } else {
            saleFeedback.setFcreateTime(new Timestamp(new Date().getTime()));
            saleFeedbackMapper.insert(saleFeedback);
        }

    }

    /**
     * 发货十五天后未评价数据系统自动设置为好评
     *
     * @author TangYuping
     * @version 2017年2月16日 下午4:44:26
     */
    public void updateFeedback() {
        customerClientMapper.updateFeedback();
    }

    /**
     * 获取当前登录客户历史送修设备
     *
     * @author TangYuping
     * @version 2017年2月28日 上午11:11:29
     * @param client
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<Workflow> getRepairNumberList(CustomerClient client, Integer currentPage, Integer pageSize) {
        Page<Workflow> pages = new Page<Workflow>();
        pages.setCurrentPage(currentPage);
        pages.setSize(pageSize);
        pages = customerClientMapper.repairNumberList(pages, client);
        List<Workflow> list = pages.getResult();
        pages.setResult(list);
        return pages;
    }

    /**
     * @Title: getRepairNumberList
     * @Description:微信公众号显示所有批次
     * @param client
     * @return
     * @author HuangGangQiang
     * @date 2017年9月12日 上午10:51:13
     */
    public List<Workflow> getRepairNumberList(CustomerClient client) {
        return customerClientMapper.repairNumberList(client);
    }

    /**
     * 通过批次号获取评价信息
     *
     * @param repairnNmber
     */
    public SaleFeedback selectByRepairnNmber(String repairnNmber) {
        return saleFeedbackMapper.selectByRepairnNmber(repairnNmber);

    }

    /**
     * 通过批次号查询发货的单数
     *
     * @param repairnNmber
     * @return
     */
    public Integer getSendNumByRepairnNum(String repairnNmber) {
        return saleFeedbackMapper.getSendNumByRepairnNum(repairnNmber);
    }

    /**
     * 检查支付状态
     *
     * @param repairnNmber
     * @return
     */
    public boolean getPayState(String repairnNmber) {
        if (customerClientMapper.getPayState(repairnNmber) == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 生成支付记录
     *
     * @param repairnNmber
     * @param name
     * @param payType
     * @return PayRecord
     */
    public PayRecord createPayRecord(String repairnNmber, BigDecimal amount, String name, String payType) {
        PayRecord record = new PayRecord();
        record.setAmount(amount);
        record.setRepairnNmber(repairnNmber);
        record.setName(name);
        record.setOutTradeNo(generateOutTradeNo());
        record.setPayType(payType);
        record.setCreateTime(new Date());
        record.setStatus(1);
        payRecordMapper.insert(record);
        return record;
    }

    /**
     * 处理支付结果
     *
     * @author huangwm
     * @date 2016年7月31日下午7:20:01
     * @param outTradeNo
     * @param msg
     * @return
     */
    public PayRecord dealResult(String outTradeNo, String msg) {
        PayRecord order = payRecordMapper.selectByNo(outTradeNo);
        if (null != order && null != order.getStatus() && Integer.valueOf(order.getStatus()) == 1) {
            order.setPayTime(new Date());
            order.setStatus(0);
            order.setMsg(msg);
            payRecordMapper.update(order);
        }
        return order;
    }

    /**
     * 获取支付记录
     *
     * @param outTradeNo
     * @return
     */
    public PayRecord selectByNo(String outTradeNo) {
        return payRecordMapper.selectByNo(outTradeNo);
    }

    private synchronized String generateOutTradeNo() {
        StringBuffer sb = new StringBuffer();
        sb.append("PAY");
        sb.append(DateTimeUtil.formatString(new Date(), "yyyyMMddHHmmss"));
        Random r = new Random();
        sb.append(r.nextInt(1000));
        return sb.toString();
    }

    /**
     * @Title: getAcceptanceTimeList
     * @Description:客户返修批次树
     * @param phone
     * @return
     * @author HuangGangQiang
     * @date 2017年8月21日 下午2:24:55
     */
    public List<TimeTree> getAcceptanceTimeList(String phone) {
        List<String> sList = customerClientMapper.getAcceptanceTimeList(phone);
        List<TimeTree> tList = new ArrayList<TimeTree>();

        if (null != sList && sList.size() > 0) {
            for (String s : sList) {
                TimeTree t = new TimeTree();
                t.setId(s);
                t.setText(s);
                List<String> sList1 = customerClientMapper.getRepairNumberByTreetime(phone, s);
                List<TimeTree> tList1 = new ArrayList<TimeTree>();
                if (null != sList1 && sList1.size() > 0) {
                    for (String s1 : sList1) {
                        TimeTree t1 = new TimeTree();
                        t1.setId(s1);
                        t1.setText(s1);
                        tList1.add(t1);
                    }
                    t.setChildren(tList1);
                }
                tList.add(t);
            }
        }
        return tList;
    }

    /**
     * @Title: getStateByCId
     * @Description:修改客户信息时查询该客户所有批次是否是已完成状态
     * @param cId
     * @return
     * @author HuangGangQiang
     * @date 2017年8月25日 下午4:32:35
     */
    public int getStateByCId(String cId) {
        return customerClientMapper.getStateByCId(cId);
    }
}
