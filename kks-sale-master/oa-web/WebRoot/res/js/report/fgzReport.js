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
	var url = ctx + "/reportCon/fenBreakdownList";
	var param = null;
	var marketModel = $.trim($('#jx option:selected') .val());
	var startTime = $("#outTimeStart").val();
	var endTime = $("#outTimeEnd").val();
	param = "startTime=" + startTime + "&endTime=" + endTime + "&marketModel=" +marketModel;
	$("#Table_Accqute").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
				var xAxisData  = new Array();
				var seriesData = new Array();
				$.each(returnData.data,function(i, item){
					xAxisData.push(item.faultType);
					seriesData.push(item.number);
				})
				if(null!=xAxisData && null!=seriesData){
					onloadEChart(xAxisData,seriesData,marketModel);
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


 function onloadEChart(xAxisData,seriesData,marketModel)
 {
 	var myChart = echarts.init(document.getElementById('main'));
     // 指定图表的配置项和数据
 	option = {
 		    title : {
 		        text: marketModel,
 		        x:'center'
 		    },
 		    tooltip : {
 		        trigger: 'item',
 		        formatter: "{a} <br/>{b} : {c} ({d}%)"
 		    },
 		    legend: {
 		        orient: 'vertical',
 		        left: 'left',
 		        data: xAxisData
 		    },
 		    series : [
 		        {
 		            name: '数量',
 		            type: 'pie',
 		            radius : '55%',
 		            center: ['50%', '60%'],
// 		            data:[
// 		                {value:seriesData[0], name:xAxisData[0]},
// 		                {value:seriesData[1], name:xAxisData[1]},
// 		                {value:seriesData[2], name:xAxisData[2]}		       
// 		            ],
 		           data: (function(){		        	  
                     var res = [];
                     var len = 0;
                     for(var i=0,size=xAxisData.length;i<size;i++) {
                       res.push({
                       name: xAxisData[i],
                       value: seriesData[i]
                       });
                     }
                     return res;
                    })(),
 		            
 		            itemStyle: {
 		                emphasis: {
 		                    shadowBlur: 10,
 		                    shadowOffsetX: 0,
 		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
 		                },
		 		        normal:{
		                   label:{
		                     show: true,
		                     formatter: '{b} \n {d}%'
		                   },
		                   labelLine :{show:true}
		                 }    
 		            }
 		        }
 		    ]
 		};
     //指定的配置项和数据显示图表。
     myChart.setOption(option);
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
			}
		]
	}); */	
	queryMarketModel();
}

/**
 * 机型列表
 */
function queryMarketModel() {
	var url = ctx + "/reportCon/getMarketModelList";
	var param = null;
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			  var fieldList = returnData.data;  
              if(fieldList != null && fieldList.length > 0){  
                  for(var i = 0; i< fieldList.length; i++){  
                      $("<option value='"+fieldList[i].marketModel+"'>"+fieldList[i].marketModel+"</option>").appendTo("#jx");  
                  }  
              }  
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	var marketModel = $.trim($('#jx option:selected') .val());
	var startTime = $("#outTimeStart").val();
	var endTime = $("#outTimeEnd").val();		
	ExportExcelDatas("reportCon/fgzExportDatas?marketModel="+marketModel+"&startTime=" + startTime + "&endTime=" + endTime);
}

