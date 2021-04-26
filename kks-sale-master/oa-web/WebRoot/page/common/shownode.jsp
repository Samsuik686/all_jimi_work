<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   <div id="ww" class="easyui-window" title="节点指派人员" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
   	  style="width:600px;height:320px;padding:5px;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			   <form id="taskPerson" action='${ctx }/node/specifyUser' method="post">
			   	<input type="hidden" name="wfid" id="wfid" value="">
			   	<input type="hidden" name="type" id="type" value="">
			   	<input type="hidden" name="matterid" id="matterid" value="">
				<div id="testaaa"></div>
				<input type="submit" id="tasksubmit" style="display:none">
			   </form>
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a id="treeUpdate" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:void(0)" onclick="javascript:$('#tasksubmit').click()">确认指定</a>
				<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#ww').window('close');">关闭</a>
			</div>
		</div>
	</div>




