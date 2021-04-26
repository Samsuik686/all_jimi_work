<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script type="text/javascript" src="${ctx}/res/js/workflow/repair/tree.js"></script>
<%--    <script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/worktimeTree.js?20170831"></script>--%>
    <script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/workflow/eqpNotSendInfo.js"></script>


    <style type="text/css">

        /* 扫描IMEI */
        strong{
            font-family:"微软雅黑";
            font-size: 16px;
            color:#0066FF;
        }
    </style>
</head>
<body style="margin: 0">
<input type="hidden" id="gIds"/>
<input type="hidden" id="tree-Date"/>
<input type="hidden" id="tree-State"/>
<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
<div class="easyui-layout"	style="overflow: hidden; auto; width: 100%; height: 100%" fit="true">
    <div region="west" id="dic-west" style="width: 150px;" title="选择日期">
        <ul id="typeTreeTime" class="easyui-tree">
        </ul>
    </div>
    <div region="center" id="dic-center" style="overflow: auto" title="未寄出列表">
        <div id="toolbar" style=" padding: 10px 15px;">
            <div style="margin-bottom:2px">
                <span>
						&nbsp;开始日期:
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">
                </span>

                <span>&nbsp;送修批号:&nbsp;<input type="text" class="form-search" id="repairnNmber" style="width: 135px"></span>&nbsp;

                <span>
						已至装箱:&nbsp;
						<select id="isPacked" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
                            <option value="2">全部</option>
                            <option value="1">是</option>
							<option value="0" selected="selected">否</option>
						</select>&nbsp;
                </span>

            </div>
            <div style="margin-bottom: 5px;">
                <span>
						&nbsp;结束日期:
                        <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">
                </span>

                <span>
						&nbsp;送修单位:&nbsp;<input type="text" class="form-search" id="cusName" style="width: 135px">
                </span>

                <span id="repairUserSpan">&nbsp;维修人员:&nbsp;
						<select id="engineer" class="easyui-combobox" style="width: 100px;"></select>&nbsp;
                </span>
                <span id="workStationSpan">&nbsp;工站:&nbsp;
						<select id="workStation" class="easyui-combobox" style="width: 100px;">
                        <option value="0" selected="selected">全部</option>
                        <option value="1" >受理</option>
                        <option value="2" >分拣</option>
                        <option value="3" >维修</option>
                        <option value="4" >报价</option>
                        <option value="5" >终检</option>
                        <option value="6" >测试</option>
<%--                        <option value="7" >装箱</option> 通过已至装箱可以直接判断是否在装箱了--%>
                        </select>&nbsp;
                </span>
                <span>
						<a onclick="searchNotSendInfo()" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
                </span>
            </div>

            <div>
                <table cellspacing="0" cellpadding="0" style="margin-top: 10px;">
                    <tbody>
                    <tr>
                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clickLook();">批次查看</a></td>
                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="searchNotSendInfo();">刷新</a></td>
                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportnotsendData();">导出</a></td>


                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <table id="notSendInfoTable"  singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">

        </table>
    </div>
        <!-- ----------- 分页功能  --------------- -->
        <input type="hidden" name="total" value=""/>
        <input type="hidden" name="currentpage" value="1"/>
        <!-- -------------------------------- -->
    <!--批次详情 -->
    <div id="lookManage" class="easyui-window" title="查看" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
         style="width: 950px; height: 520px; visibility: hidden;">
        <div class="easyui-layout" fit="true">
            <div region="center" id="dic-center" style="overflow:auto;">
                <div style="font-weight:400; font-size: 18px;text-align: center;color:#0066FF;">维修状态统计</div>
                <div>
                    <table id="repairStateTable" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" fitColumns="true" striped="true">
                    </table>
                </div>
                <div id="batch_toolbar" style="height:30px; padding: 2px 15px;">
                    <table width="100%;">
                        <tr>
                            <td width="65%;">
					   					<span>
<%--								   			IMEI：<input type="text" id="searchImei"/>--%>
<%--											状态：<select id="pack_search_State" style="width: 130px;"></select>--%>
                                                维修人员:&nbsp;<select id="lookEngineer" class="easyui-combobox" style="width: 100px;"></select>&nbsp;
                                                   &nbsp;工站:&nbsp;
                                                    <select id="lookWorkStation" class="easyui-combobox" style="width: 100px;">
                                                    <option value="0" selected="selected">全部</option>
                                                    <option value="1" >受理</option>
                                                    <option value="2" >分拣</option>
                                                    <option value="3" >维修</option>
                                                    <option value="4" >报价</option>
                                                    <option value="5" >终检</option>
                                                    <option value="6" >测试</option>
                                                    </select>&nbsp;
								   			<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="searchLookData()" >查询</a> &nbsp;&nbsp; <font style="color: red;">双击数据修改IMEI</font>
							   			</span>
                            </td>
                            <td>
                                <span id="infoCount" style="font-size: 14px;"></span>
                            </td>
                            <td>
                                <span id="packCount" style="font-size: 14px;"></span>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="height: 330px;overflow:auto;overflow-x:hidden;">
                    <table id="lookDataTable" singleSelect="true" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" toolbar="#batch_toolbar">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>