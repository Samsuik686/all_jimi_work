$(function() {
	var currentPageNum;
	initEvent();
	initData();
	setInterval("runTip('TIP_TEST')", 5000);
});

function initData() {
	$("#DataGrid1").datagrid({
		singleSelect : false,
		onDblClickRow : function(rowIndex, row) {
			showInfo(rowIndex, row);
		}
	});
	queryListPage(1);
}

function initEvent() {
	keySearch(queryListPage);

	$('#searchinfo').click(function() {
		$('#tree-Date').val("");
		$("#tree-State").val("");
		queryListPage(1)
	});

	$("#resultSave").click(function() {
		addOrUpdateTestResult();
	});

	$("#resultClose").click(function() {
		$('#w_testDesc').window('close');
	});
}

function queryListPage(pageNumber) {
	$("#showTip").hide();
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	getTreeNode();
	var testStatus = $("#testState option:selected").val();
	if ((!$.trim($("#imei").val()) || !$.trim($("#lockId").val())) && !$("#tree-Date").val() && testStatus == 2 && (!$("#startTime").val() || !$("#endTime").val())) {
		$.messager.alert("操作提示", "查询已完成数据请选择开始，结束日期", "info");
		return;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var selParams = {
		"testStatus" : testStatus,
		"startTime" : $("#startTime").val(),
		"endTime" : $("#endTime").val(),
		"treeDate" : $("#tree-Date").val(),
		"imei" : $.trim($("#imei").val()),
		"lockId" : $.trim($("#lockId").val()),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	};
	var url = ctx + "/workflowTest/workflowTestList";

	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', selParams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			commonData(ret);
			$("#DataGrid1").datagrid('loadData', ret);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#DataGrid1").datagrid('loaded');
		getPageSize();
		resetCurrentPageShow(currentPageNum);
	}, function() {
		$("#DataGrid1").datagrid('loaded');
		$.messager.alert("操作提示", "网络错误", "info");
	});
	// 重置推送消息
	resetTip('TIP_TEST');
}

/**
 * 需要转换的数据
 */
function commonData(ret) {
	$.each(ret, function(i, item) {
		// 距受理时间1天，受理时间显示黄色，两天，红色
		if (item.testStatus != "-1") {//测试工站未发送
			var t = getDayFromAcceptanceTime(item.acceptanceTime);
			if (t == 1) {
				item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
			} else if (t == 2) {
				item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
			}
		}

		if (item.isWarranty == 0) {
			item.isWarranty = "保内";
		} else {
			item.isWarranty = "保外";
		}

		if (item.w_isRW == 0) {
			item.w_isRW = "<label style='color:red;font-weight: bold;'>是</label>";
		} else {
			item.w_isRW = "<label style='color:green;font-weight: bold;'>否</label>";
		}

		if (item.testStatus == 0) {
			item.testStatus = "<label style='color:red;font-weight: bold;'>已受理，测试中</label>";
			item.testStatusCode = 0;
		} else if (item.testStatus == 1) {
			item.testStatus = "<label style='color:red;font-weight: bold;'>待维修，测试中</label>";
			item.testStatusCode = 1;
		} else if (item.testStatus == 2) {
			item.testStatus = "<label style='color:red;font-weight: bold;'>待终检，测试中</label>";
			item.testStatusCode = 2;
		} else if (item.testStatus == 3) {
			item.testStatus = "<label style='color:green;font-weight: bold;'>测试完成</label>";
			item.testStatusCode = 3;
		} else if (item.testStatus == -1) {
			item.testStatus = "<label style='color:green;font-weight: bold;'>已发送</label>";
			item.testStatusCode = -1;
		} else if (item.testStatus == 4) {
			item.testStatus = "<label style='color:green;font-weight: bold;'>不报价，测试中</label>";
			item.testStatusCode = 4;
		}
	});
}

function getTreeNode() {

	var treeDate = $('#tree-Date').val();
	if (treeDate && treeDate != '1' && treeDate != '2') {
		// 点击tree查询清空处理状态和开始结束日期
		$("#testState option:selected").val('0');
		$("#startTime").val("");
		$("#endTime").val("");
	} else {
		$('#tree-Date').val("");
	}
	var treeState = $("#tree-State").val();
	if (treeState == '2') {
		// 查询已完成的数据
		$("#testState").prop("selectedIndex", 2).change();
		$("#testState option:selected").val(2);
	} else if (treeState == '1') {
		$("#testState").prop("selectedIndex", 1).change();
		$("#testState option:selected").val(1);
	}
}

/**
 * 返回数据到数据来源站
 */
function sendData() {
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	var count = 0;//不符合条件数据个数
	if (SendInfo.length > 0) {
		var marked_words = "确定要发送<font color='red'>选中数据</font>到 原工站？";
		$.messager.confirm("操作提示", marked_words, function(conf) {
			if (conf) {
				var ids = "";
				for (var i = 0; i < SendInfo.length; i++) {
					if (SendInfo[i].testStatus.indexOf("测试完成") > -1) { // 测试完成 的数据
						(i == 0) ? ids = SendInfo[i].id : ids = SendInfo[i].id + "," + ids;
					}else{
						count++;
					}
				}
				if(count > 0){
					$.messager.alert("操作提示", "返回数据源工站有不符合条件的数据！", "warning");
					return;
				}

				if (ids) {
					var url = ctx + "/workflowTest/returnToDataSource";
					var param = {
						"ids" : ids
					};
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							$.messager.alert("操作提示", "数据返回成功！", "info", function() {
								queryListPage(currentPageNum);
							});
						} else {
							$.messager.alert("操作提示", returnData.msg, "info");
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				} else {
					$.messager.alert("操作提示", "未找到匹配数据", "warning");
				}
			}
		});
	} else {
		$.messager.alert("操作提示", "请先选择所要操作的行", "info");
	}
}

function notPrice(index, entity){
	if (!entity) {
		var entity = $('#DataGrid1').datagrid('getSelections');
		if (entity.length == 0) {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
			return;
		} 
	} 
	var ids ="";
	for (var i=0; i< entity.length; i++){
		if (entity[i].dataSource != "受理站" || entity[i].testStatusCode != 0){
			$.messager.alert("提示信息", "已受理，测试中的设备才能执行不报价操作！", "info");
			return;
		}
		ids = ids + "," + entity[i].id;
	}
	ids = ids.substr(1, ids.length);
	console.log(entity, "entity");
	
	var url = ctx + "/workflowTest/notPrice";
	var formParam = "ids=" + ids;
	asyncCallService(url, 'post', false, 'json', formParam, function(returnData) {
		if (returnData.code == '0') {
			$.messager.alert("操作提示", "操作成功！", "info", function() {
				$('#w_testDesc').window('close');
				queryListPage(currentPageNum);
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	});
}

function showInfo(index, entity) {
	if (entity) {
		addEntityValue(entity);
	} else {
		var entity = $('#DataGrid1').datagrid('getSelections');
		if (entity.length == 1) {
			addEntityValue(entity[0]);
		} else if (entity.length == 0) {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		} else {
			$.messager.alert("提示信息", "请先选择一行进行维修！", "info");
		}
	}
}

function addEntityValue(entity) {
	windowCommOpen("w_testDesc");
	windowVisible("w_testDesc");

	if (entity.testStatus.indexOf("已发送") > -1) {
		$("#testResult_info").attr('disabled', true);
		$("#showSaveBut").css("display", "none");
	} else {
		$("#testResult_info").attr('disabled', false);
		$("#showSaveBut").css("display", "block");
	}

	var modalArray = entity.w_model.split("|");
	$("#id").val(entity.id);
	$("#imei_info").val(entity.imei);
	$("#lockId_info").val(entity.lockId);
	$("#bluetoothId_info").val(entity.bluetoothId);
	$("#dataSource_info").val(entity.dataSource);
	$("#accepter_info").val(entity.accepter);
	$("#cusName_info").val(entity.w_cusName);
	$("#phone_info").val(entity.w_phone);
	$("#repairnNumber_info").val(entity.repairnNmber);
	$("#isWarranty_info").val(entity.isWarranty);
	$("#modelMarket_info").val(modalArray ? modalArray[0] : '');
	$("#model_info").val(modalArray ? modalArray[1] : '');
	$("#sulotion_info").val(entity.w_solution);
	$("#acceptRemark_info").val(entity.acceptRemark);
	$("#remark_info").val(entity.remark);
	$("#repairNumberRemark_info").val(entity.repairNumberRemark);
	$("#cjDesc_info").val(entity.w_cjgzDesc);
	$("#zgDesc_info").val(entity.w_zzgzDesc);
	$("#sjfj_info").val(entity.w_sjfjDesc);
	$("#repariPriceDesc_info").val(entity.w_wxbjDesc);
	$("#testResult_info").val(entity.testResult);
	$("#engneer_info").val(entity.w_engineer);
}

function addOrUpdateTestResult() {
	if (!$("#testResult_info").val()) {
		$.messager.alert("提示信息", "请填写测试结果！", "info");
		return;
	}
	var url = ctx + "/workflowTest/saveOrUpdateTestResult";
	var formParam = {
		"testResult" : $("#testResult_info").val(),
		"wfId" : $("#id").val()
	}
	asyncCallService(url, 'post', false, 'json', formParam, function(returnData) {
		if (returnData.code == '0') {
			$.messager.alert("操作提示", "操作成功！", "info", function() {
				$('#w_testDesc').window('close');
				queryListPage(currentPageNum);
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	});
}

// 测试人员获取数据到自己名下
function getData() {
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	var count = 0;//不符合条件数据个数
	if (SendInfo.length > 0) {
		var marked_words = "确定要获取<font color='red'>选中数据</font>到自己名下？";
		$.messager.confirm("操作提示", marked_words, function(conf) {
			if (conf) {
				var ids = "";
				for (var i = 0; i < SendInfo.length; i++) {
					if (SendInfo[i].testStatus.indexOf("测试完成") < 0) { // 不是测试完成 的数据
						if (SendInfo[i].testPerson) {
							var message = "IMEI为：<font color='red'>" + SendInfo[i].imei + "</font>的数据当前测试人员是：<font color='red'>" + SendInfo[i].testPerson + "</font>。请重新选择无测试人员的数据获取";
							$.messager.alert("操作提示", message, "info");
							return;
						}
						(i == 0) ? ids = SendInfo[i].id : ids = SendInfo[i].id + "," + ids;

					}else{
						count++;
					}
				}
				
				if(count > 0){
					$.messager.alert("操作提示", "返回数据源工站有不符合条件的数据！", "warning");
					return;
				}
				
				if (ids) {
					var url = ctx + "/workflowTest/getData";
					var param = {
						"ids" : ids
					};
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							$.messager.alert("操作提示", "获取数据成功！", "info", function() {
								queryListPage(currentPageNum);
							});
						} else {
							$.messager.alert("操作提示", returnData.msg, "info");
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				} else {
					$.messager.alert("操作提示", "未找到匹配数据", "warning");
				}
			}
		});
	} else {
		$.messager.alert("操作提示", "请选择要获取的数据", "info");
	}
}

//终端技术     180
var terminal_oId = 180;
// 发送给其他人
function sendToOther() {
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	if (SendInfo.length > 0) {
		windowVisible("testUser_w");
		$('#testUser_w').window('open');
		// 2019-6-12 “终端技术”改为“技术支持”
		 initUserInfo("test_user", '', "", ctx+"/user/UserListByOrgId", "oId=" + terminal_oId, "技术支持");
		$("#testuserAdd").unbind('click').click(function() {
			sendWorkTest();
		});
		$("#cancelTestUser").click(function() {
			windowCommClose('testUser_w');
		});
	} else {
		$.messager.alert("操作提示", "未选中数据，请检查！", "info");
	}
}

// 发送到测试工站其他人
function sendWorkTest() {
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	if (SendInfo.length > 0) {
		var marked_words = "确定要发送<font color='red'>选中数据</font>给其他人？";
		$.messager.confirm("操作提示", marked_words, function(conf) {
			if (conf) {
				var ids = "";
				for (var i = 0; i < SendInfo.length; i++) {
					if (SendInfo[i].testStatus.indexOf("测试完成") > -1) {
						$.messager.alert("操作提示", "测试完成数据不能发送给其他人", "info", function() {
							queryListPage(currentPageNum);
							windowCommClose('testUser_w');
						});
						return;
					} 
					(i == 0) ? ids = SendInfo[i].id : ids = SendInfo[i].id + "," + ids;
				}

				if (ids) {
					var testuser = $("#test_user option:selected").val();
					if (testuser) {
						var url = ctx + "/workflowTest/sendToOther";
						var param = {
							"ids" : ids,
							"testPerson" : testuser
						};
						asyncCallService(url, 'post', false, 'json', param, function(returnData) {
							processSSOOrPrivilege(returnData);
							if (returnData.code == '0') {
								$.messager.alert("操作提示", "操作成功", "info", function() {
									queryListPage(currentPageNum);
									windowCommClose('testUser_w');
								});
							} else {
								$.messager.alert("操作提示", returnData.msg, "info");
							}
						}, function() {
							$.messager.alert("操作提示", "网络错误", "info");
						});
					} else {
						$.messager.alert("操作提示", "未选中要发送的人员！", "info", function() {
							queryListPage(currentPageNum);
							windowCommClose('testUser_w');
						});
					}
				} else {
					$.messager.alert("操作提示", "未找到匹配数据", "warning");
				}
			}
		});
	} else {
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}