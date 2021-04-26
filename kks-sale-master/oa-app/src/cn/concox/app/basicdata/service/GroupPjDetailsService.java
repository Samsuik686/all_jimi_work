package cn.concox.app.basicdata.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.GroupPjDetailsMapper;
import cn.concox.app.basicdata.mapper.GroupPjMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.vo.basicdata.GroupPj;
import cn.concox.vo.basicdata.GroupPjDetails;
import cn.concox.vo.material.Material;
/**
 * 配件料 业务层
 */
@Service("groupPjDetailsService")
@Scope("prototype")
public class GroupPjDetailsService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="groupPjDetailsMapper")
	private GroupPjDetailsMapper<GroupPjDetails> groupPjDetailsMapper;
	
	@Resource(name="groupPjMapper")
	private GroupPjMapper<GroupPj> groupPjMapper;

	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@SuppressWarnings("unchecked")
	public Page<GroupPjDetails> getGroupPjDetailsList(GroupPjDetails groupPjDetails, int currentPage, int pageSize){
		Page<GroupPjDetails> groupPjDetailssPage = new Page<GroupPjDetails>();
		groupPjDetailssPage.setCurrentPage(currentPage);
		groupPjDetailssPage.setSize(pageSize);
		groupPjDetailssPage = groupPjDetailsMapper.getGroupPjDetailsList(groupPjDetailssPage, groupPjDetails);
		return groupPjDetailssPage;
	} 
	
	public void deleteGroupPjDetailsInfo(String... ids){
		groupPjDetailsMapper.deleteInfo(ids);
	} 
	
	public void insertGroupPjDetailsInfo(GroupPjDetails groupPjDetails){
		groupPjDetailsMapper.insert(groupPjDetails);
	}
	
	public void updateGroupPjDetailsInfo(GroupPjDetails groupPjDetails){
		groupPjDetailsMapper.update(groupPjDetails);
		if(null != groupPjDetails && null != groupPjDetails.getGroupId() && groupPjDetails.getGroupId() != 0){
			GroupPj groupPj = groupPjMapper.getT(groupPjDetails.getGroupId());
			BigDecimal price =	groupPjDetailsMapper.getPriceByGroupId(groupPjDetails.getGroupId());
			if(null != groupPj){
				groupPj.setGroupPrice(price);
				groupPjMapper.update(groupPj);
			}
		}
	}
	
	public GroupPjDetails getGroupPjDetailsInfo(GroupPjDetails groupPjDetails){ 
		return groupPjDetailsMapper.getT(groupPjDetails.getId());
	}

	public int isExists(GroupPjDetails groupPjDetails){
		return groupPjDetailsMapper.isExists(groupPjDetails);
	}
	
	public int findCountForGroupPj(String groupId, String id) {
		return groupPjDetailsMapper.findCountForGroupPj(groupId, id);
	}
	
	public void deleteInfoIsNull(Integer groupId){
		groupPjDetailsMapper.deleteInfoIsNull(groupId);
	} 
	
	public void updateGroupIdByIds(String groupId, String ... ids){
		groupPjDetailsMapper.updateGroupIdByIds(groupId, ids);
	}
	
	public void deleteByGroupId(Integer groupId){
		groupPjDetailsMapper.deleteByGroupId(groupId);
	}
}