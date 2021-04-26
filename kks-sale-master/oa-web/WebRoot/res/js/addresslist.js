$(function(){
	treeInit();
	datagridInit();
	treeSelect(); 
}); 
var nodestatus=false;
$.messager.defaults= { ok: "确定",cancel: "取消" };
function treeInit()
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
        	treeLoadAgin(data);
        },
       error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
       });
   
}
var diclist="";
function treeLoadAgin(returnData)
{
	$("#tt2").empty();
	diclist='[{"id":"0","text":"所有部门","children":[';
	
	eachlist(returnData.data);
    var reg=/,$/gi;
    diclist=diclist.replace(reg,"");
    diclist+=']}]';
    $('#tt2').tree({
       data : eval(diclist)  // 正确效果
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
function treeSelect()
{
	 
	$('#tt2').tree({
		onClick:function(node){
		    document.getElementById("treeOrgId").value=node.id;
		
			var xnode=node.id;
			 $("#oidSelect").val(xnode);  
			  
			 
			if(node.id==0)
			{ 	     
				var param="uStatus=0";
	 
				$.ajax({
					  type:"POST",
			 			async:false,
			 			cache:false,
			 			data:param,
				        url:ctx+'/usermanager/userselectall',
				        dataType:"json",
				        success:function(returnData){
				        	processSSOOrPrivilege(returnData);
				        	if(returnData.data=="false")
				        	{
				        		$.messager.alert("操作提示","通讯录查询错误，请稍后再试。","info");
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
						   $.messager.alert("操作提示","通讯录查询错误，请稍后再试。","info");
					   }
				       });
			}
			else
			{
 
			var param="oId="+node.id;
			 $.ajax({ 
				  type:"POST",
		 			async:false,
		 			cache:false,
		 			data:param,
			        url:ctx+'/usermanager/userselect',
			        dataType:"json",
			        success:function(returnData){
			        	processSSOOrPrivilege(returnData);
			        	if(returnData.data=="false")
			        	{
			        		$.messager.alert("操作提示","通讯录查询错误，请稍后再试。","info");
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
					   $.messager.alert("操作提示","通讯录查询错误，请稍后再试。","info");
				   }
			       });
				}
		
		},
		onDblClick:function(node){
			treeSelectListenerDBClick(this,node);
		}
			
	});
	
}
function windowClose()
{
    $('#w').window("close");	
}
function isChar(str) 
{
	        isCharacter = /^[A-Za-z]+$/;
	       //验证并返回结果
	       return (isCharacter.test(str));
}
function searchinfo()
{  
	var treeOrgId= document.getElementById("oidSelect").value;
	parm="uName="+$("#searchUname").val()+"&oId="+treeOrgId+"&uStatus=0"+"&cId="+$("#searchCid").val(); 
		$.ajax({ 
		     type:"POST",
		     async:false, 
		     cache:false,  
		     data:parm, 
		     url:ctx+'/user/SelectUserByUserInfo',
		     dataType:"json", 
		     success:function(returnData){
		    	 //$("#DataGrid1").datagrid('loadData',returnData.data);
		    	 	clearTable();
		    		var usersInfo=returnData.data;
	        		for(var i=0;i<usersInfo.length;i++){
	        			btnAddRow(usersInfo[i],i);
	        		}
		     }
		});
} 
function importexcel()
{
	//var treeOrgId= document.getElementById("treeOrgId").value;
	 
	var treeOrgId= document.getElementById("oidSelect").value;
	//if(treeOrgId=='')treeOrgId=0;    
	parm="uName="+$("#searchUname").val()+"&oId="+treeOrgId;
	//getUserSessionStatus("page/AddressList.jsp"); 
	 
	parent.location.href=ctx+"/Address/export?"+parm;
}
function datagridInit()
{
	parm="uStatus=0";   
	  $.ajax({
		  type:"POST",
 			async:false,
 			cache:false,
 		    data:parm, 
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


function clearTable(){
	 $("#mytable tr").eq(0).nextAll().remove();
}

function btnAddRow(user,rowIndex)
{
	var uManager=user.uManagerName;
 
    if(uManager==null)
    {  
    	uManager="";  
    }
    var cId = user.cId;
	if (!cId) {
		cId="";
	}	  
	var row="<tr><td>"+(rowIndex+1)+"&nbsp;</td><td>"+cId+"&nbsp;</td><td>"+user.uName+"&nbsp;</td><td>"+user.uId+"&nbsp;</td><td>"+user.oName+"&nbsp;</td><td>"+user.uPosition+"&nbsp;</td><td>"+uManager+"&nbsp;</td><td>"+user.uTelphone+"&nbsp;</td><td>"+user.uPhone+"&nbsp;</td><td>"+user.uMail+"&nbsp;</td><td>"+user.uQQ+"&nbsp;</td><td>"+user.uEnterpriseQQ+"&nbsp;</td></tr>";
    $(row).insertAfter($("#mytable tr:eq("+rowIndex+")"));   
}


