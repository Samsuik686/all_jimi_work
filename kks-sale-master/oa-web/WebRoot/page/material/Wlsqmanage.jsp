<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/material/wlsqmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow:hidden; width: 100%;" fit="true" scroll="no">
		<div region="center" id="dic-center" style="overflow: auto" title="物料申请管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>申请人: <input type="text" class="form-search" id="searchByApplicater" style="width: 135px"></span>&nbsp;
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> </span>&nbsp;
					<span>结束日期: <input type="text" class="form-search" id="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pficon" plain="true" onclick="wlsq_appInfo();">批复</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xzmbicon" plain="true" onclick="downloadTemplet();">下载模板</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daoruicon" plain="true" onclick="importInfo();">导入</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">		   
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="platform" width="160px" align="center">平台</th>
						<th field="matType" width="130px" align="center">物料类型</th>
						<th field="proNO" width="160px" align="center">物料编码</th>
						<th field="proName" width="200px" align="center">物料名称</th>
						<th field="proSpeci" width="280px" align="center">物料规格</th>
						<th field="number" width="80px" align="center">申请数量</th>
						<th field="unit" width="80px" align="center">单位</th>
						<th field="applicater" width="100px" align="center">申请人</th>
						<th field="appTime" width="160px" align="center">申请日期</th>
						<th field="purpose" width="220px" align="center">申请用途</th>
						<th field="state" width="80px" align="center">批复</th>
						<th field="remark" width="220px" align="center">备注</th>			
					</tr>
				</thead>
			</table>
			</div>
		<div style="position:fixed;">
				<div id="wlAppWindow" class="easyui-window" title="物料申请批复" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
					  style="visibility:hidden; width: 550px; height: 500px; padding: 5px;">
					<div class="easyui-layout" fit="true">
						<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
							<form id="w1Apply_Form" action="">
								<table align="center">
									<tr>
										<input id="id"  type="text"  style="display: none;"/>
									</tr>
									<tr>
										<td>物料类型</td>
										<td><input type="text" id="matType" disabled="disabled"></td>
										<td>&nbsp;平&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;台：</td>
										<td><input type="text" id="platform"/></td>
									</tr>
									<tr>
									
										<td>物料编码：</td>
										<td colspan="3"><input type="text" id="proNO" disabled="disabled" style="width: 100%"></td>
									</tr>
									<tr>
										<td>物料名称：</td>
										<td colspan="3"><input type="text" id="proName" disabled="disabled" style="width: 100%"></td>
									</tr>
									<tr>
										<td>物料规格：</td>
										<td colspan="3"><input type="text" id="proSpeci" disabled="disabled" style="width: 100%"></td>
									</tr>
									<tr>
										<td>申请数量：</td>
										<td><input type="text" id="number" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段"></td>
										<td>&nbsp;单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
										<td><input type="text" id="unit"></td>
									</tr>
									<tr>
										<td>&nbsp;申&nbsp;请&nbsp;人：</td>
										<td><input type="text" id="applicater" disabled="disabled"/></td>
										<td>申请日期：</td>
										<td><input type="text" id="appTime" disabled="disabled"/></td>
									</tr>
									<tr>
										<td>申请用途：</td>
										<td colspan="7"><textarea id="purpose" disabled="disabled" style="width: 100%; height: 80px; background-color: #DDD;" maxlength="255"></textarea></td>
									</tr>
									<tr>
										<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
										<td colspan="7"><textarea id="remark" style="width: 100%;  height: 80px;" maxlength="255"></textarea></td>
									</tr>
									<tr>
										<td>&nbsp;批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;复：</td>
										<td>
											<input type="radio" name="state" value="0"/>同意
											<input type="radio" name="state" value="1"/>不同意
										</td>	
									</tr> 
								</table>
							</form>
						</div>
						<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="xlAppSave()">保存</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="wlAppWindowClose()">关闭</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>