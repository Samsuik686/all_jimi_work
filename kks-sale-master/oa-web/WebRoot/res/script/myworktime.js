
$(function(){
	initGrid();
	queryListPage(1);
}); 

function initGrid()
{
	$("#DataGrid1").datagrid();
}

function searchWorkTime(){
	queryListPage('');
}


function queryListPage(pageNumber)  {
	//查询函数
	var getConditionYear=$("#reviewYearId").val();
	var getConditionMonth=$("#reviewMonthId").val();
	$.ajax({        
		type:"get",      
		async:false,         
		cache:false,      
		data:{
			getConditionDate:getConditionYear+"-"+getConditionMonth,
			currentpage:pageNumber
		},     
		url:ctx+"/worktime/querymyworktimes.api",
		dataType:"json",   
		success:function(returnData){  
			    processSSOOrPrivilege(returnData)
		    	$("#DataGrid1").datagrid('loadData',returnData.data);  
	        	getPageSize();
		},
	   error:function(){
		   $.messager.alert("操作提示","服务器繁忙，请稍后再试!如果问题一直存在，请联系系统管理员。","info");
	   }
	}); 
 }