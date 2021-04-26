<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
	/* 获取流程名称 */
	var flowName = '<% out.print(new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8")); %>';
	/* id为0代表开始页面 */
	var modelId = '<% out.print(request.getParameter("id")); %>';
	<% 
		request.setAttribute("flowName", new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8")); 
		request.setAttribute("modelId", request.getParameter("id"));
	%>	
</script>

<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/feedbackManage/customerReception.js?20170308"></script>
<script type="text/javascript" src="${ctx}/res/js/customFlow/customModel.js?${num}"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<%-- <script type="text/javascript" src="${ctx}/res/js/basicdata/ycfk.tree/ycfkTree.js"></script> --%>
<script type="text/javascript" src="${ctx}/res/js/ajaxfileupload.js"></script>

<style type="text/css">
	.fileInfo a:link{
		text-decoration: underline; 
	}
	.fileInfo a{
		color:blue;
	}
	.fileInfo a:hover{
		color:red;
	}
	.fileInfo .panel-tool-close { 
		vertical-align: top; 
		border:0; 
		box-shadow:none; 
		height:18px; outline:none;     
	}
</style>
</head>
<!-- 客服中心 -->
<body style="margin: 0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/>
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
		<!-- <div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="ycfkTreeTime" class="easyui-tree">
			</ul>
		</div> -->
		<div region="center" id="dic-center" style="overflow: auto" title="${flowName }">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:5px">	
					<span>流水号: <input type="text" class="form-search" id="serial_search" style="width: 135px" /></span>&nbsp;
					<span>反馈人: <input type="text" class="form-search" id="creater_search" style="width: 135px" /></span>&nbsp; 
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px" /> </span>&nbsp; 
					<span>结束日期: <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px" /> </span>&nbsp;
					<span>
						本工站状态:&nbsp;
						<select id="state_search"  style="width: 100px;" panelHeight="auto" >
							<option value="">全部</option>
							<option value="0" selected="selected">待解决</option>
							<option value="1">已完成</option>
							<option value="2">已过期</option>
						</select>&nbsp;
					</span> 
					<span><a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="javascript:void(0)">查询</a> </span>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr id="${modelId} ">
							<c:if test="${not empty modelId and modelId eq '0'}">
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="customTask_addInfo();">增加</a>															
							</td>
							</c:if>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="customTask_updateInfo();">修改</a>						
								<div class="datagrid-btn-separator"></div>
							</td>
							<c:if test="${not empty modelId and modelId eq '0'}">
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="customTask_deleteInfo();">删除</a>							
								<div class="datagrid-btn-separator"></div>
							</td>	
							</c:if>						
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-plfszfjicon" plain="true" onclick="sendDataToNextSiteOpen();">发送下一工站</a>							
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>							
								<div class="datagrid-btn-separator"></div>
							</td>	 				
							<td>
								<div class="datagrid-btn-separator"></div>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
							<c:if test="${not empty modelId and modelId eq '0'}">
							<td>
								<div class="datagrid-btn-separator"></div>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="openCustomField();">自定义字段</a>
							</td>
							</c:if>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
			</table>
		</div>
		<div style="position:fixed;">
			<!-- 添加-START-->
			<div id="addCustomTask" class="easyui-window" title="新增${flowName }" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
				style="visibility:hidden; width: 650px; height: 480px; padding: 2px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 2px; background: #fff; border: 1px solid #ccc;">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="id" value="" />
										<input type="hidden" id="flowName" value="${flowName }" />
										<input type="hidden" id="modelId" value="${modelId }" />
										<span id="userName" style="display: none;">${USERSESSION.uName}</span>
									</td>
								</tr>
								<tr>
									<td>反馈人：</td>
									<td>
										<span id="createrSpan" style="display: none;">${USERSESSION.uName}</span>
										<input type="text" id="creater" style="width: 200px" readonly="true"/>
									</td>
									<!-- <td>&nbsp;&nbsp;跟&nbsp;进&nbsp;人：</td>
									<td>
										<input type="text" id="follower" style="width: 200px" disabled="disabled" />
									</td> -->
								</tr>								
								<tr>
									<td>备注：</td>
									<td colspan="">
										<textarea  id="remark"  rows="" cols="72" class="easyui-validatebox"></textarea>
									</td>									
								</tr>
								<tr>
									<td>附件：</td>
							     	<td colspan="3">
							     		<div id="descriptionFileDiv" class="fileInfo">						     		 	
							     		</div>
							     		<div id="fileDiv"> 								     			
							     		</div>						     		 
							     	</td>
								</tr>
							</table>
							<HR style="FILTER:progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="85%" color="#987cb9" SIZE="5"/>
							<table id="paramsTable" align="left" style="padding-left: 48px;">
								
							</table>
						</form>
					</div>
						<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
							<a id="ycfkAddOrUPdate" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="customTaskSave()">保存</a> 
							<a id="ycfkClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseYcfkTwomanage()">关闭</a>
						</div>
				</div>
			</div>
			<!-- 添加-END-->
		</div>
									
		<!-- 异常反馈发送数据到下一个工站  Start -->
		<div id="nextSiteUser_w" class="easyui-window" title="完成并发送下一工站" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
			style="width:600px;height:420px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" style="padding-top: 5px;padding-left: 5px;">	
				<table>
					<tr>
						<td>
							<span>
								下一工站：<select id="next_site" style="width: 130px;">
											<option value="" selected="selected">请选择</option>
									   </select>
							</span>	
						</td>
						<br><br><br>
						<span>
						跟进人员：<input id = "followup_user" type="text" style="width: 300px;"/>
					</span>
					</tr>
					<tr>
						<td>
							<span>
								过期时间：
								<input type="text" class="form-search" id="expireDateStr" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width: 135px" />
							</span>
						</td>
					</tr>
				</table>
															
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="nextuserAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="customTaskSendData()">确定</a> 
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('nextSiteUser_w')">取消</a>
				</div>
			</div>
		</div>
		<!-- 异常反馈发送数据到下一个工站  END -->
		
		<!-- 自定义字段  Start -->
		<div id="customFieldModify" class="easyui-window" title="自定义字段" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
			style="width:600px;height:420px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" style="padding-top: 5px;padding-left: 5px;">
					<table id="customFieldTable">

					</table>
													
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="saveCUstomField" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveCustomField()">确定</a> 
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('customFieldModify')">取消</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>