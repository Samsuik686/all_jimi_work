<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!------------- 翻页功能   < 一 >----------------->
<input type="hidden" name="total" value="">
<input type="hidden" name="currentpage" value="1">


<!------------- 翻页功能  < 二 >-----------------> 
<input type="hidden" name="totalTwo" value=""> 
<input type="hidden" name="currentpageTwo" value="1">

<!------------- 翻页功能 --------------------------->

<script type="text/javascript"> 
var currPageSize = 20;
var windowCurrPageSize = 10;
 function pageSet(total){
   var p = $('#DataGrid1').datagrid('getPager');
   $(p).pagination(
    			{  
					    total:total,  
					    pageList:true,
					    showPageList:true,
					    pageList: [10,20,30,50],
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
        					queryListPage(pageNumber);   
       						$(this).pagination('loaded'); 
    					},
       					onChangePageSize:function(pageSize){ 
					       $(this).pagination('loading');
       						currPageSize = pageSize;
					       $(this).pagination('loaded'); 
    
    			   }  
    }); 
  
 }
  function getPageSize(){
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
	        	pageSet(returnData.data);
	        },   
	       error:function(){
			   $.messager.alert("操作提示","网络错误","info");
			   return 0;
		   }
     });  
 }
      
      
  // ***********************为实现一个页面中，第二个翻页功能 ********************************//
  function pageSetTwo(DataGrid,total){
    var p = $('#'+DataGrid+'').datagrid('getPager');
    $(p).pagination(
    				{  
					    total:total,  
					    pageList:true,
					    showPageList:true,
					    pageList: [10,20,30,50],
					    showRefresh:true,
					    beforePageText: '第',
					    afterPageText: '页    共 {pages} 页',  
					    displayMsg: '   共 {total} 条记录',  
	        			onBeforeRefresh:function(){ 	        				
	    					$(this).pagination('loading'); 
	       					$(this).pagination('loaded'); 
	    				}, 
       					onSelectPage:function(pageNumber, pageSize, total){ 
      						 $(this).pagination('loading'); 
        					 queryListPageTwo(pageNumber); 
       						 $(this).pagination('loaded'); 
    					},
       					onChangePageSize:function(pageSize){ 
       						$(this).pagination('loading');
       						windowCurrPageSize = pageSize;
      	 					$(this).pagination('loaded'); 
    
    					}  
    					}); 
 	}
  	
   function getPageSizeTwo(DataGrid){
	  $.ajax({ 
	        type:"GET",      
	        async:false,    
	        cache:false,    
	       	url:ctx+"/form/queryCurrentPageSizeTwo.api",    
	        dataType:"json",
	        success:function(returnData){	
	        	if(returnData.code!=null){
	        		$.messager.alert("操作提示","系统繁忙，请稍候在试!","info");
	        		return 0;
	        	}
	        	pageSetTwo(DataGrid,returnData.data); 
	        },   
	       error:function(){
			   $.messager.alert("操作提示","网络错误","info");
			   return 0;
		   }
	   });  
	}
   
   function resetCurrentPageShowT(DataGrid,currentPageNumTwo) {
		if (currentPageNumTwo == "" || currentPageNumTwo == null) {
			currentPageNumTwo = 1;
		}
		$('#'+DataGrid+'').datagrid('getPager').pagination({
			pageNumber : currentPageNumTwo
		});
	}
   
   
   /**
    * 添加分页参数
    * @param DataGrid	DataGrid ID
    * @param currentPage 当前页
    * @param param		传递参数
    * @auth  huangwm
    */
   function addPageParam(DataGrid,currentPage,param){	
	   var dg = $('#'+DataGrid+'').datagrid('options');
	   var pageSize = 20;
	   if(DataGrid == 'DataGrid1'){
		   // 一级页面的table
		   pageSize =  currPageSize;
	   }else{
		   // window 弹框的table
		   pageSize = windowCurrPageSize;	   
	   }
	   if(param !=null && param.length >0){
		   param = param +"&currentpage=" + currentPage + "&pageSize="+pageSize;
	   }else{
		   param = "currentpage=" + currentPage + "&pageSize="+pageSize;
	   }

	   return param;
   }
   
   function getCurrentPageSize(DataGrid){
	   var pageSize = 20;
	   if (DataGrid == 'DataGrid1') {
		   // 一级页面的table
		   pageSize =  currPageSize;
	   } else {
		   pageSize = windowCurrPageSize;	   
	   }
	   return pageSize;
   }
   </script>

