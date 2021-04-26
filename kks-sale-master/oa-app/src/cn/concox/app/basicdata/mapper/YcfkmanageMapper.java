package cn.concox.app.basicdata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Ycfkmanage;

public interface YcfkmanageMapper<T> extends BaseSqlMapper<T>{
	public Page<Ycfkmanage>  queryYcfkmanageListPage(@Param(PageInterceptor.PAGE_KEY) Page<Ycfkmanage> page, @Param("po") T po) throws  DataAccessException; 

	public List<Ycfkmanage> ycfkCountList(Ycfkmanage ycfk);
}
