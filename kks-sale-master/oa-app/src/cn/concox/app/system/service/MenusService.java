package cn.concox.app.system.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.system.mapper.MenusMapper;
import cn.concox.vo.system.Menus;


@Service("menusService")
@Scope("prototype")
public class MenusService{
	
@Resource(name="menusMapper")
private MenusMapper<Menus> menusMapper;


	/**
	 * mybatis基本查询例子
	 * @author lizhongjie
	 * create date 2013-9-10
	 * @return
	 */
	public List<Menus> queryMenus(){
		Menus men=new Menus();
		men.setParentId(0);
		
		List<Menus> list=menusMapper.queryList(men);
		
		/*
		for (Menus menus : list) {
			System.out.println(menus.getDisplayName());
			System.out.println(menus.getDescrp());
			System.out.println(menus.getUrl());
		}*/
		return list;
	}
	
	/**
	 * 根据UId查询用户对应的权限FunctionURL
	 */
	public List<String> queryUserFunctionURLsByUId(String uId){
		Map map=new HashMap();
		map.put("uId",uId);
		return menusMapper.queryUserFunctionURLsByUId(map);
	}
	
	/**
	 * 获取公共请求的URI
	 * @return
	 */
	public List<String> queryCommonFunctionURL(){
		return menusMapper.queryCommonFunctionURL();
	}
	
	
	/**
	 * 根据uId查询用户的一级菜单列表
	 * @return
	 */
	public List<Menus> queryParentMenuListByUId(String uId){
		Map map=new HashMap();
		map.put("uId",uId);
		List<Menus> list=menusMapper.queryParentMenuListByUId(map);
		return list;
	}
	
	/**
	 * 查询所有的FunctionURL
	 */
	public List<String> queryAllFunctionURL(){
		return menusMapper.queryAllFunctionURL();
	}
	
	/**
	 * 根据uId查询用户的二级菜单列表
	 * @return
	 */
	public List<Menus> queryChildMenusListByUIDAndParentMenuIds(String uId,int parentMenuId){
		Map map=new HashMap();
		map.put("uId",uId);
		map.put("parentMenuId",parentMenuId);
		List<Menus> list=menusMapper.queryChildMenusListByUIDAndParentMenuIds(map);
		return list;
	}
	
	public Menus queryMenuId(Integer menuId)
	{
		Menus menu=menusMapper.queryMenuById(menuId);
		return menu;
	}
	
	public List<Menus> queryMenuIdChildren(Integer menuId)
	{
		Menus men=new Menus();
		men.setParentId(menuId);
		List<Menus> menu=menusMapper.queryMenuByIdChildren(men);
		return menu;
	}
	
	public List<Menus> queryParentId()
	{
		Menus men=new Menus();
		men.setParentId(0);
		
		List<Menus> list=menusMapper.queryList(men);
		return list;
	}
	
	public void insert(Menus menus)
	{
		menusMapper.insert(menus);
	}
	
	public void delete(int menuId)
	{
		Menus men=new Menus();
		men.setMenuId(menuId);
		menusMapper.delete(men);
	}
	
	public void deleteChildren(int menuId)
	{
		Menus men=new Menus();
		men.setMenuId(menuId);
		menusMapper.deleteChildren(men);
	}
	
	public void update(Menus menus)
	{
		
		menusMapper.update(menus);
	}
	
	public List<Menus> queryALL(){
		List<Menus> list=menusMapper.queryList(null);
		return list;
	}
	
	/**
	 * 数据库事务测试例子
	 * @author lizhongjie
	 * create date 2013-9-10
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void addMenus(){
		Menus men=new Menus();
		men.setMenuId(1234);
		men.setParentId(0);
		men.setDisplayName("test1");
		men.setDescrp("sdfsdfsdf");
		men.setUrl("sdfsdfsdfsdf");
		
 
		Menus men1=new Menus();
		men1.setMenuId(1223);
		men1.setParentId(0);
		men1.setDisplayName("test2");
		men1.setDescrp("sdfsdfsdf");
		men1.setUrl("sdfsdfsdfsdf");
		
 
		Menus men2=new Menus();
		men2.setMenuId(2356);
		men2.setParentId(0);
		men2.setDisplayName("斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬收到发斯蒂芬阿斯顿发送到发送到发送到发斯蒂芬阿萨德发阿萨德发阿萨德发斯蒂芬撒地方阿萨德发撒地方撒旦3");
		men2.setDescrp("sdfsdfsdf");
		men2.setUrl("sdfsdfsdfsdf");
		
		menusMapper.insert(men);
		menusMapper.insert(men1);
		menusMapper.insert(men2);
		
		
		
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println();
			System.exit(0);
		} catch (Exception e) {
			 System.out.println("error");
		}
		
	}
	 
	
}
