package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.math.BigDecimal;
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

import cn.concox.app.basicdata.mapper.GzbjmanageMapper;
import cn.concox.app.basicdata.mapper.RepairPriceTempMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Gzbjmanage;
import cn.concox.vo.basicdata.RepairPriceTempManage;
import freemarker.template.Template;

/**
 * <pre>
 * 故障报价业务类
 * </pre>
 */
@Service("gzbjmanageService")
@Scope("prototype")
public class GzbjmanageService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "gzbjmanageMapper")
	private GzbjmanageMapper<Gzbjmanage> gzbjmanageMapper;
	
	@Resource(name="repairPriceTempMapper")
	private RepairPriceTempMapper<RepairPriceTempManage> repairPriceTempMapper;

	@SuppressWarnings("unchecked")
	public Page<Gzbjmanage> getGzbjmanageListPage(Gzbjmanage gzbjmanage, int currentPage, int pageSize) {
		Page<Gzbjmanage> gzbjmanages = new Page<Gzbjmanage>();
		gzbjmanages.setCurrentPage(currentPage);
		gzbjmanages.setSize(pageSize);
		return gzbjmanageMapper.getGzbjmanageList(gzbjmanages, gzbjmanage);
	}

	public Gzbjmanage getGzbjmanage(Gzbjmanage gzbjmanage) {
		return gzbjmanageMapper.getT(gzbjmanage.getId());
	}

	public void deleteGzbjmanage(Gzbjmanage gzbjmanage) {
		gzbjmanageMapper.delete(gzbjmanage);
	}

	public void insertGzbjmanage(Gzbjmanage gzbjmanage) {
		gzbjmanageMapper.insert(gzbjmanage);
	}
	
	@Transactional
	public void updateGzbjmanage(Gzbjmanage gzbjmanage) {
		gzbjmanageMapper.update(gzbjmanage);
		
		List<RepairPriceTempManage> rtList = repairPriceTempMapper.getRepairPriceListByGzbjId(gzbjmanage.getId());
		if(rtList.size() > 0){
			for (RepairPriceTempManage rt : rtList) {
				BigDecimal gzbjPrice = getGzbjPrice(rt);
				rt.setGzbjPrice(gzbjPrice);
				rt.setPrice(rt.getPjlPrice().add(gzbjPrice));
				repairPriceTempMapper.update(rt);
			}
		}
		
	}
	
	private BigDecimal getGzbjPrice(RepairPriceTempManage manage) {
		BigDecimal gzbjPrice = BigDecimal.ZERO;
		String idsTemp = manage.getGzbjDesc();
		if(null != idsTemp && !"".equals(idsTemp)){
			String[] ids = idsTemp.split(",");
			gzbjPrice = gzbjmanageMapper.getSumPriceByIds(ids);
		}
		return gzbjPrice;
	}

	public List<Gzbjmanage> getGzbjmanageList(Gzbjmanage gzbjmanage) {
		return gzbjmanageMapper.getGzbjmanageList(gzbjmanage);
	}
	
	public int isExists(Gzbjmanage gzbjmanage){
		return gzbjmanageMapper.isExists(gzbjmanage);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(Gzbjmanage gzbjmanage, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Gzbjmanage> gzbjmanageList = gzbjmanageMapper.getGzbjmanageList(gzbjmanage);
		
		String[] fieldNames = new String[] {"故障类别", "故障现象", "价格（￥）", "备注"};
		Map map = new HashMap();
		map.put("size", gzbjmanageList.size() + 2);
		map.put("peList", gzbjmanageList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("故障报价管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA).append("Gzbjmanage.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,template, map);
	}
	
	@Transactional(readOnly = false)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Gzbjmanage> successList = new ArrayList<Gzbjmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Gzbjmanage s = new Gzbjmanage();
						Object[] obj = new Object[4];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								//判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[2])) {
									throw new RuntimeException();
								}else{
									s.setFaultType((String) obj[0]);
									s.setFaultDesc((String)obj[1]);
									s.setCosts(new BigDecimal(obj[2].toString()));
									s.setRemark((String)obj[3]);
									if(isExists(s)==0){
										try {
											gzbjmanageMapper.insert(s);
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
	
	//故障报价获取价格
	public BigDecimal getSumPriceByIds(String... ids){
		return gzbjmanageMapper.getSumPriceByIds(ids);
	}
	
	/**
	 * 检查是否可删除
	 * @param sxdwmanage
	 * @return
	 */
	public int checkSupportDel(Gzbjmanage gzbjmanage) {
		return gzbjmanageMapper.findCountByGzbj(gzbjmanage);
	}
}
