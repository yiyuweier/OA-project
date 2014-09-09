<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
 
<script type="text/javascript" src="./jsp/ckeditor/ckeditor.js"></script>

<style>
body {
	margin: 0px;
	padding: 0px;
	font-size:13px;
	color:#222;
}

.oa_message_main {
	width: 850px;
	margin: auto;
	overflow: hidden;
}

.oa_message_content {
	font-size: 13px;
	color: #222;
}

.oa_message_content p {
	text-indent: 2em;
}
</style>
</head>

<body>
	<s:form action="Jbpm_Taskgothough" method="post">
		<s:hidden name="taskId" value="%{#session.taskid}" />
		<s:hidden name="executionId" value="%{#session.executionId}" />
		<s:hidden name="userid" value="%{#session.user.getId()}" />

		<div class="oa_message_main">
			<div class="oa_message_content">
				<h1 style="text-align: center;">${Document_name}</h1>
				${Document_content}
				意见：<textarea type="text" name="suggestion" id="suggestion" style="width: 845px;height: 122px;"></textarea>
			</div>
			<div id="footer">
				附件：
				<s:a href=".%{#session.D_FJUrl}">${D_FJName}</s:a>
			</div>
			<p>
				<input type="submit" name="ISPASS" value="同意"> 
				<input type="submit" name="ISPASS" value="不同意">
			</p> 
		</div>

		
	</s:form>
</body>
</html>
