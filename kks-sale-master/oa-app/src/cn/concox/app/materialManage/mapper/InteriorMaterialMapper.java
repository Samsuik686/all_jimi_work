package cn.concox.app.materialManage.mapper;


import org.apache.ibatis.annotations.Param;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.materialManage.InteriorMaterial;

/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午11:31:05
 * @param <T>
 */
public interface InteriorMaterialMapper<T> extends BaseSqlMapper<T> {
	
	public Page<T> interiorMaterialPageList(@Param(PageInterceptor.PAGE_KEY) Page<T> page, 
			@Param("po") InteriorMaterial interiorMaterial);	
	
	
		
}
