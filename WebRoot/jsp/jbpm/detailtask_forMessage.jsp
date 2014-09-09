<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>



<html>
  <head>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	<link rel="stylesheet" href="./jsp/ckeditor/samples/sample.css">
	-->
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
  		<div class="oa_message_main">
  			<h1 style="text-align: center;">${MessageName}</h1>
  			${Message}
		
		<s:if test="%{#session.M_FJUrl!=null}">
			<span>附件：</span><s:a href=".%{#session.M_FJUrl}">${M_FJName}</s:a>
		</s:if>
  		<s:form id="readFm" action="Jbpm_Taskgothough" method="post">
  			<s:hidden name="taskId" value="%{#session.taskid}"/>
    		<s:hidden name="executionId" value="%{#session.executionId}"/>
    		<s:hidden name="userid" value="%{#session.user.getId()}"/>
  		
			<s:if test="%{#session.state == false}">
    			<s:submit value="已阅" />
    			<!-- <a href="#" iconCls="icon-ok" class="easyui-linkbutton" onclick="submitRead()">已阅</a> -->
    		</s:if>
    	</s:form>
    	</div>
		<div id="footer">
		</div>
	
  </body>
  <script type="text/javascript">
  	$(function load(){
	  	function submitRead(){
			$('#readFm').form('submit',{
				url:'Jbpm_Taskgothough',
				success:function(data){
					var data = eval('('+data+')');
		            if(data.success){
		                 $.messager.alert('添加成功提示',"已阅");
		                 
		            }else
		                $.messager.alert('系统提示',data.errorMsg);
				}
			});
		}
	});
  
  </script>
</html>
