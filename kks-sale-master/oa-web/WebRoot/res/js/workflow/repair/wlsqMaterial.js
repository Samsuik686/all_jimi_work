////////////// 物料JS
 var currentPageNum_WlSQ;
 var allRows = new Array();
 $(function(){
	 keySearch(queryListPageMaterial,5);
 });
/**
 * 获取当前分页参数
 * @param size		数据源
 * @param DataGrid	DataGrid ID
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
 function pageSetWlSQ(size){
  var p = $("#WLSQ_MaterialDataGrid").datagrid('getPager');  
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
				queryListPageMaterial(pageNumber);   
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
 function getPageSizeWlSQ(){
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
	        	pageSetWlSQ(returnData.data);
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
 function resetCurrentPageWlSQ(currentPageNum){
 	if(currentPageNum=="" || currentPageNum==null){
 		currentPageNum_WlSQ=1;
 	}
 	$('#WLSQ_MaterialDataGrid').datagrid('getPager').pagination({
 		 pageNumber:currentPageNum_WlSQ
 	});
 }

 function accept_openWlSQ(){
	 $("#s_proNO").val("");
	 $("#s_proName").val("");
	 windowCommOpen("w_material");
	 queryListPageMaterial(1);
}

 
 /**
  * 选择物料信息
  */
 function chooseMaterial(){
	windowVisible("w_material");
	queryListPageMaterial(1);
	accept_openWlSQ();
 	
 }
 
 
 /**
  * 物料List
  */
 function queryListPageMaterial(pageNumber){
 	currentPageNum_WlSQ = pageNumber;
 	if(currentPageNum_WlSQ=="" || currentPageNum_WlSQ==null){
 		currentPageNum_WlSQ=1;
 	}
 	var url=ctx;
 	var matType = $("#matType").combobox('getValue');
 	if(matType == 0){
 		url += "/pjlmanage/pjlmanageList";
 	}else if(matType == 1){
 		url += "/dzlmanage/dzlmanageList";
 	}
 	var param="proNO=" +$.trim($("#s_proNO").val())
 			  +"&proName="+$.trim($("#s_proName").val())
 			  +"&currentpage="+currentPageNum_WlSQ;
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			$("#WLSQ_MaterialDataGrid").datagrid('loadData', returnData.data);
 			getPageSizeWlSQ();
 			resetCurrentPageWlSQ(currentPageNum_WlSQ);
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
 function dbClickChooseMaterial(){
	 var ret = $('#WLSQ_MaterialDataGrid').datagrid('getSelected');
	 if(ret){
		 $("#proSpeci").val(ret.proSpeci);
		 $("#proNO").val(ret.proNO);
		 $("#proName").val(ret.proName);
		 w_materialClose(); 
	 }else{
		 $.messager.alert("提示信息", "请先选择一行操作！", "warning");
	 }
 }
 
 function w_materialClose(){
 	$('#w_material').window('close');
 }

 function w_materialOpen()
 {
	windowVisible("w_material");
 	$(".validatebox-tip").remove();
 	$(".validatebox-invalid").removeClass("validatebox-invalid");
 	$('#w_material').window('open');
 	
 }
 