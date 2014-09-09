<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
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
	<%@include file="/jsp/menu/s.jsp"%>
	<style type="text/css">
.m-widget-container-2 {
	width: 96%;
	margin-left: 2%;
}

.m-widget-2 {
	margin-top: 0px;
	margin-bottom: 20px;
	border-radius: 3px;
	box-shadow: #E6E6E6 0px 1px 1px 0px;
}

.m-widget-2 .header {
	height: 20px;
	padding: 5px 15px;
	border: 1px solid #C2C2C3;
	padding-left: 10px;
	border-radius: 3px 3px 0px 0px;
	background-color: #CCCCCC;
	box-shadow: 0 1px 0 0 rgba(255, 255, 255, 0.5) inset;
	background: linear-gradient(to bottom, #FAFAFA 0%, #EFEFEF 100%) repeat scroll 0 0 transparent;
	text-shadow: 0 1px 0 #FFFFFF;
	color: #333333;
}

.m-widget-2 .header .title {
	float: left;
	margin: 0px;
	font-size: 14px;
}

.m-widget-2 .header .ctrl {
	float: right;
}

.m-widget-2 .header .ctrl .btn {
	margin: 0px;
	padding-left: 3px;
	padding-right: 3px;
	padding-top: 0px;
	padding-bottom: 0px;
}

.m-widget-2 .content {
	border-left: 1px solid #C2C2C3;
	border-right: 1px solid #C2C2C3;
	border-bottom: 1px solid #C2C2C3;
	border-radius: 0px 0px 3px 3px;
	height: 200px;
	overflow: hide;
}

.m-widget-2 .content.content-inner {
	padding-left: 10px;
	padding-top:10px;
	font-size: 12px;
}

.m-widget-2 .content .m-table {
	margin-top: 0px;
	margin-bottom: 0px;
}
	</style>
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
					<s:iterator value="#session.taskslist" var="task">
					  <tr>
						<td>${task.getName()}</td>
						<td>${task.getCreateTime()}</td>
						<td>
						  <s:a href="Jbpm_getDetailTask?executionId=%{task.getExecutionId()}&taskId=%{task.getId()}&userid=%{#session.user.getId()}">处理</s:a>
						</td>
					  </tr>
					  </s:iterator>
					</tbody>
				  </table>
		</div>
  </body>
</html>
