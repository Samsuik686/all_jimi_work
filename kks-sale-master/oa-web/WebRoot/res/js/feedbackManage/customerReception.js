//客服中心
$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
$(function() {
	
});

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-YCFKTWO-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("ycfkTwomanage/ImportDatas");
}


