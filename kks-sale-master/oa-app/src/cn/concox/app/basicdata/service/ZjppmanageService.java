package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.ZjppmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Zjppmanage;
import freemarker.template.Template;

/**
 * <pre>
 * 终检型号匹配业务类
 * </pre>
 */
@Service("zjppmanageService")
@Scope("prototype")
public class ZjppmanageService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "zjppmanageMapper")
	private ZjppmanageMapper<Zjppmanage> zjppmanageMapper;

	@SuppressWarnings("unchecked")
	public Page<Zjppmanage> getZjppmanageListPage(Zjppmanage zjppmanage, int currentPage, int pageSize) {
		Page<Zjppmanage> zjppmanages = new Page<Zjppmanage>();
		zjppmanages.setCurrentPage(currentPage);
		zjppmanages.setSize(pageSize);
		return zjppmanageMapper.getZjppmanageListPage(zjppmanages, zjppmanage);
	}

	public Zjppmanage getZjppmanage(Zjppmanage zjppmanage) {
		return zjppmanageMapper.getT(zjppmanage.getMatchId());
	}

	public void deleteZjppmanage(Zjppmanage zjppmanage) {
		zjppmanageMapper.delete(zjppmanage);
	}

	public void insertZjppmanage(Zjppmanage zjppmanage) {
		zjppmanageMapper.insert(zjppmanage);
	}

	public void updateZjppmanage(Zjppmanage zjppmanage) {
		zjppmanageMapper.update(zjppmanage);
	}

	public List<Zjppmanage> queryList(Zjppmanage zjppmanage) {
		return zjppmanageMapper.queryList(zjppmanage);
	}
	
	public List<Zjppmanage> getZjppListByModel(Zjppmanage zjppmanage){
		return zjppmanageMapper.getZjppListByModel(zjppmanage.getModel());
	}
	
	
	public int isExists(Zjppmanage zjppmanage){
		return zjppmanageMapper.isExists(zjppmanage);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(Zjppmanage zjppmanage, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Zjppmanage> zjppmanageList = zjppmanageMapper.queryList(zjppmanage);
		
		String[] fieldNames = new String[] { "主板型号", "测试步骤", "方法", "注意事项" };
		Map map = new HashMap();
		map.put("size", zjppmanageList.size() + 2);
		map.put("peList", zjppmanageList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("终检型号匹配管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA).append("Zjppmanage.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,template, map);
	}
	
	@Transactional(readOnly = false)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Zjppmanage> successList = new ArrayList<Zjppmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Zjppmanage s = new Zjppmanage();
						// Zjppmanage对象的属性值{model[0]、marketModel[1]、modelType[2]、remark[3]}
						Object[] obj = new Object[4];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								//判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2])|| StringUtils.isBlank((String)obj[3])) {
									throw new RuntimeException();
								}else{
									s.setModel((String) obj[0]);
									s.setStep(Integer.valueOf(obj[1].toString()));
									s.setTestMethod((String)obj[2]);
									s.setBecareful((String)obj[3]);
									if(isExists(s)==0){
										try {
											zjppmanageMapper.insert(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else{
										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行数据已存在,未导入,请检查......");
									}
								}
							} catch (Exception e) {
								if (errorList.size() < 1000) {// 最多保存1000条错误数据
									errorList.add((errorList.size() + 1) + ":第" + (j + 1) + "行导入错误,有必填数据未填写......");
								}
							}
						}
					}
					if (errorList.size() < 1) {

						if (successList.size() > 0) {
							errorList.add("导入成功！！！");
						}else{
							errorList.add("可导入数据已全部存在！！！");
						}
						if(repeatList.size()>0){
							errorList.addAll(repeatList);
						}
					} else {
						errorList.add(0, "导入失败.......");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return errorList;
	}
}
