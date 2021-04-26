package cn.concox.app.user.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sso.comm.model.Organizational;
import sso.comm.model.UserInfo;
import sso.comm.model.UserLogin;
import sso.comm.model.UserOrganizational;
import sso.comm.util.SSOUtils;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.rolePrivilege.service.UserRoleMgtService;
import cn.concox.app.user.controller.service.UserService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
 



@Controller
@Scope("prototype")
public class UserManagerController extends BaseController{
	Logger log=Logger.getLogger(UserManagerController.class);
	
	@Resource(name="userService")
	private UserService userSvr;
	
	@Resource(name="userRoleMgtService")
	private UserRoleMgtService userRoleMgtService;
	
	@RequestMapping("/usermanager/userselectall")
	@ResponseBody
	public APIContent UserSelectAll(@ModelAttribute UserInfo userInfo) {

		SSOUtils utils = new SSOUtils();
		log.info("员工信息查询开始");
		try {

			//userInfo.setuStatus(0);
			List<UserInfo> result = utils.getUserInfoList(userInfo);

			return super.putAPIData(result);
		} catch (Exception e) {
			log.error("员工信息查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");

		}
	}
	
	@RequestMapping("/user/SelectUidByUid")
	@ResponseBody
	public APIContent SelectUidByUid(@RequestParam("uId") String uId){
 
		SSOUtils utils=new SSOUtils();
 
		log.info("用户ID查询开始");
		try {
			UserInfo userInfo=new UserInfo();
			userInfo.setuId(uId);
		  List<UserInfo> result=utils.getUserInfoList(userInfo);
		   return super.putAPIData(result);
		} 
		catch (Exception e) {
			log.error("用户ID查询失败！");
			log.error(e.toString());
			return super.putAPIData("{'code':201101,'msg':'参数错误'}");
			
		}
	}
	
	
	
	@RequestMapping("/user/SelectUserByUserInfo")
	@ResponseBody
	public APIContent SelectUserByUserInfo(@ModelAttribute UserInfo userInfo){
 
		 
		SSOUtils utils=new SSOUtils();
	  
		
		log.info("用户全局查询开始");
		try {
			  //userInfo.setuStatus(0);   
			if(userInfo.getuName()==null||userInfo.getuName().equals(""))
				userInfo.setuName(null);  
			if(userInfo.getoId()==null||userInfo.getoId()==0)
				userInfo.setoId(null);    
			if(userInfo.getcId()==null || userInfo.getcId().equals("")){
				userInfo.setcId(null);
			} 
			if (userInfo.getuStatus()==null) {
				userInfo.setuStatus(null);
			}
		   List<UserInfo> result=utils.getUserInfoList(userInfo);
	 
		   return super.putAPIData(result);
		} 
		catch (Exception e) {
			log.error("用户全局查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	
	
	
	
	@RequestMapping("/user/SelectOrgByOId")
	@ResponseBody
	public APIContent SelectOrgByOId(@RequestParam("oId") Integer oId){
 
		SSOUtils utils=new SSOUtils();
 
		log.info("用户ID查询ByOId开始");
		try {
		
		  Organizational result=utils.getOrganizational(String.valueOf(oId));
		   return super.putAPIData(result);
		} 
		catch (Exception e) {
			log.error("用户ID查询ByOId失败！");
			log.error(e.toString());
			return super.putAPIData("{'code':201101,'msg':'参数错误'}");
			
		}
	}
	
	@RequestMapping("/user/GetSessionUser")
	@ResponseBody
	public APIContent GetSessionUser(){
		SSOUtils utils=new SSOUtils();
		log.info("操作人员信息查询开始");
		try {
		     UserInfo userInfo=(UserInfo) this.getRequest().getSession().getAttribute(Globals.USER_KEY);
		     UserInfo useri=utils.getUserInfo(userInfo.getuId());
             return super.putAPIData(useri);
         }catch(Exception e)
         {

 			log.error("员工信息查询失败！");
 			log.error(e.toString());
 			return super.putAPIData("false");
         }
	} 
	
	@RequestMapping("/usermanager/userselect")
	@ResponseBody
	public APIContent UserSelect(@RequestParam("oId") String oId){
 
		SSOUtils utils=new SSOUtils();
		log.info("员工信息查询开始");
		try {
		   List<UserInfo> result=utils.getUserInfoListByOrgId(oId ,Globals.USERSTATUS0);
		   return super.putAPIData(result);
		} 
		catch (Exception e) {
			log.error("员工信息查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/usermanager/userorgquery")
	@ResponseBody
	public APIContent UserOrgQuery(@RequestParam("uId") String uId){
 
		SSOUtils utils=new SSOUtils();
		//servitce.addMenus();
		log.info("员工部门查询开始");
		try {
		  List<Organizational> lorg=utils.getUserOrgsByUId(uId);
		   return super.putAPIData(lorg);
		} 
		catch (Exception e) {
			log.error("员工部门查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/usermanager/usermainorgquery")
	@ResponseBody
	public APIContent UserMainOrgQuery(@RequestParam("uId") String uId){
 
		SSOUtils utils=new SSOUtils();
		log.info("员工部门查询开始");
		try {
		  Organizational org=utils.getUserMainOrgByUId(uId);
		   return super.putAPIData(org);
		} 
		catch (Exception e) {
			log.error("员工部门查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/usermanager/insert")
	@ResponseBody
	public APIContent insert(@ModelAttribute UserInfo ui,@ModelAttribute UserLogin ul
			,@RequestParam("omainId") String omainId
			,@RequestParam("osecondId") String osecondId
			,@RequestParam("JoinDateTime") String JoinDateTime
			,@RequestParam("PositiveDateTime") String PositiveDateTime
			,@RequestParam("Birthday") String Birthday
			,@RequestParam("ContractStart") String ContractStart
			,@RequestParam("ContractEnd") String ContractEnd
			,HttpServletRequest request){
		SSOUtils utils=new SSOUtils();
		Date joinDateTime=new Date();
		Date positiveDateTime=new Date();
		Date birthday=new Date();
		Date contractStart=new Date();
		Date contractEnd=new Date();

		List<UserOrganizational> luorg=new ArrayList<UserOrganizational>();
		//servitce.addMenus();
		log.info("员工信息增加开始");
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			if(JoinDateTime!="")
				joinDateTime=sdf.parse(JoinDateTime);
			if(PositiveDateTime!="")
			    positiveDateTime=sdf.parse(PositiveDateTime);
			if(Birthday!="")
		     birthday=sdf.parse(Birthday);
			if(ContractStart!="")
			 contractStart=sdf.parse(ContractStart);
			if(ContractEnd!="")
			 contractEnd=sdf.parse(ContractEnd);
	
			  
			
			ui.setuBirthday(birthday);
			ui.setuJoinDateTime(joinDateTime);
			ui.setuPositiveDateTime(positiveDateTime);
			ui.setuContractEnd(contractEnd);
			ui.setuContractStart(contractStart);
  
			   
			utils.addUserInfo(ui, ul);
			UserInfo obj= (UserInfo)request.getSession().getAttribute(Globals.USER_KEY);
			if(obj==null||obj.getuId()==null){
				log.error("get user info is null from session,add user roles error!");
				return super.putAPIData("false");
			}else{
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("userId", ui.getuId());
				map.put("creater", obj.getuId());
				map.put("rolerId", 44);
				userRoleMgtService.newUserDefaultRoleAdd(map);
			}
			
			
			/**
			 * 用户组织架构插入
			 */
			if(omainId!="")
			{

				UserOrganizational uorg=new UserOrganizational();
			uorg.setuId(ui.getuId());
			uorg.setoId(Integer.valueOf(omainId));
			uorg.setmDpt(1);
			luorg.add(uorg);
			
			}
			if(osecondId!="")
			{ 
				String[] osecondIdarray=osecondId.split(","); 
		      for (int i = 0; i < osecondIdarray.length; i++) 
		      {
		  		UserOrganizational uorg=new UserOrganizational();
		    	    uorg.setuId(ui.getuId());
					uorg.setoId(Integer.valueOf(osecondIdarray[i]));
					uorg.setmDpt(0);
					luorg.add(uorg);
		      }
		    }
			utils.updateOrInsertUserOrganizationals(ui.getuId(), luorg);
			
				
			return super.putAPIData("true");
		} 
		catch (Exception e) {
			log.error("员工信息增加失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	@RequestMapping("/usermanager/UserUpdate")
	@ResponseBody
	public APIContent UserUpdate(@ModelAttribute UserInfo ui){
 
		SSOUtils utils=new SSOUtils();
		log.info("员工信息修改开始");
		try {
			  
			utils.updateUserInfo(ui);
			
			UserInfo newUser=utils.getUserInfo(ui.getuId());
			this.getRequest().getSession().setAttribute(Globals.USER_KEY,newUser);//重新设置用户信息
                       
			return super.putAPIData("true");
		} 
		catch (Exception e) {
			log.error("员工信息修改失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/usermanager/update")
	@ResponseBody
	public APIContent update(@ModelAttribute UserInfo ui
			,@RequestParam("omainId") String omainId
			,@RequestParam("osecondId") String osecondId
			,@RequestParam("JoinDateTime") String JoinDateTime
			,@RequestParam("PositiveDateTime") String PositiveDateTime
			,@RequestParam("Birthday") String Birthday
			,@RequestParam("ContractStart") String ContractStart
			,@RequestParam("ContractEnd") String ContractEnd
			,@RequestParam("JumpDate") String JumpDate
			
			){
         //***********************功能全部放到service层，userSvr.UserUpdate事务处理
		try { 
			userSvr.UserUpdate(ui, omainId, osecondId, JoinDateTime, PositiveDateTime, Birthday, ContractStart, ContractEnd,JumpDate);   //事务执行 
			return super.putAPIData("true");
		}  
		catch (Exception e) {
			log.error("员工信息修改失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
		
		
	}
	
	@RequestMapping("/usermanager/getUserInfoByuID")
	@ResponseBody
	public APIContent getUserInfoByuID(@RequestParam("uId") String uId){
       
		SSOUtils utils=new SSOUtils();
		log.info("员工信息查询开始");
		try { 
			UserInfo newUser=utils.getUserInfo(uId);   
			return super.putAPIData(newUser);
		} 
		catch (Exception e) {
			log.error("员工信息查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	} 
	
	@RequestMapping("/usermanager/UserExport")
	public void UserExport(@ModelAttribute UserInfo userInfo,HttpServletRequest request, HttpServletResponse response)     
		    throws Exception { 
		      
		        SSOUtils utils=new SSOUtils(); 
		        List<UserInfo> result=utils.getUserInfoList(userInfo);
 
		        
		    	HSSFWorkbook workbook;
				HSSFSheet sheet;
			    HSSFRow row;
				try {
					workbook = new HSSFWorkbook(new FileInputStream(this.getRequest().getSession().getServletContext().getRealPath("//")+"//salarytmp//"+"花名册模板.xls"));
					sheet = workbook.getSheetAt(0);
					
					for (int i = 0; i < result.size(); i++) {    
						row = sheet.getRow(i+2);   
						UserInfo userm= result.get(i);    

			            row.getCell(0).setCellValue(i+1);
			            row.getCell(1).setCellValue(userm.getoName());
			            row.getCell(2).setCellValue(userm.getuName()); 
			            row.getCell(3).setCellValue(userm.getcId());
			            row.getCell(4).setCellValue(userm.getuPosition());
			            row.getCell(5).setCellValue(userm.getuOfficeArea());
			            row.getCell(6).setCellValue(userm.getuJoinDateTime()!=null?DateTimeUtil.getDateString(userm.getuJoinDateTime()) :"");
			            if(userm.getuJoinDateTime()!=null)
			            {        
			                Date year=new Date();
				            int serviceLength=  year.getYear()-userm.getuJoinDateTime().getYear();   
			            	row.getCell(7).setCellValue(serviceLength); 
			            }     
			            if(userm.getuPositiveDateTime()!=null)
			            { 
			            	row.getCell(8).setCellValue(userm.getuPositiveDateTime()!=null?DateTimeUtil.getDateString(userm.getuPositiveDateTime()) :""); 
			            }
			            if(userm.getuSex()!=null)
			            {
			            	if(userm.getuSex()==1)
			            	{row.getCell(9).setCellValue("男");
			            	}
			            	else if(userm.getuSex()==0)
			            	{
			            		row.getCell(9).setCellValue("女");
			                 }
			            	else if(userm.getuSex()==2)
			            	{
			            		row.getCell(9).setCellValue("未知");
			                 }
			            }
			            row.getCell(10).setCellValue(userm.getuEthnic());
			            if(userm.getuBirthday()!=null)
			            {
			            row.getCell(11).setCellValue(userm.getuBirthday()!=null?DateTimeUtil.getDateString(userm.getuBirthday()) :"" );
			            }
			            Date bir=  userm.getuBirthday();
			            Date year=new Date();
			            int iage=  year.getYear()-bir.getYear();   
			            row.getCell(12).setCellValue(iage);
			            row.getCell(13).setCellValue(userm.getuEducationalBackground());
			            if(userm.getuContractStart()!=null)
			            {
			            	row.getCell(14).setCellValue(userm.getuContractStart()!=null?DateTimeUtil.getDateString(userm.getuContractStart()) :"" );
			            }
			            if(userm.getuContractEnd()!=null)
			            {
			            	row.getCell(15).setCellValue(userm.getuContractEnd()!=null?DateTimeUtil.getDateString(userm.getuContractEnd()) :"" );
			            } 
			            row.getCell(16).setCellValue(userm.getuMaritalStatus());
			            row.getCell(17).setCellValue(userm.getuHometown());
			            row.getCell(18).setCellValue(userm.getuIdCard());
			            row.getCell(19).setCellValue(userm.getuHomeAddress());
			            row.getCell(20).setCellValue(userm.getuEmergencyRelations());
			            row.getCell(21).setCellValue(userm.getuEmergencyTelephone());
			          //  row.getCell(23).setCellValue(userm.getuGradeName());
			            row.getCell(22).setCellValue(userm.getuGraduated());
			            row.getCell(23).setCellValue(userm.getuProfessionalName());
			            row.getCell(24).setCellValue(userm.getuSocialSecurityNumber());
			            row.getCell(25).setCellValue(userm.getuBankAccount());
			            row.getCell(26).setCellValue(userm.getuUserDesc());
			            row.getCell(27).setCellValue(userm.getuMail());
			            row.getCell(28).setCellValue(userm.getuEnterpriseQQ());
			            row.getCell(29).setCellValue(userm.getuQQ());
			            row.getCell(30).setCellValue(userm.getuProvidentFundAccount());
			            if(userm.getuStatus()!=null)
				        {   
			            	if(userm.getuStatus()==0)
				            {
			            		row.getCell(31).setCellValue("正常");
				            }
			            	else if(userm.getuStatus()==1)
				            {
			            		row.getCell(31).setCellValue("离职");
				            }
			            	else if(userm.getuStatus()==2)
				            {
			            		row.getCell(31).setCellValue("已删除");
				            }
			            }
			        }   
			        
					HSSFWorkbook wb =workbook;    
			        response.setContentType("application/vnd.ms-excel");    
			        response.setHeader("Content-disposition", "attachment;filename=user_list.xls");    
			        OutputStream ouputStream = response.getOutputStream();    
			        wb.write(ouputStream);    
			        ouputStream.flush();    
			        ouputStream.close(); 
			        
				} catch (FileNotFoundException e) {
					log.error("FileNotFoundException: "+ e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		           
	 }
	
	@RequestMapping("/usermanager/getPinYinByUname")
	@ResponseBody
	public APIContent getUidByUname(@RequestParam("uName") String uName){
		log.info("员工账号获取开始");
		return new APIContent(getPinYinUid(uName));
	}
	
	public String getPinYinUid(String uName){
		SSOUtils ssoUtils = new SSOUtils();
		try {
			String uId = ssoUtils.getPinYin(uName);
			UserInfo userInfo = ssoUtils.getUserInfo(uId);
			if (userInfo!=null) {
				uName = uName+"1";
				return getPinYinUid(uName);
			} else {
				return uId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@RequestMapping("/user/SelectUserByCid")
	@ResponseBody
	public APIContent SelectUserByCid(@ModelAttribute UserInfo userInfo){
 
		 
		SSOUtils utils=new SSOUtils();
	  
		log.info("验证工号开始！");
		try {
		   List<UserInfo> result=utils.getUserInfoList(userInfo);
		   if (result.size()==0) {
			   return super.putAPIData(true);
		} else{
		   return super.putAPIData(false);
			}
		} 
		catch (Exception e) {
			log.error("验证工号失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}

	/**
	 * 获取拥有权限的角色
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/user/UserListByMenu")
	@ResponseBody
	public APIContent getUserListByMenu(@RequestParam Integer menuId){
		log.info("查询具有权限的角色");
		try{
			List<UserInfo> userInfos = userSvr.getUserListByMenu(menuId);


			if(userInfos == null || userInfos.size()==0){
				return super.putAPIData("请确认是否有用户拥有权限");
			}
			List<UserInfo> result = new ArrayList<>(userInfos.size());
			for(UserInfo user:userInfos){
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(user.getuId());
				userInfo.setcId(user.getcId());
				userInfo.setoId(user.getoId());
				userInfo.setuName(user.getuName());
				userInfo.setoName(user.getoName());
				userInfo.setuPosition(user.getuPosition());
				userInfo.setuBirthday(user.getuBirthday());
				userInfo.setuOfficeArea(user.getuOfficeArea());
				userInfo.setuStatus(user.getuStatus());
				result.add(userInfo);
			}
			return super.putAPIData(result);
		}catch (Exception e){
			log.error("查询具有权限的角色失败");
			return super.putAPIData("获取失败");
		}
	}
}
