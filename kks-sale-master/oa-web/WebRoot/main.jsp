<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>几米终端客户服务系统</title>
<jsp:include page="/page/common/page.jsp"/>
<jsp:include page="/page/comm.jsp"/>
<%
	String getChildCallBackURL=(String)session.getAttribute("childCallBackURL");
	request.setAttribute("getChildCallBackURL",getChildCallBackURL);
%>
    <link href="${ctx }/res/css/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src='${ctx }/res/js/outlook2.js?20200619'> </script>
    <script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>

	<script type="text/javascript" src="${ctx}/res/js/workflow/accept/customer.js?20171012"></script>

		var n=0;
		$(window).resize(function() {
		    if(n%2==0){
		   		window.setTimeout("resetMainPage()",500);
	    	}
	    	n++;
		});

	</script>
        
    <script type="text/javascript">
		var getChildCallBackURL="${getChildCallBackURL}";
		var getUserId="${USERSESSION.uId}";
		var getUserName="${USERSESSION.uName}";
		window.onload=function(){ 
			  //var a = document.getElementById("loading"); 
			  var b = document.getElementById("backloading"); 
			  //a.parentNode.removeChild(a); 
			  b.parentNode.removeChild(b);  
	    }; 
	</script>
    
    <script type="text/javascript">
        $.messager.defaults= { ok: "确定",cancel: "取消" };

        //设置登录窗口
         function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 220,
                resizable:false
            });
        } 
        //关闭登录窗口
        function close() {
            $('#w').window('close');
        }

        //修改密码
        function serverLogin() {
        	var oldpass = $('#txtOldPass').val();
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

			if (oldpass == '') {
               	msgShowCallBack('系统提示', '请输入原密码！', 'warning',0);
                return false;
            }
            
            if ($newpass.val() == '') {
                msgShowCallBack('系统提示', '请输入密码！', 'warning',0);
                return false;
            }
            if ($rePass.val() == '') {
                msgShowCallBack('系统提示', '请输入确认密码！', 'warning',0);
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShowCallBack('系统提示', '两次密码不一致！请重新输入！', 'warning',0);
                return false;
            }
            
            //用户合法性验证  
       		$.post('${ctx }/oaEditPassword?oldpass='+oldpass+'&newpass=' + $newpass.val(), function(data) {
 				//var data=eval("("+data+")");
				if(data.code=='-13'){
            			msgShowCallBack('系统提示', '原密码不正确！请重新输入', 'warning',0);
            	}else if(data.code=='-6'){
            			alert(data.msg);
            	}else {
	            		msgShowCallBackByConfirm('系统提示', '恭喜，密码修改成功！<br>点击OK按钮后系统会立即跳转到登录页。您的新密码为：' + data.code,data.msg);
	                	$newpass.val('');
	                	$rePass.val('');
	                	close();
            	}
						            	
		    });
        }

        function init() {

           openPwd();
 
            $('#editpass').click(function() {
                $('#w').window('open');
                $('#txtOldPass').focus();
            });

            $('#btnEp').click(function() {
                serverLogin();
            });

            $('.l-btn-text').click(function(){
            	$('#w').window('close',true);
            });

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                    if (r) {
                        	$.ajax({
								type: "POST",
							  	cache: false,
							  	url: "${ctx }/oaLogout",
							  	dataType: "json",
							  	success : function(returnData){
							  		if(returnData.code=='-1'){
							  			location.href=returnData.msg;
							  		}else {//退出调用SSO异常
							  			alert(returnData.msg);
							  		}
								}, 
								error : function(XMLHttpRequest, textStatus, errorThrown){
									alert("错误:"+errorThrown);
							 	}
							});
                    }
                });

            });
        } 

//document.write('<div id="loading" style="background:#CC4444;color:#FFF;padding-left:205px;position:absolute;line-height:22px;z-index:30;margin:0 auto;width:900px;">加载中稍后...</div>');
document.write('<div id="backloading" style="width:100%;height:100%;position:absolute;z-index:20;border:0px;background-color:#fff;"align="center"></div>'); 

        

    </script>

</head>
<body class="easyui-layout" style="overflow: hidden"  scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>
    <div id ="north" region="north" split="true" border="false" iconCls="true" style="overflow: hidden;  background: url(${ctx}/res/image/title.jpg) no-repeat center center #0051A2; font-family: Verdana, 微软雅黑,黑体;height:60px;">
        <div class="logo" style="float:left;height: 60px;margin-top: 10px;"><img src="${ctx}/res/image/concox-logo.png" alt="康凯斯" /></div>
       	<div style="float:right;height: 60px;width: 60px;"></div>
        <div style="float:right;height: 60px; text-align:right; padding-right:20px; width: 400px;line-height: 60px;" class="head">
		        <div style="height:40px;padding: 0px;">
		         <span style="color:#fff;">欢迎：<b id="uNameShowId">${USERSESSION.uName}</b>&nbsp;&nbsp;&nbsp;</span>
		         <a href="${ctx}/res/file/SALE-HELP.docx" id="help">使用帮助</a>&nbsp;&nbsp;&nbsp;
<!-- 		         <a href="#" id="editpass">修改密码</a>&nbsp;&nbsp;&nbsp; -->
		         <a href="#" id="loginOut">安全退出</a>
		        </div>
<!-- 		       <div style="height:25px;"> -->
<!-- 		       	<table style="float: right;" border="0"> -->
<!-- 		       		<tr> -->
<%-- 		       			<td><img style="width:16px;height:16px;" src="${ctx}/res/image/mail.png"/></td> --%>
<!-- 		       			<td><a id="mailCount" href="" target="_blank" style="text-decoration:none;">您有0封邮件</a></td> -->
<!-- 		       		</tr> -->
<!-- 		       	</table> -->
<!-- 		       </div> -->
        </div>
        <div style="margin:auto;position: relative;width: 400px;top: 6px; font-size: 30px;padding-left: 4%;"><span style="font-size:30px;color: white;">几米终端客户服务系统</span></div>
    </div>
     
     
<!--     <div region="south" split="true" iconCls="true" style="height:30px; background: #D2E0F2; overflow:hidden"> -->
<!--         <div style="text-align: center;color: #15428B;margin: 0px;padding: 0px;line-height: 23px;font-weight: bold;" >   -->
<!--         	深圳市几米软件有限公司版权所有  ©jimisoft KKS After-SSM 2016 -->
<!--         </div> -->
<!--     </div> -->
    <div region="west" split="true" title="导航菜单" iconCls="true" style="width:200px;overflow:hidden" id="west">
            <div class="easyui-accordion" fit="true" border="false" id="wnav">
              <!--  导航内容 -->
            </div>
    </div>
    <div id="mainPanle" region="center" iconCls="true" style="background:white;">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" style="" >
         <%--
            <div title="首页" id="home">
           		<div style="padding:20px; text-align:center;">
	            	<h1 style="font-size: 24px">作业指导图</h1>
	                <img src="${ctx }/res/image/KKS-SALE.png" alt="" />
            	</div>            	
             </div>
             --%>
            	  <div region="center" id="dic-center" style="overflow: auto" title="首页">
					<div id="toolbar" style=" padding: 10px 15px;">
						<div style="margin-bottom:5px">	
							<span>
								查询方式:&nbsp;
								<select id="fashion" class="easyui-combobox" style="width: 110px;" editable="false" panelHeight="auto">
									<option value="" selected="selected">全部</option>
									<option value="0">送修单位</option>
									<option value="1">送修批号</option>
									<option value="2">手机号</option>
									<option value="3">IMEI</option>
									<option value="4">无名件</option>
									<option value="5">最终故障</option>
									<option value="6">智能锁ID</option>
									<option value="7">快递单号</option>
									<option value="8">主板型号</option>
								</select>&nbsp;
							</span>		
							<span>&nbsp;关&nbsp;键&nbsp;字:&nbsp;
							    <input type="text" class="form-search" id="crucial" style="width: 135px" disabled="disabled"/>
							    <textarea class="form-search" style="visibility: hidden;overflow: hidden;position: absolute;z-index:100;left: 251px;resize: none; width: 135px;height: 14px;" id="crucial_imei" onfocus="clickFocus()" onblur="clickBlur()"></textarea> 
							</span>&nbsp;	
							<span>
								受理开始日期:&nbsp;
								<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"/> 
							</span> &nbsp;
							<span>
								受理结束日期:&nbsp; 
								<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"/> 
							</span> &nbsp;
							<span>&nbsp; &nbsp;
							 	<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
							</span>
						</div>
						<div>
							<table cellspacing="0" cellpadding="0">
								<tbody>
								<tr>
									 <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pcckicon" plain="true" onclick="showAllNotPackInfo();">查看未发货批次</a></td>
									 <td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>
									 <td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="exportInfo();">导出</a></td>
									 <td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="fixOutDate();">同步出货日期</a></td>
									<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="selectCustomer();">修改客户</a></td>
								</tr>
							 	</tbody>
							</table>
							
						</div>
					</div>
					<table id="DataGrid1" singleSelect="true" rownumbers="false" autoRowHeight="true" pagination="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
						<thead>
							<tr>
								<th checkbox="true" field="w_packId"></th>						
								<th field="cusName" width="150" align="center">客户名称</th>
								<th field="w_phone" width="80" align="center">手机号</th>
								<th field="repairnNmber" width="100" align="center">送修批号</th>
								<th field="imei" width="110" align="center">IMEI</th>
								<th field="w_station"  width="60"  align="center">工站</th>
								<th field="state"  width="90"  align="center">状态</th>
								<th field="w_marketModel" width="100" align="center">市场型号</th>
								<th field="w_model" width="70" align="center">主板型号</th>
								<th field="w_engineer" width="60" align="center">维修员</th>
								<th field="w_repairRemark" width="150" align="center">维修备注</th>
								<th field="isWarranty" width="60" align="center">保内|保外</th>
								<th field="w_isRW" width="50" align="center">人为</th>
								<th field="acceptanceTime" width="120" align="center">受理时间</th>
								<th field="backTime" width="135" align="center">预计出货时间</th>
								<th field="salesTime" width="120" align="center">出货日期</th>
								<th field="sfVersion" width="180" align="center">软件版本号</th>
								<th field="lockId" width="80" align="center">智能锁ID</th>
								<th field="insideSoftModel" width="80" align="center">内部机型</th>
								<th field="remark" width="150" align="center">送修备注</th>
								<th field="w_cjgzDesc" width="150" align="center">初检故障</th>
								<th field="w_zzgzDesc" width="200" align="center">最终故障</th>
								<th field="w_solution" width="150" align="center">处理方法</th>
								<th field="w_expressNO" width="120" align="center">快递单号</th>
								<th field="w_expressCompany" width="80" align="center">快递公司</th>
								<th field="returnTimes" width="60" align="center">返修次数</th>
								<th field="acceptRemark" width="150" align="center">受理备注</th>	
								<th field="accepter" width="60" align="center">受理人</th>
								<th field="payDelivery" width="80" align="center">客户寄货方式</th>
								<th field="w_linkman" width="80" align="center">联系人</th>
								<th field="w_address" width="150" align="center">联系地址</th>
								<th field="w_email" width="150" align="center">邮箱</th>
								<th field="w_repairMoney" width="90" align="center">维修金额（￥）</th>
								<th field="w_wxbjDesc" width="200" align="center">维修报价描述</th>
								<th field="w_modelType" width="80" align="center">主板类型</th> 
								<th field="lastEngineer" width="80" align="center">上次维修人员</th> 
								<th field="lastAccTime" width="80" align="center">上次受理时间</th>
								<th field="payedLogCost" width="100" align="center">支付物流费</th>
								<th field="w_matDesc" width="150" align="center">配件料</th>
								<th field="w_dzlDesc" width="150" align="center">电子料</th>
								<th field="w_repairAgainCount" width="100" align="center">内部二次维修次数</th>
								<th field="customerStatus" width="60" align="center">无名件</th>
								<th field="bluetoothId" width="90" align="center">蓝牙ID</th>
								<th field="repairNumberRemark" width="120" align="center">批次备注</th>							
								<th field="price_createTime" width="130" align="center">发送报价时间</th>
								<th field="w_payTime" width="130" align="center">付款时间</th>
								<th field="testPerson" width="60" align="center">测试员</th>
								<th field="w_sjfjDesc" width="150" align="center">随机附件</th>
								<th field="w_ispass" width="60" align="center">终检结果</th>	
								<th field="sendPackTime" width="120" align="center">发送装箱时间</th>																							
								<th field="w_packTime" width="120" align="center">装箱时间</th>
								<th field="bill" width="130" align="center">订单号</th>	
								<th field="outCount" width="60" align="center">出货总数量</th>	
								<th field="testMatStatus" width="60" align="center">使用试流料</th>	
								<th field="w_priceRemark" width="150" align="center">报价说明</th>
								<th field="w_isPrice" width="80" align="center">放弃报价</th>
								<th field="w_giveUpRepairStatus" width="110" align="center">客户放弃维修状态</th>
								<th field="w_offer" width="60" align="center">报价人</th>
								<th field="w_onePriceRemark" width="150" align="center">单台报价备注</th>
								<th field="w_customerReceipt" width="100" align="center">客户收货方式</th>
								<th field="w_price_Remark" width="150" align="center">批次报价备注</th>
								<th field="w_payReason" width="120" align="center">批次报价原因</th>
								<th field="w_remAccount" width="80" align="center">付款方式</th>
								<th field="w_isPay" width="60" align="center">是否付款</th>
								<th field="w_FinDesc" width="150" align="center">终检备注</th>
								<th field="w_FinChecker" width="60" align="center">终检人</th>
								<th field="w_packingNO" width="150" align="center">装箱单号</th>
								<th field="w_packer" width="60" align="center">装箱人</th>
								<th field="w_shipper" width="150" align="center">发货方</th>
								<th field="w_packRemark" width="150" align="center">装箱备注</th>
								<th field="w_cusRemark" width="150" align="center">送修单位备注</th>
								<th field="dataSource" width="100" align="center">测试数据来源站</th>
								<th field="sendTime" width="130" align="center">发送测试时间</th>
								<th field="testResult" width="150" align="center">测试结果</th>
								<th field="testTime" width="130" align="center">测试时间</th>
							</tr>
						</thead>
					</table>
				</div>
        </div>
    </div>
    
     <!-- 查看未发货批次 start -->
     <div id="showAllNotPackInfo" class="easyui-window" title="查看未发货批次" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
				 style="width: 550px; height: 520px; visibility: hidden;">
				<div class="easyui-layout" fit="true">
				   <div region="center" id="dic-center" style="overflow:auto;">
				   <dir style="height: 20px; text-align: right; padding: 0px 10px 0px;margin: 0px;">
				   		<font color="red;" style="font-size: 12px; font-weight: bold;">双击查看批次设备详细信息</font>
				   </dir>
				    <div style="height: 425px;overflow:auto;overflow-x:hidden;">
						<table id="allNotPackInfo_DataGrid" class="easyui-DataGrid" singleSelect="true" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" fitColumns="true">
							<thead>
								<tr>
									<th field="w_cusName" width="170" align="center">送修单位</th>
									<th field="w_phone" width="100" align="center">手机号</th>
									<th field="repairnNmber" width="150" align="center">送修批号</th>
									<th field="acceptanceTime" width="150" align="center">受理日期</th>
								</tr>
							</thead>
						</table>
			 	    </div>
			    </div>
		 	    <div region="south" border="false" style="text-align:right; height:35px; line-height:30px; margin: 3px -5px 20px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('showAllNotPackInfo')">关闭</a>
			   </div>
			</div>
		</div>
	 <!-- 查看未发货批次 end -->

	<!-- 选择客户信息 -START-->
	<div id="wkh" class="easyui-window" title="选择客户信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
		 style="width:800px;height:455px; visibility: hidden; ">
		<div class="easyui-layout" fit="true">
			<div region='center' >
				<div id="cusToolbar" style=" padding: 10px 15px;">
					<div style="margin-bottom:5px">
						<span>&nbsp;客户名称:&nbsp;<input type="text" class="form-search1" id="cusName" style="width:135px" /></span>&nbsp;
						<span>&nbsp;联系人:&nbsp;<input type="text" class="form-search1" id="linkman" style="width:135px" /></span>&nbsp;
						<span>&nbsp;手机号:&nbsp;<input type="text" class="form-search1" id="phone" style="width:135px" /></span>&nbsp;
						<span>&nbsp; &nbsp;
							<a id="cusSerchInfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageSXDW(1);">查询</a>
						</span>
					</div>
				</div>

				<table id="DataGrid-Customer" class="easyui-DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fitColumns="true" autoRowHeight="true"
					   striped="true" scrollbarSize="0" toolbar="#cusToolbar">
					<thead>
					<tr>
						<th field="cId" hidden="true"></th>
						<th field="cusName" width="110"   align="center">客户名称</th>
						<th field="linkman" width="110"  align="center">联系人</th>
						<th field="phone" width="110"  align="center">手机号</th>
						<th field="telephone" width="110"  align="center">座机号</th>
						<th field="address" width="300" align="center">通信地址</th>
					</tr>
					</thead>
				</table>
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;">
				<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="modifyCustomerByQuery()">保存</a>
				<a id="treeDelete1" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wkh')">关闭</a>
			</div>
		</div>
	</div>
	<!-- 选择客户信息 -END-->
    
    <!--修改密码窗口-->
     <div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 350px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                	 <tr>
                        <td>原密码：</td>
                        <td><input id="txtOldPass" type="password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"
                        onclick="javascript:void(0)">取消</a>
            </div>
        </div>
    </div> 
    
    <div id="noticeDetailsId" class="easyui-window" title="公告详细" modal="true" closed="true" style="width:720px;height:590px;padding:5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<table>
					    <tr >
							<th align="left">公告主题：</th>
							<th align="left" id="noticeTitleP"></th>
						</tr>

						<tr >
							<th align="left">公告描述：</th>
							<th align="left" ><textarea id="noticeDescP" style="width:550px;color:#333300;font-weight:bold" rows="25"></textarea></th>
						</tr>
						
						 <tr >
							<th align="left">发布时间：</th>
							<th align="left" id="creatTimeP"></th>
						</tr>

						 <tr >
							<th align="left">发布人：</th>
							<th align="left" id="createrNameP"></th>
						</tr>

					</table>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseNoticeDetails()">关闭</a>
			    </div>
			</div>
	     </div>



    <div id="mm" class="easyui-menu" style="width:150px;">
        <div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">全部关闭</div>
        <div id="mm-tabcloseother">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-exit">退出</div>
    </div>
    

 
</body>
</html>
