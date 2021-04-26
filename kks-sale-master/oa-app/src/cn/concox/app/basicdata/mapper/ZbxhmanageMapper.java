package cn.concox.app.basicdata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Zbxhmanage;

public interface ZbxhmanageMapper<T> extends BaseSqlMapper<T> {

	@SuppressWarnings("rawtypes")
	public Page getZbxhmanageListPage( @Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;

	public List<Zbxhmanage> getZbTypeBySctype(@Param("Sctype") String Sctype); 
	
	public List<Zbxhmanage> getZbTypeByAms(@Param("marketModel") String marketModel, @Param("model") String model); 
	
	/**
	 * 主板型号列表
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> queryModelList() throws DataAccessException;
	
	/**
	 * 新增并返回自增长ID
	 * @param zbxhmanage
	 * @return
	 */
	public int doInsert(Zbxhmanage zbxhmanage);

	public int findCountWorkflowByxhId(Zbxhmanage zbxhmanage);

	public Integer selectCountByCreateTypeId(Integer createTypeId);

	public Integer updateTypeAndCreateTypeByMids(@Param("list") List<Integer> mids,@Param("gId") Integer gId,@Param("modelType") String modelType,@Param("createType") Integer createType);

	public Integer isExistsByGid(Zbxhmanage zbxhmanage);

	public List<Zbxhmanage> selectZbxhmanagesByMids(@Param("list")List<Integer> mids);
}
