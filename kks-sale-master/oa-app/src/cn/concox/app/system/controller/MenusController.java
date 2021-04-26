/*
 * Created: 2013-9-10
 * This software consists of contributions made by concox R&D.
 * @author: Li Zhongjie
 * MenusController.java
 */
package cn.concox.app.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.system.service.MenusService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.system.Menus;



@Controller
@Scope("prototype")
public class MenusController extends BaseController{
	
	Logger log=Logger.getLogger(MenusController.class);
	
	@Resource(name="menusService")
	private MenusService service;
	
	/*
	@RequestMapping("/system/MenuSelect")
	@ResponseBody
	public APIContent select()throws Exception{
 
		//servitce.addMenus();
		log.info("查询开始");
		try {
			return super.putAPIData(service.queryMenus());
		} 
		catch (Exception e) {
			log.error("查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
		}
	}
	*/
	
	/**
	 * 用户菜单列表获取
	 * @author wg.he 2013/10/22
	 * @return
	 */
	@RequestMapping("/system/MenuSelect")
	@ResponseBody
	public APIContent selectMenusByUID(){
		log.info("用户菜单列表获取开始");
		try {
			List<Menus> userMenuList= (List<Menus>)super.getRequest().getSession().getAttribute(Globals.USER_MENULIST_KEY);
			if(userMenuList!=null && userMenuList.size()!=0){
				log.info("用户菜单列表获取结束,父级菜单数："+userMenuList.size());
				return super.putAPIData(userMenuList);
			}
			return new APIContent(Globals.USER_GETUSERMENUS_CODE,Globals.USER_GETUSERMENUS_CODE_MSG);
		}catch (Exception e) {
			log.error("用户菜单列表获取异常:"+e.toString());
			return new APIContent(Globals.USER_GETUSERMENUS_CODE,Globals.USER_GETUSERMENUS_CODE_MSG);
		}
	}
	
 
	@RequestMapping("/system/MenuInsert")
	@ResponseBody
	public APIContent insert(@ModelAttribute Menus menus){
		//service.addMenus();
		log.info("增加开始");
		try {
			service.insert(menus);	
			return super.putAPIData(service.queryMenus());
			
		} catch (Exception e) {
			log.error("增加失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
		
		
	}
	/**
	 * 查询所有的FunctionURL
	 * @return
	 */
	public List<String> queryAllFunctionURL(){
		log.info("所有权限URL获取开始");
		try {
			List<String> funcURLList= (List<String>)super.getRequest().getSession().getAttribute(Globals.ALL_FUNCTIONURL_KEY);
			if(funcURLList==null || funcURLList.size()==0){
				funcURLList=service.queryAllFunctionURL();
			}
			return funcURLList;
		}catch (Exception e) {
			log.error("所有权限URL获取异常:"+e.toString());
			return null;
		}
	}
	
	@RequestMapping("/system/MenuDelete")
	@ResponseBody
	public APIContent delete(@RequestParam("menuId") int menuId){
		//service.addMenus();
	   log.info("删除开始");
	   try{
		service.delete(menuId);
		return super.putAPIData(service.queryMenus());}
	   catch(Exception e)
	   {
		   log.error("删除失败");
		   log.error(e.toString());
		   return super.putAPIData("false");
	   }
	}
 
	@RequestMapping("/system/deleteChildren")
	@ResponseBody
	public APIContent deleteChildren(@RequestParam("menuId") int menuId){
		//service.addMenus();
		log.info("删除子菜单开始");
		   try{
				service.deleteChildren(menuId);
				return super.putAPIData(service.queryMenus());
		   }
		   catch(Exception e)
		   {
			   log.error("删除子菜单失败");
			   log.error(e.toString());
			   return super.putAPIData("false");
		   }
	}

	@RequestMapping("/system/MenuUpdate")
	@ResponseBody
	public APIContent update(@ModelAttribute Menus menus){
		//service.addMenus();
		log.info("修改开始");
		try{
			service.update(menus);
			return super.putAPIData(service.queryMenus());
		   }
		   catch(Exception e)
		   {
			   log.error("修改失败");
			   log.error(e.toString());
			   return super.putAPIData("false");
		   }
	}
	
	@RequestMapping("/system/queryMenuId")
	@ResponseBody
	public APIContent queryMenuById(@RequestParam("menuId") Integer menuId)
	{
		log.info("查询指定菜单开始");
		try{
			return super.putAPIData(service.queryMenuId(menuId));
		   }
		   catch(Exception e)
		   {
			   log.error("查询指定菜单失败");
			   log.error(e.toString());
			   return super.putAPIData("false");
		   }
	}
	
	@RequestMapping("/system/queryMenuIdChildren")
	@ResponseBody
	public APIContent queryMenuByIdChildren(@RequestParam("menuId") int menuId)
	{
		log.info("查询指定菜单子菜单开始");
		try{
			return super.putAPIData(service.queryMenuIdChildren(menuId));
		   }
		   catch(Exception e)
		   {
			   log.error("查询指定菜单子菜单失败");
			   log.error(e.toString());
			   return super.putAPIData("false");
		   }
		
	}
	
	@RequestMapping("/system/queryParentId")
	@ResponseBody
	public APIContent queryParentById()
	{
		log.info("由父级菜单查询开始");
		try{
		    return super.putAPIData(service.queryParentId());
		   }
		   catch(Exception e)
		   {
			   log.error("由父级菜单查询失败");
			   log.error(e.toString());
			   return super.putAPIData("false");
		   }
	}
	
	@RequestMapping("/system/MenuManagerload")
	@ResponseBody
	public APIContent MenuManagerload()
	{
		log.info("由菜单重载查询开始");
		try{
		return super.putAPIData(service.queryMenus());
		 }
		   catch(Exception e)
		   {
			   log.error("由菜单重载查询失败");
			   log.error(e.toString());
			   return super.putAPIData("false");
		   }
	}
	/**
	 * 查询所有，曾删改查相同的操作。
	 * @param request
	 * @return
	 */
	@RequestMapping("demo/searchMenus")
	public void queryALL(HttpServletRequest request){
		List<Menus> menus=service.queryALL();
		request.setAttribute("menus", menus);
		
	}

	
	
	
	/**
	 * 获取我的消息
	 * @param user
	 * @return
	 */
//	public APIContent querySysMsg(User user){
//		//获取用户信息
//		return null;
//	}

}

