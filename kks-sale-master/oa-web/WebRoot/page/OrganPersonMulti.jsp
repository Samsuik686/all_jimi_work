  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
   <div  zIndex=1000 id="ManagerWindowMulti" class="easyui-window" title="组织架构人员窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
    	  style="width:500px;height:320px;padding:5px;">
		<div class="easyui-layout" fit="true" id="childrenNodeMulti">  
		<div region="west" id="dic-westMulti" split="false" style="width: 250px;overflow:auto;" title="组织架构" >
	    <ul id="ManagertreeMulti" class="easyui-tree" animate="false" dnd="false"> </ul>
	    </div>
	    <div region="center" id="dic-centerMulti"  style="overflow:hidden" title="人员列表">
	    <table id="ManagerDataGridMulti" style="height:auto" sortable="true"  fit="true" idField="uId"  striped="true" fitColumns="true">
	    <thead>
	    <tr>
	    <th checkbox="true"></th>
	    <th field="uId" hidden="hidden" ></th>
	    <th field="uName" width="60" align="center" >员工名称</th>
	    </tr>
	    </thead>
	    </table>
	    </div>
	    <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
		<a id="treeAddMulti" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ManagerWindowOnSelectMulti()">确定</a>
		<a id="treeDeleteMulti" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="ManagerWindowCloseMulti()">关闭</a>
	    </div>
    </div>
    </div> 
     <script>
  manwinMulti();
  </script>