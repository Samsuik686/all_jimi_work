package cn.concox.app.aipay.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.aipay.CustomerClient;
import cn.concox.vo.basicdata.Cjgzmanage;
import cn.concox.vo.basicdata.RepairPriceManage;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.workflow.Workflow;

public interface CustomerClientMapper<T> extends BaseSqlMapper<T> {

	public CustomerClient checkClientLogin(CustomerClient client);

	public String getSumCount(CustomerClient client);

	public CustomerClient getSum(CustomerClient client);

	@SuppressWarnings("rawtypes")
	public Page<CustomerClient> queryListPage( @Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po);
	
	public List<CustomerClient>  queryListPage(@Param("po") CustomerClient customerClient);
	
	public List<CustomerClient> queryListClients(@Param("po") T po);

	public List<Cjgzmanage> getCJGZList(String[] cjArrys);

	public List<Zgzmanage> getZZGZList(String[] zzArrys);

	public List<Sjfjmanage> getSJFJList(String[] sjArrys);
	
	public List<RepairPriceManage> getWxbjList(String[] wxbjArrys);

	public int getPayState(String repairnNmber); 
	
	public BigDecimal sumRepairPrice(CustomerClient client);
	
	public void updateFeedback();
	
	@SuppressWarnings("rawtypes")
	public Page<Workflow> repairNumberList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") CustomerClient client);
	
	//公众号获取批次信息
	public List<Workflow> repairNumberList(@Param("po") CustomerClient client);
	
	/**
	 * @Title: getLatestRepairNumber 
	 * @Description:根据手机号获取最新的批次号
	 * @param phone
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年8月11日 下午3:03:54
	 */
	public String getLatestRepairNumber(@Param("phone") String phone);
	
	public List<String> getAcceptanceTimeList(@Param("phone") String phone);
	
	public List<String> getRepairNumberByTreetime(@Param("phone") String phone,@Param("treeTime") String treeTime);
	
	public int getStateByCId(@Param("cId") String cId);
	
}
