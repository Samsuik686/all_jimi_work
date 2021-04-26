<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <jsp:include page="/page/comm.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <script type="text/javascript" src="${ctx}/res/js/dictionaryInit.js"></script>
  </head>
  
 <body style="margin:0px;" >
<div class="easyui-layout" style="overflow: hidden; width:100%;height:100%"  scroll="no"   fit="true">
    <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="字典树" >
      <div class="easyui-panel" style="background-color:#E0ECFF">
      <a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-addicon" onclick="treeAddwindow()">增加</a>
      &nbsp;&nbsp;<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-delecticon" onclick="treeDelete()">删除</a>
     &nbsp;&nbsp;<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-xgicon" onclick="treeUpdateWindow()">修改</a>
      </div>
	       <ul id="tt2" class="easyui-tree" animate="true" dnd="false">
	       </ul>
       
   </div>
   <div region="center" id="dic-center" fit="true" style="overflow:auto" title="字典数据">
  
      	<table id="DataGrid1" style="width:700px;height:auto" singleSelect="true" sortable="true"
			fit="true" idField="dicId" fitColumns="true" rownumbers="false" striped="true">
		<thead>
			<tr>
			    <th checkbox="true" ></th>  
				<th field="dicId" width="80" hidden="true">dicId</th>
				<th field="proTypeId" width="100" hidden="true">proTypeid</th> 
				<th field="itemName" width="80" align="center" editor="text">分类名</th>
				<th field="itemValue" width="80" align="center" editor="text">分类值</th>
				<th field="descrp" width="80" align="center" editor="text">描述</th>
				<th field="sn" width="250" align="center"  editor="text" >SN</th>
				<th field="orderindex" width="160" align="center" editor="numberbox">排序序号</th>
			</tr>
		</thead>  
	</table>
   </div>
   	<div id="w" class="easyui-window" title="分类增加窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
   		style="width:350px;height:250px;padding:5px;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<table>
				    <tr>
				     <td>分类名：</td>
				     <td><input type="text" id="itemName" style="width:200px"></td>
				     </tr>
				     <tr>
				     <td>分类值：</td>
				     <td><input type="text" id="itemValue" style="width:200px"></td>
				     </tr>
				     <tr>
				     <td>描述：</td>
				     <td><input type="text" id="descrp" style="width:200px"></td>
				     </tr>
				     <tr>
				     <td>SN：</td>
				     <td><input type="text" id="sntext" style="width:200px"></td>
				     </tr>
				     <tr>
				     <td>排序序号：</td>
				     <td><input type="text" onkeyup="value=value.replace(/[^\d]/g,'') " 
                     onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                     id="orderindex" style="width:200px"></td>
				    </tr>
				</table>
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a id="treeUpdate" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:void(0)" onclick="treeUpdate()">确认修改</a>
				<a id="treeSave" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="treeAdd()">保存</a>
				<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowClose()">关闭</a>
			</div>
		</div>
	</div>
   
   
   
   </div>
   
   
   
   
  </body>
</html>
