$(function(){    
	WorkTreeInit();
});
/**------------------------------------------------------------- 工作站 右侧日期树 公共组件 Start----------------------------------------------------**/
function WorkTreeInit(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/timetree/queryList',
		data : null,
		dataType : "json",
		success : function(returnData) {
			WorkTreeLoad(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}
function WorkTreeLoad(returnData) {
	var type = getUrlParameter("workType");
	 	
  		var groupList = '{"id":"1","text":"待处理",children:[';

  		var parentTree = "";	
  		if(type == 'accept'){
  			parentTree = '[{"id":"","text":"受理单",children:[';		
  	  		groupList = '{"id":"1","text":"已受理",children:[';
  	  		;
  		}else if(type == 'sort'){
  			parentTree = '[{"id":"","text":"分拣单",children:[';	
  			groupList = '{"id":"1","text":"待分拣",children:[';
  	  		; 
  		}else if(type == 'repair'){
  			parentTree = '[{"id":"","text":"维修单",children:[';	
  			groupList = '{"id":"1","text":"待维修",children:[';
  	  		; 
  		}else if(type == 'price'){
  			parentTree = '[{"id":"","text":"报价单",children:[';	
  			groupList = '{"id":"1","text":"待报价",children:[';
  	  		;
  		}else if(type == 'ficheck'){
  			parentTree = '[{"id":"","text":"终检单",children:[';	
  			groupList = '{"id":"1","text":"待终检",children:[';
  	  		;
  		}else if(type == 'pack'){
  			parentTree = '[{"id":"","text":"装箱单(周)",children:[';	
  			groupList = '{"id":"1","text":"待装箱",children:[';
  	  		;
  		} else if (type == 'test') {
  			parentTree = '[{"id":"","text":"测试单",children:[';	
  			groupList = '{"id":"1","text":"待测试",children:[';
  	  		;
  		}else if(type == "eqpNotSendInfo"){
			parentTree = '[{"id":"","text":"未寄出设备",children:[';
			groupList = '{"id":"1","text":"未寄出",children:[';
			;
		}
  		
  		if(type == 'ficheck' || type == 'accept'){
  			$.each(returnData.data, function(i, j) {
  	  			groupList += '{"id":"' + j.id + '上午","text":"' + j.text + ' 上午"},';
  	  			groupList += '{"id":"' + j.id + '下午","text":"' + j.text + ' 下午"},';
  	  		});
  	  		var reg = /,$/gi;
  	  		groupList = groupList.replace(reg, "");
  	  		groupList += ']},';
  	  		
  	  		groupList += '{"id":"2","text":"已完成",children:[';
  	  	  	;
  	  	  	$.each(returnData.data, function(i, j) {
 	  			groupList += '{"id":"' + j.id + '上午","text":"' + j.text + ' 上午"},';
  	  			groupList += '{"id":"' + j.id + '下午","text":"' + j.text + ' 下午"},';
  	  	  	});
  	  	  	var reg = /,$/gi;
  	  	  	groupList = groupList.replace(reg, "");
  	  	  	groupList += ']}';
  	  	  	
  	  	  parentTree += groupList+']}]';
  		}else{
  			$.each(returnData.data, function(i, j) {
  	  			groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
  	  		});
  	  		var reg = /,$/gi;
  	  		groupList = groupList.replace(reg, "");
  	  		groupList += ']},';
  	  		
  	  		groupList += '{"id":"2","text":"已完成",children:[';
  	  	  	;
  	  	  	$.each(returnData.data, function(i, j) {
  	  	  		groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
  	  	  	});
  	  	  	var reg = /,$/gi;
  	  	  	groupList = groupList.replace(reg, "");
  	  	  	groupList += ']}';
  	  	  	
  	  	  parentTree += groupList+']}]';
  		}
  		
	$('#typeTreeTime').tree(
		 {
			data : eval(parentTree),
			onClick : function(node){
				$("#tree-Date").val(node.id);
				if(node.id == '1' || node.id == '2'){
					$("#tree-State").val(node.id);
				}else{
					var parentNode = $('#tree-Date').tree('getParent',node.target);
					$("#tree-State").val(parentNode.id);
				}
				//装箱区分两个树的数据
				if($("#treeType").val()){
					$("#treeType").val("");
				}
				queryListPage(1);
			}
		 }
     );
}
/**------------------------------------------------------------- 工作站 左侧日期树 公共组件 End----------------------------------------------------**/