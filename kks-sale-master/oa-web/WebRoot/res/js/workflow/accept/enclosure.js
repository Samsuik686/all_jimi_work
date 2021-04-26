////// 随机附件 JQ 

var temp_sjfj_selectIds;    //临时存储的Ids
var temp_sjfj_selectName;   //临时存储的名称
var currentPageNum_SJFJ;

$(function(){
	 keySearch(queryListPageSJFJ,3);
});
/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetSJFJ(size){
 $('.panel-tool').hide();
 var p = $("#table_sjfj").datagrid('getPager');  
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
     						queryListPageSJFJ(pageNumber);   
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
function getPageSizeSJFJ(){
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
	        	pageSetSJFJ(returnData.data);
	        	$("#customerModal").val("");
	        	$("#faultModal").val("");
	        	$("#fileModal").val("SJFJ");
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
function resetCurrentPageSJFJ(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_SJFJ=1;
	}
	$('#table_sjfj').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_SJFJ
	});
}

function accept_openSJFJ(){
	$("#name").val("");
	defaultSelectFirst_SJFJ();
	windowCommOpen('wjs');
}

//随机附件默认选中第一个
function defaultSelectFirst_SJFJ() {
	$('#typeTree').tree({
		onLoadSuccess : function(node, data) {
			if (data.length > 0) {
				var n = $("#typeTree").tree('find', data[0].id);
				if (n != null) {
					$("#typeTree").tree("select", n.target);
					$("#gIds-SJFJ").val("");
					queryListPageSJFJ(1);
				}
			}
		}
	});
}

/**
 * 选择随机附件
 */
function chooseSjfj(rowindex)
{
	windowVisible("wjs");
	queryListPageSJFJ();
	var names = null;
	var ids   = null;
	$("#selected-type-box-FJ").empty();
	
	if(rowindex != undefined){
		$("#hideSindex").val(rowindex);
		temp_sjfj_selectName  =$("#sjfjname-"+rowindex).val();
		temp_sjfj_selectIds =$("#sjfjId-"+rowindex).val();
		
		//TODO 获取原先数据
		names  = temp_sjfj_selectName.split("|"); 
		ids    = temp_sjfj_selectIds.split(","); 
		
	}else{
		temp_sjfj_selectIds   = $("#fm_w_sjfjId").val();
		temp_sjfj_selectName  = $("#fm_w_sjfjname").val();
		
		
		names = temp_sjfj_selectName.split("|");
		ids   = temp_sjfj_selectIds.split(",");
	}
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			var id   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_sjfjId' value="+id+"><input type='hidden' name='win_sjfjname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-FJ").append(htmlDatas);
	}
	
	ClearSJFJFunctionMethon();
	//打开窗口
//	windowCommOpen('wjs');
	accept_openSJFJ();
}

/**
 * 查询随机附件
 */
function queryListPageSJFJ(pageNumber)
{
	currentPageNum_SJFJ=pageNumber;
	if(currentPageNum_SJFJ=="" || currentPageNum_SJFJ==null){
		currentPageNum_SJFJ=1;
	}
	var url=ctx+"/sjfjmanage/sjfjmanageList";
    var param="gId="+$("#gIds-SJFJ").val()+"&name="+$.trim($("#name").val())
    		 +"&currentpage="+currentPageNum_SJFJ;
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$("#table_sjfj").datagrid('loadData',returnData.data);  
			getPageSizeSJFJ();
 			resetCurrentPageSJFJ(currentPageNum_SJFJ);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 双击选择随机附件
 */
function dbClickChooseSJFJ(index,value)
{
	var Falg  = true;
	var ids   = temp_sjfj_selectIds.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.eid){
				Falg = false ;
				break ;
			}
		}
	}
	
	if(Falg){
		$("#selected-type-box-FJ").append("<span class='tag'>"+value.name+"<input type='hidden' name='win_sjfjId' value="+value.eid+"><input type='hidden' name='win_sjfjname' value="+value.name+"><button class='panel-tool-close'></button></span>");
		if(temp_sjfj_selectIds!=""){
			temp_sjfj_selectIds  = temp_sjfj_selectIds +","+value.eid;
		}else{
			temp_sjfj_selectIds = value.eid;
		}
		if(temp_sjfj_selectName!=""){
			temp_sjfj_selectName = temp_sjfj_selectName+" | "+value.name;
		}else{
			temp_sjfj_selectName = value.name;
		}
		ClearSJFJFunctionMethon();
		enclosureSave();//双击选中后关闭
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
	
}
/**
 * 随机附件保存
 */
function enclosureSave(){
	var TABLE_Cell =$("#hideSindex").val();
	//附件ID
	if(TABLE_Cell==null || TABLE_Cell==""){          //非扫描处理
		sysSelectDatasSJFJ();
		$("#fm_w_sjfjId").val(temp_sjfj_selectIds);
		$("#fm_w_sjfjname").val(temp_sjfj_selectName);
	}else{
		sysSelectDatasSJFJ();
		$("#sjfjId-"+TABLE_Cell).val(temp_sjfj_selectIds);
		$("#sjfjname-"+TABLE_Cell).val(temp_sjfj_selectName);
	}
	windowCommClose("wjs");
}


/**
 * 保存数据
 */
function sysSelectDatasSJFJ(){
	var ids   =""; 
	var names =""; 
	
	$("input[name='win_sjfjId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_sjfjname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});
 
	temp_sjfj_selectIds  = ids;    
	temp_sjfj_selectName = names;  
}
/**
 * 初始化清除和删除方法
 */
function ClearSJFJFunctionMethon(){
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		sysSelectDatasSJFJ();
		$(this).parent().remove();
	});
	$(".selected-type .clear-select-btn").click(function(){
		sysSelectDatasSJFJ();
		$(this).parents(".selected-type").find(".selected-type-box").empty();
	});
}

/**
 * 复制随机附件，给后面的列赋相同的值
 */
function copySjfj(rowindex) {
	var selectIds  =$("#sjfjId-"+rowindex).val();
	var selectName =$("#sjfjname-"+rowindex).val();
	$("input[name='sjfjId']").each(function() {
		var id = $(this).attr("id");
 		var strs= new Array(); //定义一数组
 		strs=id.split("-"); //字符分割 
		if(strs[1]>rowindex){
			//先删除，后新增的行赋值
			$("#sjfjId-"+strs[1]).val(selectIds);
			$("#sjfjname-"+strs[1]).val(selectName);
 		}
	});
}
