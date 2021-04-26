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
  	<div class="logo logo-min">
		<img src="${ctx}/res/image/concox-logo-webcat.png" alt="" width="180">
	</div>
    <div id="bding-dev">
	    <input type="text" class="form-control" placeholder="手机号" id="loginPhone" onblur="checkPhone()">
	    <input type="password" class="form-control" placeholder="密码" id="loginPwd">
	    <input type="password" class="form-control" placeholder="确认密码" id="reLoginPwd" onblur="checkLoginPwd();">
	    <input type="text" class="form-control" placeholder="验证码" id="validateCode" style="width: 58%;">
	    <input type="button" class="form-control" id="getValidateCode" style="background-color:#7CCD7C; width: 40%;" value="获取验证码"/>
	    <input type="button" class="form-control" id="register" value="注册" onclick="register();">
	   
    </div>
    <div>
     <a href="javascript:;"  class="link"  onclick="backRepairNumberLogin()">批次登录</a></div>
  </div>

<div class="mobile-modal" id="modalTip">
	<div class="mobile-dialog">
		<button  class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body"></div>
		<div class="mobile-modal-footer" id="doTrueTip">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>

<div class="mobile-modal" id="registerTip">
	<div class="mobile-dialog">
		<button  class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body"></div>
		<div class="mobile-modal-footer" id="registerSuccessTip">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>

<script type="text/javascript">
	var resend = ""; // 发送验证码倒计时
$(function(){
	// 防止刷新验证码失效
	 if($.cookie("captcha")){  
        var count = $.cookie("captcha"); 
   		$("#loginPhone").val($.cookie("loginPhone"));
		$("#loginPwd").val($.cookie("loginPwd"));
		$("#reLoginPwd").val($.cookie("reLoginPwd"));
		$("#validateCode").val($.cookie("validateCode"));

        if(count > 0){
  			setValidateCodeInterval(count, $('#getValidateCode'));
  			$("#loginPhone").attr('disabled','disabled');
         }else{
   	  		clearValidateCodeInterval($('#getValidateCode'));
   	  		$("#loginPhone").removeAttr('disabled');
	     }
   	}  
	
	$("#getValidateCode").unbind('click').click(function(){
		$(this).attr('disabled','disabled');
		$(this).attr('style','background-color:#BEBEBE; width: 40%;');
		$("#loginPhone").attr('disabled','disabled');
		getValidateCode($(this)); 
	});
});

//清空注册输入框 
function initReg(){
	$("#loginPhone").val("");
	$("#loginPwd").val("");
	$("#reLoginPwd").val("");
	$("#validateCode").val("");
}

// 验证码倒计时
function setValidateCodeInterval(count, obj){ 
	resend = setInterval(function(){
        count--;  
        if (count > 0){  
        	obj.val(count+'秒');  
            obj.attr('disabled','disabled');
			obj.attr('style','background-color:#BEBEBE; width: 40%;');
			$("#loginPhone").attr('disabled','disabled');
            $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});  
            $.cookie("phone", $.trim($("#loginPhone").val()), {expires: (1/86400)*count});
            $.cookie("loginPwd", $.trim($("#loginPwd").val()), {expires: (1/86400)*count});
            $.cookie("reLoginPwd", $.trim($("#reLoginPwd").val()), {expires: (1/86400)*count});
            $.cookie("validateCode", $.trim($("#validateCode").val()), {expires: (1/86400)*count});
        }else{
        	clearValidateCodeInterval($('#getValidateCode'));
   	  		$("#loginPhone").removeAttr('disabled');
        }  
	}, 1000); 
} 

// 停止验证码倒计时
function clearValidateCodeInterval(obj){
	$.cookie("captcha", null, {path: '/'});  
    clearInterval(resend);  
    obj.val("获取验证码");
    obj.removeAttr('disabled');
    obj.attr('style','background-color:#7CCD7C; width: 40%;') 
    $("#loginPhone").removeAttr('disabled');
}

// 验证手机号
function checkPhone(){
	var params = {"phone" : $.trim($("#loginPhone").val())};
	$.ajax({
		url : ctx+"/custClient/checkRegister",
		type : 'post', 
		async:false,
		dataType : 'json',
		data : params,
		cache : false,
		success : function(returnData) {
			if (returnData.code == '-500') {
				$("#modalTip").find(".mobile-modal-body").text(returnData.msg);
				$("#modalTip").mobileModal();
				$("#loginPhone").val("");
			}
		}
	});
}

// 验证密码
function checkLoginPwd(){
	var loginPwd = $.trim($("#loginPwd").val());
	var reLoginPwd = $.trim($("#reLoginPwd").val());
	if(loginPwd){
		if(loginPwd.length <6){
			$("#modalTip").find(".mobile-modal-body").text("密码不能小于6位");
			$("#modalTip").mobileModal();
		}else{
			if(loginPwd !=reLoginPwd){
				$("#modalTip").find(".mobile-modal-body").text("两次密码不一致");
				$("#modalTip").mobileModal();
			}
		}
	}else{
		$("#modalTip").find(".mobile-modal-body").text("密码不能为空");
		$("#modalTip").mobileModal();
	}
}

// 获取验证码
function getValidateCode(obj){
	var phone = $.trim($("#loginPhone").val());
	if(phone && phone.length == 11){
		var params = {"phone" : $.trim($("#loginPhone").val())};
		$.ajax({
			url : ctx+"/custClient/getValidateCode",
			type : 'post', 
			async:false,
			dataType : 'json',
			data : params,
			cache : false,
			success : function(returnData) {
				if (returnData.code == '0') {
					$("#modalTip").find(".mobile-modal-body").text("验证码发送成功");
					$("#modalTip").mobileModal();
					var count = 120;
				 	 if(count > 0){
	           			 setValidateCodeInterval(count, $('#getValidateCode'));
			          }else{
		        	  	clearValidateCodeInterval($('#getValidateCode'));
				     }
					return false;
				}else{
					$("#modalTip").find(".mobile-modal-body").text(returnData.msg);
					$("#modalTip").mobileModal();
					clearValidateCodeInterval($('#getValidateCode'));
					return false;
				}
			}, 
			error: function(e) { 
				$("#modalTip").find(".mobile-modal-body").text("获取验证码错误");
				$("#modalTip").mobileModal();
				clearValidateCodeInterval($('#getValidateCode'));
			} 
		});
	}else{
		$("#modalTip").find(".mobile-modal-body").text("请输入正确的手机号");
		$("#modalTip").mobileModal();
		clearValidateCodeInterval($('#getValidateCode'));
	}
}

// 注册
function register(){
	if(validateForm()){
		var params = {
				"phone" : $.trim($("#loginPhone").val()),
				"loginPwd" : $.trim($("#loginPwd").val()),
				"validateCode" : $.trim($("#validateCode").val())
			};
		$.ajax({
			url : ctx+"/custClient/register",
			type : 'post', 
			async:false,
			dataType : 'json',
			data : params,
			cache : false,
			success : function(returnData) {
				if (returnData.code == '0') {
					$("#registerTip").find(".mobile-modal-body").text("注册成功");
					$("#registerTip").mobileModal();
				}else{
					$("#modalTip").find(".mobile-modal-body").text(returnData.msg);
					$("#modalTip").mobileModal();
				}
			}
		});
	}
}

// 注册验证
function validateForm(){
	 var phone = $.trim($("#loginPhone").val());
	 var loginPwd = $.trim($("#loginPwd").val());
	 var reLoginPwd = $.trim($("#reLoginPwd").val());
	 var validateCode =  $.trim($("#validateCode").val());
	 if(!phone){
		 $("#modalTip").find(".mobile-modal-body").text("请填写手机号");
		 $("#modalTip").mobileModal();
		 return false;
	}else{
		if(loginPwd.length <6){
			$("#modalTip").find(".mobile-modal-body").text("密码不能小于6位");
			$("#modalTip").mobileModal();
			 return false;
		}else{
			if(!loginPwd || !reLoginPwd){
				$("#modalTip").find(".mobile-modal-body").text("请填写密码");
				$("#modalTip").mobileModal();
				 return false;
			}else if(loginPwd != reLoginPwd){
				$("#modalTip").find(".mobile-modal-body").text("密码不一致");
				$("#modalTip").mobileModal();
				 return false;
			}else{
				if(!validateCode){
					$("#modalTip").find(".mobile-modal-body").text("请填写验证码");
					$("#modalTip").mobileModal();
					return false;
				}
			}
		}
	}
	return true;
}

// 除注册成功外其他确定框
$("#doTrueTip").click(function(fn){ 
	$("#modalTip").mobileModal("hide");
});

// 注册成功 清空注册页面，停止计时器，跳转到登录页面
$("#registerSuccessTip").click(function(){ 
	$("#registerTip").mobileModal("hide");
	initReg();
	clearValidateCodeInterval($('#getValidateCode'));
	window.location.href=ctx+"/wechat/pwdLogin.jsp";
});

// 跳转批次登录
function backRepairNumberLogin(){
	window.location.href=ctx+"/wechat/login.jsp";
}
</script>
</body>
</html>