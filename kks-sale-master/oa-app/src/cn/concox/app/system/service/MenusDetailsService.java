package cn.concox.app.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.system.mapper.MenusDetailsMapper;
import cn.concox.vo.system.MenuDetails;




@Service("menusDetailsService")
@Scope("prototype")
public class MenusDetailsService {
	
	@Resource(name="menusDetailsMapper")
    private MenusDetailsMapper<MenuDetails> menusDetailsMapper;
	
	public List<MenuDetails> queryall(){
		List<MenuDetails> list=menusDetailsMapper.queryList(null);
		return list;
	}
	
	public List<MenuDetails> queryMenusDetailsByMenuId(Integer menuId){
		MenuDetails med=new MenuDetails();
		med.setMenuId(menuId);
		List<MenuDetails> list=menusDetailsMapper.queryList(med);
		return list;
	}
	
	public List<MenuDetails> queryMenusDetailsByFunId(Integer funId){
		MenuDetails med=new MenuDetails();
		med.setFunctionId(funId);
		List<MenuDetails> list=menusDetailsMapper.queryList(med);
		return list;
	}
	
	public void insertMenusDetails(MenuDetails med)
	{
		menusDetailsMapper.insert(med);
	}
	
	public void deleteMenusDetails(Integer funId)
	{
		MenuDetails med=new MenuDetails();
		med.setFunctionId(funId);
		menusDetailsMapper.delete(med);
	}
	
	public void deleteByMenuId(MenuDetails med){
		menusDetailsMapper.deleteByMenuId(med);
	}
}
