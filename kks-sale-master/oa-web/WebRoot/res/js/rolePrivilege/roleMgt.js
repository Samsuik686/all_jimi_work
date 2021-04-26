var currentPageNum;

$(function(){    
   datagridInit(); 
   keySearch(queryListPage);
}); 

function queryListPage(pageNumber)  {//初始化数据及查询函数
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/rolePrivilege/roleList";
    var param="rolerName="+$.trim($("#rolerName").val())+"&rolerDesc="+$.trim($("#rolerDesc").val()); 
    param =addPageParam('DataGrid1',currentPageNum,param);
    $("#DataGrid1").datagrid('loading');
    asyncCallService(url, 'post', false,'json', param, function(returnData){
		
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$("#DataGrid1").datagrid('loadData',returnData.data);  
        	getPageSize();
        	resetCurrentPageShow(currentPageNum);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
 } 
 
$.messager.defaults= { ok: "确定",cancel: "取消" };
   
 function datagridInit()  
{
	var lastIndex;
 
	$("#DataGrid1").datagrid({
     singleSelect:true,//是否单选  
 
//		toolbar:[
//		{
//			text:'增加角色', 
//			iconCls:'icon-addicon',
//			handler:function(){
//					roleAndPrivilegeAdd();
//			} 
//		},'-',{
//			text:'角色权限', 
//			iconCls:'icon-jsqxicon',
//			handler:function(){  
//					roleAndPrivilegeSearch();
//			}      
//		},'-',{      
//				text:'删除',
//				iconCls:'icon-delecticon',
//				handler:function(){    
//					
//				}    
//			},'-',{
//				text:'保存',
//				iconCls:'icon-save',
//				handler:function(){
//					
//					  
//	                 
//				}
//			},'-',{
//				 text:'刷新', 
//				 iconCls:'icon-reload',
//				 handler:function(){
//					 queryListPage(currentPageNum);
//			     } 
//			  }
//			],
			onDblClickRow:function(rowIndex){
//				if(lastIndex!=rowIndex) 看不出这个判断有什么用，导致同一行在不刷新列表的情况下，不能连续双击
//					{
					   $('#DataGrid1').datagrid('endEdit',lastIndex);
					   $('#DataGrid1').datagrid('beginEdit',rowIndex);
//					}
				lastIndex=rowIndex; 
			}
	}); 
	
	queryListPage(1);//初始化查询页面数据，必须放在datagrid()初始化调用之后
}      
 
function roleAndPrivilegeAdd(){
	
	$("#rolerId_hidden").val(0);
	//清空下缓存
	$("#rolerNameP").val("");
	$("#rolerDescP").val("");
	
	//加载所有MenuId及对应的FunctionId
	//loadAllMenuFunction();
	 windowOpenRole();
	 
	//改变弹出框位置add by wg.he 2013/12/10
	 chageWinSize('w');
}

function roleAndPrivilegeSearch(){
	var selected = $('#DataGrid1').datagrid('getSelected');
	if (selected){
		
		//改变弹出框位置add by wg.he 2013/12/10
		chageWinSize('privilegeId');
    
		//加载所有MenuId及对应的FunctionId
		loadAllMenuFunction(selected.rolerId);
		
	}else{
		$.messager.alert("提示信息","请先选择角色！","info");
	}
}

/**
 * 删除
 */
function roleAndPrivilege_deleteInfo(){
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row){
	  $.messager.confirm("操作提示","是否删除此角色信息",function(conf){
		if(conf){
			var deleted = row.rolerId;
			var url=ctx+"/rolePrivilege/roleDelete";
    		var param="rolerId="+deleted;
    		if(deleted!=44){//基础权限不能删除
    			asyncCallService(url, 'post', false,'json', param, function(returnData){
					 processSSOOrPrivilege(returnData);
					 $.messager.alert("操作提示",returnData.msg,"info");
    				 queryListPage(currentPageNum);//重新加载RoleList
				 }, function(){
        			 $.messager.alert("操作提示","网络错误","info");
        		});
    		}else {
    			$.messager.alert("操作提示","基础权限不能删除!","info");
    			queryListPage(currentPageNum);//重新加载RoleList
    		}
    	}
	  });
	}else {
		$.messager.alert("提示信息","请先选择所要删除的行！","info");
	}
 
}

/**
 * 保存
 */
function roleAndPrivilege_save(){
	 endEdit();
	 
     var updated = $('#DataGrid1').datagrid('getChanges', "updated"); 
      if(updated.length>0)
  	{
    	  if(updated[0].rolerName !="")
          {
    		  var url=ctx+"/rolePrivilege/roleUpdate";
    		  
    		  for(var j=0;j<updated.length;j++)
    		  {
    			  var param="rolerId="+updated[j].rolerId
    			  +"&rolerName="+updated[j].rolerName
    			  +"&rolerDesc="+updated[j].rolerDesc;
    			  
    			  asyncCallService(url, 'post', false,'json', param, function(returnData){
    				  
    				  processSSOOrPrivilege(returnData);
    				  
    				  if(returnData.code=='0'){
    					  queryListPage(currentPageNum);//重新加载RoleList
    				  }
    				  $.messager.alert("操作提示",returnData.msg,"info");		                					                				
    			  }, function(){
    				  $.messager.alert("操作提示","网络错误","info");
    			  });
    			  
    		  }
    		  $('#DataGrid1').datagrid('acceptChanges');  
          }
    	  else
		  {
    		  $.messager.alert("操作提示","角色名称不能为空","info",function(){
    			  refreshInfo();
    		  });
		  }
  	}else{
  		 $.messager.alert("操作提示","您未对数据进行修改操作","info");
  	}
}

/**
 * 刷新
 */
function refreshInfo(){
	queryListPage(currentPageNum);
}

function loadMenuFunctionByCurrentRolerId(getRolerId){
	
    //查看角色信息
    var url=ctx+"/rolePrivilege/getRolerInfosByRolerId";
	var param="rolerId="+getRolerId;
	asyncCallService(url, 'get', false,'json', param, function(returnData){
		
		 processSSOOrPrivilege(returnData);
		 
		 if(returnData.code==undefined){
				//显示角色信息及设置FunctionId被选状态
			 var getRoleInfo=returnData.data;
			 $("#rolerId_hidden_p").val(getRolerId);
			 
			 $("#rolerNameShowId").html(getRoleInfo.rolerName);
			 $("#rolerDescShowId").html(getRoleInfo.rolerDesc);
			
			 var getRoleMenuInfo=getRoleInfo.roleMenus;
			 $(getRoleMenuInfo).each(function(x){
				 var menusShow=getRoleMenuInfo[x];
				 $("#fun"+menusShow.functionId).prop("checked", true);;
			 });
			 
		 }else {
			 $.messager.alert("操作提示",returnData.msg,"info");
		 }
		 
	 }, function(){
		 $.messager.alert("操作提示","网络错误","info");
	});
}

function loadAllMenuFunction(getRolerId){
	var url=ctx+"/rolePrivilege/allMenuFuncLoad";
	asyncCallService(url, 'get', true,'json', '', function(returnData){
		processSSOOrPrivilege(returnData);
		windowOpenMenu();
		processHTMLByReturnData(returnData);
		
		loadMenuFunctionByCurrentRolerId(getRolerId);
		
	}, function(){
		 $.messager.alert("操作提示","网络错误","info");
	});
}

function parentCheckboxClick(parentId,status){
	if(status){
		var parentObj=$("input[id=menuParent"+parentId+"]");
		parentObj.prop("checked",true);
		
		$(parentObj).each(function(){
			var getMenuFuncIds=$(this).val();
			var menuFuncIds=getMenuFuncIds.split(",");
			for(var i=0;i<getMenuFuncIds.length;i++){
				$("#fun"+menuFuncIds[i]).prop("checked",true);
			}
		});
		
	}else {
		var parentObj=$("input[id=menuParent"+parentId+"]");
		parentObj.prop("checked", false);
		
		$(parentObj).each(function(){
			var getMenuFuncIds=$(this).val();
			var menuFuncIds=getMenuFuncIds.split(",");
			for(var i=0;i<getMenuFuncIds.length;i++){
				$("#fun"+menuFuncIds[i]).prop("checked", false);
			}
		});
	}
}

function parentMenuClick(parentId){
	if($("#parent"+parentId).is(":checked")==true){//选中
		parentCheckboxClick(parentId,true);
	}else {
		parentCheckboxClick(parentId,false);
	}
}

function menuClick(thisObj,parentId){
	if($(thisObj).is(":checked")==true){//选中
		menuCheckboxClick(thisObj,true,parentId);
	}else {
		menuCheckboxClick(thisObj,false,parentId);
	}
}

function menuCheckboxClick(thisObj,status,parentId){
	if(status){
		
		var getMenuFuncIds=$(thisObj).val();
		var menuFuncIds=getMenuFuncIds.split(",");
		for(var i=0;i<getMenuFuncIds.length;i++){
				$("#fun"+menuFuncIds[i]).prop("checked",true);
		}
		
		var parentObj=$("input[id=menuParent"+parentId+"]");
		var parentTag=false;
		$(parentObj).each(function(){
			if($(this).is(":checked")==false){
				parentTag=true;
				return false;
			}else {
				return true;
			}
		});
		
		if(parentTag){
			$("#parent"+parentId).prop("checked",false);
		}else {
			$("#parent"+parentId).prop("checked",true);
		}
		
	}else {
		var getMenuFuncIds=$(thisObj).val();
		var menuFuncIds=getMenuFuncIds.split(",");
		for(var i=0;i<getMenuFuncIds.length;i++){
				$("#fun"+menuFuncIds[i]).prop("checked", false);
		}
		
		var parentObj=$("input[id=menuParent"+parentId+"]");
		var parentTag=false;
		$(parentObj).each(function(){
			if($(this).is(":checked")==false){
				parentTag=true;
				return false;
			}else {
				return true;
			}
		});
		
		if(parentTag){
			$("#parent"+parentId).prop("checked",false);
		}else {
			$("#parent"+parentId).prop("checked",true);
		}
		
	}
}

/*
function funcClick(thisObj,parentId,menuId){
	if($(thisObj).is(":checked")==true){//选中
		var funcObj=$("input[value="+parentId+"_"+menuId+"]");
		var funcTag=false;
		$(funcObj).each(function(){
			if($(this).is(":checked")==true){
				funcTag=true;
				return false;
			}else {
				return true;
			}
		});
		
		if(funcTag){
			$("#parent"+parentId).prop("checked",true);
		}else {
			$("#parent"+parentId).prop("checked",false);
		}
		
	}else {
		
	}
}
*/


function processHTMLByReturnData(returnData){
		var functionsList = returnData.data;
		var appendHtml ="";
		var parentIdUp;
		var menuFuncIds;
		$(functionsList).each(function(j){
			var mainMenu = functionsList[j];
			var mainParentMenuDesc = mainMenu.descrp;
			var funlist = mainMenu.funcDetails;
			var menuId = mainMenu.menuId;
			menuFuncIds = mainMenu.sn;
			
			if(funlist!=null && ''!=funlist){
				
		
				if(mainMenu.parentId!=parentIdUp){
					appendHtml += "<h1><input id='parent"+mainMenu.parentId+"' class='funclass' type='checkbox' value='' name='parentName' onclick='parentMenuClick("+mainMenu.parentId+")'>"+mainParentMenuDesc+"</h1>";
				}
				
				parentIdUp=mainMenu.parentId;
				
				appendHtml += "<h>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id='menuParent"+mainMenu.parentId+"' class='funclass' type='checkbox' value='"+menuFuncIds+"' name='menusName' onclick='menuClick(this,"+mainMenu.parentId+")'>"+mainMenu.displayName+"</h><table>";
				
				$(funlist).each(function(i){
						var func =  funlist[i];
						
						appendHtml+="<tr><td>&nbsp;&nbsp;<td>";
					
					    appendHtml += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' class='funclass' id='fun"
								       +func.functionId+"' name='funcName' value='"
								       +mainMenu.parentId+"_"+func.menuId+"'/>&nbsp;" +func.functionDesc+"</td>";
					
						appendHtml+="</tr>";
						
				});
				
				appendHtml+="</table>";
				
			}
			
		});
		if(appendHtml.length<2) {
			appendHtml+="<tr><td>后台暂无权限数据</td></tr></table>";
		}
		
		$("#limitsId").html(appendHtml);
}

function roleInfoSave(){
	
	var rolerName=$("#rolerNameP").val();
	var rolerDesc=$("#rolerDescP").val();
	if(!rolerName){
		alert('请填写角色名称');
		$("#rolerNameP").focus();
		return;
	}
	
	if(rolerDesc=='' || rolerDesc==null){
		rolerDesc="";
	}
	

	var reqAjaxPrams="rolerId="+$("#rolerId_hidden").val()+"&rolerName="+rolerName+"&rolerDesc="+rolerDesc+"&id=0";
	processSaveAjax(reqAjaxPrams,0);
	
}

function menuFuncSave(){
	
	var parent_menu_func_id='';
	$("input[name='funcName']:checkbox:checked").each(function(i){
		var funcId=$(this).attr("id");
		funcId=funcId.replace("fun","");
		var thisValue=$(this).val();
		var parentId=thisValue.substring(0,thisValue.indexOf("_"));
		var menuId=thisValue.substring(thisValue.indexOf("_")+1);
		if(i==0){
			parent_menu_func_id=parentId+"_"+menuId+"_"+funcId;
		}else {
			parent_menu_func_id=parent_menu_func_id+","+parentId+"_"+menuId+"_"+funcId;
		}
	});
	
	if(''==parent_menu_func_id){
		$.messager.alert("操作提示","请选择权限","info");
	}else {
		var reqAjaxPrams="rolerId="+$("#rolerId_hidden_p").val()+"&rolerName="+$("#rolerNameShowId").text()+"&rolerDesc="+$("#rolerDescShowId").text()+"&id="+parent_menu_func_id;
		processSaveAjax(reqAjaxPrams,1);
	}
}

function processSaveAjax(reqAjaxPrams,type){
	var url=ctx+"/rolePrivilege/roleMenusSave";
	asyncCallService(url, 'post', false,'json', reqAjaxPrams, function(returnData){
		
		processSSOOrPrivilege(returnData);

		if(returnData.code=='0'){
		    $.messager.alert("操作提示",returnData.msg,"info",function(){
		    	if(type==0){
		    		windowCloseRole();
		    	}else {
		    		windowCloseMenu();
		    	}
			 	queryListPage(1);//重新加载RoleList
		   });
		}else {
			 $.messager.alert("操作提示",returnData.msg,"info");
		}
		
	}, function(){
		 $.messager.alert("操作提示","网络错误","info");
	});
}

function endEdit(){   
    var rows =  $('#DataGrid1').datagrid('getRows');  
    for ( var i = 0; i < rows.length; i++) {  
    	 $('#DataGrid1').datagrid('endEdit', i);  
    }  
} 


function windowCloseRole()
{
	$('#w').window('close');
}

function windowOpenRole()
{
	$('#w').window('open');
}

function windowCloseMenu()
{
	$('#privilegeId').window('close');
}

function windowOpenMenu()
{
	$('#privilegeId').window('open');
}

