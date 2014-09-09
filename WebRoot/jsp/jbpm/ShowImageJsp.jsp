<%@page import="org.jbpm.api.Configuration"%>
<%@page import="org.jbpm.api.ProcessEngine" %>
<%@page import="org.jbpm.api.ExecutionService" %>
<%@page import="org.jbpm.api.RepositoryService" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.oa.jbpm.operation.BaseJbpmOperation" %>
<%@ page import="com.oa.jbpm.operation.impl.BaseJbpmOperationimpl" %>
<%@ page import="org.jbpm.api.ProcessInstance" %>
<%@ page import="org.jbpm.api.model.ActivityCoordinates" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <title>流程列表</title>
    <link type="text/css" rel="stylesheet" href="./../../resource/s/bootstrap/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="./../../resource/s/bootstrap/css/bootstrap-responsive.min.css">
    <link type="text/css" rel="stylesheet" href="./../../resource/s/mossle/css/layout3.css" media="screen" />
  </head>
<%
	String id = request.getParameter("flowProcessId");
	System.out.println("jsp页面中的id="+id);
	ActivityCoordinates ac = null;
	ProcessEngine processEngine = Configuration.getProcessEngine();
	ExecutionService executionServie = processEngine.getExecutionService();
	RepositoryService repositoryService = processEngine.getRepositoryService();
	//BaseJbpmOperation basejbpmoperation = new BaseJbpmOperationimpl();
	//BaseJbpmOperationimpl basejbpmoperation = new BaseJbpmOperationimpl();
	if(executionServie==null){
		System.out.println("executionServie is null");
	}else{
		System.out.println("executionServie is not null");
	}
	ProcessInstance pi = executionServie.findProcessInstanceById(id);
	Set<String> activitySet = pi.findActiveActivityNames();
	for(String activityName : activitySet){
		ac = repositoryService.getActivityCoordinates(pi.getProcessDefinitionId(), activityName);
		
	}


 %>
  <body>
    <div class="row-fluid">
	<!-- start of main -->
    <section id="m-main" class="span10" style="float:left;margin-left:40px;">
    

	  <article class="m-widget">
        <header class="header">
		  <h4 class="title">流程图</h4>
		</header>
        <div id="demoSearch" class="content">
		  <img src="ShowFlowImage_viewImage?flowProcessId=<%=request.getParameter("flowProcessId") %>" style="posintion:absolute;left:0px;top:0px;"/>
		</div>
		<div style="position:absolute;border:1px solid red;left:<%=ac.getX()%>;top:<%=ac.getY()%>;width:<%=ac.getWidth()%>;height:<%=ac.getHeight()%>;"></div>
	  </article>

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">列表</h4>
		</header>
		<div class="content">

  <table id="demoGrid" class="m-table table-hover">
    <thead>
      <tr>
        <th class="sorting" name="name">名称</th>
        <th class="sorting" name="startTime">开始时间</th>
        <th class="sorting" name="endTime">结束时间</th>
        <th class="sorting" name="assignee">负责人</th>
        <th class="sorting" name="deleteReason">处理结果</th>
      </tr>
    </thead>

    <tbody>
      <s:iterator value="#session.HistoryActivityInstanceList" var="HistoryActivityInstance">
      <tr>
	    <td>${HistoryActivityInstance.getActivityName()}</td>
	    <td>${HistoryActivityInstance.getStartTime()}</td>
	    <td>${HistoryActivityInstance.getEndTime()}</td>
	    <td></td>
	    <td></td>
      </tr>
      </s:iterator>
    </tbody>
  </table>
        </div>
      </article>

    </section>
	<!-- end of main -->
	</div>

  </body>

<html>

