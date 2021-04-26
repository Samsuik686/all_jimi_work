$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;
var chart;

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
	var url = ctx + "/reportCon/everyBreakdownList";
	var param = null;
	var boardModel = $.trim($("#jx").val());
	var startTime = $("#outTimeStart").val();
	var endTime = $("#outTimeEnd").val();
	var isWarranty = $.trim($("#isWarranty option:selected").val());
	var isSend = $.trim($("#isSend option:selected").val());
//	var topfive = $("#topfive option:selected").val();
	param = "boardModel=" + boardModel + "&startTime=" + startTime + "&endTime=" + endTime + 
			"&isWarranty=" + isWarranty + "&isSend=" + isSend;
	$("#Table_Accqute").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			if(boardModel==null||boardModel==''){
				$.each(returnData.data,function(j, item){
					if(j == returnData.data.length - 1){
						item.boardModel="<label style='font-weight: bold;'>"+item.boardModel+"</label>";
						if(item.number!=null)	item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
					}	
				});
				$("#chartdiv").hide();
			}else{
				$("#chartdiv").show();
				var jsonObjs = [];
				$.each(returnData.data,function(i, item){
					if(i < returnData.data.length-1){
						var jsonObj = {};
						jsonObj["faultType"] = item.faultType;
						jsonObj["number"] = item.number;
						jsonObjs.push(jsonObj);
					}else {
						item.boardModel="<label style='font-weight: bold;'>"+item.boardModel+"</label>";
						if(item.number!=null)	item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
					}
				});
				//数组不为空时，显示图表
				if(null != jsonObjs){
					onloadEChart(jsonObjs);
				}else{
					//未查询到数据
					c$("#chartdiv").hide();
				}
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
	chart = AmCharts.makeChart("chartdiv", {
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
	queryListPage(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	var boardModel = $.trim($("#jx").val());
	var startTime = $("#outTimeStart").val();
	var endTime = $("#outTimeEnd").val();
	var isSend = $("#isSend").val();
	ExportExcelDatas("reportCon/everygzExportDatas?boardModel=" + boardModel
			+"&isWarranty=" + $.trim($("#isWarranty option:selected").val())
			+ "&startTime=" + startTime
			+ "&endTime=" + endTime
			+ "&isSend=" + isSend);
}

