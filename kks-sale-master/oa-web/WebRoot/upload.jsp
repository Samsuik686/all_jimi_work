<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
<body>
<font color="red">可直接发布zip文件</font><br />
<form name="form1" method="post" action="ReleaseJBPM">
<label>
发布流程文件 
<input type="file" name="processDef" onchange="readFile(this)">
</label> 
<label> 
<input type="submit" name="Submit" value="提交">
</label>
<script type="text/javascript">
	function readFile(obj) {
		document.getElementById("file").value = obj.value;
	}
</script>
</form>
</body>
</html>
