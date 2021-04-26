var currentPageNum;
var currentUserName;

$(function() {
	queryListPage(1)
	currentUserName = $("#currentName").val();
});

//导出试题模板
function exportTemplate() {
	var url =  ctx + "/examInfo/exportTemplate";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

//新增考试弹窗
function exam_add() {
	windowVisible("exam_add");
	$("#creater_add").val(currentUserName);
	$('#exam_add').window('open');		
}

//新增考试保存
function saveExam () {
	$("#saveButton").linkbutton("disable");
	var formParam = {
			"creater" : $("#creater_add").val(),
			"examName" : $("#examName_add").val(),
			"examTotalTime" : $("#examTotalTime_add").val(),
			"examDescription" : $("#examDescription_add").val(),
	}		
	$.ajaxFileUpload({
        url: ctx + "/examInfo/addExam", 
        type: 'post',
        data: formParam,
        secureuri: false, 
        fileElementId: "examTopicFile", 
        success: function (data){ 
        	if (data.code == undefined) {
        		$("#saveButton").linkbutton("enable");
        		$.messager.alert("操作提示","考试新增成功！","info");
        		$("#saveButton").linkbutton("enable");
        		closeWindow();
        		queryListPage(currentPageNum); 
        	} else {
        		$("#saveButton").linkbutton("enable");
        		$.messager.alert("操作提示","考试新增失败，请检查考试文件！","info");
        		$("#saveButton").linkbutton("enable");
        	}
        },
        error: function (data, status, e){
        	$("#saveButton").linkbutton("enable");
        	$.messager.alert("操作提示","试题上传异常！","info");
        	$("#saveButton").linkbutton("enable");
        }
    });
	
}

//开始考试
function exam_start() {
	var row = $('#DataGrid1').datagrid('getSelections');
	if(row.length == 1) {
		$.messager.confirm("操作提示", "是否开始本次考试？", function(flag) {
			if(flag) {
				if(row[0].state != 1) {
					$.messager.alert("提示信息", "该考试不在未开始状态！", "info");
				}else {
					// 修改该场次考试状态为 2 -> 进行中 
					var url = ctx + "/examInfo/startExam";
					var param = {
						"examId": row[0].examId,
						"state": row[0].state	
					}
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);// 重新加载RoleList
						}
						$.messager.alert("操作提示", returnData.msg, "info");
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				}
			}
		});
	} else if(row.length > 1) {
		$.messager.alert("提示信息", "请选择一场考试！", "info");
	} else {
		$.messager.alert("提示信息", "请先选择所要操作的考试！", "info");
	}
}

// 结束考试
function exam_stop() {
	var row = $('#DataGrid1').datagrid('getSelections');
	if(row.length == 1) {
		$.messager.confirm("操作提示", "是否结束本次考试？", function(flag) {
			if(flag) {
				if(row[0].state != 2) {
					$.messager.alert("提示信息", "该考试不在进行中状态！", "info");
				} else {
					// 修改该场次考试状态为 3 -> 已结束 
					var url = ctx + "/examInfo/stopExam";
					var param = {
						"examId": row[0].examId,
						"state": row[0].state
					}
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);// 重新加载RoleList
						}
						$.messager.alert("操作提示", returnData.msg, "info");
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				}
			}
		});
	} else if(row.length > 1) {
		$.messager.alert("提示信息", "请选择一场考试！", "info");
	} else {
		$.messager.alert("提示信息", "请先选择所要操作的考试！", "info");
	}
}

// 删除考试
function exam_del() {
	var row = $('#DataGrid1').datagrid('getSelections');
	if(row.length > 0) {
		// 进行中的考试不可删除
		for(var i = 0; i < row.length; i++) {
			if(row[i].state != 1) {
				$.messager.alert("操作提示", "只能删除未开始考试！", "info");
				return;
			}
		}
		
		$.messager.confirm("操作提示", "是否删除所选考试？", function(flag) {
			if(flag) {
				// 删除该考试 
				var url = ctx + "/examInfo/delExam";
				var examId = row[0].examId;
				for(var i = 1; i < row.length; i++) {
					examId += "," + row[i].examId;
				}
				var param = {
					"examId": examId,
					"state": row[0].state
				}
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						queryListPage(currentPageNum);// 重新加载RoleList
					}
					$.messager.alert("操作提示", returnData.msg, "info");
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				}); 
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要操作的考试！", "info");
	}
}

// 初始化数据及查询函数
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var creater = $("#creater").val();
	var examName = $("#examName").val();
	var url = ctx + "/examInfo/examInfoList";
	var param = {
		"examName": examName,
		"creater": creater,
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	}

	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var ret = returnData.data;
		if(ret.length > 0) {
			$.each(ret, function(i, j) { 
				if(j.state == 1) {
					j.stateText = "<strong style='color:blue'>未开始</strong>";
				}
				if(j.state == 2) {
					j.stateText = "<strong style='color:green'>进行中</strong>";
				}
				if(j.state == 3) {
					j.stateText = "<strong style='color:red'>已结束</strong>";
				}
				
				j.examTotalTime += "分钟";
				j.preview = "<a style=\"color: blue\" onclick=\"exam_preview('"+ j.examId +"')\">预览试卷</a>"
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
	$("#creater").val("");
	$("#examName").val("");
	queryListPage(currentPageNum);
}

function closeWindow(){
	clean()
	$('#exam_add').window('close');
}

//清除新增考试表单数据
function clean() {
	$("#creater_add").val("");
	$("#examName_add").val("");
	$("#examTotalTime_add").val("");
	$("#examDescription_add").val("");
	$("#examTopicFile").val("");
}

//考试时间验证
$.extend($.fn.validatebox.defaults.rules, {
    examTime: {
		validator: function(value, param){
			if(value >= param[0] && value <= param[1]) {
				return true;
			} else {
				return false;
			}
			
		},
		message: '考试时长在{0}分钟到{1}分钟之间！'
    }
});


//预览试题弹窗
function exam_preview(examId) {
	windowVisible("exam_preview");
	$('#exam_preview').window('open');
	queryTopic(examId);
}


var hashMap = {
	    Set : function(key,value){this[key] = value},
	    Get : function(key){return this[key]},
	    Contains : function(key){return this.Get(key) == null?false:true},
	    Size : function(){return this.length}
	}

// 通过点击/失焦事件保存
$(document).ready(function() {
	$('input[type=radio][name=singleSelect]').change(function() {
		var answer = $('input[type=radio][name=singleSelect]:checked').val();
		var topicId = selectTopic[currentTopicSequence].topicId;
		hashMap.Set(topicId,answer);
	});
	
	$('input[type=radio][name=judgeSelect]').change(function() {
		var answer = $('input[type=radio][name=judgeSelect]:checked').val();
		var topicId = judgeTopic[currentTopicSequence].topicId;
		hashMap.Set(topicId,answer);
	});
	
	$('#fillAnswer').blur(function() {
		var answer = $('#fillAnswer').val();
		var topicId = fillTopic[currentTopicSequence].topicId;
		hashMap.Set(topicId,answer);
	});
	
	$('#answerAnswer').blur(function() {
		var answer = $('#answerAnswer').val();
		var topicId = answerTopic[currentTopicSequence].topicId;
		hashMap.Set(topicId,answer);
	});
	
});

// 判断此题是否已答
function isContains(index,currentTopicSequence){
	if(index == 0){
		var topicId = selectTopic[currentTopicSequence].topicId;
		if(hashMap.Contains(topicId)){
			var answer = hashMap.Get(topicId);
			$('input[type=radio][name=singleSelect][value='+ answer +']').attr('checked','true');
		}
	}
	if(index == 1){
		var topicId = judgeTopic[currentTopicSequence].topicId;
		if(hashMap.Contains(topicId)){
			var answer = hashMap.Get(topicId);
			$('input[type=radio][name=judgeSelect][value='+ answer +']').attr('checked','true');
		}
	}
	if(index == 2){
		var topicId = fillTopic[currentTopicSequence].topicId;
		if(hashMap.Contains(topicId)){
			var answer = hashMap.Get(topicId);
			$('#fillAnswer').val(answer);
		}
	}
	if(index == 3){
		var topicId = answerTopic[currentTopicSequence].topicId;
		if(hashMap.Contains(topicId)){
			var answer = hashMap.Get(topicId);
			$('#answerAnswer').val(answer);
		}
	}
	
}

// 第一题
function first(){
	var flag = innerHTML(currentTopicSequence);
	if (flag){
		$.messager.alert("操作提示", "没有该题型的题目。", "info");
		
		$("#topicContent" + flag).hide();
		$("#count").hide();
	}
}

// 上一题
function back(){
	var tab = $('#topicTabs').tabs('getSelected');
	var index = $('#topicTabs').tabs('getTabIndex',tab);
	
	if (currentTopicSequence == 0 && index == 0){
		$.messager.alert("操作提示", "这已经第一题了。", "info");
		return;
	}
	if (currentTopicSequence == 0 && index > 0){
		$('#topicTabs').tabs('select',--index);
		
		if(index == 0){
			currentTopicSequence = selectTopic.length-1;
		}
		if(index == 1){
			currentTopicSequence = judgeTopic.length-1;
		}
		if(index == 2){
			currentTopicSequence = fillTopic.length-1;
		}
		innerHTML(currentTopicSequence);
		isContains(index, currentTopicSequence);
		return;
	}
	innerHTML(--currentTopicSequence);
	isContains(index, currentTopicSequence);
}

// 下一题
function next(){
	var flag = innerHTML(++currentTopicSequence);
	if (flag && flag == 4){
		$.messager.alert("操作提示", "这已经最后一题了。", "info");
		currentTopicSequence--;
		return;
	}
	if (flag && flag < 4){
		$('#topicTabs').tabs('select',flag);
	}
}

// 
function innerHTML(topicSequence){
	var tab = $('#topicTabs').tabs('getSelected');
	var index = $('#topicTabs').tabs('getTabIndex',tab);
	var topicType = index + 1;
	
	var topic;
	if (topicType == 1){
		toptic = selectTopic[topicSequence];
		if (toptic == undefined){
			return topicType;
		}
		var desc = topicSequence + 1 + "、" +toptic.topicDescription + "(" + toptic.topicScore + "分)";
		$("#select_topic_desc").html(desc);
		if (toptic.isSingleSelect){
			$("#multiple_select").hide();
			$("#single_select").show();
			$("#single_select_option_a").html(toptic.topicOptionA);
			$("#single_select_option_b").html(toptic.topicOptionB);
			$("#single_select_option_c").html(toptic.topicOptionC);
			$("#single_select_option_d").html(toptic.topicOptionD);
			$('input[type=radio][name="singleSelect"]:checked').attr("checked", false);
		} else {
			$("#single_select").hide();
			$("#multiple_select").show();
			$("#multiple_select_option_a").html(toptic.topicOptionA);
			$("#multiple_select_option_b").html(toptic.topicOptionB);
			$("#multiple_select_option_c").html(toptic.topicOptionC);
			$("#multiple_select_option_d").html(toptic.topicOptionD);
			$("input[type=checkbox][name='multipleSelect']").removeAttr("checked");
		}
		
		$("#totalTopic").html(selectTopic.length);
		$('input[type=radio][name="singleSelect"]:checked').attr("checked", false);
		isContains(index, topicSequence);
	}
	if (topicType == 2){
		toptic = judgeTopic[topicSequence];
		if (toptic == undefined){
			return topicType;
		}
		var desc = topicSequence + 1 + "、" +toptic.topicDescription + "(" + toptic.topicScore + "分)";
		$("#judge_topic_desc").html(desc);
		
		$("#totalTopic").html(judgeTopic.length);
		$('input[type=radio][name="judgeSelect"]:checked').attr("checked", false);
		isContains(index, topicSequence);
	}
	if (topicType == 3){
		toptic = fillTopic[topicSequence];
		if (toptic == undefined){
			return topicType;
		}
		var desc = topicSequence + 1 + "、" +toptic.topicDescription + "(" + toptic.topicScore + "分)";
		$("#fill_topic_desc").html(desc);
		
		$("#totalTopic").html(fillTopic.length);
		$('#fillAnswer').val("");
		isContains(index, topicSequence);
	}
	if (topicType == 4){
		toptic = answerTopic[topicSequence];
		if (toptic == undefined){
			return topicType;
		}
		var desc = topicSequence + 1 + "、" +toptic.topicDescription + "(" + toptic.topicScore + "分)";
		$("#answer_topic_desc").html(desc);
		
		$("#totalTopic").html(answerTopic.length);
		$('#answerAnswer').val("");
		isContains(index, topicSequence);
	}
	$("#currentTopic").html(topicSequence + 1);
	
}

var selectTopic;
var judgeTopic;
var fillTopic;
var answerTopic;
var totalTopic;
// 获取题目
function queryTopic(examId){
	var url = ctx + "/startExam/queryExamTopicList";
	var param = {
		"examId" : examId
	}

	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == undefined) {
			var ret = returnData.data;
			totalTopic = ret;
			selectTopic = new Array();
			judgeTopic = new Array();
			fillTopic = new Array();
			answerTopic = new Array();
			for(var i = 0; i < ret.length; i++){
				if(ret[i].topicType == 1) {
					selectTopic.push(ret[i]);
				}
				if(ret[i].topicType == 2) {
					judgeTopic.push(ret[i]);
				}
				if(ret[i].topicType == 3) {
					fillTopic.push(ret[i]);
				}
				if(ret[i].topicType == 4) {
					answerTopic.push(ret[i]);
				}
			}
			$("#DataGrid1").datagrid('loaded');
			
			$('#topicTabs').tabs({
				onSelect:function(title,index){
					index++;
					$("#topicContent" + index).show();
					$("#count").show();
					currentTopicSequence = 0;
					first();
				}
			})
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


