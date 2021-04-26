package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.sql.Timestamp;
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
import cn.concox.app.basicdata.mapper.DzlmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.Dzlmanage;
import cn.concox.vo.material.Material;
import freemarker.template.Template;
/**
 * 电子料 业务层
 */
@Service("dzlmanageService")
@Scope("prototype")
public class DzlmanageService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="dzlmanageMapper")
	private DzlmanageMapper<Dzlmanage> dzlmanageMapper;
	
	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@Resource(name="basegroupMapper")
	private  BasegroupMapper<Basegroup> basegroupMapper;
	
	@SuppressWarnings("unchecked")
	public Page<Dzlmanage> getDzlmanageList(Dzlmanage dzlmanage, int currentPage, int pageSize){
		Page<Dzlmanage> dzlmanagesPage = new Page<Dzlmanage>();
		dzlmanagesPage.setCurrentPage(currentPage);
		dzlmanagesPage.setSize(pageSize);
		dzlmanagesPage = dzlmanageMapper.getDzlmanageList(dzlmanagesPage, dzlmanage);
		return dzlmanagesPage;
	} 
	
	public void deleteDzlInfo(Dzlmanage dzlmanage){
		dzlmanageMapper.delete(dzlmanage);
	} 
	
	public Integer isExistsGid(Dzlmanage dzlmanage){
		return dzlmanageMapper.isExistsGid(dzlmanage);
	}
	
	/**
	 * 删除或修改组时查看组下是否有数据
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId){
		return dzlmanageMapper.getCountByGid(gId);
	}
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @param gId
	 */
	public void updateByGid(Integer gId){
		dzlmanageMapper.updateByGid(gId);
	}
	
	@Transactional
	public void insertDzlInfo(Dzlmanage dzlmanage){
		if(null != dzlmanage && null != dzlmanage.getProNO() && null != dzlmanage.getMasterOrSlave()){
			Material m = materialMapper.getInfoByProNOAndMasterOrSlave(dzlmanage.getProNO(), dzlmanage.getMasterOrSlave());
			if(null == m){
				m = new Material();
				m.setMaterialType(1);
				m.setProNO(dzlmanage.getProNO());
				m.setProName(dzlmanage.getProName());
				m.setProSpeci(dzlmanage.getProSpeci());
				m.setConsumption(dzlmanage.getConsumption());
				m.setMasterOrSlave(dzlmanage.getMasterOrSlave());
				if(materialMapper.isExists(m) == 0){
					m.setTotalNum(0);
					m.setCreateTime(new Timestamp(System.currentTimeMillis()));
					materialMapper.insert(m);
				}else{
					m.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					materialMapper.update(m);
				}
			}
			dzlmanageMapper.insert(dzlmanage);
		}
	}
	
	@Transactional
	public void updateDzlInfo(Dzlmanage dzlmanage){
		Dzlmanage d = null;
		if(null != dzlmanage.getDzlId()){
			d = dzlmanageMapper.getT(dzlmanage.getDzlId());//修改前的电子料信息
		}else if(null != dzlmanage.getPlacesNO() && null != dzlmanage.getProNO() && null != dzlmanage.getMasterOrSlave()){
			 d = dzlmanageMapper.getOneDzlInfo(dzlmanage.getProNO(), dzlmanage.getPlacesNO(), dzlmanage.getMasterOrSlave());
		}
		if(null != d){
			dzlmanage.setDzlId(d.getDzlId());
			dzlmanageMapper.update(dzlmanage);
			
			Material m = materialMapper.getInfoByProNOAndMasterOrSlave(d.getProNO(), d.getMasterOrSlave());
			if(null != m){
				m.setProNO(dzlmanage.getProNO());
				m.setProName(dzlmanage.getProName());
				m.setProSpeci(dzlmanage.getProSpeci());
				m.setConsumption(dzlmanage.getConsumption());
				m.setMasterOrSlave(dzlmanage.getMasterOrSlave());
				m.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				materialMapper.update(m);
			}
		}
		
	}
	
	public Dzlmanage getDzlInfo(Dzlmanage dzlmanage){ 
		return dzlmanageMapper.getT(dzlmanage.getDzlId());
	}

	public int isExists(Dzlmanage dzlmanage){
		return dzlmanageMapper.isExists(dzlmanage);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Dzlmanage dzlmanage, HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Dzlmanage> dzlmanage_list = dzlmanageMapper.getDzlmanageList(dzlmanage);
		
		String[] fieldNames = new String[] {"类别", "编码", "名称", "规格", "序号", "用量", "M|T", "备注"};
		Map map = new HashMap();
		map.put("size", dzlmanage_list.size() + 2);
		map.put("peList",dzlmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("电子料管理报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("Dzlmanage.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	
	/**
	 * 导入数据（不重复时新增，重复时修改）
	 * @param filePath
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Dzlmanage> successList = new ArrayList<Dzlmanage>();// 保存可以插入成功的表的数据
		List<Dzlmanage> repeatList = new ArrayList<Dzlmanage>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Dzlmanage s = new Dzlmanage();
						// Dzlmanage对象的属性值{dzlType[0]、proNO[1]、proName[2]、proSpeci[3]、placesNO[4]、consumption[5]、masterOrSlave[6]、remark[7]}
						
						Object[] obj = new Object[8];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2]) || 
									StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[4]) || StringUtils.isBlank((String)obj[5]) ||
									StringUtils.isBlank((String)obj[6])) {
									throw new RuntimeException();
								} else {
									s.setDzlType(((String)obj[0]).trim());
									s.setProNO(((String)obj[1]).trim());
									s.setProName((String)obj[2]);
									s.setProSpeci((String)obj[3]);
									s.setPlacesNO((String)obj[4]);
									s.setConsumption(Integer.valueOf((String)obj[5]));
									if(!"M".equals(((String) obj[6]).trim().toUpperCase()) && !"T".equals(((String) obj[6]).trim().toUpperCase())){
										s.setMasterOrSlave("T");
									}else{
										s.setMasterOrSlave(((String) obj[6]).toUpperCase());;
									}
									s.setRemark((String)obj[7]);
									s.setEnabledFlag(1);//默认可用
									
									if(isExistsGid(s)==null){
										Basegroup b=new Basegroup();
										b.setEnumSn("BASE_DZL");
										b.setGName(s.getDzlType());
										basegroupMapper.insert(b);
									}
									if(isExists(s)==0){
										try {
											this.insertDzlInfo(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else{
										repeatList.add(s);
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
							errorList.add("新增成功" + successList.size() + "条数据");
						}
						if(repeatList.size() > 0){
							List<Dzlmanage> list = new ArrayList<Dzlmanage> ();  
							list.addAll(repeatList);
							if(null != list && list.size() > 0){
								for (int k = 0; k < list.size(); k++) {
									this.updateDzlInfo(list.get(k));
								}
							}
							errorList.add("修改成功" + repeatList.size() + "条数据");
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
	
	/**
	 * 检查是否可删除
	 * @param sxdwmanage
	 * @return
	 */
	public int checkSupportDel(Dzlmanage dzlmanage) {
		return dzlmanageMapper.findCountByDzl(dzlmanage);
	}
}