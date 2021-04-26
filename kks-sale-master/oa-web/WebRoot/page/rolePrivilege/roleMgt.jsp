<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 
<script type="text/javascript" src="${ctx}/res/js/rolePrivilege/roleMgt.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>

</head>

<body style="margin:0">
	<div class="easyui-layout"
		style="overflow: hidden; width:100%;height:100%" fit="true" >

		<div region="center" id="dic-center" style="overflow:auto" title="详细信息">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>角色名称: <input type="text" class="form-search" id="rolerName" style="width: 200px" /> </span>&nbsp; &nbsp; &nbsp; &nbsp;
					<span>角色描述: <input type="text" class="form-search" id="rolerDesc" style="width: 200px" /> </span>&nbsp; &nbsp; &nbsp; &nbsp;
					<span style="width: 200px"></span>&nbsp; &nbsp; &nbsp; &nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="roleAndPrivilegeAdd();">增加角色</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-jsqxicon" plain="true" onclick="roleAndPrivilegeSearch();">角色权限</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="roleAndPrivilege_deleteInfo();">删除</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="roleAndPrivilege_save();">保存</a>
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
			<table id="DataGrid1" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="rolerId"></th>
						<th field="rolerName" width="220" align="center" editor="text">角色名称</th>
						<th field="rolerDesc" width="245" align="center" editor="text">角色描述</th>
						<th field="creatTime" width="220" align="center" >操作时间</th>
					</tr>
			 
				</thead>
			</table>
		</div>
		
		<div style="position:fixed;">	
			<div id="w" class="easyui-window" title="角色添加" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
				draggable="false" style="width:600px;height:240px;padding:5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
						<table>
						    <tr >
								<td align="left">角色名称：</td>
								<td align="left" ><input type="text" id="rolerNameP" style="width:200px"/></td>
								<td ><input type="hidden" id="rolerId_hidden"/></td>
									
							</tr>
	
							<tr >
								<td align="left">角色描述： </td>
								<td align="left" ><textarea id="rolerDescP" style="width:450px" rows="1"></textarea></td>
							</tr>
		  
						</table>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="roleInfoSave()">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseRole()">关闭</a>
				    </div>
				</div>
		     </div>
		     
		     
		     <div id="privilegeId" class="easyui-window" title="角色权限管理" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
		     	 draggable="false" style="width:600px;height:540px;padding:5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
						<table>
						    <tr >
								<th align="left">角色名称：</th>
								<th align="left" id="rolerNameShowId"></th>
								<th ><input type="hidden" id="rolerId_hidden_p"/></th>
									
							</tr>
	
							<tr >
								<th align="left">角色描述： </th>
								<th align="left" id="rolerDescShowId"></th>
							</tr>
		  
							<tr >
								<th align="left">权限设置： </th>
								<td valign="top">
			                        <ul id="limitsId">    
			                                            
			                        </ul>
	                        	</td>
							</tr>
						</table>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="menuFuncSave()">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseMenu()">关闭</a>
				    </div>
				</div>
		     </div>
		</div>
	</div>

</body>
</html>