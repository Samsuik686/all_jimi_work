///////////////////// 选择最终故障JQ
var temp_ZZGZ_selectIds;     //临时存储的Ids
var temp_ZZGZ_selectName;    //临时存储的名称

var currentPageNum_ZZGZ;

$(function(){
	 keySearch(queryListPageZZGZ,1);
});
/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetZZGZ(size){
 $('.panel-tool').hide();
 var p = $("#zzgz_DataGrid").datagrid('getPager');  
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
     						queryListPageZZGZ(pageNumber);   
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
function getPageSizeZZGZ(){
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
	        	pageSetZZGZ(returnData.data);
	        	$("#finallyFault").val("Finally");
	        	$("#cplModal").val("");
	        	$("#dzlModal").val("");
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
function resetCurrentPageZZGZ(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_ZZGZ=1;
	}
	$('#zzgz_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_ZZGZ
	});
}


/*---------获取最终故障 start-----------*/
function queryListPageZZGZ(pageNumber){
	currentPageNum_ZZGZ=pageNumber;
	if(currentPageNum_ZZGZ=="" || currentPageNum_ZZGZ==null){
		currentPageNum_ZZGZ=1;
	}
	var url=ctx+"/zgzmanagecon/manageList";
    var param="methodNO="+$.trim($("#methodNO").val())
    		  +"&proceMethod="+$.trim($("#proceMethod").val())
//    		  +"&modelType="+$.trim($("#repair_modelType").val())//型号类别
    		  +"&gId="+$("#gIds").val()
    		  +"&showType=repair"
    		  +"&currentpage="+currentPageNum_ZZGZ; 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$.each(returnData.data, function(i, item) {
				if (item.isSyncSolution == 1) {
					item.isSyncSolution = '是';
				} else if (item.isSyncSolution == 0) {
					item.isSyncSolution = '否';
				}
			});
			$("#zzgz_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeZZGZ();
 			resetCurrentPageZZGZ(currentPageNum_ZZGZ);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/*-------获取最终故障 end--------------*/

/*-----------------选择最终故障 start--------------*/
function repair_openZZGZ(){
	$("#methodNO").val("");
	$("#proceMethod").val("");
	defaultSelectFirst_ZZGZ();
	windowCommOpen("zzgz_w");
}

//最终故障默认选中第一个
function defaultSelectFirst_ZZGZ() {
	$('#typeTree-ZZGZ').tree({
		onLoadSuccess : function(node, data) {
			if (data.length > 0) {
				var n = $("#typeTree-ZZGZ").tree('find', data[0].id);
				if (n != null) {
					$("#typeTree-ZZGZ").tree("select", n.target);
					$("#gIds").val("");
					queryListPageZZGZ(1);
				}
			}
		}
	});
}

//最终故障添加按钮事件
function doinsertZZGZ()
{
	windowVisible("zzgz_w");
	queryListPageZZGZ(1);
	$("#selected-type-box-ZZGZ").empty();
	
	temp_ZZGZ_selectIds  = $("#repair_w_zzgzId").val();    
	temp_ZZGZ_selectName = $("#repair_w_zzgzDesc").val();    
	
	//初始化所选择的最终故障
	var names = temp_ZZGZ_selectName.split("|");
	var ids   = temp_ZZGZ_selectIds.split(",");
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			var id   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_ZZGZId' value="+id+"><input type='hidden' name='win_ZZGZname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-ZZGZ").append(htmlDatas);
	}
	ClearZZGZFunctionMethon();
	
//	windowCommOpen("zzgz_w");
	repair_openZZGZ();
	
}

function dbClickChooseZZGZ(index,value){
	var Falg  = true;
	var ids   = temp_ZZGZ_selectIds.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.id){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-ZZGZ").append("<span class='tag'>"+value.proceMethod+"<input type='hidden' name='win_ZZGZId' value="+value.id+"><input type='hidden' name='win_ZZGZname' value="+value.proceMethod+"><button class='panel-tool-close'></button></span>");
		if(temp_ZZGZ_selectIds!=""){
			temp_ZZGZ_selectIds  = temp_ZZGZ_selectIds +","+value.id;
		}else{
			temp_ZZGZ_selectIds = value.id;
		}
		if(temp_ZZGZ_selectName!=""){
			temp_ZZGZ_selectName = temp_ZZGZ_selectName+" | "+value.proceMethod;
		}else{
			temp_ZZGZ_selectName = value.proceMethod;
		}
		ClearZZGZFunctionMethon();
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
}


/**
 * 保存数据
 */
function sysSelectDatasZZGZ(){
	var ids    =""; 
	var names  =""; 
	
	$("input[name='win_ZZGZId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_ZZGZname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});
	temp_ZZGZ_selectIds   = ids;    
	temp_ZZGZ_selectName  = names; 
}
/**
 * 初始化清除和删除方法
 */
function ClearZZGZFunctionMethon(){
	sysSelectDatasZZGZ();
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		$(this).parent().remove();
		sysSelectDatasZZGZ();
	});
	$(".selected-type .clear-select-btn").click(function(){
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasZZGZ();
	});
}

/**
 * 最终故障保存
 */
function ZZGZSave(){
	sysSelectDatasZZGZ();
	$("#repair_w_zzgzId").val(temp_ZZGZ_selectIds);
	$("#repair_w_zzgzDesc").val(temp_ZZGZ_selectName);
	windowCommClose("zzgz_w");
	var subString_ret = $.trim($("#repair_stateFlag").val());
	if (subString_ret && ("待报价" == subString_ret || "已分拣,待维修" == subString_ret)) {
		if($.trim($("#repair_w_zzgzDesc").val())){
			$(".repair_beforeHide").show();
		}else{
			$(".repair_beforeHide").hide();
		}
	}
	getSolutionTwo();
}



function queryList(pageNumber){
	currentPageNum_ZZGZ=pageNumber;
	if(currentPageNum_ZZGZ=="" || currentPageNum_ZZGZ==null){
		currentPageNum_ZZGZ=1;
	}
	
	var url=ctx+"/zgzmanage/getList";
    var param="proceMethod="+$("#faultName").val()
    		  +"&gId="+$("#gIds").val()
    		  +"&currentpage="+currentPageNum_ZZGZ; 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#zzgz_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeZZGZ();
 			resetCurrentPageZZGZ(currentPageNum_ZZGZ);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/*---------------选择最终故障 end-------------------*/