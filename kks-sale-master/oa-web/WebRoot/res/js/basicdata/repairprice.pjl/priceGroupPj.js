///////////////////// 维修报价选择报价配件料组合
var temp_PGPJ_selectIds; // 临时存储的Ids
var temp_PGPJ_selectName; // 临时存储的名称

var pjlIds_fin;//配件料最终保存的ids
var pjlDesc_fin;//配件料最终保存的名称


var currentPageNum_PGPJ;
/**
 * 获取当前分页参数
 * 
 * @param size
 *            数据源
 * @param DataGrid
 *            DataGrid ID
 */
function pageSetPGPJ(size) {
	$('.panel-tool').hide();
	var p = $("#pgpj_DataGrid").datagrid('getPager');
	$(p).pagination({
		total : size,
		pageList : true,
		pageSize : pageCount,
		showPageList : false,
		showRefresh : false,
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		},
		onSelectPage : function(pageNumber, pageSize, total) {
			$(this).pagination('loading');
			queryListPagePGPJ(pageNumber);
			$(this).pagination('loaded');
		},
		onChangePageSize : function(pageNumber, pageSize) {
			$(this).pagination('loading');
			$(this).pagination('loaded');

		}
	});
}

/**
 * 获取缓存分页参数
 * 
 * @param DataGrid
 *            DataGrid ID
 */
function getPageSizePGPJ() {
	$.ajax({
		type : "GET",
		async : false,
		cache : false,
		url : ctx + "/form/queryCurrentPageSize.api",
		dataType : "json",
		success : function(returnData) {
			if (returnData.code != null) {
				$.messager.alert("操作提示", "系统繁忙，请稍候在试!", "info");
				return 0;
			}
			pageSetPGPJ(returnData.data);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
			return 0;
		}
	});
}

/**
 * 当当前页处于>1时，点击某条件查询按钮后，分页栏还是未同步当前页码
 * 
 * @param currentPageNum
 *            当前页码
 * @param DataGrid
 *            DataGrid ID
 */
function resetCurrentPagePGPJ(currentPageNum) {
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum_PGPJ = 1;
	}
	$('#pgpj_DataGrid').datagrid('getPager').pagination({
		pageNumber : currentPageNum_PGPJ
	});
}

/*---------获取报价配件料组合start-----------*/
function queryListPagePGPJ(pageNumber) {
	currentPageNum_PGPJ = pageNumber;
	if (currentPageNum_PGPJ == "" || currentPageNum_PGPJ == null) {
		currentPageNum_PGPJ = 1;
	}
	var pageSize = getCurrentPageSize('pgpj_DataGrid');
	var url = ctx + "/priceGroupPj/priceGroupPjList";
	var param = {
			"model" : $.trim($("#model").val()),
			"groupName" : $.trim($("#searchByGroupName").val()),
			"searchType" : "repairPriceSearch",
			"currentpage" : currentPageNum_PGPJ,
			"pageSize" : pageSize
	};
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#pgpj_DataGrid").datagrid('loadData', returnData.data);
			getPageSizePGPJ();
			resetCurrentPagePGPJ(currentPageNum_PGPJ);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
/*-------获取报价配件料组合end--------------*/

////打开选择报价配件料组合窗口
function openPGPJ(){
	windowVisible("pgpj_w");
	$("#searchByGroupName").val("");
	$('#pgpj_w').window('open');
	queryListPagePGPJ();
}

function closePGPJ_w(){
	$('#pgpj_w').window('close');
}

//报价配件料组合添加按钮事件
function doinsertPGPJ() {
	windowVisible("pgpj_w");
	queryListPagePGPJ(1);
	$("#selected-type-box-PGPJ").empty();
	temp_PGPJ_selectIds = $("#priceGroupId").val();
	temp_PGPJ_selectName = $("#priceGroupDesc").val();
	
	pjlIds_fin = $("#priceGroupId").val();
	pjlDesc_fin = $("#priceGroupDesc").val();
	// 初始化所选择的报价配件料组合
	var names = temp_PGPJ_selectName.split("|");
	var ids = temp_PGPJ_selectIds.split(",");
	if (names != "" && names != null && names.length > 0) {
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++) {
			var name = names[int];
			var id = ids[int]
			htmlDatas = htmlDatas + "<span class='tag'>" + name + "<input type='hidden' name='win_PGPJId' value=" + id + "><input type='hidden' name='win_PGPJname' value=" + name + "><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-PGPJ").append(htmlDatas);
	}
	clearPGPJFunctionMethod();
	openPGPJ();

}

function dbClickChoosePGPJ(index, value) {
	var Falg = true;
	var ids = temp_PGPJ_selectIds.toString().split(",");
	if (ids != "" && ids != null && ids.length > 0) {
		for (var int = 0; int < ids.length; int++) {
			if (ids[int] == value.groupPjId) {
				Falg = false;
				break;
			}
		}
	}
	if (Falg) {
		$("#selected-type-box-PGPJ").append("<span class='tag'>" + value.groupName + "<input type='hidden' name='win_PGPJId' value=" + value.groupPjId + "><input type='hidden' name='win_PGPJname' value=" + value.groupName + "><button class='panel-tool-close'></button></span>");
		if (temp_PGPJ_selectIds != "") {
			temp_PGPJ_selectIds = temp_PGPJ_selectIds + "," + value.groupPjId;
		} else {
			temp_PGPJ_selectIds = value.groupPjId;
		}
		if (temp_PGPJ_selectName != "") {
			temp_PGPJ_selectName = temp_PGPJ_selectName + " | " + value.groupName;
		} else {
			temp_PGPJ_selectName = value.groupName;
		}
		clearPGPJFunctionMethod();
	} else {
		$.messager.alert("操作提示", "附件已选择！", "warning");
	}
}

/**
 * 保存数据
 */
function sysSelectDatasPGPJ() {
	var ids = "";
	var names = "";
 
	$("input[name='win_PGPJId']").each(function(index, item) {
		if (index == 0) {
			ids = $(this).val();
		} else {
			ids = ids + "," + $(this).val();
		}
	});
	$("input[name='win_PGPJname']").each(function(index, item) {
		if (index == 0) {
			names = $(this).val();
		} else {
			names = names + " | " + $(this).val();
		}
	});
	temp_PGPJ_selectIds = ids;
	temp_PGPJ_selectName = names;
}
/**
 * 初始化清除和删除方法
 */
function clearPGPJFunctionMethod() {
	sysSelectDatasPGPJ();
	$(".selected-type-box>.tag> .panel-tool-close").click(function() {
		$(this).parent().remove();
		sysSelectDatasPGPJ();
	});
	$(".selected-type .clear-select-btn").click(function() {
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasPGPJ();
	});
}

/**
 * 报价配件料组合保存
 */
function saveChoose_pgpj() {
	sysSelectDatasPGPJ();
	$("#priceGroupId").val(temp_PGPJ_selectIds);
	$("#priceGroupDesc").val(temp_PGPJ_selectName);
	
	var url = ctx + "/priceGroupPj/getPriceGroupPjlByIds";
	var param = {
		"ids" : $.trim($("#priceGroupId").val())
	};
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == 0) {
			
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				var pjlIdTemp = pjlIds_fin.toString().split(",");
				
				var pjlIds = "";
				var pjlDescs = "";
				$.each(ret, function(i, item) {
					if(pjlIdTemp.length > 0){
						if(pjlIds){
							if($.inArray(item.pjlId, pjlIdTemp) == -1){
								pjlIds = pjlIds + "," + item.pjlId;
								pjlDescs = pjlDescs + " | " + item.proName;
								pjlIdTemp.push(item.pjlId);
							}
						}else{
							pjlIds = item.pjlId;
							pjlDescs = item.proName;
							pjlIdTemp.push(item.pjlId);
						}
					}
					pjlIds_fin = pjlIds;
					pjlDesc_fin = pjlDescs;
				});
			}
			$("#pjlId").val(pjlIds_fin);
			$("#pjlDesc").val(pjlDesc_fin);
		}
		closePGPJ_w();
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
/*---------------选择报价配件料组合end-------------------*/