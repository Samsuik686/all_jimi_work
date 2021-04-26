package cn.concox.app.workflow.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.workflow.WorkflowTotalpay;

import java.util.List;

/**
 * 报价
 * 
 */
public interface WorkflowTotalpayMapper<T> extends BaseSqlMapper<T> {
	public boolean exists(String repairNumber) throws DataAccessException;
	
	/**
	 * 报价已付款的批次修改workflow的state状态
	 */
	public void updateByRepairnNmber(@Param("repairnNmber") String repairnNmber) throws DataAccessException;
	
	public void updateReceiptByNumber(@Param("repairnNmber") String repairnNmber);
	
	public WorkflowTotalpay getByRepairNumber (@Param("repairNumber") String repairNumber);
	
	/**
	 * @Title: deleteByRepairNumber 
	 * @Description:根据批次号删除记录
	 * @param repairNumber 
	 * @author 20160308
	 * @date 2017年11月3日 上午10:14:11
	 */
	public void deleteByRepairNumber(@Param("repairNumber") String repairNumber);
	
	/**
	 * @Title: isPayByRepairNumber 
	 * @Description:该批次是否已付款，防止测试的数据可以报价
	 * @param repairNumber
	 * @return
	 * @throws DataAccessException 
	 * @author 20160308
	 * @date 2017年11月13日 上午10:58:43
	 */
	public boolean isPayByRepairNumber(String repairNumber) throws DataAccessException;

	public List<WorkflowTotalpay> selectTotalpayByRepairNumber(String repairNumber);
}