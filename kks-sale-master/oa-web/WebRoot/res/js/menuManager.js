$(function(){
	datagridInit();
	MenuManagerload();
	InitLeftMenu(_menus);
	$("#update").hide();
	$("#delete").hide();
	$("#add").hide();
	$("#AddInsertUrl").attr("disabled",true);
	
});

function UrlShow(menuId)
{
	
	$.ajax({
		type: "POST",
	  	async: false,
	  	cache: false,
	  	data:"menuId="+menuId,
	  	url: ctx+"/system/MenuDetailsSelectByMenuid",
	  	dataType: "json",
	  	success : function(returnData){
	  	//alert(returnData.data);
	  	 if(returnData.data=="MenusDeatailByMenuIdSearchfail")
	  	{ 
	  		 $('#DataGrid1').datagrid('loadData',{total:0,rows:[]});
	  	     $.messager.alert("操作提示","查询失败，参数错误！","info");
	  	}
	  	else
	  	{
	  		$('#DataGrid1').datagrid('loadData',returnData.data);
	  		
		} 
		},
		error : function(){
			$.messager.alert("操作提示","菜单权限Url网络错误","info");
	 	}
}); 
}
function datagridInit()
{
	var lastIndex="";
	$("#DataGrid1").datagrid({
		toolbar:[{
				text:'删除',
				iconCls:'icon-delecticon',
				handler:function(){
						var row = $('#DataGrid1').datagrid('getSelected');
						if (row){
						  $.messager.confirm("操作提示","是否删除此权限信息",function(conf){
							if(conf){
							var index = $('#DataGrid1').datagrid('getRowIndex', row);
							$('#DataGrid1').datagrid('deleteRow', index);
							var deleted = $('#DataGrid1').datagrid('getChanges', "deleted");  
							
		                	for(var z=0;z<deleted.length;z++)
		                	{ 
		                		DeleteUrl(deleted[z].functionId);
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
			
			}]
			
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

function AddInsertUrl()
{
	InsertUrl($("#menuID").val(),$("#functionURL").val(),$("#functionDesc").val());
	$("#functionURL").val(""); 
	$("#functionDesc").val("");
	
}
function InsertUrl(menuId,InsertUrl,displayName)
{
	
	
	GetTime();
	if(menuId!="")
		{
	          var param="menuId="+menuId
	                    +"&functionURL="+InsertUrl
	                    +"&functionDesc="+displayName
	                    +"&creater="
	                    +"&creatTime="+time;
	                    $.ajax({
	                		type: "POST",
	                	  	async: false,
	                	  	cache: false,
	                	  	data:param,
	                	  	url: ctx+"/system/insertMenusDetails",
	                	  	dataType: "json",
	                	  	success : function(returnData){
	                	  	 if(returnData.data=="MenusDeatailByFunIdAddfail")
	                	  	{ 
	                	  	     $.messager.alert("操作提示","菜单权限插入失败，插入参数错误！","info");
	                	  	}
	                	  	else
	                	  	{
	            	    	  	 $.messager.alert("操作提示","增加成功！","info");
	            	    	  	$('#DataGrid1').datagrid('loadData',returnData.data);
	                		} 
	                		},
	                		error : function(){
	                			$.messager.alert("操作提示","菜单权限插入，传递参数错误","info");
	                	 	}
	                }); 
		}
}
function DeleteUrl(funId)
{
	var param="functionId="+funId;
    $.ajax({
		type: "POST",
	  	async: false,
	  	cache: false,
	  	data:param,
	  	url: ctx+"/system/deleteMenusDetails",
	  	dataType: "json",
	  	success : function(returnData){
	  	//alert(returnData.data);
	  	 if(returnData.data=="MenusDeatailByFunIdDeletefail")
	  	{ 
	  	     $.messager.alert("操作提示","菜单权限删除失败，参数错误！","info");
	  	}
	  	else
	  	{
    	  	 $.messager.alert("操作提示","删除成功！","info");
    	  	  
		} 
		},
		error : function(){
			$.messager.alert("操作提示","菜单权限删除失败，网络错误","info");
	 	}
}); 
	
}
function add(){
	  //alert($("#SN").val());
     var param="parentId="+$("#parentID").val()
     +"&displayName="+$("#displayName").val()
     +"&url="+$("#url").val()
     +"&descrp="+$("#descrp").val()
     +"&sn="+$("#SN").val()
     +"&orderindex="+$("#orderindex").val();
     	$.ajax({
    		type: "POST",
    	  	async: false,
    	  	cache: false,
    	  	data:param,
    	  	url: ctx+"/system/MenuInsert",
    	  	dataType: "json",
    	  	success : function(returnData){
    	  	//alert(returnData.data);
    	  	 if(returnData.data=="false")
    	  	{ 
    	  	     $.messager.alert("操作提示","菜单增加失败，参数错误！","info");
    	  	}
    	  	else
    	  	{
    	  	     //alert("增加成功！");
	    	  	 $.messager.alert("操作提示","增加成功！","info",function(){
	    	  	 _menusAgain=returnData;
	    	  	   InitLeftMenuAgain(); 
	    	  	 });
	    	  	  
    		} 
    		},
    		error : function(){
    			$.messager.alert("操作提示","菜单增加失败，网络错误","info");
    	 	}
    }); 
}
function deleteChildren()
{
     var param="menuId="+$("#menuID").val();
 	$.ajax({
		type: "POST",
	  	async: false,
	  	cache: false,
	  	data:param,
	  	url: ctx+"/system/deleteChildren",
	  	dataType: "json",
	  	success : function(returnData){
	    /* _menusAgain=returnData;
	  	InitLeftMenuAgain();
	  		$.messager.alert("操作提示","删除成功！","info");
	  		AddbuttonShow();
	  		$("#add").hide(); */
		},
		error : function(){
			$.messager.alert("操作提示","网络错误","info");
    	 	}
    	 	
   	    });
}
function Menudelete(){
 
    	$.messager.confirm("操作提示","确认删除此目录，删除操作不可恢复！",function(conf){
    	if(conf)
    	{
    	 var param="menuId="+$("#menuID").val();
    	 var mid=$("#parentID").val();
    	 if(mid==0)
    	 { 
    	   $.messager.confirm("操作提示","此目录为主目录，删除操作会导致其子目录一并删除，请确认！",function(conf1){
    	   if(conf1){
    	   deleteChildren();
	     	$.ajax({
	    		type: "POST",
	    	  	async: false,
	    	  	cache: false,
	    	  	data:param,
	    	  	url: ctx+"/system/MenuDelete",
	    	  	dataType: "json",
	    	  	success : function(returnData){
	    	  	 if(returnData.data=="false")
	    	  	{ 
	    	  	     $.messager.alert("操作提示","菜单删除失败，参数错误！","info");
	    	  	}
	    	  	 else
	    	  	 {
	    	  		$.messager.alert("操作提示","删除成功！","info",function(){
	    	  	 _menusAgain=returnData;
	    	  	   InitLeftMenuAgain(); 
	    	  	 });
	    	  	 }
	    	  		AddbuttonShow();
	    	  		$("#add").hide();
	    	  	 
	    		},
	    		error : function(){
	    			$.messager.alert("操作提示","菜单删除失败，网络错误","info");
	    	 	}
	    	 	
    	    });
    	   }
    	   });
    	}
    	else{
    	$.ajax({
	    		type: "POST",
	    	  	async: false,
	    	  	cache: false,
	    	  	data:param,
	    	  	url: ctx+"/system/MenuDelete",
	    	  	dataType: "json",
	    	  	success : function(returnData){
	    	  	
	    	  		 if(returnData.data=="false")
	    	  	{ 
	    	  	     $.messager.alert("操作提示","菜单删除失败，参数错误！","info");
	    	  	}
	    	  	 else
	    	  	 {
	    	  		$.messager.alert("操作提示","删除成功！","info",function(){
	    	  	 _menusAgain=returnData;
	    	  	   InitLeftMenuAgain(); 
	    	  	 });
	    	  	 }
	    	  		AddbuttonShow();
	    	  		$("#add").hide();
	    		},
	    		error : function(){
	    			$.messager.alert("操作提示","菜单删除失败，网络错误","info");
	    	 	}
	    	 	
    	    });
    	  }
    	  
    	}
	});
	
}
function MenuUpdate(){
	//alert($("#SN").val());
var param="menuId="+$("#menuID").val()
+"&parentId="+$("#parentID").val()
+"&displayName="+$("#displayName").val()
+"&url="+$("#url").val()
+"&descrp="+$("#descrp").val()
+"&sn="+$("#SN").val()
+"&orderindex="+$("#orderindex").val();
$.ajax({
	type: "POST",
  	async: false,
  	cache: false,
  	data:param,
  	url: ctx+"/system/MenuUpdate",
  	dataType: "json",
  	success : function(returnData){
 		$.messager.alert("操作提示","修改成功！","info",function(){
	  	 _menusAgain=returnData;
	  	   InitLeftMenuAgain(); 
	  	 });
	},
	error : function(){
		$.messager.alert("操作提示","菜单修改失败，网络错误","info");
	 	}
	});

}
 function InitLeftMenuAgain() {
        /*   queryMenuId(_menusAgain.data[0].menuId);
           InitLeftMenu(_menusAgain);*/
	 parent.location.reload();    
/*         
        $(window.parent.document).find(".easyui-accordion").empty();
 	    var menulist = '';
	    $.each(_menusAgain.data, function(i, n) {
	        menulist += '<div title="'+n.displayName+'"  icon="icon-sys" style="overflow:hidden;">';
			menulist += '<ul>';
	        $.each(n.children, function(j, o) {
				menulist += '<li><div><a ref="' + o.menuId + '" href="#" rel="'
			+ o.url + '" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">' + o.displayName
			+ '</span></a></div></li> ';
	        });
	        menulist += '</ul></div>';
	    });
	 $(window.parent.document).find(".easyui-accordion").append(menulist);
	  
	 $(window.parent.document).find(".easyui-accordion").accordion({animate:true}); 
	
	 $('#wnav li a',window.parent.document).live('click', function() {
		var tabTitle = $(this).children('.nav').text();
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		//var icon = getIcon(menuid, icon);
		//$('#wnav').accordion("remove","工作流程");
		addTab(tabTitle, url);
		$('#wnav li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});

	 self.location.reload();
    
	  AddbuttonShow();
      $("#add").hide();*/
}
function addTab(subtitle,url){
	if(!$('#tabs',window.parent.document).tabs('exists',subtitle)){
		$('#tabs',window.parent.document).tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			width:$('#mainPanle').width()-10,
			height:$('#mainPanle').height()-26
		});
	}else{
		$('#tabs',window.parent.document).tabs('select',subtitle);
	}
	//tabClose();
}

function createFrame(url)
{
	var s = '<iframe name="mainFrame" scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function InitLeftMenu(_menusData) {
   
	
    $("#ea1").empty();
    var menulist = "";
   menulist+="<ul>";
    $.each(_menusData.data, function(i, n) {
        menulist += '<li><div style="overflow:hidden;">'+
       '<a id="'+n.menuId+'" target="mainFrame" href="javascript:queryMenuId('+n.menuId+')" ><span class="icon icon-sys" >&nbsp;</span><span class="nav">'
       + n.displayName+ '</span></a></div></li>';
	
    });
    menulist+='</ul>';
	$("#ea1").append(menulist);
	hoverMenuItem();
	$('#ea1 li a').live('click', function() {
		$('#ea1 li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});
}
/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem() {
	$(".easyui-accordion").find('a').hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
}

function AddbuttonShow()
{
	$("#update").hide();
	$("#delete").hide();
	  $("#add").show();
	   $("#menuID").val("");
	   $("#parentID").val("");
	   $("#displayName").val("");
	   $("#url").val("");
	   $("#descrp").val("");
	   $("#SN").val("");
	   $("#orderindex").val("");
	   $("#menuID").attr("disabled",false);
	   $("#parentID").attr("disabled",false);
	   $("#parentID").empty();
	   selectInit();
	   if(_displayNa!=null)
		 { $("#parentID").val(_displayNa);}
	   $('#DataGrid1').datagrid('loadData',{total:0,rows:[]});
	   $("#AddInsertUrl").attr("disabled",true);
}

function selectInit()
{
	   $.ajax({
		   type:"POST",
		   async:false,
	       cache:false,
	       url:"system/queryParentId",
	       dataType:"json",
	       success:function(returnData)
	       {
	    	   $("#parentID").append("<option value='0'>根目录</option>");   
		    	for(i=0;i<returnData.data.length;i++)
		       {
		    		$("#parentID").append("<option value='"+returnData.data[i].menuId+"'>"+returnData.data[i].displayName+"</option>");
		       }
	       },
	       error:function()
	       {
	    	   alert("查询错误");	
	       }
	   });
	}

function InitchildMenu(returnData)
{    
	
    $("#ea2").empty();
	var menulist1="";
	menulist1+='<ul>';
	$.each(returnData.data,function(i,n){
           menulist1 += '<li><div style="overflow:hidden;"><a target="mainFrame" href="javascript:ChildqueryMenuId('
 				+n.menuId+')" ><span class="icon icon-nav" >&nbsp;</span><span class="nav">' + n.displayName
				+ '</span></a></div></li>';
 
	  });
	//alert(menulist1);
    $("#ea2").append(menulist1);
    hoverMenuItem();
	$('#ea2 li a').live('click', function() {
		$('#ea2 li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});
	
}

function queryMenuIdChildren(menuId)
{
	$.ajax({
		type:"POST",
		async:false,
		cache:false,
		data:"menuId="+menuId,
		url:"system/queryMenuIdChildren",
		dataType:"json",
		success:function(returnData)
		{
		   InitchildMenu(returnData);
		}
	});
	}

function ChildqueryMenuId(menuId)
{
	
	$.ajax({
		type:"POST",
		async:false,
	   cache:false,
	   data:"menuId="+menuId,
	   url:"system/queryMenuId",
	   dataType: "json",
	   success: function(returnData){
		   $("#parentID").empty();
		   selectInit();
		   if(returnData.data.displayName=="菜单管理")
		   {
			   $("#update").hide();
			   $("#delete").hide();
			   $("#add").hide();
		   }
		   else{
		   $("#update").show();
		   $("#delete").show();
		   $("#add").hide();
		   }
		   //alert(returnData.data.sn);
		   
		   $("#menuID").val(returnData.data.menuId);
		   $("#parentID").val(returnData.data.parentId);
		   $("#displayName").val(returnData.data.displayName);
		   $("#url").val(returnData.data.url);
		   $("#descrp").val(returnData.data.descrp);
		   $("#SN").val(returnData.data.sn);
		   $("#orderindex").val(returnData.data.orderindex);
		   $("#parentID").attr("disabled",false);
		   UrlShow(returnData.data.menuId);
		   $("#AddInsertUrl").attr("disabled",false);
	   },
		error: function(){
		  alert("查询错误");	
		}
	});
}

function queryMenuId(menuId)
{
	
	$.ajax({
		type:"POST",
		async:false,
	   cache:false,
	   data:"menuId="+menuId,
	   url:"system/queryMenuId",
	   dataType: "json",
	   success: function(returnData){
		   $("#parentID").empty();
		   selectInit();
		   queryMenuIdChildren(returnData.data.menuId);
		   if(returnData.data.displayName=="系统管理")
		   {
			   $("#update").hide();
			   $("#delete").hide();
			   $("#add").hide();
		   }
		   else{
		   $("#update").show();
		   $("#delete").show();
		   $("#add").hide();
		   }
		   $("#menuID").val(returnData.data.menuId);
		   $("#parentID").val(returnData.data.parentId);
		   $("#displayName").val(returnData.data.displayName);
		   $("#url").val(returnData.data.url);
		   $("#descrp").val(returnData.data.descrp);
		   $("#SN").val(returnData.data.sn);
		   $("#orderindex").val(returnData.data.orderindex);
		   _displayNa=returnData.data.menuId;
		   $("#parentID").attr("disabled",true);
		   $("#AddInsertUrl").attr("disabled",true);
		   $('#DataGrid1').datagrid('loadData',{total:0,rows:[]});
	   },
		error: function(){
		  alert("查询错误");	
		}
	});
	
	
}
