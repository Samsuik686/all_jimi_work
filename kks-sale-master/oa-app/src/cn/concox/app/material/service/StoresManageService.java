package cn.concox.app.material.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.StoresManageMapper;
import cn.concox.vo.material.StoresManage;

/**
 * <pre>
 * 仓库表业务接口
 * </pre>
 * @author Li.Bifeng 
 * @version v1.0
 */
@Service("storesManageService")
@Scope("prototype")
public class StoresManageService {
	@Resource(name="storesManageMapper")
	private  StoresManageMapper<StoresManage> storesManageMapper;
	
	public void insert(StoresManage storesManage){
		storesManageMapper.insert(storesManage);
	}
	
	public void  update(StoresManage storesManage){
		storesManageMapper.update(storesManage);
	}
	
	public void  delete(StoresManage storesManage){
		storesManageMapper.delete(storesManage);
	}
	public StoresManage  select (StoresManage storesManage){
	    return	storesManageMapper.select(storesManage);
	}
	
	@SuppressWarnings("unchecked")
	public Page<StoresManage> getCjgzmanageListPage(StoresManage storesManage,int currentPage){
		Page<StoresManage> storesManagePage = new Page<StoresManage>();
		storesManagePage.setCurrentPage(currentPage);
		storesManagePage =storesManageMapper.queryStoresManageListPage(storesManagePage,storesManage);
		return storesManagePage;
	} 
	
	public List<StoresManage> queryList(StoresManage storesManage){
		return storesManageMapper.queryList(storesManage);
	}
}
