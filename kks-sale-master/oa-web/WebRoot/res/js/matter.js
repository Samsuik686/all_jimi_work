    $(function(){
    	datagridInit();
    	treeLoadAgin();
    	close1();
    	treeSelect();
    	initdata();
    })
    function initdata(){
    	
    	$.ajax({
		     type:"POST",
		     async:false,
		     cache:false,
		     url:'${ctx }/matter/initDatas',
		     dataType:"json",
		     success:function(returnData){
		    	 if(returnData.data==null)
	    		 {
	    		    $('#DataGrid1').empty();
	    		 }
		    	 else{
		    	 $('#DataGrid1').datagrid('loadData',returnData.data);
		    	 }
			
		     }
		});
    	
    }
    
    function treeSelect()
{
	$('#tt2').tree({
		onClick:function(node){
			/***
		  $.ajax({
			     type:"POST",
			     async:false,
			     cache:false,
			     data:"formid="+node.id,
			     url:'indexByFormid',
			     dataType:"json",
			     success:function(returnData){
			    	 
			    	 if(returnData.data==null)
		    		 {
		    		    $('#DataGrid1').empty();
		    		 }
			    	 else{
			    	 $('#DataGrid1').datagrid('loadData',returnData.data);
			    	 }
				
			     }
			});****/
		}
			
	});
}
    
    function close1(){
		$('#w').window('close');
	}
    var allck=1;
    function delOption(row){
    	
    	$.ajax({
		     type:"POST",
		     async:false,
		     cache:false,
		     data:"id="+row.id,
		     url:'${ctx }/matter/delMatter',
		     dataType:"json",
		     success:function(returnData){
		    	 if (returnData.data==1) {
		    		if (row){
    					var index = $('#DataGrid1').datagrid('getRowIndex', row);
    					$('#DataGrid1').datagrid('deleteRow', index);
    				}
		    		 
		}
		     }
		});
    }
    
    
    function datagridInit()
    {
    	var lastIndex;
    	$('#DataGrid1').datagrid({
    		nowrap:false,
    		
    		
    		columns:[[
		     {field:'id',title:'',width:100,hidden:true},
		     {field:'title',title:'事项名称',width:200},
  		     {field:'memo',title:'事项描述',width:300},
  		     {field:'status',title:'事项状态',width:200,align:'right',
  		       styler:function(value,row,index){
  			if (value < 20){
  			return 'background-color:#ffee00;color:red;';
  				}
  			}
  		     },
  		     {field:'createTime',title:'创建时间',width:200},
  		     {field:'creator',title:'创建者',width:80,align:'center'}
  		]],
    		
    		
    		toolbar:[{
    			text:'新建事项',
    			iconCls:'icon-add',
    			handler:function(){
    			var node=$("#tt2").tree("getSelected");
    			
    			if(node){
    				$("#workFlowId").val(node.id);
    				$("#workFlowName").val(node.text);
    				$('#w').window('open');
    				
    			}else{
    				$.messager.alert('错误信息','请选中左边树对应的流程！','error');
    				
    			}
    			
    			
    			
    			
    			}
    		},'-',{
    			text:'删除事项',
    			iconCls:'icon-cancel',
    			handler:function(){
    				var row = $('#DataGrid1').datagrid('getSelected');
    				
    				/*******************删除逻辑*******************/
    				
    				$.messager.confirm('提示', '是否彻底删除'+row.title+'事项?', function(r){
    					if (r){
						//delByWFid    						
						delOption(row);
    						 
    						
    						
    					}
    				});
    				
    				
    				
    				
    			}
    		},'-',{
    			text:'启动事项',
    			iconCls:'icon-back',
    			handler:function(){
    				var row = $('#DataGrid1').datagrid('getSelected');
    				
    				/*******************删除逻辑*******************/
    				
    				$.messager.confirm('提示', '是否彻底删除'+row.title+'事项?', function(r){
    					if (r){
						//delByWFid    						
						delOption(row);
    						 
    						
    						
    					}
    				});
    				
    				
    				
    				
    			}
    		}]
    	
    	
    	
    	/***,
    		
    		
    		
    		
    		
    		onClickRow:function(rowIndex){
    			if (lastIndex != rowIndex){
    				$('#DataGrid1').datagrid('endEdit', lastIndex);
    				$('#DataGrid1').datagrid('beginEdit', rowIndex);
    			}
    			lastIndex = rowIndex;
    		}*****/
    		
    	});
    	//var dic;
    /*   $.ajax({
         type:"POST",
         async:false,
         cache:false,
         url:'selectdic',
         dataType:"json",
         success:function(returnData){
        	 $('#DataGrid1').datagrid('loadData',returnData.data);
         }
    	
         });*/
    }