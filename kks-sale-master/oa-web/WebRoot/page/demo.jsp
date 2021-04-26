<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<jsp:include page="/page/comm.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>DEMO</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function test(){
			window.location.href=ctx+"/main.jsp";
		}
	</script>

  </head>
  
  <body>
  <input type="button" value="测试" onclick="test()">
     	Menus<br/>
     	查询所有<br/>
     	<table>
     		<tr>
     			<td>menuId</td>
     			<td>parentId</td>
     			<td>displayName</td>
     			<td>url</td>
     			<td>descrp</td>
     			<td>sn</td>
     		</tr>
     		<c:forEach var="menu" items="${menus}">
     			<tr>
	     			<td>${menu.menuId }</td>
	     			<td>${menu.parentId}</td>
	     			<td>${menu.displayName}</td>
	     			<td>${menu.url}</td>
	     			<td>${menu.descrp}</td>
	     			<td>${menu.sn}</td>
     			</tr>
     		</c:forEach>
     		
     		
     	</table>
  </body>
</html>
