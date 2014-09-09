<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.oa.bean.User" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'AskForLeave.jsp' starting page</title>
    <%@include file="/jsp/menu/s.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <%
  	User user = (User)request.getSession().getAttribute("user");
  	Long userid = user.getId();
  	request.setAttribute("userid", userid);
  	
  
   %>
  <body>
    <s:form action="startProcessInstance_Message" method="post">
    	<s:hidden id="userid" name="userid" value="%{#request.userid}"/>
    	<s:textfield id="messagename" name="messagename" label="消息名称"/>
    	<s:textarea id="messagecontent" name="messagecontent" label="消息内容"/>
    	<s:textfield id="sendto" name="sendto" label="审批人" readonly="true" title="<点击此处选择审批人>" value="<点击此处选择审批人>" onclick="openNewDiv('userPicker');return false;"/>
    	<s:submit value="发送消息"/>
    </s:form>
    
    
    
    <div id="userPicker" class="modal hide fade in" style="display: none;" aria-hidden="false">
  		<div class="modal-header">
    		<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeNewDiv('userPicker')">×</button>
    		<h3>选择用户</h3>
  		</div>
  		<div class="modal-body">
      		<article class="m-widget">
        		<header class="header">
		  			<h4 class="title">用户列表</h4>
		  			<input id="selectedItem" type="radio" class="selectedItem" name="selectedItem" value="0" style="float: right">所有人
				</header>
				<div class="content">
					
  						<table id="userPickerGrid" class="m-table table-hover">
    						<s:iterator value="#session.departmentlist">
    						<thead>
      							<tr>
        							<th width="10" class="m-table-check">&nbsp;</th>
        							<th>${D_name}</th>
      							</tr>
    						</thead>
    						<tbody id="userPickerBody">
    						<s:iterator value="users">
    							<tr>
    								<td>
    									<input id="selectedItem${id}" type="radio" class="selectedItem" name="selectedItem" value="${id}">
    								</td>
    								<td>${U_name}</td>
    							</tr>
    						</s:iterator>
    						</tbody>
    						</s:iterator>
  						</table>
        		</div>
      		</article>
  		</div>
  		<div class="modal-footer">
    			<span id="userPickerResult"></span>
    			<a id="userPickerBtnClose" href="" class="btn" onclick="closeNewDiv('userPicker')">关闭</a>
    			<input id="userPickerBtnSelect" class="btn btn-primary" type="button" value="选择" onclick="Adduser()"/>
  		</div>
	</div>
  </body>
  
  
  <script type="text/javascript" charset="utf-8">
  	function Adduser(){
  		alert("here");
  		var radioElements = document.getElementsByName("selectedItem");
  		for(var i=0;i<radioElements.length;i++){
  			if(radioElements[i].checked){
  				document.getElementById("sendto").value = radioElements[i].value;
  			}
  		}
  		closeNewDiv("userPicker");	
  	}
  	
  	function closeNewDiv(id){
  		var newDiv = document.getElementById(id);
   		newDiv.style.display = "none";
  	}
  	var docEle = function() {
   		return document.getElementById(arguments[0]) || false;
	}
	function openNewDiv(id) {
    	//新激活图层
   		var newDiv = document.getElementById(id);
   		newDiv.style.display = "block";
   		newDiv.style.position = "absolute";
   		newDiv.style.zIndex = "9999";
   		newDiv.style.width = "400px";
   		newDiv.style.height = "300px";
   		newDiv.style.top = "100px";
   		newDiv.style.left = (parseInt(document.body.scrollWidth) - 300) / 2 + "px"; // 屏幕居中
   		newDiv.style.background = "EEEEEE";
   		newDiv.style.border = "1px solid #0066cc";
   		newDiv.style.padding = "5px";
   		newDiv.style.overflow = "auto";
   		
   		
   	
   		// mask图层
   		//var newMask = document.createElement("div");
   		//newMask.id = m;
   		//newMask.style.position = "absolute";
   		//newMask.style.zIndex = "1";
   		//newMask.style.width = document.body.scrollWidth + "px";
   		//newMask.style.height = document.body.scrollHeight + "px";
   		//newMask.style.top = "0px";
   		//newMask.style.left = "0px";
   		//newMask.style.background = "#000";
   		//newMask.style.filter = "alpha(opacity=40)";
   		//newMask.style.opacity = "0.40";
   		//document.body.appendChild(newMask);
   		// 关闭mask和新图层
   		//var newA = document.createElement("a");
   		//newA.href = "#";
   		//newA.innerHTML = "关闭激活层";
   		//newA.onclick = function() {
    		//document.body.removeChild(docEle(_id));
    		//document.body.removeChild(docEle(m));
    		//return false;
   		//}
   		//newDiv.appendChild(newA);
   
    	return false;
   }
   
  </script>
</html>



<!-- 
<title>JavaScript 弹出层，背景变暗</title>
<script>
var docEle = function() {
   return document.getElementById(arguments[0]) || false;
}
function openNewDiv(_id) {
   var m = "mask";
   if (docEle(_id)) document.removeChild(docEle(_id));
   if (docEle(m)) document.removeChild(docEle(m));
    //新激活图层
   var newDiv = document.createElement("div");
   newDiv.id = _id;
   newDiv.style.position = "absolute";
   newDiv.style.zIndex = "9999";
   newDiv.style.width = "200px";
   newDiv.style.height = "300px";
   newDiv.style.top = "100px";
   newDiv.style.left = (parseInt(document.body.scrollWidth) - 300) / 2 + "px"; // 屏幕居中
   newDiv.style.background = "EEEEEE";
   newDiv.style.border = "1px solid #0066cc";
   newDiv.style.padding = "5px";
   newDiv.innerHTML = "新激活图层内容";
   document.body.appendChild(newDiv);
   // mask图层
   var newMask = document.createElement("div");
   newMask.id = m;
   newMask.style.position = "absolute";
   newMask.style.zIndex = "1";
   newMask.style.width = document.body.scrollWidth + "px";
   newMask.style.height = document.body.scrollHeight + "px";
   newMask.style.top = "0px";
   newMask.style.left = "0px";
   newMask.style.background = "#000";
   newMask.style.filter = "alpha(opacity=40)";
   newMask.style.opacity = "0.40";
   document.body.appendChild(newMask);
   // 关闭mask和新图层
   var newA = document.createElement("a");
   newA.href = "#";
   newA.innerHTML = "关闭激活层";
   newA.onclick = function() {
    document.body.removeChild(docEle(_id));
    document.body.removeChild(docEle(m));
    return false;
   }
   newDiv.appendChild(newA);
}
</script>
<a href="#" onclick="openNewDiv('newDiv');return false;">弹出新层</a>

 -->