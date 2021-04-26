<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<% request.setAttribute("num", Math.floor(Math.random()*100)); %> <%-- 随机数通过 ${num} 放在引用的js后面清缓存 --%>
<script type="text/javascript" src="${ctx}/res/js/exam/examGrade.js?20170308"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/ajaxfileupload.js"></script>

</head>
<!-- 考试成绩 -->

<body style="margin:0">
	<div class="easyui-layout"
		style="overflow: hidden; width:100%;height:100%" fit="true" >

		<div region="center" id="dic-center" style="overflow:auto" title="详细信息">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>
                        &nbsp;考试名称:&nbsp;
                        <select id="examId" style="width: 100px;"></select>&nbsp; 
                    </span>&nbsp; &nbsp; &nbsp; &nbsp;
                    <span>
                        &nbsp;审阅状态:&nbsp;
                        <select id="gradeState" style="width: 100px;">
                        	<option value="">全部</option>
                        	<option value="-1">未交卷</option>
                        	<option value="0">未完成</option>
                        	<option value="1">已完成</option>
                        </select>&nbsp; 
                    </span>&nbsp; &nbsp; &nbsp; &nbsp;
					<span>考生姓名: <input type="text" class="form-search" id="examineeName" style="width: 200px" /> 
					</span>&nbsp; &nbsp; &nbsp; &nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>							
								<div class="datagrid-btn-separator"></div>
							</td>	
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
				<thead>
					<tr>
						<!-- <th checkbox="true" field=id></th> -->
						<th field="examName" width="120" align="center" >考试名称</th>
						<th field="examineeName" width="100" align="center" >考生姓名</th>
						<th field="grade" width="100" align="center" editor="text">考生成绩</th>
						<th field="gradeStateText" width="100" align="center" editor="text">评阅状态</th>
						<th field="costTime" width="120" align="center" >考试用时</th>
						<th field="createTime" width="100" align="center" >考试时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>