<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<meta name="format-detection" content="telephone=no"/>  
<meta name="format-detection" content="email=no" />
<title>几米终端客户服务</title>
<jsp:include page="/wechat/comm/comm.jsp" />  
<link rel="stylesheet" type="text/css" href="${ctx}/wechat/css/jquery.mobileModal.css">
<link rel="stylesheet" type="text/css" href="${ctx}/wechat/css/app.css">
<script type="text/javascript" src="${ctx}/wechat/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/wechat/js/jquery.mobileModal.js"></script>
<script type="text/javascript" src="${ctx}/res/js/jquery.cookie.js"></script>
</head>



<body class="login-bg">

  <div class="login">
  	<div class="logo">
		<img src="${ctx}/res/image/concox-logo-webcat.png" alt="" width="180">
	</div>
    <div id="bding-dev">
	    <input type="text" class="form-control" placeholder="手机号" id=loginPhone>
	    <input type="password" class="form-control" placeholder="密码" id="loginPwd">
   		<div style="font-size:1.7rem;text-align: -webkit-left; color: #FFF;">
			<label><input type="checkbox" id="checkbox" name="checkbox">&nbsp;记住密码(2周)</label>
		</div>
		<button type="button" id="bding">登录</button>
		<a href="javascript:;"  class="link"  onclick="getPwd();">忘记密码</a>
		<a class="link" >&nbsp;|&nbsp;</a>
		<a href="javascript:;"  class="link"  onclick="register()">立即注册</a>
		<!-- <a href="#" style="text-decoration:none; margin-left: 85px;" onclick="repairNumberLogin();"><font color="blue">返回</font><font color="red">批次登录</font></a> -->
    </div>
  </div>

<div class="mobile-modal" id="modal">
	<div class="mobile-dialog">
		<button  class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body">手机号或密码错误！</div>
		<div class="mobile-modal-footer" id="doTrue">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>

<div class="mobile-modal" id="modalTip">
	<div class="mobile-dialog">
		<button  class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body">手机号密码不能为空</div>
		<div class="mobile-modal-footer" id="doTrueTip">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>

<div class="mobile-modal" id="pwdTip">
	<div class="mobile-dialog">
		<button class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body"></div>
		<div class="mobile-modal-footer" id="getPwdTip">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>


<script type="text/javascript">
$(function(){
	// 初始化判断是否记住密码
	if ($.cookie("rmbUser") == "true") { 
		$("#checkbox").attr("checked", true); 
		$("#loginPhone").val($.cookie("loginPhone")); 
		$("#loginPwd").val($.cookie("loginPwd"));
	}else{
		$("#loginPhone").val("");
		$("#loginPhone").focus();
		$("#loginPwd").val(""); 
	}
	
	// 勾选记住密码
	$("#checkbox").click(function(){
		 save();
	});
});

// 记住用户名密码
function save() {
	if ($("#checkbox").prop("checked")) {
		var loginPhone = $("#loginPhone").val();
		var loginPwd = $("#loginPwd").val();
		$.cookie("rmbUser", "true", { expires: 14 }); 
		$.cookie("loginPhone", loginPhone, { expires: 14 });
		$.cookie("loginPwd", loginPwd, { expires: 14 });
	}else{
		$.cookie("rmbUser", "false", { expire: -1 });
		$.cookie("loginPhone", "", { expires: -1 });
		$.cookie("loginPwd", "", { expires: -1 });
	}
}

//询问框 
$("#bding").click(function(){
	var loginPhone  = $.trim($("#loginPhone").val());
	var loginPwd = $.trim($("#loginPwd").val());
	if(loginPwd!="" && loginPhone!=""){
		$.ajax({
			url : ctx+"/wechatdev/doWeChatPwdLogin",
			type : 'post', 
			async:false,
			dataType : 'json',
			data : {"loginPwd":loginPwd, "loginPhone":loginPhone},
			cache : false,
			success : function(returnData) {
				if(returnData.code==0){  
					window.location.href=ctx+"/wechat/repairNumberList.jsp";
				}else{
		        	$("#modal").mobileModal();
				}
			}
		});
	}else{
		$("#modalTip").mobileModal();
	}
});

// 输入错误确认框
$("#doTrue").click(function(){
	$("#modal").mobileModal("hide");
	$("#loginPwd").val("");
	$("#loginPhone").val("");
});

// 为空确认框
$("#doTrueTip").click(function(){ 
	$("#modalTip").mobileModal("hide");
});

//获取密码确认框
$("#getPwdTip").click(function(){ 
	$("#pwdTip").mobileModal("hide");
});

// 获取密码
function getPwd(){
	 var loginPhone = $.trim($("#loginPhone").val());
	 if(loginPhone && loginPhone.length == 11){
		$.ajax({
			url : ctx+"/custClient/getPwd",
			type : 'post', 
			async:false,
			dataType : 'json',
			data : {"phone" : loginPhone},
			cache : false,
			success : function(returnData) {
				if(returnData.code==0){  
					$("#pwdTip").find(".mobile-modal-body").text("密码已发送");
					$("#pwdTip").mobileModal();
				}else{
					$("#pwdTip").find(".mobile-modal-body").text("手机号错误");
					$("#pwdTip").mobileModal();
				}
			}
		});
	}else{
		$("#pwdTip").find(".mobile-modal-body").text("请输入正确的手机号");
		$("#pwdTip").mobileModal();
	}
}

//跳转注册页面
function register(){
	window.location.href=ctx+"/wechat/register.jsp";
}

/* // 跳转批次登录
function repairNumberLogin(){
	window.location.href=ctx+"/wechat/login.jsp";
} */
</script>
</body>
</html>