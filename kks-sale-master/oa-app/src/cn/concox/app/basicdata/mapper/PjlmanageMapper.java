package cn.concox.app.basicdata.mapper;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Pjlmanage;
/**
 * 配件料 Mappper
 * @param <T>
 */
public interface PjlmanageMapper<T> extends BaseSqlMapper<T>{

	@SuppressWarnings("rawtypes")
	public Page getPjlmanageList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public List<Pjlmanage> getPjlmanageList(@Param("po") T po) throws  DataAccessException;

	public int findCountByPjl(Pjlmanage pjlmanage);
	
	public String getGRoupConcat(String... ids);
	
	/**
	 * 获得配件料的id和名称，显示时一一对应
	 * @param ids
	 * @return
	 */
	public List<Pjlmanage> getListByIds(String... ids);
	
	public Pjlmanage getPjlByProNO(String proNO);
	
	public String getAllOherModel(@Param("model") String model);
	
	//维修报价获取关联配件料价格
	public BigDecimal getSumPriceByIds(String... ids);
	
	/**
	 * 导入时判断数据存在后获取该条数据
	 * @param model
	 * @param proNO
	 * @param masterOrSlave
	 * @return
	 */
	public Pjlmanage getOnePjlInfo(@Param("marketModel") String marketModel, @Param("model") String model, @Param("proNO") String proNO, @Param("masterOrSlave") String masterOrSlave);
	
	/**
	 * @Title: getPjlByPjlIds 
	 * @Description:配件料组合根据选中的配件料ids，获取配件料信息列表
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月16日 下午4:05:37
	 */
	public List<Pjlmanage> getPjlByPjlIds(String... ids);
	
	/**
	 * @Title: getAllPjlModes 
	 * @Description:报价配件料组合获取所有配件料主板型号
	 * @return 
	 * @author 20160308
	 * @date 2017年11月20日 上午11:13:07
	 */
	public String getAllPjlModels();
	
	/**
	 * @Title: getAllPjlMarketModels 
	 * @Description:销售配件料组合获取所有配件料市场型号
	 * @return 
	 * @author 20160308
	 * @date 2017年11月20日 上午11:12:25
	 */
	public String getAllPjlMarketModels();
}
