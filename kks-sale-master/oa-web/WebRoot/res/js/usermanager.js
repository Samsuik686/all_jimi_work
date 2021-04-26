$(function(){
	
	$("#SelectStatus option[value='0']").attr("selected", true);     
	
	var status=$("#uStatus").val();
	if(statusInsert==false&&status==1){                       
		document.getElementById("s1").style.display="block";      
		document.getElementById("s2").style.display="block";   
		  
	}else{  
		document.getElementById("s1").style.display="none";        
		document.getElementById("s2").style.display="none";      
		                                                                   
		}  

	treeInit("#tt2","#oName","#omanagerName");
	$("#oName").combotree( {
		onChange:function(newValue, oldValue)
		{
				$.ajax({
				     type:"POST",
				     async:false,
				     cache:false,
				     data:"oId="+newValue,
				     url:ctx+'/user/SelectOrgByOId',
				     dataType:"json",
				     success:function(returnData){
				    	$("#uManagerId").val(returnData.data.oManagerId);
				    	$("#uManagerName").val(returnData.data.oManagerName);
				     }
				});
		}
    });
	
	datagridInit();
	treeSelect();
	
	//selectInit(); 
	//$("#updatebutton").attr("disabled",true);
}); 

function isChar(str)  
{ 
	        isCharacter = /^[A-Za-z]+$/;
	       //验证并返回结果
	       return (isCharacter.test(str));
}
function searchinfo()
{ 
	
	var oId= document.getElementById("oidSelect").value;
	var uName = $("#searchUname").val();
	var uStatus = $("#SelectStatus").val();
	var cId = $("#cIdSelect").val();
	
	var param = "oId="+oId+"&uName="+uName+"&uStatus="+uStatus+"&cId="+cId;
	$.ajax({
	     type:"POST",
	     async:false,
	     cache:false,
	     data:param,
	     url:ctx+'/user/SelectUserByUserInfo',
	     dataType:"json",
	     success:function(returnData){

	        	processSSOOrPrivilege(returnData);
	    	 if(returnData.data=="false")
   		 {
   		 $.messager.alert("操作提示","查询失败，请检查参数。","info");
   		 }
   else{
   		clearTable();
   		var usersInfo=returnData.data;
   		for(var i=0;i<usersInfo.length;i++){
   			btnAddRow(usersInfo[i],i);
   		}
    }
	     }
	});
	
	/*if($("#searchUname").val()!="")
   {  
	   if(treeOrgId!=0)
		par="uName="+$("#searchUname").val()+"&uStatus="+$("#SelectStatus").val()+"&oId="+treeOrgId;
	   else
			par="uName="+$("#searchUname").val()+"&uStatus="+$("#SelectStatus").val();
	   
		$.ajax({
		     type:"POST",
		     async:false,
		     cache:false,
		     data:par,
		     url:ctx+'/user/SelectUserByUserInfo',
		     dataType:"json",
		     success:function(returnData){

		        	processSSOOrPrivilege(returnData);
		    	 if(returnData.data=="false")
	    		 {
	    		 $.messager.alert("操作提示","查询失败，请检查参数。","info");
	    		 }
	    else{
	    		clearTable();
	    		var usersInfo=returnData.data;
        		for(var i=0;i<usersInfo.length;i++){
        			btnAddRow(usersInfo[i],i);
        		}
	     }
		     }
		});
	   //}
   }
	else
		{
		
		   if(treeOrgId!=0)
				par="uStatus="+$("#SelectStatus").val()+"&oId="+treeOrgId;
			   else
					par="uStatus="+$("#SelectStatus").val();
		
		$.ajax({
		     type:"POST",
		     async:false,
		     cache:false,
		     data:par, 
		     url:ctx+'/user/SelectUserByUserInfo',
		     dataType:"json",
		     success:function(returnData){

		        	processSSOOrPrivilege(returnData);
		    	 if(returnData.data=="false")
	    		 {
	    		 $.messager.alert("操作提示","查询失败，请检查参数。","info");
	    		 }
	    	 else{
	    	 //$("#DataGrid1").datagrid('loadData',returnData.data);
	    		clearTable();
		    		var usersInfo=returnData.data;
	        		for(var i=0;i<usersInfo.length;i++){
	        			btnAddRow(usersInfo[i],i);
	        	}
	     }
		     }
		});
		}*/
}

function showJumpDate(){                    
	
	var status=$("#uStatus").val();
	//alert("status:"+status);
	if(statusInsert==false&&status==1){            
		//$('#uJumpDate').datebox('setValue',"");      
		document.getElementById("s1").style.display="block";      
		document.getElementById("s2").style.display="block";  
		
	}else{  
		document.getElementById("s1").style.display="none";        
		document.getElementById("s2").style.display="none";      
		                                                                   
		}
 
}
function isalphanumber(str)
{
	       var result = str.match(/^[a-zA-Z0-9_]{1,}$/);
	       if (result == null)
	            return false;
	        return true;
}

var nodestatus=false;
$.messager.defaults= { ok: "确定",cancel: "取消" };
function treeInit(treeid,combotree1,combotree2)
{
	 
       $.ajax({
        type:"POST",
        async:false,
        cache:false,
        url:ctx+'/user/OrganSelectList',
        dataType:"JSON",
        success:function(returnData){
        	var data= $.parseJSON(returnData);
        	
        	id=0;
        	$("#oidSelect").append("<option value='"+id+"'>"+"所有部门"+"</option>"); 
        	
        	selectInitlist(data.data);
        	treeLoadAgin(data,treeid,combotree1,combotree2);
        },
       error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
       });
   
}

function selectInitlist(returnData)
{

	$.each(returnData,function(i,j){
	 
	       if(j.list!=""){
 
	    	    $("#oidSelect").append("<option value='"+j.oId+"'>"+j.oName+"</option>"); 
	    
	    	   selectInitlist(j.list);      
	       }  
	       else{      
 
 
	    	    $("#oidSelect").append("<option value='"+j.oId+"'>"+j.oName+"</option>");
	       }
	     
	    });
}
var diclist="";
function treeLoadAgin(returnData,treeid,combotree1,combotree2)
{
	$(treeid).empty();
	diclist='[{"id":"0","text":"所有部门","children":[';
	 
	eachlist(returnData.data);
    var reg=/,$/gi;
    diclist=diclist.replace(reg,"");
    diclist+=']}]';
    $(treeid).tree({
       data : eval(diclist)  // 正确效果
    });
   
    $(combotree1).combotree({   
    	data : eval(diclist)
     });  
    $(combotree2).combotree({   
    	data : eval(diclist)
     });  

}

function eachlist(returnData)
{
	$.each(returnData,function(i,j){
	       if(j.list!=""){
	    	   diclist+='{"id":"'+j.oId+'","text":"'+j.oName+'","state":"closed","children":[';
		       eachlist(j.list);
		       var reg=/,$/gi;
		       diclist=diclist.replace(reg,"");
	       }
	       else{
	    	   diclist+='{"id":"'+j.oId+'","text":"'+j.oName+'","children":[';
	       }
	      diclist+=']},';
	    });
}


function treeSelect()
{
	$('#tt2').tree({
		onClick:function(node){
			var xnode=node.id;
			 $("#oidSelect").val(xnode);       
		    document.getElementById("treeOrgId").value=node.id;
		    
			   if(xnode!=0)
				   param="uName="+$("#searchUname").val()+"&uStatus="+$("#SelectStatus").val()+"&oId="+xnode;
				   else
					   param="uName="+$("#searchUname").val()+"&uStatus="+$("#SelectStatus").val(); 
			    
			if(node.id==0) 
			{
				$.ajax({
					  type:"POST",
			 			async:false,
			 			cache:false,
			 			data:param,
					     url:ctx+'/user/SelectUserByUserInfo',    
				        dataType:"json",
				        success:function(returnData){

				        	processSSOOrPrivilege(returnData);
				        	if(returnData.data=="false")
				        	{
				        		$.messager.alert("操作提示","员工信息查询，参数错误","info");
				        	}
				        	else
				        	{
				        		//$("#DataGrid1").datagrid('loadData',returnData.data);
				        		clearTable();
					    		var usersInfo=returnData.data;
				        		for(var i=0;i<usersInfo.length;i++){
				        			btnAddRow(usersInfo[i],i);
				        		}
				        	}
				        },
				       error:function(){
						   $.messager.alert("操作提示","员工信息查询，网络错误","info");
					   }
				       });
			}
			else
			{
			 $.ajax({ 
				  type:"POST",
		 			async:false,
		 			cache:false,
		 			data:param,
				     url:ctx+'/user/SelectUserByUserInfo',   
			        dataType:"json",
			        success:function(returnData){

			        	processSSOOrPrivilege(returnData);
			        	if(returnData.data=="false")
			        	{
			        		$.messager.alert("操作提示","员工信息查询，参数错误","info");
			        	}
			        	else
			        	{
			        		clearTable();
				    		var usersInfo=returnData.data;
			        		for(var i=0;i<usersInfo.length;i++){
			        			btnAddRow(usersInfo[i],i);
			        		}
			        	}
			        },
			       error:function(){
					   $.messager.alert("操作提示","员工信息查询，网络错误","info");
				   }
			       });
				}
		},
		onDblClick:function(node){
			treeSelectListenerDBClick(this,node);
		}
			
	});
	
}
var time="";
function GetTime()
{
	var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    time = year + "-" + month + "-" + date +" " + hour + ":" + minu + ":" + sec;
    
}
function UserInsert()
{
	 if (!$("#uId").text().replace(/(^\s*)|(\s*$)/g, "")){
		 $.messager.alert("操作提示","账号不能为空！","error");
 		return;
	 }
	 if (!$("#cId").val().replace(/(^\s*)|(\s*$)/g, "")) {
 		$.messager.alert("操作提示","工号不能为空！","error");
 		return;
	 }
	$.ajax({
		  type:"POST",
			async:false,
			cache:false,
			data:"uId="+$("#uId").text().replace(/(^\s*)|(\s*$)/g, ""),
	        url:ctx+'/user/SelectUidByUid',
	        dataType:"json",
	        success:function(returnData){
	        	
	        	if(returnData.data!="")
	        	{
	        		$.messager.alert("操作提示","员工账号已存在，请修改员工账号.","error");
	        	}
	        	else if(returnData.data=="")
	        	{
	        		
	        		if(isalphanumber($("#uId").text())&& $('#oName').combotree('getValues')!="")
		        	{
	        			var param = "cId="+$("#cId").val();
	        			$.ajax({
	        				type:"POST",
	        				async:false,
	        				cache:false,
	        				data:param,
	        		        url:ctx+'/user/SelectUserByCid',
	        		        dataType:"json",
	        		        success:function(returnData){
	        		        	if(returnData.data==false){
	        		        		$.messager.alert("操作提示","工号已存在，请修改员工工号.","error");
	        		        		$("#cId").focus();
	        		        	}else {
	        		        		var param="cId="+$("#cId").val()	
	        		        		+"&uId="+$("#uId").text()
	        		        		+"&uName="+$("#uName").val()
	        		        		+"&omainId="+$('#oName').combotree('getValues')
	        		        		+"&osecondId="+$('#omanagerName').combotree('getValues')/////////////////////////////////////////////////////////
	        		        		+"&uSex="+$("#uSex").val()
	        		        		+"&uMail="+$("#uMail").val()
	        		        		+"&uMailPwd="+$("#uMailPwd").val()
	        		        		+"&uTelphone="+$("#uTelphone").val()
	        		        		+"&uPhone="+$("#uPhone").val()
	        		        		+"&uStatus="+$("#uStatus").val()
	        		        		//+"&uJumpDate="+$('#uJumpDate').datebox('getValue')  
	        		        		  
	        		        		+"&uPosition="+$("#uPosition").val()
	        		        		+"&uManagerId="+$("#uManagerId").val()
	        		        		+"&uManagerName="+$("#uManagerName").val()
	        		        		+"&uOfficeArea="+$("#uOfficeArea").val() 
	        		        		+"&JoinDateTime="+$('#uJoinDateTime').datebox('getValue')
	        		        		+"&uInDateTime="+$("#uInDateTime").val()
	        		        		+"&PositiveDateTime="+$('#uPositiveDateTime').datebox('getValue')
	        		        		+"&uEthnic="+$("#uEthnic").val()
	        		        		+"&Birthday="+$('#uBirthday').datebox('getValue')
	        		        		+"&uAge="+$("#uAge").val()
	        		        		+"&uEducationalBackground="+$("#uEducationalBackground").val()
	        		        		+"&uIdCard="+$("#uIdCard").val()
	        		        		+"&ContractStart="+$('#uContractStart').datebox('getValue')
	        		        		+"&ContractEnd="+$('#uContractEnd').datebox('getValue')
	        		        		+"&uMaritalStatus="+$("#uMaritalStatus").val()
	        		        		+"&uHometown="+$("#uHometown").val()
	        		        		+"&uHomeAddress="+$("#uHomeAddress").val()
	        		        		+"&uEmergencyTelephone="+$("#uEmergencyTelephone").val()
	        		        		+"&uEmergencyRelations="+$("#uEmergencyRelations").val()
	        		        		+"&uGradeName="+$("#uGradeName").val()
	        		        		+"&uGraduated="+$("#uGraduated").val()
	        		        		+"&uProfessionalName="+$("#uProfessionalName").val()
	        		        		+"&uSocialSecurityNumber="+$("#uSocialSecurityNumber").val()
	        		        		+"&uBankAccount="+$("#uBankAccount").val()
	        		        		+"&uProvidentFundAccount="+$("#uProvidentFundAccount").val()
	        		        		+"&workTimeId="+$("#workTimeId").val()
	        		        		+"&uEnterpriseQQ="+$("#uEnterpriseQQ").val()
	        		        		+"&uQQ="+$("#uQQ").val()
	        		        		+"&uUserDesc="+$("#uUserDesc").val()
	        		        		+"&uPwd=1111";
	        		        		 $.ajax({
	        		        			  type:"POST",
	        		        				async:false,
	        		        				cache:false,
	        		        				data:param,
	        		        		        url:ctx+'/usermanager/insert',
	        		        		        dataType:"json",
	        		        		        success:function(returnData){
	        		        		        	if(returnData.data=="false")
	        		        		        	{
	        		        		        		$.messager.alert("操作提示","员工信息增加，参数错误","info");
	        		        		        	}
	        		        		        	else
	        		        		        	{
	        		        		        		$.messager.alert("操作提示","员工信息增加成功！","info");
	        		        		        		datagridReload($('#oName').combotree('getValues'));
	        		        		        		windowClose();
	        		        		        	}
	        		        		        },
	        		        		       error:function(){
	        		        				   $.messager.alert("操作提示","员工信息增加，网络参数错误","info");
	        		        			   }
	        		        		      });
	        		        	}
	        		        }
	        			});
		        }
	        	else{  
	        		var msg="";
	        		if(!isalphanumber($("#uId").val()))
		        	{
	        			msg+="员工账号只能由数字,字母和下划线组成。<br/>";
	        		
		        	}
	        		if($('#oName').combotree('getValues')=="")
	        		{
	        			msg+="主部门不能为空。";
	        		}
	        		$.messager.alert("操作提示",msg,"error");
	        	}	
	         }
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工信息增加，网络参数错误","info");
		   }
	 });

	
}

function queryCid(cId){
	$.ajax({
		type:"POST",
		async:false,
		cache:false,
		data:"cId="+$("#cId").val(),
        url:ctx+'/user/SelectUserByUserInfo',
        dataType:"json",
        success:function(returnData){
        	if(returnData.data!=null || returnData.data!=""){
        		return false;
        	}
        }
	});
}


function UserUpdate()
{
	var param="cId="+$("#cId").val()
		+"&uId="+$("#uId").text()
		+"&uName="+$("#uName").val()
		+"&omainId="+$('#oName').combotree('getValues')
		+"&osecondId="+$('#omanagerName').combotree('getValues')/////////////////////////////////////////////////////////
		+"&uSex="+$("#uSex").val()
		+"&uMail="+$("#uMail").val()
		+"&uMailPwd="+$("#uMailPwd").val()
		+"&uTelphone="+$("#uTelphone").val()
		+"&uPhone="+$("#uPhone").val()
		+"&uStatus="+$("#uStatus").val()
 
		+"&JumpDate="+$('#uJumpDate').datebox('getValue')   
		  
		+"&uPosition="+$("#uPosition").val()
		+"&uManagerId="+$("#uManagerId").val()
		+"&uManagerName="+$("#uManagerName").val()
		+"&uOfficeArea="+$("#uOfficeArea").val()
		+"&JoinDateTime="+$('#uJoinDateTime').datebox('getValue')
		+"&uInDateTime="+$("#uInDateTime").val()
		+"&PositiveDateTime="+$('#uPositiveDateTime').datebox('getValue')
		+"&uEthnic="+$("#uEthnic").val()
		+"&Birthday="+$('#uBirthday').datebox('getValue')
		+"&uAge="+$("#uAge").val()
		+"&uEducationalBackground="+$("#uEducationalBackground").val()
		+"&uIdCard="+$("#uIdCard").val()
		+"&ContractStart="+$('#uContractStart').datebox('getValue')
		+"&ContractEnd="+$('#uContractEnd').datebox('getValue')
		+"&uMaritalStatus="+$("#uMaritalStatus").val()
		+"&uHometown="+$("#uHometown").val()
		+"&uHomeAddress="+$("#uHomeAddress").val()
		+"&uEmergencyTelephone="+$("#uEmergencyTelephone").val()
		+"&uEmergencyRelations="+$("#uEmergencyRelations").val()
		+"&uGradeName="+$("#uGradeName").val()
		+"&uGraduated="+$("#uGraduated").val()
		+"&uProfessionalName="+$("#uProfessionalName").val()
		+"&uSocialSecurityNumber="+$("#uSocialSecurityNumber").val()
		+"&uBankAccount="+$("#uBankAccount").val()
		+"&uProvidentFundAccount="+$("#uProvidentFundAccount").val()
		+"&workTimeId="+$("#workTimeId").val()
		+"&uEnterpriseQQ="+$("#uEnterpriseQQ").val()
		+"&uQQ="+$("#uQQ").val()
		+"&uUserDesc="+$("#uUserDesc").val();
		if($('#oName').combotree('getValues')!="")
		{
				 if($("#uStatus").val()==1){
		         		var uJumpDate = $('#uJumpDate').datebox('getValue');	// get datebox value
		         			if(uJumpDate==null|| uJumpDate==''){
		         				$.messager.alert("系统错误","请选择离职时间","info");
		         				return false;
		         			}
		         	}
				if($("#cId").val()==null||$("#cId").val()==""){
					$.messager.alert("操作提示","工号不能为空！","info");
     				return false;
				}
				if($("#cId").val()!=$("#oldCId").val()) {
					 $.ajax({
		     				type:"POST",
		     				async:false,
		     				cache:false,
		     				data:"cId="+$("#cId").val(),
		     		        url:ctx+'/user/SelectUserByCid',
		     		        dataType:"json",
		     		        success:function(returnData){
		     		        	if(returnData.data==false){
		     		        		$.messager.alert("操作提示","工号已存在，请修改员工工号.","error");
		     		        	}else {
		     		        		
		     		        		 $.ajax({
		     		        			  type:"POST",
		     		        				async:false,
		     		        				cache:false,
		     		        				data:param,
		     		        		        url:ctx+'/usermanager/update',
		     		        		        dataType:"json",
		     		        		        success:function(returnData){
		     		        		        	if(returnData.data=="false")
		     		        		        	{
		     		        		        		$.messager.alert("操作提示","员工信息修改，参数错误","info");
		     		        		        	}
		     		        		        	else
		     		        		        	{
		     		        		        		$.messager.alert("操作提示","员工信息修改成功！","info");
		     		        		        		//datagridReload($('#oName').combotree('getValues'));
		     		        		        		//alert("orgid:="+document.getElementById("treeOrgId").value); 
		     		        		        	  
		     		        		        		datagridReload(document.getElementById("treeOrgId").value);
		     		        		        		windowClose();
		     		        		        		clearTable();
		     		        		        		searchinfo();
		     		        		        	}
		     		        		        },error:function(){   
		     		        		    	      
		     		        				   $.messager.alert("操作提示","员工信息修改，网络参数错误","info");
		     		        			   }    
		     		        		      });
		     		        		}
		     		        	}
		     		        });
				} else {
					 $.ajax({
	        			  type:"POST",
	        				async:false,
	        				cache:false,
	        				data:param,
	        		        url:ctx+'/usermanager/update',
	        		        dataType:"json",
	        		        success:function(returnData){
	        		        	if(returnData.data=="false")
	        		        	{
	        		        		$.messager.alert("操作提示","员工信息修改，参数错误","info");
	        		        	}
	        		        	else
	        		        	{
	        		        		$.messager.alert("操作提示","员工信息修改成功！","info");
	        		        		//datagridReload($('#oName').combotree('getValues'));
	        		        		//alert("orgid:="+document.getElementById("treeOrgId").value); 
	        		        	  
	        		        		datagridReload(document.getElementById("treeOrgId").value);
	        		        		windowClose();
	        		        		clearTable();
	        		        		searchinfo();
	        		        	}
	        		        },error:function(){   
	        		    	      
	        				   $.messager.alert("操作提示","员工信息修改，网络错误","info");
	        			   }    
	        		      });
				}
				
	}
	else
		{
		$.messager.alert("操作提示","主部门不能为空。","error");
		}
	    
}
function windowClose()
{
    $('#w').window("close");	
}
function datagridReload(oID)
{

	if(oID!='')
	param="uStatus="+$("#SelectStatus").val()+"&oId="+oID;
	else  
		param="uStatus="+$("#SelectStatus").val(); 
	$.ajax({
		  type:"POST",
 			async:false, 
 			cache:false,  
 			//data:"oId="+oID,     
 			data:param,
	        //url:ctx+'/usermanager/userselect',     
 	        url:ctx+'/user/SelectUserByUserInfo',  
	        dataType:"json",     
	        success:function(returnData){ 
	        	processSSOOrPrivilege(returnData);
	        	if(returnData.data=="false")
	        	{
	        		$.messager.alert("操作提示","员工信息查询，参数错误","info");
	        	}
	        	else
	        	{
	        		//$("#DataGrid1").datagrid('loadData',returnData.data);
	        	}
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工信息查询，网络错误","info");
		   }
	       });
}

function mform(){
	 $("#uId").html('账号由员工姓名自动生成');
	    //$("#uName").attr("onblur","javascript:getPinYin()");
		$("#cId").val("");
		$("#UserInsert").show();
		$("#UserUpdate").hide();
		$('#oName').combotree('setValue',1);
		$('#omanagerName').combotree('setValues',"");
		$("#uId").val("");
		$("#uName").val("");
		$("#uSex").val("");
		$("#uMail").val("");
		$("#uMailPwd").val("");
		$("#uTelphone").val("");
		$("#uPhone").val("");
		$("#uStatus").val("");
		$('#uJumpDate').datebox('setValue',"");
		showJumpDate();
		
		$("#uManagerId").val("");
		$("#uManagerName").val("");
		$("#uPosition").val("");
		$("#uOfficeArea").val("");
		$('#uJoinDateTime').datebox('setValue',"");
		$("#uInDateTime").val("");
		$('#uPositiveDateTime').datebox('setValue',"");
		$("#uEthnic").val("");
		$('#uBirthday').datebox('setValue',"");
		$("#uAge").val("");
		$("#uEducationalBackground").val("");
		$("#uIdCard").val("");
		$('#uContractStart').datebox('setValue',"");
		$('#uContractEnd').datebox('setValue',"");
		$("#uMaritalStatus").val("");
		$("#uHometown").val("");
		$("#uHomeAddress").val("");
		$("#uEmergencyTelephone").val("");
		$("#uEmergencyRelations").val("");
		$("#uGradeName").val("");
		$("#uGraduated").val("");
		$("#uProfessionalName").val("");
		$("#uSocialSecurityNumber").val("");
		$("#uBankAccount").val("");
		$("#uProvidentFundAccount").val("");
		$("#uEnterpriseQQ").val("");
		$("#workTimeId").val("");
		$("#uQQ").val("");
		$("#uUserDesc").val("");
}

var statusInsert=false;  
function datagridInit()
{
	$("#DataGrid1").datagrid({
		toolbar:[
			{text:'导出',
				iconCls:'icon-print',
				handler:function(){
					var treeOrgId= document.getElementById("oidSelect").value;
					 if(treeOrgId!=0){
						par="uName="+$("#searchUname").val()+"&uStatus="+$("#SelectStatus").val()+"&oId="+treeOrgId;
					 }else{
						par="uName="+$("#searchUname").val()+"&uStatus="+$("#SelectStatus").val();
					 }
					parent.location.href=ctx+"/usermanager/UserExport?"+par;
				}
			}]
	   });
	
	   $.ajax({
		  type:"POST",
 			async:false,
 			cache:false,
	        url:ctx+'/usermanager/userselectall',
	        dataType:"json",
	        success:function(returnData){
	        
	            loadDataAfter();//add by ht.xie 2013/12/28 
	        	processSSOOrPrivilege(returnData);
	        	if(returnData.data=="false")
	        	{
	        		$.messager.alert("操作提示","员工信息查询，参数错误","info");
	        	}
	        	else
	        	{
	        		//$("#DataGrid1").datagrid('loadData',returnData.data);
		    		var usersInfo=returnData.data;
	        		for(var i=0;i<usersInfo.length;i++){
	        			btnAddRow(usersInfo[i],i);
	        		}
	        	}
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工信息查询，网络错误","info");
		   }
	       });
}
 

 function formatJoinDate(val,row){    
       return val.substr(0,10);  
}    
  
function formatBirDate(val,row){  
    return val.substr(0,10);  
}   
  
function clearTable(){
	 $("#mytable tr").eq(0).nextAll().remove();
}    

// chkItem事件 
$(function () {

$("[name = checkbox11]:checkbox").bind("click", function () {

	$("[name = checkbox11]:checkbox").prop("checked", false);
	$(this).prop("checked", "checked");     

}); 
});

function tr_click(id){ 
	//清理表单
	//$("#mform").form('clear');
	mform();
	statusInsert=false;
	 var oMainId="";
	 var oSecondId="";
	 $("#uId").attr("disabled",true);
	 $.ajax({
		  type:"POST",
			async:false,
			cache:false,
			data:"uId="+id,
	        url:ctx+'/usermanager/usermainorgquery',
	        dataType:"json",
	        success:function(returnData){
	        	oMainId=returnData.data.oId;
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工主部门查询失败，网络参数错误","info");
		   }
	    });
	 
	 
	$.ajax({
		  type:"POST",
			async:false,
			cache:false,
			data:"uId="+id,
	        url:ctx+'/usermanager/userorgquery',
	        dataType:"json",
	        success:function(returnData){
	        	$.each(returnData.data,function(i,j){
	        		oSecondId+=j.oId+",";
	        		
	        	});
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工部门查询失败，网络参数错误","info");
		   }
	   });
	
	
	
	oSecondId=oSecondId.replace(oMainId,"");
	var osa=oSecondId.split(',');
	 
	var arr=$.grep(osa,function(n,i){
		 if(n!="")
		   return n;
		});
	$('#oName').combotree('setValue', oMainId);
	$('#omanagerName').combotree('setValues', arr);
   
	 //通过uID查询userinfo表
	 $.ajax({  
		  type:"POST",
			async:false,
			cache:false,
			data:"uId="+id,
	        url:ctx+'/usermanager/getUserInfoByuID',
	        dataType:"json",
	        success:function(returnData){
	    		 
	        	
	        	   row=returnData.data;

	        	   var birt=row.uBirthday;
	     	   	   var injob=row.uJoinDateTime;
	     	   	   birt=birt.substring(0,4);
	     	   	   injob=injob.substring(0,4);
	     	   	   var now = new Date();
	     	   	   var year = now.getFullYear(); //getFullYear getYear
	     	   	   var age=year-birt;
	     	   	   var incomp=year-injob;
	     	   	  //$("#uName").removeAttr("onblur");
	        	  $("#uId").html(row.uId);
	        	  $("#uName").unbind('blur');
	              $("#uName").val(row.uName);
	              $("#uSex").val(row.uSex);
	              $("#uMail").val(row.uMail);
	              $("#uMailPwd").val(row.uMailPwd);
	              $("#uTelphone").val(row.uTelphone);
	              $("#uPhone").val(row.uPhone);
	              $("#uStatus").val(row.uStatus);
	      
 
	    	      if(row.uStatus==1){
	    
	    	    	  if(row.uJumpDate!=""){
	    	    		  //alert("sssssssssssss");
	    	    		  //$("#uStatus").val("------------"+row.uJumpDate.substr(0,10));
	    	    		  $('#uJumpDate').datebox('setValue',row.uJumpDate.substr(0,10));
	    	    		  
	    	    	  }
	    	    
	    	      }  
			    	
			      else  $('#uJumpDate').datebox('setValue',"");  
	    	      $("#oldCId").val(row.cId);
	    	      $("#cId").val(row.cId);   
	              $("#uPosition").val(row.uPosition);
	              $("#uManagerId").val(row.uManagerId);
	              $("#uManagerName").val(row.uManagerName);
	              $("#uOfficeArea").val(row.uOfficeArea);
	              if(row.uJoinDateTime!=null&&row.uJoinDateTime!="")
	            	  $('#uJoinDateTime').datebox('setValue',row.uJoinDateTime.substr(0,10));
	              $("#uInDateTime").val(incomp);
	              if(row.uPositiveDateTime!=null&&row.uPositiveDateTime!="")
	            	  $('#uPositiveDateTime').datebox('setValue',row.uPositiveDateTime.substr(0,10));
	              $("#uEthnic").val(row.uEthnic);
	              if(row.uBirthday!=null&&row.uBirthday!="")
	            	  $('#uBirthday').datebox('setValue',row.uBirthday.substr(0,10));
	              $("#uAge").val(age); 
	              $("#uEducationalBackground").val(row.uEducationalBackground);
	              $("#uIdCard").val(row.uIdCard);
	              if(row.uContractStart!=null&&row.uContractStart!="")
	            	  $('#uContractStart').datebox('setValue',row.uContractStart.substr(0,10));
	              if(row.uContractEnd!=null&&row.uContractEnd!="")
	            	  $('#uContractEnd').datebox('setValue',row.uContractEnd.substr(0,10));
	              $("#uMaritalStatus").val(row.uMaritalStatus);
	              $("#uHometown").val(row.uHometown);
	              $("#uHomeAddress").val(row.uHomeAddress);
	              $("#uEmergencyTelephone").val(row.uEmergencyTelephone);
	              $("#uEmergencyRelations").val(row.uEmergencyRelations);
	              $("#uGradeName").val(row.uGradeName);
	              $("#uGraduated").val(row.uGraduated);
	              $("#uProfessionalName").val(row.uProfessionalName);
	              $("#uSocialSecurityNumber").val(row.uSocialSecurityNumber);
	              $("#uBankAccount").val(row.uBankAccount);
	              $("#uProvidentFundAccount").val(row.uProvidentFundAccount);
	              $("#workTimeId").val(row.workTimeId);
	              $("#uEnterpriseQQ").val(row.uEnterpriseQQ);
	              $("#uQQ").val(row.uQQ);
	              $("#uUserDesc").val(row.uUserDesc);
	             
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工信息表查询失败，请联系管理员","info");
		   }
	    });
	  
	 
	 showJumpDate();
	$('#w').window('open');
	$("#UserInsert").hide();
	$("#UserUpdate").show();
     
}  
function getPinYin(){
	var uName = $("#uName").val();
	if (uName!=null && uName!="") {
		$.ajax({  
			  type:"POST",
				async:false,
				cache:false,
				data:"uName="+uName,
		        url:ctx+'/usermanager/getPinYinByUname',
		        dataType:"json",
		        success:function(returnData){
		        	if (returnData.data!=false) {
		        		var uId = returnData.data;
						$("#uId").html(uId);
					}
		        }
		});
	}
}     
	

$(function(){
    $(".datebox :text").attr("readonly","readonly"); 
});

function btnAddRow(user,rowIndex)  
{   
	var id=user.uId; 
	var cId = user.cId;
	
	if (!cId) {
		cId="";
	}
    var row="<tr ondblclick='tr_click("+'"'+  
    id+'"'+")'  ><td>"+(rowIndex+1)+"&nbsp;</td><td>"+"<input name='checkbox11' type='checkbox' value="+id+">"+"&nbsp;</td><td>"+cId+"&nbsp;</td><td>"+user.uName+"&nbsp;</td><td>"+user.uId+"&nbsp;</td><td>"+user.oName+"&nbsp;</td><td>"+user.uTelphone+"&nbsp;</td><td>"+user.uPhone+"&nbsp;</td><td>"+user.uPosition+"&nbsp;</td><td>"+user.uJoinDateTime+"&nbsp;</td><td>"+user.uBirthday+"&nbsp;</td></tr>";
     
    $(row).insertAfter($("#mytable tr:eq("+rowIndex+")"));   
} 
 