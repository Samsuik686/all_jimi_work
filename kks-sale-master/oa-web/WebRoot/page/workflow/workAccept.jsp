<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/tree.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/worktimeTree.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/accept.js?0"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/enclosure.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/Initial.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/customer.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/accept/zbxh.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/print/LodopFuncs.js"></script>
<script type="text/javascript" src="${ctx}/res/js/print/accept_print.js"></script>

<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
</head>
<body style="margin:0">
    <input type="hidden" id="gIds-SJFJ"/> 
    <input type="hidden" id="gIds-CJGZ"/> 
    <input type="hidden" id="tree-Date"/> 
    <input type="hidden" id="tree-State"/>
    <div class="easyui-layout" fit="true">
        <div region="west" id="dic-west" style="width: 180px;" title="选择日期"> 
            <ul id="typeTreeTime" class="easyui-tree">
            </ul>
        </div>
        <div region='center' title='受理列表'>
            <div id="toolbar" style=" padding: 10px 15px;">
                <div style="margin-bottom:2px;">
                    <span>
                        设备IMEI:&nbsp;
                        <input type="text" class="form-search"  id="imei" style="width:130px">
                    </span>
                    <span>
                        &nbsp;开始日期:&nbsp;
                        <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
                    </span>
                    <span>
                        &nbsp;送修单位:&nbsp;
                        <input type="text" class="form-search" id="w_cusName" style="width:135px">
                    </span>
                    <span>
                        &nbsp;处理状态:&nbsp;
                        <select id="state" class="easyui-combobox form-search" style="width: 110px;" editable="false" panelHeight="auto" >
                            <option value="">全部</option>
                            <option value="0" selected="selected">待处理</option>
                            <option value="2">已完成</option>
                        </select>
                    </span>
                    <span>
                        &nbsp;保修期内:&nbsp;
                        <select id="isWarranty" class="easyui-combobox form-search" style="width: 110px;" editable="false" panelHeight="auto" >
                            <option value="" selected="selected">全部</option>
                            <option value="0">保修期内</option>
                            <option value="1">保修期外</option>
                        </select>
                    </span>
                </div>
                <div style="margin-bottom: 5px;">
                    <span>
                        智能锁ID:&nbsp;
                        <input type="text" class="form-search"  id="lockId" style="width:130px">
                    </span>
                    <span>
                        &nbsp;结束日期:&nbsp; 
                        <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
                    </span>
                    <span>
                        &nbsp;送修批号:&nbsp;
                        <input type="text" class="form-search" id="repairnNmber" style="width:135px">
                    </span>
                    <span>
                        &nbsp;设备状态:&nbsp;
                        <select id="customerStatus" class="easyui-combobox form-search" style="width: 110px;" editable="false" panelHeight="auto" >
                            <option value="">全部</option>
                            <option value="normal" selected="selected">正常</option>
                            <option value="un_normal">无名件</option>
                        </select>
                    </span>
                    <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
                </div>
                <div>
                    <table cellspacing="0" cellpadding="0">
                        <tbody>
                            <tr>
                                <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="InfoPrint();">打印</a></td>
                                <td><div class="datagrid-btn-separator"></div></td>
                                <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="InfoUpdate();">修改</a></td>
                                <td><div class="datagrid-btn-separator"></div></td>
                                <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="InfoDelete();">删除</a></td>
                                <td><div class="datagrid-btn-separator"></div></td>
                                <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-slicon" plain="true" onclick="openScanWindow();">批量受理</a></td>
                                <td><div class="datagrid-btn-separator"></div></td>
                                <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-plfszfjicon" plain="true" onclick="sendSort();">批量发送至分拣</a></td>
                                <td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-plfszfjicon" plain="true" onclick="sendWorkTestView();">发送至测试</a></td>
                                <td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>
                            </tr>
                        </tbody>
                    </table>
                 </div>
            </div>
            <table id="DataGrid1" class="easyui-datagrid"  singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" pageSize="20"
                     autoRowHeight="true" striped="true" scrollbarSize="0" toolbar="#toolbar">
                <thead>
                    <tr>
                        <th checkbox="true" field="id"></th>
                        <th field="lastEngineer" hidden="true"></th>
                        <th field="lastAccTime" hidden="true"></th>
                        <th field="acceptanceTime" width="130" align="center">受理时间</th>
                        <th field="imei" width="120" align="center">IMEI</th>
                        <th field="lockId" width="80" align="center">智能锁ID</th>
                        <th field="state" width="80" align="center">进度</th>
                        <th field="w_cusName" width="160"  align="center">客户名称</th>
                        <th field="customerStatus" width="80" align="center">无名件</th>
                        <th field="repairnNmber" width="110" align="center">送修批号</th>
                        <th field="w_model" width="140" align="center">主板型号|市场型号</th>
                        <th field="isWarranty" width="80" align="center">保内|保外</th>
                        <th field="sfVersion" width="180" align="center">软件版本号</th>
                        <th field="insideSoftModel" width="100" align="center">内部机型</th>
                        <th field="bluetoothId" width="100" align="center">蓝牙ID</th>
                        <th field="w_sjfjDesc" width="160" align="center">随机附件</th>
                        <th field="w_cjgzDesc" width="160" align="center">初检故障</th>
                        <th field="returnTimes" width="80" align="center">返修次数</th> 
                        <th field="salesTime" width="130" align="center">出货日期</th>
                        <th field="backTime" width="130" align="center">预计返还日期</th>
                        <th field="remark" width="120" align="center">送修备注</th> 
                        <th field="acceptRemark" width="120" align="center">受理备注</th>   
                        <th field="repairNumberRemark" width="120" align="center">批次备注</th>
                        <th field="accepter" width="80" align="center">受理人</th>
                        <th field="testPerson" width="80" align="center">测试人</th>   
                        <th field="testResult" width="150" align="center"><font color="green;">测试结果</font></th>
                        <th field="bill" width="120" align="center">订单号</th>    
                        <th field="outCount" width="80" align="center">出货总数量</th>   
                        <th field="testMatStatus" width="80" align="center">使用试流料</th>  
                    </tr>
                </thead>
            </table>
        </div>
        
        <!-- UPDATE受理-START-->
        <div id="wupdate" class="easyui-window" title="修改受理信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="width:1010px;height:550px;visibility: hidden; ">

            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding:2px;background:#fff;border:1px solid #ccc;">
                    <fieldset style="border:1px solid #ccc; padding:1px;">
                        <legend><b>产品信息</b></legend> 
                        <table align="center" width="100%" cellpadding="1">
                            <tr>
                                <td align="right">IMEI：</td> 
                                <td>
                                    <input type="text" id="fm_imei"  style="width:150px;" maxlength="15">
                                    <input type="hidden" id="fm_id">
                                    <input type="hidden" id="fm_rfild">
                                    <input type="hidden" id="fm_xhId">
                                </td>
                                <td align="right">出货日期：</td> 
                                <td> <input type="text" id="fm_salesTime" style="width:150px;" class="easyui-datetimebox" disabled="disabled" /> </td>
                                <td align="right">主板型号 ：</td>
                                <td> 
                                    <input type="text" id="fm_w_model" style="width:90px;" readonly="readonly"/> 
                                    <label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="updateZbxh()">修改</a></label>
                                </td>
                                <td align="right">市场型号：</td>
                                <td> <input type="text" id="fm_w_marketModel" style="width:150px;" disabled="disabled" /> </td> 
                             </tr>
                             <tr>
                                <td align="right">智能锁ID ：</td>
                                <td><input type="text" id="fm_lockId" style="width:150px;"  /></td>
                                <td align="right">保内|保外：</td>
                                <td><input type="text" id="fm_isWarranty" style="width:150px;" maxlength="25" disabled="disabled" /></td>
                                <td align="right">内部机型 ：</td>
                                <td><input type="text" id="fm_insideSoftModel" style="width:150px;" disabled="disabled" /></td>
                                <td align="right">订单号 ：</td>
                                <td><input type="text" id="fm_bill" style="width:150px;" disabled="disabled" /></td>
                             </tr>
                             <tr>
                                <td align="right">软件版本号 ：</td>
                                <td colspan="3"><input type="text" id="fm_sfVersion" style="width:97%;" disabled="disabled" /></td>
                                <td align="right">使用试流料 ：</td>
                                <td><input type="text" id="fm_testMatStatus" style="width:150px;" disabled="disabled" /></td>
                                <td align="right">订单数量：</td>
                                <td><input type="text" id="fm_outCount" style="width:150px;" disabled="disabled" /></td>
                             </tr>
                             <tr>
                                <td align="right">随机附件：</td>
                                <td colspan="3"> 
                                    <input type="text" id="fm_w_sjfjname" style="width:325px;" maxlength="25" readonly="readonly" />
                                    <input type="hidden" id="fm_w_sjfjId"> 
                                    <label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseSjfj()">添加</a></label>
                                </td>
                                <td align="right">蓝牙ID ：</td>
                                <td><input type="text" id="fm_bluetoothId" style="width:150px;" disabled="disabled" /></td>
                             </tr>
                         </table> 
                    </fieldset>
                    <br>
                    
                    <fieldset style="border:1px solid #ccc; padding:1px;">
                        <legend><b>客户信息</b></legend>
                        <form id="cusForm" action="">
                            <table align="center" width="100%" cellpadding="1">        
                                 <tr>       
                                     <td align="right">客户名称：</td>  
                                     <td>
                                        <!-- onclick="chooseCustomer(); -->
                                        <input type="text" id="fm_w_cusName" style="width:150px;" disabled="disabled" class="easyui-validatebox" required="true" missingMessage="请填写此字段" /> 
                                        <input type="hidden" id="fm_w_sxdwId" /> 
                                     </td>
                                     <td align="right">联&nbsp;系&nbsp;人：</td>
                                     <td>
                                        <input type="text" id="fm_w_linkman" style="width:150px;" disabled="disabled" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
                                     </td>
                                     <td align="right">手&nbsp;机&nbsp;号：</td>
                                     <td>
                                        <input type="text" id="fm_w_phone" style="width:150px;" disabled="disabled"  validType="Mobile" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
                                     </td>
                                     <td align="right">座&nbsp;机&nbsp;号：</td>
                                     <td>
                                        <input type="text" id="fm_w_telephone" style="width:150px;" disabled="disabled" />
                                     </td>
                                 </tr>
                                 <tr>
                                     <td align="right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
                                     <td>
                                        <input type="text" id="fm_w_email" style="width:150px;" disabled="disabled" class="easyui-validatebox"  validType="Email" />
                                     </td>
                                     <td align="right">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
                                     <td>
                                        <input type="text" id="fm_w_fax" style="width:150px;" maxlength="40" disabled="disabled" class="easyui-validatebox" validType="Fax" />
                                     </td>
                                      <td align="right">无&nbsp;名&nbsp;件：</td>
                                     <td>
                                        <input type="radio" style="width:15px;height:15px;"  name="customerStatus" disabled="disabled" value="normal"/>正常&nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" style="width:15px;height:15px;"  name="customerStatus" disabled="disabled" value="un_normal"/>无名件&nbsp;&nbsp;&nbsp;
                                     </td>
                                 </tr>
                                 <tr>
                                     <td align="right">客户地址：</td>
                                     <td colspan="3">
                                        <input type="text" id="fm_w_address" style="width:375px;" disabled="disabled" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
                                     </td>
                                 </tr> 
                             </table>
                          </form>
                        </fieldset>
                         
                          <br>  
                          <fieldset style="border:1px solid #ccc; padding:1px;">
                            <legend><b>初检信息</b></legend>     
                             <table align="center" width="100%" cellpadding="1">  
                             <tr>
                                 <td align="right">送修批号：</td>
                                 <td>
                                    <input type="text" id="fm_repairnNmber" style="width:150px;" disabled="disabled" /> 
                                 <td align="right">受 &nbsp;理&nbsp;人：</td>
                                 <td>
                                     <input type="text" id="fm_accepter" style="width:150px;" disabled="disabled" />
                                 </td>
                                 <td align="right">受理时间：</td>
                                 <td>
                                    <input type="text" id="fm_acceptanceTime" style="width:150px;" class="easyui-datetimebox"  disabled="disabled" />
                                 </td>
                                 <td align="right">返修次数：</td> 
                                 <td>
                                    <input type="text" id="fm_returnTimes" style="width:150px;" disabled="disabled" />
                                 </td>
                             </tr>
                             <tr>
                                 <td align="right">进&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
                                 <td>
                                    <input type="text" id="fm_state" style="width:150px;" disabled="disabled" />
                                 </td>
                                 <td align="right">初检故障：</td> 
                                 <td colspan="4">
                                    <input type="text" id="fm_w_initheckFault" style="width:375px;" readonly="readonly" />
                                    <input type="hidden" id="fm_w_cjgzId" />
                                    <label class="acc_hide"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseCjgz();">添加</a></label>
                                 </td>
                             </tr>
                             <tr>
                               <td align="right">送修备注：</td>
                               <td colspan="7"><textarea rows="2" id="fm_remark" style="width:100%; resize:none;" maxlength="250"></textarea></td>
                             </tr>
                             <tr>
                               <td align="right">受理备注：</td>
                               <td colspan="7"><textarea rows="2" id="fm_acceptRemark" style="width:100%;height: 100%; resize:none;" maxlength="250"></textarea></td>
                             </tr>
                             <tr>
                                <td align="right">测试结果：</td>
                                <td colspan="7">
                                    <textarea rows="2" id="fm_testResult" style="width:100%;height: 100%; resize:none;" disabled="disabled"></textarea>
                                </td>
                             </tr>
                        </table> 
                    </fieldset>
                </div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
                    <a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="InfoSave()">保存</a>
                    <a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wupdate')">关闭</a>
                </div>
            </div>   
         </div>
         <!-- UPDATE受理-END-->
        
        <!-- 选择客户信息 -START-->
        <div id="wkh" class="easyui-window" title="选择客户信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
              style="width:760px;height:455px; visibility: hidden; ">
            <div class="easyui-layout" fit="true">
                <div region='center' >
                    <div id="cusToolbar" style=" padding: 10px 15px;">
                        <div style="margin-bottom:5px">         
                            <span>&nbsp;客户名称:&nbsp;<input type="text" class="form-search1" id="cusName" style="width:135px" /></span>&nbsp;
                            <span>&nbsp;联系人:&nbsp;<input type="text" class="form-search1" id="linkman" style="width:135px" /></span>&nbsp;
                            <span>&nbsp;手机号:&nbsp;<input type="text" class="form-search1" id="phone" style="width:135px" /></span>&nbsp;
                            <span>&nbsp; &nbsp; 
                                <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageSXDW(1);">查询</a>
                            </span>       
                        </div>
                        
                        <div>
                            <table cellspacing="0" cellpadding="0">
                                <tbody>
                                    <tr>
                                        <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="sxdwmanageInsert();">新增</a></td>                      
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                    </div>
                                    
                    <table id="DataGrid-Customer" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fitColumns="true" autoRowHeight="true" 
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
                    <a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wkh')">关闭</a>  
                </div> 
            </div>
         </div>
        <!-- 选择客户信息 -END-->
        
        <!-- 新增客户信息 -START-->
        <div id="addCustomerWindow" class="easyui-window" title="送修单位增加" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
              style="width: 500px; height: 475px;visibility: hidden;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                    <form id="custform" action="">
                        <table align="center">
                            <tr style="display: none">
                                <td><input type="hidden" id="cId_hidden" style="width: 250px;" maxlength="11"/></td>
                            </tr>
                            <tr>
                                <td>客户名称：</td>
                                <td><input type="text" id="cusNameP" style="width: 250px;" maxlength="255" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
                            </tr>
                            <tr>
                                <td>服务周期：</td>
                                <td><input type="text" id="serviceTimeP" style="width: 250px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
                            </tr>
                            <tr>
                                <td>&nbsp;联&nbsp;系&nbsp;人：</td>
                                <td><input type="text" id="linkmanP" style="width: 250px;" maxlength="50" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
                            </tr>
                            <tr>
                                <td>&nbsp;手&nbsp;机&nbsp;号：</td>
                                <td><input type="text" id="phoneP" style="width: 250px;" class="easyui-validatebox" validType="Mobile" required="true" missingMessage="请填写此字段" /></td>
                            </tr>
                            <tr>
                                <td>&nbsp;座&nbsp;机&nbsp;号：</td>
                                <td><input type="text" id="telephoneP" style="width: 250px;"/></td>
                            </tr>
                            <tr>
                                <td>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
                                <td><input type="text" id="emailP" style="width: 250px;" maxlength="50" class="easyui-validatebox" validType="Email"/></td>
                            </tr>
                            <tr>
                                <td>传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</td>
                                <td><input type="text" id="faxP" style="width: 250px;" class="easyui-validatebox" validType="Fax"></td>
                            </tr>
                            <tr>
                                <td>通信地址：</td>
                                <td colspan="7"><textarea id="addressP" style="width: 100%; height: 80px;" maxlength="255" class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea></td>
                            </tr>
                            <tr>
                                <td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                                <td colspan="7"><textarea id="remarkP" style="width: 100%; height: 80px;" maxlength="255"></textarea></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <a id="sxdwmanageInsert" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="sxdwmanageSave()">保存</a>
                    <a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="addCustomerWindowClose()">关闭</a>
                </div>
            </div>
        </div>
        <!-- 新增客户信息 -END-->
        
        <!-- 选择附件 -START-->
        <div id="wjs" class="easyui-window" title="选择随机附件" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
              style="width:760px;height:520px; visibility: hidden;">
            <div class="easyui-layout" fit="true">
                <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
                   <ul id="typeTree" class="easyui-tree" animate="true" dnd="false"></ul>
                </div>
                <div region="center" id="dic-center" style="overflow:auto" title="[详细信息]  注：双击选择">
                    <div style="height:280px;">
                        <div id="fjToolbar" style=" padding: 10px 15px;">
                            <div style="margin-bottom:5px">     
                                <span>&nbsp;附件名称:&nbsp;<input type="text" class="form-search3" id="name" style="width:200px;"></span>&nbsp; &nbsp; &nbsp; &nbsp;
                                <span>&nbsp; &nbsp; 
                                    <a id="searchinfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageSJFJ('')">查询</a>
                                </span>
                            </div>
                        </div>
                        <table id="table_sjfj"  fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" autoRowHeight="true" 
                               pagination="true" fitColumns="true" striped="true" scrollbarSize="0" toolbar="#fjToolbar">
                            <thead>
                                <tr>
                                    <th field="eid" hidden="true"></th>
                                    <th field="category" width="220" align="center">附件类别</th>
                                    <th field="brand"    width="220" align="center">品牌</th>
                                    <th field="name"     width="220" align="center">名称</th>
                                    <th field="color"    width="220" align="center">颜色</th>
                                    <th field="number"   width="220" align="center">数量</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="selected-type" style="height:130px;">
                        <div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选附件</div>
                        <div class="selected-type-box" id ="selected-type-box-FJ">
                        </div>                      
                    </div>
                </div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
                    <a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="enclosureSave()">保存</a>
                    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wjs')">关闭</a>
                </div>
            </div>
         </div>
         <!-- 选择附件 -END-->
        
        <!-- 选择初检故障 -START-->
        <div id="wgz" class="easyui-window" title="选择初检故障" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
              style="width:760px;height:520px;visibility: hidden;">

            <div class="easyui-layout" fit="true">
                <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
                   <ul id="typeTree-CCGZ" class="easyui-tree" animate="true" dnd="false"></ul>
                </div>
                <div region="center" id="dic-center" style="overflow:auto" title="[详细信息]  注：双击选择">
                    <div style="height:280px;">
                        <div id="cjToolbar" style=" padding: 10px 15px;">
                            <div style="margin-bottom:5px">     
                                <span>&nbsp;关键字:&nbsp;<input type="text" class="form-search2" id="name1" style="width:200px" placeholder="初检故障,描述,编码"></span>&nbsp; &nbsp; &nbsp; &nbsp;
                                <span>&nbsp; &nbsp; 
                                    <a id="searchinfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryCCGZListPage('')">查询</a>
                                </span>
                            </div>
                        </div>
                        <table id="table_gz" singleSelect="false" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" 
                                pagination="true" fitColumns="true" striped="true" scrollbarSize="0" toolbar="#cjToolbar">
                            <thead>
                                <tr>
                                    <th field="iid" hidden="true">故障序号</th>
                                    <th field="faultClass" width="200" align="center" >故障类别</th>
                                    <th field="initheckFault" width="200" align="center" >初检故障</th>
                                    <th field="description" width="200" align="center" >故障现象描述</th>
                                    <th field="number" width="200" align="center" >故障编码</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="selected-type" id="selected-type-GZ" style="height: 130px;">
                        <div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选故障</div>
                        <div class="selected-type-box" id ="selected-type-box-GZ">
                        </div>                      
                    </div>
                </div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
                    <a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="InitalSave()">保存</a>
                    <a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wgz')">关闭</a>
                </div>
            </div>
        </div>
        <!-- 选择初检故障 -END-->
        
        <!-- SCAN受理-START--> 
        <div id="wscan" class="easyui-window" title="添加受理管理" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
              style="width:1200px;height:500px;visibility: hidden;">
            <div class="easyui-layout" fit="true">
                <div region="north" border="true" style="height: 21px;">
                    <span style="font-family: 微软雅黑;font-size: 14px;font-weight: bold;">【备注：点击批量受理选中全部数据,按enter键同步智能锁ID二维码数据】</span>
                </div>
                <div region="center" border="false">
                    <div class="datagrid-toolbar">
                            <table cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="addScanRow();">新增</a></td>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="deleteScanRow();">删除</a></td>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="batchAccept();">批量受理</a></td>                            
                        </tr>
                        </tbody>
                        </table>
                    </div>
                    <table width="190%" align="center"  border="1" cellspacing="0" cellpadding="0" bordercolor="#F5F5F5" style="table-layout: fixed;">    
                        <thead style="text-align:center;font-weight: bold;color: rgb(255, 255, 255);cursor: default;background: rgb(102, 153, 204);"> 
                            <tr>
                                <th style="height:24px;" width="30px;" ><input type="checkbox" onclick="selectAll(this);"></th>
                                <th style="height:24px;" width="135px;">IMEI</th> 
                                <th style="height:24px;" width="185px;">客户名称 </th>
                                <th style="height:24px;" width="135px;">市场型号</th> 
                                <th style="height:24px;" width="165px;">主板型号</th> 
                                <th style="height:24px;" width="185px;">软件版本号</th>
                                <th style="height:24px;" width="90px;">保内|保外</th>
                                <th style="height:24px;" width="165px;">出货日期</th>
                                <th style="height:24px;" width="135px;">上次维修人员 </th>
                                <th style="height:24px;" width="135px;">上次受理时间 </th>
                                <th style="height:24px;" width="90px;">返修次数</th>
                                <th style="height:24px;" width="185px;">初检故障</th>
                                <th style="height:24px;" width="185px;">送修备注</th>
                                <th style="height:24px;" width="180px;">智能锁ID二维码</th> 
                                <th style="height:24px;" width="135px;">智能锁ID</th> 
                                <th style="height:24px;" width="185px;">随机附件</th>
                                <th style="height:24px;" width="185px;">受理备注</th>
                                <th style="height:24px;" width="185px;">批次备注</th>                                                                                       
                                <th style="height:24px;" width="135px;">客户寄货方式</th>
                                <th style="height:24px;" width="135px;">蓝牙ID</th> 
                                <th style="height:24px;" width="135px;">送修批号</th> 
                                <th style="height:24px;" width="135px;">内部机型</th> 
                                <th style="height:24px;" width="135px;">无名件 </th>
                                <th style="height:24px;" width="185px;">快递公司</th>
                                <th style="height:24px;" width="185px;">快递单号</th>
                                <th style="height:24px;" width="135px;">订单号</th>    
                                <th style="height:24px;" width="90px;">出货总数量</th>   
                                <th style="height:24px;" width="90px;">使用试流料</th>
                                <th style="height:24px;" width="135px;">应返还时间</th>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>
                                <th style="display: none;"></th>
                            </tr>
                        </thead>                
                        <tbody id="scan_tbody"></tbody>
                    </table>
                    <input type="hidden" id="hideSindex"  value=""/>
                </div>
                <div region="south" border="false" style="text-align:right;height:75px;line-height:20px;">
                    <div>
                        <span style="margin-right:860px;">批量受理共<label style="color: green;">&nbsp;<span id="acc_number">0</span>&nbsp;</label>条记录（成功受理后自动删除临时保存记录）</span>
                        <a id="tempSave" class="easyui-linkbutton" style="margin-right:5px;" iconCls="icon-add" href="javascript:void(0)" onclick="tempSave()">临时保存</a>
                        <a id="getTempData" class="easyui-linkbutton" style="margin-right:5px;" iconCls="icon-edit" href="javascript:void(0)" onclick="getTempData()">获取保存记录</a>
                        <a id="deleteTempData" class="easyui-linkbutton" style="margin-right:670px;" iconCls="icon-remove" href="javascript:void(0)" onclick="deleteTempData()">删除保存记录</a>
                        <a id="treeDelete"class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wscan')">关闭</a>
                    </div>
                    
                </div>
            </div>
         </div>
         <!-- SCAN受理-END-->
         
         <!-- 选择主板型号 -START-->
        <div id="zbsc" class="easyui-window" title="选择主板型号|市场型号" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
              style="width:760px;height:430px; visibility: hidden; ">
            <div class="easyui-layout" fit="true">
                <div region='center' >
                    <div id="zbxhToolbar" style=" padding: 10px 15px;">
                        <div style="margin-bottom:5px"> 
                            <input type="hidden" id="zbxh_rowindex"/> 
                            <span>&nbsp;主板型号:&nbsp;<input type="text" class="form-search5"  id="searchBymodel" style="width:135px" /></span>&nbsp;
                            <span>
                                <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageZBXH(1);">查询</a>
                            </span>       
                        </div>
                        
                        <div>
                            <table cellspacing="0" cellpadding="0">
                                <tbody>
                                    <tr>
                                        <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="zbxhmanageInsert();">新增</a></td>                      
                                    </tr>
                                </tbody>
                            </table>
                        </div>                      
                    </div>
                                    
                    <table id="DataGrid-Zbxh" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fitColumns="true" autoRowHeight="true" 
                           striped="true" scrollbarSize="0" toolbar="#zbxhToolbar">
                        <thead>
                            <tr>
                                <th field="mId" hidden="true"></th>
                                <th field="model" width="110"   align="center">主板型号</th>
                                <th field="marketModel" width="110"  align="center">市场型号</th>
                                <th field="modelType" width="110"  align="center">型号类别</th>
                                <th field="createTypeName" width="110" align="center">创建类型</th>
                                <th field="enabledFlag" width="110" align="center">禁用</th>
                                <th field="remark" width="110"  align="center">备注</th>
                            </tr>
                        </thead>
                    </table> 
                </div> 
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
                    <a id="zbwinclose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="zbxh_window_close(rowindex);">关闭</a>  
                </div> 
            </div>
         </div>
        <!-- 选择主板型号 -END-->
         
         <!-- 创建型号 -START-->
        <div id="wzb" class="easyui-window" title="新增主板型号" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
              style="width: 500px; height: 300px; visibility: hidden; ">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
                    <form id="wzbform" action="">
                        <table align="center">
                            <tr style="display: none">
                                <td><input id="mId_hidden" type="hidden" /></td>
                            </tr>
                            <tr>
                                <td>主板型号：</td>
                                <td><input type="text" id="modelP"  style="width: 220px;" maxlength="255" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
                            </tr>
                            <tr>
                                <td>市场型号：</td>
                                <td><input type="text" id="marketModelP" style="width:220px;" maxlength="100" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
                            </tr>
                            <tr>
                                <td>型号类别：</td>
                                <td>
                                    <input id="type" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width:150px;" required="true" missingMessage="请选择类别"/>
                                </td>
                            </tr>
                            <tr>
                                <td>创建类别：</td>
                                <td>
                                    <input id="createType" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width:150px;" required="true" missingMessage="请选择类别"/>
                                </td>
                            </tr>
                            <tr>
                                <td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                                <td colspan="7"><textarea id="zbremarkP" style="width: 100%; height: 80px;" maxlength="255"></textarea></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <a id="zbxhmanageInsert" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="zbxhmanageSave()">保存</a>
                    <a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
                </div>
            </div>
        </div>
    <!-- 创建型号-END-->    
        
    </div>
    <div>
        <!-- 区分客户，初检故障和随机附件的弹框 -->
        <input type="hidden" id="customerModal"/>
        <input type="hidden" id="faultModal"/>
        <input type="hidden" id="fileModal"/>
    </div>
</body>
</html>