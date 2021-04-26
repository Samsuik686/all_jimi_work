var currentPageNum;

$(function(){    
   datagridInit(); 
   keySearch(queryListPage);
}); 

function queryListPage(pageNumber)  {//初始化数据及查询函数
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/wlsqmanage/wlsqmanageList";
    var param="applicater="+$.trim($("#searchByApplicater").val())+"&startDate="+$("#startTime").val()+"&endDate="+$("#endTime").val(); 
    param = addPageParam('DataGrid1',currentPageNum,param);
    $("#DataGrid1").datagrid('loading');
    asyncCallService(url, 'post', false,'json', param, function(returnData){
		
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$.each(returnData.data,function(i, item){
				//保内保外
				if(item.state==0){
					item.state="同意";
				}else if(item.state==1){
					item.state="不同意";
				}
				
				if(item.matType==0){
					item.matType="配件料";
				}else if(item.matType==1){
					item.matType="电子料";
				}
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
 
$.messager.defaults= { ok: "确定",cancel: "取消" };
   
 function datagridInit()  
{
	var lastIndex;
 
	$("#DataGrid1").datagrid({
     singleSelect:true,//是否单选 
     onDblClickRow : function(rowIndex, row) {
    	 wlsq_appInfo(rowIndex, row);
		}
	}); 
	queryListPage(1);
}      
 
 /**
  * 批复
  */
 function wlsq_appInfo(){
	$("#wlAppWindow").panel({title:"物料申请批复"});
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row){
		var param="id="+row.id;
		var url=ctx+"/wlsqmanage/getWlsqmanage";
		asyncCallService(url, 'post', false,'json', param, function(returnData){
				$("#id").val(returnData.data.id);	
				$("#proSpeci").val(returnData.data.proSpeci);
				$("#unit").val(returnData.data.unit);
				$("#platform").val(returnData.data.platform);
				$("#number").val(returnData.data.number);
				$("#proName").val(returnData.data.proName);
				$("#proNO").val(returnData.data.proNO);
				$("#applicater").val(returnData.data.applicater);
				$("#appTime").val(returnData.data.appTime);
				$("#purpose").val(returnData.data.purpose);
				if(returnData.data.state=="0"){
					$("input[name='state'][value='0']").prop('checked',true);
				}else{
					$("input[name='state'][value='1']").prop('checked',true);
				}
				$("#remark").val(returnData.data.remark);
		}, function(){
			 $.messager.alert("操作提示","网络错误","info");
		});
		wlAppWindowOpen();
		chageWinSize('wlAppWindow');
	}else{
		$.messager.alert("提示信息","请先选择所要批复的行！","info");
	}
 }
 
 /**
  * 下载模板
  */
 function downloadTemplet() {
	 downLoadExcelTmp("MATERIAL/Excel-WLSQ-TEMP.xlsx");
 }

 /**
  * 导入
  */
 function importInfo() {
	 ImportExcelDatas("wlsqmanage/ImportDatas");
 }

 /**
  * 导出
  */
 function exportInfo() {
	 ExportExcelDatas("wlsqmanage/ExportDatas?applicater="+$.trim($("#searchByApplicater").val())+"&startDate="+$("#startTime").val()+"&endDate="+$("#endTime").val());
 }

 /**
  * 刷新
  */
 function refreshInfo() {
 	queryListPage(currentPageNum);
 }
 
 
function xlAppSave(){
	var isValid = $('#w1Apply_Form').form('validate');
	if(isValid){
		var url=ctx+"/wlsqmanage/updateWlsqmanage";
		var param="id="+$("#id").val()+"&state="+$("input[name='state']:checked").val()
					+"&unit="+$("#unit").val() +"&platform="+$("#platform").val()
					+"&number="+$("#number").val() +"&remark="+$("#remark").val();
		
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code=='0'){
				wlAppWindowClose();
			}
			$.messager.alert("操作提示",returnData.msg,"info");
		}, function(){
			$.messager.alert("操作提示","网络错误","info");
		});
		queryListPage(currentPageNum);
	}
}

//关闭物料申请窗口
function wlAppWindowClose(){
	$('#wlAppWindow').window('close');
}
//打开物料申请窗口
function wlAppWindowOpen(){
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#wlAppWindow').window('open');
	windowVisible("wlAppWindow");
}
