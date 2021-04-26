<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 
<script type="text/javascript" src="${ctx}/res/js/rolePrivilege/userMgt.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
</head>

<body style="margin:0">
	<div class="easyui-layout" style="overflow: hidden; width:100%;height:100%" fit="true" scroll="no">
		<div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="人员列表" >
	       <ul id="roleManageShowOrganizationId" class="easyui-tree" animate="false" dnd="false"></ul>
   		</div>
		<div region="center" id="dic-center"
			style="overflow:auto" title="用户信息">
			<div  id="toolbar" style="padding:10px 15px;">
			    <div style="margin-bottom:5px">
				<span>登录帐号:&nbsp;<input type="text" class="form-search" id="uId" style="width:200px">&nbsp;</span>
				<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryUserRoleListPage()">查询</a>				
			    </div>
			    <div>
                 <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-fpjsicon" plain="true" onclick="getUserInfoByUserInfo();">分配角色</a> 			     
			    </div>
			</div>
			<table id="DataGrid1" singleSelect="true" class="easyui-datagrid" fit="true" sortable="true"  idField="id" rownumbers="false" pagination="false" fitColumns="true" striped="true" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="uId" checkbox="true" ></th>
						<th field="uName" width="220" align="center" >用户名</th>
						<th field="uManagerName" width="245" align="center" >主管</th>
						<th field="oName" width="220" align="center" >部门</th>
						<th field="uPosition" width="220" align="center" >职务</th>
						
					</tr>
			 
				</thead>
	 
				
  
			</table>
 
			
		</div>
		
		
	     
	     <div id="userRoleId" class="easyui-window" title="用户角色分配" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
	     	  style="width:700px;height:340px;padding:5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<table>
					    <tr >
							<td align="left">用户ID：</td>
							<td align="left" id="userId"></td>
						</tr>
						<tr >
							<td align="left">用户名：</td>
							<td align="left" id="userNameId"></td>
						</tr>

						<tr >
							<td align="left">角色列表： </td>
							<td align="left">
		                           
		                            
		                            			
												
												<div id="userRoleListId">
												
												</div>
												
									     
		                        
                        	</td>
						</tr>
					</table>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="userRoleSave()">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="userRoleWindowClose()">关闭</a>
			    </div>
			</div>
	     </div>

	</div>


</body>
</html>