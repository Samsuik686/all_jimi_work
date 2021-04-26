package cn.concox.app.material.service;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.mapper.DzlmanageMapper;
import cn.concox.app.basicdata.mapper.GroupPjDetailsMapper;
import cn.concox.app.basicdata.mapper.PjlmanageMapper;
import cn.concox.app.basicdata.mapper.SxdwmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialLogMapper;
import cn.concox.app.material.mapper.MaterialLogTempMapper;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.app.report.mapper.XspjcostsReportMapper;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowRelatedMapper;
import cn.concox.app.workflow.mapper.WorkflowRepairMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.Convert;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Dzlmanage;
import cn.concox.vo.basicdata.GroupPjDetails;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.PricePj;
import cn.concox.vo.basicdata.Sxdwmanage;
import cn.concox.vo.material.Material;
import cn.concox.vo.material.MaterialLog;
import cn.concox.vo.material.MaterialLogTemp;
import cn.concox.vo.report.XspjcostsReport;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowRelated;
import cn.concox.vo.workflow.WorkflowRepair;
import freemarker.template.Template;

/**
 * <pre>
 * 出库入库业务接口
 * </pre>
 * 
 * @author Li.Bifeng
 * @version v1.0
 */
@Service("materialLogService")
@Scope("prototype")
public class MaterialLogService {

	private static Logger log = Logger.getLogger(MaterialLogService.class);

	@Resource(name = "materialLogMapper")
	private MaterialLogMapper<MaterialLog> materialLogMapper;
	
	@Resource(name = "materialLogTempMapper")
	private MaterialLogTempMapper<MaterialLogTemp> materialLogTempMapper;
	
	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;

	@Resource(name = "materialService")
	private MaterialService materialService;
	
	@Resource(name="workflowMapper")
	private  WorkflowMapper<Workflow> workflowMapper;
	
	@Resource(name="workflowRepairMapper")
	private  WorkflowRepairMapper<WorkflowRepair> workflowRepairMapper;
	
	@Resource(name="workflowRelatedMapper")
	private  WorkflowRelatedMapper<WorkflowRelated> workflowRelatedMapper;
	
	@Resource(name="sxdwmanageMapper")
	private  SxdwmanageMapper<Sxdwmanage> sxdwmanageMapper;
	
	@Resource(name="pjlmanageMapper")
	private PjlmanageMapper<Pjlmanage> pjlmanageMapper;
	
	@Resource(name="dzlmanageMapper")
	private DzlmanageMapper<Dzlmanage> dzlmanageMapper;
	
	@Resource(name = "xspjcostsReportMapper")
	private XspjcostsReportMapper<XspjcostsReport> xspjcostsReportMapper;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="groupPjDetailsMapper")
	private GroupPjDetailsMapper<GroupPjDetails> groupPjDetailsMapper;

	public void insert(MaterialLog materialLog) throws Exception {
		int boundStatu;
		generateOrderNOAndOutTime(materialLog);			
		if (0 == materialLog.getTotalType()) {// 入库
			boundStatu = materialService.inbound(materialLog);
		} else {// 出库
			Material inventory = materialMapper.getInfoByProNOAndMasterOrSlave(materialLog.getProNO(), materialLog.getMasterOrSlave());//获取物料
			if(null != inventory){
				boundStatu = materialService.outbound(materialLog);
			}else{
				throw new Exception("找不到对应的库存物料");
			}
		}
		if (0 == boundStatu) {
			materialLogMapper.insert(materialLog);
		}else{
			throw new Exception(getStateDescribe(boundStatu));
		}
	}
	
	private MaterialLog generateOrderNOAndOutTime(MaterialLog materialLog){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if(materialLog.getOutTime() == null || StringUtils.isBlank(materialLog.getOutTime().toString()) ){			
			materialLog.setOutTime(timestamp);
		}
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		Integer id = selectMaxId();
		String orderNO = str + "000" + id;
		materialLog.setOrderNO(orderNO);
		return materialLog;
	}
	
	
	@Transactional
	public void update(MaterialLog materialLog) {
		materialLogMapper.update(materialLog);
		materialService.updateBound(materialLog);
	}

	@Transactional
	public void delete(MaterialLog materialLog) {
		materialLogMapper.deleteByIds(StringUtil.split(materialLog.getIds()));
	}

	public MaterialLog select(MaterialLog materialLog) {
		return materialLogMapper.select(materialLog);
	}

	@SuppressWarnings("unchecked")
	public Page<MaterialLog> getMaterialLogListPage(MaterialLog materialLog, int currentPage) {
		Page<MaterialLog> materialLogPage = new Page<MaterialLog>();
		materialLogPage.setCurrentPage(currentPage);
		materialLogPage = materialLogMapper.queryMaterialLogListPage(materialLogPage, materialLog);
		return materialLogPage;
	}

	@SuppressWarnings("unchecked")
	public Page<MaterialLog> getMaterialLogListPage(MaterialLog materialLog, int currentPage,int pageSize) {
		Page<MaterialLog> materialLogPage = new Page<MaterialLog>();
		materialLogPage.setCurrentPage(currentPage);
		materialLogPage.setSize(pageSize);
		materialLogPage = materialLogMapper.queryMaterialLogListPage(materialLogPage, materialLog);
		return materialLogPage;
	}
	
	public List<MaterialLog> queryList(MaterialLog materialLog) {
		return materialLogMapper.queryList(materialLog);
	}

	public Integer selectMaxId() throws DataAccessException {
		Integer id = materialLogMapper.selectMaxId();
		return id == null ? 1 : id;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(MaterialLog materialLog, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<MaterialLog> materialLog_list = materialLogMapper.queryList(materialLog);

		String[] fieldNames = new String[] {"单据编号  ","物料类型（电子 | 配件）", "出库|入库", "出库入库类型 ", "仓库的名字 ", "出入库日期 ", "数量", "收货方",
				"经手人 ", "物料型号 ", "平台", "物料编码", "物料名称 ", "物料规格 ", "位号", "用量 ", "M | T", "损耗 ", "批发价（￥）", "成本价（￥）", "工时费（￥）", "零售价（￥）", "厂商代码 ", "厂商名称 ", "备注 " };
		Map map = new HashMap();
		map.put("size", materialLog_list.size()+2);
		map.put("peList", materialLog_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("出入库管理列表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(
				request.getRealPath(GlobalCons.FREEMARKER)).append("/")
				.append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_MATERIAL)
				.append("MaterialLog.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,
				template, map);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		
		String importFlag=UUID.randomUUID().toString();
		
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<MaterialLog> successList = new ArrayList<MaterialLog>();// 保存可以插入成功的表的数据
		List<MaterialLog> matList = new ArrayList<MaterialLog>();//已改变物料数量的数据
		List<MaterialLogTemp> errorInfoList = new ArrayList<MaterialLogTemp>();// 保存可以插入失败的表的数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file, null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						MaterialLog ml = new MaterialLog();
						
						//MaterialLog对象的属性值:materialType[0]、totalType[1]、type[2]、depotName[3]、outTime[4]、number[5]、receivingParty[6]、clerk[7]、marketModel[8]、platform[9]、proNO[10]、
						//proName[11]、proSpeci[12]、placesNO[13]、consumption[14]、masterOrSlave[15]、loss[16]、tradePrice[17]、hourlyRates[18]、costPrice[19]、retailPrice[20]、
						//manufacturerCode[21]、manufacturer[22]、remark[23]
						
						Object[] obj = new Object[24];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2])|| StringUtils.isBlank((String)obj[3])
										|| StringUtils.isBlank((String)obj[4]) || StringUtils.isBlank((String)obj[5]) || StringUtils.isBlank((String)obj[6])
										|| StringUtils.isBlank((String)obj[10]) || StringUtils.isBlank((String)obj[11]) || StringUtils.isBlank((String)obj[12])
										|| StringUtils.isBlank((String)obj[14]) || StringUtils.isBlank((String)obj[15])) {
									//增加出入库导入管理表
									MaterialLogTemp ml2 = new MaterialLogTemp();
									ml2.setMaterialType(obj[0]);
									ml2.setTotalType(obj[1]);
									ml2.setType(obj[2]);
									ml2.setDepotName(obj[3]);
									ml2.setOutTime(obj[4]);
									ml2.setNumber(obj[5]);
									ml2.setReceivingParty(obj[6]);
									ml2.setClerk(obj[7]);
									ml2.setMarketModel(obj[8]);
									ml2.setPlatform(obj[9]);
									ml2.setProNO(obj[10]);
									ml2.setProName(obj[11]);
									ml2.setProSpeci(obj[12]);
									ml2.setPlacesNO(obj[13]);
									ml2.setConsumption(obj[14]);	
									ml2.setMasterOrSlave(obj[15]);
									ml2.setLoss(obj[16]);
									ml2.setTradePrice(obj[17]);
									ml2.setHourlyRates(obj[18]);
									ml2.setCostPrice(obj[19]);
									ml2.setRetailPrice(obj[20]);
									ml2.setManufacturerCode(obj[21]);
									ml2.setManufacturer(obj[22]);
									ml2.setRemark(obj[23]);
									ml2.setRowNO(j + 1);
									ml2.setImportDate(new Timestamp(System.currentTimeMillis()));//导入时间
									ml2.setImportPerson(getSessionUserName(request));
									ml2.setErrorInfo("第" + (j + 1) + "行导入错误,有必填数据未填写......");
									errorInfoList.add(ml2);
									materialLogTempMapper.insert(ml2);
								} else {
									// 入库的情况下，执行添加库存
									if ("入库".equals(((String) obj[1]).trim())) {
										ml.setTotalType(0);
									}else if("出库".equals(((String) obj[1]).trim())){
										ml.setTotalType(1);
									}else{
										ml.setTotalType(-1);
									}
									
									//生成出入库单据编号
									Date date = new Date(System.currentTimeMillis());
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									String str = sdf.format(date);
									Integer id = selectMaxId() + j - 2;
									String orderNO = str + "000" + id;
									ml.setOrderNO(orderNO);
									
									if("配件料".equals(obj[0].toString().trim()) || "配件".equals(obj[0].toString().trim())){
										ml.setMaterialType(0);
									}else if("电子料".equals(obj[0].toString().trim()) || "电子".equals(obj[0].toString().trim())){
										ml.setMaterialType(1);
									}else{
										ml.setMaterialType(-1);
									}
									
									if(!"M".equals(((String) obj[15]).trim().toUpperCase()) && !"T".equals(((String) obj[15]).trim().toUpperCase())){
										ml.setMasterOrSlave("T");
									}else{
										ml.setMasterOrSlave(((String) obj[15]).toUpperCase());;
									}
									
									ml.setType((String) obj[2]);
									ml.setDepotName((String) obj[3]);
									ml.setOutTime(DateUtil.getTimestampByStr((String)obj[4]));
									ml.setNumber(Integer.valueOf(((String)obj[5]).trim()));
									ml.setReceivingParty((String) obj[6]);
									ml.setClerk((String) obj[7]);
									ml.setMarketModel(((String) obj[8]).trim());
									ml.setPlatform((String) obj[9]);
									ml.setProNO(((String) obj[10]).trim());
									ml.setProName((String) obj[11]);
									ml.setProSpeci((String) obj[12]);
									ml.setPlacesNO((String) obj[13]);
									ml.setConsumption(Integer.valueOf(((String)obj[14]).trim()));	
									ml.setLoss((String) obj[16]);
									if(!StringUtil.isRealEmpty((String)obj[17])){
										ml.setTradePrice(BigDecimal.valueOf(Double.valueOf((String) obj[17])));
									}
									if(!StringUtil.isRealEmpty((String)obj[18])){
										ml.setHourlyRates(BigDecimal.valueOf(Double.valueOf((String) obj[18])));
									}
									if(!StringUtil.isRealEmpty((String)obj[19])){
										ml.setCostPrice(BigDecimal.valueOf(Double.valueOf((String) obj[19])));
									}
									if(!StringUtil.isRealEmpty((String)obj[20])){
										ml.setRetailPrice(BigDecimal.valueOf(Double.valueOf((String) obj[20])));
									}
									ml.setManufacturerCode((String) obj[21]);
									ml.setManufacturer((String) obj[22]);
									ml.setRemark((String) obj[23]);
									ml.setImportFlag(importFlag);//导入标识
									int business = -1;
									if(-1 != ml.getMaterialType()){
										if(0 == ml.getTotalType()){//入库
											business = materialService.inbound(ml);
										}else if(1 == ml.getTotalType()){//出库
											business = materialService.outbound(ml);
										}
									}
									if(business == 0){
										successList.add(ml);
										matList.add(ml);
										materialLogMapper.insert(ml);
									}else{
										MaterialLogTemp ml2 = new MaterialLogTemp();
										ml2.setMaterialType(obj[0]);
										ml2.setTotalType(obj[1]);
										ml2.setType(obj[2]);
										ml2.setDepotName(obj[3]);
										ml2.setOutTime(obj[4]);
										ml2.setNumber(obj[5]);
										ml2.setReceivingParty(obj[6]);
										ml2.setClerk(obj[7]);
										ml2.setMarketModel(obj[8]);
										ml2.setPlatform(obj[9]);
										ml2.setProNO(obj[10]);
										ml2.setProName(obj[11]);
										ml2.setProSpeci(obj[12]);
										ml2.setPlacesNO(obj[13]);
										ml2.setConsumption(obj[14]);	
										ml2.setMasterOrSlave(obj[15]);
										ml2.setLoss(obj[16]);
										ml2.setTradePrice(obj[17]);
										ml2.setHourlyRates(obj[18]);
										ml2.setCostPrice(obj[19]);
										ml2.setRetailPrice(obj[20]);
										ml2.setManufacturerCode(obj[21]);
										ml2.setManufacturer(obj[22]);
										ml2.setRemark(obj[23]);
										ml2.setRowNO(j + 1);
										ml2.setImportDate(new Timestamp(System.currentTimeMillis()));//导入时间
										ml2.setImportPerson(getSessionUserName(request));
										ml2.setErrorInfo("第" + (j + 1) + "行导入错误,修改物料信息失败!");
										errorInfoList.add(ml2);
										materialLogTempMapper.insert(ml2);
									}
								}
							} catch (Exception e) {
								log.error("导入错误：", e);
							}
						}
					}
					
					if (successList.size() > 0 ) {
						errorList.add("导入成功" + successList.size() + "条数据！！");
					}
					if(errorInfoList.size()>0){
						errorList.add("导入失败" + errorInfoList.size() + "条数据！！");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return errorList;
	}

	/*
	 * 获取当前登录用户
	 */
	protected String getSessionUserName(HttpServletRequest req) {
		UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
		if(null !=user) return user.getuName(); else return null;
	}
	
	/**
	 * 获取出入库状态具体描述原因
	 * @param state
	 * @return String
	 */
	private String getStateDescribe(int state){
		switch (state) {
		case 1:
			return "缺少关键参数";
		case 2:
			return "库存不足，无法出库";
		case 3:
			return "无法根据物料编码和物料型号找到对应物料信息";
		default:
			return null;
		}
	}
	
	
	/**
	 * API 接口，批量处理售后出库
	 * 		针对终检成功后，发送装箱调用
	 * @param ids 公共表主键ID集合
	 * @param clerk 经手人
	 * @throws Exception 出库异常

	 */
	public void afterOutBound(String... ids) throws Exception{
		//出库配件料
		for(String id:ids){
			Workflow workflow = workflowMapper.getT(Integer.parseInt(id));
			WorkflowRepair repair = workflowRepairMapper.getRepairByWfId(Integer.parseInt(id));
			Sxdwmanage sxdwmanage = sxdwmanageMapper.getT(Convert.getInteger(workflow.getSxdwId().toString()));
			WorkflowRelated related = workflowRelatedMapper.getT(workflow.getRfild());
			String[] marrys = StringUtil.split(related.getMatDesc());
			for(String m:marrys){
				Pjlmanage pjl = pjlmanageMapper.getT(Integer.valueOf(m));//获得配件料编码
				if(null !=pjl && null != pjl.getProNO() && null != pjl.getMasterOrSlave()){
					int result = outboundHandle(pjl.getProNO(), pjl.getMasterOrSlave(), sxdwmanage.getCusName(), repair.getEngineer());
					if(0 != result){
						log.error("配件料出库失败：" + result);
						throw new Exception("配件料出库失败：" + result);
					}
				}else{
					log.error("配件料出库失败：无此配件料");
					throw new Exception("配件料出库失败：无此配件料");
				}
			}
			
			//出库电子料
			String[] earrys = StringUtil.split(related.getDzlDesc());
			for(String e:earrys){
				Dzlmanage dzl = dzlmanageMapper.getT(Integer.valueOf(e));//获得电子料编码
				if(null != dzl && null != dzl.getProNO() && null != dzl.getMasterOrSlave()){
					int result = outboundHandle(dzl.getProNO(), dzl.getMasterOrSlave(), sxdwmanage.getCusName(), repair.getEngineer());
					if(0 != result){
						log.error("电子料出库失败：" + result);
						throw new Exception("电子料出库失败：" + result);
					}
				}else{
					log.error("电子料出库失败：无此电子料");
					throw new Exception("电子料出库失败：无此电子料");
				}
			}
		}
	}
	
	/**
	 * 批次配件料出库
	 * @param p
	 * @throws Exception
	 */
	public void outBoundPj(PricePj p) throws Exception{
		//出库配件料
		if(null != p){
			int result = outboundHandlePj(p);
			if(0 != result){
				log.error("配件料出库失败：" + result);
				throw new Exception("配件料出库失败：" + result);
			}
		}
	}	
	
	/**
	 * 批次配件料出库
	 * @param p
	 * @return
	 */
	@Transactional
	public int outboundHandlePj(PricePj p){
		int boundStatu;
		Pjlmanage pjl = pjlmanageMapper.getT(p.getMid());
		
		Material inventory = materialMapper.getInfoByProNOAndMasterOrSlave(pjl.getProNO(), pjl.getMasterOrSlave());
		if(null != inventory){
			MaterialLog materialLog = new MaterialLog();
			generateOrderNOAndOutTime(materialLog);
			materialLog.setTotalType(1);
			materialLog.setType("康凯斯售后系统（报价批次）出库");
			materialLog.setDepotName(inventory.getDepot());
			materialLog.setProSpeci(inventory.getProSpeci());
			materialLog.setConsumption(inventory.getConsumption());
			materialLog.setMasterOrSlave(inventory.getMasterOrSlave());
			materialLog.setLoss(inventory.getLossType());
			materialLog.setNumber(p.getPjNumber());//配件料数量
			materialLog.setProNO(inventory.getProNO());
			materialLog.setProName(inventory.getProName());
			materialLog.setMarketModel(pjl.getMarketModel());
			materialLog.setMaterialType(0);
			materialLog.setReceivingParty(p.getCusName());
			materialLog.setClerk(p.getOffer());
			materialLog.setRetailPrice(p.getRetailPrice());
			boundStatu = materialService.outbound(materialLog);
			if (0 == boundStatu) {
				materialLogMapper.insert(materialLog);
			}
		}else{
			boundStatu = 3;//找不到对应的库存物料
		}
		return boundStatu;
	}
	
	/**
	 * 销售配件料出库
	 * @param p
	 * @throws Exception
	 */
	public void outBoundXSPj(XspjcostsReport xspj) throws Exception{
		//出库配件料
		if(null != xspj){
			if(null != xspj.getGroupPjId()){
				List<GroupPjDetails> gList = groupPjDetailsMapper.getGroupPjDetailsListByGroupId(xspj.getGroupPjId()); 
				if(gList.size() > 0){
					Integer number =xspj.getNumber();
					for (GroupPjDetails g : gList) {
						if(null != g.getProNumber()){
							xspj.setNumber(number *  g.getProNumber());
							xspj.setProNO(g.getProNO());
							xspj.setMasterOrSlave(g.getMasterOrSlave());
							xspj.setUnitPrice(g.getRetailPrice());
							int result = outboundHandleXSPj(xspj);
							if(0 != result){
								log.error("配件料出库失败：" + result);
								throw new Exception("配件料出库失败：" + result);
							}
						}
					}
				}
			}else{
				int result = outboundHandleXSPj(xspj);
				if(0 != result){
					log.error("配件料出库失败：" + result);
					throw new Exception("配件料出库失败：" + result);
				}
			}
		}
	}	
	
	/**
	 * 销售配件料出库
	 * @param p
	 * @return
	 */
	@Transactional
	public int outboundHandleXSPj(XspjcostsReport x){
		int boundStatu;
		Material inventory = materialMapper.getInfoByProNOAndMasterOrSlave(x.getProNO(), x.getMasterOrSlave());
		if(null != inventory){
			MaterialLog materialLog = new MaterialLog();
			generateOrderNOAndOutTime(materialLog);
			materialLog.setTotalType(1);
			materialLog.setType("康凯斯售后系统（销售）出库");
			materialLog.setDepotName(inventory.getDepot());
			materialLog.setProSpeci(inventory.getProSpeci());
			materialLog.setConsumption(inventory.getConsumption());
			materialLog.setMasterOrSlave(inventory.getMasterOrSlave());
			materialLog.setLoss(inventory.getLossType());
			materialLog.setNumber(x.getNumber());//配件料数量
			materialLog.setProNO(inventory.getProNO());
			materialLog.setProName(inventory.getProName());
			materialLog.setMarketModel(x.getMarketModel());
			materialLog.setMaterialType(0);
			materialLog.setReceivingParty(x.getCusName());
			materialLog.setRetailPrice(x.getUnitPrice());
			materialLog.setClerk(x.getCreateBy());//创建人
			boundStatu = materialService.outbound(materialLog);
			if (0 == boundStatu) {
				materialLogMapper.insert(materialLog);
			}
		}else{
			boundStatu = 3;//找不到对应的库存物料
		}
		return boundStatu;
	}


	
	/**
	 * API 接口，提供出库处理
	 * @param proNO		                       物料编码
	 * @param masterOrSlave		    M|T
	 * @param receivingParty	收货人
	 * @param clerk				经手人
	 * @return int 			0:出库成功，1：传参错误，2：库存不足，无法出库，3：找不对到对应的机型
	 */
	@Transactional
	public int outboundHandle(String proNO, String masterOrSlave, String receivingParty, String clerk){
		int boundStatu;
		
		Material inventory = materialMapper.getInfoByProNOAndMasterOrSlave(proNO, masterOrSlave);
		Pjlmanage p = pjlmanageMapper.getPjlByProNO(proNO);
		Dzlmanage d = dzlmanageMapper.getDzlByProNO(proNO);
		if(null != inventory){
			MaterialLog materialLog = new MaterialLog();
			generateOrderNOAndOutTime(materialLog);
			materialLog.setTotalType(1);
			materialLog.setType("康凯斯售后系统（维修）出库");
			materialLog.setDepotName(inventory.getDepot());
			materialLog.setProSpeci(inventory.getProSpeci());
			materialLog.setConsumption(inventory.getConsumption());
			materialLog.setMasterOrSlave(inventory.getMasterOrSlave());
			materialLog.setLoss(inventory.getLossType());
			materialLog.setNumber(inventory.getConsumption() == null ? 0 : inventory.getConsumption());
			materialLog.setProNO(inventory.getProNO());
			materialLog.setProName(inventory.getProName());
			if(null != p && null != p.getMarketModel()){
				materialLog.setMarketModel(p.getMarketModel());
				materialLog.setRetailPrice(p.getRetailPrice());
			}
			if(null != d && null != d.getPlacesNO()){
				materialLog.setPlacesNO(d.getPlacesNO());
			}
			materialLog.setMaterialType(inventory.getMaterialType());
			materialLog.setReceivingParty(receivingParty);
			materialLog.setClerk(clerk);
			boundStatu = materialService.outbound(materialLog);
			if (0 == boundStatu) {
				materialLogMapper.insert(materialLog);
			}
		}else{
			boundStatu = 3;//找不到对应的库存物料
		}
		return boundStatu;
	}
}

