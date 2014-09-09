<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<div id="MeetRoomtb">
	
		<s:if test='#session.user.hasPrivilegeByName("车辆管理相关操作")'>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:showNewMeetingRoomDlg()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:submitDeleteMeetingRoom()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:showEditMeetingRoomDlg()">编辑</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-book" plain="true" onclick="javascript:showBookMeetingRoomHistory()">会议室预定记录</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-book" plain="true" onclick="javascript:showHistoryMeetingRoom()">会议室历史记录</a>
		</s:if>
    
</div>


<!--数据表!-->
<table id="MeetingRoomDg" class="easyui-datagrid">
</table>





<!--新增!-->
<div id="NewMeetingRoomDlg" class="easyui-window" closed="true" resizable="false"
    style="width: 280px; height: 250x; padding: 10px 5px;"  maximizable="false"
    modal="true" title="新增会议室" minimizable="false" collapsible="false">
  <form id="NewMeetingRoomFm" method="post" name="NewMeetingRoomFm">
    <table>
      
      <tr>
        <td>名称：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="name"/></td>
      </tr>
      <tr>
        <td>容纳人数：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="people"/></td>
      </tr>
      <tr>
        <td>会议室地址</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="roomAddress"/></td>
      </tr>
      <tr>
        <td>状态：</td>
        <td>
		<input type="radio" name="state" value="1" checked />可用
        <input type="radio" name="state" value="0" />不可用
		</td>
      </tr>
      <tr>
        <td align="center" colspan="2"><a href="#" class="easyui-linkbutton" iconcls="icon-search"  onclick="submitaddMeetingRoom()">新增</a></td>
      </tr>
    </table>
  </form>
</div>
<!--编辑!
resizable="false"
-->
<div id="EditMeetingRoomDlg" class="easyui-window" closed="true" 
    style="width: 280px; height: 250px; padding: 10px 5px;"  maximizable="false"
    modal="true" title="编辑会议室" minimizable="false" collapsible="false">
  <form id="EditMeetingRoomFm" method="post">
    <table>
     <!--   <tr>
        <td>编号：</td>
         </tr>
      -->
        <td><input type="hidden" class="easyui-validatebox" data-options="required:true" name="id" /></td>
     
      <tr>
        <td>名称：</td>
        <td><input type="text"  class="easyui-validatebox" data-options="required:true" name="name" /></td>
      </tr>
      <tr>
        <td>容纳人数：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="people" /></td>
      </tr>
      <tr>
        <td>会议室地址</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="roomAddress" /></td>
      </tr>
       
      <tr>
      
      <!--  <input type="text" class="easyui-validatebox" data-options="required:true" name="state"  />-->
        <td>状态：</td>
        <td><input type="radio" name="state" value="1" />可用
        <input type="radio" name="state" value="0" />不可用
        </td>
      </tr> 
      <tr>
        <td style="text-align:center;" colspan="2"><a href="#" class="easyui-linkbutton" iconcls="icon-ok" onclick="submitUpdateMeetingRoom()" >编辑</a></td>
      </tr>
    </table>
  </form>
</div>


<script type="text/javascript">
$(load0());
function load0(){
 //$('#MeetingRoomDg').datagrid('loadData',{total:0,rows:[]}); 
	$('#MeetingRoomDg').datagrid({	
		url:'MeetingServlet_list',
		fit:true,
		columns:[[
			//{field:'ck',title:'选择',width:40,checkbox:true},
			{field:'id',title:'会议室编号',width:40,hidden:true},
			{field:'name',title:'名称',width:100},
			{field:'people',title:'人数',width:40},
			{field:'roomAddress',title:'会议室地址',width:200},
			{field:'state',title:'状态',width:60}
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
		title:"会议室管理",
		toolbar:'#MeetRoomtb'
		/*
		[
			{
				text:'新增',
				iconCls:'icon-add',
				handler:showNewMeetingRoomDlg 
			},
			{
				text:'编辑',
				iconCls:'icon-edit',
				handler:showEditMeetingRoomDlg 
			},{
				text:'删除',
				iconCls:'icon-remove',
				handler:submitDeleteMeetingRoom
			},{
				text:'会议室历史记录',
				iconCls:'icon-book',
				handler:showHistoryMeetingRoom
			}
			,{
				text:'预定历史记录',
				iconCls:'icon-book',
				handler:showBookMeetingRoomHistory
			}
		]
		*/
	});

}
//新增对话框显示
function showNewMeetingRoomDlg(){
	$('#NewMeetingRoomDlg').window('open');
	//$('#NewMeetingRoomFm').form('clear');
}

//提交添加
	function submitaddMeetingRoom(){
	if(form_onsubmit()){
		$('#NewMeetingRoomFm').form('submit',{
			url:'MeetingServlet_addmeetingroom',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"添加会议室成功");
	                 $('#NewMeetingRoomDlg').window('close');
	                 $('#MeetingRoomDg').datagrid('reload');
                     load();
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	else alert("提交失败");
	}
//编辑对话框显示
function showEditMeetingRoomDlg(){
	var row=$('#MeetingRoomDg').datagrid('getSelected');
	if(row){  
     $('#EditMeetingRoomDlg').window('open');
	if(row.state=="可用")
	 row.state=1; 
	 else
	 row.state=0;  
     $('#EditMeetingRoomFm').form('load',row);

	}else
		$.messager.alert('系统提示','请选择编辑的行');
}

//删除会议室
function submitDeleteMeetingRoom(){ 
	   	var row=$('#MeetingRoomDg').datagrid('getSelected');
		
		if(row){
		  if(confirm("确定要删除"+row.name+"吗？")){
			$.post(
				'MeetingServlet_delete?id='+row.id,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#MeetingRoomDg').datagrid('reload');
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
		}
		}else //if(row==undefined)
			$.messager.alert('系统提示','请选择要删除的行');
	}
	
function submitUpdateMeetingRoom(){
             $('#EditMeetingRoomFm').form('submit',{
			url:'MeetingServlet_update',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"更新会议室成功");
	                 $('#EditMeetingRoomDlg').window('close');
	                 $('#MeetingRoomDg').datagrid('reload');
                     load();
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})

}

function showHistoryMeetingRoom(){

	var url="./jsp/Meeting/HistoryMeetingRoom.jsp";
	Open("会议室历史记录",url);

} 

//查看历史记录
function showBookMeetingRoomHistory(){
   var url="./jsp/Meeting/HistoryBookMeetingRoom.jsp";
	Open("预定历史记录",url);

}

function form_onsubmit() { 
var a=document.NewMeetingRoomFm.people.value;
var patten = /^-?\d+$/;
 if(!(patten.test(a))){
  alert("容纳人数请输入整数"); 
document.NewMeetingRoomFm.people.focus();
return false;     
}
else 
   return true;
}

</script>
