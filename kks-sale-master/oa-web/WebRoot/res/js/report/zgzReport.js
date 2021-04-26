$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
   datagridInit();   
   keySearch(queryListPage);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START

/**
 * 初始化列表
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var url = ctx + "/reportCon/getBreakdownList";
	var param = null;
	var startTime = $("#outTimeStart").val();
	var endTime = $("#outTimeEnd").val();
	var isWarranty = $.trim($("#isWarranty option:selected").val());
	param = "startTime=" + startTime + "&endTime=" + endTime + "&isWarranty=" + isWarranty;
	$("#Table_Accqute").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
				var jsonObjs = [];		
				$.each(returnData.data,function(i, item){
					if(i < returnData.data.length-1){
						var jsonObj = {};
						jsonObj["faultType"] = item.faultType;
						jsonObj["number"] = item.number;
						jsonObjs.push(jsonObj);
					}else {
						item.faultType="<label style='font-weight: bold;'>"+item.faultType+"</label>";
						if(item.number!=null)	item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
					}	
				})
				if(null != jsonObjs){
					onloadEChart(jsonObjs);
				}

			$("#Table_Accqute").datagrid('loadData', returnData.data);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#Table_Accqute").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
//往图表里面放数据
function onloadEChart(jsonObjs) {	        	
	var chart = AmCharts.makeChart("chartdiv", {
        "type": "pie",
        "theme": "light",   //颜色主题
        "innerRadius": "70%", // 内圈大小
        "gradientRatio": [-0.4, -0.4, -0.4, -0.4, -0.4, -0.4, 0, 0.1, 0.2, 0.1, 0, -0.2, -0.5], //图表的梯度
        "dataProvider":jsonObjs,
        "balloonText": "[[value]]",
        "valueField": "number",
        "titleField": "faultType",
        "balloon": {
            "drop": true,
            "adjustBorderColor": false,
            "color": "#FFFFFF",
            "fontSize": 16
        },
        "export": {
            "enabled": true,
            "beforeCapture": function() {
              
            }
          }
    });
	
}  


/**
 * 初始化 表单工具栏面板
 */
function datagridInit()  
{
	/*$("#Table_Accqute").datagrid({
     singleSelect:false,	
		toolbar:[
			{
				text:'导出', 
				iconCls:'icon-daoruicon',
				handler:function(){
					exportReportExcel();
				}
			},'-',{
				 text:'刷新', 
				 iconCls:'icon-reload',
				 handler:function(){
					 queryListPage(currentPageNum);
			     } 
			  }
//			,'-',{      
//				text:'打印',
//				iconCls:'icon-print',
//				handler:function(){
//					printReportExcel();
//				} 
//			}
		]
	}); */	
	queryListPage(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	var startTime = $("#outTimeStart").val();
	var endTime = $("#outTimeEnd").val();
	var isWarranty = $.trim($("#isWarranty option:selected").val());
	ExportExcelDatas("reportCon/zgzExportDatas?startTime=" + startTime + "&endTime=" + endTime + "&isWarranty=" + isWarranty);
}

/**
 * 打印
 */
function printReportExcel()
{
	$.messager.alert("操作提示","功能暂未完善","info");
}
