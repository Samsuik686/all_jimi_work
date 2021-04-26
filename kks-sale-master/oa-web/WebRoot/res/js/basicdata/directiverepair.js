$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;
$(function(){    
   datagridInit();     
}); 

/**
 * 初始化 表单工具栏面板
 */
function datagridInit()  
{
	$("#DataGrid1").datagrid({
     singleSelect:false
	});
}

/**
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	if(!$("#startTime").val() || !$("#endTime").val()){
		$.messager.alert("操作提示","开始结束日期必填","info");
	}else{
		var url=ctx+"/pack/directiveListPage";
	    var param=addPageParam('DataGrid1',currentPageNum,"operator="+$.trim($("#operator").val())+"&workstation="+$("#workstation").combobox('getValue')+"&startTime="+$("#startTime").val() 
	    	    +"&endTime="+$("#endTime").val())
	    $("#DataGrid1").datagrid('loading');
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				
				$.each(returnData.data,function(i, item){
					//终检结果
					if(item.w_ispass=="0"){
						item.w_ispass="<label style='color:red;font-weight: bold;'>不合格</label>";
					}else if(item.w_ispass=="1"){
						item.w_ispass="<label style='color:green;font-weight: bold;'>合格</label>";
					}
					
					// 状态
					if (item.state == "-1") {
						item.state = "<label style='color:green;font-weight: bold;'>已发货</label>";
					} else if (item.state == "0") {
						item.state = "<label style='color:red;font-weight: bold;'>已受理</label>";
					} else if (item.state == "1") {
						item.state = "<label style='color:red;font-weight: bold;'>已受理，待分拣</label>";
					} else if (item.state == "2") {
						item.state = "<label style='color:red;font-weight: bold;'>已分拣，待维修</label>";
					} else if (item.state == "3") {
						item.state = "<label style='color:red;font-weight: bold;'>待报价</label>";
					} else if (item.state == "4") {
						item.state = "<label style='color:red;font-weight: bold;'>已付款，待维修</label>";
					} else if (item.state == "5") {
						item.state = "<label style='color:red;font-weight: bold;'>已维修，待终检</label>";
					} else if (item.state == "6") {
						item.state = "<label style='color:red;font-weight: bold;'>已终检，待维修</label>";
					} else if (item.state == "7") {
						item.state = "<label style='color:red;font-weight: bold;'>已终检，待装箱</label>";
					} else if (item.state == "8") {
						item.state = "<label style='color:red;font-weight: bold;'>放弃报价，待装箱</label>";
					} else if (item.state == "9") {
						item.state = "<label style='color:red;font-weight: bold;'>已报价，待付款</label>";
					} else if (item.state == "10") {
						item.state = "<label style='color:red;font-weight: bold;'>不报价，待维修</label>";
					} else if (item.state == "11") {
						item.state = "<label style='color:red;font-weight: bold;'>放弃报价，待维修</label>";
					} else if (item.state == "12") {
						item.state = "<label style='color:red;font-weight: bold;'>已维修，待报价</label>";
					} else if (item.state == "13") {
						item.state = "<label style='color:red;font-weight: bold;'>待终检</label>";
					} else if (item.state == "14") {
						item.state = "<label style='color:red;font-weight: bold;'>放弃维修</label>";
					} else if (item.state == "15") {
						item.state = "<label style='color:red;font-weight: bold;'>测试中</label>";
					} else if (item.state == "16") {
						item.state = "<label style='color:red;font-weight: bold;'>已测试，待维修</label>";
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
			 	$("#DataGrid1").datagrid('loaded');	
		});
	}
}