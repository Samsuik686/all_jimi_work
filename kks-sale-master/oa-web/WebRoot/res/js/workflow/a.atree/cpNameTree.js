$(function() {
	WorkTreeInit();
});

function WorkTreeInit() {
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/workflowcon/queryCpNameList',
		data : null,
		dataType : "json",
		success : function(returnData) {
			WorkTreeLoad(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function WorkTreeLoad(returnData) {
	var groupList = '';
	var parentTree = '[{"id":"1","text":"公司名称",children:[';

	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + "" + '","text":"' + j + ' "},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']},';

	parentTree += groupList + ']';

	$('#typeTreeTime').tree({
		data : eval(parentTree),
		onClick : function(node) {
			$("#cpName").val(node.text);
			if(node.id == "1"){
				$("#cpName").val("");
			}
			queryListPage(1);
		}
	});
}
