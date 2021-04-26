///////////////////// 选择维修报价JQ
var temp_Wxbj_selectIds;     //临时存储的Ids
var temp_Wxbj_selectName;    //临时存储的名称

var currentPageNum_Wxbj;
var pjlIds_fin;//配件料最终保存的ids
var pjlDesc_fin;//配件料最终保存的名称

$(function(){
	 keySearch(queryListPageWxbj,2);
});
/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
* @author Li.Shangzhi
* @Date 2016-09-06 11:02:00
*/
function pageSetWxbj(size){
 $('.panel-tool').hide();
 var p = $("#wxbj_DataGrid").datagrid('getPager');  
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
     						queryListPageWxbj(pageNumber);   
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
function getPageSizeWXBJ(){
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
	        	pageSetWxbj(returnData.data);
	        	$("#wxbjModal").val("WXBJ");
	        	$("#finallyFault").val("");
	        	$("#cplModal").val("");
	        	$("#dzlModal").val("");
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
function resetCurrentPageWxbj(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_Wxbj=1;
	}
	$('#wxbj_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_Wxbj
	});
}


/*---------获取维修报价start-----------*/
function queryListPageWxbj(pageNumber){
	currentPageNum_Wxbj=pageNumber;
	if(currentPageNum_Wxbj=="" || currentPageNum_Wxbj==null){
		currentPageNum_Wxbj=1;
	}
	
	var url=ctx+"/repairPrice/repairPriceList";
	var param="priceNumber="+$.trim($("#wxbjName").val())
			  +"&gId="+$("#gIds-WXBJ").val()
			  +"&amount="+$.trim($("#searchKey").val())
			  +"&model="+$.trim($("#repair_w_model").val())
			  +"&searchType=modalSearch"
			  +"&useState=0"
			  +"&currentpage="+currentPageNum_Wxbj;  
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if(item.price||item.price=='0'){
						item.price=item.price.toFixed(2);
					}else{
						item.price='0.00';
					}
					if(item.hourFee||item.hourFee=='0'){
						item.hourFee=item.hourFee.toFixed(2);
					}else{
						item.hourFee='0.00';
					}
				});
			}
			$("#wxbj_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeWXBJ();
			resetCurrentPageWxbj(currentPageNum_Wxbj);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/*-------获取维修报价 end--------------*/

/*-----------------选择维修报价 start--------------*/
function repair_openWXBJ(){
	$("#wxbjName").val("");
	$("#searchKey").val("");
	defaultSelectFirst_WXBJ();
	windowCommOpen("wxbj_w");
}

//维修报价默认选中第一个
function defaultSelectFirst_WXBJ() {
	$('#typeTree-WXBJ').tree({
		onLoadSuccess : function(node, data) {
			if (data.length > 0) {
				var n = $("#typeTree-WXBJ").tree('find', data[0].id);
				if (n != null) {
					$("#typeTree-WXBJ").tree("select", n.target);
					$("#gIds-WXBJ").val("");
					queryListPageWxbj(1);
				}
			}
		}
	});
}

//维修报价添加按钮事件
function doinsertWXBJ(){
	windowVisible("wxbj_w");
	$('#wxbjName').val("");
	$('#searchKey').val("");
	queryListPageWxbj(1);
	$("#selected-type-box-Wxbj").empty();
	temp_Wxbj_selectIds  = $("#repair_w_wxbjId").val();    
	temp_Wxbj_selectName = $("#repair_w_wxbjDesc").val();
	
	pjlIds_fin  = $("#repair_w_matId").val();    
	pjlDesc_fin = $("#repair_w_matDesc").val();  
	
	//初始化所选择的维修报价
	var names = temp_Wxbj_selectName.split("|");
	var ids   = temp_Wxbj_selectIds.split(",");
	if(names !="" && names!=null && names.length>0){
		var htmlDatas = "";
		for (var int = 0; int < names.length; int++){
			var amount = names[int];
			var rid   = ids[int]
			htmlDatas= htmlDatas +"<span class='tag'>"+amount+"<input type='hidden' name='win_WxbjId' value="+rid+"><input type='hidden' name='win_Wxbjname' value="+amount+"><button class='panel-tool-close'></button></span>";
		}
		$("#selected-type-box-Wxbj").append(htmlDatas);
	}
	ClearWxbjFunctionMethon();
	repair_openWXBJ();
	
}

function dbClickChooseWxbj(index,value){
	var Falg  = true;
	var ids   = temp_Wxbj_selectIds.toString().split(",");
	if(ids !="" && ids!=null && ids.length>0){
		for (var int = 0; int < ids.length; int++){
			if(ids[int] == value.rid){
				Falg = false ;
				break ;
			}
		}
	}
	if(Falg){
		$("#selected-type-box-Wxbj").append("<span class='tag'>【"+value.repairType+"】"+value.amount+"<input type='hidden' name='win_WxbjId' value="+value.rid+"><input type='hidden' name='win_Wxbjname' value='【"+value.repairType+"】"+value.amount+"'><input type='hidden' name='win_Wxbjprice' value="+value.price+"><button class='panel-tool-close'></button></span>");
		if(temp_Wxbj_selectIds!=""){
			temp_Wxbj_selectIds  = temp_Wxbj_selectIds +","+value.rid;
		}else{
			temp_Wxbj_selectIds = value.rid;
		}
		if(temp_Wxbj_selectName!=""){
			temp_Wxbj_selectName = temp_Wxbj_selectName+" | "+value.amount;
		}else{
			temp_Wxbj_selectName = value.amount;
		}
		ClearWxbjFunctionMethon();
	}else{
		$.messager.alert("操作提示","附件已选择！","warning");
	}
}

/**
 * 根据维修报价保存配件料
 */
function getPjlByWxbjId(){
	var url=ctx+"/repairPrice/getPjlByWxbjId";
	var param="ids="+$.trim($("#repair_w_wxbjId").val());  
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			var ret = returnData.data;
			var pjlIds = "";
			var pjlDesc = "";
			var pjlIdsTemp = pjlIds_fin.toString().split(",");
			var pjlDescTemp = pjlDesc_fin.toString().split(" | ");
			if(pjlIdsTemp.length > 0){
				$.each(ret, function(i, item) {
					if(pjlIds){
						if($.inArray(item.pjlId, pjlIdsTemp) == -1){
							pjlIds = pjlIds + "," + item.pjlId;
							pjlDesc = pjlDesc + " | " + item.proName;
						}
					}else{
						pjlIds = item.pjlId;
						pjlDesc = item.proName;
					}
					pjlIds_fin = pjlIds;
					pjlDesc_fin = pjlDesc;
				});
			}
			$("#repair_w_matId").val(pjlIds_fin);
			$("#repair_w_matDesc").val(pjlDesc_fin);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 保存数据
 */
function sysSelectDatasWxbj(){
	var ids    =""; 
	var names  =""; 
	
	$("input[name='win_WxbjId']").each(function(index,item){
		if(index ==0){
			ids = $(this).val();
		}else{
			ids = ids+","+$(this).val();
		}
	});
	$("input[name='win_Wxbjname']").each(function(index,item){
		if(index ==0){
			names = $(this).val();
		}else{
			names = names+" | "+$(this).val();
		}
	});
	temp_Wxbj_selectIds   = ids;    
	temp_Wxbj_selectName  = names;
}
/**
 * 初始化清除和删除方法
 */
function ClearWxbjFunctionMethon(){
	sysSelectDatasWxbj();
	$(".selected-type-box>.tag> .panel-tool-close").unbind('click').click(function(){
		var url=ctx+"/repairPrice/getPjlByWxbjId";
		var param="ids="+ $(this).parent().find("input[name='win_WxbjId']").val();  
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				var ret = returnData.data;
				var finPjlIds = pjlIds_fin.toString().split(",");
				var finPjlDesc = pjlDesc_fin.toString().split(" | ");
				$.each(ret, function(i, item) {
					finPjlIds.splice($.inArray(item.pjlId, finPjlIds),1);
					finPjlDesc.splice($.inArray(item.proName, finPjlDesc),1);
				});
				if(finPjlIds.length > 0){
					for (var int = 0; int < finPjlIds.length; int++) {
						if(int == 0 || int == finPjlIds.length - 1){
							pjlIds_fin = "";
							pjlDesc_fin = "";
						}else{
							pjlIds_fin = "";
							pjlDesc_fin = "";
							pjlIds_fin = pjlIds_fin + "," + finPjlIds[int];
							pjlDesc_fin = pjlDesc_fin + " | " + finPjlDesc[int];
						}
					}
				}else{
					pjlIds_fin = "";
					pjlDesc_fin = "";
				}
			}
		});

		$(this).parent().remove();
		sysSelectDatasWxbj();
	});
	$(".selected-type .clear-select-btn").click(function(){//维修报价操作时，配件料选择按钮已禁用，故不需要根据id来清空关联物料
		$(this).parents(".selected-type").find(".selected-type-box").empty();
		pjlIds_fin = "";
		pjlDesc_fin = ""
		sysSelectDatasWxbj();
	});
}

/**
 * 维修报价保存
 */
function WxbjSave(){
	if($("#repair_testResult").val()){
		$.messager.confirm("操作提示","已测试数据报价前请先确认该批次是否未付款，付款后不允许选择维修报价，是否保存选择？",function(con){
			if(con){
				saveWxbj();
			}else{
				$(".selected-type .clear-select-btn").click();
			}
		});
	}else{
		saveWxbj();
	}
}

function saveWxbj(){
	sysSelectDatasWxbj();
	$("#repair_w_wxbjId").val(temp_Wxbj_selectIds);
	$("#repair_w_wxbjDesc").val(temp_Wxbj_selectName);
	$("#repair_w_matId").val(pjlIds_fin);
	$("#repair_w_matDesc").val(pjlDesc_fin);
	getPjlByWxbjId();
	windowCommClose("wxbj_w");
	$.messager.alert("操作提示","请确认并选择是否人为","info");
}

function queryList(pageNumber){
	currentPageNum_Wxbj=pageNumber;
	if(currentPageNum_Wxbj=="" || currentPageNum_Wxbj==null){
		currentPageNum_Wxbj=1;
	}
	
	var url=ctx+"/repairPrice/repairPriceList";
    var param="priceNumber="+$("#wxbjName").val()
    		  +"&gId="+$("#gIds-WXBJ").val()
    		  +"&model="+$("#repair_w_model").val().trim()
    		  +"&searchType=modalSearch"
    		  +"&currentpage="+currentPageNum_Wxbj; 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#wxbj_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizeWXBJ();
			resetCurrentPageWxbj(currentPageNum_Wxbj);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/*---------------选择维修报价 end-------------------*/