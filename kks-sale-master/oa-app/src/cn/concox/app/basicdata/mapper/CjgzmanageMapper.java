package cn.concox.app.basicdata.mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Cjgzmanage;
/**
 * <pre>
 * CjgzmanageMapper 数据访问接口
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
public interface CjgzmanageMapper<T>  extends BaseSqlMapper<T> {
	public Page<Cjgzmanage> queryCjgzmanageListPage(@Param(PageInterceptor.PAGE_KEY) Page<Cjgzmanage> page, @Param("po") T po) throws  DataAccessException;

	public String getGRoupConcat(String... ids);

	public int findCountWorkflowRelatedBycjgzDesc(Cjgzmanage cjgzmanage);   
}
