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
	<script type="text/javascript" src="./jsp/ckeditor/ckeditor.js"></script>
	<link rel="stylesheet" href="./jsp/ckeditor/samples/sample.css">

  </head>
  <%
  	String flowName = request.getParameter("flowname");
  	String imageName = null;
  	if(flowName.equals("公文审批")){
  		imageName = "gwsp";
  	}else if(flowName.equals("请假流程")){
  		imageName = "askforleave";
  	}
  
   %>
  
  <body>
  	
  		<div class="description" style="text-align: center;">
  			<img alt="<%=flowName%>" src="../../resource/flowimage/<%=imageName%>.png"/>
		</div>
		<div id="footer">
		</div>
  </body>
</html>
