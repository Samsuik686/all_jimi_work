<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/sxdwmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="送修单位管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px"> 
					<span>客户名称: <input type="text" class="form-search" id="searchByCusName" style="width: 150px" /> </span>&nbsp;
					<span>手机号: <input type="text" class="form-search" id="searchByPhone" style="width: 150px" /> </span>&nbsp;  
					<span>
						禁用
						<select id="searchByEnabledFlag" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>&nbsp;
					</span>
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="sxdw_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="sxdw_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="sxdw_deleteInfo();">删除</a>
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
						<th checkbox="true" field="cId"></th>
						<th field="cusName" width="150" align="center">客户名称</th>
						<th field="serviceTime" width="60" align="center">服务周期</th>
						<th field="linkman" width="80" align="center">联系人</th>
						<th field="phone" width="120" align="center">手机号</th>
						<th field="telephone" width="120" align="center">座机号</th>
						<th field="email" width="150" align="center">邮箱</th>
						<th field="fax" width="120" align="center">传真</th>
						<th field="address" width="300" align="center">通信地址</th>
						<th field="enabledFlag" width="60" align="center">禁用</th>
						<th field="createBy" width="60" align="center">创建人</th>
						<th field="createTime" width="120" align="center">创建时间</th>
						<th field="updateBy" width="60" align="center">修改人</th>
						<th field="updateTime" width="120" align="center">修改时间</th>
						<th field="remark" width="220" align="center">备注</th> 
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">	
			<div id="updateWindow" class="easyui-window" title="更新送修单位信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="cId_hidden"/>
									</td>
								</tr>
								<tr>
									<td>客户名称：</td>
									<td colspan="3">
										<input type="text" id="cusNameP" style="width: 100%;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;联&nbsp;系&nbsp;人：</td>
									<td>
										<input type="text" id="linkmanP" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
									<td>服务周期：</td>
									<td>
										<input type="text" id="serviceTimeP" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;手&nbsp;机&nbsp;号：</td>
									<td>
										<input type="text" id="phoneP" style="width: 150px;" class="easyui-validatebox" data-options="required:true,validType:['Mobile','phoneCheck']" missingMessage="请填写此字段" />
									</td>
									<td>&nbsp;座&nbsp;机&nbsp;号：</td>
									<td>
										<input type="text" id="telephoneP" style="width: 150px;" />
									</td>
								</tr>
								<tr>
									<td>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
									<td>
										<input type="text" id="emailP" style="width: 150px;" class="easyui-validatebox" validType="Email" />
									</td>
									<td>传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
									<td>
										<input type="text" id="faxP" style="width: 150px;" class="easyui-validatebox" validType="Fax">
									</td>
								</tr>
								<tr>
									<td>&nbsp;创&nbsp;建&nbsp;人</td>
									<td>
										<input type="text" id="createBy" style="width: 150px;" disabled="disabled"/>
									</td>
									<td>创建时间：</td>
									<td>
										<input type="text" id="createTime" style="width: 150px;" disabled="disabled">
									</td>
								</tr>
								<tr>
									<td>&nbsp;修&nbsp;改&nbsp;人</td>
									<td>
										<input type="text" id="updateBy" style="width: 150px;" disabled="disabled"/>
									</td>
									<td>修改时间：</td>
									<td>
										<input type="text" id="updateTime" style="width: 150px;" disabled="disabled">
									</td>
								</tr>
								<tr>
									<td>是否禁用：</td>
									<td align="left">
										<input type="radio" name="enabledFlag" value="0" checked="checked"/>否 &nbsp; 
										<input type="radio" name="enabledFlag" value="1"/>是
									</td>
								</tr>
								<tr>
									<td>通信地址：</td>
									<td colspan="7">
										<textarea id="addressP" style="width: 100%; height: 80px;" class="easyui-validatebox" data-options="required:true,validType:'addressCheck'" missingMessage="请填写此字段"></textarea>
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7">
										<textarea id="remarkP" style="width: 100%; height: 80px;" maxlength="255"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="sxdwmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="sxdwmanageSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>