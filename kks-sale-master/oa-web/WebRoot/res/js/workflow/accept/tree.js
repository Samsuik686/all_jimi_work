$(function(){    
	groupTreeInit_SJFJ();
	groupTreeInit_CCGZ();
});
/**------------------------------------------------------------- Tree随机附件管理 Start----------------------------------------------------**/
function groupTreeInit_SJFJ(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=BASE_SJFJ",
		dataType : "json",
		success : function(returnData) {
			treeLoadAgin_SJFJ(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function treeLoadAgin_SJFJ(returnData) {
	var groupList = '[{"id":"","text":"所有类别",children:[';
	;
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree').tree(
		 {
			data : eval(groupList),
			onClick : function(node){
				$("#gIds-SJFJ").val(node.id);
				queryListPageSJFJ(1);
			}
		 }
     );
}
/**------------------------------------------------------------- Tree随机附件管理 End-----------------------------------------------------  **/

/**------------------------------------------------------------- Tree初检查故障 Start--------------------------------------------------- **/
function groupTreeInit_CCGZ(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=BASE_CCGZ",
		dataType : "json",
		success : function(returnData) {
			treeLoadAgin_CCGZ(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}

function treeLoadAgin_CCGZ(returnData) {
	var groupList = '[{"id":"","text":"所有类别",children:[';
	;
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree-CCGZ').tree(
		 {
			data : eval(groupList),
			onClick : function(node){
				$("#gIds-CJGZ").val(node.id);
				queryCCGZListPage(1);
			}
		 }
	);
}
/**------------------------------------------------------------- Tree初检查故障 End----------------------------------------------------- **/