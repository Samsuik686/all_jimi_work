
var startTime;          // 开始日期
var endTime;            // 结束日期
var workType;           // 上班时间
var userType;           // 未寄出/异常反馈
var pageNum;         //分页 页数
var pageSize;           //分页 页大小
var weekDay            //周几
$(function () {
    loadQueryData();
    workDateTableInit();
});
/**
 * 点击查询按钮
 */
function searchWorkDate(){
    loadQueryData()
    // $('#workDateTable').datagrid('load');
    queryListPage(1);
}
//批量插入数据
function batchAdd(){
    //发送请求
    $.ajax({
        type:"GET",
        url:ctx+"/workDate/batchAdd",
        async:false,
        success:function (result) {
            $.messager.alert("操作成功","已插入","info");
        },
        error:function (result) {
            $.messager.alert("操作异常","网络错误","info");
        }
    });
}

/**
 * 工作日表格初始化
 */
function workDateTableInit() {
    $('#workDateTable').datagrid({
        pagination: true,
        pageSize: 31,
        pageList: [7,14,28,31, 62,366],
        pageNumber: 1,
        singleSelect:true,
        url:ctx+"/workDate/getWorkDate",
        method:'GET',
        // onDblClickRow:function(rowIndex,row){initLookWindow(row.repairnNmber);},
        //设置向后台查询时的参数。默认情况下datagrid在选择页数时传递的分页参数为page和rows，这里删除了这两个参数。
        onBeforeLoad : function(param){
            // param["isFinish"] = isFinish;
            if(startTime != "" && startTime!= null && startTime!= undefined) {
                param["startTime"] = startTime;
            }
            if(endTime != "" && endTime!= null && endTime!= undefined) {
                param["endTime"] = endTime;
            }
            // param["repairnNmber"] = repairnNmber;
            param["userType"] = userType;
            param["workType"] = workType;
            param["weekDay"] = weekDay;
            // param["workStation"] = workStation;
            // param["startTime"] = startTime;
            // param["w_engineer"] = w_engineer;
            param["pageSize"] = param.rows;
            param["pageNum"] = param.page;
            delete param.rows;
            delete param.page;
        },
        columns:[[
            {title:'日期',field:'normalDate',align:'center',width:130,formatter:function (value,row) {
                return /\d{4}-\d{1,2}-\d{1,2}/g.exec(value);
            }},
            {title:'类型',field:'userType',align:'center',width:110,formatter:function (value,row) {
                    switch (value) {
                        case 1:return "<label style='color:dodgerblue;font-weight: bold;'>维修未寄出</label>";
                        case 2:return "<label style='color:indigo;font-weight: bold;'>异常反馈</label>";
                    }
            }},
            {title:'星期',field:'weekDay',align:'center',width:110,formatter: function(value,row){
                switch (value) {
                    case 1:return "星期一";
                    case 2:return "星期二";
                    case 3:return "星期三";
                    case 4:return "星期四";
                    case 5:return "星期五";
                    case 6:return "星期六";
                    case 7:return "星期日";
                }
            }},
            {title:'工作状态',field:'workType',align:'center',width:120,formatter:function (value,row) {
                    switch (value) {
                        case 0:return "<label style='color:green;font-weight: bold;'>放假</label>";
                        case 1:return "<label style='color:red;font-weight: bold;'>全天工作</label>";
                        case 2:return "<label style='color:orange;font-weight: bold;'>上午工作</label>";
                        case 3:return "<label style='color:greenyellow;font-weight: bold;'>下午工作</label>";
                    }
                }},
        ]]
    });
}
function loadQueryData(){
    startTime = $("#startTime").val();//开始日期
    endTime = $("#endTime").val();//结束日期
    workType = $("#workType").combobox("getValue");//
    userType = $("#userType").combobox("getValue");//
    weekDay = $("#weekDay").combobox("getValue");
}
/**
 *
 * @param pageNumber  第几页
 */
function queryListPage(pageNumber){

    loadQueryData();

    var options = $("#workDateTable").datagrid("getPager" ).data("pagination" ).options;
    var param={};
    if(startTime != "" && startTime!= null && startTime!= undefined) {
        param.startTime = startTime;
    }
    if(endTime != "" && endTime!= null && endTime!= undefined) {
        param.endTime = endTime;
    }
    param.workType = workType;
    param.userType = userType;
    param.weekDay = weekDay;
    param.pageNum = pageNumber;
    param.pageSize = options.pageSize;
    console.log(startTime);
    //发送请求
    $.ajax({
        type:"GET",
        data: param,
        url:ctx+"/workDate/getWorkDate",
        async:false,
        success:function (result) {
            var data = {};
            data.rows=result.rows;
            data.total=result.total;
            //重新加载表格数据
            $("#workDateTable").datagrid('loadData',data);
            //设置到第一页
            $('#workDateTable').datagrid('getPager').pagination({
                pageNumber:1
            });
        },
        error:function (result) {
            $.messager.alert("操作异常","网络错误","info");
        }
    });
}

/**
 * 修改
 */
function modifyWorkDate(){
    var modifyRow = $("#workDateTable").datagrid('getSelected');
    if(modifyRow == null || modifyRow == undefined){
        $.messager.alert("提示","请先选择一行","info");
        return;
    }
    $("#modifyId").val(modifyRow.id);
    //获取日期
    $("#normalDate").val(modifyRow.normalDate);
    //获取类别
    $("#modifyUserType").combobox('setValue',modifyRow.userType);
    //获取工作状态
    $("#modifyWorkType").combobox('setValue',modifyRow.workType);
    //打开修改窗
    $(".validatebox-tip").remove();
    $(".validatebox-invalid").removeClass("validatebox-invalid");
    $('#modifyWindow').window('open');
    windowVisible("modifyWindow");
}

function doModifyWorkDate() {
    var param = {};
    var date = $("#normalDate").val();
    param.normalDate = /\d{4}-\d{1,2}-\d{1,2}/g.exec(date)[0];
    param.userType = $("#modifyUserType").combobox('getValue');
    param.workType = $("#modifyWorkType").combobox('getValue');
    param.id = $("#modifyId").val();
    $.ajax({
        url:ctx+"/workDate/modifyWorkDate",
        type:"POST",
        data:param,
        async:false,
        success:function (result) {
            $("#modifyWindow").window('close');
            $.messager.alert("操作提示",result.msg,"info");
            $("#workDateTable").datagrid('reload');
        },
        error:function (result) {
            $.messager.alert("操作异常","网络错误","info");
        }
    })
}
function closeWindows(){
    $("#modifyWindow").window('close');
}