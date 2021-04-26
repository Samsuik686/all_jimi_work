////////////// 主板型号JS
 var currentPageNum_ZBXH;
 $(function(){
	 keySearch(queryZBXHPage,3);
	 
 });
/**
 * 获取当前分页参数
 * @param size		数据源
 * @param DataGrid	DataGrid ID
 */
 function pageSetZBXH(size){
  var p = $("#DataGrid_chooseZBXH").datagrid('getPager');  
   $(p).pagination(
		{  
		    total:size,  
		    pageList:true,
		    pageSize:pageCount,    
		    showPageList:false,   
		    showRefresh:false,
		    beforePageText: '第',
		    afterPageText: '页    共 {pages} 页',  
		    displayMsg: '   共 {total} 条记录',  
			onBeforeRefresh:function(){ 
				$(this).pagination('loading'); 
				$(this).pagination('loaded'); 
			},
			onSelectPage:function(pageNumber, pageSize, total){ 
				$(this).pagination('loading'); 
				queryZBXHPage(pageNumber);   
				$(this).pagination('loaded'); 
			},
			onChangePageSize:function(pageNumber, pageSize){ 
		       $(this).pagination('loading'); 
		       $(this).pagination('loaded'); 
   		 }  
     }); 
 }

/**
 * 获取缓存分页参数
 * @param DataGrid    DataGrid ID
 */
 function getPageSizeZBXH(){
	  $.ajax({ 
	        type:"GET",     
	        async:false,    
	        cache:false,    
	       	url:ctx+"/form/queryCurrentPageSize.api",    
	        dataType:"json",
	        success:function(returnData){	
	        	if(returnData.code!=null){
	        		$.messager.alert("操作提示","系统繁忙，请稍候在试!","info");
	        		return 0;
	        	}
	        	pageSetZBXH(returnData.data);
	        },   
	       error:function(){
			   $.messager.alert("操作提示","网络错误","info");
			   return 0;
		   }
    }); 
	  
 }
 
 /**
  * 当当前页处于>1时，点击某条件查询按钮后，分页栏还是未同步当前页码
  * @param currentPageNum	当前页码
  * @param DataGrid		DataGrid ID
  */
 function resetCurrentPageZBXH(currentPageNum){
 	if(currentPageNum=="" || currentPageNum==null){
 		currentPageNum_ZBXH=1;
 	}
 	$('#DataGrid_chooseZBXH').datagrid('getPager').pagination({
 		 pageNumber:currentPageNum_ZBXH
 	});
 }
 
 /**
  * 选择主板型号
  */
 function chooseZbxh(){
		windowVisible("w_chooseZBXH");
		$("#searchBymodel").val("");
		windowCommOpen('w_chooseZBXH');
		queryZBXHPage(1);
	}
 
 
 /**
  * 主板型号List
  */
 function queryZBXHPage(pageNumber,index){
 	var currentPageNum_ZBXH = pageNumber;
 	if(currentPageNum_ZBXH=="" || currentPageNum_ZBXH==null){
 		currentPageNum_ZBXH=1;
 	}
 	var url=ctx+"/zbxhmanage/zbxhmanageList";
 	var param="model=" + $.trim($("#searchBymodel").val()) + "&currentpage=" + currentPageNum_ZBXH + "&showType=product";
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			$.each(returnData.data, function(i, item) {
				if (item.enabledFlag == 0) {
					item.enabledFlag = '<label style="color:green;font-weight: bold;">否</label>';
				} else if (item.enabledFlag == 1) {
					item.enabledFlag = '<label style="color:red;font-weight: bold;">是</label>';
				}
				if (item.createType == 0) {
					item.createType = '<label style="color:green;font-weight: bold;">默认</label>';
				} else if (item.createType == 1) {
					item.createType = '<label style="color:red;font-weight: bold;">产品</label>';
				}
			});
 			$("#DataGrid_chooseZBXH").datagrid('loadData',returnData.data); 
 			getPageSizeZBXH();
 			resetCurrentPageZBXH(currentPageNum_ZBXH);
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }

 /**
  * 双击选择主板型号
  */
 function dbClickChooseZbxh(index,value) {
	 var ret = $('#DataGrid_chooseZBXH').datagrid('getSelected');
	 if(ret){
		 $("#model").val(ret.model);
		 $("#marketModel").val(ret.marketModel);
		 $("#proType").val(ret.modelType);
		 closeChooseZBXH(); 
	 }else{
		 $.messager.alert("提示信息", "请先选择一行操作！", "warning");
	 }
 }
 
 // 关闭选择主板型号
 function closeChooseZBXH(){
 	$('#w_chooseZBXH').window('close');
 }
 //新增主板型号
 function insertZbXH(){
	$("#zbxh_mId").val(0);
	$("#zbxh_model").val("");
	$("#zbxh_marketModel").val("");
	$("#zbxh_remark").val("");
	 insertZBXHWindowOpen();
	 
	 comboboxInit();
	}
 
 // 打开新增主板型号
 function insertZBXHWindowOpen(){
	windowVisible("w_zbxh");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$(".updateHide").removeAttr("disabled");
	$("#type").combobox({disabled: false});
	$('#w_zbxh').window('open');
 }
 
 //保存
 function zbxhSave() {
	var isValid = $('#zbxhform').form('validate');
	if(isValid){
		var model= $.trim($("#zbxh_model").val().toUpperCase());
		if (/[\u4E00-\u9FA5]/i.test(model)) {
			$.messager.alert("操作提示","主板型号不能输入中文","info");
		    return;
		}
		var mId= $.trim($("#zbxh_mId").val());
		var marketModel= $.trim($("#zbxh_marketModel").val().toUpperCase());
		var gId= $("#type").combobox('getValue');
		var modelType= $("#type").combobox('getText');
		var remark= $.trim($("#zbxh_remark").val());
		var reqAjaxPrams={
			"mId" : mId,	
			"model" : model,
			"gId" : gId,
			"marketModel" : marketModel,
			"modelType" : modelType,
			"remark" : remark,
			"createType" : "1",
			"enabledFlag" : "1"
		}
		processSaveAjax(reqAjaxPrams, 0);
	}
 }

 function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/zbxhmanage/addOrUpdateZbxhmanage";
	var id = $("#mId_hidden").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			closeZbxhSave();
			if (id > 0) {
				queryZBXHPage(currentPageNum_ZBXH);// 重新加载List
			}else{
				queryZBXHPage(1);// 重新加载List
			}
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
 }
	
 // 关闭新增主板型号
 function closeZbxhSave(){
	 $('#w_zbxh').window('close');
 }
 
 // 打开修改窗口
 function updateZbXHWindowOpen(){
	windowVisible("w_zbxh");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$(".updateHide").attr("disabled","disabled");
	$("#type").combobox({ disabled: true })
	$('#w_zbxh').window('open'); 
 }
 
 function updateZbXH() {
		var row = $('#DataGrid_chooseZBXH').datagrid('getSelected');
		if (row) {
			var param = "mId=" + row.mid;
			var url = ctx + "/zbxhmanage/getZbxhmanage";
			asyncCallService(url, 'post', false, 'json', param, function(returnData) {
				processSSOOrPrivilege(returnData);
				$("#zbxh_mId").val(returnData.data.mid);
				$("#zbxh_model").val(returnData.data.model.toUpperCase());
				$("#zbxh_marketModel").val(returnData.data.marketModel.toUpperCase());
				$("#type").combobox('setValue', returnData.data.gId);
				$("#type").combobox('setText', returnData.data.modelType);
				$("#zbxh_remark").val(returnData.data.remark);
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
			updateZbXHWindowOpen();
//			chageWinSize('updateWindow');
		} else {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		}
	}
 
//类型下拉框的公用方法
 function comboboxInit() {
 	var url = ctx + '/basegroupcon/queryList';
 	var param = "enumSn=BASE_ZBXH";
 	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
 		var diclist = '[';
 		$.each(returnData.data, function(i, j) {
 			diclist += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
 		});
 		var reg = /,$/gi;
 		diclist = diclist.replace(reg, "");
 		diclist += ']';
 		$('#type').combobox({
 			data : eval('(' + diclist + ')'),
 			valueField : 'id',
 			textField : 'text',
 			onLoadSuccess : function(data) {
 				if (data) {
 					$('#type').combobox('setValue', data[0].id);
 				}
 			}
 		});
 	}, function() {
 		$.messager.alert("操作提示", "网络错误", "info");
 	});
 }