//////////////// 物料管理JS
var temp_CPL_selectIds;     	//临时存储的Ids
var temp_CPL_selectName;    	//临时存储的名称

var currentPageNum_CPL;
var temp_model;//备选型号选中的主板型号

$(function(){
	 keySearch(queryListPageCPL,3);
	 $("#otherModel").combobox({//备选型号
		onChange: function (newValue, oldValue ) {
			temp_model = newValue;
		}
	});
});
/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetCPL(size){
 var p = $("#mat_DataGrid").datagrid('getPager');  
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
     						queryListPageCPL(pageNumber);   
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
function getPageSizeCPL(){
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
	        	pageSetCPL(returnData.data);
	        	$("#finallyFault").val("");
	        	$("#cplModal").val("CPL");
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
function resetCurrentPageCPL(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_CPL=1;
	}
	$('#mat_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_CPL
	});
}

/*------获取维修组件 start-------------*/

/**
 * 筛选数据组装
 */
function getSelParams() {
	var param = "proNO=" + $.trim($("#s_proNO_cpl").val())
			 +"&proName="+ $.trim($("#s_proName_cpl").val())
			 +"&searchType=modelSearch"
			 +"&currentpage="+currentPageNum_CPL; ;
	 if(temp_model){
		 param += "&model=" + temp_model;
	 }else{
		 param += "&model=" + $.trim($("#repair_w_model").val());
	 }
	return param;
}

function queryListPageCPL(pageNumber){
	currentPageNum_CPL=pageNumber;
	if(currentPageNum_CPL=="" || currentPageNum_CPL==null){
		currentPageNum_CPL=1;
	}
	var url=ctx+"/pjlmanage/pjlmanageList";
    var param=getSelParams(); 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#mat_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeCPL();
			resetCurrentPageCPL(currentPageNum_CPL);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

//获得所有备选型号，默认选中本身
function getAllOherModel(){
	var url=ctx+"/pjlmanage/getAllOherModel";
    var param = "model="+$.trim($("#repair_w_model").val()); 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			var ret = [];
			var w_model = "";
			var diclist = '[';
			$.each(returnData.data, function(i, item) {
				diclist += '{"id":"' + item + '","text":"' + item + '"},';
				if(item == $.trim($("#repair_w_model").val())){
					w_model = $.trim($("#repair_w_model").val());
				}
			});
			var reg = /,$/gi;
			diclist = diclist.replace(reg, "");
			diclist += ']';
			$('#otherModel').combobox({
				data : eval('(' + diclist + ')'),
				valueField : 'id',
				textField : 'text',
				onLoadSuccess : function(data) {
					if (data) {
						$('#otherModel').combobox('setValue', w_model);
					}
				}
			});
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

//配件料添加按钮事件
function doinsertMat() {
	windowVisible("mat_w");
	$("#s_proNO_cpl").val("");
	$("#s_proName_cpl").val("");
	queryListPageCPL(1);
	getAllOherModel();
	$("#selected-type-box-CPL").empty();
	
	temp_CPL_selectIds  = $("#repair_w_matId").val();    
	temp_CPL_selectName = $("#repair_w_matDesc").val();    
	
	//初始化所选择的配件料
	var names = temp_CPL_selectName.split(" | ");
	var ids   = temp_CPL_selectIds.split(",");
	
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			var id   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_CPLId' value="+id+"><input type='hidden' name='win_CPLname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-CPL").append(htmlDatas);
	}
	
	ClearCPLFunctionMethon();
	
	windowCommOpen("mat_w");
}

/*-------------- 选择维修组件 start-----------------*/
function dbClickChooseMat(index,value){
	var Falg  = true;
	
	var ids   = temp_CPL_selectIds.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.pjlId){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-CPL").append("<span class='tag'>"+value.proName+"<input type='hidden' name='win_CPLId' value="+value.pjlId+"><input type='hidden' name='win_CPLname' value="+value.proName+"><button class='panel-tool-close'></button></span>");
		if(temp_CPL_selectIds!=""){
			temp_CPL_selectIds  = temp_CPL_selectIds +","+value.pjlId;
		}else{
			temp_CPL_selectIds = value.pjlId;
		}
		if(temp_CPL_selectName!=""){
			temp_CPL_selectName = temp_CPL_selectName+" | "+value.proName;
		}else{
			temp_CPL_selectName = value.proName;
		}
		ClearCPLFunctionMethon();
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
}

function getPjlByWxbj(){
	var url=ctx+"/repairPrice/getPjlByWxbjId";
	var param="ids="+$.trim($("#repair_w_wxbjId").val());  
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			var ret = returnData.data;
			$.each(ret, function(i, item) {
				$("input[name='win_CPLId'][value='" + item.pjlId + "']").parent().find(".panel-tool-close").attr("style","display:none;");
			});
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 保存数据
 */
function sysSelectDatasCPL(){
	var ids    =""; 
	var names  =""; 
	var prices =0*1;
	var ourlyRates = 0*1;
	
	$("input[name='win_CPLId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_CPLname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});
	
	temp_CPL_selectIds   = ids;    
	temp_CPL_selectName  = names; 
	
	getPjlByWxbj();//维修报价选中的配件料不允许删除
}

/**
 * 初始化清除和删除方法
 */
function ClearCPLFunctionMethon(){
	sysSelectDatasCPL();
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		$(this).parent().remove();
		sysSelectDatasCPL();
	});
	$(".selected-type .clear-select-btn").unbind('click').click(function(){
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		
		var url=ctx+"/repairPrice/getPjlByWxbjId";
		var param="ids="+$.trim($("#repair_w_wxbjId").val());  
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				var ret = returnData.data;
				$(this).parents(".selected-type").find(".selected-type-box").empty();
				$.each(ret, function(i, item) {
					$("#selected-type-box-CPL").append("<span class='tag'>"+item.proName+"<input type='hidden' name='win_CPLId' value="+item.pjlId+"><input type='hidden' name='win_CPLname' value="+item.proName+"><button class='panel-tool-close' style='display:none;'></button></span>");
				});
			}
		});
		sysSelectDatasCPL();
	});
}

/**
 * 配件料保存
 */
function CPLSave(){
	sysSelectDatasCPL();
	$("#repair_w_matId").val(temp_CPL_selectIds);    
	$("#repair_w_matDesc").val(temp_CPL_selectName);    
	
	pjl_w_close();
}

function pjl_w_close(){
	temp_model = "";
	windowCommClose('mat_w');
}
/*---------获取维修组件 end---------*/