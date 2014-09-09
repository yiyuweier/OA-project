<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.oa.bean.User"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>发起请假流程</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript" src="./resource/js/jquery.js"></script>
<script language="javascript" src="./My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="./resource/js/pageCommon.js"
	charset="utf-8"></script>
<script language="javascript" src="./resource/js/PageUtils.js"
	charset="utf-8"></script>
<link type="text/css" rel="stylesheet"
	href="./resource/css/pageCommon.css" />

<link href="./jqeasyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="./jqeasyui/themes/default/easyui.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="./jqeasyui/jquery.js"></script>
<script type="text/javascript" src="./jqeasyui/jquery.easyui.js"></script>
<script type="text/javascript"
	src="./jqeasyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	
<%@include file="/jsp/menu/s.jsp"%>
	
</script>
<style>
body {
	padding-top: 5px;
	padding-left:5px;
	font-size: 13px;
	color: #222;
	line-height: 20px;
}

#oa_askleave td {
	padding: 2px;
}
</style>
</head>

<%
	User user = (User) request.getSession().getAttribute("user");
	Long userid = user.getId();
	request.setAttribute("userid", userid);
%>
<body>

	<!-- 标题显示 -->

	<div style="width:500px;height:300px;float:left;clear:both;">
		<div data-options="fit:true" title="发起请假流程" class="easyui-panel"
			id="oa_askleave" style="padding:5px;">

			<form action="startProcessInstance_askForLeave" method="post"
				id="myform">
				<s:hidden id="userid" name="userid" value="%{#request.userid}" />

				<table cellpadding="0" cellspacing="0"
					style="border:none; width:98%;">

					<tr>
						<td><s:radio list='#{"1":"婚假", "2":"产假", "3":"病假","4":"倒休"}'
								id="selecttype" name="selecttype" label="请假类型" /></td>
					</tr>
					<tr>
						<td><s:textfield id="begintime" name="begintime"
								label="请假起始时间" onClick="WdatePicker()" style="width:240px;" /></td>
					</tr>
					<tr>
						<td><s:select list="{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,'1~2个月','2~3个月','3~4个月','4个月以上'}" id="time" name="time" style="width:200px;"
								label="天数" /></td>
					</tr>
					<tr>
						<td><s:textfield id="sendto" name="sendto" label="审批人" style="width:240px;"
								readonly="true" title="<点击此处选择审批人>" value="<点击此处选择审批人>"
								onclick="$('#selectUsers').window('open');return false;" /></td>
					</tr>
					<tr>
						<td><s:textarea id="reason" name="reason" label="请假原因"
								cols="60" rows="5" /></td>
					</tr>
					<tr>
						<td colspan=2 style="padding-top:10px; text-align:center;"><a
							class="easyui-linkbutton" iconCls="icon-ok" onclick="save()"
							href="#">提交</a> <a class="easyui-linkbutton"
							iconCls="icon-cancel" onclick="javascript:history.go(-1);"
							href="#">返回</a>
					</tr>
				</table>


			</form>
		</div>
	</div>
	</div>
	<div class="Description">
		说明：<br /> 1，上级部门的列表是有层次结构的（树形）。<br />
		2，如果是修改：上级部门列表中不能显示当前修改的部门及其子孙部门。因为不能选择自已或自已的子部门作为上级部门。<br />
	</div>



	<div id="selectUsers"  class="easyui-window" closed="true" resizable="false"
    style="width: 350px; height: 400px; padding: 10px 5px;"  maximizable="false"
    modal="true" title="选择用户" minimizable="false" collapsible="false">
		<div class="modal-body">
			<article class="m-widget"> <header class="header">
			<h4 class="title">用户列表</h4>
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
									<td><input id="selectedItem${id}" type="radio"
										class="selectedItem" name="selectedItem"
										value="${id}:${U_name}"></td>
									<td>${U_name}</td>
								</tr>
							</s:iterator>
						</tbody>
					</s:iterator>
				</table>
			</div>
			</article>
		</div>

		  <a href="#"  class="easyui-linkbutton" iconCls="icon-ok" onclick="Adduser()"  >选择</a>
	</div>



</body>
<script type="text/javascript" charset="utf-8">
	function Adduser() { 
		var radioElements = document.getElementsByName("selectedItem");
		for ( var i = 0; i < radioElements.length; i++) {
			if (radioElements[i].checked) {
				document.getElementById("sendto").value = radioElements[i].value;
			}
		}
		$('#selectUsers').window('close');
	}

	function closeNewDiv(id) {
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
		newDiv.style.left = (parseInt(document.body.scrollWidth) - 300) / 2
				+ "px"; // 屏幕居中
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

	function save() {
		var reason = document.getElementById("reason").value;
		if (reason == null || reason == "") {
			alert("请假原因为空，请填写请假原因！");
			return false;
		}
		var uniform_selecttype1 = document.getElementById("selecttype1");
		var uniform_selecttype2 = document.getElementById("selecttype2");
		var uniform_selecttype3 = document.getElementById("selecttype3");
		var uniform_selecttype4 = document.getElementById("selecttype4");
		if (!uniform_selecttype1.checked && !uniform_selecttype2.checked
				&& !uniform_selecttype3.checked && !uniform_selecttype4.checked) {
			alert("请假类型不能为空，请选择请假类型！");
			return false;
		}
		var begintime = document.getElementById("begintime").value;
		if (begintime == null || begintime == "") {
			alert("请假起始时间不能为空，请填写请假的起始时间！");
			return false;
		}
		var time = document.getElementById("time").value;
		if (time == null || time == "") {
			alert("请假的天数不能为空，请填写请假的天数！");
			return false;
		}
		var sendto = document.getElementById("sendto").value;
		if (sendto == null || sendto == "<点击此处选择审批人>") {
			alert("审批人不能为空，请选择审批人！");
			return false;
		}
		document.getElementById("myform").submit();
	}
</script>
</html>

