<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/fileManage/productMaterial.js?20170414"></script>
<script type="text/javascript" src="${ctx}/res/js/fileManage/zbxh.js?20170414"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/ajaxfileupload.js"></script>

<style type="text/css">
	.divCss a:link{
		text-decoration: underline; 
	}
	.divCss a{
		color:blue;
	}
	.divCss a:hover{
		color:red;
	}
	.fileInfo .panel-tool-close { 
		vertical-align: top; 
		border:0; 
		box-shadow:none; 
		height:18px; outline:none;     
	}
	.divInput input{
		width:260px;
	}
	.font_color{
		font-weight: bold; 
		color: red;
	}
</style>
</head>
	<body style="margin:0">
		<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
			<div region="center" id="dic-center" style="overflow: auto" title="产品材料列表">
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
						<span> 主板型号：<input type="text" id="searchModel" class="form-search" style="width: 135px"/> </span>
						<span> 市场型号：<input type="text" id="searchMarketModel" class="form-search" style="width: 135px"/> </span>
						<span> 物料编码：<input type="text" id="searchByProNum" class="form-search" style="width: 150px" placeholder="精确匹配"/> </span>
						<span> 项目名称：<input type="text" id="searchByProjectName" class="form-search" style="width: 150px"/> </span>
					</div>
					<div style="margin-bottom:5px">	
						<span> &nbsp;创&nbsp;建&nbsp;人：<input type="text" id="searchCreateUser" class="form-search" style="width: 135px"/> </span>
						<span> 创建时间：<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px">
						  - <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
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
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="productMaterial_add();">增加</a>										
									</td>
									
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="productMaterial_update();">修改</a>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="productMaterial_delete();">删除</a>
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
							<th field="createUser"  align="center" width="100">项目创建人</th>
							<th field="createTime"  align="center" width="130">创建时间</th>
							<th field="projectName"  align="center" width="150">项目名称</th>
							<th field="productName"  align="center" width="150">产品名称</th>
							<th field="model"  align="center" width="130">主板型号</th>
							<th field="marketModel"  align="center" width="130">市场型号</th>
							<th field="trademark"  align="center" width="100">品牌</th>
							<th field="series"  align="center" width="150">系列</th>
							<th field="proColor"  align="center" width="80">颜色</th>
							<th field="proNum"  align="center" width="150">编码</th>
							<th field="customerName" align="center" width="150">客户名称</th>
							<th field="proType" align="center" width="150">类别</th>
							<th field="proChildType" align="center" width="150" >子类别</th>
							<th field="project" align="center" width="50" ><font color="red">项目</font></th>
 							<th field="bomCount" align="center" width="100" >组装料BOM表</th>
							<th field="dianzibomCount" align="center" width="100" >电子料BOM表</th>
							<th field="repairCount" align="center" width="65" >维修手册</th>
							<th field="differenceCount" align="center" width="65" >差异说明</th>
							<th field="projectRemarkCount" align="center" width="65" >备注附件</th>
							<th field="product" align="center" width="50" ><font color="red">产品</font></th>
							<th field="softwareCount" align="center" width="65" >软件</th>
							<th field="explainCount" align="center" width="65" >说明书</th>
							<th field="kfCount" align="center" width="65" >客服手册</th>
							<th field="softDifCount" align="center" width="100" >软件功能差异表</th>
							<th field="datumCount" align="center" width="65" >产品资料</th>
							<th field="huaceCount" align="center" width="65" >产品画册</th>
							<th field="functionCount" align="center" width="65" >功能列表</th>
							<th field="zhilingCount" align="center" width="65" >指令表</th>
							<th field="productRemarkCount" align="center" width="65" >备注附件</th>
							<th field="quality" align="center" width="50" ><font color="red">品质</font></th>
							<th field="zuzhuangCount" align="center" width="100" >组装作业指导书</th>
							<th field="testCount" align="center" width="100" >测试作业指导书</th>
							<th field="testToolCount" align="center" width="65" >测试工具</th>
							<th field="xiehaoTooCount" align="center" width="65" >写号工具</th>
							<th field="qualityRemarkCount" align="center" width="65" >备注附件</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<div style="position:fixed;">
		<!-- 添加，修改信息   Start -->		
			<div id="infro_window" class="easyui-window" title="新增产品材料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
				style="visibility:hidden;width: 1000px; height: 550px; padding: 2px;">								
				<font color="red">先录入项目基本信息保存后，再选择附件上传。【附件大小不能超过300M！】</font>
				<div class="easyui-layout" fit="true">					
					<div region="center" border="false" style="padding: 5px; background: #fff; border: 1px solid #ccc; " >
						<input type="hidden" id="id"/>
						<form id="project_form" onsubmit="return false;" enctype="multipart/form-data">
							<fieldset style="border:1px solid #ccc;" id="projectTab" class="divInput">
								<legend><b>项目信息</b></legend>
								<table align="center" width="100%" cellpadding="1">        
							     <tr>       
									<td align="right" style="width: 100px;"><label class="font_color">*</label>项目名称：</td>
									<td>
										<input type="text" id="projectName" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
									<td align="right">编   码：</td>
									<td>
										<input type="text" id="proNum"/>
									</td> 
							     </tr>
							      <tr>       
									<td align="right"><label class="font_color">*</label>产品名称：</td>
									<td>
										<input type="text" id="productName" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
									<td align="right">品  牌：</td>
									<td>
										<input type="text" id="trademark"/>
									</td>
							     </tr>
							      <tr>       
									<td align="right"><label class="font_color">*</label>主板型号：</td>
									<td><input type="text" id="model" style="width: 50%" class="easyui-validatebox" required="true" missingMessage="请填写此字段" readonly="readonly">
										<label><a href="javascript:void(0)" id="chooseZbxh" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseZbxh();">添加</a></label>
									</td>
									<td align="right">系  列：</td>
									<td>
										<input type="text" id="series"/>
									</td>
							     </tr>
							      <tr>       
									<td align="right"><label class="font_color">*</label>市场型号：</td>
									<td>
										<input type="text" id="marketModel" disabled="disabled"/>
									</td>
									<td align="right">颜  色：</td>
									<td>
										<input type="text" id="proColor"/>
									</td>
							     </tr>
							      <tr>       
									<td align="right">客户名称：</td>
									<td>
										<input type="text" id="customerName"/>
									</td>
									<td align="right">类  别：</td>
									<td>
										<input type="text" id="proType" disabled="disabled"/>
									</td>
							     </tr>
							      <tr>
							      	     
									<td align="right">子 类 别：</td>
									<td>
										<input type="text" id="proChildType"/>
									</td>
							     </tr>							    
							   </table>
							   <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;margin-right: 3%;">
									<a class="easyui-linkbutton" iconCls="icon-ok"  href="javascript:void(0)" id="saveProductBut" onclick="saveOrUpdateProductInfo()">保存</a>
									<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindow();">关闭</a>						  	 		
						  	  </div>
							</fieldset>
							
							<fieldset style="border:1px solid #ccc;" id="projectTabf" class="divCss">
							<legend><b>项目信息附件</b></legend>
							<table align="center" width="100%" cellpadding="2"> 
								<tr>
							     	<td align="right" style="width: 100px;">组装料BOM表：</td>
							     	<td>
							     		<div id="bomfileInfo" class="fileInfo">
							     		 	
							     		 </div>
							     		<span id="bomNameSpan"> 
								     		<input id="bomName" name="files" type="file" style="width: 85%;">
								     		<button name="fileUpload" type="button" value="bomName">上传</button>	
							     		</span>						     		 
							     	</td>
						     	</tr>
						     	<tr>
						     		<td height="5px;"></td>
						     	</tr>
						     	<tr>
							     	<td align="right">电子料BOM表：</td>
							     	<td>
							     		<div class="fileInfo" id="dianZiBomfileInfo">	
							     											     			
							     		 </div>
							     		 <span id="dianzibomNameSpan"> 
								     		 <input id="dianzibomName" name="files" type="file" style="width: 85%;">	
								     		 <button name="fileUpload" type="button" value="dianzibomName">上传</button>	
								     		 <button name="chooseOther" type="button" value="">选择</button>	
							     		</span>							     		 
							     	</td>
							     </tr>
							     <tr>
						     		<td height="5px;"></td>
						     	</tr>
							     <tr>
							     	<td align="right">维修手册：</td>
							     	<td>
							     		<div id="repairfileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		  <span id="repairNameSpan"> 
								     		 <input id="repairName" name="files" type="file" style="width: 85%;">	
								     		 <button name="fileUpload" type="button" value="repairName">上传</button>
								     		 <button name="chooseOther" type="button" value="">选择</button>		
							     		</span>							     		 
							     	</td>
						     	</tr>
						     	<tr>
						     		<td height="5px;"></td>
						     	</tr>
						     	<tr>
							     	<td align="right">差异说明：</td>
							     	<td>
							     		<div id="differencefileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		  <span id="differenceNameSpan"> 
								     		 <input id="differenceName" name="files" type="file" style="width: 85%;">
								     		 <button name="fileUpload" type="button" value="differenceName">上传</button>
								     		 <button name="chooseOther" type="button" value="">选择</button>		
							     		</span>								     		 
							     	</td>
							     </tr>
							     <tr>
						     		<td height="5px;"></td>
						     	</tr>
							     <tr>
							     	<td align="right">备注附件：</td>
							     	<td>
							     		<div id="projectRemarkfileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		  <span id="projectRemarkNameSpan"> 
								     		 <input id="projectRemarkName" name="files" type="file" style="width: 85%;">	
								     		 <button name="fileUpload" type="button" value="projectRemarkName">上传</button>	
								     		 <button name="chooseOther" type="button" value="">选择</button>	
							     		</span>							     		 
							     	</td>
							     </tr>
								</table>
							</fieldset>
						</form>
						
						<form id="product_form" onsubmit="return false;">
							<fieldset style="border:1px solid #ccc;" id="projectTab" class="divCss">
								<legend><b>产品信息</b></legend>
								<table align="center" width="100%" cellpadding="2">      
								    <tr>
								     	<td align="right" style="width: 100px;">软件：</td>
								     	<td>
								     		<div id="softwarefileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		 <span id="softwareNameSpan">
									     		 <input id="softwareName" name="files" type="file" style="width: 85%;">
									     		 <button name="fileUpload" type="button" value="softwareName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>								     		 
								     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
								     	<td align="right">说明书：</td>
								     	<td>
								     		<div id="explainfileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="explainNameSpan">
									     		 <input id="explainName" name="files" type="file" style="width: 85%;">
									     		 <button name="fileUpload" type="button" value="explainName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>							     		 
								     	</td>
								     </tr>
								     <tr>
							     		<td height="5px;"></td>
							     	</tr>
								     <tr>
								     	<td align="right">客服手册：</td>
								     	<td>
								     		<div id="kffileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="kfNameSpan">
									     		 <input id="kfName" name="files" type="file" style="width: 85%;">	
									     		 <button name="fileUpload" type="button" value="kfName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>							     		 
								     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
							     	<!-- <td align="right">产品系列：</td>
								     	<td>
								     		<div id="proseriesfileInfo" class="fileInfo">
								     		 	下载，删除附件部分
								     		 </div>
								     		  <span id="proseriesNameSpan">
									     		 <input id="proseriesName" name="files" type="file" style="width: 85%;">	
									     		 <button name="fileUpload" type="button" value="proseriesName">上传</button>	
							     			</span>							     		 
								     	</td> -->
								     </tr>
								     <tr>
								     	<td align="right" style="width: 100px;">软件功能差异表：</td>
								     	<td>
								     		<div id="softDiffileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="softDifNameSpan">
									     		 <input id="softDifName" name="files" type="file" style="width: 85%;">
									     		 <button name="fileUpload" type="button" value="softDifName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>							     		 
								     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
								     	<td align="right">产品资料：</td>
								     	<td>
								     		<div id="datumfileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="datumNameSpan">
									     		 <input id="datumName" name="files" type="file" style="width: 85%;">	
									     		 <button name="fileUpload" type="button" value="datumName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>							     		 
								     	</td>
								     </tr>
								     <tr>
							     		<td height="5px;"></td>
							     	</tr>
								     <tr>
								     	<td align="right">产品画册：</td>
								     	<td>
								     		<div id="huacefileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="huaceNameSpan">
									     		 <input id="huaceName" name="files" type="file" style="width: 85%;">	
									     		 <button name="fileUpload" type="button" value="huaceName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>							     		 
								     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
								     	<td align="right">功能列表：</td>
								     	<td>
								     		<div id="functionfileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="functionNameSpan">
									     		 <input id="functionName" name="files" type="file" style="width: 85%;">
									     		 <button name="fileUpload" type="button" value="functionName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>							     		 
								     	</td>
								     </tr>
								     <tr>
							     		<td height="5px;"></td>
							     	</tr>
								     <tr>
								     	<td align="right">指令表：</td>
								     	<td>
								     		<div id="zhilingfileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="zhilingNameSpan">
									     		 <input id="zhilingName" name="files" type="file" style="width: 85%;">
									     		 <button name="fileUpload" type="button" value="zhilingName">上传</button>
									     		 <button name="chooseOther" type="button" value="">选择</button>		
							     			</span>								     		 
								     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
								     	<td align="right">备注附件：</td>
								     	<td>
								     		<div id="productRemarkfileInfo" class="fileInfo">
								     		 	<!-- 下载，删除附件部分 -->
								     		 </div>
								     		  <span id="productRemarkNameSpan">
									     		 <input id="productRemarkName" name="files" type="file" style="width: 85%;">
									     		 <button name="fileUpload" type="button" value="productRemarkName">上传</button>	
									     		 <button name="chooseOther" type="button" value="">选择</button>	
							     			</span>								     		 
								     	</td>
								     </tr>
							     </table>
						     </fieldset>						
						</form>
						<form id="quality_form" onsubmit="return false;">		
							<fieldset style="border:1px solid #ccc;" id="projectTab" class="divCss">
								<legend><b>品质信息</b></legend>
								<table align="center" width="100%" cellpadding="2">        
								      <tr>
							     	<td align="right" style="width: 100px;">组装作业指导书：</td>
							     	<td>
							     		<div id="zuzhuangfileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		 <span id="zuzhuangNameSpan">
								     		 <input id="zuzhuangName" name="files" type="file" style="width: 85%;">	
								     		 <button name="fileUpload" type="button" value="zuzhuangName">上传</button>	
								     		 <button name="chooseOther" type="button" value="">选择</button>	
							     		</span>							     		 
							     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
							     	<td align="right" style="width: 100px;">测试作业指导书：</td>
							     	<td>
							     		<div id="testfileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		 <span id="testNameSpan">
								     		 <input id="testName" name="files" type="file" style="width: 85%;">	
								     		 <button name="fileUpload" type="button" value="testName">上传</button>	
								     		 <button name="chooseOther" type="button" value="">选择</button>	
							     		</span>							     		 
							     	</td>
							     </tr>
							     <tr>
						     		<td height="5px;"></td>
						     	</tr>
							      <tr>
							     	<td align="right">测试工具：</td>
							     	<td>
							     		<div id="testToolfileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		 <span id="testToolNameSpan">
								     		 <input id="testToolName" name="files" type="file" style="width: 85%;">	
								     		 <button name="fileUpload" type="button" value="testToolName">上传</button>	
								     		 <button name="chooseOther" type="button" value="">选择</button>	
							     		</span>							     		 
							     	</td>
							     	</tr>
							     	<tr>
							     		<td height="5px;"></td>
							     	</tr>
						     		<tr>
							     	<td align="right">写号工具：</td>
							     	<td>
							     		<div id="xiehaoToofileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		 <span id="xiehaoTooNameSpan">
								     		 <input id="xiehaoTooName" name="files" type="file" style="width: 85%;">
								     		 <button name="fileUpload" type="button" value="xiehaoTooName">上传</button>	
								     		 <button name="chooseOther" type="button" value="">选择</button>	
							     		</span>								     		 
							     	</td>
							     </tr>
							     <tr>
						     		<td height="5px;"></td>
						     	</tr>
							      <tr>
							     	<td align="right">备注附件：</td>
							     	<td>
							     		<div id="qualityRemarkfileInfo" class="fileInfo">
							     		 	<!-- 下载，删除附件部分 -->
							     		 </div>
							     		 <span id="qualityRemarkNameSpan">
								     		 <input id="qualityRemarkName" name="files" type="file" style="width: 85%;">
								     		 <button name="fileUpload" type="button" value="qualityRemarkName">上传</button>
								     		 <button name="chooseOther" type="button" value="">选择</button>		
							     		</span>								     		 
							     	</td>
							     </tr>
							     </table>
						     </fieldset>					
						</form>
					</div>					
				</div>
			</div>			
			<!-- 添加，修改信息   End -->
			
			 <!-- 选择主板型号 -START-->
			<div id="w_chooseZBXH" class="easyui-window" title="选择主板型号|市场型号" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
				  style="width:760px;height:430px; visibility: hidden; ">
				<div class="easyui-layout" fit="true">
					<div region='center' >
						<div id="zbxhToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">	
								<input type="hidden" id="zbxh_rowindex"/>		
								<span>&nbsp;主板型号:&nbsp;<input type="text" class="form-search3"  id="searchBymodel" style="width:135px" /></span>&nbsp;
								<span>
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryZBXHPage(1);">查询</a>
									&nbsp;&nbsp;&nbsp;<font color="red">双击选择一条主板型号信息</font>
								</span>       
							</div>
							
							<div>
								<table cellspacing="0" cellpadding="0">
									<tbody>
										<tr>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="insertZbXH();">新增</a></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="updateZbXH();">修改备注</a></td>						
										</tr>
									</tbody>
								</table>
							</div>						
						</div>
										
						<table id="DataGrid_chooseZBXH" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fitColumns="true" autoRowHeight="true" 
							   striped="true" scrollbarSize="0" toolbar="#zbxhToolbar">
							<thead>
								<tr>
									<!-- <th field="createType" hidden="true">创建类型</th> -->
									<th field="mId" checkbox="true"></th>
									<th field="model" width="110" align="center">主板型号</th>
									<th field="marketModel" width="110"  align="center">市场型号</th>
									<th field="modelType" width="110"  align="center">型号类别</th>
									<th field="remark" width="110"  align="center">备注</th>
								</tr>
							</thead>
						</table> 
					</div> 
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
						<a id="closeChooseZBXH" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeChooseZBXH();">关闭</a>  
				    </div> 
				</div>
		     </div>
		    <!-- 选择主板型号 -END-->
			
			 <!--新增主板型号 -START-->
			<div id="w_zbxh" class="easyui-window" title="新增主板型号" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
				  style="width: 450px; height: 280px; visibility: hidden; ">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="zbxhform" action="">
							<table align="center">
								<tr>
									<td><input id="zbxh_mId" type="hidden" /></td>
								</tr>
								<tr>
									<td>主板型号：</td>
									<td><input type="text" id="zbxh_model" style="width: 220px;" class="easyui-validatebox updateHide" required="true" missingMessage="请填写此字段"/></td>
								</tr>
								<tr>
									<td>市场型号：</td>
									<td><input type="text" id="zbxh_marketModel" style="width:220px;" class="easyui-validatebox updateHide" required="true" missingMessage="请填写此字段"/></td>
								</tr>
								<tr>
									<td>型号类别：</td>
									<td>
										<input id="type" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width:150px;" required="true" missingMessage="请选择类别"/>
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7"><textarea id="zbxh_remark" style="width: 100%; height: 80px;" maxlength="255"></textarea></td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="zbxhSave" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="zbxhSave()">保存</a> 
						<a id="closeZbxhSave" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeZbxhSave()">关闭</a>
					</div>
				</div>
			</div>
			<!-- 创建主板型号-END-->	
			
			<!-- 选择已上传附件  -Start-->
			<div id="choose_w" class="easyui-window" title="选择已上传附件" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
				 style="width:950px;height:550px; visibility: hidden; ">
				<div class="easyui-layout" fit="true">
					 <div region="center" id="dic-center" style="overflow:auto">
						<div style="height:465px;">
							<div id="file_bar" style=" padding: 10px 15px;">
								<div style="margin-bottom:5px">	
									<input type="hidden" id="search_fileType">
				 					<span>&nbsp;文件名称:&nbsp;<input type="text" class="form-search2" id="searchByFileName" style="width:150px"></span>&nbsp;
									<span> <a id="searchInfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryFileList()">查询</a> </span>
								</div>
							</div>
							<table id="file_DataGrid" fit="true" singleSelect="false" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" scrollbarSize="0" toolbar="#file_bar">
								<thead>
									<tr>
										<th checkbox="true" field="fid"></th>
										<th field="model" width="80" align="center">主板型号</th>
										<th field="fileName" width="180" align="center">文件名</th>
										<th field="fileType" width="100" align="center">文件类型</th>
										<th field="createUser" width="80" align="center">上传人</th>
										<th field="createTime" width="130" align="center">上传时间</th>
										<th field="fileUrl" width="550" align="center">路径</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					    <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="selectFileSave();">保存</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('choose_w')">关闭</a>
				    </div>
		    	</div>
		   </div>
	   	 <!-- 选择已上传附件  - End-->
		</div>
						
	</body>
</html>













