<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	request.setAttribute("ctx", request.getContextPath());
%>
<head>
<jsp:include page="/page/comm.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>几米终端客户服务</title>
<!--- CSS --->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/res/js/themes/bootstrap.min.css" />   --%>
<link rel="stylesheet" href="${ctx}/page/alipay/resource/css/layout.css" type="text/css" />
<script type="text/javascript" src="${ctx}/res/js/jquery.cookie.js"></script>
	<style type="text/css">
		footer{
			width: 100%;
			position: absolute;
			bottom: 0
		}
	</style>
</head>

<!-- class="bc-1E58AE" -->
<body class="gradiented login-bg" >
	<div class="load">
		<div class="content"> 
			<div class="input center-input"> 
				<div align="center" class="p-b20">
					<img src="${ctx}/page/alipay/resource/images/pc-login-logo.png"/>
					<div style="font-size: 30px;color: gray;">几米终端客服服务</div>
				</div>
				<!--  class="active" -->
				<ul id="tabs" class="nav login-mode clearfix">
			      <li><a href="#">批次登录</a></li>
			      <li><a href="#dn">密码登录</a></li>
			      <li><a href="#reg">注册</a></li>
			    </ul>
				<div id="con">
					<div>
						<form class="form-horizontal" method="post">
							<div class="form-group">
								<div class="col-md-12">
									<input type="text" id="batchPhone" name="batchPhone" placeholder="请输入手机号" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<input type="text" id="batchNumber" name="batchNumber" placeholder="请输入送修批号" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<div align="center" class="col-md-12">
									<input type="button" class="btn btn-default btn-block" value="登录" onclick="login();"/>
								</div>
							</div>
						</form>
					</div>
					<div class="dn">
						<form class="form-horizontal" method="post">
							<div class="form-group">
								<div class="col-md-12">
									<input type="text" id="loginPhone" name="loginPhone" placeholder="请输入手机号" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<input type="password" id="loginPassword" name="loginPassword" placeholder="请输入密码" class="form-control"/>
								</div>
								<div>
									<span><input type="checkbox" id="checkbox" name="checkbox"></span>&nbsp;<font color="#696969">记住密码(2周)</font>
									<a href="#" style="text-decoration:none; margin-left: 115px;" onclick="getPwd();"><font color="#696969">忘记密码？</font></a>
								</div>
							</div>
							<div class="form-group">
								<div align="center" class="col-md-12">
									<input type="button" class="btn btn-default btn-block" value="登录" onclick="login();"/>
								</div>
							</div>
						</form>
					</div>
					<div class="reg">
						<form class="form-horizontal" method="post">
							<div class="form-group-reg">
								<div class="col-md-12">
									<input type="text" id="phone" name="phone" placeholder="请输入手机号" class="form-control-reg" onblur="checkPhone();"/>
								</div>
							</div>
							<div class="form-group-reg">
								<div class="col-md-12">
									<input type="password" id="loginPwd" name="loginPwd" placeholder="请输入密码" class="form-control-reg"/>
								</div>
							</div>
							<div class="form-group-reg">	
								<div class="col-md-12">
									<input type="password" id="reLoginPwd" name="reLoginPwd" placeholder="请确认密码" class="form-control-reg" onblur="checkLoginPwd();"/>
								</div>
							</div>
							<div class="form-group-reg">	
								<div class="col-md-12">
									<input type="text" id="validateCode" name="validateCode" placeholder="请输入验证码" class="form-control-reg" style="width: 120px;"/>
									<input type="button" id="getValidateCode" style="background-color:#7CCD7C;" value="获取验证码"/>
								</div>
							</div>
							<div class="form-group-reg">
								<div align="center" class="col-md-12">
									<input type="button" class="btn btn-default btn-block" value="确定" onclick="register();"/>
								</div>
							</div>
						</form>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<div class="qr-code" style="width:150px;">
	  <img src="${ctx}/page/alipay/resource/images/KKS-SMS.jpg" alt="二维码"  width="150" height="150"/></br>
	  <span style="color: #ffffff; font-size: 14px; font-weight: bold;">
	  		微信扫描二维码，
			关注公众号，
			也可查询和付款哦！
	</span>
	</div>
	<footer id="footer">
		<p style="width:500px;margin:auto;font-size: 15px">深圳市康凯斯信息技术有限公司 2020 版权所有. <a href="https://beian.miit.gov.cn/" style="font-size: 15px;color: blue">粤ICP备11049713号</a></p>
	</footer>

	<script>
		var resend = ""; // 发送验证码倒计时
		$(function(){
			// 初始化判断是否记住密码
			if ($.cookie("rmbUser") == "true") { 
				$("#checkbox").attr("checked", true); 
				$("#loginPhone").val($.cookie("loginPhone")); 
				$("#loginPassword").val($.cookie("loginPassword"));
				$("#batchPhone").val($.cookie("loginPhone")); 
			}else{
				$("#loginPhone").val("");
				$("#loginPhone").focus();
				$("#loginPassword").val(""); 
				$("#batchPhone").val("");
			}
			
			// 勾选记住密码
			$("#checkbox").click(function(){
				 save();
			});

			// 防止刷新验证码失效
			 if($.cookie("captcha")){  
	            var count = $.cookie("captcha"); 
	         	$("#phone").val($.cookie("phone"));
       			$("#loginPwd").val($.cookie("loginPwd"));
       			$("#reLoginPwd").val($.cookie("reLoginPwd"));
       			$("#validateCode").val($.cookie("validateCode"));

	             if(count > 0){
           			setValidateCodeInterval(count, $('#getValidateCode'));
           			$("#phone").attr('disabled','disabled');
		          }else{
	        	  	clearValidateCodeInterval($('#getValidateCode'));
	        	  	$("#phone").removeAttr('disabled');
			     }
	        }  
			
			$("#getValidateCode").unbind('click').click(function(){
				$(this).attr('disabled','disabled');
				$(this).attr('style','background-color:#BEBEBE;');
				$("#phone").attr('disabled','disabled');
				getValidateCode($(this)); 
			});
			
		});

		 function tab(nav, active, con, fn) {
			  var e_navs = document.getElementById(nav).children;
			  var e_cons = document.getElementById(con).children;
			  for (var i = 0; i < e_navs.length; i++) {
			    e_navs[i].index = i;
			    e_navs[i].onclick = function() {
			      for (var j = 0; j < e_navs.length; j++) {
			        e_navs[j].className = '';
			        e_cons[j].style.display = 'none';
			      }
			      this.className = active;
			      e_cons[this.index].style.display = 'block';
			      if (typeof fn == 'function') {
			        fn();
			      }
			    }
			  }
			  e_navs[0].className = active;
			  e_cons[0].style.display = 'block';
		}
			
		tab('tabs', 'active', 'con');


		// 记住用户名密码
		function save() {
			if ($("#checkbox").prop("checked")) {
				var loginPhone = $("#loginPhone").val();
				var loginPassword = $("#loginPassword").val();
				$.cookie("rmbUser", "true", { expires: 14 }); 
				$.cookie("loginPhone", loginPhone, { expires: 14 });
				$.cookie("loginPassword", loginPassword, { expires: 14 });
			}else{
				$.cookie("rmbUser", "false", { expire: -1 });
				$.cookie("loginPhone", "", { expires: -1 });
				$.cookie("loginPassword", "", { expires: -1 });
			}
		}

		// 清空注册输入框 
		function initReg(){
			$("#phone").val("");
			$("#loginPwd").val("");
			$("#reLoginPwd").val("");
			$("#validateCode").val("");
		}

		// 验证码倒计时
		function setValidateCodeInterval(count, obj){ 
			resend = setInterval(function(){
	            count--;  
	            if (count > 0){  
	            	obj.val(count+'秒后可重新获取');  
	                obj.attr('disabled','disabled');
					obj.attr('style','background-color:#BEBEBE;');
					$("#phone").attr('disabled','disabled');
	                $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});  
                    $.cookie("phone", $.trim($("#phone").val()), {expires: (1/86400)*count});
                    $.cookie("loginPwd", $.trim($("#loginPwd").val()), {expires: (1/86400)*count});
                    $.cookie("reLoginPwd", $.trim($("#reLoginPwd").val()), {expires: (1/86400)*count});
                    $.cookie("validateCode", $.trim($("#validateCode").val()), {expires: (1/86400)*count});
	            }else{
	            	clearValidateCodeInterval($('#getValidateCode'));
	        	  	$("#phone").removeAttr('disabled');
	            }  
			}, 1000); 
        } 

		// 停止验证码倒计时
        function clearValidateCodeInterval(obj){
        	$.cookie("captcha", null, {path: '/'});  
            clearInterval(resend);  
            obj.val("获取验证码");
            obj.removeAttr('disabled');
            obj.attr('style','background-color:#7CCD7C;') 
            $("#phone").removeAttr('disabled');
        }

		// 验证手机号
		function checkPhone(){
			var params = {"phone" : $.trim($("#phone").val())};
			var url = ctx + "/custClient/checkRegister";
			asyncCallService(url, 'post', false, 'json', params, function(returnData) {
				processSSOOrPrivilege(returnData);
				if (returnData.code == '-500') {
					$.messager.alert("操作提示", returnData.msg, "info", function(){
						$("#phone").val("");
					});
				}
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
		}

		// 验证密码
		function checkLoginPwd(){
			var loginPwd = $.trim($("#loginPwd").val());
			var reLoginPwd = $.trim($("#reLoginPwd").val());
			if(loginPwd){
				if(loginPwd.length <6){
					$.messager.alert("操作提示", "密码不能小于6位", "info");
				}else{
					if(loginPwd !=reLoginPwd){
						$.messager.alert("操作提示", "两次密码不一致", "info");
					}
				}
			}else{
				$.messager.alert("操作提示", "密码不能为空", "info");
			}
		}

		// 获取验证码
		function getValidateCode(obj){
			var phone = $.trim($("#phone").val());
			if(phone && phone.length == 11){
				var params = {"phone" : $.trim($("#phone").val())};
				var url = ctx + "/custClient/getValidateCode";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", "验证码发送成功", "info", function(){
							var count = 120;
						 	 if(count > 0){
			           			 setValidateCodeInterval(count, $('#getValidateCode'));
					          }else{
				        	  	clearValidateCodeInterval($('#getValidateCode'));
						     }
						});
						return false;
					}else{
						$.messager.alert("操作提示", returnData.msg, "info", function(){
							clearValidateCodeInterval($('#getValidateCode'));
						});
						return false;
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function(){
						clearValidateCodeInterval($('#getValidateCode'));
					});
				});
			}else{
				$.messager.alert("操作提示", "请输入正确的手机号", "info", function(){
					clearValidateCodeInterval($('#getValidateCode'));
				});
			}
		}

		// 注册
		function register(){
			if(validateForm()){
				var params = {
						"phone" : $.trim($("#phone").val()),
						"loginPwd" : $.trim($("#loginPwd").val()),
						"validateCode" : $.trim($("#validateCode").val())
						};
				var url = ctx + "/custClient/register";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", "注册成功", "info", function(){
							initReg();
							clearValidateCodeInterval($('#getValidateCode'));
							$("ul li:eq(1) a").unbind('click').click();
						});
					}else{
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		}

		// 注册验证
		function validateForm(){
			 var phone = $.trim($("#phone").val());
			 var loginPwd = $.trim($("#loginPwd").val());
			 var reLoginPwd = $.trim($("#reLoginPwd").val());
			 var validateCode =  $.trim($("#validateCode").val());
			 if(!phone){
				 $.messager.alert("操作提示", "请填写手机号", "info");
				 return false;
			}else{
				if(loginPwd.length <6){
					$.messager.alert("操作提示", "密码不能小于6位", "info");
					 return false;
				}else{
					if(!loginPwd || !reLoginPwd){
						 $.messager.alert("操作提示", "请填写密码", "info");
						 return false;
					}else if(loginPwd != reLoginPwd){
						 $.messager.alert("操作提示", "密码不一致", "info");
						 return false;
					}else{
						if(!validateCode){
							 $.messager.alert("操作提示", "请填写验证码", "info");
							 return false;
						}
					}
				}
			}
			return true;
		}

		// 登录
		function login(){
			 var batchPhone = $.trim($("#batchPhone").val());
			 var batchNumber = $.trim($("#batchNumber").val());
			 var loginPhone = $.trim($("#loginPhone").val());
			 var loginPassword = $.trim($("#loginPassword").val());

			 if((batchPhone && batchNumber) || (loginPhone && loginPassword)){
				 var params = {
						"batchPhone" : batchPhone,
						"batchNumber" : batchNumber,
						"loginPhone" : loginPhone,
						"loginPassword" : loginPassword
					};
				var url = ctx + "/custClient/getAiPaytList";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						 window.location.href=ctx+"/page/alipay/ClientIndex.jsp";
					}else{
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}else{
				$.messager.alert("操作提示", "输入信息不完整", "info");
			}
		}

		// 获取密码
		function getPwd(){
			 var loginPhone = $.trim($("#loginPhone").val());
			 if(loginPhone && loginPhone.length == 11){
				var params = {"phone" : $.trim($("#loginPhone").val())};
				var url = ctx + "/custClient/getPwd";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", "密码已发送成功", "info");
					}else{
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}else{
				$.messager.alert("操作提示", "请输入正确的手机号", "info");
			}
		}
	</script>
</body>
</html>