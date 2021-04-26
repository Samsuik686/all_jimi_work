
function manwin(){

	 
	ManagerWindowTreeInit("#Managertree");
	ManagerWindowDatagridInit();
	ManagerWindowTreeSelect();
}
var tbId;
var tbIdTwo;
function ManagerWindowOnOpen(openElementId){
	 tbId=openElementId;
	 tbIdTwo=null;
	 $('#ManagerDataGrid').datagrid('clearSelections');
	 $('#ManagerWindow').window("open");
}
function ManagerWindowOnOpenAndId(openElementId,openHiddenId){
	 tbId=openElementId;
	 tbIdTwo=openHiddenId;
	 $('#ManagerDataGrid').datagrid('clearSelections');
	 $('#ManagerWindow').window("open");
}


var ManagerWindowuId="";
function ManagerWindowOnSelect()
{
	var node=$("#ManagerDataGrid").datagrid("getSelected");
	if(node){
		$(tbId).val(node.uName);
		if(tbIdTwo!=null)
		{
			$(tbIdTwo).val(node.uId);
		}
		ManagerWindowuId=node.uId;
		ManagerWindowClose();
		GETUID(ManagerWindowuId);
	}
	else
	{
		$(tbId).val("");
		ManagerWindowClose();
	}
	
}


/**
 * 员工信息管理面板
 */
function userManagerOpenSelect()
{
	var node=$("#ManagerDataGrid").datagrid("getSelected");
	if(node){
		$("#uManagerName").val(node.uName);
		$("#uManagerId").val(node.uId);
		ManagerWindowClose();
	}
	else
	{
		$(tbId).val("");
		$("#uManagerName").val("");
		ManagerWindowClose();
	}
	
}




function ManagerWindowOnSelectV1()
{
	var node=$("#ManagerDataGrid").datagrid("getSelected");
	if(node){
		$(tbId).val(node.uId);
		if(tbIdTwo!=null)
		{
			$(tbIdTwo).val(node.uId);
		}
		ManagerWindowuId=node.uId;
		ManagerWindowClose();
		
		accept('转发',2);
		GETUID(ManagerWindowuId);
	}
	else
	{
		$(tbId).val("");
		ManagerWindowClose();
	}
	
}


function ManagerWindowOnSelectV2()
{
	var node=$("#ManagerDataGrid").datagrid("getSelected");
	if(node){
		$(tbId).val(node.uId);
		if(tbIdTwo!=null)
		{
			$(tbIdTwo).val(node.uName);
		}
		ManagerWindowuId=node.uId;
		ManagerWindowClose();
		
		accept('转发',2);
		GETUID(ManagerWindowuId);
	}
	else
	{
		$(tbId).val("");
		ManagerWindowClose();
	}
	
}


//传递两个控件ID的函数
function ManagerWindowOnOpenOnInput(IdOne,IdTwo){
	 tbId=IdOne;
	 tbIdTwo=IdTwo;
	 $('#ManagerWindow').window("open");
}


function ManagerWindowClose()
{
    $('#ManagerWindow').window("close");
}

 
function ManagerWindowTreeInit(tid)
{
	 
       $.ajax({
        type:"POST",
        async:false,
        cache:false,
        url:ctx+'/user/OrganSelectList',
        dataType:"JSON",
        success:function(returnData){
        	var data= $.parseJSON(returnData);
        	treeLoadAgin1(data,tid);
        },
       error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
       });
   
	}
var diclist="";
function treeLoadAgin1(returnData,tid)
{
	$(tid).empty();
	diclist='[{"id":"0","text":"组织架构","children":[';
	eachlist1(returnData.data);
    var reg=/,$/gi;
    diclist=diclist.replace(reg,"");
    diclist+=']}]';
    $(tid).tree({
       data : eval(diclist)  // 正确效果
    });
	
}

function eachlist1(returnData)
{
	$.each(returnData,function(i,j){
	       if(j.list!=""){
	    	   
	    	   diclist+='{"id":"'+j.oId+'","text":"'+j.oName+'","state":"closed","children":[';
		       eachlist1(j.list);
		       var reg=/,$/gi;
		       diclist=diclist.replace(reg,"");
	       }
	       else{
	    	   diclist+='{"id":"'+j.oId+'","text":"'+j.oName+'","children":[';
	       }
	      diclist+=']},';
	    });
}
function ManagerWindowTreeSelect()
{
	$('#Managertree').tree({
		onClick:function(node){
		  $.ajax({
			     type:"POST",
			     async:false,
			     cache:false,
			     data:"oId="+node.id,
			     url:ctx+'/user/UserListByOrgId',
			     dataType:"json",
			     success:function(returnData){
			    	 if(returnData.data==null)
		    		 {
		    		    $('#ManagerDataGrid').empty();
		    		 }
			    	 else{
			    	 $('#ManagerDataGrid').datagrid('loadData',returnData.data);
			    	 }
				
			     }
			});
		}
		
	});
	
}
function ManagerWindowDatagridInit()
{
	$("#ManagerDataGrid").datagrid({
		onClickRow:function(rowIndex){
			var node=$("#ManagerDataGrid").datagrid("getSelected");
			//alert(node);
			if(node)
			{
				$("#ManagerDataGrid").datagrid("unselectRow",rowIndex);
			}
			else{
				$("#ManagerDataGrid").datagrid("selectRow",rowIndex);
			}
		}
	});
}