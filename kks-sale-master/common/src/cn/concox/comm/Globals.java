package cn.concox.comm;
public interface Globals {
	
	/**
	 * 登录用户以后，使用这个字段保存为session的key，判断用户是否登录
	 */
	public static final String USER_KEY = "USERSESSION";

	public static final String USER_ID_KEY = "USERID";//修改密码时用
	
	public static final String MAIL_COUNT="mail-unread-count-api";
	
	public static final String MAIL_COUNT_ADDRESS="mail_count_address";
	
	public static final String WEBMAIL_ADDRESSS="webmail-address";
	public static final String WEBMAIL_ADDRESS_PROP="webMailAddress";
	
	public static final String OAACCESSPROJECTNAME="oaAccessProjectName";
	
	public static final String ALLOWUPLOADFILESUFFIX="allowUploadFileSuffix";
	
	public static final String ALLOWUPLOADFILESIZE="allowUploadFileSize";
	
	public static final String TOP1MANAGERID="top1ManagerId";

	public static final String ILLEGAL_PARAMETER="400";
	public static final String ILLEGAL_PARAMETER_DESC="参数错误";
	/*
	 * 登录令牌
	 */
	public static final String ACCESS_TOKEN="access_token"; 
	

	public static final String USER_POWER_KEY = "USERPOWER";//session中用于存取用户对应的具体操作functionURL
	
	public static final String USER_MENULIST_KEY = "USEMENULISTKEY";//session中用于存取用户对应的菜单列表Key
	
	public static final String ALL_MENU_AND_FUNC_KEY = "ALLMENUFUNCLOADKEY";//角色分配权限时列表
	
	public static final String ALL_FUNCTIONURL_KEY = "FUNCTIONURLKEY";//所有的FunctionURL
	
	public static final String COMMON_FUNCTIONURL_KEY = "COMMONFUNCTIONURLKEY";//公共URL FunctionURL
	
	public static final String ISNOTASSESS_DESC0 = "<a style='color:#FFFFFF;background-color:#CC0000;'>未考核<a>";
	
	public static final String ISNOTASSESS_DESC1 = "<a style='color:#FFFFFF;background-color:#33FF00;'>已考核<a>";
	
	
	public static final String USTATUS_DESC0 = "正常";
	
	public static final String USTATUS_DESC1 = "离职";
	
	public static final String USTATUS_DESC2 = "已删除";
	
	
	public static final String REVIEWSTATUS_DESC0 = "未提交";
	
	public static final String REVIEWSTATUS_DESC1 = "待审核";
	
	public static final String REVIEWSTATUS_DESC2 = "通过";
	
	public static final String REVIEWSTATUS_DESC3 = "驳回";
	
	public static final String REVIEWSTATUS_DESC4 = "部门经理待审批";//add by wg.he 2014/02/07 对于像刘维建所在的产品事业一部下又分多个主管来进行员工考核的情况，现在是要给他(部门经理)增加一个对其下属主管提交的考核进行一个审核通过或驳回的操作页面，只有他审核通过的那个主管下的员工才能让高总进行通过或驳回操作
	
	public static final String REVIEWSTATUS_DESC5 = "部门经理驳回";
	
	public static final String REQUEST_XML="XMLHttpRequest";
	
	public static final String ADMINACCOUNT="admin";
	
	public static final String OPERA_SUCCESS_CODE = "0";//后台业务操作成功
	
	public static final String OPERA_SYSCNFAIL_CODE = "-100";//数据同步失败
	
	
	public static final String OPERA_SUCCESS_CODE_DESC = "操作成功";//后台业务操作成功
	
	public static final String OPERA_FAIL_CODE = "-500";//后台业务操作异常
	
	public static final String OPERA_FAIL_CODE_DESC = "操作异常";//后台业务操作异常
	
	public static final String USER_NOLOGIN_CODE = "-1";//用户无未登录的CODE
	
	public static final String USER_MENUNOPOWER_CODE = "-2";//用户无权限操作时系统跳转的CODE
	
	public static final String USER_MENUNOPOWER_URL = "AuthenticationFailed.jsp";//用户无权限操作时系统跳转的页面
	
	public static final String USER_LOGOUT_CODE_EXC = "-3";//用户退出
	
	public static final String USER_LOGOUT_CODE_EXC_DESC = "用户退出SSO异常";//用户退出
	
	public static final String USER_GETUSERMENUS_CODE = "-4";//获取用户菜单列表异常
	
	public static final String USER_GETUSERMENUS_CODE_MSG = "获取用户菜单列表异常";//
	
	public static final String USER_MENUNOSET_CODE = "-5";//用户菜单并未设置,msg为USER_MENUNOPOWER_URL
	
	public static final String USER_NOLOGIN_EDITPD_CODE = "-6";
	
	public static final String USER_NOLOGIN_EDITPD_CODE_DESC = "用户修改密码异常";//
	
	public static final String OPERA_USERROLE_EXISTS_CODE = "-7";
	
	public static final String OPERA_USERROLE_EXISTS_CODE_DESC = "用户角色已经存在，请重新选择";//
	
	public static final String ROLER_EXISTS = "角色已存在系统，请修改当前角色名称再做操作";
	
	public static final String USER_REQINVALID_CODE = "404";//
	
	public static final String USER_REQINVALID_CODE_URL = "reqInvalid.jsp";//用户无权限操作时系统跳转的页面
	
	public static final String MANAGER_NOTEXISTSUSER_CODE = "-8";
	
	public static final String MANAGER_NOTEXISTSUSER_CODE_DESC = "当前月份下当前主管在组织架构中并不存在所属员工，故无法对员工进行考核及提交操作";
	
	public static final String REVIEW_SUBMIT_CODE = "-9";
	
	public static final String REVIEW_SUBMIT_CODE_DESC = "当月还存在绩效未考核的员工";
	
	public static final String MANAGER_REVIEWSTATUS_NOT0_CODE = "-10";
	
	public static final String MANAGER_REVIEWSTATUS_NOT0_CODE_DESC = "当月绩效考核已处于待审核状态，您不能再次提交";
	
	public static final String RETURNNODATA_CODE="-11";
	
	public static final String RETURNNODATA_CODE_DESC = "暂无当月绩效考核数据";
	
	public static final String USEREXISTS="-12";
	
	public static final String MANAGER_NOTEXISTSUSER2_CODE_DESC = "暂无小组成员";
	
	public static final String USERLOGINSTATUS="-13";
	
	public static final String USERLOGINSTATUS_DESC = "原密码与当前用户账户不匹配";
	
	public static final String EXPOREEXECLPATH="D:";
	
	public static final String FILESUFFIXINVALID = "-14";
	
	public static final String FILESUFFIXINVALID_DESC = "文件格式不对";
	
	public static final String FILESIZEINVALID = "-15";
	
	public static final String FILESIZEINVALID_DESC = "文件太大";
	
	public static final String MANAGER_REVIEWSTATUS_APPROVED_CODE = "-16";
	
	public static final String MANAGER_REVIEWSTATUS_APPROVED_CODE_DESC = "当月绩效考核已处于审核通过状态，您不能再次提交";
	
	public static final String MANAGER_NOTEXISTSMANAGERID_CODE = "-17";
	
	public static final String MANAGER_NOTEXISTSMANAGERID_CODE_DESC = "当前用户在组织架构中并末指定某个直属上司";
	
	public static final String SALARY_NOTEXISTSUSER_CODE = "-18";
	
	public static final String SALARY_NOTEXISTSUSER_CODE_DESC = "暂无数据";
	
	public static final String AMS_EXCEPTION_CODE = "-19";
	
	public static final String PHONE_HAD_EXIST = "-20";
	
	public static final String PHONE_HAD_EXIST_DESC = "手机号已存在";
	
	public static final String ADDRESS_HAD_EXIST = "-21";
	
	public static final String ADDRESS_HAD_EXIST_DESC = "地址已存在";
	
	public static final String AMS_EXCEPTION_CODE_DESC = "AMS接口异常";
	
	public static final String ZBXH_FAIL_CODE = "-30";//售后无AMS同步的主板型号管理的数据
	
	//支付方式
	public static final String WECHATPAY = "weChatPay";  //微信支付
	public static final String ALIPAY = "aliPay";  //支付宝支付
	public static final String PERSONPAY = "personPay";  //线下支付
	
	
	public static final String USERSTATUS0="0";//在职用户
	public static final String USERSTATUS1="1";//离职用户
	public static final String USERSTATUS2="2";//被删除用户
	/**
	 * 用户主键
	 */
	public static final String USER_MENUS_KEY="USER_MENUS";
	
	/**
	 * 系统错误
	 */
	public static final String SYSERROR="SYSERROR";
	
	/**
	 * 参数错误
	 */
	public static final String PARAMERROR="PARAMERROR";
	
	public static final String VALIDCODE="validCode";
	/**
	 * 验证码错误
	 */
	public static final String VALIDERROR="VALIDERROR";
	

	
	/**
	 * 资源根目录
	 */
	public static final String RESPATH="resource-root";
	
	public static final String TRUE="true";
	
	public static final String FALSE="false";

	/**
	 * app登录
	 */
	public static final String E0001="您输入的用户名或密码错误!";
	public static final String E0002="SSO system is busy, please try again later.";
	public static final String E0003="Parameter error, the incoming parameter is not UserInfo json type.";
	
	
	/**
	 * ams系统接口url
	 */
//	public static final String AMS_Goodsurl = "http://172.16.0.103:8765/ams-web/getOutGoods";//出货总数
//	public static final String AMS_Salesurl = "http://172.16.0.103:8765/ams-web/findSaleData";//查询销售数据
	
	//线上ams
	public static final String AMS_Goodsurl = "http://ams.jimicloud.com/ams-web/getOutGoods";//出货总数
	public static final String AMS_Salesurl = "http://ams.jimicloud.com/ams-web/findSaleData";//查询销售数据
}
