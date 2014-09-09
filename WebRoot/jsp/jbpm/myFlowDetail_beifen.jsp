<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String[] usernametmp = (String[])request.getSession().getServletContext().getAttribute("allusernametmp");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'detailtasks.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
 


<style>
body {
	margin-top: 5px;
	color:#BBD2FF;
	font-size:13px;
}
.oa_myflowDetail{
 
}
.oa_myflowDetail th {
	padding: 5px;
	border-right: 1px solid #ccc;
	border-bottom: 2px solid #BBD2FF;
	line-height: 25px;
	background-color: #eee;
	text-align: center;
	font-size:13px;
}

.oa_myflowDetail td {
	text-align: center;
	line-height:25px;
	font-size:13px;
	padding-left:5px;
	border-bottom: soild 1px #222;
}
.oa_myflowDetail tr{
	border-bottom:solid 1px #222;
}
.oa_myflowDetail tr:hover{
	background-color:#F5F5F5;
}
</style>
</head>

<body>

	<table cellpadding="0" cellspacing="0" 
		style="border: solid 1px #BBD2FF;border-bottom:none;width:99%;margin:auto;margin-top:5px;margin-bottom:5px;" class="oa_myflowDetail"  >
		<thead>
			<tr>
				<th width="70%" style="border-bottom:1px solid #BBD2FF;border-right:1px solid #BBD2FF;padding: 5px;color:#082C5A;background-color: #E7EFFF;">名称</th>
				<th width="10%" style="border-bottom:1px solid #BBD2FF;border-right:1px solid #BBD2FF;padding: 5px;color:#082C5A;background-color: #E7EFFF;">处理人</th>
				<th width="10%" style="border-bottom:1px solid #BBD2FF;border-right:1px solid #BBD2FF;padding: 5px;color:#082C5A;background-color: #E7EFFF;">方式</th>
				<th width="10%" style="border-bottom:1px solid #BBD2FF;padding: 5px;background-color: #E7EFFF;color:#082C5A;">是否处理</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#session.mydocumentlist">
				<s:if test="%{sendto==null}">
				 <tr>
						<td>${D_name}</td>
						<td>${D_content}</td>
						<td>${type==1?'串行':(type==2?'并行':'下发文件')}</td>
						<td>默认状态下处理人均未处理，下边列出的均为已处理的</td>
					</tr> 
				</s:if>
				<s:else>
					<tr>
						<td style="border-bottom:1px solid #BBD2FF;border-right:1px solid #BBD2FF;padding: 5px;font-size:13px;">${D_name}</td>
						<td style="border-bottom:1px solid #BBD2FF;border-right:1px solid #BBD2FF;padding: 5px;font-size:13px;text-align:center;">${sendtoName}</td>
						<td style="border-bottom:1px solid #BBD2FF;border-right:1px solid #BBD2FF;padding: 5px;font-size:13px;text-align:center;">${type==1?'串行':(type==2?'并行':(type==3?'下发文件':''))}</td>
						<td style="border-bottom:1px solid #BBD2FF;padding: 5px;font-size:13px;text-align:center;">&nbsp;<span>${ispass==0?'不同意-':(ispass==1?'通过':'没处理')}</span> 
						<s:if test="ispass==0">
								<!-- <a href="Jbpm_viewSuggest?executionId=${D_executionid}&&userid=${user.getId()}">查看意见</a> -->
								<a href="#" onclick="Open2('审批详情','Jbpm_viewSuggest?executionId=${D_executionid}&&userid=${user.getId()}');">查看意见</a>
						</s:if></td>
					</tr>
				</s:else>
			</s:iterator>
		</tbody>

	</table>

	<div style="text-align: center;  margin-top:10px; clear:both;">
		<s:if test="#session.flowtype=='Document'">
			<s:if test="#session.iscx">
				<s:if test="#session.isHq">
					<img alt="串行会签" src="./resource/flowimage/cxgwsphq.png" width="90%" />
				</s:if>
				<s:elseif test="#session.isNotpass">
					<img alt="串行审批没通过" src="./resource/flowimage/cxgwsphqnotpass.png"
						width="90%" />
				</s:elseif>
				<s:elseif test="#session.isbgs">
					<img alt="串行办公室" src="./resource/flowimage/cxgwspbgs.png"
						width="90%" />
				</s:elseif>
				<s:else>
					<img alt="串行文件下发" src="./resource/flowimage/cxgwspwjxf.png"
						width="90%" />
				</s:else>
			</s:if>
			<s:else>
				<s:if test="#session.isHq">
					<img alt="并行会签" src="./resource/flowimage/bxgwsphq.png" width="90%" />
				</s:if>
				<s:elseif test="#session.isNotpass">
					<img alt="并行审批没通过" src="./resource/flowimage/bxgwsphqnotpass.png"
						width="90%" />
				</s:elseif>
				<s:elseif test="#session.isbgs">
					<img alt="并行办公室" src="./resource/flowimage/bxgwspbgs.png"
						width="90%" />
				</s:elseif>
				<s:else>
					<img alt="并行下发文件" src="./resource/flowimage/bxgwspwjxf.png"
						width="90%" />
				</s:else>
			</s:else>
		</s:if>
		<s:elseif test="#session.flowtype=='AskForLeave'">
			<s:if test="#session.issp">
				<img alt="并行下发文件" src="./resource/flowimage/askforleavesp.png"
					width="90%" />
			</s:if>
			<s:elseif test="#session.askforleaveispass">
				<img alt="并行下发文件" src="./resource/flowimage/askforleavexj.png"
					width="90%" />
			</s:elseif>
			<s:else>
				<img alt="并行下发文件" src="./resource/flowimage/askforleavenotpass.png"
					width="90%" />
			</s:else>

		</s:elseif>
	</div>
	<div id="footer"></div>
</body>
</html>
