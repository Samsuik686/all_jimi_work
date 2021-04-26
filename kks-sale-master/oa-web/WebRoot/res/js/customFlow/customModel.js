var currentPageNum;
var sendIds = "";
var fileId = [ "fileName1" ];
var fileIndex = 1;
var initFileSpanHtml = '';
var downloadFileHtml = '';
var customFieldHtml = '';
var adminFlag = false;
var dataStatus;
var curname;
var needArray = [];
// var type = getUrlParameter("ycfkType");
$(function() {
	initDatas();
	datagridInit();
	keySearch(queryListPage);
	// getCurrentUserRole();
	
	$('#searchinfo').click(function() {
		// $('#tree-Date').val("");
		// $("#tree-State").val("");
		queryListPage(1);
	});
	    
	 $("#next_site").change(function () {
		 var val = $("#next_site option:selected").val();
		 // 根据模块ID，获取拥有此权限的人员
		 getFollowers(val);
	 });
});

function getFollowers(val){
	if(val == null || val == '' || val == undefined){
		return;
	}
	var url = ctx + "/customModel/getFollowers";
	var param = {
		"id" : val
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == undefined) {
			var returnDatas = returnData.data;
			$("#followup_user").empty();
			$.each(returnDatas, function(key, val) {
				$("#followup_user").append(
						"<option value='" + key + "'>" + val + "</option>");
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");

		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

/**
 * 初始化 流程模组
 */
function initDatas() {
	var url = ctx + "/customModel/customModelByIdAndBelong";
	var param = {
		"belong" : flowName,
		"id" : modelId
	}
	// 标题名称
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == undefined) {
			var returnDatas = returnData.data;
			curname = returnDatas.name;
			// '信息');
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");

		}

	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});

	// 下一工站
	var url = ctx + "/customModel/customModelBackByBelong";
	var param = {
		"belong" : flowName,
		"id" : modelId
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == undefined) {
			var returnDatas = returnData.data;
			$.each(returnDatas, function() {
				$("#next_site").append(
						"<option value='" + this.id + "'>" + this.name
								+ "</option>");
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");

		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});

}

/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	var columns = [
			{
				field : 'id',
				title : '选择',
				checkbox : true,
				width : 30,
				formatter : function(value, rowData, index) {
					return "<input type='checkbox' name='id' value='"
							+ rowData.id + "'/>";
				}
			},
			{
				field : 'serial',
				title : '流水号',
				width : 220,
				align : 'center'
			},{
				field : 'modelName',
				title : '所处工站',
				width : 130,
				align : 'center'
			},
			{
				field : 'state',
				title : '本工站状态',
				width : 80,
				align : 'center',
				formatter : function(value, rowData, index) {
					if (rowData.state == '0') {
						return "<font color='blue'>处理中</font>";
					} else if (rowData.state == '1') {
						return "<font color='green'>已完成</font>";
					} else if (rowData.state == '2') {
						return "<font color='red'>已过期</font>";
					}else {
						return "";
					}
				}
			}, 
			{
				field : 'fromModel',
				title : '上一工站',
				width : 130,
				align : 'center'
			}, {
				field : 'flowName',
				title : '所属流程',
				width : 130,
				align : 'center'
			}, {
				field : 'remak',
				title : '备注',
				width : 220,
				align : 'center'
			}, {
				field : 'creater',
				title : '反馈人',
				width : 100,
				align : 'center'
			}, {
				field : 'follower',
				title : '跟进人',
				width : 100,
				align : 'center'
			}, {
				field : 'updater',
				title : '更新人',
				width : 100,
				align : 'center'
			}, {
				field : 'createDate',
				title : '反馈日期',
				width : 130,
				align : 'center'
			}, {
				field : 'updateDate',
				title : '更新日期',
				width : 130,
				align : 'center'
			}, {
				field : 'expireDate',
				title : '过期时间',
				width : 130,
				align : 'center'
			}
	];
	
	
	var url = ctx + "/customModel/getCustomField";
	var param = {
			"flowName" : flowName
	}
	
	$.ajax({
		url : url,
		type : 'post',
		async:false,
		dataType : 'json',
		data : param,
		cache : false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success : function(returnData) {
			var entity = returnData.data;
			if (entity != null && entity != undefined && entity.length > 0) {
				downloadFileHtml = '';
				$.each(entity,function(index, val) {
					var column = {
							field : "params_"+val.name,
							title : val.name,
							width : 100,
							align : 'center'
					};
					columns.push(column);
				});
			}
		},
		error : function() {
			$.messager.alert("操作提示","网络错误", "info");
		}
	});
	$("#DataGrid1")
			.datagrid(
					{
						columns : [ 
						     columns
						],
						onDblClickRow : function(rowIndex, row) {
							customTask_updateInfo(rowIndex, row);
						}
					});
	queryListPage(1);
}

function getTreeNode() {
	var treeDate = $('#tree-Date').val();
	if (treeDate && treeDate != '1' && treeDate != '2') {
		// 点击tree查询清空处理状态和开始结束日期
		$("#completionState").combobox('setValue', '0');
		$("#startTime").val("");
		$("#endTime").val("");
	} else {
		$('#tree-Date').val("");
	}
	var treeState = $("#tree-State").val();
	if (treeState == '2') {
		// 查询已完成的数据
		$("#completionState").combobox('setValue', '1');
	} else if (treeState == '1') {
		$("#completionState").combobox('setValue', '0');
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
//	getTreeNode();
	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/customModel/customTaskList";
	var param = {
		"flowName" : flowName,
		"modelId" : modelId,
		"creater" : $.trim($("#creater_search").val()),
		"serial" : $.trim($("#serial_search").val()),
		"state" : $("#state_search option:selected").val(),
		"startTime" : $("#startTime").val(),
		"endTime" : $("#endTime").val(),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	}
	
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {

		var ret = returnData.data;
		if (ret.length > 0) {
			$.each(ret, function(i, j) {

			});
		}
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			
			// 处理params字段，让表单能正常显示
			// 先遍历结果，获取每个结果的params字段
			$.each(returnData.data,function(key,value){
				var paramsJson = value.params;
				// 为每个结果添加字段
				if(paramsJson != null && paramsJson.length > 0){
					// 转成对象
					var paramsObj = $.parseJSON(paramsJson);
					$.each(paramsObj,function(key2,value2){
						// 给原对象赋值
						value["params_"+key2] = value2;
					})
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

// TODO 清除表单数据
function clearFromParams() {
	$("#id").val("");
	$("#remark").val("");
	$("#mform :input").attr("disabled", false);
	$("#ycfkAddOrUPdate").linkbutton('enable');

}

// 新增异常管理信息
function customTaskSave() {
	var isValid = $('#mform').form('validate');
	var params = submitParams();
	if(null == params){
		return;
	}
	if (isValid) {
		$("#ycfkAddOrUPdate").linkbutton('disable');// 点击验证后禁用按钮,防止重复提交
		var reqAjaxPrams = {
			"id" : $("#id").val(),
			"flowName" : $("#flowName").val(),
			"modelId" : $.trim($("#modelId").val()),
			"creater" : $.trim($("#creater").val()),
			"remark" : $.trim($("#remark").val()),
			"params" : $.trim(submitParams())
		}
		processSaveAjax(reqAjaxPrams);
	}
}

function submitParams(){
	var inputs = $("#paramsTable").find('input');
	var param = {};
	var returnFlag = false;
	if(inputs.length > 0){
		$.each(inputs,function(key,value){			
			var val = $(value).val();
			var id = $(value).attr('id');
			if($(value).attr('type') == 'radio' && $(value).attr("checked")){
				id = $(value).attr('name');
				param[id] = val;
			}else{
				param[id] = val;				
			}
			
			if(needArray.indexOf(id) != -1 && (val == null || val == '')){
				returnFlag = true;
				$.messager.alert("操作提示", id + "不能为空", "info");
				return;
			}
		});
	}
	if(returnFlag){
		return null;
	}
	var stringify=JSON.stringify(param).replace(/\"/g,"'");
	return stringify;
}

// 是否同步刷新
function processSaveAjax(reqAjaxPrams) {
	var url = ctx + "/customModel/addOrUpdateCustomTask";
	var id = $("#id").val();
	$.ajaxFileUpload({
		url : url,
		type : 'post',
		data : reqAjaxPrams,// 此参数非常严谨，写错一个引号都不行
		secureuri : false, // 是否启用安全提交，一般设置为false
		fileElementId : fileId, // 文件上传空间的id属性
		// dataType: 'json', //返回值类型 一般设置为json，不设置JQ会自动判断
		success : function(data) { // 服务器成功响应处理函数
			windowCloseYcfkTwomanage();
			if (id > 0) {
				queryListPage(currentPageNum);
			} else {
				queryListPage(1);
			}
			$.messager.alert("操作提示", "操作成功", "info");
		},
		error : function(data, status, e) {// 服务器响应失败处理函数
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});

}
// 删除异常反馈信息
function customTask_deleteInfo(flag) {
	var row = $('#DataGrid1').datagrid('getSelections');
	if (row.length > 0) {
		$.messager.confirm("操作提示", "删除操作会删除整个流水号相关的任务链，是否删除？", function(conf) {
			if (conf) {
				var ids = "";
				for (var i = 0; i < row.length; i++) {
					var siteStatus = row[i].state;
					if (1 == siteStatus) {
						$.messager.alert("操作提示",
								"不能删除<font color='red'>已完成</font>数据", "info");
						return;
					}
					(i == 0) ? ids = row[i].serial : ids = row[i].serial + ","
							+ ids;
				}
				if (ids) {
					var url = ctx + "/customModel/deleteCustomTask";
					var param = "ids=" + ids;
					asyncCallService(url, 'post', false, 'json', param,
							function(returnData) {
								processSSOOrPrivilege(returnData);

								if (returnData.code == '0') {
									queryListPage(currentPageNum);
								}
								$.messager
										.alert("操作提示", returnData.msg, "info");
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
function getCurrentUserRole() {
	var url = ctx + "/rolePrivilege/getCurrentUserRoleId";
	asyncCallService(url, 'post', false, 'json', '', function(returnData) {
		if (returnData.code == undefined) {
			var getRoleIdListData = returnData.data;
			if (getRoleIdListData.indexOf('25') > -1
					|| getRoleIdListData.indexOf('96') > -1) {
				$("#delBut").show();
				$("#divDelBut").css('display', 'block');
				adminFlag = true;
			} else {
				$("#delBut").hide();
				$("#divDelBut").css('display', 'none');
			}

		} else {
			$.messager.alert("操作提示", returnData.msg, "info");

		}

	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});

}

// 弹出新增框
function customTask_addInfo() {
	clearFromParams();
	$("#flowName").val(flowName);
	$("#modelId").val(modelId);
	$("#creater").val($("#createrSpan").html());
	// $('#addCustomTask').panel({title: "新增"+flowName});
	chageWinSize("addCustomTask");
	$("#ycfkAddOrUPdate").linkbutton('enable');
	initFileSpanHtml = '<input id="fileName1" name="files" type="file" style="width:350px;">'
			+ '<button name="fileADD"  type="button" onclick="addCustomTaskFile()">+</button>';
	$("#fileDiv").html(initFileSpanHtml);
	fileIndex = 1;
	$("#descriptionFileDiv").html('');
	synParamsTable();
	windowOpenYcfkTwomanage();
}

// 同步自定义字段参数
function synParamsTable(){
	var url = ctx + "/customModel/getCustomField";
	var param = {
		"flowName" : flowName
	}
	
	$.ajax({
		url : url,
		type : 'post',
		async:false,
		dataType : 'json',
		data : param,
		cache : false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success : function(returnData) {
			var entity = returnData.data;
			// TODO 数据展示
			if (entity != null && entity != undefined && entity.length > 0) {
				needArray = [];
				$("#paramsTable").html('');
				$.each(entity,function(index, val) {
					var name = val.name;
					var need = val.isNeed;
					var type = val.type;
					var checkBox = val.checkBox;
					// required="true"
					
					var appendHtml = '<tr><td >'+name+':</td>';
					if(0 == need){
						needArray.push(name);
						appendHtml = '<tr><td><font color="red">*'+name+'</font>:</td>';
					}
					
					if(1 == type){
						appendHtml += '<td>';
						$.each(checkBox.split(','),function (index,val){
							if(index == 0){
								appendHtml += '<input name="'+name+'" id="'+(name+index)+'" type="radio" value="'+val+'" checked="checked">'+val+'</input>';
							}else{
								appendHtml += '<input name="'+name+'" id="'+(name+index)+'" type="radio" value="'+val+'" >'+val+'</input>';															
							}
						});
						appendHtml += '</td></tr>';
					}else{
						appendHtml += '<td><input id="'+val.name+'" type="text" value="" required="true" /></td></tr>';
					}
					$("#paramsTable").append(appendHtml);
				});
			}
		},
		error : function() {
			$.messager.alert("操作提示","网络错误", "info");
		}
	});
}

// 弹出修改框
function customTask_updateInfo(index, entity) {
	if (entity) {
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

function addEntityValue(entity) {
	if (modelId == 0 && entity.modelName != '开始'){
		$.messager.alert("提示信息", "只能修改属于开始界面的任务", "info");
		return;
	}
	
	initFileSpanHtml = '<input id="fileName1"  name="files"   type="file" style="width:350px;">'
			+ '<button name="fileADD"  type="button" onclick="addCustomTaskFile()">+</button>';
	$("#fileDiv").html(initFileSpanHtml);
	fileIndex = 1;
	
	synParamsTable();
	
	var siteStatus = entity.state;

	if (siteStatus != undefined && null != siteStatus) {
		if (siteStatus == 1) {
			$("#mform :input").attr("disabled", true);// 已完成数据不能操作
			$("#ycfkAddOrUPdate").linkbutton('disable');
			dataStatus = -1;
		} else {
			$("#mform :input").attr("disabled", false);
			$("#ycfkAddOrUPdate").linkbutton('enable');
			dataStatus = 0;
		}
	} else {
		// 异常反馈列表查看信息
		$("#ycform :input").attr("disabled", true);
	}

	var getCurrentId = entity.id;
	var url = ctx + "/customModel/selectCustomTask";
	var param = "id=" + getCurrentId;
	$("#descriptionFileDiv").html('');
	asyncCallService(
			url,
			'post',
			false,
			'json',
			param,
			function(returnData) {
				var entity = returnData.data.data;
				// TODO 数据展示
				if (entity.attachFileList) {
					downloadFileHtml = '';
					$.each(entity.attachFileList,function(index, fileData) {
										var fileUrl = encodeURIComponent(fileData.fileUrl);
										var appendHtml = '<span><a  href="javascript:;" onclick="downLoadFile(\''
												+ fileUrl
												+ '\', \''
												+ fileData.fileName
												+ '\')">'
												+ fileData.fileName
												+ '</a><button id='
												+ fileData.fid
												+ ' class="panel-tool-close" type="button" onclick="deleteFile(\''
												+ fileData.fid
												+ '\', \''
												+ fileData.createUser
												+ '\')" ></button>[附件上传人：'
												+ fileData.createUser
												+ '|'
												+ fileData.createTime
												+ ']<br/></span>';
										downloadFileHtml += appendHtml;
									});

					$("#descriptionFileDiv").html(downloadFileHtml);
				}

				$("#id").val(entity.id);
				$("#flowName").val(entity.flowName);
				$("#modelId").val(entity.modelId);
				$("#creater").val(entity.creater);
				$("#remark").val(entity.remark);
				
				var paramsObj = $.parseJSON(entity.params);
				writeParams(paramsObj);

				$('#addCustomTask').panel({
					title : "编辑" + curname
				});
				
				
				windowOpenYcfkTwomanage();
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
}

// 填充自定义字段的值
function writeParams(paramsObj){
	var inputs = $("#paramsTable").find('input');
	var param = {};
	if(inputs.length > 0 && null != paramsObj){
		$.each(inputs,function(key,value){			
			var id = $(value).attr('id');
			if(paramsObj.hasOwnProperty(id)){
				$(value).val(paramsObj[id]);				
			}
		});
	}
}

// 删除附件后刷新附件显示
function getFileByYid() {
	var url = ctx + "/customModel/getFileByYid";
	var param = "yid=" + $("#backIdi").val();
	asyncCallService(
			url,
			'post',
			false,
			'json',
			param,
			function(returnData) {
				var entity = returnData.data;
				// TODO 数据展示
				if (entity) {
					downloadFileHtml = '';
					$
							.each(
									entity,
									function(index, fileData) {
										var fileUrl = encodeURIComponent(fileData.fileUrl);
										var appendHtml = '<span><a  href="javascript:;" onclick="downLoadFile(\''
												+ fileUrl
												+ '\', \''
												+ fileData.fileName
												+ '\')">'
												+ fileData.fileName
												+ '</a><button id='
												+ fileData.fid
												+ ' class="panel-tool-close" type="button" onclick="deleteFile(\''
												+ fileData.fid
												+ '\', \''
												+ fileData.createUser
												+ '\')" ></button>[附件上传人：'
												+ fileData.createUser
												+ '|'
												+ fileData.createTime
												+ ']<br/></span>';
										downloadFileHtml += appendHtml;
									});

					$("#descriptionFileDiv").html(downloadFileHtml);
				}
			});

}
// 删除附件
function deleteFile(fid, createUser) {
	if (dataStatus == 0) {
		if (fid) {
			// 附件上传人才可以删除附件,
//			adminFlag = false;
//			var currentUserName = $("#userName").html();
//			getCurrentUserRole();
			setTimeout(function() {
//				adminFlag;
//				if (createUser == currentUserName || adminFlag) {
					$.messager.confirm("操作提示", "是否删除此附件？", function(conf) {
						if (conf) {
							var url = ctx + "/customModel/deleteFileByFid";
							var param = {
								"fid" : fid
							}
							asyncCallService(url, 'post', true, 'json', param,
									function(returnData) {
										if (returnData.code == '0') {
											// 刷新当前页面
											getFileByYid();
											$.messager.alert("操作提示", "删除成功！",
													"info");
										} else {
											$.messager.alert("操作提示", "操作异常！",
													"info");
										}
									});
						}
					});
//				} else {
//					$.messager.alert("提示信息", "只有管理员和附件上传人才可以删除此附件！", "info");
//				}
			}, 500);
		} else {
			$.messager.alert("提示信息", "未匹配到文件ID！", "info");
		}
	} else {
		$.messager.alert("提示信息", "已完成数据禁止删除附件！", "info");
	}

}

// 下载附件
function downLoadFile(fileUrl, fileName) {
	var url = ctx + "/customModel/downLoadFile";
	var $form1 = $("<form action='" + url
			+ "' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='fileUrl' value='" + fileUrl
			+ "'/>"));
	$form1.append($("<input type='hidden' name='fileName' value='" + fileName
			+ "'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

// 发送数据到下一个工站
function sendDataToNextSiteOpen() {
	
	sendIds = "";
	windowVisible("nextSiteUser_w");
	$('#next_site').val('');
	$('#followup_user').val('');
	$("#followup_user").empty();
	$('#expireDateStr').val('');
	var sendInfos = $('#DataGrid1').datagrid('getSelections');
	if (sendInfos.length > 0) {
		for (var i = 0; i < sendInfos.length; i++) {
			var siteStatus =sendInfos[i].state;
			if (siteStatus == 0) { // 待解决 的数据
				(i == 0) ? sendIds = sendInfos[i].id
						: sendIds = sendInfos[i].id + "," + sendIds;
			} else {
				$.messager.alert("操作提示", "只能发送处理中数据到下一工站", "info");
				return;
			}
			var modelName =sendInfos[i].modelName;
			if(modelId ==0 && modelName != '开始'){
				$.messager.alert("操作提示", "只能操作处于开始工站的数据", "info");
				return;
			}
			
		}
			$('#nextSiteUser_w').window('open');
	} else {
		$.messager.alert("操作提示", "请选择要发送的数据", "info");
	}
}

function customTaskSendData() {
	var followUser = $("#followup_user option:selected").val();
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
	if (sendInfos.length > 0) {
		$.messager
				.confirm("操作提示","确定发送选中数据到下一工站",
						function(conf) {
							if (conf) {
								if (sendIds) {
									var url = ctx + "/customModel/sendDataToNextSite";
									var param = {
										"ids" : sendIds,
										"modelId" : nextSite,
										"follower" : followUser,
										"flowName" : flowName,
										"expireDateStr" : $('#expireDateStr').val()
									}
									debugger;
									asyncCallService(
											url,
											'post',
											false,
											'json',
											param,
											function(returnData) {
												processSSOOrPrivilege(returnData);
												if (returnData.code == '0') {
													$.messager
															.alert("操作提示",returnData.msg,"info",
																	function() {
																		windowCommClose('nextSiteUser_w');
																		queryListPage(currentPageNum);
																	});
												} else {
													$.messager.alert("操作提示",
															returnData.msg,
															"info");
												}
											}, function() {
												$.messager.alert("操作提示",
														"网络错误", "info");
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
	var url = ctx + "/customModel/exportDatas";
	
	var $form1 = $("<form action='" + url
			+ "' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='flowName' value='"
			+ flowName + "'/>"));
	$form1.append($("<input type='hidden' name='modelId' value='"
			+ modelId + "'/>"));
	$form1.append($("<input type='hidden' name='creater' value='"
			+ $.trim($("#creater_search").val()) + "'/>"));
	$form1.append($("<input type='hidden' name='serial' value='"
			+ $.trim($("#serial_search").val()) + "'/>"));
	$form1.append($("<input type='hidden' name='state' value='"
			+ $("#state_search option:selected").val() + "'/>"));
	$form1.append($("<input type='hidden' name='startTime' value='"
			+ $("#startTime").val() + "'/>"));
	$form1.append($("<input type='hidden' name='endTime' value='"
			+ $("#endTime").val() + "'/>"));
	
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

// 添加一组file 框
function addCustomTaskFile() {
	fileIndex = fileIndex + 1;
	var addFileHtml = '<input id="fileName' + fileIndex
			+ '"  name="files"   type="file" style="width:350px;">';
	initFileSpanHtml += addFileHtml;
	$("#fileDiv").html(initFileSpanHtml);
	fileId.push('fileName' + fileIndex + '');
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
	$('#addCustomTask').window('open');
	windowVisible("addCustomTask");
}
function windowCloseYcfkTwomanage() {
	$('#addCustomTask').window('close');
}

function openCustomField(){
	windowVisible("customFieldModify");
	customFieldHtml = '<tr name="addFieldButton"><td><a href="javascript:;" class="easyui-linkbutton l-btn l-btn-plain" iconcls="icon-addicon" plain="true" onclick="addCustomFieldRow();" id=""><span class="l-btn-left"><span class="l-btn-text icon-addicon l-btn-icon-left">增加</span></span></a></td></tr>';
	$('#customFieldTable').html(customFieldHtml);
	var url = ctx + "/customModel/getCustomField";
	var param = {
			"flowName" : flowName
	}
	asyncCallService(
			url,
			'post',
			false,
			'json',
			param,
			function(returnData) {
				var entity = returnData.data;
				// TODO 数据展示
				if (entity != null && entity != undefined && entity.length > 0) {
					downloadFileHtml = '';
					$.each(entity,function(index, val) {
						// required="true"
						var name = val.name;
						var need = val.isNeed;
						var type = val.type;
						var checkBox = val.checkBox;
						
						var appendHtml = '<tr><td><input name="fields" type="text" value="'+ name +'"  /></td>';
						if(need == 1){
							appendHtml += '<td><select name="isNeed" style="width: 80px;"><option value="0">必填</option><option value="1" selected="selected">非必填</option></select></td>';
						}else{
							appendHtml += '<td><select name="isNeed" style="width: 80px;"><option value="0" selected="selected">必填</option><option value="1">非必填</option></select></td>';
						}
						if(type == 1){
							appendHtml += '<td><select name="type" style="width: 80px;" onchange="addCheckBox(this)"><option value="0" >文本</option><option value="1" selected="selected">单选</option></select></td>';
							appendHtml += '<td><input name="checkBoxField" style="width: 100px;" type="text" value="'+checkBox+'" /></td>';
						}else{
							appendHtml += '<td><select name="type" style="width: 80px;" onchange="addCheckBox(this)"><option value="0" selected="selected">文本</option><option value="1" >单选</option></select></td>';
						}
						appendHtml += '<td><button name="delfield"  type="button" onclick="delField(this)">-</button>'
						+ '<button name="moveFiled" type="button" onclick="moveField(this)">^</button></td></tr>'
						
						$("#customFieldTable").append(appendHtml);
					});
				}
			},
			function() {
				$.messager.alert("操作提示","网络错误", "info");
			}
	);
	$('#customFieldModify').window('open');
}

function addCustomFieldRow(){
	$('#customFieldTable').append('<tr><td><input name="fields" type="text" value="" /></td>'
			+ '<td><select name="isNeed" style="width: 80px;"><option value="0" selected="selected">必填</option><option value="1">非必填</option></select></td>'
			+ '<td><select name="type" style="width: 80px;" onchange="addCheckBox(this)"><option value="0" selected="selected" >文本</option><option value="1">单选</option></select></td>'
			+ '<td><button name="delfield"  type="button" onclick="delField(this)">-</button>'
			+ '<button name="moveFiled" type="button" onclick="moveField(this)">^</button></td></tr>');
	
}

function addCheckBox(type){
	var next = $(type).parent().next().children();
	if($(type).val() == 1 && "checkBoxField" != next.attr('name')){
		$(type).parent().after('<td><input name="checkBoxField" style="width: 100px;" type="text" value="" /></td></tr>');
	}else{
		if("checkBoxField" == next.attr('name')){
			next.parent().remove();
		}
	}
}

// 上移元素
function moveField(val){
	var cur = $(val).parent().parent();
	var curTop = cur.prev();
	if(curTop == null || typeof(curTop) == "undefined" || "addFieldButton" == curTop.attr('name')){
		return;
	}else{
		cur.after(curTop);
	}
}

function delField(val){
	$(val).parent().parent().remove();
}

function saveCustomField(){
//	var inputs = $("#customFieldTable").find('input');
	var inputs = $("#customFieldTable :input[name='fields']");
	var fields = "";
	var filedList = new Array();
	var count = 0;
	var returnFlag = false;
	if(inputs.length > 0){
		$.each(inputs,function(key,value){
			var field= $(value);
			var filedName = $(value).val();
			var filedNeed = $(value).parent().next().children().val();
			var filedType = $(value).parent().next().next().children().val();
			var filedCheckBox = null;
			if(filedType == '1'){
				filedCheckBox = $(value).parent().next().next().next().children().val();
				if($.trim(filedCheckBox) == null || $.trim(filedCheckBox) == ''){
					$.messager.alert("操作提示","字段="+filedName+"，为单选类型，请填写单选的值！","info");
					returnFlag = true;
					return;
				}
			}
			var filedObj = {
					"name" : filedName,
					"isNeed" : filedNeed,
					"type" : filedType,
					"checkBox" : filedCheckBox,
					"order" : count++
			}
			filedList.push(filedObj);
		});
	}
	if(returnFlag){
		return;
	}
	
	var url = ctx + "/customModel/addCustomField";
	
	var stringify=JSON.stringify(filedList).replace(/\"/g,"'");
	var param = {
			"name" : flowName,
			"fields" : stringify
	}
	asyncCallService(
			url,
			'post',
			false,
			'json',
			param,
			function(returnData) {
				processSSOOrPrivilege(returnData);
				if (returnData.code == '0') {
					$.messager
							.alert("操作提示",returnData.msg,"info",
									function() {
										windowCommClose('customFieldModify');
										datagridInit();
//										queryListPage(currentPageNum);
									});
				} else {
					$.messager.alert("操作提示",returnData.msg,"info");
				}
			}, 
			function() {
				$.messager.alert("操作提示","网络错误", "info");
			}
		);
}

