
var currentPageNum;
var sendIds = "";
var fileId = ["fileName1"];
var fileIndex = 1;
var initFileSpanHtml = '';
var downloadFileHtml = '';
var adminFlag = false;
var dataStatus;

var stationMenu = new Array();
stationMenu.push(
	{"gid":11435,"qname":"客服"},
	{"gid":11441,"qname":"维修"},
	{"gid":11436,"qname":"终端"},
	{"gid":11437,"qname":"平台"},
	{"gid":11438,"qname":"品质"},
	{"gid":11442,"qname":"项目"},
	{"gid":11439,"qname":"产品"},
	{"gid":11443,"qname":"几米"},
	{"gid":11458,"qname":"营销"},
	{"gid":11460,"qname":"研发"},
	{"gid":11462,"qname":"物联网卡"},
	{"gid":11463,"qname":"SIM卡平台"},
	{"gid":11461,"qname":"测试"},
)

var type = getUrlParameter("ycfkType");
$(function() {
	datagridInit();
	keySearch(queryListPage);
	getCurrentUserRole();
	
	$("input[name='completionState']").click(function(){
		iscomplete();
	});
	
    $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	});
    
    $("#next_site").change(function () {
    	var val = $("#next_site option:selected").val();
    	 // sendDataToNextSite(val);
		//跟进人需要支持多选
		var site = getMenuId(val);
		if(site != null)
			initFollowPeople(site);
    });

	// 选择抄送人
	$("#zzgz_DataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseZZGZ(index, value);
		}
	});

	$("#followup_user").combobox ({
		editable : false,
		valueField : "uName", //相当于 option 中的 value 发送到后台的
		textField : "uName",//option中间的内容 显示给用户看的
		data:[],
		multiple:true,//设置可以多选
	});
	treeLoadAgin_ZZGZ();
});


/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		onDblClickRow:function(rowIndex,row){
			ycfkTwo_updateInfo(rowIndex,row);
		}
	});
	queryListPage(1);
}


/**
 * 判断解决状态
 */
function iscomplete(){
	var completionState = $("input[name='completionState']:checked").val();
	if(completionState == "1"){
		if($("input[name='checkResult']:checked").val() != '0'){
			if(!$("#completionDatei").val()){
				$("#completionDatei").val(initTime())
			}
			$("#ycfkAddOrUPdate").linkbutton('enable');
		}else{
			$.messager.alert("操作提示", "请先选择验证结果", "info",function(){
				$("#ycfkAddOrUPdate").linkbutton('disable');
				$("input[name='completionState'][value='2']").prop('checked', true);//处理中
				$("#ycfkAddOrUPdate").linkbutton('enable');
			});
		}
	}else{
		$("#ycfkAddOrUPdate").linkbutton('enable');
		$("#completionDatei").val("");
	}
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
	var url = ctx + "/ycfkTwomanage/ycfkTwomanageList";
	var param = {
		"feedBackPerson" : $.trim($("#feedBackPerson_search").val()),
		"model" : $.trim($("#model_search").val()),
		"recipient" : $("#recipient_search").val(),
		"startTime" : $("#startTime").val(),
		"endTime" : $("#endTime").val(),
		"treeDate" :$("#tree-Date").val(),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize,
		"customerName":$("#customerName").val(),
		"phone":$("#phone").val()
	}
	if(!type){
		param['ycfk_currentSite'] = $("#ycfk_currentSite_search").combobox('getValue');
		param['followupPeople'] = $.trim($("#followupPeople_search").val());
		param['ycfk_completionState'] = $("#ycfk_completionState").combobox('getValue');
	}else{
		if (type == 'customer') {
			param['currentSite'] = "customer";
		} else if (type == 'termail') {
			param['currentSite'] = "termail";
		} else if (type == 'platform') {
			param['currentSite'] = "platform";
		} else if (type == 'quality') {
			param['currentSite'] = "quality";
		} else if (type == 'product') {
			param['currentSite'] = "product";
		} else if (type == 'repair') {
			param['currentSite'] = "repair";
		} else if (type == 'project') {
			param['currentSite'] = "project";
		} else if (type == 'jimi') {
			param['currentSite'] = "jimi";
		} else if (type == 'market') {
			param['currentSite'] = "market";
		} else if (type == 'research') {
			param['currentSite'] = "research";
		} else if (type == 'test') {
			param['currentSite'] = "test";
		} else if (type == 'iot') {
			param['currentSite'] = "iot";
		} else if (type == 'sim') {
			param['currentSite'] = "sim";
		}
		param['completionState'] = $("#completionState").combobox('getValue');
	}
	
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		
		var ret = returnData.data;
		if (ret.length > 0) {
			$.each(ret, function(i, j) {
				if (j.warranty == '0') {
					j.warranty = '保内';
				} else if (j.warranty == '1') {
					j.warranty = '保外';
				}
				if(j.measures.length > 17){
					j.measures = j.measures.substring(0,17) + "...";
				}
				
				if (type == 'customer') {
					if (j.customerStatus == '0') {
						j.customerStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.customerStatus == '1') {
						j.customerStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'termail') {
					if (j.termailStatus == '0') {
						j.termailStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.termailStatus == '1') {
						j.termailStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'platform') {
					if (j.platformStatus == '0') {
						j.platformStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.platformStatus == '1') {
						j.platformStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'quality') {
					if (j.qualityStatus == '0') {
						j.qualityStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.qualityStatus == '1') {
						j.qualityStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'product') {
					if (j.productStatus == '0') {
						j.productStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.productStatus == '1') {
						j.productStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'repair') {
					if (j.repairStatus == '0') {
						j.repairStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.repairStatus == '1') {
						j.repairStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'project') {
					if (j.projectStatus == '0') {
						j.projectStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.projectStatus == '1') {
						j.projectStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'jimi') {
					if (j.jimiStatus == '0') {
						j.jimiStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.jimiStatus == '1') {
						j.jimiStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'market') {
					if (j.marketStatus == '0') {
						j.marketStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.marketStatus == '1') {
						j.marketStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'research') {
					if (j.researchStatus == '0') {
						j.researchStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.researchStatus == '1') {
						j.researchStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'test') {
					if (j.testStatus == '0') {
						j.testStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.testStatus == '1') {
						j.testStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'iot') {
					if (j.iotStatus == '0') {
						j.iotStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.iotStatus == '1') {
						j.iotStatus = "<strong style='color:green'>已完成</strong>";
					}
				} else if (type == 'sim') {
					if (j.simStatus == '0') {
						j.simStatus = "<strong style='color:red'>待解决</strong>";
					} else if (j.simStatus == '1') {
						j.simStatus = "<strong style='color:green'>已完成</strong>";
					}
				}
				//距受理时间1天，受理时间显示黄色，两天，红色
				var t =	getDayFromAcceptanceTime(j.feedBackDate);
				if(t == 1){
					
					j.feedBackDate = "<label style='background-color:#FFEC8B;'>" + j.feedBackDate + "</label>";
				}else if(t == 2){
					j.feedBackDate = "<label style='background-color:#FF6347;'>" + j.feedBackDate + "</label>";
				}
				
				if (j.completionState == '0') {
					j.completionState = "<strong style='color:red'>待解决</strong>";
				} else if (j.completionState == '1') {
					j.completionState = "<strong style='color:green'>已完成</strong>";
				} else if (j.completionState == '2') {
					j.completionState = "<strong style='color:blue'>处理中</strong>";
				}
				if (j.levelFlag == '0') {
					j.levelFlag = "一般";
				} else if (j.levelFlag == '1') {
					j.levelFlag = "<strong style='color:blue'>较紧急</strong>";
				} else if (j.levelFlag == '2') {
					j.levelFlag = "<strong style='color:red'>紧急</strong>";
				}

				if (j.severityFlag == '0') {
					j.severityFlag = "一般问题";
				} else if (j.severityFlag == '1') {
					j.severityFlag = "<strong style='color:#ff4500'>重要问题</strong>";
				} else if (j.severityFlag == '2') {
					j.severityFlag = "<strong style='color:red'>严重问题</strong>";
				}else if(j.severityFlag == '3'){
					j.severityFlag = "<strong style='color:#8b0000'>重大问题</strong>"
				}

				if (j.checkResult == '0') {
					j.checkResult = "";
				} else if (j.checkResult == '1') {
					j.checkResult = "<strong style='color:red'>不合格</strong>";
				} else if (j.checkResult == '2') {
					j.checkResult = "<strong style='color:green'>合格</strong>";
				} else if (j.checkResult == '3') {
					j.checkResult = "<strong style='color:blue'>有条件通过</strong>";
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
	$("#backIdi").val("");
	$("#yidi").val(0);
	$("#modeli").val("");
	$("#problemsi").val("");
	$("#imeii").val("");
	$("input[name='warrantyi']").prop('checked', false);
	$("input[name='completionState'][value='0']").prop('checked', true);
	$("input[name='levelFlag'][value='1']").prop('checked', true);
	$("input[name='severityFlag'][value='1']").prop('checked', true);
	$("input[name='checkResult'][value='0']").prop('checked', true);
	$("#checker").val("");
	$("#analysis").val("");
	$("#strategy").val("");
	$("#descriptioni").val("");
	$("#numberi").val("");
	$("#feedBackPersoni").val("");
	$("#feedBackDatei").val(initTime());
	$("#responsibilityUniti").val("");
	$("#measuresi").val("");
	$("#hideMethod").val("");
	$("#completionDatei").val("");
	$("#followupPeoplei").val("");
	$("#customerNamei").val("");
	$("#phonei").val("");
	$("#remaki").val("");
	$("#recipienti").val("");
	$("#measuresiTab").hide();
	$("#mform :input").attr("disabled", false);
	$("#ycfkAddOrUPdate").linkbutton('enable');
	$("#checker").val("");
	$("#repair_w_zzgzDesc").val("");
}

//新增异常管理信息
function ycfkTwomanageSave() {
	var feedBackDate = $("#feedBackDatei").val();
	if (!feedBackDate) {
		$("#feedBackDatei").val(initTime());
	}
	if ($("input[name='checkResult']:checked").val() != 0){//验收结果不为0，验收人即为反馈人
		$("#checker").text($("#feedBackPersoni").val());
		$("#checker").val($("#feedBackPersoni").val());
	}
	feedBackDate = $("#feedBackDatei").val();
	
	if($("input[name='checkResult']:checked").val() != "0"){
		if($("input[name='completionState']:checked").val() != "1"){
			$.messager.alert("操作提示", "未完成数据不能选择验收结果", "info", function(){
				$("input[name='checkResult'][value='0']").prop('checked', true);
			});
			return;
		}
	}
	
	var isValid = $('#mform').form('validate');
	if (isValid) {
		$("#ycfkAddOrUPdate").linkbutton('disable');//点击验证后禁用按钮,防止重复提交
		var warrantyi = $("input[name='warrantyi']:checked").val();
		if(undefined == warrantyi || null == warrantyi){
			warrantyi = "";
		}
		var reqAjaxPrams = {
				"backId" : $("#backIdi").val(),
				"model" : $.trim($("#modeli").val()),
				"imei" : $.trim($("#imeii").val()),
				"warranty" : warrantyi, 
				"completionState" : $("input[name='completionState']:checked").val(),
				"levelFlag" : $("input[name='levelFlag']:checked").val(),
				"checkResult" : $("input[name='checkResult']:checked").val(),
				"checker" : $("#checker").val(),
				"description" : $.trim($("#descriptioni").val()),
				"number" : $("#numberi").val(), 
				"feedBackPerson" : $("#feedBackPersoni").val(),
				"feedBackTime" : feedBackDate,
				"recipient" : $("#recipienti").val(),
				"responsibilityUnit" : $.trim($("#responsibilityUniti").val()),
				"measures" : $.trim($("#measuresi").val()),
				"hideMethod" : $("#hideMethod").val(),
				"completionTime" : $("#completionDatei").val(),
				"followupPeople" : $("#followupPeoplei").val(),
				"customerName" : $.trim($("#customerNamei").val()), 
				"phone" : $.trim($("#phonei").val()),
				"remak" : $.trim($("#remaki").val()),				
				"yid" : $("#yidi").val(),
				"severityFlag":$("input[name='severityFlag']:checked").val(),
				"analysis":$.trim($("#analysis").val()),
				"strategy":$.trim($("#strategy").val()),
				"copyPerson":$("#repair_w_zzgzDesc").val()
		}
		if (type == 'customer') {
			reqAjaxPrams["customerStatus"] = 0;
			reqAjaxPrams["currentSite"] = "customer";
		} else if (type == 'termail') {
			reqAjaxPrams["termailStatus"] = 0;
			reqAjaxPrams["currentSite"] = "termail";
		} else if (type == 'platform') {
			reqAjaxPrams["platformStatus"] = 0;
			reqAjaxPrams["currentSite"] = "platform";
		} else if (type == 'repair') {
			reqAjaxPrams["repairStatus"] = 0;
			reqAjaxPrams["currentSite"] = "repair";
		} else if (type == 'quality') {			
			reqAjaxPrams["currentSite"] = "quality";
		} else if (type == 'product') {
			reqAjaxPrams["currentSite"] = "product";
		} else if (type == 'project') {
			reqAjaxPrams["currentSite"] = "project";
		} else if (type == 'jimi') {
			reqAjaxPrams["currentSite"] = "jimi";
		} else if (type == 'market') {
			reqAjaxPrams["currentSite"] = "market";
		} else if (type == 'research') {
			reqAjaxPrams["currentSite"] = "research";
		} else if (type == 'test') {
			reqAjaxPrams["currentSite"] = "test";
		} else if (type == 'iot') {
			reqAjaxPrams["currentSite"] = "iot";
		} else if (type == 'sim') {
			reqAjaxPrams["currentSite"] = "sim";
		}
		processSaveAjax(reqAjaxPrams);
	}
}
//是否同步刷新
function processSaveAjax(reqAjaxPrams) {
	var url = ctx + "/ycfkTwomanage/addOrUpdateYcfkTwomanage";
	var id = $("#backIdi").val();
	
	$.ajaxFileUpload({
        url:url, 
        type: 'post',
        data: reqAjaxPrams,//此参数非常严谨，写错一个引号都不行
        secureuri: false, //是否启用安全提交，一般设置为false
        fileElementId: fileId, //文件上传空间的id属性  
        //dataType: 'json', //返回值类型 一般设置为json，不设置JQ会自动判断
        success: function (data){  //服务器成功响应处理函数
        	windowCloseYcfkTwomanage();
			if (id > 0) {
				queryListPage(currentPageNum);
			} else {
				queryListPage(1);
			}
			$.messager.alert("操作提示", "操作成功", "info");
        },
        error: function (data, status, e){//服务器响应失败处理函数
        	$.messager.alert("操作提示", "网络错误", "info");
        }
    });
	
}
//删除异常反馈信息
function ycfkTwo_deleteInfo(flag) {
	var row = $('#DataGrid1').datagrid('getSelections');
	if (row.length > 0) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var ids="";
				for (var i = 0; i < row.length; i++) {
					var siteStatus = row[i].customerStatus;
					if (type == 'customer') {
						siteStatus = row[i].customerStatus;
					} else if (type == 'termail') {
						siteStatus = row[i].termailStatus;
					} else if (type == 'platform') {
						siteStatus = row[i].platformStatus;
					} else if (type == 'repair') {
						siteStatus = row[i].repairStatus;
					}
					
					if (flag && flag =='admin') {
						(i == 0) ? ids = row[i].backId : ids = row[i].backId + "," + ids;
					} else {							
						if (siteStatus.indexOf("待解决") > -1 && row[i].feedBackPerson == $("#feedbackPersonSpan").html()) {
							(i == 0) ? ids = row[i].backId : ids = row[i].backId + "," + ids;
						} else {
							$.messager.alert("操作提示", "只能删除<font color='red'>反馈人是自己的待解决</font>数据", "info");
							return;
						}		
					}					
				}
				if (ids) {
					var url = ctx + "/ycfkTwomanage/deleteYcfkTwomanage";
					var param = "ids=" + ids;
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
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

/**
 * 获取当前登录人角色
 */
function  getCurrentUserRole(){
	var url=ctx+"/rolePrivilege/getCurrentUserRoleId";
	asyncCallService(url, 'post', false,'json', '', function(returnData){		
		if(returnData.code==undefined){
			var getRoleIdListData=returnData.data;
			if(getRoleIdListData.indexOf('25') > -1 || getRoleIdListData.indexOf('96') > -1){
				$("#delBut").show();
				$("#divDelBut").css('display','block');
				adminFlag = true;
			}else{
				$("#delBut").hide();
				$("#divDelBut").css('display','none');				
			}
			
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
			
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
	
}


//弹出新增框
function ycfkTwo_addInfo() {
	clearFromParams();
	windowOpenYcfkTwomanage();
	$("#feedBackPersoni").val($("#feedbackPersonSpan").html());
	$('#ycfk_w').panel({title: "新增异常反馈信息"});
	chageWinSize("ycfk_w");
	$("#ycfkAddOrUPdate").linkbutton('enable');
	initFileSpanHtml = '<input id="fileName1" name="files" type="file" style="width:350px;">'+
						'<button name="fileADD"  type="button" onclick="addYcfkFile()">+</button>';
	$("#fileDiv").html(initFileSpanHtml);
	fileIndex = 1;
	$("#descriptionFileDiv").html('');
	
}
//弹出修改框
function ycfkTwo_updateInfo(index, entity) {
	if(entity){		
		addEntityValue(entity);		
	} else {
		var updated = $('#DataGrid1').datagrid('getSelections');
		if (updated.length == 1) {
			addEntityValue(updated[0])
		} else if (updated.length > 1) {
			$.messager.alert("提示信息", "请选择一行修改！", "info");
		} else {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		}
	}
	
}

function addEntityValue(entity){
	initFileSpanHtml = '<input id="fileName1"  name="files"   type="file" style="width:350px;">'+
						'<button name="fileADD"  type="button" onclick="addYcfkFile()">+</button>';
	$("#fileDiv").html(initFileSpanHtml);
	fileIndex = 1;
	
	var siteStatus = "";
	if (type == 'customer') {
		siteStatus = entity.customerStatus;
	} else if (type == 'termail') {
		siteStatus = entity.termailStatus;
	} else if (type == 'platform') {
		siteStatus = entity.platformStatus;
	} else if (type == 'quality') {
		siteStatus = entity.qualityStatus;
	} else if (type == 'product') {
		siteStatus = entity.productStatus;
	} else if (type == 'repair') {
		siteStatus = entity.repairStatus;
	} else if (type == 'project') {
		siteStatus = entity.projectStatus;
	} else if (type == 'jimi') {
		siteStatus = entity.jimiStatus;
	}  else if (type == 'market') {
		siteStatus = entity.marketStatus;
	} else if (type == 'research') {
		siteStatus = entity.researchStatus;
	} else if (type == 'test') {
		siteStatus = entity.testStatus;
	}  else if (type == 'iot') {
		siteStatus = entity.iotStatus;
	}  else if (type == 'sim') {
		siteStatus = entity.simStatus;
	}
	
	if (siteStatus) {
		if (siteStatus.indexOf("已完成") > -1 ) { 
			$("#mform :input").attr("disabled", true);//已完成数据不能操作
			$("#ycfkAddOrUPdate").linkbutton('disable');
			dataStatus = -1;
		} else {
			$("#mform :input").attr("disabled", false);
			$("#ycfkAddOrUPdate").linkbutton('enable');
			dataStatus = 0;
		}
		if((siteStatus.indexOf("待解决") > -1 || siteStatus.indexOf("处理中") > -1) && entity.feedBackPerson == $("#feedbackPersonSpan").html()){//反馈人才能点击完成和验收
			$(".feedbackerShow").attr("disabled", false);
		}else{
			$(".feedbackerShow").attr("disabled", "disabled");
		}
	} else {
		// 异常反馈列表查看信息
		$("#ycform :input").attr("disabled", true);
	}
	
	
//	$('#measuresi').validatebox({required:false});
	var getCurrentId = entity.backId;
	var url = ctx + "/ycfkTwomanage/selectYcfkTwomanage";
	var param = "backId=" + getCurrentId;
	$("#descriptionFileDiv").html('');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var entity = returnData.data.data;
		// TODO 数据展示
		if (entity.ycfkFileList) {
			downloadFileHtml = '';
			$.each(entity.ycfkFileList, function(index, fileData){
				var fileUrl = encodeURIComponent(fileData.fileUrl);
				var appendHtml = '<span><a  href="javascript:;" onclick="downLoadFile(\'' + fileUrl + '\', \''+fileData.fileName+'\')">' + fileData.fileName + '</a><button id=' + fileData.fid + ' class="panel-tool-close" type="button" onclick="deleteFile(\'' + fileData.fid + '\', \'' + fileData.createUser + '\')" ></button>[附件上传人：'+fileData.createUser +'|'+ fileData.createTime+']<br/></span>';		
				downloadFileHtml += appendHtml;
			});
			
			$("#descriptionFileDiv").html(downloadFileHtml);
		}
		
		$("#backIdi").val(entity.backId);
		$("#yidi").val(entity.yid);
		$("#modeli").val(entity.model);
		$("#imeii").val(entity.imei);
		if (entity.warranty == 0) {
			$("input[name='warrantyi'][value='0']").prop("checked", true);
		} else if (entity.warranty == 1){
			$("input[name='warrantyi'][value='1']").prop("checked", true);
		}
		if (entity.completionState == 0) {
			$("input[name='completionState'][value='0']").prop("checked", true);
		} else if (entity.completionState == 1){
			$("input[name='completionState'][value='1']").prop("checked", true);
		} else if (entity.completionState == 2){
			$("input[name='completionState'][value='2']").prop("checked", true);
		}
		if (entity.levelFlag == 0) {
			$("input[name='levelFlag'][value='0']").prop("checked", true);
		} else if (entity.levelFlag == 1){
			$("input[name='levelFlag'][value='1']").prop("checked", true);
		} else if (entity.levelFlag == 2){
			$("input[name='levelFlag'][value='2']").prop("checked", true);
		}

		if (entity.severityFlag == 0) {
			$("input[name='severityFlag'][value='0']").prop("checked", true);
		} else if (entity.severityFlag == 1){
			$("input[name='severityFlag'][value='1']").prop("checked", true);
		} else if (entity.severityFlag == 2){
			$("input[name='severityFlag'][value='2']").prop("checked", true);
		}else if(entity.severityFlag == 3){
			$("input[name='severityFlag'][value='3']").prop("checked", true);
		}

		if (entity.checkResult == 0) {
			$("input[name='checkResult'][value='0']").prop("checked", true);
		} else if (entity.checkResult == 1){
			$("input[name='checkResult'][value='1']").prop("checked", true);
		} else if (entity.checkResult == 2){
			$("input[name='checkResult'][value='2']").prop("checked", true);
		} else if (entity.checkResult == 3){
			$("input[name='checkResult'][value='3']").prop("checked", true);
		}
		$("#repair_w_zzgzDesc").val(entity.copyPerson);
		$("#analysis").val(entity.analysis);
		$("#strategy").val(entity.strategy);
		$("#descriptioni").val(entity.description);
		$("#numberi").val(entity.number);
		$("#feedBackPersoni").val(entity.feedBackPerson);
		$("#feedBackDatei").val(entity.feedBackDate);
		var feedBackDate = $("#feedBackDatei").val();
		if (!feedBackDate) {
			$("#feedBackDatei").val(initTime());
		}
		feedBackDate = $("#feedBackDatei").val();
		$("#recipienti").val(entity.recipient);
		$("#responsibilityUniti").val(entity.responsibilityUnit);
		$("#measuresi").val("");
		$("#hideMethod").val(entity.hideMethod);
		$("#completionDatei").val(entity.completionDate);
		$("#followupPeoplei").val(entity.followupPeople);
		$("#customerNamei").val(entity.customerName);
		$("#phonei").val(entity.phone);
		$("#remaki").val(entity.remak);
		$("#checker").val(entity.checker);
		$("#measuresiTab").show();
		$("#measuresiTxt").html(entity.measures);
		$('#ycfk_w').panel({title: "编辑异常反馈信息"});
		windowOpenYcfkTwomanage();
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 删除附件后刷新附件显示
function getFileByYid () {
	var url = ctx + "/ycfkTwomanage/getFileByYid";
	var param = "yid=" + $("#backIdi").val();
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var entity = returnData.data;
		// TODO 数据展示
		if (entity) {
			downloadFileHtml = '';
			$.each(entity, function(index, fileData){
				var fileUrl = encodeURIComponent(fileData.fileUrl);
				var appendHtml = '<span><a  href="javascript:;" onclick="downLoadFile(\'' + fileUrl + '\', \''+fileData.fileName+'\')">' + fileData.fileName + '</a><button id=' + fileData.fid + ' class="panel-tool-close" type="button" onclick="deleteFile(\'' + fileData.fid + '\', \'' + fileData.createUser + '\')" ></button>[附件上传人：'+fileData.createUser +'|'+ fileData.createTime+']<br/></span>';		
				downloadFileHtml += appendHtml;
			});
			
			$("#descriptionFileDiv").html(downloadFileHtml);
		}
	});

}
//删除附件
function deleteFile(fid, createUser){
	if (dataStatus == 0) {
		if (fid) {
			// 附件上传人才可以删除附件,
			adminFlag = false;
			var currentUserName =  $("#userName").html();		
			 getCurrentUserRole();	
			 setTimeout(function(){
				 adminFlag;
				 if (createUser ==  currentUserName || adminFlag){
						$.messager.confirm("操作提示", "是否删除此附件？", function(conf) {
							if (conf) {
								var url = ctx+"/ycfkTwomanage/deleteFileByFid";
								var param = {
										"fid" : fid
								}		
								asyncCallService(url, 'post', true,'json', param, function(returnData){
									if(returnData.code=='0'){
										// 刷新当前页面
										getFileByYid();
										$.messager.alert("操作提示","删除成功！","info");
									}else{
										$.messager.alert("操作提示","操作异常！","info");
									}
								});
							}
						});	
					} else {
						$.messager.alert("提示信息","只有管理员和附件上传人才可以删除此附件！","info");
					}	
				},500);
		} else {
			$.messager.alert("提示信息","未匹配到文件ID！","info");
		}
	} else {
		$.messager.alert("提示信息","已完成数据禁止删除附件！","info");
	}
	
}

// 下载附件
function downLoadFile (fileUrl, fileName) {
	var url =  ctx + "/ycfkTwomanage/downLoadFile";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='fileUrl' value='" + fileUrl +"'/>"));
	$form1.append($("<input type='hidden' name='fileName' value='" + fileName +"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}


// 发送数据到下一个工站
function sendDataToNextSiteOpen () {
	sendIds = "";
	$('#followup_user').combobox('clear');
	windowVisible("nextSiteUser_w");
	$("#next_site").prop("selectedIndex",0).change();
	$("#followup_user").prop("selectedIndex",0).change();
	var sendInfos = $('#DataGrid1').datagrid('getSelections');
	if (sendInfos.length > 0) {
		for (var i = 0; i < sendInfos.length; i++) {
			var siteStatus =sendInfos[i].customerStatus;
			if (type == 'customer') {
				siteStatus =sendInfos[i].customerStatus;
			} else if (type == 'termail') {
				siteStatus =sendInfos[i].termailStatus;
			} else if (type == 'platform') {
				siteStatus =sendInfos[i].platformStatus;
			} else if (type == 'quality') {
				siteStatus = sendInfos[i].qualityStatus;
			} else if (type == 'product') {
				siteStatus = sendInfos[i].productStatus;
			} else if (type == 'repair') {
				siteStatus =sendInfos[i].repairStatus;
			} else if (type == 'project') {
				siteStatus = sendInfos[i].projectStatus;
			} else if (type == 'jimi') {
				siteStatus = sendInfos[i].jimiStatus;
			} else if (type == 'market') {
				siteStatus = sendInfos[i].marketStatus;
			} else if (type == 'research') {
				siteStatus = sendInfos[i].researchStatus;
			} else if (type == 'test') {
				siteStatus = sendInfos[i].testStatus;
			} else if (type == 'iot') {
				siteStatus = sendInfos[i].iotStatus;
			} else if (type == 'sim') {
				siteStatus = sendInfos[i].simStatus;
			}
			
			
			if (siteStatus.indexOf("待解决") > -1 ) { // 待解决 的数据
				(i == 0) ? sendIds = sendInfos[i].backId : sendIds = sendInfos[i].backId + "," + sendIds;
			} else {
				$.messager.alert("操作提示", "只能发送待解决数据到下一工站", "info");
				return;
			}
		}		
		$('#nextSiteUser_w').window('open');		
	} else {
		$.messager.alert("操作提示", "请选择要发送的数据", "info");
	}
}

function ycfkSendData () {
	 var followUser = $("#followup_user").combobox('getValues');
	 var nextSite = $("#next_site option:selected").val();
	 if (!nextSite) {
		 $.messager.alert("操作提示", "请选择下一工站", "info");
		 return;
	 }
	 if (!followUser) {
		 $.messager.alert("操作提示", "请选择下一工站跟进人", "info");
		 return;
	 } 
	var sendInfos = $('#DataGrid1').datagrid('getSelections');
	 var users="";
	 for(let i = 0;i<followUser.length;i++){
	 	users = users + followUser[i]+',';
	 }
	 users =users.substr(0,users.length-1)
	if (sendInfos.length > 0) {
			$.messager.confirm("操作提示", "确定发送选中数据到下一工站", function (conf){
				 if (conf) {
					 if (sendIds) {
						 var url = ctx + "/ycfkTwomanage/sendDataToNextSite";											
						 var param = {
								 "ids" : sendIds,
								 "nextSite" : nextSite,
								 "followupPeople" : users
						 }
						 if (type == 'customer') {
							 	param["currentSite"] = "customer";
						} else if (type == 'termail') {
							 param["currentSite"] = "termail";
						} else if (type == 'platform') {
							 param["currentSite"] = "platform";
						} else if (type == 'quality') {
							 param["currentSite"] = "quality";
						} else if (type == 'product') {
							 param["currentSite"] = "product";
						} else if (type == 'repair') {
							 param["currentSite"] = "repair";
						} else if (type == 'project') {
							 param["currentSite"] = "project";
						} else if (type == 'jimi') {
							 param["currentSite"] = "jimi";
						} else if (type == 'market') {
							 param["currentSite"] = "market";
						} else if (type == 'research') {
							 param["currentSite"] = "research";
						} else if (type == 'test') {
							 param["currentSite"] = "test";
						} else if (type == 'iot') {
							 param["currentSite"] = "iot";
						} else if (type == 'sim') {
							 param["currentSite"] = "sim";
						}
						 
						 asyncCallService(url, 'post', false,'json', param, function(returnData){
							 processSSOOrPrivilege(returnData);
								if(returnData.code=='0'){
									$.messager.alert("操作提示","数据发送成功！","info",function(){
										windowCommClose('nextSiteUser_w');
										queryListPage(currentPageNum);    
									});
								}else{
									$.messager.alert("操作提示",returnData.msg,"info");
								}
						 },function(){
							 $.messager.alert("操作提示","网络错误","info");
						 });
					 } else {
						 $.messager.alert("提示信息", "未找到匹配数据", "info");
					 }
				 }
			});
	} else {
		$.messager.alert("操作提示", "请选择要发送的数据", "info");
	}
	
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/ycfkTwomanage/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='feedBackPerson' value='" + $.trim($("#feedBackPerson_search").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='model' value='" + $.trim($("#model_search").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='startTime' value='" + $("#startTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='endTime' value='" + $("#endTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='treeDate' value='" + $.trim($("#tree-Date").val())+"'/>"));	
	if(!type){
		$form1.append($("<input type='hidden' name='ycfk_currentSite' value='" + $("#ycfk_currentSite_search").combobox('getValue') + "'/>"));
		$form1.append($("<input type='hidden' name='followupPeople' value='" + $.trim($("#followupPeople_search").val()) +"'/>"));
		$form1.append($("<input type='hidden' name='ycfk_completionState' value='" + $("#ycfk_completionState").combobox('getValue') +"'/>")); 
	}else{
		if (type == 'customer') {
			$form1.append($("<input type='hidden' name='currentSite' value='customer'/>"));
		} else if (type == 'termail') {
			$form1.append($("<input type='hidden' name='currentSite' value='termail'/>"));
		} else if (type == 'platform') {
			$form1.append($("<input type='hidden' name='currentSite' value='platform'/>"));
		} else if (type == 'quality') {
			$form1.append($("<input type='hidden' name='currentSite' value='quality'/>"));
		} else if (type == 'product') {
			$form1.append($("<input type='hidden' name='currentSite' value='product'/>"));
		} else if (type == 'repair') {
			$form1.append($("<input type='hidden' name='currentSite' value='repair'/>"));
		} else if (type == 'project') {
			$form1.append($("<input type='hidden' name='currentSite' value='project'/>"));
		} else if (type == 'jimi') {
			$form1.append($("<input type='hidden' name='currentSite' value='jimi'/>"));
		} else if (type == 'market') {
			$form1.append($("<input type='hidden' name='currentSite' value='market'/>"));
		}  else if (type == 'research') {
			$form1.append($("<input type='hidden' name='currentSite' value='research'/>"));
		} else if (type == 'test') {
			$form1.append($("<input type='hidden' name='currentSite' value='test'/>"));
		} else if (type == 'iot') {
			$form1.append($("<input type='hidden' name='currentSite' value='iot'/>"));
		} else if (type == 'sim') {
			$form1.append($("<input type='hidden' name='currentSite' value='sim'/>"));
		}  
		$form1.append($("<input type='hidden' name='completionState' value='" + $("#completionState").combobox('getValue') +"'/>")); 
	}
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

// 添加一组file 框
function addYcfkFile(){
	fileIndex = fileIndex+1;
	var addFileHtml = '<input id="fileName'+fileIndex+'"  name="files"   type="file" style="width:350px;">';
	initFileSpanHtml += addFileHtml;
	$("#fileDiv").html(initFileSpanHtml);
	fileId.push('fileName'+fileIndex+'');
}

/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function windowOpenYcfkTwomanage() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#ycfk_w').window('open');
	windowVisible("ycfk_w");
}
function windowCloseYcfkTwomanage() {
	$('#ycfk_w').window('close');
}

var ycfk_customer_menuId = 11435;//异常反馈-客服
var ycfk_repair_menuId = 11441;//异常反馈-维修
var ycfk_terminal_menuId = 11436; //异常反馈-终端
var ycfk_platform_menuId = 11437; //异常反馈-平台
var ycfk_quality_menuId = 11438; //异常反馈-品质
var ycfk_project_menuId = 11442;//异常反馈-项目
var ycfk_product_menuId = 11439;//异常反馈-产品
var ycfk_jimi_menuId = 11443;//异常反馈-几米
var ycfk_market_menuId = 11458;//异常反馈-营销
var ycfk_research_menuId = 11460;//异常反馈-研发
var ycfk_iot_menuId = 11462;//异常反馈-物联网卡
var ycfk_sim_menuId = 11463;//异常反馈-SIM卡平台
var ycfk_test_menuId = 11461//异常反馈-测试

function  getMenuId(currentSite){

	if (currentSite == 'customer') {
		//客服     176  客服
		return ycfk_customer_menuId;
	} else if (currentSite == 'repair') {
		//维修     177  维修
		return ycfk_repair_menuId;
	} else  if (currentSite == 'terminal') {
		//终端技术     180    终端技术，2019-6-11 “终端技术”改为“技术支持”
		return ycfk_terminal_menuId;
	} else if (currentSite == 'platform') {
		//平台技术     179 平台技术
		return ycfk_platform_menuId;
	} else if (currentSite == 'quality') {
		//品质     167   品质部  QC主管
		return ycfk_quality_menuId;
	} else  if (currentSite == 'product') {
		//产品     173  产品规划部
		return  ycfk_product_menuId;
	} else  if (currentSite == 'project') {
		//项目    172   项目部
		return ycfk_project_menuId;
	} else  if (currentSite == 'jimi') {
		//几米    147   软件一部  技术支持
		return ycfk_jimi_menuId;
	} else  if (currentSite == 'market') {
		//市场 168市场部,20194/4，“客服文员”更变为“”
		return ycfk_market_menuId;
	} else  if (currentSite == 'research') {
		return ycfk_research_menuId;
	} else  if (currentSite == 'test') {
		return ycfk_test_menuId;
	} else  if (currentSite == 'iot') {
		return ycfk_iot_menuId;
	} else  if (currentSite == 'sim') {
		return ycfk_sim_menuId;
	}
	return null;
}
/**
 *  跟进人多选框初始化
 * @param nextStation 下一工站的名字
 */
function initFollowPeople(nextStation){
	var data=[];
	$.ajax({
		url:ctx+"/user/UserListByMenu?menuId="+nextStation,
		type:'GET',
		async:false,
		success:function(result){
			data = result.data;
		},
		error:function(result){
			alert("初始化错误");
		}
	});
	$("#followup_user").combobox ({
		editable : false,
		valueField : "uName", //相当于 option 中的 value 发送到后台的
		textField : "uName",//option中间的内容 显示给用户看的
		data:data,
		multiple:true,//设置可以多选
	});
}

///////////////////// 选择抄送人
var temp_ZZGZ_selectName;    //临时存储的名称

var currentPageNum_ZZGZ;

$(function(){
	keySearch(queryListPageZZGZ,1);
});


/*---------获取抄送人 start-----------*/
function queryListPageZZGZ(){

	var url=ctx+"/user/UserListByMenu?menuId="+$("#gIds").val();

	asyncCallService(url, 'post', false,'json', null, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$("#zzgz_DataGrid").datagrid('loadData',returnData.data);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
}

/*-------获取最终故障 end--------------*/

/*-----------------选择最终故障 start--------------*/
function repair_openZZGZ(){
	defaultSelectFirst_ZZGZ();
	windowCommOpen("zzgz_w");
}

//默认选客服
function defaultSelectFirst_ZZGZ() {
	$('#typeTree-ZZGZ').tree({
		onLoadSuccess : function(node, data) {
			$("#gIds").val("11435");
			queryListPageZZGZ();
		}
	});
}
/**------------------------------------------------------------- Tree抄送人员 Start----------------------------------------------------**/

function treeLoadAgin_ZZGZ() {
	var groupList = '[{"id":"","text":"所有类别",children:[';
	;
	$.each(stationMenu, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.qname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree-ZZGZ').tree(
		{
			data : eval(groupList),
			onClick : function(node){
				$("#gIds").val(node.id);
				queryListPageZZGZ(1);
			}
		}
	);
}
//最终故障添加按钮事件
function doinsertZZGZ()
{

	windowVisible("zzgz_w");
	queryListPageZZGZ(1);
	$("#selected-type-box-ZZGZ").empty();

	temp_ZZGZ_selectName = $("#repair_w_zzgzDesc").val();

	//初始化所选择的最终故障
	var names = temp_ZZGZ_selectName.split(",");
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_ZZGZname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-ZZGZ").append(htmlDatas);
	}
	ClearZZGZFunctionMethon();

//	windowCommOpen("zzgz_w");
	repair_openZZGZ();
}

function dbClickChooseZZGZ(index,value){
	var Falg  = true;
	var ids   = temp_ZZGZ_selectName.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.id){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-ZZGZ").append("<span class='tag'>"+value.uName+"<input type='hidden' name='win_ZZGZname' value="+value.uName+"><button class='panel-tool-close'></button></span>");
		if(temp_ZZGZ_selectName!=""){
			temp_ZZGZ_selectName = temp_ZZGZ_selectName+","+value.uName;
		}else{
			temp_ZZGZ_selectName = value.uName;
		}
		ClearZZGZFunctionMethon();
	}else{
		$.messager.alert("操作提示","已选择！","warning");
	}
}


/**
 * 保存数据
 */
function sysSelectDatasZZGZ(){
	var names  ="";

	$("input[name='win_ZZGZname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+","+$(this).val();
		}
	});

	temp_ZZGZ_selectName  = names;
}
/**
 * 初始化清除和删除方法
 */
function ClearZZGZFunctionMethon(){
	sysSelectDatasZZGZ();
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		$(this).parent().remove();
		sysSelectDatasZZGZ();
	});
	$(".selected-type .clear-select-btn").click(function(){
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasZZGZ();
	});
}

/**
 * 最终故障保存
 */
function ZZGZSave(){
	sysSelectDatasZZGZ();
	$("#repair_w_zzgzDesc").val(temp_ZZGZ_selectName);
	windowCommClose("zzgz_w");
}
/*---------------选择最终故障 end-------------------*/