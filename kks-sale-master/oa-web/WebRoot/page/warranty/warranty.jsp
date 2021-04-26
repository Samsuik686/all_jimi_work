<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/cpNameTree.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/warranty/warranty.js?20170311"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/ajaxfileupload.js"></script>
<style>
.topic-desc span{
font-size:16px; 
}
.topic-desc textarea{
font-size:16px; 
}
.topic-desc input{
zoom:150%
}
.topic-desc div {
padding:10px;
}
.topic-desc{
padding:10px;
}
.form-search {
    margin-top: 3px;
}
</style>
</head>

<body style="margin:0">
    <div class="easyui-layout"
        style="overflow: hidden; width:100%;height:100%" fit="true" >
        <div region="west" id="dic-west" style="width: 180px;" title="选择公司"> 
            <ul id="typeTreeTime" class="easyui-tree">
            </ul>
        </div>
        <div region="center" id="dic-center" style="overflow:auto" title="保修期限">
            <div id="toolbar" style="padding: 10px 15px;">
                <div style="margin-bottom:2px;">
                    <span>
                        &nbsp;设备IMEI:&nbsp;
                        <input type="text" class="form-search" style="width:135px">
                        <textarea class="form-search" style="overflow:hidden;position: absolute;left:79px;resize: none; width: 135px;height: 26px;" id="imei" onfocus="clickFocus()" onblur="clickBlur()"></textarea> 
                    </span>
                    <span>
                        &nbsp;设备型号:&nbsp;
                        <input type="text" class="form-search" id="mcType" style="width:135px">
                    </span>
                    <span>
                        &nbsp;主板软件版本号:&nbsp;
                        <input type="text" class="form-search" id="sfVersion" style="width:135px">
                    </span>
                    <br>
                    <span>
                        &nbsp;公司名称:&nbsp;
                        <input type="text" class="form-search" id="cpName" style="width:135px">
                    </span>
                    <span>
                        &nbsp;订单号:&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" class="form-search" id="bill" style="width:135px">
                    </span>
                    <span>
                        &nbsp;保修期限:&nbsp;
                        <select id="prolongMonth" class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto"">
                            <option value="" selected="selected">全部</option>
                            <option value="other">自定义范围</option>
                            <option value="0">不保修</option>
                            <option value="13">一年（13个月）</option>
                            <option value="24">二年（24个月）</option>
                            <option value="36">三年（36个月）</option>
                            <option value="48">四年（48个月）</option>
                            <option value="60">五年（60个月）</option>
                        </select>
                    </span>
                    <div id="cs_text" style="display: none;">
	                    <span>
	                        &nbsp;月数:&nbsp;
	                        <input type="number" class="form-search" id="prolongMonthStart" style="width: 60px" value=3 min=1 max=200 onchange="monthNumberStart()"> 
	                    </span>
	                    <span>
	                        &nbsp;至&nbsp;
	                        <input type="number" class="form-search" id="prolongMonthEnd" style="width: 60px" value=12 min=1 max=200 onchange="monthNumberEnd()"> 
	                    </span>
                    </div>
                    <br>
                    <span>
                        &nbsp;创建人:&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" class="form-search" id="cpName" style="width:135px">
                    </span>
                    <span>
                        &nbsp;创建日期:&nbsp;
                        <input type="text" class="form-search" id="createTimeStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
                    </span>
                    <span>
                        &nbsp;至&nbsp;
                        <input type="text" class="form-search" id="createTimeEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
                    </span>
                    <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="search_data();">查询</a>
                </div>
                <div>
                    <!-- datagrid-btn-separator 分割线 -->
                    <table cellspacing="0" cellpadding="0">
                        <tr>
<!--                             <td> -->
<!--                                 <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="warranty_add();">修改保修期限</a> -->
<!--                             </td> -->
                            <td>
                                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="warranty_del();">删除</a>
                            </td>
                            <td>
                                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
                            </td>
                            <td>
                                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportData();">导出</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
                <thead>
                    <tr>
                        <th checkbox="true" field=id></th>
                        <th field="prolongMonth" width="120" align="center" >保修期限</th>
                        <th field="imei" width="100" align="center" >IMEI</th>
                        <th field="mcType" width="100" align="center" >设备型号</th>
                        <th field="sfVersion" width="100" align="center" >主板软件版本号</th>
                        <th field="bill" width="100" align="center" >订单号</th>
                        <th field="cpName" width="100" align="center" >公司名称</th>
                        <th field="createBy" width="100" align="center" >创建人</th>
                        <th field="createTime" width="100" align="center" >创建时间</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>

</body>
</html>