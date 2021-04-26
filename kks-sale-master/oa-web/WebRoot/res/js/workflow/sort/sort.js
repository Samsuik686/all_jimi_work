$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;
var Heartbeat =10000 //10秒
var nimeiAginSubmit = false;
$(function(){    
   datagridInit(); 
   keySearch(queryListPage);
   $("#scanimei").focus();//扫描IMEI获得焦点   
   
   $('#searchinfo').click(function(){
   	 $('#tree-Date').val("");
     $("#tree-State").val("");
     queryListPage(1);  
  });
   
   $('#returnAccount').click(function(){
	   sendDataToAccept();
   });
   
   //获取推送消息
   setInterval("runTip('TIP_FJ')", 5000);  
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	//受理列表工具栏
	$("#DataGrid1").datagrid({
      singleSelect:false		
	});
	queryListPage(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

//--------------------------------------------- 分拣列表管理  ------------------------------------------ START
/**
 * 计时刷新同步数据
 */
function syscDBDatas() {
	window.setInterval("queryListPage(1)",Heartbeat); 
}


/**
 * 初始化分拣列表
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber) {
	$("#showTip").hide();
	if(pageNumber == -1){
		$("#scanimei").val("");
		$("#scanimei").focus();
		pageNumber = 1;
	}
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	getTreeNode();
	var sortState = $("#sortState").combobox('getValue');
	if(!$("#tree-Date").val() && sortState == '1' && (!$("#startTime").val() || !$("#endTime").val())){
		$.messager.alert("操作提示","查询已完成数据请选择开始，结束日期","info");
		return;
	}
	var url=ctx+"/sortcon/sortList";
    var selParams="imei="+$.trim($("#imei").val())
    			+"&lockId=" + $.trim($("#lockId").val())
    			+"&w_cusName="+$.trim($("#w_cusName").val())
    			+"&repairnNmber="+$.trim($("#repairnNmber").val())
    			+"&w_model="+$.trim($("#w_model").val())
    			+"&w_modelType="+$.trim($("#w_modelType").val())
    			+"&w_cjgzDesc="+$.trim($("#w_cjgzDesc").val())
    			+"&sortState="+sortState
		    	+"&startTime="+$("#startTime").val() 
		    	+"&endTime="+$("#endTime").val() 
		    	+"&treeDate="+$("#tree-Date").val();
    var param = addPageParam('DataGrid1', currentPageNum, selParams);
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$.each(returnData.data,function(i, item){
				//上次受理日期
				if(item.lastAccTime){
					var t = (new Date(item.acceptanceTime)*1 - new Date(item.lastAccTime).getTime()*1)/1000/3600/24;//上次受理时间和受理时间的时间差（天）
					if(t <= 92){
						item.lastAccTime = "<label style='background-color:#EE3B3B;'>" + item.lastAccTime + "</label>";
					}
				}
				//距受理时间1天，受理时间显示黄色，两天，红色
				if(item.state == "1"){//分拣工站未发送数据
					var t =	getDayFromAcceptanceTime(item.acceptanceTime);
					if(t == 1){
						item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
					}else if(t == 2){
						item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
					}
				}
				// 试流料
				if (item.testMatStatus == "是") {
					item.testMatStatus = "<label style='color:#0000FF;font-weight: bold;'>是</label>";
				}else if (item.testMatStatus == "否"){
					item.testMatStatus = "<label style='color:#808080;;font-weight: bold;'>否</label>";
				}
				
				//保内/保外
				if(item.isWarranty==0){
					item.isWarranty="保内";
				}else{
					item.isWarranty="保外";
				}
				//状态
				if(item.state=="1"){
					item.state="<label style='color:red;font-weight: bold;'>已受理,待分拣</label>"; 
				}else if(item.state=="18"){
					item.state="<label style='color:red;font-weight: bold;'>已测试,待分拣</label>"; 
				}else{
					item.state="<label style='color:green;font-weight: bold;'>已完成</label>"; 
				}
				if(item.lastEngineer){
					item.lastEngineer = "<label style='color:red;font-weight: bold;'>"+ item.lastEngineer +"</label>";
				}
			});
			$("#DataGrid1").datagrid('loadData',returnData.data);  
			getPageSize();
			resetCurrentPageShow(currentPageNum);
			$("#scanimei").val("");
			$("#scanimei").focus();
		}else {
			$.messager.alert("操作提示",returnData.msg,"info", function(){
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		}
		$("#DataGrid1").datagrid('loaded');	
		$("#acc_number").html($("#DataGrid1").datagrid('getRows').length);
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
	//重置推送消息
    resetTip('TIP_FJ');
}

function getTreeNode(){
	var treeDate = $('#tree-Date').val();
	if(treeDate && treeDate!='1' && treeDate!='2'){
		//点击tree查询清空处理状态和开始结束日期
		$("#sortState").combobox('setValue','0');
		$("#startTime").val("");
		$("#endTime").val("");
	}else{
		$('#tree-Date').val("");
	}
	var treeState = $("#tree-State").val();
	if(treeState=='2'){
		//查询已完成的数据
		$("#sortState").combobox('setValue','1');
	}else if(treeState=='1'){
		$("#sortState").combobox('setValue','0');
	}
}

var sortFlag=true;//分拣标识，为true时才允许分拣
/**
 * 同步筛选扫描数据
 */
function scancData(event) {	
	$('#DataGrid1').datagrid("clearSelections");
	var Scanimei =$.trim($("#scanimei").val());
//	var evt=evt?evt:(window.event?window.event:null); //兼容IE和FF
	var evt = event;
	
	if((Scanimei.length >=6 && evt.keyCode==13) && !nimeiAginSubmit){
		var rows = $("#DataGrid1").datagrid("getRows");
		var ids = null;
		if(rows){
			for (var int = 0; int < rows.length; int++) {
				var row = rows[int];
				if(($.trim(row.imei)==Scanimei || $.trim(row.lockId)==Scanimei || $.trim(row.lockInfo)==Scanimei) && row.state.indexOf('已完成') == -1){
					$('#DataGrid1').datagrid('selectRow',int);
					ids=row.id;
				}
			}
			if(null !=ids && sortFlag){
				sortFlag=false;
				nimeiAginSubmit = true;
				$("#scanimei").blur();
				var url=ctx+"/sortcon/sendRepair";
				var param="ids="+ids; 
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					nimeiAginSubmit = false;
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$("#scanimei").val("");
						queryListPage(1); 
						$("#scanimei").focus();
					}else {
						$.messager.alert("操作提示",returnData.msg,"info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});;
					}
					sortFlag=true;
				}, function(){
					$.messager.alert("操作提示","网络错误","info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
					sortFlag=true;
				});
			}else{
				$("#scanimei").blur();
				nimeiAginSubmit = false;
				$('#DataGrid1').datagrid("clearSelections");
				$.messager.alert("操作提示","未匹配到数据","warning",function(){
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			}
		}
	}else if(Scanimei.length > 15 && !nimeiAginSubmit){
		nimeiAginSubmit = false;
		$.messager.alert("操作提示","未匹配到数据","warning",function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	}else{
		$("#scanimei").focus();
		nimeiAginSubmit = false;
	}
	
}

//分拣数据返回到受理工站
function sendDataToAccept(){
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	var all = true;
	var marked_words="";
	if (SendInfo.length>0){
		marked_words = "确定要发送<font color='red'>选中数据</font>到受理工站？";
		all = false;
	}else{
		SendInfo = $("#DataGrid1").datagrid("getRows"); //获取当前页面所有数据
		if(SendInfo.length>0){
			//一键发送符合条件的数据到受理
			marked_words = "确定要发送所有<font color='red'>已受理，待分拣</font>的数据到受理工站？";
		}else{
			$.messager.alert("操作提示","未找到匹配数据","info");
			return;
		}
	}
	 $.messager.confirm("操作提示",marked_words,function(conf){
			if(conf){
				var ids = "";
				for (var int = 0; int < SendInfo.length; int++) {	
					var state = SendInfo[int].state.substring(SendInfo[int].state.indexOf(">")+1,SendInfo[int].state.indexOf("</label>")).trim()		
					if(state == '已完成'){
						$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>是已完成的数据禁止操作","info");
						return;
					}
					(int == 0)?ids = SendInfo[int].id:ids = SendInfo[int].id+","+ids;
					
				}
				if(ids){
					var url=ctx+"/sortcon/sendDataToAccept";
				    var param="ids="+ids; 
					asyncCallService(url, 'post', false,'json', param, function(returnData){
						processSSOOrPrivilege(returnData);
						if(returnData.code=='0'){
							$.messager.alert("操作提示","匹配数据返受理工站成功！","info");
							queryListPage(1);            	
						}else {
							$.messager.alert("操作提示",returnData.msg,"info");
						}
					}, function(){
						 	$.messager.alert("操作提示","网络错误","info");
					});
				}else{
					$.messager.alert("操作提示","未找到匹配数据","info");
				}
			}
	 });
}
//--------------------------------------------- 分拣列表管理  ------------------------------------------ End