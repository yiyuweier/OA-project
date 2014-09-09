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
  	<s:form action="Jbpm_Taskgothough" method="post">
  		<s:hidden name="taskId" value="%{#session.taskid}"/>
    	<s:hidden name="executionId" value="%{#session.executionId}"/>
    	<s:hidden name="userid" value="%{#session.user.getId()}"/>
  		<div class="description">
  			<h1 style="text-align: center;">${Document_name}</h1>
  			${Document_content}
  			<!-- 
  			<p>
			<textarea class="ckeditor" cols="80" id="editor1" name="editor1" rows="10">
				${Document_content}
			</textarea>
		</p>
		 -->
		</div>
		<p>附件：<s:a href=".%{#session.D_FJUrl}">${D_FJName}</s:a></p>
		<div id="footer">
		</div>
	</s:form>
  </body>
</html>
