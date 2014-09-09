<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table id="UserManageDg">
</table>
<!--新增对话框!-->
<div id="NewUserDlg" class="easyui-window" closed="true" style="width:350px; height:340px;" maximizable="false"   modal="true" title="新增用户" minimizable="false" collapsible="false">
<form id="NewUserFm" method="post">
  <table>
    <tr>
      <td>
	  <s:select name="departmentId" cssclass="easyui-combobox" list="#departmentlist" listKey="id" listValue="D_name" headerKey="" headerValue="==请选择部门==" label="所属部门"></s:select>
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
      <td>
      <s:select name="roleIdList" cssclass="easyui-combotree" list="#rolelist" listKey="id" listValue="R_name" label="岗位"></s:select>
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
      <td>
	  <s:select name="departmentId" cssclass="easyui-combobox" list="#departmentlist" listKey="id" listValue="D_name" headerKey="" headerValue="==请选择部门==" label="所属部门"></s:select>
	  </select>
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
      <td>
	  <s:select name="roleIdList" cssclass="easyui-combotree" list="#rolelist" listKey="id" listValue="R_name" label="岗位"></s:select>
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
			toolbar:[{
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
			method:"get",
			title:"用户管理" 
		});
	});
	
	//显示新增用户
	function  showNewUserDlg(){
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
