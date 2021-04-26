$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
   datagridInit();   
   keySearch(queryListPage);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit()  
{
	$("#DataGrid1").datagrid({
     singleSelect:false,
	}); 
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END
/**
 * 初始化列表
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber)  
{
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var pageSize = getCurrentPageSize('DataGrid1');
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(!startTime || !endTime){
		$.messager.alert("操作提示","查询请选择开始日期和结束日期","info");
		return;
	}
	var param={
			"isWarranty" : $.trim($("#isWarranty option:selected").val()),
			"isPrice" : $.trim($("#isPrice option:selected").val()),
			"freeRepair" : $.trim($("#freeRepair option:selected").val()),
			"returnTimes" : $("#returnTimesSearch").val(),
			"repairnNmber" : $.trim($("#repairnNmberSearch").val()),
			"repairDetailFlag" : $.trim($("#repairDetailFlag option:selected").val()),
			"cusName" : $.trim($("#cusNameSearch").val()),
			"compare" : $.trim($("#compare option:selected").val()),
			"model" : $.trim($("#modelSearch").val()),
			"repairTimeType":$.trim($("#repairTimeType option:selected").val()),
			// "finChecker":$("#finChecker option:selected").val(),
			"startTime" : startTime,
			"endTime" : endTime,
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
	}
	var url=ctx+"/reportCon/getRepairDetailListPage";
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$.each(returnData.data,function(i, item){
				if(item.isPrice==0){
					item.isPrice="<label style='color:green;font-weight: bold;'>否</label>";
				}else if(item.isPrice==1){
					item.isPrice="<label style='color:red;font-weight: bold;'>是</label>";
				}
				if(item.returnTimes!=null) item.returnTimes==0;

			});
			$("#DataGrid1").datagrid('loadData',returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		 $("#DataGrid1").datagrid('loaded');
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}
/**
 * 导出Excel报表
 */
function exportInfo(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(!startTime || !endTime){
		$.messager.alert("操作提示","查询请选择开始日期和结束日期","info");
		return;
	}
	
	var url =  ctx + "/reportCon/repairDetailExport";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='isWarranty' value='" + $.trim($("#isWarranty option:selected").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='isPrice' value='" + $.trim($("#isPrice option:selected").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='freeRepair' value='" + $.trim($("#freeRepair option:selected").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='returnTimes' value='" + $("#returnTimesSearch").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='repairnNmber' value='" + $.trim($("#repairnNmberSearch").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='repairDetailFlag' value='" + $.trim($("#repairDetailFlag option:selected").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='repairTimeType' value='" + $.trim($("#repairTimeType option:selected").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='cusName' value='" + $.trim($("#cusNameSearch").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='compare' value='" + $.trim($("#compare option:selected").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='model' value='" + $.trim($("#modelSearch").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='startTime' value='" + startTime +"'/>"));
	$form1.append($("<input type='hidden' name='endTime' value='" + endTime +"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

/**
 * 生成Echart图表
 */
function creatReportEchart()
{
	windowCommOpen("Echart_Accqute");
}


