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

  </head>
  
  <body>
    <s:form action="Jbpm_Taskgothough" method="post">
    	<s:hidden name="taskId" value="%{#session.taskid}"/>
    	<s:hidden name="executionId" value="%{#session.executionId}"/>
    	<s:textfield name="type" value="%{#session.AskForLeave_type}" readonly="true" label="请假的类型"/>
    	<s:textarea name="reason" value="%{#session.AskForLeave_reason}" readonly="true" label="请假的原因"/>
    	<s:textfield name="begintime" value="%{#session.AskForLeave_begin_time}" readonly="true" label="请假的起始时间"/>
    	<s:textfield name="time" value="%{#session.AskForLeave_time}" readonly="true" label="请假的天数"/>
    	<s:submit name="ISPASS" value="不同意"/>
    	<s:submit name="ISPASS" value="同意"/>
    	
    </s:form>
  </body>
</html>
