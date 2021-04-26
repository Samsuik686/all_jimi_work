$(function(){
	
	treeInit();
	datagridInit();
	treeSelect();
	  
}); 
$.messager.defaults= { ok: "确定",cancel: "取消" };
function treeInit()
{
	 
       $.ajax({
        type:"POST",
        async:false,
        cache:false,
        url:ctx+'/dictionary/selectdic',
        dataType:"json",
        success:function(returnData){
        	treeLoadAgin(returnData);
        },
       error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
       });
   
	}
function treeLoadAgin(returnData)
{
	var diclist='[{"id":"0","text":"主分类",children:[';
    $.each(returnData.data,function(i,j){
       diclist+='{"id":"'+j.dicId+'","text":"'+j.itemName+'"},';
    });
    var reg=/,$/gi;
    diclist=diclist.replace(reg,"");
    diclist+=']}]';
    $('#tt2').tree({
       data : eval(diclist)  // 正确效果
    });
	
	}
function treeAdd()
{
	var param="proTypeId=0&itemName="+$("#itemName").val()+"&itemValue="+$("#itemValue").val()+"&descrp="+$("#descrp").val()+"&sn="+$("#sntext").val()+"&orderindex="+$("#orderindex").val();
	//alert(param);
	$.ajax({
		type:"POST",
		async:false,
		cache:false,
		data:param,
		url:ctx+"/dictionary/treeAdd",
		dataType:"json",
		success:function(returnData){
			if(returnData.data=="false")
			{$.messager.alert("操作提示","数据字典树增加失败，参数错误","info");
		    }
			else{
			$.messager.alert("操作提示","数据字典树增加成功","info",function(){
				 treeLoadAgin(returnData);
			 });
			}
		},
	   error:function(){
		   $.messager.alert("操作提示","数据字典树增加失败，网络错误","info");
	   }
	});
	clearText();
	$("#w").window("close");
}

function treeUpdateWindow()
{
	var node=$("#tt2").tree('getSelected');
   if(node.id==0)
   { 
	   $.messager.alert("操作提示","不能修改主分类节点","error");   
   } 
   else
   {
	   
 
		if(node.id<13)
		 {    
			 $.messager.alert("操作提示","系统初始化数据,不能修改","error");
			 return false ; 
		 }  
		  
	   $.ajax({
		 type:"POST",
		 async:false,
		 cache:false,
		 data:"dicId="+node.id,
		 url:ctx+"/dictionary/selectTreeById",
		 dataType:"json",
		 success:function(returnData){
			 $("#itemName").val(returnData.data.itemName);
			 $("#itemValue").val(returnData.data.itemValue);
			   $("#descrp").val(returnData.data.descrp);
			   $("#sntext").val(returnData.data.sn);
			   $("#orderindex").val(returnData.data.orderindex);
			   $('#treeUpdate').linkbutton('enable');
				$("#treeSave").linkbutton("disable");
			   $('#w').window('open');
		 },
	   error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   } 
	   });
	   
   }
	
}

function treeUpdate(){
	
	var node=$("#tt2").tree('getSelected');

	 
	//var param="dicId="+node.id+"&proTypeId=0&itemName="+$("#itemName").val()+"&itemValue="+$("#itemValue").val()+"&descrp="+$("#descrp").val()+"&sn="+$("#sntext").val()+"&orderindex="+$("#orderindex").val();
	//alert(param);   
	$.ajax({
		type:"GET",
		async:false,
		cache:false,
		data:{
			dicId:node.id,
            proTypeId:0,
            itemName:$("#itemName").val(),
            itemValue:$("#itemValue").val(),
            sn:$("#sntext").val(),
            orderindex:$("#orderindex").val()  
		},
		url:ctx+"/dictionary/UpdateTreeById",
		dataType:"json",
		success:function(returnData){
			$.messager.alert("操作提示","修改成功","info",function(){
				 treeLoadAgin(returnData);
			 });
		},
	   error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
	});
	    clearText();
		$("#w").window("close");
}

function treeSelect()
{
	
	$('#tt2').tree({
		onClick:function(node){
		  $.ajax({
			     type:"POST",
			     async:false,
			     cache:false,
			     data:"dicId="+node.id,
			     url:ctx+'/dictionary/selectTreeByProTypeId',
			     dataType:"json",
			     success:function(returnData){
			    	 if(returnData.data==null)
		    		 {
		    		    $('#DataGrid1').empty();
		    		 }
			    	 else{
			    		 
			    	 $('#DataGrid1').datagrid('loadData',returnData.data);
			    	 }
				
			     },
		       error:function(){
			   $.messager.alert("操作提示","单击数据字典树网络错误","info");
		   }
			});
		}
			
	});
}

function clearText()
{
	
   $("#itemName").val("");
   $("#itemValue").val("");
   $("#descrp").val("");
   $("#sntext").val("");
   $("#orderindex").val("");
   
}

function treeAddwindow()
{
	
	$('#w').window('open');
	$('#treeUpdate').linkbutton('disable');
	$('#treeSave').linkbutton('enable');
	clearText();
}

function deleteChildren()
{
	
	 var node=$("#tt2").tree('getSelected');
	 $.ajax({
		 type:"POST",
		 async:false,
		 cache:false,
		 data:"dicId="+node.id,
		 url:ctx+"/dictionary/treeDeleteChildren",
		 dataType:"json",
		 success:function(returnData){
		 },
	   error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
		   
	   });
	
	}

function treeDelete()
{
   var node=$("#tt2").tree('getSelected');
   if(node.id<13)
	{    
		 $.messager.alert("操作提示","系统初始化数据,不能删除","error");
		 return false ; 
	}
	   
   if(node.id==0)
   { 
	   $.messager.alert("操作提示","不能删除主分类节点","error"); 
   }
   else
   {
	  $.messager.confirm("操作提示","删除分类节点会使此节点数据全部删除，请谨慎删除。",function(conf){
	    	if(conf)
	    	{ 
			   $.ajax({
				 type:"POST",
				 async:false,
				 cache:false,
				 data:"dicId="+node.id,
				 url:ctx+"/dictionary/treedelete",
				 dataType:"json",
				 success:function(returnData){
					 deleteChildren();
					 $.messager.alert("操作提示","删除成功","info",function(){
						 treeLoadAgin(returnData);
					  
					 });
				 },
			   error:function(){
				   $.messager.alert("操作提示","网络错误","info");
			   }
				   
			   });
	    	}
	  });
           
	}
}

function windowClose()
{
	$('#w').window('close');
}
function datagridInit()
{
	var lastIndex;
	$('#DataGrid1').datagrid({
		toolbar:[{
			text:'增加',
			iconCls:'icon-addicon',
			handler:function(){
			var node=$("#tt2").tree("getSelected");
			$('#DataGrid1').datagrid('endEdit', lastIndex);
				$('#DataGrid1').datagrid('appendRow',{
					dicId:'',
					proTypeId:node.id,
					itemName:'',
					descrp:'',
					sn:'',
					orderindex:''
				});
				var lastIndex = $('#DataGrid1').datagrid('getRows').length-1;
				$('#DataGrid1').datagrid('selectRow', lastIndex);
				$('#DataGrid1').datagrid('beginEdit', lastIndex);
			}
		},'-',{
			text:'删除',
			iconCls:'icon-delecticon',
			handler:function(){
				var row = $('#DataGrid1').datagrid('getSelected');
				var node=$("#tt2").tree("getSelected");
				
				  if(node.id<13)
				   { 
					   $.messager.alert("操作提示","系统初始化数据,不能删除","error");
					   return false ; 
				   }   
				
				if (row){
					var index = $('#DataGrid1').datagrid('getRowIndex', row);
					$('#DataGrid1').datagrid('deleteRow', index);
				}
			}
		},'-',{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				endEdit();
	 
				var inserted = $('#DataGrid1').datagrid('getChanges', "inserted");  
                var deleted = $('#DataGrid1').datagrid('getChanges', "deleted");  
                var updated = $('#DataGrid1').datagrid('getChanges', "updated");  
				var rowData = $('#DataGrid1').datagrid('getSelected');  
               //alert(rowData.proTypeId);
               if(inserted.length>0)
            	{
                	//alert("inserted");
            	     for(i=0;i<inserted.length;i++)
        	    	 {
            	    	 if(inserted[i].itemName!=null&&inserted[i].itemValue!=null
            	    			 &&inserted[i].itemName!=null&&inserted[i].descrp!=null
            	    			 &&inserted[i].orderindex!=null)
        	    	 	{
            	    		 var param="proTypeId="+inserted[i].proTypeId
			        	    	 	+"&itemName="+inserted[i].itemName
			        	    	 	+"&itemValue="+inserted[i].itemValue
			        	    	 	+"&descrp="+inserted[i].descrp
			        	    	 	+"&sn="+inserted[i].sn
			        	    	 	+"&orderindex="+inserted[i].orderindex;
			        	    	 	//alert(param);
			        	    		$.ajax({
			        	    			type:"POST",
			        	    			async:false,
			        	    			cache:false,
			        	    			data:param,
			        	    			url:ctx+"/dictionary/treeAdd",
			        	    			dataType:"json",
			        	    			success:function(returnData){
			        	    			},
			        	    		   error:function(){
			        	    			   $.messager.alert("操作提示","网络错误","info");
			        	    		   }
			        	    		});
        	    		}
        	    	 }
            	}
                if(updated.length>0)
            	{
                	//alert("updated");
	                 for(j=0;j<updated.length;j++)
	       	    	 {
 
	                	   if(updated[j].dicId<13&&updated[j].dicId!=9)
	                		{     
	                			 $.messager.alert("操作提示","系统初始化数据,不能修改","error");
	                			 return false ; 
	                		}
	                		
	                		var param="dicId="+updated[j].dicId
			                +"&itemName="+updated[j].itemName
			                +"&itemValue="+updated[j].itemValue
			                +"&descrp="+updated[j].descrp
			                +"&sn="+updated[j].sn
			                +"&orderindex="+updated[j].orderindex;
	                		
	                		$.ajax({
	                			type:"POST",
	                			async:false,
	                			cache:false,
	                			data:param,
	                			url:ctx+"/dictionary/UpdateTreeById",
	                			dataType:"json",
	                			success:function(returnData){
	                				$.messager.alert("操作提示","修改成功","info");
	                			},
	                		   error:function(){
	                			   $.messager.alert("操作提示","网络错误","info");
	                		   }
	                		});
	       	    	 }
            	}
                if(deleted.length>0)
                {
                	//alert("deleted");
                	for(z=0;z<deleted.length;z++)
                	{ 
                		
                		   if(updated[j].dicId<13)
	                		{     
	                			 $.messager.alert("操作提示","系统初始化数据,不能成功","error");
	                			 return false ; 
	                		}
                		    
                		$.ajax({
        				 type:"POST",
        				 async:false,
        				 cache:false,
        				 data:"dicId="+deleted[z].dicId,
        				 url:ctx+"/dictionary/treedelete",
        				 dataType:"json",
        				 success:function(returnData){
        					 $.messager.alert("操作提示","删除成功","info",function(){
        						 //treeLoadAgin(returnData);
        					  
        					 });
        				 },
        			   error:function(){
        				   $.messager.alert("操作提示","网络错误","info");
        			   }
        				   
        			   });
                	}
                }
                $('#DataGrid1').datagrid('acceptChanges');  
			}
		}],
		
		onClickRow:function(rowIndex){
			if (lastIndex != rowIndex){
				$('#DataGrid1').datagrid('endEdit', lastIndex);
				$('#DataGrid1').datagrid('beginEdit', rowIndex);
			}
			lastIndex = rowIndex;
		},
	});

}

function endEdit(){  
    var rows =  $('#DataGrid1').datagrid('getRows');  
    for ( var i = 0; i < rows.length; i++) {  
    	 $('#DataGrid1').datagrid('endEdit', i);  
    }  
} 
