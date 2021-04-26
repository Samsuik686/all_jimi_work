package cn.concox.app.material.service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialLogTempMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.material.MaterialLogTemp;
import freemarker.template.Template;

/**
 * <pre>
 * 出库入库业务接口
 * </pre>
 * 
 */
@Service("materialLogTempService")
@Scope("prototype")
public class MaterialLogTempService {
	@Resource(name = "materialLogTempMapper")
	private MaterialLogTempMapper<MaterialLogTemp> materialLogTempMapper;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	public void insert(MaterialLogTemp materialLogTemp) throws Exception {
		materialLogTempMapper.insert(materialLogTemp);
	}
	
	@Transactional
	public void delete(MaterialLogTemp materialLogTemp) {
		materialLogTempMapper.deleteByIds(StringUtil.split(materialLogTemp.getIds()));
	}
	
	@SuppressWarnings("unchecked")
	public Page<MaterialLogTemp> getMaterialLogTempList(MaterialLogTemp materialLogTemp, int currentPage,int pageSize) {
		Page<MaterialLogTemp> materialLogTempPage = new Page<MaterialLogTemp>();
		materialLogTempPage.setCurrentPage(currentPage);
		materialLogTempPage.setSize(pageSize);
		materialLogTempPage = materialLogTempMapper.getMaterialLogTempList(materialLogTempPage, materialLogTemp);
		return materialLogTempPage;
	}
	
	public List<MaterialLogTemp> getMaterialLogTempList(MaterialLogTemp materialLogTemp){
		return  materialLogTempMapper.getMaterialLogTempList(materialLogTemp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(MaterialLogTemp materialLogTemp, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<MaterialLogTemp> materialLogTemplist = getMaterialLogTempList(materialLogTemp);

		String[] fieldNames = new String[] {"物料类型（电子 | 配件）", "出库|入库", "出库入库类型", "仓库的名字", "出入库日期", "数量", "收货方",
				"经手人 ", "物料型号 ", "平台", "物料编码", "物料名称", "物料规格", "序号", "用量", "M | T", "损耗", "批发价（￥）", "成本价（￥）", "工时费（￥）", "零售价（￥）", "厂商代码", "厂商名称", "备注", "导入时间", "导入人", "行号", "错误原因" };
		Map map = new HashMap();
		map.put("size", materialLogTemplist.size()+2);
		map.put("peList", materialLogTemplist);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("出入库导出管理列表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(
				request.getRealPath(GlobalCons.FREEMARKER)).append("/")
				.append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_MATERIAL)
				.append("MaterialLogTemp.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,
				template, map);
	}
}

