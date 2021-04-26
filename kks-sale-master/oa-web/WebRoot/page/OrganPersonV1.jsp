  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
   <div  zIndex=1000 id="ManagerWindow" class="easyui-window" title="组织架构人员窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
   		 style="width:500px;height:500px;padding:5px;">
		<div class="easyui-layout" fit="true" id="childrenNode">  
		<div region="west" id="dic-west" split="false" style="width: 250px;overflow:hidden;" title="组织架构" >
	    <ul id="Managertree" class="easyui-tree" animate="false" dnd="false"> </ul>
	    </div>
	    <div region="center" id="dic-center"  style="overflow:hidden" title="人员列表">
	    <table id="ManagerDataGrid" singleSelect="true" sortable="true"  fit="true" idField="id" rownumbers="false" striped="true" fitColumns="true">
	    <thead>
	    <tr>
	    <th checkbox="true" ></th>
	    <th field="uId" hidden="hidden" ></th>
	    <th field="uName" width="60" align="center" >员工名称</th>
	    </tr>
	    </thead>
	    </table>
	    </div>
	    <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
		<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ManagerWindowOnSelectV2()">确定</a>
		<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="ManagerWindowClose()">关闭</a>
	    </div>
    </div>
    </div> 
     <script>
  manwin();
  </script>