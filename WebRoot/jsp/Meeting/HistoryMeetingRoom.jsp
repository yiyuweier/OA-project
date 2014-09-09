<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!--数据表!-->
<table id="MeetingRoomDg2" class="easyui-datagrid">
</table>


<script type="text/javascript">
$(showHistoryMeetingRoom());

function showHistoryMeetingRoom(){
	$('#MeetingRoomDg2').datagrid({
		url:'MeetingServlet_HistoryMeetingRoom',
		fit:true,
		columns:[[
			//{field:'ck',title:'选择',width:40,checkbox:true},
			{field:'id',title:'会议室编号',width:40,hidden:true},
			{field:'name',title:'名称',width:100},
			{field:'count',title:'人数',width:40},
			{field:'address',title:'会议室地址',width:200},
			//{field:'state',title:'状态',width:60},
			{field:'createTime',title:'创建时间',width:70}
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
		title:"会议室历史记录",
		
	});

} 

//查看历史记录

/*function showBookMeetingRoomHistory(){
   $('#MeetingRoomDg').datagrid('loadData',{total:0,rows:[]}); 
	$('#MeetingRoomDg').datagrid({
		url:'MeetingbookServlet_HistoryBookMeetingRoom',
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
		pageList: [1,2,10,20,99],//可以设置每页记录条数的列表  
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
				text:'返回',
				iconCls:'icon-book',
				handler:load0
		}]
	});

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
*/
</script>
