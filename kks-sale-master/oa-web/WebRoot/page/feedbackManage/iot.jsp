<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/feedbackManage/iot.js?20170314"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/ycfkTwomanage.js?20170822"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/ycfk.tree/ycfkTree.js"></script>
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
</style>
</head>
<!-- 物联网卡 -->
<body style="margin: 0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/>
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="ycfkTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="异常反馈管理列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:5px">	
					<span>反馈人: <input type="text" class="form-search" id="feedBackPerson_search" style="width: 135px" /></span>&nbsp; 
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px" /> </span>&nbsp; 
					<span>结束日期: <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px" /> </span>&nbsp;
					<span>
						本工站状态:&nbsp;
						<select id="completionState" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="0" selected="selected">待解决</option>
							<option value="1">已完成</option>
						</select>&nbsp;
					</span> 
					<span><a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="javascript:void(0)">查询</a> </span>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="ycfkTwo_updateInfo();">修改</a>						
							</td>	
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-plfszfjicon" plain="true" onclick="sendDataToNextSiteOpen();">发送下一工站</a>							
								<div class="datagrid-btn-separator"></div>
							</td>					
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>							
								<div class="datagrid-btn-separator"></div>
							</td>	 				
							<td>
								<div class="datagrid-btn-separator"></div>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="backId"></th>												
						<th field="followupPeople" width="100" align="center">跟进人</th>
						<th field="currentSite" width="100" align="center">所在工站</th>
						<th field="levelFlag" width="80" align="center">紧急度</th>
						<th field="severityFlag" width="80" align="center">问题严重程度</th>
						<th field="completionState" width="80" align="center">状态</th>
						<th field="projectCreateTime" width="130" align="center">设备到站时间</th>
						<th field="model" width="130" align="center">销售机型</th>
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="recipient" width="130" align="center">责任人</th>
						<th field="projectStatus" width="80" align="center">本工站状态</th>
						<th field="feedBackPerson" width="100" align="center">反馈人</th>
						<th field="feedBackDate" width="160" align="center">反馈日期</th>												
						<th field="customerName" width="130" align="center">客户姓名</th>
						<th field="phone" width="130" align="center">联系电话</th>
						<th field="warranty" width="100" align="center">保内/保外</th>
						<th field="description" width="220" align="center">问题描述</th>
						<th field="analysis" width="280" align="center">原因分析</th>
						<th field="number" width="80" align="center">配件数量</th>
						<th field="responsibilityUnit" width="130" align="center">责任单位</th>
						<th field="measures" width="280" align="center">处理措施</th>
						<th field="strategy" width="280" align="center">库存品处理策略</th>
						<th field="completionDate" width="160" align="center">完成日期</th>
						<th field="checkResult" width="80" align="center">验收结果</th>
						<th field="checker" width="130" align="center">验收人</th>
						<th field="copyPerson" width="130" align="center">抄送人</th>
						<th field="timeoutDate" width="90" align="center">未完成时间(天)</th>
						<th field="remak" width="220" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">
			<!-- 添加-START-->
			<div id="ycfk_w" class="easyui-window" title="新增异常反馈信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
				style="visibility:hidden; width: 650px; height: 480px; padding: 2px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 2px; background: #fff; border: 1px solid #ccc;">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="backIdi" value="0" />
										<input type="hidden" id="yidi" value="0" />
										<input type="hidden" id="problemsi"/>
										<input type="hidden" id="gIds"/>
										<input type="hidden" id="hideMethod"/>
										<span id="userName" style="display: none;">${USERSESSION.uName}</span>
									</td>
								</tr>
								<tr>
									<td>&nbsp;销售机型：</td>
									<td>
										<input type="text" id="modeli" style="width: 200px" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
									<td>&nbsp;责任单位：</td>
									<td>
										<input type="text" id="responsibilityUniti" style="width: 200px" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
								</tr>								
								<tr>
									<td>&nbsp;设备IMEI：</td>
									<td colspan="3">
										<textarea  id="imeii"  rows="2" cols="72" class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea>
									</td>									
								</tr>
								<tr>
									<td>&nbsp;配件数量：</td>
									<td>
										<input type="text" id="numberi" style="width: 200px" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段"/>
									</td>
									<td>&nbsp;&nbsp;反&nbsp;馈&nbsp;人：</td>
									<td>
										<span id="feedbackPersonSpan" style="display: none;">${USERSESSION.uName}</span>
										<input type="text" id="feedBackPersoni" style="width: 200px" disabled="disabled"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;责&nbsp;任&nbsp;人&nbsp;：</td>
									<td>										
										<input type="text" id="recipienti" style="width: 200px" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
									<td>&nbsp;&nbsp;跟&nbsp;进&nbsp;人：</td>
									<td>
										<input type="text" id="followupPeoplei" style="width: 200px" disabled="disabled"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;反馈日期：</td>
									<td>
										<input type="text" id="feedBackDatei" style="width: 200px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" class="easyui-validatebox"/>
									</td>
									<td>&nbsp;完成时间：</td>
									<td>
										<input type="text" id="completionDatei" style="width: 200px" disabled="disabled" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" class="easyui-validatebox"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;客户姓名：</td>
									<td>
										<input type="text" id="customerNamei" style="width: 200px" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
									<td>&nbsp;联系电话：</td>
									<td>
										<input type="text" id="phonei" style="width: 200px" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
								</tr>
								<tr>
									<td>保内|保外：</td>
									<td align="left">
										<input type="radio" name="warrantyi" value="0" />保内 
										<input type="radio" name="warrantyi" value="1" />保外
									</td>
									<td>&nbsp;验收结果：</td>
									<td align="left">
										<input type="radio" name="checkResult" value="0" checked="checked" style="display: none;"/>
										<input type="radio" name="checkResult" value="1" class="feedbackerShow"/><font color="red">不合格</font>
										<input type="radio" name="checkResult" value="2" class="feedbackerShow"/><font color="green">合格</font>
										<input type="radio" name="checkResult" value="3" class="feedbackerShow"/><font color="blue">有条件通过</font>
									</td>
								</tr>
								<tr>
									<td><font color="red">&nbsp;&nbsp;紧&nbsp;急&nbsp;度：</font></td>
									<td align="left">
										<input type="radio" name="levelFlag" value="0"/>一般
										<input type="radio" name="levelFlag" value="1" checked="checked"/><font color="blue">较紧急</font>
										<input type="radio" name="levelFlag" value="2" /><font color="red">紧急</font>
									</td>
									<td><font color="red">&nbsp;严重程度：</font></td>
									<td align="left">
										<input type="radio" name="severityFlag" value="0"/>一般
										<input type="radio" name="severityFlag" value="1" checked="checked"/><font color="#ff4500">重要</font>
										<input type="radio" name="severityFlag" value="2" /><font color="red">严重</font>
										<input type="radio" name="severityFlag" value="3" /><font color="#8b0000">重大</font>
									</td>

								</tr>
								<tr>
									<td><font color="red">&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</font></td>
									<td align="left">
										<input type="radio" name="completionState" value="0" checked="checked"/>待解决
										<input type="radio" name="completionState" value="2" />处理中
										<input type="radio" name="completionState" value="1" class="feedbackerShow"/>已完成
									</td>
									<td>&nbsp;&nbsp;验&nbsp;收&nbsp;人：</td>
									<td>
										<input type="text" id="checker" style="width: 200px" disabled="disabled"/>
									</td>
								</tr>
								<tr>
									<td align="right" style="width: 85px;">抄送人：</td>
									<td>
										<input type="text"  id="repair_w_zzgzDesc" style="width:200px;" readonly="readonly">
									</td>
									<td>
										<label class="only_show"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertZZGZ()">添加</a></label>
									</td>
								</tr>
								<tr>
									<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="3">
										<textarea  id="remaki" rows="2" cols="72" ></textarea>
									</td>
								</tr>
								<tr>
									<td>&nbsp;问题描述：</td>
									<td colspan="3">
										<textarea  rows="2" cols="72"  id="descriptioni"  class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea>
									</td>
								</tr>
								<tr>
									<td>&nbsp;附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：</td>
							     	<td colspan="3">
							     		<div id="descriptionFileDiv" class="fileInfo">						     		 	
							     		</div>
							     		<div id="fileDiv"> 								     			
							     		</div>						     		 
							     	</td>
								</tr>
								<tr>
									<td>&nbsp;处理措施：</td>
									<td colspan="3">
										<textarea  id="measuresi" rows="2" cols="72"  class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea>
									</td>
								</tr>
								<tr>
									<td>&nbsp;库存品处理策略：</td>
									<td colspan="3">
										<textarea  rows="2" cols="72"  id="strategy"  class="easyui-validatebox" required="false"></textarea>
									</td>
								</tr>
							</table>
						</form>
						<fieldset style="border:1px solid #ccc; width: 550px; margin: 2px 20px;" id="measuresiTab">
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
							<a id="ycfkAddOrUPdate" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ycfkTwomanageSave()">保存</a> 
							<a id="ycfkClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseYcfkTwomanage()">关闭</a>
						</div>
				</div>
			</div>
			<!-- 添加-END-->
		</div>
		
		<!-- 异常反馈发送数据到下一个工站  Start -->
		<div id="nextSiteUser_w" class="easyui-window" title="选择跟进人员" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
			style="width:600px;height:420px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center">	
					<span>
						下一工站：<select id="next_site" style="width: 130px;">
									<option value="" selected="selected">请选择</option>
									<option value="customer">客服</option>
									<option value="repair">维修</option>
									<option value="terminal">终端技术</option>	
									<option value="platform">平台技术</option>	
									<option value="quality">品质部</option>	
									<option value="product" >产品规划部</option>
									<option value="jimi">几米</option>	
									<option value="market">营销</option>	
									<option value="research">研发</option>
									<option value="test">测试</option>
									<option value="iot">物联网卡</option>
									<option value="sim">SIM卡平台</option>	
							   </select>
					</span>
					<br><br><br>
					<span>
						跟进人员：<input id = "followup_user" type="text" style="width: 300px;"/>
					</span>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="nextuserAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="ycfkSendData()">确定</a> 
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('nextSiteUser_w')">取消</a>
				</div>
			</div>
		</div>
		<!-- 异常反馈发送数据到下一个工站  END -->
		<div id="zzgz_w" class="easyui-window" title="选择最终故障" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			 style="width:850px;height:520px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
					<ul id="typeTree-ZZGZ" class="easyui-tree" animate="true" dnd="false"></ul>
				</div>
				<div region="center" id="dic-center" style="overflow:auto" title="[详细信息]  注：双击选择">
					<div style="height:280px;">
						<div id="zToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">
							</div>
						</div>

						<table id="zzgz_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false"
							   autoRowHeight="true" pagination="true" fitColumns="true" striped="true" scrollbarSize="0" toolbar="#zToolbar">
							<thead>
							<tr>
								<th field="id" hidden="true"></th>
								<th field="uName" width="220" align="center">姓名</th>
							</tr>
							</thead>
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选最终故障</div>
						<div class="selected-type-box" id ="selected-type-box-ZZGZ">
						</div>
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="ZZGZSave()">保存</a>
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('zzgz_w')">关闭</a>
				</div>
			</div>
		</div>
		<!-- 选择抄送人  - End-->
	</div>
</body>
</html>