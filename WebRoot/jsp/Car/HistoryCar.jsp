<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--数据表!-->
<table id="CarDg1" class="easyui-datagrid">
</table>

<script type="text/javascript">
$(showHistoryCar());

function showHistoryCar(){
	$('#CarDg1').datagrid({
		url:'CarServlet_HistoryCar',
		fit:true,
		columns:[[
			{field:'id',title:'车辆编号',width:40,hidden:true},
			{field:'carName',title:'车辆名称',width:50},
			{field:'carPeople',title:'载重人数',width:40},
			{field:'code',title:'车牌号',width:50},
			{field:'carStatus',title:'状态',width:50},
			{field:'driverName',title:'司机姓名',width:50},
			{field:'driverPhone',title:'司机状态',width:60},
			{field:'carCreateTime',title:'创建时间',width:60},
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
		title:"车辆历史记录",
		
	});
} 

/*
function showBookCarHistory(){
    $('#CarDg').datagrid('loadData',{total:0,rows:[]}); 
	$('#CarDg').datagrid({
		url:'CarbookServlet_HistoryBookInfo',
		fit:true,
		columns:[[
			{field:'id',title:'编号',width:40,hidden:true},
			{field:'carUsage',title:'车辆用途',width:100},
			{field:'starttime',title:'开始时间',width:80},
			{field:'endtime',title:'结束时间',width:80},
			{field:'carName',title:'车辆名称',width:40},
			{field:'code',title:'车牌号',width:40},
			{field:'bookGuest',title:'预定用户',width:40},
			{field:'booktime',title:'预定时间',width:80}
		]],
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
*/
</script>
