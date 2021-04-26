﻿﻿ var _menus;
 var currentPageNum=1;
 var mailCountddress;
 var mailAddress;
 var reflashFlag=0;
$(function(){
	init();
	datagridInit();
	Clearnav();
	queryAll();	
	keySearch(queryListPage);
	searchType();//更改查询方式
});

function processCallBackURL(getChildCallBackURL){
  		if(getChildCallBackURL!=''){
  			$("#wnav").find('.panel:first').find(".panel-body").css("display","none");//第一个主菜单对应的二级菜单处于收缩状态
  			$("#wnav").find('.panel:first').find(".accordion-collapse").addClass("accordion-expand");//第一个主菜单按钮图标处于收缩状态
  			$("a[rel='"+getChildCallBackURL+"']").trigger("click");//触发JSP跳转到MainFrame
  			$("a[rel='"+getChildCallBackURL+"']").parent().parent().parent().parent().parent().find('.panel-header').addClass("accordion-header-selected");//当前主菜单被选状态
  			$("a[rel='"+getChildCallBackURL+"']").parent().parent().parent().parent().parent().find('.accordion-collapse').removeClass("accordion-expand");//当前主菜单图标处于展开键头状态
  			$("a[rel='"+getChildCallBackURL+"']").parent().parent().parent().parent().parent().find('.panel-body').css({"display":"block"});//当前主菜单下面的二级菜单处理展开状态
  		}
}

 
function queryAll(){
    
	$.ajax({
		type: "POST",
	  	async: false,
	  	cache: false,
	  	url: ctx+"/system/MenuSelect",
	  	dataType: "json",
	  	success : function(returnData){
	  		_menus=returnData;
	  		if(_menus.code==undefined || _menus.code==null){
	  	  		addNav(_menus.data);
	  	  		InitLeftMenu();
	  	  		tabClose();
	  	  		tabCloseEven();
	  	  		processCallBackURL(getChildCallBackURL);//记忆用户退出之前的操作页面
	  	  	}else if(_menus.code='-1' || _menus.code=='-2'){//-1:用户未登录，请求SSO单点登录服务;-2:用户无相应操作权限
	  	  		location.href=_menus.msg;
	  	  	}else if(_menus.code='-4'){//查询用户菜单异常
	  	  		alert(_menus.msg);
	  	  	}
	  		
	  		
		}, 
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert("错误:"+errorThrown);
	 	}
	});
} 




function Clearnav() {
	var pp = $('#wnav').accordion('panels');

	$.each(pp, function(i, n) {
		if (n) {
			var t = n.panel('options').title;
			$('#wnav').accordion('remove', t);
		}
	});

	pp = $('#wnav').accordion('getSelected');
	if (pp) {
		var title = pp.panel('options').title;
		$('#wnav').accordion('remove', title);
	}
	
}

function addNav(data) {

	$.each(data, function(i, sm) {
		var menulist = "";
		menulist += '<ul>';
		$.each(sm.children, function(j, o) {
			menulist += '<li><div><a ref="' + o.menuId + '" href="#" rel="'
					+ o.url + '" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">' + o.displayName
					+ '</span></a></div></li> ';
		});
		menulist += '</ul>';

		$('#wnav').accordion('add', {
			title : sm.displayName,
			content : menulist,
			iconCls : 'icon icon-sys'
		});

	});

	var pp = $('#wnav').accordion('panels');
	var t = pp[0].panel('options').title;
	$('#wnav').accordion('select', t);

}



// 初始化左侧
function InitLeftMenu() {
	
	hoverMenuItem();
	
	$('#wnav li a').live('click', function() {
		
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		addTab(tabTitle, url);
		$('#wnav li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});

}


function searchDetails(rowData){
	
		windowOpenNoticeDetails();
		
		chageWinSize('noticeDetailsId');
		
		var getNoticeTitle=rowData.noticeTitle;
		var getNoticeDesc=rowData.noticeDesc;
		var getCreatTime=rowData.creatTime;
		var getCreaterName=rowData.createrName;
		
		$("#noticeTitleP").html(getNoticeTitle);
		$("#noticeDescP").val(getNoticeDesc);
		$("#creatTimeP").html(getCreatTime);
		$("#createrNameP").html(getCreaterName);
	
	
}



/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem() {
	$(".easyui-accordion").find('a').hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
}

function addTab(subtitle,url){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			width:$('#mainPanle').width()-10,
			height:$('#mainPanle').height()-26
		});
	}else{
		$('#tabs').tabs('select',subtitle);
	}
	
	tabClose();
}


function createFrame(url)
{
	if(url==0){
		reflashFlag=1;
		//queryListPage(0);
	}else {
		var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	}
	
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('close',subtitle);
	})

	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		
		var subtitle =$(this).children("span").text();
		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
//add by wg.he 2013/12/20
function resetMainPage(){
	var currTab = $('#tabs').tabs('getSelected');
	var url = $(currTab.panel('options').content).attr('src');
	//主页公告管理,add by wg.he 2013/12/20
	if(url==null){
		url=0;
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url)
			}
		});
	}
	
	
}
//绑定右键菜单事件
function tabCloseEven()
{
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		
		//主页公告管理,add by wg.he 2013/12/10
		if(url==null){
			$("#myIndexIframe").attr("src",$("#myIndexIframe").attr("src"));
			url='noticAndMytodo.jsp';
		}else{
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			});
		}
	});
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});	
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t!=currtab_title)
				$('#tabs').tabs('close',t);
		});	
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType,alertAfter(0));
}

//弹出信息窗口根据type调用回调函数
function msgShowCallBack(title, msgString, msgType,type) {
	$.messager.alert(title, msgString, msgType,alertAfter(type));
}

//弹出确认框的回调方法
function msgShowCallBackByConfirm(title, msgString, url) {
	$.messager.confirm(title, msgString,function(data){
		if(data){
			location.href=url;
		}else {
			
		}
		
	});
}
function alertAfter(type){
	if(type==0){
		$('#w').window('open');
	}else {
		$('#w').window('close');
	}
}

function clockon() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

    $("#bgclock").html(time);

    var timer = setTimeout("clockon()", 200);
}


/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	$("#DataGrid1").datagrid({
     singleSelect:true,			
     onDblClickRow:function(rowIndex,row){
    	 //queryImeiSate(rowIndex,row);
		}
	}); 
	
	$("#allNotPackInfo_DataGrid").datagrid({
	     singleSelect:true,			
	     onDblClickRow:function(rowIndex,row){
	    	 $("#fashion").combobox('setValue',"1");//送修批号
	    	 $("#crucial").val(row.repairnNmber);
	    	 queryListPage('');
	    	 $('#showAllNotPackInfo').window('close');
		}
	});

	//初始化客户信息表格
	$("#DataGrid-Customer").datagrid({
		singleSelect:true,
	});

	//queryListPage(1);
}

/**
 * 更改查询方式
 */
function searchType(){
	$("#fashion").combobox({
		onChange:function(newValue, oldValue){
			if(newValue == '3'){
				$("#crucial_imei").val("");
				document.getElementById("crucial_imei").style.visibility = "visible";
			}else{
				document.getElementById("crucial_imei").style.visibility = "hidden";
			}
			
			if(!newValue){
				$("#crucial").val("");
				$("#crucial").attr("disabled","disabled");
			}else{
				$("#crucial").removeAttr("disabled");
			}
		}
	});
}

function clickFocus() {
	document.getElementById("crucial_imei").style.height = "100px";
	document.getElementById("crucial_imei").style.overflow = "auto";
}

function clickBlur() {
	document.getElementById("crucial_imei").style.height = "14px";
	document.getElementById("crucial_imei").style.overflow = "hidden";
}

//用来记录上次的查询条件
var lastStartTime;
var lastEndTime;
var lastFashion;
var lastCrucial;
/**
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	if((!$("#startTime").val() || !$("#endTime").val()) && (!$("#fashion").combobox('getValue') || $("#fashion").combobox('getValue') == 5)){//全部或最终故障需选择日期
		$.messager.alert("操作提示","请选择开始日期和结束日期","info");
		return;
	}

	if($("#crucial_imei").val() && $("#fashion").combobox('getValue')==3){
		var patt = /^[0-9\n]+$/i;
		if(!patt.test($("#crucial_imei").val())){
			$.messager.alert("操作提示","请输入正确的IMEI","info");
			return;
		}
	}

	var crucial = $("#crucial").val();
	var fashion = $("#fashion").combobox('getValue');
	if(fashion == '3'){
		crucial = $("#crucial_imei").val().trim().replace(/\n/g, ',');
	}

	var url=ctx+"/pack/deviceListPage";
    var param=addPageParam('DataGrid1',currentPageNum,"crucial="+$.trim(crucial)+
    		"&fashion="+fashion+
    		"&startTime="+$("#startTime").val()+
    		"&endTime="+$("#endTime").val());
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$.each(returnData.data,function(i, item){
				//保内/保外
				if(item.isWarranty == 0) {
					item.isWarranty = "<label style='color:green;font-weight: bold;'>保内</label>";
				}else if(item.isWarranty == 1){
					item.isWarranty = "<label style='color:red;font-weight: bold;'>保外</label>";
				} 
				
				//是否人为
				if(item.w_isRW == 0) {
					item.w_isRW = "<label style='color:red;font-weight: bold;'>是</label>";
				}else if(item.w_isRW == 1){
					item.w_isRW = "<label style='color:green;font-weight: bold;'>否</label>";
				} 
				
				//终检结果
				if(item.w_ispass=="0"){
					item.w_ispass="<label style='color:red;font-weight: bold;'>不合格</label>";
				}else if(item.w_ispass=="1"){
					item.w_ispass="<label style='color:green;font-weight: bold;'>合格</label>";
				}
				
				//是否无名件
				if(item.customerStatus=="un_normal"){
					item.customerStatus="<label style='color:red;font-weight: bold;'>无名件</label>";
				}else{
					item.customerStatus="<label style='color:green;font-weight: bold;'>正常</label>";
				}
				
				if(item.payedLogCost=='0'){
					item.payedLogCost="<label style='color:green;font-weight: bold;'>已支付</label>";					
				}else if(item.payedLogCost=='1'){
					item.payedLogCost="<label style='color:red;font-weight: bold;'>未支付</label>";
				}else{
					item.payedLogCost="";
				}
				
				//放弃报价
				if(item.w_isPrice=='1'){
					item.w_isPrice="<label style='color:red;font-weight: bold;'>是</label>";					
				}else if(item.w_isPrice=='0'){
					item.w_isPrice="<label style='color:green;font-weight: bold;'>否</label>";
				}else{
					item.w_isPrice="";
				}
				
				//是否放弃维修
				if(item.w_giveUpRepairStatus == 0){
					item.w_giveUpRepairStatus="<label style='color:green;font-weight: bold;'>正常</label>";
				}else if(item.w_giveUpRepairStatus == 1){
					item.w_giveUpRepairStatus="<label style='color:red;font-weight: bold;'>放弃维修</label>";
				}else{
					item.w_giveUpRepairStatus="";
				}
				
				
				//是否付款
				if(item.w_isPay ==0){
					item.w_isPay="<label style='color:green;font-weight: bold;'>是</label>";
				}else if(item.w_isPay == 1){
					item.w_isPay="<label style='color:red;font-weight: bold;'>否</label>";
				}else{
					item.w_isPay="";
				}
				
				//付款方式
				if(item.w_remAccount == 'personPay'){
					item.w_remAccount = "<label style='font-weight: bold;'>人工付款</label>";
				}else if(item.w_remAccount == 'aliPay'){
					item.w_remAccount = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
				}else if(item.w_remAccount == 'weChatPay'){
					item.w_remAccount = "<label style='color:green;font-weight: bold;'>微信付款</label>";
				}
				
				//所在工站
				if (item.state == 0){
					item.w_station = "受理";
				}else if (item.state == 1){
					item.w_station = "分拣";
				}else if (item.state == 2 || item.state == 5 || item.state == 10 || item.state == 11 || item.state == 12 || item.state == 16){
					item.w_station = "维修";
				}else if (item.state == 3 || item.state == 9 || item.state == 14){
					item.w_station = "报价";
				}else if (item.state == 13){
					item.w_station = "终检";
				}else if (item.state == -1){
					item.w_station = "已发货";
				}else if (item.state == 15){
					item.w_station = "测试";
				}else if (item.state == 4){
					if(item.w_priceState == -1){
						item.w_station = "维修";
					}else if(item.w_repairState == -1){
						item.w_station = "报价";
					}
				}else if (item.state == 6){
					if(item.w_FinispassFalg == -1){
						item.w_station = "维修";
					}else if(item.w_repairState == -1){
						item.w_station = "终检";
					}
				}else if (item.state == 7){
					if(item.w_FinispassFalg == -1){
						item.w_station = "装箱";
					}else {
						item.w_station = "终检";
					}
				}else if (item.state == 8){
					if(item.w_repairState == -1){
						item.w_station = "装箱";
					}else {
						item.w_station = "维修";
					}
				} else if (item.state == 17){
					item.w_station = "受理";
				} else if (item.state == 18){
					item.w_station = "分拣";
				} else if (item.state == 19){
					item.w_station = "测试";
				}
				
				// 状态
				if (item.state == "-1") {
					item.state = "<label style='color:green;font-weight: bold;'>已发货</label>";
				} else if (item.state == "0") {
					item.state = "<label style='color:red;font-weight: bold;'>已受理</label>";
				} else if (item.state == "1") {
					item.state = "<label style='color:red;font-weight: bold;'>已受理，待分拣</label>";
				} else if (item.state == "2") {
					item.state = "<label style='color:red;font-weight: bold;'>已分拣，待维修</label>";
				} else if (item.state == "3") {
					item.state = "<label style='color:red;font-weight: bold;'>待报价</label>";
				} else if (item.state == "4") {
					item.state = "<label style='color:red;font-weight: bold;'>已付款，待维修</label>";
				} else if (item.state == "5") {
					item.state = "<label style='color:red;font-weight: bold;'>已维修，待终检</label>";
				} else if (item.state == "6") {
					item.state = "<label style='color:red;font-weight: bold;'>已终检，待维修</label>";
				} else if (item.state == "7") {
					item.state = "<label style='color:red;font-weight: bold;'>已终检，待装箱</label>";
				} else if (item.state == "8") {
					item.state = "<label style='color:red;font-weight: bold;'>放弃报价，待装箱</label>";
				} else if (item.state == "9") {
					item.state = "<label style='color:red;font-weight: bold;'>已报价，待付款</label>";
				} else if (item.state == "10") {
					item.state = "<label style='color:red;font-weight: bold;'>不报价，待维修</label>";
				} else if (item.state == "11") {
					item.state = "<label style='color:red;font-weight: bold;'>放弃报价，待维修</label>";
				} else if (item.state == "12") {
					item.state = "<label style='color:red;font-weight: bold;'>已维修，待报价</label>";
				} else if (item.state == "13") {
					item.state = "<label style='color:red;font-weight: bold;'>待终检</label>";
				} else if (item.state == "14") {
					item.state = "<label style='color:red;font-weight: bold;'>放弃维修</label>";
				} else if (item.state == "15") {
					item.state = "<label style='color:red;font-weight: bold;'>测试中</label>";
				} else if (item.state == "16") {
					item.state = "<label style='color:red;font-weight: bold;'>已测试，待维修</label>";
				} else if (item.state == "17") {
					item.state = "<label style='color:red;font-weight: bold;'>已受理，已测试</label>";
				} else if (item.state == "18") {
					item.state = "<label style='color:red;font-weight: bold;'>已测试，待分拣</label>";
				} else if (item.state == "19") {
					item.state = "<label style='color:red;font-weight: bold;'>不报价，测试中</label>";
				}
				
				//客户寄货方式
				if(item.payDelivery =='N'){
					item.payDelivery="<label style='color:#0066FF;font-weight: bold;'>顺付</label>";
				}else{
					item.payDelivery="<label style='font-weight: bold;'>到付</label>";
				}
				//客户收货方式
				if(item.w_customerReceipt =='N'){
					item.w_customerReceipt="<label style='font-weight: bold;'>到付</label>";					
				}else{
					item.w_customerReceipt="<label style='color:#0066FF;font-weight: bold;'>顺付</label>"; //我们这边付快递费
				}
			});
			$("#DataGrid1").datagrid('loadData',returnData.data);  
        	getPageSize();
        	resetCurrentPageShow(currentPageNum);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');
		//保存上次查询条件
		lastStartTime=$("#startTime").val();
		lastEndTime=$("#endTime").val();
		lastFashion=fashion;
		lastCrucial=crucial;

	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
		 	$("#DataGrid1").datagrid('loaded');	
	});
}

////导出所有字段
//function exportInfo(){
//	var crucial = $("#crucial").val();
//	var fashion = $("#fashion").combobox('getValue');
//	if(fashion == '3'){
//		crucial = $("#crucial_imei").val().trim().replace(/\n/g, ',');
//	}
//	ExportExcelDatas("pack/ExportDatas?crucial="+crucial+ "&fashion="+fashion+
//    		"&startTime="+$("#startTime").val()+ "&endTime="+$("#endTime").val());
//}

//导出所有字段
function exportInfo(){
	var crucial = $("#crucial").val();
	var fashion = $("#fashion").combobox('getValue');
	if(fashion == '3'){
		crucial = $("#crucial_imei").val().trim().replace(/\n/g, ',');
	}
	var start = $("#startTime").val();
	var end = $("#endTime").val();
	var url = ctx + "/pack/ExportDatas";
	
	var $form1 = $("<form action='" + url
			+ "' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='crucial' value='"
			+ crucial + "'/>"));
	$form1.append($("<input type='hidden' name='fashion' value='"
			+ fashion + "'/>"));
	$form1.append($("<input type='hidden' name='startTime' value='"
			+ start + "'/>"));
	$form1.append($("<input type='hidden' name='endTime' value='"
			+ end + "'/>"));
	
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
	
//	ExportExcelDatas("pack/ExportDatas?crucial="+crucial+ "&fashion="+fashion+
//    		"&startTime="+$("#startTime").val()+ "&endTime="+$("#endTime").val());
}


function showAllNotPackInfo(){
	var url = ctx + "/workflowcon/getAllNotPackInfo";
	asyncCallService(url, 'post', false,'json', null, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$("#allNotPackInfo_DataGrid").datagrid('loadData',returnData.data);	
			
			windowVisible("showAllNotPackInfo");
			$('#showAllNotPackInfo').window('open');
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}		
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

function fixOutDate(){
	$.messager.confirm('info', "确定要同步当前查询结果的出货日期吗？",function(data){
		if(data){
			updateOutDate();
		}else {
			
		}
		
	});
}

function updateOutDate(){
	if((!$("#startTime").val() || !$("#endTime").val()) && (!$("#fashion").combobox('getValue') || $("#fashion").combobox('getValue') == 5)){//全部或最终故障需选择日期
		$.messager.alert("操作提示","请选择开始日期和结束日期","info");
		return;
	}
	if($("#crucial_imei").val()){
		var patt = /^[0-9\n]+$/i;
		if(!patt.test($("#crucial_imei").val())){
			$.messager.alert("操作提示","请输入正确的IMEI","info");
			return;
		}
	}
	var crucial = $("#crucial").val();
	var fashion = $("#fashion").combobox('getValue');
	if(fashion == '3'){
		crucial = $("#crucial_imei").val().trim().replace(/\n/g, ',');
	}
	
	var url=ctx+"/pack/fixOutDateNull";
    var param=addPageParam('DataGrid1',currentPageNum,"crucial="+$.trim(crucial)+
    		"&fashion="+fashion+
    		"&startTime="+$("#startTime").val()+
    		"&endTime="+$("#endTime").val());
    $("#DataGrid1").datagrid('loading');
    
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		$.messager.alert("操作提示",returnData.msg,"info");
		$("#DataGrid1").datagrid('loaded');
		if('0' == returnData.code){
			queryListPage();			
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
		 	$("#DataGrid1").datagrid('loaded');	
	});
}


/**
 * 弹出选择客户框,选择客户
 * @author pengshoulong
 */
function selectCustomer(){

	// console.log("上次查询条件为：");
	// console.log(lastStartTime);
	// console.log(lastEndTime);
	// console.log(lastFashion);
	// console.log(lastCrucial);
	var rows = $("#DataGrid1").datagrid("getRows");
	if(rows.length<=0){
		console.log("没有查询出来的数据");
		return;
	}

	//展示客户信息，使用了customer.js中的方法
	$.messager.confirm('info', "确定要修改当前查询结果的客户信息吗？",function(data){
		if(data){
			chooseCustomer(1,1);
		}
	});
}

/**
 * 修改所有查询出来的数据的客户
 * @author pengshoulong
 */
function modifyCustomerByQuery(){
	//选择的客户
	var customer = $('#DataGrid-Customer').datagrid('getSelected');
	//需要选择客户
	if(isEmpty(customer) || customer.length>1){
		$.messager.confirm('info', "请选择客户",function(data){});
		return;
	}
	//选择的客户和上次查询条件
	var param=addPageParam('DataGrid1',currentPageNum,"crucial="+lastCrucial+
		"&fashion="+lastFashion+
		"&startTime="+lastStartTime+
		"&endTime="+lastEndTime+
		"&cId="+customer.cid+
		"&cusName="+customer.cusName);
	$("#DataGrid1").datagrid('loading');
	var url=ctx+"/pack/modifyCustomerByQuery";
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		$.messager.alert("操作提示",returnData.msg,"info");
		$("#DataGrid1").datagrid('loaded');
		if('0' == returnData.code){
			queryListPage();
		}
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
		$("#DataGrid1").datagrid('loaded');
	});

}
/**
 * 修改设备所属客户，只修改了查询出来的第一页数据
 * @author pengshoulong
 */
function modifyCustomer(){

	//获取客户信息
	var customer = $('#DataGrid-Customer').datagrid('getSelected');
	//保存客户信息、要修改数据的imei和修改批号
	var param={};

	//保存客户信息
	param.cId=customer.cid;
	param.cusName=customer.cusName;
	//获取当前页面要修改的数据
	var rows = $("#DataGrid1").datagrid("getRows");
	console.log(rows);
	var workflows=[];
	//提取送修批号和imei
	for(var i=0;i<rows.length;i++){
		var temp = {};
		temp.imei = rows[i]['imei'];
		temp.repairnNmber = rows[i]['repairnNmber'];
		workflows[i] = temp;
	}
	//保存imei和修改批号
	param.workflows = workflows;

	//请求url
	var url=ctx+"/pack/modifyCustomer";
	//提交修改请求
	$.ajax({
		url : url,
		type : 'POST',
		async:true,
		dataType : 'json',
		data : JSON.stringify(param),
		cache : false,
		contentType: "application/json; charset=utf-8",
		success : function(result) {
			console.log("成功");
			$.messager.alert("操作提示",result.msg,"info");
			queryListPage();
		},
		error : function(result) {
			$.messager.alert("操作提示","执行出错","info");
		}
	});
	console.log("修改数据");
}

/**
 * 判断obj是否为null或undefined或""
 * @author pengshoulong
 * @param obj
 */
function isEmpty(obj){
	if(typeof obj == "undefined" || obj == null || obj ==""){
		return true;
	}else {
		return false;
	}
}