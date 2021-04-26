/*
 * Created: 2016-8-10
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
 */
package cn.concox.app.material.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.DzlmanageMapper;
import cn.concox.app.basicdata.mapper.PjlmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.material.mapper.MaterialLogMapper;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Dzlmanage;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.material.Material;
import cn.concox.vo.material.MaterialLog;
import freemarker.template.Template;

@Service("materialService")
@Scope("prototype")
public class MaterialService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@Resource(name = "materialLogMapper")
	private MaterialLogMapper<MaterialLog> materialLogMapper;
	
	@Resource(name="pjlmanageMapper")
	private PjlmanageMapper<Pjlmanage> pjlmanageMapper;
	
	@Resource(name="dzlmanageMapper")
	private DzlmanageMapper<Dzlmanage> dzlmanageMapper;

	/**
	 * 获取物料统计数据
	 * <p>
	 * 分页
	 * </P>
	 * 
	 * @param Material.proNO 物料编码
	 * @param Material.proName 物料名称
	 * @param currentPage 当前页数
	 * @param pageSize 每页数量
	 * @return  Page<Material>
	 */
	public Page<Material> queryListPage(Material material, int currentPage,int pageSize) {
		Page<Material> materials = new Page<Material>();
		materials.setCurrentPage(currentPage);
		materials.setSize(pageSize);
		materials = materialMapper.queryListPage(materials, material);
		return materials;
	}

	/**
	 * 获取物料数据
	 * <p>
	 * 不分页
	 * </P>
	 * 
	 * @param Material.proNO 物料编码
	 * @param Material.proName 物料名称
	 * @param Material.placesNO 物料序号
	 * 
	 * @return
	 */
	public List<Material> queryList(Material material) {
		return materialMapper.queryList(material);
	}
	
	public void update(Material material){
		material.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		materialMapper.update(material);
//		if(material.getMaterialType() == 0){
//			Pjlmanage pjl = pjlmanageMapper.getPjlByProNO(material.getProNO());
//			if(null != pjl && null != pjl.getPjlId()){
//				pjl.setRetailPrice(material.getRetailPrice());
//				pjl.setMasterOrSlave(material.getMasterOrSlave());
//				pjlmanageMapper.update(pjl);//更新配件料价格和M|T
//			}
//		}else if(material.getMaterialType() == 1){
//			Dzlmanage dzl = dzlmanageMapper.getDzlByProNO(material.getProNO());
//			if(null != dzl && null != dzl.getDzlId()){
//				dzl.setProNO(material.getProNO());
//				dzl.setMasterOrSlave(material.getMasterOrSlave());
//				dzlmanageMapper.update(dzl);//更新电子料M|T
//			}
//		}
	}
	
	//批次报价增加配件料
	public Material getInfo(Material material) {
		return materialMapper.getT(material.getId());
	}
	
	/**
	 * 生成物料需求单
	 * 
	 * @param
	 * 
	 * @return
	 */
	public Page<Material> getOrderListPage(Material material,int currentPage,int pageSize) {
		Page<Material> materials = new Page<Material>();
		materials.setCurrentPage(currentPage);
		materials.setSize(pageSize);
		materials = materialMapper.getOrderListPage(materials, material);
		return materials;
	}

	/**
	 * 导出物料统计
	 * 
	 * @param material
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportMaterailStatisticsDatas(Material material, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Material> materials = materialMapper.queryList(material);

		String[] fieldNames = new String[] {"物料类型", "物料编号", "物料名称", "规格", "M | T", "损耗", "用量", "数量", "零售价(￥)", "备注"};
		Map map = new HashMap();
		map.put("size", materials.size()+2);
		map.put("peList", materials);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("物料信息管理列表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(
				request.getRealPath(GlobalCons.FREEMARKER)).append("/")
				.append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_MATERIAL)
				.append("Materials.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	/**
	 * 导出物料需求单
	 * 
	 * @param material
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({"unchecked", "rawtypes", "deprecation" })
	public void ExportMaterialOrderDatas(Material material, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Material> materials = materialMapper.getOrderList(material);

		String[] fieldNames = new String[] {"物料类型", "物料编号", "物料名称", "物料规格", "需求数量" };
		Map map = new HashMap();
		map.put("size", materials.size()+2);
		map.put("peList", materials);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("物料需求单").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(
				request.getRealPath(GlobalCons.FREEMARKER)).append("/")
				.append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_MATERIAL)
				.append("MaterialOrders.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	/**
	 * API 物料入库操作
	 * 
	 * @param materialLog.marketModel 机型号
	 * @param materialLog.proNO 物料编号 ....
	 * @return int 0:插入成功，1：传参错误 2：物料不存在
	 */
	@Transactional
	public int inbound(MaterialLog ml) {
		if (null != ml && !StringUtil.isEmpty(ml.getProNO()) && null != ml.getNumber() && !StringUtil.isEmpty(ml.getMasterOrSlave())) {
			Material material = materialMapper.getInfoByProNOAndMasterOrSlave(ml.getProNO(), ml.getMasterOrSlave());
			if(null != material && null != material.getProNO()){
				material = createMaterial(ml, material);
				material.setTotalNum(material.getTotalNum().intValue()*1 + ml.getNumber().intValue()*1);
				material.setUpdateTime(new Timestamp(DateTimeUtil.now().getTime()));
				materialMapper.update(material);
			}else{
				Material mat = new Material();
				material = createMaterial(ml, mat);
				material.setTotalNum(ml.getNumber().intValue()*1);
				material.setCreateTime(new Timestamp(DateTimeUtil.now().getTime()));
				materialMapper.insert(material); 
			}
			return 0; // TODO 插入成功
		} else {
			return 1; // TODO 参数错误
		}
	}

	/**
	 * API 物料出库操作， 目前不对库存数量进行验证操作
	 * 	
	 * @param MaterialLog.orderNo 出库唯一标识
	 *            
	 * @param MaterialLog.materialType 物料类型
	 *          
	 * @param MaterialLog.marketModel 出库机型
	 *            
	 * @param MaterialLog.proNO 出库物料编号
	 *            
	 * @param MaterialLog.number 出库数量
	 *            
	 * @param MaterialLog.remark 备注
	 * 	 
	 * @return int 0:出库成功，1：传参错误，2：物料不存在
	 */
	public int outbound(MaterialLog ml) {
		if (null != ml && !StringUtil.isEmpty(ml.getProNO()) && !StringUtil.isEmpty(ml.getMasterOrSlave())) {
			Material material = materialMapper.getInfoByProNOAndMasterOrSlave(ml.getProNO(), ml.getMasterOrSlave());
			if(null != material && null != material.getProNO()){
				material = createMaterial(ml, material);
				material.setTotalNum(material.getTotalNum().intValue()*1 + ml.getNumber().intValue()* -1);
				material.setRemark(ml.getRemark());
				material.setUpdateTime(new Timestamp(DateTimeUtil.now().getTime()));
				materialMapper.update(material);
			}else{
				Material mat = new Material();
				material = createMaterial(ml, mat);
				material.setTotalNum(ml.getNumber().intValue()*1);
				material.setCreateTime(new Timestamp(DateTimeUtil.now().getTime()));
				materialMapper.insert(material); 
			}
			return 0; // TODO 修改成功
		}
		return 1; // TODO 参数错误
	}

	private Material createMaterial(MaterialLog ml, Material material) {
		material.setMaterialType(ml.getMaterialType());
		material.setProNO(ml.getProNO());
		material.setLossType(ml.getLoss());
		material.setProName(ml.getProName());
		material.setProSpeci(ml.getProSpeci());
		material.setConsumption(ml.getConsumption());
		material.setMasterOrSlave(ml.getMasterOrSlave());
		return material;

	}
	
	/**
	 * 修改出入库（是否改变出|入库，是否改变数量）不能重新生成单号
	 * @param ml
	 * @return
	 */
	@Transactional
	public int updateBound(MaterialLog ml) {
		if (null != ml && !StringUtil.isEmpty(ml.getProNO()) && null != ml.getNumber() && !StringUtil.isEmpty(ml.getMasterOrSlave())) {
			Material material = materialMapper.getInfoByProNOAndMasterOrSlave(ml.getProNO(), ml.getMasterOrSlave());
			if(null != material && null != material.getProNO()){
				material.setMaterialType(ml.getMaterialType());
				material.setTotalNum(material.getTotalNum().intValue()*1 + ml.getErrorNum().intValue()*1);
				material.setProNO(ml.getProNO());
				material.setLossType(ml.getLoss());
				material.setProName(ml.getProName());
				material.setProSpeci(ml.getProSpeci());
				material.setMasterOrSlave(ml.getMasterOrSlave());
				material.setUpdateTime(new Timestamp(DateTimeUtil.now().getTime()));
				materialMapper.update(material);
			}
			return 0; // TODO 修改成功
		} else {
			return 1; // TODO 参数错误
		}
	}


    public void saveMaterial(Material material) {
        materialMapper.insert(material);
    }
}
