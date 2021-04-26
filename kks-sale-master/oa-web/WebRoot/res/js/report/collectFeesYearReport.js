$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){ 
   datagridInit();  
   keySearch(queryListPage);
}); 

/**
 * 初始化数据及查询函数
 * @param pageNumber
 */
function queryListPage(pageNumber)  {
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/collectFeesReport/collectFeesYearReportList";
	
	//页面只显示年
	var searchDate=$("#searchDate").val();
	if(!searchDate){
		var defaultDate=new Date().getFullYear();
		$("#searchDate").val(defaultDate);
	}
	
    var param="searchDate="+$("#searchDate").val()+"&currentpage="+currentPageNum; 
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if(item.repairPay||item.repairPay=='0'){
						item.repairPay=item.repairPay.toFixed(2);
					}else{
						item.repairPay='0.00';
					}
					
					if(item.materialPay||item.materialPay=='0'){
						item.materialPay=item.materialPay.toFixed(2);
					}else{
						item.materialPay='0.00';
					}
					
					if(item.rechargePay||item.rechargePay=='0'){
						item.rechargePay=item.rechargePay.toFixed(2);//两位小数
					}else{
						item.rechargePay='0.00';
					}
					
					if(item.phoneRecharge||item.phoneRecharge=='0'){
						item.phoneRecharge=item.phoneRecharge.toFixed(2);
					}else{
						item.phoneRecharge='0.00';
					}
					
					if(item.wlwkRecharge||item.wlwkRecharge=='0'){
						item.wlwkRecharge=item.wlwkRecharge.toFixed(2);
					}else{
						item.wlwkRecharge='0.00';
					}
					
					if(item.logCost||item.logCost=='0'){
						item.logCost=item.logCost.toFixed(2);
					}else{
						item.logCost='0.00';
					}
					
					if(item.ratePrice||item.ratePrice=='0'){
						item.ratePrice=item.ratePrice.toFixed(2);//两位小数
					}else{
						item.ratePrice='0.00';
					}
					
					if(item.totalPay||item.totalPay=='0'){
						item.totalPay=item.totalPay.toFixed(2);//两位小数
					}else{
						item.totalPay='0.00';
					}
					
					if(i == ret.length-1){
						item.repairPay="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.repairPay+"</label>";
						item.materialPay="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.materialPay+"</label>";
						item.rechargePay="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.rechargePay+"</label>";
						item.phoneRecharge="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.phoneRecharge+"</label>";
						item.wlwkRecharge="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.wlwkRecharge+"</label>";
						item.logCost="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.logCost+"</label>";
						item.ratePrice="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.ratePrice+"</label>";
						item.totalPay="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.totalPay+"</label>";
						item.payTimes="<label style='font-weight: bold;'>"+item.payMonth+"</label>";
					}
				});
			}
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
 
$.messager.defaults= { ok: "确定",cancel: "取消" };
  
/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	/*$("#DataGrid1").datagrid({
	   singleSelect:true,//是否单选  
		toolbar:[
			{
				text:'导出', 
				iconCls:'icon-daochuicon',
				handler:function(){ 
					exportInfo();
				}  
			}
			,'-',{      
				text:'打印',
				iconCls:'icon-print',
				handler:function(){
					printReportExcel();
				} 
			}
		]
	}); */
	queryListPage(1);
}

/**
 * 导出Execl表
 */
function exportInfo(){
	ExportExcelDatas("collectFeesReport/MonthExportDatas?searchDate="+$("#searchDate").val());
}

/**
 * 打印
 */
function printReportExcel()
{
	$.messager.alert("操作提示","功能暂未完善","info");
}

/**
 * 生成Echart图表
 */
function creatReportEchart()
{
	windowCommOpen("Echart_Accqute");
}


