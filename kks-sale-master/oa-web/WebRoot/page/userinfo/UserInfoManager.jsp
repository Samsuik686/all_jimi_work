<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <jsp:include page="/page/comm.jsp"/>
  <jsp:include page="/page/UserManagerOrganPerson.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <style type="text/css">  
.hover2
{
  background-color: #cccc00;
}
</style>

     <script type="text/javascript" src="${ctx}/res/js/usermanager.js"></script>
   <script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript">
            function limitInput(o){
                var value=o.value;
                var min=0; 
                var max=10000;
                if(parseInt(value)<min||parseInt(value)>max){
                    $.messager.alert("操作提示","输入的数字必须为0~10000之间。","error");
                    o.value='';
                }
            }
        </script>
        
        <script type="text/javascript" src='${ctx }/res/script/ajaxLoading.js'></script><!-- add by wg.he 2013/12/11 进度条加载 -->
        
  	<link rel="stylesheet" type="text/css" href="${ctx}/res/css/user-table.css" />    
  </head>
  
  <body style="margin:0">
 <div class="easyui-layout" style="overflow: hidden; width:100%;height:100%"  scroll="no"   fit="true">
    <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="人员列表" >
	       <ul id="tt2" class="easyui-tree" animate="false" dnd="false">
	       </ul>
      
   </div>
   <div region="center" id="dic-center"  style="overflow:auto;" title="员工信息">
	   <div style="background-color:#E0ECFF;padding:0 0 0 10; width:1190px;">
		          部门：<select id="oidSelect" style="width:100px"></select>
		           &nbsp;    &nbsp;    &nbsp; 
			          工号：<input type="text" style="width:100px" id="cIdSelect">
		               &nbsp;    &nbsp;    &nbsp; 
			          员工名称：<input type="text" style="width:200px" id="searchUname">
			        &nbsp;    &nbsp;    &nbsp; 员工状态： <select type="text" id="SelectStatus" style="width:150px;" >
			             <option value="">全部</option>
						     <option value="0">正常</option>
						     <option value="1">离职</option>
						     <option value="2">已删除</option>
					</select>
				   &nbsp;    &nbsp;    &nbsp; 
	   		<a id="searchinfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="searchinfo()">查询</a>
		</div>
	    <table id="DataGrid1" singleSelect="true" sortable="true"  
			 	idField="uId" rownumbers="false" striped="true" fitColumns="true" style="width:1200px;height:30px;">
			 	 	 <input type="hidden"  id="treeOrgId" style="width:200px">  
			 	 	<thead>
						<tr>
							<th field="uId">员工账号</th> 
						</tr>
					</thead>
		</table>
		
		<table id="mytable"   cellspacing="0" style="width: 1200px;">
			<tr>
				
				<th width="40px">序号</th>
				<th width="40px">选择</th>
				<th width="60px">工号</th>
				<th width="70px">姓名</th>
				<th width="100px">账号</th>
				<th width="120px">主部门</th>
				<th width="160px">手机号码</th>
				<th width="150px">固定电话</th>
				<th width="150px">职务</th>
				<th width="180px">入职时间</th>
				<th width="180px">生日</th>
			</tr>
	    </table>
	
	
	
   </div>
	<form id="mform" action="">
   	<div id="w" class="easyui-window" title="员工详细信息窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
   		  style="width:1050px;height:500px;padding:5px;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;overflow:hidden">
				<div style="font-weight: bold;">员工账号：<span id="uId" style="color:#808080;"></span></div>
				<div><hr /></div>
				<table style="margin-left: 30px;">
				    <tr>
				    <td>工号：</td>
				     <td><input type="text" id="cId" style="width:150px;" maxlength="8" >
				     	<input type="hidden" id="oldCId" style="width:150px;" >
				     </td>
				     <td>员工姓名：</td>
				     <td><input type="text" id="uName"style="width:150px;" maxlength="15" ></td>
				     <td>主部门：</td>
				     <td>
				     <input type="text" id="oId" style="display:none">
				     <input type="text" id="oName" class="easyui-combotree" required="true" style="width:150px;"></td>
				     <td>副部门：</td>
				     <td><input type="text" id="omanagerName" class="easyui-combotree" multiple="true" cascadeCheck="false" style="width:150px;"></td>
				     
				    
				     </tr>
				     
				     <tr>
				     
				      <td>固定电话：</td>
				     <td><input  
                     id="uPhone" style="width:150px;" maxlength="25" ></td>
                     <td>手机号码：</td>
				     <td><input   
                     id="uTelphone" style="width:150px;" maxlength="25" ></td>
                     
				     <td>直接主管Id：</td>
				     <td><input type="text"  id="uManagerId" readonly="readonly" style="width:150px;" maxlength="25" onclick="ManagerWindowOnOpen(this)"/></td>
				     <td>主管姓名：</td>
				     <td><input type="text" id="uManagerName" readonly="readonly" style="width:150px;" maxlength="25" onclick="ManagerWindowOnOpen(this)" ></td>
				     </tr>   
				     <tr>       
				     
				     <td>邮箱：</td>
				     <td><input id="uMail" style="width:150px;" maxlength="40"></td>
				     <td>邮箱密码：</td>
				     <td><input type="password" id="uMailPwd" style="width:150px;" maxlength="25"></td>
                     <td>毕业院校：</td>
				     <td><input  type="text"  id="uGraduated" style="width:150px;" maxlength="25"></td>
				      <td>所学专业名称：</td>
				     <td><input  type="text" id="uProfessionalName" style="width:150px;" maxlength="25"></td>
				     </tr> 
				    
				     <tr>
				     </td>
				     <td>职务：</td>
				     <td><input type="text" id="uPosition" style="width:150px;" maxlength="25"></td>
				     
				     </div>
				     
				     <td>职称：</td>
				     <td><input  type="text" 
                     id="uGradeName" style="width:150px" maxlength="25"></td>
                     
                      <td>状态：</td>
				     <td><select type="text" id="uStatus"  onclick="showJumpDate()" style="width:150px;" >
				     <option value="0">正常</option>
				     <option value="1">离职</option>
				     <option value="2">已删除</option>  
				     </select>
				     <td ><span id="s1" style="display:none;">离职日期：</span></td>    
				     <td >
				     <span  id="s2" style="display:none;">
				     <input type="text"  id="uJumpDate" class="easyui-datebox" style="width:150px;" ></td>
				     </span>
				     </tr>
				      
				     <tr>
				
                     
                   <td>性别：</td>                   
				   <td> <select type="text" id="uSex" style="width:150px;" >
				     <option value="1">男</option>
				     <option value="0">女</option>
				     <option value="2">未知</option>  
				     
				     </select></td>  
				     <td>入职时间：</td>
				     <td><input  type="text" class="easyui-datebox"
                     id="uJoinDateTime" style="width:150px;" ></td>
                     <td> 司龄：</td>
				     <td><input type="text"  id="uInDateTime" style="width:150px;" onkeyup="value=value.replace(/[^\d]/g,'');limitInput(this) " 
                     onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" disabled="disabled"></td>
				     <td>转正时间：</td>
				     <td><input  type="text" class="easyui-datebox"
                     id="uPositiveDateTime" style="width:150px;" ></td>
				     </tr>
				     
				     <tr>
                     <td>民族：</td>
				     <td><input  type="text" 
                     id="uEthnic" style="width:150px;" maxlength="25"></td>
                     <td>出生日期：</td>
				     <td><input type="text" class="easyui-datebox"
                     id="uBirthday" style="width:150px;" ></td>  
				     <td>年龄：</td>
				     <td><input  type="text" id="uAge" style="width:150px;" maxlength="10" disabled="disabled"></td>
                     <td>学历：</td>
				     <td><input  type="text"  id="uEducationalBackground" style="width:150px;" maxlength="25"></td>
				     </tr>
				      
				      <tr>
				    
				      <td>身份证号码：</td>
				     <td><input  type="text" 
                     id="uIdCard" style="width:150px;" maxlength="18"></td>
                     <td>合同起始日期：</td>
				     <td><input  type="text"  id="uContractStart" class="easyui-datebox" style="width:150px;" ></td>
				     <td>合同终止日期：</td>
				     <td><input type="text"  id="uContractEnd" class="easyui-datebox" style="width:150px;" ></td>
				     <td>婚姻状况：</td>
				     <td><input  type="text"  id="uMaritalStatus" 
                    style="width:150px;" maxlength="10"></td>
				     </tr>
				     
				     
				     <tr>
				     <td>籍贯：</td>
				     <td><input type="text" 
                     id="uHometown" style="width:150px;" maxlength="50"></td>
                     <td>家庭住址：</td>
				     <td><input  type="text" 
                     id="uHomeAddress" style="width:150px;" maxlength="50"></td>
                     <td>紧急联系电话：</td>
				     <td><input  
                     id="uEmergencyTelephone" style="width:150px;" maxlength="25"></td>
                     <td>紧急联系人：</td>
				     <td><input type="text"  id="uEmergencyRelations" style="width:150px;" maxlength="25"></td>
				     </tr>
				     
				      
				     
				     <tr>
				     <td>社保卡号：</td>
				     <td><input type="text"  id="uSocialSecurityNumber" style="width:150px;" maxlength="50"></td>
				     <td>银行账号：</td>
				     <td><input type="text" id="uBankAccount" style="width:150px;" maxlength="50"></td>
				     
                     <td>公积金账号：</td>
				     <td><input  type="text" id="uProvidentFundAccount" style="width:150px;" maxlength="50" ></td>
				     
                     <td>考勤账号：</td>
				     <td><input  type="text" id="workTimeId" style="width:150px;" onkeyup="value=value.replace(/[^\d]/g,'');limitInput(this) " 
                     onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"></td>
				     </tr>
				
				     <tr>
				     <td>企业QQ：</td>
				     <td><input  type="text"    id="uEnterpriseQQ" style="width:150px;" maxlength="50"></td>
                     <td>个人QQ：</td>
				     <td><input  type="text" id="uQQ" style="width:150px;" maxlength="50"></td>
				    
				      <td>办公区域：</td>
				     <td><input type="text" 
                     id="uOfficeArea" style="width:150px;" maxlength="25"></td>
				     </tr> 
				     <tr>
				      <td>备注：</td>
				     <td colspan="7"><textarea type="text"  id="uUserDesc" style="width:100%;height: 80px;" maxlength="250"></textarea></td>
				     </tr>
				</table>   
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<label style="color:red">*副部门为员工属于两个不同部门时填写，可为空。</label>
			<a id="UserUpdate" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="UserUpdate()">确认修改</a>
			<a id="UserInsert" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="UserInsert()">保存</a>
			<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowClose()">关闭</a>
		</div>
		</div>
		
	</div>
	</form>
   </div>
   	
  </body>
</html>