/* ==========================================保内对换主板申请 start======================================== */
//时分秒十以内前面加0
function addZero(v) {
	if (v < 10) {
		return '0' + v;
	} else {
		return v;
	}
}

// 获得当前时间
function initTime() {
	var date = new Date();
	return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

//清除保内对换主板表单数据
function clearChangeboard() {
	// 清空下缓存
	$("#changeboard_id").val(0);
	$("#changeboard_wfId").val("");
	$("#changeboard_cusName").val("");
	$("#changeboard_imei").val("");
	$("#changeboard_model").val("");
	$("#changeboard_number").val(1);
	$("#changeboard_appTime").val(initTime());
	$("#changeboard_purpose").val("");
	$("#changeboard_remark").val("");
	$("#changeboard_state").val(0);
	$("#changeboard_isWarranty").val("");
	$("#changeboard_repairOrTest").val("");
}

//弹出保内对换主板
function changeboardOpen() {
	windowVisible("changeboard_w");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#changeboard_w').window('open');
	chageWinSize('changeboard_w');
}

function changeboard(type){
	clearChangeboard();
	var rows = $('#DataGrid1').datagrid('getSelections');
	var isWarry = rows[0].isWarranty;
	if (rows.length == 1){
		if(type == 'workRepair'){
			isWarry = $.trim(rows[0].isWarranty.substring(rows[0].isWarranty.indexOf(">") + 1, rows[0].isWarranty.indexOf("</label>")));
		}
		var flag = false;
		if(rows[0].testStatus){
			flag = (rows[0].testStatus.indexOf("已发送") > -1);
		}else if(rows[0].w_repairState){
			flag = (rows[0].w_repairState.indexOf("已完成") > -1);
		}
		if(flag){
			$.messager.alert("提示信息","已完成数据不能执行此操作！","info");
		}else{
			var param ="wfId=" + rows[0].id;
			var url = ctx + "/changeboard/getCountByWfId";
			asyncCallService(url, 'post', false,'json', param, function(returnData){
				processSSOOrPrivilege(returnData);
				if (returnData.code == undefined) {
					if(returnData.data <= 0){
						var model = rows[0].w_model.split("|");
						$("#changeboard_cusName").val(rows[0].w_cusName);
						$("#changeboard_wfId").val(rows[0].id);
						$("#changeboard_imei").val(rows[0].imei);
						$("#changeboard_model").val(model ? model[0] : "");
						$("#changeboard_isWarranty").val(isWarry);
						changeboardOpen();
					}else{
						$.messager.alert("操作提示", "该IMEI的设备已申请且未完成，请确认后操作", "info");
					}
				} else {
					$.messager.alert("操作提示", returnData.msg, "info");
				}
			}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function() {
				});
			});
			
		}
	}else if (rows.length > 1){
		$.messager.alert("提示信息","请只选择一行进行操作！","info");
	}else{
		$.messager.alert("提示信息","请先选择需要操作的行！","info");
	}
}

function changeboardAdd(type) {
	var url = ctx + "/changeboard/insertChangeboard";
	// 未选择时间时，默认当前时间
	var appTime = $("#changeboard_appTime").val();
	if (!appTime) {
		$("#changeboard_appTime").val(initTime());
	}
	appTime = $("#changeboard_appTime").val();

	if(type == "repair"){
		$("#changeboard_repairOrTest").val("维修");
	}else if(type == "test"){
		$("#changeboard_repairOrTest").val("测试");
	}
	var isValid = $('#changeboard_form').form('validate');
	if(isValid){
		var param=
			{
				"wfId" : $("#changeboard_wfId").val(),
				"cusName" : $("#changeboard_cusName").val(),
				"imei"  :  $("#changeboard_imei").val(),
				"model" : $("#changeboard_model").val(),
				"isWarranty" : $.trim($("#changeboard_isWarranty").val()) == "保内" ? 0 : 1 ,
				"number" : 1,
				"applicater" : $("#changeboard_applicater").val(),
				"appDate" : appTime,
				"repairOrTest" : $("#changeboard_repairOrTest").val(),
				"purpose" : $("#changeboard_purpose").val(),
				"state" : "0",
				"sendFlag" : 1,
				"remark" : $("#changeboard_remark").val()
			};
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if (returnData.code == '0') {
				$.messager.alert("操作提示", returnData.msg, "info", function() {
					windowCommClose('changeboard_w');
				});
			} else {
				$.messager.alert("操作提示", returnData.msg, "info", function() {
				});
			}
		}, function() {
				$.messager.alert("操作提示", "网络错误", "info", function() {
			});
		});
	}
}

function changeboard_close(){
	windowCommClose('changeboard_w');
}
/* ==========================================保内对换主板申请 end======================================== */
