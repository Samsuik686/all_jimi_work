////////////// 送修单位JS
 var currentPageNum_SXDW;
 var allRows = new Array();
 $(function(){
	 keySearch(queryListPageSXDW,1);
 });
/**
 * 获取当前分页参数
 * @param size		数据源
 * @param DataGrid	DataGrid ID
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
 function pageSetSXDW(size){
  var p = $("#DataGrid-Customer").datagrid('getPager');  
   $(p).pagination(
		{  
		    total:size,  
		    pageList:true,
		    pageSize:pageCount,    
		    showPageList:false,   
		    showRefresh:false,
		    beforePageText: '第',
		    afterPageText: '页    共 {pages} 页',  
		    displayMsg: '   共 {total} 条记录',  
			onBeforeRefresh:function(){ 
				$(this).pagination('loading'); 
				$(this).pagination('loaded'); 
			},
			onSelectPage:function(pageNumber, pageSize, total){ 
				$(this).pagination('loading'); 
				queryListPageSXDW(pageNumber);   
				$(this).pagination('loaded'); 
			},
			onChangePageSize:function(pageNumber, pageSize){ 
		       $(this).pagination('loading'); 
		       $(this).pagination('loaded'); 
   		 }  
     }); 
 }

/**
 * 获取缓存分页参数
 * @param DataGrid    DataGrid ID
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
 function getPageSizeSXDW(){
	  $.ajax({ 
	        type:"GET",     
	        async:false,    
	        cache:false,    
	       	url:ctx+"/form/queryCurrentPageSize.api",    
	        dataType:"json",
	        success:function(returnData){	
	        	if(returnData.code!=null){
	        		$.messager.alert("操作提示","系统繁忙，请稍候在试!","info");
	        		return 0;
	        	}
	        	pageSetSXDW(returnData.data);
	        	$("#customerModal").val("customer");
	        	$("#faultModal").val("");
	        	$("#fileModal").val("");
	        },   
	       error:function(){
			   $.messager.alert("操作提示","网络错误","info");
			   return 0;
		   }
    }); 
	  
 }
 
 /**
  * 当当前页处于>1时，点击某条件查询按钮后，分页栏还是未同步当前页码
  * @param currentPageNum	当前页码
  * @param DataGrid		DataGrid ID
  * @author Li.Shangzhi
  * @Date 2016-09-06 11:01:19
  */
 function resetCurrentPageSXDW(currentPageNum){
 	if(currentPageNum=="" || currentPageNum==null){
 		currentPageNum_SXDW=1;
 	}
 	$('#DataGrid-Customer').datagrid('getPager').pagination({
 		 pageNumber:currentPageNum_SXDW
 	});
 }

 function accept_openSXDW(){
	 $("#cusName").val("");
	 $("#linkman").val("");
	 windowCommOpen("wkh");
	 queryListPageSXDW(1);
}

 
 /**
  * 选择送修单位信息
  */
 function chooseCustomer(rowindex, allRows){
 	allRows = allRows;
	windowVisible("wkh");
	queryListPageSXDW(1);
	if(rowindex){
		$("#hideSindex").val(rowindex);
	}	
// 	windowCommOpen('wkh');
	accept_openSXDW();
 	
 }
 
 
 /**
  * 送修单位List
  */
 function queryListPageSXDW(pageNumber){
 	currentPageNum_SXDW = pageNumber;
 	if(currentPageNum_SXDW=="" || currentPageNum_SXDW==null){
 		currentPageNum_SXDW=1;
 	}
 	var url=ctx+"/sxdwmanage/sxdwmanageList";
 	var param="cusName=" +$.trim($("#cusName").val())
 			  +"&linkman="+$.trim($("#linkman").val())
 			  +"&phone="+$.trim($("#phone").val())
 			  + "&showType=accept"
 			  +"&currentpage="+currentPageNum_SXDW;
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			$("#DataGrid-Customer").datagrid('loadData',returnData.data); 
 			getPageSizeSXDW();
 			resetCurrentPageSXDW(currentPageNum_SXDW);
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		$.messager.alert("操作提示","网络错误","info");
 	});
 }
 
 

 /**
  * 双击选择送修单位
  */
 function dbClickChooseCustomer(index,value){
	var TABLE_Cell =$("#hideSindex").val();
 	var getCurrentId=value.cid;
 	var param="cId="+getCurrentId;
 	var url=ctx+"/sxdwmanage/getSxdwmanage";
 	asyncCallService(url, 'post', false,'json', param, function(returnData){
 		if(returnData.code==undefined){ 
 			var entity=returnData.data;
 			var TABLE_Cell =$("#hideSindex").val();
 			if(TABLE_Cell==null || TABLE_Cell==""){          //非扫描处理
 				//修改受理信息时选择客户
 				$('#fm_w_cusName').val(entity.cusName);
 				$('#fm_w_sxdwId').val(entity.cid);
 				$('#fm_w_linkman').val(entity.linkman);
 				$('#fm_w_phone').val(entity.phone);
 				$('#fm_w_telephone').val(entity.telephone);
 				$('#fm_w_email').val(entity.email);
 				$('#fm_w_fax').val(entity.fax);
 				$('#fm_w_address').val(entity.address);	
 				changeSxdw();//更改送修单位，判断保内保外
 			}else{						   				     //扫描处理
 				//TODO 送修批号处理
 			    if(CommScanBatchMap[entity.cid] == null || CommScanBatchMap[entity.cid]==undefined){
 			    	CommScanBatchMap[entity.cid] = entity.repairnum;
 			    }
 				$("#cusName-"+TABLE_Cell).val(entity.cusName); 			   
 				$("#sxdwId-"+TABLE_Cell).val(entity.cid);
 				$("#repairnNmber-"+TABLE_Cell).val(CommScanBatchMap[entity.cid]);
 				
 			    if($("#repairnNmber-1").val() && $("#sxdwId-1").val() == entity.cid){
 			    	$("#repairnNmber-"+TABLE_Cell).val($("#repairnNmber-1").val());
 			    }
 				
 				$("#hideSindex").val(null);
 				if(entity.isExistsAndPay == 0){
 					//工作流中存在此批号，再次受理时生成的是同一个送修批号，设置客户寄货方式为第一次选中的值
 					var delivery = entity.isDelivery;
 					$("input[name='payDelivery-"+TABLE_Cell+"'][value='" + delivery + "']").prop("checked", "checked");
 					$("input[name='payDelivery-"+TABLE_Cell+"']").prop('disabled',true);
 					$("#pricedCount-"+TABLE_Cell).val(entity.pricedCount);
 					$("#oldRepairNumber-" + TABLE_Cell).val(entity.oldRepairNumber); // 修改前的批次号
 					
 					//该批次已经报价，是否更改批次号
 					if($("#pricedCount-"+TABLE_Cell).val() == 1){
 						var repArr = [];
 						$("input[name='oldRepairNumber']").each(function(i,j) {
 							if($("#sxdwId-"+(i+1)).val() && $("#oldRepairNumber-"+(i+1)).val() && $("#sxdwId-"+(i+1)).val() == $("#sxdwId-"+TABLE_Cell).val() 
 									&& $("#oldRepairNumber-" + TABLE_Cell).val() != $("#repairnNmber-"+(i+1)).val()){
 								repArr.push($("#repairnNmber-"+(i+1)).val());
 							}
 						});
 						if(repArr.length > 0){
 							$("#repairnNmber-" + TABLE_Cell).val(repArr[0]);
 						}else{
 							$.messager.confirm('系统提示', '批次号【'+($("#repairnNmber-" + TABLE_Cell).val())+'】的设备已报价，是否重新生成批次号?', function(conf) {
 								if (conf) {
 									var currentRepairNumber = new Date().getTime();
 									$("input[name='repairnNumber']").each(function(i, j){
 										if($("#sxdwId-"+(i+1)).val() && $("#sxdwId-"+(i+1)).val() == $("#sxdwId-"+TABLE_Cell).val()){
 											$("#repairnNmber-"+(i+1)).val(currentRepairNumber);
 										}
 									});
 								}
 							});
 						}
					}
 				}else{					
 					if(allRows.length > 1 ){
 						var min = Math.min.apply(null, allRows);
 						var v = $("input[name='payDelivery-"+min+"']:checked ").val();
 						$("input[name='payDelivery-"+rowindex+"'][value='"+v+"']").prop("checked", "checked");
 					}else{
 						$("input[name='payDelivery-"+TABLE_Cell+"'][value='N']").prop("checked", "checked");	 					
 					} 	
 					$("input[name='payDelivery-"+TABLE_Cell+"']").prop('disabled',false);
 				}
 			}
 			if($("#cusName-"+TABLE_Cell).val() && $("#cusName-"+TABLE_Cell).val().indexOf("营销三部") > -1){
 				
 				var url=ctx+"/workflowcon/zbxhIsWarranty";
 			 	var param = {
 			 			"xhId" : $("#xhId-"+TABLE_Cell).val(),
 			 			"sxdw" : $("#cusName-"+TABLE_Cell).val(),
 			 			"salesTime" : $("#salesTime-" + TABLE_Cell).val()
 			 	};
 				asyncCallService(url, 'post', false,'json', param, function(returnData) {
 		 			processSSOOrPrivilege(returnData);
 		 			if(returnData.code=='0'){
 		 				var ret=returnData.data;
 		 				if(ret==0){
 		 					$("#isWarranty-"+TABLE_Cell).val('保内');
 		 				}else if(ret==1){
 		 					$("#isWarranty-"+TABLE_Cell).val('保外');
 		 				}else{
 		 					$("#isWarranty-"+TABLE_Cell).val('未知');
 		 				}
 		 			}
 		 		});
 			}else{
 	 			zbxh_window_close(TABLE_Cell);
 			}
 			windowCommClose("wkh");
 		}else{
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }
 
//更改无名件，判断保内保外
 function changeSxdw(){
 	if($("#fm_xhId").val()){
 		//判断保内保外
 		var url=ctx+"/workflowcon/zbxhIsWarranty";
 		var param="xhId=" + $("#fm_xhId").val() + "&salesTime="+ $("#fm_salesTime").datebox("getValue") + "&sxdw=" + $("#fm_w_cusName").val(); 
 		asyncCallService(url, 'post', false,'json', param, function(returnData) {
 			processSSOOrPrivilege(returnData);
 			if(returnData.code=='0'){
 				var ret=returnData.data;
 				if(ret==0){
 					$("#fm_isWarranty").val('保内');
 				}else if(ret==1){
 					$("#fm_isWarranty").val('保外');
 				}else{
 					$("#fm_isWarranty").val('未知');
 				}
 			}
 		});
 	}
 }
 
 /**
  * 复制送修单位，给后面的列赋相同的值
  */
 function copyCusName(rowindex){
 	var selectIds = $("#sxdwId-"+rowindex).val();
 	var selectName =$("#cusName-"+rowindex).val();
 	var selectRepairn =$("#repairnNmber-"+rowindex).val();
 	var v = $("input[name='payDelivery-"+rowindex+"']:checked ").val(); //顺付，到付，对已有的批号做统一处理
 	var d = $("input[name='payDelivery-"+rowindex+"']").prop('disabled');//同一批次的再次受理
 	if(selectIds){
 		$("input[name='sxdwId']").each(function() {
 			var id = $(this).attr("id");
 			var strs= new Array(); //定义一数组
 			strs=id.split("-"); //字符分割 
 			if(strs[1]>rowindex){
 				//给先删除，后新增的行赋值
 				$("#sxdwId-"+strs[1]).val(selectIds);
 				$("#cusName-"+strs[1]).val(selectName);	
 				$("#repairnNmber-"+strs[1]).val(selectRepairn);
 				$("input[name='payDelivery-"+strs[1]+"'][value='"+v+"']").prop("checked", "checked");
 				$("input[name='payDelivery-"+strs[1]+"']").prop('disabled',d);
 				if($("#cusName-"+strs[1]).val() && $("#cusName-" + strs[1]).val().indexOf("营销三部") > -1){
 					var url=ctx+"/workflowcon/zbxhIsWarranty";
 	 			 	var param = {
 	 			 			"xhId" : $("#xhId-"+strs[1]).val(),
 	 			 			"sxdw" : $("#cusName-" + strs[1]).val(),
 	 			 			"salesTime" : $("#salesTime-" + strs[1]).val()
 	 			 	};
 	 				asyncCallService(url, 'post', false,'json', param, function(returnData) {
 	 		 			processSSOOrPrivilege(returnData);
 	 		 			if(returnData.code=='0'){
 	 		 				var ret=returnData.data;
 	 		 				if(ret==0){
 	 		 					$("#isWarranty-"+strs[1]).val('保内');
 	 		 				}else if(ret==1){
 	 		 					$("#isWarranty-"+strs[1]).val('保外');
 	 		 				}else{
 	 		 					$("#isWarranty-"+strs[1]).val('未知');
 	 		 				}
 	 		 			}
 	 		 		});	
 				}else{
 					zbxh_window_close(strs[1]);
 				}
 			}
 		});
 	}
 }
 
 function sxdwmanageInsert(){
		
		$("#cId_hidden").val(0);
		//清空下缓存
		$("#cusNameP").val("");
		$("#serviceTimeP").val("");
		$("#linkmanP").val("");
		$("#phoneP").val("");
	    $("#telephoneP").val("");
		$("#emailP").val("");
		$("#faxP").val("");
		$("#addressP").val("");
		$("#remarkP").val("");
		//加载所有MenuId及对应的FunctionId
		//loadAllMenuFunction();
		addCustomerWindowOpen();
		 
	}
 
 function sxdwmanageSave(){
		var isValid = $('#custform').form('validate');
		if(isValid){
			var cusName=$("#cusNameP").val();
			var serviceTime=$("#serviceTimeP").val();
			var linkman=$("#linkmanP").val();
			var phone=$("#phoneP").val();
			var telephone=$("#telephoneP").val();
			var email=$("#emailP").val();
			var fax=$("#faxP").val();
			var address=$("#addressP").val();
			var remark=$("#remarkP").val();
			var reqAjaxPrams="&cusName="+cusName+"&serviceTime="+serviceTime+"&linkman="+linkman+"&phone="+phone+"&telephone="+telephone+"&email="+email
							+ "&fax="+fax+"&address="+address+"&remark="+remark+ "&enabledFlag=0&id=0";
			var url=ctx+"/sxdwmanage/insertSxdwmanage";
			asyncCallService(url, 'post', false,'json', reqAjaxPrams, function(returnData){
				
				processSSOOrPrivilege(returnData);

				if(returnData.code=='0'){
					addCustomerWindowClose();
					queryListPageSXDW(1);//重新加载SxdwmanageList
				}
				$.messager.alert("操作提示",returnData.msg,"info");
			}, function(){
				 $.messager.alert("操作提示","网络错误","info");
			});
		}
	}
 
 function addCustomerWindowClose(){
 	$('#addCustomerWindow').window('close');
 }

 function addCustomerWindowOpen(){
	windowVisible("addCustomerWindow");
 	$(".validatebox-tip").remove();
 	$(".validatebox-invalid").removeClass("validatebox-invalid");
 	$('#addCustomerWindow').window('open');
 	
 }
 