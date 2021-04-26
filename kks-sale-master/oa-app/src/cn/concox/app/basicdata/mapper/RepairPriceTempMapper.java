package cn.concox.app.basicdata.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.RepairPriceTempManage;

public interface RepairPriceTempMapper<T> extends BaseSqlMapper<T>{
	
	@SuppressWarnings("rawtypes")
	public Page getRepairPriceTempManageList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public String getGRoupConcat(String... ids); 

	/**
	 * 删除或修改分组，查看该组下数据个数
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId);
	
	public int findCountWorkflowByRepairId(RepairPriceTempManage manage);
	
	public List<RepairPriceTempManage> getRepairPriceTempManageList(@Param("po") T po);
	
	/**
	 * 查询维修报价
	 * @author TangYuping
	 * @version 2016年11月29日 下午5:54:00
	 * @param ids
	 * @return
	 */
	public RepairPriceTempManage getSumPrice(String... ids);  
	
	/**
	 * 根据维修报价保存配件料
	 * @param ids
	 * @return
	 */
	public String getPjlIdsByWxbjId(String... ids);
	
	public List<RepairPriceTempManage> getRepairPriceListByPjlId(Integer pjlId);
	
	public BigDecimal getPjlPriceByPjlDesc(Integer rid);
	
	public List<RepairPriceTempManage> getRepairPriceListByGzbjId(Integer id);
	
	public RepairPriceTempManage getHourFee(String... ids);
}
