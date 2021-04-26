<%@page import="sso.comm.util.JsonUtils"%>
<%@page import="sso.comm.model.UserInfo"%>
<%@page import="sso.comm.util.SSOUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<jsp:include page="page/comm.jsp"/>
<jsp:include page="page/OrganPerson.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>jquery easy ui 学习之——Accordion</title>

    <script type="text/javascript">
    	function test(){
 			 window.location.href=ctx+"/page/demo.jsp";
    	}
    </script>
</head>
<body>

<input value="测试" type="button" onclick="test()"/><br/>
选择用户：<input type="text" onclick="ManagerWindowOnOpen(this)"><br/>

</body>
</html> 
SSO用户查询样例：<br/>
 	SSOUtils utils=new SSOUtils();<br/>
	UserInfo info= utils.getUserInfo("lizhongjie");<br/>
	out.print(JsonUtils.beanToJson(info));<br/>
	
	
	<br/>
		<br/>
			<br/>
输出：<br/>
<%
	SSOUtils utils=new SSOUtils();
	UserInfo info= utils.getUserInfo("lizhongjie");
	out.print(JsonUtils.beanToJson(info));
%>
	<br/>	<br/>
	
	SSOUtils 包含了SSO系统中的所有操作方法。具体请自行查看。