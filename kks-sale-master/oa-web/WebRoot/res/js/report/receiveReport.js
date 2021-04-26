$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
   datagridInit(); 
   getCurrentMonth();
   keySearch(queryReceiveList);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	$("#Table_Accqute").datagrid({
	});
	queryReceiveList(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

function queryReceiveList(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/reportCon/getReceiveList";
	var param= null;
	if(!$('#dateTime').val()){
		getCurrentMonth();
	}
	var dateTime = $('#dateTime').val();				
	if(dateTime !=""){
		param="dateTime="+dateTime+
			  "&model="+$("#model").val()+
			  "&startTime="+$("#startTime").val()+
			  "&endTime="+$("#endTime").val();
		$("#Table_Accqute").datagrid('loading');
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){ 
				var ret = returnData.data;
				if (ret && ret.length > 0) {
					$.each(ret, function(i, item) {
						if(i == ret.length-1){
							item.model="<label style='font-weight: bold;'>"+item.model+"</label>";
						if(item.date1!=null)	item.date1="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date1+"</label>";
						if(item.date2!=null)    item.date2="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date2+"</label>";
						if(item.date3!=null)	item.date3="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date3+"</label>";
						if(item.date4!=null)	item.date4="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date4+"</label>";
						if(item.date5!=null)	item.date5="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date5+"</label>";
						if(item.date6!=null)	item.date6="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date6+"</label>";
						if(item.date7!=null)	item.date7="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date7+"</label>";
						if(item.date8!=null)	item.date8="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date8+"</label>";
						if(item.date9!=null)	item.date9="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date9+"</label>";
						if(item.date10!=null)	item.date10="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date10+"</label>";
						if(item.date11!=null)	item.date11="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date11+"</label>";
						if(item.date12!=null)	item.date12="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date12+"</label>";
						if(item.date13!=null)	item.date13="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date13+"</label>";
						if(item.date14!=null)	item.date14="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date14+"</label>";
						if(item.date15!=null)	item.date15="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date15+"</label>";
						if(item.date16!=null)	item.date16="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date16+"</label>";
						if(item.date17!=null)	item.date17="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date17+"</label>";
						if(item.date18!=null)	item.date18="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date18+"</label>";
						if(item.date19!=null)	item.date19="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date19+"</label>";
						if(item.date20!=null)	item.date20="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date20+"</label>";
						if(item.date21!=null)	item.date21="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date21+"</label>";
						if(item.date22!=null)	item.date22="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date22+"</label>";
						if(item.date23!=null)	item.date23="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date23+"</label>";
						if(item.date24!=null)	item.date24="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date24+"</label>";
						if(item.date25!=null)	item.date25="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date25+"</label>";
						if(item.date26!=null)	item.date26="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date26+"</label>";
						if(item.date27!=null)	item.date27="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date27+"</label>";
						if(item.date28!=null)	item.date28="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date28+"</label>";
						if(item.date29!=null)	item.date29="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date29+"</label>";
						if(item.date30!=null)	item.date30="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date30+"</label>";
						if(item.date31!=null)	item.date31="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.date31+"</label>";
						if(item.totalNum!=null)	item.totalNum="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.totalNum+"</label>";
						}
					});
				}
				$("#Table_Accqute").datagrid('loadData',returnData.data);
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
			$("#Table_Accqute").datagrid('loaded');
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	}
}

function getCurrentMonth(){
	var d=new Date();
	function addZero(v){
		if(v<10){
			return '0'+v;
		}else{
			return v;
		}
	}
	var	defaultDate=d.getFullYear() + '-' + addZero(d.getMonth()+1);
	$("#dateTime").val(defaultDate);
}

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	if(!$('#dateTime').val()){
		getCurrentMonth();
	}
	var dateTime = $("#dateTime").val();
	if(dateTime !=""){
		ExportExcelDatas("reportCon/receiveExport?dateTime="+dateTime+
				"&model=" + $.trim($("#model").val()) + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val());
	}else{
		$.messager.alert("操作提示","请选择导出日期","info");
	}
}


