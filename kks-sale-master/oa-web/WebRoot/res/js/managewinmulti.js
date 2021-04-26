function manwinMulti(){
	ManagerWindowTreeInitMulti("#ManagertreeMulti");
	ManagerWindowDatagridInitMulti();
	ManagerWindowTreeSelectMulti();
}
var tbIdMulti;
var tbIdMultiName="";
var tbIdMultiId="";
var tbIdTwoMulti;
function ManagerWindowOnOpenMulti(openElementIdMulti){
	 tbIdMulti=openElementIdMulti;
	 tbIdTwoMulti=null;
	 $('#ManagerDataGridMulti').datagrid('clearSelections');
	 $('#ManagerWindowMulti').window("open");
}
var ManagerWindowuIdMulti="";
function ManagerWindowOnSelectMulti()
{
	tbIdMultiName="";
	tbIdMultiId="";
	var nodeMulti=$("#ManagerDataGridMulti").datagrid('getSelections');
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
		ManagerWindowuIdMulti=tbIdMultiId;
		ManagerWindowCloseMulti();
		GETUIDMulti(ManagerWindowuIdMulti);
	}
	else
	{
		$(tbIdMulti).val("");
		ManagerWindowCloseMulti();
	}
	
}
//传递两个控件ID的函数
function ManagerWindowOnOpenOnInputMulti(IdOne,IdTwo){
	
	
	tbIdMulti=IdOne;
	tbIdTwoMulti=IdTwo;
	 $('#ManagerWindowMulti').window("open");
}
function ManagerWindowOnOpenOnInputThree(IdOne,IdTwo,IdThree){
	tbIdMulti=IdOne;
	tbIdTwoMulti=IdTwo;
	$(IdThree).val(0);
	 $('#ManagerWindowMulti').window("open");
}


function ManagerWindowCloseMulti()
{
    $('#ManagerWindowMulti').window("close");
}

 
function ManagerWindowTreeInitMulti(tid)
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
var diclistMulti="";
function treeLoadAgin1Multi(returnData,tid)
{
	$(tid).empty();
	diclistMulti='[{"id":"0","text":"组织架构","children":[';
	eachlist1Multi(returnData.data);
    var reg=/,$/gi;
    diclistMulti=diclistMulti.replace(reg,"");
    diclistMulti+=']}]';
    $(tid).tree({
       data : eval(diclistMulti)  // 正确效果
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
function ManagerWindowTreeSelectMulti()
{
	$('#ManagertreeMulti').tree({
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
		    		    $('#ManagerDataGridMulti').empty();
		    		 }
			    	 else{
			    	 $('#ManagerDataGridMulti').datagrid('loadData',returnData.data);
			    	 }
				
			     }
			});
		},
		onDblClick:function(node){//add by wg.he 2013/12/23 添加组织架构父节点双击事件
			treeSelectListenerDBClick(this,node);
		}
		
	});
	
}
function ManagerWindowDatagridInitMulti()
{
	$("#ManagerDataGridMulti").datagrid({
	});
}