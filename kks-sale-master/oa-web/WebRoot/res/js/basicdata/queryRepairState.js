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

function queryData(){
	var url = ctx + "/queryRepairState/getList";
	var param = "s_imei=" + $.trim($("#scanimei").val());
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data, function(i, item) {
				//状态
				if(item.state=="-1"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已发货</label>";
				}else if(item.state=="0"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已受理</label>";
				}else if(item.state=="1"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已受理，待分拣</label>";
				}else if(item.state=="2"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已分拣，待维修</label>";
				}else if(item.state=="3"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>待报价</label>";
				}else if(item.state=="4"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已付款，待维修</label>";
				}else if(item.state=="5"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已维修，待终检</label>";
				}else if(item.state=="6"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已终检，待维修</label>";
				}else if(item.state=="7"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已终检，待装箱</label>";
				}else if(item.state=="8"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>放弃报价，待装箱</label>";
				}else if(item.state=="9"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>已报价，待付款</label>";
				}else if(item.state=="10"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>不报价，待维修</label>";
				}else if(item.state=="11"){
					item.state="<label style='color:#0088cc;font-weight: bold;'>放弃报价，待维修</label>";
				}else if (item.state == "12") {
					item.state = "<label style='color:#0088cc;font-weight: bold;'>已维修，待报价</label>";
				}else if (item.state == "13") {
					item.state = "<label style='color:#0088cc;font-weight: bold;'>待终检</label>";
				}else if (item.state == "14") {
					item.state = "<label style='color:#0088cc;font-weight: bold;'>放弃维修</label>";
				}else if (item.state == "15") {
					item.state = "<label style='color:#0088cc;font-weight: bold;'>测试中</label>";
				}else if (item.state == "16") {
					item.state = "<label style='color:red;font-weight: bold;'>已测试，待维修</label>";
				}
			});
			$("#DataGrid1").datagrid('loadData', returnData.data);
			var rows=$("#DataGrid1").datagrid('getRows');
			if(rows.length==0){
				$.messager.alert("操作提示", "未查询到数据", "info" ,function(){
					refreshInfo();//清空所有数据
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
	var Scanimei =$.trim($("#scanimei").val());
	var evt = event;
	var nimeiAginSubmit = false;
	if((Scanimei.length >=6 && evt.keyCode==13) && !nimeiAginSubmit){
		nimeiAginSubmit = true;
		$("#scanimei").blur();
		queryData();
	}else{
		$("#scanimei").focus();
		nimeiAginSubmit = false;
	}
}

function refreshInfo(){
	$("#scanimei").val("");
	$("#scanimei").focus();// 扫描IMEI获得焦点
	$("#DataGrid1").datagrid('loadData', { total: 0, rows: [] });//清空表格数据
}
