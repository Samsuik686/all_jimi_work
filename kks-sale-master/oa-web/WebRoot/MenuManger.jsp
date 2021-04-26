<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/page/comm.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <link href="${ctx }/res/css/table.css" rel="stylesheet" type="text/css" />
    <link href="${ctx }/res/css/layout.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${ctx }/res/js/menuManager.js"></script>    
    <script type="text/javascript">

    var _displayNa;
    var _menus;
    var  _menusAgain;
    $.messager.defaults = { ok: "确定", cancel: "取消" };
    function MenuManagerload(){
        $.ajax({
           type:"POST",
           async:false,
           cache:false,
           url:"system/MenuManagerload",
           dataType:"json",
           success:function(returnData){
             _menus=returnData;
            
           },
           error : function(){
    			$.messager.alert("操作提示","网络错误","info");
    	 	}
        });
        
    }
    </script>
  </head>

<body style="margin:0px;">
<div class="easyui-layout" style="overflow: hidden;width:100%;height:100%;" scroll="no"   fit="true">
    <div class="easyui-layout" region="west" split="false" title="菜单树" style="width: 400px;overflow:hidden;" id="west">
    <div style="text-align:right;overflow:hidden;" region="north" split="false"> 
    <div style="padding:10px 0; text-align:center;">
    <a class="easyui-linkbutton" href="javascript:AddbuttonShow()" icon="icon-mlzjicon">目录增加</a>
    </div>
    </div>
       <div region="west" split="false" title="主菜单目录" style="width: 180px;" id="west1" >
	       <div class="easyui-accordion" fit="true" border="false" id="ea1">
              <!--  导航内容 -->
            </div>
        </div>
        <div  region="center"split="false" title="子菜单目录" style="width: 220px;" id="west2">
            <div class="easyui-accordion" fit="true" border="false" id="ea2">
	        </div>
	       
        </div>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px; background: #fff; overflow-y: hidden;">
        <div class="easyui-panel" title="菜单详情" fit="true" >
          
        <table width="100%" style="border-collapse:collapse; border-spacing:0; border-color:none;">	
           <tr>
             <td width="15%" style="text-align:center;">菜单名称：</td>
             <td width="80%" style="padding:5px 15px;">
             <input type="hidden" id="menuID" name="menuID" value="">
             <input type="text" id="displayName" name="displayName" value="" style="width: 600px; height:30px"></td>
           </tr>
           <tr>
             <td style="text-align:center;">父级菜单名称：</td>
             <td style="padding:5px 15px;"><select  id="parentID" name="parentID"  style="width: 600px; height:30px" ></select></td>
           </tr>
           <tr>
             <td style="text-align:center;">URL：</td>
             <td style="padding:5px 15px;"><input type="text" id="url" name="url" value="" style="width: 600px; height:30px"></td>
           </tr> 
           <tr>
             <td style="text-align:center;">SN：</td>
             <td style="padding:5px 15px;"><input type="text" id="SN"  name="SN" value=""  style="width: 600px; height:30px"></td>
           </tr>
           <tr>
             <td style="text-align:center;">序号：</td>
             <td style="padding:5px 15px;"><input type="text" id="orderindex" maxlength="6" onkeyup="value=value.replace(/[^\d]/g,'')" 
      onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
      name="orderindex" value="" style="width: 600px; height:30px"></td>
           </tr>
           <tr>
             <td style="text-align:center;">详细描述：</td>
             <td style="padding:5px 15px;">
             
             <textarea class="easyui-textarea" type="text" id="descrp" name="descrp" value="" style="width: 600px;height:100px; "></textarea></td>
           </tr>

           <tr>
             <td></td>
             <td>
               	 <a class="easyui-linkbutton"  href="javascript:void(0)" onclick="add()"  icon="icon-addicon" id="add">增加</a>
	             <a  class="easyui-linkbutton" href="javascript:MenuUpdate()" icon="icon-xgicon" id="update">修改</a>
	             <a class="easyui-linkbutton" href="javascript:Menudelete()" icon="icon-delecticon" id="delete">删除</a>
             </td>
           </tr>
           
           <tr>
             <td style="text-align:center;">菜单权限URL：</td>
             <td  style="padding-top:15px;">
				 权限名称： <input type="text" id="functionDesc"  name="InsertUrl" value=""  style="width: 100px; height:30px">
			             权限URL： <input type="text" id="functionURL"  name="InsertUrl" value=""  style="width: 300px; height:30px">
			            <input type="hidden"  id="functionId">      
			     <a class="easyui-linkbutton"  href="javascript:void(0)" onclick="AddInsertUrl()"  icon="icon-addicon" id="AddInsertUrl">增加</a>
	          <div style="height:200px; padding-top:15px;">
	            <table id="DataGrid1" singleSelect="true" sortable="true"  fit="true"
		 	           idField="menuId" rownumbers="false" striped="true" fitColumns="true">
					<thead>
						<tr>
						    <th field="menuId" width="80" hidden="true"></th>
							<th field="functionId" width="80"  hidden="true"></th>
							<th field="functionDesc" width="80" align="center" style="padding:0;">权限名称</th>
							<th field="functionURL" width="80" align="center" >权限URL</th>
						</tr>
					</thead>
				</table>
	         </div>
			</td>
           </tr>
         </table>
         </div>
        </div>
    </div>
</div>

</body>
</html>
