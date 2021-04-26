<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/xspjcostsReport.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/price_pj.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="销售配件收费报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>客户名称: <input type="text" class="form-search" id="searchCusName" style="width: 150px"> </span>&nbsp;
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px"> </span>&nbsp;
					<span>结束日期: <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px"> </span>&nbsp;
					<span>
						汇款方式:&nbsp;
						<select id="payMethod_search" class="easyui-combobox form-search" style="width: 180px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="personPay">人工付款</option>
							<option value="aliPay">支付宝付款</option>
							<option value="weChatPay">微信付款</option>
						</select>&nbsp;
					</span>
					<span style="width: 200px"></span>&nbsp;&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="xspj_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="xspj_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="xspj_deleteInfo();">删除</a>
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
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="false" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="saleId"></th>
						<th field="cusName" width="180" align="center">客户名称</th>
						<th field="saleDate" width="150" align="center">销售日期</th>
						<th field="marketModel" width="100" align="center">市场型号</th>
						<th field="proNO" width="120" align="center">物料编码</th>
						<th field="proName" width="120" align="center">物料名称</th>
						<th field="groupName" width="120" align="center">组合名称</th>
						<th field="number" width="80" align="center">数量</th>
						<th field="unitPrice" width="80" align="center">单价(￥)</th>
						<th field="payReason" width="120" align="center">收款原因</th>
						<th field="payMethod" width="100" align="center">汇款方式</th>
						<th field="payDate" width="150" align="center">付款日期</th>
						<th field="expressType" width="100" align="center">快递方式</th>
						<th field="expressNO" width="100" align="center">快递单号</th>
						<th field="payPrice" width="80" align="center">配件费(￥)</th>
						<th field="logCost" width="80" align="center">运费(￥)</th>
						<th field="ratePrice" width="80" align="center">税费(￥)</th>
						<th field="sumPrice" width="80" align="center">总费用(￥)</th>
						<th field="receipt" width="80" align="center">是否开发票</th>
						<th field="rate" width="80" align="center">税率(%)</th>
						<th field="payState" width="80" align="center">确认结果</th>
						<th field="createBy" width="150" align="center">创建人</th>
						<th field="remark" width="150" align="center">备注原因</th>
					</tr> 
				</thead>
			</table>
		</div>
		
		<div style="position:fixed;">
			<div id="updateWindow" class="easyui-window" title="更新销售配件收费信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility:hidden;width: 550px; height: 535px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="saleId"/>
										<input type="hidden" id="proNO"/>
										<input type="hidden" id="masterOrSlave"/>
										<input type="hidden" id="groupPjId"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;客户名称：</td>
									<td>
										<input type="text" id="cusName" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
									<td>销售日期：</td>
									<td>
										<input type="text" id="saleDate" style="width: 150px;" onfocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;物料名称：</td>
									<td colspan="3">
										<input type="text" id="proName" style="width: 220px;" readonly="readonly" class="easyui-validatebox"  missingMessage="请选择物料"/>
										<label class="notGroupShow"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseMaterial();">选择</a></label>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;组合名称：</td>
									<td colspan="3">
										<input type="text" id="groupName" style="width: 220px;" readonly="readonly" class="easyui-validatebox" missingMessage="请选择物料"/>
										<label class="groupShow"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseGroupPj();">选择组合</a></label>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;市场型号：</td>
									<td>
										<input type="text" id="marketModel" style="width: 150px;" disabled="disabled"/>
									</td>
									<td>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</td>
									<td>
										<input type="text" id="unitPrice" style="width: 150px;" class="easyui-validatebox groupHide" required="true" validType="Amount" missingMessage="请填写此字段"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;收款原因：</td>
									<td>
										<input id="payReason" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
									<td>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
									<td>
										<input type="text" id="number" style="width: 150px;" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;配&nbsp;&nbsp;件&nbsp;&nbsp;费：</td>
									<td>
										<input id="payPrice" style="width: 150px;" value="0.00" disabled="disabled" />
									</td>
									<td>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</td>
									<td>
										<input id="logCost" style="width: 150px;" value="0.00" class="easyui-validatebox" validType="Amount"/>
									</td>
								</tr>
								<tr>	
								    <td>是否开发票： </td>
									<td>
										<input type="radio" name="receipt" value="0"/>是
										<input type="radio" name="receipt" value="1"  checked="checked"/>否
										<input type="text" id="rate" disabled="disabled" placeholder="税率（整数）" style="width: 50px;" maxlength="3" validType="Number" class="easyui-validatebox"/>%
									</td>
								    <td>税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费： </td>
									<td>
										<input type="text" id="ratePrice" style="width: 150px;" value="0.00" class="easyui-validatebox" validType="Amount" disabled="disabled"/>
									</td>
								</tr>
								<tr>
									 <td>&nbsp;&nbsp;总&nbsp;&nbsp;费&nbsp;&nbsp;用： </td>
									<td>
										<input type="text" id="sumPrice" style="width: 150px;" value="0.00" disabled="disabled"/>
									</td>
								</tr>				
								<tr>
									<td>&nbsp;&nbsp;&nbsp;汇款方式：</td>
									<td>
										<select id="payMethod" class="easyui-combobox" style="width: 150px;" editable="false" panelHeight="auto" >
											<option value="personPay">人工付款</option>
											<option value="aliPay">支付宝付款</option>
											<option value="weChatPay">微信付款</option>
										</select>
									</td>
									<td>付款日期：
									<td>
										<input type="text" id="payDate" style="width: 150px;" onfocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;快递方式：</td>
									<td>
										<input id="expressType" style="width: 150px;" class="easyui-validatebox"/>
									</td>
									<td>快递单号：</td>
									<td>
										<input id="expressNO" style="width: 150px;" class="easyui-validatebox"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;确认结果：</td>
									<td align="left">
										<input type="radio" name="payState" value="1" checked="checked"/>已确认
										<input type="radio" name="payState" value="0" />未确认
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7">
										<textarea id="remark" style="width: 100%; height: 80px;"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="xspjcosts_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="xspjcostsSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
			
			<div id="choosepj_w" class="easyui-window" title="选择配件料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:750px;height:450px;visibility: hidden;">
				<div class="easyui-layout" fit="true">
					<div region="center" style="overflow:auto">
						<div id="datagrid-toolbar">
							<div style="margin-bottom:2px">	
								<span>&nbsp;配件编码:&nbsp;<input type="text" class="form-search1" id="pj_proNO" style="width:135px"></span>&nbsp;
								<span>&nbsp;配件名称:&nbsp;<input type="text" class="form-search1" id="pj_proName" style="width:150px"></span>&nbsp;	
								<span>&nbsp;市场型号:&nbsp;<input type="text" class="form-search1" id="pj_marketModel" style="width:135px"></span>&nbsp;
								<span>
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPagePj();">查询</a>
								</span>
							</div>
						</div>
						<table id="choosepj_DataGrid" fit="true" sortable="true" rownumbers="false" 
								autoRowHeight="true" pagination="true" striped="true" pageSize="30" toolbar="#datagrid-toolbar">
								<thead>
								<tr>
									<th checkbox="true" field="id"></th>
									<th field="marketModel" width="100" align="center">市场型号</th>
									<th field="model" width="100" align="center">主板型号</th>
									<th field="proNO" width="120" align="center">配件编码</th>
									<th field="proName" width="150" align="center">配件名称</th>
									<th field="retailPrice" width="90" align="center">单价（￥）</th>
									<th field="proSpeci" width="280" align="center">规格</th>
									<th field="masterOrSlave" width="50px" align="center">M|T</th>
								</tr>
							</thead>				
						</table>
					</div>
					<div region="south" border="true" style="text-align:right;height:35px;line-height:30px;">
						<div>
							<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveChoose('pj');">保存</a>
							<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('choosepj_w')">关闭</a>
						</div>
				    </div>
				</div>
		     </div>
		     
		  <div id="chooseGroupPj_w" class="easyui-window" title="选择销售配件料组合" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:600px;height:450px;visibility: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="center" style="overflow:auto">
					<div id="datagrid-groupPjToolbar">
						<div style="margin-bottom:5px">	
							<span>名称:&nbsp;<input type="text" class="form-search2"  id="groupName_Pj" style="width:150px"></span>&nbsp;
							<span>市场型号:&nbsp;<input type="text" class="form-search2"  id="groupMarketModel_Pj" style="width:150px"></span>&nbsp;								
							<span> 
								<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageGroupPj('');">查询</a>
							</span>
						</div>
					</div>
					<table id="chooseGroupPj_DataGrid" fit="true" sortable="true" rownumbers="false" 
							autoRowHeight="true" pagination="true" fitColumns="true" striped="true" pageSize="20" toolbar="#datagrid-groupPjToolbar">
							<thead>
							<tr>
								<th checkbox="true" field="groupPjId"></th>
								<th field="marketModel" width="110" align="center">市场型号</th>
								<th field="groupName" width="110" align="center">名称</th>
								<th field="groupPrice" width="80" align="center">总价格（￥）</th>
								<th field="remark" width="150" align="center">备注</th>
							</tr>
						</thead>				
					</table>
				</div>
				<div region="south" border="true" style="text-align:right;height:35px;line-height:30px;">
					<div>
						<a id="saveGroupPj" class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveChoose('groupPj');">保存</a>
						<a id="closeGroupPj" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('chooseGroupPj_w')">关闭</a>
					</div>
			    </div>
			</div>
	      </div>
	      <div id="groupPjDetails_w" class="easyui-window" title="销售配件料组合详情" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:500px;height:380px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:280px;">
						<table id="groupPjDetails_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" 
							   striped="true" scrollbarSize="0">
							<thead>
								<tr>
									<!-- <th checkbox="true" field="id"></th> -->
									<th field="groupId" >groupId</th>
									<th field="pjlId" >pjlId</th>
									<th field="proNO" width="150" align="center">编码</th>
									<th field="proName" width="110" align="center">名称</th>
									<th field="retailPrice" width="80" align="center">单价（￥）</th>
									<th field="proNumber" width="80" align="center">数量</th>
									<th field="proSpeci" width="220" align="center">规格</th>
									<th field="masterOrSlave" width="50px" align="center">M|T</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('groupPjDetails_w')">关闭</a>
			    </div>
			</div>
	     </div>
		</div>
		
		
		<!-- 收款金额自动计算 -->
		<script type="text/javascript">
			$(function() {
				//收款金额随单价改变而改变
				$("#unitPrice").blur(function() {
					getSumPrice();
				});
				//收款金额随数量改变而改变
				$("#number").blur(function() {
					getSumPrice();
				});

				//收款金额随数量改变而改变
				$("#logCost").blur(function() {
					 if(!$("#logCost").val()){
						 $("#logCost").val(0);
					 }
					 getSumPrice();
				});

				//填写费率自动计算报价费用
				$("#rate").blur(function(){
					 getSumPrice();
				});	
			});
		</script>
	</div>
</body>
</html>