<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/addresslist.js"></script>
<script type="text/javascript" src='${ctx }/res/script/ajaxLoading.js'></script><!-- add by wg.he 2013/12/11 进度条加载 -->
 <link rel="stylesheet" type="text/css" href="${ctx}/res/css/user-table.css" />    
</head>
<body style="margin:0px;">
	<div class="easyui-layout"
		style="overflow: hidden; width:100%;height:100%" scroll="no"  fit="true">
		<div region="west" id="dic-west" style="width: 200px;overflow:auto;"
			title="组织架构">

			<ul id="tt2" class="easyui-tree" animate="false" dnd="false" >
			</ul>

		</div>
		<div region="center" id="dic-center" style="overflow:auto;" title="通讯录">
			<div class="easyui-panel" style="width:1030px; height: 35px; padding-top: 5px;">
			
			          部门：<select id="oidSelect" style="width:100px"></select>        &nbsp;    &nbsp;    &nbsp; 
			    工号：<input type="text" id="searchCid" style="width:100px">    &nbsp;     &nbsp;   &nbsp;       
			员工名称：<input type="text" id="searchUname" style="width:200px">    &nbsp;     &nbsp;   &nbsp; 
			<a id="search" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="searchinfo()">查询</a> 
			   &nbsp;    &nbsp;    &nbsp; 
					<a id="importexcel" class="easyui-linkbutton" iconCls="icon-print"
					href="javascript:void(0)" onclick="importexcel()">导出</a>
			</div>
				 	 <input     type="hidden"  id="treeOrgId" style="width:200px">  
				<table id="mytable"  cellspacing="0" width="100%">
					<tr>
						<th width="40px">序号</th>
						<th width="100px">工号</th>
						<th width="100px">姓名</th>
						<th width="100px">ID</th>
						<th width="120px">主部门</th>
						<th width="150px">职务</th>
						<th width="150px">直接领导</th>
						<th width="100px">手机号码</th>
						<th width="150px">固定电话</th>
						<th width="80px">邮箱</th>
						<th width="80px">QQ</th>
						<th width="100px">企业QQ</th>
					</tr>
				</table>
				
				
				
		</div>
	</div>




</body>
</html>
