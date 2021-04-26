//////////////// 物料管理JS
var temp_DZL_selectIds;     				//临时存储的Ids
var temp_DZL_selectName;    				//临时存储的名称

var currentPageNum_DZL;
$(function(){
	 keySearch(queryListPageDZL,4);
});

/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetDZL(size){
 var p = $("#dzl_DataGrid").datagrid('getPager');  
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
     						queryListPageDZL(pageNumber);   
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
function getPageSizeDZL(){
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
	        	pageSetDZL(returnData.data);
	        	$("#finallyFault").val("");
	        	$("#cplModal").val("");
	        	$("#dzlModal").val("DZL");
	        	$("#wxbjModal").val("");
	        	$("#sllModal").val("");
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
function resetCurrentPageDZL(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_DZL=1;
	}
	$('#dzl_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_DZL
	});
}


/*------获取维修组件 start-------------*/
/**
 * 筛选数据组装
 */
function getSelParamsDZL(){
	var param = "proNO="+ $.trim($("#s_proNO_dzl").val())
				+"&gId="+$("#gIds-DZL").val()
				+"&proName=" + $.trim($("#s_proName_dzl").val())
				+"&showType=repair"
				+ "&currentpage="+currentPageNum_DZL;
	return param;
}

function queryListPageDZL(pageNumber){
	currentPageNum_DZL=pageNumber;
	if(currentPageNum_DZL=="" || currentPageNum_DZL==null){
		currentPageNum_DZL=1;
	}
	var url=ctx+"/dzlmanage/dzlmanageList";
    var param=getSelParamsDZL(); 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#dzl_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeDZL();
			resetCurrentPageDZL(currentPageNum_DZL);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

function repair_openDZL(){
	$("#s_proNO_dzl").val("");
	$("#s_proName_dzl").val("");
	defaultSelectFirst_DZL();
	windowCommOpen("dzl_w");
}

//电子料分类默认选中第一个
function defaultSelectFirst_DZL() {
	$('#typeTree-DZL').tree({
		onLoadSuccess : function(node, data) {
			if (data.length > 0) {
				var n = $("#typeTree-DZL").tree('find', data[0].id);
				if (n != null) {
					$("#typeTree-DZL").tree("select", n.target);
					$("#gIds-DZL").val("");
					queryListPageDZL(1);
				}
			}
		}
	});
}

//电子料添加按钮事件
function doinsertDzl() {
	windowVisible("dzl_w");
	queryListPageDZL(1);
	$("#selected-type-box-DZL").empty();
	
	temp_DZL_selectIds  = $("#repair_w_dzlId").val();    
	temp_DZL_selectName = $("#repair_w_dzlDesc").val();    
	
	//初始化所选择的最终故障
	var names = temp_DZL_selectName.split("|");
	var ids   = temp_DZL_selectIds.split(",");
	
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			var id   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_DZLId' value="+id+"><input type='hidden' name='win_DZLname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-DZL").append(htmlDatas);
	}
	
	ClearDZLFunctionMethon();
	
	repair_openDZL();
	
//	windowCommOpen("dzl_w");
}

///*-------------- 选择维修组件 start-----------------*/
function dbClickChooseDZL(index,value){
	var Falg  = true;
	
	var ids   = temp_DZL_selectIds.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.dzlId){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-DZL").append("<span class='tag'>"+value.proName+"<input type='hidden' name='win_DZLId' value="+value.dzlId+"><input type='hidden' name='win_DZLname' value="+value.proName+"><button class='panel-tool-close'></button></span>");
		if(temp_DZL_selectIds!=""){
			temp_DZL_selectIds  = temp_DZL_selectIds +","+value.dzlId;
		}else{
			temp_DZL_selectIds = value.dzlId;
		}
		if(temp_DZL_selectName!=""){
			temp_DZL_selectName = temp_DZL_selectName+" | "+value.proName;
		}else{
			temp_DZL_selectName = value.proName;
		}
		ClearDZLFunctionMethon();
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
}


/**
 * 保存数据
 */
function sysSelectDatasDZL(){
	var ids    =""; 
	var names  =""; 
	
	$("input[name='win_DZLId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_DZLname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});
	
	temp_DZL_selectIds   = ids;    
	temp_DZL_selectName  = names; 
}

/**
 * 初始化清除和删除方法
 */
function ClearDZLFunctionMethon(){
	sysSelectDatasDZL();
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		$(this).parent().remove();
		sysSelectDatasDZL();
	});
	$(".selected-type .clear-select-btn").click(function(){
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasDZL();
	});
}

/**
 * 电子料保存
 */
function DZLSave(){
	sysSelectDatasDZL();
	$("#repair_w_dzlId").val(temp_DZL_selectIds);    
	$("#repair_w_dzlDesc").val(temp_DZL_selectName);    

	windowCommClose("dzl_w");
}

/*---------获取维修组件 end---------*/