////////////// 物料JS
 var currentPageNum_LOG;
 var allRows = new Array();
 $(function(){
	 keySearch(queryLogMatListPage,5);
 });
/**
 * 获取当前分页参数
 * @param size		数据源
 * @param DataGrid	DataGrid ID
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
 function pageSetLog(size){
  var p = $("#log_MaterialDataGrid").datagrid('getPager');  
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
				queryLogMatListPage(pageNumber);   
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
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
 function getPageSizeLog(){
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
	        	pageSetLog(returnData.data);
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
  * @author Li.Shangzhi
  * @Date 2016-09-06 11:01:19
  */
 function resetCurrentPageLog(currentPageNum){
 	if(currentPageNum=="" || currentPageNum==null){
 		currentPageNum_LOG=1;
 	}
 	$('#log_MaterialDataGrid').datagrid('getPager').pagination({
 		 pageNumber:currentPageNum_LOG
 	});
 }

 function accept_openLog(){
	 $("#log_proNO").val("");
	 $("#log_proName").val("");
	 windowCommOpen("log_material");
	 queryLogMatListPage(1);
}

 
 /**
  * 选择物料信息
  */
 function log_chooseMaterial(){
	windowVisible("log_material");
	queryLogMatListPage(1);
	accept_openLog();
 }
 
 
 /**
  * 物料List
  */
 function queryLogMatListPage(pageNumber){
 	currentPageNum_LOG = pageNumber;
 	if(currentPageNum_LOG=="" || currentPageNum_LOG==null){
 		currentPageNum_LOG=1;
 	}
 	var url=ctx;
 	var matType = $("#materialTypei").combobox('getValue');
 	if(matType == 0){
 		url += "/pjlmanage/pjlmanageList";
 	}else if(matType == 1){
 		url += "/dzlmanage/dzlmanageList";
 	}
 	var param="proNO=" +$.trim($("#log_proNO").val())
 			  +"&proName="+$.trim($("#log_proName").val())
 			  +"&currentpage="+currentPageNum_LOG;
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			$("#log_MaterialDataGrid").datagrid('loadData', returnData.data);
 			getPageSizeLog();
 			resetCurrentPageLog(currentPageNum_LOG);
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }

 /**
  * 双击选择物料
  */
 function log_dbClickChooseMaterial(){
	 var ret = $('#log_MaterialDataGrid').datagrid('getSelected');
	 if(ret){
		 $("#proSpeci").val(ret.proSpeci);
		 $("#proNOi").val(ret.proNO);
		 $("#proNamei").val(ret.proName);
		 $("#marketModeli").val(ret.marketModel);
		 $("#placesNOi").val(ret.placesNO);
		 $("#consumption").val(ret.consumption);
		 $("#retailPrice").val(ret.retailPrice);
		 log_materialClose(); 
	 }else{
		 $.messager.alert("提示信息", "请先选择一行操作！", "warning");
	 }
 }
 
 function log_materialClose(){
 	$('#log_material').window('close');
 }

 function log_materialOpen(){
	windowVisible("log_material");
 	$(".validatebox-tip").remove();
 	$(".validatebox-invalid").removeClass("validatebox-invalid");
 	$('#log_material').window('open');
 }
 