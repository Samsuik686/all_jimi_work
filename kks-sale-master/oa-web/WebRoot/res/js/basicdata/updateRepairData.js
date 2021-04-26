$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
$(function() {
	datagridInit();
	initDataGrid();
	keySearch(queryData);
});

function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true
	});

}

function queryData(){
	if(!$.trim($("#repairnNmber").val()) || !$.trim($("#imei").val())){
		$.messager.alert("操作提示","请输入批次号和IMEI","info");
		return;
	}
	var url=ctx+"/workflowcon/getDataList";
    var param = {
    		"repairnNmber" : $.trim($("#repairnNmber").val()),
    		"imei" : $.trim($("#imei").val())
    };
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$.each(returnData.data,function(i, item){
				//放弃报价
				if(item.w_isPrice=='1'){
					item.w_isPrice="<label style='color:green;font-weight: bold;'>是</label>";					
				}else if(item.w_isPrice=='0'){
					item.w_isPrice="<label style='color:red;font-weight: bold;'>否</label>";
				}else{
					item.w_isPrice="";
				}
				
				//是否放弃维修
				if(item.w_giveUpRepairStatus == 0){
					item.w_giveUpRepairStatus="<label style='font-weight: bold;'>正常</label>";
				}else if(item.w_giveUpRepairStatus == 1){
					item.w_giveUpRepairStatus="<label style='color:red;font-weight: bold;'>放弃维修</label>";
				}else{
					item.w_giveUpRepairStatus="";
				}
				
				
				//是否付款
				if(item.w_isPay ==0){
					item.w_isPay="<label style='color:red;font-weight: bold;'>是</label>";
				}else if(item.w_isPay == 1){
					item.w_isPay="<label style='font-weight: bold;'>否</label>";
				}else{
					item.w_isPay="";
				}
				
				//所在工站
				if (item.state == 0){
					item.w_station = "受理";
				}else if (item.state == 1){
					item.w_station = "分拣";
				}else if (item.state == 2 || item.state == 5 || item.state == 10 || item.state == 11 || item.state == 12 || item.state == 16){
					item.w_station = "维修";
				}else if (item.state == 3 || item.state == 9 || item.state == 14){
					item.w_station = "报价";
				}else if (item.state == 13){
					item.w_station = "终检";
				}else if (item.state == -1){
					item.w_station = "已发货";
				}else if (item.state == 15){
					item.w_station = "测试";
				}else if (item.state == 4){
					if(item.w_priceState == -1){
						item.w_station = "维修";
					}else if(item.w_repairState == -1){
						item.w_station = "报价";
					}
				}else if (item.state == 6){
					if(item.w_FinispassFalg == -1){
						item.w_station = "维修";
					}else if(item.w_repairState == -1){
						item.w_station = "终检";
					}
				}else if (item.state == 7){
					if(item.w_FinispassFalg == -1){
						item.w_station = "装箱";
					}else {
						item.w_station = "终检";
					}
				}else if (item.state == 8){
					if(item.w_repairState == -1){
						item.w_station = "装箱";
					}else {
						item.w_station = "维修";
					}
				}
				
				// 状态
				if (item.state == "-1") {
					item.state = "<label style='color:green;font-weight: bold;'>已发货</label>";
				} else if (item.state == "0") {
					item.state = "<label style='color:red;font-weight: bold;'>已受理</label>";
				} else if (item.state == "1") {
					item.state = "<label style='color:red;font-weight: bold;'>已受理，待分拣</label>";
				} else if (item.state == "2") {
					item.state = "<label style='color:red;font-weight: bold;'>已分拣，待维修</label>";
				} else if (item.state == "3") {
					item.state = "<label style='color:red;font-weight: bold;'>待报价</label>";
				} else if (item.state == "4") {
					item.state = "<label style='color:red;font-weight: bold;'>已付款，待维修</label>";
				} else if (item.state == "5") {
					item.state = "<label style='color:red;font-weight: bold;'>已维修，待终检</label>";
				} else if (item.state == "6") {
					item.state = "<label style='color:red;font-weight: bold;'>已终检，待维修</label>";
				} else if (item.state == "7") {
					item.state = "<label style='color:red;font-weight: bold;'>已终检，待装箱</label>";
				} else if (item.state == "8") {
					item.state = "<label style='color:red;font-weight: bold;'>放弃报价，待装箱</label>";
				} else if (item.state == "9") {
					item.state = "<label style='color:red;font-weight: bold;'>已报价，待付款</label>";
				} else if (item.state == "10") {
					item.state = "<label style='color:red;font-weight: bold;'>不报价，待维修</label>";
				} else if (item.state == "11") {
					item.state = "<label style='color:red;font-weight: bold;'>放弃报价，待维修</label>";
				} else if (item.state == "12") {
					item.state = "<label style='color:red;font-weight: bold;'>已维修，待报价</label>";
				} else if (item.state == "13") {
					item.state = "<label style='color:red;font-weight: bold;'>待终检</label>";
				} else if (item.state == "14") {
					item.state = "<label style='color:red;font-weight: bold;'>放弃维修</label>";
				} else if (item.state == "15") {
					item.state = "<label style='color:red;font-weight: bold;'>测试中</label>";
				} else if (item.state == "16") {
					item.state = "<label style='color:red;font-weight: bold;'>已测试,待维修</label>";
				}
			});
			$("#DataGrid1").datagrid('loadData',returnData.data);  
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
		 	$("#DataGrid1").datagrid('loaded');	
	});
}

function initDataGrid(){
	$("#repairnNmber").val("");
	$("#imei").val("");
	$("#DataGrid1").datagrid('loadData', { total: 0, rows: [] });//清空表格数据
}

function InfoUpdate(){
	var entity = $('#DataGrid1').datagrid('getSelections');
	if (entity.length > 0) {
		$.messager.confirm("操作提示", "确定要修改该条数据吗？确保此批次客户<font color='red'>未付款</font>的情况下修改", function(conf) {
			if (conf) {
				var ret_state = entity[0].state;
				var ret_isPay = entity[0].w_isPay;
				var ret_subStr = $.trim(ret_state.substring(ret_state.indexOf(">") + 1, ret_state.indexOf("</label>")));
				var isPay_subStr = "";
				if($.trim(ret_isPay)){
					isPay_subStr = $.trim(ret_isPay.substring(ret_isPay.indexOf(">") + 1, ret_isPay.indexOf("</label>")));
				}
				if(isPay_subStr && isPay_subStr == "是"){
					$.messager.alert("操作提示","此批次已付款，请确认后操作","info");
					return;
				}else{
					if(ret_subStr){
						if("2" == $("#state").combobox('getValue')){
							if ("不报价，待维修" == ret_subStr || "已维修，待终检" == ret_subStr || "已终检，待维修" == ret_subStr || "放弃报价，待维修" == ret_subStr || "放弃报价，待装箱" == ret_subStr){
							}else{
								$.messager.alert("操作提示","修改成<font color='red'>已分拣，待维修</font>的数据，必须是<font color='red'>不报价，待维修</font>或<font color='red'>已维修，待终检</font>或<font color='red'>放弃报价，待维修</font>或<font color='red'>放弃报价，待装箱</font>的数据","info");
								return;
							} 
						}else if("3" == $("#state").combobox('getValue')){
							if ("放弃报价，待维修" == ret_subStr || "放弃报价，待装箱" == ret_subStr){
							}else{
								$.messager.alert("操作提示","修改成<font color='red'>待报价</font>的数据，必须是<font color='red'>放弃报价，待维修</font>或<font color='red'>放弃报价，待装箱</font>的数据","info");
								return;
							}
						}
						
						var url=ctx+"/workflowcon/updateRepairData";
						var param = {
								"id" : entity[0].id,
								"repairnNmber" : $.trim($("#repairnNmber").val()),
								"imei" : $.trim($("#imei").val()),
								"state" : $("#state").combobox('getValue')
						};
						asyncCallService(url, 'post', false,'json', param, function(returnData){
							processSSOOrPrivilege(returnData);
							if(returnData.code=="0"){ 
								$.messager.alert("操作提示",returnData.msg,"info",function(){
									queryData();//查询修改后的数据
								});
							}else {
								$.messager.alert("操作提示",returnData.msg,"info");
							}
						}, function(){
							$.messager.alert("操作提示","网络错误","info");
						});
					}
				}
			}
		});
	} else{
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	} 
}
