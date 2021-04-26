$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
$(function() {
	datagridInit();
	groupTreeInit_YCFK();
	keySearch(queryListPage);
	$("input[name='completionState']").click(function(){
		iscomplete();
	});
	 $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	});
});

/**
 * 判断解决状态
 */
function iscomplete(){
	var completionState = $("input[name='completionState']:checked").val();
	if(completionState == "0"){
		$("#measuresi").val("");
		$("#completionDatei").val("");
	}else if(completionState == "1"){
		if(!$("#completionDatei").val()){
			$("#completionDatei").val(initTime())
		}
		if(!$("#measures").val()){
			$("#measuresi").val($("#hideMethod").val());
		}
	}
}
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
	getTreeNode();
	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/ycfkmanage/ycfkmanageList";
	var param = {
			"completionState" : $("#completionState").combobox('getValue'),
			"startTime" : $("#startTime").val(),
			"endTime" : $("#endTime").val(),
			"treeDate" :$("#tree-Date").val(),
			"currentpage" : currentPageNum,
			"pageSize" : pageSize,
			"recipient":$("#recipient").val(),
			"customerName":$("#customerName").val(),
			"phone":$("#phone").val()
		}	
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var ret = returnData.data;
		if (ret.length > 0) {
			$.each(ret, function(i, j) {
//				if(j.measures.length > 17){
//					j.measures = j.measures.substring(0,17) + "...";
//				}
				if (j.completionState == '0') {
					j.completionState = "<strong style='color:red'>待解决</strong>";
				} else if (j.completionState == '1') {
					j.completionState = "<strong style='color:green'>已完成</strong>";
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


function getTreeNode(){
	var treeDate = $('#tree-Date').val();
	if(treeDate && treeDate!='1' && treeDate!='2'){
		//点击tree查询清空处理状态和开始结束日期
		$("#completionState").combobox('setValue','0');
		$("#startTime").val("");
		$("#endTime").val("");
	}else{
		$('#tree-Date').val("");
	}
	var treeState = $("#tree-State").val();
	if(treeState=='2'){
		//查询已完成的数据
		$("#completionState").combobox('setValue','1');
	}else if(treeState=='1'){
		$("#completionState").combobox('setValue','0');
	}
}

/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			ycfk_updateInfo(rowIndex,row);
		}
	});
	queryListPage(1);
	
	$("#ycfkBaseDataGrid").datagrid({
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			dbClickChooseYcfkBase(rowIndex,row);
		}
	});
	queryYcfkBaseList(1);
}

//时分秒十以内前面加0
function addZero(v) {
	if (v < 10) {
		return '0' + v;
	} else {
		return v;
	}
}

// 获得当前时间
function initTime() {
	var date = new Date();
	return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

// TODO 清除表单数据
function clearFromParams() {
	$("#backIdi").val(0);
	$("#yidi").val(0);
	$("#modeli").val("");
	$("#problemsi").val("");
	$("#imeii").val("");
	$("input[name='completionState'][value='0']").prop('checked', true);
	$("#descriptioni").val("");
	$("#feedBackDatei").val(initTime());
	$("#recipienti").val("");
	$("#responsibilityUniti").val("");
	$("#measuresi").val("");
	$("#hideMethod").val("");
	$("#completionDatei").val("");
	$("#customerNamei").val("");
	$("#phonei").val("");
	$("#remaki").val("");
	$("#measuresiTab").hide();
}

function addClear(){
	$("#backIdi").val(0),
	$("#modeli").val(""),
	$("#imeii").val(""),
	$("input[name='completionState'][value='0']").prop('checked', true);
	$("#descriptioni").val(""),  
	$("#feedBackDatei").val(""), 
	$("#recipienti").val(""), 
	$("#responsibilityUniti").val(""),
	$("#measuresi").val(""),
	$("#hideMethod").val(""),
	$("#completionDatei").val(""),
	$("#customerNamei").val(""), 
	$("#phonei").val(""),
	$("#remaki").val(""),
	$("#yidi").val(0)
}

// 新增异常管理信息
function ycfkmanageSave() {
	var isValid = $('#mform').form('validate');
	var feedBackDate = $("#feedBackDatei").val();
	if (!feedBackDate) {
		$("#feedBackDatei").val(initTime());
	}
	feedBackDate = $("#feedBackDatei").val();
	if (isValid) {
		var reqAjaxPrams ={
			"backId" : $("#backIdi").val(),
			"model" : $("#modeli").val(),
			"imei" : $("#imeii").val(),
			"completionState" : $("input[name='completionState']:checked").val(),
			"description" : $("#descriptioni").val(),  
			"feedBackTime" : feedBackDate,
			"recipient" : $("#recipienti").val(), 
			"responsibilityUnit" : $("#responsibilityUniti").val(),
			"measures" : $("#measuresi").val(),"hideMethod" : $("#hideMethod").val(),
			"completionTime" : $("#completionDatei").val(),
			"customerName" : $("#customerNamei").val(), 
			"phone" : $("#phonei").val(),
			"remak" : $("#remaki").val(),
			"yid" : $("#yidi").val()
		}
		processSaveAjax(reqAjaxPrams);
	}
}
// 是否同步刷新
function processSaveAjax(reqAjaxPrams) {
	var url = ctx + "/ycfkmanage/addOrUpdateYcfkmanage";
	var id = $("#backIdi").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			windowCloseYcfkmanage();
			if (id > 0) {
				queryListPage(currentPageNum);
			} else {
				queryListPage(1);
			}
			
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
// 删除400电话记录信息
function ycfk_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var index = $('#DataGrid1').datagrid('getRowIndex', row);
				$('#DataGrid1').datagrid('deleteRow', index);
				var deleted = $('#DataGrid1').datagrid('getChanges', "deleted");
				var getCurrentId;
				for (var z = 0; z < deleted.length; z++) {
					getCurrentId = deleted[z].backId;

					var url = ctx + "/ycfkmanage/deleteYcfkmanage";
					var param = "backId=" + getCurrentId;

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
				$('#DataGrid1').datagrid('acceptChanges');
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

// 弹出新增框
function ycfk_addInfo() {
	clearFromParams();
	windowOpenYcfkmanage();
	$('#w').panel({title: "新增400电话记录信息"});
	chageWinSize('w');
	addClear();
}
// 弹出修改框
function ycfk_updateInfo() {
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		$('#measuresi').validatebox({required:false});
		var getCurrentId = updated.backId;
		var url = ctx + "/ycfkmanage/selectYcfkmanage";
		var param = "backId=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var entity = returnData.data.data;
			// TODO 数据展示
			$("#backIdi").val(entity.backId);
			$("#yidi").val(entity.yid);
			$("#modeli").val(entity.model);
			$("#imeii").val(entity.imei);
			if (entity.completionState == 0) {
				$("input[name='completionState'][value='0']").prop("checked", true);
			} else if (entity.completionState == 1){
				$("input[name='completionState'][value='1']").prop("checked", true);
			}
			$("#descriptioni").val(entity.description);
			$("#feedBackDatei").val(entity.feedBackDate);
			var feedBackDate = $("#feedBackDatei").val();
			if (!feedBackDate) {
				$("#feedBackDatei").val(initTime());
			}
			feedBackDate = $("#feedBackDatei").val();
			$("#recipienti").val(entity.recipient);
			$("#responsibilityUniti").val(entity.responsibilityUnit);
			$("#measuresi").val(entity.measures);
			$("#hideMethod").val(entity.hideMethod);
			$("#completionDatei").val(entity.completionDate);
			$("#customerNamei").val(entity.customerName);
			$("#phonei").val(entity.phone);
			$("#remaki").val(entity.remak);
			$('#w').panel({title: "编辑400电话记录信息"});
			windowOpenYcfkmanage();
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});		
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-YCFK-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("ycfkmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/ycfkmanage/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='completionState' value='" + $("#completionState").combobox('getValue') +"'/>")); 
	$form1.append($("<input type='hidden' name='startTime' value='" + $("#startTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='endTime' value='" + $("#endTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='treeDate' value='" + $.trim($("#tree-Date").val())+"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

//////////////// +++++  移植400电话记录定义的问题描述新增框 --start   +++++////////////////

function windowOpenEncl_YcfkBase() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#w1_ycfkBase').window('open');
	windowVisible("w1_ycfkBase");
}
function windowCloseEncl_YcfkBase() {
	$('#w1_ycfkBase').window('close');
}
//弹出问题描述新增框
function ycfkBase_addInfo() {
	clearFromParams()
	windowOpenEncl_YcfkBase();
	chageWinSize('w1_ycfkBase');
	comboboxInit();
	
}

//TODO 清除表单数据
function clearFromParams() {
	$("#yid").val(0);
	$("#problems").val("");
	$("#proDetails").val("");
	$("#methods").val("");
	comboboxInit();
}

//新增400电话记录定义管理信息
function ycfkBase_Save() {
	var isValid = $('#mform_w1').form('validate');
	if (isValid) {
		var reqAjaxPrams={
				yid : $("#yid").val(),
				gId : $("#type").combobox('getValue'),
				problems : $("#type").combobox('getText'),
				proDetails : $.trim($("#proDetails").val()),
				methods : $.trim($("#methods").val())		
		}
		processSaveAjaxes_w1(reqAjaxPrams);
	}
}

//是否同步刷新
function processSaveAjaxes_w1(reqAjaxPrams) {
	var url = ctx + "/ycfkBase/addOrUpdateYcfkBase";
	var id = $("#yid").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {

		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			windowCloseEncl_YcfkBase();
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


////////////////+++++  移植400电话记录定义的问题描述新增框 --end   +++++////////////////



/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function windowOpenYcfkmanage() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#w').window('open');
	windowVisible("w");
}
function windowCloseYcfkmanage() {
	$('#w').window('close');
}

