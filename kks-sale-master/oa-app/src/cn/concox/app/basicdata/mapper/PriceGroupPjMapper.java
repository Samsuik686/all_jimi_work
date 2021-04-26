package cn.concox.app.basicdata.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.PriceGroupPj;
/**
 * 报价配件料组合管理 Mappper
 * @param <T>
 */
public interface PriceGroupPjMapper<T> extends BaseSqlMapper<T>{

	@SuppressWarnings("rawtypes")
	public Page getPriceGroupPjList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public List<PriceGroupPj> getPjlmanageList(@Param("po") T po) throws  DataAccessException;

	public int findCountByGroupPjId(PriceGroupPj groupPj);
	
	public String getGRoupConcat(String... ids);

	/**
	 * @Title: getPjlIdsByGroupPjIds 
	 * @Description:维修报价选择组合配件料
	 * @param ids 报价配件料组合id
	 * @return 
	 * @author 20160308
	 * @date 2017年11月22日 上午10:34:03
	 */
	public List<Pjlmanage> getPjlIdsByGroupPjIds(@Param("ids") String... ids);
}
