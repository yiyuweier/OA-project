<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<div id="departmenttb">
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>
<table id="DepartmentManageDg">
</table>
<!--新增部门!-->
<s:hidden id="hidden" value="%{#session.parentId}"/>
<div id="NewDepartmentDlg" class="easyui-window" closed="true"   style="width:350px; height:240px; padding:5px;" maximizable="false"   modal="true" title="新增部门" minimizable="false" collapsible="false">
  <form id="NewDepartmentFm" method="post">
    <table>
      <tr>
        <td>部门名称：</td>
        <td><input name="D_name" type="text" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <input type="hidden" name="parentId" value="0"/>
      <tr>
        <td>部门说明：</td>
        <td><textarea name="D_description" class="easyui-validatebox" data-options="required:true" ></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center;"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitNewDepartment()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<!--编辑部门!-->
<div id="EditDepartmentDlg" class="easyui-window" closed="true" style="width:350px; height:240px; padding:5px;" maximizable="false"   modal="true" title="新增用户" minimizable="false" collapsible="false">
  <form id="EditDepartmentFm" method="post">
  <s:hidden id="hiddenDepartmentId" name="D_id"/>
    <table>
      <tr>
        <td>部门名称：</td>
        <td><input name="D_name" type="text" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td>
         	<s:select name="parentId" cssclass="SelectStyle" list="#departmentlist" listKey="id" listValue="D_name" headerKey="0" headerValue="==请选择上级部门==" value="所属上级名称"/>
        </td>
      </tr>
      <tr>
        <td>部门说明：</td>
        <td><textarea name="D_description" class="easyui-validatebox" data-options="required:true" ></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center;"><a href="#" class="easyui-linkbutton" iconCls="icon-ok"  onclick="submitEditDepartment()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript">
	$(function(){
	var hiddenElement = document.getElementById("hidden").value;
		$('#DepartmentManageDg').datagrid({ 
			url:'DepartmentServlet_list?parentId='+hiddenElement,
			fit:true,
			columns:[[
				{field:'ck',title:'选择',width:40,checkbox:true},
				{field:'D_name',title:'部门名称',width:80},
				{field:'parentDepartmentName',title:'所属上级名称',width:80},
				{field:'D_description',title:'部门说明',width:200} 
			]],
			fitColumns:true,
			toolbar:'#departmenttb',
		//	[{
		//		text:'新增',
		//		iconCls:'icon-add',
		//		handler:showNewDepartment
		//	},{
		//		text:'编辑',
		//		iconCls:'icon-edit',
		//		handler:showEditDepartment
		//	},{
		//		text:'删除',
		//		iconCls:'icon-remove',
		//		handler:submitDeleteDepartment
		//	}],
			loadMsg:'数据加载中请稍后……',  
			rownumbers: true,               
			striped: true, 
			pagination:true,
			title:'部门管理'
		});
		var p = $('#DepartmentManageDg').datagrid('getPager');  
		$(p).pagination({  
			pageSize: 10,//每页显示的记录条数，默认为10  
			pageList: [5,10,20,99],//可以设置每页记录条数的列表  
			beforePageText: '第',//页数文本框前显示的汉字  
			afterPageText: '页    共 {pages} 页',  
			displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
		});
	});
	function showNewDepartment(){
		$('#NewDepartmentDlg').window('open');
	}
	function submitNewDepartment(){
		$('#NewDepartmentFm').form('submit',{
			url:'DepartmentServlet_add',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#NewDepartmentDlg').window('close');
	                 $.messager.alert('添加成功提示',"添加部门成功");
	                 $('#DepartmentManageDg').datagrid('reload');
	            }else
	                 $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	function showEditDepartment(){
		var row=$('#DepartmentManageDg').datagrid('getSelected');
		if(row){
			$('#EditDepartmentFm').form('load',row);
			$('#EditDepartmentDlg').window('open');
		}else
			$.messager.alert('系统提示','请选择要编辑的行');
	}
	function submitDeleteDepartment(){
		var row=$('#DepartmentManageDg').datagrid('getSelected');
		if(row){
			$.post(
				'DepartmentServlet_delete?departmentid='+row.D_id,
				function(data){
					var data =eval('('+data+')');
					if(data.success){
						$.messager.alert('添加成功提示',"删除部门成功");
	                 	$('#DepartmentManageDg').datagrid('reload');
					}else
						$.messager.alert('',data.errorMsg);
				}
			);
		}else
			$.messager.alert('系统提示','请选择要删除的行');
	}
	function submitEditDepartment(){
	var hiddenDepartmentIdElement=document.getElementById("hiddenDepartmentId").value;
		$('#EditDepartmentFm').form('submit',{
			url:'DepartmentServlet_edit?departmentid='+hiddenDepartmentIdElement,
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#EditDepartmentDlg').window('close');
	                 $.messager.alert('添加成功提示',"修改部门成功");
	                 $('#DepartmentManageDg').datagrid('reload');
	                 
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
</script>
