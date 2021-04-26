$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;

$(function() {
	datagridInit();
	keySearch(queryListPage);
});

function queryListPage(pageNumber) {// 初始化数据及查询函数
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var url = ctx + "/sxdwmanage/sxdwmanageList";
	var selParams = "cusName=" + $.trim($("#searchByCusName").val()) + "&phone=" + $.trim($("#searchByPhone").val())
					+ "&enabledFlag=" + $("#searchByEnabledFlag").combobox('getValue');
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {

		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data, function(i, item) {
				if (item.enabledFlag == 0) {
					item.enabledFlag = '<label style="color:green;font-weight: bold;">否</label>';
				} else if (item.enabledFlag == 1) {
					item.enabledFlag = '<label style="color:red;font-weight: bold;">是</label>';
				}
			});
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

function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			sxdw_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}

function sxdw_addInfo() {

	$("#cId_hidden").val(0);
	// 清空下缓存
	$("#cusNameP").val("");
	$("#serviceTimeP").val("");
	$("#linkmanP").val("");
	$("#phoneP").val("");
	$("#telephoneP").val("");
	$("#emailP").val("");
	$("#faxP").val("");
	$("#addressP").val("");
	$("#remarkP").val("");
	$("#createBy").val("");
	$("#updateBy").val("");
	$("#createTime").val("");
	$("#updateTime").val("");
	$("input[name='enabledFlag'][value='0']").prop('checked', true);// 默认不禁用
	updateWindowOpen();

	// 改变弹出框位置add by wg.he 2013/12/10
	chageWinSize('updateWindow');
}

// 自定义校验规则
$.extend($.fn.validatebox.defaults.rules, {
	
	//校验系统是否存在该手机号
	phoneCheck: {
		validator: function(value, param){
			var cId = $('#cId_hidden').val();
			if (cId == 0) {
				cId = null;
			}
			if (value.length == 11) {
				var flag = false;
				var data = {
					"cId" : cId,
					"phone" : value
				};
				var url = ctx + "/sxdwmanage/phoneCheck"
				$.ajax({
					url : url,
					type : 'post',
					async: false,
					data : data,
					success : function(returnData){
						if (returnData.code == 0) {
							flag = true;
						} 
					},
					fail:function(){
					}
				});
				return flag;
			} else {
				return true;
			}
		},
		message: '该手机号已存在！'
    },
    //校验系统是否存在该地址
    addressCheck: {
		validator: function(value, param){
			var cId = $('#cId_hidden').val();
			if (cId == 0) {
				cId = null;
			}
			var flag = false;
			var data = {
				"cId" : cId,
				"address" : value
			};
			var url = ctx + "/sxdwmanage/addressCheck";
			$.ajax({
				url : url,
				type : 'post',
				async: false,
				data : data,
				success : function(returnData){
					if (returnData.code == 0) {
						flag = true;
					}
				},
				fail:function(){
				}
			});
			return flag;
		},
		message: '该地址已存在！'
    },
});


function sxdwmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var cId = $("#cId_hidden").val();
		var cusName = $("#cusNameP").val();
		var serviceTime = $("#serviceTimeP").val();
		var linkman = $.trim($("#linkmanP").val());
		var phone = $.trim($("#phoneP").val());
		var telephone = $.trim($("#telephoneP").val());
		var email = $.trim($("#emailP").val());
		var fax = $("#faxP").val();
		var address = $.trim($("#addressP").val());
		var remark = $.trim($("#remarkP").val());
		var reqAjaxPrams = "cId=" + $("#cId_hidden").val() + "&cusName=" + cusName + "&serviceTime=" + serviceTime + "&linkman=" + linkman + 
							"&phone=" + phone + "&telephone=" + telephone + "&email=" + email + "&fax=" + fax + "&address=" + address + 
							"&remark=" + remark + "&enabledFlag=" + $("input[name='enabledFlag']:checked").val() + "&id=0";
		processSaveAjax(reqAjaxPrams, 0);
	}
}



function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/sxdwmanage/addOrUpdateSxdwmanage";
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {

		processSSOOrPrivilege(returnData);

		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载SxdwmanageList
			}else{
				queryListPage(1);// 重新加载SxdwmanageList
			}
			
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function sxdw_updateInfo() {
	
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		var param = "cId=" + row.cid;
		var url = ctx + "/sxdwmanage/getSxdwmanage";
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			$("#cId_hidden").val(returnData.data.cid);
			$("#cusNameP").val(returnData.data.cusName);
			$("#serviceTimeP").val(returnData.data.serviceTime);
			$("#linkmanP").val(returnData.data.linkman);
			$("#phoneP").val(returnData.data.phone);
			$("#telephoneP").val(returnData.data.telephone);
			$("#emailP").val(returnData.data.email);
			$("#faxP").val(returnData.data.fax);
			$("#addressP").val(returnData.data.address);
			$("#remarkP").val(returnData.data.remark);
			$("#createBy").val(returnData.data.createBy);
			$("#updateBy").val(returnData.data.updateBy);
			$("#createTime").val(returnData.data.createTime);
			$("#updateTime").val(returnData.data.updateTime);
			if (returnData.data.enabledFlag == "1") {
				$("input[name='enabledFlag'][value='1']").prop('checked', true);
			} else {
				$("input[name='enabledFlag'][value='0']").prop('checked', true);
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
		updateWindowOpen();
		chageWinSize('updateWindow');
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 删除
 */
function sxdw_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此客户信息", function(conf) {
			if (conf) {
				var getCId = row.cid;
				var url = ctx + "/sxdwmanage/deleteSxdwmanage";
				var param = "cId=" + getCId;
				if (getCId != -1) {
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);// 重新加载SxdwmanageList
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
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-SXDW-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("sxdwmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	ExportExcelDatas("sxdwmanage/ExportDatas?cusName=" + $.trim($("#searchByCusName").val()) + "&phone=" + $.trim($("#searchByPhone").val())
					+ "&enabledFlag=" + $("#searchByEnabledFlag").combobox('getValue'));
}

/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function updateWindowClose() {
	$('#updateWindow').window('close');
}

function updateWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#updateWindow').window('open');
	windowVisible("updateWindow");
}
