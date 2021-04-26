<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/basicdata/workdate.js"></script>
</head>
<body style="margin: 0">
<%--<input type="hidden" id="gIds"/>--%>
<%--<input type="hidden" id="tree-Date"/>--%>
<%--<input type="hidden" id="tree-State"/>--%>
<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
<div class="easyui-layout"	style="overflow: hidden; auto; width: 100%; height: 100%" fit="true">
<%--    <div region="west" id="dic-west" style="width: 150px;" title="选择日期">--%>
<%--        <ul id="typeTreeTime" class="easyui-tree">--%>
<%--        </ul>--%>
<%--    </div>--%>
    <div region="center" id="dic-center" style="overflow: auto" title="工作日设置">
        <div id="toolbar" style=" padding: 10px 15px;">
            <div style="margin-bottom:2px">
                <span>
						&nbsp;开始日期:
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">
                </span>

                <span>
                        星期:&nbsp;
						<select id="weekDay" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
                            <option value="0" selected="selected">全部</option>
                            <option value="1">星期一</option>
							<option value="2">星期二</option>
                            <option value="3">星期三</option>
							<option value="4">星期四</option>
                            <option value="5">星期五</option>
							<option value="6">星期六</option>
                            <option value="7">星期日</option>
						</select>&nbsp;
                </span>&nbsp;

                <span>
						类型:&nbsp;
						<select id="userType" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
                            <option value="0" selected="selected">全部</option>
                            <option value="1">维修未寄出</option>
							<option value="2">异常反馈</option>
						</select>&nbsp;
                </span>

            </div>
            <div style="margin-bottom: 5px;">
                <span>
						&nbsp;结束日期:
                        <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">
                </span>

                <span>
						工作状态:&nbsp;
						<select id="workType" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
                            <option value="0">不工作</option>
							<option value="1">全天工作</option>
                            <option value="2">上午工作</option>
                            <option value="3">下午工作</option>
                            <option value="4" selected="selected">全部</option>
						</select>&nbsp;
                </span>

                <span>
						<a onclick="searchWorkDate()" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
                </span>
                <span>
						<a onclick="batchAdd()" name="ckek" class="easyui-linkbutton" iconCls="icon-daochuicon" href="javascript:void(0)">批量插入</a>
                </span>
            </div>

            <div>
                <table cellspacing="0" cellpadding="0" style="margin-top: 10px;">
                    <tbody>
                    <tr>
                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="modifyWorkDate();">修改</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <table id="workDateTable"  singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">

        </table>
    </div>
    <!-- ----------- 分页功能  --------------- -->
    <input type="hidden" name="total" value=""/>
    <input type="hidden" name="currentpage" value="1"/>
    <!-- -------------------------------- -->
    <!-- 修改窗口 -->
    <div style="position:fixed;">
    <div id="modifyWindow" class="easyui-window" title="更新工作日" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 300px; padding: 5px;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                <form id="batchForm" action="">
                    <table align="center">
                        <tr>
                            <input id="modifyId" type="hidden" disabled="disabled"  style=""/>
                        </tr>
                        <tr>
                            <td>日期：</td>
                            <td>
                                <input id="normalDate" type="text" disabled="disabled" style="width: 150px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td>类型：</td>
                            <td>
                                <select id="modifyUserType" class="easyui-combobox" style="width: 100px;" disabled="disabled" panelHeight="auto" >
                                    <option value=1>未寄出</option>
                                    <option value=2>异常反馈</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>工作状态：</td>
                            <td>
                                <select id="modifyWorkType" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
                                    <option value=0>不工作</option>
                                    <option value=1>全天工作</option>
                                    <option value=2>上午工作</option>
                                    <option value=3>下午工作</option>
                                </select>&nbsp;
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="modify" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="doModifyWorkDate()">保存</a>
                <a id="modifyWindowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindows('modifyWindow')">关闭</a>
            </div>
        </div>
    </div>
    </div>
</div>
</body>
</html>