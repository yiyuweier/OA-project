<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="easyui-layout"  data-options="fit:true">
  <div  style="padding:5px;" region="center" title="通讯录" >
    <ul id="MaillistTree" class="easyui-tree" data-options="
                
                 url: 'UserServlet_test',
                method: 'get',
                animate: true,
                onContextMenu: function(e,node){
                    e.preventDefault();
                    $(this).tree('select',node.target);
                    $('#MailListMenu').menu('show',{
                        left: e.pageX,
                        top: e.pageY
                    });
                },
				onClick:TreeNodeClick
            ">
    </ul>
  </div>
  <div style="width:300px;" split="true" region="east" >
    <table id="mailInfo"  title="详细信息" data-options="fit:true" >
    </table>
  </div>
</div>
<!--新增分组!-->
<div id="NewArrayDlg" class="easyui-window" closed="true"   resizable="false"
    style="width:300px; height:130px; padding: 10px 5px;"  maximizable="false"
    modal="true" title="新增分组" minimizable="false" collapsible="false">
  <form id="NewArrayFm">
    <table>
      <tr>
        <td>分组名称：</td>
        <td><input type="hidden" value="false" name="leaf" />
          <input type="text" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center; "><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitNewArray()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<!--新增联系人!-->
<div id="NewMailDlg" class="easyui-window" closed="true" style="width:300px; height:280px;" maximizable="false"
    modal="true" title="新增联系人" minimizable="false" collapsible="false" resizable="false">
  <form id="NewMailFm">
    <table>
      <tr>
        <td>姓名：</td>
        <td><input type="text"  name="name" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td>电话：</td>
        <td><input type="text"  name="phone" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td>地址：</td>
        <td><input type="text" name="address" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td>Email:</td>
        <td><input type="text"  name="email" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td>QQ:</td>
        <td><input type="text"  name="QQ" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>
      <tr>
        <td>备注:</td>
        <td><textarea name="remarks" class="easyui-validatebox" data-options="required:true" ></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center;"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitNewMail()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<!--发送消息!-->
<div id="SendMailDlg" class="easyui-window" closed="true" style="width:400px; height:220px;" maximizable="false"
    modal="true" title="发送消息" minimizable="false" collapsible="false" resizable="false">
  <form id="SendMailFm">
    <table>
      <tr>
        <td>消息:</td>
        <td><textarea name="remarks" class="easyui-validatebox" cols="35" rows="5" data-options="required:true" ></textarea>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center;"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSendMail()">提交</a> </td>
      </tr>
    </table>
  </form>
</div>
<div id="MailListMenu" class="easyui-menu" style="width:120px;">
  <div  onClick="showAddArray();" data-options="iconCls:'icon-groupadd'">添加分组</div>
  <div  onClick="removeit();" data-options="iconCls:'icon-groupdel'">删除分组</div>
  <div  onClick="showAddMail();" data-options="iconCls:'icon-useradd'">添加联系人</div>
  <div  onClick="removeit();" data-options="iconCls:'icon-userdel'">删除联系人</div>
  <div  onClick="showSendMail();" data-options="iconCls:'icon-email_go'" >发送消息</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#mailInfo').propertygrid({	   
			columns: [[
					{ field: 'name', title: 'Name', width: 100, resizable: true },
					{ field: 'value', title: 'value', width: 100, resizable: false }
			]],
		});
	});
	   function TreeNodeClick(node){
			if(node.attributes&&node.attributes.leaf){
				$('#mailInfo').propertygrid('loadData',node.attributes.data.rows); 
			}
	   }
		function showAddArray(){
            var t = $('#MaillistTree');
            var node = t.tree('getSelected');
			if(node.attributes&&!node.attributes.leaf){
				$('#NewArrayDlg').window('open');
			}else
				$.messager.alert('系统提示','请选择一个父分组');
        }
		function showAddMail(){
            var t = $('#MaillistTree');
            var node = t.tree('getSelected');
			if(node.attributes&&!node.attributes.leaf){
				$('#NewMailDlg').window('open');
			}else
				$.messager.alert('系统提示','请选择一个分组');
        }
        function removeit(){
            var node = $('#MaillistTree').tree('getSelected');
            $('#MaillistTree').tree('remove', node.target);
        }
        function showSendMail(){ 
            $('#SendMailDlg').window('open');
        }
		function submitNewArray(){
			$('#NewArrayFm').form('submit',{
				url:'',
				success:function(data){
					var data=eval('('+data+')');
					if(data.success){
						$('#MaillistTree').tree('reload');
						$('#NewArrayDlg').window('close');
					}else
						$.messager.alert('',data.errorMsg);
				}
			})
		}
		function submitNewMail(){
			$('#NewMailFm').form('submit',{
				url:'',
				success:function(data){
					var data=eval('('+data+')');
					if(data.success){
						$('#MaillistTree').tree('reload');
						$('#NewMailDlg').window('close');
					}else
						$.messager.alert('',data.errorMsg);
				}
			})
		}
</script>
