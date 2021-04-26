$(function(){
	treeInit();
	datagridInit();
	treeSelect(); 
	
}); 
var nodestatus=false;
$.messager.defaults= { ok: "确定",cancel: "取消" };

function searchinfo()
{
 
		  $.ajax({
			     type:"POST",
			     async:false,
			     cache:false,
			     data:"oId="+$("#oidSelect").val(),
			     url:ctx+'/user/OrganSelectByProID',
			     dataType:"json",
			     success:function(returnData){
			    	 var rdata= $.parseJSON(returnData);
			    	 if(rdata.data==null)
		    		 {
		    		    $('#DataGrid1').empty();
		    		 }
			    	 else{
			    	 $('#DataGrid1').datagrid('loadData',rdata.data);
			    	 }
				
			     }
			});  
 
}

function treeInit()
{
 
	$("#oidSelect").append("<option value=''>所有部门</option>"); 
	$("#oParentId").append("<option value='0'>组织架构</option>"); 
       $.ajax({
        type:"POST",
        async:false, 
        cache:false,
        url:ctx+'/user/OrganSelectList',
        dataType:"JSON",
        success:function(returnData){
        	var data= $.parseJSON(returnData);
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
	diclist='[{"id":"","text":"所有部门","children":[';
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
	    	   $("#oParentId").append("<option value='"+j.oId+"'>"+j.oName+"</option>"); 
	    	   selectInitlist(j.list);  
	       }
	       else{
	    	   $("#oParentId").append("<option value='"+j.oId+"'>"+j.oName+"</option>");
	    	   $("#oidSelect").append("<option value='"+j.oId+"'>"+j.oName+"</option>");
	       }
	     
	    });
}

function treeSelect()
{
	var diclist;
	$('#tt2').tree({
		onClick:function(node){  
			$("#oParentId").val(node.id);
			$("#oidSelect").val(node.id);   
	   
		  $.ajax({
			     type:"POST",
			     async:false,
			     cache:false,
			     data:"oId="+node.id,
			     url:ctx+'/user/OrganSelectByProID',
			     dataType:"json",
			     success:function(returnData){
			    	 var rdata= $.parseJSON(returnData);
			    	 if(rdata.data==null)
		    		 {
		    		    $('#DataGrid1').empty();
		    		 }
			    	 else{
			    	 $('#DataGrid1').datagrid('loadData',rdata.data);
			    	 }
				
			     }
			});
		},
		onDblClick:function(node){
			treeSelectListenerDBClick(this,node);
		},
	onBeforeExpand:function(node,param){
		
       // $('#tt2').tree('options').url =ctx+'/SalaryMonths/selectall';
        
    }	
	});
	
}
function windowClose()
{
    $('#w').window("close");
}
function oSave()
{
	 
  var oParentId = $("#oParentId").val()
	if (oName == '') {
		$.messager.alert("操作提示", "上级部门名称不能为空", "error");
		return; 
	}	
	

   var oName = $("#oName").val();
	if (oName == '') {
		$.messager.alert("操作提示", "部门名称不能为空", "error");
		return;
	}

	var oManagerName = $("#oManagerName").val();
	if (oManagerName == '') {
		$.messager.alert("操作提示", "主管名称不能为空", "error");
		return;
	}

	var oIndex = $("#oIndex").val();
	if (oIndex == '') {
		$.messager.alert("操作提示", "排序序号不能为空", "error");
		return;
	}
			    
	   
	
	windowClose();
   var node = $('#tt2').tree('getSelected'); 
   if(node==null)
	   {
	      node=$('#tt2').tree("getRoot");
	   }
	 var param="&oParentId="+$("#oParentId").val()
   	 	+"&oName="+$("#oName").val()
   	 	+"&oManagerId="+$("#oManagerId").val()
   	 	+"&oManagerName="+$("#oManagerName").val()
		 +"&oIndex="+$("#oIndex").val()
   	 	+"&oDesc="+$("#oDesc").val();
   		$.ajax({
   			type:"POST",
   			async:false,
   			cache:false,
   			data:param,
   			url:ctx+"/user/OrganInserList",
   			dataType:"json",
   			success:function(returnData){
   				var rdata= $.parseJSON(returnData);
   			    $('#DataGrid1').datagrid('loadData',rdata.data);
	   			var nodes='[{"id":"","text":"'+$("#oName").val()+'"}]';
	   			$("#tt2").tree('append',{parent:node.target,data:eval(nodes)});
   				$.messager.alert("操作提示","增加成功","info");
   			},
   		   error:function(){
   			   $.messager.alert("操作提示","网络错误","info");
   		   }
   		});
   		
   		inputclean();
	}

function GETUID(ManagerWindowuId)
{
	$("#oManagerId").val(ManagerWindowuId);
}
function oUpdate()
{
	windowClose();
	   var node = $('#tt2').tree('getSelected'); 
	   if(node==null)
	   {
	      node=$('#tt2').tree("getRoot");
	   }
		 var param="oId="+$("#oId").val()
			 +"&oParentId="+$("#oParentId").val()
	   	 	+"&oName="+$("#oName").val()
	   	 	+"&oManagerId="+$("#oManagerId").val()
	   	 	+"&oManagerName="+$("#oManagerName").val()
			 +"&oIndex="+$("#oIndex").val()
	   	 	+"&oDesc="+$("#oDesc").val();
	   		$.ajax({
	   			type:"POST",
	   			async:false,
	   			cache:false,
	   			data:param,
	   			url:ctx+"/user/OrganUpdate",
	   			dataType:"json",
	   			success:function(returnData){
	   				var rdata= $.parseJSON(returnData);
	   			    $('#DataGrid1').datagrid('loadData',rdata.data);
	   			    var treed= $("#tt2").tree('find',$("#oId").val());
					$("#tt2").tree("remove",treed.target);
		   			var nodes='[{"id":"","text":"'+$("#oName").val()+'"}]';
		   			$("#tt2").tree('append',{parent:node.target,data:eval(nodes)});
	   				$.messager.alert("操作提示","修改成功","info");
	   			},
	   		   error:function(){
	   			   $.messager.alert("操作提示","网络错误","info");
	   		   }
	   		});
	   		
	   		inputclean();
}
function inputclean()
{
	$("#oId").val("");
	$("#oParentId").val("");
	$("#oName").val("");
	$("#oManagerId").val("");
	$("#oManagerName").val("");
	$("#oIndex").val("");
	$("#oDesc").val("");
	
}

function deletechild(oId)
{
	  $.ajax({
		     type:"POST",
		     async:false,
		     cache:false,
		     data:"oId="+oId,
		     url:ctx+'/user/OrganSelectByProID',
		     dataType:"json",
		     success:function(returnData){
		    	 var rdata= $.parseJSON(returnData);
		        $.each(rdata.data,function(i,j){
		        	//alert(j.oId);
		        	$.ajax({
       				 type:"POST",
       				 async:false,
       				 cache:false,
       				 data:"oId="+j.oId,
       				 url:ctx+"/user/OrganDelete",
       				 dataType:"json",
       				 success:function(returnData){
       				 },
       			   error:function(){
       				   $.messager.alert("操作提示","网络错误","info");
       			   }
       				   
       			   });
		        });
		     }
	  });
	}

function datagridInit()
{
	var lastIndex;
	$("#DataGrid1").datagrid({
		toolbar:[{
			text:'增加',
			iconCls:'icon-add',
			handler:function(){
				$("#oUpdate").hide();
				$("#oSave").show();
				$('#w').window("open");
				
			}
		},'-',{
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var row = $('#DataGrid1').datagrid('getSelected');
					if (row){
					  $.messager.confirm("操作提示","是否删除此部门信息(如果此部门有子部门存在也会一并删除，请谨慎)!",function(conf){
						if(conf){
						var index = $('#DataGrid1').datagrid('getRowIndex', row);
						$('#DataGrid1').datagrid('deleteRow', index);
						var deleted = $('#DataGrid1').datagrid('getChanges', "deleted");  
						
	                	for(z=0;z<deleted.length;z++)
	                	{ 
	                		deletechild(deleted[z].oId);
	                		//alert(deleted[z].dicid);
	                		$.ajax({
	        				 type:"POST",
	        				 async:false,
	        				 cache:false,
	        				 data:"oId="+deleted[z].oId,
	        				 url:ctx+"/user/OrganDelete",
	        				 dataType:"json",
	        				 success:function(returnData){
	        					 deletechild(deleted[z].oId);
	        					var treed= $("#tt2").tree('find',deleted[z].oId);
	        					$("#tt2").tree("remove",treed.target);
	        					$.messager.alert("操作提示","删除成功","info",function(){
	        					 });
	        				 },
	        			   error:function(){
	        				   $.messager.alert("操作提示","网络错误","info");
	        			   }
	        				   
	        			   });
	                	}
			            $('#DataGrid1').datagrid('acceptChanges');  
					   }
					  });
					}
					else
					{
						$.messager.alert("提示信息","请先选择所要删除的行！","info");
						}
				}
			},'-',{
				text:'修改',
				iconCls:'icon-redo',
				handler:function(){
					var row = $('#DataGrid1').datagrid('getSelected');
					if (row){
					$("#oId").val(row.oId);
					$("#oParentId").val(row.oParentId);
					 $("#oName").val(row.oName);
					 $("#oManagerId").val(row.oManagerId);
					 $("#oManagerName").val(row.oManagerName);
					$("#oIndex").val(row.oIndex);
					 $("#oDesc").val(row.oDesc);
					 $("#oUpdate").show();
					$("#oSave").hide();
					$('#w').window("open");
					}
				}
			}],
			onDblClickRow:function(rowIndex, rowData){
				$("#oId").val(rowData.oId);
				$("#oParentId").val(rowData.oParentId);
				 	$("#oName").val(rowData.oName);
				 	$("#oManagerId").val(rowData.oManagerId);
				 $("#oManagerName").val(rowData.oManagerName);
				$("#oIndex").val(rowData.oIndex);
				 $("#oDesc").val(rowData.oDesc);
				 $("#oUpdate").show();
					$("#oSave").hide();
				$('#w').window("open");
			}
	});
	 
	searchinfo();
}
