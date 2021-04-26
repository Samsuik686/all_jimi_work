var currentPageNum;
var currentUserName;
var currentTopicSequence = 0;
var currentExamId;

$(function() {
	queryListPage(1);
	currentUserName = $("#currentName").val();
	
	$("#topicContent1").hide();
	$("#count").hide();
});

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
	
	$('input[type=checkbox][name=multipleSelect]').change(function() {
		var answer = ""; 
		$('input[type=checkbox][name=multipleSelect]:checked').each(function(){
			answer +=$(this).val();
		});
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
			if (selectTopic[currentTopicSequence].isSingleSelect){
				$('input[type=radio][name=singleSelect][value='+ answer +']').attr('checked','true');
			} else {
				var answerArr = answer.split("");
				for(var i=0; i < answerArr.length; i++){
					$('input[type=checkbox][name=multipleSelect][value='+ answerArr[i] +']').attr('checked','true');
				}
			}
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
//		$.messager.alert("操作提示", "没有该题型的题目。", "info");
		
		$("#topicContent" + flag).hide();
		$("#count").hide();
	}
}

// 上一题
function back(){
	var tab = $('#topicTabs').tabs('getSelected');
	var index = $('#topicTabs').tabs('getTabIndex',tab);
	
	if (currentTopicSequence == 0 && index == tabIndex[0]){
		$.messager.alert("操作提示", "这已经第一题了。", "info");
		return;
	}
	if (currentTopicSequence == 0 && index > tabIndex[0]){
		tabSelect--;
		$('#topicTabs').tabs('select', tabIndex[tabSelect]);
		if(tabIndex[tabSelect] == 0){
			currentTopicSequence = selectTopic.length-1;
		}
		if(tabIndex[tabSelect] == 1){
			currentTopicSequence = judgeTopic.length-1;
		}
		if(tabIndex[tabSelect] == 2){
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
	if (flag && flag >= tabIndex[tabIndex.length-1]+1){
		$.messager.alert("操作提示", "这已经最后一题了。", "info");
		currentTopicSequence--;
		return;
	}
	if (flag && flag < tabIndex[tabIndex.length-1]+1){
		tabSelect++;
		$('#topicTabs').tabs('select',tabIndex[tabSelect]);
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
var tabIndex;
var tabSelect;

// 获取题目
function queryTopic(examId, examTotalTime){
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
			tabIndex = new Array();
			tabSelect = 0;
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
			if(selectTopic.length < 1){
				$('#topicTabs').tabs('disableTab', 0);
			} else {
				tabIndex.push(0);
			}
			if(judgeTopic.length < 1){
				$('#topicTabs').tabs('disableTab', 1);
			} else {
				tabIndex.push(1);
			}
			if(fillTopic.length < 1){
				$('#topicTabs').tabs('disableTab', 2);
			} else {
				tabIndex.push(2);
			}
			if(answerTopic.length < 1){
				$('#topicTabs').tabs('disableTab', 3);
			} else {
				tabIndex.push(3);
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
			$('#topicTabs').tabs('select', tabIndex[tabSelect]);
			queryRestTime(examId, examTotalTime);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 开始答题
function exam_startAnswer(examId, examTotalTime) {
	var url = ctx + "/startExam/queryExamByExamId";
	var param = {
		"examId" : examId
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == undefined){
			currentExamId = examId;
			var ret = returnData.data;
			if(ret.state != 2) {
				$.messager.alert("操作提示", "此次考试状态以改变，不能进行答题。", "info");
				refreshInfo();
				return;
			}
			if(ret.answerStatus > 0) {
				$.messager.alert("操作提示", "您已经参与过此次考试了。", "info");
				return;
			}
			$.messager.confirm("操作提示","考试期间<strong style='color:red'>关闭答题界面将会丢失已完成的题目，考试时间不会暂停</strong>，时间用尽是将会自动提交试卷，确认开始答题吗？",function(conf){
				if (conf){
					queryTopic(examId, examTotalTime);
				}
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	});
}

// 查询考试剩余时间
function queryRestTime(examId, examTotalTime){
	var url = ctx + "/startExam/queryExamStartTime";
	var param = {
		"examId" : examId
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var startTime = returnData.data;
		var restTime;
		if (startTime != undefined){
			var currentTime = (new Date()).getTime();
			var useTime = Math.ceil((currentTime - startTime)/1000);
			restTime = examTotalTime * 60 - useTime;
			// 时间用尽，则自动提交
			if (restTime < 0){
				autoSaveAnswer();
				return;
			}
		} else {
			restTime = examTotalTime * 60;
		}
		windowVisible("exam_startAnswer");
		$('#exam_startAnswer').window('open');
		
		$('#examTime').timeTo(restTime, function(){
			autoSaveAnswer();
		}); 
	});
}

// 初始化数据及查询函数
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/startExam/queryExam";
	var param = {
		"currentpage" : currentPageNum,
		"pageSize" : pageSize,
		"searchText" : $('#searchExam').val().trim()
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
				
				if(j.answerStatus == 0){
					j.answerStatusText = "<strong style='color:red'>未参与</strong>";
				}
				if(j.answerStatus > 0){
					j.answerStatusText = "<strong style='color:green'>已参与</strong>";
				}
				
				j.examTotalTimeText = j.examTotalTime + "分钟";
				j.preview = "<strong style=\"color: blue\" onclick=\"exam_startAnswer(\'"+ j.examId + "\',\'"+ j.examTotalTime+"\')\">开始答题</a>"
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

function saveAnswer(){
	$.messager.confirm("操作提示","提交试卷后答案将<strong style='color:red'>不可修改</strong>，且无法再次进行答题，是否确认提交？",function(conf){
		if (conf){
			var answerList = new Array();
			for(var i=0; i<totalTopic.length; i++){
				var topicId = totalTopic[i].topicId;
				var answer = hashMap.Get(topicId);
				var examAnswer = {
						"topicId" : topicId?topicId:null, 
						"examineeAnswer" : answer?answer:null
				};
				answerList.push(examAnswer);
			}
			$("#DataGrid1").datagrid('loading');
			var url = ctx + "/startExam/saveExamAnswer";
			var param = {
				"answerJson" : JSON.stringify(answerList),
				"examId" : currentExamId
			}
			asyncCallService(url, 'post', false, 'json', param, function(returnData) {
				if (returnData.code == 0) {
					$.messager.alert("操作提示", "保存成功", "info");
					$('#exam_startAnswer').window('close');
					queryListPage(currentPageNum);
				} 
				$("#DataGrid1").datagrid('loading');
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
		}
	});
}

function autoSaveAnswer(){
	var answerList = new Array();
	for(var i=0; i<totalTopic.length; i++){
		var topicId = totalTopic[i].topicId;
		var answer = hashMap.Get(topicId);
		var examAnswer = {
				"topicId" : topicId?topicId:null, 
				"examineeAnswer" : answer?answer:null
		};
		answerList.push(examAnswer);
	}
	
	var url = ctx + "/startExam/saveExamAnswer";
	var param = {
		"answerJson" : JSON.stringify(answerList),
		"examId" : currentExamId
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == 0) {
			$.messager.alert("操作提示", "您的考试时间已用尽，系统已经为您自动保存并提交。", "info");
			$('#exam_startAnswer').window('close');
			queryListPage(currentPageNum);
		} 
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function refreshInfo(){
	queryListPage(currentPageNum);
}

