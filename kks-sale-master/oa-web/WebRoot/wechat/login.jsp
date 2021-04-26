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
</head>
<body class="login-bg">

  <div class="login">
  	<div class="logo">
		<img src="${ctx}/res/image/concox-logo-webcat.png" alt="" width="180">
	</div>
    <div id="bding-dev">
	    <input type="text" class="form-control" placeholder="手机号" id="cphone">
	    <input type="text" class="form-control" placeholder="送修批号" id="cnumber">
	    <button type="button" id="bding">登录</button>
	    <a href="javascript:;"  class="link" onclick="pwdLogin()">密码登录</a>
    </div>
  </div>

<div class="mobile-modal" id="modal">
	<div class="mobile-dialog">
		<button  class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body">送修批号或者手机号码错误！</div>
		<div class="mobile-modal-footer" id="doTrue">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>

<div class="mobile-modal" id="modalTip">
	<div class="mobile-dialog">
		<button  class="mobile-close" type="button"></button>
		<div class="mobile-modal-header">操作提示</div>
		<div class="mobile-modal-body">送修批号和手机号不能为空</div>
		<div class="mobile-modal-footer" id="doTrueTip">
			<a class="mobile-btn mobile-btn-block">确定</a>
		</div>
	</div>
</div>


<script type="text/javascript">
//询问框 
$("#bding").click(function(){
	var cnumber = $("#cnumber").val();
	var cphone  = $("#cphone").val();
	if(cnumber!="" && cphone!=""){
		$.ajax({
			url : ctx+"/wechatdev/doWeChatLogin",
			type : 'post', 
			async:false,
			dataType : 'json',
			data : {"cnumber":cnumber,"cphone":cphone},
			cache : false,
			success : function(returnData) {
				if(returnData.code==0){  
					window.location.href=ctx+"/wechat/orderList.jsp";
				}else{
		        	$("#modal").mobileModal();
				}
			}
		});
	}else{
		$("#modalTip").mobileModal();
	}
});

//确认框
$("#doTrue").click(function(){
	$("#modal").mobileModal("hide");
	$("#cnumber").val("");
	$("#cphone").val("");
});

//确认框
$("#doTrueTip").click(function(){ 
	$("#modalTip").mobileModal("hide");
});

// 跳转到密码登录
function pwdLogin(){
	window.location.href=ctx+"/wechat/pwdLogin.jsp";
}
</script>
</body>
</html>