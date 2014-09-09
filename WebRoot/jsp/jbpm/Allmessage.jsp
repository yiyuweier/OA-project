<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'detailtasks.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="./jsp/ckeditor/ckeditor.js"></script>
	<link rel="stylesheet" href="./jsp/ckeditor/samples/sample.css">

  </head>
  
  <body>
  <div class="description">
  	<table class="m-table table-hover">
			<thead>
			  <tr>
				<th>名称</th>
				<th>创建时间</th>
				<th>&nbsp;</th>
			  </tr>
			</thead>
			<tbody>
			<s:iterator value="#session.announcementlist" begin="0" end="%{#session.announcementlist.size()>5?5:(#session.announcementlist.size()-1)}">
			  <tr>
				<td>${A_name}</td>
				<td>${A_time}</td>
				<td>
				  <s:a href="jsp/jbpm/detailtask_forMessage1.jsp?A_name=%{A_name}&&A_content=%{A_content}&&A_time=%{A_time}">查看</s:a>
				</td>
			  </tr>
			  </s:iterator>
			</tbody>
		  </table>
	</div>
  </body>
</html>
