$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){ 
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	datagridInit();   
	keySearch(queryListPage);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
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
//			,'-',{      
//				text:'打印',
//				iconCls:'icon-print',
//				handler:function(){
//					printReportExcel();
//				} 
//			}
//			},'-',{      
//				text:'生成图表',
//				iconCls:'icon-sum',
//				handler:function(){
//					creatReportEchart();
//				} 
//			}
			]
	});*/ 
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	var marketModel = $.trim($("#marketModel").val());
	if(marketModel !=""){
		ExportExcelDatas("reportCon/doExportDatas?marketModel="+marketModel);
	}else{
		$.messager.alert("操作提示","市场型号不能为空","warning");
	}
	
}
