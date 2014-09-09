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
  	<s:form action="DocumentForeditor" method="post">
  		<s:hidden name="D_taskid" value="%{#session.D_taskid}"/>
  		<s:hidden name="Document_name" value="%{#session.Document_name}"/>
  		<s:hidden name="Document_content" value="%{#session.Document_content}"/>
  		<s:hidden name="Document_type" value="%{#session.D_type}"/>
  		<div class="description">
  			<h1>${Document_name}</h1>
				${Document_content}
		</div>
		<p>
			审批意见:${D_suggestion}
		</p>
				
		<p>
			<span>审批人:</span> ${D_sendto} <span>||  审批结果:</span> ${ispass==1?"通过":"不通过"}
		</p>
		<div id="footer">
			<p>在 首页->待办任务 中可以查看并重新发起审批 或者 通过 首页->常用流程 中发起新的流程！</p>
		</div>
	</s:form>
  </body>
</html>





<!-- 
<s:form action="viewResultForDocument" method="post">
    	<s:hidden name="D_taskid" value="%{#session.D_taskid}"/>
    	<s:textfield name="D_suggest" value="%{#session.D_suggestion}" label="审批意见"/>
    	<s:textfield name="D_sendto" value="%{#session.D_sendto}" label="审批人"/>
    	<s:textfield name="ispass" value="%{#session.ispass==true?'通过':'不通过'}" label="审批结果"/>
    	<s:submit value="知道了"/>
    </s:form>
 -->