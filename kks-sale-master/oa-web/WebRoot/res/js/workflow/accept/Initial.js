///// 选择初检故障 JQ 

var temp_GZ_selectIds;    //临时存储的Ids
var temp_GZ_selectName;   //临时存储的名称
var currentPageNum_CJGZ;

$(function(){
	 keySearch(queryCCGZListPage,2);
});

/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetCJGZ(size){
 $('.panel-tool').hide();
 var p = $("#table_gz").datagrid('getPager');  
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
     						queryCCGZListPage(pageNumber);   
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
function getPageSizeCJGZ(){
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
	        	pageSetCJGZ(returnData.data);
	        	$("#customerModal").val("");
	        	$("#faultModal").val("CJGZ");
	        	$("#fileModal").val("");
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
function resetCurrentPageCJGZ(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_CJGZ=1;
	}
	$('#table_gz').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_CJGZ
	});
}



/**
 * 查询初检故障
 */
function queryCCGZListPage(pageNumber)
{
	currentPageNum_CJGZ=pageNumber;
	if(currentPageNum_CJGZ=="" || currentPageNum_CJGZ==null){
		currentPageNum_CJGZ=1;
	}
	
	var url=ctx+"/cjgzmanage/CjgzmanageList";
    var param="gId="+$("#gIds-CJGZ").val()
    			//未禁用
    		   +"&available=1"
    		   +"&currentpage="+currentPageNum_CJGZ
    		   +"&description="+$.trim($("#name1").val());
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$("#table_gz").datagrid('loadData',returnData.data); 
			getPageSizeCJGZ();
 			resetCurrentPageCJGZ(currentPageNum_CJGZ);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");  
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

function accept_openCJGZ(){
	$("#name1").val("");
	defaultSelectFirst_CJGZ();
	windowCommOpen('wgz');
}

//初检故障默认选中第一个
function defaultSelectFirst_CJGZ() {
	$('#typeTree-CCGZ').tree({
		onLoadSuccess : function(node, data) {
			if (data.length > 0) {
				var n = $("#typeTree-CCGZ").tree('find', data[0].id);
				if (n != null) {
					$("#typeTree-CCGZ").tree("select", n.target);
					$("#gIds-CJGZ").val("");
					queryCCGZListPage(1);
				}
			}
		}
	});
}

/**
 * 选择初检故障
 */
function chooseCjgz(rowindex){
	windowVisible("wgz");
	queryCCGZListPage();
	var names = null;
	var ids   = null;
	$("#selected-type-box-GZ").empty();
	if(rowindex!=undefined){
		$("#hideSindex").val(rowindex);
		temp_GZ_selectIds  =$("#cjgzId-"+rowindex).val();
		temp_GZ_selectName =$("#initheckFault-"+rowindex).val();
		
		//TODO 获取原先数据
		names = temp_GZ_selectName.split("|");
		ids   = temp_GZ_selectIds.split(",");
	}else{
		temp_GZ_selectIds   = $("#fm_w_cjgzId").val();
		temp_GZ_selectName  = $("#fm_w_initheckFault").val();
		
		
		names = temp_GZ_selectName.split("|");
		ids   = temp_GZ_selectIds.split(",");
	}
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var name = names[int];
			var id   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+name+"<input type='hidden' name='win_GZId' value="+id+"><input type='hidden' name='win_GZname' value="+name+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-GZ").append(htmlDatas);
	}
	
	ClearGZFunctionMethon();
	//打开窗口
//	windowCommOpen('wgz');
	accept_openCJGZ()
}


/**
 * 双击选择初选故障
 */
function dbClickChooseCJGZ(index,value){
	//TODO 获取原先数据
	var ids   = temp_GZ_selectIds.toString().split(",");
	var Falg  = true;
	
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.iid){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-GZ").append("<span class='tag'>"+value.initheckFault+"<input type='hidden' name='win_GZId' value="+value.initheckFault+"><input type='hidden' name='win_GZname' value="+value.initheckFault+"><button class='panel-tool-close'></button></span>");
		if(temp_GZ_selectIds!=""){
			temp_GZ_selectIds  = temp_GZ_selectIds +","+value.iid;
		}else{
			temp_GZ_selectIds = value.iid;
		}
		if(temp_GZ_selectName!=""){
			temp_GZ_selectName = temp_GZ_selectName+" | "+value.initheckFault;
		}else{
			temp_GZ_selectName = value.initheckFault;
		}
		ClearGZFunctionMethon();
		InitalSave();//双击选中后关闭
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
}
function InitalSave(){
	var TABLE_Cell =$("#hideSindex").val();
	//附件ID
	if(TABLE_Cell==null || TABLE_Cell==""){          //非扫描处理
	    $("#fm_w_cjgzId").val(temp_GZ_selectIds);
	    $("#fm_w_initheckFault").val(temp_GZ_selectName);
	}else{
		$("#cjgzId-"+TABLE_Cell).val(temp_GZ_selectIds);
		$("#initheckFault-"+TABLE_Cell).val(temp_GZ_selectName);		
	}
	windowCommClose("wgz");
}


/**
 * 复制初检查故障，给后面的列赋相同的值
 */
function copyCjgz(rowindex)
{
	var selectIds  =$("#cjgzId-"+rowindex).val();
	var selectName =$("#initheckFault-"+rowindex).val();
	$("input[name='cjgzId']").each(function() {
		var id = $(this).attr("id");
 		var strs= new Array(); //定义一数组
 		strs=id.split("-"); //字符分割 
		if(strs[1]>rowindex){
			//给先删除，后新增的行赋值
			$("#cjgzId-"+strs[1]).val(selectIds);
			$("#initheckFault-"+strs[1]).val(selectName);
 		}
	});
}

/**
 * 保存数据
 */
function sysSelectDatasGZ(){
	var ids   =""; 
	var names =""; 
	
	$("input[name='win_GZId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_GZname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});

	temp_GZ_selectIds  = ids;    
	temp_GZ_selectName = names;  
}

/**
 * 初始化清除和删除方法
 */
function ClearGZFunctionMethon(){
	$(".selected-type-box>.tag> .panel-tool-close").click(function(){
		$(this).parent().remove();
		sysSelectDatasGZ();
	});
	$(".selected-type .clear-select-btn").click(function(){
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		sysSelectDatasGZ();
	});
}



