package cn.concox.app.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.workflow.WorkflowRepair;

public interface WorkflowRepairMapper<T> extends BaseSqlMapper<T> {
	public void updateState(@Param("repairState") long repairState, @Param("ids") String... ids);

	public void updateStateByRepairNumber(@Param("repairState") long repairState, @Param("repairNumbers") String[] repairNumbers,
			@Param("ids") String... ids);

	public WorkflowRepair getRepairByWfId(@Param("wfId") Integer wfId) throws DataAccessException;

	public void doClear(@Param("id") Integer id);

	public void doRZClear(Integer id);

	public void doREInsert(@Param("rfild") Integer rfild, @Param("zzgzDesc") String zzgzDesc, @Param("zzgzGid") Integer zzgzGid);

	public void beathUpdate(@Param("po") WorkflowRepair workflowRepair, @Param("ids") String... ids);

	public void updateRepairAgainCount(@Param("ids") String... ids);

	public WorkflowRepair getByImei(@Param("imei") String imei, @Param("acceptTime") String acceptTime);

	public void notPrice(@Param("po") WorkflowRepair workflowRepair, @Param("ids") String... ids);

	public Page<WorkflowRepair> getRepairPage(@SuppressWarnings("rawtypes") @Param(PageInterceptor.PAGE_KEY) Page page,
			@Param("po") WorkflowRepair workflowRepair);

	public List<WorkflowRepair> getRepairList(WorkflowRepair workflowRepair);

	public void deleteRepairByWfids(@Param("ids") String... ids);

	/**
	 * @Title: getCountToSortByWfids
	 * @Description:维修工站查询选中的数据是否有不是已分拣，待维修或已维修，待报价或不报价，待维修或放弃报价，待维修？有：不允许点击返回分拣
	 * @param ids
	 * @return
	 * @author 20160308
	 * @date 2017年11月6日 上午10:44:06
	 */
	public int getCountToSortByWfids(@Param("ids") String... ids);

	/**
	 * @Title: getCountNoPriceByWfids
	 * @Description:维修工站查询选中的数据是否有不是已分拣，待维修？有：不允许点击不报价、发送测试、返回分拣
	 * @param ids
	 * @return
	 * @author 20160308
	 * @date 2017年11月6日 上午10:09:54
	 */
	public int getCountNoPriceByWfids(@Param("ids") String... ids);

	/**
	 * @Title: getCountToPriceByWfids
	 * @Description:维修工站查询选中的数据是否有不是已维修，待报价？有：不允许点击发送报价
	 * @param ids
	 * @return
	 * @author 20160308
	 * @date 2017年11月6日 上午10:10:01
	 */
	public int getCountToPriceByWfids(@Param("ids") String... ids);

	/**
	 * @Title: getCountRepairedByWfids
	 * @Description:维修工站查询选中的数据是否有不是已维修，待终检？有：不允许点击发送终检
	 * @param ids
	 * @return
	 * @author 20160308
	 * @date 2017年11月6日 上午10:10:06
	 */
	public int getCountRepairedByWfids(@Param("ids") String... ids);

	/**
	 * @Title: getCountToPackByWfids
	 * @Description:维修工站查询选中的数据是否有不是放弃维修，发送装箱？有：不允许点击发送装箱
	 * @param ids
	 * @return
	 * @author 20160308
	 * @date 2017年11月6日 上午10:10:10
	 */
	public int getCountToPackByWfids(@Param("ids") String... ids);

}