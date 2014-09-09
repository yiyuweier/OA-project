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
  	<s:form action="DocumentForeditor" method="post">
  		<s:hidden name="D_taskid" value="%{#session.D_taskid}"/>
  		<s:hidden name="Document_name" value="%{#session.Document_name}"/>
  		<s:hidden name="Document_content" value="%{#session.Document_content}"/>
  		<s:hidden name="Document_type" value="%{#session.D_type}"/>
  		<div class="oa_message_main">
  			<h1>${Document_name}</h1>
				${Document_content}
		
			<p>
				审批意见:${D_suggestion}
			</p>
					
			<p>
				<span>审批人:</span> ${D_sendto} <span>||  审批结果:</span> ${ispass==1?"通过":"不通过"}
			</p>
			<div id="footer">
				<input type="submit" value="修改并重新提交"/>
			</div>
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