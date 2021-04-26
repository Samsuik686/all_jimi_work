<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <jsp:include page="/page/comm.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
     <script type="text/javascript" src="${ctx}/page/organizational/organization.js"></script>

  </head>
  
  <body style="margin:0">
 <div class="easyui-layout" style="overflow: hidden; width:100%;height:100%"  scroll="no"  id="maindiv"  fit="true">
    <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="组织架构" >
    
	       <ul id="tt2" class="easyui-tree" animate="false" dnd="false">
	       </ul>
      
   </div>
   <div region="center" id="dic-center"  style="overflow:hidden" title="组织架构信息">
 
	          部门：<select id="oidSelect" style="width:100px"></select>   &nbsp;    &nbsp;    &nbsp; 
   <a id="searchinfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="searchinfo()">查询</a>
	
	 
	
      	<table id="DataGrid1" singleSelect="true" sortable="true"  fit="true"
		 	idField="id" rownumbers="false" striped="true" fitColumns="true"
		 	 >
		<thead>
			<tr>
				<th checkbox="true" ></th>
				<th field="oId"  width="60" align="center" >部门ID</th> 
				<th field="oParentId" width="80" align="center" >上级部门</th>
				<th field="oName" width="60" align="center" >部门名称</th>
				<th field="oManagerId" width="60" align="center" >主管ID</th>
				<th field="oManagerName" width="60" align="center">主管名称</th>
				<th field="oIndex" width="60" align="center">排序序号</th>
				<th field="oDesc" width="140" align="center">详细描述</th>
			</tr>
		</thead>
	</table>
	
   </div>
   	<div id="w" class="easyui-window" zIndex=10 title="组织架构新增窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
   		 style="width:340px;height:250px;padding:5px;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<table>
				     <tr>
				     <td>上级部门：</td>
				     <td>
				     <input type="text"  id="oId" style="display:none">
				     <select  id="oParentId" style="width:200px" disabled="disabled"></select></td>
				     </tr>
				     <tr>
				     <td>部门名称：</td>
				     <td><input type="text"  id="oName" style="width:200px"></td>
				     </tr>
				     <tr>
				     <td>主管名称：</td>
				     <td>
				     <input type="text"  id="oManagerId" style="display:none" >
				     <input type="text" readonly="readonly" id="oManagerName" onclick="ManagerWindowOnOpen(this)" style="width:200px" ></td>
				    </tr>
				    <tr>
				     <td>排序序号：</td>
				     <td><input type="text" onkeyup="value=value.replace(/[^\d]/g,'') " 
                     onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                     id="oIndex" style="width:200px"></td>
				    </tr>
				     <tr>
				     <td>详细描述：</td>
				     <td><input type="text"  id="oDesc" style="width:200px"></td>
				    </tr>
				</table>     
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a id="oUpdate" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:void(0)" onclick="oUpdate()">修改保存</a>
			<a id="oSave" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="oSave()">保存</a>
			<a id="oClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowClose()">关闭</a>
		</div>
		</div>
		
	</div>
   	<jsp:include page="/page/OrganPerson.jsp"/>
   </div>
  

   
  </body>
</html>