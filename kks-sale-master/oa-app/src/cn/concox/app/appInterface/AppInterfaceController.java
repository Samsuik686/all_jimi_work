package cn.concox.app.appInterface;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sso.comm.model.UserInfo;
import sso.comm.model.UserLogin;
import sso.comm.util.SSOUtils;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;

@Controller
@Scope("prototype")
@RequestMapping(value="/app")
public class AppInterfaceController extends BaseController{

	Logger log=Logger.getLogger(AppInterfaceController.class);

	/**
	 * 验证登录
	 * @param userLogin
	 * @return
	 */
	@RequestMapping(value="/userLogin")
	@ResponseBody
	public APIContent judgeLogin(@ModelAttribute UserLogin userLogin,HttpServletRequest request , HttpServletResponse response){
		SSOUtils ssoUtils=new SSOUtils();
		try {
			if(null!=userLogin){
				String userId= ssoUtils.validUserLogin(userLogin);
				if(null==userId||"".equals(userId)){
					 return new APIContent("0001", Globals.E0001);
				}
				request.getSession().setAttribute(Globals.USER_KEY, ssoUtils.getUserInfo(userId));
				return new APIContent(userId);
			}
			return new APIContent("0003",Globals.E0003);
		} catch (Exception e) {
			logger.error("验证登录错误：",e);
		}
		return new APIContent("0002", Globals.E0002);
	}
	
	/**
	 * 获取部门信息
	 * @return
	 */
	@RequestMapping("/OrganSelectList")
	@ResponseBody
	public String OrganSelectList(){
		SSOUtils utils=new SSOUtils();
		log.info("组织架构查询开始");
		try {
			String result=utils.getOrganizationalList("0");
		    return result;
		} 
		catch (Exception e) {
			logger.error("获取组织架构错误：",e);
			return "{'code':201101,'msg':'参数错误'}";
		}
	}
	
	/**
	 * 根据uId获取用户信息
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/queryUserInfoByUid")
	@ResponseBody
	public APIContent queryUserInfo(@ModelAttribute UserInfo userInfo){
		SSOUtils ssoUtils=new SSOUtils();
		try {
			List<UserInfo> list=ssoUtils.getUserInfo(userInfo);
			return new APIContent(list);
		} catch (Exception e) {
			logger.error("根据uId获取用户信息错误：",e);
			return new APIContent(null);
		}
	}
}
