<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--数据表!-->
<table id="ContactsDg">
</table>

<!-- 发送消息 -->
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



<script type="text/javascript">
$(load0());
function load0(){
	$('#ContactsDg').datagrid({
		url:'UserServlet_testlist',
		fit:true,
		columns:[[
			
			{field:'userid',title:'用户编号',width:40,hidden:true},
			{field:'name',title:'姓名',width:100},
			{field:'sex',title:'性别',width:100},
			{field:'email',title:'电子邮箱',width:40},
			{field:'phone',title:'电话号码',width:200},
			{field:'department',title:'部门',width:60},
			{field:'gangwei',title:'岗位',width:60},
		]],
		    pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [5,10,20,99],//可以设置每页记录条数的列表  
		    beforePageText: '第',//页数文本框前显示的汉字  
		    afterPageText: '页    共 {pages} 页',  
		    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' ,
		fitColumns:true,
		loadMsg:'数据加载中请稍后……',  
		rownumbers: true,               
		striped: true, 
		pagination:true,
		method:"post",
		title:"通讯录",
		toolbar:[
		    {
				text:'查询',
				iconCls:'icon-search',
				handler:findByUserName 
			}
			//,
			//{
			//	text:'发送消息',
			//	iconCls:'icon-add',
			//	handler:showSendMessageDlg 
			//}
			
		]
	});
	
	//$("<td >开始时间：<input id='starttime1' class='easyui-datetimebox'/>截止时间：<input id='endtime1' class='easyui-datetimebox' /></td>").prependTo(".datagrid-toolbar table tbody tr");
	$("<td>查询姓名：<input id='username' type='text' /></td>").prependTo(".datagrid-toolbar table tbody tr");	
}

 function showSendMail(){ 
    	var row=$('#ContactsDg').datagrid('getSelected');
               if(row) 
            $('#SendMailDlg').window('open');
               else 
               $.messager.alert('系统提示','请选择要发送消息的用户');
        }
        
   
        
        
function findByUserName(){
	var name=document.getElementById('username').value;
	var url="./jsp/Contacts/FindResult.jsp";
	Open("查询结果",url);
		
	}
	
	function showSendMessageDlg() {
		var row=$('#ContactsDg').datagrid('getSelected');
         if(row) 
            $('#SendMailDlg').window('open');
         else 
               $.messager.alert('系统提示','请选择要发送消息的用户');
		var sendtoElement = document.getElementById("sendtopeople");
		sendtoElement.innerHTML = "【" + row.name + "】";
		var hiddenElement = document.getElementById("hiddenpeople");
		hiddenElement.value = row.name;
		var username = document.getElementById("hiddenusername").value;
		var sendformhiddenElement = document
				.getElementById("sendfromhiddenpeople");
		sendformhiddenElement.value = username;
		$('#SendMessageDlg').window('open');
	}

	function submitSendMessage() {
		$('#SendMessageFm').form('submit', {
			url : 'SendMessage',
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.success) {
					$('#SendMessageDlg').window('close');
					$.messager.alert('添加成功提示', "消息发送成功");

				} else
					$.messager.alert('系统提示', data.errorMsg);
			}
		})
	}
//}
</script>
