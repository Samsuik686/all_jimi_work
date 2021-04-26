<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/warranty/deviceInfo.js?201703010"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/enclosure.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/Initial.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>

<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<style>
    .form-search {
        margin-top: 3px;
    }
</style>
</head>
<body style="margin:0">
    <input type="hidden" id="gIds-SJFJ"/> 
    <input type="hidden" id="gIds-CJGZ"/> 
    <input type="hidden" id="tree-Date"/> 
    <input type="hidden" id="tree-State"/>
    <div class="easyui-layout" fit="true">
        <div region='center' title='销售列表'>
            <div id="toolbar" style=" padding: 10px 15px;">
                <div style="margin-bottom:2px;">
                    <span style="position: relative;">
                        &nbsp;设备IMEI:&nbsp;
                        <input type="text" class="form-search" style="width:138px">
                        <textarea class="form-search" style="overflow:hidden;position: absolute;left:64px;resize: none; width: 138px;height: 26px;" id="imei" onfocus="clickFocus()" onblur="clickBlur()"></textarea> 
                    </span>
                    <span>
                        &nbsp;设备型号:&nbsp;
                        <input type="text" class="form-search" id="mcType" style="width:135px">
                    </span>
                    <span>
                        &nbsp;主板软件版本号:&nbsp;
                        <input type="text" class="form-search" id="sfVersion" style="width:135px">
                    </span>
<%--                    <span>--%>
<%--                        &nbsp;主板类型:&nbsp;--%>
<%--                        <select id="zbxhCombobox" style="width:135px" class="easyui-combobox">--%>
<%--                        </select>--%>
<%--                    </span>--%>
                    <br>
                    <span>
                        &nbsp;公司名称:&nbsp;
                        <input type="text" class="form-search" id="cpName" style="width:138px">
                    </span>
                    <span>
                        &nbsp;出货日期:&nbsp;
                        <input type="text" class="form-search" id="outDateStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
                    </span>
                    <span>
                        &nbsp;至&nbsp;
                        <input type="text" class="form-search" id="outDateEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
                    </span>
                    <br>
                    <span>
                        &nbsp;订单号:&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" class="form-search" id="bill" style="width:138px">
                    </span>
                    <span>
                        &nbsp;生产日期:&nbsp;
                        <input type="text" class="form-search" id="productionDateStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
                    </span>
                    <span>
                        &nbsp;至&nbsp;
                        <input type="text" class="form-search" id="productionDateEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
                    </span>
                    <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="search_data();">查询</a>
                </div>
                <div>
                    <table cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="warranty_add();">设置保修期限</a>
                            </td>
                            <td>
                                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
                            </td>
                            <td>
                                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daoruicon" plain="true" onclick="batchAddWindow()">批量导入</a>
                            </td>
                        </tr>
                    </table>
                 </div>
            </div>
            <table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
                <thead>
                    <tr>
                        <th checkbox="true" field="id"></th>
                        <th field="imei" width="135" align="center">IMEI</th>
                        <th field="mcType" width="135" align="center">设备型号</th>
                        <th field="sfVersion" width="135" align="center">主板软件版本号</th>
                        <th field="cpName" width="135" align="center">公司名称</th>
                        <th field="bill" width="135" align="center">订单号</th>
                        <th field="outDate" width="135"  align="center">出货日期</th>
                        <th field="productionDate" width="135" align="center">生产日期</th>
                    </tr>
                </thead>
            </table>
        </div>
        
        <!-- 新增 Start -->
        <div style="position:fixed;">
            <div id="warranty_add" class="easyui-window" title="设置保修期限" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
                style="visibility:hidden;width: 500px; height: 320px; padding: 5px;">
                <div class="easyui-layout" fit="true">
                    <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                        <form id="warranty_form" action="">
                            <input type="hidden" id="id"/>
                            <table align="center" width="100%" cellpadding="2"> 
                                <tr>    
                                    <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保修期限:</td>
                                    <td>
                                       <select id="prolongMonth" style="width: 200px;" onchange="prolongOther()">
                                            <option value="other" >自定义</option>
                                            <option value="0" selected="selected">不保修</option>
                                            <option value="13">一年（13个月）</option>
                                            <option value="24">二年（24个月）</option>
                                            <option value="36">三年（36个月）</option>
                                            <option value="48">四年（48个月）</option>
                                            <option value="60">五年（60个月）</option>
                                        </select>
                                    </td>
                                 </tr>
                                 <tr id="cs_text" style="display: none;">
                                    <td align="right">自定义月数:</td>
                                    <td>
                                        <input id="cs_number" type="number" value=3 min=0 max=200 step=2 onchange="monthNumber()"/>
                                    </td>
                                 </tr>
                                 <tr>
                                    <td align="right" style="font-weight: 800">已选设备</td>
                                 </tr>
                                 
                                 <tr> 
                                    <td align="right" style="display: inline-block; width:95%;">IMEI:</td>
                                    <td>
                                        <textarea id="imei_add" class="easyui-validatebox" style="width:200px;height:100px" disabled="disabled"></textarea>
                                    </td>
                                 </tr>
                              </table>
                        </form>
                        <br>
                        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                            <a id="saveButton" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveWarranty()">保存</a> 
                            <a id="close" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">关闭</a>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <!-- 新增 END -->

        <!-- 批量导入 Start -->
        <div style="position:fixed;">
            <div id="batchWarranty" class="easyui-window" title="设置保修期限" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
                 style="visibility:hidden;width: 500px; height: 320px; padding: 5px;">
                <div class="easyui-layout" fit="true">
                    <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                        <form id="batchWarrantyForm" action="">
                            <input type="hidden" id="batchId"/>
                            <table align="center" width="100%" cellpadding="2">
                                <tr>
                                    <td valign="top" width="80px">导入提示：</td>
                                    <td>
                                        <label id="upload_tip">
                                            1、请使用AMS的导出数据，用于导入<br/>
                                            2、文件大小必须小于10Mb<br/>
                                            2、耐心等待<br/>
                                        </label>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保修期限:</td>
                                    <td>
                                        <select id="batchProlongMonth" style="width: 200px;" onchange="batchProlongOther()">
                                            <option value="other" >自定义</option>
                                            <option value="0" selected="selected">不保修</option>
                                            <option value="13">一年（13个月）</option>
                                            <option value="24">二年（24个月）</option>
                                            <option value="36">三年（36个月）</option>
                                            <option value="48">四年（48个月）</option>
                                            <option value="60">五年（60个月）</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr id="batch_text" style="display: none;">
                                    <td align="right">自定义月数:</td>
                                    <td>
                                        <input id="batch_number" type="number" value=3 min=0 max=200 step=2"/>
                                    </td>
                                </tr>
                                <tr id="batch_file">
                                    <td align="right">上传文件:</td>
                                    <td>
                                        <input id="file" type="file" value=3 min=0 max=200 step=2 />
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <br>
                        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                            <a id="batchSaveButton" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="batchSaveWarranty()">保存</a>
                            <a id="batchClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="batchCloseWindow()">关闭</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 批量导入 END -->

        <div style="position:fixed;">
            <div id="warranty_repeat" class="easyui-window" title="设置保修期限" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
                style="visibility:hidden;width: 500px; height: 260px; padding: 5px;">
                <div class="easyui-layout" fit="true">
                    <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                        <form id="warranty_form" action="">
                            <input type="hidden" id="id"/>
                            <table align="center" width="100%" cellpadding="2"> 
                                 <tr>
                                    <td align="right" style="font-weight: 800">已设置期限设备</td>
                                 </tr>
                                 <tr> 
                                    <td align="right" style="display: inline-block; width:95%;">IMEI:</td>
                                    <td>
                                        <textarea id="imei_repeat" class="easyui-validatebox" style="width:200px;height:100px" disabled="disabled"></textarea>
                                    </td>
                                 </tr>
                              </table>
                        </form>
                        <br>
                        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                            <a id="close" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeRepeatWindow()">取消</a>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
    </div>
</body>
</html>