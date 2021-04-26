<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/customFlow/customFlow.js?${num}"></script>
<script type="text/javascript" src="${ctx}/res/js/fileManage/zbxh.js?20170414"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/ajaxfileupload.js"></script>

</style>
</head>
	<body style="margin:0">
		<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
			<div region="center" id="dic-center" style="overflow: auto" title="自定义流程">
				<!-- 当前登录用户基本信息 -->
				<div style="display: none;">
					<span id="userName">${USERSESSION.uName}</span>
					<span id="userId">${USERSESSION.uId}</span>
					<span id="userOrgId">${USERSESSION.oId}</span>	<!-- 组织ID -->
					<span id="position">${USERSESSION.uPosition }</span> <!-- 职位 -->
				</div>
				<div id="toolbar" style="padding: 10px 15px;">
					<!-- 查询条件 -->
					<div style="margin-bottom:2px">
						<span> 流程图名称：<input type="text" id="searchName" class="form-search" style="width: 135px"/> </span>
						<span> &nbsp;创&nbsp;建&nbsp;人：<input type="text" id="searchCreateUser" class="form-search" style="width: 135px"/> </span>
						<!-- <span>
							状态：
							<select id="searchStatus" name="ckek" style="width: 100px;">
								<option value="0" selected="selected">生效</option>
								<option value="1">失效</option>
							</select>
						</span> -->
						<span>
							<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
						</span>
					</div>
					<div style="margin-bottom:5px">
					</div>
					
					<div>					
                   		 <table cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="openFlowWin(1);">增加</a>										
									</td>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="deleteCustomFlow();">删除</a>
										<div class="datagrid-btn-separator"></div>									
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
				
				<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
					<thead>
						<tr>
							<th checkbox="true" field="id" align="center">ID</th>
							<th field="name"  align="center" width="300">流程图名称</th>
							<!-- <th field="statusStr"  align="center" width="100" data-options="formatter:convertStatus">状态</th> -->
							<th field="createUser"  align="center" width="100">项目创建人</th>
							<th field="createTime"  align="center" width="130" data-options="formatter:formatDate">创建时间</th>
							<th field="_operate" data-options="formatter:formatOper"  align="center" width="150">查看流程图</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
						
	</body>
</html>













