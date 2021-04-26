package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.GroupPjDetailsMapper;
import cn.concox.app.basicdata.mapper.GroupPjMapper;
import cn.concox.app.basicdata.mapper.PjlmanageMapper;
import cn.concox.app.basicdata.mapper.PriceGroupPjDetailsMapper;
import cn.concox.app.basicdata.mapper.PriceGroupPjMapper;
import cn.concox.app.basicdata.mapper.RepairPriceTempMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.GroupPj;
import cn.concox.vo.basicdata.GroupPjDetails;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.PriceGroupPj;
import cn.concox.vo.basicdata.PriceGroupPjDetails;
import cn.concox.vo.basicdata.RepairPriceTempManage;
import cn.concox.vo.material.Material;
import freemarker.template.Template;
/**
 * 配件料 业务层
 */
@Service("pjlmanageService")
@Scope("prototype")
public class PjlmanageService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="pjlmanageMapper")
	private PjlmanageMapper<Pjlmanage> pjlmanageMapper;

	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@Resource(name="groupPjDetailsMapper")
	private GroupPjDetailsMapper<GroupPjDetails> groupPjDetailsMapper;
	
	@Resource(name="groupPjMapper")
	private GroupPjMapper<GroupPj> groupPjMapper;
	
	@Resource(name="priceGroupPjDetailsMapper")
	private PriceGroupPjDetailsMapper<PriceGroupPjDetails> priceGroupPjDetailsMapper;
	
	@Resource(name="priceGroupPjMapper")
	private PriceGroupPjMapper<PriceGroupPj> priceGroupPjMapper;
	
	@Resource(name="repairPriceTempMapper")
	private RepairPriceTempMapper<RepairPriceTempManage> repairPriceTempMapper;
	
	@SuppressWarnings("unchecked")
	public Page<Pjlmanage> getPjlmanageList(Pjlmanage pjlmanage, int currentPage, int pageSize){
		Page<Pjlmanage> pjlmanagesPage = new Page<Pjlmanage>();
		pjlmanagesPage.setCurrentPage(currentPage);
		pjlmanagesPage.setSize(pageSize);
		pjlmanagesPage = pjlmanageMapper.getPjlmanageList(pjlmanagesPage, pjlmanage);
		return pjlmanagesPage;
	} 
	
	public void deletePjlInfo(Pjlmanage pjlmanage){
		pjlmanageMapper.delete(pjlmanage);
	} 
	
	@Transactional
	public void insertPjlInfo(Pjlmanage pjlmanage){
		if(null != pjlmanage && null != pjlmanage.getProNO()){
			Material m = materialMapper.getInfoByProNOAndMasterOrSlave(pjlmanage.getProNO(), pjlmanage.getMasterOrSlave());
			if(null == m){
				m = new Material();
				m.setMaterialType(0);
				m.setProNO(pjlmanage.getProNO());
				m.setProName(pjlmanage.getProName());
				m.setProSpeci(pjlmanage.getProSpeci());
				m.setConsumption(pjlmanage.getConsumption());
				m.setRetailPrice(pjlmanage.getRetailPrice());
				m.setMasterOrSlave(pjlmanage.getMasterOrSlave());
				if(materialMapper.isExists(m) == 0){
					m.setTotalNum(0);
					m.setCreateTime(new Timestamp(System.currentTimeMillis()));
					materialMapper.insert(m);
				}else{
					m.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					materialMapper.update(m);
				}
			}
			pjlmanageMapper.insert(pjlmanage);
		}
	}
	
	/**
	 * 修改配件料价格，相应的修改物料、销售配件料组合详情、报价配件料组合、维修报价的价格
	 * @param pjlmanage
	 */
	@Transactional
	public void updatePjlInfo(Pjlmanage pjlmanage){
		Pjlmanage p = null;
		if(null != pjlmanage.getPjlId()){
			p = pjlmanageMapper.getT(pjlmanage.getPjlId());
		}else if(null != pjlmanage.getMarketModel() && null !=pjlmanage.getModel() && null != pjlmanage.getProNO() && null != pjlmanage.getMasterOrSlave()){
			 p = pjlmanageMapper.getOnePjlInfo(pjlmanage.getMarketModel(), pjlmanage.getModel(),pjlmanage.getProNO(),pjlmanage.getMasterOrSlave());
		}
		if(null != p){
			pjlmanage.setPjlId(p.getPjlId());
			pjlmanageMapper.update(pjlmanage);
			//修改物料
			Material m = materialMapper.getInfoByProNOAndMasterOrSlave(p.getProNO(), p.getMasterOrSlave());
			if(null != m){
				m.setProNO(pjlmanage.getProNO());
				m.setProName(pjlmanage.getProName());
				m.setProSpeci(pjlmanage.getProSpeci());
				m.setConsumption(pjlmanage.getConsumption());
				m.setRetailPrice(pjlmanage.getRetailPrice());
				m.setMasterOrSlave(pjlmanage.getMasterOrSlave());
				m.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				materialMapper.update(m);
			}
			
			 //修改销售配件料组合详情、销售配件料组合
			 List<GroupPjDetails> gList = groupPjDetailsMapper.getGroupPjDetailsListByProNOAndMasterOrSlave(p.getProNO(), p.getMasterOrSlave());
			 if(gList.size() > 0){
				 for (GroupPjDetails g : gList) {
					if(pjlmanage.getRetailPrice().compareTo(g.getRetailPrice()) != 0){//价格不一样时修改
						g.setRetailPrice(pjlmanage.getRetailPrice());
						groupPjDetailsMapper.update(g);//修改销售配件料组合详情价格
						
						BigDecimal groupPrice =	groupPjDetailsMapper.getPriceByGroupId(g.getGroupId());
						
						GroupPj groupPj = groupPjMapper.getT(g.getGroupId());
						groupPj.setGroupPrice(groupPrice);
						groupPjMapper.update(groupPj);//修改销售配件料组合价格
					}
				}
			 }
			 
			 //修改报价配件料组合详情、报价配件料组合
			 List<PriceGroupPjDetails> pgList = priceGroupPjDetailsMapper.getPriceGroupPjDetailsListByProNOAndMasterOrSlave(p.getProNO(), p.getMasterOrSlave());
			 if(pgList.size() > 0){
				 for (PriceGroupPjDetails g : pgList) {
					if(pjlmanage.getRetailPrice().compareTo(g.getRetailPrice()) != 0){//价格不一样时修改
						g.setRetailPrice(pjlmanage.getRetailPrice());
						priceGroupPjDetailsMapper.update(g);//修改报价配件料组合详情价格
						
						BigDecimal pjPrice = priceGroupPjDetailsMapper.getPriceByGroupId(g.getGroupId());
						
						PriceGroupPj groupPj = priceGroupPjMapper.getT(g.getGroupId());
						if(null == groupPj.getHourFee()){
							groupPj.setHourFee(BigDecimal.ZERO);
						}
						groupPj.setPjlPrice(pjPrice);
						groupPj.setGroupPrice(pjPrice/*.add(groupPj.getHourFee())*/);
						priceGroupPjMapper.update(groupPj);//修改报价配件料组合价格
					}
				}
			 }
			 
			 //修改维修报价（做到各物料修改处）
			 List<RepairPriceTempManage> rList = repairPriceTempMapper.getRepairPriceListByPjlId(p.getPjlId());
			 if(rList.size() > 0){
				 for (RepairPriceTempManage r : rList) {
					 BigDecimal price = repairPriceTempMapper.getPjlPriceByPjlDesc(r.getRid());
					 r.setPjlPrice(price);
					 if(null == r.getGzbjPrice()){
						 r.setGzbjPrice(BigDecimal.ZERO);
					 }
					 r.setPrice(r.getPjlPrice().add(r.getGzbjPrice()));
					 repairPriceTempMapper.update(r);
				}
			 }
		}
	}
	
	public Pjlmanage getPjlInfo(Pjlmanage pjlmanage){ 
		return pjlmanageMapper.getT(pjlmanage.getPjlId());
	}

	public int isExists(Pjlmanage pjlmanage){
		return pjlmanageMapper.isExists(pjlmanage);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Pjlmanage pjlmanage, HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Pjlmanage> pjlmanage_list = pjlmanageMapper.getPjlmanageList(pjlmanage);
		
		String[] fieldNames = new String[] {"市场型号", "主板型号", "编码", "名称", "规格", "用量", "M|T", "零售价（￥）", "工时费（￥）", "备选型号", "备注"};
		Map map = new HashMap();
		map.put("size", pjlmanage_list.size() + 2);
		map.put("peList",pjlmanage_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("配件料管理报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER))
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
									.append("Pjlmanage.ftl")
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
		List<Pjlmanage> successList = new ArrayList<Pjlmanage>();// 保存可以插入成功的表的数据
		List<Pjlmanage> repeatList = new ArrayList<Pjlmanage>();// 修改重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Pjlmanage s = new Pjlmanage();
						// Pjlmanage对象的属性值{marketModel[0]、model[1]、proNO[2]、proName[3]、proSpeci[4]、consumption[5]、masterOrSlave[6]、retailPrice[7]、hourFee[8]、otherModel[9]、remark[10]}
						
						Object[] obj = new Object[11];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2]) || 
									StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[4]) || StringUtils.isBlank((String)obj[5]) || 
									StringUtils.isBlank((String)obj[6]) || StringUtils.isBlank((String)obj[7]) || StringUtils.isBlank((String)obj[8])) {
									throw new RuntimeException();
								} else {
									s.setMarketModel(((String)obj[0]).trim());
									s.setModel(((String)obj[1]).trim());
									s.setProNO(((String)obj[2]).trim());
									s.setProName((String)obj[3]);
									s.setProSpeci(obj[4].toString());
									s.setConsumption(Integer.valueOf((String)obj[5]));
									if(!"M".equals(((String) obj[6]).trim().toUpperCase()) && !"T".equals(((String) obj[6]).trim().toUpperCase())){
										s.setMasterOrSlave("T");
									}else{
										s.setMasterOrSlave(((String) obj[6]).toUpperCase());;
									}
									s.setRetailPrice(BigDecimal.valueOf(Double.valueOf((String) obj[7])));
									s.setHourFee(BigDecimal.valueOf(Double.valueOf((String) obj[8])));
									if(null != obj[8] && StringUtils.isNotBlank((String)obj[9])){
										s.setOtherModel(((String)obj[9]).trim());
									}
									s.setRemark((String)obj[10]);
									if(isExists(s)==0){
										try {
											this.insertPjlInfo(s);
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
							List<Pjlmanage> list = new ArrayList<Pjlmanage> ();  
							list.addAll(repeatList);
							if(null != list && list.size() > 0){
								for (int k = 0; k < list.size(); k++) {
									this.updatePjlInfo(list.get(k));
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
	public int checkSupportDel(Pjlmanage pjlmanage) {
		return pjlmanageMapper.findCountByPjl(pjlmanage);
	}
	
	/**
	 * 获得配件料的id和名称，显示时一一对应
	 * @param ids
	 * @return
	 */
	public List<Pjlmanage> getListByIds(String... ids){
		List<Pjlmanage> pjlList = new ArrayList<Pjlmanage>();
		if(null != ids && ids.length > 0){
			 pjlList = pjlmanageMapper.getListByIds(ids);
		}
		return pjlList;
	}
	
	/**
	 * 维修和报价根据主板型号查询所有备选型号及本身型号
	 * @param model
	 * @return
	 */
	public Set<String> getAllOherModel(String model){
		Set<String> modelSet = new HashSet<String>();
		if(null != model && !"".equals(model)){
			String m = pjlmanageMapper.getAllOherModel(model);
			if(null != m && !"".equals(m)){
				String[] s1 = m.split(",");
				for (String s : s1) {
					modelSet.add(s);
				}
			}
		}
		return modelSet;
	}
	
	//维修报价获取关联配件料价格
	public BigDecimal getSumPriceByIds(String... ids){
		return pjlmanageMapper.getSumPriceByIds(ids);
	}
	
	/**
	 * @Title: getPjlByPjlIds 
	 * @Description:配件料组合根据选中的配件料ids，获取配件料信息列表
	 * @param ids
	 * @return 
	 * @author 20160308
	 * @date 2017年11月16日 下午4:05:37
	 */
	public List<Pjlmanage> getPjlByPjlIds(String... ids){
		return pjlmanageMapper.getPjlByPjlIds(ids);
	}
}