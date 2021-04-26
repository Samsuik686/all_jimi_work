
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("ctx", request.getContextPath());
	session.setMaxInactiveInterval(60*60);
%>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<link href="${ctx}/res/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/res/js/themes/bootstrap/easyui.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/res/js/themes/default/easyui.css"/> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/res/js/themes/bootstrap/validatebox.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/res/js/themes/icon.css" />
<script type="text/javascript" src="${ctx}/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/res/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/res/js/managewin.js"></script>
<script type="text/javascript" src="${ctx}/res/script/validatebox.js"></script>
       
<script type="text/javascript" src="${ctx}/res/script/comm.js"></script>
<script type="text/javascript" src="${ctx}/res/js/yhhmap.js"></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<head></head>
<style type="text/css">
	.l-btn-text{font-size: 14px;}
	.l-btn-small .l-btn-text{font-size: 12px;}
</style>
<body>

	<!-- 发送测试选择测试人员 -->
	<div id="testUser_w" class="easyui-window" title="选择测试人员" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
		style="width:400px;height:320px;visibility: hidden; ">
		<div class="easyui-layout" fit="true">
			<div region="center">					
				<span>
					测试人员：<select id="test_user" style="width: 130px;"></select>
				</span>								
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a id="testuserAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a> 
				<a id="cancelTestUser" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" >取消</a>
			</div>
		</div>
	</div>
	<!-- END -->

</body>
<script type="text/javascript">
	

	var ctx="${ctx}";
	var rootPath=location.protocol+"//"+location.host+ctx+"/";
	
	var pageCount = 10;
	

 	/* 获取推送消息*/
 	function runTip(enumWS) {  
 		var url = ctx + "/tip/findTipNum";
 		var param="enumWS="+enumWS; 
 		asyncCallService(url, 'post', true,'json', param, function(returnData){
 			processSSOOrPrivilege(returnData);
 			if(returnData != '0'){
 				$("#showTip .number").html(returnData);
 				$("#showTip").show();
 			}else{
 				$("#showTip").hide();
 			}
 		});  
 	} 
	 
// 	/* 重置推送消息 */
 	function resetTip(enumWS) {  
 		var url = ctx + "/tip/resetTip";
 		var param="enumWS=" + enumWS; 
 		asyncCallService(url, 'post', true,'json', param, function(returnData){
 			processSSOOrPrivilege(returnData);
 			if(returnData != 'ok'){
 				$("#showTip").hide();
 			}
 		});  
 	} 
</script>
