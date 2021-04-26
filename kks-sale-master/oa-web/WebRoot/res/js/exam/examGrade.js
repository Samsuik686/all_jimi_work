var currentPageNum;
var currentUserName;

$(function() {
	examInit();
	queryListPage(1);
});

// 考试下拉框初始化
function examInit() {
	var url = ctx + "/examInfo/getAllExam";
	asyncCallService(url, 'post', false, 'json', null, function(returnData) {
		var ret = returnData.data;
		if(ret.length > 0) {
			var html = "<option value=\"\" selected=\"selected\">全部</option>";
			$.each(ret, function(i, j) { 
                html += "<option value=\""+ j.examId +"\">"+ j.examName +"</option>"
			})
			$("#examId").append(html);
		};
	}, function() {
		$.messager.alert("系统提示", "网络错误", "info");
	});
}

// 导出成绩
function exportInfo() {
	var url =  ctx + "/examGrade/exportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='gradeState' value='" + $('#gradeState').val() +"'/>")); 
	$form1.append($("<input type='hidden' name='examId' value='" + $("#examId").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='examineeName' value='" + $("#examineeName").val() +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}


// 初始化数据及查询函数
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var examineeName = $("#examineeName").val();
	var gradeState = $('#gradeState').val();
	var examId = $("#examId ").val();
	var url = ctx + "/examGrade/examGradeList";
	var param = {
		"examId": examId,
		"examineeName": examineeName,
		"gradeState" : gradeState,
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	}

	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var ret = returnData.data;
		if(ret.length > 0) {
			$.each(ret, function(i, j) { 
				if(j.grade == null || j.grade == ""){
					j.grade = 0;
				}
				if(j.gradeState == -1) {
					j.gradeStateText = "<strong style='color:blue'>未交卷</strong>";
				}
				if(j.gradeState == 0) {
					j.gradeStateText = "<strong style='color:blue'>未完成</strong>";
				}
				if(j.gradeState == 1) {
					j.gradeStateText = "<strong style='color:green'>已完成</strong>";
				}
				
				if (j.costTime == undefined)
				{
					j.costTime = "/";
				} else {
					j.costTime += "分钟";
				}
				
				
			})
		};
		
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

// 刷新
function refreshInfo() {
	$("#examId").val("");
	$("#examineeName").val("");
	queryListPage(currentPageNum);
}

