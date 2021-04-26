var currentPageNum_Pj;

$(function(){
//	 keySearch(queryListPagePj,3);
});
/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetPj(size){
 var p = $("#choosepj_DataGrid").datagrid('getPager');  
  $(p).pagination({  
	    total:size,  
	    pageList:true,
	  /* pageSize:pageSize,  */ 
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
				queryListPagePj(pageNumber);   
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
function getPageSizePj(){
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
        	pageSetPj(returnData.data);
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
function resetCurrentPagePj(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_Pj=1;
	}
	$('#choosepj_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_Pj
	});
}

function getCurrentPageSizeByChoosepj(DataGrid){
	   var pageSize = 30;
	   return pageSize;
}
