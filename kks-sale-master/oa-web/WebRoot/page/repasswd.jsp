<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<jsp:include page="/page/comm.jsp"/>
<jsp:include page="/page/OrganPerson.jsp"/>
<html>
  <head>
    <title>密码重置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="${ctx }/res/css/repasswd.css" rel="stylesheet" type="text/css" />
	
  </head>
  
  <body>
  	 <div class="coupon_right">
			<div class="courht">
				<div class="courht_title">
					<span>密码重置</span>
				</div>
				<div class="modify">
					<h1>1、选择员工</h1>
					<h3>2、重置密码</h3>
				</div>
				<div class="modify1">
				<form action="${ctx}/user/repasswd" method="post" id="rePasswd">
						<div class="forgotpassword02_text">
							<div class="forgotpassword02_text01">
								请选择员工：<input id="uName" onclick="ManagerWindowOnOpenAndId(this,$('#uId'))" maxlength="4" type="text" readonly="readonly" class="login0202_text1input1"/> </br>
								<p><span>*</span> 请您确定选择的员工是否需要重置密码，确认后，点击重置密码按钮，您将会收到是否成功的提示。</p>
								<input type="hidden" name="uId" id="uId"> 
							</div>
							<div class="forgotpassword02_text02">
								<input value="重置密码" type="button" class="btn-130-30" onclick="rePasswd()"/>
								<span style="color: red;"> ${msg}</span>
							</div>
							<div class="forgotpassword02_text03">
								注意事项？
							</div>
							<div class="forgotpassword02_text04">
								<ul>
									<li>密码重置操作一经成功，不可逆转，请您谨慎操作。</li>
									<li>密码重置后，初始密码为：1111</li>
								</ul>
							</div>
						</div>
				</form>
				</div>
			</div>
		</div>
		<script type="text/javascript">
 		$.messager.defaults= { ok: "确定",cancel: "取消" };
		function rePasswd(){
			var uId=$("#uId").val();
			var uName=$("#uName").val();
			if(null==uId||""==uId||null==uName||""==uName){
			
				$.messager.alert("操作提示","请先选择需要重置密码的员工。","info");
				
			}else{
				$.messager.confirm("系统提示:", "您确定要需要重置员工  "+uId+" 的密码？",function(data){
					if(data){
						document.getElementById("rePasswd").submit();
					}
				});
			}
			
		}
	</script>
  </body>
</html>
