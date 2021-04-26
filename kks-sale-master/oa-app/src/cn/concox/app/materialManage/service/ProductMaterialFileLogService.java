package cn.concox.app.materialManage.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.materialManage.mapper.ProductMaterialFileLogMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.materialManage.ProductMaterialFileLog;
import freemarker.template.Template;

/**
 * 
 */
@Service("productMaterialFileLogService")
@Scope("prototype")
public class ProductMaterialFileLogService {

	@Resource(name = "productMaterialFileLogMapper")
	private ProductMaterialFileLogMapper<ProductMaterialFileLog> productMaterialFileLogMapper;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 下载日志记录
	 * 
	 * @param pm
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<ProductMaterialFileLog> productMaterialFileLogPage(ProductMaterialFileLog pm, Integer currentPage, Integer pageSize) {
		Page<ProductMaterialFileLog> pages = new Page<ProductMaterialFileLog>();
		pages.setCurrentPage(currentPage);
		pages.setSize(pageSize);
		pages = productMaterialFileLogMapper.productMaterialFileLogPage(pages, pm);
		return pages;
	}

	public List<ProductMaterialFileLog> productMaterialFileLogList(ProductMaterialFileLog pm) {
		return productMaterialFileLogMapper.productMaterialFileLogPage(pm);
	}

	public void insert(ProductMaterialFileLog pmfl) {
		if (null != pmfl.getFileType() && !"".equals(pmfl.getFileType())) {
			if ("bomName".equals(pmfl.getFileType())) {
				pmfl.setFileType("组装料BOM表");
			} else if ("dianzibomName".equals(pmfl.getFileType())) {
				pmfl.setFileType("电子料BOM表");
			} else if ("repairName".equals(pmfl.getFileType())) {
				pmfl.setFileType("维修手册");
			} else if ("differenceName".equals(pmfl.getFileType())) {
				pmfl.setFileType("差异说明");
			} else if ("projectRemarkName".equals(pmfl.getFileType())) {
				pmfl.setFileType("备注附件");
			} else if ("softwareName".equals(pmfl.getFileType())) {
				pmfl.setFileType("软件");
			} else if ("explainName".equals(pmfl.getFileType())) {
				pmfl.setFileType("说明书");
			} else if ("kfName".equals(pmfl.getFileType())) {
				pmfl.setFileType("客服手册");
			} else if ("softDifName".equals(pmfl.getFileType())) {
				pmfl.setFileType("软件功能差异表");
			} else if ("datumName".equals(pmfl.getFileType())) {
				pmfl.setFileType("产品资料");
			} else if ("huaceName".equals(pmfl.getFileType())) {
				pmfl.setFileType("产品画册");
			} else if ("functionName".equals(pmfl.getFileType())) {
				pmfl.setFileType("功能列表");
			} else if ("zhilingName".equals(pmfl.getFileType())) {
				pmfl.setFileType("指令表");
			} else if ("productRemarkName".equals(pmfl.getFileType())) {
				pmfl.setFileType("备注附件");
			} else if ("zuzhuangName".equals(pmfl.getFileType())) {
				pmfl.setFileType("组装作业指导书");
			} else if ("testName".equals(pmfl.getFileType())) {
				pmfl.setFileType("测试作业指导书");
			} else if ("testToolName".equals(pmfl.getFileType())) {
				pmfl.setFileType("测试工具");
			} else if ("xiehaoTooName".equals(pmfl.getFileType())) {
				pmfl.setFileType("写号工具");
			} else if ("qualityRemarkName".equals(pmfl.getFileType())) {
				pmfl.setFileType("备注附件");
			}
			productMaterialFileLogMapper.insert(pmfl);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(ProductMaterialFileLog ProductMaterialFileLog, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ProductMaterialFileLog> ProductMaterialFileLogList = productMaterialFileLogList(ProductMaterialFileLog);
		String[] fieldNames = new String[] { "IP", "下载人", "文件名", "类型", "下载时间" };
		Map map = new HashMap();
		map.put("size", ProductMaterialFileLogList.size() + 2);
		map.put("peList", ProductMaterialFileLogList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("下载日志记录表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_FILEMANAGE).append("ProductMaterialFileLog.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
}
