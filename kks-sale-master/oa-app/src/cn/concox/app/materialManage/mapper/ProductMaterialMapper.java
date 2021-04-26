package cn.concox.app.materialManage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;



import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.materialManage.ProductMaterial;
import cn.concox.vo.materialManage.ProductMaterialFile;

/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午11:31:05
 * @param <T>
 */
public interface ProductMaterialMapper<T> extends BaseSqlMapper<T> {
	
	public Page<T> productMaterialPageList(@Param(PageInterceptor.PAGE_KEY) Page<T> page, 
			@Param("po") ProductMaterial pm);	
	
	public ProductMaterial productMaterialPageList(@Param("po") ProductMaterial pm);	
	
	public void insertProductFile (ProductMaterialFile proFile);		//附件表 
	
	public void deleteProductFileById (ProductMaterialFile proFile);

	public List<ProductMaterialFile> getProductFileListByPid(@Param("pid") String pid);
	
	public List<ProductMaterialFile> getProductFileCountListByPid(@Param("pid") String pid);	
	
	public List<ProductMaterialFile> getProductFileListByProjectNameAndFileType(ProductMaterialFile proFile);
	
	public List<ProductMaterialFile> getProductFileListByFid(String... fid);
	
	public int getProNumCount(@Param("proNum") String proNum);
}
