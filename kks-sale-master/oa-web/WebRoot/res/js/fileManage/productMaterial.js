var currentPageNum;

var dianZiBomHtml = "", // 电子料BOM
	bomfileHtml = "", //BOM表
	repairfileHtml = "", //维修手册
	differencefileHtml = "", //差异说明
	projectRemarkHtml = "", // 项目备注附件
	softwarefileHtml = "", // 软件
	explainfileHtml = "", //说明书
	kffileHtml = "", // 客服手册
	proseriesfileHtml = "", //产品系列	
	softDiffileHtml = "", //软件功能差异表
	datumfileHtml = "", //产品资料
	huacefileHtml = "", //产品画册
	functionfileHtml = "", // 功能列表
	zhilingfileHtml = "", //指令表
	productRemarkfileHtml = "", //产品信息备注附件
	zuzhuangfileHtml = "", //组装作业指导书
	testfileHtml = "", // 测试作业指导书
	testToolfileHtml = "", //测试工具
	xiehaoToofileHtml = "", //写号工具
	qualityRemarkfileHtml = ""//品质备注附件
	;

var currentUserName = "",
	currentUserId = "",
 	currentUserOrgId = "",
 	currentUserPosition = "";

$(function(){	
	initEvent();
	initData();	
	getCurrentUserInfo();
	
	$("#proNum").blur(function(){
		if(!$("#id").val()){
			getProNumCount();
		}
	});
	
});

//新增时查询编码数量,提示是否已存在
function getProNumCount(){
	var url = ctx+"/productMaterial/getProNumCount";
	var formParam = {
			"proNum": $.trim($("#proNum").val())
	}
	asyncCallService(url, 'post', true,'json', formParam, function(returnData){
		if(returnData.code==undefined){
			if(returnData.data > 0){
				$.messager.alert("操作提示","该编码已存在！","info");
			}
		}else{
			$.messager.alert("操作提示","操作异常！","info");
		}
	});
}

function getCurrentUserInfo(){
	 currentUserName = $("#userName").html();
	 currentUserId = $("#userId").html();
	 currentUserOrgId = $("#userOrgId").html();
	 currentUserPosition = $("#position").html();
}


function initData(){
	$("#DataGrid1").datagrid({
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			productMaterial_update(rowIndex,row);
		}
	}); 
	queryListPage(1);
	$("#file_DataGrid").datagrid({
	});
	
	$("#DataGrid_chooseZBXH").datagrid({
		onDblClickRow:function(rowIndex,row){
			dbClickChooseZbxh(rowIndex,row);
		}
	});
}

function initEvent(){
	 keySearch(queryListPage);
	 keySearch(queryFileList, 2);
	 
	 $("#searchinfo").click(function(){
		 queryListPage(1);
	 });
	 
	 $("button:button[name=fileUpload]").click(function(){
		 uploadProductMaterialFile(this.value);
	 });
	 
	 $("button:button[name=chooseOther]").click(function(){
		 //弹出该主板型号的该文件列表
		 $("#search_fileType").val($(this).parent("span").find("input :first").attr("id"));
		 openFileListWindow();
	 });
	 	
}

function queryListPage(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum == "" || currentPageNum == null){
		currentPageNum = 1;
	} 
	
	var pageSize = getCurrentPageSize('DataGrid1');
	var marketModel = $.trim($("#searchMarketModel").val());
	var selParams = {
			"marketModel" : marketModel,
			"createUser" : $.trim($("#searchCreateUser").val()),
			"model" : $.trim($("#searchModel").val()),
			"proNum" : $.trim($("#searchByProNum").val()),
			"projectName" : $.trim($("#searchByProjectName").val()),
			"startTime" : $("#startTime").val(),
			"endTime" : $("#endTime").val(),
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
	};
	var url = ctx+"/productMaterial/productMaterialList";
	
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', selParams, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			$("#DataGrid1").datagrid('loadData',ret);  
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
		getPageSize();
		resetCurrentPageShow(currentPageNum);
	}, function(){
		$("#DataGrid1").datagrid('loaded');	
	 	$.messager.alert("操作提示","网络错误","info");
	});
}

function currentUserAuthrity () {
	if (currentUserOrgId == "215") {
		//项目部  172,2019/4/12更改为215
		$("#saveProductBut").linkbutton('enable');
		$("#chooseZbxh").linkbutton('enable');
		$("#product_form :input").attr("disabled",true);
		$("#quality_form :input").attr("disabled",true);
	}else if(currentUserOrgId == "210" ){
		// 管理员测试
		$("#saveProductBut").linkbutton('enable');
		$("#chooseZbxh").linkbutton('enable');
	} else if(currentUserOrgId == "214" ){
		//产品规划部 173,2019/4/12更改为214
		$("#saveProductBut").linkbutton('disable');
		$("#chooseZbxh").linkbutton('disable');
		$("#project_form :input").attr("disabled",true);
		$("#quality_form :input").attr("disabled",true);
	} else if ( currentUserOrgId == "167") {
		//品质部  167
		$("#saveProductBut").linkbutton('disable');
	    $("#chooseZbxh").linkbutton('disable');
		$("#project_form :input").attr("disabled",true);
		$("#product_form :input").attr("disabled",true);
	} else {
		$("#saveProductBut").linkbutton('disable');
	    $("#chooseZbxh").linkbutton('disable');
		$("#project_form :input").attr("disabled",true);
		$("#product_form :input").attr("disabled",true);
		$("#quality_form :input").attr("disabled",true);
	}

	//测试使用
//	if (currentUserOrgId == "92") {
//		$("#saveProductBut").linkbutton('enable');
//		$("#chooseZbxh").linkbutton('enable');
//	} else {
//		$("#saveProductBut").linkbutton('disable');
//		$("#chooseZbxh").linkbutton('disable');
//		$("#project_form :input").attr("disabled",true);
//		$("#product_form :input").attr("disabled",true);
//		$("#quality_form :input").attr("disabled",true);
//	}
}


function productMaterial_add(){
	openWindow();
	currentUserAuthrity();
	$("#id").val("");
	$("#projectName").val("");
	$("#model").val("");
	$("#marketModel").val("");
	$("#productName").val("");
	$("#proNum").val("");
	$("#proType").val("");
	$("#proChildType").val("");
	$("#trademark").val("");
	$("#series").val("");
	$("#proColor").val("");
	$("#customerName").val("");
	clearFileValue(); //清空附件 file 的value
	clearHtmlDiv(); //清空显示的附件
	appendHtmlToFileDiv(); //拼接html到文件的div
	
}

// 清空选择的附件信息
function clearFileValue(id){
	if (id) {
		$("#"+id).val("");
	} else {
		$("#differenceName").val("");
		$("#bomName").val("");
		$("#dianzibomName").val("");
		$("#repairName").val("");
		$("#differenceName").val("");
		$("#projectRemarkName").val("");
		$("#softwareName").val("");
		$("#explainName").val("");
		$("#kfName").val("");
//		$("#proseriesName").val("");
		$("#softDifName").val("");	
		$("#datumName").val("");
		$("#huaceName").val("");
		$("#functionName").val("");
		$("#zhilingName").val("");	
		$("#productRemarkName").val("");
		$("#zuzhuangName").val("");
		$("#testName").val("");
		$("#testToolName").val("");
		$("#xiehaoTooName").val("");
		$("#qualityRemarkName").val("");
	}	
}

function productMaterial_update(Index,entity){
	if(entity){		
		addEntityValue(entity);		
	}else{
		var entity = $('#DataGrid1').datagrid('getSelections');
		if (entity.length == 1){
			addEntityValue(entity[0]);			
		}else if(entity.length == 0){
			$.messager.alert("提示信息","请先选择所要修改的行！","info");
		}else{
			$.messager.alert("提示信息","请先选择一行进行维修！","info");
		}
	}
	
}

// 查询要修改的数据显示
function addEntityValue (entity) {
	openWindow();
	clearHtmlDiv(); //清空显示的附件
	clearFileValue(); //清空文件域	
	currentUserAuthrity();
	$("#id").val(entity.id);
	$("#projectName").val(entity.projectName);
	$("#model").val(entity.model);
	$("#marketModel").val(entity.marketModel);
	$("#productName").val(entity.productName);
	$("#proNum").val(entity.proNum);
	$("#proType").val(entity.proType);
	$("#proChildType").val(entity.proChildType);
	$("#trademark").val(entity.trademark);
	$("#series").val(entity.series);
	$("#proColor").val(entity.proColor);
	$("#customerName").val(entity.customerName);
	
	// 查询相关附件显示
	var params = {
			"id" : $("#id").val()
	}
	getProductMaterialInfo(params);
}

// 删除产品资料
function productMaterial_delete(){
	var entity = $('#DataGrid1').datagrid('getSelections');
	if (entity.length == 1){
		// 项目创建人，才能删除, 项目部  215	&& currentUserOrgId == "215"
		if (entity[0].createUser == currentUserName) {
			$.messager.confirm("操作提示", "是否删除此产品资料信息及相关附件？", function(conf) {
				if (conf) {
					//后台删除
					var url = ctx+"/productMaterial/deleteProdectMaterialById";
					var formParam = {
							"id":entity[0].id
					}		
					asyncCallService(url, 'post', true,'json', formParam, function(returnData){
						if(returnData.code=='0'){
							$.messager.alert("操作提示",returnData.msg,"info",function(){				
								queryListPage(currentPageNum);
							});
						}else{
							$.messager.alert("操作提示","操作异常！","info");
						}
					});
				}
			});	
		} else {
			$.messager.alert("提示信息","只有项目创建人才可以删除此项目！","info");
		}					
	}else if(entity.length == 0){
		$.messager.alert("提示信息","请先选择所要删除的行！","info");
	}else{
		$.messager.alert("提示信息","请先选择一行进行删除！","info");
	}
}

//删除文件
function deleteFile (fid, fileCreateUser) {
	if (fid) {
		// 附件上传人才可以删除附件,
		if (fileCreateUser ==  currentUserName ){
			$.messager.confirm("操作提示", "是否删除此附件？", function(conf) {
				if (conf) {
					var url = ctx+"/productMaterial/deleteFileByFid";
					var formParam = {
							"fid" : fid
					}		
					asyncCallService(url, 'post', true,'json', formParam, function(returnData){
						if(returnData.code=='0'){
							$.messager.alert("操作提示",returnData.msg,"info",function(){				
								var params = {
					        			"id" : $("#id").val()
					        		}
					        		getProductMaterialInfo(params);
							});
						}else{
							$.messager.alert("操作提示","操作异常！","info");
						}
					});
				}
			});	
		} else {
			$.messager.alert("提示信息","只有附件上传人才可以删除此附件！","info");
		}
			
		
	} else {
		$.messager.alert("提示信息","未匹配到文件ID！","info");
	}
}


function saveOrUpdateProductInfo(){
	var isValid = $('#project_form').form('validate');
	if(isValid){
		var url = ctx+"/productMaterial/saveOrUpdateInfo";
		var formParam = {
				"id":$("#id").val(),
				"projectName":$.trim($("#projectName").val()),
				"model":$.trim($("#model").val()),
				"marketModel":$.trim($("#marketModel").val()),
				"productName":$.trim($("#productName").val()),
				"proNum":$.trim($("#proNum").val()),
				"proType":$.trim($("#proType").val()),
				"proChildType":$.trim($("#proChildType").val()),
				"trademark":$.trim($("#trademark").val()),
				"series":$.trim($("#series").val()),
				"proColor":$.trim($("#proColor").val()),
				"customerName":$.trim($("#customerName").val())
		}
		
		asyncCallService(url, 'post', true,'json', formParam, function(returnData){
			
			if(returnData.code==undefined){
				$.messager.alert("操作提示","项目基本信息保存成功！","info",function(){
					if (returnData.data) {
						$("#id").val(returnData.data);
					} else {
						closeWindow();
					}					
					queryListPage(currentPageNum);
				});
			}else{
				$.messager.alert("操作提示","操作异常！","info");
			}
		});
	}
}

function uploadProductMaterialFile(fileType){
	if ($("#"+fileType).val()) {
		// file框的ID
		if (fileType && $("#id").val()) {
			var submitParameters = {
					"fileType":fileType,
					"id":$("#id").val(),
					"model":$("#model").val(),
					"marketModel":$("#marketModel").val()
			}
			
			$.ajaxFileUpload({
		        url: ctx+"/productMaterial/uploadProductFile", 
		        type: 'post',
		        data: submitParameters,//此参数非常严谨，写错一个引号都不行
		        secureuri: false, //是否启用安全提交，一般设置为false
		        fileElementId: fileType, //文件上传空间的id属性  
		        //dataType: 'json', //返回值类型 一般设置为json，不设置JQ会自动判断
		        success: function (data){  //服务器成功响应处理函数
	        		$.messager.alert("操作提示","文件上传成功！","info");
	        		// 查询相关附件显示
	        		var params = {
	        			"id" : $("#id").val()
	        		}
	        		clearFileValue(fileType);
	        		getProductMaterialInfo(params);
					queryListPage(currentPageNum); 
					
		        },
		        error: function (data, status, e){//服务器响应失败处理函数
		        	$.messager.alert("操作提示","文件上传异常！","info");
		        }
		    });
		} else {
			$.messager.alert("操作提示","请先保存项目基本信息再上传文件","info");
		}
	} else {
		$.messager.alert("操作提示","请选择要上传的文件","info");
	}
	
		
}

/**
 * 获取单个产品资料信息及相关附件
 * @param params
 */
function getProductMaterialInfo(params){
	clearHtmlDiv();
	var url = ctx+"/productMaterial/getProductMaterialById";	
	asyncCallService(url, 'post', false,'json', params, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var fileData = returnData.data.fileList;
			if (fileData.length > 0) {
				$.each(fileData, function(index,item){
					fileAppendHtml(item);
				});						
			}
			appendHtmlToFileDiv();				
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
	
}

//清空删除附件列表
function clearHtmlDiv(){
	dianZiBomHtml = "";
	bomfileHtml = "", //BOM表
	repairfileHtml = "", //维修手册
	differencefileHtml = "", //差异说明
	projectRemarkHtml = "", // 项目备注附件
	softwarefileHtml = "", // 软件
	explainfileHtml = "", //说明书
	kffileHtml = "", // 客服手册
	proseriesfileHtml = "", //产品系列	
	softDiffileHtml = "", //软件功能差异表
	datumfileHtml = "", //产品资料
	huacefileHtml = "", //产品画册
	functionfileHtml = "", // 功能列表
	zhilingfileHtml = "", //指令表
	productRemarkfileHtml = "", //产品信息备注附件
	zuzhuangfileHtml = "", //组装作业指导书
	testfileHtml = "", // 测试作业指导书
	testToolfileHtml = "", //测试工具
	xiehaoToofileHtml = "", //写号工具
	qualityRemarkfileHtml = ""//品质备注附件
	;
}
function fileAppendHtml(fileData){
	var fileType = fileData.fileType;
	var fileUrl = encodeURIComponent(fileData.fileUrl);
	var appendHtml = '<span><a  href="javascript:;" onclick="downLoadFile(\'' + fileUrl + '\', \''+fileData.fileName+'\', \''+ fileType+'\')">' + fileData.fileName + '</a><button id=' + fileData.fid + ' class="panel-tool-close" type="button" onclick="deleteFile(\'' + fileData.fid + '\', \'' + fileData.createUser + '\')" ></button>[附件上传人：'+fileData.createUser +'|'+ fileData.createTime+']<br/></span>';
	if (fileType == "qualityRemarkName") {
		//品质备注附件
		qualityRemarkfileHtml += appendHtml;
	} else if (fileType == "projectRemarkName") {
		//项目备注附件
		projectRemarkHtml += appendHtml;
	} else if (fileType == "differenceName") {
		//差异说明
		differencefileHtml += appendHtml;
	} else if (fileType == "repairName") {
		//维修手册
		repairfileHtml += appendHtml;
	} else if (fileType == "dianzibomName") {
		//电子料BOM表
		dianZiBomHtml += appendHtml;	
	}else if (fileType == "bomName") {
		//BOM表
		bomfileHtml += appendHtml;	
	} else if (fileType == "softDifName") {
		//软件功能差异表		
		softDiffileHtml += appendHtml;
//	} else if (fileType == "proseriesName") {
//		//产品系列
//		proseriesfileHtml += appendHtml;
	} else if (fileType == "kfName") {
		//客服手册
		kffileHtml += appendHtml;
	} else if (fileType == "explainName") {
		//说明书
		explainfileHtml += appendHtml;
	} else if (fileType == "softwareName") {
		//软件
		softwarefileHtml += appendHtml;
	} else if (fileType == "productRemarkName") {
		//产品备注附件
		productRemarkfileHtml += appendHtml;
	} else if (fileType == "zhilingName") {
		//指令表	
		zhilingfileHtml += appendHtml;
	} else if (fileType == "functionName") {
		//功能列表	
		functionfileHtml += appendHtml;
	} else if (fileType == "huaceName") {
		//产品画册
		huacefileHtml += appendHtml;
	} else if (fileType == "datumName") {
		//产品资料
		datumfileHtml += appendHtml;
	}else if (fileType == "xiehaoTooName") {
		//写号工具
		xiehaoToofileHtml += appendHtml;
	} else if (fileType == "testToolName") {
		//测试工具	
		testToolfileHtml += appendHtml;
	} else if (fileType == "testName") {
		//测试作业指导书	
		testfileHtml += appendHtml;
	} else if (fileType == "zuzhuangName") {
		//组装作业指导书
		zuzhuangfileHtml += appendHtml;
	} 
	
}

function appendHtmlToFileDiv(){
	$("#dianZiBomfileInfo").html(dianZiBomHtml);
	$("#bomfileInfo").html(bomfileHtml);
	$("#repairfileInfo").html(repairfileHtml);
	$("#differencefileInfo").html(differencefileHtml);
	$("#projectRemarkfileInfo").html(projectRemarkHtml);
	$("#softwarefileInfo").html(softwarefileHtml);
	$("#explainfileInfo").html(explainfileHtml);
	$("#kffileInfo").html(kffileHtml);
	$("#proseriesfileInfo").html(proseriesfileHtml);
	$("#softDiffileInfo").html(softDiffileHtml);
	$("#datumfileInfo").html(datumfileHtml);
	$("#huacefileInfo").html(huacefileHtml);
	$("#functionfileInfo").html(functionfileHtml);
	$("#zhilingfileInfo").html(zhilingfileHtml);
	$("#productRemarkfileInfo").html(productRemarkfileHtml);
	$("#zuzhuangfileInfo").html(zuzhuangfileHtml);
	$("#testfileInfo").html(testfileHtml);
	$("#testToolfileInfo").html(testToolfileHtml);
	$("#xiehaoToofileInfo").html(xiehaoToofileHtml);
	$("#qualityRemarkfileInfo").html(qualityRemarkfileHtml);
}


// 下载文件
function downLoadFile(fileUrl, fileName, fileType){	
	var url =  ctx + "/productMaterial/downLoadProductFile";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='fileUrl' value='" + fileUrl +"'/>"));
	$form1.append($("<input type='hidden' name='fileName' value='" + fileName +"'/>"));
	$form1.append($("<input type='hidden' name='fileType' value='" + fileType +"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}


function openWindow(){
	windowVisible("infro_window");
	windowCommOpen("infro_window");		
}

function closeWindow(){
	$('#infro_window').window('close');
}

function queryFileList(){
	var selParams = {
		"fileType" : $.trim($("#search_fileType").val()),
		"fileName" : $.trim($("#searchByFileName").val()),
		"projectName" : $.trim($("#projectName").val())
	};
	var url = ctx+"/productMaterial/getProductFileListByProjectNameAndFileType";
	
    $("#file_DataGrid").datagrid('loading');
	asyncCallService(url, 'post', false,'json', selParams, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			
			if(ret&&ret.length>0){  
				$.each(ret,function(i,item){
					if (item.fileType == "bomName") {
						item.fileType = "组装料BOM表";
					} else if (item.fileType == "dianzibomName") {
						item.fileType = "电子料BOM表";
					} else if (item.fileType == "repairName") {
						item.fileType = "维修手册";
					} else if (item.fileType == "differenceName") {
						item.fileType = "差异说明";
					} else if (item.fileType == "projectRemarkName") {
						item.fileType = "备注附件";
					} else if (item.fileType == "softwareName") {
						item.fileType = "软件";
					} else if (item.fileType == "explainName") {
						item.fileType = "说明书";
					} else if (item.fileType == "kfName") {
						item.fileType = "客服手册";
					} else if (item.fileType == "softDifName") {
						item.fileType = "软件功能差异表";
					} else if (item.fileType == "datumName") {
						item.fileType = "产品资料";
					} else if (item.fileType == "huaceName") {
						item.fileType = "产品画册";
					} else if (item.fileType == "functionName") {
						item.fileType = "功能列表";
					} else if (item.fileType == "zhilingName") {
						item.fileType = "指令表";
					} else if (item.fileType == "productRemarkName") {
						item.fileType = "备注附件";
					} else if (item.fileType == "zuzhuangName") {
						item.fileType = "组装作业指导书";
					} else if (item.fileType == "testName") {
						item.fileType = "测试作业指导书";
					} else if (item.fileType == "testToolName") {
						item.fileType = "测试工具";
					} else if (item.fileType == "xiehaoTooName") {
						item.fileType = "写号工具";
					} else if (item.fileType == "qualityRemarkName") {
						item.fileType = "备注附件";
					}
				});
			}
			$("#file_DataGrid").datagrid('loadData',ret);  
		}else {
			$("#file_DataGrid").datagrid('loadData', {total: 0, rows: []});
			$.messager.alert("操作提示",returnData.msg, "info");
		}
		$("#file_DataGrid").datagrid('loaded');	
	}, function(){
		$("#file_DataGrid").datagrid('loaded');	
	 	$.messager.alert("操作提示","网络错误","info");
	});
}

function openFileListWindow(){
	$("#searchByFileName").val("");
	queryFileList();
	windowVisible("choose_w");
	windowCommOpen("choose_w");	
}

function closFileListWindow(){
	$("#search_fileType").val("");
	$("#searchByFileName").val("");
	$('#choose_w').window('close');
}

function selectFileSave(){
	var rows = $("#file_DataGrid").datagrid('getSelections');
	if(rows.length > 0){
		$.messager.confirm("操作提示", "是否保存选择？", function(conf) {
			if (conf) {
				var ids = "";
				for (var i = 0; i < rows.length; i++) {
					(i == 0) ? ids = rows[i].fid : ids = rows[i].fid + "," + ids;
				}
				var url = ctx+"/productMaterial/chooseProductFileListByFid";
				var formParam = {
						"fids" : ids,
						"pid" : $("#id").val()
				}
				asyncCallService(url, 'post', true,'json', formParam, function(returnData){
					if(returnData.code=='0'){
						$.messager.alert("操作提示",returnData.msg,"info",function(){	
							closFileListWindow();
							var params = {
				        		"id" : $("#id").val()
				        	}
				        	getProductMaterialInfo(params);
						});
					}else{
						$.messager.alert("操作提示","操作异常！","info");
					}
				});
//				$.messager.alert("操作提示","已选择"+ rows.length +"条数据","info");
			}
		});	
	}else{
		$.messager.alert("操作提示","未选中数据！","info");
	}
}
