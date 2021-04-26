package cn.concox.app.rolePrivilege.controller;
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

import sso.comm.model.UserInfo;
import cn.concox.app.common.page.Page;
import cn.concox.app.rolePrivilege.service.RoleMgtService;
import cn.concox.app.rolePrivilege.service.UserRoleMgtService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.rolePrivilege.FunctionDetails;
import cn.concox.vo.rolePrivilege.MenusFunction;
import cn.concox.vo.rolePrivilege.RoleMenus;
import cn.concox.vo.rolePrivilege.RoleMgt;

@Controller
@Scope("prototype")
public class RoleMgtController extends BaseController {
	
	Logger roleLog=Logger.getLogger("privilege");
	
	@Resource(name="roleMgtService")
	private RoleMgtService roleMgtService;
	
	@Resource(name="userRoleMgtService")
	private UserRoleMgtService userRoleMgtService;
	
	/**
	 * 根据RoleName查询角色列表
	 * @param roleMgt
	 * @param req
	 * @return
	 * @DateTime 2013/10/23 PM
	 */
	@RequestMapping("rolePrivilege/roleList")
	@ResponseBody
	public APIContent getRoleListPage(@ModelAttribute RoleMgt roleMgt,@ModelAttribute CommonParam comp,HttpServletRequest req){
		roleLog.info("角色列表开始查询");
		try{
			Page<RoleMgt> roleListPage=roleMgtService.getRoleListByRoleMgtBean(roleMgt, comp.getCurrentpage(),comp.getPageSize());
			req.getSession().setAttribute("totalValue", roleListPage.getTotal());
			return super.putAPIData(roleListPage.getResult());
		}catch(Exception e){
			roleLog.error("角色列表查询失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("rolePrivilege/roleUpdate")
	@ResponseBody
	public APIContent updateRoleInfo(@ModelAttribute RoleMgt roleMgt,HttpServletRequest req){
		roleLog.info("角色信息更新开始");
		try{
			RoleMgt exist = roleMgtService.getRoleByName(roleMgt);
			if(exist != null){
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,roleMgt.getRolerName()+" "+Globals.ROLER_EXISTS);
			}else{
				UserInfo ui=(UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
				roleMgt.setCreater(ui.getuId());
				roleMgtService.updateRoleInfo(roleMgt);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"角色信息更新"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
			
		}catch(Exception e){
			roleLog.error("角色信息更新失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("rolePrivilege/roleDelete")
	@ResponseBody
	public APIContent deleteRoleInfo(@RequestParam("rolerId") Integer rolerId){
		roleLog.info("角色信息删除开始");
		try{
			//判断角色是否分配到用户
			if(userRoleMgtService.getRoleIdCount(rolerId) > 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE_DESC,"角色信息已分配到用户，不能删除");
			}else{
				RoleMgt rm=new RoleMgt();
				rm.setRolerId(rolerId);
				roleMgtService.deleteRoleInfo(rm);//主表oa_roler
				roleMgtService.deleRoleMenus(rolerId);//关系表oa_roler_menus
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"角色信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}
		}catch(Exception e){
			roleLog.error("角色信息删除失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 加载所有的菜单及子模块Function
	 * @param roleMgt
	 * @param req
	 * @return
	 */
	@RequestMapping("rolePrivilege/allMenuFuncLoad")
	@ResponseBody
	public APIContent allMenuFuncLoad(HttpServletRequest req){
		roleLog.info("加载所有的菜单及子模块开始查询");
		try{
			List<MenusFunction> menus=(List<MenusFunction>) req.getSession().getAttribute(Globals.ALL_MENU_AND_FUNC_KEY);
			if(menus==null || menus.size()==0){
				menus=roleMgtService.getAllMenuFunc();
				//采用分步查询,并未采用在IBatis中一对多方式查询实现,为了封装返回数据参数
				for(MenusFunction mf: menus){
					Integer getMenuId=mf.getMenuId();
					//根据menuId查询functionIds
					List<FunctionDetails> fd=roleMgtService.queryFuncListByMenuId(getMenuId);
					String funcIds="";
					for(int i=0;i<fd.size();i++){
						if(i==0){
							funcIds=fd.get(i).getFunctionId().toString();
						}else {
							funcIds +=","+fd.get(i).getFunctionId().toString();
						}
					}
					mf.setSn(funcIds);
					mf.setFuncDetails(fd);
				}
				
				req.getSession().setAttribute(Globals.ALL_MENU_AND_FUNC_KEY, menus);
			}
			return super.putAPIData(menus);
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("加载所有的菜单及子模块查询失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 角色及权限关系添加
	 * @param req
	 * @return
	 */
	@RequestMapping("rolePrivilege/roleMenusSave")
	@ResponseBody
	public APIContent roleMenusSave(HttpServletRequest req,@ModelAttribute RoleMgt roleMgt,@RequestParam("id") String id){
		roleLog.info("角色及权限关系添加开始");
		try{
			UserInfo ui=(UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
			String getUserId=ui.getuId();
			roleMgt.setCreater(getUserId);
			
			Integer getRolerId=roleMgt.getRolerId();
			if(getRolerId!=0){
				//修改即角色权限设置
//				roleMgtService.updateRoleInfo(roleMgt);//主表oa_roler
				roleMgtService.deleRoleMenus(getRolerId);//关系表oa_roler_menus
				
				if(!"0".equals(id)){
					String[] parent_menu_func_id=id.split(",");
					RoleMenus rm=null;
					for(String p_m_f_id:parent_menu_func_id){
							Integer getParentId=Integer.parseInt(p_m_f_id.substring(0, p_m_f_id.indexOf("_")));
							Integer getMenuId=Integer.parseInt(p_m_f_id.substring(p_m_f_id.indexOf("_")+1,p_m_f_id.lastIndexOf("_")));
							Integer getFuncId=Integer.parseInt(p_m_f_id.substring(p_m_f_id.lastIndexOf("_")+1));
							
							rm=new RoleMenus(getRolerId,getParentId,getMenuId,getFuncId,getUserId);
							
							roleMgtService.roleMenusSave(rm);
					}
					return new APIContent(Globals.OPERA_SUCCESS_CODE,"角色权限"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
				
				return new APIContent(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
				
			}else{
				//角色信息添加,直接添加并获取到主键		
				RoleMgt exist = roleMgtService.getRoleByName(roleMgt);
				if(exist != null){
					return super.operaStatus(Globals.OPERA_FAIL_CODE,roleMgt.getRolerName()+" "+Globals.ROLER_EXISTS);
				}else{
					getRolerId=roleMgtService.getAutoIncreateId(roleMgt);
					return new APIContent(Globals.OPERA_SUCCESS_CODE,"角色添加"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("角色及权限关系添加失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 根据角色ID查询角色t_oa_roler及功能信息t_oa_roler_menus
	 * @param rolerId
	 * @return
	 */
	@RequestMapping("rolePrivilege/getRolerInfosByRolerId")
	@ResponseBody
	public APIContent getRolerInfosByRolerId(@RequestParam("rolerId") Integer rolerId){
		roleLog.info("当前角色详细信息开始查询");
		try{
			RoleMgt rm=roleMgtService.getRolerInfosByRolerId(rolerId);
			return super.putAPIData(rm);
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("当前角色详细信息查询失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}


}
