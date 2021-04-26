package cn.concox.app.basicdata.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.PriceGroupPjDetailsMapper;
import cn.concox.app.basicdata.mapper.PriceGroupPjMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.vo.basicdata.PriceGroupPj;
import cn.concox.vo.basicdata.PriceGroupPjDetails;
import cn.concox.vo.material.Material;
/**
 * 报价配件料组合详情 业务层
 */
@Service("priceGroupPjDetailsService")
@Scope("prototype")
public class PriceGroupPjDetailsService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="priceGroupPjDetailsMapper")
	private PriceGroupPjDetailsMapper<PriceGroupPjDetails> priceGroupPjDetailsMapper;
	
	@Resource(name="priceGroupPjMapper")
	private PriceGroupPjMapper<PriceGroupPj> priceGroupPjMapper;

	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@SuppressWarnings("unchecked")
	public Page<PriceGroupPjDetails> getPriceGroupPjDetailsListPage(PriceGroupPjDetails groupPjDetails, int currentPage, int pageSize){
		Page<PriceGroupPjDetails> groupPjDetailsPage = new Page<PriceGroupPjDetails>();
		groupPjDetailsPage.setCurrentPage(currentPage);
		groupPjDetailsPage.setSize(pageSize);
		groupPjDetailsPage = priceGroupPjDetailsMapper.getPriceGroupPjDetailsList(groupPjDetailsPage, groupPjDetails);
		return groupPjDetailsPage;
	} 
	
	public List<PriceGroupPjDetails> getPriceGroupPjDetailsList(PriceGroupPjDetails groupPjDetails){
		 List<PriceGroupPjDetails> groupPjDetailsList = priceGroupPjDetailsMapper.getPriceGroupPjDetailsList(groupPjDetails);
		return groupPjDetailsList;
	} 
	
	public void deletePriceGroupPjDetailsInfo(String... ids){
		priceGroupPjDetailsMapper.deleteInfo(ids);
	} 
	
	public void insertPriceGroupPjDetailsInfo(PriceGroupPjDetails groupPjDetails){
		priceGroupPjDetailsMapper.insert(groupPjDetails);
	}
	
	public void updatePriceGroupPjDetailsInfo(PriceGroupPjDetails groupPjDetails){
		priceGroupPjDetailsMapper.update(groupPjDetails);
		if(null != groupPjDetails && null != groupPjDetails.getGroupId() && groupPjDetails.getGroupId() != 0){
			PriceGroupPj groupPj = priceGroupPjMapper.getT(groupPjDetails.getGroupId());
			BigDecimal price =	priceGroupPjDetailsMapper.getPriceByGroupId(groupPjDetails.getGroupId());
			if(null != groupPj){
				groupPj.setGroupPrice(price);
				priceGroupPjMapper.update(groupPj);
			}
		}
	}
	
	public PriceGroupPjDetails getPriceGroupPjDetailsInfo(PriceGroupPjDetails groupPjDetails){ 
		return priceGroupPjDetailsMapper.getT(groupPjDetails.getId());
	}

	public int isExists(PriceGroupPjDetails groupPjDetails){
		return priceGroupPjDetailsMapper.isExists(groupPjDetails);
	}
	
	public int findCountForGroupPj(String groupId, String id) {
		return priceGroupPjDetailsMapper.findCountForGroupPj(groupId, id);
	}
	
	public void deleteInfoIsNull(Integer groupId){
		priceGroupPjDetailsMapper.deleteInfoIsNull(groupId);
	} 
	
	public void updateGroupIdByIds(String groupId, String ... ids){
		priceGroupPjDetailsMapper.updateGroupIdByIds(groupId, ids);
	}
	
	public void deleteByGroupId(Integer groupId){
		priceGroupPjDetailsMapper.deleteByGroupId(groupId);
	}
	
	public List<PriceGroupPjDetails> getPjlByGroupIds(String... ids){
		return priceGroupPjDetailsMapper.getPjlByGroupIds(ids);
	}
}