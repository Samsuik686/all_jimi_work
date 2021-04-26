///////////////////// 选择配件料JQ
var temp_PJL_selectIds; // 临时存储的Ids
var temp_PJL_selectName; // 临时存储的名称
var temp_PJL_price;//价格

var currentPageNum_PJL;

$(function() {
//	keySearch(queryListPagePJL, 1);
});
/**
 * 获取当前分页参数
 * 
 * @param size
 *            数据源
 * @param DataGrid
 *            DataGrid ID
 */
function pageSetPJL(size) {
	$('.panel-tool').hide();
	var p = $("#pjl_DataGrid").datagrid('getPager');
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
			queryListPagePJL(pageNumber);
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
function getPageSizePJL() {
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
			pageSetPJL(returnData.data);
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
function resetCurrentPagePJL(currentPageNum) {
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum_PJL = 1;
	}
	$('#pjl_DataGrid').datagrid('getPager').pagination({
		pageNumber : currentPageNum_PJL
	});
}

/*---------获取配件料 start-----------*/
function queryListPagePJL(pageNumber) {
	currentPageNum_PJL = pageNumber;
	if (currentPageNum_PJL == "" || currentPageNum_PJL == null) {
		currentPageNum_PJL = 1;
	}
	var pageSize = getCurrentPageSize('pjl_DataGrid1');
	var url = ctx + "/pjlmanage/pjlmanageList";
	var param = {
		"marketModel" : $("#searchByMarketModel").val(),
		"model" : $("#defaultModel").val(),
		"searchType" : "modelSearch",
		"proName" : $("#searchByProName").val(),
		"proNO" : $("#searchByProNO").val(),
		"currentpage" : currentPageNum_PJL,
		"pageSize" : pageSize
	};
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#pjl_DataGrid").datagrid('loadData', returnData.data);
			getPageSizePJL();
			resetCurrentPagePJL(currentPageNum_PJL);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
/*-------获取配件料 end--------------*/

////打开选择配件料窗口
function openPJL(){
	windowVisible("pjl_w");
	$("#searchByMarketModel").val("");
	$("#searchByProName").val("");
	$("#searchByProNO").val("");
	$('#pjl_w').window('open');
	queryListPagePJL();
}

function closepjl_w(){
	$('#pjl_w').window('close');
}

// 配件料添加按钮事件
function doinsertPJL() {
	windowVisible("pjl_w");
	queryListPagePJL(1);
	$("#selected-type-box-PJL").empty();
	temp_PJL_selectIds = $("#pjlId").val();
	temp_PJL_selectName = $("#pjlDesc").val();
	temp_PJL_price = $("#pjlPrice").val();

	// 初始化所选择的配件料
	var names = temp_PJL_selectName.split("|");
	var ids = temp_PJL_selectIds.split(",");
	if (names != "" && names != null && names.length > 0) {
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++) {
			var name = names[int];
			var id = ids[int]
			htmlDatas = htmlDatas + "<span class='tag'>" + name + "<input type='hidden' name='win_PJLId' value=" + id + "><input type='hidden' name='win_PJLname' value=" + name + "><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-PJL").append(htmlDatas);
	}
	clearPJLFunctionMethod();
	openPJL();

}

function dbClickChoosePJL(index, value) {
	var Falg = true;
	var ids = temp_PJL_selectIds.toString().split(",");
	if (ids != "" && ids != null && ids.length > 0) {
		for (var int = 0; int < ids.length; int++) {
			if (ids[int] == value.pjlId) {
				Falg = false;
				break;
			}
		}
	}
	if (Falg) {
		$("#selected-type-box-PJL").append("<span class='tag'>" + value.proName + "<input type='hidden' name='win_PJLId' value=" + value.pjlId + "><input type='hidden' name='win_PJLname' value=" + value.proName + "><input type='hidden' name='win_PJLprice' value=" + value.retailPrice + "><button class='panel-tool-close'></button></span>");
		if (temp_PJL_selectIds != "") {
			temp_PJL_selectIds = temp_PJL_selectIds + "," + value.pjlId;
			temp_PJL_price += temp_PJL_price*1 + value.retailPrice*1;
		} else {
			temp_PJL_selectIds = value.pjlId;
			temp_PJL_price = value.retailPrice*1;
		}
		if (temp_PJL_selectName != "") {
			temp_PJL_selectName = temp_PJL_selectName + " | " + value.proName;
		} else {
			temp_PJL_selectName = value.proName;
		}
		clearPJLFunctionMethod();
	} else {
		$.messager.alert("操作提示", "附件已选择！", "warning");
	}
}

/**
 * 保存数据
 */
function sysSelectDatasPJL() {
	var ids = "";
	var names = "";
	var price = 0;
 
	$("input[name='win_PJLId']").each(function(index, item) {
		if (index == 0) {
			ids = $(this).val();
		} else {
			ids = ids + "," + $(this).val();
		}
	});
	$("input[name='win_PJLname']").each(function(index, item) {
		if (index == 0) {
			names = $(this).val();
		} else {
			names = names + " | " + $(this).val();
		}
	});
	$("input[name='win_PJLprice']").each(function(index, item) {
		if (index == 0) {
			price = $(this).val();
		} else {
			price = price*1 + $(this).val()*1;
		}
	});
	temp_PJL_selectIds = ids;
	temp_PJL_selectName = names;
	temp_PJL_price = price;
}
/**
 * 初始化清除和删除方法
 */
function clearPJLFunctionMethod() {
	sysSelectDatasPJL();
	$(".selected-type-box>.tag> .panel-tool-close").click(function() {
		$(this).parent().remove();
		//删除配件料，配件料组合全删除
		$("#priceGroupId").val("");
		$("#priceGroupDesc").val("");
		sysSelectDatasPJL();
	});
	$(".selected-type .clear-select-btn").click(function() {
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		//删除配件料，配件料组合全删除
		$("#priceGroupId").val("");
		$("#priceGroupDesc").val("");
		sysSelectDatasPJL();
	});
}

/**
 * 配件料保存
 */
function pjlSave() {
	$.messager.alert("操作提示", "1111111111111111111", "info");
	sysSelectDatasPJL();
	$("#pjlId").val(temp_PJL_selectIds);
	$("#pjlDesc").val(temp_PJL_selectName);
	
	var url = ctx + "/pjlmanage/getSumPriceByIds";
	var param = {
		"ids" : $("#pjlId").val()
	};
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#pjlPrice").val(returnData.data);
			if(!$("#pjlPrice").val()){
				$("#price").val(0);
			}
			if(!$("#gzbjPrice").val()){
				$("#gzbjPrice").val(0);
			}
			$("#price").val(($("#pjlPrice").val()*1 + $("#gzbjPrice").val()*1).toFixed(2));
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
	windowCommClose("pjl_w");
}
/*---------------选择配件料 end-------------------*/