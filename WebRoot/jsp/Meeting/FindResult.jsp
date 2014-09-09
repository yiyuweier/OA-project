<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!--数据表!-->

<table id="BookMeetingRoomDg3">
</table>

<script type="text/javascript">
$(selectByTime());
function selectByTime(){
	    var a=$("#Mstarttime").datetimebox("getValue");
         var b=$("#Mendtime").datetimebox("getValue");  
        if(a<=b){

 	$('#BookMeetingRoomDg3').datagrid({	
		url:'MeetingbookServlet_selectbytime?startTime='+a+'&endTime='+b,
		fit:true,
		columns:[[
			{field:'id',title:'编号',width:40,hidden:true},
			{field:'name',title:'会议室名称',width:100},
			{field:'address',title:'会议室地址',width:100},
			{field:'people',title:'人数',width:80},
		]],
		pageSize: 10,//每页显示的记录条数，默认为10  
		pageList: [2,5,10,20,99],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' ,
		fitColumns:true,
		loadMsg:'数据加载中请稍后……',  
		rownumbers: true,               
		striped: true, 
		pagination:true,
		method:"post",
		title:"空闲会议室",
		
	});
	     
	}
	else 
	alert("时间顺序有误");     
	
	}
</script>