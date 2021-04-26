<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	request.setAttribute("ctx", request.getContextPath());
	session.setMaxInactiveInterval(60*60);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<!-- bpmn-js modeler -->
<script src="${ctx}/res/js/bpmn/bower_components/bpmn-js/dist/bpmn-modeler.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>bpmn-js 绘制流程图</title>
<script type="text/javascript">
	var ctx="${ctx}";
</script>
<script type="text/javascript" src="${ctx}/res/js/jquery-1.8.0.min.js"></script>
<style type="text/css">
html, body, #canvas {
	height: 100%;
	padding: 0;
}

#remark{
	position: relative;
	
	top: 2px;
	right: 2px;
	border: 1px;
}

#inputDiv{
	position: absolute;
	top: 20px;
	right: 20px;
	border: solid 3px #dedede;
}
</style>
<link rel="stylesheet"
	href="${ctx}/res/js/bpmn/bower_components/bpmn-js/dist/assets/diagram-js.css">
<link rel="stylesheet"
	href="${ctx}/res/js/bpmn/bower_components/bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css">
</head>

<body>
	<input type="hidden" id="xml" value='${xml }' />
	<input type="hidden" id="forward" value="${forward }" />
	<!-- element to draw bpmn diagram in -->
	<div id="canvas"></div>
	
	<div id="inputDiv" style="background-color: grey;">
		<table>
			<tr>
				<td><span>备&nbsp;&nbsp;&nbsp;注：</span></td>
				<td><textarea id="remark" rows="3" style="width: 300px;"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><button id="save-button" >提交</button></td>
			</tr>
			<tr>
				<td >
					<div style="color: black;">注意：</div>
				</td>
			</tr>
			<tr align="left" >
				<td colspan="4">
					<div style="color: black;width: 300px;">&nbsp;&nbsp;
						1：现版本节点类型只支持开始（startEvnet），结束（endEvent），任务（Task）三种类型，以及连接各个节点的连线。
					</div>
				</td>
			</tr>
			<tr align="left">
				<td colspan="4">
					<div style="color: black;">&nbsp;&nbsp;2：开始，结束节点只能有一个。
					</div>
				</td>
			</tr>
		</table>
		
	</div>
<script src="${ctx}/res/js/bpmn/modeler.js?${num}"></script>
</body>
<!-- application -->
</html>
