<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/exam/examInfo.js?20170308"></script>
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
	<div class="easyui-layout"
		style="overflow: hidden; width:100%;height:100%" fit="true" >

		<div region="center" id="dic-center" style="overflow:auto" title="详细信息">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>创建者: <input type="text" class="form-search" id="creater" style="width: 200px" /> 
					</span>&nbsp; &nbsp; &nbsp; &nbsp;
					<span>考试名称: <input type="text" class="form-search" id="examName" style="width: 200px" /> 
					</span>&nbsp; &nbsp; &nbsp; &nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="exam_add();">新增考试</a>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="exam_start();">开始考试</a>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="exam_stop();">结束考试</a>
							</td>
						    <td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="exam_del();">删除考试</a>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field=id></th>
						<th field="examName" width="120" align="center" >考试名称</th>
						<th field="examDescription" width="320" align="center" editor="text">考试描述</th>
						<th field="examTotalTime" width="100" align="center" >考试时长</th>
						<th field="creater" width="100" align="center" editor="text">创建人</th>
						<th field="createTime" width="120" align="center" >创建时间</th>
						<th field="stateText" width="100" align="center" >状态</th>
						<th field="preview" width="100" align="center" >操作</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 新增考试 Start -->
		<div style="position:fixed;">
			<div id="exam_add" class="easyui-window" title="新增考试" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
				style="visibility:hidden;width: 500px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="exam_form" action="">
							<input type="hidden" id="id"/>
							<input type="hidden" id="currentName" value="${USERSESSION.uName}"/>
							<table align="center" width="100%" cellpadding="2"> 
								<tr>    
									<td align="right"><label class="font_color">*</label>考试名称：</td>
									<td>
										<input type="text" id="examName_add" class="easyui-validatebox"  data-options="required:true,validType:['length[0,20]']" style="width: 200px;"/>
									</td>
							     </tr>
							     <tr>       
									<td align="right"><label class="font_color">*</label>考试时长：</td>
									<td>
										<input type="text" id="examTotalTime_add" class="easyui-validatebox"  data-options="required:true,validType:['Number','examTime[20,240]']" style="width: 200px;"/>
										<font>分钟</font>
									</td>
							     </tr> 
							     <tr>       
									<td align="right"><label class="font_color">*</label>考试描述：</td>
									<td>
										<textarea type="text" id="examDescription_add" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']" style="width: 200px;"></textarea>
									</td>
							     </tr>    
							     <tr>       
									<td align="right" id="fileTitle"><label class="font_color">*</label>上传文件：</td>
									<td>
							     		<div id="fileInfoDiv" class="fileInfo" >
							     		 	
							     		 </div>
							     		<span id="fileSpan"> 
								     		<input id="examTopicFile" class="easyui-validatebox" data-options="required:true"  name="examTopicFile" type="file" style="width: 200px;">							     		
							     		</span>						     		 
							     	</td>
							     	<td>
										<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportTemplate();">下载模板</a>
									</td>
							     </tr>
							     <tr>       
									<td align="right">上传人：</td>
									<td>
										<input type="text" id="creater_add" disabled="disabled" value="" style="width: 200px;"/>
									</td>
							     </tr>
							  </table>
						</form>
						<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
							<a id="saveButton" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveExam()">保存</a> 
							<a id="close" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">关闭</a>
						</div>
					</div> 
				</div>
			</div>
		</div>
		<!-- 新增考试 END -->
		
		<!-- 预览试题 START -->
        <div style="position:fixed;">
            <div id="exam_preview" class="easyui-window" title="开始答题" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
                style="visibility:hidden;width: 750px; height: 500px; padding: 5px;">

			    <div id="topicTabs" class="easyui-tabs" style="width:710px;height:350px;" >
			        <div id="select_topic" title="选择题" style="padding:10px">
			            <div id="topicContent1">
				            <div class="topic-desc">
				                <span id="select_topic_desc">1、下面哪个是正常驾驶行为?</span>
				            </div>
				            <!-- 多选 -->
				            <div id="multiple_select" class="topic-desc" style="display:none;">
					            <div>
					                <input type="checkbox" name ="multipleSelect" value="A"/><span> A:</span>
					                <span id="multiple_select_option_a">闯红灯</span><br>
					            </div >
					            <div>
					                <input type="checkbox" name ="multipleSelect" value="B"/><span> B:</span>
					                <span id="multiple_select_option_b">开车100KM/H在街道</span><br>
					            </div>
					            <div>
					                <input type="checkbox" name ="multipleSelect" value="C"/><span> C:</span>
					                <span id="multiple_select_option_c">遇斑马线停车等待</span><br>
					            </div>
					            <div>
					                <input type="checkbox" name ="multipleSelect" value="D"/><span> D:</span>
					                <span id="multiple_select_option_d">开车打电话</span><br>
					            </div>
	                        </div>
	                        <!-- 单选 -->
	                        <div id="single_select" class="topic-desc" >
	                            <div>
	                                <input type="radio" name ="singleSelect" value="A"/><span> A:</span>
	                                <span id="single_select_option_a">闯红灯</span><br>
	                            </div >
	                            <div>
	                                <input type="radio" name ="singleSelect" value="B"/><span> B:</span>
	                                <span id="single_select_option_b">开车100KM/H在街道</span><br>
	                            </div>
	                            <div>
	                                <input type="radio" name ="singleSelect" value="C"/><span> C:</span>
	                                <span id="single_select_option_c">遇斑马线停车等待</span><br>
	                            </div>
	                            <div>
	                                <input type="radio" name ="singleSelect" value="D"/><span> D:</span>
	                                <span id="single_select_option_d">开车打电话</span><br>
	                            </div>
	                        </div>
                        </div>
			        </div>
			        <div id="judge_topic" title="判断题" style="padding:10px">
			            <div id="topicContent2">
				            <div class="topic-desc">
	                            <span id="judge_topic_desc">1、label 元素不会向用户呈现任何特殊效果。</span>
	                        </div>
	                        <div class="topic-desc">
	                            <div>
	                                <input type="radio" name ="judgeSelect" value="A"/>
	                                <span>正确</span><br>
	                            </div >
	                            <div>
	                                <input type="radio" name ="judgeSelect" value="B"/>
	                                <span>错误</span><br>
	                            </div>
	                        </div>
                        </div>
			        </div>
			        <div id="fill_topic" title="填空题" style="padding:10px">
			            <div id="topicContent3">
				            <div class="topic-desc">
	                            <span id="fill_topic_desc">1、label 元素不会向用户呈现任何______。</span>
	                        </div>
	                        <div class="topic-desc">
	                            <input id="fillAnswer" type="text">
	                        </div>
                        </div>
			        </div>
			        <div id="answer_topic" title="问答题" style="padding:10px">
			            <div id="topicContent4">
	                        <div class="topic-desc">
	                            <span id="answer_topic_desc">1、label 元素不会向用户呈现任何______。</span>
	                        </div>
	                        <div class="topic-desc">
		                        <textarea id="answerAnswer" rows="10" cols="80"></textarea>
	                        </div>
                        </div>
                    </div>


			    </div>
                <div id="count" style="text-align:center;padding:10px;width:680px;height:25px;float: left;">
                     <a href="javascript:void(0)" style="margin-right:40px;" iconCls="icon-back" class="easyui-linkbutton" onclick="back()">上一题</a>
                     <span id="currentTopic">1</span>/<span id="totalTopic">100</span>
                     <a href="javascript:void(0)" style="margin-left:40px;" class="easyui-linkbutton" iconCls="icon-next" iconAlign="right" onclick="next()">下一题</a>
                </div>
            </div>
        </div>
		<!-- 预览试题 END -->
		
		
		
	</div>

</body>
</html>