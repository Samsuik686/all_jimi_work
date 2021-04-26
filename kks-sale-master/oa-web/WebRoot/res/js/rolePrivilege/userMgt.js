var getCurrentUId="";
var getCurrentOId="";
var getCurrentUName="";
var roleManageShowOrganizationId="roleManageShowOrganizationId";
$(function(){    
   userOrganizationInit();    
   
   datagridInit(); 
   
   treeSelectListener();
   keySearch(queryUserRoleListPage);
}); 

function userOrganizationInit(){
	getAllOrganizationList(roleManageShowOrganizationId);
}

function getUserInfoByUserInfo(){
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row){
		var getUserId=row.uId;
		var getUserName=row.uName;
		
		userRoleWindowOpen();
		
		var url=ctx+"/rolePrivilege/allUserRoleList";
		 $("#DataGrid1").datagrid('loading');
		asyncCallService(url, 'post', false,'json', '', function(returnData){
			
			processSSOOrPrivilege(returnData);
			 
			if(returnData.code==undefined){
				$("#userId").html(getUserId);
				$("#userNameId").html(getUserName);
				var getRoleListData=returnData.data;
				var userRoleHtml="";
				$(getRoleListData).each(function(i){
					var getRoleList=getRoleListData[i];
					userRoleHtml +="<tr>"
										+"<td align='left'><input id='"+getRoleList.rolerId+"' type='checkbox' name='rolerName' value='"+getRoleList.rolerId+"'/></td>"
									    +"<td align='left'>"+getRoleList.rolerName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(</td>"     
							            +"<td align='left'>"+getRoleList.rolerDesc+")</td>" 
									+"</tr><br>";
				});
				
				var titleHtml="<tr>"
									+"<td align='left'></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
									+"<td align='left'>角色名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>"
									+"<td align='left'>角色描述</td>"
							  +"</tr><br>";
				//$("#userRoleListId").html(titleHtml+userRoleHtml);
				document.getElementById("userRoleListId").innerHTML=titleHtml+userRoleHtml;				
				
				//加载用户对应的角色
				loadUserRoleIdList(getUserId);
				
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
			 $("#DataGrid1").datagrid('loaded');	
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	}else
	{
		$.messager.alert("提示信息","请先选择所要分配角色的用户！","info");
	}
	
}

function loadUserRoleIdList(userId){
	var url=ctx+"/rolePrivilege/getUserRoleIdListByUId";
	var param="userId="+userId;
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		
		processSSOOrPrivilege(returnData);
		 
		if(returnData.code==undefined){
			var getRoleIdListData=returnData.data;
			$(getRoleIdListData).each(function(i){
				var getRoleIdData=getRoleIdListData[i];
				$("#"+getRoleIdData).prop("checked",true);
			});
			
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

function setUserRole(){
	
	var getUId=getCurrentUId;
	if(getUId=='' || getUId==null){
		$.messager.alert("提示信息","请先选择用户！","info");
	}else {
		
		$("#userId").html(getCurrentUId);
		$("#userNameId").html(getCurrentUName);
		
		 userRoleWindowOpen();
		 //加载所有的角色
		 loadAllRoleInfo();
	}

		 
		 
}

function loadAllRoleInfo(){
	var url=ctx+"/rolePrivilege/allUserRoleList";
	asyncCallService(url, 'post', false,'json', '', function(returnData){
		
		processSSOOrPrivilege(returnData);
		 
		if(returnData.code==undefined){
			var getRoleListData=returnData.data;
			var optionHtml="";
			$(getRoleListData).each(function(i){
				var getRoleList=getRoleListData[i];
				optionHtml +="<option value='"+getRoleList.rolerId+"'>"+getRoleList.rolerName+"</option>";
			});
			$("#roleListId").html(optionHtml);
			
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

function userRoleSave(){
	var getUserId=$("#userId").text();
	var getRolerId="";
	
	$("input[name='rolerName']:checkbox:checked").each(function(i){
		
		var thisValue=$(this).val();
		
		if(i==0){
			getRolerId=thisValue;
		}else {
			getRolerId=getRolerId+","+thisValue;
		}
	});
	
	if(getRolerId=='' || getRolerId==null){
		$.messager.alert("操作提示","请选择角色","info");
	}else {
		var url=ctx+"/rolePrivilege/userRoleAdd";
		var param="userId="+getUserId+"&rolerId="+getRolerId;
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			
			processSSOOrPrivilege(returnData);
			 
			//添加成功后重新加载当前用户的角色列表
			if(returnData.code=='0'){
				userRoleWindowClose();
//					queryListPage(1);
				//getAllUserInfoListByOganizationID(getCurrentOId)
			}
			$.messager.alert("操作提示",returnData.msg,"info");
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	}
	
}
function userRoleWindowClose()
{
	$('#userRoleId').window('close');
}

function userRoleWindowOpen()
{
	$('#userRoleId').window('open');
}

function datagridInit()  
{/*
	var lastIndex;
 
	$("#DataGrid1").datagrid({
     singleSelect:true,//是否单选  
 
		toolbar:[
		{
			text:'分配角色', 
			iconCls:'icon-fpjsicon',
			handler:function(){
				
				  //改变弹出框位置add by wg.he 2013/12/10
				    chageWinSize('userRoleId');
				 
					getUserInfoByUserInfo();
			} 
		}],
			onClickRow:function(rowIndex){
				if(lastIndex!=rowIndex)
					{
					   $('#DataGrid1').datagrid('endEdit',lastIndex);
					   $('#DataGrid1').datagrid('beginEdit',rowIndex);
					}
				lastIndex=rowIndex; 
			},
			onDblClickRow:function(rowIndex){
				getUserInfoByUserInfo();
			}
	}); 
	*/
	//queryListPage(1);//初始化查询页面数据，必须放在datagrid()初始化调用之后
}

function queryListPage(pageNumber)  {//初始化数据及查询函数
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/rolePrivilege/userRoleList";
    var param="uId="+getCurrentUId+"&rolerName="+$.trim($("#rolerName").val())+"&rolerDesc="+$.trim($("#rolerDesc").val())+"&currentpage="+currentPageNum; 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		
		processSSOOrPrivilege(returnData);
		 
		if(returnData.code==undefined){
			$("#DataGrid1").datagrid('loadData',returnData.data);  
        	getPageSize();
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
 } 

function treeSelectListener(){
	$('#'+roleManageShowOrganizationId).tree({
		onClick:function(node){
//			alert(node.text+","+node.id);
			getCurrentOId=node.id;
			getCurrentUName=node.text;
			
			$("#uName").val("");    
			
			//queryListPage(1);
			
			getAllUserInfoListByOganizationID(getCurrentOId);
		},
		onDblClick:function(node){
			treeSelectListenerDBClick(this,node);
		}
			
	});
}

/**
 * 根据用户名查询用户信息
 * @param uName
 */
function queryUserRoleListPage(){
	var url=ctx+"/user/SelectUidByUname";
	var param=null;
	if($("#uId").val()==""){
		param="oId="+getCurrentOId;
	}else{
		param="oId="+getCurrentOId+"&uName="+$.trim($("#uId").val());
	}
	asyncCallService(url, 'post', false,'json', param, function(ssoReturnData){
		
		processSSOOrPrivilege(ssoReturnData);
		
		if(ssoReturnData.code==undefined){
			parseSSOUserInfo(ssoReturnData,1);
		}else {
			$.messager.alert("操作提示",ssoReturnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}


/**
 * 根据组织架构oId获取下属员工列表
 * @author Li.Shangzhi
 * @Date 2016-09-22 14:01:55
 */
function getAllUserInfoListByOganizationID(oId){
	
	var url=ctx+"/user/SelectUidByUname";
	var param="oId="+oId;
	asyncCallService(url, 'post', false,'json', param, function(ssoReturnData){
		
		processSSOOrPrivilege(ssoReturnData);
		
		if(ssoReturnData.code==undefined){
			parseSSOUserInfo(ssoReturnData,1);
		}else {
			$.messager.alert("操作提示",ssoReturnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
}

function parseSSOUserInfo(ssoReturnData,pageNumber){
	currentPageNum=pageNumber;
	var getSSORetunUserInfoData=ssoReturnData;
	if(getSSORetunUserInfoData.code==undefined){
			$("#DataGrid1").datagrid("clearSelections");
			$("#DataGrid1").datagrid('loadData',getSSORetunUserInfoData.data);  
        	getPageSize();
	}else {
			$.messager.alert("操作提示",returnData.msg,"info");
	}
}

