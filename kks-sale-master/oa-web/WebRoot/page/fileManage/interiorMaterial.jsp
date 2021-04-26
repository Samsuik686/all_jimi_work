<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/fileManage/interiorMaterial.js?20170407"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/ajaxfileupload.js"></script>

<style type="text/css">
	.fileInfo a:link{
		text-decoration: underline; 
	}
	.fileInfo a{
		color:blue;
	}
	.fileInfo a:hover{
		color:red;
	}
	.fileInfo .panel-tool-close { 
		vertical-align: top; 
		border:0; 
		box-shadow:none; 
		height:18px; outline:none;     
	}
	.font_color{
		font-weight: bold; 
		color: red;
	}
</style>

</head>
	<body style="margin:0">
		<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" scroll="no" fit="true">
			<div region="center" id="dic-center" style="overflow: auto" title="内部材料列表">
				<div id="toolbar" style="padding: 10px 15px;">
					<!-- 查询条件 -->
					<div>
						<span>
							上传人：<input type="text" id="searchCreateUser" class="form-search"/>
						</span>
						<span>
							文件名称：<input type="text" id="searchFileName" class="form-search"/>
						</span>
						<span>
							<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
						</span>
					</div>
					
					<div>					
                   		 <table cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="interiorFileAdd();">增加</a>										
									</td>
									
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="interiorFileUpdate();">修改</a>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="interiorFileDelete();">删除</a>
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
				
				<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
					<thead>
						<tr>
							<th checkbox="true" field="id" align="center">ID</th>
							<th field="fileName"  align="center" width="250">文件名称</th>
							<th field="createTime"  align="center" width="250">上传日期</th>
							<th field="createUser"  align="center" width="250">材料创建人</th>
							<th field="updateUser"  align="center" width="250">附件上传人</th>		
							<th field="fileUrl"  align="center" hidden="true">文件路径</th>							
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<!-- 新增内部材料 Start -->
		<div style="position:fixed;">
			<div id="infro_window" class="easyui-window" title="新增内部材料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
				style="visibility:hidden;width: 500px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="interior_form" action="">
							<input type="hidden" id="id"/>
							<input type="hidden" id="currentName" value="${USERSESSION.uName}"/>
							<table align="center" width="100%" cellpadding="2">        
							     <tr>       
									<td align="right" id="fileTitle"><label class="font_color">*</label>上传文件：</td>
									<td>
							     		<div id="fileInfoDiv" class="fileInfo" >
							     		 	
							     		 </div>
							     		<span id="fileSpan"> 
								     		<input id="interiorFile"  name="interiorFileName"   type="file" style="width: 200px;">							     		
							     			<font color="red">文件大小不能超过300M！</font>
							     		</span>						     		 
							     	</td>
							     </tr>
							     <tr>       
									<td align="right">上传人：</td>
									<td>
										<input type="text" id="fileCreateUser" disabled="disabled" value="" style="width: 200px;"/>
									</td>
							     </tr>
							     <tr>       
									<td align="right">上传时间：</td>
									<td>
										<input type="text" id="fileTime" disabled="disabled" style="width: 200px;"/>
									</td>
							     </tr>
							  </table>
						</form>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
						<a class="easyui-linkbutton" iconCls="icon-ok"  href="javascript:void(0)" id="saveProductBut" onclick="saveOrUpdateInteriorInfo()">保存</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindow();">关闭</a>
			  	   </div>
				</div>
			</div>
		</div>
		<!-- 新增内部材料 END -->
						
	</body>
</html>