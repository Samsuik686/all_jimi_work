
	var deptName="";  
function manwin4Form(){
	ManagerWindowTreeInit4Form("#Managertree4Form");
	ManagerWindowTreeSelect4Form();  
} 
var tbId;
var tbIdTwo;
function ManagerWindowOnOpen4Form(openElementId){
	 tbId=openElementId;
	 tbIdTwo=null;
	 showDiv(openElementId);
}
 
var ManagerWindowuId="";

function ManagerWindowOnSelect4Form()
{
  
    if(deptName!=""){
    	
		$(tbId).val(deptName);
	     
		//ManagerWindowuId=node.oId;
		 ManagerWindowClose4Form();
		//GETUID(ManagerWindowuId);
	}
	else 
	{
		$(tbId).val("");
		ManagerWindowClose();
	}
	
}

function ManagerWindowClose4Form()
{
    $('#ManagerWindow4Form').window("close");
}

 
function ManagerWindowTreeInit4Form(tid)
{
 
	$.ajax({
        type:"POST",
        async:false,
        cache:false,
        url:ctx+'/user/OrganSelectList',
        dataType:"JSON",
        success:function(returnData){
        	var data= $.parseJSON(returnData);
        	treeLoadAgin14Form(data,tid);
        },
       error:function(){
		   $.messager.alert("操作提示","网络错误","info");
	   }
       });
   
	}
var diclist="";
function treeLoadAgin14Form(returnData,tid)
{
	$(tid).empty();
	diclist='[{"id":"0","text":"组织架构","children":[';
	eachlist14Form(returnData.data);
    var reg=/,$/gi;
    diclist=diclist.replace(reg,"");
    diclist+=']}]';
    $(tid).tree({
       data : eval(diclist)  // 正确效果
    });
	
}

function eachlist14Form(returnData)
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

function ManagerWindowTreeSelect4Form()
{ 
    
 
	$('#Managertree4Form').tree({
		onClick:function(node){
		   deptName=node.text;
 
		},  
		onDblClick:function(node){
			ManagerWindowOnSelect4Form();
		}
		 
		
	}); 
	 
}      
 
function showDiv(obj) {

    // 获得元素的左偏移量
    var left = obj.offsetLeft; 
    // 获得元素的顶端偏移量 
    var top = obj.offsetTop;

    // 循环获得元素的父级控件，累加左和顶端偏移量
    while (obj = obj.offsetParent) {
        left += obj.offsetLeft;
        top += obj.offsetTop;
    }
 
    var $win;
    $win = $('#ManagerWindow4Form').window({top:(top) ,  left:(left) });
    $win.window("open");  
    
    
}
 
