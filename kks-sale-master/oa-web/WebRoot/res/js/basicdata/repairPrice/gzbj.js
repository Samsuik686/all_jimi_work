///////////////////// 选择故障报价JQ
var temp_GZBJ_selectIds; // 临时存储的Ids
var temp_GZBJ_selectName; // 临时存储的名称
var temp_GZBJ_price;//价格

var currentPageNum_GZBJ;

$(function() {
//	keySearch(queryListPageGZBJ, 1);
});
/**
 * 获取当前分页参数
 * 
 * @param size
 *            数据源
 * @param DataGrid
 *            DataGrid ID
 */
function pageSetGZBJ(size) {
	$('.panel-tool').hide();
	var p = $("#gzbj_DataGrid").datagrid('getPager');
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
			queryListPageGZBJ(pageNumber);
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
function getPageSizeGZBJ() {
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
			pageSetGZBJ(returnData.data);
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
function resetCurrentPageGZBJ(currentPageNum) {
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum_GZBJ = 1;
	}
	$('#gzbj_DataGrid').datagrid('getPager').pagination({
		pageNumber : currentPageNum_GZBJ
	});
}

/*---------获取故障报价 start-----------*/
function queryListPageGZBJ(pageNumber) {
	currentPageNum_GZBJ = pageNumber;
	if (currentPageNum_GZBJ == "" || currentPageNum_GZBJ == null) {
		currentPageNum_GZBJ = 1;
	}
	var pageSize = getCurrentPageSize('gzbj_DataGrid1');
	var url = ctx + "/gzbjmanage/gzbjmanageList";
	var param = {
			"faultType" : $.trim($("#searchByFaultType").val()),
			"currentpage" : currentPageNum_GZBJ,
			"pageSize" : pageSize
	};
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#gzbj_DataGrid").datagrid('loadData', returnData.data);
			getPageSizeGZBJ();
			resetCurrentPageGZBJ(currentPageNum_GZBJ);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
/*-------获取故障报价 end--------------*/

////打开选择故障报价窗口
function openGZBJ(){
	windowVisible("gzbj_w");
	$("#searchByFaultType").val("");
	$('#gzbj_w').window('open');
	queryListPageGZBJ();
}

function closeGZBJ_w(){
	$('#gzbj_w').window('close');
}

// 故障报价添加按钮事件
function doinsertGZBJ() {
	windowVisible("gzbj_w");
	queryListPageGZBJ(1);
	$("#selected-type-box-GZBJ").empty();
	temp_GZBJ_selectIds = $("#gzbjId").val();
	temp_GZBJ_selectName = $("#gzbjDesc").val();
	temp_GZBJ_price = $("#gzbjPrice").val();

	// 初始化所选择的故障报价
	var names = temp_GZBJ_selectName.split("|");
	var ids = temp_GZBJ_selectIds.split(",");
	if (names != "" && names != null && names.length > 0) {
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++) {
			var name = names[int];
			var id = ids[int]
			htmlDatas = htmlDatas + "<span class='tag'>" + name + "<input type='hidden' name='win_GZBJId' value=" + id + "><input type='hidden' name='win_GZBJname' value=" + name + "><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-GZBJ").append(htmlDatas);
	}
	clearGZBJFunctionMethod();
	openGZBJ();

}

function dbClickChooseGZBJ(index, value) {
	var Falg = true;
	var ids = temp_GZBJ_selectIds.toString().split(",");
	if (ids != "" && ids != null && ids.length > 0) {
		for (var int = 0; int < ids.length; int++) {
			if (ids[int] == value.id) {
				Falg = false;
				break;
			}
		}
	}
	if (Falg) {
		$("#selected-type-box-GZBJ").append("<span class='tag'>" + value.faultType + "<input type='hidden' name='win_GZBJId' value=" + value.id + "><input type='hidden' name='win_GZBJname' value=" + value.faultType + "><input type='hidden' name='win_GZBJprice' value=" + value.costs + "><button class='panel-tool-close'></button></span>");
		if (temp_GZBJ_selectIds != "") {
			temp_GZBJ_selectIds = temp_GZBJ_selectIds + "," + value.id;
			temp_GZBJ_price += temp_GZBJ_price*1 + value.costs*1;
		} else {
			temp_GZBJ_selectIds = value.id;
			temp_GZBJ_price = value.costs*1;
		}
		if (temp_GZBJ_selectName != "") {
			temp_GZBJ_selectName = temp_GZBJ_selectName + " | " + value.faultType;
		} else {
			temp_GZBJ_selectName = value.faultType;
		}
		clearGZBJFunctionMethod();
	} else {
		$.messager.alert("操作提示", "附件已选择！", "warning");
	}
}

/**
 * 保存数据
 */
function sysSelectDatasGZBJ() {
	var ids = "";
	var names = "";
	var price = 0;
 
	$("input[name='win_GZBJId']").each(function(index, item) {
		if (index == 0) {
			ids = $(this).val();
		} else {
			ids = ids + "," + $(this).val();
		}
	});
	$("input[name='win_GZBJname']").each(function(index, item) {
		if (index == 0) {
			names = $(this).val();
		} else {
			names = names + " | " + $(this).val();
		}
	});
	$("input[name='win_GZBJprice']").each(function(index, item) {
		if (index == 0) {
			price = $(this).val();
		} else {
			price = price*1 + $(this).val()*1;
		}
	});
	temp_GZBJ_selectIds = ids;
	temp_GZBJ_selectName = names;
	temp_GZBJ_price = price;
}
/**
 * 初始化清除和删除方法
 */
function clearGZBJFunctionMethod() {
	sysSelectDatasGZBJ();
	$(".selected-type-box>.tag> .panel-tool-close").click(function() {
		$(this).parent().remove();
		sysSelectDatasGZBJ();
	});
	$(".selected-type .clear-select-btn").click(function() {
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasGZBJ();
	});
}

/**
 * 故障报价保存
 */
function gzbjSave() {
	sysSelectDatasGZBJ();
	$("#gzbjId").val(temp_GZBJ_selectIds);
	$("#gzbjDesc").val(temp_GZBJ_selectName);
	
	var url = ctx + "/gzbjmanage/getSumPriceByIds";
	var param = {
		"ids" : $("#gzbjId").val()
	};
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#gzbjPrice").val(returnData.data);
			
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
	windowCommClose("gzbj_w");
}
/*---------------选择故障报价 end-------------------*/