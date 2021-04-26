package cn.concox.app.basicdata.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Dzlmanage;
/**
 * 电子料 Mappper
 * @param <T>
 */
public interface DzlmanageMapper<T> extends BaseSqlMapper<T>{

	@SuppressWarnings("rawtypes")
	public Page getDzlmanageList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public List<Dzlmanage> getDzlmanageList(@Param("po") T po) throws  DataAccessException;

	public int findCountByDzl(Dzlmanage dzlmanage);
	
	public String getGRoupConcat(String... ids);
	
	public Dzlmanage getDzlByProNO(String proNO);
	
	/**
	 * 导入时判断数据存在后获取该条数据
	 * @param proNO
	 * @param placesNO
	 * @param masterOrSlave
	 * @return
	 */
	public Dzlmanage getOneDzlInfo(@Param("proNO") String proNO, @Param("placesNO") String placesNO, @Param("masterOrSlave") String masterOrSlave);
}
