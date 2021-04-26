//////////////// 物料管理JS
var temp_SLL_selectMatNO;     				//临时存储的编码
var temp_SLL_selectName;    				//临时存储的名称

var currentPageNum_SLL;
$(function(){
	 keySearch(queryListPageSLL,6);
});

/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
*/
function pageSetSLL(size){
 var p = $("#sll_DataGrid").datagrid('getPager');  
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
     						queryListPageSLL(pageNumber);   
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
function getPageSizeSLL(){
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
	        	pageSetSLL(returnData.data);
	        	$("#finallyFault").val("");
	        	$("#cplModal").val("");
	        	$("#dzlModal").val()
	        	$("#wxbjModal").val("");
	        	$("#sllModal").val("SLL");
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
function resetCurrentPageSLL(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_SLL=1;
	}
	$('#sll_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_SLL
	});
}


/*------获取维修组件 start-------------*/
/**
 * 筛选数据组装
 */
function getSelParamsSLL(){
	var param = "proNO="+ $.trim($("#s_proNO_sll").val()) 
				+"&proName=" + $.trim($("#s_proName_sll").val())
				+"&bill=" + $.trim($("#repair_bill").val())
				+ "&currentpage="+currentPageNum_SLL;
	return param;
}

function queryListPageSLL(pageNumber){
	currentPageNum_SLL=pageNumber;
	if(currentPageNum_SLL=="" || currentPageNum_SLL==null){
		currentPageNum_SLL=1;
	}
	var url=ctx+"/testMaterial/testMaterialList";
    var param=getSelParamsSLL(); 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#sll_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeSLL();
			resetCurrentPageSLL(currentPageNum_SLL);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

//试流料添加按钮事件
function doinsertSLL() {
	windowVisible("sll_w");
	$("#s_proNO_sll").val("");
	$("#s_proName_sll").val("");
	queryListPageSLL(1);
	$("#selected-type-box-SLL").empty();
	
	temp_SLL_selectMatNO  = $("#repair_w_sllMatNO").val();    
	temp_SLL_selectName = $("#repair_w_sllDesc").val();    
	
	//初始化所选择的最终故障
	var names = temp_SLL_selectName.split("|");
	var ids   = temp_SLL_selectMatNO.split(",");
	
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			var id   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_SLLId' value="+id+"><input type='hidden' name='win_SLLname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-SLL").append(htmlDatas);
	}
	
	ClearSLLFunctionMethon();
	
	windowCommOpen("sll_w");
}

///*-------------- 选择组件 start-----------------*/
function dbClickChooseSLL(index,value){
	var Falg  = true;
	
	var ids   = temp_SLL_selectMatNO.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.materialNumber){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-SLL").append("<span class='tag'>"+value.materialName+"<input type='hidden' name='win_SLLId' value="+value.materialNumber+"><input type='hidden' name='win_SLLname' value='"+value.materialName+"'><button class='panel-tool-close'></button></span>");
		if(temp_SLL_selectMatNO!=""){
			temp_SLL_selectMatNO  = temp_SLL_selectMatNO +","+value.materialNumber;
		}else{
			temp_SLL_selectMatNO = value.materialNumber;
		}
		
		if(temp_SLL_selectName!=""){
			temp_SLL_selectName = temp_SLL_selectName+" | "+value.materialName;
		}else{
			temp_SLL_selectName = value.materialName;
		}
		ClearSLLFunctionMethon();
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
}


/**
 * 保存数据
 */
function sysSelectDatasSLL(){
	var ids    =""; 
	var names  =""; 
	
	$("input[name='win_SLLId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_SLLname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});
	
	temp_SLL_selectMatNO   = ids;    
	temp_SLL_selectName  = names; 
}

/**
 * 初始化清除和删除方法
 */
function ClearSLLFunctionMethon(){
	sysSelectDatasSLL();
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		$(this).parent().remove();
		sysSelectDatasSLL();
	});
	$(".selected-type .clear-select-btn").click(function(){
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasSLL();
	});
}

/**
 * 试流料保存
 */
function SLLSave(){
	sysSelectDatasSLL();
	$("#repair_w_sllMatNO").val(temp_SLL_selectMatNO);    
	$("#repair_w_sllDesc").val(temp_SLL_selectName);    

	windowCommClose("sll_w");
}

/*---------获取组件 end---------*/