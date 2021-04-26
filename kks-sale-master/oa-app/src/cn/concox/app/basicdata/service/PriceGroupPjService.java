package cn.concox.app.basicdata.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.PjlmanageMapper;
import cn.concox.app.basicdata.mapper.PriceGroupPjKeyMapper;
import cn.concox.app.basicdata.mapper.PriceGroupPjMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.PriceGroupPj;
import cn.concox.vo.basicdata.PriceGroupPjKey;
import cn.concox.vo.material.Material;
/**
 * 报价配件料组合 业务层
 */
@Service("priceGroupPjService")
@Scope("prototype")
public class PriceGroupPjService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="priceGroupPjMapper")
	private PriceGroupPjMapper<PriceGroupPj> priceGroupPjMapper;

	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@Resource(name="pjlmanageMapper")
	private PjlmanageMapper<Pjlmanage> pjlmanageMapper;
	
	@Resource(name="priceGroupPjKeyMapper")
	private PriceGroupPjKeyMapper<PriceGroupPjKey> priceGroupPjKeyMapper;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@SuppressWarnings("unchecked")
	public Page<PriceGroupPj> getPriceGroupPjList(PriceGroupPj groupPj, int currentPage, int pageSize){
		Page<PriceGroupPj> groupPjsPage = new Page<PriceGroupPj>();
		groupPjsPage.setCurrentPage(currentPage);
		groupPjsPage.setSize(pageSize);
		groupPjsPage = priceGroupPjMapper.getPriceGroupPjList(groupPjsPage, groupPj);
		return groupPjsPage;
	} 
	
	public void deletePriceGroupPjInfo(PriceGroupPj groupPj){
		priceGroupPjMapper.delete(groupPj);
	} 
	
	public void insertPriceGroupPjInfo(PriceGroupPj groupPj){
		priceGroupPjMapper.insert(groupPj);
	}
	
	public void updatePriceGroupPjInfo(PriceGroupPj groupPj){
		priceGroupPjMapper.update(groupPj);
	}

	/**
	 * @Title: getPriceGroupPjInfo 
	 * @Description:获取单个配件料组合
	 * @param groupPj
	 * @return 
	 * @author 20160308
	 * @date 2017年11月16日 下午4:08:12
	 */
	public PriceGroupPj getPriceGroupPjInfo(PriceGroupPj groupPj){
		return priceGroupPjMapper.getT(groupPj.getGroupPjId());
	}

	public int isExists(PriceGroupPj groupPj){
		return priceGroupPjMapper.isExists(groupPj);
	}

	public int checkSupportDel(PriceGroupPj groupPj) {
		return priceGroupPjMapper.findCountByGroupPjId(groupPj);
	}
	
	/**
	 * @Title: getPjlIdsByProName 
	 * @Description: 获取所有物料名称模糊匹配的配件料id,并保存
	 * @param model
	 * @param marketModel
	 * @param keyDesc
	 * @return 
	 * @author 20160308
	 * @date 2017年11月20日 上午10:02:54
	 */
	@Transactional
	public void getPjlIdsByKeyDesc(String model, String marketModel, String keyDesc, String keyType){
		if(!StringUtil.isRealEmpty(model)){
			autoAdd(model, marketModel, keyDesc, keyType);
		}else{
			String models = pjlmanageMapper.getAllPjlModels();
			if(!StringUtil.isRealEmpty(models)){
				String[] ms = models.split(",");
				for (String s : ms) {
					if(!StringUtil.isRealEmpty(s)){
						autoAdd(s, marketModel, keyDesc, keyType);
					}
				}
			}
		}
	}

	/**
	 * @Title: autoAdd 
	 * @Description:自动生成报价组合
	 * @param model
	 * @param marketModel
	 * @param keyDesc 
	 * @author 20160308
	 * @date 2017年11月20日 上午11:42:18
	 */
	private void autoAdd(String model, String marketModel, String keyDesc, String keyType) {
		if(!StringUtil.isRealEmpty(keyDesc)){
			String[] proNames = keyDesc.split(",");
			if(proNames != null && proNames.length > 0){
				addPriceGroupPjs(model, marketModel, keyType, keyDesc, proNames);
			}
		}else{
			PriceGroupPjKey entity = new PriceGroupPjKey();
			List<PriceGroupPjKey> list = priceGroupPjKeyMapper.queryList(entity);
			if(list != null && list.size() > 0){
				for (PriceGroupPjKey p : list) {
					if(p != null && !StringUtil.isRealEmpty(p.getKeyDesc())){
						String[] s = p.getKeyDesc().split(",");
						if(s != null && s.length > 0){
							if(!"通用".equals(keyType)){
								if(p.getKeyType().equals(keyType)){
									addPriceGroupPjs(model, marketModel, keyType, p.getKeyDesc(), s);
								}
							}else{
								addPriceGroupPjs(model, marketModel, p.getKeyType(), p.getKeyDesc(), s);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @Title: addPriceGroupPjs 
	 * @Description:增加所有组合报价
	 * @param model
	 * @param marketModel
	 * @param keyDesc
	 * @param proNames 
	 * @author 20160308
	 * @date 2017年11月20日 上午11:41:00
	 */
	private void addPriceGroupPjs(String model, String marketModel, String keyType, String keyDesc, String[] proNames) {
		String ids = getPjlIdsByProName(model, marketModel, keyType, proNames);
		if(!StringUtil.isRealEmpty(ids)){
			PriceGroupPj groupPj = new PriceGroupPj();
			groupPj.setPjlDesc(ids);
			groupPj.setGroupName("【" + keyType + "】" + "【" + model + "】" + keyDesc);
			groupPj.setModel(model);
			groupPj.setPjlPrice(BigDecimal.ZERO);
			if(priceGroupPjMapper.isExists(groupPj) == 0){
				priceGroupPjMapper.insert(groupPj);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getPjlIdsByProName(String model, String marketModel, String keyType, String[] proNames) {
		StringBuilder sb = new StringBuilder();
		Set idSet = new HashSet();// id
		for (String s : proNames) {
			if(!StringUtil.isRealEmpty(s)){
				String tempids = priceGroupPjKeyMapper.getPjlIdsByProName(model, marketModel, keyType, s);
				if(!StringUtil.isRealEmpty(tempids)){
					String[] ids = tempids.split(",");
					if(ids != null && ids.length > 0){
						for (String str : ids) {
							if(!StringUtil.isRealEmpty(str)){
								if(idSet.add(str)){
									if(sb == null || sb.length() == 0){
										sb.append(str);
									}else{
										sb.append(",").append(str);
									}
								}
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * @Title: getPjlIdsByGroupPjIds 
	 * @Description:维修报价选择组合配件料
	 * @param ids 报价配件料组合id
	 * @return 
	 * @author 20160308
	 * @date 2017年11月22日 上午10:32:48
	 */
	public List<Pjlmanage> getPjlIdsByGroupPjIds(String... ids){
		return priceGroupPjMapper.getPjlIdsByGroupPjIds(ids);
	}	
}