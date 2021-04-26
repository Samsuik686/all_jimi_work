package cn.concox.app.system.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.system.service.MenusDetailsService;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.system.MenuDetails;

@Controller
@Scope("prototype")
public class MenusDetailsController extends BaseController{
	Logger log=Logger.getLogger(MenusDetailsController.class);
	
	@Resource(name="menusDetailsService")
	private MenusDetailsService service;
	
	@RequestMapping("/system/MenuDetailsSelectByMenuid")
	@ResponseBody
	public APIContent selectByMenuid(@RequestParam("menuId") Integer menuId){
 
		//servitce.addMenus();
		log.info("菜单权限查询开始");
		try {
		return super.putAPIData(service.queryMenusDetailsByMenuId(menuId));
		} 
		catch (Exception e) {
			log.error("菜单权限查询失败！");
			log.error(e.toString());
			return super.putAPIData("MenusDeatailByMenuIdSearchfail");
			
		}
	}
	
	@RequestMapping("/system/MenuDetailsSelectByFunid")
	@ResponseBody
	public APIContent selectByFunid(@RequestParam("functionId") Integer functionId){
 
		//servitce.addMenus();
		log.info("菜单权限查询开始");
		try {
		return super.putAPIData(service.queryMenusDetailsByFunId(functionId));
		} 
		catch (Exception e) {
			log.error("菜单权限查询失败！");
			log.error(e.toString());
			return super.putAPIData("MenusDeatailByFunIdSearchfail");
			
		}
	}
	
	@RequestMapping("/system/insertMenusDetails")
	@ResponseBody
	public APIContent insertMenusDetails(@ModelAttribute MenuDetails med){
 
		//servitce.addMenus();
		log.info("菜单权限增加开始");
		try {
			service.insertMenusDetails(med);
		return super.putAPIData(service.queryMenusDetailsByMenuId(med.getMenuId()));
		} 
		catch (Exception e) {
			log.error("菜单权限增加失败！");
			log.error(e.toString());
			return super.putAPIData("MenusDeatailByFunIdAddfail");
			
		}
	}
	
	@RequestMapping("/system/deleteMenusDetails")
	@ResponseBody
	public APIContent deleteMenusDetails(@RequestParam("functionId") Integer funId){
 
		//servitce.addMenus();
		log.info("菜单权限增加开始");
		try {
			service.deleteMenusDetails(funId);
		return super.putAPIData(service.queryMenusDetailsByFunId(funId));
		} 
		catch (Exception e) {
			log.error("菜单权限增加失败！");
			log.error(e.toString());
			return super.putAPIData("MenusDeatailByFunIdDeletefail");
			
		}
	}
	
}
