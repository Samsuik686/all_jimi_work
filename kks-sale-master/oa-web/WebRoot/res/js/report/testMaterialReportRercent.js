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
function queryListPage(pageNumber) {
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/testMaterialReport/testMaterialReportRercentList";
	var param= null;
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();		
		param={
				"bill" : $.trim($("#searchByBill").val()),
				"materialNumber" : $.trim($("#searchByMaterialNumber").val()),
				"materialName" : $.trim($("#searchByMaterialName").val())
		}
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				$.each(returnData.data, function(i, item) {
					if(item.matCount > 0 && item.materialName){
						item.materialName = "<label style='color:red;font-weight: bold;'>" + item.materialName + "</label>";
						item.matCount = "<label style='color:red;font-weight: bold;'>" + item.matCount + "</label>";
					}
				});
				$("#Table_Accqute").datagrid('loadData',returnData.data);
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
			
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
}

/**
 * 导出Excel报表
 */
function exportReportExcel() {
	var url =  ctx + "/testMaterialReport/exportDatasTestMaterialReportRercent";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='bill' value='" + $.trim($("#searchByBill").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='materialNumber' value='" + $.trim($("#searchByMaterialNumber").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='materialName' value='" + $.trim($("#searchByMaterialName").val()) +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}
