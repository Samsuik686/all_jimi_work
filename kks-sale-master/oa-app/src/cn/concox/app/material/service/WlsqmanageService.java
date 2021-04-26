package cn.concox.app.material.service;

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

import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.WlsqmanageMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.DateUtil;
import cn.concox.vo.material.Wlsqmanage;
import freemarker.template.Template;

@Service("wlsqmanageService")
@Scope("prototype")
public class WlsqmanageService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "wlsqmanageMapper")
	private WlsqmanageMapper<Wlsqmanage> wlsqmanageMapper;

	@SuppressWarnings("unchecked")
	public Page<Wlsqmanage> getWlsqmanageListPage(Wlsqmanage wlsqmanage, int currentPage,int pageSize) {
		Page<Wlsqmanage> wlsqmanageListPage = new Page<Wlsqmanage>();
		wlsqmanageListPage.setCurrentPage(currentPage);
		wlsqmanageListPage.setSize(pageSize);
		wlsqmanageListPage = wlsqmanageMapper.getListPage(wlsqmanageListPage, wlsqmanage);
		return wlsqmanageListPage;
	}

	public Wlsqmanage getWlsqmanage(Wlsqmanage wlsqmanage) {
		return wlsqmanageMapper.getT(wlsqmanage.getId());
	}

	public void insertWlsqmanage(Wlsqmanage wlsqmanage) {
		wlsqmanageMapper.insert(wlsqmanage);
	}

	public void updateWlsqmanage(Wlsqmanage wlsqmanage) {
		wlsqmanageMapper.update(wlsqmanage);
	}

	public void deleteWlsqmanage(Wlsqmanage wlsqmanage) {
		wlsqmanageMapper.delete(wlsqmanage);
	}

	public List<Wlsqmanage> queryList(Wlsqmanage wlsqmanage) {
		return wlsqmanageMapper.queryList(wlsqmanage);
	}
	public int isExists(Wlsqmanage wlsqmanage){
		return wlsqmanageMapper.isExists(wlsqmanage);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(Wlsqmanage wlsqmanage, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String applicater=wlsqmanage.getApplicater();
		if(null!=applicater&&!"".equals(applicater)){
			wlsqmanage.setApplicater(new String(applicater.getBytes("iso8859-1"),"utf-8"));
		}
		List<Wlsqmanage> wlsqmanageList = wlsqmanageMapper.queryList(wlsqmanage);
		String[] fieldNames = new String[] { "平台", "物料类型", "物料编码", "物料名称", "物料规格", "申请数量", "单位", "申请人", "申请日期", "申请用途", "批复", "备注" };
		Map map = new HashMap();
		map.put("size", wlsqmanageList.size()+2);
		map.put("peList",wlsqmanageList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("物料申请管理列表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_MATERIAL)
				
									.append("Wlsqmanage.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Wlsqmanage> successList = new ArrayList<Wlsqmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Wlsqmanage s = new Wlsqmanage();
						// Wlsqmanage对象的属性值{platform[0]、matType[1]、proNO[2]、proName[3]、proSpeci[4]、number[5]、unit[6]、applicater[7]、appTime[8]、purpose[9]、state[10]、remark[11]}
						Object[] obj = new Object[12];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2]) || StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[4]) || 
									StringUtils.isBlank((String)obj[5]) || StringUtils.isBlank((String)obj[7]) || StringUtils.isBlank((String)obj[8]) || StringUtils.isBlank((String)obj[10])) {
									throw new RuntimeException();
								} else {
									s.setPlatform((String) obj[0]);
									if ("配件料".equals(((String) obj[1]).trim())) {
										s.setMatType(0);
									}else if("电子料".equals(((String) obj[1]).trim())){
										s.setMatType(1);
									}
									s.setProNO(((String) obj[2]).trim());
									s.setProName((String)obj[3]);
									s.setProSpeci(obj[4].toString());
									s.setNumber(Integer.valueOf(obj[5].toString()));
									s.setUnit((String) obj[6]);
									s.setApplicater((String) obj[7]);
									s.setAppTime(DateUtil.getTimestampByStr((String)obj[8]));
									s.setPurpose((String) obj[9]);
									s.setState((obj[10].toString().equals("同意"))?0:1);
									s.setRemark((String) obj[11]);
									if(isExists(s)==0){
										try {
											wlsqmanageMapper.insert(s);
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
