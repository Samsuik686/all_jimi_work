$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
$(function() {
	datagridInit();
	refreshInfo();
});

function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false
	});
}

function queryData() {
	var url = ctx + "/reportCon/saleList";
	var param = "s_imei=" + $.trim($("#scanimei").val());
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {

			$.each(returnData.data, function(i, item) {
				// 状态
				if (item.status == 1) {
					item.status = "正常";
				} else if (item.status == 3) {
					item.status = "禁止接入系统";
				} else {
					item.status = "已删除";
				}
			});
			$("#DataGrid1").datagrid('loadData', returnData.data);
			var rows = $("#DataGrid1").datagrid('getRows');
			if (rows.length == 0) {
				$.messager.alert("操作提示", "未查询到数据", "info", function() {
					refreshInfo();// 清空所有数据
				});
			}
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

/**
 * 同步筛选扫描数据
 */
function scancData(event) {
	$("#scanimei").val($.trim($("#scanimei").val()));
	var Scanimei = $("#scanimei").val();
//	var evt = evt ? evt : (window.event ? window.event : null); // 兼容IE和FF
	var evt = event;
	var nimeiAginSubmit = false;
//	if (Scanimei.length > 15) {
//		$.messager.alert("操作提示", "IMEI格式不正确", "warning", function() {
//			refreshInfo();// 清空所有数据
//		});
//	} else {

		if((Scanimei.length >=6 && evt.keyCode==13) && !nimeiAginSubmit){
			nimeiAginSubmit = true;
			$("#scanimei").blur();
			queryData();
		} else {
			$("#scanimei").focus();
			nimeiAginSubmit = false;
		}
//	}
}

function refreshInfo() {
	$("#scanimei").val("");
	$("#scanimei").focus();// 扫描IMEI获得焦点
	$("#DataGrid1").datagrid('loadData', {
		total : 0,
		rows : []
	});// 清空表格数据
}
