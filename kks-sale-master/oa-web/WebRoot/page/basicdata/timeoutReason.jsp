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
    <script type="text/javascript" src="${ctx}/res/js/basicdata/timeoutReason.js"></script>
</head>
<body style="margin: 0">
<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
<div class="easyui-layout"	style="overflow: hidden; auto; width: 100%; height: 100%" fit="true">
    <div region="center" id="dic-center" style="overflow: auto" title="工作日设置">
        <div id="toolbar" style=" padding: 10px 15px;">
            <div style="margin-bottom:2px">
                <span>
						&nbsp;超期原因:
						<input type="text" class="form-search" id="reason"  style="width: 200px">
                </span>

                <span>
						<a onclick="searchReason()" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
                </span>
            </div>

            <div>
                <table cellspacing="0" cellpadding="0" style="margin-top: 10px;">
                    <tbody>
                    <tr>
                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="modifyReason();">修改</a></td>
                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="addReason();">添加</a></td>
<%--                        <td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="removeReason();">删除</a></td>--%>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <table id="reasonTable"  singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">

        </table>
    </div>
    <!-- ----------- 分页功能  --------------- -->
    <input type="hidden" name="total" value=""/>
    <input type="hidden" name="currentpage" value="1"/>
    <!-- -------------------------------- -->
    <!-- 修改窗口 -->
    <div style="position:fixed;">
        <div id="modifyWindow" class="easyui-window" title="修改超期原因" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 200px; padding: 5px;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                    <form id="batchForm" action="">
                        <table align="center">
                            <tr>
                                <input id="modifyId" type="hidden" disabled="disabled"  style=""/>
                            </tr>
                            <tr>
                                <td>超期原因：</td>
                                <td>
                                    <input id="modifyReason" type="text" style="width: 300px;"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <a id="modify" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="doModifyReason()">保存</a>
                    <a id="modifyWindowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindows('modifyWindow')">关闭</a>
                </div>
            </div>
        </div>
    </div>
    <!-- 添加窗口 -->
    <div style="position:fixed;">
        <div id="addWindow" class="easyui-window" title="添加超期原因" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 200px; padding: 5px;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                    <form id="addForm" action="">
                        <table align="center">
                            <tr>
                                <td>超期原因：</td>
                                <td>
                                    <input id="addReason" type="text"  style="width: 300px;"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <a id="add" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="doAddReason()">保存</a>
                    <a id="addWindowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindows('addWindow')">关闭</a>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>