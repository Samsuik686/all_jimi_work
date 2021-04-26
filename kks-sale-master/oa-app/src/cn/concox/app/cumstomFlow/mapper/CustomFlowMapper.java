package cn.concox.app.cumstomFlow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.customProcess.CustomField;
import cn.concox.vo.customProcess.CustomFlow;
import cn.concox.vo.customProcess.CustomModel;

/**
 * 
 * @author 183
 * @version 2020年5月28日
 * @param <T>
 */
public interface CustomFlowMapper<T> extends BaseSqlMapper<T> {
	
	// 分页查询
	public Page<T> customFlowPageList(@Param(PageInterceptor.PAGE_KEY) Page<T> page, 
			@Param("po") CustomFlow pm);	
	
	// 获取流程图xml文档
	public String customFlowPageXml(@Param("name") String name);
	
	// 根据查询条件获取总数，一般用于校验
	public Integer customFlowPageCount(@Param("po") CustomFlow pm);
	
	// 插入流程模块
	public void insertModel(@Param("list") List<CustomModel> cms);
	
	// 查询流程模块
	public List<CustomModel> queryCustomModel(@Param("po") CustomModel cm);
	
	// 查询流程模块
	public List<CustomModel> queryCustomModelByIds(@Param("po") CustomModel cm);
	
	// 查询跟进人
	public List<String> queryFollowers(@Param("menuId") Integer menuId);
	
	// model id-name缓存
	public List<CustomModel> selectIdNameMap();
	
	public void deleteFlow(@Param("name")String name);

	public CustomFlow selectCustomFlow(@Param("name")String name);
	
	public void deleteModel(@Param("flowName") String flowName);
	
	@Select("select name from t_sale_custom_flow")
	public List<String> selectFlowNames();
	
	// 插入流程字段
	public void insertField(@Param("list") List<CustomField> cms);

	@Delete("delete from t_sale_custom_field where belong = #{belong }")
	public void deleteFieldByBelong(@Param("belong")String belong);
	
	@Select("select field_name AS name,type,belong, isNeed,checkBox,field_order as 'order' from t_sale_custom_field where belong = #{belong }")
	public List<CustomField> selectCustomField(@Param("belong") String belong);
}
