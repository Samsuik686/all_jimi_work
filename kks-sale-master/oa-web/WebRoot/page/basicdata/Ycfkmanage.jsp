<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/ycfkmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/chooseYcfkBase.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/ycfk.tree/ycfkTree.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>
</head>
<body style="margin: 0">
	<input type="hidden" id="LX_Type" value="BASE_YCFK" />
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/>
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="ycfkTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="400电话记录管理列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:5px">	
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px" /> </span>&nbsp; 
					<span>结束日期: <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px" /> </span>&nbsp; 
					<span>
						状态:&nbsp;
						<select id="completionState" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0">待解决</option>
							<option value="1">已完成</option>
						</select>&nbsp;
					</span>
					<span>责任人：<input type="text" style="width: 100px" class="form-search" id="recipient"></span>
					<span>客户姓名：<input type="text" style="width: 100px" class="form-search" id="customerName"></span>
					<span>联系电话：<input type="text" style="width: 100px" class="form-search" id="phone"></span>
					<span><a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="javascript:void(0)">查询</a> </span>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="ycfk_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="ycfk_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="ycfk_deleteInfo();">删除</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xzmbicon" plain="true" onclick="downloadTemplet();">下载模板</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daoruicon" plain="true" onclick="importInfo();">导入</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="backId"></th>
						<th field="model" width="130" align="center">销售机型</th>
						<th field="imei" width="160" align="center">IMEI</th>
						<th field="description" width="160" align="center">问题描述</th>
						<th field="feedBackDate" width="160" align="center">反馈日期</th>
						<th field="recipient" width="130" align="center">责任人</th>
						<th field="responsibilityUnit" width="130" align="center">责任单位</th>
						<th field="measures" width="160" align="center">处理措施</th>
						<th field="completionState" width="90" align="center">状态</th>
						<th field="completionDate" width="160" align="center">完成日期</th>
						<th field="customerName" width="130" align="center">客户姓名</th>
						<th field="phone" width="130" align="center">联系电话</th>
						<th field="remak" width="130" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">
			<!-- 添加-START-->
			<div id="w" class="easyui-window" title="新增400电话记录信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 680px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
						<from id="sss"> </from>
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="backIdi" value="0" />
										<input type="hidden" id="yidi" value="0" />
										<input type="hidden" id="problemsi"/>
										<input type="hidden" id="gIds"/>
										<input type="hidden" id="hideMethod"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;销售机型：</td>
									<td>
										<input type="text" id="modeli" style="width: 200px" />
									</td>
									<td>&nbsp;&nbsp;责任单位：</td>
									<td>
										<input type="text" id="responsibilityUniti" style="width: 200px" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;问题描述：</td>
									<td colspan="3"><input type="text" id="descriptioni" style="width: 420px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" readonly="readonly">
										<label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseYcfk();">添加</a></label>
									</td>
								</tr>
								<tr>
									<td>&nbsp;设备IMEI：</td>
									<td>
										<textarea type="text" id="imeii" style="width: 200px" row="10"></textarea>
									</td>
									<td>&nbsp;&nbsp;处理措施：</td>
									<td>
										<textarea type="text" id="measuresi" style="width: 200px" row="10" disabled="disabled"></textarea>
									</td>
								</tr>
								<tr>
									<td>&nbsp;反馈日期：</td>
									<td>
										<input type="text" id="feedBackDatei" style="width: 200px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" class="easyui-validatebox"/>
									</td>
									<td>&nbsp;&nbsp;完成时间：</td>
									<td>
										<input type="text" id="completionDatei" style="width: 200px" disabled="disabled" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" class="easyui-validatebox"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;客户姓名：</td>
									<td>
										<input type="text" id="customerNamei" style="width: 200px"/>
									</td>
									<td>&nbsp;&nbsp;联系电话：</td>
									<td>
										<input type="text" id="phonei" style="width: 200px"  />
									</td>
								</tr>
								<tr>
									<td>&nbsp;责&nbsp;任&nbsp;人&nbsp;：</td>
									<td>
										<input type="text" id="recipienti" style="width: 200px" />
									</td>
									<td>&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
									<td align="left">
										<input type="radio" name="completionState" value="0" checked="checked"/>待解决
										<input type="radio" name="completionState" value="1" />已完成
									</td>
								
								</tr>
								<tr>
									<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="3">
										<textarea type="text" id="remaki" style="width: 465px" row="10"></textarea>
									</td>
								</tr>
							</table>
						</form>
						<fieldset style="border:1px solid #ccc;display: none" id="measuresiTab">
							<legend><b>处理措施列表</b></legend>
							<table align="center" width="100%" cellpadding="2">        
						     <tr>       
								<td>
									<div id="measuresiTxt">
									</div>
								</td>
						     </tr>
						     </table>
						  </fieldset>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ycfkmanageSave()">保存</a> 
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseYcfkmanage()">关闭</a>
					</div>
				</div>
			</div>
		</div>
			<!-- 添加-END-->
			
			<!-- 添加-START-->
			
			<div id="w1_ycfkBase" class="easyui-window" title="更新400电话记录定义信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 450px; height: 280px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 5px; background: #fff; border: 1px solid #ccc;">
						<form id="mform_w1" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="yid" value="0" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
									<td>
										<input type="text" id="type" style="width: 150px" selected="selected" class="easyui-combobox" data-options="valueField:'id',textField:'text'" editable="false" panelHeight="auto" required="true" missingMessage="请选择类别" />
									</td>
								</tr>
								<tr>
									<td>问题描述：</td>
									<td>
										<input type="text" id="proDetails" style="width: 250px" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>处理方法：</td>
									<td>
										<textarea id="methods" style="width: 250px" maxlength="255"  class="easyui-validatebox" required="true" missingMessage="请填写此字段" ></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ycfkBase_Save()">保存</a> 
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseEncl_YcfkBase()">关闭</a>
					</div>
				</div>
			</div>
			
			<!-- 添加-END-->
			
			<!-- 选择400电话记录定义 -START-->
		<div id="w_ycfkBase" class="easyui-window" title="选择400电话记录定义" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			  style="width:760px;height:500px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
			 	<div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="400电话记录类别">
			       <ul id="typeTree-YCFK" class="easyui-tree" animate="true" dnd="false"></ul>
	  			</div>
				<div region='center' id="dic-center" style="overflow:auto" title="[详细信息]  注：双击选择" >
					<div id="ycfk_Toolbar" style=" padding: 10px 15px;">
						<div style="margin-bottom:5px">			
							<span>&nbsp;问题描述:&nbsp;<input type="text" class="form-search1" id="proDetailsSearch" style="width:180px" /></span>&nbsp; &nbsp;
							<span>&nbsp; &nbsp; 
								<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryYcfkBaseList(1);">查询</a>
							
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="ycfkBase_addInfo();">新增问题描述</a>
							
							</span> 
						</div>
					</div>
									
					<table id="ycfkBaseDataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fitColumns="true" autoRowHeight="true" 
						   striped="true" scrollbarSize="0" toolbar="#ycfk_Toolbar">
						<thead>
							<tr>
								<th checkbox="true" field="yid"></th>
								<th field="problems" width="220" align="center">类别</th>
								<th field="proDetails" width="220" align="center">问题描述</th>
								<th field="methods" width="220" align="center">处理方法</th>
							</tr>
						</thead>
					</table>
				</div> 
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
					<a id="ycfkbase" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w_ycfkBase')">关闭</a>  
			    </div> 
			</div>
	     </div>
	    <!-- 选择400电话记录定义 -END-->
			
	</div>
</body>
</html>