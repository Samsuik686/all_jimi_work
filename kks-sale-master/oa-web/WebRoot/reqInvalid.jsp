<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

request.setAttribute("ctx", request.getContextPath());

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="${ctx}/res/js/jquery-1.6.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/js/jquery.easyui.min.js"></script>
    <script type="text/javascript">
    	function authenticationFailedClick(){
    				 $.ajax({
								type: "POST",
							  	cache: false,
							  	url: "${ctx }/oaLogout",
							  	dataType: "json",
							  	success : function(returnData){
							  		if(returnData.code=='-1'){
							  			location.href=returnData.msg;
							  		}else {//退出调用SSO异常
							  			alert(returnData.msg);
							  		}
								}, 
								error : function(XMLHttpRequest, textStatus, errorThrown){
									alert("错误:"+errorThrown);
							 	}
							}); 
    	}
    
    </script>
    
    <title>OA Request URI Invalid</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


	<style type="text/css">
	
			html, body
	        {
	            height: 100%;
	            overflow: auto;
	        }
		
			body {
				 padding: 0;
	             margin: 0;
				 background:#99CCCC;
				 color:#FFFFFF;
			}
		
			#wrap{
				width:100%;height:100%;position:relative;
			};
			
			<!--IE滤镜支持  -->
			.pngfix{
				width:100%;height:100%;
				filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${ctx}/res/image/authenticationFailed.jpg',sizingMethod='scale');
				background-repeat: no-repeat; 
				background-positon: 100%, 100%;
			}
			#backLoginPageId{
				position:relative;
				cursor:pointer;
			}
			
		
	</style>

  </head>
  
  <div id="wrap">
     <div class="pngfix">
	  <body style="background-image:url(${ctx}/res/image/authenticationFailed.jpg)"><!-- 非ie下加上style属性 -->
		  
			<table border="0" cellpadding="0" cellspacing="0" >
				  <tr> <td ><h4 >错误代码：</h4></td><td><h1 style="color:#99FFFF">404</h1></td></tr>
				  <tr> <td ><h4 >代码说明：</h4></td><td><h1 style="color:#99FFFF">当前用户请求地址无效</h1></td></tr>
				  <tr > <td ><h4 ></h4></td><td><input  id="backLoginPageId" type='button' value="返回到登录页" title="点击跳转到登录页" onclick="authenticationFailedClick()"/></td></tr>
			</table> 
		  
	  </body>
	  </div>
  </div>
</html>
