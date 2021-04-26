var currentPageNum;
var currentUserName;
$(function(){	
	initEvent();
	initData();	
	
});

function initData(){
	$("#DataGrid1").datagrid({
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			interiorFileUpdate(rowIndex,row);
		}
	}); 
	queryListPage(1);
	
	currentUserName = $("#currentName").val();
}

function initEvent(){
	 keySearch(queryListPage);
	 
	 $("#searchinfo").click(function(){
		 queryListPage(1);
	 });
}

function queryListPage(pageNumber){
	
	currentPageNum=pageNumber;
	if(currentPageNum == "" || currentPageNum == null){
		currentPageNum = 1;
	} 
	
	var pageSize = getCurrentPageSize('DataGrid1');
	var searchCreateUser = $("#searchCreateUser").val();
	var searchFileName = $("#searchFileName").val();
	var selParams = {
			"updateUser" : searchCreateUser,
			"fileName" : searchFileName,
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
	};
	var url = ctx+"/interiorMaterial/interiorMaterialList";
	
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', selParams, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			$.each(ret, function(index, item){
				if (item.fileName){
					var fileUrl = encodeURIComponent(item.fileUrl);
					item.fileName = '<span class = "fileInfo"><a  href="javascript:;" onclick="downLoadFile(\'' + fileUrl + '\', \''+item.fileName+'\')">'+item.fileName+'</a></span>';
				}
			});
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

function interiorFileAdd () {
	openWindow();
	$("#infro_window").panel({title:"新增内部材料"});
	$("#fileSpan").show();
	$("#fileInfoDiv").html("");
	$("#interiorFile").val("");	
	$("#id").val("");
	$("#fileCreateUser").val(currentUserName);
	$("#fileTime").val(getCurrentDateTime());
	$("#fileTitle").html('<label class="font_color">*</label>上传文件：');
}

function interiorFileUpdate(index,entity){
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

function addEntityValue(entity){
	openWindow();
	$("#fileTitle").html('下载文件：');
	$("#infro_window").panel({title:"修改内部资料"});
	if (entity.fileUrl && entity.fileName) {
		var fileName = entity.fileName.substring(entity.fileName.indexOf(")\">") + 3 , entity.fileName.indexOf("</a></span>"));
		$("#fileSpan").hide();
		$("#interiorFile").val("");
		var fileUrl = encodeURIComponent(entity.fileUrl);
		var fileHtml = '<span>' 
			+'<a href="javascript:;" onclick="downLoadFile(\'' + fileUrl + '\', \'' + fileName + '\');" >' + fileName + '</a>'
			+'<br/>'+ fileName
			+'<button id= \'' + entity.id + '\' class="panel-tool-close" type="button" onclick="deleteFile(\'' + entity.id + '\', \'' + entity.createUser + '\');" >'
			+'</button><br/></span>';
		$("#fileInfoDiv").html(fileHtml);
		$("#fileCreateUser").val(entity.createUser);
		$("#fileTime").val(entity.createTime);		
	} else {
		$("#fileSpan").show();
		$("#fileInfoDiv").html("");
		$("#interiorFile").val("");	
		$("#fileCreateUser").val(currentUserName);
		$("#fileTime").val(getCurrentDateTime());
	}	
	$("#id").val(entity.id);
}

//下载文件
function downLoadFile(fileUrl, fileName){	
	var url =  ctx + "/interiorMaterial/downLoadInteriorFile";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='fileUrl' value='" + fileUrl +"'/>"));
	$form1.append($("<input type='hidden' name='fileName' value='" + fileName +"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

//删除文件
function deleteFile (id, fileCreateUser) {
	if (id) {
		// 附件上传人才可以删除附件,
		if (fileCreateUser ==  currentUserName ){
			$.messager.confirm("操作提示", "是否删除此附件？", function(conf) {
				if (conf) {
					var url = ctx+"/interiorMaterial/saveOrUpdateInfo";
					var formParam = {
							"id" : id,
							"fileUrl" : "",
							"fileName" : ""
					}		
					asyncCallService(url, 'post', true,'json', formParam, function(returnData){
						if(returnData.code=='0'){
							$("#fileTitle").html('<label class="font_color">*</label>上传文件：');
							$("#fileSpan").show();
							$("#fileInfoDiv").html("");							
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
			$.messager.alert("提示信息","只有附件上传人才可以删除此附件！","info");
		}			
	} else {
		$.messager.alert("提示信息","未匹配到文件ID！","info");
	}
}

//删除数据
function interiorFileDelete(){
	var entity = $('#DataGrid1').datagrid('getSelections');
	if (entity.length == 1){
		// 项目创建人，才能删除,
		if (entity[0].createUser == currentUserName ) {
			$.messager.confirm("操作提示", "是否删除此数据？", function(conf) {
				if (conf) {
					//后台删除
					var url = ctx+"/interiorMaterial/deleteInteriorMaterialById";
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
			$.messager.alert("提示信息","只有创建人才可以删除此数据！","info");
		}					
	}else if(entity.length == 0){
		$.messager.alert("提示信息","请先选择所要删除的行！","info");
	}else{
		$.messager.alert("提示信息","请先选择一行进行删除！","info");
	}
}


function saveOrUpdateInteriorInfo () {
	if ($("#interiorFile").val()) {
		var formParam = {
				"id":$("#id").val()
		}		
		$.ajaxFileUpload({
	        url: ctx+"/interiorMaterial/saveOrUpdateInfo", 
	        type: 'post',
	        data: formParam,//此参数非常严谨，写错一个引号都不行
	        secureuri: false, //是否启用安全提交，一般设置为false
	        fileElementId: "interiorFile", //文件上传空间的id属性  
	        //dataType: 'json', //返回值类型 一般设置为json，不设置JQ会自动判断
	        success: function (data){  //服务器成功响应处理函数
        		$.messager.alert("操作提示","文件上传成功！","info");
        		closeWindow();
        		queryListPage(currentPageNum); 				
	        },
	        error: function (data, status, e){//服务器响应失败处理函数
	        	$.messager.alert("操作提示","文件上传异常！","info");
	        }
	    });
	}else if ($("#fileInfoDiv").html()) {
		closeWindow();
	} else {
		$.messager.alert("操作提示","请选择要上传的文件","info");
	}
}

function openWindow(){
	windowVisible("infro_window");
	windowCommOpen("infro_window");		
}

function closeWindow(){
	$('#infro_window').window('close');
}