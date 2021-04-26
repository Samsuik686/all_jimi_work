var getCurrentUId="";
var getCurrentOId="";
var getCurrentUName="";
var roleManageShowOrganizationId="roleManageShowOrganizationId";

$(function(){    
   userOrganizationInit();    
   
   datagridInit(); 
   
   treeSelectListener();
}); 

function userOrganizationInit(){
	getAllOrganizationList(roleManageShowOrganizationId);
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
					$.messager.alert("操作提示",returnData.msg,"info",function(){
						userRoleWindowClose();
//						queryListPage(1);
						//getAllUserInfoListByOganizationID(getCurrentOId)
				   });
				}else {
					 $.messager.alert("操作提示",returnData.msg,"info");
				}
				
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
{
	$("#Table_User").datagrid({
	     singleSelect:false,
	     onDblClickRow: function(index,value){
	 	 }
	}); 
}

function queryListPage(pageNumber)  {//初始化数据及查询函数
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var url=ctx+"/rolePrivilege/userRoleList";
    var param="uId="+getCurrentUId+"&rolerName="+$("#rolerName").val()+"&rolerDesc="+$("#rolerDesc").val()+"&currentpage="+currentPageNum; 
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		
		processSSOOrPrivilege(returnData);
		 
		if(returnData.code==undefined){
			$("#Table_User").datagrid('loadData',returnData.data);  
        	getPageSize();
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
	
 } 

/**
 * 添加树节点监听事件
 */
function treeSelectListener(){
	$('#'+roleManageShowOrganizationId).tree({
		onClick:function(node){
			getCurrentOId=node.id;
			getCurrentUName=node.text;
			$("#uName").val("");
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
	var param="uName="+$("#uId").val();
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
 */
function getAllUserInfoListByOganizationID(oId){
	
	var url=ctx+"/user/UserListByOrgId";
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
			$("#Table_User").datagrid('loadData',getSSORetunUserInfoData.data);  
        	getPageSize();
	}else {
			$.messager.alert("操作提示",returnData.msg,"info");
	}
}

