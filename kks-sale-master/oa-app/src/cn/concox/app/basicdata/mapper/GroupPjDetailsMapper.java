package cn.concox.app.basicdata.mapper;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.GroupPjDetails;
/**
 * 销售配件料组合详情 Mappper
 * @param <T>
 */
public interface GroupPjDetailsMapper<T> extends BaseSqlMapper<T>{

	@SuppressWarnings("rawtypes")
	public Page getGroupPjDetailsList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public List<GroupPjDetails> getGroupPjDetailsList(@Param("po") T po) throws  DataAccessException;
	
	public int findCountForGroupPj(@Param("groupId") String groupId, @Param("id") String id);

	public void deleteInfo(@Param("ids") String... ids) throws DataAccessException;
	
	public void deleteInfoIsNull(@Param("groupId") Integer groupId);
	
	public void updateGroupIdByIds(@Param("groupId") String groupId, @Param("ids") String... ids) throws DataAccessException;

	public void deleteByGroupId(Integer groupId);
	
	public BigDecimal getPriceByGroupId(@Param("groupId") Integer groupId);
	
	public List<GroupPjDetails> getGroupPjDetailsListByGroupId(@Param("groupId") Integer groupId);
	
	public List<GroupPjDetails> getGroupPjDetailsListByProNOAndMasterOrSlave(@Param("proNO") String proNO, @Param("masterOrSlave")String masterOrSlave);
}
