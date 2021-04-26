<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript"
	src="${ctx}/res/js/report/rechargeReport.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript"
	src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout"
		style="overflow: hidden; width: 100%; height: 100%" scroll="no"
		fit="true">
		<div region="center" id="dic-center" style="overflow: auto"
			title="平台充值报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 2px">
					<span>开始日期: <input type="text" class="form-search"
						id="startTime"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
						style="width: 110px"></span>&nbsp; <span>&nbsp;汇款方式: <select
						id="remittanceMethodSearch" class="easyui-combobox form-search"
						style="width: 100px;" editable="false" panelHeight="auto">
							<option value="" selected="selected">全部</option>
							<option value="personPay">人工付款</option>
							<option value="aliPay">支付宝付款</option>
							<option value="weChatPay">微信付款</option>
					</select>&nbsp;
					</span> <span>单位名称：<input type="text" class="form-search"
						id="unitNameSearch" style="width: 135px" />&nbsp;
					</span>&nbsp;
				</div>
				<div style="margin-bottom: 5px">
					<span>结束日期: <input type="text" class="form-search"
						id="endTime"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
						style="width: 110px"></span>&nbsp; <span>一年/终身: <select
						id="termSearch" class="easyui-combobox form-search"
						style="width: 100px;" editable="false" panelHeight="auto">
							<option value="" selected="selected">全部</option>
							<option value="0">一年</option>
							<option value="1">终身</option>
					</select>&nbsp;
					</span> <span style="width: 200px"></span> <a id="searchinfo" name="ckek"
						class="easyui-linkbutton" iconCls="icon-search"
						href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-addicon" plain="true"
								onclick="recharge_addInfo();">增加</a></td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-xgicon" plain="true"
								onclick="recharge_updateInfo();">修改</a></td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-delecticon" plain="true"
								onclick="recharge_deleteInfo();">删除</a></td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-xzmbicon" plain="true"
								onclick="downloadTemplet();">下载模板</a></td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-daoruicon" plain="true" onclick="importInfo();">导入</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true"
				sortable="true" rownumbers="false" pagination="true" fit="true"
				fitColumns="true" striped="true" pageSize="20" scrollbarSize="0"
				toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="rechId"></th>
						<th field="unitName" width="150" align="center">单位名称</th>
						<th field="rechargeDate" width="135" align="center">充值日期</th>
						<th field="imei" width="130" align="center">IMEI</th>
						<th field="term" width="70" align="center">一年/终身</th>
						<th field="number" width="60" align="center">数量</th>
						<th field="unitPrice" width="60" align="center">单价(￥)</th>
						<th field="totalCollection" width="100" align="center">收款合计(￥)</th>
						<th field="ratePrice" width="100" align="center">税费(￥)</th>
						<th field="remittanceMethod" width="80" align="center">汇款方式</th>
						<th field="orderStatus" width="80" align="center">订单状态</th>
						<th field="remark" width="150" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>

		<div style="position: fixed;">
			<div id="addWindow" class="easyui-window" title="新增平台充值信息"
				modal="true" closed="true" maximizable="false" minimizable="false"
				collapsible="false"
				style="visibility: hidden; width: 500px; height: 580px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false"
						style="padding: 5px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="aform" action="">
							<table align="center">
								<tr style="display: none">
									<td><input type="hidden" id="rechIdAdd"
										style="width: 250px;" /></td>
								</tr>
								<tr>
									<td>&nbsp;单位名称：</td>
									<td><input type="text" id="unitNameAdd"
										style="width: 250px;" class="easyui-validatebox"
										required="true" missingMessage="请填写此字段" /></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IMEI：</td>
									<td><input type="text" id="imeiAdd" style="width: 250px;"
										class="easyui-validatebox" /></td>
								</tr>
								<tr>
									<td>&nbsp;充值日期：</td>
									<td><input type="text" id="rechargeDateAdd"
										style="width: 150px;"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
									</td>
								</tr>

								<tr>
									<td>&nbsp;一年数量：</td>
									<td><input type="text" id="yearNumber"
										style="width: 150px;" class="easyui-validatebox"
										validType="Number" /></td>
								</tr>
								<tr>
									<td>&nbsp;一年单价：</td>
									<td><input type="text" id="yearUnitPrice"
										style="width: 150px;" class="easyui-validatebox" 
										validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;一年合计：</td>
									<td><input type="text" id="yearTotalCollection" 
										style="width: 150px;" disabled="disabled" /></td>
								</tr>

								<tr>
									<td>&nbsp;终身数量：</td>
									<td><input type="text" id="lifeNumber"
										style="width: 150px;" value="0" class="easyui-validatebox"
										validType="Number" /></td>
								</tr>
								<tr>
									<td>&nbsp;终身单价：</td>
									<td><input type="text" id="lifeUnitPrice" 
										style="width: 150px;" class="easyui-validatebox"
										validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;终身合计：</td>
									<td><input type="text" id="lifeTotalCollection" 
										style="width: 150px;" disabled="disabled" /></td>
								</tr>

								<tr>
									<td>&nbsp;收款合计：</td>
									<td><input type="text" id="totalCollectionAdd" 
										style="width: 150px;" disabled="disabled" /></td>
								</tr>

								<tr>
									<td>&nbsp;税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点：</td>
									<td><select type="text" id="taxRate" style="width: 150px;"
										class="easyui-combobox" panelHeight="auto" editable="false">
											<option value=0 selected="selected">  0</option>
											<option value=6>  6</option>
											<option value=10>  10</option>
											<option value=17>  17</option>
									</select></td>
								</tr>

								<tr>
									<td>&nbsp;税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</td>
									<td><input id="ratePriceAdd" style="width: 150px;" 
										disabled="disabled" /></td>
								</tr>
								<tr>
									<td>&nbsp;汇款方式：</td>
									<td><select id="remittanceMethodAdd"
										class="easyui-combobox form-search" style="width: 150px;"
										editable="false" panelHeight="auto">
											<option value="" selected="selected">全部</option>
											<option value="personPay">人工付款</option>
											<option value="aliPay">支付宝付款</option>
											<option value="weChatPay">微信付款</option>
									</select></td>
								</tr>
								<tr>
									<td>&nbsp;订单状态：</td>
									<td><select id="orderStatusAdd"
										class="easyui-combobox form-search" style="width: 150px;"
										editable="false" panelHeight="auto">
											<option value="已充值" selected="selected">已充值</option>
											<option value="待充值">待充值</option>
									</select></td>
								</tr>
								<tr>
									<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7"><textarea id="remarkAdd"
											style="width: 100%; height: 72px;"></textarea></td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false"
						style="text-align: right; height: 30px; line-height: 30px;">
						<a id="recharge_save" class="easyui-linkbutton" iconCls="icon-ok"
							href="javascript:void(0)" onclick="rechargeAdd()">保存</a> <a
							id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)" onclick="addWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>

		<div style="position: fixed;">
			<div id="updateWindow" class="easyui-window" title="更新平台充值信息"
				modal="true" closed="true" maximizable="false" minimizable="false"
				collapsible="false"
				style="visibility: hidden; width: 500px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false"
						style="padding: 5px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td><input type="hidden" id="rechId" style="width: 250px;" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;单位名称：</td>
									<td><input type="text" id="unitName" style="width: 250px;"
										class="easyui-validatebox" required="true"
										missingMessage="请填写此字段" /></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IMEI：</td>
									<td><input type="text" id="imei" style="width: 250px;"
										class="easyui-validatebox" /></td>
								</tr>
								<tr>
									<td>&nbsp;充值日期：</td>
									<td><input type="text" id="rechargeDate"
										style="width: 150px;"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
									</td>
								</tr>

								<tr>
									<td>一年/终身：</td>
									<td><select type="text" id="term" style="width: 150px;"
										class="easyui-combobox" required="true" editable="false"
										panelHeight="auto">
											<option value="0">一年</option>
											<option value="1">终身</option>
									</select></td>
								</tr>

								<tr>
									<td>&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
									<td><input type="text" id="number" style="width: 150px;"
										value="0" class="easyui-validatebox" validType="Number" /></td>
								</tr>
								<tr>
									<td>&nbsp;单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</td>
									<td><input type="text" id="unitPrice"
										style="width: 150px;" class="easyui-validatebox"
										validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>

								<tr>
									<td>&nbsp;收款合计：</td>
									<td><input type="text" id="totalCollection"
										style="width: 150px;" disabled="disabled" /></td>
								</tr>

								<tr>
									<td>&nbsp;税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</td>
									<td><input id="ratePrice" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td>&nbsp;汇款方式：</td>
									<td><select id="remittanceMethod"
										class="easyui-combobox form-search" style="width: 150px;"
										editable="false" panelHeight="auto">
											<option value="" selected="selected">全部</option>
											<option value="personPay">人工付款</option>
											<option value="aliPay">支付宝付款</option>
											<option value="weChatPay">微信付款</option>
									</select></td>
								</tr>
								<tr>
									<td>&nbsp;订单状态：</td>
									<td><select id="orderStatus"
										class="easyui-combobox form-search" style="width: 150px;"
										editable="false" panelHeight="auto">
											<option value="已充值" selected="selected">已充值</option>
											<option value="待充值">待充值</option>
									</select></td>
								</tr>
								<tr>
									<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7"><textarea id="remark"
											style="width: 100%; height: 72px;"></textarea></td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false"
						style="text-align: right; height: 30px; line-height: 30px;">
						<a id="recharge_save" class="easyui-linkbutton" iconCls="icon-ok"
							href="javascript:void(0)" onclick="rechargeSave()">保存</a> <a
							id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>

		<!-- 收款合计自动计算 -->
		<script type="text/javascript">
			$(function() {
				$("#unitPrice").blur(function() {
					if ($("#unitPrice").val().length > 0 && $('#unitPrice').validatebox("isValid") && $("#number").val().length > 0 && $('#number').validatebox("isValid")) {
						$("#totalCollection").val(($("#unitPrice").val() * $("#number").val()).toFixed(2));
					} else {
						$("#totalCollection").val(0.00);
					}
				});
				$("#number").blur(function() {
					if ($("#unitPrice").val().length > 0 && $('#unitPrice').validatebox("isValid") && $("#number").val().length > 0 && $('#number').validatebox("isValid")) {
						$("#totalCollection").val(($("#unitPrice").val() * $("#number").val()).toFixed(2));
					} else {
						$("#totalCollection").val(0.00);
					}
				});
				
                $("#yearUnitPrice").blur(function() {
                    if ($("#yearUnitPrice").val().length > 0 && $('#yearUnitPrice').validatebox("isValid") && $("#yearNumber").val().length > 0 && $('#yearNumber').validatebox("isValid")) {
                        $("#yearTotalCollection").val(($("#yearUnitPrice").val() * $("#yearNumber").val()).toFixed(2));
                    } else {
                        $("#yearTotalCollection").val(0.00);
                    }
                    $("#yearTotalCollection").change();
                });
                $("#yearNumber").blur(function() {
                    if ($("#yearUnitPrice").val().length > 0 && $('#yearUnitPrice').validatebox("isValid") && $("#yearNumber").val().length > 0 && $('#yearNumber').validatebox("isValid")) {
                        $("#yearTotalCollection").val(($("#yearUnitPrice").val() * $("#yearNumber").val()).toFixed(2));
                    } else {
                        $("#yearTotalCollection").val(0.00);
                    }
                    $("#yearTotalCollection").change();
                });
                
                $("#lifeUnitPrice").blur(function() {
                    if ($("#lifeUnitPrice").val().length > 0 && $('#lifeUnitPrice').validatebox("isValid") && $("#lifeNumber").val().length > 0 && $('#lifeNumber').validatebox("isValid")) {
                        $("#lifeTotalCollection").val(($("#lifeUnitPrice").val() * $("#lifeNumber").val()).toFixed(2));
                    } else {
                        $("#lifeTotalCollection").val(0.00);
                    }
                    $("#lifeTotalCollection").change();
                });
                $("#lifeNumber").blur(function() {
                    if ($("#lifeUnitPrice").val().length > 0 && $('#lifeUnitPrice').validatebox("isValid") && $("#lifeNumber").val().length > 0 && $('#lifeNumber').validatebox("isValid")) {
                        $("#lifeTotalCollection").val(($("#lifeUnitPrice").val() * $("#lifeNumber").val()).toFixed(2));
                    } else {
                        $("#lifeTotalCollection").val(0.00);
                    }
                    $("#lifeTotalCollection").change();
                });
                
                
                $("#yearTotalCollection").change(function() {
                    $("#totalCollectionAdd").val((parseFloat($("#yearTotalCollection").val()) + parseFloat($("#lifeTotalCollection").val())).toFixed(2));
                    $("#totalCollectionAdd").change();
                });
                $("#lifeTotalCollection").change(function() {
                    $("#totalCollectionAdd").val((parseFloat($("#yearTotalCollection").val()) + parseFloat($("#lifeTotalCollection").val())).toFixed(2));
                	$("#totalCollectionAdd").change();
                });
                
                var taxRate = 0;
                $('#taxRate').combobox({
                	onSelect: function(param){
                		taxRate = param.value/100;
                        $("#ratePriceAdd").val(($("#totalCollectionAdd").val() * taxRate).toFixed(2));
                    }
                });
                
                $("#totalCollectionAdd").change(function() {
                    $("#ratePriceAdd").val(($("#totalCollectionAdd").val() * taxRate).toFixed(2));
                });
			});
		</script>
	</div>
</body>
</html>