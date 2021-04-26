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
import cn.concox.app.basicdata.mapper.CjgzmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.Cjgzmanage;
import freemarker.template.Template;

/**
 * <pre>
 * 初检故障业务类
 * </pre>
 * @author Liao.bifeng
 * @version v1.0
 */
@Service("cjgzmanageService")
@Scope("prototype")
public class CjgzmanageService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="cjgzmanageMapper")
	private  CjgzmanageMapper<Cjgzmanage> cjgzmanageMapper;
	
	@Resource(name="basegroupMapper")
	private  BasegroupMapper<Basegroup> basegroupMapper;

	public void insert(Cjgzmanage cjgzmanage){
		cjgzmanageMapper.insert(cjgzmanage);
	}
	
	public void  update(Cjgzmanage cjgzmanage){
		cjgzmanageMapper.update(cjgzmanage);
	}
	
	public void  delete(Cjgzmanage cjgzmanage){
		cjgzmanageMapper.delete(cjgzmanage);
	}
	public Cjgzmanage  select(Cjgzmanage cjgzmanage){
	    return	cjgzmanageMapper.select(cjgzmanage);
	}
	
	public Integer isExistsGid(Cjgzmanage cjgzmanage){
		return cjgzmanageMapper.isExistsGid(cjgzmanage);
	}
	
	/**
	 * 删除或修改组时查看组下是否有数据
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId){
		return cjgzmanageMapper.getCountByGid(gId);
	}
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @param gId
	 */
	public void updateByGid(Integer gId){
		cjgzmanageMapper.updateByGid(gId);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Cjgzmanage> getCjgzmanageListPage(Cjgzmanage cjgzmanage, int currentPage, int pageSize){
		Page<Cjgzmanage> cjgzmanagePage = new Page<Cjgzmanage>();
		cjgzmanagePage.setCurrentPage(currentPage);
		cjgzmanagePage.setSize(pageSize);
		cjgzmanagePage =cjgzmanageMapper.queryCjgzmanageListPage(cjgzmanagePage,cjgzmanage);
		return cjgzmanagePage;
	} 
	
	public int isExists(Cjgzmanage cjgzmanage){
		return cjgzmanageMapper.isExists(cjgzmanage);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Cjgzmanage cjgzmanage, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String initheckFault=cjgzmanage.getInitheckFault();
		String number=cjgzmanage.getNumber();
		if(null!=initheckFault&&!"".equals(initheckFault)){
			cjgzmanage.setInitheckFault(new String(initheckFault.getBytes("iso8859-1"),"utf-8"));
		}
		if(null!=number&&!"".equals(number)){
			cjgzmanage.setNumber(new String(number.getBytes("iso8859-1"),"utf-8"));
		}
		
		List<Cjgzmanage> cjgzmanage_list = cjgzmanageMapper.queryList(cjgzmanage);
		
		String[] fieldNames = new String[] {"故障类别","初检故障","故障现象描述 ","故障编码 ","是否禁用 "};
		Map map = new HashMap();
		map.put("size", cjgzmanage_list.size()+2);
		map.put("peList",cjgzmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("初检故障管理列表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath("freemarker"))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("Cjgzmanage.ftl")
									.toString(); 
	
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	public List<Cjgzmanage> queryList(Cjgzmanage cjgzmanage){
		return cjgzmanageMapper.queryList(cjgzmanage);
	}
	
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Cjgzmanage> successList = new ArrayList<Cjgzmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Cjgzmanage s = new Cjgzmanage();
						// cjgzmanage对象的属性值{cusfaultClass[0]、initheckFault[1]、description[2]、number[3]、available[4]}
						Object[] obj = new Object[5];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[3])) {
									throw new RuntimeException();
								} else {
									s.setFaultClass(((String)obj[0]).trim());
									s.setInitheckFault((String) obj[1]);
									s.setDescription((String)obj[2]);
									s.setNumber((String)obj[3]);
									s.setAvailable(1);//导入默认不禁用
									if(isExistsGid(s)==null){
										Basegroup b=new Basegroup();
										b.setEnumSn("BASE_CCGZ");
										b.setGName(s.getFaultClass());
										basegroupMapper.insert(b);
									}
									if(isExists(s)==0){
										try {
											cjgzmanageMapper.insert(s);
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

	public boolean checkSupportDel(Cjgzmanage cjgzmanage) {
		int count = cjgzmanageMapper.findCountWorkflowRelatedBycjgzDesc(cjgzmanage);
		if(count > 0){
			return false;
		}
		return true;
		
	}
}
