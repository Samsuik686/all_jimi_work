<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/exam/markScore.js?20190705"></script>
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

</style>
</head>
<!-- 考试信息 -->

<body style="margin:0">
    <div class="easyui-layout" style="overflow: hidden; width:100%;height:100%" fit="true" >
        <div region="center" id="dic-center" style="overflow:auto" title="详细信息">
            <div id="toolbar" style="padding: 10px 15px;">
                <div style="margin-bottom: 5px">
                    <span>考试名称: <input type="text" class="form-search" id="searchExam" style="width: 200px" /> 
                    </span>&nbsp; &nbsp; &nbsp; &nbsp;
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="queryListPage();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshExam();">刷新</a>
                </div>
            </div>
            <table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
                <thead>
                    <tr>
<!--                         <th checkbox="true" field=id></th> -->
                        <th field="examName" width="120" align="center" >考试名称</th>
                        <th field="examDescription" width="320" align="center" editor="text">考试描述</th>
                        <th field="examTotalTime" width="100" align="center" >考试时长</th>
                        <th field="stateText" width="100" align="center" >考试状态</th>
                        <th field="progressText" width="100" align="center" >批阅进度</th>
                        <th field="preview" width="100" align="center" >操作</th>
                    </tr>
                </thead>
            </table>
        </div>
        
        <!-- 选择考生 -->
        <div style="position:fixed;">
            <div id="exam_chooseExaminee" class="easyui-window" title="选择考生" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
                style="visibility:hidden;width: 1000px; height: 500px; padding: 5px;">
		        <div id="toolbar2" style="padding: 10px 15px;">
	                <div style="margin-bottom: 5px">
	                    <span>考生名称: <input type="text" class="form-search" id="searchExaminee" style="width: 200px" /> 
	                    </span>&nbsp; &nbsp; &nbsp; &nbsp;
	                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"  onclick="queryExamineeListPage();">查询</a>
	                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshExaminee();">刷新</a>
	                </div>
	            </div>
	            <table id="DataGrid2" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar2">
	                <thead>
	                    <tr>
<!-- 	                        <th checkbox="true" field=id></th> -->
	                        <th field="examineeid" hidden="true"></th>
	                        <th field="examineeName" width="100" align="center" >考生名称</th>
	                        <th field="stateText" width="100" align="center" >评阅状态</th>
	                        <th field="preview" width="100" align="center" >操作</th>
	                    </tr>
	                </thead>
	            </table>
            </div>
        </div>
        
        <!-- 开始评阅 -->
        <div style="position:fixed;">
            <div id="exam_markScore" class="easyui-window" title="开始答题" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
                style="visibility:hidden;width: 1000px; height: 500px; padding: 5px;">

                <div id="topicTabs" class="easyui-tabs" style="width:710px;height:370px;float:left;" >
                    <div id="fill_topic" title="填空题" style="padding:10px">
                        <div id="topicContent1">
                            <div class="topic-desc">
                                <span id="fill_topic_desc">1、label 元素不会向用户呈现任何______。</span>
                            </div>
                            <div class="topic-desc">
                                <span>参考答案：</span>
                                <span id="fillReference"></span>
                            </div>
                            <div class="topic-desc">
                                <span>考生答案：</span>
                                <input id="fillAnswer" type="text" disabled>
                            </div>
                        </div>
                    </div>
                    <div id="answer_topic" title="问答题" style="padding:10px">
                        <div id="topicContent2">
                            <div class="topic-desc">
                                <span id="answer_topic_desc">1、label 元素不会向用户呈现任何______。</span>
                            </div>
                            <div class="topic-desc">
                                <span>参考答案：</span><br>
                                <span id="answerReference"></span>
                            </div>
                            <div class="topic-desc">
                                <span>考生答案：</span><br>
                                <textarea id="answerAnswer" rows="8" cols="75" disabled></textarea>
                            </div>

                        </div>
                    </div>

                </div>
                <div id="mark" class="topic-desc" style="text-align:left;padding:10px;width:230px;height:25px;float:right;margin-top:40px;">
                    <div style="padding:10px">
	                    <span>本题得分：</span>
	                    <input id="score" type="text" style="width:60px" class="easyui-validatebox" validType="Number">
	                </div>
                    <div style="padding:10px">
                         <a href="javascript:void(0)" id="minScore" style="float:left;" class="easyui-linkbutton" iconCls="icon-remove" onclick="">零分</a>
                         <a href="javascript:void(0)" id="maxScore" style="float:right;" class="easyui-linkbutton" iconCls="icon-add" onclick="">满分</a>
                    </div>
                </div>
                
                <div id="count" style="text-align:center;padding:10px;width:680px;height:25px;float:left;">
                    <a href="javascript:void(0)" style="margin-right:40px;" iconCls="icon-back" class="easyui-linkbutton" onclick="back()">上一题</a>
                    <span id="currentTopic">1</span>/<span id="totalTopic">100</span>
                    <a href="javascript:void(0)" style="margin-left:40px;" class="easyui-linkbutton" iconCls="icon-next" iconAlign="right" onclick="next()">下一题</a>
                </div>
                
                <div id="submit" style="text-align:center;padding:10px;width:230px;height:25px;float:right;">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveScore()">提交得分</a>
                </div>
            </div>
        </div>
        
    </div>

</body>
</html>