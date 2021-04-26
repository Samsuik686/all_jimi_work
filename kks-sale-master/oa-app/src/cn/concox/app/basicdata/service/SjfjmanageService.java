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

import cn.concox.app.basicdata.mapper.BasegroupMapper;
import cn.concox.app.basicdata.mapper.SjfjmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.Sjfjmanage;
import freemarker.template.Template;

/**
 * <pre>
 * 随机附件业务类
 * </pre>
 * @author Liao.bifeng 
 * @version v1.0
 */
@Service("sjfjmanageService")
@Scope("prototype")
public class SjfjmanageService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="sjfjmanageMapper")
	private  SjfjmanageMapper<Sjfjmanage> sjfjmanageMapper;
	
	@Resource(name="basegroupMapper")
	private  BasegroupMapper<Basegroup> basegroupMapper;
	
	public Sjfjmanage select(Sjfjmanage sjfjmanage){
		return sjfjmanageMapper.select(sjfjmanage);
	}
	
	public void update(Sjfjmanage sjfjmanage){
		sjfjmanageMapper.update(sjfjmanage);
	}
	
	public void detele(Sjfjmanage sjfjmanage){
		sjfjmanageMapper.delete(sjfjmanage);
	}
	
	public void insert(Sjfjmanage sjfjmanage){
		sjfjmanageMapper.insert(sjfjmanage);
	}
	
	public int isExists(Sjfjmanage sjfjmanage){
		return sjfjmanageMapper.isExists(sjfjmanage);
	}
	
	public Integer isExistsGid(Sjfjmanage sjfjmanage){
		return sjfjmanageMapper.isExistsGid(sjfjmanage);
	}
	
	/**
	 * 删除或修改组时查看组下是否有数据
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId){
		return sjfjmanageMapper.getCountByGid(gId);
	}
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @param gId
	 */
	public void updateByGid(Integer gId){
		sjfjmanageMapper.updateByGid(gId);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Sjfjmanage> getSjfjmanageListPage(Sjfjmanage sjfjmanage, int currentPage, int pageSize){
		Page<Sjfjmanage> sjfjmanagePage = new Page<Sjfjmanage>();
		sjfjmanagePage.setCurrentPage(currentPage);
		sjfjmanagePage.setSize(pageSize);
		sjfjmanagePage =sjfjmanageMapper.querySjfjmanageListPage(sjfjmanagePage,sjfjmanage);
		return sjfjmanagePage;
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Sjfjmanage sjfjmanage, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String brand=sjfjmanage.getBrand();
		String name=sjfjmanage.getName();
		if(null!=brand&&!"".equals(brand)){
			sjfjmanage.setBrand(new String(brand.getBytes("iso8859-1"),"utf-8"));
		}
		if(null!=name&&!"".equals(name)){
			sjfjmanage.setName(new String(name.getBytes("iso8859-1"),"utf-8"));
		}
		
		List<Sjfjmanage> sjfjmanage_list = sjfjmanageMapper.queryList(sjfjmanage);
		
		String[] fieldNames = new String[] {"类别  ","品牌  ","名称 ","颜色","数量 "};
		Map map = new HashMap();
		map.put("size", sjfjmanage_list.size()+2);
		map.put("peList",sjfjmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("随机附件管理列表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("Sjfjmanage.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	public List<Sjfjmanage> queryList(Sjfjmanage sjfjmanage){
		return sjfjmanageMapper.queryList(sjfjmanage);
	}
	
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Sjfjmanage> successList = new ArrayList<Sjfjmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Sjfjmanage s = new Sjfjmanage();
						
						// Sjfjmanage对象的属性值{category[0], brand[1], name[2], color[3], number[4]}
						Object[] obj = new Object[5];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2]) 
										|| StringUtils.isBlank((String)obj[3])|| StringUtils.isBlank((String)obj[4])) {
									throw new RuntimeException();
								} else {
									s.setCategory(((String)obj[0]).trim());
									s.setBrand((String) obj[1]);
									s.setName((String)obj[2]);
									s.setColor((String)obj[3]);
									s.setNumber(Integer.valueOf(obj[4].toString()));
									if(isExistsGid(s)==null){
										Basegroup b=new Basegroup();
										b.setEnumSn("BASE_SJFJ");
										b.setGName(s.getCategory());
										basegroupMapper.insert(b);
									}
									if(isExists(s)==0){
										try {
											sjfjmanageMapper.insert(s);
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

	public boolean checkSupportDel(Sjfjmanage sjfjmanage) {
		int count = sjfjmanageMapper.findCountWorkflowRelatedBysjfjDesc(sjfjmanage);
		if(count > 0){
			return false;
		}
		return true;
	}
}
