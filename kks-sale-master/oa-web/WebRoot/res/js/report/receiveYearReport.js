$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
   datagridInit();  
   getCurrentYear();
   keySearch(queryReceiveYearList);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	$("#Table_Accqute").datagrid({
	});
	queryReceiveYearList(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

function queryReceiveYearList(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/reportCon/getReceiveYearList";
	var param= null;
	if(!$('#dateTime').val()){
		getCurrentYear();
	}
	var dateTime = $('#dateTime').val();				
	if(dateTime !=""){
		param="dateTime="+dateTime+
			  "&model="+$("#model").val()+
			  "&startTime="+$("#startTime").val()+
			  "&endTime="+$("#endTime").val();
		$("#Table_Accqute").datagrid('loading');
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				var ret = returnData.data;
				if (ret && ret.length > 0) {
					$.each(ret, function(i, item) {
						if(i == ret.length-1){
							item.model="<label style='font-weight: bold;'>"+item.model+"</label>";
						if(item.month1!=null)	item.month1="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month1+"</label>";
						if(item.month2!=null)   item.month2="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month2+"</label>";
						if(item.month3!=null)	item.month3="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month3+"</label>";
						if(item.month4!=null)	item.month4="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month4+"</label>";
						if(item.month5!=null)	item.month5="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month5+"</label>";
						if(item.month6!=null)	item.month6="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month6+"</label>";
						if(item.month7!=null)	item.month7="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month7+"</label>";
						if(item.month8!=null)	item.month8="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month8+"</label>";
						if(item.month9!=null)	item.month9="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month9+"</label>";
						if(item.month10!=null)	item.month10="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month10+"</label>";
						if(item.month11!=null)	item.month11="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month11+"</label>";
						if(item.month12!=null)	item.month12="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.month12+"</label>";
						if(item.totalNum!=null)	item.totalNum="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.totalNum+"</label>";
						}
					});
				}
				$("#Table_Accqute").datagrid('loadData',returnData.data);
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
			$("#Table_Accqute").datagrid('loaded');
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	}
}
function getCurrentYear(){
	var d=new Date();
	var	defaultDate=d.getFullYear();
	$("#dateTime").val(defaultDate);
}

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	if(!$('#dateTime').val()){
		getCurrentYear();
	}
	var dateTime = $("#dateTime").val();
	if(dateTime !=""){
		ExportExcelDatas("reportCon/receiveYearExport?dateTime="+dateTime+
				"&model=" + $.trim($("#model").val()) + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val());
	}else{
		$.messager.alert("操作提示","请选择导出日期","info");
	}
}


