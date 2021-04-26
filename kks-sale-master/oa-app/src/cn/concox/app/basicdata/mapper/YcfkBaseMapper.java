package cn.concox.app.basicdata.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.YcfkBase;
/**
 * <pre>
 * YcfkBaseMapper 数据访问接口
 * </pre>
 */
public interface YcfkBaseMapper<T>  extends BaseSqlMapper<T> {
	public Page<YcfkBase> queryYcfkBaseListPage(@Param(PageInterceptor.PAGE_KEY) Page<YcfkBase> page, @Param("po") T po) throws  DataAccessException;

	public int findCountByYcfk(YcfkBase ycfk);
	
	/**
	 * 根据gid查询问题描述list
	 * @param ycfk
	 * @return
	 */
	public List<YcfkBase> getProDetailsByGid(Integer gId);

}
