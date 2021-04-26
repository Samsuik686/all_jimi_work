  <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <script type="text/javascript" src="${ctx}/res/js/deptlistwin.js"></script>
 
   <div  zIndex=1000  id="ManagerWindow4Form" class="easyui-window" title="组织架构人员窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
    	style="width:260px;height:500px;padding:5px;">
		<div class="easyui-layout" fit="true" id="childrenNode">  
		<div region="west" id="dic-west" split="true" style="width: 250px;overflow:hidden;" title="组织架构" >
	    <div id="Managertree4Form"   style="position:absolute;height:400;width:230;overflow:auto; class="easyui-tree" animate="false" dnd="false"    > </div> 
	    </div>
	    <div region="center" id="dic-center"  style="overflow:hidden" title="">
 
	    </div>
	         <br>
       
	    <div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
		<a id="treeAdd4Form" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ManagerWindowOnSelect4Form()">确定</a>
		<a id="treeDelete4Form" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="ManagerWindowClose4Form()">关闭</a>
	    </div> 
    </div>  
    </div> 
     <script>
  manwin4Form();
  </script>