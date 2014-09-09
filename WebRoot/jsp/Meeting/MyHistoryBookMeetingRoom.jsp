<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!--数据表!-->

<table id="BookMeetingRoomDg1">
</table>

<script type="text/javascript">
$(showBookMeetingRoomHistory());

//查看个人历史记录
function showBookMeetingRoomHistory(){
   
	$('#BookMeetingRoomDg1').datagrid({
		url:'MeetingbookServlet_userdetails',
		fit:true,
		columns:[[
			//{field:'id',title:'编号',width:40},
			{field:'meetingName',title:'会议名称',width:100},
			{field:'starttime',title:'开始时间',width:80},
			{field:'endtime',title:'结束时间',width:80},
			{field:'roomName',title:'会议室名称',width:40},
			{field:'bookGuest',title:'预定用户',width:40},
			{field:'booktime',title:'预定时间',width:80}
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
		title:"预定历史记录",
	    toolbar:[{
				text:'删除',
				iconCls:'icon-remove',
				handler:submitDeleteBookInfo
		}]
	});

}

function submitDeleteBookInfo(){
   	var row=$('#BookMeetingRoomDg1').datagrid('getSelected');
   	//alert(row.meetingName+" "+row.id);	
		if(row){
		  if(confirm("确定要删除"+row.meetingName+"吗？")){
			$.post(
				'MeetingbookServlet_delete1?id='+row.id,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#BookMeetingRoomDg1').datagrid('reload');
						$('#BookMeetingRoomDg').datagrid('reload');
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
		}
		}else 
			$.messager.alert('系统提示','请选择要删除的行');



}


 
</script>
