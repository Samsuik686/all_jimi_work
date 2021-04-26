<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>

</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="客户回访汇总报表" fit="true">
			<div id="toolbar" style="padding: 10px 15px;">
			  <div style="margin-bottom:5px">
					<span>开始日期:&nbsp;<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"/>&nbsp;</span>
					<span>结束日期:&nbsp;<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"/>&nbsp;</span>
					<span>
						技能评价:
						<select id="skillEvaluate" class="easyui-combobox" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0" >满意</option>
							<option value="1">比较满意</option>
							<option value="2" >一般</option>
							<option value="3">不满意</option>
							<option value="4" >非常不满意</option>
						</select>
					</span>&nbsp;
					<span>
						服务评价:
						<select id="serviceEvaluate" class="easyui-combobox" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0" >满意</option>
							<option value="1">比较满意</option>
							<option value="2" >一般</option>
							<option value="3">不满意</option>
							<option value="4" >非常不满意</option>
						</select>
					</span>&nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				 </div>
				 <div>
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="ExportOrders();">导出</a> 
				 </div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid"  fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" pageSize="20" fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<!-- <th checkbox="true" field="w_repairId"></th> -->
						<th field="repairnNmber" width="90" align="center">送修批号</th>
						<th field="cusName" width="150" align="center">送修单位</th>
						<th field="acceptanceTime" width="110" align="center">受理时间</th>
						<th field="sendTime" width="110" align="center">取机时间</th>
						<th field="evaluateTime" width="110" align="center">评价时间</th>
						<th field="skillEvaluate" width="50" align="center">技能评价</th>
						<th field="serviceEvaluate" width="50" align="center">服务评价</th>
						<th field="fremark" width="300" align="center">评价说明</th>
					</tr>
				</thead>
			</table>
		</div>
		<script type="text/javascript">
			var currentPageNum;
			$(function() {
				materialLogInit();
				queryListPage(1);
				keySearch(queryListPage);
			});

			/**
			 * 初始化 表单工具栏面板
			 */
			function materialLogInit() {

				$("#DataGrid1").datagrid({
					singleSelect : true
				});

			}

			function ExportOrders() {
				var url =  ctx + "/reportCon/crExportDatas";
				var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
				$form1.append($("<input type='hidden' name='startTime' value='" + $("#startTime").val() +"'/>"));
				$form1.append($("<input type='hidden' name='endTime' value='" + $("#endTime").val() +"'/>"));
				$form1.append($("<input type='hidden' name='skillEvaluate' value='" + $("#skillEvaluate").combobox('getValue') +"'/>"));
				$form1.append($("<input type='hidden' name='serviceEvaluate' value='" + $("#serviceEvaluate").combobox('getValue') +"'/>")); 
				$("body").append($form1);
				$form1.submit();
				$form1.remove()
			}
			/**
			 * 初始化列表
			 * 初始化数据及查询函数
			 * @param pageNumber  当前页数
			 */
			function queryListPage(pageNumber) {
				currentPageNum = pageNumber;
				if (currentPageNum == "" || currentPageNum == null) {
					currentPageNum = 1;
				}
				var url = ctx + "/reportCon/getCallbackListPage";
				
				var pageSize = getCurrentPageSize('DataGrid1');
				var param = {
					"startTime" : $("#startTime").val(),
					"endTime" : $("#endTime").val(),
					"skillEvaluate" : $("#skillEvaluate").combobox('getValue'),
					"serviceEvaluate" : $("#serviceEvaluate").combobox('getValue'),
					"currentpage" : currentPageNum,
					"pageSize" : pageSize
				}
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == undefined) {
						$.each(returnData.data, function(i, item) {
							//技能评价
							if ($.trim(item.skillEvaluate) == "一般") {
								item.skillEvaluate = "<label style='color:red;font-weight: bold;'>一般</label>";
							} else if ($.trim(item.skillEvaluate) == "不满意") {
								item.skillEvaluate = "<label style='color:red;font-weight: bold;'>不满意</label>";
							} else if ($.trim(item.skillEvaluate) == "非常不满意") {
								item.skillEvaluate = "<label style='color:red;font-weight: bold;'>非常不满意</label>";
							}

							//服务评价
							if ($.trim(item.serviceEvaluate) == "一般") {
								item.serviceEvaluate = "<label style='color:red;font-weight: bold;'>一般</label>";
							} else if ($.trim(item.serviceEvaluate) == "不满意") {
								item.serviceEvaluate = "<label style='color:red;font-weight: bold;'>不满意</label>";
							} else if ($.trim(item.serviceEvaluate) == "非常不满意") {
								item.serviceEvaluate = "<label style='color:red;font-weight: bold;'>非常不满意</label>";
							}
						});
						$("#DataGrid1").datagrid('loadData', returnData.data);
						getPageSize();
						resetCurrentPageShow(currentPageNum);
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}

				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		</script>

	</div>
</body>
</html>