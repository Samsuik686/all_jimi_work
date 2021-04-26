<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/fileManage/productMaterialFileLog.js?20170414"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
	<body style="margin:0">
		<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
			<div region="center" id="dic-center" style="overflow: auto" title="下载日志记录表">
				<div id="toolbar" style="padding: 10px 15px;">
					<!-- 查询条件 -->
					<div style="margin-bottom:2px">
						<span>&nbsp;文&nbsp;件&nbsp;名：<input type="text" id="searchByFileName" class="form-search" style="width: 135px"/> </span>
						<span>&nbsp;下&nbsp;载&nbsp;人：<input type="text" id="searchByUserName" class="form-search" style="width: 135px"/> </span>
						<span>&nbsp;I&nbsp;P：<input type="text" id="searchByIp" class="form-search" style="width: 150px" placeholder="精确匹配"/> </span>
					</div>
					<div style="margin-bottom:5px">	
						<span>开始时间：<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>
						<span>结束时间：<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> </span>
						<span>&nbsp;&nbsp;
							<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" onclick="queryListPage('');" >查询</a>
						</span>
					</div>
					
					<div>					
                   		 <table cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daoruicon" plain="true" onclick="exportInfo();">导出</a>										
									</td>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a>
										<div class="datagrid-btn-separator"></div>
									</td>
								</tr>
						 	</tbody>
						</table>				
					</div>
				</div>
				
				<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
					<thead>
						<tr>
							<th checkbox="true" field="id" align="center">id</th>
							<th field="ip"  align="center" width="100">IP</th>
							<th field="userName"  align="center" width="100">下载人</th>
							<th field="fileName"  align="center" width="400">文件名</th>
							<th field="fileType"  align="center" width="100">类型</th>
							<th field="downloadTime"  align="center" width="130">下载时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</body>
</html>













