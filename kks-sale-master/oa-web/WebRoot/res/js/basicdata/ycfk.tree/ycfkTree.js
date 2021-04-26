$(function(){    
	YcfkTreeInit();
});
/**------------------------------------------------------------- 工作站 右侧日期树 公共组件 Start----------------------------------------------------**/
function YcfkTreeInit(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/timetree/queryList',
		data : null,
		dataType : "json",
		success : function(returnData) {
			YcfkTreeLoad(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function YcfkTreeLoad(returnData) {
 	var	parentTree = '[{"id":"","text":"完成状态",children:[';	
 	var	groupList = '{"id":"1","text":"待解决",children:[';
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']},';
	
	groupList += '{"id":"2","text":"已完成",children:[';
  	;
  	$.each(returnData.data, function(i, j) {
  		groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
  	});
  	var reg = /,$/gi;
  	groupList = groupList.replace(reg, "");
  	groupList += ']}';
  	
    parentTree += groupList+']}]';
  	
	$('#ycfkTreeTime').tree(
		 {
			data : eval(parentTree),
			onClick : function(node){
				$("#tree-Date").val(node.id);
				if(node.id == '1' || node.id == '2'){
					$("#tree-State").val(node.id);
				}else if(node.id){
					var parentNode = $('#tree-Date').tree('getParent',node.target);
					$("#tree-State").val(parentNode.id);
				}
				queryListPage(1);
			}
		 }
     );
}
/**-------------------------------------------------------------左侧日期树 公共组件 End----------------------------------------------------**/