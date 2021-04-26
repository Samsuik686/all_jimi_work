var currentPageNum;
var currentExamineePageNum;
var currentUserName;
var currentTopicSequence = 0;
var currentExamId;

$(function() {
	queryListPage(1);
	
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
	$('#score').blur(function() {
		var tab = $('#topicTabs').tabs('getSelected');
		var index = $('#topicTabs').tabs('getTabIndex',tab);
		
		var toptic;
		if(index == 0){
			toptic = fillTopic[currentTopicSequence];
		}
		if(index == 1){
			toptic = answerTopic[currentTopicSequence];
		}
		var score = $('#score').val();
		if (score > toptic.topicScore){
			$.messager.alert("操作提示", "得分超出题目最大分值。", "info");
			$('#score').val(null);
			return;
		}
		hashMap.Set(toptic.answerId, score);
	});
	
	$('#maxScore').click(function() {
		var tab = $('#topicTabs').tabs('getSelected');
		var index = $('#topicTabs').tabs('getTabIndex',tab);
		
		var toptic;
		if(index == 0){
			toptic = fillTopic[currentTopicSequence];
		}
		if(index == 1){
			toptic = answerTopic[currentTopicSequence];
		}
		$('#score').val(toptic.topicScore);
		$('#score').blur();
	});
	
	$('#minScore').click(function() {
		$('#score').val(0);
		$('#score').blur();
	});
	
	
});

// 判断此题是否已打分
function isContains(index,currentTopicSequence){
	if(index == 0){
		var answerId = fillTopic[currentTopicSequence].answerId;
		if(hashMap.Contains(answerId)){
			var score = hashMap.Get(answerId);
			$('#score').val(score);
		} else {
			$('#score').val(null);
		}
	}
	if(index == 1){
		var answerId = answerTopic[currentTopicSequence].answerId;
		if(hashMap.Contains(answerId)){
			var score = hashMap.Get(answerId);
			$('#score').val(score);
		} else {
			$('#score').val(null);
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
	if (flag && flag >= tabIndex[tabIndex.length-1]+3){
		$.messager.alert("操作提示", "这已经最后一题了。", "info");
		currentTopicSequence--;
		return;
	}
	if (flag && flag < tabIndex[tabIndex.length-1]+3){
		tabSelect++;
		$('#topicTabs').tabs('select',tabIndex[tabSelect]);
	}
}

// 
function innerHTML(topicSequence){
	var tab = $('#topicTabs').tabs('getSelected');
	var index = $('#topicTabs').tabs('getTabIndex',tab);
	var topicType = index + 3;
	
	var topic;
	if (topicType == 3){
		toptic = fillTopic[topicSequence];
		if (toptic == undefined){
			return topicType;
		}
		var desc = topicSequence + 1 + "、" +toptic.topicDescription + "(" + toptic.topicScore + "分)";
		$("#fill_topic_desc").html(desc);
		
		$("#totalTopic").html(fillTopic.length);
		$('#fillAnswer').val(toptic.examineeAnswer);
		$('#fillReference').html(toptic.topicAnswer);
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
		$('#answerAnswer').val(toptic.examineeAnswer);
		$('#answerReference').html(toptic.topicAnswer);
		isContains(index, topicSequence);
	}
	$("#currentTopic").html(topicSequence + 1);
}

var fillTopic;
var answerTopic;
var totalTopic;
var tabIndex;
var tabSelect;
// 获取考生答案
function queryExamAnswer(examineeId, examId){
	var url = ctx + "/markScore/listExamAnswer";
	var param = {
		"examineeId" : examineeId,
		"examId" : examId
	}

	$("#DataGrid2").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == undefined) {
			var ret = returnData.data;
			totalTopic = ret;
			fillTopic = new Array();
			answerTopic = new Array();
			tabIndex = new Array();
			tabSelect = 0;
			for(var i = 0; i < ret.length; i++){
				if(ret[i].topicType == 3) {
					fillTopic.push(ret[i]);
				}
				if(ret[i].topicType == 4) {
					answerTopic.push(ret[i]);
				}
				if(ret[i].examineeScore != undefined){
					hashMap.Set(ret[i].answerId, ret[i].examineeScore);
				}
			}

			if(fillTopic.length < 1){
				$('#topicTabs').tabs('disableTab', 0);
			} else {
				tabIndex.push(0);
			}
			if(answerTopic.length < 1){
				$('#topicTabs').tabs('disableTab', 1);
			} else {
				tabIndex.push(1);
			}
			
			$("#DataGrid2").datagrid('loaded');
			
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
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 选择考生
function exam_chooseExaminee(examId) {
	windowVisible("exam_chooseExaminee");
	$('#exam_chooseExaminee').window('open');
	queryExamineeListPage(1, examId);
}

//开始批阅
function startMarkScore(examineeId, examId) {
	windowVisible("exam_markScore");
	$('#exam_markScore').window('open');
	queryExamAnswer(examineeId, examId);
}

// 获取考生列表
function queryExamineeListPage(pageNumber, examId) {
	if(examId == undefined){
		examId = currentExamId;
	}
	if(pageNumber == undefined){
		pageNumber = currentExamineePageNum;
	}
	currentExamId = examId;
	currentExamineePageNum = pageNumber;
	if (currentExamineePageNum == "" || currentExamineePageNum == null) {
		currentExamineePageNum = 1;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/markScore/queryExaminee";
	var param = {
		"examId" : examId,
		"currentpage" : currentExamineePageNum,
		"pageSize" : pageSize,
		"searchText" : $("#searchExaminee").val().trim()
	}

	$("#DataGrid2").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var ret = returnData.data;
		if(ret.length > 0) {
			$.each(ret, function(i, j) { 
				if(j.gradeState == 1) {
					j.stateText = "<strong style='color:green'>已评阅</strong>";
				}else {
					j.stateText = "<strong style='color:red'>未评阅</strong>";
				}
				
				j.preview = "<strong style=\"color: blue\" onclick=\"startMarkScore(\'"+ j.examineeId +"\',\'"+ examId +"\')\">试卷评阅</a>"
			})
		};
		
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#DataGrid2").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentExamineePageNum);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}

		$("#DataGrid2").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


// 初始化数据及查询函数
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/markScore/queryExam";
	var param = {
		"currentpage" : currentPageNum,
		"pageSize" : pageSize,
		"searchText" : $("#searchExam").val().trim()
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
				if(j.doneNum < j.totalNum){
					j.progressText = "<strong style='color:red'>" + j.doneNum + "/" + j.totalNum + "</strong>";
				}else{
					j.progressText = "<strong style='color:green'>" + j.doneNum + "/" + j.totalNum + "</strong>";
				}
				
				
				j.examTotalTime += "分钟";
				j.preview = "<strong style=\"color: blue\" onclick=\"exam_chooseExaminee(\'"+ j.examId +"\')\">选择考生</a>"
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

function saveScore(){
	for(var i=0; i<totalTopic.length; i++){
		var answerId = totalTopic[i].answerId;
		var score = hashMap.Get(answerId);
		if (!score){
			flag = false;
			$.messager.confirm("操作提示","存在未评分的题目，这些题目将自动评为0分，是否确认保存？",function(conf){
				if (conf){
					ScoreRequest();
				}
			});
			return;
		}
	}
	ScoreRequest();
}

function ScoreRequest(){
	var scoreList = new Array();
	for(var i=0; i<totalTopic.length; i++){
		var answerId = totalTopic[i].answerId;
		var score = hashMap.Get(answerId);
		var examAnswer = {
				"answerId" : answerId, 
				"examineeScore" : score?score:0
		};
		scoreList.push(examAnswer);
	}
	
	var url = ctx + "/markScore/updateExamAnswer";
	var param = {
		"answerJson" : JSON.stringify(scoreList),
		"examId" : currentExamId
	}
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == 0) {
			$.messager.alert("操作提示", "保存成功", "info");
			$('#exam_markScore').window('close');
			queryExamineeListPage(currentExamineePageNum, currentExamId)
		} 
		$("#DataGrid1").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function refreshExam(){
	queryListPage(currentPageNum);
}

function refreshExaminee(){
	queryExamineeListPage(currentExamineePageNum, currentExamId);
}
