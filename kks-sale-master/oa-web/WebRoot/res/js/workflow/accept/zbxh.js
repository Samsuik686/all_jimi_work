////////////// 主板型号JS
 var currentPageNum_ZBXH;
 $(function(){
	 keySearch(queryListPageZBXH,5);
	 
 });
/**
 * 获取当前分页参数
 * @param size		数据源
 * @param DataGrid	DataGrid ID
 */
 function pageSetZBXH(size){
  var p = $("#DataGrid-Zbxh").datagrid('getPager');  
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
				queryListPageZBXH(pageNumber);   
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
 	$('#DataGrid-Zbxh').datagrid('getPager').pagination({
 		 pageNumber:currentPageNum_ZBXH
 	});
 }

 
 /**
  * 选择主板型号
  */
 function chooseZbxh(rowindex){
 	windowVisible("zbsc");
 	$("#hideSindex").val(rowindex);
 	$("#zbxh_rowindex").val(rowindex);
// 	windowCommOpen('zbsc');
 	accept_openZBXH(rowindex);
	
 }
 
 
 /**
  * 主板型号List
  */
 function queryListPageZBXH(pageNumber,index){
 	var currentPageNum_ZBXH = pageNumber;
 	if(currentPageNum_ZBXH=="" || currentPageNum_ZBXH==null){
 		currentPageNum_ZBXH=1;
 	}
 	var url=ctx+"/zbxhmanage/zbxhmanageList";
 	var param="model=" +$.trim($("#searchBymodel").val()) + "&currentpage="+currentPageNum_ZBXH + "&showType=accept";
 	//非修改页面修改主板型号
 	if(index != -1){
 		param +="&marketModelAccept=" + $.trim($("#searchByMarketmodel-"+index).val());
 	}
 	
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			$.each(returnData.data, function(i, item) {
				if (item.enabledFlag == 0) {
					item.enabledFlag = '<label style="color:green;font-weight: bold;">否</label>';
				} else if (item.enabledFlag == 1) {
					item.enabledFlag = '<label style="color:red;font-weight: bold;">是</label>';
				}
			});
 			$("#DataGrid-Zbxh").datagrid('loadData',returnData.data); 
 			getPageSizeZBXH();
 			resetCurrentPageZBXH(currentPageNum_ZBXH);
 			if(index && index != -1){
 				var tipModel = "";
 				if($("#searchByMarketmodel-"+index).val()){
 					if($("#ams_model-"+index).val()){
 						tipModel = ",主板型号是：【" + $("#ams_model-"+index).val() + "】";
 					}
 				}
 				if($("#DataGrid-Zbxh").datagrid('getRows').length >=1){
 					var market_row=$("#DataGrid-Zbxh").datagrid('getRows')[0].marketModel;
 					if(!market_row){
 						$.messager.alert("操作提示","未匹配到市场型号是：【" +$("#searchByMarketmodel-"+index).val() + "】" + tipModel + " 的数据，请增加主板型号管理数据","info",function(){
 							$("#searchBymodel").val("");
 						});
 					}
 				}else{
 					$.messager.alert("操作提示","未匹配到市场型号是：【" +$("#searchByMarketmodel-"+index).val() + "】" + tipModel + " 的数据，请增加主板型号管理数据","info",function(){
 						$("#searchBymodel").val("");
 					});
 				}
 			}
 			$("#searchBymodel").focus();//查询输入框获得焦点
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }
 
 function accept_openZBXH(rowindex){
	$("#searchBymodel").val("");
	windowCommOpen('zbsc');
	queryListPageZBXH(1,rowindex);
}


 /**
  * 双击选择主板型号
  */
 function dbClickChooseZbxh(index,value) {
	var rowindex=$("#zbxh_rowindex").val();
	
 	var getCurrentId=value.mid;
 	var param="mId="+getCurrentId;
 	var url=ctx+"/zbxhmanage/getZbxhmanage";
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			var entity=returnData.data;
 			if(rowindex){//批量受理
 				var TABLE_Cell =$("#hideSindex").val();
 				if(TABLE_Cell==null || TABLE_Cell==""){
 				}else{
 					$("#model-"+TABLE_Cell).val(entity.model);
 					$("#marketModel-"+TABLE_Cell).val(entity.marketModel); 
 					$("#xhId-"+TABLE_Cell).val(entity.mid);
 					zbxh_window_close(rowindex);
 				}
 			}else{//修改受理页面的主板型号
 				$("#fm_xhId").val(entity.mid);
 				$("#fm_w_model").val(entity.model);
 				$("#fm_w_marketModel").val(entity.marketModel);
 				
 				$("#searchBymodel").val("");
 				windowCommClose('zbsc');
 			}
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }
 
 /**
  * 市场型号一致时
  * 复制主板型号，给后面的列赋相同的值
  */
 function copyModel(rowindex){
 	var selectIds  =$("#xhId-"+rowindex).val();
 	var selectName =$("#model-"+rowindex).val();
 	var selectMarketModel =$("#marketModel-"+rowindex).val();
 	if(selectMarketModel){
 		$("input[name='xhId']").each(function() {
 			var id = $(this).attr("id");
 			var strs= new Array(); //定义一数组
 			strs=id.split("-"); //字符分割 
 			if(strs[1] > rowindex && $("#marketModel-"+strs[1]).val() == selectMarketModel){
 				//给先删除，后新增的行赋值
 				$("#xhId-"+strs[1]).val(selectIds);
 				$("#model-"+strs[1]).val(selectName);
 				zbxh_window_close(strs[1]);//保内、保外
 			}
 		});
 	}
 }
 
 /**
  * 修改受理页面的主板型号
  * 
  */
 function updateZbxh(){
	 windowVisible("zbsc");
	$("#searchBymodel").val("");
	windowCommOpen('zbsc');
	queryListPageZBXH(1, -1);
 }
 
 