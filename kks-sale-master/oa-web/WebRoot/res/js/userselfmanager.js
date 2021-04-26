$(function(){
	
$.ajax({
	     type:"POST",
	     async:false,
	     cache:false,
	     url:ctx+'/user/GetSessionUser',
	     dataType:"json",
	     success:function(returnData){

				$("#uId").val(returnData.data.uId);
				$("#uName").val(returnData.data.uName);
				$("#uTelphone").val(returnData.data.uTelphone);
				$("#uAge").val(returnData.data.uAge);

				$("#uMail").val(returnData.data.uMail);
				$("#uMailPwd").val(returnData.data.uMailPwd);

				$("#uEnterpriseQQ").val(returnData.data.uEnterpriseQQ);
				$("#uQQ").val(returnData.data.uQQ);
	     }
	});
});

function UISave()
{
	var param="uId="+$("#uId").val()
	+"&uName="+$("#uName").val()
	+"&uMail="+$("#uMail").val()
	+"&uMailPwd="+$("#uMailPwd").val()
	+"&uTelphone="+$("#uTelphone").val()
	+"&uAge="+$("#uAge").val()
	+"&uEnterpriseQQ="+$("#uEnterpriseQQ").val()
	+"&uQQ="+$("#uQQ").val();
	 $.ajax({
		  type:"POST",
			async:false,
			cache:false,
			data:param,
	        url:ctx+'/usermanager/UserUpdate',
	        dataType:"json",
	        success:function(returnData){
	        	if(returnData.data=="false")
	        	{
	        		$.messager.alert("操作提示","员工信息修改，参数错误","info");
	        	}
	        	else
	        	{
	        		$.messager.alert("操作提示","员工信息修改成功！","info");
	        	}
	        },
	       error:function(){
			   $.messager.alert("操作提示","员工信息修改，网络参数错误","info");
		   }
	      });
	 
}