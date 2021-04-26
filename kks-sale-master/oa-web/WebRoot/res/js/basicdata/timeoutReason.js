var reason;          // 超期原因
$(function () {
    loadQueryData();
    reasonTableInit();
});
/**
 * 点击查询按钮
 */
function searchReason(){
    loadQueryData()
    $('#reasonTable').datagrid('load');
    // queryListPage(1);
}


/**
 * 超期原因表格初始化
 */
function reasonTableInit() {
    $('#reasonTable').datagrid({
        pagination: false,
        singleSelect:true,
        url:ctx+"/timeoutReason/getTimeoutReason",
        method:'GET',
        // onDblClickRow:function(rowIndex,row){initLookWindow(row.repairnNmber);},
        //设置向后台查询时的参数。默认情况下datagrid在选择页数时传递的分页参数为page和rows，这里删除了这两个参数。
        onBeforeLoad : function(param){
            param["reason"] = reason;
            param["pageSize"] = param.rows;
            param["pageNum"] = param.page;
            delete param.rows;
            delete param.page;
        },
        columns:[[
            {title:'ID',field:'id',align:'center',width:130},
            {title:'超期原因',field:'reason',align:'center',width:300},
        ]]
    });
}
function loadQueryData(){
    reason = $("#reason").val();//开始日期
}
/**
 * 根据条件查询未寄出设备数据，侧边栏点击查询调用了该方法
 * @param pageNumber  第几页
 */
function queryListPage(pageNumber){

    loadQueryData();

    var options = $("#reasonTable").datagrid("getPager" ).data("pagination" ).options;
    var param={};
    param.reason = reason;
    param.pageNum = pageNumber;
    param.pageSize = options.pageSize;
    //发送请求
    $.ajax({
        type:"GET",
        data: param,
        url:ctx+"/timeoutReason/getTimeoutReason",
        async:false,
        success:function (result) {
            var data = {};
            data.rows=result.rows;
            data.total=result.total;
            //重新加载表格数据
            $("#reasonTable").datagrid('loadData',data);
            //设置到第一页
            $('#reasonTable').datagrid('getPager').pagination({
                pageNumber:1
            });
        },
        error:function (result) {
            $.messager.alert("操作异常",result.msg,"info");
        }
    });
}

/**
 * 打开修改窗口
 */
function modifyReason(){
    var modifyRow = $("#reasonTable").datagrid('getSelected');
    if(modifyRow == null || modifyRow == undefined){
        $.messager.alert("提示","请先选择一行","info");
        return;
    }
    $("#modifyId").val(modifyRow.id);
    //获取日期
    $("#modifyReason").val(modifyRow.reason);

    $(".validatebox-tip").remove();
    $(".validatebox-invalid").removeClass("validatebox-invalid");
    $('#modifyWindow').window('open');
    windowVisible("modifyWindow");
}

/**
 * 修改超期原因
 */
function doModifyReason() {
    var param = {};
    param.reason = $("#modifyReason").val();
    param.id = $("#modifyId").val();
    $.ajax({
        url:ctx+"/timeoutReason/modifyTimeoutReason",
        type:"POST",
        data:param,
        async:false,
        success:function (result) {
            $("#modifyWindow").window('close');
            $.messager.alert("操作提示",result.msg,"info");
            $("#reasonTable").datagrid('reload');
        },
        error:function (result) {
            $.messager.alert("操作异常","网络错误","info");
        }
    })
}

/**
 * 添加超期原因
 */
function addReason(){
    $(".validatebox-tip").remove();
    $(".validatebox-invalid").removeClass("validatebox-invalid");
    $('#addWindow').window('open');
    windowVisible("addWindow");
}

/**
 * 添加超期原因
 */
function doAddReason() {
    var param = {};
    param.reason = $("#addReason").val();
    $.ajax({
        url:ctx+"/timeoutReason/addTimeoutReason",
        type:"POST",
        data:param,
        async:false,
        success:function (result) {
            $("#modifyWindow").window('close');
            $.messager.alert("操作提示",result.msg,"info");
            $("#reasonTable").datagrid('reload');
        },
        error:function (result) {
            $.messager.alert("操作异常","网络错误","info");
        }
    })
}
/**
 * 删除超期原因
 */
function removeReason(){
    var modifyRow = $("#reasonTable").datagrid('getSelected');
    if(modifyRow == null || modifyRow == undefined){
        $.messager.alert("提示","请先选择一行","info");
        return;
    }else{
        $.messager.confirm("提示","是否删除","info",function (conf) {
            if(conf) {
                var param = {};
                param.id = modifyRow.id;
                $.ajax({
                    url: ctx + "/timeoutReason/removeTimeoutReason",
                    type: "POST",
                    data: param,
                    async: false,
                    success: function (result) {
                        $.messager.alert("操作提示", result.msg, "info");
                        $("#reasonTable").datagrid('reload');
                    },
                    error: function (result) {
                        $.messager.alert("操作异常", "网络错误", "info");
                    }
                });
            }
        })
    }
}
function closeWindows(id){
    $("#"+id).window('close');
}