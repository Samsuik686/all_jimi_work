var browser = {
	versions: function () {
		var u = navigator.userAgent, app = navigator.appVersion;
		return {//移动终端浏览器版本信息 
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器 
		};
	}(),
	language: (navigator.browserLanguage || navigator.language).toLowerCase()
}
var old_c = document.body.className;
if (browser.versions.ios) {
    document.body.className = old_c  + ' ios';
}else if(browser.versions.android){
    document.body.className = old_c + ' android';
}