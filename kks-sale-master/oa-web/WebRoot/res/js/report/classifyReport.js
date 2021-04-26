$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
   datagridInit();  
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit()  
{
	queryListPage();
	 keySearch(queryListPage);	
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
	var url=ctx+"/reportCon/getClassifyModelList";
	var param= null;
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();		
	var isWarranty = $("#isWarranty option:selected").val();
		param="startTime="+startTime+"&endTime="+endTime+"&isWarranty="+isWarranty;
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 		    				
				var jsonObjs = [];
								
				$.each(returnData.data,function(i, item){
					if(i < returnData.data.length-1){
					var jsonObj = {};
					jsonObj["model"] = item.model;
					jsonObj["useage"] = item.usage;
					jsonObjs.push(jsonObj);
					}else {
						item.model="<label style='font-weight: bold;'>"+item.model+"</label>";
						if(item.usage!=null)	item.usage="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.usage+"</label>";
						
					}
				});
				$("#Table_Accqute").datagrid('loadData',returnData.data);
				if(null != jsonObjs ){
					onloadEChart(jsonObjs);
				}
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
			
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
}
// 往图表里面放数据
function onloadEChart(jsonObjs) {	        	
	var chart = AmCharts.makeChart("chartdiv", {
        "type": "pie",
        "theme": "light",   //颜色主题
        "innerRadius": "70%", // 内圈大小
        "gradientRatio": [-0.4, -0.4, -0.4, -0.4, -0.4, -0.4, 0, 0.1, 0.2, 0.1, 0, -0.2, -0.5], //图表的梯度
        "dataProvider":jsonObjs,
        "balloonText": "[[value]]",
        "valueField": "useage",
        "titleField": "model",
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
 * 导出Excel报表
 */
function exportReportExcel()
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var isWarranty = $("#isWarranty option:selected").val();
	ExportExcelDatas("reportCon/classifyModelExport?startTime="+startTime+"&endTime="+endTime+"&isWarranty=" + isWarranty);
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


