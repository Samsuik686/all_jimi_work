function basegroupTreeInit() {
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=" + $.trim($("#LX_Type").val()),
		dataType : "json",
		success : function(returnData) {
			treeLoadAgin(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function treeLoadAgin(returnData) {
	var groupList = '[{"id":"","text":"所有类别",children:['
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree').tree({
		data : eval(groupList)
	// 正确效果
	});
}

function basegroupTreeSelect() {
	$('#typeTree').tree({
		onClick : function(node) {
			$("#gIds").val(node.id);
			queryListPage(currentPageNum);
		}
	});
}
//添加分类时弹出框
function treesAddwindow() {
	clearText();
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#wg').window('open');
	$("#treeUpdate").attr("onclick","zbxhTypeAddOrUpdate(\'"+"BASE_ZBXH"+"\')");
	windowVisible("wg");

}
function tree_windowClose() {
	$('#wg').window('close');
}
function clearText() {
	$("#gId").val(0);
	$("#enumSn").val("");
	$("#gName").val("");
	$("#createTime").val("");
}

// 增加
function treesAddOrUpdate() {
	var isValid = $('#group_form').form('validate');
	if(isValid){
		var reqAjaxPrams = {
				gId : $.trim($("#gId").val()),
				enumSn : $.trim($("#LX_Type").val()),
				gName : $.trim($("#gName").val())
		}
		processSaveAjaxed(reqAjaxPrams);
	}
}

// 是否同步刷新
function processSaveAjaxed(reqAjaxPrams) {
	var url = ctx + "/basegroupcon/addOrUpdateInfo";
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			tree_windowClose();
			basegroupTreeInit();
			queryListPage(1);//初始化页面数据
			comboboxInit();
			if($("#LX_Type1").val()){
				comboboxInit1();//最终故障型号类别
			}
		} 
		$.messager.alert("操作提示",returnData.msg,"info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
// 删除
function treesDelete() {
	var node = $("#typeTree").tree('getSelected');
	if (!node) {
		$.messager.confirm("操作提示", "请选中一个类型", "info");
	} else {
		$.messager.confirm("操作提示", "确定要删除此类型吗？", function(conf) {
			if (conf) {
				var url = ctx + '/basegroupcon/deleteInfo';
				var param = "gId=" + node.id;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					$.messager.alert("操作提示", returnData.msg, "info");
					basegroupTreeInit();
					queryListPage(1);//初始化页面数据
					comboboxInit();
					if($("#LX_Type1").val()){
						comboboxInit1();//最终故障型号类别
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});

			}
		});
	}
}

// 修改
function treesUpdateWindow(type) {
	var id="typeTree";
	if(type !=null && type != undefined){
		id = getTreeIdByType(type);
	}
	var node = $("#"+id).tree('getSelected');
	if (!node) {
		$.messager.confirm("操作提示", "请选中一个类型", "info");
	} else {
		$.messager.confirm("操作提示", "确定要修改此类型？", function(conf) {
			if (conf) {
				var url = ctx + '/basegroupcon/getInfo';
				var param = "gId=" + node.id;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					$("#gName").val(returnData.data.gname);
					$("#gId").val(returnData.data.gid);
					$("#enumSn").val(returnData.data.enumSn);
					$("#createTime").val(returnData.data.createTime);
					$(".validatebox-tip").remove();
					$(".validatebox-invalid").removeClass("validatebox-invalid");
					$('#wg').window('open');
					$("#treeUpdate").attr("onclick","zbxhTypeAddOrUpdate(\'"+returnData.data.enumSn+"\')");
					windowVisible("wg");
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		});
	}
}

// 类型下拉框的公用方法
function comboboxInit() {
	var url = ctx + '/basegroupcon/queryList';
	var param = "enumSn=" + $("#LX_Type").val();;
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		comboboxLoadType(returnData);
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function comboboxLoadType(returnData) {
	var diclist = '[';
	$.each(returnData.data, function(i, j) {
		diclist += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	diclist = diclist.replace(reg, "");
	diclist += ']';
	$('#type').combobox({
		data : eval('(' + diclist + ')'),
		valueField : 'id',
		textField : 'text',
		onShowPanel: function () {
		    var v = $(this).combobox('panel')[0].childElementCount;
		    if (v <= 10) {
		        $(this).combobox('panel').height("auto");
		    } else {
		        $(this).combobox('panel').height(238);
		    }
		},
		onLoadSuccess : function(data) {
			if (data) {
				/*var node=$("#typeTree").tree('getSelected');
				$('#type').combobox('setValue',node.id);
				$('#type').combobox('setText', node.text);*/
				$('#type').combobox('setValue', data[0].id);
			}
		}
	});
}

//zbxh的分类===============================================================
//添加分类的弹出框
function zbxhTypeAddWindow(type){
	console.log(type);
	clearText();
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	//设置添加分类的参数
	$("#treeUpdate").attr("onclick","zbxhTypeAddOrUpdate(\'"+type+"\')");
	$('#wg').window('open');
	windowVisible("wg");
}
//添加分类
function zbxhTypeAddOrUpdate(type){
	console.log(type);
	var isValid = $('#group_form').form('validate');
	if(!isValid){
		return;
	}
	var reqAjaxPrams = {
		gId : $.trim($("#gId").val()),
		enumSn : type,
		gName : $.trim($("#gName").val())
	}
	doZbxhTypeAddOrUpdate(reqAjaxPrams);
}
function doZbxhTypeAddOrUpdate(reqAjaxPrams){
	var url = ctx + "/basegroupcon/addOrUpdateInfo";
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			tree_windowClose();
			//重新加载分类
			zbxhTreeInit(reqAjaxPrams.enumSn);
			zbxhComboboxInit(reqAjaxPrams.enumSn);
			//重新加载页面
			queryListPage(currentPageNum);
		}
		$.messager.alert("操作提示",returnData.msg,"info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
//加载分类
function zbxhTreeInit(type) {
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=" + $.trim(type),
		dataType : "json",
		success : function(returnData) {
			var treeId = getTreeIdByType(type);
			zbxhTreeLoadAgin(treeId,returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
//获取treeId
function getTreeIdByType(type){
	switch (type) {
		case "BASE_ZBXH":return "typeTree";
		case "BASE_CJLX":return "createTypeTree";
		default:return "";
	}
}
//加载
function  zbxhTreeLoadAgin(id,returnData){
	var groupList = '[{"id":"","text":"所有类别",children:['
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#'+id).tree({
		data : eval(groupList)
	});
}

function zbxhTypeTreeSelect(type) {
	var id = getTreeIdByType(type);
	$('#'+id).tree({
		onClick : function(node) {
			if(type == 'BASE_ZBXH'){
				$("#gIds").val(node.id);
			}
			queryListPage(currentPageNum);
		}
	});
}

//删除类型
function zbxhTreesDelete(type) {
	var treeId = getTreeIdByType(type);
	var node = $("#"+treeId).tree('getSelected');
	if (!node) {
		$.messager.confirm("操作提示", "请选中一个类型", "info");
	} else {
		$.messager.confirm("操作提示", "确定要删除此类型吗？", function(conf) {
			if (conf) {
				var url = ctx + '/basegroupcon/deleteInfo';
				var param = "gId=" + node.id;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					$.messager.alert("操作提示", returnData.msg, "info");
					zbxhTreeInit(type);
					queryListPage(1);//初始化页面数据
					zbxhComboboxInit(type);
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});

			}
		});
	}
}
// 主板类型下拉框方法
function zbxhComboboxInit(type) {
	var data = zbxhQueryType(type);
	var id = getUpdateIdByType(type);
	zbxhComboboxLoadType(id,data);
}

function zbxhQueryType(type){
	var data={};
	var url = ctx + '/basegroupcon/queryList';
	var param = "enumSn=" + type;
	$.ajax({
		url : url,
		type : "post",
		async:false,
		dataType : 'json',
		data : param,
		cache : false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success : function(result) {
			console.log("返回数据")
			data = result;
		},
		error : function(result) {
			if(error!=undefined && error!=null)
				error.call(this, result);
		}
	});
	console.log("结束了")
	return data;
}
function getUpdateIdByType(type){

	if(type="BASE_CJLX") {
		return "addCreateType";
	}else if(type="BASE_ZBXH") {
		return "type"
	}
}
function zbxhComboboxLoadType(id,returnData) {
	var diclist = '[';
	$.each(returnData.data, function(i, j) {
		diclist += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	diclist = diclist.replace(reg, "");
	diclist += ']';
	$('#'+id).combobox({
		data : eval('(' + diclist + ')'),
		valueField : 'id',
		textField : 'text',
		onShowPanel: function () {
			var v = $(this).combobox('panel')[0].childElementCount;
			if (v <= 10) {
				$(this).combobox('panel').height("auto");
			} else {
				$(this).combobox('panel').height(238);
			}
		},
		onLoadSuccess : function(data) {
			if (data) {
				$('#'+id).combobox('setValue', data[0].id);
			}
		}
	});
}
function strIsEmpty(str) {
	if(str==null||str==undefined||str==""){
		return true;
	}
	return false;
}
