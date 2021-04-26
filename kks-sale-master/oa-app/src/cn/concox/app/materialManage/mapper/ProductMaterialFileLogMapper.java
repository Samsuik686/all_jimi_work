package cn.concox.app.materialManage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.materialManage.ProductMaterialFileLog;

/**
 * 
 * @param <T>
 */
public interface ProductMaterialFileLogMapper<T> extends BaseSqlMapper<T> {
	
	public Page<T> productMaterialFileLogPage(@Param(PageInterceptor.PAGE_KEY) Page<T> page, @Param("po") ProductMaterialFileLog pm);	
	
	public List<ProductMaterialFileLog> productMaterialFileLogPage(@Param("po") ProductMaterialFileLog pm);	
}
