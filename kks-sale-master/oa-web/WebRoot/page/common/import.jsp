<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 数据导入 -START -->
<div id="Import_comm_window" class="easyui-window" title="导入数据" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
	  style="visibility:hidden; width:500px;height:300px;padding:5px;">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:15px;background:#fff;border:1px solid #ccc;">
		     <form id="FormUpload" name="f1" action="" enctype="multipart/form-data" method="post">
				<table>
					<tr>
					  <td valign="top" width="80px">导入提示：</td>
					  <td>
						  <label id="upload_tip"></label>
					  </td>
				  </tr>
				  <tr>
					  <td>选择文件：</td>
					  <td>
				  		  <input type="file"  name="file"  id="fileToUpload" multiple="multiple" onchange="fileSelected();" accept=".xls,.xlsx"/>
				  		  <span id="fileSize"></span>
				  		  <a id="uploadFile" style=" display:none" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">上传并导入</a>
					  </td>
				  </tr>
				  <tr id ="showInfo" style="display: none;">
					  <td>文件信息：</td>
					  <td id="fileInfo"></td>
				  </tr>
				 </table>
			  </form>
	    </div>
		<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeImportWin();">关闭</a>
		</div>
		<div>
			<input type="hidden" id="importUrl"/>
		</div>
	</div>
</div>
<!-- 数据导入 -END -->

<!-- 窗口右上角关闭图标隐藏 -->
<style type="text/css">
.panel-tool .panel-tool-close{
 	display: none;
 }
</style>
<script language="javascript" type="text/javascript">
$(function(){
	var upload_tipHtml = '1、请使用提供的模板存储数据，用于导入<br/>';
		upload_tipHtml += '2、文件大小必须小于15Mb<br/>';
		upload_tipHtml += '3、文件里日期格式必须是yyyy-MM-dd格式：如2016-07-01<br/>';

	$("#upload_tip").html(upload_tipHtml);
	clearFileForm();
	
	$("#uploadFile").unbind('click').click(function(){
		$(this).hide();//上传按钮只允许点一次
		var uploadUrl = $("#importUrl").val();
		uploadFile(uploadUrl);
	});
});

function closeImportWin(){
	clearFileForm();
	windowCommClose('Import_comm_window');
}

function clearFileForm() {
	var file = $("#fileToUpload") ;
	file.after(file.clone().val(""));  
	file.remove();  
	$("#showInfo").hide(); 
	$("#uploadFile").hide();
}

//导入
var import_Flag = true; //可执行导入标识
function uploadFile(url){
	if(import_Flag){
	   import_Flag = false;
		
	   $('#FormUpload').form('submit',{
			url:ctx+"/"+url,
			success:function(ret){
				import_Flag = true;
				var ret=JSON.parse(ret);
				var s=ret.data;//list中数据
				//消息框只显示10条错误消息,防止消息框自动拉伸
				var d="<br/><br/><br/>";
				for(var i=0;i<s.length;i++){
					if(i<=10){
						d +=s[i]+"<br/>";
					}else if(i>10&&i<=12){
						d +="......<br/>";
					}
				}
				 if(ret&&ret.data){
					$.messager.alert("操作提示",d,"info",function(){
						$("#Import_comm_window").window('close');
					}); 
					queryListPage(1);
					
					//判断是否需要分组
					if($("#group_form").length > 0){
						basegroupTreeInit();
						basegroupTreeSelect();
						comboboxInit();
					}
				}else{
					$.messager.alert("操作提示",d,"error",function(){
						$("#Import_comm_window").window('close');
					});
				}
				 clearFileForm();	
			},
			error : function(){
				import_Flag = true;
			}
		}); 
	}
}

function fileSelected(){
      var file = document.getElementById('fileToUpload').files[0];
      var fileName = file.name;
      var fileSize=file.size/1024;
      if(fileSize<1024){
    	  $("#fileInfo").html(fileName+"	---"+fileSize+"KB");
       }else{
     	  $("#fileInfo").html(fileName+"	---"+fileSize/1024+"MB");
       }
      var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
  
      if (file_typename == '.xls'  || file_typename == '.xlsx') {	
          if (file) {
            $("#uploadFile").show();
          	$("#showInfo").show(); 
          }
      } else {
    	  $("#uploadFile").hide();
    	  $.messager.alert('系统提示:','请导入正确格式的数据模版文件','info');
      }
  }
</script>