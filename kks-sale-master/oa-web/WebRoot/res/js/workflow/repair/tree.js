$(function(){    
	groupTreeInit_ZZGZ();
	groupTreeInit_WXBJ();
	groupTreeInit_DZL();
});
/**------------------------------------------------------------- Tree最终故障管理 Start----------------------------------------------------**/
function groupTreeInit_ZZGZ(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=BASE_ZZGZ",
		dataType : "json",
		success : function(returnData) {
			treeLoadAgin_ZZGZ(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function treeLoadAgin_ZZGZ(returnData) {
	var groupList = '[{"id":"","text":"所有类别",children:[';
	;
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree-ZZGZ').tree(
		 {
			data : eval(groupList),
			onClick : function(node){
				$("#gIds").val(node.id);
				queryListPageZZGZ(1);
			}
		 }
     );
}
/**------------------------------------------------------------- Tree最终故障管理 End-----------------------------------------------------  **/


function groupTreeInit_WXBJ(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=BASE_WXBJ",
		dataType : "json",
		success : function(returnData) {
			treeLoadAgin_WXBJ(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function treeLoadAgin_WXBJ(returnData) {
	var groupList = '[{"id":"","text":"所有类别",children:[';
	;
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree-WXBJ').tree(
		 {
			data : eval(groupList),
			onClick : function(node){
				$("#gIds-WXBJ").val(node.id);
				queryListPageWxbj(1);
			}
		 }
     );
}

/**------------------------------------------------------------- Tree电子料管理 End-----------------------------------------------------  **/


function groupTreeInit_DZL(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/basegroupcon/queryList',
		data : "enumSn=BASE_DZL",
		dataType : "json",
		success : function(returnData) {
			treeLoadAgin_DZL(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function treeLoadAgin_DZL(returnData) {
	var groupList = '[{"id":"","text":"所有类别",children:[';
	;
	$.each(returnData.data, function(i, j) {
		groupList += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	groupList = groupList.replace(reg, "");
	groupList += ']}]';
	$('#typeTree-DZL').tree(
		 {
			data : eval(groupList),
			onClick : function(node){
				$("#gIds-DZL").val(node.id);
				queryListPageDZL(1);
			}
		 }
     );
}
