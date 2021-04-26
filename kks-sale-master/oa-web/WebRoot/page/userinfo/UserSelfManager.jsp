<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <jsp:include page="/page/comm.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script type="text/javascript" src="${ctx}/res/js/userselfmanager.js"></script>
   <script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 

  </head>
  
  <body style="margin:0">
 <div class="easyui-layout" style="overflow: hidden; width:100%;height:100%"  scroll="no"   fit="true">
   <div region="center" id="dic-center"  style="overflow:hidden" title="员工信息">
   <div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;overflow:hidden">
				<table>
				     <tr>
				     <td>员工账号：</td>
				     <td>
				     <input type="text" id="uId" style="width:150px;" disabled="disabled"></td>
				     </tr>
				    <tr>
				     <td>员工名称：</td>
				     <td>
				     <input type="text" id="uName"style="width:150px;"></td>
				     </tr>
				     <tr>
                     <td>手机：</td>
				     <td><input  
                     id="uTelphone" style="width:150px;" ></td>
				     </tr>
				    <tr>
				     <td>邮箱：</td>
				     <td><input id="uMail" style="width:150px;" ></td>
				     </tr>
				     <tr>
				     <td>邮箱密码：</td>
				     <td><input type="password" id="uMailPwd" style="width:150px;" ></td>
				     </tr>
				     <tr>
				     <td>企业QQ：</td>
				     <td><input  type="text"    id="uEnterpriseQQ" style="width:150px;" ></td>
				     </tr>
				     <tr>
                     <td>QQ：</td>
				     <td><input  type="text" id="uQQ" style="width:150px;" ></td>
				     </tr>
				     <tr>
				     <td>年龄：</td>
				     <td><input  type="text" id="uAge" style="width:150px;" ></td>
				     </tr>
				      <tr>
                     <td></td><td><a id="UISave" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="UISave()">保存</a>
		            </td></tr> 
				</table>
			</div>
		</div>
   </div>
   	
   </div>
   	
  </body>
</html>