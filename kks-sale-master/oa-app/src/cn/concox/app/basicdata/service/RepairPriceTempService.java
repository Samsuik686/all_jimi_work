package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import cn.concox.app.basicdata.mapper.GzbjmanageMapper;
import cn.concox.app.basicdata.mapper.PjlmanageMapper;
import cn.concox.app.basicdata.mapper.PriceGroupPjMapper;
import cn.concox.app.basicdata.mapper.RepairPriceTempMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.Gzbjmanage;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.PriceGroupPj;
import cn.concox.vo.basicdata.RepairPriceTempManage;
import freemarker.template.Template;

@Service("repairPriceTempService")
@Scope("prototype")
public class RepairPriceTempService {	
	
	@Resource(name="repairPriceTempMapper")
	private RepairPriceTempMapper<RepairPriceTempManage> repairPriceTempMapper;

	@Resource(name="basegroupMapper")
	private  BasegroupMapper<Basegroup> basegroupMapper;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="pjlmanageMapper")
	private PjlmanageMapper<Pjlmanage> pjlmanageMapper;
	
	@Resource(name = "gzbjmanageMapper")
	private GzbjmanageMapper<Gzbjmanage> gzbjmanageMapper;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@Resource(name="priceGroupPjMapper")
	private PriceGroupPjMapper<PriceGroupPj> priceGroupPjMapper;
	
	/**
	 * 分页查询列表数据
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:30:31
	 * @param manage
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<RepairPriceTempManage> getManageList(RepairPriceTempManage manage, int currentPage, int pageSize) {
		Page<RepairPriceTempManage> repairPriceTempManageListPage = new Page<RepairPriceTempManage>();
		repairPriceTempManageListPage.setCurrentPage(currentPage);
		repairPriceTempManageListPage.setSize(pageSize);
		repairPriceTempManageListPage = repairPriceTempMapper.getRepairPriceTempManageList(repairPriceTempManageListPage, manage);
		List<RepairPriceTempManage> rpList = repairPriceTempManageListPage.getResult();
		setList(rpList);
		repairPriceTempManageListPage.setResult(rpList);
		return repairPriceTempManageListPage;
	}
	
	/**
	 * 查询单个信息
	 * @param rp
	 * @return
	 */
	public RepairPriceTempManage getRepairPriceInfo(RepairPriceTempManage rp){
		RepairPriceTempManage rpm =	repairPriceTempMapper.getT(rp.getRid());
		return setOne(rpm);
	}
	
	/**
	 * 删除或修改组时查看组下是否有数据
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:30:24
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId){
		return repairPriceTempMapper.getCountByGid(gId);
	}
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:30:18
	 * @param gId
	 */
	public void updateByGid(Integer gId){
		repairPriceTempMapper.updateByGid(gId);
	}
	
	/**
	 * 查询编号和主板型号结合是否存在
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:29:50
	 * @param manage
	 * @return
	 */
	public int isExists(RepairPriceTempManage manage){
		return repairPriceTempMapper.isExists(manage);
	}
	
	/**
	 * 根据维修报价保存配件料
	 * @param ids
	 * @return
	 */
	public List<Pjlmanage> getPjlByWxbjId(String... ids){
		List<Pjlmanage> pjlList = new ArrayList<Pjlmanage>();
		HashSet<String> pjlSet = new HashSet<String>();
		if(null != ids && ids.length > 0){
			String pjlIds = repairPriceTempMapper.getPjlIdsByWxbjId(ids);
			if(!StringUtil.isRealEmpty(pjlIds)){
				String[] pjlIdsTemp =pjlIds.split(",");
				if(null != pjlIdsTemp && pjlIdsTemp.length > 0){
					for (int i = 0; i < pjlIdsTemp.length; i++) {
						pjlSet.add(pjlIdsTemp[i]);
					}
					List<String> pjlIdList = new ArrayList<String>(pjlSet);
					pjlIdsTemp = (String[])pjlIdList.toArray(new String[pjlIdList.size()]);
					if(null != pjlIdsTemp && pjlIdsTemp.length > 0){
						pjlList = pjlmanageMapper.getListByIds(pjlIdsTemp);
					}
				}
			}
		}
		return pjlList;
	}
	
	/**
	 * 更新维修报价信息
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:34:20
	 * @param manage
	 */
	@Transactional
	public void updateRepairPrice(RepairPriceTempManage manage){
		BigDecimal pjlPrice = getPjlPrice(manage);
		manage.setPjlPrice(pjlPrice);
		
		BigDecimal gzbjPrice = getGzbjPrice(manage);
		manage.setGzbjPrice(gzbjPrice);
		manage.setPrice(pjlPrice.add(gzbjPrice));
		repairPriceTempMapper.update(manage);
	}

	/**
	 * 增加维修报价信息
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:34:39
	 * @param manage
	 */
	@Transactional
	public void insertRepairPrice(RepairPriceTempManage manage){
		BigDecimal pjlPrice = getPjlPrice(manage);
		manage.setPjlPrice(pjlPrice);
		
		BigDecimal gzbjPrice = getGzbjPrice(manage);
		manage.setGzbjPrice(gzbjPrice);
		manage.setPrice(pjlPrice.add(gzbjPrice));
		repairPriceTempMapper.insert(manage);
		
	}
	
	private BigDecimal getPjlPrice(RepairPriceTempManage manage) {
		BigDecimal pjlPrice = BigDecimal.ZERO;
		String idsTemp = manage.getPjlDesc();
		if(null != idsTemp && !"".equals(idsTemp)){
			String[] ids = idsTemp.split(",");
			pjlPrice = pjlmanageMapper.getSumPriceByIds(ids);
		}
		return pjlPrice;
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
	
	/**
	 * 删除维修报价信息
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:34:48
	 * @param manage
	 */
	public void deleteRepairPrice(RepairPriceTempManage manage){
		repairPriceTempMapper.delete(manage);
	}
	
	/**
	 * 检查维修报价是否可以删除，有没有被用到过
	 * @author TangYuping
	 * @version 2016年11月26日 下午4:56:00
	 * @param manage
	 * @return
	 */
	public int checkSupportDel(RepairPriceTempManage manage) {
		int count = repairPriceTempMapper.findCountWorkflowByRepairId(manage);
		return count;
	}
	
	/**
	 * 导入维修报价
	 * @author TangYuping
	 * @version 2016年11月26日 下午6:03:30
	 * @param file
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<RepairPriceTempManage> successList = new ArrayList<RepairPriceTempManage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						RepairPriceTempManage manage = new RepairPriceTempManage();
						//导入的时候默认状态都是正常
						manage.setUseState("0");
						// RepairPriceTempManage对象的属性值{obj[0]:repairType,obj[1]:amount,obj[2]:price, obj[3]:hourFee,obj[4]:priceNumber,obj[5]:model
						Object[] obj = new Object[6];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								//判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2])
										|| StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[4])) {
									throw new RuntimeException();
								}else{
									manage.setRepairType(obj[0].toString());
									manage.setAmount(obj[1].toString());
									manage.setPrice(new BigDecimal(obj[2].toString()));
									manage.setHourFee(new BigDecimal(obj[3].toString()));
									manage.setPriceNumber(obj[4].toString());
									if(obj[5] != null && StringUtils.isNotBlank(obj[5].toString())){
										manage.setModel(obj[5].toString());
									}									
									if(this.isExistsGid(manage)==0){
										Basegroup b=new Basegroup();
										b.setEnumSn("BASE_WXBJ");
										b.setGName(manage.getRepairType());
										basegroupMapper.insert(b);
									}
//									if(this.isExists(manage)==0){
										try {
											repairPriceTempMapper.insert(manage);
											successList.add(manage);
										} catch (Exception e) {
											e.printStackTrace();
										}
//									}else{
//										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行编码数据已存在,未导入,请检查......");
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
	
	/**
	 * 查看导入的数据类别是否存在数据库
	 * @author TangYuping
	 * @version 2016年11月26日 下午6:21:32
	 * @param manage
	 * @return
	 */
	public int isExistsGid(RepairPriceTempManage manage){
		return repairPriceTempMapper.isExistsGid(manage);		
	}
	
	/**
	 * 导出维修报价信息
	 * @author TangYuping
	 * @version 2016年11月26日 下午6:21:51
	 * @param zbxhmanage
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(RepairPriceTempManage manage, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<RepairPriceTempManage> repairPriceTempManageList = repairPriceTempMapper.getRepairPriceTempManageList(manage);
		repairPriceTempManageList = setList(repairPriceTempManageList);
		
		String[] fieldNames = new String[] { "报价类别", "报价描述", "报价金额（￥）", "编码", "主板型号", "配件费（￥）", "关联配件料", "故障费（￥）", "故障报价", "工时费（￥）"};
		Map map = new HashMap();
		map.put("size", repairPriceTempManageList.size() + 2);
		map.put("peList", repairPriceTempManageList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("临时维修报价管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA).append("RepairPriceTempManage.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,template, map);
	}
	
	/**
	 * 维修报价集合
	 * @param rpList
	 * @return
	 */
	public List<RepairPriceTempManage> setList(List<RepairPriceTempManage> rpList){
		if(null != rpList && rpList.size() > 0){
			for (RepairPriceTempManage rp : rpList) {
				if(!StringUtil.isRealEmpty(rp.getPjlDesc())){
					String pjlDesc = pjlmanageMapper.getGRoupConcat(StringUtil.split(rp.getPjlDesc()));
					String pjlIds =	workflowService.sortIds(rp.getPjlDesc());
					rp.setPjlId(pjlIds);
					rp.setPjlDesc(pjlDesc);
				}
				
				if(!StringUtil.isRealEmpty(rp.getGzbjDesc())){
					String gzbjDesc = gzbjmanageMapper.getGRoupConcat(StringUtil.split(rp.getGzbjDesc()));
					String gzbjIds = workflowService.sortIds(rp.getGzbjDesc());
					rp.setGzbjId(gzbjIds);
					rp.setGzbjDesc(gzbjDesc);
				}
				
				if(!StringUtil.isRealEmpty(rp.getPriceGroupDesc())){
					String ppgpDesc = priceGroupPjMapper.getGRoupConcat(StringUtil.split(rp.getPriceGroupDesc()));
					String ppgpIds = workflowService.sortIds(rp.getPriceGroupDesc());
					rp.setPriceGroupId(ppgpIds);
					rp.setPriceGroupDesc(ppgpDesc);
				}
			}
		}
		return rpList;
	}
	
	/**
	 * 单个维修报价
	 * @param rpList
	 * @return
	 */
	public RepairPriceTempManage setOne(RepairPriceTempManage rp){
		if(null != rp){
			if(!StringUtil.isRealEmpty(rp.getPjlDesc())){
				String pjlDesc = pjlmanageMapper.getGRoupConcat(StringUtil.split(rp.getPjlDesc()));
				String pjlIds =	workflowService.sortIds(rp.getPjlDesc());
				rp.setPjlId(pjlIds);
				rp.setPjlDesc(pjlDesc);
			}
			
			if(!StringUtil.isRealEmpty(rp.getGzbjDesc())){
				String gzbjDesc = gzbjmanageMapper.getGRoupConcat(StringUtil.split(rp.getGzbjDesc()));
				String gzbjIds = workflowService.sortIds(rp.getGzbjDesc());
				rp.setGzbjId(gzbjIds);
				rp.setGzbjDesc(gzbjDesc);
			}
			
			if(!StringUtil.isRealEmpty(rp.getPriceGroupDesc())){
				String ppgpDesc = priceGroupPjMapper.getGRoupConcat(StringUtil.split(rp.getPriceGroupDesc()));
				String ppgpIds = workflowService.sortIds(rp.getPriceGroupDesc());
				rp.setPriceGroupId(ppgpIds);
				rp.setPriceGroupDesc(ppgpDesc);
			}
		}
		return rp;
	}
}
