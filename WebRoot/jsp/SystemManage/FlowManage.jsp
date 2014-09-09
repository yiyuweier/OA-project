<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<!--    
<div id="flowtb">
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>
-->
<table id="FlowDg">
</table>
<div id="EditFlowDlg" class="easyui-window" closed="true" style="width:300px; height:250px;" maximizable="false"   modal="true" title="编辑流程" minimizable="false" collapsible="false">
  <form id="EditFlowFm" method="post">
    <table>
      <tr>
        <td>流程名称：</td>
        <td>
        	<input type="text" class="easyui-validatebox" data-options="required:true" name="F_name"/>
        </td>
      </tr>
      <tr>
      	<td>流程种类</td>
        <td>
        	<input id="categoryId" class="easyui-combobox" name="categoryId" data-options="valueField:'id',textField:'text',url:'FlowDeployServlet_getFlowCategory'">
        </td>
      </tr>
      <tr>
        <td>流程说明：</td>
        <td><textarea type="text" class="easyui-validatebox" data-options="required:true" name="F_desc"></textarea></td>
      </tr>
      <tr>
        <td colspan="2"><a href="#" class="easyui-linkbutton" iconCls="icon-ok">修改</a> </td>
      </tr>
    </table>
  </form>
</div>
<!--新增流程分类!-->
<div id="NewFlowCategoryDlg" class="easyui-window" closed="true" style="width:300px; height:120px;" maximizable="false"   modal="true" title="新增流程类别" minimizable="false" collapsible="false">
  <form id="NewFlowCategoryFm" method="post">
    <table>
      <tr>
        <td>流程类别：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="Category_name"/>
        </td>
      </tr>
      <tr>
        <td colspan="2"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitNewFlowCategory()">修改</a> </td>
      </tr>
    </table>
  </form>
</div>
<!--部署流程!-->
<div id="deployFlowDlg" class="easyui-window" closed="true" style="width:350px; height:300px;" maximizable="false"   modal="true" title="部署流程" minimizable="false" collapsible="false">
  <form id="deployFlowFm" method="post" enctype="multipart/form-data">
    <table>
      <tr>
        <td>流程名称：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="FlowProcess_name"/>
        </td>
      </tr>
      <tr>
        <td>流程种类</td>
        <td>
        	<input id="categoryId" class="easyui-combobox" name="categoryId" mode="remote" data-options="valueField:'id',textField:'text',url:'FlowDeployServlet_getFlowCategory'">
        </td>
      </tr>
      <tr>
        <td>流程描述：</td>
        <td><textarea type="text" class="easyui-validatebox" data-options="required:true" name="FlowProcess_desc"></textarea>
        </td>
      </tr>
      <tr>
        <td>流程配置文件及图片（打包成ZIP）：</td>
        <td><input type="file" class="easyui-validatebox" data-options="required:true" name="image"/>
        </td>
      </tr>
      <tr>
        <td colspan="2"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitDeployFlow()">部署流程</a> </td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript">
$(function(){
	$('#FlowDg').datagrid({
		url:'FlowDeployServlet_list',
		fit:true,
		columns:[[
			{field:'id',title:'选择',width:40,checkbox:true},
			{field:'F_name',title:'流程名称',width:100},
			{field:'flowcategory',title:'流程分类',width:80},
			{field:'F_desc',title:'流程描述',width:200} 
		]],
		fitColumns:true,
		toolbar:
		[{
			text:'编辑流程',
			iconCls:'icon-edit',
			handler:showEditFlowDlg
		},{
			text:'删除流程',
			iconCls:'icon-remove',
			handler:submitDeleteFlow
		},{
			text:'新建流程分类',
			iconCls:'icon-add',
			handler:showNewFlowCategoryDlg
		},{
			text:'部署流程',
			iconCls:'icon-add',
			handler:showDeployFlowDlg
		}],
		loadMsg:'数据加载中请稍后……',  
		rownumbers: true,               
		striped: true, 
		pagination:true,
		method:"post",
		title:"流程管理" 
	});
	var p = $('#FlowDg').datagrid('getPager');  
	$(p).pagination({  
		pageSize: 10,//每页显示的记录条数，默认为10  
		pageList: [5,10,20,99],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	});
});
//显示编辑流程
function showEditFlowDlg(){
	var row=$('#FlowDg').datagrid('getSelected');
	if(row){
		$('#EditFlowDlg').window('open');
		$('#EditFlowFm').form('load',row);
	}else
		$.messager.alert('系统提示','请选择编辑的行');
}
//显示新建流程分类
function showNewFlowCategoryDlg(){
	$('#NewFlowCategoryDlg').window('open');
}
//处理流程分类，持久化流程分类
function submitNewFlowCategory(){
	$('#NewFlowCategoryFm').form('submit',{
			url:'FlowDeployServlet_addCategory',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#NewFlowCategoryDlg').window('close');
	                 $.messager.alert('添加成功提示',"添加流程分类成功");
	                 $('#FlowDg').datagrid('reload');
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
}

//部署流程页面
function showDeployFlowDlg(){
	$('#categoryId').combobox('reload');
	$('#deployFlowDlg').window('open');
}
//持久化流程
function submitDeployFlow(){
	$('#deployFlowFm').form('submit',{
			url:'UploadFlowZipServlet',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#deployFlowDlg').window('close');
	                 $.messager.alert('添加成功提示',"部署流程成功");
	                 $('#FlowDg').datagrid('reload');
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
}
//删除流程提交
function  submitDeleteFlow(){
	var row=$('#FlowDg').datagrid('getSelected');
	if(row){
		$.post('',
			function(data){
				var data=eval('('+data+')');
				if(data.success){
					$('FlowDg').datagrid('reload');
				}else
					$.messager.alert('系统提示',data.errorMsg);
			}
		)
	}else
		$.messager.alert('系统提示','请选择要删除的行');
}
</script>
