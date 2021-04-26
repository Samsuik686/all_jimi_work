$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
$(function() {
	datagridInit();	
	keySearch(queryListPage);
	
	//改变物料类型
	$("#materialTypei").combobox({
		onChange: function (newValue, oldValue ) {
			if(newValue == '1'){
				$(".pjlShow").attr('disabled',true);
			}else if(newValue == '0'){
				$(".pjlShow").attr('disabled',false);
			}	
		}
	});
});
/**
 * 初始化数据及查询函数
 * 
 * @param pageNumber
 *            当前页数
 */
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var pageSize = getCurrentPageSize('DataGrid1');
	var param =
		{
			"materialType" : $("#s_materialType").combobox('getValue'),
			"marketModel" : $.trim($("#s_marketModel").val()), 
			"totalType" : $("#s_totalType").combobox('getValue'), 
			"type" : $.trim($("#s_type").val()), 
			"outTimeStarts" : $("#outTimeStart").val(), 
			"outTimeEnds" : $("#outTimeEnd").val(), 
			"proName" : $.trim($("#s_proName").val()),
			"proNO" : $.trim($("#s_proNO").val()),
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
		};
	var url = ctx + "/materialLog/getMaterialLogListPage";
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', true, 'json', param, function(returnData) {
		var ret = returnData.data;
		if (ret.length > 0) {
			$.each(ret, function(i, j) {
				if (j.totalType == '0') {
					j.totalType = '入库';
				}
				if (j.totalType == '1') {
					j.totalType = '出库';
				}
				if (j.materialType == '0') {
					j.materialType = '配件料';
				}
				if (j.materialType == '1') {
					j.materialType = '电子料';
				}
			});
		}
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		 $("#DataGrid1").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false
	});
	queryListPage(1);
	
	$("#log_MaterialDataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			log_dbClickChooseMaterial(index, value);
		}
	});
	
}
// TODO 清除表单数据
function clearFromParams() {
	$("#idi").val(0);
	$("#orderNOi").val("");
	$("#materialTypei").combobox('setValue',0);
	var date = DateToString(new Date());
	$("#outTimei").val(date);
	$("#depotNamei").val("");
	$("#typei").val("");
	$("#numberi").val("");
	$("#receivingPartyi").val("");
	$("#clerki").val("");
	$("#marketModeli").val("");
	$("#platformi").val("");
	$("#proNamei").val("");
	$("#proNOi").val("");
	$("#proSpeci").val("");
	$("#placesNOi").val("");
	$("#manufacturerCode").val("");
	$("#manufacturer").val("");
	$("#loss").val("");
	$("#consumption").val("");
	$("#remarki").val("");
	$("#retailPrice").val("");
	$("#hourlyRates").val("");
	$("#tradePrice").val("");
	$("#costPrice").val("");
	$("#masterOrSlave").combobox("setValue", "M");

}

// 是否同步刷新
function processSaveMaterialLog(reqAjaxPrams) {
	var isValid = $('#mform').form('validate');
	var id = $("#idi").val();
	if (isValid) {
		var url = ctx + "/materialLog/addOrUpdateMaterialLog";
		asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(
				returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == '0') {
				windowCloseMaterialLog();
				if(id > 0){
					queryListPage(currentPageNum);
				}else{
					queryListPage(1);
				}
			}
			$.messager.alert("操作提示", returnData.msg, "info");
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}
}

// 新增出库入库管理信息
function MaterialLogSave() {
	var outTimei = $("#outTimei").val();
	if(!outTimei){
		$.messager.alert("操作提示", "请填写 出入库日期", "info");
		return;
	}
	var reqAjaxPrams = 
		{	
			"id" : $("#idi").val(),
			"orderNO" : $.trim($("#orderNOi").val()),
			"outTimes" : $("#outTimei").val(),
			"materialType" : $("#materialTypei").combobox('getValue'),
			"depotName" : $.trim($("#depotNamei").val()),
			"totalType" : $("#totalTypei").combobox('getValue'),
			"type" : $("#typei").val(),
			"number" : $("#numberi").val(),
			"receivingParty" : $("#receivingPartyi").val(),
			"clerk" : $.trim($("#clerki").val()),
			"platform" : $.trim($("#platformi").val()),
			"marketModel" : $.trim($("#marketModeli").val()),
			"proName" : $.trim($("#proNamei").val()),
			"proNO" : $.trim($("#proNOi").val()),
			"proSpeci" : $.trim($("#proSpeci").val()),
			"placesNO" : $.trim($("#placesNOi").val()),
			"masterOrSlave" : $("#masterOrSlave").combobox('getValue'),
			"manufacturerCode" : $.trim($("#manufacturerCode").val()),
			"manufacturer" : $.trim($("#manufacturer").val()),
			"loss" : $.trim($("#loss").val()),
			"retailPrice" : $("#retailPrice").val(),
			"hourlyRates" : $("#hourlyRates").val(),
			"tradePrice" : $("#tradePrice").val(),
			"costPrice" : $("#costPrice").val(),
			"consumption" : $("#consumption").val(),
			"remark" : $.trim($("#remarki").val())
		};
	if(reqAjaxPrams['id'] != 0){
		if(reqAjaxPrams['totalType'] == temp_totalType){
			if(reqAjaxPrams['number'] != temp_number){
				if(reqAjaxPrams['totalType'] == 0){//入库
					reqAjaxPrams['errorNum'] = reqAjaxPrams['number']*1 - temp_number*1; 
				}else{//出库
					reqAjaxPrams['errorNum'] = (reqAjaxPrams['number']*1 - temp_number*1)*(-1); 
				}
			}else{
				if(reqAjaxPrams['totalType'] == 0){//入库
					reqAjaxPrams['errorNum'] = reqAjaxPrams['number']*1; 
				}else{
					reqAjaxPrams['errorNum'] = reqAjaxPrams['number']*(-1); 
				}
			}
		}else{
			if(reqAjaxPrams['totalType'] == 0){//入库
				reqAjaxPrams['errorNum'] = reqAjaxPrams['number']*1 + temp_number*1; 
			}else{
				reqAjaxPrams['errorNum'] = (reqAjaxPrams['number']*1 + temp_number*1)*(-1); 
			}
		}
	}
	processSaveMaterialLog(reqAjaxPrams);
}

// 删除出库入库信息
function materialLogDelete() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示","是否删除此信息",
			function(conf) {
				if (conf) {
					var getCurrentId = new Array();
					var getCurrentOrderNO =new Array();
					var row = $('#DataGrid1').datagrid('getSelections');
					for (var z = 0; z < row.length; z++) {
						getCurrentId[z] = row[z].id;
						getCurrentOrderNO[z] = row[z].orderNO;
					}
					var url = ctx + "/materialLog/deleteMaterialLog";
					var param = "ids=" + getCurrentId+ "&orderNOs=" + getCurrentOrderNO;
					asyncCallService(url,'post',false,'json',param,function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);
						}
						$.messager.alert("操作提示",returnData.msg,"info");
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
					$('#DataGrid1').datagrid('acceptChanges');
				}
			});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

// 弹出新增框
function materialLogAdd() {
	clearFromParams();
	$(".pjlShow").attr('disabled',false);
	$('#proNOi').attr('disabled',false);
	$('#materialTypei').combobox('enable');
	windowOpenMaterialLog();
	chageWinSize('w');
}

var temp_totalType;//出库|入库
var temp_number;//保存的出入库数量

// 弹出修改框
function materialLogUpdate() {
	var updated = $('#DataGrid1').datagrid('getSelections');
	if (updated.length == 1) {
		var materialType = updated[0].materialType;
		if(materialType == '电子料'){
			$(".pjlShow").attr('disabled',true);
		}else if(materialType == '配件料'){
			$(".pjlShow").attr('disabled',false);
		}	
		$('#proNOi').attr('disabled',true);
		$('#materialTypei').combobox('disable');
		var getCurrentId = updated[0].id;
		var url = ctx + "/materialLog/selectMaterialLog";
		var param = "id=" + getCurrentId;
		$(".validatebox-tip").remove();
		$(".validatebox-invalid").removeClass("validatebox-invalid");
		asyncCallService( url, 'post', false, 'json', param, function(returnData) {
					var entity = returnData.data.data;
					// TODO 数据展示
					$("#idi").val(entity.id);
					$("#orderNOi").val(entity.orderNO);
					$("#materialTypei").combobox('setValue', entity.materialType);
					$("#outTimei").val(entity.outTime);
					$("#depotNamei").val(entity.depotName);
					$("#totalTypei").combobox('setValue', entity.totalType);
					$("#typei").val(entity.type);
					$("#numberi").val(entity.number);
					
					temp_totalType = $("#totalTypei").combobox('getValue');//供改变出库|入库
					temp_number = $("#numberi").val();//供改变出入库数量
					
					$("#receivingPartyi").val(entity.receivingParty);
					$("#clerki").val(entity.clerk);
					$("#platformi").val(entity.platform);
					$("#marketModeli").val(entity.marketModel);
					$("#proNamei").val(entity.proName);
					$("#proNOi").val(entity.proNO);
					$("#proSpeci").val(entity.proSpeci);
					$("#placesNOi").val(entity.placesNO);
					$("#masterOrSlave").combobox('setValue', entity.masterOrSlave);
					$("#manufacturerCode").val(entity.manufacturerCode);
					$("#remarki").val(entity.remark);
					$("#manufacturer").val(entity.manufacturer);
					$("#retailPrice").val(entity.retailPrice);
					$("#hourlyRates").val(entity.hourlyRates);
					$("#tradePrice").val(entity.tradePrice);
					$("#costPrice").val(entity.costPrice);
					$("#loss").val(entity.loss);
					$("#consumption").val(entity.consumption);
					$("#updateHide").hide();
					windowOpenMaterialLog();
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
	} else if(updated.length == 0) {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}else{
		$.messager.alert("提示信息", "请先选择一行进行修改！", "info");
	}
}

/**
 * 筛选数据组装
 */
function getSelParams() {
	var param =
		{
			"materialType" : $("#s_materialType").combobox('getValue'),
			"marketModel" : $.trim($("#s_marketModel").val()), 
			"totalType" : $("#s_totalType").combobox('getValue'), 
			"type" : $.trim($("#s_type").val()), 
			"outTimeStarts" : $("#outTimeStart").val(), 
			"outTimeEnds" : $("#outTimeEnd").val(), 
			"proName" : $.trim($("#s_proName").val()),
			"proNO" : $.trim($("#s_proNO").val())
		}
	return param;
}

/**
 * 导出数据
 */
function ExportOrders() {
	var url =  ctx + "/materialLog/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='materialType' value='" + $("#s_materialType").combobox('getValue') +"'/>"));
	$form1.append($("<input type='hidden' name='marketModel' value='" + $.trim($("#s_marketModel").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='totalType' value='" + $("#s_totalType").combobox('getValue') +"'/>")); 
	$form1.append($("<input type='hidden' name='type' value='" + $.trim($("#s_type").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='outTimeStarts' value='" + $("#outTimeStart").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='outTimeEnds' value='" + $("#outTimeEnd").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='proName' value='" + $.trim($("#s_proName").val())+"'/>"));
	$form1.append($("<input type='hidden' name='proNO' value='" + $.trim($("#s_proNO").val())+"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

function windowOpenMaterialLog() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#w').window('open');
	windowVisible("w");
}

function windowCloseMaterialLog() {
	$("#updateHide").show();
	$('#w').window('close');
}


