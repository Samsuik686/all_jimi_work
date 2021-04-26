package cn.concox.app.cumstomFlow.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.common.page.Page;
import cn.concox.app.cumstomFlow.DomFlowUtils;
import cn.concox.app.cumstomFlow.mapper.CustomFlowMapper;
import cn.concox.app.cumstomFlow.mapper.CustomTaskMapper;
import cn.concox.app.rolePrivilege.mapper.RoleMenusMapper;
import cn.concox.app.rolePrivilege.mapper.RoleMgtMapper;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.app.system.mapper.MenusDetailsMapper;
import cn.concox.app.system.mapper.MenusMapper;
import cn.concox.vo.customProcess.CustomField;
import cn.concox.vo.customProcess.CustomFlow;
import cn.concox.vo.customProcess.CustomModel;
import cn.concox.vo.customProcess.CustomTask;
import cn.concox.vo.rolePrivilege.RoleMenus;
import cn.concox.vo.rolePrivilege.RoleMgt;
import cn.concox.vo.rolePrivilege.UserRoleMgt;
import cn.concox.vo.system.MenuDetails;
import cn.concox.vo.system.Menus;
import sso.comm.model.UserInfo;

/**
 * 
 * @author 183
 * @version 2020年5月28日
 */
@Service("customFlowService")
@Scope("prototype")
public class CustomFlowService {

	@Autowired
	private CustomFlowMapper<CustomFlow> customFlowMapper;
	
	@Resource(name="customTaskMapper")
	private  CustomTaskMapper<CustomTask> customTaskMapper;

	@Resource(name = "menusMapper")
	private MenusMapper<Menus> menusMapper;

	@Resource(name = "menusDetailsMapper")
	private MenusDetailsMapper<MenuDetails> menusDetailsMapper;

	@Resource(name = "roleMgtMapper")
	private RoleMgtMapper<RoleMgt> roleMgtMapper;

	@Resource(name = "roleMenusMapper")
	private RoleMenusMapper<RoleMenus> roleMenusMapper;

	@Resource(name = "userRoleMgtMapper")
	private UserRoleMgtMapper<UserRoleMgt> userRoleMgtMapper;

	private final static String menusUrlPrefix = "page/customFlow/customFlow.jsp?name=";

	private final static String modelUrlPrefix = "page/customFlow/customModel.jsp?name=";

	
	/**
	 * 查询自定义流程数据列表
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:05:52
	 * @param pm
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<CustomFlow> customFlowList(CustomFlow cf, Integer currentPage, Integer pageSize) {
		Page<CustomFlow> pages = new Page<CustomFlow>();
		pages.setCurrentPage(currentPage);
		pages.setSize(pageSize);
		pages = customFlowMapper.customFlowPageList(pages, cf);
		return pages;
	}

	/**
	 * 查询自定义流程数据列表总计
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:05:52
	 * @param pm
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Integer customFlowCount(CustomFlow cf) {
		return customFlowMapper.customFlowPageCount(cf);
	}

	/**
	 * 查询自定义流程图
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:05:52
	 * @param pm
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public String customFlowXml(String name) {
		return customFlowMapper.customFlowPageXml(name);
	}

	/**
	 * 插入自定义流程图
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:05:52
	 * @param pm
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public void customFlowInsert(CustomFlow cf) {
		customFlowMapper.insert(cf);
	}

	/**
	 * 创建自定义流程
	 * 
	 * @author 183
	 * @version 2020年5月29日 下午2:05:52
	 * @param pm
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String customFlowCreate(String xml, String remark, UserInfo user) throws Exception {
		DomFlowUtils sdu = DomFlowUtils.getDomFlowUtils(xml);
		String flowName = sdu.getFlowName();

		CustomFlow query = new CustomFlow();
		query.setName(flowName);
		Integer customFlowCount = this.customFlowCount(query);
		if (customFlowCount == null || customFlowCount > 0) {
			return "流程图名称已存在！";
		}

		CustomFlow cf = new CustomFlow();
		cf.setName(flowName);
		cf.setStatus(0);
		cf.setXml(xml);
		cf.setRemark(remark);
		cf.setCreateUser(user.getuId());
		cf.setCreateDate(new Date());
		// 创建模块
		List<CustomModel> trans2Model = sdu.trans2Model();

		// 主菜单创建
		Menus menus = createMenu(0, flowName, menusUrlPrefix + flowName, remark,
				(int) (System.currentTimeMillis() / 1000));
		menusMapper.insert(menus);
		cf.setMenuId(menus.getMenuId());
		this.customFlowInsert(cf);

		// ---------------------单独一个模块的建立--------------------------------

		int count = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 二级菜单和页面权限
		for (CustomModel cm : trans2Model) {
			// 开始，结束节点不做
			if (cm.getType() == 1) {
				continue;
			}
			String name = cm.getName();
			String url = modelUrlPrefix + flowName + "&id=" + cm.getId();
			int order = 0;
			if (cm.getType() == 0) {
				// 创建开始页面
				name = flowName + "总览";
				url = modelUrlPrefix + flowName + "&id=0";
				order = 0;
			} else {
				order = ++count;
			}
			Menus menusModel = createMenu(menus.getMenuId(), name, url, remark, order);
			menusMapper.insert(menusModel);

			cm.setMenuId(menusModel.getMenuId());

			// 菜单拥有的url权限
			MenuDetails med = new MenuDetails();
			med.setMenuId(menusModel.getMenuId());
			med.setFunctionURL(flowName + "->" + name);
			med.setFunctionDesc(flowName + "->" + name);
			med.setCreater(user.getuId());
			med.setCreatTime(sdf.format(new Date()));
			menusDetailsMapper.insert(med);

			// 创建角色
			RoleMgt roleMgt = new RoleMgt();
			String roleName = flowName + "->" + name;
			roleMgt.setRolerName(roleName);
			roleMgt.setRolerDesc(roleName + ":流程自动创建");
			roleMgt.setCreatTime(new Date());
			roleMgt.setCreater(user.getuId());
			roleMgtMapper.insert(roleMgt);

			// url权限-roler关系映射插入
			RoleMenus roleMenus = new RoleMenus(roleMgt.getRolerId(), menus.getMenuId(), menusModel.getMenuId(),
					med.getFunctionId(), user.getuId());

			roleMenusMapper.insert(roleMenus);

			// 用户-roler关系插入
			Map<String, Object> userRoleAddMap = new HashMap<String, Object>();
			userRoleAddMap.put("userId", user.getuId());
			userRoleAddMap.put("rolerId", roleMgt.getRolerId());
			userRoleAddMap.put("creater", user.getuId());
			userRoleMgtMapper.userRoleAdd(userRoleAddMap);

		}

		customFlowMapper.insertModel(trans2Model);

		return null;
	}

	private Menus createMenu(int parentId, String name, String url, String desc, int oi) {
		Menus menus = new Menus();
		menus.setParentId(parentId);
		menus.setDisplayName(name);
		menus.setUrl(url);
		menus.setDescrp(desc);
		menus.setOrderindex(oi + "");
		menus.setSn(oi + "");
		return menus;
	}

	// 插入流程模块
	public void insertModel(List<CustomModel> cms) {
		customFlowMapper.insertModel(cms);
	}

	// 查询流程模块
	public List<CustomModel> queryCustomModel(CustomModel cm) {
		return customFlowMapper.queryCustomModel(cm);
	}

	// 查询流程模块子模块
	public List<CustomModel> queryCustomModelByIds(CustomModel cm) {
		return customFlowMapper.queryCustomModelByIds(cm);
	}
	
	// 查询跟进人
	public List<String> queryFollowers(Integer menuId){
		return customFlowMapper.queryFollowers(menuId);
	}

	@Transactional
	public void updateFields(String belong, List<CustomField> cms) {
		customFlowMapper.deleteFieldByBelong(belong);
		if(null != cms && cms.size() > 0){
			customFlowMapper.insertField(cms);
		}
	}
	
	public void updateFields(List<CustomField> cms) {
		customFlowMapper.insertField(cms);
	}
	
	public void deleteFieldByBelong(String belong) {
		customFlowMapper.deleteFieldByBelong(belong);
	}
		
	public CustomFlow selectCustomFlow(String name){
		return customFlowMapper.selectCustomFlow(name);
	}

	/**
	 * 无效流程，会删除相关所有表信息，只留下xml
	 * @param name
	 */
	@Transactional
	public void deleteFlow(CustomFlow cf) {
		// 删除流程图信息
		Integer menuId = cf.getMenuId();
		
		if(null != menuId){
			// 查询模块信息
			CustomModel query = new CustomModel();
			query.setBelong(cf.getName());
			List<CustomModel> queryCustomModel = this.queryCustomModel(query);
			for(CustomModel cm : queryCustomModel){
				Integer cmMenuId = cm.getMenuId();
				
				if(null == cmMenuId){
					continue;
				}
				// 删除url信息
				MenuDetails med=new MenuDetails();
				med.setMenuId(cmMenuId);
				menusDetailsMapper.deleteByMenuId(med);
				
				// 删除菜单
				Menus men=new Menus();
				men.setMenuId(cmMenuId);
				menusMapper.delete(men);
				
				// 查询rolerId
				List<Integer> rolerInfosByMenuId = roleMenusMapper.getRolerInfosByMenuId(cmMenuId);
				if(rolerInfosByMenuId == null || rolerInfosByMenuId.size() == 0){
					continue;
				}
				
				for(Integer rolerId : rolerInfosByMenuId){
					// 删除用户角色关系
					Map userRoleDeleteMap=new HashMap();
					userRoleDeleteMap.put("rolerId", rolerId);
					userRoleMgtMapper.deleteUserRoleInfoByRolerId(userRoleDeleteMap);
					
					RoleMgt rm=new RoleMgt();
					rm.setRolerId(rolerId);
					// 删除角色
					roleMgtMapper.delete(rm);//主表oa_roler
					// 删除角色-菜单
					roleMenusMapper.deleRoleMenus(rolerId);//关系表oa_roler_menus
				}
				
			}
			// 删除主菜单
			Menus men=new Menus();
			men.setMenuId(menuId);
			menusMapper.delete(men);
		}
		
		// 删除服务上的文件
		List<String> fileUrls = customTaskMapper.selectFileUrlByFlowName(cf.getName());
		for(String url : fileUrls){
			File file = new File(url);
			if(file.exists()){
				file.delete();
			}
		}
		
		// 删除所有任务附件
		customTaskMapper.deleteFileByFlowName(cf.getName());
		
		// 删除所有任务
		customTaskMapper.deleteByFlowName(cf.getName());
		
		// 删除模块
		customFlowMapper.deleteModel(cf.getName());
		
		// 删除字段
		customFlowMapper.deleteFieldByBelong(cf.getName());
		
		// 删除流程
		customFlowMapper.deleteFlow(cf.getName());
	}
}
