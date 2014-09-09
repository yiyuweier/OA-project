<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.oa.bean.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
	<link rel="stylesheet" href="./jsp/ckeditor/samples/sample.css">
	-->
<link rel="stylesheet" href="./jqeasyui/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="./jqeasyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="./jsp/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="./jqeasyui/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="./jqeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./jsp/artdialog/artDialog.js"></script>
<script type="text/javascript"
	src="./jsp/artdialog/artDialog.plugins.js"></script>
<script type="text/javascript" src="./jsp/artdialog/extend/F.js"></script>
<script type="text/javascript"
	src="./resource/js/jquery/jquery.myajax.js"></script>
<script type="text/javascript" src="./resource/js/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="./jsp/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="./resource/js/selectemp.js"></script>
<script type="text/javascript" src="./jsp/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="./jsp/ckfinder/ckfinder.js"></script>
<style>
body {
	margin: 0px; 
}

td {
	padding: 2px;
	font-size: 13px;
	background-color: #F3F7FF;
	border: solid 1px #E7EFFF;
}
</style>
</head>
<%
	User user = (User) request.getSession().getAttribute("user");
	Long userid = user.getId();
	request.setAttribute("userid", userid);
%>
<script type="text/javascript">
	window.onload = function() {
		var editor = CKEDITOR.replace('messagecontent');
		CKFinder.SetupCKEditor(editor, './jsp/ckfinder/');
	}
	var tabObj;
	$(function() {
		tabObj = $("#tabs").tabs();
	});

	function showusers(id, type) {
		//alert("here");
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var usernamelist = xmlhttp.responseText.substring(1,
						xmlhttp.responseText.length - 1);
				//alert(usernamelist);
				var arry = usernamelist.split(",");
				var ParentElement = document.getElementById("list1").childNodes;
				if (ParentElement.length != 0) {
					//alert("ParentElement的长度为："+ParentElement.length);
					for ( var j = 0; j <= ParentElement.length; j++) {
						//alert("元素："+ParentElement[j]);
						document.getElementById("list1").removeChild(
								ParentElement[0]);
					}
				}
				for ( var i = 0; i < arry.length; i++) {
					//alert(arry[i]);
					var newElement = document.createElement("option");
					newElement.innerHTML = arry[i];
					document.getElementById("list1").appendChild(newElement);
				}
				//document.getElementById("list1").list=arry;
				//document.getElementById("userlistdiv").innerHTML="";
			}
		}
		if (type == "department") {
			xmlhttp.open("POST", "GetUsers_FromDarpId.action?id=" + id, true);
		} else if (type == "role") {
			xmlhttp.open("POST", "GetUsers_FromRoleId.action?id=" + id, true);
		}

		xmlhttp.send();
	}

	var zTreeDepartmentObj, zTreeDepartmentSetting = {
		view : {
			selectedMulti : true,//是否能够多选
			expandSpeed : ""//树形列表打开时的动画速度
		},
		async : {
			enable : true,//设置为异步加载
			type : "post",//传输方式为post
			url : "zTreeJson_action.action",//处理action的url
			autoParam : [ "id" ]
		//自动传递给action的id
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			}
		}
	}, zTreeDepartmentNodes = [ {
		"id" : "-1",
		"pId" : null,
		"name" : "全部部门",
		click : "alert('123');",
		isParent : true
	} ];

	var zTreeRoleObj, zTreeRoleSetting = {
		view : {
			selectedMulti : true,//是否能够多选
			expandSpeed : ""//树形列表打开时的动画速度
		},
		async : {
			enable : true,//设置为异步加载
			type : "post",//传输方式为post
			url : "zTreeJson_getUserByRole.action",//处理action的url
			autoParam : [ "id" ]
		//自动传递给action的id
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			}
		}
	}, zTreeRoleNodes = [ {
		"id" : "-1",
		"pId" : null,
		"name" : "全部职务",
		click : "alert('123');",
		isParent : true
	} ];
	$(document).ready(
			function() {
				zTreeDepartmentObj = $.fn.zTree.init($("#DeptTreeDiv"),
						zTreeDepartmentSetting, zTreeDepartmentNodes);
				zTreeRoleObj = $.fn.zTree.init($("#TitleTreeDiv"),
						zTreeRoleSetting, zTreeRoleNodes);
			});
</script>

<body>
	<s:form action="startProcessInstance_Message" method="post"
		enctype="multipart/form-data" id="myform">
		<s:hidden id="userid" name="userid" value="%{#request.userid}" />
		<div class="description">

			<div  style="width:99%;padding:5px; background-color:#F3F7FF;margin-bottom:2px;"> 
				<a class="easyui-linkbutton"  data-options=" iconCls:'icon-email_go'" onclick="save();">发送消息</a>
			</div>
			<table style="width:100%;">
				<tr>
					<td>消息名称:<input type="text" id="messagename"
						name="messagename" style="width:550px;"></td>
				</tr>
				<tr>
					<td><textarea id="messagecontent" name="messagecontent"></textarea></td>
				</tr>
				<tr>
					<td>附件：<input id="FJInput" name="image" type="file" /></td>
				</tr>
				<tr>
					<td>发送到：<input id="trackInput" name="D_sendto" type="text"
						class="input-text" style="cursor:pointer;" readonly
						maxlength="200" title="<点击此处添加发送对象>" value="<点击此处添加发送对象>"
						onclick="$('#userPicker').window('open');return false;">(默认是发送到所有人，如果要指定发送到某些人，请选择要发送的对象)
					</td>
				</tr>
			</table>

 			<div  style="width:99%;padding:5px; background-color:#F3F7FF;margin-top:2px;"> 
				<a class="easyui-linkbutton"  data-options=" iconCls:'icon-email_go'" onclick="save();">发送消息</a>
			</div>
	</s:form>

	<div id="userPicker"  class="easyui-window" title="选择接收人" closed="true" resizable="false"
     maximizable="false"
    modal="true" title="选择用户" minimizable="false" collapsible="false" >
		<div id="tabs" class="easyui-tabs"
			style="margin-left:0;padding:0;width: 600px;height: 400px;">
			<div title="部门">
				<div id="tabs-1"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="overflow-y:scroll;overflow-x:auto;height:170px;width:218px;border:1px solid #aaa;display:block;margin-top:5px;"
						id="deptDiv">
						<ul id="DeptTreeDiv" class="ztree" style="margin:0;">
						</ul>
					</div>
				</div>
			</div>
			<div title="职务">
				<div id="tabs-2"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="overflow-y:scroll;overflow-x:auto;height:170px;width:218px;border:1px solid #aaa;display:block;margin-top:5px;"
						id="titleDiv">
						<ul id="TitleTreeDiv" class="ztree" style="margin:0;">
						</ul>
					</div>
				</div>
			</div>
			<div title="组">
				<div id="tabs-3"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="overflow-y:scroll;overflow-x:auto;height:170px;width:218px;border:1px solid #aaa;display:block;margin-top:5px;"
						id="teamDiv">
						<ul id="TeamTreeDiv" class="ztree" style="margin:0;">
							<li>销售一组</li>
							<li>销售二组</li>
							<li>销售三组</li>
						</ul>
					</div>
				</div>
			</div>
			<div title="组合查询">
				<div id="tabs-4"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="height:170px;width:218px;border:1px solid #aaa;display:block;margin:0px;"
						id="queryDiv">
						<div style="height:20px;font-weight:bold;padding-top:5px;">查询条件：</div>
						<div style="height:20px;">
							部门：<input type="text" readonly class="input-search"
								style="width:163px;" id="deptName" name="deptName"
								onclick="selectDeptObj.select();">
						</div>
						<div class="hidden" style="padding-left:32px;">
							<input type="checkbox" id="isContainSub" name="isContainSub"
								checked><label for="isContainSub">包含下级部门</label>
						</div>
						<div style="margin-top:2px;">
							职务：<input type="text" readonly class="input-search"
								style="width:163px;" id="titleName" name="titleName"
								onclick="selectTitleObj.select();">
						</div>
						<div style="height:20px;padding-top:5px;text-align:right;">
							<button onclick="query()" title="查询" class="button">查询</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div style="position:absolute;top:250px;left:11px;" id="userlistdiv">
			<select multiple id="list1" name="list1"
				style="width:220px;height:180px;border:1px solid #aaa;overflow-y:scroll;overflow-x:auto;"
				ondblclick="addSelected()" size="10">

			</select>
		</div>
		<div
			style="position:absolute;top:68px;left:285px;width:220px;height:350px;">
			<select multiple id="list2" name="list2"
				style="width:220px;height:360px;" ondblclick="removeSelected()"></select>
		</div>
		<div style="position:absolute;top:140px;left:237px;width:46px;">
			<button onClick="removeOrgSelected()" class="button"
				style="width:20px;padding:0;" title="移除选中"><</button>
			<button onClick="addOrgSelected()" class="button"
				style="width:20px;padding:0;" title="添加选中">></button>
		</div>
		<div style="position:absolute;top:300px;left:237px;width:46px;">
			<button onClick="removeSelected()" class="button"
				style="width:20px;padding:0;" title="移除选中"><</button>
			<button onClick="addSelected()" class="button"
				style="width:20px;padding:0;" title="添加选中">></button>
			<br> <br> <br> <br>
			<button onClick="removeAll()" class="button"
				style="width:20px;padding:0;" title="移除全部"><<</button>
			<button onClick="addAll()" class="button"
				style="width:20px;padding:0;" title="添加全部">>></button>
		</div>
		<div style="position:absolute;top:200px;left:505px;width:20px;">
			<img src="resource/image/up.png" style="cursor:pointer;" title="向上移动"
				onclick="moveUp()"> <br> <br> <img
				src="resource/image/down.png" style="cursor:pointer;" title="向下移动"
				onclick="moveDown()">
		</div>
		<div align="center" style="margin-top:0px;">
			<BUTTON class=Btnnormal
				onmousedown="javascript:this.className='Btnover'"
				onmouseover="javascript:this.className='Btnover'" title='确定' id="ok"
				onmouseout="javascript:this.className='Btnnormal'" onclick="ok();">&nbsp;&nbsp;确定&nbsp;&nbsp;</BUTTON>
				
				
			&nbsp;
			<BUTTON class=Btnnormal
				onmousedown="javascript:this.className='Btnover'"
				onmouseover="javascript:this.className='Btnover'" title='取消'
				accessKey=S onmouseout="javascript:this.className='Btnnormal'"
				onclick="closeArtDialog();">&nbsp;&nbsp;取消&nbsp;&nbsp;</BUTTON>
			&nbsp;
		</div>
	</div>


</body>
<script type="text/javascript">
	function createFlow() {
		alert("here");
		window
				.showModalDialog('./jsp/js/selectemp/selectempforcollaborate1.jsp');
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
		newDiv.style.width = "600px";
		newDiv.style.height = "350px";
		newDiv.style.top = "50px";
		newDiv.style.left = (parseInt(document.body.scrollWidth) - 300) / 2
				+ "px"; // 屏幕居中
		newDiv.style.background = "EEEEEE";
		newDiv.style.border = "1px solid #0066cc";
		newDiv.style.padding = "5px";
		newDiv.style.overflow = "auto";
		return false;
	}

	function save() {
		var messagename = document.getElementById("messagename").value;
		if (messagename == null || messagename == "") {
			alert("消息名称不能为空！请填写消息名称！");
			return false;
		}
		var messagecontent = CKEDITOR.instances.messagecontent.getData();
		if (messagecontent == null || messagecontent == "") {
			alert("消息内容不能为空！请填写消息内容！");
			return false;
		}
		document.myform.submit();

	}

	function ok() {
		var SelectedElement = document.getElementById("list2");
		var selectedValue;
		if (SelectedElement.length != 0) {
			selectedValue = SelectedElement[0].value;
		}
		for ( var i = 1; i < SelectedElement.length; i++) {
			selectedValue = selectedValue + "," + SelectedElement[i].value;
		}
		alert(selectedValue);
		document.getElementById("trackInput").value = selectedValue;
		$('#userPicker').window('close');
	}
</script>
</html>
