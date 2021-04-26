<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
 /**
  * 获取当前分页参数
  * @param size		数据源
  * @param DataGrid	DataGrid ID
  * @author Li.Shangzhi
  * @Date 2016-09-06 11:02:00
  */
  function pageSetComm(size,DataGrid){
   var p = $('#'+DataGrid).datagrid('getPager');  
    $(p).pagination(
    				{  
					    total:size,  
					    pageList:true,
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
        					queryListPage(pageNumber);   
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
  function getPageSizeComm(DataGrid){
	  $.ajax({ 
	        type:"GET",     
	        async:false,    
	        cache:false,    
	       	url:ctx+"/form/queryCurrentPageSize.api",    
	        dataType:"json",
	        success:function(returnData){	
	        	if(returnData.code!=null){
	        		$.messager.alert("操作提示","系统繁忙，请稍候再试!","info");
	        		return 0;
	        	}
	        	pageSetComm(returnData.data,DataGrid);
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
  function resetCurrentPageComm(currentPageNum,DataGrid){
  	if(currentPageNum=="" || currentPageNum==null){
  		currentPageNum=1;
  	}
  	$('#'+DataGrid).datagrid('getPager').pagination({
  		 pageNumber:currentPageNum
  	});
  }
   
   /**
    * 添加分页参数
    * @param param		传递参数
    * @param DataGrid	DataGrid ID
    * @auth  huangwm
    */
   function addPageParam(DataGrid,param){
	   var dg = $("#DataGrid1").datagrid('options');
	   var pageSize = dg.pageSize;
	   if(param !=null && param.length >0){
		   param +="&currentpage=" + currentPageNum + "&pageSize="+pageSize;
	   }else{
		   param += "currentpage=" + currentPageNum + "&pageSize="+pageSize;
	   }
	   return param;
   }
   
   /**
    * 添加分页参数
    * @param DataGrid	DataGrid ID
    * @param currentPage 当前页
    * @param param		传递参数
    * @auth  huangwm
    */
   function addPageParam(DataGrid,currentPage,param){
	   var dg = $("#DataGrid1").datagrid('options');
	   var pageSize = dg.pageSize;
	   if(param !=null && param.length >0){
		   param +="&currentpage=" + currentPage + "&pageSize="+pageSize;
	   }else{
		   param += "currentpage=" + currentPage + "&pageSize="+pageSize;
	   }
	   return param;
   }
</script>

