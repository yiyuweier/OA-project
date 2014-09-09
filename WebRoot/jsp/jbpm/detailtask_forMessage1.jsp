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
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="./jsp/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" href="./jsp/ckeditor/samples/sample.css">

</head>
<style>
body {
	margin: 0px;
	padding: 0px;
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
<body>

	<s:form action="Jbpm_Taskgothough" method="post">
		<div class="oa_message_main">
			<div class="oa_message_content">
				<h1 style="text-align: center;">${A_name}</h1>
				${A_content}
			</div>
			<div id="footer">
				
					<span>附件：</span>
					<s:a href=".%{#session.A_FJUrl}">${A_FJName}</s:a>
				
			</div>
		</div>
	</s:form>

</body>
</html>
