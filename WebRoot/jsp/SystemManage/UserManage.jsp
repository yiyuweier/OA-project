<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<!-- 
UserServlet_userManageUI   UserManage.jsp
<table id="tt" class="easyui-datagrid" style="width:100%;height:100%"
        url="UserServlet_list" title="用户管理" iconCls="icon-save" toolbar="#tb">
    <thead>
        <tr>
            <th field="ck" width="80">选择</th>
            <th field="userid" width="80">用户编号</th>
            <th field="loginname" width="80" align="right">用户登陆名</th>
            <th field="U_name" width="80" align="right">用户姓名</th>
            <th field="department" width="150">部门</th>
            <th field="gangwei" width="60" align="center">岗位</th>
            <th field="U_description" width="60" align="center">备注</th>
        </tr>
    </thead>
</table>

	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:showNewUserDlg()">新增用户</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:showEditUserDlg()">编辑用户</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:submitDeleteUser()">删除用户</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:submitResetPassword()">重置密码</a>
 -->

<!-- 
<div id="usertb">
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>
 --> 
<table id="UserManageDg">
</table>

<!--新增对话框!-->
<div id="NewUserDlg" class="easyui-window" closed="true" style="width:350px; height:340px;" maximizable="false"   modal="true" title="新增用户" minimizable="false" collapsible="false">
<form id="NewUserFm" method="post">
  <table>
    <tr>
      <td>所属部门：</td>
      <td>
      <input id="departmentIdforAdd" class="easyui-combobox" name="departmentId" data-options="valueField:'id',textField:'text',url:'UserServlet_getDepartmentInfo'">
	  </td>
    </tr>
    <tr>
      <td>用户名：</td>
      <td>
	  <input type="text" class="easyui-validatebox" data-options="required:true" name="loginname"/>
	  </td>
    </tr>
    <tr>
      <td>姓名：</td>
      <td> <input  type="text" class="easyui-validatebox" data-options="required:true" name="U_name"/></td>
    </tr>
    <tr>
      <td>性别：</td>
      <td>
	     
	      
	        <input type="radio" name="U_gender" value="男"  checked="checked">
	        男 
	     
	     
	        <input type="radio" name="U_gender" value="女">
	        女 
	       </td>
    </tr>
    <tr>
      <td>联系电话：</td>
      <td> <input  type="text" class="easyui-validatebox" data-options="required:true" name="U_phoneNumber"/></td>
    </tr>
    <tr>
      <td>E-mail：</td>
      <td> <input  type="text" class="easyui-validatebox" data-options="required:true" name="U_email"/></td>
    </tr>
    <tr>
      <td>备注：</td>
      <td>
	  <textarea class="easyui-validatebox" data-options="required:true" name="U_description"></textarea>
	  </td>
    </tr>
    <tr>
      <td>岗位：</td>
      <td>
      	<input id="roleIdListforAdd" class="easyui-combobox" name="roleIdList" data-options="valueField:'id',textField:'text',url:'UserServlet_getRoleInfo'">
      </td>
    </tr>
	    <tr>
      <td colspan="2" >
      <a iconCls="icon-ok" class="easyui-linkbutton" href="#" onclick="submitaddUser();">
	  </td>
 
    </tr>
  </table>
  </form>
</div>
<!--编辑对话框!-->
<div id="EditUserDlg" class="easyui-window" closed="true" style="width:350px; height:340px;" maximizable="false"   modal="true" title="编辑用户" minimizable="false" collapsible="false">
<form id="EditUserFm" method="post">
  <table>
  	<s:hidden id="userid" name="userid"/>
    <tr>
      <td>所属部门：</td>
      <td>
      <input id="departmentIdforEdite" class="easyui-combobox" name="departmentId" mode="remote" data-options="valueField:'id',textField:'text',url:'UserServlet_getDepartmentInfo'">
	  </td>
    </tr>
    <tr>
      <td>用户名：</td>
      <td>
	  <input  type="text" class="easyui-validatebox" data-options="required:true" name="loginname"/>
	  </td>
    </tr>
    <tr>
      <td>姓名：</td>
      <td> <input  type="text" class="easyui-validatebox" data-options="required:true" name="U_name"/></td>
    </tr>
    <tr>
      <td>性别：</td>
      <td>
	     
	      
	        <input type="radio" name="U_gender" value="男">
	        男 
	     
	     
	        <input type="radio" name="U_gender" value="女">
	        女 
	       </td>
    </tr>
    <tr>
      <td>联系电话：</td>
      <td> <input  type="text" class="easyui-validatebox" data-options="required:true" name="U_phoneNumber"/></td>
    </tr>
    <tr>
      <td>E-mail：</td>
      <td> <input  type="text" class="easyui-validatebox" data-options="required:true" name="U_email"/></td>
    </tr>
    <tr>
      <td>备注：</td>
      <td>
	  <textarea class="easyui-validatebox" data-options="required:true" name="U_description"></textarea>
	  </td>
    </tr>
    <tr>
      <td>岗位：</td>
      <td>
      	<input id="roleIdListforEdite" class="easyui-combobox" name="roleIdList" mode="remote" data-options="valueField:'id',textField:'text',url:'UserServlet_getRoleInfo'">
      </td>
    </tr>
	    <tr>
      <td colspan="2" >
	  <a href="#" iconCls="icon-ok" class="easyui-linkbutton" onclick="submitEditorUser()">提交</a>
	  </td>
 
    </tr>
  </table>
  </form>
</div>
<script type="text/javascript">
	$(function load(){
		$('#UserManageDg').datagrid({
			url:'UserServlet_list',
			fit:true,
			columns:[[
				{field:'ck',title:'选择',width:40,checkbox:true},
				{field:'userid',title:'用户编号',width:40},
				{field:'loginname',title:'用户登陆名',width:80},
				{field:'U_name',title:'用户姓名',width:80},
				{field:'department',title:'所属部门',width:80},
				{field:'gangwei',title:'岗位',width:80},
				{field:'U_description',title:'备注',width:200}
			]],
			fitColumns:true,
			toolbar:
		[{
				text:'新增',
				iconCls:'icon-add',
				handler:showNewUserDlg
			},{
				text:'编辑',
				iconCls:'icon-edit',
				handler:showEditUserDlg
			},{
				text:'删除',
				iconCls:'icon-remove',
				handler:submitDeleteUser
			},{
				text:'重置密码',
				iconCls:'icon-cog',
				handler:submitResetPassword
			}],
			loadMsg:'数据加载中请稍后……',  
			rownumbers: true,               
			striped: true, 
			pagination:true,
			method:"post",
			title:"用户管理" 
		});
		var p = $('#UserManageDg').datagrid('getPager');  
    	$(p).pagination({  
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [5,10,15],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
        /*onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            alert('before refresh'); 
            $(this).pagination('loaded'); 
        }*/ 
       });
	});
	
	//显示新增用户
	function  showNewUserDlg(){
		$('#departmentIdforAdd').combobox('reload');
		$('#roleIdListforAdd').combobox('reload');
		
		$('#NewUserDlg').window('open');
	}
	//提交添加
	function submitaddUser(){
		$('#NewUserFm').form('submit',{
			url:'UserServlet_add',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#NewUserDlg').window('close');
	                 $.messager.alert('添加成功提示',"添加用户成功");
	                 $('#UserManageDg').datagrid('reload');
	                 
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	//显示编辑用户
	function  showEditUserDlg(){
		$('#departmentIdforEdite').combobox('reload');
		$('#roleIdListforEdite').combobox('reload');
		var row=$('#UserManageDg').datagrid('getSelected');
		if(row){
			$('#EditUserDlg').window('open');
			$('#EditUserFm').form('load',row);
		}else
			$.messager.alert('系统提示','请选择要编辑的行');
	}
	//提交编辑用户
	function submitEditorUser(){
	var useridHidden = document.getElementById("userid").value;
		$('#EditUserFm').form('submit',{
			url:'UserServlet_edit?id='+useridHidden,
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#EditUserDlg').window('close');
	                 $.messager.alert('添加成功提示',"修改用户成功");
	                 $('#UserManageDg').datagrid('reload');
	                 
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	//提交删除
	function submitDeleteUser(){
		var row=$('#UserManageDg').datagrid('getSelected');
		if(row){
			$.post(
				'UserServlet_delete?id='+row.userid,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#UserManageDg').datagrid('reload');
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
		}else
			$.messager.alert('系统提示','请选择要删除的行');
	}
	//
	function submitResetPassword(){
		var row=$('#UserManageDg').datagrid('getSelected');
		if(row){
			$.post(
				'UserServlet_initPassword?id='+row.userid,
				function(data){
					var data=eval("("+data+")");
					if(data.success){ 
						$.messager.alert('系统提示','密码重置成功');
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
		}else
			$.messager.alert('系统提示','请选择要重置密码的行');
	}
</script>
