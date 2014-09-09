<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>岗位列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="./jsp/SysManage/js/jquery.js"></script>
    <script language="javascript" src="./jsp/SysManage/js/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="./jsp/SysManage/js/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="./jsp/SysManage/css/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="./jsp/SysManage/images/title_arrow.gif"/> 待办任务
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            	<td width="200px">ID</td>
            	<td width="150px">事件</td>
                <td width="300px">时间</td>
                <td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer">
        	<s:iterator value="#session.taskslist" var="task">
        	<s:hidden value="%{id}" name="id"/>
			<tr class="TableDetail1 template">
				<td>${task.getId()}</td>
				<td><s:a href="Jbpm_getDetailTask?executionId=%{task.getExecutionId()}&taskId=%{task.getId()}&userid=%{#session.user.getId()}">${task.getName()}</s:a></td>
				<td>${task.getCreateTime()}</td>
				<td>
					<s:a href="./jsp/jbpm/ShowImageJsp.jsp?flowProcessId=%{task.getExecutionId()}">查看流程图</s:a>
				</td>
			</tr>
			</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    
</div>
<div id="Description"> 
</div>
</body>
</html>

