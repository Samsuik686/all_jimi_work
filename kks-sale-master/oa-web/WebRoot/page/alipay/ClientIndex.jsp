<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>几米终端客户服务系统</title>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/wechat/js/qrcode.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<style type="text/css">
/* .datagrid-toolbar {
	height: 20px;
	padding: 10px 10px;
	border-width: 0 0 1px 0;
	border-style: solid;
} */

.easyui-btn-xs {
	background-color: #fefefe;
	border: 1px solid #ccc;
	font-size: 12px;
	padding: 0 10px 2px;
	display: inline-block;
	border-radius: 5px;
	text-decoration: none;
}

.easyui-btn-xs:hover {
	background-color: #fceeee;
	color: #333;
	text-decoration: none;
}

.easyui-btn-green {
	background-color: #6aa84f;
}

.easyui-btn-green:hover {
	background-color: #eefcee;
}

.easyui-btn-red {
	background-color: #FF0000;
}

.easyui-btn-redable {
	background-color: #FFEFDB;
}

.acolor {
	color: #0066cc;
}
</style>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="north" split="true" border="false" iconCls="true" style="overflow: hidden;  background: url(${ctx}/res/image/title.jpg) no-repeat center center #0051A2; font-family: Verdana, 微软雅黑,黑体;height:60px;">
			<div class="logo" style="float: left; height: 60px;margin-top: 10px;">
				<img src="${ctx}/res/image/concox-logo.png" alt="康凯斯" />
			</div>
			<div style="margin:auto;position: relative;width: 400px;top: 6px; font-size: 30px;padding-left: 4%;"><span style="font-size:30px;color: white;">几米终端客户服务系统</span></div>
		</div>
		<div region="center" id="dic-center" title="送修设备维修状态">
			<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%;" fit="true">
				<div region="north" id="dic-north1" style="height: 85px;">
					<div>
						<form id="FromParams" action="${ctx}/page/alipay/resource/alipayapi.jsp" method="post" target="_blank" class="m-b0">
							<div style="width: auto; padding: 10px 15px;">
								<input type="hidden" name="total_fee" id="input-sumPrice" /> <input type="hidden" name="out_trade_no" id="out_trade_no" value="" />
								<table style="width: 100%;">
									<tr style="width: 100%;">
										<td>
											<div style="color: rgb(51, 153, 255); font-size: 14px; font-weight: bold; display: inline;">
												<input type="hidden" name="repairnNmber" id="repairnNmber" value="${BATCHNUMBER}" readonly="readonly" style="width: 120px" /> 
												<input type="hidden" name="phone" id="phone" value="${PHONENUMBER}" readonly="readonly" style="width: 120px" /> 
												&nbsp;送修批号为&nbsp;<span style="color: black;" id="showRepairNumber"> ${BATCHNUMBER} </span>
											    &nbsp;手机号为<span style="color: black;" id="showPhone"> ${PHONENUMBER} </span> 的客户，欢迎您 ！
											</div>
										</td>
										<td align="right">
											<span style="color: red; font-size: 16px; font-weight: bold; display: inline;">如有疑问请拨打售后服务专线：0755-27598050</span>
										</td>
									</tr>
								</table>
							</div>
						</form>
					</div>
					<div align="right">
						<table>
							<tr>
								<td align="right" style="text-align: center;">
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-weixinpay" plain="true" onclick="doWeChatPay()">微信支付</a>
								</td>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td align="right" style="text-align: center;">
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-alipay" plain="true" onclick="doAliPay()">支付宝支付</a>
								</td>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td align="right" style="text-align: center;">
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pjicon" plain="true" onclick="doAssess()">评价</a>
								</td>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td align="right" style="text-align: center;">
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="getSxdwInfoList()">查看客户信息</a>
								</td>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td align="right" style="text-align: center;">
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="updatePwd()">修改密码</a>
								</td>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td align="right" style="text-align: center;">
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-outload" plain="true" onclick="doOutLogin()">退出登录</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div region="west" id="dic-west1" style="width: 242px;" title="选择日期">
					<input type="hidden" id="treeMonth">
					<ul id="typeTreeTime" class="easyui-tree">
					</ul>
				</div>
				<div region="center" id="dic-center1">
					<div id="toolbar" style="padding: 5px 5px;">
						<div style="margin-bottom: 2px;">
							<span> 开始日期: <input type="text" class="form-search1" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 100px"></span>
							<span> &nbsp;送修批号: <input type="text" class="form-search" id="searchByRepairnNmber" style="width: 115px"> </span> 
							<span> &nbsp;处理状态: 
								<select id="state" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto">
										<option value="" selected="selected">全部</option>
										<option value="repair">待维修</option>
										<option value="price">待付款</option>
										<option value="ficheck">待终检</option>
										<option value="pack">待装箱</option>
										<option value="packed">已完成</option>
								</select>
							</span> 
						</div>
						<div style="margin-bottom: 2px;">
							<span> 结束日期: <input type="text" class="form-search1" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 100px"></span>
							<span> &nbsp;设备IMEI: <input type="text" class="form-search" id="searchByImei" style="width: 115px"> </span> 
							&nbsp;<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
						</div>
						<div>
							<table cellspacing="0" cellpadding="0" height="30px;">
								<tbody>
									<tr>
										<td align="left" style="text-align: center;">
											<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
										</td>
										<td align="left">
											<div class="datagrid-btn-separator"></div>
										</td>
										<td align="left" style="text-align: center;">
											<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openRepairNumberList()">历史送修设备</a>
										</td>
										<td align="left">
											<div class="datagrid-btn-separator"></div>
										</td>
										<td align="left">
											<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage(1);">刷新</a>
										</td>
										
										<td align="left">
                                            <div class="datagrid-btn-separator"></div>
                                        </td>
                                        <td align="left">
                                            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="giveUpAll();">全部放弃维修</a>
                                        </td>
                                                                                <td align="left">
                                        <div class="datagrid-btn-separator"></div>
                                        </td>
                                        <td align="left">
                                            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="recoverAll();">全部恢复维修</a>
                                        </td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<table id="DataGrid1" pagination="true" rownumbers="false" striped="true" pageSize="20">
						<thead>
							<tr height="30px;">
								<th colspan="19">
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="sumTotal">总维修数量：0</label>&nbsp;&nbsp;| &nbsp; 
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="sumLogs">物流费：￥0.00</label>&nbsp;&nbsp;| &nbsp; 
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="sumProlongCost">延保费：￥0.00</label>&nbsp;&nbsp;| &nbsp; 
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="sumRepair">维修费：￥0.00</label>&nbsp;&nbsp;| &nbsp; 
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="batchPjCosts">配件费：￥0.00</label>&nbsp;&nbsp;| &nbsp; 
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="ratePrice">税费：￥0.00</label>&nbsp;&nbsp;| &nbsp; 
									<label style='color: rgb(51, 153, 255); font-weight: bold;' id="sumPrice">总费用：￥0.00</label> 
									<input type="hidden" id="hidePrice" />
								</th>
							</tr>
							<tr>
								<th field="cusName" width="150" align="center">送修单位</th>
								<th field="repairnNmber" width="110" align="center">批次号</th>
								<th field="imei" width="110" align="center">IMEI</th>
								<th field="marketModel" width="110" align="center">机型</th>
								<th field="acceptTime" width="130" align="center">受理时间</th>
								<th field="isWarranty" width="60" align="center">是否保修</th>
								<th field="isRW" width="60" align="center">是否人为</th>
								<th field="wxbjDesc" width="200" align="center">维修报价描述</th>
								<th field="sumPrice" width="80" align="center">维修费(￥)</th>
								<th field="act" width="80" align="center">操作</th>
								<th field="state" width="110" align="center">进度</th>
								<th field="remark" width="150" align="center">送修备注</th>
								<th field="zzgzDesc" width="150" align="center">最终故障</th>
								<th field="sjfjDesc" width="150" align="center">随机附件</th>
								<th field="solutionTwo" width="150" align="center">处理措施</th>
								<th field="expressCompany" width="80" align="center">快递公司</th>
								<th field="expressNO" width="110" align="center">快递单号</th>
								<th field="remAccount" width="70" align="center">付款方式</th>
								<th field="payTime" width="130" align="center">支付时间</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>

		<!-- =========================================================================== -->
		<div style="position: fixed;">
			<!-- 支付  - Start-->
			<div id="w-pay" class="easyui-window" title="微信扫描支付" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="width: 500px; height: 380px; padding: 5px; line-height: 60px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 15px; background: #fff; border: 1px solid #ccc;">
						<div id="code" align="center"></div>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w-pay')">关闭</a>  
				    </div>
				</div>
			</div>
			<!-- 支付 - End-->

			<!-- 评价管理  - Start-->
			<div id="wpj" class="easyui-window" title="服务评价" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="width: 600px; height: 380px; padding: 5px; line-height: 60px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 5px; background: #fff; border: 1px solid #ccc;">
						<input type="hidden" id="f_id" />
						批次号：<label id="wpj_repairnNmber" style="color: red;"></label>
						<table align="center" cellspacing="4" style="font-size: 14px;">
							<br>
							<tr>
								<td>工作技能：</td>
								<td>
									<input type="radio" name="skillDesc" class="skillDesc" value="0" checked="checked" />满意&nbsp; 
									<input type="radio" name="skillDesc" class="skillDesc" value="1" />比较满意&nbsp; 
									<input type="radio" name="skillDesc" class="skillDesc" value="2" />一般&nbsp; 
									<input type="radio" name="skillDesc" class="skillDesc" value="3" />不满意&nbsp; 
									<input type="radio" name="skillDesc" class="skillDesc" value="4" />非常不满意&nbsp;
								</td>
							</tr>
							<tr>
								<td>服务效率：</td>
								<td>
									<input type="radio" name="serviceDesc" class="serviceDesc" value="0" checked="checked" />满意&nbsp; 
									<input type="radio" name="serviceDesc" class="serviceDesc" value="1" />比较满意&nbsp; 
									<input type="radio" name="serviceDesc" class="serviceDesc" value="2" />一般&nbsp; 
									<input type="radio" name="serviceDesc" class="serviceDesc" value="3" />不满意&nbsp; 
									<input type="radio" name="serviceDesc" class="serviceDesc" value="4" />非常不满意&nbsp;
								</td>
							</tr>
							<tr>
								<td>评价说明：</td>
								<td colspan="2">
									<textarea id="remarkA" disabled="disabled" style="width: 100%; height: 80px; resize: none;" maxlength="255"></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save()">保存</a> 
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wpj')">关闭</a>
					</div>
				</div>
			</div>
			<!-- 评价管理  - End-->

			<!-- 历史送修记录 Start -->
			<div id="w__repairList" class="easyui-window" title="历史送修批次记录" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 860px; height: 520px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
						<div id="repairNumberDataGrid_toolbar" style="padding: 2px 5px; height: 55px;">
							<div style="margin-bottom: 2px;">
								<span> 送修批号：<input type="text" id="search_repairNumber" class="form-search1" style="width: 110px"/></span>
								<span> <a id="searchinfo1" name="ckek" class="easyui-linkbutton" iconCls="icon-search" onclick="queryListPageTwo('')">查询</a> 
									<font color="3399ff" style="font-size: 14px; font-weight: bold;">点击批次号查看此批次设备详细信息</font>
								</span>
							</div>
						</div>
						<table id="repairNumberDataGrid" singleSelect="true" striped="true" pagination="true" rownumbers="false" autoRowHeight="true" fit="true" fitColumns="true" pageSize='10' toolbar="#repairNumberDataGrid_toolbar">
							<thead>
								<tr>
									<th field="w_cusName" width="150" align="center">送修单位</th>
									<th field="repairnNmber" width="120" align="center">批次号</th>
									<th field="acceptanceTime" width="100" align="center">受理日期</th>
									<th field="w_packTime" width="100" align="center">装箱日期</th>
									<th field="stateStr" width="150" align="center">状态</th>
								</tr>
							</thead>
						</table>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w__repairList')">关闭</a>
					</div>
				</div>
			</div>
			<!-- 历史送修记录 END -->

			<!-- 客户信息 -START-->
			<div id="w_sxdw" class="easyui-window" title="客户信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
				  style="width:760px;height:455px; visibility: hidden; ">
				<div class="easyui-layout" fit="true">
					<div region='center' >
						<div>
							<font color="3399ff" style="font-size: 14px; font-weight: bold;">双击修改信息</font>
						</div>
						<table id="DataGrid_sxdw" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="false" fitColumns="true" autoRowHeight="true" 
							   striped="true" scrollbarSize="0">
							<thead>
								<tr>
									<th field="cId" hidden="true"></th>
									<th field="cusName" width="110"   align="center">客户名称</th>
									<th field="linkman" width="110"  align="center">联系人</th>
									<th field="phone" width="110"  align="center">手机号</th>
									<th field="address" width="300" align="center">通信地址</th>
								</tr>
							</thead>
						</table> 
					</div> 
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w_sxdw')">关闭</a>  
				    </div> 
				</div>
		     </div>
		    <!-- 客户信息 -END-->

			<!-- 修改客户信息 start  -->
			<div id="w_updatesxdw" class="easyui-window" title="修改客户信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 500px; height: 300px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="sxdw_cId" /> 
										<input type="hidden" id="sxdw_oldAddress" />
										<input type="hidden" id="sxdw_repairnumCount" />
									</td>
								</tr>
								<tr>
									<td>送修单位：</td>
									<td colspan="3">
										<input type="text" id="sxdw_cusName" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;联&nbsp;系&nbsp;人：</td>
									<td>
										<input type="text" id="sxdw_linkman" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;手&nbsp;机&nbsp;号：</td>
									<td>
										<input type="text" id="sxdw_oldPhone" style="width: 150px;" disabled="disabled"/>
										<input type="text" id="oldValidateCode" name="oldValidateCode" placeholder="请输入验证码" class="form-control-reg newPhoneShow" style="width: 100px;"/>
									</td>
								</tr>
								<tr>
									<td>新手机号：</td>
									<td>
										<input type="text" id="sxdw_phone" style="width: 150px;" class="easyui-validatebox" validType="Mobile"/>
										<input type="text" id="validateCode" name="validateCode" placeholder="请输入验证码" class="form-control-reg newPhoneShow" style="width: 100px;"/>
										<input type="button" style="background-color:#7CCD7C;" value="获取验证码" onclick="getValidateCode();" class="newPhoneShow"/>
									</td>
								</tr>
								<tr>
									<td>联系地址：</td>
									<td colspan="7">
										<textarea id="sxdw_address" style="width: 350px; height: 80px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="sxdwmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveSxdw();">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeSxdw();">关闭</a>
					</div>
				</div>
			</div>
			<!-- 修改客户信息 end  -->
			
			<!-- 修改客户密码 start  -->
			<div id="w_updatePwd" class="easyui-window" title="修改密码" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 350px; height: 230px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="pwdform" action="">
							<table align="center">
								<tr>
									<td>手机号：</td>
									<td>
										<input type="text" id="u_phone" style="width: 150px;" disabled="disabled"/>
									</td>
								</tr>
								<tr>
									<td>原密码：</td>
									<td>
										<input type="password" id="u_loginPwd" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>新密码：</td>
									<td>
										<input type="password" id="u_newLoginPwd" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
								</tr>
								<tr>
									<td>确认新密码：</td>
									<td>
										<input type="password" id="u_reLoginPwd" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="pwd_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="savePwd();">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closePwd();">关闭</a>
					</div>
				</div>
			</div>
			<!-- 修改客户信息 end  -->
			<!-- ============================================================================= -->
		</div>
	</div>
	<script type="text/javascript">
		var currentPageNum;
		var currentPageNumTwo;
		$(function() {
			$('.panel-tool').hide();//折叠按钮不显示
			keySearch(queryListPage);
			keySearch(queryListPageTwo, 1);
			$("#searchByRepairnNmber").val($.trim($("#repairnNmber").val()));
			$("#searchByImei").val("");
			$("#startTime").val("");//开始日期
			$("#endTime").val("");//结束日期
			$("#treeMonth").val("");//月

			CheckOut();
			datagridInit();
			treeInit();
			$("#price_table").datagrid({});
			$(".skillDesc").change(function() {
				if (checkSkill()) {
					$("#remarkA").removeAttr("disabled");
				} else {
					if (!checkService()) {
						$("#remarkA").val("");
						$("#remarkA").attr("disabled", "disabled");
					}
				}
			});

			$(".serviceDesc").change(function() {
				if (checkService()) {
					$("#remarkA").removeAttr("disabled", "disabled");
				} else {
					if (!checkSkill()) {
						$("#remarkA").val("");
						$("#remarkA").attr("disabled", "disabled");
					}
				}
			});

			$('#searchinfo').click(function() {
				/* $("#treeMonth").val(""); */
				queryListPage(1);
			});

			$("#searchinfo1").click(function() {
				queryRepairNumberList(1);
			});

			// 新手机号
			$(".newPhoneShow").hide(); //默认不显示发送验证码
			 $("#sxdw_phone").blur(function(){
				 var phone = $.trim($("#sxdw_phone").val());
				 var oldPhone = $.trim($("#sxdw_oldPhone").val());
				 if(phone && phone.length == 11 && phone != oldPhone){
					$(".newPhoneShow").show();
				 }else{
					$(".newPhoneShow").hide(); 
				}
			 });

			// 批次号输入框有修改，取消树节点选中事件
			$("#searchByRepairnNmber").blur(function(){
				$("#searchByRepairnNmber").change(function(){
					$('#typeTreeTime').find('.tree-node-selected').removeClass('tree-node-selected');//取消树节点选中事件
				});
			});
		});

		$.messager.defaults = {
			ok : "确定",
			cancel : "取消"
		};
		function checkSkill() {
			var $selectedvalue = $("input[name='skillDesc']:checked").val();
			if ($selectedvalue > 1) {
				return true;
			} else {
				return false;
			}
		}
		function checkService() {
			var $selectedvalue = $("input[name='serviceDesc']:checked").val();
			if ($selectedvalue > 1) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 初始化 表单工具栏面板
		 */
		function datagridInit() {
			$("#DataGrid1").datagrid({
			});
			queryListPage(1);

			$("#repairNumberDataGrid").datagrid({
			});

			$("#DataGrid_sxdw").datagrid({
				singleSelect : true,// 是否单选
				onDblClickRow : function(rowIndex, row) {
					update_sxdw(rowIndex, row);
				}
			});
		}

		function CheckOut() {
			if ($("#repairnNmber").val() == "") {
				location.href = ctx + "/page/alipay/login.jsp";
			}
		}

		//当前客户历史送修记录	
		function openRepairNumberList() {
			windowVisible("w__repairList");
			$("#search_repairNumber").val("");
			$("#w__repairList").window("open");
			queryListPageTwo(1);
		}

		function queryListPageTwo(pageNumber) {
			currentPageNumTwo = pageNumber;
			if (currentPageNumTwo == "" || currentPageNumTwo == null) {
				currentPageNumTwo = 1;
			}
			var pageSize = getCurrentPageSize('repairNumberDataGrid');
			var selParams = {
				'phone' : $.trim($("#phone").val()),
				'searchKey' : $.trim($("#search_repairNumber").val()),
				'currentpage' : currentPageNumTwo,
				'pageSize' : pageSize
			};
			var url = ctx + "/custClient/queryRepairNumberList";

			asyncCallService(url, 'post', false, 'json', selParams, function(returnData) {
				if (returnData.code == undefined) {
					var ret = returnData.data;
					$.each(ret, function(i, item) {
						if (item.stateStr == '维修中') {
							item.stateStr = '<font color="#3399ff" style=" font-weight: bold;">维修中</font>';
						} else if (item.stateStr == '已完成') {
							item.stateStr = '<font color="#13d813" style=" font-weight: bold;">已完成</font>';
						} else {
							item.stateStr = "";
						}

						if(item.acceptanceTime){
							item.acceptanceTime = item.acceptanceTime.substring(0, item.acceptanceTime.indexOf(" 00"))
						}
						
						if (item.repairnNmber) {
							item.repairnNmber = '<a href="javascript:;" style="text-decoration:underline" class="easyui-linkbutton acolor" onclick="selDataByRepairNumber(this)">' + item.repairnNmber + '</a>';
						}
					});
					$("#repairNumberDataGrid").datagrid('loadData', ret);
					getPageSizeTwo("repairNumberDataGrid");
					resetCurrentPageShowT("repairNumberDataGrid", currentPageNumTwo);
				} else {
					$.messager.alert("操作提示", "网络错误", "info");
				}
			});
		}

		/**
		 *从历史批次记录点击批次号查询详细信息
		 */
		function selDataByRepairNumber(repairnNumber) {
			if (repairnNumber.innerText) {
				$("#repairnNmber").val(repairnNumber.innerText);
				$("#searchByRepairnNmber").val(repairnNumber.innerText);
				$("#showRepairNumber").text(repairnNumber.innerText);//显示的批次号
				windowCommClose('w__repairList');
				queryListPage(1);
			} else {
				$.messager.alert("操作提示", "未获取到批次号", "info");
			}
		}

		/**
		 * 评价
		 */
		function doAssess() {
			if($.trim($("#searchByRepairnNmber").val())){
				var reqAjaxPrams ={
						"repairnNmber":	$.trim($("#searchByRepairnNmber").val())
					}
				var url = ctx + "/custClient/checkEvalueat";
				asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						if (returnData.data) {
							$("#remarkA").val(returnData.data.fremark);
							$("input:radio[name=skillDesc][value='" + returnData.data.skillDesc + "']").prop("checked", true);
							$("input:radio[name=serviceDesc][value='" + returnData.data.serviceDesc + "']").prop("checked", true);
							$("#f_id").val(returnData.data.id);
							if (returnData.data.skillDesc > 1 || returnData.data.serviceDesc > 1) {
								$("#remarkA").attr("disabled", false);
							}
						}else{
							$("#remarkA").val("");
							$("input:radio[name=skillDesc][value='0']").prop("checked", true);
							$("input:radio[name=serviceDesc][value='0']").prop("checked", true);
							$("#remarkA").attr("disabled", "disabled");
						}
						windowCommOpen('wpj');
						$("#wpj_repairnNmber").html($("#searchByRepairnNmber").val());
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}else{
				$.messager.alert("操作提示", "未选择要评价的批次", "info");
			}
		}

		/**
		 * 保存评价
		 */
		function save() {
			var selectedSkill = $("input[name='skillDesc']:checked").val();
			var selectedService = $("input[name='serviceDesc']:checked").val();
			var remarkA = $("#remarkA").val();
			var reqAjaxPrams = {
				"skillDesc" : selectedSkill,
				"serviceDesc" : selectedService,
				"Fremark" : remarkA,
				"id" : $("#f_id").val(),
				"repairnNmber" : $.trim($("#searchByRepairnNmber").val())
			};
			var isVaild = false;
			if (checkService() || checkSkill()) {
				if (remarkA.length > 0) {
					isVaild = true;
				}
			} else {
				isVaild = true;
			}
			if (isVaild) {
				var url = ctx + "/custClient/saveEvaluate";
				asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", returnData.msg, "info", function() {
							windowCommClose('wpj');
							$("#wpj_repairnNmber").html("");
						});
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			} else {
				$.messager.alert("操作提示", "您的评价过低，请您填写相关意见到评价说明中", "info");
			}
		}

		/**
		 * 退出登录
		 */
		function doOutLogin() {
			$.messager.confirm("操作提示", "是否退出登录", function(conf) {
				if (conf) {
					var url = ctx + "/custClient/doOutLogin";
					asyncCallService(url, 'post', false, 'json', null, function(returnData) {
						if (returnData.code == '0') {
							location.href = ctx + "/page/alipay/login.jsp";
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				}
			});
		}

		/**
		 * 查询数据
		 */
		function queryListPage(pageNumber) {
			currentPageNum = pageNumber;
			if (currentPageNum == "" || currentPageNum == null) {
				currentPageNum = 1;
			}
			falg = true;
			var pageSize = getCurrentPageSize('DataGrid1');
			var url = ctx + "/custClient/queryList";
			var searchByRepairnNmber = $.trim($("#searchByRepairnNmber").val());
			if (searchByRepairnNmber) {
				$("#repairnNmber").val(searchByRepairnNmber);
				$("#showRepairNumber").text(searchByRepairnNmber);//显示的批次号
			}
			var searchByImei = $.trim($("#searchByImei").val());
			if(searchByImei && searchByImei.length < 6){
				$.messager.alert("操作提示", "输入的IMEI长度不能小于6位", "info");
				return;
			}
			var param = {
				"phone" : $.trim($("#phone").val()),
				"repairnNmber" : $.trim($("#searchByRepairnNmber").val()),
				"imei" : searchByImei,
				"searchPrice" : $("#state").combobox('getValue'),
				"startTime" : $("#startTime").val(),
				"endTime" : $("#endTime").val(),
				"treeMonth" : $.trim($("#treeMonth").val()),
				"currentpage" : currentPageNum,
				"pageSize" : pageSize
			}
			$("#DataGrid1").datagrid('loading');
			asyncCallService(url, 'post', false, 'json', param, function(returnData) {
				if (returnData.code == undefined) {
					$.each(returnData.data.clients, function(i, item) {
						getState(item);
					});

					if (returnData.data.client != undefined) {
						var sumLogs = returnData.data.client.logCost;
						var sumProlongCost = returnData.data.client.prolongCost;
						var sumPrice = returnData.data.client.totalPrice;
						var sumTotal = returnData.data.client.totalsum;
						var sumRepairPrice = returnData.data.client.sumPrice;
						var ratePrice = returnData.data.client.ratePrice;
						var batchPjCosts = returnData.data.client.batchPjCosts;
						if (sumLogs != undefined) {
							(sumLogs == "") ? sumLogs = 0 : sumLogs = sumLogs;

							$("#sumLogs").html("物流费:￥" + sumLogs);
						}
						console.log(sumProlongCost);
						if (sumProlongCost != undefined) {
                            (sumProlongCost == "") ? sumProlongCost = 0 : sumProlongCost = sumProlongCost;
                            $("#sumProlongCost").html("延保费:￥" + sumProlongCost);
                        }
						if (sumPrice != undefined) {
							(sumPrice == "") ? sumPrice = 0 : sumPrice = sumPrice;
							
							/* if (!$("#input-sumPrice").val()) { */
								$("#input-sumPrice").val(sumPrice + sumLogs);
							/* } */
							$("#sumPrice").html("总费用：￥" + sumPrice);
						}

						if (sumRepairPrice != undefined) {
							(sumRepairPrice == "") ? sumRepairPrice = 0 : sumRepairPrice = sumRepairPrice;
							$("#sumRepair").html("维修费：￥" + sumRepairPrice);
						}
						if (ratePrice) {
							(ratePrice == "") ? ratePrice = 0 : ratePrice = ratePrice;
							$("#ratePrice").html("税费：￥" + ratePrice);
						}
						if (batchPjCosts) {
							(batchPjCosts == "") ? batchPjCosts = 0 : batchPjCosts = batchPjCosts;
							$("#batchPjCosts").html("配件费：￥" + batchPjCosts);
						}

						if (sumTotal != undefined) {
							(sumTotal == "") ? sumTotal = 0 : sumTotal = sumTotal;
							$("#sumTotal").html("总维修数量：" + sumTotal);
						}
					} else {
						if (returnData.data.repairCount) {
							$("#sumTotal").html("总维修数量：" + returnData.data.repairCount);
						} else {
							$("#sumTotal").html("总维修数量：0");
						}

						$("#batchPjCosts").html("配件费：￥ 0.00");
						$("#ratePrice").html("税费：￥ 0.00");
						$("#sumPrice").html("总费用：￥ 0.00");
						$("#sumLogs").html("物流费:￥ 0.00");
						$("#sumProlongCost").html("延保费:￥ 0.00");
						$("#sumRepair").html("维修费：￥ 0.00");
						$("#input-sumPrice").val(0);
					}
					$("#DataGrid1").datagrid('loadData', returnData.data.clients);
					getPageSize();
					resetCurrentPageShow(currentPageNum);
					$("#DataGrid1").datagrid('loaded');
				} else {
					location.href = ctx + "/page/alipay/login.jsp";
				}
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info", function(){
					$("#DataGrid1").datagrid('loaded');
					location.href = ctx + "/page/alipay/login.jsp";
				});
			});
		}

		function getState(item) {
			//保内保外  0:保内
			if (item.isWarranty == 0) {
				item.isWarranty = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>是</label>";
			} else if (item.isWarranty == 1) {
				item.isWarranty = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>否</label>";
			}

			//是否人为
			if (item.isRW == 0) {
				item.isRW = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>是</label>";
			} else if (item.isRW == 1) {
				item.isRW = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>否</label>";
			}

			//付款方式
			if (item.sumPrice > 0) {
				if (item.remAccount == 'personPay') {
					item.remAccount = "<label style='font-weight: bold;'>人工付款</label>";
				} else if (item.remAccount == 'aliPay') {
					item.remAccount = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
				} else if (item.remAccount == 'weChatPay') {
					item.remAccount = "<label style='color:green;font-weight: bold;'>微信付款</label>";
				} else {
					item.remAccount = "";
				}
			} else {
				item.remAccount = "";
			}

			//待报价之前的数据维修费为0		
			if (!item.sumPrice) {
				item.sumPrice = "0.00";
			} else if (item.state == 3) {
				item.sumPrice = "0.00";
			}

			//状态
			if (item.state == -1) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已发货</label>";
			} else if (item.state == 0) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已受理</label>";
				falg = false;
			} else if (item.state == 1) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已受理，待分拣</label>";
				falg = false;
			} else if (item.state == 2) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已分拣，待维修</label>";
				falg = false;
			} else if (item.state == 3) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>待报价</label>";
				falg = false;
			} else if (item.state == 4) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已付款，待维修</label>";
				falg = false;
			} else if (item.state == 5) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已维修，待终检</label>";
				falg = false;
			} else if (item.state == 6) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已终检，待维修</label>";
				falg = false;
			} else if (item.state == 7) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已终检，待装箱</label>";
				falg = false;
			} else if (item.state == 8) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>放弃报价，待装箱</label>";
				falg = false;
			} else if (item.state == 9) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已报价，待付款</label>";
				falg = false;
			} else if (item.state == 10) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>不报价，待维修</label>";
				falg = false;
			} else if (item.state == 11) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>放弃报价，待维修</label>";
				falg = false;
			} else if (item.state == 12) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已维修，待报价</label>";
				falg = false;
			} else if (item.state == 13) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>待终检</label>";
				falg = false;
			} else if (item.state == 14) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>放弃维修</label>";
				falg = false;
			} else if (item.state == 15) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>测试中</label>";
				falg = false;
			} else if (item.state == 16) {
				item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已测试，待维修</label>";
				falg = false;
			} else if (item.state == 17) {
                item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已受理，已测试</label>";
                falg = false;
            } else if (item.state == 18) {
                item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>已测试，待分拣</label>";
                falg = false;
            } else if (item.state == 19) {
                item.state = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>不报价，测试中</label>";
                falg = false;
            }

			if ($.trim(item.state.substring(item.state.indexOf(">") + 1, item.state.indexOf("</label>"))) == "已报价，待付款") {
				item.act = "<a  href='javascript:void(0)' class='easyui-btn-xs easyui-btn-green' onclick=\"repairAct('" + item.imei + "','" + item.acceptTime + "','1')\">放弃维修</a>";
			} else if (item.giveUpRepairStatus == '1') {
				if ($.trim(item.state.substring(item.state.indexOf(">") + 1, item.state.indexOf("</label>"))) == "放弃维修" && item.isPay == '1') {
					item.act = "<a  href='javascript:void(0)' class='easyui-btn-xs easyui-btn-red' onclick=\"repairAct('" + item.imei + "','" + item.acceptTime + "','0')\">恢复维修</a>";
				} else {
					item.act = "<a  href='javascript:void(0)' class='easyui-btn-xs easyui-btn-redable'>恢复维修</a>";
				}
			} else {
				item.act = "";
			}
		}

		var clickFlag = false; //客户是否点击放弃或恢复按钮
		function repairAct(imei, acceptTime, repairStatus) {
			var conStr = "";
			if (repairStatus == '0') {
				conStr = "<label style='color:red;'>确定恢复维修此台设备？</label>";
			} else if (repairStatus == '1') {
				conStr = "<label style='color:red;'>确定放弃维修此台设备？</label>";
			}

			$.messager.confirm('系统提示', conStr, function(conf) {
				if (conf) {
					if(!clickFlag){
						clickFlag = true;
						var params = "imei=" + imei + "&acceptanceTime=" + acceptTime + "&giveUpRepairStatus=" + repairStatus;
						var url = ctx + "/custClient/updateRepairStatus";
						asyncCallService(url, 'post', false, 'json', params, function(returnData) {
							clickFlag = false;
							processSSOOrPrivilege(returnData);
							$.messager.alert("操作提示", returnData.msg, "info", function() {
								queryListPage(currentPageNum);
							});
						}, function() {
							clickFlag = false;
							$.messager.alert("操作提示", "网络错误", "info");
						});
					}
				}
			});
		}
		
		// TODO flag
		function giveUpAll() {
		    var conStr = "<label style='color:red;'>确定放弃维修该批次全部设备？</label>";
	        $.messager.confirm('系统提示', conStr, function(conf) {
	            if (conf) {
                    var params = "repairnNmber=${BATCHNUMBER}" + "&phone=${PHONENUMBER}" +"&giveUpRepairStatus=1";
                    var url = ctx + "/custClient/updateAllRepairStatus";
                    asyncCallService(url, 'post', false, 'json', params, function(returnData) {
                        processSSOOrPrivilege(returnData);
                        $.messager.alert("操作提示", returnData.msg, "info", function() {
                            queryListPage(currentPageNum);
                        });
                    }, function() {
                        $.messager.alert("操作提示", "网络错误", "info");
                    });
	            }
	        });
		}
		
		function recoverAll() {
		    var conStr = "<label style='color:red;'>确定恢复维修该批次全部设备？</label>";
	        $.messager.confirm('系统提示', conStr, function(conf) {
	            if (conf) {
	                var params = "repairnNmber=${BATCHNUMBER}" + "&phone=${PHONENUMBER}" +"&giveUpRepairStatus=0";
	                var url = ctx + "/custClient/updateAllRepairStatus";
	                asyncCallService(url, 'post', false, 'json', params, function(returnData) {
	                    processSSOOrPrivilege(returnData);
	                    $.messager.alert("操作提示", returnData.msg, "info", function() {
	                        queryListPage(currentPageNum);
	                    });
	                }, function() {
	                    $.messager.alert("操作提示", "网络错误", "info");
	                });
	            }
	        });
		}

		function doAliPay() {
			if ($("#input-sumPrice").val() != "" && $("#input-sumPrice").val() > 0) {
				var param = {
						"repairnNmber":	$.trim($("#searchByRepairnNmber").val())
					}
				var url = ctx + "/custClient/checkAipay";
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					if(returnData.code == undefined){
						if (returnData.data.totalPrice > 0) {
							$("#input-sumPrice").val(returnData.data.totalPrice);
							$("#out_trade_no").val(returnData.data.outTradeNo);
							$("#FromParams").submit();
						} else {
							$.messager.alert("操作提示", "您的设备维修费用已支付成功！", "warning");
						}
					}else{
						$.messager.alert("操作提示", returnData.msg, "warning");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			} else {
				$.messager.alert("操作提示", "暂无费用支付", "warning");
			}
		}

		function doWeChatPay() {
			if ($("#input-sumPrice").val() != "" && $("#input-sumPrice").val() > 0) {
				$("#code").html("");
				$.ajax({
					url : ctx + "/wechatdev/getTotalPrice",
					type : 'post',
					async : false,
					dataType : 'json',
					data : null,
					cache : false,
					success : function(returnData) {
						if (returnData.code == "0") {
							var qrcode = new QRCode('code', {
								text : returnData.msg,
								width : 250,
								height : 250,
								colorDark : '#000000',
								colorLight : '#ffffff',
								correctLevel : QRCode.CorrectLevel.H
							});
							// 使用 API
							qrcode.clear();
							qrcode.makeCode(returnData.msg);
							windowCommOpen("w-pay");
						} else {
							$.messager.alert("操作提示", returnData.msg, "warning");
						}
					}
				});
			} else {
				$.messager.alert("操作提示", "暂无费用支付", "warning");
			}
		}

		//导出数据
		function exportInfo() {
			var url =  ctx + "/custClient/ExportDatas";
			var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
			$form1.append($("<input type='hidden' name='phone' value='" + $.trim($("#phone").val()) +"'/>"));
			$form1.append($("<input type='hidden' name='repairnNmber' value='" + $.trim($("#searchByRepairnNmber").val()) +"'/>"));
			$form1.append($("<input type='hidden' name='imei' value='" + $.trim($("#searchByImei").val()) +"'/>")); 
			$form1.append($("<input type='hidden' name='searchPrice' value='" + $("#state").combobox('getValue') +"'/>"));
			$form1.append($("<input type='hidden' name='startTime' value='" + $.trim($("#startTime").val()) +"'/>"));
			$form1.append($("<input type='hidden' name='endTime' value='" + $.trim($("#endTime").val()) +"'/>")); 
			$form1.append($("<input type='hidden' name='treeMonth' value='" + $.trim($("#treeMonth").val()) +"'/>")); 
			$("body").append($form1);
			$form1.submit();
			$form1.remove();
		}
		/* ===========================右侧日期树 start================================== */
		function treeInit() {
			$.ajax({
				type : "POST",
				async : false,
				cache : false,
				url : ctx + '/custClient/getTreeTime',
				data : {
					"phone" : $.trim($("#phone").val())
				},
				dataType : "json",
				success : function(returnData) {
					treeLoad(returnData);
				},
				error : function() {
					$.messager.alert("操作提示", "网络错误", "info");
				}
			});
		}

		function treeLoad(returnData) {
			var groupList = '[{"id":"","text":"受理日期", "state" : "open", "children":[';
			;
			var reg = /,$/gi;
			$.each(returnData.data, function(i, j) {
				if (j.children) {
					groupList += '{"id":"' + j.id + '","text":"' + j.text + '", "state" : "closed", "children":[';
					$.each(j.children, function(a, b) {
						groupList += '{"id":"' + b.id + '","text":"' + b.text + '"},';
					});
					groupList = groupList.replace(reg, "");
					groupList += ']},';
				} else {
					groupList += '{"id":"' + j.id + '","text":"' + j.text + '", "state" : "closed"},';
				}
			});
			groupList = groupList.replace(reg, "");
			groupList += ']}]';

			$('#typeTreeTime').tree({
				data : eval(groupList),
				/* onLoadSuccess:function() {  
				    $("#typeTreeTime").tree("collapseAll");  
				}, */
				onClick : function(node) {
					if (node.id) {
							$("#startTime").val("");//开始日期
							$("#endTime").val("");//结束日期
						if (node.id.indexOf("--") > 0) {
							$("#treeMonth").val("");
							var nodeId = node.id;
							var repairNumber = nodeId.substring(0, nodeId.indexOf("--"));
							$("#searchByRepairnNmber").val(repairNumber);
							$("#repairnNmber").val(repairNumber);
						}else if(node.id.indexOf("--") <=0 && node.id.indexOf("-") >0){
							$("#treeMonth").val(node.id);
							$("#searchByRepairnNmber").val("");
							$("#searchByImei").val("");
						}
					}else{
						$("#treeMonth").val("");
					}
					queryListPage(1);
				}
			});
		}
		/* ===========================右侧日期树 end================================== */

		// 修改选中客户
		function update_sxdw() {
			var row = $('#DataGrid_sxdw').datagrid('getSelected');
			if (row) {
				var params = "cId=" + row.cid;
				var url = ctx + "/custClient/getStateByCId";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						updateSxdwInfo();
						var repairnumCount = $("#sxdw_repairnumCount").val();
						if(repairnumCount && repairnumCount > 0){
							$(".validatebox-tip").remove();
							$(".validatebox-invalid").removeClass("validatebox-invalid");
							$('#w_updatesxdw').window('open');
							windowVisible("w_updatesxdw");
						}
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			} else {
				$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
			}
		}

		function closeSxdw() {
			$("#sxdw_cId").val("");
			$("#sxdw_cusName").val("");
			$("#sxdw_linkman").val("");
			$("#sxdw_phone").val("");
			$("#sxdw_address").val("");
			$("#sxdw_oldPhone").val("");
			$("#sxdw_oldAddress").val("");
			$("#validateCode").val("");
			$("#oldValidateCode").val("");
			$("#sxdw_repairnumCount").val("");
			windowCommClose('w_updatesxdw')
		}

		function getSxdwInfoList() {
			var params = {
				"phone" : $.trim($("#phone").val())
			};
			var url = ctx + "/custClient/getSxdwList";
			asyncCallService(url, 'post', false, 'json', params, function(returnData) {
				processSSOOrPrivilege(returnData);
				if (returnData.code == undefined) {
		 			$("#DataGrid_sxdw").datagrid('loadData',returnData.data); 
		 			$('#w_sxdw').window('open');
					windowVisible("w_sxdw");
				}
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
		}

		function updateSxdwInfo(){
			var row = $('#DataGrid_sxdw').datagrid('getSelected');
			if (row) {
				var param = "cId=" + row.cid;
				var url = ctx + "/custClient/getSxdwInfo";
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if(returnData.code == undefined){
						var ret = returnData.data;
						$("#sxdw_cId").val(ret.cid);
						$("#sxdw_cusName").val(ret.cusName);
						$("#sxdw_linkman").val(ret.linkman);
						$("#sxdw_address").val(ret.address);
						$("#sxdw_oldPhone").val(ret.phone);
						$("#sxdw_oldAddress").val(ret.address);
						$("#sxdw_repairnumCount").val(ret.repairnumCount);
					}else{
						$.messager.alert("操作提示", returnData.msg , "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			} else {
				$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
			}
		}


		function getValidateCode(){
			var phone = $.trim($("#sxdw_phone").val());
			var oldPhone = $.trim($("#sxdw_oldPhone").val());
			 if(phone && phone.length == 11 && phone != oldPhone){
				 var params = {
						"phone" : $.trim($("#sxdw_phone").val()),
						"oldPhone" : $.trim($("#sxdw_oldPhone").val())
					};
				var url = ctx + "/custClient/getUpdateSxdwValidateCode";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					$.messager.alert("操作提示", returnData.msg, "info");
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		}
		
		function saveSxdw() {
			var isValid = $('#mform').form('validate');
			if (isValid) {
				 var phone = $.trim($("#sxdw_phone").val());
				 var oldPhone = $.trim($("#sxdw_oldPhone").val());
				 var validateCode = $.trim($("#validateCode").val());
				 var oldValidateCode = $.trim($("#oldValidateCode").val());
				 if(phone && phone.length == 11 && phone != oldPhone){
					 if(!validateCode || !oldValidateCode){//验证码
						 $.messager.alert("操作提示", "输入验证码", "info");
						 return false;
					}
				}
				var params = {
					"cId" : $.trim($("#sxdw_cId").val()),
					"cusName" : $.trim($("#sxdw_cusName").val()),
					"linkman" : $.trim($("#sxdw_linkman").val()),
					"phone" : $.trim($("#sxdw_phone").val()),
					"address" : $.trim($("#sxdw_address").val()),
					
					"validateCode" : validateCode,
					"oldValidateCode" : oldValidateCode,
					
					"oldAddress" : $.trim($("#sxdw_oldAddress").val()),
					"oldPhone" : $.trim($("#sxdw_oldPhone").val())
				};
				var url = ctx + "/custClient/updateSxdw";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", returnData.msg, "info", function() {
							if($.trim($("#sxdw_phone").val())){
								$("#phone").val($.trim($("#sxdw_phone").val()));
								$("#showPhone").text($.trim($("#sxdw_phone").val()));
							}else{
								$("#phone").val($.trim($("#sxdw_oldPhone").val()));
								$("#showPhone").text($.trim($("#sxdw_oldPhone").val()));
							}
							getSxdwInfoList();//客户列表
							treeInit();//重新加载树数据
							closeSxdw();
						});
					}else if (returnData.code == '-500') {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		}

		function updatePwd(){
			$(".validatebox-tip").remove();
			$(".validatebox-invalid").removeClass("validatebox-invalid");
			$("#u_phone").val($.trim($("#phone").val()));
			$("#u_loginPwd").val("");
			$("#u_newLoginPwd").val("");
			$("#u_reLoginPwd").val("");
			$('#w_updatePwd').window('open');
			windowVisible("w_updatePwd");
		}

		function closePwd(){
			$('#w_updatePwd').window('close');
		}

		function savePwd(){
			var isValid = $('#pwdform').form('validate');
			if (isValid) {
				 var loginPwd = $.trim($("#u_loginPwd").val());
				 var newLoginPwd = $.trim($("#u_newLoginPwd").val());
				 var reLoginPwd = $.trim($("#u_reLoginPwd").val());
				 if(loginPwd.length < 6){
					 $.messager.alert("操作提示", "原密码长度不小于6位", "info");
					 return false;
				}
				 if(newLoginPwd.length < 6){
					 $.messager.alert("操作提示", "新密码长度不能小于6位", "info");
					 return false;
				}
				 if(reLoginPwd.length < 6){
					 $.messager.alert("操作提示", "确认密码长度不能小于6位", "info");
					 return false;
				}
				 if(newLoginPwd != reLoginPwd){
					 $.messager.alert("操作提示", "两次新密码不一致", "info");
					 return false;
				}
				 var params = {
						 	"phone" : $.trim($("#u_phone").val()),
							"loginPwd" : loginPwd,
							"newLoginPwd" : newLoginPwd
						};
				var url = ctx + "/custClient/updatePwd";
				asyncCallService(url, 'post', false, 'json', params, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", "修改成功", "info",function(){
						closePwd();
						});
					}else{
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});	
				
			}
		}
	</script>
</body>
</html>