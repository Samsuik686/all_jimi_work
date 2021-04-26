$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
$(function() {
	datagridInit();
	keySearch(queryListPage);
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
	var url = ctx + "/priceGroupPj/priceGroupPjList";
	var param = {
		"model" : $.trim($("#searchByModel").val()),
		"groupName" : $.trim($("#searchByGroupName").val()),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	};
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
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
		singleSelect : true,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			priceGroupPj_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);
	
	//选中配件料详情
	$("#details_DataGrid").datagrid({
		singleSelect : false// 是否单选
	});
	showPjlInfos("");
	
	
	$("#choosepj_DataGrid").datagrid({
		singleSelect : false,// 是否单选
		onDblClickRow : function(rowIndex, row) {
		}
	});
}

//清除表单数据
function priceGroupPj_addInfo() {
	$("#groupPjId").val(0); //组合配件id
	$("#pjlDesc").val(""); //配件料ids
	$("#pjlPrice").val(0);
	$("#groupName").val("");
	$("#model").val("");
	$("#remark").val("");
	
	showPjlInfos("");
	updateWindowOpen();
}

//打开修改窗口
function updateWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#updateWindow').window('open');
	$("#choosepj_model").removeAttr('disabled');
	windowVisible("updateWindow");
}

//修改
function priceGroupPj_updateInfo() {
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.groupPjId;
		var url = ctx + "/priceGroupPj/getPriceGroupPjInfo";
		var param = "groupPjId=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			if(returnData.code == 0){
				var entity = returnData.data;
				$("#groupPjId").val(entity.groupPjId);
				$("#groupName").val(entity.groupName);
				$("#pjlPrice").val(entity.pjlPrice);
				$("#model").val(entity.model);
				$("#choosepj_model").val(entity.model);//增加配件料主板型号查询
				$("#remark").val(entity.remark);
				$("#pjlDesc").val(entity.pjlDesc);
				if(entity.pjlDesc){
					showPjlInfos(entity.pjlDesc);
				}
				updateWindowOpen();
				$("#choosepj_model").attr('disabled','disabled');
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

//显示配件料详情
function showPjlInfos(ids){
	var url = ctx + "/pjlmanage/getPjlByPjlIds";
	var param ={
			"ids" : ids
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if(returnData.code == undefined){
			$("#details_DataGrid").datagrid('loadData', returnData.data);
		}
		// TODO
		var price = 0;
		var arr = returnData.data;
		for (var i=0; i<arr.length; i++) {
			price += arr[i].retailPrice;
		}
		$("#pjlPrice").val(price);
	});
}

//保存修改
function priceGroupPjSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams = {
			"groupPjId" : $("#groupPjId").val(),
			"model" : $.trim($("#model").val()),
			"groupName" : $.trim($("#groupName").val()),
			"pjlPrice" : $.trim($("#pjlPrice").val()),
			"pjlDesc" : $("#pjlDesc").val(), //配件料id
			"remark" : $("#remark").val()
		};
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/priceGroupPj/addOrUpdatePriceGroupPjInfo";
	var id = $("#groupPjId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载pjlmanageList
			} else {
				queryListPage(1);// 重新加载pjlmanageList
			}
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

//关闭修改窗
function updateWindowClose() {
	$("#groupPjId").val(0); //组合配件id
	$("#pjlDesc").val(""); //配件料ids
	$("#pjlPrice").val(0);
	$("#groupName").val("");
	$("#model").val("");
	$("#remark").val("");
	
	$("#choosepj_model").val("");
	$("#choosepj_marketModel").val("");
	$("#choosepj_model").removeAttr('disabled');
	$('#updateWindow').window('close');
}

//删除配件料
function delPjl(){
	var delRows = $('#details_DataGrid').datagrid('getSelections');
	if(delRows.length > 0){
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var ids = "";
				var pjlDesc = $.trim($("#pjlDesc").val());
				for (var i = 0; i < delRows.length; i++) {
					(i ==0)?ids = delRows[i].pjlId:ids = delRows[i].pjlId+","+ids;
				}
				if(ids!="" && ids!=null && ids!=undefined){
					if(pjlDesc){
						var pjlDescTemp = pjlDesc.toString().split(",");
						var idsTemp = ids.toString().split(",");
						for (var int = 0; int < idsTemp.length; int++) {
							pjlDescTemp.splice($.inArray(idsTemp[int], pjlDescTemp),1);
						}
						$("#pjlDesc").val(pjlDescTemp);
						showPjlInfos($.trim($("#pjlDesc").val()));
					}
				}
			}
		});
	}else{
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

//增加配件料按钮
function addPjl(){
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#choosepj_w').window('open');
	windowVisible("choosepj_w");
	
	if($.trim($("#model").val)){
		$("#choosepj_model").val($.trim($("#model").val()));
	}
	queryListPagePj(1);
}

//查询所有配件料
function queryListPagePj(pageNumber){
	var isValid = $('#searchPjlForm').form('validate');
	if (isValid) {
		currentPageNum_Pj=pageNumber;
		if(currentPageNum_Pj=="" || currentPageNum_Pj==null){
			currentPageNum_Pj=1;
		}
		var url=ctx+"/pjlmanage/pjlmanageList";
		var pageSize = getCurrentPageSizeByChoosepj('choosepj_DataGrid');
	    var param={
	    		"priceGroupsearchKey" : $.trim($("#choosepj_model").val()),
	    		"marketModel" : $.trim($("#choosepj_marketModel").val()),
	    		"proNO" : $.trim($("#choosepj_proNO").val()),
	    		"proName" : $.trim($("#choosepj_proName").val()),
	    		"currentpage" : currentPageNum_Pj,
	    		"pageSize" : pageSize
	    }
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				$("#choosepj_DataGrid").datagrid('loadData',returnData.data); 
				getPageSizePj();
				resetCurrentPagePj(currentPageNum_Pj);
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	}else{
		$("#choosepj_DataGrid").datagrid('loadData', {total: 0, rows: [] });
	}
}

function saveChoosepj(){
	var rows = $("#choosepj_DataGrid").datagrid('getSelections');
	if(rows.length > 0){
		$.messager.confirm("操作提示", "确定保存已选择的数据吗？", function(conf) {
			if (conf) {
				
				var ids = "";
				var pjlIds =$("#pjlDesc").val();
				var pjlPrice = $("#pjlPrice").val()*1;
				for (var i = 0; i < rows.length; i++) {
					(i == 0) ? ids = rows[i].pjlId : ids = rows[i].pjlId + "," + ids;
					(i == 0) ? pjlPrice = (rows[i].retailPrice)*1 : pjlPrice += (rows[i].retailPrice)*1;
					(!pjlIds || pjlIds ==0) ? pjlIds = rows[i].pjlId : pjlIds = rows[i].pjlId + "," + pjlIds;
				}
				
				if(pjlIds){
					var pjlIds1 = pjlIds.toString().split(",");
					var pjlIds2 = pjlIds1.sort();
					var falg = false;
					for (var int = 0; int < pjlIds2.length; int++) {
						if(pjlIds2[int] == pjlIds2[int+1]){
							falg = true;
						}
					}
					if(falg){
						$.messager.alert("操作提示", "配件料有已选择的，请确认", "info");
						return;
					}
				}
				$("#model").val(rows[0].model);
				$("#pjlDesc").val(pjlIds);
				
				showPjlInfos($.trim($("#pjlDesc").val()));
			}
		});
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}

/**
 * 删除
 */
function priceGroupPj_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var param = "groupPjId=" + row.groupPjId;
				var url = ctx + "/priceGroupPj/deletePriceGroupPjInfo";
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						queryListPage(currentPageNum);
					}
					$.messager.alert("操作提示", returnData.msg, "info");
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

//自动批量增加
function bathch_addInfo(){
	$("#batch_model").val("");
	$("#batch_key").val("");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#batchAdd_w').window('open');
	windowVisible("batchAdd_w");
	comboboxInit();
}


function closeBatch(){
	$("#batch_model").val("");
	$('#batchAdd_w').window('close');
}

//生成批量数据
function saveAll(){
	$.messager.confirm("操作提示", "是否要自动生成", function(conf) {
		if (conf) {
			var param = {
					"model" : $.trim($("#batch_model").val()),
					"keyType" : $("#type").combobox('getText'),
					"keyDesc" : $.trim($("#batch_key").val())
			}
			var url =  ctx + "/priceGroupPj/addAll";
			asyncCallService(url, 'post', false, 'json', param, function(returnData) {
				processSSOOrPrivilege(returnData);
				if (returnData.code == '0') {
					closeBatch();
					queryListPage(currentPageNum);
				}
				$.messager.alert("操作提示", returnData.msg, "info");
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
		}
	});
}