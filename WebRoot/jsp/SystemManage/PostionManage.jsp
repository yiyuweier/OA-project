<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">       
<div id="roletb">
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>
<table id="PostionManageDg">
</table>
<!--新增岗位!-->
<div id="NewPostionDlg" class="easyui-window" closed="true" style="width:350px; height:180px;" maximizable="false"   modal="true" title="新增岗位" minimizable="false" collapsible="false">
  <form id="NewPostionFm" method="post">
    <table>
      <tr>
        <td>岗位名称：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="R_name"/>
        </td>
      </tr>
      <tr>
        <td>岗位说明：</td>
        <td><textarea name="R_description"></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center;"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitNewPostion()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<!--编辑岗位!-->
<div id="EditPostionDlg" class="easyui-window" closed="true" style="width:350px; height:180px;" maximizable="false"   modal="true" title="部署流程" minimizable="false" collapsible="false">
  <form id="EditPostionFm" method="post">
    <input type="hidden" name="postionid" id="Hiddenpostionid"/>
    <table>
      <tr>
        <td>岗位名称：</td>
        <td><input type="text" name="R_name" class="easyui-validatebox" data-options="required:true" />
        </td>
      </tr>
      <tr>
        <td>岗位说明：</td>
        <td><textarea name="R_description"  class="easyui-validatebox" data-options="required:true" ></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center;"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitEditPostion()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript">
	$(function(){
		$('#PostionManageDg').datagrid({
			url:'RoleServlet_list',
			fit:true,
			columns:[[
				{field:'ck',title:'选择',width:40,checkbox:true},
				{field:'postionid',title:'岗位编号',width:80},
				{field:'R_name',title:'岗位名称',width:80}, 
				{field:'R_description',title:'岗位说明',width:100},
				{field:'url',title:'操作',width:20,
					formatter:function(v,row,index){
						return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="Open2(\''+"权限管理"+'\',\''+row.url+'\');">权限</a>';
						
					}
				} 
			]],
			fitColumns:true,
			toolbar:'#roletb',
		//	[{
		//		text:'新增',
		//		iconCls:'icon-add',
		//		handler:showNewPostionDlg
		//	},{
		//		text:'编辑',
		//		iconCls:'icon-edit',
		//		handler:showEditPostionDlg
		//	},{
		//		text:'删除',
		//		iconCls:'icon-remove',
		//		handler:deleterole
		//	}],
			loadMsg:'数据加载中请稍后……',  
			rownumbers: true,               
			striped: true, 
			pagination:true,
			title:"岗位管理" 
		});
		var p = $('#PostionManageDg').datagrid('getPager');  
		$(p).pagination({  
			pageSize: 10,//每页显示的记录条数，默认为10  
			pageList: [5,10,20,99],//可以设置每页记录条数的列表  
			beforePageText: '第',//页数文本框前显示的汉字  
			afterPageText: '页    共 {pages} 页',  
			displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
		});
	});
	function showNewPostionDlg(){
		$('#NewPostionDlg').window('open');
	}
	function showPrivilegeDlg(){
		$('#PrivilegeDlg').window('open');
	}
	function submitNewPostion(){
		$('#NewPostionFm').form('submit',{
			url:'RoleServlet_add',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#NewPostionDlg').window('close');
	                 $.messager.alert('添加成功提示',"添加岗位成功");
	                 $('#PostionManageDg').datagrid('reload');
	                 
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	function showEditPostionDlg(){
		var row=$('#PostionManageDg').datagrid('getSelected');
		if(row){
			$('#EditPostionFm').form('load',row);
			$('#EditPostionDlg').window('open');
		}else
			$.messager.alert('系统提示','请选择要编辑的行');
		
	}
	function submitEditPostion(){
	var postionid = document.getElementById("Hiddenpostionid").value;
		$('#EditPostionFm').form('submit',{
			url:'RoleServlet_edit?id='+postionid,
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	 $('#EditPostionDlg').window('close');
	                 $.messager.alert('添加成功提示',"修改岗位成功");
	                 $('#PostionManageDg').datagrid('reload');
	                 
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	function deleterole(){
		var row=$('#PostionManageDg').datagrid('getSelected');
		if(row){
			$.post(
				'RoleServlet_delete?id='+row.postionid,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#PostionManageDg').datagrid('reload');
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
		}else
			$.messager.alert('系统提示','请选择要删除的行');
	}
</script>
