//查询条件未寄出表示
// var isFinish ;          //完成就是已至装箱，未寄出就是未到装箱
var startTime;          //开始日期
var endTime;            //结束日期
var repairnNmber;       //送修批号
var isPacked;           //是否装箱
var w_cusName;            //送修单位
var workStation;        //工站
var currentpage;         //分页 页数
var pageSize;           //分页 页大小
var w_engineer;         //维修人员
$(function () {
    //加快首页加载，进入页面默认查询当天的+
    var today = new Date();
    $("#startTime").val(today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate());
    $("#endTime").val(today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate());
    WorkTreeLoad();
    comboboxInit("engineer");
    loadQueryData();
    notSendTableInit();
});
/**
 * 维修选择栏初始化
 **/
function comboboxInit(id){
    var data=[];
    $.ajax({
        url:ctx+"/user/UserListByMenu?menuId="+"11412",
        type:'GET',
        async:false,
        success:function(result){
            data = result.data;
        },
        error:function(result){
            alert("初始化错误");
        }
    });
    data.push({"uName":"全部"});
    $("#"+id).combobox ({
        editable : false,
        valueField : "uName", //相当于 option 中的 value 发送到后台的
        textField : "uName",//option中间的内容 显示给用户看的
        data:data
    });
}

/**
 * 点击查询按钮
 */
function searchNotSendInfo(){
    loadQueryData()
    $('#notSendInfoTable').datagrid('load');

}

// 导出未寄出设备
function exportnotsendData() {
    var startTime = $("#startTime").val();//开始日期
    var endTime = $("#endTime").val();//结束日期
    var repairnNmber = $.trim($("#repairnNmber").val());//送修批号
    var isPacked = $("#isPacked").combobox("getValue");//是否装箱
    var w_cusName=$.trim($("#cusName").val());//送修单位
    var workStation = $("#workStation").combobox("getValue");//工站
    var w_engineer = $("#engineer").combobox('getValue');//维修人员
    if(w_engineer === '全部'){
        w_engineer ='';
    }
    var url =  ctx + "/workflowcon/exportNotSendInfo";
    var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
    $form1.append($("<input type='hidden' name='startTime' value='" + startTime +"'/>"));
    $form1.append($("<input type='hidden' name='endTime' value='" + endTime +"'/>"));
    $form1.append($("<input type='hidden' name='repairnNmber' value='" + repairnNmber +"'/>"));
    $form1.append($("<input type='hidden' name='isPacked' value='" + isPacked +"'/>"));
    $form1.append($("<input type='hidden' name='w_cusName' value='" + w_cusName +"'/>"));
    $form1.append($("<input type='hidden' name='workStation' value='" + workStation +"'/>"));
    $form1.append($("<input type='hidden' name='w_engineer' value='" + w_engineer +"'/>"));
    $("body").append($form1);
    $form1.submit();
    $form1.remove();
}



/**
 * 未寄出表格初始化
 */
function notSendTableInit() {
    $('#notSendInfoTable').datagrid({
        pagination: true,
        pageSize: 2000000,
        pageList: [2000000],
        pageNumber: 1,
        fit:true,
        singleSelect:true,
        url:ctx+"/workflowcon/notSendInfo",
        method:'POST',
        onDblClickRow:function(rowIndex,row){initLookWindow(row.repairnNmber);},
        //设置向后台查询时的参数。默认情况下datagrid在选择页数时传递的分页参数为page和rows，这里删除了这两个参数。
        onBeforeLoad : function(param){
            // param["isFinish"] = isFinish;
            param["startTime"] = startTime;
            param["endTime"] = endTime;
            param["repairnNmber"] = repairnNmber;
            param["isPacked"] = isPacked;
            param["w_cusName"] = w_cusName;
            param["workStation"] = workStation;
            param["startTime"] = startTime;
            param["w_engineer"] = w_engineer;
            param["pageSize"] = param.rows;
            param["currentpage"] = param.page;
            delete param.rows;
            delete param.page;
        },
        columns:[[
            {title:'受理时间',field:'acceptanceTime',align:'center',width:130},
            {title:'送修批号',field:'repairnNmber',align:'center',width:110},
            {title:'客户名称',field:'w_cusName',align:'center',width:110},
            {title:'IMEI',field:'imei',align:'center',width:120},
            {title:'工站',field:'workStation',align:'center',width:110,formatter:workStationForm},
            {title:'进度',field:'state',align:'center',width:110,formatter:stateForm},
            {title:'主板型号',field:'w_model',align:'center',width:100},
            {title:'市场型号',field:'w_marketModel',align:'center',width:130},
            {title:'维修员',field:'w_engineer',align:'center',width:130},
            {title:'已至装箱',field:'isPacked',align:'center',width:80,formatter:function(value,row) {
                    if ((row.state == 7 && row.w_FinispassFalg === -1) ||
                        (row.state == 8 && row.w_repairState == -1) || row.state==-1) {
                        return  "是";

                    } else {
                        return "否";
                    }
            }},
            {title:'发送终检时间',field:'w_sendFinTime',align:'center',width:80},
            {title:'客户付款日期',field:'w_payTime',align:'center',width:130},
            {title:'发送报价日期',field:'price_createTime',align:'center',width:130},
            {title:'随机附件',field:'w_sjfjDesc',align:'center',width:160},
            {title:'返修次数',field:'returnTimes',align:'center',width:80},
            {title:'保内|保外',field:'isWarranty',align:'center',width:80,formatter:function (value,row) {
                    if(value==0){
                        return '保内';
                    }else{
                        return '保外';
                    }
            }},
            {title:'初检故障',field:'w_cjgzDesc',align:'center',width:160},
            {title:'最终故障',field:'w_zzgzDesc',align:'center',width:200},
            {title:'处理方法',field:'w_solution',align:'center',width:255},
            {title:'智能锁ID',field:'lockId',align:'center',width:80},
            {title:'是否人为',field:'w_isRW',align:'center',width:80,formatter:function(value,row){
                if(value==0){
                    return '是';
                }else{
                    return '否';
                }
            }},
            {title:'维修报价(￥)',field:'w_repairPrice',align:'center',width:100},
            {title:'报价描述',field:'w_wxbjDesc',align:'center',width:200},
            {title:'测试人员',field:'testPerson',align:'center',width:80},
            {title:'测试结果',field:'testResult',align:'center',width:200},
            {title:'送修备注',field:'remark',align:'center',width:160},
            {title:'受理备注',field:'acceptRemark',align:'center',width:225},
            {title:'批次备注',field:'testResult',align:'center',width:225},
            {title:'终检',filed:'w_FinispassFalg',hidden:true},
            {title:'报价状态',filed:'w_priceState',hidden:true},
            {title:'维修状态',filed:'w_repairState',hidden:true},
        ]]
    });
}
function loadQueryData(){
    // isFinish = $.trim($("#isFinish").val());//是否完成
    startTime = $("#startTime").val();//开始日期
    endTime = $("#endTime").val();//结束日期
    repairnNmber = $.trim($("#repairnNmber").val());//送修批号
    isPacked = $("#isPacked").combobox("getValue");//是否装箱
    w_cusName=$.trim($("#cusName").val());//送修单位
    workStation = $("#workStation").combobox("getValue");//工站
    w_engineer = $("#engineer").combobox('getValue');//维修人员
    if(w_engineer === '全部'){
        w_engineer ='';
    }
}
/**
 * 根据条件查询未寄出设备数据，侧边栏点击查询调用了该方法
 * @param pageNumber  第几页
 */
function queryListPage(pageNumber){
    loadTreeData();

    var treeDate = $("#tree-Date").val();
    if(treeDate){
        $("#startTime").val(treeDate);
        $("#endTime").val(treeDate);
    }

    loadQueryData();
    if(isPacked == 1 && workStation!=0){
        $.messager.alert("参数错误", "查询条件已完成与工站条件冲突", "info");
        return;
    }
    var options = $("#notSendInfoTable").datagrid("getPager" ).data("pagination" ).options;
    var param={};
    // param.isFinish = isFinish;
    param.startTime = startTime;
    param.endTime = endTime;
    param.repairnNmber = repairnNmber;
    param.isPacked = isPacked;
    param.w_cusName = w_cusName;
    param.workStation = workStation;
    param.w_engineer = w_engineer;
    param.currentpage = currentpage;
    param.pageSize = options.pageSize;

    //发送请求
    $.ajax({
        type:"POST",
        data: param,
        url:ctx+"/workflowcon/notSendInfo",
        async:false,
        success:function (result) {
            var data = {};
            data.rows=result.rows;
            data.total=result.total;
            //重新加载表格数据
            $("#notSendInfoTable").datagrid('loadData',data);
            //设置到第一页
            $('#notSendInfoTable').datagrid('getPager').pagination({
                pageNumber:1
            });
        },
        error:function (result) {
            $.messager.alert("操作异常","网络错误","info");
        }
    });
}

/**
 * 获取当前时间到前N天的日期
 */
function getDate(n){
    var data=[];
    var today = new Date;
    var oneDay = 24*60*60*1000;
    var year,mon,day;
    for(var i=0;i<n;i++){
        var time={};
        var tmp = new Date(today.getTime()-i*oneDay);
        year =  tmp.getFullYear();
        mon = tmp.getMonth()+1;
        if((mon+"").length == 1){
            mon = "0"+mon;
        }
        day = tmp.getDate();
        if((day+"").length == 1){
            day = "0"+day;
        }
        time.id = year+"-"+mon+"-"+day;
        time.text = year+"-"+mon+"-"+day;
        data.push(time);
    }
    return data;
}

/**
 * 侧边栏日期
 */
function WorkTreeLoad() {
    var data = getDate(10);
    var parentTree = '[{"id":"","text":"未寄出设备",children:[';
    var groupList = '{"id":"0","text":"未寄出",children:[';

    $.each( data, function(i, j) {
        groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
    });
    var reg = /,$/gi;
    groupList = groupList.replace(reg, "");
    groupList += ']},';

    groupList += '{"id":"1","text":"已完成",children:[';
    ;
    $.each(data, function(i, j) {
        groupList += '{"id":"' + j.id + '","text":"' + j.text + '"},';
    });
    var reg = /,$/gi;
    groupList = groupList.replace(reg, "");
    groupList += ']}';

    parentTree += groupList+']}]';

    $('#typeTreeTime').tree(
        {
            data : eval(parentTree),
            onClick : function(node){
                $("#tree-Date").val(node.id);
                //如果点击的是已完成或者未寄出，默认是查询所有时间
                if(node.id == '0' || node.id == '1'){
                    //（1已完成、0未寄出）
                    $("#isPacked").combobox('setValue',node.id);
                    //将开始时间和结束时间置空
                    $("#startTime").val("");
                    $("#endTime").val("");
                }else if( node.id == undefined || node.id=="" || node.id==null){
                    //如果点击未寄出设备查询，则清空时间，设置是否装箱为全部
                    $("#isPacked").combobox('setValue',2);
                    $("#startTime").val("");
                    $("#endTime").val("");
                }else{
                    //如果点击的是某个时间，则找到其父节点，并设置是否到装箱
                    var parentNode = $('#tree-Date').tree('getParent',node.target);
                    $("#isPacked").combobox('setValue',parentNode.id);
                    $("#startTime").val(node.id);
                    $("#endTime").val(node.id);
                }
                searchNotSendInfo();
            }
        }
    );
}

/**
 * 根据状态确定工站
 * @param item
 * @param row
 * @returns {string}
 */
function workStationForm(item,row){
    var value;
    //所在工站
    if (row.state == 0){
        value = "受理";
    }else if (row.state == 1){
        value = "分拣";
    }else if (row.state == 2 || row.state == 5 || row.state == 10 || row.state == 11 || row.state == 12 || row.state == 16){
        value = "维修";
    }else if (row.state == 3 || row.state == 9 || row.state == 14){
        value = "报价";
    }else if (row.state == 13){
        value = "终检";
    }else if (row.state == -1){
        value = "已发货";
    }else if (row.state == 15){
        value = "测试";
    }else if (row.state == 4){
        if(row.w_priceState == -1){
            value = "维修";
        }else if(row.w_repairState == -1){
            value = "报价";
        }
    }else if (row.state == 6){
        if(row.w_FinispassFalg == -1){
            value = "维修";
        }else if(row.w_repairState == -1){
            value = "终检";
        }
    }else if (row.state == 7){
        if(row.w_FinispassFalg == -1){
            value = "装箱";
        }else {
            value = "终检";
        }
    }else if (row.state == 8){
        if(row.w_repairState == -1){
            value = "装箱";
        }else {
            value = "维修";
        }
    } else if (row.state == 17){
        value = "受理";
    } else if (row.state == 18){
        value = "分拣";
    } else if (row.state == 19){
        value = "测试";
    }
    return value;
}
function stateForm(value,item){
    // 状态
    if (item.state == "-1") {
        value = "<label style='color:green;font-weight: bold;'>已发货</label>";
    } else if (item.state == "0") {
        value = "<label style='color:red;font-weight: bold;'>已受理</label>";
    } else if (item.state == "1") {
        value = "<label style='color:red;font-weight: bold;'>已受理，待分拣</label>";
    } else if (item.state == "2") {
        value = "<label style='color:red;font-weight: bold;'>已分拣，待维修</label>";
    } else if (item.state == "3") {
        value = "<label style='color:red;font-weight: bold;'>待报价</label>";
    } else if (item.state == "4") {
        value = "<label style='color:red;font-weight: bold;'>已付款，待维修</label>";
    } else if (item.state == "5") {
        value = "<label style='color:red;font-weight: bold;'>已维修，待终检</label>";
    } else if (item.state == "6") {
        value = "<label style='color:red;font-weight: bold;'>已终检，待维修</label>";
    } else if (item.state == "7") {
        value = "<label style='color:red;font-weight: bold;'>已终检，待装箱</label>";
    } else if (item.state == "8") {
        value = "<label style='color:red;font-weight: bold;'>放弃报价，待装箱</label>";
    } else if (item.state == "9") {
        value = "<label style='color:red;font-weight: bold;'>已报价，待付款</label>";
    } else if (item.state == "10") {
        value = "<label style='color:red;font-weight: bold;'>不报价，待维修</label>";
    } else if (item.state == "11") {
        value = "<label style='color:red;font-weight: bold;'>放弃报价，待维修</label>";
    } else if (item.state == "12") {
        value = "<label style='color:red;font-weight: bold;'>已维修，待报价</label>";
    } else if (item.state == "13") {
        value = "<label style='color:red;font-weight: bold;'>待终检</label>";
    } else if (item.state == "14") {
        value = "<label style='color:red;font-weight: bold;'>放弃维修</label>";
    } else if (item.state == "15") {
        value = "<label style='color:red;font-weight: bold;'>测试中</label>";
    } else if (item.state == "16") {
        value = "<label style='color:red;font-weight: bold;'>已测试，待维修</label>";
    } else if (item.state == "17") {
        value = "<label style='color:red;font-weight: bold;'>已受理，已测试</label>";
    } else if (item.state == "18") {
        value = "<label style='color:red;font-weight: bold;'>已测试，待分拣</label>";
    } else if (item.state == "19") {
        value = "<label style='color:red;font-weight: bold;'>不报价，测试中</label>";
    }
    return value;
}

/**
 * 点击批次详情按钮
 */
function clickLook(){
    var row = $("#notSendInfoTable").datagrid('getSelected');
    if(!row){
        $.messager("提示","请选择行","info");
        return;
    }
    initLookWindow(row.repairnNmber);
}
/**
 * 初始化批次详情查看窗口
 * @param value 送修批号
 */
function initLookWindow(value){
    //初始化查询参数
    lookWorkStation='';
    lookEngineer='';
    lookState='';
    windowVisible("lookManage");
    //详情批次
    lookRepairnNmber = value;
    comboboxInit("lookEngineer");
    $('#lookManage').window('open');
    lookDataTableInit();
    repairStateTableInit();
}

var lookRepairnNmber;
var lookWorkStation;
var lookEngineer;
var lookState;

function searchLookData(){
    loadLookQueryData();
    $('#lookDataTable').datagrid('load');

}

/**
 * 加载查询数据
 */
function loadLookQueryData(){
    lookEngineer = $("#lookEngineer").combobox('getValue');
    lookWorkStation = $("#lookWorkStation").combobox("getValue");//工站
    lookState='';//只有点击状态时才查
    if(lookEngineer === '全部'){
        lookEngineer ='';
    }
}

/**
 * 批次详情表格初始化
 */
function lookDataTableInit(){
    $("#lookDataTable").datagrid({
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        pageNumber: 1,
        url:ctx+"/workflowcon/notSendInfo",
        method:'POST',
        singleSelect:false,
        //设置向后台查询时的参数。默认情况下datagrid在选择页数时传递的分页参数为page和rows，这里删除了这两个参数。
        onBeforeLoad : function(param){
            param["repairnNmber"] = lookRepairnNmber;
            param["workStation"] = lookWorkStation;
            param["w_engineer"] = lookEngineer;
            param["state"] = lookState;
            param["pageSize"] = param.rows;
            param["currentpage"] = param.page;
            delete param.rows;
            delete param.page;
        },
        columns:[[
            {title:'受理时间',field:'acceptanceTime',align:'center',width:130},
            {title:'送修批号',field:'repairnNmber',align:'center',width:110},
            {title:'客户名称',field:'w_cusName',align:'center',width:110},
            {title:'IMEI',field:'imei',align:'center',width:120},
            {title:'工站',field:'workStation',align:'center',width:110,formatter:workStationForm},
            {title:'进度',field:'state',align:'center',width:110,formatter:stateForm},
            {title:'主板型号',field:'w_model',align:'center',width:100},
            {title:'市场型号',field:'w_marketModel',align:'center',width:130},
            {title:'维修员',field:'w_engineer',align:'center',width:130},
            {title:'已至装箱',field:'isPacked',align:'center',width:80,formatter:function(value,row) {
                    if ((row.state == 7 && row.w_FinispassFalg === -1) ||
                        (row.state == 8 && row.w_repairState == -1) || row.state==-1) {
                        return  "是";

                    } else {
                        return "否";
                    }
                }},
            {title:'发送终检时间',field:'w_sendFinTime',align:'center',width:80},
            {title:'客户付款日期',field:'w_payTime',align:'center',width:130},
            {title:'发送报价日期',field:'price_createTime',align:'center',width:130},
            {title:'随机附件',field:'w_sjfjDesc',align:'center',width:160},
            {title:'返修次数',field:'returnTimes',align:'center',width:80},
            {title:'保内|保外',field:'isWarranty',align:'center',width:80,formatter:function (value,row) {
                    if(value==0){
                        return '保内';
                    }else{
                        return '保外';
                    }
                }},
            {title:'初检故障',field:'w_cjgzDesc',align:'center',width:160},
            {title:'最终故障',field:'w_zzgzDesc',align:'center',width:200},
            {title:'处理方法',field:'w_solution',align:'center',width:255},
            {title:'智能锁ID',field:'lockId',align:'center',width:80},
            {title:'是否人为',field:'w_isRW',align:'center',width:80,formatter:function(value,row){
                    if(value==0){
                        return '是';
                    }else{
                        return '否';
                    }
                }},
            {title:'维修报价(￥)',field:'w_repairPrice',align:'center',width:100},
            {title:'报价描述',field:'w_wxbjDesc',align:'center',width:200},
            {title:'测试人员',field:'testPerson',align:'center',width:80},
            {title:'测试结果',field:'testResult',align:'center',width:200},
            {title:'送修备注',field:'remark',align:'center',width:160},
            {title:'受理备注',field:'acceptRemark',align:'center',width:225},
            {title:'批次备注',field:'testResult',align:'center',width:225},
            {title:'终检',filed:'w_FinispassFalg',hidden:true},
            {title:'报价状态',filed:'w_priceState',hidden:true},
            {title:'维修状态',filed:'w_repairState',hidden:true},
        ]]
    });
}

/**
 * 状态统计表格初始化
 */
function repairStateTableInit(){
    $("#repairStateTable").datagrid({
        singleSelect : true,
        pagination: false,
        url:ctx + "/workflowcon/getState",
        method:"GET",
        onBeforeLoad : function(param){
            param["repairnNmber"] = lookRepairnNmber;
        },
        onClickRow:function(rowIndex,rowData){
            lookState=rowData.state;
            $("#lookDataTable").datagrid('load');
        },
        columns:[[
            {title:"状态",align:'center',width:250,field:"state",formatter:stateForm},
            {title:"数量",align:'center',width:250,field:"id"}
        ]]
    });
}