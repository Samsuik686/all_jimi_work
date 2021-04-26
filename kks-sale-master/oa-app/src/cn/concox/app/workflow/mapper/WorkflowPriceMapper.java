package cn.concox.app.workflow.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.ProlongCost;
import cn.concox.vo.basicdata.ProlongInfo;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowPrice;
import cn.concox.vo.workflow.WorkflowTotalpay;

/**
 * 报价
 * 
 * @author Liao.bifeng
 * @date 2016年7月26日
 */
public interface WorkflowPriceMapper<T> extends BaseSqlMapper<T> {
	public void updateState(@Param("priceState") long priceState, @Param("ids") String... ids);
	/**
	 * 批次报价报价表需报价总费用
	 * @param price_repairnNmber
	 * @param wfIds
	 */
	public BigDecimal bathSumRepairPrice(@Param("repairnNmber") String repairnNmber);
	
	/**
	 * @Title: bathSumRepairPriceByRepair 
	 * @Description: 批次报价维修表需报价总费用
	 * @param repairnNmber
	 * @return 
	 * @author 20160308
	 * @date 2017年11月8日 上午10:31:15
	 */
	public BigDecimal bathSumRepairPriceByRepair(@Param("repairnNmber") String repairnNmber);
	
	/**
	 * 根据批次号查询物流费
	 * @author TangYuping
	 * @version 2017年1月16日 下午2:44:47
	 * @param price
	 * @return
	 */
	public WorkflowTotalpay selLOgcostByRepairNumber(WorkflowPrice price);
	/**
	 * 根据批次修改状态（已付款）
	 * @param state
	 * @param repairnNmber
	 */
	public void updateStateByRepairnNmber(@Param("repairnNmber") String repairnNmber);
	
	/**
	 * workflow表里已付款改变状态
	 * @param repairnNmber
	 */
	public void updateStateByRepairnNmberIsPay(@Param("state") long state,@Param("repairnNmber") String repairnNmber);  
	
	/**
	 * 根据批次修改状态（未付款）
	 * @param state
	 * @param repairnNmber
	 */
	public void updateStateByRepairnNmberIsNotPay(@Param("repairnNmber") String repairnNmber);
	
	/**
	 * 查询当前批次需要报价的数量
	 * @param repairnNmber
	 */
	public int selectPriceCountByRepairnNmber(@Param("repairnNmber") String repairnNmber);
	
	/**
	 * 批次查看数据
	 * @param workflowPrice
	 * @return
	 * @throws DataAccessException
	 */
	public List<Workflow> queryListByrepairNumber(@Param("repairnNmber") String repairnNmber, 
			@Param("state") String state) throws DataAccessException;
	
	/**
	 * 根据主表ID修改总价
	 * @author TangYuping
	 * @version 2016年11月28日 下午2:57:27
	 * @param price
	 */
	public void updateRepairMoneyByWfid(WorkflowPrice price);
	
	/**
	 * 根据送修批号修改报价人
	 * @author TangYuping
	 * @version 2016年11月28日 下午4:00:28
	 * @param repairnNmber
	 */
	public void updateOffer(WorkflowPrice wp);
	
	public WorkflowPrice getByWfid(Integer wfId);
	
	/**
	 * 查看需要填写物流费的数据
	 * @author TangYuping
	 * @version 2016年12月19日 上午11:52:02
	 * @param repairnNmber
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<Workflow> queryListLogCost(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") Workflow wf);
	
	/**
	 * 修改物流费支付状态
	 * @author TangYuping
	 * @version 2016年12月19日 下午6:27:00
	 * @param payedLogCost
	 * @param repairnNmber
	 */
	public void updateLogcostStatusByNmber(@Param("repairnNmber") String repairnNmber, @Param("payedLogCost") String payedLogCost);
	
	/**
	 * 根据wfid批量删除 price 表记录
	 * @author TangYuping
	 * @version 2017年1月12日 下午4:16:30
	 * @param ids
	 */
	public void delPriceByWfid(@Param("ids") String... ids);
	
	/**
	 * 查询报价列表，分页
	 * @author TangYuping
	 * @version 2017年1月3日 下午4:00:35
	 * @param page
	 * @param price
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<WorkflowPrice> getPricePageList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po")WorkflowPrice price);
	
	/**
	 * 导出查询报价
	 * @author TangYuping
	 * @version 2017年1月3日 下午4:34:23
	 * @param price
	 * @return
	 */
	public List<WorkflowPrice> getPricePageList(@Param("po") WorkflowPrice price);
	
	/**
	 * 查询所有待报价批次
	 * @author TangYuping
	 * @version 2017年1月4日 下午4:37:32
	 * @param price
	 * @return
	 */
	public List<Workflow> getPriceRepairNumber(WorkflowPrice price);
	
	/**
	 * @Title: getWfIdsByRepairnNmber 
	 * @Description: 查询该批次所有待付款数据
	 * @param repairnNmber
	 * @return 
	 * @author 20160308
	 * @date 2017年11月3日 上午10:11:18
	 */
	public String getWfIdsByRepairnNmber(@Param("repairnNmber") String repairnNmber); 
	
	/**
	 * @Title: getCountHasNotPriceByWfids 
	 * @Description:报价工站查询选中的数据是否有不是待报价或已报价，待付款的？有：不允许放弃报价
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月3日 下午2:46:16
	 */
	public int getCountHasNotPriceByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: getCountHasNotPayByWfids 
	 * @Description:报价工站查询选中的数据是否有不是已付款？有：不允许点击已付款，发送维修
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月3日 下午3:46:34
	 */
	public int getCountHasNotPayByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: getCountHasPriceByWfids 
	 * @Description:报价工站查询选中的数据是否有不是待报价？有：不允许点击待报价，发送维修
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月3日 下午3:46:34
	 */
	public int getCountHasPriceByWfids(@Param("ids") String... ids);
	
	/**
	 * @Title: getCountPayByRepairnNmber 
	 * @Description:批次下已付款数量
	 * @param repairnNmber
	 * @return 
	 * @author 20160308
	 * @date 2017年11月6日 下午3:12:57
	 */
	public int getCountPayByRepairnNmber(@Param("repairnNmber") String repairnNmber);
	
	ProlongCost queryProlongCost();
	
	void updateProlongCost(ProlongCost prolongCost);
	
	void insertProlongInfo(ProlongInfo prolongInfo);
	
	void updateProlongInfo(ProlongInfo prolongInfo);
	
	ProlongInfo getProlongInfo(ProlongInfo prolongInfo);

	void updateIsWarranty(@Param("isWarranty") int isWarranty, @Param("id") int id);
	
	BigDecimal getSumProlongCost(@Param("repairNumber") String repairNumber);
}