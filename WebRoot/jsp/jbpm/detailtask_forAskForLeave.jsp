<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.oa.bean.User"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>请假</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

 <link href="./jqeasyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="./jqeasyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./jqeasyui/jquery.js"></script>
<script type="text/javascript" src="./jqeasyui/jquery.easyui.js"></script>
<style>
	body{
		font-size:13px;
		line-height:20px;
	}
	td{
		padding:2px;
	}
</style>
</head>

<%
	User user = (User) request.getSession().getAttribute("user");
	Long userid = user.getId();
	request.setAttribute("userid", userid);
%>
<body>


	<div class="easyui-panel" title="<span>【${beginUserName}】</span>请假"
		style="width:650px;height:350px;padding:5px;">
		<form action="Jbpm_Taskgothough" method="post">
			<s:hidden name="taskId" value="%{#session.taskid}" />
			<s:hidden name="executionId" value="%{#session.executionId}" />

			<!-- 表单内容显示 -->


			<table cellpadding="0" cellspacing="0" class="mainForm">
				<tr>
					<td><s:textfield name="type"
							value="%{#session.AskForLeave_type}" readonly="true"
							label="请假的类型"  style="width:500px;"/></td>
				</tr>
				<tr>
					<td><s:textarea name="reason"
							value="%{#session.AskForLeave_reason}" readonly="true"
							label="请假的原因" cssStyle="width:500px;height:120px;" /></td>
				</tr>
				<tr>
					<td><s:textfield name="begintime"
							value="%{#session.AskForLeave_begin_time}" readonly="true"
							label="请假的起始时间" /></td>
				</tr>
				<tr>
					<td><s:textfield name="time"
							value="%{#session.AskForLeave_time}" readonly="true"
							label="请假的天数" /></td>
				</tr>
				<tr>
				<td style="padding-top:20px;text-align:center; ">
					<input type="submit" name="ISPASS"  value="不同意" /> <input
					type="submit" name="ISPASS"   value="同意" />
					 
			</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>

