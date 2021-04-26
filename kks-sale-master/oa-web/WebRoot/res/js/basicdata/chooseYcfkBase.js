////////////// 异常反馈基础JS
 var currentPageNum_YCFK;
 var allRows = new Array();
 $(function(){
	 keySearch(queryYcfkBaseList,1);
 });
/**
 * 获取当前分页参数
 * @param size		数据源
 * @param DataGrid	DataGrid ID
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
 function pageSetYCFK(size){
  var p = $("#ycfkBaseDataGrid").datagrid('getPager');  
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
				queryYcfkBaseList(pageNumber);   
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
 function getPageSizeYCFK(){
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
	        	pageSetYCFK(returnData.data);
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
 function resetCurrentPageYCFK(currentPageNum){
 	if(currentPageNum=="" || currentPageNum==null){
 		currentPageNum_YCFK=1;
 	}
 	$('#ycfkBaseDataGrid').datagrid('getPager').pagination({
 		 pageNumber:currentPageNum_YCFK
 	});
 }

 function accept_openYCFK(){
	 $("#proDetailsSearch").val("");
	 windowCommOpen("w_ycfkBase");
	 defaultSelectFirst_YCFK();
	 queryYcfkBaseList(1);
}

 
 /**
  * 选择异常反馈基础信息
  */
 function chooseYcfk(){
	windowVisible("w_ycfkBase");
	queryYcfkBaseList(1);
	accept_openYCFK();
 	
 }
 
 
 /**
  * 异常反馈基础List
  */
 function queryYcfkBaseList(pageNumber){
 	currentPageNum_YCFK = pageNumber;
 	if(currentPageNum_YCFK=="" || currentPageNum_YCFK==null){
 		currentPageNum_YCFK=1;
 	}
 	var url = ctx + "/ycfkBase/ycfkBaseList";
	var param = "gId=" + $.trim($("#gIds").val()) + "&proDetails=" + $.trim($("#proDetailsSearch").val()) + "&currentpage="+currentPageNum_YCFK;
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			$("#ycfkBaseDataGrid").datagrid('loadData', returnData.data);
 			getPageSizeYCFK();
 			resetCurrentPageYCFK(currentPageNum_YCFK);
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }
 
 

 /**
  * 双击选择异常反馈基础
  */
 function dbClickChooseYcfkBase(){
	 var ret = $('#ycfkBaseDataGrid').datagrid('getSelected');
	 if(ret){
		 $("#yidi").val(ret.yid);
		 $("#problemsi").val(ret.problems);
		 $("#descriptioni").val(ret.proDetails);
		 $("#measuresi").val("");
		 $("#hideMethod").val(ret.methods);
		 
		 $("#completionDatei").val("");
		 $("input[name='completionState'][value='0']").prop("checked", true);
		 w_ycfkBaseClose(); 
	 }else{
		 $.messager.alert("提示信息", "请先选择一行操作！", "warning");
	 }
 }
 
 function w_ycfkBaseClose(){
 	$('#w_ycfkBase').window('close');
 }

 function w_ycfkBaseOpen(){
	windowVisible("w_ycfkBase");
 	$(".validatebox-tip").remove();
 	$(".validatebox-invalid").removeClass("validatebox-invalid");
 	$('#w_ycfkBase').window('open');
 }
 /**------------------------------------------------------------- Tree异常反馈 Start----------------------------------------------------**/
 function groupTreeInit_YCFK(){
 	$.ajax({
 		type : "POST",
 		async : false,
 		cache : false,
 		url : ctx + '/basegroupcon/queryList',
 		data : "enumSn=BASE_YCFK",
 		dataType : "json",
 		success : function(returnData) {
 			treeLoadAgin_YCFK(returnData);
 		},
 		error : function() {
 			$.messager.alert("操作提示", "网络错误", "info");
 		}
 	});
 }
 function treeLoadAgin_YCFK(returnData) {
 	var groupList = '[{"id":"","text":"所有类别",children:[';
 	;
 	$.each(returnData.data, function(i, j) {
 		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
 	});
 	var reg = /,$/gi;
 	groupList = groupList.replace(reg, "");
 	groupList += ']}]';
 	$('#typeTree-YCFK').tree(
 		 {
 			data : eval(groupList),
 			onClick : function(node){
 				$("#gIds").val(node.id);
 				queryYcfkBaseList(1);
 			}
 		 }
      );
 }
 
//最终故障默认选中第一个
 function defaultSelectFirst_YCFK() {
 	$('#typeTree-YCFK').tree({
 		onLoadSuccess : function(node, data) {
 			if (data.length > 0) {
 				var n = $("#typeTree-YCFK").tree('find', data[0].id);
 				if (n != null) {
 					$("#typeTree-YCFK").tree("select", n.target);
 					$("#gIds").val("");
 					queryYcfkBaseList(1);
 				}
 			}
 		}
 	});
 }
 /**------------------------------------------------------------- Tree异常反馈 End-----------------------------------------------------  **/
 