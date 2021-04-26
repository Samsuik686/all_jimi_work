/**
 *  页面加载等待页面
 * @author wg.he
 * @date 2013/12/11
 */
    var getCurrentURL=location.href.toString();
    var imgURL;
    if(getCurrentURL.indexOf('.jsp')!=-1){
    	if(getCurrentURL.indexOf('AddressList')!=-1){//通讯录
    		imgURL='../res/image/loading.gif';
    	}else {
    		imgURL='../../res/image/loading.gif';
    	}
    }else {
    	//getCurrentURL=http://localhost:8080/oa-web/notice/selfNoticeSave
    	if(getCurrentURL.indexOf('selfNoticeSave')!=-1){
    		imgURL='../res/image/loading.gif';
    	}else {
    		imgURL='res/image/loading.gif';
    	}
    }
	var _html = "<p id='loading' align='center' style='z-index:10000;position:absolute;width:100%;margin:0 auto;no-repeat'> <img id='loadImgId' src='"+imgURL+"'></img></p>";
	document.write(_html);

	//数据加载完后调用
	function loadDataAfter(){
		var loadingDiv = document.getElementById("loading"); 
		if(loadingDiv!=null){
			$("#loading").remove('');
		}
	}

	 

 