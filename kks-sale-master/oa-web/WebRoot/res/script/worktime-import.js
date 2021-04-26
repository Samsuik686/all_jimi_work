
$(function(){
	initGrid();
	queryListPage(1);
}); 

function initGrid()
{
	$("#DataGrid1").datagrid({
		toolbar:[{
			text:'导入考勤表格',
			iconCls:'icon-undo',
			handler:function(){
				openDialog();
			}
		}]
	});
}

function searchWorkTime(){
	$("#importMsg").html("");
	queryListPage('');
}
function importSumbit(){
 
	if($('#importFile').val()=='')
	{
		 $.messager.alert("操作提示","请选择文件,文件格式需为 .xls格式","info");  
		 return;  
	}else 
		$('#sbwf').click();	
} 



function queryListPage(pageNumber)  {//查询函数
	var getConditionYear=$("#reviewYearId").val();
	var getConditionMonth=$("#reviewMonthId").val();
    
	$.ajax({        
		type:"get",      
		async:false,         
		cache:false,      
		data:{
			searchUname:$("#searchUname").val(),
			getConditionDate:getConditionYear+"-"+getConditionMonth,
			currentpage:pageNumber
		},     
		url:ctx+"/worktime/queryworktimes.api", 
		dataType:"json",   
		success:function(returnData){  
			    processSSOOrPrivilege(returnData);
		    	$("#DataGrid1").datagrid('loadData',returnData.data);  
	        	getPageSize();
		},
	   error:function(){
		   $.messager.alert("操作提示","服务器繁忙，请稍后再试!如果问题一直存在，请联系系统管理员。","info");
	   }
	}); 
 }

function openDialog(){
	$("#importFileDialog").window("open");
}

function closeDialog(){
	$("#importFileDialog").window("close");
}


