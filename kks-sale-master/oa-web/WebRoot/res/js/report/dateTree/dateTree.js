$(function(){    
	dateTreeInit();
});
/**------------------------------------------------------------- 工作站 右侧日期树 公共组件 Start----------------------------------------------------**/
function dateTreeInit(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/timetree/queryList?num=30',
		data : null,
		dataType : "json",
		success : function(returnData) {
			dateTreeLoad(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function dateTreeLoad(returnData) {
	var parentTree = '[{"id":"","text":"处理状态",children:[';		
	var groupList = '{"id":"1","text":"未完成",children:[';
		$.each(returnData.data, function(i, j) {
  			groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
  		});
  		var reg = /,$/gi;
  		groupList = groupList.replace(reg, "");
  		groupList += ']}';
		parentTree += groupList+']}]';
  		/*
  		groupList += '{"id":"2","text":"已完成",children:[';
  	  	;
  	  	$.each(returnData.data, function(i, j) {
  	  		groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
  	  	});
  	  	var reg = /,$/gi;
  	  	groupList = groupList.replace(reg, "");
  	  	groupList += ']}';
  	  	parentTree += groupList+']}]';
  		 */
  		
  	
	$('#typeTreeTime').tree(
		 {
			data : eval(parentTree),
			onClick : function(node){
				$("#tree-Date").val(node.id);
				if(node.id == '1' || node.id == '2'){
					$("#tree-State").val(node.id);
				}else{
					var parentNode = $('#tree-Date').tree('getParent',node.target);
					$("#tree-State").val(parentNode.id);
					
				}
				queryListPage(1);
			}
		 }
     );
}
/**------------------------------------------------------------- 工作站 左侧日期树 公共组件 End----------------------------------------------------**/