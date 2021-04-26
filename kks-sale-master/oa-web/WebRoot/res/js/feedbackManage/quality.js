//品质管理
$(function () {
	// 公用模块在ycfkTwomanage.js里面，当前js写专属于品质管理模块的功能
	var val = $("#next_site option:selected").val();
	sendDataToNextSite(val);
	
});