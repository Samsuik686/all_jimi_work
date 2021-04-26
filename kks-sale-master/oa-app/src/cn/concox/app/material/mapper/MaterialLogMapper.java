package cn.concox.app.material.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.material.MaterialLog;

/**
 * <pre>
 * MaterialLogMapper 数据访问接口
 * </pre>
 * @author Liao.Bifeng 
 * @version v1.0
 */
public interface MaterialLogMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page queryMaterialLogListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public Integer selectMaxId()throws  DataAccessException; 
	
	public MaterialLog selectOutByProNOOrModel(MaterialLog ml);

	public void deleteByIds(String[] ids);
	
	/**
	 * 根据导入标识删除此批已导入的数据
	 */
	public void deleteByimportFlag(String importFlag);
	
	/**
	 * 根据物料编码获得所有的出入库记录
	 * @param materialLog
	 * @return
	 */
	public List<MaterialLog> queryLogInfoByProNO(MaterialLog materialLog);

}
