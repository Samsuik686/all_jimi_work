
function rolesMulti(){

	rolesWindowTreeInitMulti("#rolesWindowMulti");
	rolesWindowDatagridInitMulti();
	rolesWindowTreeSelectMulti();
}
var tbIdMulti;
var tbIdMultiName="";
var tbIdMultiId="";
var tbIdTwoMulti;
function rolesWindowOnOpenMulti(openElementIdMulti){
	 tbIdMulti=openElementIdMulti;
	 tbIdTwoMulti=null;
	 $('#rolesDataGridMulti').datagrid('clearSelections');
	 $('#rolesWindowMulti').window("open");
}
var rolesWindowuIdMulti="";
function rolesWindowOnSelectMulti()
{
	tbIdMultiName="";
	tbIdMultiId="";
	var nodeMulti=$("#rolesDataGridMulti").datagrid('getSelections');
	if(nodeMulti){
		for(var i=0;i<nodeMulti.length;i++){
			tbIdMultiName+=nodeMulti[i].uName+",";
			tbIdMultiId+=nodeMulti[i].uId+",";
		}
	    var reg=/,$/gi;
	    tbIdMultiName=tbIdMultiName.replace(reg,"");
	    tbIdMultiId=tbIdMultiId.replace(reg,"");
		$(tbIdMulti).val(tbIdMultiName);
		if(tbIdTwoMulti!=null)
		{
			$(tbIdTwoMulti).val(tbIdMultiId);
		}
		rolesWindowuIdMulti=tbIdMultiId;
		rolesWindowCloseMulti();
		GETUIDMulti(rolesWindowuIdMulti);
	}
	else
	{
		$(tbIdMulti).val("");
		rolesWindowCloseMulti();
	}
	
}
//传递两个控件ID的函数
function rolesWindowOnOpenOnInputMulti(IdOne,IdTwo){
//	alert($(IdOne).attr("id"));alert($(IdTwo).attr("id"));
	tbIdMulti=IdOne;
	tbIdTwoMulti=IdTwo;
	 $('#rolesWindowMulti').window("open");
}


function rolesWindowCloseMulti()
{
    $('#rolesWindowMulti').window("close");
}

 
function rolesWindowTreeInitMulti(tid)
{
       $.ajax({
        type:"POST",
        async:false,
        cache:false,
        url:ctx+'/rolePrivilege/allUserRoleList',
        dataType:"JSON",
        success:function(returnData){
        	//var data= $.parseJSON(returnData);
        	//$('#rolesDataGridMulti').datagrid('loadData',returnData);
        	//treeLoadAgin1Multi(returnData.data,tid);
        },
       error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
       });
   
	}
var rolesdiclistMulti="";
function treeLoadAgin1Multi(returnData,tid)
{	
	rolesdiclistMulti='[{"id":"0","text":"角色架构","children":'+returnData;
	//eachlist1Multi(returnData.data);
	
	var reg=/,$/gi;
    rolesdiclistMulti=rolesdiclistMulti.replace(reg,"");
    rolesdiclistMulti+='}]';
    
    alert(rolesdiclistMulti);
    
    $(tid).tree({
       data : eval(rolesdiclistMulti)  // 正确效果
    });
	
}

function eachlist1Multi(returnData)
{
	$.each(returnData,function(i,j){
	       if(j.list!=""){
	    	   
	    	   diclistMulti+='{"id":"'+j.oId+'","text":"'+j.oName+'","state":"closed","children":[';
		       eachlist1(j.list);
		       var reg=/,$/gi;
		       diclistMulti=diclistMulti.replace(reg,"");
	       }
	       else{
	    	   diclistMulti+='{"id":"'+j.oId+'","text":"'+j.oName+'","children":[';
	       }
	      diclistMulti+=']},';
	    });
}
function rolesWindowTreeSelectMulti()
{
	$('#rolesWindowMulti').tree({
		onClick:function(node){
		  $.ajax({
			     type:"POST",
			     async:false,
			     cache:false,
			     data:"oId="+node.id,
			     url:ctx+'rolePrivilege/allUserRoleList',
			     dataType:"json",
			     success:function(returnData){
			    	 if(returnData.data==null)
		    		 {
		    		    $('#rolesDataGridMulti').empty();
		    		 }
			    	 else{
			    	 $('#rolesDataGridMulti').datagrid('loadData',returnData.data);
			    	 }
				
			     }
			});
		}
		
	});
	
}
function rolesWindowDatagridInitMulti()
{
	$("#rolesDataGridMulti").datagrid({
	});
}