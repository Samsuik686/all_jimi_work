var electFlowSessionTag;

//客服     176  客服
var customer_oId = 176;
//维修     177  维修
var repair_oId = 177;
//终端技术     180
var terminal_oId = 180;
//平台技术     179 平台技术，2019/4/19->180
var platform_oId = 180;
//品质     167   品质部  QC主管  
var quality_oId = 167;
//产品     173  产品规划部，2019/4/2更改为214
var product_oId = 214;
//项目    172   项目部 ，2019/4/2更改为215
var project_oId = 215;
//几米    147   软件一部
var jimi_oId = 147;
//市场 168市场部
var market_oId = 217;


var ycfk_customer_menuId = 11435;//异常反馈-客服
var ycfk_repair_menuId = 11441;//异常反馈-维修
var ycfk_terminal_menuId = 11436; //异常反馈-终端
var ycfk_platform_menuId = 11437; //异常反馈-平台
var ycfk_quality_menuId = 11438; //异常反馈-品质
var ycfk_project_menuId = 11442;//异常反馈-项目
var ycfk_product_menuId = 11439;//异常反馈-产品
var ycfk_jimi_menuId = 11443;//异常反馈-几米
var ycfk_market_menuId = 11458;//异常反馈-营销
var ycfk_research_menuId = 11460;//异常反馈-研发
var ycfk_iot_menuId = 11462;//异常反馈-物联网卡
var ycfk_sim_menuId = 11463;//异常反馈-SIM卡平台
var ycfk_test_menuId = 11461//异常反馈-测试

function queryDictBySn(sn){
	 var url;
     $.ajax({
        type:"get",
        data:{
        	sn:sn
        },
        async:false,
        cache:false,
        url:ctx+"/dictionary/selectdicIdOrSn",
        dataType:"json",
        success:function(returnData){
        	if(returnData.code!=null)
        		return null;
        	return returnData.data;
        },
       error:function(){
		   $.messager.alert("系统提示","您的网络好像存在问题，请稍后再试!","info");
	   }
    });
}
/**
 * Ajax处理返回的数据前需要调用的方法
 * @param returnData
 * @author Li.Shangzhi
 * @Date 2016-07-11
 */
function processSSOOrPrivilege(returnData){
	
	var getLoadingId=document.getElementById("loading");
	if(getLoadingId){//防止有些jsp没有引入ajaxLoading.js，但又调用了此方法
		loadDataAfter();//add by wg.he 2013/12/11,ajax或dataGrid方式加载完数据后调用
	}
	
	if(returnData.code=='-1'){//-1:用户未登录，请求SSO单点登录服务
  		parent.parent.parent.location.href=returnData.msg;
	}else if(returnData.code=='-2'){//-2:用户无相应操作权限
  		parent.location.href=rootPath+returnData.msg;
	}else if(returnData.code=='404'){//404:用户请求的URI在function_details表中不存在
  		parent.location.href=rootPath+returnData.msg;
	}else {
		changeDataGridTitleColor();
	}
}

/**
 * 当当前页处于>1时，点击某条件查询按钮后，分页栏还是未同步当前页码
 * @param currentPageNum为当前页码
 * @author Li.Shangzhi
 * @Date 2016-07-11
 */
function resetCurrentPageShow(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	$('#DataGrid1').datagrid('getPager').pagination({
		 pageNumber:currentPageNum
	});
}

/**
 * 调整弹出框位置处于可视区域
 * @param winId即弹出框id
 * @author Li.Shangzhi
 * @Date 2016-07-11
 */
function chageWinSize(winId){
	
	var getCurrentWinTop=$("#"+winId).parent().css("top").toString();
	var getCurrentWinTopInt=getCurrentWinTop.substring(0,getCurrentWinTop.indexOf("px"));
	if(getCurrentWinTopInt<0){
		$("#"+winId).parent().css("top","10px");
	}
	
	var getCurrentWinLeft=$("#"+winId).parent().css("left").toString();
	var getCurrentWinLeftInt=getCurrentWinLeft.substring(0,getCurrentWinLeft.indexOf("px"));
	if(getCurrentWinLeftInt<0){
		$("#"+winId).parent().css("left","10px");
	}
}

/**
 * 针对电子流管理中另加了两层frameset的*.location.href方式调用的处理
 * @param callBackURI，如流程管理模块：page/workflow/main.jsp;员工信息管理模块:page/userinfo/UserInfoManager.jsp
 * @author Li.Shangzhi
 * @Date 2016-07-11
 */
function getUserSessionStatus(callBackURI){
	
	var url=ctx+"/getSessionStatus";
	var param='uri='+callBackURI;
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		if(returnData.code=='-1'){//session失效
			parent.parent.parent.location.href=returnData.msg;//当前整个页面跳转到登录页面
		}else {
			changeDataGridTitleColor();
		}
		
	}, function(){
		 //$.messager.alert("操作提示","网络错误","info");
	});
}
/**
 * Ajax请求公共方法
 * @param url
 * @param type：get/post
 * @param asyncType:true/false,若不传或为空,则是默认的异步处理
 * @param dataType:返回数据类型
 * @param param：请求参数
 * @param success：成功后回调方法
 * @param error:失败后回调方法
 * @author Li.Shangzhi
 * @Date 2016-07-11
 */
function asyncCallService(url, type, asyncType,dataType, param, success, error) {
	var getAsyncType;
	if(asyncType==null || ''==asyncType){
		getAsyncType=true;
	}else {
		getAsyncType=asyncType;
	}
	
	$.ajax({
		url : url,
		type : type,
		async:getAsyncType,
		dataType : dataType,
		data : param,
		cache : false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success : function(result) {
			success.call(this, result);
		},
		error : function(result) {
			if(error!=undefined && error!=null)
				error.call(this, result);
		}
	});
}

function getAllOrganizationList(showTreeId){
	
	var url=ctx+"/user/OrganSelectList";
	
	asyncCallService(url, 'post', false,'json', '', function(returnData){
		
		var getReturnData= $.parseJSON(returnData);
		
		processSSOOrPrivilege(getReturnData);
		
		if(getReturnData.code==undefined){
			setNoteTree(getReturnData,showTreeId);
		}else {
			$.messager.alert("操作提示",getReturnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
}

var treelist="";

function setNoteTree(returnData,showTreeid)
{
	$("#"+showTreeid).empty();
	treelist='[{"id":"0","text":"组织架构","children":[';
	eachNoteTree(returnData.data);
    var reg=/,$/gi;
    treelist=treelist.replace(reg,"");
    treelist+=']}]';
    
    $("#"+showTreeid).tree({
       data : eval(treelist)  
    });
}

function eachNoteTree(returnData)
{
	$.each(returnData,function(i,j){
	       if(j.list!=""){
	    	   treelist+='{"id":"'+j.oId+'","text":"'+j.oName+'","state":"closed","children":[';
	    	   eachNoteTree(j.list);
		       var reg=/,$/gi;
		       treelist=treelist.replace(reg,"");
	       }
	       else{
	    	   treelist+='{"id":"'+j.oId+'","text":"'+j.oName+'","children":[';
	       }
	       treelist+=']},';
	    });
}
/**
 * 用户组织架构中双击父节点
 * 目前只支持两层目录
 * @param this_obj,node
 * @author Li.Shangzhi
 * @Date 2016-07-11
 */
function treeSelectListenerDBClick(this_obj,node)
{
	if('open'==node.state){
		if(node.text.toString().indexOf('组织架构')!=-1 || node.text.toString().indexOf('所有部门')!=-1){
			$(this_obj).parent().find("li:eq(0)").find("ul:eq(0)").css("display","none");
			$(this_obj).parent().find("li:eq(0)").find(".tree-hit").attr("class","tree-hit tree-collapsed");
		}else if(node.text.toString().indexOf('康凯斯')!=-1){
			$(this_obj).parent().find("li:eq(1)").find("ul:eq(0)").css("display","none");
			$(this_obj).parent().find("li:eq(1)").find(".tree-hit").attr("class","tree-hit tree-collapsed");
		}
	}else if('closed'==node.state){
		if(node.text.toString().indexOf('组织架构')!=-1 || node.text.toString().indexOf('所有部门')!=-1){
			$(this_obj).parent().find("li:eq(0)").find("ul:eq(0)").css("display","block");
			$(this_obj).parent().find("li:eq(0)").find(".tree-hit").attr("class","tree-hit tree-expanded");
			
			if($(this_obj).parent().find("li:eq(1)").find("ul:eq(0)").css("display").toString().indexOf("block")==-1){
				$(this_obj).parent().find("li:eq(1)").find(".tree-hit").attr("class","tree-hit tree-collapsed");
			}
		}else if(node.text.toString().indexOf('康凯斯')!=-1){
			$(this_obj).parent().find("li:eq(1)").find("ul:eq(0)").css("display","block");
			$(this_obj).parent().find("li:eq(1)").find(".tree-hit").attr("class","tree-hit tree-expanded");
		}
	}

}


/**
 * 时间控件格式化
 * @author Li.Shangzhi
 * @Date 2016-07-10
 */
function formatDate(date) 
{   
    var month=date.getMonth();
    var newMonth=parseInt(month, 10)+1;    
return date.getFullYear() + "-"+ newMonth+ "-" + date.getDate();  
}    

/**
 * 时间控件时间校验
 * @author Li.Shangzhi
 * @Date 2016-07-10
 */
function checkTime(startTime,endTime)
{
	//将字符串转换为日期
    var begin=new Date(startTime.replace(/-/g,"/"));
    var end=new Date(endTime.replace(/-/g,"/"));
    //js判断日期
    if(begin-end>0){     
       alert("开始日期要在结束时间之前!");  
       return false; 
    } 
}
/**
 * 修改DataGrid1第一行标题的样式
 * @author Li.Shangzhi
 * @Date 2016-07-10
 */
function  changeDataGridTitleColor()
{
	var changeBackColor=$(".datagrid-header-inner .datagrid-cell");
	$(changeBackColor).each(function(){
		$(this).css("background","#6699cc");
		$(this).css("font-weight","bold");
		$(this).css("color","#FFFFFF");
	});
}
 


/*根据起始时间和结束日期计算天数*/
function  DateDiffNoWeekDay(sDate1,sDate2)
{    
   var oDate1  = StringToDate(sDate1);
   var oDate2  = StringToDate(sDate2); 
   alert(oDate1+"       "+oDate2);
	
   if (parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24)<(oDate2 - oDate1) / 1000 / 60 / 60 /24)
   {
    var days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24)+1;
   }
   else {days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24);}
    
   //var days = parseInt((oDate2 - oDate1) / 1000 / 60 / 60 /24);//获取总的天数
   var days1= parseInt((oDate2 - oDate1) / 1000 / 60 / 60 )%8;  //获取余下的小时
   /*--减去不用上班的时间,即09-18之外的时间--*/
      if(days1>8){
           days1=8;
       }  
   var tempDate = oDate1;
   while(tempDate.getTime() <= oDate2.getTime()){
    //tempDate = addDays(tempDate,2);//加一天
    //days>0表示超过1天，防止出现负数days
    if(checkWeekDay(DateToString(tempDate))& days>0 ){
        //如果是周末,天数减1
     days--;
    }
    tempDate = addDays(tempDate,2);//加一天
   }
   if (days ==1)
   {
    if(parseInt((oDate2-oDate1)/1000/60/60/24)==0& days1==0) {days=1;}
    else{days=0;}/*一天或半天加判断解决天数的问题*/
   }
  return days+"天"+days1+"小时";
}


 /*判断是否含有周末,如果是周末 返回true,没有返回false*/
function checkWeekDay(sDate)
{
   arys=sDate.split('-');
   arys1=arys[2].split(' ');
    arys2=arys1[1].split(':');
   oDate  =  new Date(arys[0],parseInt(arys[1], 10) - 1,arys1[0],arys2[0],arys2[1],arys2[2]);
   day = oDate.getDay();//判断是否周末
   if(day==0 || day == 6){
    return true;
   }
   return false;
}

/*增加天数*/
function addDays(oDate,days)
{
 if(days > 0){
        days = days - 1;
    }
 if(days < 0){
        days = days + 1;
    }
 var result = new Date(oDate.getTime()+(days*24 * 60 * 60 * 1000));
     return result;
 }

/*将字符串转换成日期*/
function StringToDate(sDate)
{
    arys=sDate.split('-');
    arys1=arys[2].split(' ');
    arys2=arys1[1].split(':');
    if(arys2[0]>18){
        arys2[0]=18;
        arys2[1]=00;
        arys2[2]=00;
        }
        if(arys2[0]<10){//9
            arys2[0]=10;//9
            arys2[1]=0;
            arys2[2]=0;
            }
    var newDate=new Date(arys[0],parseInt(arys[1], 10) - 1,arys1[0],arys2[0],arys2[1],arys2[2]);
    return newDate;
 }

 /*为一部分月份及日期加前+0*/
 function DateToString(oDate)
 {
    var month = oDate.getMonth()+1;
    var day = oDate.getDate();
    var  hour=oDate.getHours();
    var  mi=oDate.getMinutes();
    var second=oDate.getSeconds();
    //如果月份小于10月则在前面加0
    if(month<10){
       month="0"+month;
    }
    //如果日期小于10号则在前面加0
       if(day<10){
      day = "0"+day;
      }
    if(hour<10){
        //如果小于9点 设置为9点
        if(hour<9){ //9
         hour=9;//9
        }
      hour = "0"+hour;
      }
    //如果大于18点，让他等于18点
    if(hour>18){
        hour=18;
        }
    if(mi<10){
      mi = "0"+mi;
      }
    if(second<10){
      second = "0"+second;
      }
      return oDate.getFullYear() + "-" + month +"-"+ day+" "+hour+":"+mi+":"+second;
  }
 
function getHtml(containerId)
{
		//遍历单选
		$('#'+containerId+' input:radio:checked').each(function () {
			 $(this).attr("checked", "checked");
		});
		//遍历多选
		$('#'+containerId+' input:checkbox:checked').each(function () {
			  $(this).attr("checked", "checked");
		});
		//遍历所有input
		$('#'+containerId+' input').each(function(){ 
			var dd=$(this).val();
			$(this).attr("value",dd);
		});
		
		//遍历所有textarea
		$('#'+containerId+' textarea').each(function(){ 
			var dd=$(this).val();
			$(this).text(dd);
		});
		
		//遍历所有select
		$('#'+containerId+' select').each(function(){ 
			var dd=$(this).val();
			$(this).find("option[text="+dd+"]").attr("selected",true);
		});
		 var htmlStr =$("#"+containerId).html();
		return htmlStr;
	}

//获得当前时间
function getCurrentDateTime() {
	var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

/**
 * 下载Excel模版
 * @param filePath {只包括 ctx+"/exceltmp/}
 * 
 * @author Li.Shangzhi
 * @Date 2016-07-16 11:34:54
 */
function downLoadExcelTmp(filePath){
	var url=ctx+"/exceltmp/"+filePath;
	window.open(url);
}

/**
 * 导出Excel 数据
 * @param requestPath 请求路径
 * 
 * @author Li.Shangzhi
 * @Date 2016-07-16 14:03:05
 */
function ExportExcelDatas(requestPath){
	var url=ctx+"/"+requestPath;
	window.open(url);
}


function ImportExcelDatas(url){
	windowCommOpen("Import_comm_window");
	windowVisible("Import_comm_window");
	$('#importUrl').val(url);	
}


/**
 * 弹出框 打开 OPEN
 * @param fid
 *
 * @author Li.Shangzhi
 * @Date 2016-07-19 10:38:48
 */
function windowCommOpen(fid)
{
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#'+fid).window('open');
}


/**
 * 弹出框 关闭 CLOSE
 * @param fid
 *
 * @author Li.Shangzhi
 * @Date 2016-07-19 10:39:03
 */
function windowCommClose(fid)
{	
	$('#'+fid).window('close');
}


/**
 * 弹出框 可见 visible
 * @param fid
 *
 * @author Li.Hefei
 * @Date 2016-11-04 11:20:00
 */
function windowVisible(id){
	$("#"+id).css("visibility","visible");
};

/**
 * 回车查询列表数据
 * 
 */
function keySearch(refreshListFunction,index){
	if(index){
		$('.form-search'+index).keydown(function (e) {
	        if (e.keyCode == 13) {
	        	refreshListFunction(1);
	        }
	    });
	}else{
		$('.form-search').keydown(function (e) {
	        if (e.keyCode == 13) {
	        	refreshListFunction(1);
	        }
	    });
	}
	
}

/**
 * 获取url带的参数
 */
 function getUrlParameter(parameterName) {
	var paramValue = "";
	var urlArray = window.location.href.split("?");
	if (window.location.href.indexOf("?") > 0) {
		var paramArray = window.location.href.split("?")[1].split("&");
		for (x in paramArray) {
			if (paramArray[x].indexOf(parameterName) > -1) {
				paramValue = paramArray[x].replace(parameterName+"=", "");
			}
		}
	}else{
		if(window.location.href.includes("eqpNotSendInfo")){
			paramValue="eqpNotSendInfo";
		}
	}
	return paramValue;
}
 
 /**
  * 查询数据放在select里面
  * @param selectId
  * @param defaultHtml
  * @param defaultText
  * @param url
  * @param param  请求参数
  * @param uPosition   职务过滤
  */
 function initUserInfo(selectId, defaultHtml, defaultText, url, param, uPosition){	
	var optionHtml = "<option value='"+defaultHtml+"'>"+defaultText+"</option>";
	if (url) {
		asyncCallService(url, 'post', false,'json', param, function(ssoReturnData){
			if(ssoReturnData.code==undefined){
				var getRoleIdListData=ssoReturnData.data;
				if (uPosition) {
					var up = uPosition.split(",");
//					// 查询部门下指定职务的职工
					$(getRoleIdListData).each(function(i,item){
						var optionValue = item.uName;
						for (var i = 0; i < up.length; i++) {
							if (item.uPosition.indexOf(up[i]) > -1) {
								optionHtml += "<option value='"+optionValue+"'>" + optionValue + "</option>";
								break;
							}
						}
					});
				} else {
					// 查询当前部门下所有职工
					$(getRoleIdListData).each(function(i,item){
						var optionValue = item.uName;
						optionHtml += "<option value='"+optionValue+"'>" + optionValue + "</option>"
					});
				}
				
				$("#"+selectId).html(optionHtml);
			}else {
				$.messager.alert("操作提示",ssoReturnData.msg,"info");
			}
				
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	} else {
		$("#"+selectId).html(optionHtml);
	}
 	
	 
 }
 
 /**
  * 查询所有状态
  * @param selId
  */
 function selAllState(selId){
	 var optionHtml = "<option value=''>全部</option>";
	 optionHtml += "<option value='0'>已受理</option>";
	 optionHtml += "<option value='1'>已受理,待分拣</option>";
	 optionHtml += "<option value='2'>已分拣,待维修</option>";
	 optionHtml += "<option value='3'>待报价</option>";
	 optionHtml += "<option value='4'>已付款,待维修</option>";
	 optionHtml += "<option value='5'>已维修,待终检</option>";
	 optionHtml += "<option value='6'>已终检,待维修</option>";
	 optionHtml += "<option value='7'>已终检,待装箱</option>";
	 optionHtml += "<option value='8'>放弃报价,待装箱</option>";
	 optionHtml += "<option value='9'>已报价,待付款</option>";
	 optionHtml += "<option value='10'>不报价,待维修</option>";
	 optionHtml += "<option value='11'>放弃报价,待维修</option>";
	 optionHtml += "<option value='12'>已维修,待报价</option>";
	 optionHtml += "<option value='13'>待终检</option>";
	 optionHtml += "<option value='14'>放弃维修</option>";
	 optionHtml += "<option value='15'>测试中</option>";
	 optionHtml += "<option value='16'>已测试,待维修</option>";
	 $("#"+selId).html(optionHtml);
 }
 
 /**
  * 数据发送到测试工站，公用部分
  */
 function sendWorkTestView(){	
		var SendInfo = $('#DataGrid1').datagrid('getSelections');
		if (SendInfo.length>0) {
			windowVisible("testUser_w");
			$('#testUser_w').window('open');
			initUserInfo("test_user", '', "无指定测试人员", ctx+"/user/UserListByOrgId", "oId=" + terminal_oId, "技术支持");
			$("#testuserAdd").unbind('click').click(function(){
				sendWorkTest();
			});
			$("#cancelTestUser").click(function(){
				windowCommClose('testUser_w');
			});
		}else{
			$.messager.alert("操作提示","未找到匹配数据","info");
		}
		
	}
 
 /**
  * 发送数据到下一个工站
  * @param currentSite
  */
 function sendDataToNextSite (currentSite) {
	 if (currentSite == 'customer') {
		 //客服     176  客服
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_customer_menuId, null);
	 } else if (currentSite == 'repair') {
		//维修     177  维修
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" +ycfk_repair_menuId, null);
	 } else  if (currentSite == 'terminal') {
		//终端技术     180    终端技术，2019-6-11 “终端技术”改为“技术支持”
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_terminal_menuId, null);
	 } else if (currentSite == 'platform') {
		//平台技术     179 平台技术
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_platform_menuId, null);
	 } else if (currentSite == 'quality') {
		//品质     167   品质部  QC主管  
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_quality_menuId, null);
	 } else  if (currentSite == 'product') {
		//产品     173  产品规划部
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_product_menuId, null);
	 } else  if (currentSite == 'project') {
		//项目    172   项目部
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_project_menuId, null);
	 } else  if (currentSite == 'jimi') {
		//几米    147   软件一部  技术支持
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_jimi_menuId, null);
	 } else  if (currentSite == 'market') {
		//市场 168市场部,20194/4，“客服文员”更变为“”
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_market_menuId, null);
	 } else  if (currentSite == 'research') {
		 // 研发工站
		 /*
		 var json = [
			 {uName: "杨明良"},{uName: "谭保祥"},{uName: "陈明亮"},
			 {uName: "吴南强"},{uName: "张涛"},{uName: "吕琴波"},
			 {uName: "谯森"},{uName: "边玉涛"},{uName: "罗宁"},
			 {uName: "刘启全"},{uName: "郭天文"},{uName: "蔡志兵"}
		 ];
		 initUserInfoForTen("followup_user", '', "请选择", json)
		  */
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_research_menuId, null);
	 } else  if (currentSite == 'test') {
		 // 测试工站
		 /*
		 var json = [{uName: "卢志才"},{uName: "匡宗智"}];
		 initUserInfoForTen("followup_user", '', "请选择", json)
		  */
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_test_menuId, null);
	 } else  if (currentSite == 'iot') {
		 // 物联网卡工站
		 /*
		 var json = [{uName: "罗云 "},{uName: "樊碧容"},{uName: "余燕飞"}];
		 initUserInfoForTen("followup_user", '', "请选择", json)
		  */
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_iot_menuId, null);
	 } else  if (currentSite == 'sim') {
		 // SIM卡平台工站
		 /*
		 var json = [{uName: "齐翌来"}];
		 initUserInfoForTen("followup_user", '', "请选择", json)
		  */
		 initUserInfo("followup_user", '', "请选择", ctx+"/user/UserListByMenu", "menuId=" + ycfk_sim_menuId, null);
	 }  else {
		 initUserInfo("followup_user", '', "请选择", '', "", "");
	 }
}


// 新增的10个工站初始化函数
 function initUserInfoForTen(selectId, defaultHtml, defaultText, option) {
	 var optionHtml = "<option value='"+defaultHtml+"'>"+defaultText+"</option>";
	 $(option).each(function(i,item){
		var optionValue = item.uName;
		optionHtml += "<option value='"+optionValue+"'>" + optionValue + "</option>";				
	});
	 $("#"+selectId).html(optionHtml);
 }
 
//获取受理天数（区分大小周）
 function getDayFromAcceptanceTime(acceptanceTime){
 	var t = (new Date().getTime()*1 - new Date(acceptanceTime).getTime()*1)/1000/3600/24;//当前时间和受理时间的时间差（天）
 	var acceptanceTime_week_index = new Date(acceptanceTime).getDay();//判断星期几
 	if(acceptanceTime_week_index >=1 && acceptanceTime_week_index <=3){
 		if(t >=1 && t < 2){
 			return 1;
 		}else if(t >= 2){
 			return 2;
 		}
 	}else{
 		if(isWork_sat(acceptanceTime)){
 			if(acceptanceTime_week_index == 4){
 				if(t >=1 && t < 2){
 					return 1;
 				}else if(t >= 2){
 					return 2;
 				}
 			}else if(acceptanceTime_week_index == 5){
 				if(t >=1 && t < 3){
 					return 1;
 				}else if(t >= 3){
 					return 2;
 				}
 			}else if(acceptanceTime_week_index == 6){
 				if(t >=2 && t < 3){
 					return 1;
 				}else if(t >= 3){
 					return 2;
 				}
 			}else if(acceptanceTime_week_index == 0){
 				if(t >=1 && t < 2){
 					return 1;
 				}else if(t >= 2){
 					return 2;
 				}
 			}
 		}else{
 			if(acceptanceTime_week_index == 4){
 				if(t >=1 && t < 4){
 					return 1;
 				}else if(t >= 4){
 					return 2;
 				}
 			}else if(acceptanceTime_week_index == 5){
 				if(t >=2 && t < 3){
 					return 1;
 				}else if(t >= 3){
 					return 2;
 				}
 			}else if(acceptanceTime_week_index == 6){
 				if(t >=2 && t < 3){
 					return 1;
 				}else if(t >= 3){
 					return 2;
 				}
 			}else if(acceptanceTime_week_index == 0){
 				if(t >=1 && t < 2){
 					return 1;
 				}else if(t >= 2){
 					return 2;
 				}
 			}
 		}
 	}
 }

 //受理日期所在星期六是否上班
 function isWork_sat(acceptanceTime){
 	var baseTime = StringToDate("2017-03-04 00:00:00");//星期六上班基准时间
 	var acceptanceTime_week_index = new Date(acceptanceTime).getDay();//判断星期几
 	var week_sat = "";//星期六
 	if(acceptanceTime_week_index != 0){//星期一
 		week_sat =new Date(new Date(acceptanceTime).getTime()*1 + (6 - acceptanceTime_week_index)*24*60*60*1000);
 	}else{
 		week_sat =new Date(new Date(acceptanceTime).getTime()*1 - 1*24*60*60*1000);
 	}
 	var t = (new Date(week_sat).getTime()*1 - baseTime.getTime()*1)/1000/3600/24;//当前时间和受理时间的时间差（天）
 	var isWork = Math.floor(t/7%2);
 	if(isWork == 0){
 		return true;
 	}else{
 		return false;
 	}
 	
 }


