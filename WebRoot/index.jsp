<%@page import="java.awt.Window"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>贵州省乡镇协同办公平台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!-- 
<link type="text/css" rel="stylesheet" href="./resource/css/file.css" />
<link type="text/css" rel="stylesheet" href="./resource/css/jquery.treeview.css" />
<link rel="stylesheet" type="text/css" href="resource/css/chat.css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
 -->
<link rel="stylesheet" type="text/css" href="resource/css/chat.css" />
<link type="text/css" rel="stylesheet" href="./resource/css/file.css" />
<link type="text/css" rel="stylesheet" href="./resource/css/jquery.treeview.css" />

<link href="jqeasyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="jqeasyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="jqeasyui/jquery.js"></script>
<script type="text/javascript" src="jqeasyui/jquery.easyui.js"></script>
<script type="text/javascript" src="jqeasyui/locale/easyui-lang-zh_CN.js"></script>
<script language="javascript" src="./resource/js/pageCommon.js" charset="utf-8"></script>
<script language="javascript" src="./resource/js/PageUtils.js" charset="utf-8"></script>
<script language="javascript" src="./resource/js/jquery.treeview.js"></script>
<script type="text/javascript" src="resource/js/comet4j.js"></script>

    
</head>
<body class="easyui-layout" id="bodyLayout">
	<div region="north" style="height:74px; border:none;" class="top">
		<div style="float: left; width: 600px;">
			<img src="images/OA.png" />
		</div>
		<div
			style="float: right; width: 520px; padding-right: 10px; padding-top: 35px; text-align: right;
            color: #fff; font-weight: bold;">
			当前用户：
			<s:if test="%{#session.user==null}">
				<script type="text/javascript">
					window.location = "login.html";
				</script>
			</s:if>
			<s:else>${user.getU_name()}</s:else>
			<span>-<a href="#"
				onClick="Open('待办任务','./jsp/jbpm/AllMyTasks.jsp');">消息:${tasknumber}</a></span>
			&nbsp;|&nbsp; <span><a href="#"
				onClick="showChangPasswordDlg()">修改密码</a></span><a href="LoginServlet_LoginOut"
				style="color: #fff; font-weight: bold;
                text-decoration: none;">
				<img src="jqeasyui/themes/icons/cog.png" border="0" /> 退出系统
			</a>
		</div>
		<s:hidden id="useridHidden" value="%{#session.user.getId()}" />
		<s:hidden id="hiddenusername" value="%{#session.user.getU_name()}" />
	</div>
	<div region="south"
		style="height:25px; line-height:22px; background-color:#EDF3FF; text-align:center;"
		split="false">软件产品：贵州省乡镇单位协同办公平台 V1.0;
		&nbsp;&nbsp;技术研发单位：贵州省信息与计算科学重点实验室</div>
	<div region="west" style="width:240px;" split="true"
		iconCls="icon-home" title="首页">
		<div class="easyui-accordion" fit="true" style="border:none;">
			<s:iterator value="#application.Topprivilegelist">
				<s:if test="#session.user.hasPrivilegeByName(P_name)">
					<div title="${P_name}" iconCls="${P_image}">
						<s:iterator value="children">
							<s:if test="#session.user.hasPrivilegeByName(P_name)">
								<li onClick="Open('${P_name}','${P_url}?privilegeId=${id}');">${P_name}</li>
							</s:if>
						</s:iterator>
					</div>
				</s:if>
			</s:iterator>
		</div>
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页" iconCls="icon-index" data-options="closeable:false"
				style="padding:5px;">
				<table style="width:100%;">
					<tr>
						<td width="33%"><div style=" height:270px;float:left;"
								title="待办任务" class="easyui-panel"
								data-options="fit:true,tools:[{iconCls:'icon-more',handler:function(){Open('全部待办任务','jsp/jbpm/AllMyTasks.jsp');}}]">
								<table id="IndexNeedTask">
								</table>
							</div>
						<td width="33%"><div style=" height:270px;float:left;"
								title="我的流程" class="easyui-panel"
								data-options="fit:true,tools:[{iconCls:'icon-more',handler:function(){Open('全部我的流程','jsp/flow/RunMyFlow.jsp');}}]">
								<table id="IndexMyFlow">
								</table>
							</div></td>
						<td><div style="height:270px;float:left;" title="常用流程"
								class="easyui-panel"
								data-options="fit:true,tools:[{iconCls:'icon-more'}]">
								<table id="IndexNormalFlow">
								</table>
							</div></td>
					</tr>
					<tr>
						<td><div style="height:270px;float:left;" title="通知公告"
								class="easyui-panel"
								data-options="fit:true,tools:[{iconCls:'icon-more',handler:function(){Open('全部通知公告','jsp/jbpm/AllAnnouncements.jsp');}}]">
								<table id="IndexNotice">
								</table>
							</div></td>
						<td><div style="height:270px;float:left;" title="常用工具"
								class="easyui-panel"
								data-options="fit:true,tools:[{iconCls:'icon-more'}]">
								<table id="Ofenusetools">
								</table>
							</div></td>
						<td><div id="weatherReport" style=" height:210px;"
								title="天气预报" class="easyui-panel"
								data-options="fit:true,tools:[{iconCls:'icon-more'}]"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!-- right -->
	<div region="east" style="width:120px" split="true" title="联系人"
		collapsilble="true">
		<div class="chat03_content">
		<ul>
		<s:iterator value="#session.alluserlist">
			<!-- 
			<span onclick="showSendMessageDlg('${U_name}');return false;">${U_name}</span>
			<br /> 
			-->
                <li>
                    <label class="online">
                    </label>
                    <a href="javascript:showSendMessageDlg('${U_name}');"><img src="resource/img/head/2013.jpg"></a>
                    <a href="javascript:showSendMessageDlg('${U_name}');" class="chat03_name">${U_name}</a>
                </li>
		</s:iterator>
		</ul>
		</div>
	</div>
	<!-- 用户修改密码 -->
	<div id="ChangePasswordDlg" class="easyui-window" closed="true"
		style="width:350px; height:240px; padding:5px;" maximizable="false"
		modal="true" title="修改用户密码" minimizable="false" collapsible="false">
		<form id="ChangePasswordFm" method="post">
			<table>
				<tr>
					<td>新密码：</td>
					<td><input name="NewPasswd1" id="NewPasswd1" type="text"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>再次输入：</td>
					<td><input name="NewPasswd2" id="NewPasswd2"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;"><a href="#"
						class="easyui-linkbutton" iconCls="icon-ok"
						onClick="submitChangePasswd()">提交</a></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 发送消息 -->
	<!-- 
	<div id="SendMessageDlg" class="easyui-window" closed="true"
		style="width:350px; height:260px; padding:5px;" maximizable="false"
		modal="true" title="发送消息" minimizable="false" collapsible="false">
		<form id="SendMessageFm" method="post">
			<input type="hidden" name="sendtopeople" id="hiddenpeople" /> <input
				type="hidden" name="sendfrompeople" id="sendfromhiddenpeople" />
			<table>
				<tr>
					<td>消息内容：</td>
				</tr>
				<tr>
					<td colspan="2"><textarea name="messagecontent"
							style="width: 300px;height: 120px;"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">发送到-<span id="sendtopeople"></span></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;"><a href="#"
						class="easyui-linkbutton" iconCls="icon-ok"
						onClick="submitSendMessage()">发送</a></td>
				</tr>
			</table>
		</form>
	</div>
	 -->
		<div class="content" id="SendMessageDlg" style="display: none;">
		<form id="SendMessageFm" method="post">
		<input type="hidden" name="sendtopeople" id="hiddenpeople" /> 
		<input type="hidden" name="sendfrompeople" id="sendfromhiddenpeople" />
        <div class="chatBox">
            <div class="chatLeft">
                <div class="chat01">
                    <div class="chat01_title">
                        <ul class="talkTo">
                            <li><a href="javascript:;"><span id="sendtopeople"></span></a></li></ul>
                        <a class="close_btn" href="javascript:closeSendMessageDlg();"></a>
                    </div>
                    <div class="chat01_content" id="chat01_content">
                        
                    </div>
                </div>
                <div class="chat02">
                    <div class="chat02_title">
                        <a class="chat02_title_btn ctb01" href="javascript:;"></a>
                        <div class="wl_faces_box">
                            <div class="wl_faces_content">
                                <div class="title">
                                    <ul>
                                        <li class="title_name">常用表情</li><li class="wl_faces_close"><span>&nbsp;</span></li></ul>
                                </div>
                                <div class="wl_faces_main">
                                    <ul>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_01.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_02.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_03.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_04.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_05.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_06.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_07.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_08.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_09.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_10.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_11.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_12.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_13.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_14.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_15.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_16.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_17.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_18.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_19.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_20.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_21.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_22.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_23.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_24.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_25.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_26.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_27.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_28.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_29.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_30.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_31.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_32.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_33.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_34.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_35.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_36.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_37.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_38.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_39.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_40.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_41.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_42.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_43.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_44.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_45.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_46.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_47.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_48.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_49.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_50.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_51.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_52.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_53.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_54.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_55.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_56.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_57.gif" /></a></li>
                                        <li><a href="javascript:;">
                                            <img src="resource/img/emo_58.gif" /></a></li><li><a href="javascript:;">
                                                <img src="resource/img/emo_59.gif" /></a></li><li><a href="javascript:;">
                                                    <img src="resource/img/emo_60.gif" /></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="wlf_icon">
                            </div>
                        </div>
                    </div>
                    <div class="chat02_content">
                        <textarea id="textarea" name="messagecontent" style="width:562px;"></textarea>
                    </div>
                    <div class="chat02_bar">
                        <ul>
                            <li style="left: 20px; top: 10px; padding-left: 30px;">聊天桌面，24小时在线为您服务！</li>
                            <li style="right: 5px; top: 5px;">
	                            <a href="javascript:submitSendMessage();">
	                                <img src="resource/img/send_btn.jpg">
	                            </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
	</div>
	</form>
	</div>
	<div id="winPopMessage" class="easyui-window" closed="true"
		resizable="false"
		style="width: 900px; height:600px; padding: 10px 5px;"
		maximizable="false" modal="true"   minimizable="false"
		collapsible="false">
		<iframe frameborder="0" id="ifrPopMessage" width=856 height=540></iframe>
	</div>
</body>
</html>
<script type="text/javascript">
	function Open(text, url) {
		if ($("#tabs").tabs('exists', text)) {
			$('#tabs').tabs('select', text);
		} else {
			$('#tabs').tabs('add', {
				title : text,
				closable : true,
				iconCls : 'icon-grid',
				href : url
			});
		}
	}

	function Open2(text, url) {
		if ($("#tabs").tabs('exists', text)) {
			$('#tabs').tabs('select', text);
		} else {
			$('#tabs')
					.tabs(
							'add',
							{
								title : text,
								closable : true,
								iconCls : 'icon-grid',
								content : '<iframe  src="'
										+ url
										+ '" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'
							});
		}
	}
	$("#tabs").tabs({
		onContextMenu : function(e, title) {
			e.preventDefault();
			$('#tabsMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data("tabTitle", title);
		}
	});
	$(function() {
		$('#bodyLayout').layout('collapse', 'east');
		var useridHidden = document.getElementById("useridHidden").value;
		$('#IndexNeedTask')
				.datagrid(
						{
							url : 'DashboardDbrwServlet_AllDbrw?userid='
									+ useridHidden,
							columns : [ [
									{
										field : 'TaskName',
										title : '名称',
										width : 30
									},
									{
										field : 'TaskCreateTime',
										title : '创建时间',
										width : 50
									},
									{
										field : 'url',
										title : '操作',
										width : 20,
										formatter : function(v, row, index) {
											return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="Open2(\''
													+ row.TaskName
													+ '\',\''
													+ row.url + '\');">处理</a>';
										}
									} ] ],
							fit : true,
							fitColumns : true,
							method : 'post',
							nowrap : true,
							rownumbers : true
						});
		$('#IndexMyFlow')
				.datagrid(
						{
							//var useridHidden = document.getElementById("useridHidden").value;
							url : 'DashboardMyflowServlet_toCompute?userid='
									+ useridHidden,
							columns : [ [
									{
										field : 'M_name',
										title : '流程名称',
										width : 40
									},
									{
										field : 'M_date',
										title : '创建时间',
										width : 60
									},
									{
										field : 'url',
										title : '',
										width : 20,
										formatter : function(v, row, index) {
											return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="Open(\''
													+ row.M_name
													+ '\',\''
													+ row.url + '\');">详细</a>';
										}
									} ] ],

							rownumbers : true,
							fitColumns : true,
							fit : true,
							tools : [ {
								iconCls : 'icon-reload'
							}, {
								iconCls : 'icon-more'
							} ]
						});
		$('#IndexNormalFlow')
				.datagrid(
						{
							url : 'DashboardFlowServlet_toComputer',
							columns : [ [
									{
										field : 'F_name',
										title : '名称',
										width : 100
									},
									{
										field : 'url',
										width : 30,
										formatter : function(value, row, index) {
											if (row.url) {
												return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="Open2(\''
														+ row.F_name
														+ '\',\''
														+ row.url
														+ '\');">发起</a>'
											} else {
												return value;
											}
										}
									} ] ],
							fit : true,
							method : 'post',
							nowrap : true,
							rownumbers : true,
							fitColumns : true
						});
		$('#IndexNotice')
				.datagrid(
						{
							url : 'DashboardAnnouncementsServlet_toComputer',
							columns : [ [
									{
										field : 'A_name',
										title : '名称',
										width : 40
									},
									{
										field : 'A_time',
										title : '创建时间',
										width : 60
									},
									{
										field : 'url',
										title : '操作',
										width : 20,
										formatter : function(v, row, index) {
											return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="showMessageWindow(\''
													+ row.A_name
													+ '\',\''
													+ row.url + '\');">查看</a>';
										}
									}

							] ],
							fit : true,
							rownumbers : true,
							fitColumns : true
							//showMessageWindow
						});
		$('#Ofenusetools')
				.datagrid(
						{
							url : 'json/tools.json',
							columns : [ [
									{
										field : 'Tool',
										title : '工具',
										width : 40
									},
									{
										field : 'url',
										title : '工具名',
										width : 60,
										formatter : function(v, row, index) {
											return '<a href="#" class="easyui-linkbutton" iconCls="icon-book" onclick="Open2(\''
													+ row.Toolname
													+ '\',\''
													+ row.url
													+ '\');">'
													+ row.Toolname + '</a>';
										}
									}

							] ],
							fit : true,
							fitColumns : true
						});
		$("#tabs").tabs({
			onSelect : function(text, index) {
				if (text == "首页") {
					$('#IndexNeedTask').datagrid('reload');
					$('#IndexMyFlow').datagrid('reload');
					$('#IndexNormalFlow').datagrid('reload');
					$('#IndexNotice').datagrid('reload');
					$('#Ofenusetools').datagrid('reload');

				}
				
			}
		});

	});
	function showMessageWindow(title,url) {
		$('#winPopMessage').window({title:title});
		$('#winPopMessage').window('open');
		$('#ifrPopMessage').attr('src',url);
	}
	function showChangPasswordDlg() {
		$('#ChangePasswordDlg').window('open');
	}
	function submitChangePasswd() {
		var useridHidden = document.getElementById("useridHidden").value;
		var passwd1 = document.getElementById("NewPasswd1").value;
		var passwd2 = document.getElementById("NewPasswd2").value;
		if (passwd1 == passwd2) {
			$('#ChangePasswordFm').form(
					'submit',
					{
						url : 'LoginServlet_ChangePasswd?userid='
								+ useridHidden + '&password=' + passwd1,
						success : function(data) {
							var data = eval('(' + data + ')');
							if (data.success) {
								$('#ChangePasswordDlg').window('close');
								$.messager.alert('添加成功提示', "密码修改成功");

							} else
								$.messager.alert('系统提示', data.errorMsg);
						}
					})
		} else {
			alert("密码不一致！请重新输入！");
			document.getElementById("NewPasswd1").value = "";
			document.getElementById("NewPasswd2").value = "";

		}

	}

	function showSendMessageDlg(name) {
		var sendtoElement = document.getElementById("sendtopeople");
		sendtoElement.innerHTML = name ;
		alert(name);
		var hiddenElement = document.getElementById("hiddenpeople");
		hiddenElement.value = name;
		var username = document.getElementById("hiddenusername").value;
		var sendformhiddenElement = document
				.getElementById("sendfromhiddenpeople");
		sendformhiddenElement.value = username;
		var SendMessageDlg = document.getElementById("SendMessageDlg");
		SendMessageDlg.style.display = "block";
		//$('#SendMessageDlg').window('open');
	}
	function closeSendMessageDlg(){
		var SendMessageDlg = document.getElementById("SendMessageDlg");
		SendMessageDlg.style.display = "none";
	
	}

	function submitSendMessage() {
		$('#SendMessageFm').form('submit', {
			url : 'SendMessage',
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.success) {
					//$('#SendMessageDlg').window('close');
					
					var chat01_content = document.getElementById("chat01_content");
					var mes = document.createElement("div");
					mes.setAttribute("class","message clearfix");
					mes.style.display = "block";
					var textarea = document.getElementById("textarea").value;
					var username = document.getElementById("hiddenusername").value;
					var date = new Date();
					var year = date.getFullYear();	 //年
					var month = date.getMonth() + 1;	//月
					var day = date.getDate();	 //日
					var hour = date.getHours();	 //小时
					var minute = date.getMinutes();	 //分钟
					var second = date.getSeconds();	 //秒钟
					mes.innerHTML = username+"("+year+"-"+month+"-"+day+" "+hour+"-"+minute+"-"+second+"):"+textarea;
					chat01_content.appendChild(mes);
					
					var textareaDlg = document.getElementById("textarea");
					textareaDlg.value = "";
					//$.messager.alert('添加成功提示', "消息发送成功");

				} else
					$.messager.alert('系统提示', data.errorMsg);
			}
		});
	}

	function refresh(text) {
		var userid = document.getElementById("useridHidden").value;
		var username = document.getElementById("hiddenusername").value;
		var useridandname = userid+":"+username;
		alert(useridandname);
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var TaskNumber = xmlhttp.responseText;

				//var spanElementValue = document.getElementById("tasknumber").innerHTML;

				var spanElement = document.getElementById("tasknumber");
				var newspanElementValue = "消息" + TaskNumber;
				spanElement.innerHTML = newspanElementValue;

				//doLeftMenuClick('Dashboard.action','123');
				alert(text);
				var nameArray = text.split(",");
				for ( var i = 0; i < nameArray.length; i++) {
					alert(nameArray[i]);
					
					if (useridandname == nameArray[i]) {
						alert("您有新的任务！");
						
					}

				}
			}
		};
		xmlhttp.open("POST", "GetTaskNumber.action?userid=" + userid, true);
		xmlhttp.send();
	};
	
	function newTask(usernames) {
		//alert(text);
		var userid = document.getElementById("useridHidden").value;
		var username = document.getElementById("hiddenusername").value;
		var useridandname = userid+":"+username;
		var nameArray = usernames.split(",");
		for ( var i = 0; i < nameArray.length; i++) {
			//alert(nameArray[i]);
			if (useridandname == nameArray[i]) {
				alert("您有新的任务！");
				$("#tabs").tabs({
					onSelect : function(text, index) {
						if (text == "首页") {
							$('#IndexNeedTask').datagrid('reload');
							$('#IndexMyFlow').datagrid('reload');
							$('#IndexNormalFlow').datagrid('reload');
							$('#IndexNotice').datagrid('reload');
							$('#Ofenusetools').datagrid('reload');
		
						}
						
					}
				});
			}

		}
	}

	function viewmessage(message) {
		var username = document.getElementById("hiddenusername").value;
		//alert(username);
		var messageArray = message.split("::");
		//alert(messageArray[1]);
		if (username == messageArray[1]) {
			//alert("来自" + messageArray[0] + "的消息：" + messageArray[2]);
			
			var SendMessageDlg = document.getElementById("SendMessageDlg");
			if(SendMessageDlg.style.display = "block"){
			
				var sendtoElement = document.getElementById("sendtopeople");
				sendtoElement.innerHTML = messageArray[0];
				var hiddenElement = document.getElementById("hiddenpeople");
				hiddenElement.value = messageArray[0];
				var username = document.getElementById("hiddenusername").value;
				var sendformhiddenElement = document.getElementById("sendfromhiddenpeople");
				sendformhiddenElement.value = username;
				
				var mes = document.createElement("div");
				mes.setAttribute("class","message clearfix");
				mes.style.display = "block";
				//var textarea = document.getElementById("textarea").value;
				var username = document.getElementById("hiddenusername").value;
				var date = new Date();
				var year = date.getFullYear();	 //年
				var month = date.getMonth() + 1;	//月
				var day = date.getDate();	 //日
				var hour = date.getHours();	 //小时
				var minute = date.getMinutes();	 //分钟
				var second = date.getSeconds();	 //秒钟
				mes.innerHTML = messageArray[0]+"("+year+"-"+month+"-"+day+" "+hour+"-"+minute+"-"+second+"):"+messageArray[2];
				chat01_content.appendChild(mes);
			}else{
				showSendMessageDlg(messageArray[0]);
				var sendtoElement = document.getElementById("sendtopeople");
				sendtoElement.innerHTML = messageArray[0];
				
				var mes = document.createElement("div");
				mes.setAttribute("class","message clearfix");
				mes.style.display = "block";
				//var textarea = document.getElementById("textarea").value;
				var username = document.getElementById("hiddenusername").value;
				var date = new Date();
				var year = date.getFullYear();	 //年
				var month = date.getMonth() + 1;	//月
				var day = date.getDate();	 //日
				var hour = date.getHours();	 //小时
				var minute = date.getMinutes();	 //分钟
				var second = date.getSeconds();	 //秒钟
				mes.innerHTML = messageArray[0]+"("+year+"-"+month+"-"+day+" "+hour+"-"+minute+"-"+second+"):"+messageArray[2];
				chat01_content.appendChild(mes);
			}
			
		}

	};

	JS.Engine.on({
		start : function(usernames) {
			//refresh(text);
			newTask(usernames);
		},
		message : function(message) {
			viewmessage(message);
		},
		stop : function(cause, cId, url, engine) {
		}
	});
	JS.Engine.start('conn');
</script>
