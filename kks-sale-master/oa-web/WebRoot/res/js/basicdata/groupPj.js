$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
$(function() {
	datagridInit();
	keySearch(queryListPage);
	keySearch(queryListPagePj, 1);
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
	var url = ctx + "/groupPj/groupPjList";
	var param = {
		"marketModel" : $.trim($("#searchByMarketModel").val()),
		"groupName" : $.trim($("#searchByGroupName").val()),
		"groupPrice" : $.trim($("#searchByGroupPrice").val()),
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
			groupPj_updateInfo(rowIndex, row);
		}
	});
	
	$("#details_DataGrid").datagrid({
		singleSelect : true,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			details_edit(rowIndex, row);
		}
	});
	
	$("#choosepj_DataGrid").datagrid({
		singleSelect : false,// 是否单选
		onDblClickRow : function(rowIndex, row) {
//			groupPj_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);
}

// 清除表单数据
function groupPj_addInfo() {
	$("#groupPjId").val(0);//组合配件id
	$("#groupPj_details_ids").val("");//详情配件料Ids
	$("#details_ids").val("");//详情ids
	
	$("#groupPrice").val(0);
	$("#groupName").val("");
	$("#marketModel").val("");
	$("#remark").val("");
	$("#importFalg").val(1);
	
	updateWindowOpen();
	var groupPjId = $("#groupPjId").val();
	var importFalg = $("#importFalg").val();
	if(!groupPjId || groupPjId == 0){
		deleteInfoIsNull(groupPjId);
	}
}

function groupPjSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams = {
			"groupPjId" : $("#groupPjId").val(),
			"groupName" : $.trim($("#groupName").val()),
			"groupPrice" : $.trim($("#groupPrice").val()),
			"marketModel" : $.trim($("#marketModel").val()),
			"remark" : $("#remark").val(),
			"importFalg" : 1,
			"tempPjlIds" : $("#groupPj_details_ids").val(),//配件料id
			"tempDetailsIds" : $("#details_ids").val()//详情id
		};
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/groupPj/addOrUpdateGroupPjInfo";
	var id = $("#groupPjId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			$("#importFalg").val(1);//保存或修改成功，导入标识改为1（不是导入）
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

function groupPj_updateInfo() {
	
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.groupPjId;
		var url = ctx + "/groupPj/getGroupPjInfo";
		var param = "groupPjId=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			
			var entity = returnData.data;
			$("#groupPjId").val(entity.groupPjId);
			$("#groupName").val(entity.groupName);
			$("#groupPrice").val(entity.groupPrice);
			$("#marketModel").val(entity.marketModel);
			$("#remark").val(entity.remark);
			$("#importFalg").val(entity.importFalg);
			$("#choosepj_marketModel").val(entity.marketModel);
			updateWindowOpen();
			$("#choosepj_marketModel").attr('disabled','disabled');
			
			//获取所有配件料详情
			queryListDetails();
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
		queryListPage(1);
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 删除
 */
function groupPj_deleteInfo() {
	
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				
				var getGroupPjId = row.groupPjId;
				if (getGroupPjId != -1) {
					var param = "groupPjId=" + getGroupPjId;
					var url = ctx + "/groupPj/deleteGroupPjInfo";
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
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function updateWindowClose() {
	var groupPjId = $("#groupPjId").val();
	var importFalg = $("#importFalg").val();
	if(!groupPjId || groupPjId == 0 || (groupPjId && importFalg==0)){
		deleteInfoIsNull(groupPjId);
		$("#importFalg").val(1);
	}
	
	$("#groupPjId").val(0);//组合配件id
	$("#groupPj_details_ids").val("");//详情配件料Ids
	$("#details_ids").val("");//详情ids
	$("#groupPrice").val(0);
	
	$("#choosepj_marketModel").val("");
	$('#updateWindow').window('close');
	queryListPage(currentPageNum);
}

function deleteInfoIsNull(groupId){
	var url = ctx + "/groupPjDetails/deleteInfoIsNull";
	var params = {"groupId" : groupId};
	asyncCallService(url, 'post', false, 'json', params, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
//			queryListDetails();
		}
	});	
}

function updateWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#updateWindow').window('open');
	$("#choosepj_marketModel").removeAttr('disabled');
	windowVisible("updateWindow");
}

function doChoosePJL(){
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#details_w').window('open');
	windowVisible("details_w");
	queryListDetails();
}

function queryListDetails(){
	var url = ctx + "/groupPjDetails/groupPjDetailsList";
	var groupId = $.trim($("#groupPjId").val());
	var tempPjlIds = $.trim($("#groupPj_details_ids").val());
	var param = {};
	if(groupId && groupId != 0){
		param['groupId'] = groupId;
	}
	if(tempPjlIds && tempPjlIds != 0){
		param['tempIds'] = tempPjlIds;
	}
	$("#details_DataGrid").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		
		if (returnData.code == undefined) {
			$("#details_DataGrid").datagrid('loadData', returnData.data);
			$("#detailsCount").html($("#details_DataGrid").datagrid('getData').rows.length);
			
			if(!param.groupId && !param.tempIds){//对象只有另个属性，全部为空则对象为空
//			if($.isEmptyObject(param)){//判断对象为空
				$("#details_DataGrid").datagrid('loadData', { total: 0, rows: [] });
				$("#detailsCount").html(0);
			}
			var rows = $("#details_DataGrid").datagrid("getRows");
			var groupPrice = $("#groupPrice").val()*1;
			var pjlIds =$("#groupPj_details_ids").val();
			var details_ids = "";//详情id
			if(rows.length > 0){
				for (var i = 0; i < rows.length; i++) {
					(i == 0) ? groupPrice = (rows[i].retailPrice)*(rows[i].proNumber) : groupPrice += (rows[i].retailPrice)*(rows[i].proNumber);
					(i == 0) ? details_ids = rows[i].id : details_ids = rows[i].id + "," + details_ids;
					(i == 0) ? pjlIds = rows[i].pjlId : pjlIds = rows[i].pjlId + "," + pjlIds;
				}
				$("#groupPj_details_ids").val(pjlIds);
				$("#details_ids").val(details_ids)
			}else{
				groupPrice = 0;
				$("#marketModel").val("");
			}
			$("#groupPrice").val(groupPrice);//计算总价格
			
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#details_DataGrid").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function details_add(){
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#choosepj_w').window('open');
	windowVisible("choosepj_w");
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
		var pageSize = getCurrentPageSize('choosepj_DataGrid');
	    var param={
	    		"groupsearchKey" : $.trim($("#choosepj_marketModel").val()),
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
				var pjlIds =$("#groupPj_details_ids").val();
				var groupPrice = $("#groupPrice").val()*1;
				for (var i = 0; i < rows.length; i++) {
					(i == 0) ? ids = rows[i].pjlId : ids = rows[i].pjlId + "," + ids;
					(i == 0) ? groupPrice = (rows[i].retailPrice)*1 : groupPrice += (rows[i].retailPrice)*1;
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
				
				$("#groupPrice").val(groupPrice);//计算总价格
				$("#marketModel").val(rows[0].marketModel);
				
				//先判断是否有重复数据，再插入
				var url = ctx + "/groupPjDetails/checkRepeat";
				var param = {
						"ids" : ids,
						"groupId" : $("#groupPjId").val()
				}
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					//data(重复数据的数量)
					if (returnData.code == undefined && returnData.data ==0) {
							var url1=ctx+"/groupPjDetails/addInfo";
							var param1 = {
									"ids" : ids,
									"proNumber" : 1
							}
							if($("#groupPjId").val() && $("#groupPjId").val() !=0){
								param1['groupId'] = $("#groupPjId").val();
							}
							asyncCallService(url1, 'post', false,'json', param1, function(ret){
								processSSOOrPrivilege(ret);
							}, function(){
								$.messager.alert("操作提示","网络错误","info");
							});
							$.messager.alert("操作提示", "选择成功！", "info",function(){
								$("#groupPj_details_ids").val(pjlIds);
								$('#choosepj_w').window('close');
								queryListDetails();
							});
					} else {
						$.messager.alert("操作提示", "配件料有已选择的，请确认", "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		});
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}

//查询选中要修改的配件料
function details_edit(){
	var rows = $("#details_DataGrid").datagrid('getSelections');
	if(rows.length ==1){
		var url = ctx + "/groupPjDetails/getGroupPjDetailsInfo";
		var param = "id=" + rows[0].id;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == 0) {
				var ret = returnData.data;
				$("#editPj_id").val(ret.id);
				$("#editPj_groupId").val(ret.groupId);
				$("#editPj_repairNumber").val(ret.repairNumber);
				$("#editPj_proNO").val(ret.proNO);
				$("#editPj_proName").val(ret.proName);
				$("#editPj_retailPrice").val(ret.retailPrice);
				$("#editPj_proNumber").val(ret.proNumber);
				openEditPj_w();
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
			$("#batchPj_DataGrid").datagrid('loaded');
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}else if(rows.length>1){
		
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}

//修改配件料（数量）
function openEditPj_w(){
	windowVisible("editPj_w");
	$(".singleSelect_show").show();
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#editPj_w').window('open');
}
//保存修改
function saveEditPj(){
	var isValid = $('#editPj_form').form('validate');
	if (isValid) {
		var url = ctx + "/groupPjDetails/updateInfo";
		var param = {
			"id" : $("#editPj_id").val(), 
			"groupId" : $("#editPj_groupId").val(), 
			"proNumber" : $.trim($("#editPj_proNumber").val()), 
			"retailPrice" : $.trim($("#editPj_retailPrice").val())
		}
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == 0) {
				$.messager.alert("操作提示", returnData.msg, "info",function(){
					editPj_Close();
					queryListDetails();
				});
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}
}

//删除（可多选）
function details_del(){
	var rows = $("#details_DataGrid").datagrid('getSelections');
	if(rows.length > 0){
		$.messager.confirm("操作提示", "确定删除已选中的数据吗？", function(conf) {
			if (conf) {
				
				var ids = "";
				var pjlIds = "";
				for (var i = 0; i < rows.length; i++) {
					(i == 0) ? ids = rows[i].id : ids = rows[i].id + "," + ids;
					(i == 0) ? pjlIds = rows[i].pjlId : pjlIds = rows[i].pjlId + "," + pjlIds;
				}
				
				var url = ctx + "/groupPjDetails/deleteInfo";
				var param = "ids=" + ids;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == 0) { 
						$.messager.alert("操作提示",returnData.msg, "info",function(){
							
							var tempPjlIds = $.trim($("#details_ids").val()).toString().split(",");
							var tempIds = pjlIds.toString().split(",");
							for (var int = 0; int < tempIds.length; int++) {
								tempPjlIds.splice($.inArray(tempIds[int], tempPjlIds),1);
							}
							$("#details_ids").val(tempPjlIds);
							queryListDetails();
						});
					} 
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		});
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}

//保存，不关闭窗口
function updateInfo(){
	var reqAjaxPrams = {
			"groupPjId" : $("#groupPjId").val(),
			"groupName" : $.trim($("#groupName").val()),
			"groupPrice" : 0,
			"marketModel" : $.trim($("#marketModel").val()),
			"remark" : $("#remark").val(),
			"importFalg" : 1,
			"tempPjlIds" : $("#groupPj_details_ids").val(),//配件料id
			"tempDetailsIds" : $("#details_ids").val()//详情id
		};
	var url = ctx + "/groupPj/updateGroupPjInfo";
	var id = $("#groupPjId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function closeDetails_w(){
	updateInfo();
	//重新计算价格
	windowCommClose('details_w');
}
function editPj_Close(){
	windowCommClose('editPj_w');
}

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-XSZH-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("groupPj/ImportDatas");
}