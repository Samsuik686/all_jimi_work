package cn.concox.app.basicdata.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Sjfjmanage;

public interface SjfjmanageMapper<T> extends BaseSqlMapper<T>{
	public Page<Sjfjmanage> querySjfjmanageListPage(@Param(PageInterceptor.PAGE_KEY) Page<Sjfjmanage> page, @Param("po") T po) throws  DataAccessException; 

	public String getGRoupConcat(String... ids);

	public int findCountWorkflowRelatedBysjfjDesc(Sjfjmanage sjfjmanage);  
}
