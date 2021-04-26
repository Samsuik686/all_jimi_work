/*
 * Created: 2016-7-15
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 * @file: SaleFinalfailureServiceImpl.java
 * @functionName : system
 * @systemAbreviation : sale
 */
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
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.BasegroupMapper;
import cn.concox.app.basicdata.mapper.ZgzmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.Zgzmanage;
import freemarker.template.Template;
/**
 * <pre>
 * 最终故障管理表业务类
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
@Service("zgzmanageService")
@Scope("prototype")
public class ZgzmanageService{
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="zgzmanageMapper")
	private ZgzmanageMapper<Zgzmanage> zgzmanageMapper;
	
	@Resource(name="basegroupMapper")
	private  BasegroupMapper<Basegroup> basegroupMapper;
	
	@SuppressWarnings("unchecked")
	public Page<Zgzmanage> getManageListPage(Zgzmanage finalfailure,int currentPage, int pageSize){
		Page<Zgzmanage> finalfailures = new Page<Zgzmanage>();
		finalfailures.setCurrentPage(currentPage); 
		finalfailures.setSize(pageSize);
		return zgzmanageMapper.queryListPage(finalfailures, finalfailure);
	}

	public Zgzmanage getInfo(Zgzmanage finalfailure) {  
		return zgzmanageMapper.getT(finalfailure.getId()); 
	} 
	
	public void deleteInfo(Zgzmanage finalfailure){
		zgzmanageMapper.delete(finalfailure);
	} 
	
	public void insertInfo(Zgzmanage finalfailure){
		zgzmanageMapper.insert(finalfailure);
	}
	
	public void updateInfo(Zgzmanage finalfailure){
		zgzmanageMapper.update(finalfailure);
	}
 
	public List<Zgzmanage> queryList(Zgzmanage zgzmanage) {
		return zgzmanageMapper.queryList(zgzmanage);
	}
	
	public int isExists(Zgzmanage zgzmanage){
			return zgzmanageMapper.isExists(zgzmanage);
	}
	
	public Integer isExistsGid(Zgzmanage zgzmanage){
		return zgzmanageMapper.isExistsGid(zgzmanage);
	}
	
	/**
	 *新增修改时查看 是否存在主板型号类别
	 */
	public Integer isExistsZBXH(Zgzmanage zgzmanage){
		return zgzmanageMapper.isExistsZBXH(zgzmanage);
	}
	
	/**
	 * 删除或修改组时查看组下是否有数据
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId){
		return zgzmanageMapper.getCountByGid(gId);
	}
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @param gId
	 */
	public void updateByGid(Integer gId){
		zgzmanageMapper.updateByGid(gId);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Zgzmanage zgzmanage,HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Zgzmanage> zgzmanage_list = zgzmanageMapper.queryList(zgzmanage);
		
		String[] fieldNames = new String[] {"故障类别","最终故障","故障编码","型号类别","方法描述","是否同步","是否禁用"};
		Map map = new HashMap();
		map.put("size", zgzmanage_list.size()+2);
		map.put("peList",zgzmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("最终故障管理列表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("Zgzmanage.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	
	/**
	 * 导入数据
	 * @param file
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Zgzmanage> successList = new ArrayList<Zgzmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Object[] obj = new Object[7];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || 
										StringUtils.isBlank((String)obj[2]) || StringUtils.isBlank((String)obj[3])) {
									
									throw new RuntimeException();
								}else{
									// Zgzmanage对象的属性值{faultType[0]、proceMethod[1]、methodNO[2]、modelType[3]、remark[4]、enabledFlag[5]}
									Zgzmanage s = new Zgzmanage();
									s.setFaultType(((String) obj[0]).trim());
									s.setProceMethod((String)obj[1]);
									s.setMethodNO((String) obj[2]);
									s.setModelType(obj[3].toString());
									s.setIsSyncSolution(Integer.parseInt(obj[4].toString()));
									s.setRemark((String) obj[5]);
									s.setEnabledFlag(1);//默认导入不禁用
									if(isExistsGid(s)==null){
										Basegroup b=new Basegroup();
										b.setEnumSn("BASE_ZZGZ");
										b.setGName(s.getFaultType());
										basegroupMapper.insert(b);
									}	
//									if(isExistsZBXH(s)!=null){
										if(isExists(s)==0){
											try {
												zgzmanageMapper.insert(s);
												successList.add(s);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}else{
											repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行数据已存在,未导入,请检查......");
										}
//									}else{
//										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行类型类别不存在,未导入,请检查......");
//									}
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

	public boolean checkSupportDel(Zgzmanage zgzmanage) {
		int count = zgzmanageMapper.findCountWorkflowRelatedByzzgzDesc(zgzmanage);
		if(count > 0){
			return false;
		}
		return true;
	}
	
	public List<Zgzmanage> queryListByIds(String[] ids) {
		return zgzmanageMapper.queryListByIds(ids);
	}
	
}
